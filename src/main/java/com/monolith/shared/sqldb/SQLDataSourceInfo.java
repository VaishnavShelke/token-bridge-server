package com.monolith.shared.sqldb;

import java.io.Serializable;

import lombok.Data;

@Data
public class SQLDataSourceInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String schema;
	private String minimumIdle;
	private String password;
	private String maximumPoolSize;
	private String dbURL;
	private String username;
	
}
