package tn.esprit.autoloc.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.ModePaiement;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IContratRepository;

/**
 * Demonstrates repository CRUD and the Contrat-Paiement cascade when explicitly enabled.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "autoloc.demo.contrat-crud", havingValue = "true")
public class ContratCrudDemo implements CommandLineRunner {

    private final IContratRepository contratRepository;

    @Override
    public void run(String... args) {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("100.00"));
        contrat.setValide(true);

        Paiement paiement = new Paiement();
        paiement.setMontant(new BigDecimal("100.00"));
        paiement.setDatePaiement(LocalDate.now());
        paiement.setModePaiement(ModePaiement.CARTE);
        paiement.setContrat(contrat);
        contrat.getPaiements().add(paiement);

        Contrat saved = contratRepository.save(contrat);
        log.info("CREATE: contrat id={}", saved.getIdContrat());

        Contrat loaded = contratRepository.findById(saved.getIdContrat())
                .orElseThrow(() -> new IllegalStateException("Contrat introuvable apres save"));
        log.info("READ: contrat id={}, montant={}",
                loaded.getIdContrat(), loaded.getMontantTotal());

        loaded.setMontantTotal(new BigDecimal("120.00"));
        Contrat updated = contratRepository.save(loaded);
        log.info("UPDATE: contrat id={}, montant={}",
                updated.getIdContrat(), updated.getMontantTotal());

        contratRepository.deleteById(updated.getIdContrat());
        log.info("DELETE: contrat id={} (cascade paiement)", updated.getIdContrat());
    }
}
