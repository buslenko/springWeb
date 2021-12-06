package com.example.springweb.controllers;

import com.example.springweb.dao.repositories.*;
import com.example.springweb.models.*;
import com.example.springweb.dto.*;
import com.example.springweb.util.Initialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class HTMLController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CombinationRepository combinationRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private TimeTableRepository timeTableRepository;

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @RequestMapping("/form")
    public String createTableForm (Model model){

        FormCreationDto formCreationDto = new FormCreationDto();

        model.addAttribute("classRooms", formCreationDto.getClassRooms());
        model.addAttribute("subjects", formCreationDto.getSubjects());
        model.addAttribute("professors", formCreationDto.getProfessors());
        model.addAttribute("courses", formCreationDto.getCourses());

        return "form";
    }

    @PostMapping("/generate")
    public String generateTimeTable (@ModelAttribute ClassRoomCreationDto classRoomCreationDto,
                                     @ModelAttribute SubjectCreationDto subjectCreationDto,
                                     @ModelAttribute ProfessorCreationDto professorCreationDto,
                                     @ModelAttribute CourseCreationDto courseCreationDto,
                                     Model model) throws IOException {

        subjectCreationDto.refactor();
        classRoomCreationDto.refactor();

        ArrayList<ClassRoom> classRooms = (ArrayList<ClassRoom>) classRoomCreationDto.getClassRooms();
        ArrayList<Subject> subjects = (ArrayList<Subject>) subjectCreationDto.getSubjects();
        ArrayList<Professor> professors = (ArrayList<Professor>) professorCreationDto.getProfessors();
        ArrayList<Course> courses = (ArrayList<Course>) courseCreationDto.getCourses();

        Initialization initialization = new Initialization();
        initialization.setClassRooms(classRooms);
        initialization.setAllSubjects(subjects);
        initialization.setProfessors(professors);
        initialization.setCourses(courses);
        initialization.setClassRoomRepository(classRoomRepository);
        initialization.setSubjectRepository(subjectRepository);
        initialization.setProfessorRepository(professorRepository);
        initialization.setCourseRepository(courseRepository);
        initialization.setCombinationRepository(combinationRepository);
        initialization.setTimeTableRepository(timeTableRepository);
        initialization.setTimeSlotRepository(timeSlotRepository);
        initialization.setDayRepository(dayRepository);
        initialization.setLectureRepository(lectureRepository);
        TimeTable resultTimeTable = initialization.readInput();

        int resultID = resultTimeTable.getTimeTableID();

        model.addAttribute("resultID", resultID);

        return "result";
    }

    @GetMapping("/timetable")
    public String getTimeTableByID (@RequestParam int id, Model model){

        Optional<TimeTable> timeTableOptional = timeTableRepository.findById(id);

        if (timeTableOptional.isPresent()) {

            ResultTimeTableDto resultTimeTableDto = new ResultTimeTableDto(timeTableOptional.get());

            model.addAttribute("rooms", resultTimeTableDto.getRooms());
            model.addAttribute("days", resultTimeTableDto.getDaysNames());
            model.addAttribute("timings", resultTimeTableDto.getTimings());
            model.addAttribute("pairs", resultTimeTableDto.getPairs());

            return "timetable";

        } else{

            String error = "введён неправильный id";

            model.addAttribute("error", error);

            return "error";

        }
    }
}
