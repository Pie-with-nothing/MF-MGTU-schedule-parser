public class Week {
    private boolean is_even;
    private Day study_days[] = new Day[6];

    public Week(boolean even, Day days[]){
        this.is_even = even;
        this.study_days = days;
    }
    public boolean get_is_even(){
        return this.is_even;
    }
    public Day[] get_days(){
        return this.study_days;
    }
    public Day get_day(int index){
        return this.study_days[index];
    }  
    public void set_is_even(boolean even){
        this.is_even = even;
    }
    public void set_days(Day days[]){
        this.study_days = days;
    }
    public void set_day(Day day, int index){
        this.study_days[index] = day;
    }   
    @Override
    public String toString(){
        String ret_str = is_even ? "Знаменатель" : "Числитель";
        ret_str += "[\n";
        for(Day day : study_days){
            if(day != null){
                ret_str += day.toString();
            }
        }
        return ret_str + "]";
    }
}
//котба молодец!!!!
