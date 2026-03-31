<?php
namespace App\controller;

class controllerAccueil {
    public function index() {
        $titrePage = "accueil de la plateforme";
        $user = " bouboooooou";

        require dirname(__DIR__) . '/views/accueil.php';
    }
}
    