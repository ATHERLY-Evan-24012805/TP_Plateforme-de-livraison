## Liste des options à implémenter :

Afficher les differents composants :
- Les plats -> View Store
- Les menus et leurs createurs -> Une View Menu
- Envoi de commande a API -> View Commande

Un utilisateur crée un **MENU** avec des **PLATS** et son **NOM d'UTILISATEUR** y est lié.
Un autre utilisateur commande un **MENU** ou un **PLAT**

Pour afficher tous ces données il me faut des controllers qui feront l'appelle vers les API des services.
Je pars sur un MVC pour intéragire avec les différents services.

Modeles : 
- Plat
- Menu
- utilisateur (rien à voir avec la classe user *JAVA* )
- commande
Dans une commande on peut avoir des plats et des menus, par consequent il me faut une interface communes entre les deux.
- items <<interface>>

Views :
- PlatStore     ( Tous les plats recup via API)
- MenuStore     ( Tous les menus recup via API)
- CommandeStore (commande ulterieur et en cours)

Controlleurs :
- ControllerPlat
- ControlMenu
- ControlCommande

Pour ce qui est du lien entre la classe utilisateur de l'api *plat et utilisateurs* et la classe utilisateur de php je vais faire en sorte qu'a partir du moment où tu existes dans l'api tu existes dans l'application de livraison 

**Ewan** : Il me faut une méthode createUSer(name, email) car la BDD des users est avec la composante *plat et utilisateurs*

**Thomas** : Il me faut une méthdoe createCommande(abonneId, adresse, items : array) car la BDD des commandes est avec la composante *commandes*, il faudrait aussi potientiellement une methode interne pour avoir le prix d'un commande en fonction des objets dans items, ils peuvent être des plats ou des menu ce qui implique une interface commune surement.  