<?php 

namespace App\controller;

// use App\model\
use App\services\ApiOrder;

class controllerCommand{
    private function isConnected(){
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
        if(!isset($_SESSION['token'])){
            header('location: /login');
            exit;
        }
    }

    public function getOrderByUserId(int $userId){
        $this->isConnected();
        $api = new ApiOrder;

        $orders = $api->getOrderByUserId($userId);

        require dirname(__DIR__) . '/views/ordersView.php';
    }

    public function createCommand($cartDetails) {
        if (!isset($_SESSION['user_id'])) {
            return false; 
        }

        $commande = [
            "abonneId" => $_SESSION['user_id'],
            "adresseLivraison" => $_SESSION['address'], 
            "lignes" => [],
            "prixTotal" => $cartDetails['prixTotalPanier']
        ];

        foreach ($cartDetails['menus'] as $menu) {
            $commande['lignes'][] = [
                "menuNom" => $menu['nom'],
                "quantite" => $menu['quantite'],
                "prixUnitaire" => $menu['prixTotal'], 
                "prixLigne" => $menu['prixLigne']
            ];
        }

        foreach ($cartDetails['plats'] as $plat) {
            $commande['lignes'][] = [
                "menuNom" => $plat['nom'] . " (à la carte)", 
                "quantite" => $plat['quantite'],
                "prixUnitaire" => $plat['prix'], 
                "prixLigne" => $plat['prixLigne']
            ];
        }

        $donneesJson = json_encode($commande);
        $api = new ApiOrder();
        return $api->envoyerCommandeApi($donneesJson);
    }
}