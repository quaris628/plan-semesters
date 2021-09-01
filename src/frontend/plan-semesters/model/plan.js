

// lazy singleton
var MyPlan = (function () {
	var instance;
	
	return {
		getInstance: () => {
			if (!instance) {
				instance = new Plan();
			}
			return instance;
		}
	}
	
})()



class Plan {
	
	constructor() {
		this._degrees = [];
		this._uniqueCourses = {};
	}
	
	addDegree(degree) {
		let allCourses = degree.getAllCourses();
		for (let i = 0; i < allCourses.length; i++) {
			if (this._uniqueCourses[allCourses[i].getId()] === undefined) {
				this._uniqueCourses[allCourses[i].getId()] = -1;
			}
		}
		this._degrees.push(degree);
		
		// todo: update view to reflect change in the course list
	}
	
	removeDegree(degree) {
		let index = this._degrees.indexOf(degree);
		this._degrees.splice(index, 1);
		
		// re-generate unique courses
		this._uniqueCourses = {};
		for (let i = 0; i < this._degrees.length; i++) {
			addDegree(this._degrees[i]);
		}
		
		// todo: update view to reflect change in the course list
	}
	
	// checks for validity should be done before calling this function
	take(course, semester) {
		this._uniqueCourses[course] = semester;
		// todo: update view when function is run
	}
	
	// return true iff semester is a valid time to take course
	canTake(course, semester) {
		course.getPreReqs();
		// todo
	}
	
}

