package com.example.coronavirustracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.coronavirustracker.models.LocationStats;

import jakarta.annotation.PostConstruct;

@Service
public class CoronaVirusVaccineDataServices {

	private static String VACCINE_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-20/master/data_tables/vaccine_data/global_data/time_series_covid19_vaccine_global.csv";

	private List<LocationStats> allStats = new ArrayList<>();

	public List<LocationStats> getAllStats() {
		return allStats;
	}

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchVaccineData() throws IOException, InterruptedException {
		List<LocationStats> newStats = new ArrayList<>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VACCINE_DATA_URL)).build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

		StringReader csvBodyReader = new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		LocalDate latestDate = LocalDate.now();

		for (CSVRecord record : records) {
			LocationStats locationStats = new LocationStats();

			locationStats.setCountry(record.get("Country_Region"));
			locationStats.setDate(LocalDate.parse(record.get("Date")));
			if (record.get("Doses_admin").equals("")) {
				locationStats.setDosesAdmin(0);
			} else {
				locationStats.setDosesAdmin(Long.parseLong(record.get("Doses_admin")));
			}
			if (record.get("People_at_least_one_dose").equals("")) {
				locationStats.setMinimumOneDose(0);
			} else {
				locationStats.setMinimumOneDose(Long.parseLong(record.get("People_at_least_one_dose")));
			}
			
			latestDate = LocalDate.parse(record.get("Date"));

			newStats.add(locationStats);

		}
		
		for(LocationStats stats : newStats) {
			if( stats.getDate().equals(latestDate) ) {
				allStats.add(stats);
			}
		}

	}
}
