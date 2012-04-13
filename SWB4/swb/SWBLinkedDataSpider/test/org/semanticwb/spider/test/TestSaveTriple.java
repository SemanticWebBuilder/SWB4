/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.spider.test;

import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.semanticwb.linkeddata.spider.SpiderEventListener;
import org.semanticwb.linkeddata.spider.Spider;
import org.semanticwb.linkeddata.spider.SpiderException;

/**
 *
 * @author victor.lorenzana
 */
public class TestSaveTriple implements SpiderEventListener
{
    DecimalFormat d_f=new DecimalFormat("###.##");
    PrintStream out;
    PrintStream err;
    long tini;
    int totalNTFormat;
    public TestSaveTriple()
    {
        tini=System.currentTimeMillis();
//        try
//        {
//            out = new PrintStream(new File("C:\\" + df.format(new Date())) + ".log");
//            System.setOut(out);
//
//
//            err = new PrintStream(new File("C:\\" + df.format(new Date())) + "_err.log");
//            System.setErr(err);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
    }
    SimpleDateFormat df = new SimpleDateFormat("dd'_'MM'_'yyyy'_'HH'_'mm'_'ss'_'SS");

    public void onTriple(URI suj, URI pred, String obj, Spider spider, String lang)
    {
        //System.out.println("suj: " + suj + " pred: " + pred + " obj:" + obj);
    }

    public void onError(URL url, int error)
    {
        System.err.println("Error en url: " + url + " code: " + error);
    }

    public void onError(URL url, Throwable e)
    {

        if (e instanceof SpiderException)
        {
            System.err.println("Error en url " + ((SpiderException) e).getSpider().getURL());
        }
        e.printStackTrace(System.err);
    }

    public boolean onNewSubject(URI suj)
    {
        return true;
    }

    public void onStart(URL url)
    {
        //String date=df.format(new Date());
        //System.out.println(date+": Inicia ------------ URL :" + url + " ----------------------");
    }

    public void onEnd(URL url)
    {
        //String date=df.format(new Date());
        //System.out.println(date+": Termina ------------ URL :" + url + " ----------------------");
    }

    public void onNTFormat(String row)
    {
        totalNTFormat++;
        
        long dif=System.currentTimeMillis()-tini;
        double vel=(double)totalNTFormat*1000*60*60/(double)dif;
        //System.out.println("velocidad : "+d_f.format(vel)+" reg/hr");
        System.out.println(row);
    }

    public void onTriple(URI suj, URI pred, URI obj, Spider source, String lang)
    {
    }
}
