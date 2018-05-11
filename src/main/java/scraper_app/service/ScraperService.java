package scraper_app.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import scraper_app.model.JsonModel;
import scraper_app.model.ScrapedObjectModel;

@Service
public class ScraperService
{
	private static final String DOMAIN_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/";
	private static final String LINK_SANITISING_PATTERN = "(../)+";
	
	private static final String OBJECT_SELECTOR = "ul.productLister a";
	private static final String OBJECT_HREF_SELECTOR = "href";
	
	public JsonModel getJsonModelFromUri(URI webpageUri) throws MalformedURLException, IOException
	{
		Document webpage = Jsoup.connect(webpageUri.toURL().toString()).get();
		
		Set<String> allScrapedObjectsLinks = webpage.select(OBJECT_SELECTOR).stream()
			.map(elem -> elem.attr(OBJECT_HREF_SELECTOR))
			.map(elem -> DOMAIN_URL + elem.replaceFirst(LINK_SANITISING_PATTERN, ""))
			.filter(elem -> elem.contains("berries-cherries-currants"))
			.collect(Collectors.toSet());
		
		// -- DEBUG
		System.out.println("Found the following links:");
	
//		Set<String> sanitisedLinks = allScrapedObjectsLinks.stream()
//				.map(elem -> DOMAIN_URL + elem.replaceFirst(LINK_SANITISING_PATTERN, ""))
//				.collect(Collectors.toSet());
		
		for (String link : allScrapedObjectsLinks)
		{
			System.out.println(link);
		}
	
		// call the method to parse all found links and return the JsonModel object	
		return parseAllScrapedObjectsLinks(allScrapedObjectsLinks);
	}
	
	private JsonModel parseAllScrapedObjectsLinks(final Set<String> allScrapedObjectsLinks) throws IOException
	{
		JsonModel jsonObject = new JsonModel();
		List<ScrapedObjectModel> scrapedObjects = new ArrayList<>(allScrapedObjectsLinks.size());
		Double total = new Double(0);
		
		for (String link : allScrapedObjectsLinks)
		{		
			Document productPage = Jsoup.connect(link).get();
			
			ScrapedObjectModel scrapedObject = new ScrapedObjectModel();
			scrapedObject.setTitle(ScraperServiceHelper.getScrapedObjectTitle(link, productPage));
			scrapedObject.setKcalPerHundredGrams(ScraperServiceHelper.getScrapedObjectKcal(link, productPage));
			scrapedObject.setUnitPrice(ScraperServiceHelper.getScrapedObjectUnitPrice(link, productPage));
			scrapedObject.setDescription(ScraperServiceHelper.getScrapedObjectDescription(link, productPage));
				
			scrapedObjects.add(scrapedObject);
			if (scrapedObject.getUnitPrice() != null)
			{
				total += scrapedObject.getUnitPrice();
			}
		}
		
		jsonObject.setResults(scrapedObjects);
		jsonObject.setTotal(total);
		
		return jsonObject;
	}
}
