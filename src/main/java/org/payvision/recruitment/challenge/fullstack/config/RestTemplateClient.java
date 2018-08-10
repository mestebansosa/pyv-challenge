package org.payvision.recruitment.challenge.fullstack.config;

import java.net.URI;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestTemplateClient {

	public final RestTemplate restTemplate;
	public RestTemplateClient(@Qualifier("custom-rest-template") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@PostConstruct
	public void init() {
		log.info("Initializing business Service: RestTemplateClient");
	}
	
	public <T> T getForEntity(URI url, Class<T> clazz, String methodName) throws Exception {
		ResponseEntity<T> responseEntity = null;
		try {			
			log.debug("getForEntity {} url {}", methodName, url);			
			responseEntity = restTemplate.getForEntity(url, clazz);
			checkStatusCode(responseEntity);
			log.info("{} data found {}", methodName, responseEntity);
		} catch (Exception e) {
			log.error("{} exception {} ", methodName, e.getMessage());
			throw e;
		}
		return responseEntity.getBody();

	}

	public <T> T postForEntity(URI url, Class<T> clazz, String payload, String methodName) throws Exception {
		ResponseEntity<T> responseEntity = null;
		try {
			log.debug("postForEntity {} url {}", methodName, url);
			responseEntity = restTemplate.postForEntity(url, payload, clazz);
			checkStatusCode(responseEntity);
			log.info("{} data found {}", methodName, responseEntity);
		} catch (Exception e) {
			log.error("{} exception  {} ", methodName, e.getMessage());
			throw e;
		}
		return responseEntity.getBody();
	}
	
	public <T> T postForObject(URI url, Class<T> clazz, T payload, String methodName) throws Exception {
		T response = null;
		try {
			log.debug("postForObject {} url {}", methodName, url);
			response = restTemplate.postForObject(url, payload, clazz);
			log.info("{} data found {}", methodName, response);
		} catch (Exception e) {
			log.error("{} exception  {} ", methodName, e.getMessage());
			throw e;
		}
		return response;
	}
	
	public <T> T putForObject(URI url, Class<T> clazz, T payload, String methodName) throws Exception {
		T response = null;
		try {
			log.debug("putForObject {} url {}", methodName, url);
			restTemplate.put(url, payload);
			log.info("{} data created.", methodName);
		} catch (Exception e) {
			log.error("{} exception  {} ", methodName, e.getMessage());
			throw e;
		}
		return response;
	}
	
	public <T> T exchange(URI url, Class<T> clazz, HttpMethod httpMethod, String payload, String xForwardedHost, String methodName) throws Exception {
		ResponseEntity<T> responseEntity = null;
		try {
			log.debug("exchange {} url {} payload {}", methodName, url, payload);
			HttpEntity<String> entity = createHttpEntityWithHeader(payload, xForwardedHost);
			responseEntity = restTemplate.exchange(url, httpMethod, entity, clazz); 
			checkStatusCode(responseEntity);
			log.info("exchange {} data sent {}", methodName, responseEntity);
		} catch (HttpClientErrorException h) {
			log.warn("exchange {} status {} exception {} ", methodName, h.getStatusCode(), h.getMessage());
			throw new Exception("exchange " + httpMethod.name() + ". Error:" +  h.getMessage());
		} catch (Exception e) {
			log.error("exchange {} exception {} ", methodName, e.getMessage());
			throw new Exception(httpMethod.name() + ". Error:" +  e.getMessage());
		}
		return responseEntity.getBody();
	}

	public <T> T exchange(URI url, Class<T> clazz, HttpMethod httpMethod, String payload, String methodName) throws Exception {
		return exchange(url, clazz, httpMethod, payload, "", methodName);
	}
	
	public void checkStatusCode(ResponseEntity<?> responseEntity) throws Exception {
		if (responseEntity.getStatusCode().value() != HttpStatus.OK.value()) {
			throw new Exception(
					"HTTP response code: " + responseEntity.getStatusCode());
		}
	}

	public HttpEntity<String> createHttpEntityWithHeader(String payload, String xForwardedHost) {
		if(xForwardedHost == null || xForwardedHost == "") {
			return new HttpEntity<String>(payload);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Forwarded-Host", xForwardedHost);
		return new HttpEntity<String>(payload, headers);
	}
	
}
