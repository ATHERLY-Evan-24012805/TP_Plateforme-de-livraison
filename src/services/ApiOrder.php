<?php

namespace App\services;

class ApiOrder{
    private string $url = "http://localhost:3005/commandes";

    public function getOrderByUserId(int $userId){
        $responseJson = file_get_contents($this->url . "?abonneId=".$userId);
        if ($responseJson === false) {
            return [];
        }
        $dataPlats = json_decode ($responseJson, true);
        return $dataPlats;
    } 
}
