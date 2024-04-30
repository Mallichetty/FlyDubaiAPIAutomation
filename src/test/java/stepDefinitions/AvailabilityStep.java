package stepDefinitions;

import Controllers.FlightAvailabilityController;
import Controllers.PaymentController;
import Controllers.PrepareFlightController;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import services.context.Context;
import services.context.ScenarioContext;
import services.context.TestContext;
import services.helper.FlightAvailabilityHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvailabilityStep {

    FlightAvailabilityController flightAvailabilityController;
    Map<String,String> response;
    TestContext testContext;
    ScenarioContext scenarioContext;
    FlightAvailabilityHelper flightAvailabilityHelper;
    PrepareFlightController prepareFlightController;
    PaymentController paymentController;

    public AvailabilityStep(TestContext context)
    {
        testContext = context;
        flightAvailabilityController = new FlightAvailabilityController(context);
        response = new HashMap<String, String>();
        prepareFlightController = new PrepareFlightController(context);
        flightAvailabilityHelper = new FlightAvailabilityHelper();
        paymentController = new PaymentController(context);
    }

    @When("user hits the availability api for checking the flights")
    public void userHitsTheAvailabilityApiForCheckingTheFlights() throws ParseException {
        response=flightAvailabilityController.getFlightAvailabilityStatus();
       // Log.info(testContext.getScenarioContext().getContext(Context.AVAILABILITY_REQUEST_BODY).toString());
        JSONParser parser = new JSONParser();
        JSONObject object= (JSONObject) parser.parse(response.get("responseMessage"));
        JSONObject lowestTotal = new JSONObject();
        lowestTotal.put("segments", object.get("segments"));
        lowestTotal.put("lowestTotalFare", object.get("lowestTotalFare"));
        // Find the lowest fare for the specified cabin
        JSONArray lowestTotalFare = (JSONArray) lowestTotal.get("lowestTotalFare");
        JSONObject lowest = null;
        for (Object obj : lowestTotalFare) {
            JSONObject fare = (JSONObject) obj;
            if (fare.get("cabin").toString().equalsIgnoreCase("economy")) {
                lowest = fare;
                break;
            }
        }
        // Log the lowest fare
//        System.out.println(lowest);
        List<JSONObject> retList = new ArrayList<>();
        JSONArray lowestFlights = (JSONArray) lowest.get("lowestFlights");
        JSONArray segments = (JSONArray) lowestTotal.get("segments");
        // Iterate over lowestFlights
        for (Object obj1 : lowestFlights) {
            JSONObject flight = (JSONObject) obj1;
            // Iterate over segments
            for (Object obj2 : segments) {
                JSONObject segment = (JSONObject) obj2;
                JSONArray flights = (JSONArray) segment.get("flights");
                JSONObject selectedLowestFlight = null;
                // Find the selected lowest flight
                for (Object obj3 : flights) {
                    JSONObject segmentFlight = (JSONObject) obj3;
                    if (segmentFlight.get("lfId").equals(flight.get("lfId"))) {
                        selectedLowestFlight = segmentFlight;
                        break;
                    }
                }
                if (selectedLowestFlight != null) {
                    JSONArray fareTypes = (JSONArray) selectedLowestFlight.get("fareTypes");
                    // Find and set the selected fare
                    for (Object obj4 : fareTypes) {
                        JSONObject fareType = (JSONObject) obj4;
                        if (fareType.get("fare") != null && ((JSONObject) fareType.get("fare")).get("solutionId").equals(flight.get("solutionId"))) {
                            selectedLowestFlight.put("selectedFare", fareType);
                            break;
                        }
                    }
                    retList.add(selectedLowestFlight);
                }
            }
        }
        testContext.getScenarioContext().setContext(Context.FlIGHTS_DETAILS,retList.toString());
        testContext.getScenarioContext().setContext(Context.SECURITY_TOKEN,flightAvailabilityHelper.token(response.get("responseHeader")));
    }

    @Then("user verifies the statuscode")
    public void userVerifiesTheStatuscode() {
        Assert.assertEquals("200",response.get("responseCode"));
    }

    @And("user hits the Prepare api to get the validation rules")
    public void userHitsThePrepareApiToGetTheValidationRules() throws ParseException {
        response=prepareFlightController.getPrepareFlightAPI(testContext.getScenarioContext().getContext(Context.SECURITY_TOKEN).toString(),testContext.getScenarioContext().getContext(Context.AVAILABILITY_REQUEST_BODY).toString(),
                testContext.getScenarioContext().getContext(Context.FlIGHTS_DETAILS).toString());
    }

    @Then("user hits the payment api to confirm the ticket booking")
    public void userHitsThePaymentApiToConfirmTheTicketBooking() {

        response=paymentController.getPaymentAPI(testContext.getScenarioContext().getContext(Context.SECURITY_TOKEN).toString(),testContext.getScenarioContext().getContext(Context.AVAILABILITY_REQUEST_BODY).toString(),
                testContext.getScenarioContext().getContext(Context.FlIGHTS_DETAILS).toString());
    }
}
