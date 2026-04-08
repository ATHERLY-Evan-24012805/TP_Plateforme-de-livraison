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

    public function getCartDetails(){
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
        foreach ($sessionCart['menus'] as $id => $quantite) {
            foreach ($tousLesMenus as $menuDb) {
                if ($menuDb['id'] == $id) {
                    $menuDb['quantite'] = $quantite;
                    $menuDb['prixLigne'] = $menuDb['prixTotal'] * $quantite;
                    
                    $cartDetails['menus'][] = $menuDb;
                    $cartDetails['prixTotalPanier'] += $menuDb['prixLigne'];
                    break; 
                }
            }
        }
        return $cartDetails;
    }

    public function display($cartDetails) {
        $this->isConnected();
        require dirname(__DIR__) . '/views/cart.php';
    }
}