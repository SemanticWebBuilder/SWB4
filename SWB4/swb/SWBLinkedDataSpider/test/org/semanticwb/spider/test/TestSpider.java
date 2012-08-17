/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.spider.test;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.linkeddata.spider.SpiderManager;


/**
 *
 * @author victor.lorenzana
 */
public class TestSpider {

    public TestSpider() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @Ignore
    public void SpiderFOAFTest() {

        try {
            String database = "/repository/data.jnl";
            String RDFStore = "Bigdata";
            //String database = "linkeddatastore";
            //String RDFStore = "MySQL";      
            URL url = new URL("http://www.foaf-project.org/");

            SpiderManager.addSpiderEventListener(new TestSaveTriple(database, RDFStore, url));
            SpiderManager.createSpider(url);

            try {
                Thread.sleep(650000);
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    //@Ignore
    public void SpiderLiveDBPediaTest() {

        System.setProperty("org.semanticwb.linkeddata.spider.SpiderManager.NoPred", "true");
        try {
            //String database = "/repository/data.jnl";
            //String RDFStore = "Bigdata";
            String database = "ldstore";
            String RDFStore = "MySQL";            

            URL url = new URL("http://dbpedia.org/page/Mexico");
            //URL url = new URL("http://live.dbpedia.org/page/Category:Visitor_attractions_in_Mexico");           
            
            SpiderManager.addSpiderEventListener(new TestSaveTriple(database, RDFStore, url));
            SpiderManager.createSpider(url);


            try {
                Thread.sleep(3500000);
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @Ignore
    public void SpiderMusicbrainzTest() {


        try {
            String database = "/repository/data.jnl";
            String RDFStore = "Bigdata";
            //String database = "linkeddatastore";
            //String RDFStore = "MySQL";            

            URL url = new URL("http://dbpedia.org/page/Stevie_Nicks");            
            SpiderManager.addSpiderEventListener(new TestSaveTriple(database, RDFStore, url));
            SpiderManager.createSpider(url);


            try {
                Thread.sleep(40000);
                //Thread.sleep(100000);
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
