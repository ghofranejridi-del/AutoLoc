package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository repository;
    @Override public Agence create(Agence value) { if (value.getIdAgence() != null) throw new IllegalArgumentException("Une nouvelle agence ne doit pas avoir d'identifiant"); validate(value); return repository.save(value); }
    @Override public Agence findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence", id)); }
    @Override public List<Agence> findAll() { return repository.findAll(); }
    @Override public Agence update(Long id, Agence value) { validate(value); Agence current = findById(id); current.setNom(value.getNom()); current.setVille(value.getVille()); current.setAdresse(value.getAdresse()); current.setTelephone(value.getTelephone()); return repository.save(current); }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Agence", id); repository.deleteById(id); }
    private void validate(Agence value) { if (value.getNom() == null || value.getNom().isBlank() || value.getVille() == null || value.getVille().isBlank() || value.getAdresse() == null || value.getAdresse().isBlank()) throw new IllegalArgumentException("Le nom, la ville et l'adresse de l'agence sont obligatoires"); }
}
