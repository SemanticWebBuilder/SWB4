
package org.semanticwb.portal.db;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/*import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;*/

public class SWBRecHit implements java.io.Serializable {
    private Timestamp date;
    private int iditem;
    private String item;
    private String topicmap;
    private String section;
    private int type;
    private long hits;
    
    private int year;
    private String month;
    private int day;

    private Locale locale;
    private GregorianCalendar gc;
    
    public SWBRecHit(){
    }
    
    //new SWBRecHit(date,rs.getString("topicmap"),rs.getString("idaux"),rs.getInt("type"),rs.getInt("hits"));
    public SWBRecHit(Timestamp date, String topicmap, String section, int type, long hits){
        this(date, topicmap, section, type, hits, "es");
    }

    public SWBRecHit(Timestamp date, String topicmap, String section, int type, long hits, String language){
        locale = new Locale(language);

        this.date = date;
        if(date!=null){
            gc = new GregorianCalendar(locale);
            gc.setTimeInMillis(date.getTime());
            this.setYear(gc.get(GregorianCalendar.YEAR));
            this.setMonth(Integer.toString(gc.get(GregorianCalendar.MONTH)));
            this.setDay(gc.get(GregorianCalendar.DAY_OF_MONTH));
        }
        this.topicmap = topicmap;
        this.section = section;
        this.type = type;
        this.hits = hits;
    }

    public Timestamp getDate() {
        return date;
    }  

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    public int getIditem() {
        return iditem;
    }

    public void setIditem(int iditem) {
        this.iditem = iditem;
    }
    
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTopicmap() {
        return topicmap;
    }

    public void setTopicmap(String topicmap) {
        this.topicmap = topicmap;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public int getYear() {
        return year;
    }

    private void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        SimpleDateFormat formatter  = new SimpleDateFormat("MMMM", locale);
        return formatter.format(date);



        /*String mes;
        int imes = Integer.parseInt(month);
        switch(imes){
            case 0: mes="Enero"; break;
            case 1: mes="Febrero"; break;
            case 2: mes="Marzo"; break;
            case 3: mes="Abril"; break;
            case 4: mes="Mayo"; break;
            case 5: mes="Junio"; break;
            case 6: mes="Julio"; break;
            case 7: mes="Agosto"; break;
            case 8: mes="Septiembre"; break;
            case 9: mes="Octubre"; break;
            case 10: mes="Noviembre"; break;
            case 11: mes="Diciembre"; break;
            default: mes="Desconocido";
        }      
        return mes;*/
    }

    public String getMonth(String pattern) {
        SimpleDateFormat formatter  = new SimpleDateFormat(pattern, locale);
        return formatter.format(date);
    }

    public int getMonthToInt() {
        SimpleDateFormat formatter  = new SimpleDateFormat("M");
        try {
            return Integer.parseInt(formatter.format(date), 10);
        }catch(NumberFormatException e) {
            return -1;
        }
    }

    private void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    private void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("date="+date);
        sb.append(", iditem="+iditem);
        sb.append(", item="+item);
        sb.append(", topicmap="+topicmap);
        sb.append(", section="+section);
        sb.append(", type="+type);
        sb.append(", hits="+hits);
        sb.append(", year="+year);
        sb.append(", month="+month);
        sb.append(", day="+day);
        return sb.toString();
    }
}
