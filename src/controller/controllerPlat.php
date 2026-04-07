<?php

namespace App\controller;

use App\services\ApiPlats;
use App\services\ApiMenu;
use App\services\ApiOrder;
use App\model\Menu;
use App\model\Command;

class controllerPlat {
    public function getPlats() {
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
        if(!isset($_SESSION['token'])){
            header('location: /login');
            exit;
        }
            $api = new ApiPlats();

            $plats = $api->getPlats();
            return $plats;
    }
    public function getMenus(){
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
        if(!isset($_SESSION['token'])){
            header('location: /login');
            exit;
        }
            $api = new ApiMenu();

            $menus = $api->getMenus();
            return $menus;
    }
    public function displayAll($menus,$plats){
        require dirname(__DIR__) . '/views/plats.php';
    }

    public function order(int $itemId){
        $api = new ApiOrder();
        $plat = $api->getOrderByUserId($itemId);
    }
}