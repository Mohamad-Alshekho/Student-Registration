import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Course {
	private String CourseCode;
	private String CourseTitle;
	private Semester givenSemester;
	private Schedule courseSchedule;
	private ArrayList<Student> students;
	private int courseCredit;
	private ArrayList<String> prerequisites;
	private String section; // m | ue | te | nte | fte
	private int quota;
	private int numOfStudents;
	private String type;
	private int hourCollisionCounter;
	private int prerequisiteFailureCounter;

	public int getPrerequisiteFailureCounter() {
		return prerequisiteFailureCounter;
	}

	public void setPrerequisiteFailureCounter(int prerequisiteFailureCounter) {
		this.prerequisiteFailureCounter = prerequisiteFailureCounter;
	}

	public void incrementPrerequisiteFailiureCounter(){
		this.prerequisiteFailureCounter++;
	}

	/**
	 * 
	 */
	public Course() {
		super();
	}


	/**
	 * @param courseCode
	 * @param courseTitle
	 * @param givenSemester
	 * @param courseSchedule
	 * @param students
	 * @param courseCredit
	 * @param prerequisites
	 * @param section
	 * @param quota
	 * @param numOfStudents
	 */
	public Course(String courseCode, String courseTitle, Semester givenSemester, Schedule courseSchedule,
			ArrayList<Student> students, int courseCredit, ArrayList<String> prerequisites, String section, int quota,
			int numOfStudents, String type) {
		super();
		CourseCode = courseCode;
		CourseTitle = courseTitle;
		this.type = type;
		this.givenSemester = givenSemester;
		this.courseSchedule = courseSchedule;
		this.students = students;
		this.courseCredit = courseCredit;
		this.prerequisites = prerequisites;
		this.section = section;
		this.quota = quota;
		this.numOfStudents = numOfStudents;
		this.hourCollisionCounter = 0;
	}



	public String getCourseCode() {
		return CourseCode;
	}

	public String getType() {
		return  type;
	}


	public void setCourseCode(String courseCode) {
		CourseCode = courseCode;
	}


	public String getCourseTitle() {
		return CourseTitle;
	}


	public void setCourseTitle(String courseTitle) {
		CourseTitle = courseTitle;
	}


	public Semester getGivenSemester() {
		return givenSemester;
	}


	public void setGivenSemester(Semester givenSemester) {
		this.givenSemester = givenSemester;
	}


	public Schedule getCourseSchedule() {
		return courseSchedule;
	}


	public void setCourseSchedule(Schedule courseSchedule) {
		this.courseSchedule = courseSchedule;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}


	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}


	public int getCourseCredit() {
		return courseCredit;
	}


	public void setCourseCredit(int courseCredit) {
		this.courseCredit = courseCredit;
	}


	public ArrayList<String> getPrerequisites() {
		return prerequisites;
	}


	public void setPrerequisites(ArrayList<String> prerequisites) {
		this.prerequisites = prerequisites;
	}


	public String getSection() {
		return section;
	}


	public void setSection(String section) {
		this.section = section;
	}


	public int getQuota() {
		return quota;
	}


	public void setQuota(int quota) {
		this.quota = quota;
	}


	public int getNumOfStudents() {
		return numOfStudents;
	}


	public int getHourCollisionCounter(){
		return this.hourCollisionCounter;
	}
	public void setNumOfStudents(int numOfStudents) {
		this.numOfStudents = numOfStudents;
	}

	public void incrementHourCollisionCounter() {
	this.hourCollisionCounter++;
	}

	@Override
	public String toString() {
		return "Course [CourseCode=" + CourseCode + ", CourseTitle=" + CourseTitle + ", givenSemester=" + givenSemester
				+ ", courseSchedule=" + courseSchedule + ", students=" + ", courseCredit="
				+ courseCredit + ", prerequisites=" + prerequisites + ", section=" + section + ", quota=" + quota
				+ ", numOfStudents=" + numOfStudents + "]";
	}
}
