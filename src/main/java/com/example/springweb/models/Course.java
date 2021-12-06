package com.example.springweb.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int courseID;

	private String courseName;

	private String subjects;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private Collection<Subject> subjectsIncluded = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private Collection<StudentGroup> studentGroups = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private Collection<Combination> combinations = new ArrayList<>();

	public Course(String name, ArrayList<Subject> subjectsIncluded){
		this.courseName = name;
		Iterator<Subject> subjectsIterator = subjectsIncluded.iterator();
		while(subjectsIterator.hasNext()){
			subjectsIterator.next().setCourse(this);
		}
		this.subjectsIncluded = subjectsIncluded;
	}

	public Course() {

	}

	public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	public void setSubjectsIncluded(Collection<Subject> subjectsIncluded) {
		this.subjectsIncluded = subjectsIncluded;
	}

	public void setStudentGroups(Collection<StudentGroup> studentGroups) {
		this.studentGroups = studentGroups;
	}

	public void setCombinations(Collection<Combination> combinations) {
		this.combinations = combinations;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Collection<Subject> getSubjectsIncluded() {
		return subjectsIncluded;
	}

	public void setSubjectsIncluded(ArrayList<Subject> subjectsIncluded) {
		Iterator<Subject> subjectsIterator = subjectsIncluded.iterator();
		while(subjectsIterator.hasNext()){
			subjectsIterator.next().setCourse(this);
		}
		this.subjectsIncluded = subjectsIncluded;
	}
	
	public Collection<Combination> getCombinations() {
		return combinations;
	}

	public void setCombinations(ArrayList<Combination> combinations) {
		this.combinations = combinations;
	}
	
	public Collection<StudentGroup> getStudentGroups() {
		return studentGroups;
	}

	public void setStudentGroups(ArrayList<StudentGroup> studentGroups) {
		this.studentGroups = studentGroups;
	}

	public Combination createCombination(Collection<Subject> subjects, int size){
		Combination combination = new Combination((ArrayList<Subject>) subjects, size);
		combinations.add(combination);
		return combination;
	}
	
	public void createStudentGroups(){
		int size = 0;
		ArrayList<Combination> combs = new ArrayList<>();
		Iterator<Subject> subjectIterator = subjectsIncluded.iterator();
		while(subjectIterator.hasNext()){
			Subject subject=subjectIterator.next();
			Iterator combIterator =combinations.iterator();
			while(combIterator.hasNext()){
				Combination combination = (Combination) combIterator.next();
				Collection<Subject> subjects = combination.getSubjects();
				Iterator<Subject> subjectItr = subjects.iterator();
				while(subjectItr.hasNext()){
					if(subjectItr.next().getSubjectName().equalsIgnoreCase(subject.getSubjectName())){
						size=size+combination.getSizeOfClass();
						if(!combs.contains(combination.getSubjects())){
							combination.setCourse(this);
							combs.add(combination);
						}
					}
				}
			}
			StudentGroup studentGroup = new StudentGroup(this.courseName+"/"+subject.getSubjectName(), subject.getNumberOfLecturesPerWeek(), size, combs, subject.getSubjectName(), subject.isLaboratory(), subject.getDepartment());
		    studentGroup.setCourse(this);
			Iterator<Combination> collectionIterator = combs.iterator();
			while(collectionIterator.hasNext()){
				collectionIterator.next().setStudentGroup(studentGroup);
			}
			studentGroups.add(studentGroup);
		    size=0;
		}
	}
}
