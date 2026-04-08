package fr.univamu.iut.platsutilisateurs.platsutilisateurs;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class UtilisateursService {
    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les utilisateurs
     */
    protected Data data ;
    
    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param data objet implémentant l'interface d'accès aux données
     */
    public  UtilisateursService( Data data) {
        this.data = data;
    }
    
    /**
     * Méthode retournant les informations sur les utilisateurs au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllUtilisateursJSON(){
        
        ArrayList<Utilisateurs> allUtilisateurs = data.getAllUsers();
        
        // création du json et conversion de la liste des utilisateurs
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allUtilisateurs);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }
        
        return result;
    }
    
    /**
     * Méthode retournant au format JSON les informations sur un utilisateur recherché
     * @param id identifiant du plat recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getUtilisateurJSON( int id ){
        String result = null;
        Utilisateurs myUtilisateur = data.getUser(id);
        
        // si l'utilisateur a été trouvé
        if( myUtilisateur != null ) {
            
            // création du json et conversion de l'utilisateur
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myUtilisateur);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }
    
    /**
     * Méthode permettant de mettre à jours les informations d'un utilisateur
     * @param id identifiant de l'utilisateur à mettre à jour
     * @param Utilisateur les nouvelles informations à utiliser
     * @return true si l'utilisateur a pu être mis à jour
     */
    public boolean updateUtilisateur(int id, Utilisateurs Utilisateur) {
        return data.updateUser(id, Utilisateur.nom, Utilisateur.prenom, Utilisateur.email, Utilisateur.adresse);
    }
    
    /**
     * Méthode permettant de créer un utilisateur
     * @param Utilisateur les informations à utiliser
     * @return true si l'utilisateur a pu être créé
     */
    public boolean createUtilisateur(Utilisateurs Utilisateur) {
         return data.createUser(Utilisateur.nom, Utilisateur.prenom, Utilisateur.email, Utilisateur.adresse);
    }
    
    /**
     * Méthode permettant de supprimer un utilisateur
     * @param id identifiant de l'utilisateur à supprimer
     * @return true si l'utilisateur a pu être créé
     */
    public boolean deleteUtilisateur(int id) {
         return data.deleteUser(id);
    }
}
