<?php

namespace App\model;

class User{
    private int $id;
    private string $name;
    private string $email;
    private array $listOfCommand;

    public function add(int $id, string $name, string $email, array $listOfCommand){
        $this->id = $id;
        $this->name = $name;
        $this->email = $email;
        $this->listOfCommande = $listOfCommand;
    }
}
