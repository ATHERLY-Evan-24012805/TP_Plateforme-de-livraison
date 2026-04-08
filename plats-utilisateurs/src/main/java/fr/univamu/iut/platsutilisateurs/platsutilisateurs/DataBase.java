package fr.univamu.iut.platsutilisateurs.platsutilisateurs;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

public class DataBase implements Data, Closeable {
    protected Connection dbConnection;
    
    public DataBase(String infoConnection, String user, String pwd) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }
    
    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public Plats getPlat(int id) {
        
        Plats selectedPlat = null;
        
        String query = "SELECT * FROM Plats WHERE id=?";
        
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            
            ResultSet result = ps.executeQuery();
            
            if (result.next()) {
                String nom = result.getString("nom");
                String description = result.getString("description");
                int prix = result.getInt("prix");
                
                selectedPlat = new Plats(id, nom, description, prix);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedPlat;
    }
    
    @Override
    public ArrayList<Plats> getAllPlats() {
        ArrayList<Plats> listPlats;
        
        String query = "SELECT * FROM Plats";
        
        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            // exécution de la requête
            ResultSet result = ps.executeQuery();
            
            listPlats = new ArrayList<>();
            
            // récupération du premier (et seul) tuple résultat
            while (result.next()) {
                int id = result.getInt("int");
                String nom = result.getString("nom");
                String description = result.getString("authors");
                int prix = result.getInt("prix");
                
                Plats currentPlat = new Plats(id, nom, description, prix);
                
                listPlats.add(currentPlat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPlats;
    }
    
    @Override
    public boolean updatePlat(int id, String nom, String description, int prix) {
        String query = "UPDATE Plats SET nom=?, description=?, prix=?  where id=?";
        int nbRowModified = 0;
        
        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, description);
            ps.setInt(3, prix);
            ps.setInt(4, id);
            
            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return (nbRowModified != 0);
    }
    
    @Override
    public void createPlat(String nom, String description, int prix) {
        String query = "INSERT INTO Plats VALUES nom=?, description=?, prix=?";
        
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, description);
            ps.setInt(3, prix);
            
            // exécution de la requête
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void deletePlat(int id) {
        String query = "DELETE FROM Plats WHERE id=?";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
         //   ps.setString(1, id);
            
            // exécution de la requête
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Utilisateurs getUser(int id) {
        
        Utilisateurs selectedUser = null;
        
        String query = "SELECT * FROM Utilisateurs WHERE id=?";
        
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            
            
            ResultSet result = ps.executeQuery();
            
            if (result.next()) {
                String nom = result.getString("nom");
                String prenom = result.getString("prenom");
                String email = result.getString("email");
                String adresse = result.getString("adresse");
                
                selectedUser = new Utilisateurs(id, nom, prenom, email, adresse);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedUser;
    }
    
    @Override
    public ArrayList<Utilisateurs> getAllUsers() {
        ArrayList<Utilisateurs> listUsers;
        
        String query = "SELECT * FROM Utilisateurs";
        
        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            // exécution de la requête
            ResultSet result = ps.executeQuery();
            
            listUsers = new ArrayList<>();
            
            // récupération du premier (et seul) tuple résultat
            while (result.next()) {
                int id = result.getInt("int");
                String nom = result.getString("nom");
                String prenom = result.getString("prenom");
                String email = result.getString("email");
                String adresse = result.getString("adresse");
                
                Utilisateurs currentUser = new Utilisateurs(id, nom, prenom, email, adresse);
                
                listUsers.add(currentUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUsers;
    }
    
    @Override
    public boolean updateUser(int id, String nom, String prenom, String email, String adresse) {
        String query = "UPDATE Utilisateurs SET nom=?, prenom=?, email=?, adresse=?  where id=?";
        int nbRowModified = 0;
        
        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, email);
            ps.setString(4, adresse);
            ps.setInt(5, id);
            
            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return (nbRowModified != 0);
    }
    
    @Override
    public void createUser(String nom, String prenom, String email, String adresse) {
        String query = "INSERT INTO Utilisateurs VALUES nom=?, prenom=?, email=?, adresse=?";
        
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, email);
            ps.setString(4, adresse);
            
            // exécution de la requête
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void deleteUser(int id) {
        String query = "DELETE FROM Utilisateurs WHERE id=?";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            //ps.setString(1, id);
            
            // exécution de la requête
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
