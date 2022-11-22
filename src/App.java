import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ListIterator;

public class App {
    private static String host = "https://lks.bmstu.ru/";
    private static String url = "schedule/list";
    private static String list_html = "group_list.html";
    private static String sched_html = "schedule.html";
    private Study_group grp;

    public App(Study_group need_grp){
        grp = need_grp;
    }
    public Document req_group_list() throws IOException{
        Document doc = Jsoup.connect(host + url).get();
        return doc;
    }
    public Document req_need_group(Document grp_list) throws IOException{
        Element facults = grp_list.getElementsByClass("list-group accordion").last();
        Elements raw_facult = facults.select("h4.list-group-item-heading");
        Element facult = new Element("div");
        for(Element element : raw_facult){
            if(element.text().equalsIgnoreCase(grp.get_faculty())){   
                facult = element.parent().nextElementSibling();
            }
        }
        Elements struct_of_groups = facult.getElementsByClass("btn btn-primary col-1 rounded schedule-indent");
        String group_schedule_link = "";
        for(Element groups : struct_of_groups){
            if(groups.text().equalsIgnoreCase(grp.get_group())){
                group_schedule_link = groups.attr("href");
            }
        }
        Document group_schedule_doc = Jsoup.connect(host + group_schedule_link).get();
        return group_schedule_doc;
    }
    
    public Week get_schedule(Document doc){
        Week week = new Week(true, new Day[6]);
        String head[] = doc.getElementsByTag("h4").first().text().split(" ");

        week.set_is_even(head[2].equalsIgnoreCase("знаменатель") ? true : false);

        LocalDate start_date = LocalDate.now();
        if(LocalDate.now().getMonthValue() < 9){
            start_date = LocalDate.of(LocalDate.now().getYear() - 1, 9, 1);
        }
        else{
            start_date = LocalDate.of(LocalDate.now().getYear(), 9, 1);
        }
        System.out.println(start_date + "\n");
        LocalDate week_now = start_date.plusWeeks(Integer.parseInt(head[0]) - 1);
        LocalDate start_week_now = week_now.minusDays(week_now.getDayOfWeek().getValue() - 1);

        Elements all_tabl = doc.getElementsByClass("table table-bordered text-center table-responsive");
        int i = 0;
        for(Element day_tabl : all_tabl){
            Day day = new Day(start_week_now.plusDays(i), new Para[6]);
            ListIterator <Element> pars = day_tabl.getElementsByTag("tr").listIterator(2);
            int k = 0;
            while(pars.hasNext()){
                Element para_now = pars.next();
                Element where_search = new Element("div");
                if(!(para_now.select("td[colspan]").isEmpty())){
                    where_search = para_now;
                }
                else if(week.get_is_even()){
                    //System.out.println(para_now.select(".text-primary").text());
                    if(
                        para_now.select(".text-primary").hasText() &&
                        para_now.select(".text-primary").text().isBlank()
                    )
                    {
                        where_search = para_now.select(".text-primary").first();
                    }
                }
                else{
                    //System.out.println(para_now.select(".text-info-bold").text());
                    if(
                        para_now.select(".text-info-bold").hasText() &&
                        !para_now.select(".text-info-bold").text().isBlank()
                    )
                    {
                        where_search = para_now.select(".text-info-bold").first();
                    }
                }
                if(!where_search.hasSameValue(new Element("div"))){
                    day.paras_today[k] = new Para(
                                                where_search.getElementsByTag("span").text(),
                                                where_search.getElementsByTag("i").last().text(),
                                                where_search.getElementsByTag("i").first().text()
                                                );
                }
                k++;
            }
            week.set_day(day, i);
            i++;
        }
        return week;
    }
    
    public void save_html(Document doc, String filename){
        try(
            FileOutputStream fos = new FileOutputStream(filename);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw)){
            writer.write(doc.html());
            writer.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Document load_html(String filename) throws IOException{
        File fl = new File(filename);
        return Jsoup.parse(fl, "UTF-8");
    }
    public static void main(String[] args) throws IOException {
        App app = new App(new Study_group("К", "К3", 2, "К3-33Б"));
        /*
        Document doc = app.req_group_list();
        app.save_html(doc, list_html);
        Document sched = app.req_need_group(doc);
        app.save_html(sched, sched_html);
        System.out.println(app.get_schedule(sched));
        */
        Document sched = app.load_html(sched_html);
        //app.get_schedule(sched);
        System.out.println(app.get_schedule(sched));

        //Para p = new Para(1, "Инжа", "Qqqqq");
        //Para a = new Para(2, "Инфа", "Wwwww");
        //Day arrDay[] = {new Day(LocalDate.of(2020,01,21), new Para[]{p, a}), new Day(LocalDate.of(2021,01,21), new Para[2])};
        //System.out.println(new Week(true, arrDay));
    }
}