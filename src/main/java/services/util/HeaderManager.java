package services.util;

import java.util.HashMap;
import java.util.Map;

public class HeaderManager {

    private Map<String, String> headerMap = new HashMap<String, String>();

    private HeaderManager(Map<String, String> builderMap)
    {
        headerMap = builderMap;
    }

    public Map<String, String> getHeader()
    {
        return headerMap;
    }

    public static class Builder
    {
        private Map<String,String> builderMap= new HashMap<>();

        public HeaderManager build()
        {
            HeaderManager headerCreator = new HeaderManager(builderMap);
            return headerCreator;
        }

        public Builder withContentType(String contentType)
        {
            builderMap.put("Content-Type",contentType);
            return this;
        }
        public Builder withAccept(String contentType)
        {
            builderMap.put("Accept",contentType);
            return this;
        }

        public Builder withSecurityToken(String token)
        {
            builderMap.put("securityToken",token);
            return this;
        }

    }

}
