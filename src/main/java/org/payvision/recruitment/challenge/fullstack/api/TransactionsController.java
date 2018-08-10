package org.payvision.recruitment.challenge.fullstack.api;

import java.net.URI;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.payvision.recruitment.challenge.fullstack.config.RestTemplateClient;
import org.payvision.recruitment.challenge.fullstack.constants.MyEnums;
import org.payvision.recruitment.challenge.fullstack.domains.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConfigurationProperties(prefix = "rest.template")
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionsController {
	private UriComponentsBuilder uriComponentsBuilder;
	// properties default values.
	private String host = "jovs5zmau3.execute-api.eu-west-1.amazonaws.com";
	private String path = "prod/transactions";

	private final RestTemplateClient restTemplateClient;
	private final MyEnums myEnums;

	public TransactionsController(RestTemplateClient restTemplateClient, MyEnums myEnums) {
		this.restTemplateClient = restTemplateClient;
		this.myEnums = myEnums;		
		}

	@Autowired
	private RestOperations rest;
	
	@ResponseBody
	@GetMapping(value = "transactions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Transaction[] getTransactions(HttpServletRequest request,
			@RequestParam(value = "action", defaultValue = "", required = false) String action,
			@RequestParam(value = "currencyCode", defaultValue = "", required = false) String currencyCode,
			@RequestParam(value = "orderBy", defaultValue = "", required = false) String orderBy) throws Exception {

		// check out the input parameters
		if (!action.isEmpty()) Assert.isTrue(myEnums.enumMapAction.containsValue(action), "action param has invalid value");
		if (!currencyCode.isEmpty()) Assert.isTrue(myEnums.enumMapCurrencyCode.containsValue(currencyCode), "currencyCode param has invalid value");
		if (!orderBy.isEmpty()) Assert.isTrue(myEnums.enumMapOrderBy.containsValue(orderBy), "orderBy param has invalid value");
		
		// create the url
		uriComponentsBuilder = UriComponentsBuilder.newInstance().scheme("https").host(host).path(path);
		if (!action.isEmpty())
			uriComponentsBuilder.queryParam(myEnums.enumMapFilter.get(MyEnums.Filter.ACTION), action);
		if (!currencyCode.isEmpty())
			uriComponentsBuilder.queryParam(myEnums.enumMapFilter.get(MyEnums.Filter.CURRENCYCODE), currencyCode);
		if (!orderBy.isEmpty())
			uriComponentsBuilder.queryParam(myEnums.enumMapFilter.get(MyEnums.Filter.ORDERBY), orderBy);
		URI url = uriComponentsBuilder.build().toUri();
		
		// Request to the external host
		Transaction[] response =  rest.getForObject(url, Transaction[].class);
		return response;
	}

	@ResponseBody
	@GetMapping(value = "filter", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Collection<String> getValues(HttpServletRequest request,
			@RequestParam(value = "name", required = true) String name) throws Exception {

		Assert.isTrue(myEnums.enumMapFilter.containsValue(name), "filter=" + name + " has invalid value");
			
		if(name.contentEquals(myEnums.enumMapFilter.get(MyEnums.Filter.ACTION)))
			return myEnums.enumMapAction.values();
		if(name.contentEquals(myEnums.enumMapFilter.get(MyEnums.Filter.CURRENCYCODE)))
			return myEnums.enumMapCurrencyCode.values();
		if(name.contentEquals(myEnums.enumMapFilter.get(MyEnums.Filter.ORDERBY)))
			return myEnums.enumMapOrderBy.values();
		return null;
	}

}