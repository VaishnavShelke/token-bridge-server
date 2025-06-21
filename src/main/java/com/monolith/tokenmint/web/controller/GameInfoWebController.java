package com.monolith.tokenmint.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.monolith.tokenmint.beans.GameInfo;
import com.monolith.tokenmint.create.dao.GameInfoDAO;
import com.monolith.shared.utils.Utility;

@Controller
@RequestMapping("/admin/games")
public class GameInfoWebController {
	
	private static final Logger logger = LoggerFactory.getLogger(GameInfoWebController.class);

	@Autowired
	GameInfoDAO gameInfoDAO;
	
	@GetMapping
	public String listGames(Model model) {
		logger.info("Fetching all games for list view");
		List<GameInfo> games = gameInfoDAO.getAllGames();
		model.addAttribute("games", games);
		return "games/list";
	}
	
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		logger.info("Showing create game form");
		model.addAttribute("gameInfo", new GameInfo());
		model.addAttribute("action", "Create");
		return "games/form";
	}
	
	@PostMapping("/create")
	public String createGame(@ModelAttribute GameInfo gameInfo, 
	                        BindingResult result,
	                        RedirectAttributes redirectAttributes) {
		logger.info("Creating new game: {}", Utility.getJsonFromObject(gameInfo));
		
		// Validation
		if (gameInfo.getGameId() == null || gameInfo.getGameId().trim().isEmpty()) {
			result.rejectValue("gameId", "error.gameId", "Game ID is required");
		}
		
		if (gameInfo.getGameName() == null || gameInfo.getGameName().trim().isEmpty()) {
			result.rejectValue("gameName", "error.gameName", "Game Name is required");
		}
		
		// Check if game ID already exists
		if (gameInfoDAO.gameExists(gameInfo.getGameId())) {
			result.rejectValue("gameId", "error.gameId", "Game ID already exists");
		}
		
		if (result.hasErrors()) {
			return "games/form";
		}
		
		boolean success = gameInfoDAO.createGame(gameInfo);
		if (success) {
			redirectAttributes.addFlashAttribute("successMessage", "Game created successfully!");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to create game. Please try again.");
		}
		
		return "redirect:/admin/games";
	}
	
	@GetMapping("/view/{gameId}")
	public String viewGame(@PathVariable String gameId, Model model, RedirectAttributes redirectAttributes) {
		logger.info("Viewing game details: {}", gameId);
		GameInfo gameInfo = gameInfoDAO.getGameInfoByGameId(gameId);
		
		if (gameInfo == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Game not found!");
			return "redirect:/admin/games";
		}
		
		model.addAttribute("gameInfo", gameInfo);
		return "games/view";
	}
} 