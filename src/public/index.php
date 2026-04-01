<?php
    require dirname(__DIR__,2) . '/vendor/autoload.php';

    use App\Router;
    use App\controller\controllerAccueil;
    use App\controller\controllerPlat;

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

    $uri = $_SERVER['REQUEST_URI'];
    $method = $_SERVER['REQUEST_METHOD'];

    $router->compare($uri, $method);