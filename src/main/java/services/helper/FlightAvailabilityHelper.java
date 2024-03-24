package services.helper;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
}
