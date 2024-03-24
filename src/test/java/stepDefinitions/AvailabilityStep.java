package stepDefinitions;
import Controllers.FlightAvailabilityController;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import services.context.Context;
import services.context.ScenarioContext;
import services.context.TestContext;
import services.logger.Log;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AvailabilityStep {

    FlightAvailabilityController flightAvailabilityController;
    Map<String,String> response;
    TestContext testContext;
    ScenarioContext scenarioContext;
    public AvailabilityStep(TestContext context)
    {
        testContext = context;
        flightAvailabilityController = new FlightAvailabilityController(context);
        response = new HashMap<String, String>();
    }

    @When("user hits the availability api for checking the flights")
    public void userHitsTheAvailabilityApiForCheckingTheFlights() throws ParseException {
        response=flightAvailabilityController.getFlightAvailabilityStatus();
        Log.info(testContext.getScenarioContext().getContext(Context.AVAILABILITY_REQUEST_BODY).toString());
        //Log.info(response.get("responseHeader"));

    }

    @Then("user verifies the statuscode")
    public void userVerifiesTheStatuscode() {
        Assert.assertEquals("200",response.get("responseCode"));
    }

}
