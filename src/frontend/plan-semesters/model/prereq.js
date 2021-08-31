
// Requirement that a course must be taken at the same time as another course

class PreReq {
	#reqCourse
}

class CoReq {
	#course;
	#comments;
	constructor(course, comments) {
		this.#course = course;
		this.#comments = comments;
	}
	
	isSatisfiedFor(course, plan) {
		let coReqSemester = plan.getCourseSemester(this.#course);
		let courseSemester = plan.getCourseSemester(course);
		return coReqSemester <= courseSemester;
	}
	
	
}
