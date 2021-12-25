package general;

import java.time.Year;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

import semesters.Season;
import degree.Degree;
import exceptions.LastEnabledSeasonException;
import utils.Args;

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
    private Set<Degree> degrees;
    
    public PlanSettings() {
        this.startingYear = DEFAULT_STARTING_YEAR;
        this.startingSeason = DEFAULT_STARTING_SEASON;
        this.startingCredits = DEFAULT_STARTING_CREDITS;
        this.enabledSeasons = new boolean[] {
        		DEFAULT_ENABLED_WINTER,
        		DEFAULT_ENABLED_SPRING,
				DEFAULT_ENABLED_SUMMER,
				DEFAULT_ENABLED_FALL };
        this.degrees = new HashSet<Degree>();
    }
    
    // Copy Constructor
    public PlanSettings(PlanSettings p) {
    	this.startingYear = p.startingYear;
        this.startingSeason = p.startingSeason;
        this.startingCredits = p.startingCredits;
        this.enabledSeasons = Arrays.copyOf(p.enabledSeasons, p.enabledSeasons.length);        
        this.degrees = new HashSet<Degree>();
        for (Degree degree : p.degrees) {
        	this.degrees.add(degree);
        }
    }

	
	// --------------------------------
	//  Starting Conditions
	// --------------------------------
    
	public int getStartingYear() {
		return startingYear;
	}

	public void setStartingYear(int startingYear) {
		Args.checkNonNegative(startingYear, "startingYear");
		
		if (this.startingYear != startingYear) {
			this.startingYear = startingYear;
			flagChange();
		}
	}

	public Season getStartingSeason() {
		return startingSeason;
	}

	public void setStartingSeason(Season startingSeason) {
		Args.checkNull(startingSeason, "starting season");
		if (!isEnabled(startingSeason)) {
			throw new IllegalArgumentException(
					"Cannot set starting season to a disabled season (" + startingSeason.toString() + ")");
		}
		
		if (this.startingSeason != startingSeason) {
			this.startingSeason = startingSeason;
			flagChange();
		}
	}

	public int getStartingCredits() {
		return startingCredits;
	}
	
	// credits earned prior to the plan not including the credits
	//     of already-satisfied courses 
	public void setStartingCredits(int startingCredits) {
		// negative ok
		if (this.startingCredits != startingCredits) {
			this.startingCredits = startingCredits;
			flagChange();
		}
	}


	// --------------------------------
	//  Enabling Seasons
	// --------------------------------
    
	public void enable(Season season) {
		Args.checkNull(season, "season");
		
		if (enabledSeasons[season.ordinal()] == false) {
			enabledSeasons[season.ordinal()] = true;
			flagChange();
		}
	}
	
	public void disable(Season season) {
		Args.checkNull(season, "season");
		
		if (this.getNearestEnabledAfter(season) == season) {
			throw new LastEnabledSeasonException(season);
		}
		
		if (enabledSeasons[season.ordinal()] == true) {
			enabledSeasons[season.ordinal()] = false;
			flagChange();
			
			if (startingSeason.equals(season)) {
				startingSeason = this.getNearestEnabledAfter(startingSeason);
			}
		}
	}
	
	public boolean isEnabled(Season season) {
		Args.checkNull(season, "season");
		
		return enabledSeasons[season.ordinal()];
	}
	
	public Season getNearestEnabledAfter(Season season) {
		Args.checkNull(season, "season");
		
		Season toReturn = season.getNext();
		while (!enabledSeasons[toReturn.ordinal()] ) {
			toReturn = toReturn.getNext();
		}
		return toReturn;
	}
	
	public Season getNearestEnabledBefore(Season season) {
		Args.checkNull(season, "season");
		
		Season toReturn = season.getPrev();
		while (!enabledSeasons[toReturn.ordinal()] ) {
			toReturn = toReturn.getPrev();
		}
		return toReturn;
	}
	
	
	// --------------------------------
	//  Enabled Seasons
	// --------------------------------
	
	public void addDegree(Degree degree) {
		Args.checkNull(degree, "degree");
		
		if (!degrees.contains(degree)) {
			degrees.add(degree);
			flagChange();
		}
	}
	
	public void removeDegree(Degree degree) {
		Args.checkNull(degree, "degree");
		
		if (degrees.contains(degree)) {
			degrees.remove(degree);
			flagChange();
		}
	}
	
	public Iterable<Degree> getDegrees() {
		return degrees;
	}
	
	public boolean containsDegree(Degree degree) {
		return degrees.contains(degree);
	}
	
	// --------------------------------
	//  Generic Overrides
	// --------------------------------
	
	@Override
	public PlanSettings clone() {
		return new PlanSettings(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((degrees == null) ? 0 : degrees.hashCode());
		result = prime * result + Arrays.hashCode(enabledSeasons);
		result = prime * result + startingCredits;
		result = prime * result + ((startingSeason == null) ? 0 : startingSeason.hashCode());
		result = prime * result + startingYear;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PlanSettings))
			return false;
		PlanSettings other = (PlanSettings) obj;
		if (degrees == null) {
			if (other.degrees != null)
				return false;
		} else if (!degrees.equals(other.degrees))
			return false;
		if (!Arrays.equals(enabledSeasons, other.enabledSeasons))
			return false;
		if (startingCredits != other.startingCredits)
			return false;
		if (startingSeason != other.startingSeason)
			return false;
		if (startingYear != other.startingYear)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		// TODO not very important
		return null;
	}
	
}
