package tech.portfolioshop.scraper.scrappers;

public abstract class JobScrapper {
    private final String DRIVER_PATH;
    protected JobScrapper(String driver_path) {
        DRIVER_PATH = driver_path;
    }
}
