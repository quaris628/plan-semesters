
class Degree {
	_name;
	_req;
	
	constructor(name, req) {
		this._name = name;
		this._req = req;
	}
	
	getName() { return this._name; }
	getReq() { return this._req; }
	
	getAllCourses() { return this._req.getAllCourses(); }
	
	
	getCourseSemester(course) {
		// todo
	}
}

let Degrees = {
	'SE Major': new Degree('SE Major',
			new ReqAnd(
				new ReqCourse(Courses.cs1000),
				new ReqCourse(Courses.cs2000),
				new ReqCourse(Courses.se2000),
				new ReqCourse(Courses.se3000)
			)
		),
}