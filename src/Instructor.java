import java.util.ArrayList;
import java.util.Date;

public class Instructor extends Person {
	
	private String degree;
	private ArrayList<Course> presentedCourses;
	private Schedule weeklySchedule;
	/**
	 * 
	 */
	public Instructor() {
		super();
	}
	/**
	 * @param degree
	 * @param presentedCourses
	 * @param weeklySchedule
	 */
	public Instructor(String degree, ArrayList<Course> presentedCourses, Schedule weeklySchedule) {
		super();
		this.degree = degree;
		this.presentedCourses = presentedCourses;
		this.weeklySchedule = weeklySchedule;
	}
	/**
	 * @param ssn
	 * @param name
	 * @param lastname
	 * @param email
	 * @param birthDate
	 * @param age
	 * @param degree
	 * @param presentedCourses
	 * @param weeklySchedule
	 */
	public Instructor(String ssn, String name, String lastname, String email, int birthDate, int age, String degree,
					  ArrayList<Course> presentedCourses, Schedule weeklySchedule) {
		super(ssn, name, lastname, email, birthDate, age);
		this.degree = degree;
		this.presentedCourses = presentedCourses;
		this.weeklySchedule = weeklySchedule;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public ArrayList<Course> getPresentedCourses() {
		return presentedCourses;
	}
	public void setPresentedCourses(ArrayList<Course> presentedCourses) {
		this.presentedCourses = presentedCourses;
	}
	public Schedule getWeeklySchedule() {
		return weeklySchedule;
	}
	public void setWeeklySchedule(Schedule weeklySchedule) {
		this.weeklySchedule = weeklySchedule;
	}
	@Override
	public String toString() {
		return "Instructur [degree=" + degree + ", presentedCourses=" + presentedCourses + ", weeklySchedule="
				+ weeklySchedule + "]";
	}
	
	/////////////////////////////////////////////////
	
	public void addCourse() {
		
	}
	
	
	

}
