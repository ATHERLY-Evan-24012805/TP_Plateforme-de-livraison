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
            db = new DataBase("jdbc:mariadb://mysql-td2-arch-logi.alwaysdata.net/td2-arch-logi_projet", "td2-arch-logi_projet", "Mot de passe");
        }
        catch (Exception e){
            e.printStackTrace();
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