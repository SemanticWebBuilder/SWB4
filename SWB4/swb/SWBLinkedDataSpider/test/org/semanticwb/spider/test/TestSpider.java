/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.spider.test;

import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.linkeddata.spider.Spider;

/**
 *
 * @author victor.lorenzana
 */
public class TestSpider
{

    public TestSpider()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @Ignore
    public void SpiderFOAFTest()
    {
        try
        {
            URL url = new URL("http://harth.org/andreas/foaf.rdf");
            Spider spider = new Spider(url);
            spider.addTripleEvent(new TestSaveTriple());
            spider.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    //@Ignore
    public void SpiderMusicbrainzTest()
    {
        try
        {
            //http://musicbrainz.org/release-group/21a136b7-54dd-31dc-a4d9-90c2b833b786
            //URL url = new URL("http://dbpedia.org/ontology/MusicalArtist");}
            URL url = new URL("http://dbpedia.org/page/Stevie_Nicks");
            Spider spider = new Spider(url);
            spider.addTripleEvent(new TestSaveTriple());
            spider.start();
            try
            {
                Thread.sleep(1400000);
            }
            catch(Exception e)
            {
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
