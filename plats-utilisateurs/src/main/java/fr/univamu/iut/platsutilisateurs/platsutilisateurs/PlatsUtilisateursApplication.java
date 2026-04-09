package fr.univamu.iut.platsutilisateurs.platsutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api")
@ApplicationScoped
public class PlatsUtilisateursApplication extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de données au moment de la création
     * de la ressource
     * @return un objet implémentant l'interface Data utilisée
     *          pour accéder aux données des livres, voire les modifier
     */
    @Produces
    private Data openDbConnection(){
        DataBase db = null;

        try{
            db = new DataBase("jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_library_db", "[compte]_library", "mdp");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque l'application est arrêtée
     * @param platsUtilisateursRepo la connexion à la base de données instanciée dans la méthode @openDbConnection
     */
    private void closeDbConnection(@Disposes Data platsUtilisateursRepo ) {
        platsUtilisateursRepo.close();
    }
}