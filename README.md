# java-API-REST

Auteur
- Auteur: `Ali Soule Mouanwiya`

Description
Ce fichier README.txt contient des informations sur le code source du package "com.chat.Service.controller" de l'application Java.

Prérequis
- `JDK (Java Development Kit)` : version 8 ou supérieure
- `Maven` : gestionnaire de dépendances pour Java
- `Base de données` : assurez-vous d'avoir une base de données configurée et accessible pour stocker les utilisateurs

Installation et exécution
1. Clonez le dépôt Git dans votre environnement local :
   `git clone <url_du_dépôt_git>`

2. Accédez au répertoire racine du projet :
   `cd <répertoire_du_projet>`

3. Compilez le projet à l'aide de Maven :
   `mvn clean install`

4. Exécutez l'application :
   `mvn spring-boot:run`

Endpoints
- `POST /api/v1/user/register` : Enregistre un utilisateur avec les informations fournies. Envoie un email de confirmation d'inscription avec un code QR.
- `POST /api/v1/user/resendCode` : Renvoie le code de confirmation d'inscription à un utilisateur existant.
- `POST /api/v1/user/valideCompte` : Valide le compte d'un utilisateur à l'aide du code de confirmation.
- `POST /api/v1/user/savesListe` : Enregistre une liste d'utilisateurs.
- `GET /api/v1/user/Liste` : Récupère la liste de tous les utilisateurs.
- `GET /api/v1/user/Searche/{nom}` : Recherche les utilisateurs par nom.
- `GET /api/v1/user/getOne/{id}` : Récupère un utilisateur par son identifiant.
- `DELETE /api/v1/user/deleteOne/{id}` : Supprime un utilisateur par son identifiant.
- `PUT /api/v1/user/update/{id}` : Met à jour les informations d'un utilisateur.

Dépendances
- `Spring Boot` : framework pour le développement d'applications Java
- `Spring Web` : pour la création d'API RESTful
- `Spring Data JPA` : pour l'accès aux données avec JPA
- `Spring Security` : pour la sécurité et l'authentification
- `Thymeleaf` : pour le rendu des pages HTML côté serveur
- `Apache POI` : pour la manipulation de fichiers Excel
- `iText` : pour la génération de fichiers PDF
- `QRGen` : pour la génération de codes QR
- `JavaMail` : pour l'envoi d'e-mails

Configuration
La configuration de l'application se trouve dans le fichier "application.properties". Assurez-vous de configurer correctement les informations de la base de données, les paramètres SMTP pour l'envoi d'e-mails, etc.

Contributions
Les contributions à ce projet sont les bienvenues. N'hésitez pas à ouvrir une demande de fusion (pull request) avec vos modifications ou à signaler des problèmes dans la section des problèmes (issues) du dépôt Git.

Licence
Ce projet est sous licence MIT. Veuillez consulter le fichier "LICENSE" pour plus d'informations.

Contacts
Pour toute question ou préoccupation, veuillez contacter l'auteur Ali via `mouanwiya30@gmail.com`.
