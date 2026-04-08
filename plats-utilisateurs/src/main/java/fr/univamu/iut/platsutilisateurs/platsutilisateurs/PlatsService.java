package fr.univamu.iut.platsutilisateurs.platsutilisateurs;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class PlatsService {
    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les plats
     */
    protected Data data ;
    
    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param data objet implémentant l'interface d'accès aux données
     */
    public  PlatsService( Data data) {
        this.data = data;
    }
    
    /**
     * Méthode retournant les informations sur les plats au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllPlatsJSON(){
        
        ArrayList<Plats> allPlats = data.getAllPlats();
        
        // création du json et conversion de la liste de plats
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allPlats);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }
        
        return result;
    }
    
    /**
     * Méthode retournant au format JSON les informations sur un plat recherché
     * @param id identifiant du plat recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getPlatJSON( int id ){
        String result = null;
        Plats myPlat = data.getPlat(id);
        
        // si le plat a été trouvé
        if( myPlat != null ) {
            
            // création du json et conversion du plat
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myPlat);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }
    
    /**
     * Méthode permettant de mettre à jours les informations d'un plat
     * @param id identifiant du plat à mettre à jours
     * @param plat les nouvelles infromations a utiliser
     * @return true si le plat a pu être mis à jours
     */
    public boolean updatePlat(int id, Plats plat) {
        return data.updatePlat(id, plat.nom, plat.description, plat.prix);
    }
    
    /**
     * Méthode permettant de créer un plat
     * @param plat les informations à utiliser
     * @return true si le plat a pu être créé
     */
    public boolean createPlat(Plats plat) {
        return data.createPlat(plat.nom, plat.description, plat.prix);
    }
    
    /**
     * Méthode permettant de supprimer un plat
     * @param id identifiant du plat à supprimer
     * @return true si le plat a pu être supprimé
     */
    public boolean deletePlat(int id) {
        return data.deletePlat(id);
    }
}
