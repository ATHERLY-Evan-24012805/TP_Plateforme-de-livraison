<?php

namespace App\model;

class Plat{
    private int $id;
    private string $name;
    private string $description;
    private float $price;

    public function add(int $id, string $name, string $description, float $price) {
        $this->id = $id;
        $this->name = $name;
        $this->description = $description;
        $this->price = $price;
    } 
} 