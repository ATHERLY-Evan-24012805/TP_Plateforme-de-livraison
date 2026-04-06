<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connextion</title>
</head>
<body>
    <h1>Veuillez renter vos informations pour vous connecter</h1>
    <form action="/login" method ="POST">
        <div>
            <div><b><?=htmlspecialchars($erreur) ?></b></div>
            <label><b>votre email : </b>
            <input placeholder="exemple@mail.com" name="email" type="text" required>
        
            <label><b>votre mot de passe : </b>
            <input placeholder="monMotDePasse" name="password" type="password" required>

            <button type="submit">Login</button>
        </div>
    </form>
</body>
</html>