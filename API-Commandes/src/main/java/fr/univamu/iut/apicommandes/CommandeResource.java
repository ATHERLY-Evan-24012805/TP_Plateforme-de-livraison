package fr.univamu.iut.apicommandes;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * JAX-RS Resource defining the RESTful endpoints for the Orders API.
 * Handles HTTP requests, manages response status codes, and delegates logic to the Service layer.
 */
@Path("/commandes")
@RequestScoped
public class CommandeResource {

    private CommandeService service;

    public CommandeResource() {}

    public @Inject CommandeResource(CommandeRepositoryInterface commandeRepo) {
        this.service = new CommandeService(commandeRepo);
    }

    /**
     * REST endpoint to retrieve the list of all orders (GET).
     * @return A JSON string of all orders.
     */
    @GET
    @Produces("application/json; charset=utf-8")
    public String getAllCommandes() {
        return service.getAllCommandesJSON();
    }

    /**
     * REST endpoint to retrieve a specific order by ID (GET).
     * @param id The path parameter representing the order ID.
     * @return The JSON representation of the order.
     * @throws NotFoundException if the order does not exist.
     */
    @GET
    @Path("{id}")
    @Produces("application/json; charset=utf-8")
    public String getCommande(@PathParam("id") int id) {
        String result = service.getCommandeJSON(id);
        if (result == null)
            throw new NotFoundException();
        return result;
    }

    /**
     * REST endpoint to update an order's delivery details (PUT).
     * @param id The path parameter representing the order ID.
     * @param commandeInfo The JSON body containing new delivery data.
     * @return A 200 OK response on success.
     * @throws NotFoundException if the order ID is invalid.
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updateCommande(@PathParam("id") int id, Commande commandeInfo) {
        if (!service.updateCommande(id, commandeInfo))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    /**
     * REST endpoint to delete an order (DELETE).
     * @param id The path parameter representing the order ID.
     * @return A 204 No Content response on success.
     * @throws NotFoundException if the order ID is invalid.
     */
    @DELETE
    @Path("{id}")
    public Response deleteCommande(@PathParam("id") int id) {
        if (!service.deleteCommande(id)) {
            throw new NotFoundException();
        } else {
            return Response.noContent().build();
        }
    }

    /**
     * Endpoint for creating a new order (POST).
     * @param commandeInput The order data in JSON format.
     * @return A Response object with HTTP 201 (Created) on success,
     * or HTTP 400 (Bad Request) if business rules fail.
     * @throws BadRequestException if the order cannot be processed.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json; charset=utf-8")
    public Response createCommande(Commande commandeInput) {

        String result = service.createCommandeJSON(commandeInput);

        if (result == null) {

            throw new BadRequestException("Erreur lors de la création de la commande (données invalides ou menu introuvable).");
        } else {
            return Response.status(Response.Status.CREATED)
                    .entity(result)
                    .build();
        }
    }
}