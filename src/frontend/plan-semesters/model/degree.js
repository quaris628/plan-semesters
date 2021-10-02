
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

// temporary, until I get a backend database working
let Degrees = {
	'SE Major': new Degree('SE Major',
			new ReqAnd(
				new ReqCourse(Courses.cs1000),
				new ReqCourse(Courses.cs2000),
				new ReqCourse(Courses.se2000),
				new ReqCourse(Courses.se3000)
			)
		),
	'CS Major': new Degree('CS Major',
			new ReqAnd(
				new ReqCourse(Courses.cs1000),
				new ReqCourse(Courses.cs2000),
				new ReqOr(new ReqCourse(Courses.cs3000), new ReqCourse(Courses.cs3100)),
				new ReqCourse(Courses.cs4000)
			)
		),
}