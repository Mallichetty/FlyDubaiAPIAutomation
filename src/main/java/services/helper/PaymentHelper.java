package services.helper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PaymentHelper {

    public String getPaymentRequestBody(String searchRequest, String selectFlights) {
        JSONObject reqBody = new JSONObject();
        JSONObject pref = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject pasList = new JSONObject();
        reqBody.put("currency", "AED");
        reqBody.put("itineraryAction", "3");
        reqBody.put("searchRequest", searchRequest);
        reqBody.put("selectedFlights", selectFlights);
        pasList.put("passengerId","-1");
        pasList.put("passengerType","Adult");
        pasList.put("title","Mr");
        pasList.put("firstName","Test");
        pasList.put("lastName","Test");
        pasList.put("middleName","n");
        pasList.put("dob",null);
        pasList.put("emailAddress","test@test.com");
        pasList.put("nationality","");
        pasList.put("documentNumber",null);
        pasList.put("documentExpiryDate",null);
        pasList.put("passportIssuingCountry","");
        pasList.put("contactMobileContryCode","961");
        pasList.put("contactMobileNumber","98763252");
        pasList.put("accompanyingAdult",null);
        pasList.put("memberId",null);
        pasList.put("countryOfResidence","null");
        pasList.put("contactMobileNumberField",null);
        pasList.put("contactTelephoneContryCode",null);
        pasList.put("contactTelephoneNumber",null);
        pasList.put("contactTelephoneField",null);
        pasList.put("tierId",null);
        pasList.put("tierName",null);
        pasList.put("deleteEnabled",null);
        pasList.put("isMinor",null);
        pasList.put("ffpEnabled",null);
        pasList.put("tierInfo",null);
        array.add(pasList);
        reqBody.put("passengerList", array);
        pref.put("isReadyToSignUpForOffers", false);
        reqBody.put("preferences", pref);
        reqBody.put("confirmUrl","https://qa1-flights2.np.flydubai.com/api/itinerary/confirm");
        reqBody.put("locale","en");

        return reqBody.toJSONString();
    }
}
