package com.example.springweb.models;

import com.example.springweb.dao.repositories.LectureRepository;

import javax.persistence.*;
import java.util.*;

@Entity
public class TimeTable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int timeTableID;

	private int fitness;

	@OneToMany(mappedBy = "timeTable", cascade = CascadeType.ALL)
	private Collection<ClassRoom> rooms = new ArrayList<>();

	@OneToMany(mappedBy = "timeTableTheory", cascade = CascadeType.ALL)
	private Collection<ClassRoom> theoryRooms = new ArrayList<>();

	@OneToMany(mappedBy = "timeTablePractical", cascade = CascadeType.ALL)
	private Collection<ClassRoom> practicalRooms = new ArrayList<>();

	@OneToMany(mappedBy = "timeTable", cascade = CascadeType.ALL)
	private Collection<StudentGroup> studentGroups=new ArrayList<>();

	@OneToMany(mappedBy = "timeTableTheory", cascade = CascadeType.ALL)
	private Collection<StudentGroup> theoryStudentGroups=new ArrayList<>();

	@OneToMany(mappedBy = "timeTablePractical", cascade = CascadeType.ALL)
	private Collection<StudentGroup> practicalStudentGroups=new ArrayList<>();

	@OneToMany(mappedBy = "timeTable", cascade = CascadeType.ALL)
	private Collection<Lecture> classes = new ArrayList<>();

	public TimeTable(ArrayList<ClassRoom> classroom, ArrayList<Lecture> lectures){
		this.rooms=classroom;
		this.classes=lectures;
		this.fitness =999;
	}

	public TimeTable() {

	}

	public int getTimeTableID() {
		return timeTableID;
	}

	public void setTimeTableID(int timeTableID) {
		this.timeTableID = timeTableID;
	}

	public int getFitness() {
		return fitness;
	}
	
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public Collection<ClassRoom> getRooms() {
		return rooms;
	}

	public void setRooms(Collection<ClassRoom> rooms) {
		this.rooms = rooms;
	}

	public Collection<ClassRoom> getPracticalRooms() {
		return practicalRooms;
	}

	public void setPracticalRooms(Collection<ClassRoom> practicalRooms) {
		this.practicalRooms = practicalRooms;
	}

	public Collection<ClassRoom> getTheoryRooms() {
		return theoryRooms;
	}

	public void setTheoryRooms(Collection<ClassRoom> theoryRooms) {
		this.theoryRooms = theoryRooms;
	}

	public Collection<Lecture> getClasses() {
		return classes;
	}

	public void setClasses(Collection<Lecture> classes) {
		this.classes = classes;
	}

	public Collection<StudentGroup> getStudentGroups() {
		return studentGroups;
	}

	public void setStudentGroups(Collection<StudentGroup> studentGroups) {
		this.studentGroups = studentGroups;
	}

	public Collection<StudentGroup> getTheoryStudentGroups() {
		return theoryStudentGroups;
	}

	public void setTheoryStudentGroups(Collection<StudentGroup> theoryStudentGroups) {
		this.theoryStudentGroups = theoryStudentGroups;
	}

	public Collection<StudentGroup> getPracticalStudentGroups() {
		return practicalStudentGroups;
	}

	public void setPracticalStudentGroups(Collection<StudentGroup> practicalStudentGroups) {
		this.practicalStudentGroups = practicalStudentGroups;
	}

	public void addStudentGroups(Collection<StudentGroup> studentGroups) {
		this.studentGroups.addAll(studentGroups);
	}
	
	public void initializeTimeTable(LectureRepository lectureRepository){
		for (Iterator<ClassRoom> roomsIterator = rooms.iterator(); roomsIterator.hasNext();) {
			ClassRoom room = roomsIterator.next();
			room.setTimeTable(this);
			if(room.isLaboratory()){
				room.setTimeTablePractical(this);
				practicalRooms.add(room);
			}
			else{
				room.setTimeTableTheory(this);
				theoryRooms.add(room);
			}
		}
		for (Iterator<StudentGroup> studentGroupIterator = studentGroups.iterator(); studentGroupIterator.hasNext();) {
			StudentGroup studentGroup = studentGroupIterator.next();
			studentGroup.setTimeTable(this);
			if(studentGroup.isPractical()){
				studentGroup.setTimeTablePractical(this);
				practicalStudentGroups.add(studentGroup);
			}
			else{
				studentGroup.setTimeTableTheory(this);
				theoryStudentGroups.add(studentGroup);
			}
		}
		rooms.clear();
		setTimeTable(practicalStudentGroups, practicalRooms, "practical", lectureRepository);
		setTimeTable(theoryStudentGroups, theoryRooms, "theory", lectureRepository);
		rooms.addAll(practicalRooms);
		rooms.addAll(theoryRooms);
	}
	
	public void setTimeTable(Collection<StudentGroup> studentGroups2, Collection<ClassRoom> rooms2, String string, LectureRepository lectureRepository) {
		Collections.shuffle((List<?>) studentGroups2);
		Stack<Lecture> lecturesStack = new Stack<>();
		for (Iterator<StudentGroup> sdtGrpIterator = studentGroups2.iterator(); sdtGrpIterator.hasNext();) {
			StudentGroup studentGrp = sdtGrpIterator.next();
			String subject = studentGrp.getSubjectName();
			int noOfLectures = studentGrp.getNoOfLecturePerWeek();
			for(int i=0; i<noOfLectures; i++){
				Collections.shuffle((List<?>) classes);
				Iterator<Lecture> classIterator = classes.iterator();
				while(classIterator.hasNext()){
					Lecture lecture = classIterator.next();
					if(lecture.getSubject().getSubjectName().equalsIgnoreCase(subject)){
						Lecture mainLecture = new Lecture(lecture.getProfessor(), lecture.getSubject());
						mainLecture.setStudentGroup(studentGrp);
						mainLecture.setTimeTable(this);
						lectureRepository.save(mainLecture);
						lecturesStack.push(mainLecture);
						break;
					}
				}
			}
		}
		while(!(lecturesStack.empty())){
			Collections.shuffle(lecturesStack);
			Lecture lecture2 = lecturesStack.pop();
			if(string.equalsIgnoreCase("theory")){
				placeTheoryLecture(lecture2, rooms2);
			}
			if(string.equalsIgnoreCase("practical")){
				placePracticalLecture(lecture2, rooms2);
			}
		}	
	}	

	private void placePracticalLecture(Lecture lecture2, Collection<ClassRoom> rooms2c) {
		ArrayList<ClassRoom> rooms2 = new ArrayList<>(rooms2c);
		int size = lecture2.getStudentGroup().getSize();
		String dept = lecture2.getStudentGroup().getDepartment();
		int i = 0;
		boolean invalid = true;
		ClassRoom room = null;
		Collections.shuffle(rooms2);
		while(invalid){
			room = getBestRoom(size, rooms2);
			if(room.getDepartment().equalsIgnoreCase(dept)){
				invalid = false;
			}
			Collections.shuffle(rooms2);
		}
		Collection<Day> weekdays = room.getWeek().getWeekDays();
		Iterator<Day> daysIterator=weekdays.iterator();
		while(daysIterator.hasNext() && i < 3){
			Day day = daysIterator.next();
			Collection<TimeSlot> timeslots = day.getTimeSlots();
			Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
			while(timeslotIterator.hasNext() && i<3){
				TimeSlot lecture3 = timeslotIterator.next();
				if(lecture3.getLecture()==null){
					lecture3.setLecture(lecture2);
					lecture2.setTimeSlot(lecture3);
					i++;
				}
			}
		}		
	}

	private void placeTheoryLecture(Lecture lecture, Collection<ClassRoom> rooms2c) {
		ArrayList<ClassRoom> rooms2 = new ArrayList<>(rooms2c);
		int size = lecture.getStudentGroup().getSize();
		String dept=lecture.getStudentGroup().getDepartment();
		boolean invalid=true;
		ClassRoom room = null;
		Collections.shuffle(rooms2);
		while(invalid){
			room=getBestRoom(size, rooms2);
			if(room.getDepartment().equalsIgnoreCase(dept)){
				invalid=false;
				Collections.shuffle(rooms2);
			}
			else{
				Collections.shuffle(rooms2);
			}
		}
		Collection<Day> weekdays = room.getWeek().getWeekDays();
		Iterator<Day> daysIterator=weekdays.iterator();
		while(daysIterator.hasNext()){
			Day day = daysIterator.next();
			Collection<TimeSlot> timeslots = day.getTimeSlots();
			Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
			while(timeslotIterator.hasNext()){
				TimeSlot lecture2 = timeslotIterator.next();
				if(lecture2.getLecture()==null){
					lecture2.setLecture(lecture);
					lecture.setTimeSlot(lecture2);
					return;
				}
			}
		}
	}

	private boolean checkOccupiedRoom(ClassRoom tempRoom, Collection<ClassRoom> rooms2) {
		for (Iterator<ClassRoom> roomsIterator = rooms2.iterator(); roomsIterator.hasNext();){
			ClassRoom room = roomsIterator.next();
			if(room.equals(tempRoom)){
			Collection<Day> weekdays = room.getWeek().getWeekDays();
			Iterator<Day> daysIterator=weekdays.iterator();
			while(daysIterator.hasNext()){
				Day day = daysIterator.next();
				Collection<TimeSlot> timeslots = day.getTimeSlots();
				Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
				while(timeslotIterator.hasNext()){
					TimeSlot lecture = timeslotIterator.next();
					if(lecture.getLecture()==null){
						return false;
					}
				}
			}
			return true;
			}		
		}
		return false;
	}

	private ClassRoom getBestRoom(int size, ArrayList<ClassRoom> rooms2) {
		int delta = 1000;
		ClassRoom room = null;
		for (Iterator<ClassRoom> roomsIterator = rooms2.iterator(); roomsIterator.hasNext();){
			ClassRoom tempRoom = roomsIterator.next();
			if(!checkOccupiedRoom(tempRoom, rooms2)){
		        int tmp = Math.abs(size - tempRoom.getSize());
		        if(tmp < delta){
		            delta = tmp;
		            room = tempRoom;
		    	}
			}
		}
		return room;
	}
}