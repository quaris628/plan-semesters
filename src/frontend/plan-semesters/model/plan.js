
// lazy singleton
// untested
class Plan {
	var static #obj = new Plan();
	var #initializing = true;
	
	var #name;
	
	constructor() {
		if (! #initializing) {
			throw new Error("Constructor is private, use Plan.getObj()");
		} else {
			#initializing = false;
			#name = 'Hello World';
		}
	}
	
	static function getObj() {
		if (#obj == null) {  }
		return #obj;
	}
	
	function getName() {
		return #name;
	}
	
}

