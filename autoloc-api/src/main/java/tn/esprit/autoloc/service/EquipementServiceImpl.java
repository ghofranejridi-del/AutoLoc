package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {
    private final IEquipementRepository repository;
    @Override public Equipement create(Equipement value) { if (value.getIdEquipement() != null) throw new IllegalArgumentException("Un nouvel équipement ne doit pas avoir d'identifiant"); validate(value); return repository.save(value); }
    @Override public Equipement findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Équipement", id)); }
    @Override public List<Equipement> findAll() { return repository.findAll(); }
    @Override public Equipement update(Long id, Equipement value) { validate(value); Equipement current = findById(id); current.setLibelle(value.getLibelle()); return repository.save(current); }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Équipement", id); repository.deleteById(id); }
    private void validate(Equipement value) { if (value.getLibelle() == null || value.getLibelle().isBlank()) throw new IllegalArgumentException("Le libellé de l'équipement est obligatoire"); }
}
