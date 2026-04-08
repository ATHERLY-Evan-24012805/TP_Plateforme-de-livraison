<?php
namespace App\services;

class CartService {
    
    public function __construct() {
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }
        if (!isset($_SESSION['cart'])) {
            $_SESSION['cart'] = [
                'plats' => [],
                'menus' => []
            ];
        }   
    }

    public function addPlat(int $idPlat) {
        if (!empty($_SESSION['cart']['plats'][$idPlat])) {
            $_SESSION['cart']['plats'][$idPlat]++;
        } else {
            $_SESSION['cart']['plats'][$idPlat] = 1;
        }
    }

    public function addMenu(int $idMenu) {
        if (!empty($_SESSION['cart']['menus'][$idMenu])) {
            $_SESSION['cart']['menus'][$idMenu]++;
        } else {
            $_SESSION['cart']['menus'][$idMenu] = 1;
        }
    }
    public function removePlat(int $idPlat) {
        if (isset($_SESSION['cart']['plats'][$idPlat])) {
            
            $_SESSION['cart']['plats'][$idPlat]--;

            if ($_SESSION['cart']['plats'][$idPlat] <= 0) {
                unset($_SESSION['cart']['plats'][$idPlat]);
            }
        } 
    }

    public function removeMenu(int $idMenu) {
        if (isset($_SESSION['cart']['menus'][$idMenu])) {
            $_SESSION['cart']['menus'][$idMenu]--;

            if ($_SESSION['cart']['menus'][$idMenu] <= 0) {
                unset($_SESSION['cart']['menus'][$idMenu]);
            }
        }
    }

    public function getcart() {
        return $_SESSION['cart'];
    }
}