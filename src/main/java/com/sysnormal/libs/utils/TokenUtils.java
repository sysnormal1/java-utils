package com.sysnormal.libs.utils;

import com.sysnormal.libs.utils.network.http.response.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class TokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Long getExpiration(String token) {
        logger.debug("INIT {}.{}",TokenUtils.class.getSimpleName(), "getExpiration");
        logger.debug("token: {}", token);
        if (!TextUtils.hasNotNullText(token)) return null;
        String[] parts = token.split("\\.");
        logger.debug("parts: {}", parts);
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        logger.debug("payload: {}", payload);
        // parse JSON
        Map<String, Object> payloadMap = objectMapper.readValue(payload, Map.class);
        logger.debug("payloadMap: {}", payloadMap);
        // pega o exp
        Object expObj = payloadMap.get("exp");
        if (expObj == null) {
            logger.warn("JWT sem claim 'exp'");
            return null;
        }
        logger.debug("END {}.{}",TokenUtils.class.getSimpleName(), "getExpiration");
        return ((Number) expObj).longValue();

    }
}
