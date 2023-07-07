# java-API-REST
Author
- Author: `Ali Soule Mouanwiya`

Description
This README.txt file provides information about the source code in the "com.chat.Service.controller" package of the Java application.

Prerequisites
- `JDK (Java Development Kit)`: version 8 or higher
- `Maven`: dependency management tool for Java
- `Database`: Make sure you have a configured and accessible database to store the users

Installation and Execution
1. Clone the Git repository to your local environment:
   `git clone <git_repository_url>`

2. Navigate to the project's root directory:
   `cd <project_directory>`

3. Compile the project using Maven:
   `mvn clean install`

4. Run the application:
   `mvn spring-boot:run`

Endpoints
- `POST /api/v1/user/register`: Registers a user with the provided information. Sends a confirmation email with a QR code for registration.
- `POST /api/v1/user/resendCode`: Resends the registration confirmation code to an existing user.
- `POST /api/v1/user/valideCompte`: Validates a user's account using the confirmation code.
- `POST /api/v1/user/savesListe`: Saves a list of users.
- `GET /api/v1/user/Liste`: Retrieves the list of all users.
- `GET /api/v1/user/Searche/{nom}`: Searches users by name.
- `GET /api/v1/user/getOne/{id}`: Retrieves a user by their ID.
- `DELETE /api/v1/user/deleteOne/{id}`: Deletes a user by their ID.
- `PUT /api/v1/user/update/{id}`: Updates user information.

Dependencies
- `Spring Boot`: framework for Java application development
- `Spring Web`: for creating RESTful APIs
- `Spring Data JPA`: for data access with JPA
- `Spring Security`: for security and authentication
- `Thymeleaf`: for server-side HTML rendering
- `Apache POI`: for Excel file manipulation
- `iText`: for PDF file generation
- `QRGen`: for QR code generation
- `JavaMail`: for sending emails

Configuration
The application's configuration can be found in the "application.properties" file. Make sure to correctly configure the database information, SMTP settings for email sending, etc.

Contributions
Contributions to this project are welcome. Feel free to open a pull request with your modifications or report issues in the Issues section of the Git repository.

License
This project is licensed under the MIT License. Please see the "LICENSE" file for more information.

Contacts
For any questions or concerns, please contact the author Ali via `mouanwiya30@gmail.com`.


### -------------------------------------------------------

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
