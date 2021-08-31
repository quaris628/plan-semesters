
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
	
})()

function PlanView() {
	this.#unplannedTable = 
	
}
