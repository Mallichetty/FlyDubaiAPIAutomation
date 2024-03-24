package services.helper;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlightAvailabilityHelper
{
    public String generateAvailabilityFlightRequest()
    {
        JSONParser parser = new JSONParser();
        JSONObject reqBody = new JSONObject();
        JSONArray searchCriteria = new JSONArray();
        JSONObject searchArray = new JSONObject();
        JSONObject paxArray = new JSONObject();

        reqBody.put("journeyType","ow");
        reqBody.put("cabinClass","Economy");
        reqBody.put("isOriginMetro",true);
        reqBody.put("isDestMetro",false);

        searchArray.put("origin","DXB");
        searchArray.put("dest","MCT");
        searchArray.put("originDesc","");
        searchArray.put("destDesc","");
        searchArray.put("isOriginMetro",true);
        searchArray.put("isDestMetro",false);
        searchArray.put("direction","outBound");
        searchArray.put("date","5/10/2024 12:00 AM");
        searchCriteria.add(searchArray);
        reqBody.put("searchCriteria",searchCriteria);
        paxArray.put("adultCount",2);
        paxArray.put("infantCount",0);
        paxArray.put("childCount",0);
        reqBody.put("paxInfo",paxArray);
        return reqBody.toJSONString();
    }

    public String token(String responseHeaders) {

        String pattern = "\\b([^\\s]+)=([^\\s]+)\\b";
        Map<String, String> map = new HashMap<>();
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher (responseHeaders);
        while (m.find())
        {
            System.out.println("Found a key/value: (" + m.group(1) + "," + m.group(2) + ")"); map.put(m.group (1), m.group(2));
        }
        String token = map.get("securityToken");
        String newToken=token.substring(1,token.length());
        return newToken;
    }
}
