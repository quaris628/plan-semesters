
// Requirement that a course must be completed before another course

class PreReq {
	#preReqCourse;
	#forCourse;
	#comments;
	
	constructor(preReqCourse, forCourse, comments) {
		this.#preReqCourse = preReqCourse;
		this.#forCourse = forCourse;
		this.#comments = comments;
	}
	
	isSatisfied(plan) {
		let preReqSemester = plan.getCourseSemester(this.#preReqCourse);
		let courseSemester = plan.getCourseSemester(this.#forCourse);
		return preReqSemester < courseSemester && preReqSemester >= 0;
	}
	
	getComments() { return this.#comments; }
}

// Requirement that a course must be taken at the same time as another course

class CoReq {
	#coReqCourse;
	#forCourse;
	#comments;
	
	constructor(coReqCourse, forCourse, comments) {
		this.#coReqCourse = coReqCourse;
		this.#forCourse = forCourse;
		this.#comments = comments;
	}
	
	isSatisfied(plan) {
		let coReqSemester = plan.getCourseSemester(this.#coReqCourse);
		let courseSemester = plan.getCourseSemester(this.#forCourse);
		return coReqSemester <= courseSemester && coReqSemester >= 0;
	}
	
	getComments() { return this.#comments; }
}
