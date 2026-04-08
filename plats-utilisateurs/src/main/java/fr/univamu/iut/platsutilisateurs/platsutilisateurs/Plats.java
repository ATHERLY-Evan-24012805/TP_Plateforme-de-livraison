package fr.univamu.iut.platsutilisateurs.platsutilisateurs;

public class Plats {
    protected int id;
    protected String nom;
    protected String description;
    protected int prix;
    
    public Plats(int id, String nom, String description, int prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }
    
    public int getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getPrix() {
        return prix;
    }
}
