package com.monolith.tokenmint.dto;

import lombok.Data;

@Data
public class Web3ContractArguments {
	private String from;
	private String to;
	private String id;
	private String value;
	private String data;
}
