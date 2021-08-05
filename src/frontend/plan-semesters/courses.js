class Course {
    #id;
    #fullName;
    #credits;
    #description;
    constructor(id, fullName, credits, description) {
        this.#id = id;
        this.#fullName = fullName;
        this.#credits = credits;
        this.#description = description;
    }
    getId() { return this.#id; }
    getFullName() { return this.#fullName; }
    getCredits() { return this.#credits; }
    getDescription() { return this.#description; }
    static dict = new Map();
    static addToDict(course) {
        this.dict.set(course.getId(), course);
    }
}

Course.addToDict(new Course("c1000", "Course One", 3, "The first course"));
Course.addToDict(new Course("c2000", "Course Two", 3, "The second course"));
//Course.addToDict(new Course("c3000", "Course Three", 3, "The third course"));

function courseClick(id) {
    setCourseDetailsById(id);
    document.getElementById(id).focus();
}

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
    element.children.credits.innerHTML = course.getCredits() == 1 ? "1 credit" : course.getCredits() + " credits";
    element.style.display = "block";
}

function clearCourseDetails() {
    let element = document.getElementById("courseDetails");
    element.children.name.innerHTML = "";
    element.children.desc.innerHTML = "";
    element.children.credits.innerHTML = "";
    element.style.display = "none";
}