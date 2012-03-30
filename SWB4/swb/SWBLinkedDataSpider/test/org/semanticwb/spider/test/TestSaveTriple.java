/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.spider.test;

import java.net.URI;
import java.net.URL;
import org.semanticwb.linkeddata.spider.SpiderEventListener;

/**
 *
 * @author victor.lorenzana
 */
public class TestSaveTriple implements SpiderEventListener
{

    public void onTriple(URI suj, URI pred, String obj, URL url)
    {
        System.out.println("suj: " + suj + " pred: " + pred + " obj:" + obj + " url: " + url.toString());

    }

    
    public void onError(URL url, int error)
    {
        System.out.println("Error en url: "+url+" code: "+error);        
    }

    public void onError(URL url, Throwable e)
    {
        e.printStackTrace();
    }

    public void onTripleAndFollow(URI suj, URI pred, URI obj, URL url)
    {
        System.out.println("suj: " + suj + " pred: " + pred + " urlobj:" + obj + " url: " + url.toString());
    }

    public void visit(URI suj)
    {
        
    }

    public void onStart(URL url)
    {
        System.out.println("Inicia ------------ URL :"+url+" ----------------------");
    }

    public void onEnd(URL url)
    {
        System.out.println("Termina ------------ URL :"+url+" ----------------------");
    }
}
