import java.util.ArrayList;

public class DepartmentStatistics {

    private int quotaCount = 0;
    private int conflictCount = 0;
    private int engineeringProjectCount = 0;
    private int teCount = 0;

    private ArrayList<String> quotaArray = new ArrayList<String>();
    private ArrayList<String> conflictArray = new ArrayList<String>();
    private ArrayList<String> engineeringProjectArray = new ArrayList<String>();
    private ArrayList<String> teCountArray = new ArrayList<String>();

    public ArrayList<String> getQuotaArray(){
        return this.quotaArray;
    }

    public ArrayList<String> getConflictArray(){
        return this.conflictArray;
    }

    public ArrayList<String> getEngineeringProjectArray(){
        return this.engineeringProjectArray;
    }

    public ArrayList<String> getTeCountArray(){
        return this.teCountArray;
    }

    public void setQuotaArray(ArrayList<String> quotaArray){
        this.quotaArray = quotaArray;
    }

    public void setConflictArray(ArrayList<String> conflictArray){
        this.conflictArray = conflictArray;
    }

    public void setEngineeringProjectArray(ArrayList<String> engineeringProjectArray){
        this.engineeringProjectArray = engineeringProjectArray;
    }

    public void setTeCountArray(ArrayList<String> teCountArray){
        this.teCountArray = teCountArray;
    }

    public void incrementQuotaCount() {
        this.quotaCount += 1;
    }

    public void incrementConflictCount() {
        this.conflictCount += 1;
    }

    public void incrementEngineeringProjectCount() {
        this.engineeringProjectCount += 1;
    }

    public void incrementTeCount() {
        this.teCount += 1;
    }


    public void addToQuotaArray(String s){
        this.quotaArray.add(s);
    }
    public void addToConflictArray(String s){
        this.conflictArray.add(s);
    }
    public void addToEngineeringProjectArray(String s){
        this.engineeringProjectArray.add(s);
    }
    public void addToTeCountArray(String s){
        this.teCountArray.add(s);
    }

    public int getQuotaCount(){
        return quotaCount;
    }

    public int getConflictCount(){
        return conflictCount;
    }

    public int getEngineeringProjectCount(){
        return engineeringProjectCount;
    }

    public int getTeCount(){
        return teCount;
    }

}
