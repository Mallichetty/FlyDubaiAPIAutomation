package services.helper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.context.Context;
import services.context.TestContext;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrepareFlightHelper
{
    TestContext testContext;


    public String getPrepareFlightRequestBody(String searchRequest, String selectFlights)
    {
        JSONObject reqBody = new JSONObject();
        JSONObject pref = new JSONObject();
        JSONArray array = new JSONArray();
        reqBody.put("currency","AED");
        reqBody.put("itineraryAction",1);
        reqBody.put("searchRequest",searchRequest);
        reqBody.put("selectedFlights",selectFlights);
        reqBody.put("passengerList",array);
        pref.put("isReadyToSignUpForOffers",false);
        reqBody.put("preferences",pref);
        return reqBody.toJSONString();
    }

}
