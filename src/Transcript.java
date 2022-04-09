import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class Transcript {
	
	private int semester;
	private ArrayList<Double> gpa;
	private double totalGpa;
	private ArrayList<Integer> passedCredits;
	private ArrayList<Integer> failedCredits;
	private ArrayList<ArrayList<Object[]>> coursesPassed;
	private ArrayList<ArrayList<Object[]>> coursesFailed;
	private ArrayList<Object[]> coursesNotTaken;
	private ArrayList<Object[]> activeCourses;
	/**
	 * 
	 */
	public Transcript() {
		super();
	}
	/**
	 * @param semester
	 * @param gpa
	 * @param coursesPassed
	 * @param coursesFailed
	 * @param activeCourses
	 */
	public Transcript(int semester, ArrayList<Double> gpa, double totalGpa, ArrayList<Integer> passedCredits,
					  ArrayList<Integer> failedCredits, ArrayList<ArrayList<Object[]>> coursesPassed,
					  ArrayList<ArrayList<Object[]>> coursesFailed, ArrayList<Object[]> coursesNotTaken, ArrayList<Object[]> activeCourses)
	{
		super();
		this.semester = semester;
		this.gpa = gpa;
		this.totalGpa = totalGpa;
		this.passedCredits = passedCredits;
		this.failedCredits = failedCredits;
		this.coursesPassed = coursesPassed;
		this.coursesFailed = coursesFailed;
		this.coursesNotTaken = coursesNotTaken;
		this.activeCourses = activeCourses;
	}

	public ArrayList<Object[]> getCoursesNotTaken() {
		return coursesNotTaken;
	}
	public void setCoursesNotTaken(ArrayList<Object[]> coursesNotTaken) {
		this.coursesNotTaken = coursesNotTaken;
	}
	public double getTotalGpa() {
		return totalGpa;
	}
	public void setTotalGpa(double totalGpa) {
		this.totalGpa = totalGpa;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public ArrayList<Double> getGpa() {
		return gpa;
	}
	public void setGpa(ArrayList<Double> gpa) {
		this.gpa = gpa;
	}
	public ArrayList<Integer> getPassedCredits() {
		return passedCredits;
	}

	public ArrayList<Integer> getFailedCredits() {
		return failedCredits;
	}

	public ArrayList<ArrayList<Object[]>> getCoursesPassed() {
		return coursesPassed;
	}
	public void setCoursesPassed(ArrayList<ArrayList<Object[]>> coursesPassed) {
		this.coursesPassed = coursesPassed;
	}
	public ArrayList<ArrayList<Object[]>> getCoursesFailed() {
		return coursesFailed;
	}
	public void setCoursesFailed(ArrayList<ArrayList<Object[]>> coursesFailed) {
		this.coursesFailed = coursesFailed;
	}
	public ArrayList<Object[]> getActiveCourses() {
		return activeCourses;
	}
	public void setActiveCourses(ArrayList<Object[]> activeCourses) {
		this.activeCourses = activeCourses;
	}
	public void calculateGPAs(int term) {

		for (int i = 1; i < term; i++){
			double curr_gpa = calculateTermGPA(coursesPassed.get(i-1), coursesFailed.get(i-1));
			this.gpa.add(curr_gpa);
		}
		// get total credits
		int totalFailedCredits =0 ;
		for (int i = 0; i < coursesFailed.size(); i++){
			ArrayList<Object[]> iterate = coursesFailed.get(i);
			int termFailedCredits = 0;
			for (int j = 0; j < iterate.size(); j++){
				totalFailedCredits += ((Course)iterate.get(j)[0]).getCourseCredit();
				termFailedCredits += ((Course)iterate.get(j)[0]).getCourseCredit();
			}
			failedCredits.add(termFailedCredits);
		}


		int totalPassedCredits =0 ;
		double overallGPA = 0.0;
		for (int i = 0; i < coursesPassed.size(); i++){
			ArrayList<Object[]> iterate = coursesPassed.get(i);
			int termPassedCredits = 0;
			for (int j = 0; j < iterate.size(); j++){
				int curr_credit = ((Course)iterate.get(j)[0]).getCourseCredit();
				totalPassedCredits += curr_credit;
				termPassedCredits += curr_credit;
				overallGPA += get_gradeEquivalent((String)iterate.get(j)[1]) * curr_credit;
			}
			passedCredits.add(termPassedCredits);
		}

		int totalCredits = totalFailedCredits + totalPassedCredits;
		overallGPA = overallGPA / (double)totalCredits;
		this.totalGpa = overallGPA;
	}

	public double calculateTermGPA(ArrayList<Object[]> termPassed, ArrayList<Object[]> termFailed){
		int totalFailedCredits = 0;
		for (int i = 0; i < termFailed.size(); i++) {
			totalFailedCredits += ((Course) termFailed.get(i)[0]).getCourseCredit();
		}
		int totalPassedCredits = 0;
		for (int i = 0; i < termPassed.size(); i++) {
			totalPassedCredits += ((Course) termPassed.get(i)[0]).getCourseCredit();
		}
		int totalCredits = totalFailedCredits + totalPassedCredits;


		double calculatedGpa = 0.0;
		for (int x = 0; x < termPassed.size(); x++){
			Course curr = (Course)termPassed.get(x)[0];
			String note = (String)termPassed.get(x)[1];
			int credit = curr.getCourseCredit();
			double int_note = get_gradeEquivalent(note);
			double add = (int_note * (double)credit);
			calculatedGpa += add;
		}
		double term_gpa = calculatedGpa / totalCredits;
		return term_gpa;
	}

	
	public int calculateTakenCredit () {
		int takenCredits =0;
		for (int i : passedCredits){
			takenCredits += i;
		}
		for (int i : failedCredits){
			takenCredits += i;
		}
		return takenCredits;
	}
	
	public int calculateCompletedCredit () {
		int passed =0;
		for (int i : passedCredits){
			passed += i;
		}
		return passed;
	}

	private  double get_gradeEquivalent (String grade) {
		double gradeValue =0;
		if (grade.equals ("AA"))
			gradeValue= 4.0;
		else if (grade.equals("BA"))
			gradeValue= 3.5;
		else if (grade.equals("BB"))
			gradeValue = 3.0;
		else if (grade.equals("CB"))
			gradeValue = 2.5;
		else if (grade.equals ("CC"))
			gradeValue = 2.0;
		else if (grade.equals("DC"))
			gradeValue = 1.5;
		else if (grade.equals("DD"))
			gradeValue = 1.0;
		else if (grade.equals("FF"))
			gradeValue = 0;
		return gradeValue;
	}

}
