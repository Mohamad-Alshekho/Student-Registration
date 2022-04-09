import java.util.Arrays;

public class Schedule {
	private int courseTime[][];

	
	/**
	 * 
	 */
	public Schedule() {
		super();
	}



	/**
	 * @param courseTime
	 */
	public Schedule(int[][] courseTime) {
		super();
		this.courseTime = courseTime.clone();
	}



	public int[][] getCourseTime() {
		return courseTime;
	}



	public void setCourseTime(int[][] courseTime) {
		this.courseTime = courseTime;
	}



	@Override
	public String toString() {
		return "Schedule [courseTime=" + Arrays.toString(courseTime) + "]";
	}



	
	

}
