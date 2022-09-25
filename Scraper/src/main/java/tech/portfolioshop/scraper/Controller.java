package tech.portfolioshop.scraper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.portfolioshop.scraper.scrappers.jobscrappers.NaukariScrapper;

@RestController
@RequestMapping("/scraper")
public class Controller {
    @GetMapping("/test")
    public String test(){
        NaukariScrapper naukariScrapper = new NaukariScrapper("java developer", "bangalore");
        naukariScrapper.scrape();
        return "test";
    }
}
