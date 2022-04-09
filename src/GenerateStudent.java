import com.rits.cloning.Cloner;

import java.util.ArrayList;

public class GenerateStudent {
    private Registration reg ;


    private String[] grades = {"AA", "BB","BA", "CB", "BB", "AA","CB", "BA", "BB","CC","BA", "DC","BB", "DD","AA","DD", "DD"};// 7
    private String[] firstNames   = { "Adam", "Alex", "Aaron", "Ben", "Carl", "Dan", "David", "Edward", "Fred", "Frank", "George", "Hal", "Hank", "Ike", "John", "Jack", "Joe", "Larry", "Monte", "Matthew", "Mark", "Nathan", "Otto", "Paul", "Peter", "Roger", "Roger", "Steve", "Thomas", "Tim", "Ty", "Victor", "Walter"};
    private String[] lastNames = { "Anderson", "Ashwoon", "Aikin", "Bateman", "Bongard", "Bowers", "Boyd", "Cannon", "Cast", "Deitz", "Dewalt", "Ebner", "Frick", "Hancock", "Haworth", "Hesch", "Hoffman", "Kassing", "Knutson", "Lawless", "Lawicki", "Mccord", "McCormack", "Miller", "Myers", "Nugent", "Ortiz", "Orwig"};
    private ArrayList<Student> students = new ArrayList<Student>();

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    private Transcript trans = new Transcript();// just for calculating gpa before creating a random transcript.

    GenerateStudent(int numOfStudentsPerYear, int semester, int maxFailed, Registration reg){
        this.reg = reg;

        generate_students(numOfStudentsPerYear, semester, maxFailed);
    }

    public void generate_students(int numOfStudentsPerYear, int semester, int maxFailed){
        int year = 2016;
        String ssnNumbers = "0123456789";
        for (; semester <= 8; semester=semester+2){
            for (int i = 0; i < numOfStudentsPerYear; i++){
                String ssn = "";
                for (int j = 0; j < 11 ; j++){
                    int index = (int)(Math.random()*10);
                    String add = ssnNumbers.substring(index, index + 1);
                    ssn += add;
                }

                String name = firstNames[(int)(Math.random() * firstNames.length)];
                String lastName = lastNames[(int)(Math.random() * lastNames.length)];

                String email = name + lastName + "@marun.edu.tr";

                int yearEnrolled = year - (int)(Math.random()*4);
                int birthDate = yearEnrolled - 18;

                int age = year - birthDate; // now - birth

                StudentID id = new StudentID(yearEnrolled);

                Transcript currentStudentTranscript = generate_transcript(semester, maxFailed);

                Cloner cloner = new Cloner();
                Transcript newStudentTranscript =  cloner.deepClone(currentStudentTranscript);
                //MyClass clone = cloner.deepClone(currentStudentTranscript);

                Schedule schedule = new Schedule(new int[9][5]);

                Student student = new Student(ssn,name,lastName,email,birthDate,age,id,yearEnrolled,schedule, currentStudentTranscript,  newStudentTranscript);

                students.add(student);
            }
        }
    }

