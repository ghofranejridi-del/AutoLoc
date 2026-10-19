package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {
    private final IEmployeRepository repository;
    @Override public Employe create(Employe value) {
        if (value.getIdEmploye() != null) throw new IllegalArgumentException("Un nouvel employé ne doit pas avoir d'identifiant");
        validate(value); return repository.save(value);
    }
    @Override public Employe findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employé", id)); }
    @Override public List<Employe> findAll() { return repository.findAll(); }
    @Override public Employe update(Long id, Employe value) {
        validate(value); Employe current = findById(id);
        current.setNom(value.getNom()); current.setPrenom(value.getPrenom()); current.setRole(value.getRole()); current.setAgence(value.getAgence());
        return repository.save(current);
    }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Employé", id); repository.deleteById(id); }
    private void validate(Employe value) {
        if (value.getNom() == null || value.getNom().isBlank() || value.getPrenom() == null || value.getPrenom().isBlank() || value.getRole() == null) throw new IllegalArgumentException("Le nom, le prénom et le rôle de l'employé sont obligatoires");
    }
}
