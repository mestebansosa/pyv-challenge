package org.payvision.recruitment.challenge.fullstack.constants;

import java.util.EnumMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Component
public class MyEnums {
	public enum Filter {ACTION, CURRENCYCODE, ORDERBY };
	public enum Action {EMPTY, PAYMENT, CREDIT };
	public enum CurrencyCode {EMPTY, EUR, JPY, USD };
	public enum OrderBy {EMPTY, DATE, RDATE };
	
	public EnumMap<Filter, String> enumMapFilter = new EnumMap<Filter, String>(Filter.class);
	public EnumMap<Action, String> enumMapAction = new EnumMap<Action, String>(Action.class);
	public EnumMap<CurrencyCode, String> enumMapCurrencyCode = new EnumMap<CurrencyCode, String>(CurrencyCode.class);
	public EnumMap<OrderBy, String> enumMapOrderBy = new EnumMap<OrderBy, String>(OrderBy.class);
	
	@PostConstruct
	public void init() {
		enumMapFilter.put(Filter.ACTION, "action");
		enumMapFilter.put(Filter.CURRENCYCODE, "currencyCode");
		enumMapFilter.put(Filter.ORDERBY, "orderBy");
		log.info("MyEnums enumMapFilter {}", enumMapFilter.keySet());
		
		enumMapAction.put(Action.PAYMENT, "payment");
		enumMapAction.put(Action.CREDIT, "credit");
		log.info("MyEnums enumMapAction {}", enumMapAction.keySet());
		
		enumMapCurrencyCode.put(CurrencyCode.EUR, "EUR");
		enumMapCurrencyCode.put(CurrencyCode.JPY, "JPY");
		enumMapCurrencyCode.put(CurrencyCode.USD, "USD");
		log.info("MyEnums enumMapCurrencyCode {}", enumMapCurrencyCode.keySet());
		
		enumMapOrderBy.put(OrderBy.DATE, "date");
		enumMapOrderBy.put(OrderBy.RDATE, "-date");
		log.info("MyEnums enumMapOrderBy {}", enumMapOrderBy.keySet());		
	}

}
