
import java.util.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jei
 */
public class bor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Date exdate=new Date("07/14/10 12:00");
        Date aux=new Date();
        Date cdate=new Date(aux.getYear(),aux.getMonth(),aux.getDate(),aux.getHours(),aux.getMinutes());

        String time = "2";
        long inter=Long.parseLong(time)*1000*60;
        long act=cdate.getTime()-exdate.getTime();

        System.out.println(exdate);
        System.out.println(aux+" "+aux.getTime()+" "+cdate.getTime());
        System.out.println(inter);
        System.out.println(act);
        System.out.println(act%inter);

    }

}
