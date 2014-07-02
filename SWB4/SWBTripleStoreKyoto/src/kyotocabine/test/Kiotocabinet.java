package kyotocabine.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Iterator;
import java.util.List;
import java.util.Random;
import kyotocabinet.Cursor;
import kyotocabinet.Cursor;
import kyotocabinet.DB;
import kyotocabinet.DB;

/**
 *
 * @author javier.solis.g
 */
public class Kiotocabinet
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        long time=System.currentTimeMillis();
        // create the object
        DB db = new DB();

        // open the database
        if (!db.open("/data/kyoto/_swb_s.kct", DB.OWRITER | DB.OCREATE ))  //DB.OAUTOTRAN DB.OAUTOSYNC
        {
            System.err.println("open error: " + db.error());
        }
        System.out.println("Open:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
       
        long c=db.count();
        System.out.println(c);
        System.out.println("count:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
        
        if(c<10000000)
        {
            Random ran=new Random();
            for(long x=c;x<c+10000000;x++)
            {
                db.set("jei"+x, "");//"prop"+ran.nextInt());
            }
            System.out.println("load:"+(System.currentTimeMillis()-time));
        }
        time=System.currentTimeMillis();
        
//        c=0;
//        for(long x=c;x<c+1000000;x++)
//        {
//            db.add("jei"+x, "2prop"+x);
//        }        
        
        // retrieve records
        String value = db.get("foo");        
        System.out.println("find:"+value +" "+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();

        Cursor cur=null;
        String[] rec;
        int x=0;
/**********************/     
        // traverse records
        cur = db.cursor();
        cur.jump();
        x=0;
        do
        {
            x++;
            if(x==1000000)break;
        }while(cur.step());
        cur.disable();
        System.out.println("move:"+x+" "+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();        
        
//*********************
        // traverse records
        cur = db.cursor();
        cur.jump();
        x=0;
        String key;
        while ((key = cur.get_key_str(true)) != null)
        {
            //System.out.println(key);
            x++;
            if(x==1000000)break;
        }
        cur.disable();
        System.out.println("read1:"+x+" "+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();

//*********************/
        String str=null;
        for(x=0;x<1000000;x++)
        {
            str=db.get("jei9"+x);
        }
        System.out.println("get:" +str+" "+ (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();

//*********************/
        // traverse records
        cur = db.cursor();
        cur.jump("jei9");
        x=0;
        while ((rec = cur.get_str(true)) != null)
        {
            x++;
            //System.out.println(rec[0] + ":" + rec[1]);
            if(x==1000000)break;
        }
        cur.disable();
        System.out.println("read2:"+x+" "+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();       
        
//*********************/        
        // traverse records
        //List<String> l = db.match_prefix("<http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/ProductFeature10000>", -1);
        List<String> l = db.match_prefix("jei9", -1);
        System.out.println(l.size());;
        Iterator<String> it=l.iterator();
        while (it.hasNext())
        {
            String k = it.next();
            //System.out.println(k);
        }
        System.out.println("search:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();          

//*********************/        
        // close the database
        if (!db.close())
        {
            System.err.println("close error: " + db.error());
        }
        System.out.println("close:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();

    }
}
