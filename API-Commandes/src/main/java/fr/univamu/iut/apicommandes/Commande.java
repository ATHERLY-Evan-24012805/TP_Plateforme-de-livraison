package fr.univamu.iut.apicommandes;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain model representing a Food Order in the system.
 * This class holds delivery information, total price, and the list of ordered menus.
 * It follows the structure defined in the openapi-commandes.yaml specification.
 */
public class Commande {
    protected int id;
    protected int abonneId;
    protected String dateCommande;
    protected String adresseLivraison;
    protected String dateLivraison;
    protected double prixTotal;
    protected List<LigneCommande> lignes;

    public Commande() {
        this.lignes = new ArrayList<>();
    }

    /**
     * Inner class representing a specific line item within an order.
     * It stores a snapshot of the menu details (name and price) at the time of purchase.
     */
    public static class LigneCommande {
        /**
         * Calculate the total price of the line based on the unit price and quantity.
         */
        protected int menuId;
        protected String menuNom;
        protected int quantite;
        protected double prixUnitaire;
        protected double prixLigne;

        public LigneCommande() {}
        public int getMenuId() { return menuId; }
        public void setMenuId(int menuId) { this.menuId = menuId; }

        public String getMenuNom() { return menuNom; }
        public void setMenuNom(String menuNom) { this.menuNom = menuNom; }

        public int getQuantite() { return quantite; }
        public void setQuantite(int quantite) { this.quantite = quantite; }

        public double getPrixUnitaire() { return prixUnitaire; }
        public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

        public double getPrixLigne() { return prixLigne; }
        public void setPrixLigne(double prixLigne) { this.prixLigne = prixLigne; }
    }

    // --- Getters ---

    public int getId() {
        return id;
    }

    public int getAbonneId() {
        return abonneId;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    // --- Setters ---


    public void setId(int id) {
        this.id = id;
    }

    public void setAbonneId(int abonneId) {
        this.abonneId = abonneId;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }
}