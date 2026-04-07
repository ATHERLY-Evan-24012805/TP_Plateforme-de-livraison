<?php
namespace App\controller;

class controllerAccueil {
    public function index() {
        require dirname(__DIR__) . '/views/accueil.php';
    }
}
    