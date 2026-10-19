package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {
    private final IMaintenanceRepository repository;
    @Override public Maintenance create(Maintenance value) { if (value.getIdMaintenance() != null) throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant"); validate(value); return repository.save(value); }
    @Override public Maintenance findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Maintenance", id)); }
    @Override public List<Maintenance> findAll() { return repository.findAll(); }
    @Override public Maintenance update(Long id, Maintenance value) { validate(value); Maintenance current = findById(id); current.setDateDebut(value.getDateDebut()); current.setDateFin(value.getDateFin()); current.setDescription(value.getDescription()); current.setVehicule(value.getVehicule()); return repository.save(current); }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Maintenance", id); repository.deleteById(id); }
    private void validate(Maintenance value) { if (value.getDateDebut() == null) throw new IllegalArgumentException("La date de début de maintenance est obligatoire"); if (value.getDateFin() != null && value.getDateFin().isBefore(value.getDateDebut())) throw new IllegalArgumentException("La date de fin ne peut pas précéder la date de début"); }
}
