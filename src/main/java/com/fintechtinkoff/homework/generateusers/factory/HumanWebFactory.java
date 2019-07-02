package com.fintechtinkoff.homework.generateusers.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintechtinkoff.homework.generateusers.human.Human;
import com.fintechtinkoff.homework.generateusers.human.HumanApi;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HumanWebFactory implements IHumanFactory {
    @Override
    public Human createHuman() throws Exception {
        return createHumans(1).get(0);
    }

    @Override
    public List<Human> createHumans(int countHumans) throws Exception{
        URIBuilder uriBuilder = new URIBuilder("https://randomuser.me/api/");
        uriBuilder.addParameter("results", String.valueOf(countHumans)).addParameter("noinfo", "").addParameter("inc", "gender,name,location,dob");
        HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(30 * 1000).build()).build();
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        HttpResponse httpResponse = httpClient.execute(httpGet);
        return mappingUsers(EntityUtils.toString(httpResponse.getEntity()));
    }

    private List<Human> mappingUsers(String jsonHuman) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        List<Human> randomUsers = new ArrayList<>();
        for(JsonNode jsonNode: mapper.readTree(jsonHuman).findValue("results")){
            HumanApi humanRandomUser = mapper.readValue(jsonNode.toString(), HumanApi.class);
            randomUsers.add(humanRandomUser.toHuman());
        }
        return randomUsers;
    }
}
