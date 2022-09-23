package org.jobaggregator.errors;

public class DatabaseUnavailable extends ServiceUnavailableException {
    public DatabaseUnavailable() {
        super("Database", "Database Unavailable Error");
    }
}
