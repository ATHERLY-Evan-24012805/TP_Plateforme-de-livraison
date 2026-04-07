<?php
$titrePage = "Catalogue - Plateforme";
ob_start();
?>
<h1>Bienvenue sur la page des plats !</h1>
<div style='display : flex; flex-direction : row'>
    <ul>
        <?php foreach ($plats as $plat): ?>
            <li>
                <h2><?= htmlspecialchars($plat['nom'])?></h2>
                <p><?= htmlspecialchars($plat['description'])?></p>
                <p><?= htmlspecialchars($plat['prix'])?> €</p>
                <a href="/command?id=<?= $plat['id'] ?>">
                    <img src="cart.svg" alt="Ajouter au panier">
                </a>
            </li>
        <?php endforeach; ?>
    </ul>
    <ul style="flex-wrap:wrap">
        <?php foreach ($menus as $menu): ?>
            <li>
                <h2><?= htmlspecialchars($menu['nom'])?></h2>
                <p>plats : </p>
                <?php foreach ($menu["plats"] as $item): ?>
                    <p><?= htmlspecialchars($item["nom"])?></p>
                <?php endforeach; ?>
                <p><?= htmlspecialchars($menu['prixTotal'])?> €</p>
                <a href="/command?id=<?= $menu['id'] ?>">
                    <img src="cart.svg" alt="Ajouter au panier">
                </a>
            </li>
        <?php endforeach; ?>
    </ul>
</div>
<?php 
$content = ob_get_clean();
require 'layout.php';
?>