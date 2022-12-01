package com.example.coronavirustracker.models;

import java.time.LocalDate;

public class LocationStats {

	private LocalDate date;
	private String country;
	private long dosesAdmin;
	private long minimumOneDose;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public long getDosesAdmin() {
		return dosesAdmin;
	}
	public void setDosesAdmin(long dosesAdmin) {
		this.dosesAdmin = dosesAdmin;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public long getMinimumOneDose() {
		return minimumOneDose;
	}
	public void setMinimumOneDose(long minimumOneDose) {
		this.minimumOneDose = minimumOneDose;
	}
	@Override
	public String toString() {
		return "LocationStats [country=" + country + ", dosesAdmin=" + dosesAdmin + ", minimumOneDose=" + minimumOneDose
				+ "]";
	}

}
