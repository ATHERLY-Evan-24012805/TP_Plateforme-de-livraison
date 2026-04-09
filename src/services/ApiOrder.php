<?php

namespace App\services;

class ApiOrder{
    // private string $url = "http://localhost:3005/commandes";
    private string $url = "http://localhost:8080/API-Commandes-1.0-SNAPSHOT/api/commandes";

    public function getOrderByUserId(int $userId){
        $responseJson = file_get_contents($this->url . "?abonneId=".$userId);
        if ($responseJson === false) {
            return [];
        }
        $dataPlats = json_decode ($responseJson, true);
        return $dataPlats;
    } 
    public function envoyerCommandeApi(string $jsonPayload) {

        $options = [
            'http' => [
                'method'  => 'POST',
                'header'  => ["Content-Type: application/json"],
                'content' => $jsonPayload,
                'ignore_errors' => true
            ]
        ];

        $context  = stream_context_create($options);
        $resultat = file_get_contents($this->url, false, $context);

        if ($resultat !== false) {
            unset($_SESSION['cart']); 
            return true;
        }

        return false;
    }
}
