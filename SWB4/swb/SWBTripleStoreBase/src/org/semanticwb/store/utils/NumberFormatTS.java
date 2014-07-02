/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author javier.solis.g
 */
public class NumberFormatTS
{
    private final ThreadLocal<java.text.NumberFormat> sd;
    
    public NumberFormatTS(final Locale locale)
    {
        sd = new ThreadLocal<java.text.NumberFormat>()
        {
            @Override
            protected java.text.NumberFormat initialValue()
            {
                NumberFormat nf=java.text.NumberFormat.getInstance(locale);
                return nf;
            }
        };            
    }    
    
    public Number parse(String txt)
    {
        try
        {
            return sd.get().parse(txt);
        }catch(ParseException e)
        {
            e.printStackTrace();   
            return 0;
        }
    }
    
    public Number parse2(String txt)
    {        
        byte b=0;
        long l=0;
        long d=0;
        boolean s=false;
        boolean p=false;
        long pp=1;
        for(int x=0;x<txt.length();x++)
        {
            char c=txt.charAt(x);
            if(c=='-')s=true;
            else if(c>='0' && c<='9')
            {
                b=(byte)(c-'0');
                if(!p)
                {
                    l=l*10+b;
                }else
                {   
                    pp=pp*10;
                    d=d*10+b;
                }
            }else if(c=='.')
            {
                p=true;
            }
        }
        if(s)
        {
            if(p)return -(((double)d)/pp+l);
            return -l;
        }
        
        if(p)return ((double)d)/pp+l;
        return l;
    }
    
    public String format(Number num)
    {
        return sd.get().format(num);
    }
    
    public static void main(String args[])
    {       
        String val="1,000,100.23";
        NumberFormatTS nf=new NumberFormatTS(Locale.US);                
        long time=System.currentTimeMillis();
        
        for(int x=0;x<1000000;x++)
        {
            double d=nf.parse(val).doubleValue();
        }
        System.out.println("time 1:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();        
        
        for(int x=0;x<1000000;x++)
        {
            double d=Double.parseDouble(val.replaceAll(",", ""));
        }
        System.out.println("time 1:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
        
        for(int x=0;x<1000000;x++)
        {
            double d=nf.parse2(val).doubleValue();
        }
        System.out.println("time 1:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();        
        
        for(int x=0;x<1000000;x++)
        {
            String str=Long.toString(1000L);
        }
        System.out.println("time 1:"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();        
        
        System.out.println(nf.parse2("-1000,100.23").getClass());
        System.out.println(nf.parse2("1000,100").getClass());
        System.out.println(nf.parse2("0.22323233").getClass());
        System.out.println(nf.parse2("10").getClass());
    }
}
