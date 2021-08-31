class Course {
    #id;
    #fullName;
    #credits;
    #description;
	#reqs;
	
    constructor(id, fullName, credits, description="", reqs=[]) {
        this.#id = id;
        this.#fullName = fullName;
        this.#credits = credits;
        this.#description = description;
		this.#reqs = reqs;
    }
	
    getId() { return this.#id; }
    getFullName() { return this.#fullName; }
    getCredits() { return this.#credits; }
    getDescription() { return this.#description; }
	getReqs() { return this.#reqs; }
    
}

class CourseDict {
	static dict = new Map();
	static addToDict(course) {
        this.dict.set(course.getId(), course);
    }
}

// temporary manual additions
Course.addToDict(new Course("c1000", "Course One", 3, "The first course"));
Course.addToDict(new Course("c2000", "Course Two", 3, "The second course"));
//Course.addToDict(new Course("c3000", "Course Three", 3, "The third course"));
