# Farmers Hub App

An app that allows farmers to connect with customers. Farmers can upload Produce or Machine Listings to the App. Customers can browse through the lisings and buy items directly from farmers.

## Run Locally

Clone the project

```bash
  git clone https://github.com/JaideepSingh-code/Farmers-Hub.git
```

Go to the project directory

```bash
  cd Farmers-Hub
```

Build the Project

```bash
  ./gradlew build
```

Run the Project

```bash
  ./gradlew run
```

## Setting up Database

You can either use the Mock database or the Local Postgres Database.

To set up the database, go to the DB config.java file at the following location:

```bash
<your_project_dir>/app/src/main/java/statics/DbConfig.java
```

To use Mock Database, set IS_MOCK boolean variable to true.
![App Screenshot](https://github.com/JaideepSingh-code/Farmers-Hub/blob/main/app/src/main/resources/images/MockDatabase.png?raw=true)

To use Local Postgres Database, set IS_MOCK boolean variable to false.
![App Screenshot](https://github.com/JaideepSingh-code/Farmers-Hub/blob/main/app/src/main/resources/images/LocalDatabase.png?raw=true)
Set the name of your local database to farmerhub.<br>
Set DB_USER to the username for your localhost postgres database.<br>
Set DB_PASSWORD to the password for your localhost postgres database.

## Running Tests

To run Junit tests, run the following command

```bash
  ./gradlew test
```

Test Results for Junit tests are available in the following directory:

```bash
<your_project_dir>/app/my-reports/tests/test/index.html
```

Open the index.html in a web browser to see Junit Test Results.

To run Integration test, run the following command

```bash
  ./gradlew integrationTest
```

Test Results for Integration tests are available in the following directory:

```bash
<your_project_dir>/app/my-reports/tests/integrationTest/index.html
```

## Debugging

To debug the app, set up a new debug configuration in Eclipse IDE as shown in the image below.
![App Screenshot](https://github.com/JaideepSingh-code/Farmers-Hub/blob/main/app/src/main/resources/images/DebugConfiguration.png?raw=true)

Put a breakpoint in the code and then run the gradle debug task.

```bash
    ./gradlew debug
```

## Project Structure

```
Farmers-Hub/
├── app/
│   ├── build.gradle                         # Gradle build config
│   └── src/
│       ├── main/java/
│       │   ├── controllers/
│       │   │   ├── ItemController.java      # Item CRUD operations
│       │   │   ├── LoginController.java     # Authentication logic
│       │   │   ├── OrderController.java     # Order processing
│       │   │   ├── RegistrationController.java
│       │   │   └── ReviewController.java    # Rating & review handling
│       │   ├── models/
│       │   │   ├── Customer.java            # Customer entity
│       │   │   ├── Farmer.java              # Farmer entity
│       │   │   ├── Item.java                # Base item class
│       │   │   ├── Machine.java             # Machine listing
│       │   │   ├── Produce.java             # Produce listing
│       │   │   ├── User.java                # Base user class
│       │   │   ├── OrderItem.java           # Order line items
│       │   │   ├── RatingAndReview.java     # Review entity
│       │   │   └── composite_responses/
│       │   │       └── ItemWithReviews.java # Item + reviews DTO
│       │   ├── repositories/
│       │   │   ├── itemRepository/          # Interface + Mock + Postgres impl
│       │   │   ├── orderItemRepository/     # Interface + Mock + Postgres impl
│       │   │   ├── reviewRepository/        # Interface + Mock + Postgres impl
│       │   │   └── userRepository/          # Interface + Mock + Postgres impl
│       │   ├── services/
│       │   │   ├── ItemService.java         # Item business logic
│       │   │   ├── OrderService.java        # Order business logic
│       │   │   ├── RatingAndReviewService.java
│       │   │   └── UserService.java         # User business logic
│       │   ├── statics/
│       │   │   ├── DbConfig.java            # DB connection config
│       │   │   ├── ItemStatics.java         # Item constants
│       │   │   └── UserRoles.java           # Role definitions
│       │   ├── utils/
│       │   │   ├── AlertUtils.java          # UI alert helpers
│       │   │   └── ValidationUtils.java     # Input validation
│       │   ├── views/
│       │   │   ├── LoginView.java           # Login UI
│       │   │   ├── RegistrationPageView.java
│       │   │   └── UpdateProfilePage.java   # Profile management UI
│       │   └── team1/project/
│       │       └── ApplicationRunner.java   # Application entry point
│       ├── intTest/java/app/
│       │   └── UserServiceIntegTest.java    # Integration tests
│       └── test/                            # Unit tests
├── gradle/wrapper/                          # Gradle wrapper
├── gradlew, gradlew.bat                     # Build scripts
├── settings.gradle
└── README.md
```

## Screenshots

### Login and Registration Page

![App Screenshot](https://github.com/JaideepSingh-code/Farmers-Hub/blob/main/app/src/main/resources/images/LoginAndRegistrationPage.png?raw=true)

### Farmer Landing Page and Customer Landing Page

![App Screenshot](https://github.com/JaideepSingh-code/Farmers-Hub/blob/main/app/src/main/resources/images/FarmerAndCustomerPage.png?raw=true)

### Junit Test Report (generated by Gradle)

![App Screenshot](https://github.com/JaideepSingh-code/Farmers-Hub/blob/main/app/src/main/resources/images/JunitTestsReport.png?raw=true)

### Integration Test Report (generated by Gradle)

![App Screenshot](https://github.com/JaideepSingh-code/Farmers-Hub/blob/main/app/src/main/resources/images/IntegrationTestsReport.png?raw=true)
