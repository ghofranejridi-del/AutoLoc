# AutoLoc

Plateforme de gestion de location de véhicules multi-agences.
Projet réalisé dans le cadre de l'UP ASI (Architecture des Systèmes d'Information), ESPRIT.

## Objectifs du projet
- Gérer le parc de véhicules de plusieurs agences de location
- Permettre aux clients de consulter, réserver et louer des véhicules
- Faciliter le travail des agents et des responsables d'agence
- Fournir une API REST documentée (Spring Boot, Swagger)

## Acteurs identifiés
| Acteur | Rôle |
|---|---|
| Client | Consulte les véhicules, réserve et loue |
| Agent d'agence | Gère les réservations, les retours et l'état des véhicules |
| Responsable d'agence | Supervise son agence, valide les opérations, consulte les statistiques |
| Administrateur | Gère les agences, les utilisateurs et la configuration globale |

## Cas d'utilisation (première liste)
- Client : s'inscrire, rechercher un véhicule, réserver, annuler, consulter son historique
- Agent d'agence : enregistrer une location, enregistrer un retour, mettre à jour l'état d'un véhicule
- Responsable d'agence : suivre l'activité de l'agence, gérer les agents
- Administrateur : gérer les agences, les comptes et les droits d'accès

## Stack technique
Java 17, Spring Boot, Spring Data JPA, Maven, MySQL, Lombok, Postman, Git/GitHub, IntelliJ IDEA Ultimate

## Auteur
Ghofrane Jridi