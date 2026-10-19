package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;
import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés")
public class EmployeController {
    private final IEmployeService service;

    @GetMapping @Operation(summary = "Lister les employés")
    @ApiResponse(responseCode = "200", description = "Liste des employés")
    public List<Employe> findAll() { return service.findAll(); }

    @GetMapping("/{id}") @Operation(summary = "Consulter un employé")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Employé trouvé"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
    public Employe findById(@Parameter(description = "Identifiant de l'employé") @PathVariable Long id) { return service.findById(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un employé")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Employé créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
    public Employe create(@RequestBody Employe value) { return service.create(value); }

    @PutMapping("/{id}") @Operation(summary = "Modifier un employé")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Employé modifié"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
    public Employe update(@PathVariable Long id, @RequestBody Employe value) { return service.update(id, value); }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un employé")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Employé supprimé"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
    public void deleteById(@PathVariable Long id) { service.deleteById(id); }
}
