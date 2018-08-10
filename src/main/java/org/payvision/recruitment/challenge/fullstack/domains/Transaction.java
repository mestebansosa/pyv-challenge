package org.payvision.recruitment.challenge.fullstack.domains;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction  implements Serializable {
	private static final long serialVersionUID = -6743782939567855239L;
	
	private String action;	
	private Integer amount;
	private Integer brandId;
	private Card card;
	private String currencyCode;
	private String id;
	private String trackingCode;	
}
