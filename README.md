Projet POO E-Commerce-System



------------------------------

User :

POST http://localhost:8080/users/register : Inscrire un nouvel utilisateur
{
  "username": "test_user",
  "email": "test@example.com",
  "password": "securepassword"
}


POST http://localhost:8080/users/login : Connecter un utilisateur
{
  "identifier": "test@example.com",
  "password": "securepassword"
}


POST http://localhost:8080/users/logout : Déconnecter un utilisateur
vide


GET http://localhost:8080/users  : Récupérer tous les utilisateurs

GET http://localhost:8080/users/{identifier} : Récupérer un utilisateur par son email ou username
Exemple : http://localhost:8080/users/john_doe


GET http://localhost:8080/users/orders : Récupérer l’historique des commandes de l’utilisateur connecté



------------------------------

Product


POST http://localhost:8080/products/add : Ajouter un produit (Admin uniquement)
{
  "productName": "Laptop",
  "productID": 201,
  "price": 1200.99,
  "stockQuantity": 15
}



GET	http://localhost:8080/products : Récupérer la liste de tous les produits

GET	http://localhost:8080/products/{productID} : Récupérer les détails d’un produit par ID
Exemple : http://localhost:8080/products/101

PUT	http://localhost:8080/products/{productID}/updateStock : Mettre à jour le stock d’un produit (Admin uniquement)
{
  "quantity": 5
}


DELETE http://localhost:8080/products/{productID} : Supprimer un produit (Admin uniquement)


------------------------------

Cart 

POST http://localhost:8080/cart/add : Ajouter un produit au panier
{
  "productId": 101,
  "quantity": 2
}


DELETE http://localhost:8080/cart/remove	: Supprimer un produit du panier
{
  "productId": 101
}


GET	http://localhost:8080/cart/total	: Calculer le total du panier

GET http://localhost:8080/cart/view : Voir le contenu du panier

DELETE http://localhost:8080/cart/clear : Vider le panier


------------------------------

Order 

POST http://localhost:8080/orders/place : Passer une commande (valider le panier)
Avec carte bancaire : 
{
  "paymentType": "creditCard",
  "accountDetails": "Test Account",
  "cardNumber": "1234-5678-9012-3456",
  "expirationDate": "12/25",
  "cvv": "123"
}

Avec PayPal : 
{
  "paymentType": "paypal",
  "email": "paypal@example.com"
}


PATCH http://localhost:8080/orders/{orderID}/status : Modifier le statut d’une commande (Admin uniquement)
{
    "Status": "Processing"
}

GET	http://localhost:8080/orders	: Récupérer toutes les commandes (Admin uniquement)

GET	http://localhost:8080/orders/{orderID} : Récupérer une commande par son ID


------------------------------

Utilisez Maven pour compiler et lancer l'application : mvn clean install

Lancer l'application Spring Boot : mvn spring-boot:run

L'application démarrera sur le port 8080


Membres du projet :
Léo : https://github.com/Leocollowald 
Thomas : https://github.com/thom972 
Ilies : https://github.com/IliesBossuyt