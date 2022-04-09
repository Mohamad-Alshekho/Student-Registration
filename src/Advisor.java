import java.util.ArrayList;

public class Advisor extends Instructor {
	private ArrayList<Student> students;

	/**
	 * 
	 */
	public Advisor() {
		super();
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
	 * @param students
	 */
	public Advisor(String ssn, String name, String lastname, String email, int birthDate, int age, String degree,
			ArrayList<Course> presentedCourses, Schedule weeklySchedule, ArrayList<Student> students) {
		super(ssn, name, lastname, email, birthDate, age, degree, presentedCourses, weeklySchedule);
		this.students = students;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Advisor [students=" + students + "]";
	}



	public  boolean checkCompletedCreditsForGraduationProject(Student student, Course course) {
		if (course.getCourseCode().equals("CSE4297")) {
			int totalCredit = student.getCurrentTranscript().calculateCompletedCredit();

			if (totalCredit < 165) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public  boolean checkCreditsForTechnicalElective(Student student, Course course) {
		int totalCredit = student.getCurrentTranscript().calculateCompletedCredit();

		if(course.getType().equals("te") && totalCredit < 155) {
			return false;
		}
		else {
			return true;
		}
	}

	public  Course detectConflict(Student student, Course course) {
		for(int i = 0; i<9; i++){
			for(int j = 0; j<5; j++){
				if(course.getCourseSchedule().getCourseTime()[i][j] == 1){
					if(student.getWeeklySchedule().getCourseTime()[i][j] == 1){
						student.getCollisionCourses().add(course);
						course.incrementHourCollisionCounter();

						for (Object[] x : student.getCurrentTranscript().getActiveCourses()){
							Course curr = (Course) x[0];
							int[][] curr_course_schedule = curr.getCourseSchedule().getCourseTime();
							if(curr_course_schedule[i][j] == student.getWeeklySchedule().getCourseTime()[i][j]){
								return curr;
							}
						}
					}
				}
			}
		}
		return null;
	}
}



