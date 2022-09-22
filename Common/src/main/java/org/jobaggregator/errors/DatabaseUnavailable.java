package org.jobaggregator.errors;

public class DatabaseUnavailable extends ServiceUnavailableError{
    public DatabaseUnavailable() {
        super("Database");
    }
}
