package com.dfba.gobus;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by claudio on 21/11/14.
 */
public class StopsTask extends AsyncTask<String,Integer,Results> {

    @Override
    protected Results doInBackground(String... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HttpHeaders requestHeaders = new HttpHeaders();

            MultiValueMap<String, Object> formData;
            formData = new LinkedMultiValueMap<String, Object>();
            formData.add("latitude", params[0]);
            formData.add("longitude",params[1]);
            formData.add("user_id",0);

// Sending multipart/form-data
            requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

// Populate the MultiValueMap being serialized and headers in an HttpEntity object to use for the request
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
                    formData, requestHeaders);


// Make the network request, posting the message, expecting a String in response from the server
            ResponseEntity<Results> value = restTemplate.exchange("https://fast-fire-771.appspot.com/getNextStops", HttpMethod.POST, requestEntity,
                    Results.class);
            //ResponseEntity<Results> value = restTemplate.postForEntity("https://fast-fire-771.appspot.com/getNextStops?latitude=" + params[0] + "&longitude=" + params[1] + "&user_id=0", Results.class, null);
            Log.e("stop", value.getBody().getStops().get(0).getLine());
            return value.getBody();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return null;
        } catch (HttpServerErrorException s) {
            s.printStackTrace();
            return null;
        } catch (Exception i) {
            i.printStackTrace();
            return null;
        }
    }
}
