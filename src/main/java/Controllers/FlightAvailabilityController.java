package Controllers;

import org.json.simple.JSONObject;
import org.junit.Assert;
import services.constant.AppEndPoints;
import services.context.Context;
import services.context.TestContext;
import services.helper.FlightAvailabilityHelper;
import services.logger.Log;
import services.util.HeaderManager;
import services.util.RestAPIClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FlightAvailabilityController extends FlightAvailabilityHelper {

    HeaderManager headerManager;
    RestAPIClient restAPIClient;
    String hostName;
    Map<String, String> response;
    Properties config;
    TestContext testContext;

    public FlightAvailabilityController(TestContext context)
    {
        testContext =context;
        restAPIClient = new RestAPIClient();
        response = new HashMap<String, String>();
    }

    public Map<String, String> getFlightAvailabilityStatus() {
        try {
            headerManager = new HeaderManager.Builder().withContentType("application/json").build();
            String reqBody = generateAvailabilityFlightRequest();
            testContext.getScenarioContext().setContext(Context.AVAILABILITY_REQUEST_BODY,reqBody);
            response = restAPIClient.invokeAPI(AppEndPoints.HOST_NAME + AppEndPoints.AVAILABILITY_ENDPOINT,
                    headerManager.getHeader(), "POST", reqBody);
            Assert.assertEquals( "200",response.get("responseCode"));
        } catch (Exception e) {
            Log.warn(e.getMessage());
        }
        return response;
    }
}