<?php

namespace App\services;

class ApiMenu {

    private string $url = "http://localhost:3006/menus";

    public function getMenus(){
        $responseJson = file_get_contents($this->url . $this->menu);

        if($responseJson === false) {
            return [];
        }
        $dataMenus = json_decode($responseJson,true);
        return $dataMenus;
    }
}