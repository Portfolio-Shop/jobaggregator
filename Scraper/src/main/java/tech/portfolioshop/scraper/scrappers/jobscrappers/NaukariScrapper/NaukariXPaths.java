package tech.portfolioshop.scraper.scrappers.jobscrappers.NaukariScrapper;

public enum NaukariXPaths {
    ABOUTCOMPANYSELECTOR("/html/body/div[1]/main/div[2]/div[2]/section[3]"),
    EMPLOYER("/html/body/div[1]/main/div[2]/div[2]/section[1]/div[1]/div[1]/div/a[1]"),
    EXPERIENCE("/html/body/div[1]/main/div[2]/div[2]/section[1]/div[1]/div[2]/div[1]/span"),
    JOBDESCSELECTOR("/html/body/div[1]/main/div[2]/div[2]/section[2]"),
    JOBLIST("/html/body/div[1]/div[4]/div[1]/section[2]/div[2]"),
    JOBLOCATION("/html/body/div[1]/main/div[2]/div[2]/section[1]/div[1]/div[2]/div[3]/span/a"),
    SALARY("/html/body/div[1]/main/div[2]/div[2]/section[1]/div[1]/div[2]/div[2]/span"),
    SKILLSELECTOR("/html/body/div[1]/main/div[2]/div[2]/section[2]/div[4]/div[3]"),
    TITLE("/html/body/div[1]/main/div[2]/div[2]/section[1]/div[1]/div[1]/header/h1");
    private String value;

    NaukariXPaths(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
