

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


function Plan() {
	this.#degrees = [];
	this.#uniqueCourses = {};
	
	function addDegree(degree) {
		let allCourses = degree.getAllCourses();
		for (int i = 0; i < allCourses.length; i++) {
			if (#uniqueCourses[allCourses[i]] != undefined) {
				#uniqueCourses[allCourses[i]] = Semester.Unplanned;
			}
		}
		#degrees.push(degree);
	}
	function removeDegree(degree) {
		#degrees.find(item => item.name === degree.name); // test me
	}
	
	// checks for validity should be done before calling this function
	function take(course, semester) {
		#uniqueCourses[course] = semester;
		// todo: update view when function is run
	}
	
	// return true iff semester is a valid time to take course
	function canTake(course, semester) {
		course.getPreReqs();
	}
}



class Plan {
	
	var #name;
	
	function getName() {
		return #name;
	}
	
}

