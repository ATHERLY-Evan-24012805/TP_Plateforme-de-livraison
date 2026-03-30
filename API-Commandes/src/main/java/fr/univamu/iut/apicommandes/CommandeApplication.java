package fr.univamu.iut.apicommandes;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Main configuration class for the Jakarta REST application.
 * Defines the base API path ("/api") and manages Dependency Injection (CDI)
 * for the data access layer.
 */
@ApplicationPath("/api")
@ApplicationScoped
public class CommandeApplication extends Application {

    /**
     * CDI Producer method that creates and configures the MariaDB Repository.
     * It retrieves connection credentials from system environment variables
     * (DB_URL, DB_USER, DB_PASSWORD) to ensure security and portability.
     * * @return A scoped instance of the CommandeRepositoryInterface.
     * @throws RuntimeException if the repository cannot be instantiated due to missing
     * environment variables or database connection failure.
     */
    @Produces
    @ApplicationScoped
    public CommandeRepositoryInterface getRepository() {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASSWORD");

        try {
            return new CommandeRepositoryMariaDB(url, user, pass);
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'instancier le repository MariaDB", e);
        }
    }
}