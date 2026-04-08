<?php
$titrePage = "Vos commandes - Plateforme";
ob_start();
?>
    <h1>Historique de vos commandes</h1>

    <?php if (empty($orders)): ?>
        <p>Vous n'avez passé aucune commande pour le moment.</p>
    <?php else: ?>

        <ul style="list-style-type: none; padding-left: 0;">
            <?php foreach ($orders as $order): ?>
                <li>
                    <h2>Commande du <?= htmlspecialchars(str_replace('T', ' à ', $order['dateCommande'])) ?></h2>
                    
                    <p>
                        <strong>Livraison prévue le :</strong> <?= htmlspecialchars($order['dateLivraison']) ?><br>
                        <strong>Adresse :</strong> <?= htmlspecialchars($order['adresseLivraison']) ?>
                    </p>

                    <h3>Détails :</h3>
                    <ul>
                        <?php foreach ($order["lignes"] as $ligne): ?>
                            <li>
                                <?= htmlspecialchars($ligne["quantite"]) ?>x 
                                <strong><?= htmlspecialchars($ligne["menuNom"]) ?></strong>
                                
                                <?php if(isset($ligne["prixLigne"])): ?>
                                    <em>(<?= htmlspecialchars($ligne["prixLigne"]) ?> €)</em>
                                <?php endif; ?>
                            </li>
                        <?php endforeach; ?>
                    </ul>

                    <h3>Total payé : <?= htmlspecialchars($order["prixTotal"]) ?> €</h3>
                    
                    <hr>
                </li>
            <?php endforeach; ?>
        </ul>

    <?php endif; ?>

<?php
$content = ob_get_clean();
require 'layout.php';
?>