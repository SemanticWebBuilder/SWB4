/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.spider.test;

import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
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

    PrintStream out;
    PrintStream err;

    public TestSaveTriple()
    {
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
            System.err.println("ERror en url " + ((SpiderException) e).getSpider().getURL());
        }
        e.printStackTrace(err);
    }

    public void onNewSubject(URI suj)
    {
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
        String date = df.format(new Date());
        System.out.println(row);
    }
}
