import com.rits.cloning.Cloner;

import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Registration {
    public static int semesterCount = 0;
    private Advisor advisor;
    private DepartmentStatistics depStat;
    private OutputGenerator outputGenerator;
    private ArrayList<ElectiveCourse> te;
    private ArrayList<ElectiveCourse> nte;
    private ArrayList<ElectiveCourse> fte;
    private ArrayList<ElectiveCourse> ue;
    private ArrayList<Course> allCourses;

    public Registration(Advisor advisor, OutputGenerator outputGenerator, DepartmentStatistics depStat, ArrayList<Course> courses,
    ArrayList<ElectiveCourse> te, ArrayList<ElectiveCourse> nte, ArrayList<ElectiveCourse> ue, ArrayList<ElectiveCourse> fte){
        this.advisor = advisor;
        this.depStat = depStat;
        this.outputGenerator = outputGenerator;
        this.allCourses = courses;
        this.te = te;
        this.nte = nte;
        this.fte = fte;
        this.ue = ue;
    }

    public void register(ArrayList<Student> students, int semester){
        for (Student s: students){
            Transcript tr = s.getCurrentTranscript();
            ArrayList<Object[]> available_courses = new ArrayList<Object[]>();

            if(tr.getGpa().size()== 0){
                available_courses = getAvailableCoursesForRegistration(tr.getSemester(), 0.0, tr.getCoursesPassed(),tr.getCoursesFailed(),tr.getCoursesNotTaken());

            }
            else
            available_courses = getAvailableCoursesForRegistration(tr.getSemester(), tr.getGpa().get(tr.getGpa().size()-1), tr.getCoursesPassed(),tr.getCoursesFailed(),tr.getCoursesNotTaken());

            replaceElectives(available_courses);

            int creditsTakenForTheTerm = 0;
            for (Object[] course: (ArrayList<Object[]>) available_courses.clone()){
                Course curr_course = (Course)course[0];

                // Actions are logged here
                // CHECK PREREQUISITES
                boolean condition = Registration.checkPrerequisites(course, tr.getCoursesPassed());
                if (condition == false){
                    available_courses.remove(course);
                    boolean cond = true;
                    for (Object[] x : tr.getCoursesNotTaken()){
                        if (((Course)x[0]).getCourseCode().equals((curr_course).getCourseCode())){
                            cond = false;
                        }
                    }
                    if (cond)
                    tr.getCoursesNotTaken().add(course);

                    s.getLogs().add("The student " + s.getStudentId() + " could not take the course " + curr_course.getCourseCode() +
                            " because of a prerequisite dependency. Prerequisite: " + curr_course.getPrerequisites().get(0));
                    continue;
                }

                // check conflict
                Course conflict = this.advisor.detectConflict(s, curr_course);
                if (conflict != null){
                    // this.depStat.conflictCount++;
                    this.depStat.incrementConflictCount();
                    this.depStat.addToConflictArray(s.getStudentId().getId());
                    available_courses.remove(course);
                    s.getLogs().add("The course " + ((Course)course[0]).getCourseCode() + " could not be taken because it conflicts with " + conflict.getCourseCode() + ".");
                    continue;
                }

                // check quota
                if (curr_course.getNumOfStudents() >= curr_course.getQuota()){
                    // DepartmentStatistics.quotaCount++;
                    this.depStat.incrementQuotaCount();
                    this.depStat.addToQuotaArray(s.getStudentId().getId());
                    available_courses.remove(course);
                    s.getLogs().add("The quota for " + curr_course.getCourseCode() + " is full. Could not take the course");
                    continue;
                }
                else {
                    curr_course.setNumOfStudents(curr_course.getNumOfStudents()+1);
                }

                // check if the student can take the engineering project course
                boolean canTake = this.advisor.checkCompletedCreditsForGraduationProject(s,curr_course);
                if (!canTake){
                    this.depStat.incrementEngineeringProjectCount();
                    this.depStat.addToEngineeringProjectArray(s.getStudentId().getId());
                    s.getLogs().add("Cannot take graduation project class due to lack of total completed credits.");
                    continue;
                }

                // check if the student can take the elective course
                canTake = this.advisor.checkCreditsForTechnicalElective(s,curr_course);
                if (!canTake){
                    //DepartmentStatistics.teCount++;
                    this.depStat.incrementTeCount();
                    this.depStat.addToEngineeringProjectArray(s.getStudentId().getId());
                    s.getLogs().add("Cannot take the course " + curr_course.getCourseCode() + " because total number of completed credits are less than 155");
                    continue;
                }

                // add to schedule
                // add to active courses
                int[][] student_schedule = s.getWeeklySchedule().getCourseTime();
                int[][] course_schedule = curr_course.getCourseSchedule().getCourseTime();

                for (int i = 0 ; i < 9; i++){
                    for (int j = 0; j < 5; j++){
                        if (course_schedule[i][j] == 1){
                            student_schedule[i][j] = course_schedule[i][j];
                        }
                    }
                }
                s.getWeeklySchedule().setCourseTime(student_schedule);
                tr.setActiveCourses(available_courses);
            }
        }

        this.outputGenerator.evaluateCourseStatistics();
    }


    public static boolean checkPrerequisites(Object[] checkCourse,
                                             ArrayList<ArrayList<Object[]>> globalPassedCourses) {
        // CHECK PREREQUISITES
        boolean condition = false;
        Course check = (Course) checkCourse[0];
        ArrayList<String> prerequisites = check.getPrerequisites();
        if (prerequisites.size() == 0) {
            condition = true;
        }
        for (int m = 0; m < prerequisites.size(); m++) {
            String prerequisite = prerequisites.get(m);
            for (int x = 0; x < globalPassedCourses.size(); x++) {
                ArrayList<Object[]> a = globalPassedCourses.get(x);
                for (int y = 0; y < a.size(); y++) {
                    String code = ((Course) a.get(y)[0]).getCourseCode();

                    if (code.equals(prerequisite)) {
                        condition = true;
                    }
                }
            }
        }
        return condition;
    }

    public ArrayList<Object[]> getAvailableCoursesForRegistration(int semester, double gpa, ArrayList<ArrayList<Object[]>> globalPassedCourses,
                                                          ArrayList<ArrayList<Object[]>> globalFailedCourses, ArrayList<Object[]> notTakenCourses) {
        ArrayList<Object[]> availableCourses = new ArrayList<Object[]>();
        for (int i = 0; i < globalFailedCourses.size(); i++) {
            int count = 0;
            for (int y = 0; y < globalFailedCourses.get(i).size(); y++) {
                // check if a failed course is passed in next semesters => if so => don't add to available courses.
                for (int a = 0; a < globalPassedCourses.size(); a++) {
                    for (int c = 0; c < globalPassedCourses.get(a).size(); c++) {
                        if (((Course) (globalPassedCourses.get(a).get(c)[0])).getCourseCode().equals(((Course) (globalFailedCourses.get(i).get(y)[0])).getCourseCode())) {
                            count++;
                        }
                    }
                }
                //check if it is already in available courses because it may be in two semesters in global failed array => not to be duplicated.
                for (int z = 0; z < availableCourses.size(); z++) {
                    if (((Course) (globalFailedCourses.get(i).get(y)[0])).getCourseCode().equals(((Course) (availableCourses.get(z)[0])).getCourseCode())) {
                        count++;
                    }
                }
                if (count > 0)
                    continue;
                availableCourses.add(globalFailedCourses.get(i).get(y));
            }
        }

        for (Object[] course : notTakenCourses) {
            int counter = 0;
            for (int z = 0; z < availableCourses.size(); z++) {
                if (((Course) (course[0])).getCourseCode().equals(((Course) (availableCourses.get(z)[0])).getCourseCode())) {
                    counter++;
                }
            }
            if (counter > 0) {
                //System.out.println("The course is already taken.");
                continue;
            }
            availableCourses.add(course);

        }


        if (gpa < 1.8 && semester > 2) {
            semesterCount++;
            // loop to add all courses with DD DC note letter.// , make it optional after that
            for (int a = 0; a < globalPassedCourses.size(); a++) {
                for (int c = 0; c < globalPassedCourses.get(a).size(); c++) {
                    if (((String) (globalPassedCourses.get(a).get(c)[1])).equals("DD") || ((String) (globalPassedCourses.get(a).get(c)[1])).equals("DC")) {
                        availableCourses.add(globalPassedCourses.get(a).get(c));
                    }
                }
            }
            return availableCourses;
        }
        for (int r = 0; r < semesterCount; r++) {// if gpa in previous semester was < 1.8 => take courses of its direct next semester.
            semester -= 1;
        }

        for (int i = 0; i < getGlobalCourses().size(); i++) {
            if (getGlobalCourses().get(i).getGivenSemester().getNum() == semester) {
                Course myCourse = getGlobalCourses().get(i);

                String grade = "XX";
                Object[] newObj = new Object[2];
                newObj[0] = myCourse;
                newObj[1] = grade;
                availableCourses.add(newObj);
            }

        }
        semesterCount = 0;

        for (int a = 0; a < globalPassedCourses.size(); a++) {
            for (int c = 0; c < globalPassedCourses.get(a).size(); c++) {
                if (((String) (globalPassedCourses.get(a).get(c)[1])).equals("DD") || ((String) (globalPassedCourses.get(a).get(c)[1])).equals("DC")) {
                    availableCourses.add(globalPassedCourses.get(a).get(c));
                }
            }
        }
        return availableCourses;
    }

    public ArrayList<Object[]> getAvailableCoursesForSimulation(int semester, double gpa, ArrayList<ArrayList<Object[]>> globalPassedCourses,
                                                                       ArrayList<ArrayList<Object[]>> globalFailedCourses, ArrayList<Object[]> notTakenCourses) {
        ArrayList<Object[]> availableCourses = new ArrayList<Object[]>();
        for (int i =0 ; i < globalFailedCourses.size() ; i++){
            int count =0;
            for (int y=0 ; y < globalFailedCourses.get(i).size() ; y++){
                // check if a failed course is passed in next semesters => if so => don't add to available courses.
                for (int a=0 ; a < globalPassedCourses.size() ; a++) {
                    for (int c = 0; c < globalPassedCourses.get(a).size(); c++) {
                        if (((Course)(globalPassedCourses.get(a).get(c)[0])).getCourseCode().equals(((Course)(globalFailedCourses.get(i).get(y)[0])).getCourseCode())){
                            count++;
                        }
                    }
                }
                //check if it is already in available courses because it may be in two semesters in global failed array => not to be duplicated.
                for(int z =0 ; z < availableCourses.size() ; z++){
                    if (((Course)(globalFailedCourses.get(i).get(y)[0])).getCourseCode().equals(((Course)(availableCourses.get(z)[0])).getCourseCode())){
                        count++;
                    }
                }
                if (count > 0 )
                    continue;
                availableCourses.add(globalFailedCourses.get(i).get(y));
            }
        }

        for (Object[] course: notTakenCourses){
            int counter =0;
            for(int z =0 ; z < availableCourses.size() ; z++){
                if (((Course)(course[0])).getCourseCode().equals(((Course)(availableCourses.get(z)[0])).getCourseCode())){
                    counter++;
                }
            }
            if(counter > 0){
                //System.out.println("The course is already taken");
                continue;
            }
            availableCourses.add(course);

        }


        if(gpa < 1.8 && semester > 2){
            semesterCount++ ;
            // loop to add all courses with DD DC note letter.// , make it optional after that
            for (int a=0 ; a < globalPassedCourses.size() ; a++) {
                for (int c = 0; c < globalPassedCourses.get(a).size(); c++) {
                    if (((String)(globalPassedCourses.get(a).get(c)[1])).equals("DD") || ((String)(globalPassedCourses.get(a).get(c)[1])).equals("DC")){
                        availableCourses.add(globalPassedCourses.get(a).get(c));
                    }
                }
            }
            return availableCourses;
        }
        for(int r=0 ; r< semesterCount ; r++){// if gpa in previous semester was < 1.8 => take courses of its direct next semester.
            semester -=1;
        }

        for (int i = 0; i < getGlobalCourses().size(); i++){
            if (getGlobalCourses().get(i).getGivenSemester().getNum() == semester){
                Course myCourse = getGlobalCourses().get(i);

                String grade = "XX";
                Object[] newObj = new Object[2];
                newObj[0] = myCourse;
                newObj[1] = grade;
                availableCourses.add(newObj);
            }

        }
        semesterCount =0;

        return availableCourses;

    }

    public void replaceElectives(ArrayList<Object[]> active_courses){
        for(Object[] c: (ArrayList<Object[]>) active_courses.clone()){
            String first = ((Course) c[0]).getCourseCode().split(" ")[0];
            if ((first.equals("NTE"))){
                int size= getGlobalNTEs().size();
                int random = (int)(Math.random()*size);
                ElectiveCourse x = getGlobalNTEs().get(random);

                Cloner cloner = new Cloner();
                ElectiveCourse y = cloner.deepClone(x);

                y.setCourseCode(((Course) c[0]).getCourseCode());
                y.setCourseTitle(((Course) c[0]).getCourseTitle());

                active_courses.remove(c);
                Object[] add = new Object[2];
                add[0] = y;
                add[1] = c[1];
                active_courses.add(add);
            }
            else if ((first.equals("TE"))){
                int size= getGlobalTEs().size();
                int random = (int)(Math.random()*size);
                ElectiveCourse x = getGlobalTEs().get(random);

                Cloner cloner = new Cloner();
                ElectiveCourse y = cloner.deepClone(x);

                y.setCourseCode(((Course) c[0]).getCourseCode());
                y.setCourseTitle(((Course) c[0]).getCourseTitle());

                active_courses.remove(c);
                Object[] add = new Object[2];
                add[0] = y;
                add[1] = c[1];
                active_courses.add(add);
            }
            else if ((first.equals("UE"))){
                int size= getGlobalUEs().size();
                int random = (int)(Math.random()*size);
                ElectiveCourse x = getGlobalUEs().get(random);

                Cloner cloner = new Cloner();
                ElectiveCourse y = cloner.deepClone(x);

                y.setCourseCode(((Course) c[0]).getCourseCode());
                y.setCourseTitle(((Course) c[0]).getCourseTitle());

                active_courses.remove(c);
                Object[] add = new Object[2];
                add[0] = y;
                add[1] = c[1];
                active_courses.add(add);
            }
            else if ((first.equals("FTE"))){
                int size= getGlobalFTs().size();
                int random = (int)(Math.random()*size);
                ElectiveCourse x = getGlobalFTs().get(random);

                Cloner cloner = new Cloner();
                ElectiveCourse y = cloner.deepClone(x);

                y.setCourseCode(((Course) c[0]).getCourseCode());
                y.setCourseTitle(((Course) c[0]).getCourseTitle());

                active_courses.remove(c);
                Object[] add = new Object[2];
                add[0] = y;
                add[1] = c[1];
                active_courses.add(add);
            }
        }
    }

    public ArrayList<Course> getGlobalCourses(){
        return this.allCourses;
    }

    public ArrayList<ElectiveCourse> getGlobalNTEs(){
        return this.nte;
    }

    public ArrayList<ElectiveCourse> getGlobalTEs(){
        return this.te;
    }

    public ArrayList<ElectiveCourse> getGlobalUEs(){
        return this.ue;
    }

    public ArrayList<ElectiveCourse> getGlobalFTs(){
        return this.fte;
    }
}

