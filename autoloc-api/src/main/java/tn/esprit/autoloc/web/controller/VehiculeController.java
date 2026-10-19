package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;
import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules")
public class VehiculeController {
    private final IVehiculeService service;

    @GetMapping @Operation(summary = "Lister les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste des véhicules")
    public List<Vehicule> findAll() { return service.findAll(); }

    @GetMapping("/{id}") @Operation(summary = "Consulter un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule trouvé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public Vehicule findById(@Parameter(description = "Identifiant du véhicule") @PathVariable Long id) { return service.findById(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Véhicule créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
    public Vehicule create(@RequestBody Vehicule value) { return service.create(value); }

    @PutMapping("/{id}") @Operation(summary = "Modifier un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule modifié"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule value) { return service.update(id, value); }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Véhicule supprimé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public void deleteById(@PathVariable Long id) { service.deleteById(id); }
}
