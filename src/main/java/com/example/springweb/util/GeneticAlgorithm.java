package com.example.springweb.util;

import com.example.springweb.dao.repositories.*;
import com.example.springweb.models.*;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.*;

public class GeneticAlgorithm {

	private static TimeSlotRepository timeSlotRepository;
	private static DayRepository dayRepository;
	private static TimeTableRepository timeTableRepository;
	private static TimeTable GlobalBestTimetable;
	private static int min=1000;
	private static ArrayList<String>weekDayNames=new ArrayList<>();
	private static ArrayList<String>lectureTimings=new ArrayList<>();

	public void setTimeSlotRepository(TimeSlotRepository timeSlotRepository) {
		this.timeSlotRepository = timeSlotRepository;
	}

	public void setDayRepository(DayRepository dayRepository) {
		this.dayRepository = dayRepository;
	}

	public void setTimeTableRepository(TimeTableRepository timeTableRepository) {
		this.timeTableRepository = timeTableRepository;
	}

	public TimeTable getGlobalBestTimetable(){
		return GlobalBestTimetable;
	}

	public static void populationAccepter(ArrayList<TimeTable> timeTableCollection) throws IOException{
		for (Iterator<TimeTable> iterator = timeTableCollection.iterator(); iterator.hasNext();) {
			TimeTable tt = iterator.next();
			fitness(tt);
		}
		createWeek();
		createLectureTime();
		selection(timeTableCollection);
		fixTimeTable();
	}

	private static void createWeek(){
		String[] weekDaysName = new DateFormatSymbols().getWeekdays();
		for (int i = 1; i < weekDaysName.length; i++) {
			weekDaysName[i] = weekDaysName[i].substring(0, 1).toUpperCase() + weekDaysName[i].substring(1);
			if (!(i == Calendar.SUNDAY))
				weekDayNames.add(weekDaysName[i]);
		}
	}

	private static void createLectureTime(){
		List<String> timings = Arrays.asList("9:00", "10:30", "10:40", "12:10", "12:40",
				"14:10", "14:20", "15:50", "16:20", "17:50", "18:00", "19:30");
		for(int i=0; i<12; i = i + 2){
			lectureTimings.add(timings.get(i) + " TO " + timings.get(i + 1));
		}
	}

	public static void selection(ArrayList<TimeTable> timetables) throws IOException{
		int iterations = 50;
		ArrayList<TimeTable> mutants = new ArrayList<>();
		Iterator<TimeTable> ttItr = timetables.iterator();
		while(ttItr.hasNext()){
			fitness(ttItr.next());
		}
		while(iterations != 0){
			Iterator<TimeTable> timetableIterator=timetables.iterator();
			while (timetableIterator.hasNext()) {
				TimeTable tt = timetableIterator.next();
				int score = tt.getFitness();
				if(score < min){
					min = score;
					GlobalBestTimetable = tt;
				}
			}
			iterations--;
			for (Iterator<TimeTable> iterator = timetables.iterator(); iterator.hasNext();) {
				TimeTable timetable1 = iterator.next();
				TimeTable childTimetable=crossOver(timetable1);
				TimeTable mutant=Mutation(childTimetable);
				mutants.add(mutant);
			}
			timetables.clear();
			for (int j = 0; j < mutants.size(); j++) {
				fitness(mutants.get(j));
				timetables.add(mutants.get(j));
			}
			mutants.clear();
		}
	}

	public static void fitness(TimeTable timetable){
		Collection<ClassRoom> rooms=timetable.getRooms();
		Iterator<ClassRoom> roomIterator1 = rooms.iterator();
		while(roomIterator1.hasNext()){
			int score = 0;
			ClassRoom room1 = roomIterator1.next();
			Iterator<ClassRoom> roomIterator2 = rooms.iterator();
			while(roomIterator2.hasNext()){
				ClassRoom room2 = roomIterator2.next();
				if(room2!=room1){
					Collection<Day> weekdays1 = room1.getWeek().getWeekDays();
					Collection<Day> weekdays2 = room2.getWeek().getWeekDays();
					Iterator<Day> daysIterator1=weekdays1.iterator();
					Iterator<Day> daysIterator2=weekdays2.iterator();
					while(daysIterator1.hasNext() && daysIterator2.hasNext()){
						Day day1 = daysIterator1.next();
						Day day2 = daysIterator2.next();
						Collection<TimeSlot> timeslots1 = day1.getTimeSlots();
						Collection<TimeSlot> timeslots2 = day2.getTimeSlots();
						Iterator<TimeSlot> timeslotIterator1 = timeslots1.iterator();
						Iterator<TimeSlot> timeslotIterator2 = timeslots2.iterator();
						while(timeslotIterator1.hasNext() && timeslotIterator2.hasNext()){
							TimeSlot lecture1 = timeslotIterator1.next();
							TimeSlot lecture2 = timeslotIterator2.next();
							if(lecture1.getLecture()!=null  &&  lecture2.getLecture()!=null){
								String professorName1 = lecture1.getLecture().getProfessor().getProfessorName();
								String professorName2 = lecture2.getLecture().getProfessor().getProfessorName();
								String stgrp1 = lecture1.getLecture().getStudentGroup().getGroupName();
								String stgrp2 = lecture2.getLecture().getStudentGroup().getGroupName();
								if(stgrp1.equals(stgrp2) || professorName1.equals(professorName2)){
									score = score+1;
								}
								Collection<Combination> stcomb1 = lecture1.getLecture().
										getStudentGroup().getCombinations();
								Iterator<Combination> stcombItr = stcomb1.iterator();
								while(stcombItr.hasNext()){
									if(lecture2.getLecture().getStudentGroup().
											getCombinations().contains(stcombItr.next())){
										score = score+1;
										break;
									}
								}
							}
						}
					}
				}
			}
			timetable.setFitness(score);
		}
	}

