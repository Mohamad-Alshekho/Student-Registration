import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OutputGenerator {

    private DepartmentStatistics departmentStatistics;


//ObjectMapper objectMapper = new ObjectMapper();
//objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
//
//String originalJson = ...
//JsonNode tree = objectMapper .readTree(originalJson);
//String formattedJson = objectMapper.writeValueAsString(tree);

    public OutputGenerator(DepartmentStatistics departmentStatistics){
        this.departmentStatistics = departmentStatistics;
    }


    public static void writeStudents(ArrayList<Student> students) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);



        for (Student s: students){
            // String studentJson = new Gson().toJson(s);
            JSONObject mJSONObject = new JSONObject(s);
            try {
                FileWriter file = new FileWriter("studentJsonFiles/"+ s.getStudentId().getId()  +".json");

                String originalJson = String.valueOf(mJSONObject);
                JsonNode tree = objectMapper.readTree(originalJson);
                String formattedJson = objectMapper.writeValueAsString(tree);

                file.write(formattedJson);
                file.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void evaluateCourseStatistics() {
        this.departmentStatistics.setConflictArray(removeDuplicates(this.departmentStatistics.getConflictArray()));

        String jsonString = convertToJson();
        try{
            FileWriter file = new FileWriter("jsonFiles/department_output2.json");
            file.write(jsonString);
            file.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public String convertToJson(){
        String ret = "{";

        ret += "\n\t\"number of quote failure\":" + this.departmentStatistics.getQuotaCount() + ",\n\t";
        ret += "\"students who couldn't register due to quota\": [\n\t";
        for (String s: this.departmentStatistics.getQuotaArray()){
            ret += "\t" + s + ",\n\t";
        }
        ret = ret.substring(0,ret.length()-3) + "\n\t";

        ret += "],\n";

        ret += "\n\t\"number of conflict failure\":" + this.departmentStatistics.getConflictCount() + ",\n\t";
        ret += "\"students who couldn't register due to conflict\": [\n\t";
        for (String s: this.departmentStatistics.getConflictArray()){
            ret += "\t" + s + ",\n\t";
        }
        ret = ret.substring(0,ret.length()-3) + "\n\t";
        ret += "],\n";

        ret += "\n\t\"number of engineering project registration failure\":" + this.departmentStatistics.getEngineeringProjectCount() + ",\n\t";
        ret += "\"students who couldn't register for engineering project due to credit requirement\": [\n\t";
        for (String s: this.departmentStatistics.getEngineeringProjectArray()){
            ret += "\t" + s + ",\n\t";
        }
        ret = ret.substring(0,ret.length()-3) + "\n\t";

        ret += "],\n";

        ret += "\n\t\"number of technical elective registration failure\":" + this.departmentStatistics.getTeCount() + ",\n\t";
        ret += "\"students who couldn't register for technical elective due to credit requirement\": [\n\t";
        for (String s: this.departmentStatistics.getTeCountArray()){
            ret += "\t" + s + ",\n\t";
        }
        ret = ret.substring(0,ret.length()-3) + "\n\t";

        ret += "]\n}";

        System.out.println(ret);
        return ret;
    }

    public static ArrayList<String> removeDuplicates(ArrayList<String> list)
    {
        ArrayList<String> newList = new ArrayList<String>();
        for (String element : list) {
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }
        return newList;
    }
}
