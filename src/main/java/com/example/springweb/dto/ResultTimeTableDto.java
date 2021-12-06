package com.example.springweb.dto;

import com.example.springweb.models.*;

import java.text.DateFormatSymbols;
import java.util.*;

import static java.util.Comparator.comparingInt;

public class ResultTimeTableDto {

    ArrayList<String> rooms = new ArrayList<>();
    ArrayList<String> daysNames = new ArrayList<>();
    List<String> timings = Arrays.asList("9:00 - 10:30", "10:40 - 12:10", "12:40 - 14:10", "14:20 - 15:50", "16:20 - 17:50", "18:00 - 19:30");
    ArrayList<ArrayList<ArrayList<String>>> pairs = new ArrayList<>();

    public ResultTimeTableDto(TimeTable timeTable){

        String[] weekDaysName = new DateFormatSymbols().getWeekdays();
        for (int i = 1; i < weekDaysName.length; i++) {
            weekDaysName[i] = weekDaysName[i].substring(0, 1).toUpperCase() + weekDaysName[i].substring(1);
            if (!(i == Calendar.SUNDAY))
                daysNames.add(weekDaysName[i]);
        }

        Collection<ClassRoom> classRooms = timeTable.getRooms();
        Iterator<ClassRoom> classRoomIterator = classRooms.iterator();
        while (classRoomIterator.hasNext()) {
            ClassRoom classRoom = classRoomIterator.next();
            rooms.add(classRoom.getRoomName());
            Week week = classRoom.getWeek();
            Collection<Day> days = week.getWeekDays();
            List<Day> daysList = new ArrayList<>(days);
            daysList.sort(comparingInt(Day::getDayNumber));
            Iterator<Day> dayIterator = daysList.iterator();
            ArrayList<ArrayList<String>> roomPairs = new ArrayList<>();
            while (dayIterator.hasNext()) {
                Day day = dayIterator.next();
                Collection<TimeSlot> timeSlots = day.getTimeSlots();
                List<TimeSlot> timeSlotsList = new ArrayList<>(timeSlots);
                timeSlotsList.sort(comparingInt(TimeSlot::getTimeSlotNumber));
                Iterator<TimeSlot> timeSlotIterator = timeSlotsList.iterator();
                ArrayList<String> dayPairs = new ArrayList<>();
                while (timeSlotIterator.hasNext()) {
                    TimeSlot timeSlot = timeSlotIterator.next();
                    Lecture lecture = timeSlot.getLecture();
                    String pair;
                    if (lecture != null) {
                        pair = lecture.getProfessor().getProfessorName() + "<br>" + lecture.getSubject().getSubjectName() + "<br>" + lecture.getStudentGroup().getGroupName().split("/")[0];
                    } else {
                        pair = "-";
                    }
                    dayPairs.add(pair);
                }
                roomPairs.add(dayPairs);
            }
            pairs.add(roomPairs);
        }
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public ArrayList<String> getDaysNames() {
        return daysNames;
    }

    public List<String> getTimings() {
        return timings;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getPairs() {
        return pairs;
    }
}
