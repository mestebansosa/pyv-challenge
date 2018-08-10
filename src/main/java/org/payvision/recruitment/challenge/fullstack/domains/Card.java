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
public class Card  implements Serializable {
	private static final long serialVersionUID = -6743782939567855239L;
	
	private Integer expiryMonth;
	private Integer expiryYear;
	private Integer firstSixDigits;
	private Integer lastFourDigits;
	private String holderName;	
}
