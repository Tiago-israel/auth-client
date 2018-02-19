package br.com.appauth.service;

import android.content.Context;
import android.content.SharedPreferences;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.appauth.response.Response;

public class HttpService<T extends Response,V> {

    public static  String apiUrl = "https://auth-server-api.herokuapp.com/auth/api/";
    private String path;
    private final Class<T> type;
    private String token;


    public HttpService(String path,Class<T> type){
        this.path = path;
        this.type = type;
    }

    public HttpService(String path,Class<T> type,String token){
        this.path = path;
        this.type = type;
        this.token = token;
    }

    public T post(V data){
        try{
            RestTemplate restTemplate = this.getRestTemplate();
            HttpEntity<T>entity = (HttpEntity<T>) this.configurarHttpEntity(data);
            T response = null;
            response  = restTemplate.postForObject(apiUrl+path,entity, type);
            return  response;
        }catch (Exception ex){
            System.out.print(ex.getMessage());
            return null;
        }

    }

    public void delete(Long id){
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<T>entity = (HttpEntity<T>) this.configurarHttpEntity(null);
        restTemplate.delete(apiUrl+path+"/"+id,entity);
    }

    public T put(Long id, V data){
        RestTemplate restTemplate = this.getRestTemplate();
        HttpEntity<T>entity = (HttpEntity<T>) this.configurarHttpEntity(data);
        ResponseEntity<T> response  =  restTemplate.exchange(apiUrl + path + "/" + id, HttpMethod.PUT,entity,type);
        return  response.getBody();
    }

    private HttpEntity<V> configurarHttpEntity(V data){
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", token);
        HttpEntity<V> entity = new HttpEntity<>(data,header);
        return entity;
    }

    private RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        return restTemplate;
    }


}