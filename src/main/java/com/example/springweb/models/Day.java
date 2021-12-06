package com.example.springweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Day {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int dayID;

	private String dayName;
	private int dayNumber;

	@JsonIgnore
	@ManyToOne
	private Week week;

	@OneToMany(mappedBy = "day", cascade = CascadeType.ALL)
	private Collection<TimeSlot> timeSlots = new ArrayList();

	public Day(){
		for(int i=0; i<6; i++){
			TimeSlot timeSlot = new TimeSlot();
			timeSlot.setDay(this);
			timeSlots.add(timeSlot);
		}
	}

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public int getDayID() {
		return dayID;
	}

	public void setDayID(int dayID) {
		this.dayID = dayID;
	}

	public Collection<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(Collection<TimeSlot> timeSlot) {
		this.timeSlots = timeSlot;
	}

}
