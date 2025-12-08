package com.sysnormal.libs.utils.network.http.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysnormal.libs.commons.DefaultDataSwap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;

/**
 * Response utils
 *
 * @author Alencar
 * @version 1.0.0
 */
public class ResponseUtils {

    private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static DefaultDataSwap handleResponse(ClientRawResponseWrapper response){
        logger.debug("INIT {}.{}",ResponseUtils.class.getSimpleName(), "handleResponse");
        DefaultDataSwap result = new DefaultDataSwap();
        try {
            HttpStatusCode statusCode = response.clientResponse.statusCode();
            result.httpStatusCode = statusCode.value();
            logger.debug("response status {}",result.httpStatusCode);
            result.success = statusCode.is2xxSuccessful();

            try {
                JsonNode jsonResponse = objectMapper.readTree(response.rawResponse);
                result.success = jsonResponse.get("success").asBoolean();
                //result.httpStatusCode = (HttpStatus) jsonResponse.get("httpStatus");
                result.data = jsonResponse.get("data");
                result.message = jsonResponse.get("message").asText();
                //result.exception = jsonResponse.get("exceeption");
            } catch (Exception e) {
                result.data = response.rawResponse;
            }
            logger.debug("response {}",result.data);
        } catch (Exception e) {
            e.printStackTrace();
            result.setException(e);
        }
        logger.debug("END {}.{}",ResponseUtils.class.getSimpleName(), "handleResponse");
        return result;
    }
}
