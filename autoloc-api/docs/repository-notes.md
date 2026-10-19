# Note — Atelier 3 : Spring Data JPA et repositories

## Interfaces retenues

Les neuf repositories du package `tn.esprit.autoloc.repository` étendent
`JpaRepository<Entité, Long>`. Cette interface convient au projet car elle réunit le
CRUD, les retours en `List`, le tri et la pagination, ainsi que les opérations JPA
telles que `flush()` et `saveAndFlush()`. La clé primaire des neuf entités est de
type `Long`.

| Interface | Entité | Justification |
|---|---|---|
| `IAgenceRepository` | `Agence` | CRUD et opérations de lecture JPA complètes. |
| `IEmployeRepository` | `Employe` | CRUD et opérations de lecture JPA complètes. |
| `IVehiculeRepository` | `Vehicule` | CRUD et opérations de lecture JPA complètes. |
| `IEquipementRepository` | `Equipement` | CRUD et opérations de lecture JPA complètes. |
| `IClientRepository` | `Client` | CRUD et opérations de lecture JPA complètes. |
| `IReservationRepository` | `Reservation` | CRUD et opérations de lecture JPA complètes. |
| `IContratRepository` | `Contrat` | CRUD complet ; les écritures de paiements passent par le contrat parent. |
| `IPaiementRepository` | `Paiement` | Disponible pour les lectures ; la composition est administrée par `Contrat`. |
| `IMaintenanceRepository` | `Maintenance` | CRUD et opérations de lecture JPA complètes. |

Spring Data JPA fournit les implémentations sous forme de proxies ; aucune classe
implémentant ces interfaces et aucune annotation `@Repository` ne sont nécessaires.

## Repères de comportement CRUD

- `save(entité)` persiste une entité nouvelle lorsque son identifiant est `null`.
  Pour une entité portant déjà un identifiant, JPA utilise le mécanisme de fusion
  (`merge`) ; il faut conserver et utiliser l'objet retourné par `save`, qui peut
  être différent de l'instance transmise.
- `findById(id)` retourne un `Optional<Entité>`. Le code appelant doit traiter
  explicitement l'absence de résultat plutôt que supposer que l'entité existe.
- `deleteById(id)` ne signale pas une erreur si l'identifiant n'existe pas dans
  Spring Data 3.
- Les suppressions ordinaires du repository passent par le contexte de persistance.
  Pour `Contrat`, supprimer le parent via `IContratRepository` applique la cascade
  `REMOVE` et `orphanRemoval` configurés sur `paiements`.
- Les suppressions en lot de `JpaRepository` contournent le contexte de persistance.
  Elles ne déclenchent pas ces cascades et ne doivent donc pas servir à supprimer
  des contrats avec leurs paiements.

La démonstration `ContratCrudDemo` exerce ces opérations sans test automatisé.
Pour l'exécuter avec une base de démonstration, démarrer l'application avec
`--autoloc.demo.contrat-crud=true`. Elle crée puis supprime son propre contrat et
son paiement par cascade ; ne l'activer que sur une base où cette écriture est
souhaitée.

Dans la hiérarchie Spring Data 3, `PagingAndSortingRepository` n'hérite plus de
`CrudRepository`. Il faut combiner les interfaces pour obtenir CRUD et pagination.
`JpaRepository` les regroupe déjà avec les variantes qui retournent des `List`.

## Qualité du code et SonarQube for IDE

Cette note consigne des points de revue statique du code source ; une exécution de
l'analyse SonarQube for IDE dans IntelliJ doit être faite et confirmée par
l'étudiant pour relever les diagnostics réellement affichés par son IDE.

| Anomalie ou règle étudiée | Explication | Correction / constat |
|---|---|---|
| `@Data` sur une entité JPA liée | Génère notamment `toString`, `equals` et `hashCode` sur les associations ; cela peut provoquer des parcours récursifs ou charger des relations paresseuses. | Les entités utilisent des annotations Lombok ciblées (`@Getter`, `@Setter` et constructeurs), sans `@Data`. |
| Imports inutilisés | Augmentent le bruit et peuvent masquer les dépendances réellement employées. | Aucun import inutilisé identifié dans les neuf interfaces repository lors de la revue. |
| Nom des repositories | Un nom qui ne suit pas la convention du projet réduit la cohérence du package. | Les neuf interfaces suivent `I` + nom de l'entité + `Repository`. |

## Vérification du livrable

Le package contient les neuf interfaces attendues et chacune étend
`JpaRepository` avec l'entité correspondante et une clé `Long`. Les propriétés de
l'atelier activent `spring.jpa.show-sql` et le niveau `DEBUG` pour les requêtes
Hibernate. L'observation du démarrage et des cascades CRUD nécessite une base
MySQL disponible ; elle n'est pas consignée ici comme ayant été exécutée.
