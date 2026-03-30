package fr.univamu.iut.apicommandes;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * MariaDB implementation of the Order Repository using JDBC.
 * This class manages SQL transactions to ensure data integrity between the
 * 'Commande' and 'LigneCommande' tables (Parent-Child relationship).
 */
public class CommandeRepositoryMariaDB implements CommandeRepositoryInterface, Closeable {

    protected Connection dbConnection;

    public CommandeRepositoryMariaDB(String infoConnection, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    /**
     * Closes the JDBC connection and releases database resources.
     */
    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Fetches an order from MariaDB and populates its associated line items.
     * @param id The primary key of the order.
     * @return The populated Commande object or null if not found.
     */
    @Override
    public Commande getCommande(int id) {
        Commande selectedCommande = null;

        String queryCommande = "SELECT * FROM Commande WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(queryCommande)) {
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                selectedCommande = new Commande();
                selectedCommande.setId(result.getInt("id"));
                selectedCommande.setAbonneId(result.getInt("abonneId"));
                selectedCommande.setDateCommande(result.getString("dateCommande"));
                selectedCommande.setAdresseLivraison(result.getString("adresseLivraison"));
                selectedCommande.setDateLivraison(result.getString("dateLivraison"));
                selectedCommande.setPrixTotal(result.getDouble("prixTotal"));
                String queryLignes = "SELECT * FROM LigneCommande WHERE commande_id=?";
                try (PreparedStatement psLignes = dbConnection.prepareStatement(queryLignes)) {
                    psLignes.setInt(1, id);
                    ResultSet resultLignes = psLignes.executeQuery();
                    while (resultLignes.next()) {
                        Commande.LigneCommande ligne = new Commande.LigneCommande();
                        ligne.setMenuId(resultLignes.getInt("menuId"));
                        ligne.setMenuNom(resultLignes.getString("menuNom"));
                        ligne.setQuantite(resultLignes.getInt("quantite"));
                        ligne.setPrixUnitaire(resultLignes.getDouble("prixUnitaire"));
                        ligne.setPrixLigne(resultLignes.getDouble("prixLigne"));
                        selectedCommande.getLignes().add(ligne);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectedCommande;
    }

    /**
     * Retrieves all orders stored in the MariaDB database.
     * <p>
     * This method performs a two-step retrieval process:
     * <ol>
     * <li>It executes a global SELECT to fetch all records from the 'Commande' table.</li>
     * <li>For each retrieved order, it executes a secondary SELECT to fetch all
     * associated line items from the 'LigneCommande' table using the order ID.</li>
     * </ol>
     * The method reconstructs the full object graph for each order, including delivery
     * information and the detailed list of ordered menus.
     *
     * @return An {@link ArrayList} containing all {@link Commande} objects found in the database.
     * Returns an empty list if no orders are present.
     * @throws RuntimeException if a database access error occurs during the execution of
     * either the parent or child SQL queries.
     */
    @Override
    public ArrayList<Commande> getAllCommandes() {
        ArrayList<Commande> listCommandes = new ArrayList<>();

        String queryCommandes = "SELECT * FROM Commande";
        String queryLignes = "SELECT * FROM LigneCommande WHERE commande_id=?";

        try (PreparedStatement psCommandes = dbConnection.prepareStatement(queryCommandes)) {
            ResultSet resultCommandes = psCommandes.executeQuery();

            while (resultCommandes.next()) {
                Commande commande = new Commande();
                int idCommande = resultCommandes.getInt("id");

                commande.setId(idCommande);
                commande.setAbonneId(resultCommandes.getInt("abonneId"));
                commande.setDateCommande(resultCommandes.getString("dateCommande"));
                commande.setAdresseLivraison(resultCommandes.getString("adresseLivraison"));
                commande.setDateLivraison(resultCommandes.getString("dateLivraison"));
                commande.setPrixTotal(resultCommandes.getDouble("prixTotal"));
                try (PreparedStatement psLignes = dbConnection.prepareStatement(queryLignes)) {
                    psLignes.setInt(1, idCommande);
                    ResultSet resultLignes = psLignes.executeQuery();
                    while (resultLignes.next()) {
                        Commande.LigneCommande ligne = new Commande.LigneCommande();
                        ligne.setMenuId(resultLignes.getInt("menuId"));
                        ligne.setMenuNom(resultLignes.getString("menuNom"));
                        ligne.setQuantite(resultLignes.getInt("quantite"));
                        ligne.setPrixUnitaire(resultLignes.getDouble("prixUnitaire"));
                        ligne.setPrixLigne(resultLignes.getDouble("prixLigne"));

                        commande.getLignes().add(ligne);
                    }
                }
                listCommandes.add(commande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listCommandes;
    }

    /**
     * Performs a SQL UPDATE on the delivery fields of an order.
     * @return true if at least one row was modified.
     */
    @Override
    public boolean updateCommande(int id, String adresseLivraison, String dateLivraison) {
        String query = "UPDATE Commande SET adresseLivraison=?, dateLivraison=? WHERE id=?";
        int nbRowModified = 0;
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, adresseLivraison);
            ps.setString(2, dateLivraison);
            ps.setInt(3, id);
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (nbRowModified != 0);
    }

    /**
     * Saves a new order using a SQL Transaction.
     * It performs two steps:
     * 1. Inserts the parent order and retrieves the auto-generated primary key.
     * 2. Inserts all child line items using Batch processing for performance.
     * * @param commande The Order object to be persisted.
     * @return The updated Order object with its generated ID.
     * @throws RuntimeException if a database error occurs, triggering a transaction rollback.
     */
    @Override
    public Commande createCommande(Commande commande) {
        String queryCommande = "INSERT INTO Commande (abonneId, dateCommande, adresseLivraison, dateLivraison, prixTotal) VALUES (?, ?, ?, ?, ?)";
        String queryLigne = "INSERT INTO LigneCommande (commande_id, menuId, menuNom, quantite, prixUnitaire, prixLigne) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            dbConnection.setAutoCommit(false);
            try (PreparedStatement psCommande = dbConnection.prepareStatement(queryCommande, Statement.RETURN_GENERATED_KEYS)) {
                psCommande.setInt(1, commande.getAbonneId());
                psCommande.setString(2, commande.getDateCommande());
                psCommande.setString(3, commande.getAdresseLivraison());
                psCommande.setString(4, commande.getDateLivraison());
                psCommande.setDouble(5, commande.getPrixTotal());

                psCommande.executeUpdate();

                ResultSet rsKeys = psCommande.getGeneratedKeys();
                if (rsKeys.next()) {
                    int generatedId = rsKeys.getInt(1);
                    commande.setId(generatedId); // On met à jour l'objet avec son nouvel ID

                    try (PreparedStatement psLigne = dbConnection.prepareStatement(queryLigne)) {
                        for (Commande.LigneCommande ligne : commande.getLignes()) {
                            psLigne.setInt(1, generatedId); // La fameuse clé étrangère !
                            psLigne.setInt(2, ligne.getMenuId());
                            psLigne.setString(3, ligne.getMenuNom());
                            psLigne.setInt(4, ligne.getQuantite());
                            psLigne.setDouble(5, ligne.getPrixUnitaire());
                            psLigne.setDouble(6, ligne.getPrixLigne());

                            psLigne.addBatch();
                        }
                        psLigne.executeBatch();
                    }
                }
            }

            dbConnection.commit();
            dbConnection.setAutoCommit(true);

        } catch (SQLException e) {
            try {
                dbConnection.rollback();
                dbConnection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Erreur critique lors du rollback : " + ex.getMessage());
            }
            throw new RuntimeException("Erreur lors de la création de la commande", e);
        }

        return commande;
    }

    /**
     * Removes an order and its dependencies.
     * @param id The ID of the order to delete.
     * @return true if rows were affected in the database.
     */
    @Override
    public boolean deleteCommande(int id) {
        String deleteLignesQuery = "DELETE FROM LigneCommande WHERE commande_id=?";
        String deleteCommandeQuery = "DELETE FROM Commande WHERE id=?";

        try {
            dbConnection.setAutoCommit(false);

            try (PreparedStatement psLignes = dbConnection.prepareStatement(deleteLignesQuery)) {
                psLignes.setInt(1, id);
                psLignes.executeUpdate();
            }

            int rowsAffected = 0;
            try (PreparedStatement psCommande = dbConnection.prepareStatement(deleteCommandeQuery)) {
                psCommande.setInt(1, id);
                rowsAffected = psCommande.executeUpdate();
            }

            dbConnection.commit();
            dbConnection.setAutoCommit(true);

            return rowsAffected > 0;

        } catch (SQLException e) {
            try {
                dbConnection.rollback();
                dbConnection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Erreur critique lors du rollback : " + ex.getMessage());
            }
            throw new RuntimeException("Erreur lors de la suppression de la commande", e);
        }
    }
}