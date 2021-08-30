
function setCourseDetailsById(id) {
    let course = Course.dict.get(id);
    if (course != undefined) {
        setCourseDetails(course);
    } else {
        clearCourseDetails();
    }
}

function setCourseDetails(course) {
    let element = document.getElementById("courseDetails");
    element.children.name.innerHTML = course.getFullName();
    element.children.desc.innerHTML = course.getDescription();
    element.children.credits.innerHTML = course.getCredits();
    element.style.display = "block";
}

function clearCourseDetails() {
    let element = document.getElementById("courseDetails");
    element.children.name.innerHTML = "";
    element.children.desc.innerHTML = "";
    element.children.credits.innerHTML = "";
    element.style.display = "none";
}
