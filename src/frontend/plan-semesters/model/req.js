// Requirement that a single course must be taken
// Example:
//     	let cs1000 = new Course('cs1000', 'Intro to Programming', 3);
//     	let degreeReq1 = new ReqCourse(cs1000, "C- or better");
function ReqCourse(course, comments) {
	this.#course = course;
	this.#comments = comments;
	this.isSatisfied = (plan) => {
		// may need to revise later
		if (plan.GetCourses().Contains(#course)) {
			plan; // ??? test if course is being taken in an ok semester
		} else {
			return false;
		}
		
		this.#course
	}
	
	this.getAllCourses() = () => {
		return [this.course];
	}
	
	this.getCourse = () => { return #course; }
	this.getComments = () => { return #comments; }
}


// Meta-requirement that ALL requirements in the collection must be satisfied
// Example:
//     	let r1 = new ReqCourse(cs1000);
//      let r2 = new ReqCourse(cs2000);
// 		let req = new ReqAnd(r1, r2);
function ReqAnd() {
	this.#reqs = arguments;
	
	this.isSatisfied = (plan) => {
		for (let i = 0; i < #reqs.length; i++) {
			if (#reqs[i].isSatisfied(plan) === false) { return false; }
		}
		return true;
	}
	
	this.getAllCourses() = () => {
		let toReturn = [];
		for (let i = 0; i < #reqs.length; i++) {
			toReturn.concat(#reqs[i].getAllCourses());
		}
		return toReturn;
	}
}


// Meta-requirement that AT LEAST ONE requirement in the collection must be satisfied
// Example:
//     	let r1 = new ReqCourse(cs1000);
//      let r2 = new ReqCourse(cs2000);
// 		let req = new ReqOr(r1, r2);
function ReqOr() {
	this.#reqs = arguments;
	
	this.isSatisfied = (plan) => {
		for (let i = 0; i < #reqs.length; i++) {
			if (#reqs[i].isSatisfied(plan) === true) { return true; }
		}
		return false;
	}
	
	this.getAllCourses() = () => {
		let toReturn = [];
		for (let i = 0; i < #reqs.length; i++) {
			toReturn.concat(#reqs[i].getAllCourses());
		}
		return toReturn;
	}
}


// Meta-requirement that AT LEAST N requirements in the collection must be satisfied
// Example:
//     	let r1 = new ReqCourse(cs1000);
//      let r2 = new ReqCourse(cs2000);
//      let r3 = new ReqCourse(cs2000);
// 		let req = new ReqQuota(2, r1, r2, r3); // must take at least 2 of r1, r2, r3
function ReqQuota(n) {
	this.#quota = n;
	this.#reqs = arguments;
	
	this.isSatisfied = (plan) => {
		let numSat = 0;
		for (let i = 1; i < #reqs.length; i++) {
			if (#reqs[i].isSatisfied(plan) === true) {
				numSat++;
				if (numSat >= #quota) { return true; }
			}
		}
		return false;
	}
	
	this.getAllCourses() = () => {
		let toReturn = [];
		for (let i = 0; i < #reqs.length; i++) {
			toReturn.concat(#reqs[i].getAllCourses());
		}
		return toReturn;
	}
}


// Meta-Course Requirement that AT LEAST n credits of requirements in the collection must be satisfied
// Example:
//     	let r1 = new ReqCourse(cs1000); // 3 credits
//      let r2 = new ReqCourse(cs2000); // 3 credits
//      let r3 = new ReqCourse(cs2000); // 4 credits
// 		let req = new ReqQuota(4, r1, r2, r3); // must take at least 4 credits out of r1, r2, r3
function ReqCreditsQuota(n) {
	this.#quota = n;
	this.#reqs = arguments;
	
	this.isSatisfied = (plan) => {
		let numSatCredits = 0;
		for (let i = 1; i < #reqs.length; i++) {
			if (#reqs[i].isSatisfied(plan) === true) {
				numSatCredits+= #reqs[i].getCredits(); // may need to edit name getCredits
				if (numSatCredits >= #quota) { return true; }
			}
		}
		return false;
	}
	
	this.getAllCourses() = () => {
		let toReturn = [];
		for (let i = 0; i < #reqs.length; i++) {
			toReturn.concat(#reqs[i].getAllCourses());
		}
		return toReturn;
	}
}
