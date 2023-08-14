package account.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseSuccessMessage {

    public static Map<String, String> getResponseSuccessMessage(String successMessage) {
        Map<String, String> response = new HashMap<>();
        response.put("status", successMessage);

        return response;
    }
}
