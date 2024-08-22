# Farmers Hub App

An app that allows farmers to connect with customers. Farmers can upload Produce or Machine Listings to the App. Customers can browse through the listings and buy items directly from farmers.

## Run Locally

Clone the project

```
git clone https://github.com/JaideepSingh-code/Farmers-Hub.git
```

Go to the project directory

```
cd Farmers-Hub
```

Build the Project

```
./gradlew build
```

Run the Project

```
./gradlew run
```

## Setting up Database

You can either use the Mock database or the Local Postgres Database.

To set up the database, go to the DB config.java file at the following location:

```
<your_project_dir>/app/src/main/java/statics/DbConfig.java
```

To use Mock Database, set IS_MOCK boolean variable to true.

To use Local Postgres Database, set IS_MOCK boolean variable to false.
Set the name of your local database to farmerhub.
Set DB_USER to the username for your localhost postgres database.
Set DB_PASSWORD to the password for your localhost postgres database.

## Running Tests

To run Junit tests, run the following command

```
./gradlew test
```

Test Results for Junit tests are available in the following directory:

```
<your_project_dir>/app/my-reports/tests/test/index.html
```

To run Integration test, run the following command

```
./gradlew integrationTest
```

## Debugging

To debug the app, set up a new debug configuration in Eclipse IDE.
Put a breakpoint in the code and then run the gradle debug task.

```
./gradlew debug
```

## Screenshots

### Login and Registration Page
Users can register as either a Farmer or Customer and login with their credentials.

### Farmer Landing Page
Farmers can view their uploaded listings, upload new produce or machine items, and view sales history.

### Customer Landing Page
Customers can browse available produce and machines, add items to cart, place orders, and leave reviews.

## Tech Stack

- **Language:** Java
- **UI Framework:** JavaFX 21
- **Build Tool:** Gradle 7.6
- **Database:** PostgreSQL (with Mock DB option)
- **Testing:** JUnit 5
- **Styling:** BootstrapFX
