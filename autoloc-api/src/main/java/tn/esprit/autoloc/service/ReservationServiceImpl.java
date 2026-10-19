package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository repository;
    @Override public Reservation create(Reservation value) { if (value.getIdReservation() != null) throw new IllegalArgumentException("Une nouvelle réservation ne doit pas avoir d'identifiant"); validate(value); return repository.save(value); }
    @Override public Reservation findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Réservation", id)); }
    @Override public List<Reservation> findAll() { return repository.findAll(); }
    @Override public Reservation update(Long id, Reservation value) { validate(value); Reservation current = findById(id); current.setDateDebut(value.getDateDebut()); current.setDateFin(value.getDateFin()); current.setStatut(value.getStatut()); current.setClient(value.getClient()); current.setVehicule(value.getVehicule()); return repository.save(current); }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Réservation", id); repository.deleteById(id); }
    private void validate(Reservation value) { if (value.getDateDebut() == null || value.getDateFin() == null || value.getStatut() == null) throw new IllegalArgumentException("Les dates et le statut de la réservation sont obligatoires"); if (value.getDateFin().isBefore(value.getDateDebut())) throw new IllegalArgumentException("La date de fin ne peut pas précéder la date de début"); }
}
