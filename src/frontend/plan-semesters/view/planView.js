
// lazy singleton
var MyPlanView = (function () {
	var instance;
	
	return {
		getInstance: () => {
			if (!instance) {
				instance = new PlanView();
			}
			return instance;
		}
	}
	
})();

const _unplannedTable = document.getElementById("plannerTableUnplanned");
const _planTable = document.getElementById("plannerTableMain");
const _reqsList = document.getElementById("reqList") // unordered list

class PlanView {
	
	constructor() {
		
	}
	
	
	// courses list management
	
	addCourse(course) {
		this._uniqueCourses.push(course);
		
		let courseLi = document.createElement("li");
		courseLi.setAttribute("id", course.getId());
		courseLi.innerText = course.getFullName();
		_reqsList.appendChild(courseLi)
		
		// TODO unplanned, planner tables
		
	}
	
	clearCourses() {
		this._uniqueCourses = [];
		
		// clear reqs list
		while (_reqsList.hasChildNodes()) {
			_reqsList.removeChild(_reqsList.firstChild);
		}
		
		// TODO unplanned, planner tables
		
	}
	
	// TODO more functions will be in this class later
	
}