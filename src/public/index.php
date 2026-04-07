<?php
    require dirname(__DIR__,2) . '/vendor/autoload.php';

    use App\Router;
    use App\controller\controllerAccueil;
    use App\controller\controllerPlat;
    use App\controller\LoginController;
    use App\controller\controllerCommand;

    // require_once 'Router.php';
    // require_once dirname(__DIR__) . '/controller/controllerAccueil.php';
    
    $router = new Router();

    $router->add('GET','/accueil',function()  {
        $controller = new controllerAccueil();
        $controller->index();
    });

    $router->add('GET','/plats',function() {
        $controller = new controllerPlat();
        $plats = $controller->getPlats();
        $menus = $controller->getMenus();
        $controller->displayAll($menus,$plats);
    });

    $router->add('GET','/login',function() {
        $controller = new LoginController();
        $controller->index();
    });

    $router->add('POST','/login',function(){
        $controller = new LoginController();
        $controller->dataFromForm();
    });
    $router->add('GET','/logout',function(){
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
        session_unset();
        session_destroy();
        var_dump($_SESSION['token']);
        header('Location: /accueil');
        exit;
    });

    $router->add('GET','/commandes',function(){
        session_start();
        if (isset($_SESSION["user_id"])) {
            $id = $_SESSION["user_id"];
            $controller = new controllerCommand();
            $controller->getOrderByUserId($id);
        }
        else{
            echo "Erreur : aucun plat n'est trouvé";
        }
    });

    $uri = $_SERVER['REQUEST_URI'];
    $method = $_SERVER['REQUEST_METHOD'];

    $router->compare($uri, $method);