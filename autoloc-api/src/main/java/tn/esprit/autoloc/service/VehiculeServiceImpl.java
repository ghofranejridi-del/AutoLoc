package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository repository;
    @Override public Vehicule create(Vehicule value) {
        if (value.getIdVehicule() != null) throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        validate(value); return repository.save(value);
    }
    @Override public Vehicule findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Véhicule", id)); }
    @Override public List<Vehicule> findAll() { return repository.findAll(); }
    @Override public Vehicule update(Long id, Vehicule value) {
        validate(value); Vehicule current = findById(id);
        current.setImmatriculation(value.getImmatriculation()); current.setMarque(value.getMarque()); current.setModele(value.getModele()); current.setCategorie(value.getCategorie()); current.setTarifJournalier(value.getTarifJournalier()); current.setStatut(value.getStatut()); current.setAgence(value.getAgence()); current.setEquipements(value.getEquipements());
        return repository.save(current);
    }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Véhicule", id); repository.deleteById(id); }
    private void validate(Vehicule value) {
        if (value.getImmatriculation() == null || value.getImmatriculation().isBlank() || value.getMarque() == null || value.getMarque().isBlank() || value.getModele() == null || value.getModele().isBlank() || value.getCategorie() == null || value.getStatut() == null || value.getTarifJournalier() == null || value.getTarifJournalier().signum() < 0) throw new IllegalArgumentException("Les informations du véhicule sont obligatoires et son tarif ne peut pas être négatif");
    }
}
