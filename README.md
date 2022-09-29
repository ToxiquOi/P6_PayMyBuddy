# PayMyBuddy
Projet 6 OCR

## Prérequis

- Java 17
- Maven
- NPM
- docker / docker-compose

## Configuration

- le fichier 'compose.yml' contient le secret permettant à jasypt de décripter le mdp de la bdd
- le mot de passe de la bdd est situé dans le fichier application.properties, il est crypté à l'aide de JASYPT et du secret.

## Démarrage de l'application

- Cloner le projet.
- cd P6_PayMyBuddy/ : se rendre à la racine du projet
- ./mvnw package : grace à l'utilisation d'un plugin, le goal maven 'package' déclenchera la génération du dockerfile et le déploiement des conteneurs sur votre machine.