	private static TimeTable Mutation(TimeTable parentTimetable) {
		TimeTable mutantTimeTable=parentTimetable;
		int rnd1,rnd2;
		Random randomGenerator = new Random();
		Collection<ClassRoom> presentClassroom=mutantTimeTable.getRooms();
		for (Iterator<ClassRoom> iterator = presentClassroom.iterator(); iterator.hasNext();) {
			ClassRoom classRoom = iterator.next();
			rnd1=randomGenerator.nextInt(5);
			rnd2=-1;
			while(rnd1!=rnd2){
				rnd2=randomGenerator.nextInt(5);
			}
			Collection<Day> weekDaysc = classRoom.getWeek().getWeekDays();
			ArrayList<Day> weekDays = new ArrayList(weekDaysc);
			Day day1 = weekDays.get(rnd1);
			Day day2 = weekDays.get(rnd2);


			Collection<TimeSlot> timeSlotsOfday1 = day1.getTimeSlots();
			Collection<TimeSlot> timeSlotsOfday2 = day2.getTimeSlots();

			day1.setTimeSlots(timeSlotsOfday2);
			day2.setTimeSlots(timeSlotsOfday1);

			break;
		}
		return mutantTimeTable;
	}

	private static TimeTable crossOver(TimeTable fatherTimeTable){
		Random randomGenerator = new Random();
		Iterator<ClassRoom> parentTimeTableClassRooms=fatherTimeTable.getRooms().iterator();
		while(parentTimeTableClassRooms.hasNext()) {
			ClassRoom room = parentTimeTableClassRooms.next();
			if(!room.isLaboratory()){
				Collection<Day> daysc = room.getWeek().getWeekDays();
				ArrayList<Day> days = new ArrayList(daysc);
				int i=0;
				while(i<3){
					int rnd=randomGenerator.nextInt(5);
					Day day = days.get(rnd);
					Collections.shuffle((List<?>) day.getTimeSlots());
					i++;
				}
			}
		}
		return fatherTimeTable;
	}

	private static void fixTimeTable() throws IOException{
		timeTableRepository.save(GlobalBestTimetable);
		Collection<ClassRoom> classRooms = GlobalBestTimetable.getRooms();
		Iterator<ClassRoom> classRoomIterator = classRooms.iterator();
		while (classRoomIterator.hasNext()){
			ClassRoom classRoom = classRoomIterator.next();
			Week week = classRoom.getWeek();
			Collection<Day> days = week.getWeekDays();
			Iterator<Day> dayIterator = days.iterator();
			int dayNumber = 0;
			while (dayIterator.hasNext()){
				Day day = dayIterator.next();
				Optional<Day> dayOptional = dayRepository.findById(day.getDayID());
				Day oldDay = dayOptional.get();
				oldDay.setDayName(weekDayNames.get(dayNumber));
				oldDay.setDayNumber(dayNumber);
				dayRepository.save(oldDay);
				dayNumber++;
				Collection<TimeSlot> timeSlotss = day.getTimeSlots();
				Iterator<TimeSlot> timeSlotIterator = timeSlotss.iterator();
				int timeSlotNumber = 0;
				while (timeSlotIterator.hasNext()){
					timeSlotNumber++;
					TimeSlot timeSlot = timeSlotIterator.next();
					Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(timeSlot.getTimeSlotID());
					TimeSlot oldTimeSlot = timeSlotOptional.get();
					oldTimeSlot.setTimeSlotNumber(timeSlotNumber);
					timeSlotRepository.save(oldTimeSlot);
					timeSlot.setTimeSlotNumber(timeSlotNumber);
				}
			}
		}
	}
}