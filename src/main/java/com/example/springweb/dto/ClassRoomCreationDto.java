package com.example.springweb.dto;

import com.example.springweb.models.ClassRoom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClassRoomCreationDto {

    private List<ClassRoom> classRooms;

    public ClassRoomCreationDto(List<ClassRoom> classRooms) {
        this.classRooms = classRooms;
    }

    public ClassRoomCreationDto() {
        this.classRooms = classRooms = new ArrayList<>();
    }

    public void addClassRoom(ClassRoom classRoom) {
        this.classRooms.add(classRoom);
    }

    public List<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(List<ClassRoom> classRooms) {
        this.classRooms = classRooms;
    }

    public void refactor(){
        Iterator<ClassRoom> classRoomIterator = classRooms.iterator();
        while (classRoomIterator.hasNext()){
            ClassRoom classRoom = classRoomIterator.next();
            if (classRoom.getType().equals("true")){
                classRoom.setLaboratory(true);
            } else {
                classRoom.setLaboratory(false);
            }
        }
    }
}