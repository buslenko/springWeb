package com.example.springweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class TimeSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int timeSlotID;
	private int timeSlotNumber;
	@JsonIgnore
	@ManyToOne
	private Day day;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lectureID", referencedColumnName = "lectureID")
	private Lecture lecture;

	public TimeSlot(int timeSlotID, Lecture lecture) {
		this.timeSlotID = timeSlotID;
		lecture.setTimeSlot(this);
		this.lecture = lecture;
	}

	public TimeSlot() {};

	public int getTimeSlotNumber() {
		return timeSlotNumber;
	}

	public void setTimeSlotNumber(int timeSlotNumber) {
		this.timeSlotNumber = timeSlotNumber;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public int getTimeSlotID() {
		return timeSlotID;
	}

	public void setTimeSlotID(int timeSlotID) {
		this.timeSlotID = timeSlotID;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}
}
