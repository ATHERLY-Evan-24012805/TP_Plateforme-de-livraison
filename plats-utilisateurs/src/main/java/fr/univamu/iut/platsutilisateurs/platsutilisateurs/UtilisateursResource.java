package fr.univamu.iut.platsutilisateurs.platsutilisateurs;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * Ressource associée aux utilisateurs
 * (point d'accès de l'API REST)
 */
@Path("/utilisateurs")
public class UtilisateursResource {
    /**
     * Service utilisé pour accéder aux données des utilisateurs et récupérer/modifier leurs informations
     */
    private UtilisateursService service;
    
    /**
     * Constructeur par défaut
     */
    public UtilisateursResource(){}
    
    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param data objet implémentant l'interface d'accès aux données
     */
    public @Inject UtilisateursResource( Data data ){
        this.service = new UtilisateursService(data) ;
    }
    
    /**
     * Constructeur permettant d'initialiser le service d'accès aux utilisateurs
     */
    public UtilisateursResource( UtilisateursService service ){
        this.service = service;
    }
    
    
    /**
     * Endpoint permettant de publier de tous les utilisateurs enregistrés
     * @return la liste des utilisateurs (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllUtilisateurs() {
        return service.getAllUtilisateursJSON();
    }
    
    /**
     * Endpoint permettant de publier les informations d'un utilisateur dont la référence est passée paramètre dans le chemin
     * @param id identifiant de l'utilisateur recherché
     * @return les informations de l'utilisateur recherché au format JSON
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getUtilisateur( @PathParam("id") int id){
        
        String result = service.getUtilisateurJSON(id);
        
        // si l'utilisateur n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();
        
        return result;
    }
    
    /**
     * Endpoint permettant de mettre à jours un utilisateur
     * @param id identifiant de l'utilisateur dont il faut changer le statut
     * @param Utilisateur l'utilisateur transmis en HTTP au format JSON et convertit en objet utilisateur
     * @return une réponse "updated" si la mise à jour a été effectuée, une erreur NotFound sinon
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updateUser(@PathParam("id") int id, Utilisateurs Utilisateur ){
        
        // si l'utilisateur n'a pas été trouvé
        if( ! service.updateUtilisateur(id, Utilisateur) )
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }
    
    /**
     * Endpoint permettant de créer un utilisateur
     * @param Utilisateur l'utilisateur transmis en HTTP au format JSON et convertit en objet utilisateur
     * @return une réponse "created" si la création a été effectuée, une erreur NotFound sinon
     */
    @POST
    @Consumes("application/json")
    public Response createUser(Utilisateurs Utilisateur ){
        
        // si l'utilisateur n'a pas été trouvé
        if( ! service.createUtilisateur(Utilisateur) )
            throw new NotFoundException();
        else
            return Response.ok("created").build();
    }
    
    /**
     * Endpoint permettant de supprimer un utilisateur
     * @param id identifiant de l'utilisateur à supprimer
     * @return une réponse "deleted" si la suppression a été effectuée, une erreur NotFound sinon
     */
    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    public Response deleteUser(@PathParam("id") int id ){
        
        // si l'utilisateur n'a pas été trouvé
        if( ! service.deleteUtilisateur(id) )
            throw new NotFoundException();
        else
            return Response.ok("deleted").build();
    }
}