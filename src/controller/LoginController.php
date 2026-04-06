<?php 

namespace App\controller;

use App\services\LoginService;

class LoginController {

    public function index(){
        require dirname(__DIR__) . '/views/login.php';
    }
    public function dateFromForm(){
        if ($_SERVER['REQUEST_METHOD'] === 'POST'){
            $email = filter_var($_POST['email'], FILTER_SANITIZE_EMAIL);
            $password = $_POST['password'];
            $loginService = new LoginService();
            $isConnected = $loginService->login($email,$password);

            if(isset($isConnected['accessToken'])) {
                var_dump("lo");
                session_start();

                $_SESSION['token'] = $isConnected['accessToken'];
                $_SESSION['user_id'] = $isConnected['user']['id'];

                header('Location: /accueil');
                exit;
            }else{
                $erreur = "Identifiants incorrects.";
                require dirname(__DIR__) . '/views/login.php';
            }
        }
    }
}