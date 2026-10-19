# Atelier 3 — Couche Repository Spring Data JPA

## 1. Interfaces de repository

Les neuf repositories du package `tn.esprit.autoloc.repository` étendent `JpaRepository<Entite, Long>` et ne portent pas l’annotation `@Repository` : Spring Data détecte ces interfaces et crée automatiquement leurs implémentations proxy.

| Interface | Entité | Justification |
| --- | --- | --- |
| `IAgenceRepository` | `Agence` | CRUD, consultation de la liste des agences, pagination et tri. |
| `IEmployeRepository` | `Employe` | CRUD et gestion des employés. |
| `IVehiculeRepository` | `Vehicule` | CRUD et gestion du parc de véhicules. |
| `IEquipementRepository` | `Equipement` | CRUD des équipements. |
| `IClientRepository` | `Client` | CRUD, pagination et tri des clients. |
| `IReservationRepository` | `Reservation` | CRUD des réservations. |
| `IContratRepository` | `Contrat` | CRUD et opérations de synchronisation comme `flush` et `saveAndFlush`. |
| `IPaiementRepository` | `Paiement` | Accès et CRUD des paiements ; leur création métier peut être pilotée par le contrat. |
| `IMaintenanceRepository` | `Maintenance` | CRUD et suivi des opérations de maintenance. |

Toutes les clés primaires sont typées `Long` dans la déclaration demandée : `JpaRepository<Entite, Long>`.

## 2. Pourquoi `JpaRepository` plutôt que `CrudRepository` ?

`CrudRepository` fournit les opérations CRUD de base. `JpaRepository` reprend ces opérations et ajoute des fonctionnalités utiles à une application JPA, notamment la pagination et le tri via ses interfaces parentes, ainsi que les opérations de flush et les suppressions en lot. Il permet donc de couvrir les besoins de cette couche avec une interface Spring Data standard, sans implémentation manuelle.

## 3. Attention aux suppressions en lot

`deleteAllInBatch()` exécute une suppression directe en base, sans synchroniser les entités concernées avec le contexte de persistance de l’`EntityManager`. Les cascades JPA `CascadeType.REMOVE` et `orphanRemoval = true` ne sont donc pas appliquées comme lors de la suppression d’entités chargées. Pour préserver les règles métier et les relations entre `Contrat` et `Paiement`, privilégier `delete()` ou `deleteById()` lorsque ces cascades doivent intervenir.

## 4. Lombok et entités JPA

Les entités utilisent `@Getter`, `@Setter`, `@NoArgsConstructor` et `@AllArgsConstructor` au lieu de `@Data`. `@Data` générerait notamment `equals()` et `hashCode()` à partir de tous les champs, ce qui peut être problématique avec les relations JPA bidirectionnelles et le chargement différé. Les accesseurs et constructeurs sont donc déclarés séparément, sans générer automatiquement ces méthodes à partir de toutes les propriétés.
