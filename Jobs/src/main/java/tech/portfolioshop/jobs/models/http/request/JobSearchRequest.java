package tech.portfolioshop.jobs.models.http.request;

import javax.validation.constraints.NotNull;

public class JobSearchRequest {
    @NotNull(message = "Location is required")
    private String location;
    @NotNull(message = "Search query is required")
    private String query;
}
