<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ma Super App</title>
</head>
<body>
    <h1>Bienvenue sur la page des plats !</h1>
    <ul>
        <?php foreach ($plats as $plat): ?>
            <li>
                <h2><?= htmlspecialchars($plat['nom'])?></h2>
                <p><?= htmlspecialchars($plat['description'])?></p>
                <p><?= htmlspecialchars($plat['prix'])?> €</p>
            </li>
        <?php endforeach; ?>
    </ul>
</body>
</html>