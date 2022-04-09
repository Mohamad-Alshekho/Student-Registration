import java.util.ArrayList;

public class ElectiveCourse extends Course{
    private String electiveCourseCode;
    private String electiveCourseTitle;


    public ElectiveCourse() {
    }

    public ElectiveCourse(String courseCode, String courseTitle, Semester givenSemester, Schedule courseSchedule, ArrayList<Student> students, int courseCredit, ArrayList<String> prerequisites, String section, int quota, int numOfStudents, String type, String electiveCourseTitle, String electiveCourseCode) {
        super(courseCode, courseTitle, givenSemester, courseSchedule, students, courseCredit, prerequisites, section, quota, numOfStudents, type);
        this.electiveCourseCode = electiveCourseCode;
        this.electiveCourseTitle = electiveCourseTitle;
    }

    public String getElectiveCourseCode() {
        return electiveCourseCode;
    }

    public void setElectiveCourseCode(String electiveCourseCode) {
        this.electiveCourseCode = electiveCourseCode;
    }

    public String getElectiveCourseTitle() {
        return electiveCourseTitle;
    }

    public void setElectiveCourseTitle(String electiveCourseTitle) {
        this.electiveCourseTitle = electiveCourseTitle;
    }
}

