/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.spider.test;

import java.io.File;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.semanticwb.linkeddata.spider.SpiderEventListener;
import org.semanticwb.linkeddata.spider.Spider;

/**
 *
 * @author victor.lorenzana
 */
public class TestSaveTriple implements SpiderEventListener
{

    DecimalFormat d_f = new DecimalFormat("###.##");
    PrintStream out;
    PrintStream err;
    long tini;
    int totalNTFormat;

    public TestSaveTriple()
    {


        Properties properties = new Properties();
        File journal = new File("/data.jnl");
//        properties.setProperty(BigdataSail.Options.FILE, journal.getAbsolutePath());
        //The persistence engine.  Use 'Disk' for the WORM or 'DiskRW' for the RWStore.
        properties.setProperty("com.bigdata.journal.AbstractJournal.bufferMode", "DiskRW");
        properties.setProperty("com.bigdata.btree.writeRetentionQueue.capacity", "4000");
        properties.setProperty("com.bigdata.btree.BTree.branchingFactor", "128");

        //# 200M initial extent.
        properties.setProperty("com.bigdata.journal.AbstractJournal.initialExtent", "100715200");
        properties.setProperty("com.bigdata.journal.AbstractJournal.maximumExtent", "100715200");

        //##
        //## Setup for QUADS mode without the full text index.
        //##
        properties.setProperty("com.bigdata.rdf.sail.isolatableIndices", "true");
        properties.setProperty("com.bigdata.rdf.sail.truthMaintenance", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.quads", "true");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.statementIdentifiers", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.textIndex", "false");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.axiomsClass", "com.bigdata.rdf.axioms.NoAxioms");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.vocabularyClass", "com.bigdata.rdf.vocab.NoVocabulary");
        properties.setProperty("com.bigdata.rdf.store.AbstractTripleStore.justify", "false");

        tini = System.currentTimeMillis();
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

    @Override
    public void onTriple(URI suj, URI pred, String obj, Spider spider, String lang)
    {
        System.out.println("onTriple 1 suj: " + suj + " pred: " + pred + " obj:" + obj);
        //new Throwable().printStackTrace();
    }

    @Override
    public void onTriple(URI suj, URI pred, URI obj, Spider source, String lang)
    {
        System.out.println("onTriple 2 suj: " + suj + " pred: " + pred + " obj:" + obj);
        //new Throwable().printStackTrace();
    }

    @Override
    public void onError(URL url, int error)
    {
        System.err.println("Error en url: " + url + " code: " + error);
    }

    @Override
    public void onError(URL url, Throwable e)
    {
//        if (e instanceof SpiderException)
//        {
//            System.err.println("Error en url " + ((SpiderException) e).getSpider().getURL());
//        }
//        e.printStackTrace(System.err);
    }

    @Override
    public boolean onNewSubject(URI suj)
    {
        return false;
    }

    @Override
    public void onStart(URL url)
    {
        String date=df.format(new Date());
        System.out.println(date+": Inicia ------------ URL :" + url + " ----------------------");
        //new Throwable().printStackTrace();
    }

    @Override
    public void onEnd(URL url)
    {
        String date=df.format(new Date());
        //System.out.println(date+": Termina ------------ URL :" + url + " ----------------------");
    }

    @Override
    public void onNTFormat(String row)
    {
        totalNTFormat++;

        long dif = System.currentTimeMillis() - tini;
        double vel = (double) totalNTFormat * 1000 * 60 * 60 / (double) dif;
        //System.out.println("velocidad : "+d_f.format(vel)+" reg/hr");
        //System.out.println(row);
    }
}
