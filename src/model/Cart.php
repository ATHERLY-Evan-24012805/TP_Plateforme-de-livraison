<?php

namespace App\model;

use App\model\Plat;
use App\model\Menu;

class Cart{
    private Menu $menu;
    private array $listItems = [];
    #$this->listItems.push($menu);

    public function addToCart(Plat $plat){
        if($plat instanceof Plat){
            $this->listItems->push($plat);
        }else{
            echo "l'objet envoyer n'est ni un plat ni un menu";
        }
    }

    public function removeFromCart(Plat $plat){
        if ($plat instanceof Plat){
            foreach($this->listItems as $key => $item){
                if ($item->getId() === $plat->getId() && $item->getName() === $plat->getName()){
                    unset($this->listItems[$key]);

                    $this->listItems = array_values($this->listItems);

                    break;
                }
            }
        }
    }

}