Sainsbury's Java developer technical test - May 2018

The project uses Java 1.8, Maven for managing project dependencies and Jsoup for scraping webpages.

You should be able to run the project after a simple "git clone" operation.
If your IDE does not recognise the project as a Maven project immediately, you can remove it and import it as an existing Maven project.
Preferably, a Maven update should be performed as well.

Afterwards, you can run the project as a Java Application in the IDE, with or without arguments.
If no arguments are specified, the user will be prompted for input in the console.
You can simply hit ENTER and the application will run with the default link provided:

https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html

NOTES:
- The kcal_per_100g information is missing as it is not scraped in the current stage of the project
- There is potentially a bug when scraping a multi-line description for a product's page
- No tests included at this point.