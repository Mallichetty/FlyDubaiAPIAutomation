package Controllers;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import services.constant.AppEndPoints;
import services.context.TestContext;
import services.helper.PrepareFlightHelper;
import services.util.HeaderManager;
import services.util.RestAPIClient;


import java.util.HashMap;
import java.util.Map;

public class PrepareFlightController extends PrepareFlightHelper
{

    RestAPIClient apiClient;
    HeaderManager headerManager;
    Map<String, String> response;

    TestContext testContext;
    RestAPIClient restAPIClient;

    public PrepareFlightController(TestContext context)
    {
        testContext =context;
        restAPIClient = new RestAPIClient();
        response = new HashMap<String, String>();
        PrepareFlightController prepareFlightController;
    }

    public Map<String, String> getPrepareFlightAPI(String token, String searchRequest, String selectedFlight) throws ParseException {
        headerManager = new HeaderManager.Builder()
                .withContentType("application/json")
                .withAppId("Desktop")
                .withSecurityToken(token)
                .build();
        String requestBody= getPrepareFlightRequestBody(searchRequest, selectedFlight);
//        System.out.println(requestBody);
        response = restAPIClient.invokeAPI(AppEndPoints.HOST_NAME+ AppEndPoints.ADDFLIGHT_ENDPOINT,
                headerManager.getHeader(),"POST",requestBody);
        Assert.assertEquals("200",response.get("responseCode"));
        return response;
    }
}
