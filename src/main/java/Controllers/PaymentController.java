package Controllers;

import org.junit.Assert;
import services.constant.AppEndPoints;
import services.context.TestContext;
import services.helper.PaymentHelper;
import services.util.HeaderManager;
import services.util.RestAPIClient;

import java.util.HashMap;
import java.util.Map;

public class PaymentController extends PaymentHelper
{
    HeaderManager headerManager;
    Map<String, String> response;

    TestContext testContext;
    RestAPIClient restAPIClient;
    public PaymentController(TestContext context)
    {
        testContext =context;
        restAPIClient = new RestAPIClient();
        response = new HashMap<String, String>();
    }
    public Map<String, String> getPaymentAPI(String token, String searchRequest, String selectedFlight)
    {
        headerManager = new HeaderManager.Builder()
                .withContentType("application/json")
                .withAppId("Desktop")
                .withSecurityToken(token)
                .build();
        String requestBody= getPaymentRequestBody(searchRequest, selectedFlight);
        response = restAPIClient.invokeAPI(AppEndPoints.HOST_NAME+ AppEndPoints.PAYMENT_ENDPOINT,
                headerManager.getHeader(),"POST",requestBody);
        Assert.assertEquals("200",response.get("responseCode"));
        return response;
    }

}
