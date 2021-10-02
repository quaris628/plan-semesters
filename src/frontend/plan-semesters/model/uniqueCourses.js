
// special value for the semester number when a course is unplanned
const UNPLANNED_CODE = -1;
// special value for the semester number when a course is satisfied (currently unused)
const SATISFIED_CODE = 0;


// this class manages the list of required courses in the plan.
// Courses may not be duplicated in the list; i.e. every entry in the list is unique.
class UniqueCourses {
	_courses; // update view whenever this is updated
	
	constructor() {
		this._courses = {};
	}
	
	add(course) {
		if (this._courses[course.getId()] === undefined) {
			this._courses[course.getId()] = UNPLANNED_CODE;
			// update view
			MyPlanView.getInstance().addCourse(course)
		}
	}
	
	addDegreeCourses(degree) {
		let allCourses = degree.getAllCourses();
		for (let i = 0; i < allCourses.length; i++) {
			this.add(allCourses[i]);
		}
	}
	
	clear() {
		this._courses = {};
		
		// update view
		MyPlanView.getInstance().clearCourses()
	}
	
	getCourses() {
		return _courses;
	}
}