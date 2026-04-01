<?php

namespace App\controller;

use App\model\ApiPlats;

class controllerPlat {
    public function getPlats() {
        $api = new ApiPlats();

        $plats = $api->getPlats();

        require dirname(__DIR__) . '/views/plats.php';
    }
}