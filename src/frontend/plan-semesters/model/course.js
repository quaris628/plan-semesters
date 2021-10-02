class Course {
    #id;
    #fullName;
    #credits;
    #description;
	#req;
	
    constructor(id, fullName, credits, description="", req=null) {
        this.#id = id;
        this.#fullName = fullName;
        this.#credits = credits;
        this.#description = description;
		this.#req = req;
    }
	
    getId() { return this.#id; }
    getFullName() { return this.#fullName; }
    getCredits() { return this.#credits; }
    getDescription() { return this.#description; }
	getReq() { return this.#req; }
    
}

// temporary manual additions
let Courses = {
	'cs1000': new Course("cs1000", "Intro to Programming", 3, "Learn the basics of computer programming. This course is for beginners."),
	'cs2000': new Course("cs2000", "Object-Oriented Programming", 3, "Learn how to use classes and objects in computer programming, and learn good object-oriented design principles.",
			new PreReq('cs1000', 'cs2000')),
	'cs3000': new Course("cs3000", "Computer Networks", 3, "Learn the basics of packet-switched computer networks, from the physical layer to the application layer.",
			new PreReq('cs2000', 'cs3000')),
	'cs3100': new Course("cs3100", "Operating Systems", 3, "Learn the basics of operating systems and their components.",
			new PreReq('cs2000', 'cs3100')),
	'cs4000': new Course("cs4000", "Intro to Computer Security", 3, "Learn the basics of computer security, including common types of attacks and countermeasures.",
			new ReqOr(new PreReq('cs3000', 'cs4000'), new PreReq('cs3100', 'cs4000'))),
	'se2000': new Course("se2000", "Intro to Software Engineering", 3, "Learn the basics of the software development process. Practice the waterfall development strategy.",
			new PreReq('cs1000', 'se2000')),
	'se3000': new Course("se3000", "Intermediate Software Engineering", 3, "Learn more advanced software development strategies. Practice the agile development strategy.",
			new ReqAnd(new PreReq('se2000', 'se3000'), new PreReq('cs2000', 'se3000')))
}
