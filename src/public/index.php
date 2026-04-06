<?php
    require dirname(__DIR__,2) . '/vendor/autoload.php';

    use App\Router;
    use App\controller\controllerAccueil;
    use App\controller\controllerPlat;
    use App\controller\LoginController;

    // require_once 'Router.php';
    // require_once dirname(__DIR__) . '/controller/controllerAccueil.php';
    
    $router = new Router();

    $router->add('GET','/accueil',function()  {
        $controller = new controllerAccueil();
        $controller->index();
    });

    $router->add('GET','/plats',function() {
        $controller = new controllerPlat();
        $controller->getPlats();
    });

    $router->add('GET','/login',function() {
        $controller = new LoginController();
        $controller->index();
    });

    $router->add('POST','/login',function(){
        $controller = new LoginController();
        $controller->dateFromForm();
    });

    $uri = $_SERVER['REQUEST_URI'];
    $method = $_SERVER['REQUEST_METHOD'];

    $router->compare($uri, $method);