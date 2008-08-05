/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import org.junit.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBInstance;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticModel;

/**
 *
 * @author Jei
 */
public class TestCreate {

   private Logger log=SWBUtils.getLogger(TestCreate.class);

    public TestCreate()
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
        SemanticModel model=SWBInstance.getSemanticMgr().loadDBModel("SWBAdmin");
        
        long time=System.currentTimeMillis();
        for(int x=0;x<1000;x++)
        {
            WebPage page=SWBContext.createWebPage(model, "Page"+x);
            page.setTitle("Pagina Numero "+x);
        }
        System.out.println("Time:"+(System.currentTimeMillis()-time));
        
        time=System.currentTimeMillis();
        for(int x=0;x<10;x++)
        {
            WebPage page=SWBContext.getWebPage("Page"+x);
            String title=page.getTitle();
            System.out.println("Title:"+title);
        }
        System.out.println("Time:"+(System.currentTimeMillis()-time));
        
    }

}
