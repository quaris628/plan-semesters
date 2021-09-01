

function toggleDegree(degreeCheckbox) {
	if (Degrees[degreeCheckbox.id] != undefined) {
		if (degreeCheckbox.checked) { // add
			MyPlan.getInstance().addDegree(Degrees[degreeCheckbox.id]);
		} else { // remove
			MyPlan.getInstance().removeDegree(Degrees[degreeCheckbox.id]);
		}
	}
}

