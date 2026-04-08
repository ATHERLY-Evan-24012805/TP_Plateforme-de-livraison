package fr.univamu.iut.platsutilisateurs.platsutilisateurs;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/plats")
public class PlatsResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Plats!";
    }
}
