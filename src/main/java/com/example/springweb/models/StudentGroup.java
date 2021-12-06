package com.example.springweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class StudentGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int studentGroupID;

	private String groupName;
	private int noOfLecturePerWeek;
	private int size;
	private String subjectName;
	private boolean isPractical;
	private String department;

	@OneToMany(mappedBy = "studentGroup", cascade = CascadeType.ALL)
	private Collection<Combination> combinations = new ArrayList<Combination>();

	@OneToMany(mappedBy = "studentGroup", cascade = CascadeType.ALL)
	private Collection<Lecture> lecture = new ArrayList<Lecture>();

	@JsonIgnore
	@ManyToOne
	private Course course;

	@JsonIgnore
	@ManyToOne
	private TimeTable timeTable;

	@JsonIgnore
	@ManyToOne
	private TimeTable timeTablePractical;

	@JsonIgnore
	@ManyToOne
	private TimeTable timeTableTheory;

	public StudentGroup(String string, int numberOfLectures, int i, ArrayList<Combination> combs, String subject, boolean lab, String dept) {
		this.setGroupName(string);
		this.setNoOfLecturePerWeek(numberOfLectures);
		this.setCombinations(combs);
		this.setSize(i);
		this.subjectName = subject;
		this.isPractical = lab;
		this.setDepartment(dept);
	}

	public StudentGroup() {

	}

	public TimeTable getTimeTablePractical() {
		return timeTablePractical;
	}

	public StudentGroup setTimeTablePractical(TimeTable timeTablePractical) {
		this.timeTablePractical = timeTablePractical;
		return this;
	}

	public TimeTable getTimeTableTheory() {
		return timeTableTheory;
	}

	public StudentGroup setTimeTableTheory(TimeTable timeTableTheory) {
		this.timeTableTheory = timeTableTheory;
		return this;
	}

	public TimeTable getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(TimeTable timeTable) {
		this.timeTable = timeTable;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getStudentGroupID() {
		return studentGroupID;
	}

	public void setStudentGroupID(int studentGroupID) {
		this.studentGroupID = studentGroupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getNoOfLecturePerWeek() {
		return noOfLecturePerWeek;
	}

	public void setNoOfLecturePerWeek(int noOfLecturePerWeek) {
		this.noOfLecturePerWeek = noOfLecturePerWeek;
	}

	public Collection<Combination> getCombinations() {
		return combinations;
	}

	public void setCombinations(Collection<Combination> combinations) {
		this.combinations = combinations;
	}

	public Collection<Lecture> getLecture() {
		return lecture;
	}

	public void setLecture(Collection<Lecture> lecture) {
		this.lecture = lecture;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public boolean isPractical() {
		return isPractical;
	}

	public void setPractical(boolean isPractical) {
		this.isPractical = isPractical;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
