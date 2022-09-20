package tech.portfolioshop.jobs.models.request;

import javax.validation.constraints.NotNull;

public class JobSearchRequest {
    @NotNull(message = "Location is required")
    private String location;
    @NotNull(message = "Search query is required")
    private String query;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
