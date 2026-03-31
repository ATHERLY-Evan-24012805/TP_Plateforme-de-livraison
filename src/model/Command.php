<?php

class Command {
    
    private int $id;
    private int $subscriberId;
    private DateTime $dateCommand;
    private string $address;
    private DateTime $dateLivraison;
    private array $shoppingCart;

    // Absance des date car je ne sais pas elles sont communiquées dans l'api 
    public function add(int $id, int $subscriberId, string $address,array $shoppingCart) {
        $this->id = $id;
        $this->subscriberId = $subscriberId;
        $this->address = $address;
        $this->shoppingCart = $shoppingCart;
    }

}