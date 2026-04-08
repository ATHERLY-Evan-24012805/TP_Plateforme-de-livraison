<?php

namespace App\controller;

use App\services\CartService;
use App\services\ApiPlats;
use App\services\ApiMenu;

class controllerCart{
    private function isConnected(){
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
        if(!isset($_SESSION['token'])){
            header('location: /login');
            exit;
            return false;
        }
    }

    public function display() {
        $this->isConnected();

        $cartService = new CartService();
        $sessionCart = $cartService->getcart(); 

        $apiPlats = new ApiPlats();
        $apiMenu = new ApiMenu();
        
        $tousLesPlats = $apiPlats->getPlats();
        $tousLesMenus = $apiMenu->getMenus();

        $cartDetails = [
            'plats' => [],
            'menus' => [],
            'prixTotalPanier' => 0
        ];

        foreach ($sessionCart['plats'] as $id => $quantite) {
            foreach ($tousLesPlats as $platDb) {
                if ($platDb['id'] == $id) {
                    $platDb['quantite'] = $quantite;
                    $platDb['prixLigne'] = $platDb['prix'] * $quantite;
                    
                    $cartDetails['plats'][] = $platDb;
                    $cartDetails['prixTotalPanier'] += $platDb['prixLigne'];
                    break; 
                }
            }
        }
        require dirname(__DIR__) . '/views/cart.php';
    }
}