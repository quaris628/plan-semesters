import java.time.Year;


public class PlanSettings {
    
    public static final int DEFAULT_STARTING_YEAR = Year.now().getValue();
    public static final Season DEFAULT_STARTING_SEASON = Season.FALL;
    public static final int DEFAULT_STARTING_CREDITS = 0;
    
    public static final boolean DEFAULT_ENABLED_SPRING = true;
	public static final boolean DEFAULT_ENABLED_SUMMER = false;
	public static final boolean DEFAULT_ENABLED_FALL = true;
	public static final boolean DEFAULT_ENABLED_WINTER = false;
    
    private int startingYear;
    private Season startingSeason;
    private int startingCredits;
    
    private boolean[] enabledSeasons;
    
    private CourseList satisfied;
    
    public PlanSettings() {
        startingYear = DEFAULT_STARTING_YEAR;
        startingSeason = DEFAULT_STARTING_SEASON;
        startingCredits = DEFAULT_STARTING_CREDITS;
        
        enabledSeasons = new boolean[] {
				DEFAULT_ENABLED_SPRING,
				DEFAULT_ENABLED_SUMMER,
				DEFAULT_ENABLED_FALL,
				DEFAULT_ENABLED_WINTER };
        
        satisfied = new CourseList();
    }
    
    // TODO getters and settings for all but a setter for satisfed
}
