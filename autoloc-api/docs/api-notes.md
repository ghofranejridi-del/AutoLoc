# Atelier 5 — API REST AutoLoc

## A. Endpoints

Toutes les routes sont préfixées par `/api` via `server.servlet.context-path`.
Les réponses d'erreur sont produites par `GlobalExceptionHandler`.

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| Agence | GET `/api/agences` | `IAgenceService.findAll` | 200 | — |
| Agence | GET `/api/agences/{id}` | `IAgenceService.findById` | 200 | 404 |
| Agence | POST `/api/agences` | `IAgenceService.create` | 201 | 400 |
| Agence | PUT `/api/agences/{id}` | `IAgenceService.update` | 200 | 400, 404 |
| Agence | DELETE `/api/agences/{id}` | `IAgenceService.deleteById` | 204 | 404 |
| Client | GET `/api/clients` | `IClientService.findAll` | 200 | — |
| Client | GET `/api/clients/{id}` | `IClientService.findById` | 200 | 404 |
| Client | POST `/api/clients` | `IClientService.create` | 201 | 400 |
| Client | PUT `/api/clients/{id}` | `IClientService.update` | 200 | 400, 404 |
| Client | DELETE `/api/clients/{id}` | `IClientService.deleteById` | 204 | 404 |
| Contrat | GET `/api/contrats` | `IContratService.findAll` | 200 | — |
| Contrat | GET `/api/contrats/{id}` | `IContratService.findById` | 200 | 404 |
| Contrat | POST `/api/contrats` | `IContratService.create` | 201 | 400 |
| Contrat | PUT `/api/contrats/{id}` | `IContratService.update` | 200 | 400, 404 |
| Contrat | DELETE `/api/contrats/{id}` | `IContratService.deleteById` | 204 | 404 |
| Employe | GET `/api/employes` | `IEmployeService.findAll` | 200 | — |
| Employe | GET `/api/employes/{id}` | `IEmployeService.findById` | 200 | 404 |
| Employe | POST `/api/employes` | `IEmployeService.create` | 201 | 400 |
| Employe | PUT `/api/employes/{id}` | `IEmployeService.update` | 200 | 400, 404 |
| Employe | DELETE `/api/employes/{id}` | `IEmployeService.deleteById` | 204 | 404 |
| Equipement | GET `/api/equipements` | `IEquipementService.findAll` | 200 | — |
| Equipement | GET `/api/equipements/{id}` | `IEquipementService.findById` | 200 | 404 |
| Equipement | POST `/api/equipements` | `IEquipementService.create` | 201 | 400 |
| Equipement | PUT `/api/equipements/{id}` | `IEquipementService.update` | 200 | 400, 404 |
| Equipement | DELETE `/api/equipements/{id}` | `IEquipementService.deleteById` | 204 | 404 |
| Maintenance | GET `/api/maintenances` | `IMaintenanceService.findAll` | 200 | — |
| Maintenance | GET `/api/maintenances/{id}` | `IMaintenanceService.findById` | 200 | 404 |
| Maintenance | POST `/api/maintenances` | `IMaintenanceService.create` | 201 | 400 |
| Maintenance | PUT `/api/maintenances/{id}` | `IMaintenanceService.update` | 200 | 400, 404 |
| Maintenance | DELETE `/api/maintenances/{id}` | `IMaintenanceService.deleteById` | 204 | 404 |
| Paiement | GET `/api/paiements` | `IPaiementService.findAll` | 200 | — |
| Paiement | GET `/api/paiements/{id}` | `IPaiementService.findById` | 200 | 404 |
| Reservation | GET `/api/reservations` | `IReservationService.findAll` | 200 | — |
| Reservation | GET `/api/reservations/{id}` | `IReservationService.findById` | 200 | 404 |
| Reservation | POST `/api/reservations` | `IReservationService.create` | 201 | 400 |
| Reservation | PUT `/api/reservations/{id}` | `IReservationService.update` | 200 | 400, 404 |
| Reservation | DELETE `/api/reservations/{id}` | `IReservationService.deleteById` | 204 | 404 |
| Vehicule | GET `/api/vehicules` | `IVehiculeService.findAll` | 200 | — |
| Vehicule | GET `/api/vehicules/{id}` | `IVehiculeService.findById` | 200 | 404 |
| Vehicule | POST `/api/vehicules` | `IVehiculeService.create` | 201 | 400 |
| Vehicule | PUT `/api/vehicules/{id}` | `IVehiculeService.update` | 200 | 400, 404 |
| Vehicule | DELETE `/api/vehicules/{id}` | `IVehiculeService.deleteById` | 204 | 404 |

