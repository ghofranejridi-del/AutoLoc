# Atelier 3 — Couche Repository Spring Data JPA

## Choix des interfaces

| Interface | Étend | Justification |
|---|---|---|
| `IAgenceRepository` | `JpaRepository<Agence, Long>` | CRUD, listes, tri, pagination et opérations JPA. |
| `IEmployeRepository` | `JpaRepository<Employe, Long>` | CRUD, listes, tri, pagination et opérations JPA. |
| `IVehiculeRepository` | `JpaRepository<Vehicule, Long>` | CRUD, listes, tri, pagination et opérations JPA. |
| `IEquipementRepository` | `JpaRepository<Equipement, Long>` | CRUD, listes, tri, pagination et opérations JPA. |
| `IClientRepository` | `JpaRepository<Client, Long>` | CRUD, listes, tri, pagination et opérations JPA. |
| `IReservationRepository` | `JpaRepository<Reservation, Long>` | CRUD, listes, tri, pagination et opérations JPA. |
| `IContratRepository` | `JpaRepository<Contrat, Long>` | CRUD, listes, tri, pagination et `saveAndFlush`. |
| `IPaiementRepository` | `JpaRepository<Paiement, Long>` | Lecture des paiements ; leurs écritures passent par le contrat. |
| `IMaintenanceRepository` | `JpaRepository<Maintenance, Long>` | CRUD, listes, tri, pagination et opérations JPA. |

`JpaRepository` est retenue car elle réunit le CRUD avec des retours en `List`, le tri, la pagination et les opérations propres à JPA. Les suppressions en lot ne doivent pas être utilisées pour les contrats : elles ne déclenchent ni la cascade ni `orphanRemoval` des paiements.

## Anomalies SonarQube for IDE corrigées

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| Lombok `@Data` sur une entité liée | `@Data` génère `toString`, `equals` et `hashCode`, qui peuvent parcourir les associations bidirectionnelles. | Les entités utilisent des annotations Lombok ciblées (`@Getter`, `@Setter` et constructeurs). |
| Injection de dépendance par attribut | Règle S6813 : la dépendance est cachée et ne peut pas être `final`. | Les services utilisent l'injection par constructeur avec `@RequiredArgsConstructor`. |
| Exception générique lors d'une ressource absente | Règle S112 : une exception générique ne décrit pas la cause fonctionnelle. | Une `ResourceNotFoundException` dédiée est utilisée. |
