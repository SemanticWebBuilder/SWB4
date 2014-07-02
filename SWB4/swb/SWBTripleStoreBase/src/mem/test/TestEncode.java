/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mem.test;

import java.io.IOException;
import org.semanticwb.store.utils.LexiSortable;
import org.semanticwb.store.utils.Utils;

/**
 *
 * @author javier.solis.g
 */
public class TestEncode
{
    public static void main(String[] args) throws IOException
    {
        int c=1000000;
        
        long time=System.currentTimeMillis();
        long time2=0;
        {
            long x=100*10000L;
            long x2=0;
            String s=null;
            for(int i=0;i<c;i++)
            {
                s=LexiSortable.toLexiSortable(x);
                x2=LexiSortable.longFromLexiSortable(s);
            }
            time2=System.currentTimeMillis();
            System.out.println(x);
            System.out.println(s);
            System.out.println(x2);
        }
        System.out.println("Time:"+(time2-time));
        time=System.currentTimeMillis();
        
        {
            double x=100*10000;
            double x2=0;
            String s=null;
            for(int i=0;i<c;i++)
            {
                s=LexiSortable.toLexiSortable(x);
                x2=LexiSortable.doubleFromLexiSortable(s);
            }
            time2=System.currentTimeMillis();
            System.out.println(x);
            System.out.println(s);
            System.out.println(x2);
        }
        System.out.println("Time:"+(time2-time));
        time=System.currentTimeMillis();        
                
        {
            long x=63;
            long x2=0;
            String s=null;
            for(int i=0;i<c;i++)
            {
                s=Utils.encodeLong(x);
                x2=Utils.decodeLong(s);
            }
            time2=System.currentTimeMillis();
            System.out.println(x);
            System.out.println(s);
            System.out.println(x2);
        }
        System.out.println("Time:"+(time2-time));
        time=System.currentTimeMillis();    
        
        {
            String s="WebPage:10000000000";
            String x=null;
            String x2=null;
            
            for(int i=0;i<c;i++)
            {
                x=encodeURI(s);
                x2=decodeURI(s);
            }
            time2=System.currentTimeMillis();
            System.out.println(s);
            System.out.println(x);
            System.out.println(x2);
        }
        System.out.println("Time:"+(time2-time));
        time=System.currentTimeMillis();            
        
    }
    
    public static String encodeURI(String uri)
    {
        
        int x=uri.lastIndexOf(':');
        if(x>-1)
        {
            String base=uri.substring(0,x+1);            
            String n=uri.substring(x+1);
            Long l=Utils.parseLong(n);
            if(l!=null)
            {
                StringBuffer buf=new StringBuffer();
                buf.append(base);
                buf.append('^');
                buf.append(Utils.encodeSortableLong(l));
                return buf.toString();
            }
        }
        return uri;
    }
    
    public static String decodeURI(String uri)
    {
        
        int x=uri.lastIndexOf(":^");
        if(x>-1)
        {
            String base=uri.substring(0,x+1);            
            String n=uri.substring(x+2);
            return base+Utils.decodeLong(n);
        }
        return uri;
    }    

    
}
