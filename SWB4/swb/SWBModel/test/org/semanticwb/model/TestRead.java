/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import java.util.Iterator;
import org.junit.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

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
        SWBPlatform.createInstance(null);
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
    //@Test
    public void test()
    {
        WebSite site=SWBContext.getWebSite("sep");
        long time=System.currentTimeMillis();
        for(int x=0;x<1000;x++)
        {
            WebPage page=site.getWebPage("Page"+x);
            //WebPage page=(WebPage)SWBInstance.getSemanticMgr().getOntology().getSemanticObject("Page"+x);
            //WebPage page=SWBContext.getWebPage("Page"+x);
            String title=page.getTitle();
            System.out.println("Title:"+title);
        }
        System.out.println("Time:"+(System.currentTimeMillis()-time));
        
        time=System.currentTimeMillis();
        Iterator<WebPage> it=site.listWebPages();
        while(it.hasNext())
        {
            WebPage page=it.next();
            String title=page.getURI();
            System.out.println("Title:"+title);
        }
        System.out.println("Time:"+(System.currentTimeMillis()-time));        
    }
    

    @Test
    public void testFormView()
    {
        SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
        SemanticProperty propertyMode=new SemanticProperty(ont.getRDFOntModel().getProperty(SemanticVocabulary.URI+"propertyMode"));
        SemanticProperty propertyRef=new SemanticProperty(ont.getRDFOntModel().getProperty(SemanticVocabulary.URI+"propertyRef"));
        
        
        Iterator<FormView> it=SWBContext.getVocabulary().swbxf_FormView.listGenericInstances();
        while(it.hasNext())
        {
            FormView view=it.next();
            System.out.println("View:"+view.getId());
            Iterator<SemanticObject> itp=view.listCreatePropertys();
            while(itp.hasNext())
            {
                SemanticObject obj=itp.next();
                String mode=obj.getProperty(propertyMode);
                SemanticObject ref=obj.getObjectProperty(propertyRef);
                SemanticProperty prop=obj.transformToSemanticProperty();
                System.out.println("-->prop:"+prop.getURI()+" mode:"+mode+" ref:"+ref);
            }
        }
    }
    

}
