package fr.univamu.iut.platsutilisateurs.platsutilisateurs;

public class Plats {
    private int id;
    private String nom;
    private String description;
    private int prix;
    
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
