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

/**
 *
 * @author Jei
 */
public class TestWebPages {

   private Logger log=SWBUtils.getLogger(TestCreate.class);

    public TestWebPages()
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
    public void testCreate()
    {
        WebSite site=SWBContext.createWebSite("sep", "http://www.sep.gob.mx");
        WebPage home=site.createWebPage("home");
        home.setTitle("Home");
        site.setHomePage(home);
//        for(int x=0;x<10;x++)
//        {
//            WebPage page=site.createWebPage("page"+x);
//            page.setTitle("Page"+x);
//            page.addParentWebPage(home);
//            for(int y=0;y<10;y++)
//            {
//                WebPage ipage=site.createWebPage("page"+x+"_"+y);
//                ipage.setTitle("Page"+x+"."+y);
//                ipage.addParentWebPage(page);
//            }
//        }
    }
    
    //@Test
    public void testRead()
    {
        WebSite site=SWBContext.getWebSite("sep");
        WebPage home=site.getHomePage();
        System.out.println("Home:"+home);
        printChilds(home);
    }
    
    public void printChilds(WebPage page)
    {
        Iterator<WebPage> it=page.listChilds();
        while(it.hasNext())
        {
            WebPage p=it.next();
            System.out.println("Page:"+p);
            printChilds(p);
        }
    }
    
    @Test
    public void testCreateLocale()
    {
        WebSite site=SWBContext.getWebSite("sep");
        WebPage home=site.getHomePage();
        home.setTitle("6_Inicio");
        home.setTitle("6_Inicio_es", "es");
        home.setTitle("6_Home_en", "en");
        home.setTitle("6_Home_fr", "fr");         
    }    
    
    @Test
    public void testReadLocale()
    {
        WebSite site=SWBContext.getWebSite("sep");
        WebPage home=site.getHomePage();
        System.out.println("Home:"+home.getId()+" "+home.getURI()+" "+home.hashCode()+" "+home.getSemanticObject().getRDFResource().getModel());
        home=site.getWebPage("home");
        System.out.println("Home:"+home.getId()+" "+home.getURI()+" "+home.hashCode()+" "+home.getSemanticObject().getRDFResource().getModel());
        System.out.println("getTitle:"+home.getTitle());
        System.out.println("getTitle_es:"+home.getTitle("es"));
        System.out.println("getTitle_en:"+home.getTitle("en"));
        System.out.println("getTitle_fr:"+home.getTitle("fr"));
    }

}
