import java.time.*;

public class Day {
    public LocalDate date_today;
    public Para paras_today[] = new Para[6];

    public Day(LocalDate today, Para paras[]){
        this.date_today = today;
        this.paras_today = paras;
    }
    @Override
    public String toString(){
        String ret_str = date_today + "{\n";
        for(Para para : paras_today){
            if(para == null){
                ret_str += "Нет пары\n";
            }
            else{
            ret_str += para + "\n";
            }
        }
        return ret_str + "}\n";
    }
}
