package fr.univamu.iut.platsutilisateurs.platsutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * Ressource associée aux plats
 * (point d'accès de l'API REST)
 */
@Path("/plats")
public class PlatsResource {
    /**
     * Service utilisé pour accéder aux données des plats et récupérer/modifier leurs informations
     */
    private PlatsService service;

    /**
     * Constructeur par défaut
     */
    public PlatsResource(){}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param data objet implémentant l'interface d'accès aux données
     */
    public PlatsResource( Data data ){
        this.service = new PlatsService(data) ;
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux plats
     */
    public PlatsResource( PlatsService service ){
        this.service = service;
    }


    /**
     * Endpoint permettant de publier de tous les plats enregistrés
     * @return la liste des plats (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllPlats() {
        return service.getAllPlatsJSON();
    }

    /**
     * Endpoint permettant de publier les informations d'un plat dont la référence est passée paramètre dans le chemin
     * @param id identifiant du plat recherché
     * @return les informations du plat recherché au format JSON
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getPlats( @PathParam("id") int id){

        String result = service.getPlatsJSON(id);

        // si le plat n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint permettant de mettre à jours un plat
     * @param id identifiant du plat dont il faut changer le statut
     * @param Plats le plat transmis en HTTP au format JSON et convertit en objet plat
     * @return une réponse "updated" si la mise à jour a été effectuée, une erreur NotFound sinon
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updatePlats(@PathParam("id") int id, Plats Plats ){

        // si le plat n'a pas été trouvé
        if( ! service.updatePlats(id, Plats) )
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    /**
     * Endpoint permettant de créer un plat
     * @param Plats le plat transmis en HTTP au format JSON et convertit en objet plat
     * @return une réponse "created" si la création a été effectuée, une erreur NotFound sinon
     */
    @PUT
    @Consumes("application/json")
    public Response createPlats(Plats Plats ){

        // si le plat n'a pas été trouvé
        if( ! service.createPlats(Plats) )
            throw new NotFoundException();
        else
            return Response.ok("created").build();
    }

    /**
     * Endpoint permettant de supprimer un plat
     * @param id identifiant du plat à supprimer
     * @return une réponse "deleted" si la suppression a été effectuée, une erreur NotFound sinon
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response deletePlats(@PathParam("id") int id ){

        // si le plat n'a pas été trouvé
        if( ! service.deletePlats(id) )
            throw new NotFoundException();
        else
            return Response.ok("deleted").build();
    }
}