## B. Annotations Jackson

| Association | Annotation | Justification |
|---|---|---|
| `Contrat.paiements` ↔ `Paiement.contrat` | `@JsonManagedReference("contrat-paiements")` / `@JsonBackReference("contrat-paiements")` | Le contrat sérialise ses paiements et le paiement ne réémet pas son contrat. À la lecture, Jackson rattache l'enfant à son parent. La cascade JPA reste pilotée par Contrat. |
| Collections inverses d'Agence, Client, Equipement, Reservation et Vehicule | `@JsonIgnore` | Ces liens ne sont pas nécessaires pour représenter l'entité renvoyée et pourraient déclencher une récursion. Les références propriétaires restent lisibles dans les corps JSON. |

`@JsonBackReference` est conçu pour un côté enfant d'une paire : il supprime le lien parent dans la réponse, tout en restaurant le lien lors de la désérialisation de la collection gérée. `@JsonIgnore` exclut complètement une propriété de la lecture et de l'écriture JSON. Il ne faut donc pas l'appliquer aux références `Reservation.client` ou `Reservation.vehicule`, utilisées lors de la création d'une réservation.

## C. Postman et Swagger

Postman envoie des requêtes rejouables et vérifie les réponses : par exemple le POST d'un contrat retourne 201 et son identifiant est réutilisé par la requête GET. Swagger UI présente le contrat OpenAPI généré et permet d'exécuter une requête depuis le navigateur. Les URL de documentation sont `/api/swagger-ui.html` et `/api/v3/api-docs`.

## D. Anomalies SonarQube for IDE

| Règle / problème | Explication | Correction |
|---|---|---|
| Injection de dépendance par champ | Elle masque les dépendances et rend les classes plus difficiles à tester. | Tous les contrôleurs utilisent un champ `final`, `@RequiredArgsConstructor` et l'interface du service. |
| Import inutilisé | Il ajoute du bruit et complique la lecture. | Supprimer les imports signalés par l'IDE; les contrôleurs ne gardent que leurs dépendances utilisées. |
| Contrôleur trop couplé à la persistance | Un contrôleur qui utilise un repository contourne la couche métier. | Les contrôleurs délèguent exclusivement aux interfaces `I...Service`. |

## E. Questions

1. `DispatcherServlet` reçoit les requêtes servlet. Il consulte les mappings enregistrés par Spring MVC, associe méthode HTTP et chemin à une méthode de contrôleur, puis délègue son invocation.
2. `IContratService` garde le contrôleur indépendant de JPA et de l'implémentation métier concrète. La logique reste dans le service, qui peut évoluer sans modifier la couche web.
3. 201 signale la création d'une ressource; 204 confirme la suppression sans corps de réponse. Une ressource absente est une situation métier connue (404), alors qu'un 500 signale une erreur serveur inattendue.
4. Paiement est géré par la composition et la cascade depuis Contrat; le service ne propose que la lecture. Un POST `/api/paiements` ne correspond à aucune route et renvoie 405 Method Not Allowed.
5. Exposer les entités JPA révèle le modèle interne, rend l'API dépendante du schéma et peut exposer des associations paresseuses. `Contrat.paiements` est une composition sérialisée avec sa racine et requiert une paire managed/back reference. `Agence.vehicules` est un lien inverse non nécessaire dans la réponse; `@JsonIgnore` évite une récursion et empêche cette collection de faire partie du contrat JSON.