    public  Transcript generate_transcript(int rand_semester, int maxFailed){
        ArrayList<ArrayList<Object[]>> globalPassedCourses = new ArrayList<ArrayList<Object[]>>();
        ArrayList<ArrayList<Object[]>> globalFailedCourses = new ArrayList<ArrayList<Object[]>>();
        ArrayList<Object[]> globalNotTakenCourses = new ArrayList<Object[]>();

        ArrayList<Integer> globalTotalFailedCredits = new ArrayList<Integer>();
        ArrayList<Integer> globalTotalPassedCredits = new ArrayList<Integer>();
        ArrayList<Double> gpas = new ArrayList<Double>();
        ArrayList<Object[]> passedCo = new ArrayList<Object[]>();
        ArrayList<Object[]> failedCo = new ArrayList<Object[]>();
        double totalGpa = 0.0;
        double cummulativeGpa = 0.0;

        int transTotalCredits = 0;
        for (int i = 1; i < rand_semester; i++){
            ArrayList<Object[]> passedCourses = new ArrayList<Object[]>();
            ArrayList<Object[]> failedCourses = new ArrayList<Object[]>();
            ArrayList<Object[]> active_courses = new ArrayList<Object[]>();
           // ArrayList<Integer> totalTakenCredit = new ArrayList<Integer>();
            if (i == 1){
                active_courses = reg.getAvailableCoursesForSimulation(i, 0.0,
                        globalPassedCourses, globalFailedCourses, globalNotTakenCourses);
            }
            else{
                active_courses = reg.getAvailableCoursesForSimulation(i, trans.calculateTermGPA(passedCo, failedCo),
                        globalPassedCourses, globalFailedCourses, globalNotTakenCourses);
            }

            reg.replaceElectives(active_courses);

            // generate a number between 0 and 2
            int failedCount = maxFailed;
            failedCount = (int)Math.random()*(maxFailed+1);
            int nOfCourses = active_courses.size();
            if (nOfCourses <= 2){// don't let him fail if he can only take 2 courses or less.
                failedCount =0;
            }
            for (int j = 0; j < failedCount; j++){
                // generate a number between 0 and nOfCourses
                int failedCourseIndex = (int) (Math.random() * (nOfCourses-1) ); //random//
                Object[] fail = active_courses.remove(failedCourseIndex);
                fail[1] = "FF";
                // Object[2] = {course(course/electivecourse), grade(string)}

                // CHECK PREREQUISITES
                if (i > 2){ // check after second semester.
                   boolean condition = reg.checkPrerequisites(fail, globalPassedCourses);
                    if (condition == false){
                        ((Course)fail[0]).incrementPrerequisiteFailiureCounter();
                        fail[1] = "XX";
                        boolean cond = true;
                        for (Object[] x: globalNotTakenCourses){
                            if (((Course)x[0]).getCourseCode().equals(((Course)fail[0]).getCourseCode())){
                                cond = false;
                            }
                        }
                        if (cond)
                        globalNotTakenCourses.add(fail); // keep a record of all the not taken courses

                        continue;
                    }
                }
                failedCourses.add(fail);
            }
            int limit = active_courses.size();
            for (int j = 0; j < limit; j++){
                // generate random a grade between DD and AA
                String randomGrade = grades[(int) (Math.random() * 15)];
                String note = randomGrade; //random

                Object[] pass = active_courses.remove(0);
                Object[] passClone = pass.clone();// to avoid mismatch if a course was previously failed,
                // CHECK PREREQUISITES
                if (i > 2){ // check after second semester.
                    boolean condition = reg.checkPrerequisites(pass, globalPassedCourses);
                    if (condition == false){
                        ((Course)pass[0]).incrementPrerequisiteFailiureCounter();
                        boolean cond = true;
                        for (Object[] x: globalNotTakenCourses){
                            if (((Course)x[0]).getCourseCode().equals(((Course)pass[0]).getCourseCode())){
                                cond = false;
                            }
                        }
                        if (cond)
                        globalNotTakenCourses.add(pass); // keep a record of all the not taken courses
                        continue;
                    }
                }

                passClone[1] = note;
                passedCourses.add(passClone);
            }
            passedCo = (ArrayList<Object[]>) passedCourses.clone();
            failedCo = (ArrayList<Object[]>) failedCourses.clone();
            globalPassedCourses.add(passedCo);
            globalFailedCourses.add(failedCo);
            //double g = trans.calculateTermGPA(passedCourses, failedCourses);
        }

        Transcript tr = new Transcript(rand_semester, gpas, 0.0, globalTotalPassedCredits, globalTotalFailedCredits,
                globalPassedCourses, globalFailedCourses, globalNotTakenCourses, null);
        tr.calculateGPAs(rand_semester);
        return tr;
    }


}
