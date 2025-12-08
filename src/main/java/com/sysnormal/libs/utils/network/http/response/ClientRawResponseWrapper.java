package com.sysnormal.libs.utils.network.http.response;

import org.springframework.web.reactive.function.client.ClientResponse;

/**
 * class to use in lambda of exangeToMono(webflux client) to get simultanely reponse and body to reuse after consumed response
 * @author Alencar
 * @version 1.0.0
 */
public class ClientRawResponseWrapper {
    public final ClientResponse clientResponse;
    public final String rawResponse;

    public ClientRawResponseWrapper(ClientResponse clientResponse, String rawResponse) {
        this.clientResponse = clientResponse;
        this.rawResponse = rawResponse;
    }
}
