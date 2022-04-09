import java.util.Date;
import java.util.ArrayList;

public class Student extends Person {
	private StudentID studentId;
	private int yearEnrolled;
	private Schedule weeklySchedule;
	private Transcript previousTranscript;
	private Transcript currentTranscript;
	private ArrayList<Course> collisionCourses = new ArrayList<Course>();
	private ArrayList<String> logs = new ArrayList<String>();

	public Student() {
		// TODO Auto-generated constructor stub
	}
	
	public Student(String ssn, String name, String lastname, String email, int birthDate, int age, StudentID studentId,
			int yearEnrolled, Schedule weeklySchedule, Transcript currentTranscript, Transcript previousTranscript) {
		super(ssn, name, lastname, email, birthDate, age);
		this.studentId = studentId;
		this.yearEnrolled = yearEnrolled;
		this.weeklySchedule = weeklySchedule;
		this.currentTranscript = currentTranscript;
		this.previousTranscript = previousTranscript;
	}

	public ArrayList<String> getLogs() {
		return logs;
	}

	public void setLogs(ArrayList<String> logs) {
		this.logs = logs;
	}

	public StudentID getStudentId() {
		return studentId;
	}

	public void setStudentId(StudentID studentId) {
		this.studentId = studentId;
	}

	public int getYearEnrolled() {
		return yearEnrolled;
	}

	public void setYearEnrolled(int yearEnrolled) {
		this.yearEnrolled = yearEnrolled;
	}

	public Schedule getWeeklySchedule() {
		return weeklySchedule;
	}

	public void setWeeklySchedule(Schedule weeklySchedule) {
		this.weeklySchedule = weeklySchedule;
	}

	public Transcript getPreviousTranscript() {
		return previousTranscript;
	}

	public Transcript getCurrentTranscript() {
		return currentTranscript;
	}


//	@Override
//	public String toString() {
//		return "Student [studentId=" + studentId + ", yearEnrolled=" + yearEnrolled + ", weeklySchedule="
//				+ weeklySchedule + ", transcript=" + transcript + "]";
//	}

	/////////////////////////////////////////////////

	public ArrayList<Course> getCollisionCourses(){
		return this.collisionCourses;
	}

	public void printTranscript () {
		
	}
	

}
