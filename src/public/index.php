<?php
    require dirname(__DIR__,2) . '/vendor/autoload.php';

    use App\Router;
    use App\controller\controllerAccueil;

    // require_once 'Router.php';
    // require_once dirname(__DIR__) . '/controller/controllerAccueil.php';
    
    $router = new Router();

    $router->add('GET','/accueil',function()  {
        $controller = new controllerAccueil();
        $controller->index();
    });

    $uri = $_SERVER['REQUEST_URI'];
    $method = $_SERVER['REQUEST_METHOD'];

    $router->compare($uri, $method);