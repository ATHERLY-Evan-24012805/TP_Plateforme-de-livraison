<?php

namespace App\services;

class LoginService{
    private string $url = "http://localhost:3000";

    public function login (string $email,string $password) {
        $donnees = [
            'email' => $email,
            'password' => $password
        ];
        var_dump($donnees);

        $request = [
            'http'=>[
                'method' => 'POST',
                'header' => "Content-type: application/json\r\n",
                'content'=>  json_encode($donnees),
            ]
        ];
        $context = stream_context_create($request);
        
        $responseJson = file_get_contents($this->url . '/login', false, $context);
        var_dump($responseJson);

        return json_decode($responseJson, true);
    }
}