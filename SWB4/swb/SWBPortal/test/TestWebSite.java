
import com.hp.hpl.jena.ontology.OntResource;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Dns;
import org.semanticwb.model.Language;
import org.semanticwb.model.PortletType;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.PortletRef;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;


/**
 *
 * @author Jei
 */
public class TestWebSite {
    

    public TestWebSite()
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
    @Test
    public void Test1()
    {
        if(SWBContext.getWebSite("sep")==null)
        {
            WebSite site=SWBContext.createWebSite("sep", "http://www.sep.gob.mx");
            site.setTitle("Sep");
            site.setDescription("Sep WebSite");
            site.setActive(true);
            
            //Crear lenguajes por defecto
            Language lang=site.createLanguage("es");
            lang.setTitle("Español", "es");
            lang.setTitle("Spanish", "en");
            lang=site.createLanguage("en");
            lang.setTitle("Ingles", "es");
            lang.setTitle("English", "en");
            
            //Create HomePage
            WebPage home=site.createWebPage("home");
            site.setHomePage(home);
            home.setActive(true);
            home.setTitle("Pagina Principal");

            //Set User Repository
            UserRepository urep=SWBContext.getDefaultRepository();
            site.setUserRepository(urep);

            //Create DNS
            WebSite global=SWBContext.getGlobalWebSite();
            Dns dns=global.getDns("localhost");
            dns.setWebPage(home);

            Template tpl=site.createTemplate();
            tpl.setActive(true);
            tpl.setTitle("Platilla1");
            
            VersionInfo version=site.createVersionInfo();
            version.setVersionNumber(1);
            version.setVersionFile("template.html");
            tpl.setActualVersion(version);
            tpl.setLastVersion(version);
            
            TemplateRef tplref=site.createTemplateRef();
            tplref.setTemplate(tpl);
            tplref.setActive(true);
            tplref.setPriority(3);
            
            home.addTemplateRef(tplref);
        }
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void Test2()
    {
        WebSite site=SWBContext.getWebSite("sep");
        WebPage home=site.getHomePage();
        UserRepository urep=SWBContext.getDefaultRepository();
        User user=urep.getUser("1");
        Template tpl=site.getTemplate("1");
        
        if(site.getPortletType("Test")==null)
        {
            
            PortletType ptype=site.createPortletType("Test");
            ptype.setPortletClassName("org.semanticwb.portal.resources.Test");
            ptype.setPortletBundle("org.semanticwb.portal.resources.Test");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso Test");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("Test");
            
            PortletRef portletref=site.createPortletRef();
            portletref.setActive(true);
            portletref.setPortlet(portlet);
            portletref.setPriority(3);
            home.addPortletRef(portletref);            
            
            portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("Test");
            
            portletref=site.createPortletRef();
            portletref.setActive(true);
            portletref.setPortlet(portlet);
            portletref.setPriority(3);
            home.addPortletRef(portletref);            
            
        }
        
        if(site.getPortletType("Banner")==null)
        {
            
            PortletType ptype=site.createPortletType("Banner");
            ptype.setPortletClassName("org.semanticwb.portal.resources.Banner");
            ptype.setPortletBundle("org.semanticwb.portal.resources.Banner");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso Banner");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("Banner");
        }       
        
        if(site.getPortletType("Menu")==null)
        {
            
            PortletType ptype=site.createPortletType("Menu");
            ptype.setPortletClassName("org.semanticwb.portal.resources.Menu");
            ptype.setPortletBundle("org.semanticwb.portal.resources.Menu");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso Menu");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("Menu");
        }          
        
        if(site.getPortletType("HtmlContent")==null)
        {
            
            PortletType ptype=site.createPortletType("HtmlContent");
            ptype.setPortletClassName("org.semanticwb.portal.resources.HtmlContent");
            ptype.setPortletBundle("org.semanticwb.portal.resources.HtmlContent");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso HtmlContent");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("HtmlContent");
            
            PortletRef portletref=site.createPortletRef();
            portletref.setActive(true);
            portletref.setPortlet(portlet);
            portletref.setPriority(3);
            
            home.addPortletRef(portletref);
        }         
        
        if(site.getPortletType("HelloXforms")==null)
        {
            
            PortletType ptype=site.createPortletType("HelloXforms");
            ptype.setPortletClassName("org.semanticwb.portal.resources.HelloXforms");
            ptype.setPortletBundle("org.semanticwb.portal.resources.HelloXforms");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso HelloXforms");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("HelloXforms");
            
            PortletRef portletref=site.createPortletRef();
            portletref.setActive(true);
            portletref.setPortlet(portlet);
            portletref.setPriority(3);
            
            home.addPortletRef(portletref);
        }     
    }
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void Test3()
    {
        WebSite site=SWBContext.getWebSite("sep");
        WebPage home=site.getHomePage();
        UserRepository urep=site.getUserRepository();
        Template tpl=site.getTemplate("1");

        if(site.getWebPage("page0")==null)
        {
            for(int x=0;x<10;x++)
            {
                WebPage page=site.createWebPage("page"+x);
                page.setTitle("Pagina "+x);
                page.setParent(home);
                page.setActive(true);
                for(int y=0;y<10;y++)
                {
                    WebPage child=site.createWebPage("page"+x+"_"+y);
                    child.setTitle("Pagina "+x+" "+y);
                    child.setParent(page);
                    child.setActive(true);  
//                    for(int z=0;z<10;z++)
//                    {
//                        WebPage tchild=site.createWebPage("page"+x+"_"+y+"_"+z);
//                        tchild.setTitle("Pagina "+x+" "+y+" "+z);
//                        tchild.setParent(child);
//                        tchild.setActive(true);                    
//                    }                    
                }
            }
        }
    }
    
    //@Test
    public void TestAdmin1()
    {    
        if(SWBContext.getWebSite("SWBAdmin")==null)
        {
            WebSite site=SWBContext.createWebSite("SWBAdmin", "http://www.semanticwb.org/SWBAdmin");
            site.setTitle("Admin");
            site.setDescription("Admin WebSite");
            site.setActive(true);
            
            //Crear lenguajes por defecto
            Language lang=site.createLanguage("es");
            lang.setTitle("Español", "es");
            lang.setTitle("Spanish", "en");
            lang=site.createLanguage("en");
            lang.setTitle("Ingles", "es");
            lang.setTitle("English", "en");
            
            //Create HomePage
            WebPage home=site.createWebPage("home");
            site.setHomePage(home);
            home.setActive(true);
            home.setTitle("Semantic WebBuilder");

            //Set User Repository
            UserRepository urep=SWBContext.getDefaultRepository();
            site.setUserRepository(urep);

            Template tpl=site.createTemplate();
            tpl.setActive(true);
            tpl.setTitle("container");
            
            VersionInfo version=site.createVersionInfo();
            version.setVersionNumber(1);
            version.setVersionFile("template.html");
            tpl.setActualVersion(version);
            tpl.setLastVersion(version);
            
            TemplateRef tplref=site.createTemplateRef();
            tplref.setTemplate(tpl);
            tplref.setActive(true);
            tplref.setPriority(3);
            
            home.addTemplateRef(tplref);
        }        
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void TestOntology()
    {
        WebSite site=SWBContext.getWebSite("sep");
        WebPage home=site.getHomePage();
        UserRepository urep=site.getUserRepository();
        Template tpl=site.getTemplate("1");

        User user=urep.getUserByLogin("admin");
        System.out.println("User:"+user);

        WebPage homeOnt=(WebPage)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(home.getURI(), SWBContext.getVocabulary().WebPage);

        System.out.println("home:"+home+" "+home.getTitle());
        System.out.println("homeOnt:"+homeOnt+" "+homeOnt.getTitle());

        home.setTitle("Prueba 1a");

        System.out.println("home:"+home+" "+home.getTitle());
        System.out.println("homeOnt:"+homeOnt+" "+homeOnt.getTitle());

        home.setTitle("Prueba 1b");

        System.out.println("home:"+home+" "+home.getTitle());
        System.out.println("homeOnt:"+homeOnt+" "+homeOnt.getTitle());

        homeOnt.setTitle("Prueba 2");

        System.out.println("home:"+home+" "+home.getTitle());
        System.out.println("homeOnt:"+homeOnt+" "+homeOnt.getTitle());

        home.setTitle("Home");

        System.out.println("home:"+home+" "+home.getTitle());
        System.out.println("homeOnt:"+homeOnt+" "+homeOnt.getTitle());
    }   
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void TestPerformance1()
    {
            WebSite site=SWBContext.getWebSite("sep");
            WebPage home=site.getHomePage();
            UserRepository urep=site.getUserRepository();
            Template tpl=site.getTemplate("1");
            
            for(int x=0;x<10;x++)
            {
                WebPage page=site.getWebPage("page"+x);
                //System.out.println(page.getTitle());
            }
    }       
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void TestPerformance2()
    {
            WebSite site=SWBContext.getWebSite("sep");
            WebPage home=site.getHomePage();
            UserRepository urep=site.getUserRepository();
            Template tpl=site.getTemplate("1");
            
            Iterator<WebPage> it=home.listChilds();
            while(it.hasNext())
            {
                WebPage page=it.next();
                //System.out.println(page.getTitle());
            }
            
            ArrayList arr=SWBPortal.getAppLanguages();
            System.out.println(arr);
    }     
      
    
    @Test
    public void SetAtributesDefaultUserRepository()
    {
        UserRepository urep=SWBContext.getDefaultRepository();
        urep.setProperty("SWBUR_AuthMethod", "FORM"); //BASIC
        urep.setProperty("SWBUR_LoginContext", "swb4TripleStoreModule");
        urep.setProperty("SWBUR_CallBackHandlerClassName", "org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");
    }

}
