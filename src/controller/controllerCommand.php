<?php 

namespace App\controller;

// use App\model\
use App\services\ApiOrder;

class controllerCommand{
    public function getOrderByUserId(int $userId){
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }

        if(!isset($_SESSION['token'])){
            header('location: /login');
            exit;
        }

        $api = new ApiOrder;

        $orders = $api->getOrderByUserId($userId);

        require dirname(__DIR__) . '/views/orders.php';
    }
}