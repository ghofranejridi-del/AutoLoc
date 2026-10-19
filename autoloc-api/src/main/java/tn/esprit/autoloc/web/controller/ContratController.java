package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;
import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrats", description = "Gestion des contrats de location")
public class ContratController {
    private final IContratService service;

    @GetMapping @Operation(summary = "Lister les contrats")
    @ApiResponse(responseCode = "200", description = "Liste des contrats")
    public List<Contrat> findAll() { return service.findAll(); }

    @GetMapping("/{id}") @Operation(summary = "Consulter un contrat")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Contrat trouvé"), @ApiResponse(responseCode = "404", description = "Contrat introuvable")})
    public Contrat findById(@Parameter(description = "Identifiant du contrat") @PathVariable Long id) { return service.findById(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un contrat")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Contrat créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
    public Contrat create(@RequestBody Contrat value) { return service.create(value); }

    @PutMapping("/{id}") @Operation(summary = "Modifier un contrat")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Contrat modifié"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Contrat introuvable")})
    public Contrat update(@PathVariable Long id, @RequestBody Contrat value) { return service.update(id, value); }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un contrat")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Contrat supprimé"), @ApiResponse(responseCode = "404", description = "Contrat introuvable")})
    public void deleteById(@PathVariable Long id) { service.deleteById(id); }
}
