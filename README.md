# Projekt: MyBriefmarkensammlung

## Spring Boot Webanwendung

![Java](https://img.shields.io/badge/Java-primary?style=flat-square) 
![Spring Boot](https://img.shields.io/badge/Spring_Boot-success?style=flat-square) 
![Spring Security](https://img.shields.io/badge/Spring_Security-success?style=flat-square) 
![Spring Data JDBC](https://img.shields.io/badge/Spring_Data_JDBC-success?style=flat-square) 
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-dark?style=flat-square) 
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-light?style=flat-square)

---

### Über das Projekt
Diese Web-Anwendung ermöglicht es Briefmarkensammlern ihre Sammlungen mit anderen zu Teilen. Jeder Nutzer kann die Sammlungen nach unterschiedlichen Kriterien filtern (z.B. Titel, Gebiet, Ausstellungsklasse oder Besitzer) und abrufen. Authentifizierte Nutzer können zudem neue Sammlungen hochladen (Bilder und Eckdaten). Falls keine passende Kategorie für die Sammlung existiert, dann kann eine neue (Unter-)kategorie hinzugefügt werden.

---

### Live-Demo

**[https://mybriefmarkensammlung.onrender.com](https://mybriefmarkensammlung.onrender.com)**

#### Demo-Zugang
* **Nutzername:** `test`
* **Passwort:** `123456`
* *Hinweis: Das Hochfahren des Servers kann bis zu 60 Sek. dauern.*

---

### Domäne (ER-Diagramm)
<img width="691" height="531" alt="er-diagram" src="https://github.com/user-attachments/assets/5085cc5a-32f3-4acf-8b2d-099038023d53" />

---

### Systemarchitektur

#### Frontend
* Serverseitiges Rendering mit Thymeleaf
* Responsive Gestaltung mit Bootstrap (teilweise KI generiert)

#### Backend
* Einhaltung der Schichtenarchitektur (Controller, Service, Repository, Domain)
* Trennung von Domain-Models und DTOs
* Login-System mit Spring Security
* Passwort-Hashing mit BCrypt
* Bildoptimierung mit Thumbnailator (Optimierung von Speicher und Ladezeit)
* Exception-Handling für Datenbankzugriffe

#### Datenbank
* Persistente Speicherung mit PostgreSQL
* Dateninteraktion mit Spring Data JDBC

#### Versionskontrolle
* Code-Management mit Git.

#### Deployment
* Containerisierung mittels Docker und Hosting auf Render.com.
