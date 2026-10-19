package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {
    private final IClientRepository repository;
    @Override public Client create(Client value) { if (value.getIdClient() != null) throw new IllegalArgumentException("Un nouveau client ne doit pas avoir d'identifiant"); validate(value); return repository.save(value); }
    @Override public Client findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", id)); }
    @Override public List<Client> findAll() { return repository.findAll(); }
    @Override public Client update(Long id, Client value) { validate(value); Client current = findById(id); current.setNom(value.getNom()); current.setPrenom(value.getPrenom()); current.setEmail(value.getEmail()); current.setTelephone(value.getTelephone()); current.setNumPermis(value.getNumPermis()); current.setDateInscription(value.getDateInscription()); return repository.save(current); }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Client", id); repository.deleteById(id); }
    private void validate(Client value) { if (value.getNom() == null || value.getNom().isBlank() || value.getPrenom() == null || value.getPrenom().isBlank() || value.getEmail() == null || value.getEmail().isBlank() || value.getNumPermis() == null || value.getNumPermis().isBlank() || value.getDateInscription() == null) throw new IllegalArgumentException("Les informations d'identification du client sont obligatoires"); }
}
