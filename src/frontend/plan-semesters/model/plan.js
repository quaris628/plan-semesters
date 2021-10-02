

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
	
	_degrees;
	_uniqueCourses;
	
	constructor() {
		this._degrees = [];
		this._uniqueCourses = new UniqueCourses();
	}
	
	addDegree(degree) {
		this._degrees.push(degree);
		this._uniqueCourses.addDegreeCourses(degree);
	}
	
	removeDegree(degree) {
		// remove degree from list
		let index = this._degrees.indexOf(degree);
		this._degrees.splice(index, 1);
		
		// clear unique courses
		this._uniqueCourses.clear();
		
		// re-generate unique courses
		for (let i = 0; i < this._degrees.length; i++) {
			this._uniqueCourses.addDegreeCourses(this._degrees[i]);
		}
		
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

