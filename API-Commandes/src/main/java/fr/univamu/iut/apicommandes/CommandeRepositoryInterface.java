package fr.univamu.iut.apicommandes;

import java.util.ArrayList;

/**
 * Interface defining the persistence operations for Orders.
 * This abstraction follows the Repository pattern to decouple the business logic
 * from the specific storage implementation (e.g., MariaDB, Mock, etc.).
 */
public interface CommandeRepositoryInterface {
    /**
     * Closes the connection to the data source.
     */
    public void close();

    /**
     * Retrieves a specific order by its unique identifier.
     * @param id The unique ID of the order.
     * @return The Commande object if found, null otherwise.
     */
    public Commande getCommande(int id);

    /**
     * Fetches all orders currently stored in the system.
     * @return An ArrayList containing all orders.
     */
    public ArrayList<Commande> getAllCommandes();

    /**
     * Updates the delivery details of an existing order.
     * @param id The ID of the order to update.
     * @param adresseLivraison The new delivery address.
     * @param dateLivraison The new delivery date.
     * @return true if the update was successful, false if the ID was not found.
     */
    public boolean updateCommande(int id, String adresseLivraison, String dateLivraison);

    /**
     * Persists a new order and its associated line items atomically.
     * @param commande The order object containing line items to be saved.
     * @return The persisted order including its database-generated ID.
     */
    public Commande createCommande(Commande commande);

    /**
     * Deletes an order and all its child line items from the database.
     * @param id The ID of the order to remove.
     * @return true if the deletion was successful.
     */
    public boolean deleteCommande(int id);
}