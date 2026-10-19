package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.service.IPaiementService;
import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "Consultation des paiements liés aux contrats")
public class PaiementController {
    private final IPaiementService service;

    @GetMapping @Operation(summary = "Lister les paiements")
    @ApiResponse(responseCode = "200", description = "Liste des paiements")
    public List<Paiement> findAll() { return service.findAll(); }

    @GetMapping("/{id}") @Operation(summary = "Consulter un paiement")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Paiement trouvé"), @ApiResponse(responseCode = "404", description = "Paiement introuvable")})
    public Paiement findById(@Parameter(description = "Identifiant du paiement") @PathVariable Long id) { return service.findById(id); }
}
