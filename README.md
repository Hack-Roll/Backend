# Code HACK N' ROLL Platform - Backend

This repository contains the backend of the web platform developed for HACK N' ROLL, an initiative to facilitate the organization of technological events within the community. The main objective is to create a virtual meeting point that promotes collaboration, continuous learning, and innovation in the tech sector.

## Objectives

✨ **HACK N' ROLL** aims to implement a web platform that enables the organization of tech events (online and in-person) to foster collaboration and knowledge sharing within the tech community.

## 🛠️ Technologies and Tools

- **Programming Language:** Java 21
- **Backend Framework:** Spring Boot 3.4.4
- **Spring Data:** JPA
- **Spring Security:** JWT
- **Database:** PostgreSQL / H2
- **IDE:** Visual Studio Code
- **Project Management:** Trello
- **Version Control:** Git - GitHub
- **API Client:** Postman


## 🎯 Backend Application Objectives

A backend has been developed using **Spring Boot** with a **3-layer MVC architectural pattern**, following a **client-server (REST API)** style. This backend connects to a **PostgreSQL** database to manage the platform's information.

The web platform allows registered users to:

* Create tech events such as masterclasses, workshops, and hackathons.
* Sign up for events created by other users.
* View the list of attendees for an event (registered users only).
* Manage their user profile (name, email, password, image).
* Sign up for and withdraw from events.
* Create, edit, and delete their own events.
* View the events they have created.

The application also offers functionalities for non-authenticated users:

* View an informative homepage about Code Crafters.
* See all events created by users.
* Navigate through events using pagination (maximum 15 events per page).
* Filter events by category (in-person/online), username, event name, and date.
* View the details of each event (image, title, description, date, time, maximum capacity, location, category).


## ⚙️ Environment Setup and Running the Application

To set up the development environment and run the backend application, follow these steps:

### Prerequisites

* **Java Development Kit (JDK):** Ensure you have a compatible JDK installed on your system (e.g., Java 17 or later). You can download it from [Oracle Java Downloads](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.java.net/install/).
* **Maven:** This project uses Maven as its build automation tool. Make sure you have Maven installed. You can download it from [Apache Maven](https://maven.apache.org/download.cgi).
* **PostgreSQL:** You need a running instance of PostgreSQL. You can install it locally from the [PostgreSQL website](https://www.postgresql.org/download/) or use a cloud-based PostgreSQL service.
* **IntelliJ IDEA or another Java IDE (Optional but Recommended):** For easier development and debugging.

### Configuration

1.  **Clone the Repository:**
    ```bash
    git clone [git@github.com:Hack-Roll/Backend.git]
    cd backend
    ```

2.  **Configure PostgreSQL:**
    * Create a database for the application (e.g., `hack_roll`).
    * Update the database connection properties in the `src/main/resources/application.properties` file. Replace the placeholders with your PostgreSQL configuration:

        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/hack_roll
        spring.datasource.username=your_username
        spring.datasource.password=your_password
        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
        ```

        **Note:** `spring.jpa.hibernate.ddl-auto=update` will automatically update the database schema based on your JPA entities. For production environments, consider using a database migration tool. Replace `your_postgres_username` and `your_postgres_password` with your PostgreSQL credentials.

3.  **Build the Application:**
    Navigate to the root directory of the `backend` project in your terminal and run the Maven build command:

    ```bash
    mvn clean install
    ```

    This command will download the necessary dependencies, compile the code, and run the tests.

### Running the Application

1.  Clone the project repository from GitHub.
2.  Open the project in Visual Studio Code (or your IDE).
3.  Ensure that the `.env` file is at the root of the project. The provided code (`YesterdaysnewsApplication.java`) loads these variables when the application starts.
4.  Open a terminal in the root of the project.
5.  Execute the Maven command to build and run the application:

```bash
mvn spring-boot:run
```

## 👥 Authors

* **Kat Leverton:**
    * GitHub: [Kat-lev](https://github.com/Kat-lev/)
    * LinkedIn: [Kat Leverton](https://www.linkedin.com/in/kat-leverton/)

* **Mariuxi Olaya Ruiz:**
    * GitHub: [catmaluci](https://github.com/catmaluci/)
    * LinkedIn: [Mariuxi Olaya Ruiz](https://www.linkedin.com/in/molaya)