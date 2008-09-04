
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;

/**
 *
 * @author Jei
 */
public class Borra {

    /**
     * @param args the commandint line arguments
     */
    public static void main(String[] args) 
    {
        int n=100;
        long time=System.currentTimeMillis();
        HashMap map=new HashMap();
        for(int x=0;x<n;x++)
        {
            map.put(""+x, "hola"+x);
        }
        System.out.println("Tiempo:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
        long l=0;
        for(int x=0;x<500000;x++)
        {
            int r=(int)(Math.random()*n);
            //System.out.println("rand:"+r);
            String tmp=(String)map.get(""+r);
            if(tmp!=null)
            {
                l+=tmp.length();
            }
            //l+=r;
        }
        System.out.println("Tiempo:"+(System.currentTimeMillis()-time)+" l:"+l);
    }

}
