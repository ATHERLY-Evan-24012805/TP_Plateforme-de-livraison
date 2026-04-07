<?php
$titrePage = "Vos commandes - Plateforme";
ob_start();
?>
    <h1>Vos commandes</h1>
    <ul>
        <?php foreach ($orders as $order): ?>
            <li>
                <p><?= htmlspecialchars($order['dateCommande'])?></p>
                <p><?= htmlspecialchars($order['dateLivraison'])?></p>
                <p><?= htmlspecialchars($order['adresseLivraison'])?></p>
                <?php foreach ($order["lignes"] as $ligne): ?>
                    <li>
                        <h3><?= htmlspecialchars($ligne["menuNom"])?></h3>
                        <p><?= htmlspecialchars($ligne["quantite"])?></p>
                    </li>
                    <?php endforeach; ?>
                <p><?= htmlspecialchars($order["prixTotal"])?></p>
            </li>
        <?php endforeach; ?>
    </ul>
<?php
$content = ob_get_clean();
require 'layout.php';
?>