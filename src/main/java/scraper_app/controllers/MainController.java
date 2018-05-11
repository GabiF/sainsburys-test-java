package scraper_app.controllers;

import java.net.URI;

import org.springframework.stereotype.Component;

@Component
public class MainController
{
    public void scrapeWebpageFromUri(final URI inputUri)
    {
    	System.out.println("Scraping webpage from link: " + inputUri + "\n");
    }
}
