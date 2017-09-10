package com.yogesh.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ClientCreationUtil {
    private static ClientConfig config;

    private static Client client = ClientBuilder.newClient(getJacksonConfig());


    public static Client createClient() {
        return client;
    }

    private ClientCreationUtil() {
    }


    private static ClientConfig getJacksonConfig() {
        config = new ClientConfig();
        return config.register(JacksonJsonProvider.class);
    }
}
