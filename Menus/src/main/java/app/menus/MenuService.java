package app.menus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service gérant les opérations CRUD sur les menus.
 * Utilise JPA en mode RESOURCE_LOCAL pour la persistance
 * et PlatClient pour enrichir les menus via l'API Plats & Utilisateurs (port 3003).
 */
@ApplicationScoped
public class MenuService {

    /** Factory injectée par le conteneur Jakarta EE en mode RESOURCE_LOCAL. */
    @PersistenceUnit(unitName = "MenuPU")
    private EntityManagerFactory emf;

    /** Client HTTP vers l'API Plats & Utilisateurs. */
    @Inject
    private PlatClient platClient;

    /**
     * Crée un EntityManager pour chaque opération.
     * À fermer impérativement dans un bloc finally.
     */
    private EntityManager getEm() {
        return emf.createEntityManager();
    }

    /**
     * Retourne tous les menus, enrichis avec les détails des plats.
     *
     * @return liste de tous les menus
     */
    public List<Menu> findAll() {
        EntityManager em = getEm();
        try {
            List<Menu> menus = em.createQuery("SELECT m FROM Menu m", Menu.class).getResultList();
            for (Menu m : menus) {
                enrichirMenu(m);
            }
            return menus;
        } finally {
            em.close();
        }
    }

    /**
     * Retourne un menu par son identifiant, enrichi avec les détails des plats.
     *
     * @param id identifiant du menu
     * @return le menu correspondant, ou null s'il n'existe pas
     */
    public Menu findById(int id) {
        EntityManager em = getEm();
        try {
            Menu m = em.find(Menu.class, id);
            if (m != null) {
                enrichirMenu(m);
            }
            return m;
        } finally {
            em.close();
        }
    }

    /**
     * Crée un nouveau menu.
     * Le prix total est calculé à partir des prix des plats via l'API port 3003.
     * Les dates de création et de mise à jour sont définies automatiquement.
     *
     * @param menu le menu à créer (sans id, sans prix, sans dates)
     * @return le menu créé et enrichi
     */
    public Menu create(Menu menu) {
        EntityManager em = getEm();
        try {
            em.getTransaction().begin();

            menu.setDateCreation(LocalDate.now().toString());
            menu.setDateMiseAJour(LocalDate.now().toString());
            menu.setPrixTotal(calculerPrixTotal(menu.getPlatIds()));

            em.persist(menu);
            em.getTransaction().commit();

            return enrichirMenu(menu);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Met à jour un menu existant.
     * Recalcule le prix total et met à jour la date de mise à jour.
     *
     * @param id          identifiant du menu à mettre à jour
     * @param menuDetails les nouvelles données du menu
     * @return le menu mis à jour et enrichi, ou null s'il n'existe pas
     */
    public Menu update(int id, Menu menuDetails) {
        EntityManager em = getEm();
        try {
            em.getTransaction().begin();

            Menu existing = em.find(Menu.class, id);
            if (existing == null) {
                em.getTransaction().rollback();
                return null;
            }

            existing.setNom(menuDetails.getNom());
            existing.setCreateurId(menuDetails.getCreateurId());
            existing.setCreateurNom(menuDetails.getCreateurNom());
            existing.setPlatIds(menuDetails.getPlatIds());
            existing.setPrixTotal(calculerPrixTotal(menuDetails.getPlatIds()));
            existing.setDateMiseAJour(LocalDate.now().toString());

            Menu updated = em.merge(existing);
            em.getTransaction().commit();

            return enrichirMenu(updated);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Supprime un menu par son identifiant.
     *
     * @param id identifiant du menu à supprimer
     * @return true si supprimé, false s'il n'existe pas
     */
    public boolean delete(int id) {
        EntityManager em = getEm();
        try {
            em.getTransaction().begin();

            Menu menu = em.find(Menu.class, id);
            if (menu == null) {
                em.getTransaction().rollback();
                return false;
            }

            em.remove(menu);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Calcule le prix total d'un menu en interrogeant l'API Plats & Utilisateurs.
     *
     * @param platIds liste des identifiants de plats
     * @return somme des prix des plats
     */
    private double calculerPrixTotal(List<Integer> platIds) {
        if (platIds == null || platIds.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (int platId : platIds) {
            Plat plat = platClient.getPlatById(platId);
            if (plat != null) {
                total += plat.getPrix();
            }
        }
        return total;
    }

    /**
     * Enrichit un menu avec les détails complets des plats
     * en interrogeant l'API Plats & Utilisateurs (port 3003).
     *
     * @param menu le menu à enrichir
     * @return le menu enrichi (même référence)
     */
    private Menu enrichirMenu(Menu menu) {
        List<Integer> ids = menu.getPlatIds();
        if (ids != null && !ids.isEmpty()) {
            List<Plat> platsComplets = ids.stream()
                    .map(platClient::getPlatById)
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
            menu.setPlats(platsComplets);
        }
        return menu;
    }
}