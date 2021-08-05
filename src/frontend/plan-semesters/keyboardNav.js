
document.addEventListener('keydown', keyDown, false);
var lastFocus = undefined;

function keyDown(event) {
    if (document.activeElement.className == "course")
    {
        let course = document.activeElement;
        let tr = course.parentNode.parentNode.parentNode;
        switch (event.key) {
            case "ArrowUp": CourseUp(tr); break;
            case "ArrowDown": CourseDown(tr); break;
            case "ArrowLeft": CourseLeft(course, tr); break;
            case "ArrowRight": CourseRight(course, tr); break;
            case "Enter" : setCourseDetailsById(course.id); break;
            case "Escape" : lastFocus = course; course.blur(); clearCourseDetails(); break;
            default: break;
        }
    }
    else if (event.key.substring(0, 5) == "Arrow") {
        if (lastFocus == undefined) {
            document.getElementsByClassName("course")[0].focus();
        } else {
            lastFocus.focus();
        }
        
        
    }
    
}

function CourseUp(tr) {
    let row = tr.rowIndex - 1;
    if (row > 0) {
        // find next-up course and focus it
        tr.parentNode.getElementsByClassName("course")[row - 1].focus();
    } else {
        let courses = tr.parentNode.getElementsByClassName("course");
        courses[courses.length - 1].focus();
    }

}

function CourseDown(tr) {
    let row = tr.rowIndex - 1;
    let numRows = tr.parentNode.rows.length; // counts all <tr> elements inside <tbody></tbody>
    if (row < numRows - 1) {
        // find next-down course and focus it
        tr.parentNode.getElementsByClassName("course")[row + 1].focus();
    } else {
        tr.parentNode.getElementsByClassName("course")[0].focus();
    }

}

function CourseLeft(course, tr) {
    let rowCells = Array.from(tr.children);
    let focusCell = course.parentNode.parentNode;
    let focusIndex = rowCells.findIndex((element) => element == focusCell);
    if (focusIndex > 0) {
        rowCells[focusIndex - 1].children[0].appendChild(course); // .div ?
    } else {
        rowCells[rowCells.length - 1].children[0].appendChild(course); // .div ?
    }
    course.focus();
}

function CourseRight(course, tr) {
    let rowCells = Array.from(tr.children);
    let focusCell = course.parentNode.parentNode;
    let focusIndex = rowCells.findIndex((element) => element == focusCell);
    if (focusIndex < tr.cells.length - 1) {
        rowCells[focusIndex + 1].children[0].appendChild(course); // div ?
    } else {
        
        rowCells[0].children[0].appendChild(course);
    }
    course.focus();
}