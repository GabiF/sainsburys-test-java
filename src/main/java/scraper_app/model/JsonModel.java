package scraper_app.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = { JsonModel.RESULTS, JsonModel.TOTAL })
public class JsonModel
{
	protected static final String RESULTS = "results";
    protected static final String TOTAL = "total";
    
    @NotNull
    @NotEmpty
    @JsonProperty(RESULTS)
    private List<ScrapedObjectModel> results;

    /** The total. */
    @NotNull
    @JsonProperty(TOTAL)
    private Double total;

	public List<ScrapedObjectModel> getResults() {
		return results;
	}

	public void setResults(List<ScrapedObjectModel> results) {
		this.results = results;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
