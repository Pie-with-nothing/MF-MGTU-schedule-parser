public class Study_group {
    private String faculty; 
    private String sub_faculty;
    private int course;
    private String group;

    public Study_group(String faculty, String sub_faculty, int course, String group){
        this.faculty = faculty;
        this.sub_faculty = sub_faculty;
        this.course = course;
        this.group = group;   
    }
    public String get_faculty(){
        return this.faculty;
    }
    public String get_sub_faculty(){
        return this.sub_faculty;
    }
    public int get_course(){
        return this.course;
    }
    public String get_group(){
        return this.group;
    }
}
