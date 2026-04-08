package app.menus;

import app.menus.Menu;
import app.menus.MenuService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/menus")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuResource {

    @Inject
    private MenuService menuService;

    /**
     * GET /api/menus
     * Récupère tous les menus (enrichis via le port 3003)
     */
    @GET
    public List<Menu> getAllMenus() {
        return menuService.findAll();
    }

    /**
     * GET /api/menus/{id}
     */
    @GET
    @Path("/{id}")
    public Response getMenuById(@PathParam("id") int id) {
        Menu menu = menuService.findById(id);
        if (menu != null) {
            return Response.ok(menu).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Menu non trouvé")
                .build();
    }

    /**
     * POST /api/menus
     * Crée un menu, calcule le prix via 3003 et enregistre sur AlwaysData
     */
    @POST
    public Response createMenu(Menu menu) {
        try {
            Menu created = menuService.create(menu);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la création : " + e.getMessage())
                    .build();
        }
    }

    /**
     * PUT /api/menus/{id}
     * Mise à jour du menu
     */
    @PUT
    @Path("/{id}")
    public Response updateMenu(@PathParam("id") int id, Menu menu) {
        Menu updated = menuService.update(id, menu);
        if (updated != null) {
            return Response.ok(updated).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * DELETE /api/menus/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response deleteMenu(@PathParam("id") int id) {
        boolean deleted = menuService.delete(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}