package stepDefinitions;
import Controllers.FlightAvailabilityController;
import Controllers.PrepareFlightController;
import io.cucumber.java.en.And;
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
import services.helper.FlightAvailabilityHelper;
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
    FlightAvailabilityHelper flightAvailabilityHelper;
    PrepareFlightController prepareFlightController;
    public AvailabilityStep(TestContext context)
    {
        testContext = context;
        flightAvailabilityController = new FlightAvailabilityController(context);
        response = new HashMap<String, String>();
        prepareFlightController = new PrepareFlightController(context);
        flightAvailabilityHelper = new FlightAvailabilityHelper();
    }

    @When("user hits the availability api for checking the flights")
    public void userHitsTheAvailabilityApiForCheckingTheFlights() throws ParseException {
        response=flightAvailabilityController.getFlightAvailabilityStatus();
        Log.info(testContext.getScenarioContext().getContext(Context.AVAILABILITY_REQUEST_BODY).toString());
        JSONParser parser = new JSONParser();
        JSONObject object= (JSONObject) parser.parse(response.get("responseMessage"));
        JSONArray array  = (JSONArray) object.get("segments");
        JSONObject object1 = (JSONObject) array.get(0);
        testContext.getScenarioContext().setContext(Context.FlIGHTS_DETAILS,object1.get("flights").toString());
        testContext.getScenarioContext().setContext(Context.SECURITY_TOKEN,flightAvailabilityHelper.token(response.get("responseHeader")));
    }

    @Then("user verifies the statuscode")
    public void userVerifiesTheStatuscode() {
        Assert.assertEquals("200",response.get("responseCode"));
    }

    @And("user hits the Prepare api to get the validation rules")
    public void userHitsThePrepareApiToGetTheValidationRules()
    {
        response=prepareFlightController.getPrepareFlightAPI(testContext.getScenarioContext().getContext(Context.SECURITY_TOKEN).toString(),testContext.getScenarioContext().getContext(Context.AVAILABILITY_REQUEST_BODY).toString(),
                testContext.getScenarioContext().getContext(Context.FlIGHTS_DETAILS).toString());
        Log.info(response.get("responseMessage"));
    }
}
