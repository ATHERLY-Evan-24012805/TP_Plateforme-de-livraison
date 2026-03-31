<?php

namespace App\model;

class Menu{
    private int $id;
    private string $name;
    private int $creatorId;
    private DateTime $dateCreation;
    private DateTime $dateLastUpdate;
    private int $totalPrice;

    public function add(int $id, string $name, int $creatorId, int $totalPrice){
        $this->id = $id;
        $this->name = $name;
        $this->creatorId = $creatorId ;
        $this->totalPrice = $totalPrice;
        $this->dateCreation = new DateTime();
        $this->dateLastUpdate = $this->dateCreation;
    }

    // public function update(){}
}