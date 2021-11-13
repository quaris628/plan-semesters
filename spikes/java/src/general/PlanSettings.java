package general;

import java.time.Year;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import semesters.Season;
import degree.Degree;

/**
 * 
 * @author Quaris
 *
 */
public class PlanSettings extends ChangeFlaggable implements Cloneable {
    
    public static final int DEFAULT_STARTING_YEAR = Year.now().getValue();
    public static final Season DEFAULT_STARTING_SEASON = Season.FALL;
    public static final int DEFAULT_STARTING_CREDITS = 0;
    public static final boolean DEFAULT_ENABLED_WINTER = false;
    public static final boolean DEFAULT_ENABLED_SPRING = true;
	public static final boolean DEFAULT_ENABLED_SUMMER = false;
	public static final boolean DEFAULT_ENABLED_FALL = true;
    
    private int startingYear;
	private Season startingSeason;
    private int startingCredits;
    private boolean[] enabledSeasons;
    private List<Degree> degrees;
    
    public PlanSettings() {
        this.startingYear = DEFAULT_STARTING_YEAR;
        this.startingSeason = DEFAULT_STARTING_SEASON;
        this.startingCredits = DEFAULT_STARTING_CREDITS;
        this.enabledSeasons = new boolean[] {
        		DEFAULT_ENABLED_WINTER,
        		DEFAULT_ENABLED_SPRING,
				DEFAULT_ENABLED_SUMMER,
				DEFAULT_ENABLED_FALL };
        this.degrees = new LinkedList<Degree>();
    }
    
    // Copy Constructor
    public PlanSettings(PlanSettings p) {
    	this.startingYear = p.startingYear;
        this.startingSeason = p.startingSeason;
        this.startingCredits = p.startingCredits;
        this.enabledSeasons = Arrays.copyOf(p.enabledSeasons, p.enabledSeasons.length);        
        this.degrees = new LinkedList<Degree>();
        for (Degree degree : p.degrees) {
        	this.degrees.add(degree);
        }
    }

	public int getStartingYear() {
		return startingYear;
	}

	public void setStartingYear(int startingYear) {
		if (startingYear < 0) {
			throw new IllegalArgumentException(
					"Invalid staring year: " + String.valueOf(startingYear));
		}
		if (this.startingYear != startingYear) {
			this.startingYear = startingYear;
			flagChange();
		}
	}

	public Season getStartingSeason() {
		return startingSeason;
	}

	public void setStartingSeason(Season startingSeason) {
		if (startingSeason == null) {
			throw new IllegalArgumentException(
					"Cannot set starting season to null");
		}
		if (this.startingSeason != startingSeason) {
			this.startingSeason = startingSeason;
			flagChange();
		}
	}

	public int getStartingCredits() {
		return startingCredits;
	}
	
	public void setStartingCredits(int startingCredits) {
		if (startingCredits < 0) {
			throw new IllegalArgumentException(
					"Credits must be >= 0, was passed " + String.valueOf(startingCredits));
		}
		if (this.startingCredits != startingCredits) {
			this.startingCredits = startingCredits;
			flagChange();
		}
	}

	public void enable(Season season) {
		if (season == null) {
			throw new IllegalArgumentException(
					"Cannot enable a null season");
		}
		if (enabledSeasons[season.ordinal()] == false) {
			enabledSeasons[season.ordinal()] = true;
			flagChange();
		}
	}
	
	public void disable(Season season) {
		if (season == null) {
			throw new IllegalArgumentException(
					"Cannot disable a null season");
		}
		if (enabledSeasons[season.ordinal()] == true) {
			enabledSeasons[season.ordinal()] = false;
			flagChange();
		}
	}
	
	public boolean isEnabled(Season season) {
		if (season == null) {
			throw new IllegalArgumentException("Season was null");
		}
		return enabledSeasons[season.ordinal()];
	}
	
	public Season getNearestEnabledAfter(Season season) {
		if (season == null) {
			throw new IllegalArgumentException("Season was null");
		}
		Season toReturn = season.getNext();
		while (!enabledSeasons[toReturn.ordinal()] ) {
			toReturn = toReturn.getNext();
		}
		return toReturn;
	}
	
	public Season getNearestEnabledBefore(Season season) {
		if (season == null) {
			throw new IllegalArgumentException("Season was null");
		}
		Season toReturn = season.getPrev();
		while (!enabledSeasons[toReturn.ordinal()] ) {
			toReturn = toReturn.getPrev();
		}
		return toReturn;
	}

	public void addDegree(Degree degree) {
		if (degree == null) {
			throw new IllegalArgumentException("Cannot add a null degree");
		}
		if (!degrees.contains(degree)) {
			degrees.add(degree);
			flagChange();
		}
	}
	
	public void removeDegree(Degree degree) {
		if (degree == null) {
			throw new IllegalArgumentException("Cannot remove a null degree");
		}
		if (degrees.contains(degree)) {
			degrees.remove(degree);
			flagChange();
		}
	}
	
	public Iterator<Degree> getDegrees() {
		return degrees.iterator();
	}
	
	@Override
	public PlanSettings clone() {
		return new PlanSettings(this);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO
		return false;
	}
	
	@Override
	public String toString() {
		// TODO
		return null;
	}
	
}
