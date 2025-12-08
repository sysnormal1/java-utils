package com.sysnormal.libs.utils.network.http.request;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Request utils
 *
 * @author Alencar
 * @version 1.0.0
 */
public class RequestUtils {

    public static void setUriBuilderPropertyOfMapIfExists(Map<String, Object> props, UriComponentsBuilder builder, String propName) {
        Object propValue = props.getOrDefault(propName,null);
        if (propValue != null) {
            builder.queryParam(propName,propValue);
        }
    }
}
