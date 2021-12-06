package com.example.springweb.util;

import com.example.springweb.dao.repositories.*;
import com.example.springweb.models.*;

import java.io.IOException;
import java.util.*;

public class Initialization {

	private ArrayList<ClassRoom> classRooms;
	private ArrayList<Subject> allSubjects;
	private ArrayList<Professor> professors;
	private ArrayList<Course> courses;
	private ArrayList<Subject> subjects = new ArrayList();
	private ArrayList<TimeTable> timetables = new ArrayList();
	private ArrayList<Lecture> classes = new ArrayList<>();

	private SubjectRepository subjectRepository;
	private ProfessorRepository professorRepository;
	private CombinationRepository combinationRepository;
	private LectureRepository lectureRepository;
	private CourseRepository courseRepository;
	private TimeSlotRepository timeSlotRepository;
	private DayRepository dayRepository;
	private ClassRoomRepository classRoomRepository;
	private TimeTableRepository timeTableRepository;

	public void setClassRooms(ArrayList<ClassRoom> classRooms) {
		this.classRooms = classRooms;
	}
	public void setAllSubjects(ArrayList<Subject> allSubjects) {
		this.allSubjects = allSubjects;
	}
	public void setProfessors(ArrayList<Professor> professors) {
		this.professors = professors;
	}
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	public void setSubjectRepository(SubjectRepository subjectRepository) {
		this.subjectRepository = subjectRepository;
	}
	public void setProfessorRepository(ProfessorRepository professorRepository) {
		this.professorRepository = professorRepository;
	}
	public void setCombinationRepository(CombinationRepository combinationRepository) {
		this.combinationRepository = combinationRepository;
	}
	public void setLectureRepository(LectureRepository lectureRepository) {
		this.lectureRepository = lectureRepository;
	}
	public void setCourseRepository(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	public void setTimeSlotRepository(TimeSlotRepository timeSlotRepository) {
		this.timeSlotRepository = timeSlotRepository;
	}
	public void setDayRepository(DayRepository dayRepository) {
		this.dayRepository = dayRepository;
	}
	public void setClassRoomRepository(ClassRoomRepository classRoomRepository) {
		this.classRoomRepository = classRoomRepository;
	}
	public void setTimeTableRepository(TimeTableRepository timeTableRepository) {
		this.timeTableRepository = timeTableRepository;
	}

	public TimeTable readInput() throws IOException{
		Iterator<ClassRoom> classRoomIterator = classRooms.iterator();
		while (classRoomIterator.hasNext()){
			ClassRoom classRoom = classRoomIterator.next();
			classRoom.setWeek(new Week());
		}
		classRoomRepository.saveAll(classRooms);
		Iterator<Professor> professorIterator = professors.iterator();
		while (professorIterator.hasNext()){
			Professor professor = professorIterator.next();
			ArrayList<Subject> professorSubjectsArray = new ArrayList<>();
			professor.setSubjects(professor.getSubjects().replaceAll("\\s+",""));
			String[] professorSubjects = professor.getSubjects().split(",");
			Iterator<Subject> subjectIterator = allSubjects.iterator();
			while (subjectIterator.hasNext()){
				Subject subject = subjectIterator.next();
				if (Arrays.asList(professorSubjects).contains(subject.getSubjectName())){
					professorSubjectsArray.add(subject);
				}
			}
			professor.setSubjectsTaught(professorSubjectsArray);
		}
		createLectures(professors);
		professorRepository.saveAll(professors);
		TimeTable timeTable = new TimeTable(classRooms, classes);
		subjectRepository.saveAll(allSubjects);
		Iterator<Course> courseIterator = courses.iterator();
		while (courseIterator.hasNext()){
			subjects.clear();
			Course course = courseIterator.next();
			course.setSubjects(course.getSubjects().replaceAll("\\s+",""));
			String[] courseSubjects = course.getSubjects().split(",");
			Iterator<Subject> subjectIterator = allSubjects.iterator();
			while (subjectIterator.hasNext()){
				Subject subject = subjectIterator.next();
				if (Arrays.asList(courseSubjects).contains(subject.getSubjectName())){
					subjects.add(subject);
				}
			}
			course.setSubjectsIncluded(subjects);
			Combination combination = course.createCombination(subjects, 20);
			course.createStudentGroups();
			courseRepository.save(course);
			combinationRepository.save(combination);
			Collection<StudentGroup> studentGroups = course.getStudentGroups();
			timeTableRepository.save(timeTable);
			timeTable.addStudentGroups(studentGroups);
		}
		timeTable.initializeTimeTable(lectureRepository);
		timetables.add(timeTable);
		populateTimeTable(timeTable);
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
		geneticAlgorithm.setTimeTableRepository(timeTableRepository);
		geneticAlgorithm.setTimeSlotRepository(timeSlotRepository);
		geneticAlgorithm.setDayRepository(dayRepository);
		geneticAlgorithm.populationAccepter(timetables);
		return geneticAlgorithm.getGlobalBestTimetable();
	}

	public void populateTimeTable(TimeTable timetb1){
		int i = 0;
		while(i < 3){
			TimeTable tempTimetable = timetb1;
			Collection<ClassRoom> allrooms = tempTimetable.getRooms();
			Iterator<ClassRoom> allroomsIterator = allrooms.iterator();
			while(allroomsIterator.hasNext()){
				ClassRoom room = allroomsIterator.next();
				Collection<Day> weekdays = room.getWeek().getWeekDays();
				Collections.shuffle((List<?>) weekdays);
				if(!room.isLaboratory()){
					Iterator<Day> daysIterator=weekdays.iterator();
					while(daysIterator.hasNext()){
						Day day = daysIterator.next();
						Collections.shuffle((List<?>) day.getTimeSlots());
					}
				}
			}
			timetables.add(tempTimetable);
			i++;
		}
	}

	private void createLectures (ArrayList<Professor> professors) {
		java.util.Iterator<Professor> professorIterator=professors.iterator();
		while(professorIterator.hasNext()){
			Professor professor=professorIterator.next();
			Collection<Subject> subjectsTaught = professor.getSubjectsTaught();
			Iterator<Subject> subjectIterator = subjectsTaught.iterator();
			while(subjectIterator.hasNext()){
				Subject subject = subjectIterator.next();
				classes.add(new Lecture (professor, subject));
			}
		}
	}
}