package fr.univamu.iut.platsutilisateurs.platsutilisateurs;

public class Utilisateurs {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String email;
    protected String adresse;
    
    public Utilisateurs(int id, String nom, String prenom, String email, String adresse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
    }
    
    public int getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getAdresse() {
        return adresse;
    }
}
