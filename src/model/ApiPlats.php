<?php

namespace App\model;

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
}