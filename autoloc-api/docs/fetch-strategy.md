# Associations, cascade et chargement

| Association | Fetch | Cascade / orphan removal | Justification |
|---|---|---|---|
| Contrat → Paiement | `LAZY` | `ALL`, `orphanRemoval=true` | Le paiement dépend du contrat ; sa suppression suit celle du parent et le retrait de la collection supprime l'enfant. La collection n'est chargée que si elle est consultée. |
| Agence → Véhicule | `LAZY` | Aucune | Un véhicule reste un objet métier autonome lorsque son agence est supprimée ; éviter une suppression en chaîne. |
| Agence → Employé | `LAZY` | Aucune | Aucun cycle de vie partagé n'est défini ; les employés sont chargés à la demande. |
| Véhicule ↔ Équipement | `LAZY` | Aucune | Les équipements sont partagés entre véhicules ; une suppression en cascade supprimerait un équipement encore utilisé. `Set` modélise les associations sans doublons. |
| Client → Réservation | `LAZY` | `PERSIST` | L'enregistrement initial d'un client peut enregistrer ses réservations nouvelles. La suppression et les mises à jour ne sont pas propagées. |
| Réservation → Véhicule | `LAZY` | Aucune | Le véhicule a son propre cycle de vie et ne doit pas être supprimé avec une réservation. |
| Réservation ↔ Contrat | `LAZY` | `ALL` côté Réservation | Le contrat suit le cycle de vie de la réservation selon le sujet. Contrat est propriétaire de la FK `reservation`, Réservation est le côté inverse (`mappedBy`). |
| Véhicule → Maintenance | `LAZY` | `PERSIST` | La création d'un véhicule peut persister ses maintenances nouvelles ; les opérations destructrices ne sont pas propagées. |

`@ManyToOne` est configuré explicitement en `LAZY` (son défaut JPA est `EAGER`). Les collections sont aussi `LAZY`. Les relations bidirectionnelles utilisent `mappedBy` sur le côté inverse pour éviter des associations ou tables de jointure redondantes. Aucune méthode `@Data`/`toString` générée ne parcourt les deux côtés des relations.
