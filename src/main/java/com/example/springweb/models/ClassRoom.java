package com.example.springweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class ClassRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int roomID;

	private String roomName;
	private int size;
	private boolean isLaboratory;
	private String department;
	private String type;

	@JsonIgnore
	@ManyToOne
	private TimeTable timeTable;

	@JsonIgnore
	@ManyToOne
	private TimeTable timeTablePractical;

	@JsonIgnore
	@ManyToOne
	private TimeTable timeTableTheory;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "weekID", referencedColumnName = "weekID")
	private Week week;

	public ClassRoom() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TimeTable getTimeTablePractical() {
		return timeTablePractical;
	}

	public void setTimeTablePractical(TimeTable timeTablePractical) {
		this.timeTablePractical = timeTablePractical;
	}

	public TimeTable getTimeTableTheory() {
		return timeTableTheory;
	}

	public void setTimeTableTheory(TimeTable timeTableTheory) {
		this.timeTableTheory = timeTableTheory;
	}

	public TimeTable getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(TimeTable timeTable) {
		this.timeTable = timeTable;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isLaboratory() {
		return isLaboratory;
	}

	public void setLaboratory(boolean laboratory) {
		this.isLaboratory = laboratory;
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		week.setClassRoom(this);
		this.week = week;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
