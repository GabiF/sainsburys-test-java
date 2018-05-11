package scraper_app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import scraper_app.model.JsonModel;
import scraper_app.service.ScraperService;

@Component
public class MainController
{
	@Autowired
	private ScraperService scraperService;
	
    public void scrapeWebpageFromUri(final URI inputUri)
    {
    	System.out.println("Scraping webpage from link: " + inputUri + "\n");
    	
    	try
    	{
    		JsonModel jsonObject = scraperService.getJsonModelFromUri(inputUri);
    	
    		System.out.println("\nThe resulting JSON object:\n");
    		
    		ObjectMapper objectMapper = new ObjectMapper();
    		
    		System.out.println(objectMapper.writeValueAsString(jsonObject));
    		
    		System.out.println("\nFinished scraping.\n");
    	}
    	catch (MalformedURLException e)
    	{
    		e.printStackTrace();
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    }
}
