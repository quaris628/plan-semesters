// Requirement that a single course must be taken
// Example:
//     	let cs1000 = new Course(...);
//     	let degreeReq1 = new ReqCourse(cs1000, "C- or better");

class ReqCourse {
	
	constructor(course, comments) {
		this._course = course;
		this._comments = comments;
	}
	
	isSatisfied(plan) {
		return plan.getCourses().Contains(this._course)
			&& plan.getCourseSemester(this._course) >= 0;
	}
	
	getAllCourses() {
		return [this._course];
	}
}

// Meta-requirement that ALL requirements in the collection must be satisfied
// Example:
//     	let r1 = new ReqCourse(cs1000);
//      let r2 = new ReqCourse(cs2000);
// 		let req = new ReqAnd(r1, r2);
class ReqAnd {
	
	constructor() {
		this._reqs = arguments;
	}
	
	isSatisfied(plan) {
		for (let i = 0; i < this._reqs.length; i++) {
			if (this._reqs[i].isSatisfied(plan) === false) { return false; }
		}
		return true;
	}
	
	getAllCourses() {
		let toReturn = [];
		for (let i = 0; i < this._reqs.length; i++) {
			toReturn = toReturn.concat(this._reqs[i].getAllCourses());
		}
		return toReturn;
	}
}


// Meta-requirement that AT LEAST ONE requirement in the collection must be satisfied
// Example:
//     	let r1 = new ReqCourse(cs1000);
//      let r2 = new ReqCourse(cs2000);
// 		let req = new ReqOr(r1, r2);
class ReqOr {
	
	constructor() {
		this._reqs = arguments;
	}
	
	isSatisfied(plan) {
		for (let i = 0; i < this._reqs.length; i++) {
			if (this._reqs[i].isSatisfied(plan) === true) { return true; }
		}
		return false;
	}
	
	getAllCourses() {
		let toReturn = [];
		for (let i = 0; i < this._reqs.length; i++) {
			toReturn = toReturn.concat(this._reqs[i].getAllCourses());
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
class ReqQuota {
	
	constructor() {
		this._quota = n;
		this._reqs = arguments;
	}
	
	isSatisfied(plan) {
		let numSat = 0;
		for (let i = 1; i < this._reqs.length; i++) {
			if (this._reqs[i].isSatisfied(plan) === true) {
				numSat++;
				if (numSat >= this._quota) { return true; }
			}
		}
		return false;
	}
	
	getAllCourses() {
		let toReturn = [];
		for (let i = 0; i < this._reqs.length; i++) {
			toReturn = toReturn.concat(this._reqs[i].getAllCourses());
		}
		return toReturn;
	}
}


// Meta-Course Requirement that AT LEAST n credits of requirements in the collection must be satisfied
// Example:
//     	let r1 = new ReqCourse(cs1000); // 3 credits
//      let r2 = new ReqCourse(cs2000); // 3 credits
//      let r3 = new ReqCourse(cs2000); // 4 credits
// 		let req = new ReqCreditsQuota(4, r1, r2, r3); // must take at least 4 credits out of r1, r2, r3
class ReqCreditsQuota {
	
	constructor(plan) {
		this._quota = n;
		this._courses = arguments;
	}
	
	isSatisfied(plan) {
		let numSatCredits = 0;
		for (let i = 1; i < this._courses.length; i++) {
			if (this._courses[i].isSatisfied(plan) === true) {
				numSatCredits+= this._courses[i].getCredits(); // may need to edit name getCredits
				if (numSatCredits >= this._quota) { return true; }
			}
		}
		return false;
	}
	
	getAllCourses() {
		let toReturn = [];
		for (let i = 0; i < this._courses.length; i++) {
			toReturn = toReturn.concat(this._courses[i].getAllCourses());
		}
		return toReturn;
	}
	
}
