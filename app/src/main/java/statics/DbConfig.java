package statics;

public class DbConfig {

    // Local Postgres database on port 5432 and name of database is farmerhub
    public static final String DB_CONNECTION_STRING = "jdbc:postgresql://localhost:5432/farmerhub";
    // Set DB_USER to the username used for local database
    public static final String DB_USER = "user";
    // Set DB_PASSWORD to the password used for local database
    public static final String DB_PASSWORD = "password";
    // To use local database, set IS_MOCK to false
    // To use Mock database, set IS_MOCK to true
    public static final boolean IS_MOCK = true;
}
