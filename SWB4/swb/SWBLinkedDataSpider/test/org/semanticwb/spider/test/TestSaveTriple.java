/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.spider.test;

import java.io.File;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.semanticwb.linkeddata.spider.SpiderEventListener;

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
        try
        {
            out = new PrintStream(new File("C:\\" + df.format(new Date())) + ".log");
            System.setOut(out);


            err = new PrintStream(new File("C:\\" + df.format(new Date())) + "_err.log");
            System.setErr(err);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    SimpleDateFormat df = new SimpleDateFormat("dd'_'MM'_'yyyy'_'HH'_'mm'_'ss'_'SS");

    public void onTriple(URI suj, URI pred, String obj)
    {
        System.out.println("suj: " + suj + " pred: " + pred + " obj:" + obj);

    }

    public void onError(URL url, int error)
    {
        System.err.println("Error en url: " + url + " code: " + error);
    }

    public void onError(URL url, Throwable e)
    {
        e.printStackTrace(err);
    }

    

    public void visit(URI suj)
    {
    }

    public void onStart(URL url)
    {
        System.out.println("Inicia ------------ URL :" + url + " ----------------------");
    }

    public void onEnd(URL url)
    {
        System.out.println("Termina ------------ URL :" + url + " ----------------------");
    }
}
