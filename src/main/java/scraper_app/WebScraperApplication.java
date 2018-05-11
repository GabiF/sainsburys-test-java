package scraper_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import scraper_app.controllers.MainController;

@SpringBootApplication
public class WebScraperApplication
{   
    // Default URL link
    private static final String DEFAULT_URL = "https://jsainsburyplc.github.io/serverside-test/site/"
    		+ "www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
    
    // Console user input request message
    private static final String CONSOLE_MESSAGE = "\nPlease provide a valid link to the webpage to scrape.\n"
    		+ "Alternatively, press enter and the default link will be used.";
    
    @Autowired
    private MainController mainController;
	
	public static void main(String[] args) throws IOException
	{
		final ConfigurableApplicationContext context = SpringApplication.run(WebScraperApplication.class);

        final WebScraperApplication webScraperApp = context.getBean(WebScraperApplication.class);

        webScraperApp.scrape(args);
        
        // close the application
        SpringApplication.exit(context);
	}
		
	private void scrape(final String[] args) throws IOException
	{
		System.out.printf("\nApplication started with %d arguments.\n", args.length);
		
		if (args.length > 0)
		{
			for (String arg : args)
			{
				if (isValidUri(arg))
				{
					// scrape the webpage from this link
					mainController.scrapeWebpageFromUri(getUriFromInput(arg));
				}
			}
		}
		else
		{
			promptForConsoleInput();
		}
	}
	
	private void promptForConsoleInput() throws IOException
	{
		String userInput = ConsoleReader.requestUserInput(CONSOLE_MESSAGE);
		
		if (userInput.isEmpty())
		{
			// scrape the webpage from the default link
			mainController.scrapeWebpageFromUri(getUriFromInput(DEFAULT_URL));
		}
		else if (isValidUri(userInput))
		{
			// scrape the webpage from the given link
			mainController.scrapeWebpageFromUri(getUriFromInput(userInput));
		}
		else
		{
			System.out.println("\nCould not parse the given URL: " + userInput + "\n");
		}
	}
	
	private boolean isValidUri(String input)
	{
		return new UrlValidator().isValid(input);
	}
	
	private URI getUriFromInput(String input)
	{
		return UriComponentsBuilder.fromHttpUrl(input).build().toUri();
	}
	
	public static class ConsoleReader 
	{
		public static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
		
		public static String requestUserInput(final String message) throws IOException {
            System.out.println(message);
            return READER.readLine();
        }
	}
}
