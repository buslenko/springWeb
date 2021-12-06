package com.example.springweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Entity
public class Week {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int weekID;

	@JsonIgnore
	@OneToOne(mappedBy = "week")
	private ClassRoom classRoom;

	@OneToMany(mappedBy = "week", cascade = CascadeType.ALL)
	private Collection<Day> weekDays = new ArrayList<Day>();

	public Week(){
		String[] arrWeekDays = new DateFormatSymbols().getWeekdays();
		ArrayList<String> weekDaysName = new ArrayList<>(Arrays.asList(arrWeekDays));
		for (int i = 1; i < weekDaysName.size(); i++) {
			if(!(weekDaysName.get(i).equalsIgnoreCase("воскресенье"))){
				Day day = new Day();
				day.setDayName(weekDaysName.get(i));
				day.setWeek(this);
				weekDays.add(day);
    		}
    	}
	}

	public ClassRoom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}

	public int getWeekID() {
		return weekID;
	}

	public void setWeekID(int weekID) {
		this.weekID = weekID;
	}

	public Collection<Day> getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(Collection<Day> weekDays) {
		this.weekDays = weekDays;
	}
}
