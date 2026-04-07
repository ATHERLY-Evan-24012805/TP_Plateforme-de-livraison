<?php

namespace App\services;

class LoginService{
    private string $url = "http://localhost:3000";

    public function login (string $email,string $password) {
        $donnees = [
            'email' => $email,
            'password' => $password
        ];

        $request = [
            'http'=>[
                'method' => 'POST',
                'header' => ["Content-type: application/json"],
                'content'=>  json_encode($donnees),
                'ignore_errors' => true
            ]
        ];
        $context = stream_context_create($request);
        $responseJson = file_get_contents($this->url . '/login', context : $context);
        var_dump($responseJson);
        return json_decode($responseJson, true);
    }
}