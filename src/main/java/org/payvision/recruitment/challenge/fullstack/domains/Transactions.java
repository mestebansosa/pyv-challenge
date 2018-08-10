package org.payvision.recruitment.challenge.fullstack.domains;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transactions  implements Serializable {
	private static final long serialVersionUID = -6743782939567855239L;
	
	private List<Transaction> transactions;	
}
