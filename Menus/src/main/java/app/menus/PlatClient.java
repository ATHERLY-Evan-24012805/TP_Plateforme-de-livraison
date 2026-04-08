package app.menus;

import app.menus.Plat;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class PlatClient {

    private static final String BASE_URL = "http://localhost:3003/plats";

    /**
     * Récupère un plat spécifique par son ID sur le service 3003.
     */
    public Plat getPlatById(int id) {
        Client client = ClientBuilder.newClient();
        try {
            return client.target(BASE_URL)
                    .path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON)
                    .get(Plat.class);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération du plat " + id + " : " + e.getMessage());
            return null;
        } finally {
            client.close();
        }
    }

    /**
     * Récupère la liste de tous les plats disponibles.
     */
    public List<Plat> getAllPlats() {
        Client client = ClientBuilder.newClient();
        try {
            Plat[] plats = client.target(BASE_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Plat[].class);
            return Arrays.asList(plats);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des plats : " + e.getMessage());
            return null;
        } finally {
            client.close();
        }
    }
}