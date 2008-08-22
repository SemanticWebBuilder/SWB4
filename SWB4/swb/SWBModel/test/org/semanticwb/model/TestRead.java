/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import java.util.Iterator;
import org.junit.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBInstance;
import org.semanticwb.SWBUtils;

/**
 *
 * @author Jei
 */
public class TestRead {

   private Logger log=SWBUtils.getLogger(TestRead.class);

    public TestRead()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        SWBInstance.createInstance(null);
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
    public void test()
    {
        WebSite site=SWBContext.getWebSite("sep");
        long time=System.currentTimeMillis();
        for(int x=0;x<100;x++)
        {
            WebPage page=site.getWebPage("Page"+x);
            //WebPage page=(WebPage)SWBInstance.getSemanticMgr().getOntology().getSemanticObject("Page"+x);
            //WebPage page=SWBContext.getWebPage("Page"+x);
            //String title=page.getTitle();
            //System.out.println("Title:"+title);
        }
        System.out.println("Time:"+(System.currentTimeMillis()-time));
        
        time=System.currentTimeMillis();
        Iterator<WebPage> it=site.listWebPages();
        while(it.hasNext())
        {
            WebPage page=it.next();
            //String title=page.getTitle();
            //System.out.println("Title:"+title);
        }
        System.out.println("Time:"+(System.currentTimeMillis()-time));        
    }

}
