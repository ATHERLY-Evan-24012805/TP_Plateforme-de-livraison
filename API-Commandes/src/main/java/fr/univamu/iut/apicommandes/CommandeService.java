package fr.univamu.iut.apicommandes;

import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Service layer handling the business logic for Order management.
 * It acts as an orchestrator between the REST Resource and the Repository,
 * including external API calls and price calculations.
 */
public class CommandeService {

    protected CommandeRepositoryInterface commandeRepo;

    public CommandeService(CommandeRepositoryInterface commandeRepo) {
        this.commandeRepo = commandeRepo;
    }

    /**
     * Retrieves all orders and converts them to a JSON array string.
     * @return A JSON string representing the list of all orders, or null if serialization fails.
     */
    public String getAllCommandesJSON() {
        ArrayList<Commande> allCommandes = commandeRepo.getAllCommandes();
        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allCommandes);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
    /**
     * Retrieves a single order by ID and converts it to a JSON object string.
     * @param id The unique identifier of the order.
     * @return A JSON string of the order, or null if not found or serialization fails.
     */
    public String getCommandeJSON(int id) {
        String result = null;
        Commande myCommande = commandeRepo.getCommande(id);
        if (myCommande != null) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myCommande);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Updates an order's delivery information in the database.
     * @param id The ID of the order to update.
     * @param commandeInfo Object containing the new address and delivery date.
     * @return true if the update was successful.
     */
    public boolean updateCommande(int id, Commande commandeInfo) {
        return commandeRepo.updateCommande(id, commandeInfo.adresseLivraison, commandeInfo.dateLivraison);
    }

    /**
     * Orchestrates the order creation process.
     * - Generates the server-side timestamp.
     * - Calls the external "Menus" API to fetch real-time names and prices.
     * - Calculates line totals and the final order price.
     * - Persists the result via the repository.
     * * @param commandeInput The raw order data received from the client.
     * @return A JSON string representing the final order, or null if the Menus API is unreachable.
     */
    public String createCommandeJSON(Commande commandeInput) {
        LocalDateTime now = LocalDateTime.now();
        commandeInput.setDateCommande(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        double prixTotalCommande = 0.0;

        try (Client client = ClientBuilder.newClient()) {
            for (Commande.LigneCommande ligne : commandeInput.getLignes()) {

                String url = "http://localhost:3004/menus/" + ligne.getMenuId();
                JsonObject menuJson = client.target(url)
                        .request(MediaType.APPLICATION_JSON)
                        .get(JsonObject.class);

                String nomMenu = menuJson.getString("nom");
                double prixUnitaire = menuJson.getJsonNumber("prixTotal").doubleValue();

                ligne.setMenuNom(nomMenu);
                ligne.setPrixUnitaire(prixUnitaire);

                double prixLigne = prixUnitaire * ligne.getQuantite();
                ligne.setPrixLigne(prixLigne);

                prixTotalCommande += prixLigne;
            }
        } catch (Exception e) {
            System.err.println("Erreur : Menu introuvable ou API Menus injoignable - " + e.getMessage());
            return null;
        }

        commandeInput.setPrixTotal(prixTotalCommande);

        Commande commandeCree = commandeRepo.createCommande(commandeInput);

        String result = null;
        if (commandeCree != null) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(commandeCree);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Retrieves all orders for a specific subscriber and converts them to JSON.
     */
    public String getCommandesByAbonneJSON(int abonneId) {
        ArrayList<Commande> commandesAbonne = commandeRepo.getCommandesByAbonne(abonneId);
        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(commandesAbonne);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result != null ? result : "[]";
    }

    /**
     * Deletes an order from the database.
     * @param id The ID of the order to remove.
     * @return true if the deletion was successful.
     */
    public boolean deleteCommande(int id) {
        return commandeRepo.deleteCommande(id);
    }
}