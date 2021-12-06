function setRooms(){
    let roomNumber = document.getElementById("roomNumber").value;
    let input = "";
    for (let i = 0; i < roomNumber; i++){
        input += '           <div class="row" style="margin-top: 10px">\n' +
            '                    <div class="col-md-2" style="font-size: larger; margin-top: 25px; width: 175px">\n' +
            '                        <label class="form-label">Кабинет ' + (i + 1) + '</label>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-1" style="width: 150px">\n' +
            '                        <label for="classRooms' + i + '.roomName" class="form-label">Номер кабинета</label>\n' +
            '                        <input type="text" class="form-control" id="classRooms' + i + '.roomName" name="classRooms[' + i + '].roomName" style="width: 70px" required>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-2">\n' +
            '                        <label for="classRooms' + i + '.department" class="form-label">Кафедра</label>\n' +
            '                        <input type="text" class="form-control" id="classRooms' + i + '.department" name="classRooms[' + i + '].department" required>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-2" style="width: 150px">\n' +
            '                        <label for="classRooms' + i + '.size" class="form-label">Количество мест</label>\n' +
            '                        <input type="number" class="form-control" id="classRooms' + i + '.size" name="classRooms[' + i + '].size" style="width: 50px" required>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-1" style="width: 165px">\n' +
            '                        <label for="classRooms' + i + '.type" class="form-label">Тип</label>\n<br>' +
            '                        <select class="form-select" id="classRooms' + i + '.type" name="classRooms[' + i + '].type" required>\n' +
            '                            <option selected value="true">Для практик</option>\n' +
            '                            <option value="false">Для лекций</option>\n' +
            '                        </select>\n' +
            '                    </div>' +
            '                </div>';
    }
    let rooms = document.getElementById("rooms");
    rooms.innerHTML = input;
}

function setSubjects(){
    let subjectNumber = document.getElementById("subjectNumber").value;
    let input = "";
    for (let i = 0; i < subjectNumber; i++){
        input += '           <div class="row" style="margin-top: 10px">\n' +
            '                    <div class="col-md-2" style="font-size: larger; margin-top: 25px; width: 175px">\n' +
            '                        <label class="form-label">Предмет ' + (i + 1) + '</label>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-3">\n' +
            '                        <label for="subjects' + i + '.subjectName" class="form-label">Название предмета</label>\n' +
            '                        <input type="text" class="form-control" id="subjects' + i + '.subjectName" name="subjects[' + i + '].subjectName" required>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-2">\n' +
            '                        <label for="subjects' + i + '.department" class="form-label">Кафедра</label>\n' +
            '                        <input type="text" class="form-control" id="subjects' + i + '.department" name="subjects[' + i + '].department" required>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-2" style="width: 150px">\n' +
            '                        <label for="subjects' + i + '.numberOfLecturesPerWeek" class="form-label">Количество пар</label>\n' +
            '                        <input type="number" class="form-control" id="subjects' + i + '.numberOfLecturesPerWeek" name="subjects[' + i + '].numberOfLecturesPerWeek" style="width: 50px" required>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-1" style="width: 145px">\n' +
            '                        <label for="subjects' + i + '.type" class="form-label">Тип</label>\n<br>' +
            '                        <select class="form-select" id="subjects' + i + '.type" name="subjects[' + i + '].type" required>\n' +
            '                            <option selected value="true">Практика</option>\n' +
            '                            <option value="false">Лекция</option>\n' +
            '                        </select>\n' +
            '                    </div>' +
            '                </div>';
    }
    let subjects = document.getElementById("subjects");
    subjects.innerHTML = input;
}

function setProfessors() {
    let professorNumber = document.getElementById("professorNumber").value;
    let input = "";
    for (let i = 0; i < professorNumber; i++){
        input += '           <div class="row" style="margin-top: 10px">\n' +
            '                    <div class="col-md-2" style="font-size: larger; margin-top: 25px; width: 175px">\n' +
            '                        <label class="form-label">Преподаватель ' + (i + 1) + '</label>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-3">\n' +
            '                        <label for="professors' + i + '.professorName" class="form-label">Имя преподавателя</label>\n' +
            '                        <input type="text" class="form-control" id="professors' + i + '.professorName" name="professors[' + i + '].professorName" required>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-2">\n' +
            '                        <label for="professors' + i + '.subjects" class="form-label">Предметы через запятую</label>\n' +
            '                        <input type="text" class="form-control" id="professors' + i + '.subjects" name="professors[' + i + '].subjects" required>\n' +
            '                    </div>\n' +
            '                </div>';
    }
    let professors = document.getElementById("professors");
    professors.innerHTML = input;
}

function setCourses() {
    let courseNumber = document.getElementById("courseNumber").value;
    let input = "";
    for (let i = 0; i < courseNumber; i++){
        input += '           <div class="row" style="margin-top: 10px">\n' +
            '                    <div class="col-md-2" style="font-size: larger; margin-top: 25px; width: 175px">\n' +
            '                        <label class="form-label">Группа ' + (i + 1) + '</label>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-3">\n' +
            '                        <label for="courses' + i + '.courseName" class="form-label">Название группы</label>\n' +
            '                        <input type="text" class="form-control" id="courses' + i + '.courseName" name="courses[' + i + '].courseName" required>\n' +
            '                    </div>\n' +
            '                    <div class="col-md-3">\n' +
            '                        <label for="courses' + i + '.subjects" class="form-label">Предметы через запятую</label>\n' +
            '                        <input type="text" class="form-control" id="courses' + i + '.subjects" name="courses[' + i + '].subjects" required>\n' +
            '                    </div>\n' +
            '                </div>';
    }
    let courses = document.getElementById("courses");
    courses.innerHTML = input;
}