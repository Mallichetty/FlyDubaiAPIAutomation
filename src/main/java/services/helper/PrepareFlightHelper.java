package services.helper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.context.Context;
import services.context.TestContext;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrepareFlightHelper
{
    TestContext testContext;


    public String getPrepareFlightRequestBody(String searchRequest, String selectFlights) throws ParseException {
        JSONObject reqBody = new JSONObject();
        JSONObject pref = new JSONObject();
        JSONArray array = new JSONArray();
        JSONParser parser= new JSONParser();
        reqBody.put("currency","");
        reqBody.put("searchRequest",parser.parse(searchRequest));
        reqBody.put("selectedFlights",parser.parse(selectFlights));
        reqBody.put("passengerList",array);
        pref.put("isReadyToSignUpForOffers","true");
        reqBody.put("preferences",pref);
        return reqBody.toJSONString();
    }

}
