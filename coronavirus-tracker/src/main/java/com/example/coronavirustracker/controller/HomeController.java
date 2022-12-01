package com.example.coronavirustracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.coronavirustracker.models.LocationStats;
import com.example.coronavirustracker.services.CoronaVirusVaccineDataServices;

@Controller
public class HomeController {
	
	@Autowired
	CoronaVirusVaccineDataServices coronaVirusDataService;

	@GetMapping("/")
	public String home(Model model) {
		List<LocationStats> allStats = coronaVirusDataService.getAllStats();
		Long totalVaccines = null;
		LocalDate latestDate = null;
		for(LocationStats stat : allStats) {
			if( stat.getCountry().equals("World") ) {
				totalVaccines = stat.getDosesAdmin();
			}
			latestDate = stat.getDate();
		}
	
		model.addAttribute("locationStats", allStats);
		model.addAttribute("totalReportedVaccines", totalVaccines);
		model.addAttribute("latestDate", latestDate);


		return "home";
	}

}
