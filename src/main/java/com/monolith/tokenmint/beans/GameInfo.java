package com.monolith.tokenmint.beans;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "game_info")
public class GameInfo {

	@Id
	@Column(name = "game_id")
	private String gameId;
	
	@Column(name = "game_name")
	private String gameName;
	
	@Column(name = "game_parent_company")
	private String gameParentCompany;
	
	@Column(name = "game_logo")
	private String gameLogo;
	
	@Column(name = "api_key")
	private String apiKey;
}
