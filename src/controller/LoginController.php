<?php 

namespace App\controller;

use App\services\LoginService;

class LoginController {

    public function index(){
        require dirname(__DIR__) . '/views/loginView.php';
    }
    public function dataFromForm(){
        if ($_SERVER['REQUEST_METHOD'] === 'POST'){
            $email = filter_var($_POST['email'], FILTER_SANITIZE_EMAIL);
            $password = $_POST['password'];
            $loginService = new LoginService();
            $isConnected = $loginService->login($email,$password);

            if(isset($isConnected['accessToken'])) {
                session_start();

                
                $_SESSION['token'] = $isConnected['accessToken'];
                $_SESSION['user_id'] = $isConnected['user']['id'];
                $_SESSION['address'] = $isConnected['user']['adresse'];

                header('Location: /plats');
                exit;
            }else{
                $erreur = "Identifiants incorrects.";
                require dirname(__DIR__) . '/views/loginView.php';
            }
        }
    }
}