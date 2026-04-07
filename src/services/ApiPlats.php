<?php

namespace App\services;

class ApiPlats {

    private string $url = "http://localhost:3000";
    // private string $users = "/utilisateurs";
    private string $plats = "/plats";

    public function getPlats() {
        $responseJson = file_get_contents($this->url . $this->plats);
        
        if ($responseJson === false) {
            return [];
        }

        $dataPlats = json_decode ($responseJson, true);
        return $dataPlats;
    }

    public function getPlatsById(int $itemId) {
        $responseJson = file_get_contents($this->url . $this->plats . "/" . $itemId);

        if ($responseJson === false) {
            return [];
        };
        $dataPlat = json_decode($responseJson, true);
        return $dataPlat;
    }
}