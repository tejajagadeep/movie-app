//package com.spotify.apigateway;
//
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.RequestHandler;
//import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIResponse> {
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public APIResponse handleRequest(APIGatewayProxyRequestEvent input, Context context) {
//        // Assuming you want to call USERPROFILE-SERVICE
//        ResponseEntity<List> responseEntity = invokeService("USERPROFILE-SERVICE", "/api/v1.0/userProfile/**");
//
//        // Your existing response building logic
//        APIResponse response = buildResponse(context, responseEntity.getBody());
//
//        return response;
//    }
//
//    public APIResponse handleRequestAuth(APIGatewayProxyRequestEvent input, Context context) {
//        // Assuming you want to call USERPROFILE-SERVICE
//        ResponseEntity<List> responseEntity = invokeService("AUTHENTICATION-SERVICE", "/api/v1.0/auth");
//
//        // Your existing response building logic
//        APIResponse response = buildResponse(context, responseEntity.getBody());
//
//        return response;
//    }
//    private APIResponse buildResponse(Context context, List employeeList) {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("X-amazon-author", "Lipsa");
//        headers.put("X-amazon-apiVersion", "v1");
//
//        String body = null;
//        try {
//            body = new ObjectMapper().writeValueAsString(employeeList);
//        } catch (JsonProcessingException e) {
//            context.getLogger().log("Error:::" + e.getMessage());
//        }
//
//        APIResponse response = new APIResponse(body, 200, headers);
//        context.getLogger().log("APIResponse:::" + response);
//        return response;
//    }
//
//    private ResponseEntity<List> invokeService(String serviceId, String path) {
//        String serviceUrl = "http://" + serviceId + path;
//        return restTemplate.getForEntity(serviceUrl, List.class);
//    }
//}