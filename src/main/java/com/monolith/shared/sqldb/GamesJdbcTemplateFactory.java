package com.monolith.shared.sqldb;

import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import com.monolith.shared.dao.GameConfigDAO;

import jakarta.annotation.PostConstruct;

@Service
public class GamesJdbcTemplateFactory {

	private static final Logger logger = LoggerFactory.getLogger(GamesJdbcTemplateFactory.class);

	@Autowired
	GameConfigDAO gameConfigDAO;
	
	private static HashMap<String,JdbcTemplate> gameJdbcTemplateMap = null;
	
	@PostConstruct
	private void init() {
		gameJdbcTemplateMap = new HashMap<>();
	}
	
	
	public JdbcTemplate getJdbcTemplateForGame(String gameId) {
		if(gameJdbcTemplateMap.get(gameId) != null) {
			return gameJdbcTemplateMap.get(gameId);
		}else {
			JdbcTemplate jdbcTemplate = null;
			SQLDataSourceInfo dsInfo = gameConfigDAO.getGameInstannceDataSource(gameId);
			DataSource ds = makeDataSourceObject(dsInfo);
			jdbcTemplate = makeJdbcTemplate(ds);
			gameJdbcTemplateMap.put(gameId,jdbcTemplate);
			return jdbcTemplate;
		}
	
	}

	
	public DataSource makeDataSourceObject(SQLDataSourceInfo dataSourceInfo) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(dataSourceInfo.getDbURL());
		logger.info("Datasource DB URL {}",dataSource.getUrl());
		dataSource.setUsername(dataSourceInfo.getUsername());
		dataSource.setPassword(dataSourceInfo.getPassword());
		return dataSource;
	}
	
	public JdbcTemplate makeJdbcTemplate(DataSource ds) {
		try {
			logger.info("INITIALIZATION :: JDBC Template Initiated for Game ");
			new JdbcTemplate(ds).getDataSource().getConnection();
		} catch (SQLException e) {
			logger.error("INITIALIZATION :: Unable to intitialize jdbc template for Game");
		}
		return new JdbcTemplate(ds);
	}
}
