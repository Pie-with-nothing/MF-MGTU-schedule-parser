public class Para {
    public String name;
    public String prepod;
    public String place;

    public Para(String name, String prepod, String place){
        this.name = name;
        this.prepod = prepod;
        this.place = place;
    }
    @Override
    public String toString(){
        String real_prep = prepod == "" ? "(преподаватель не указан)" : prepod;
        String real_place = place == "" ? "(аудитория не указана)" : place;
        String ret_str =  "\t" + name + " - " + real_prep + " в " + real_place;
        return ret_str;
    }
}
