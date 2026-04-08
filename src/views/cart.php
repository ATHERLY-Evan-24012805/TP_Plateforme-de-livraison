<?php
$titrePage = "Panier - Plateforme";
ob_start();
?>

<h1>Votre Panier</h1>

<div>
    <h2>Plats :</h2>
    <ul>
        <?php foreach ($cartDetails["plats"] as $plat): ?>
            <li>
                <strong><?= htmlspecialchars($plat['nom']) ?></strong> <br>
                Quantité : <?= $plat['quantite'] ?> <br>
                Prix unitaire : <?= htmlspecialchars($plat['prix']) ?> € <br>
                <em>Total pour ce plat : <?= $plat['prixLigne'] ?> €</em>
            </li>
        <?php endforeach; ?>
        <?php if(empty($cartDetails["plats"])): ?>
            <p>Aucun plat dans le panier.</p>
        <?php endif; ?>
    </ul>
</div>

<hr>
<h3>Total à payer : <?= $cartDetails['prixTotalPanier'] ?> €</h3>

<?php
$content = ob_get_clean();
require 'layout.php';
?>