mvn clean install
mvn spring-boot:run

---------------
USER :

POST /users/register : Inscrire un nouvel utilisateur
{
  "username": "test_user",
  "email": "test@example.com",
  "password": "securepassword",
  "role": "RegularUser"
}


POST /users/login : Connecter un utilisateur
{
  "identifier": "test@example.com",
  "password": "securepassword"
}


POST users/logout : Déconnecter un utilisateur
vide


GET /users : Récupérer tous les utilisateurs

GET /users/{identifier} : Récupérer un utilisateur par son email ou username

GET /users/orders : Récupérer l’historique des commandes de l’utilisateur connecté



----------------
Product


POST /products/add : Ajouter un produit (Admin uniquement)
{
  "productName": "Laptop",
  "productID": 201,
  "price": 1200.99,
  "stockQuantity": 15
}



GET	/products : Récupérer la liste de tous les produits

GET	/products/{productID} : Récupérer les détails d’un produit par ID

PUT	/products/{productID}/updateStock : Mettre à jour le stock d’un produit
{
  "quantity": 5
}


DELETE /products/{productID} : Supprimer un produit (Admin uniquement)


----------------
Cart 

POST cart/add : Ajouter un produit au panier
{
  "productId": 101,
  "quantity": 2
}


DELETE /cart/remove	: Supprimer un produit du panier
{
  "productId": 101
}


GET	/cart/total	: Calculer le total du panier

GET	/cart/view : Voir le contenu du panier

DELETE /cart/clear : Vider le panier


-------
Order 

POST /orders/place : Passer une commande (valider le panier)
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


PATCH /orders/{orderID}/status : Modifier le statut d’une commande (Admin uniquement)
"Shipped"

GET	/orders	: Récupérer toutes les commandes (Admin uniquement)

GET	/orders/my-orders: Récupérer les commandes de l'utilisateur connecté

GET	/orders/{orderID} : Récupérer une commande par son ID