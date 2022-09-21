import io.github.bonigarcia.wdm.webdrivermanager;


public class Scraper {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.firefox.driver","/usr/local/bin/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        WebDriver driver = new FirefoxDriver(options);
        driver.get("https://www.google.com");
        driver.quit();
    }
}