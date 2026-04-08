package fr.univamu.iut.platsutilisateurs.platsutilisateurs;

import java.util.ArrayList;

public interface Data {
    
    public void close();
    
    public Plats getPlat(int id);
    
    public ArrayList<Plats> getAllPlats();
    
    public boolean updatePlat(int id, String nom, String description, int prix);
    
    public void createPlat(String nom, String description, int prix);
    
    public void deletePlat(int id);
    
    public Utilisateurs getUser(int id);
    
    public ArrayList<Utilisateurs> getAllUsers();
    
    public boolean updateUser(int id, String nom, String prenom, String email, String adresse);
    
    public void createUser(String nom, String prenom, String email, String adresse);
    
    public void deleteUser(int id);
    
}
