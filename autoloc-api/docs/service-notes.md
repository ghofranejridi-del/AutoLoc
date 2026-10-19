# Note — Atelier 4 : IoC, injection et services

## A. Modes d'injection comparés

| Critère | Constructeur | Attribut (`@Autowired`) |
|---|---|---|
| Champ `final` | Oui, dépendance obligatoire | Non |
| Dépendance visible | Oui, dans le constructeur généré par `@RequiredArgsConstructor` | Non, elle est cachée dans la classe |
| Usage hors conteneur | Possible si le constructeur reçoit explicitement sa dépendance | Risque d'un champ `null` |
| SonarQube for IDE | Bonne pratique recommandée | Signalée notamment par S6813 |

## B. Services

| Service | Dépendance | Mode | Justification |
|---|---|---|---|
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur | CRUD, contrôle des champs obligatoires, dépend de l'interface repository. |
| `ClientServiceImpl` | `IClientRepository` | Constructeur | CRUD et contrôle des données d'identification. |
| `ContratServiceImpl` | `IContratRepository` | Constructeur | CRUD, montant non négatif, paiements gérés par la relation du contrat. |
| `EmployeServiceImpl` | `IEmployeRepository` | Constructeur | CRUD et contrôle du nom, prénom et rôle. |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur | CRUD et libellé obligatoire ; aucun champ quantité n'existe dans le modèle. |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` | Constructeur | CRUD et cohérence des dates ; aucun champ coût n'existe dans le modèle. |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur | Lecture uniquement : création, modification et suppression passent par le contrat. |
| `ReservationServiceImpl` | `IReservationRepository` | Constructeur | CRUD et validation de l'intervalle de dates. |
| `VehiculeServiceImpl` | `IVehiculeRepository` | Constructeur | CRUD et refus d'un tarif journalier négatif. |

Tous les services sont des beans `@Service` singleton sans état modifiable, avec dépendance `final` générée par `@RequiredArgsConstructor`. Aucun service ne crée son repository avec `new`.

## C. Messages d'erreur Spring

1. **Bean `IContratService` introuvable** : `ContratServiceImpl` peut ne pas porter `@Service`, ou être placé hors du package scanné. Ajouter le stéréotype et le placer sous `tn.esprit.autoloc`.
2. **Deux beans `emailNotificateur` et `smsNotificateur`** : ambiguïté de type. Mettre `@Primary` sur la classe candidate par défaut, ou `@Qualifier("emailNotificateur")` sur le paramètre du constructeur consommateur.
3. **Cycle `clientServiceImpl` ↔ `reservationServiceImpl`** : chacun exige l'autre à son constructeur, donc Spring ne peut en créer aucun en premier. Supprimer le cycle en déplaçant la responsabilité commune dans un troisième service ou en restructurant les appels pour qu'ils soient à sens unique.

## D. Anomalies de qualité étudiées et corrections

| Règle / anomalie | Problème | Correction |
|---|---|---|
| S6813 — injection par attribut | Dépendance cachée et champ non `final`, objet potentiellement incomplet hors Spring. | Injection par constructeur avec `@RequiredArgsConstructor`. |
| S112 — exception générique | `RuntimeException` ne décrit pas le cas métier d'une ressource absente. | `ResourceNotFoundException` avec le nom de la ressource et l'identifiant. |
| Lombok `@Data` sur entités liées | `toString`/`equals` peuvent parcourir les deux côtés et causer récursion ou chargement inattendu. | Garder les annotations ciblées `@Getter`, `@Setter`, constructeurs. |

Les deux premières règles correspondent aux expériences demandées dans l'atelier ; la troisième est évitée dans les entités du projet.

## E. Questions de compréhension

1. Spring crée `ContratServiceImpl` lors de l'initialisation du contexte, à partir de `@Service` et du scan de composants. L'instance est construite par le conteneur, qui injecte `IContratRepository`.
2. Le contrôleur dépend de `IContratService` pour rester couplé au contrat public du service et pouvoir remplacer son implémentation.
3. Le singleton est partagé entre requêtes et threads. Stocker un « contrat courant » dans un champ pourrait faire lire à une requête les données d'une autre.
4. Charger l'existant puis recopier les champs modifiables protège l'identifiant et les associations/collections non modifiées, notamment `paiements` avec `orphanRemoval`.
5. `@Component` marque une classe détectée par le scan ; `@Bean` déclare explicitement le résultat d'une méthode `@Configuration`. `@Primary` choisit le candidat par défaut ; `@Qualifier` nomme le candidat voulu à un point d'injection. Les paiements sont composés avec leur contrat : leurs opérations d'écriture passent par ce parent afin de respecter le cycle de vie et la cascade.
