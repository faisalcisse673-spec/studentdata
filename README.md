# StudentData - Plateforme distribuée de ressources pédagogiques (EPO)

Plateforme permettant la gestion et le partage de ressources pédagogiques (cours, documents, etc.) entre enseignants et étudiants.

## Stack technique

- **Backend** : Java 17, Spring Boot 3.2.0
- **Base de données** : MySQL
- **Sécurité** : Spring Security + JWT
- **Frontend** : HTML / CSS / JavaScript (statique, servi par Spring Boot)
- **RMI** : communication distribuée pour la partie ressources

## Prérequis

- Java 17 (JDK)
- MySQL Server (en cours d'exécution sur `localhost:3306`)
- Maven (optionnel, le wrapper `mvnw`/`mvnw.cmd` est inclus)

## Installation

### 1. Cloner le projet

```bash
git clone https://github.com/faisalcisse673-spec/studentdata.git
cd studentdata
```

### 2. Créer la base de données

Dans MySQL, créer une base nommée `studentdata` :

```sql
CREATE DATABASE studentdata;
```

### 3. Configurer la connexion à la base

Le fichier `src/main/resources/application.properties` contient déjà la configuration par défaut :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdata?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=
```

Adapter `spring.datasource.username` et `spring.datasource.password` selon votre installation MySQL si nécessaire.

Les tables sont créées/mises à jour automatiquement au démarrage grâce à `spring.jpa.hibernate.ddl-auto=update`.

## Lancement de l'application

### Windows

```bash
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

L'application démarre par défaut sur le port **8081**.

## Accès à l'application

- **API REST** : `http://localhost:8081/api`
- **Pages web (frontend statique)** :
  - `http://localhost:8081/index.html` — page d'accueil
  - `http://localhost:8081/login.html` — connexion
  - `http://localhost:8081/dashboard.html` — tableau de bord
  - `http://localhost:8081/admin.html` — administration

## Structure du projet

```
src/main/java/com/epo/studentdata/
├── controller/      # Contrôleurs REST (Auth, User, Ressource, Matiere, Filiere, Notification, Admin...)
├── model/            # Entités JPA (User, Ressource, Matiere, Filiere, Notification, Role...)
├── repository/       # Interfaces Spring Data JPA
├── service/           # Logique métier
├── security/          # Configuration JWT et sécurité
├── rmi/                # Serveur et client RMI
└── config/            # Configuration générale (CORS, initialisation des données...)

src/main/resources/
├── application.properties   # Configuration de l'application
└── static/                   # Frontend (HTML, CSS, JS)
```

## Notes

- Des données de démonstration peuvent être initialisées au démarrage via `DataInitializer`.
- Le dossier `uploads/` contient des fichiers de ressources uploadées (PDF de démonstration).
