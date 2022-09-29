package tech.portfolioshop.scraper.scrappers.jobscrappers;

public enum Websites {
    NAUKARI("https://www.naukri.com/"), INDEED("https://in.indeed.com/"), GLASSDOOR("https://www.glassdoor.co.in/");
    private final String baseUrl;

    Websites(String baseUrl){
        this.baseUrl = baseUrl;
    }
    public String getBaseUrl() {
        return baseUrl;
    }
}
