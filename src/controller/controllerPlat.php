<?php

namespace App\controller;

use App\services\ApiPlats;
use App\services\ApiMenu;
use App\services\ApiOrder;
use App\services\CartService;
use App\model\Menu;
use App\model\Command;

class controllerPlat {

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
    public function getPlats() {
        $this->isConnected();
    
        $api = new ApiPlats();

        $plats = $api->getPlats();
        return $plats;
    }
    public function getMenus(){
        $this->isConnected();
        $api = new ApiMenu();

        $menus = $api->getMenus();
        return $menus;
    }

    public function displayAll($menus,$plats){
        require dirname(__DIR__) . '/views/platsView.php';
    }

    public function addToCart(int $idItem, bool $isMenu, $menus, $plats){
        $this->isConnected();
        $cart = new CartService();
        if ($isMenu) {
            foreach ($menus as $menu){
                if ($menu["id"] == $idItem){
                    $cart->addMenu($idItem);
                }
            }
        }else {
            foreach ($plats as $plat){
                if ($plat["id"] == $idItem){
                    $cart->addPlat($idItem);
                }
            }
        }        
    }
}