
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
            site.setCreated(new java.util.Date(System.currentTimeMillis()));
            site.setTitle("Sep");
            site.setDescription("Sep WebSite");
            site.setActive(true);
            
            //Crear lenguajes por defecto
            Language lang=site.createLanguage("es");
            lang.setTitle("Español", "es");
            lang.setTitle("Spanish", "en");
            
            site.setLanguage(lang);
 
            Language lang2=site.createLanguage("en");
            lang2.setTitle("Ingles", "es");
            lang2.setTitle("English", "en");
            
            //Create HomePage
            WebPage home=site.createWebPage("home");
            site.setHomePage(home);
            home.setActive(true);
            home.setTitle("Pagina Principal");
            
            //Set User Repository
            UserRepository urep=SWBContext.getDefaultRepository();
            site.setUserRepository(urep);
            User user=urep.getUser("1");

            //VersionInfo para el WebSite
            VersionInfo versionws=site.createVersionInfo();
            versionws.setVersionNumber(1);
            versionws.setCreated(new java.util.Date(System.currentTimeMillis()));
            versionws.setCreator(user);
            versionws.setModifiedBy(user);
            versionws.setVersionComment("Versión inicial del sitio SEP para pruebas");
            versionws.setUpdated(new java.util.Date(System.currentTimeMillis()));
            site.setActualVersion(versionws);
            site.setLastVersion(versionws);
            site.setCreator(user);
            site.setModifiedBy(user);
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
            site.setUpdated(new java.util.Date(System.currentTimeMillis()));
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
        
        if(site.getPortletType("Poll")==null)
        {
            
            PortletType ptype=site.createPortletType("Poll");
            ptype.setPortletClassName("org.semanticwb.portal.resources.Poll");
            ptype.setPortletBundle("org.semanticwb.portal.resources.Poll");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso Poll");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("Poll");
        }  
        
        if(site.getPortletType("WBMenuMap")==null)
        {
            
            PortletType ptype=site.createPortletType("WBMenuMap");
            ptype.setPortletClassName("org.semanticwb.portal.resources.WBMenuMap");
            ptype.setPortletBundle("org.semanticwb.portal.resources.WBMenuMap");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso WBMenuMap");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("WBMenuMap");
        }  
        
        if(site.getPortletType("WBMenuNivel")==null)
        {
            
            PortletType ptype=site.createPortletType("WBMenuNivel");
            ptype.setPortletClassName("org.semanticwb.portal.resources.WBMenuNivel");
            ptype.setPortletBundle("org.semanticwb.portal.resources.WBMenuNivel");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso WBMenuNivel");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("WBMenuNivel");
        }  
        
        
        if(site.getPortletType("Banner1")==null)
        {
            
            PortletType ptype=site.createPortletType("Banner1");
            ptype.setPortletClassName("org.semanticwb.portal.resources.Banner1");
            ptype.setPortletBundle("org.semanticwb.portal.resources.Banner1");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso Banner1");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("Banner");
            
            PortletRef portletref=site.createPortletRef();
            portletref.setActive(true);
            portletref.setPortlet(portlet);
            portletref.setPriority(3);
            
            home.addPortletRef(portletref);
        } 
        
        if(site.getPortletType("Controls")==null)
        {
            
            PortletType ptype=site.createPortletType("Controls");
            ptype.setPortletClassName("org.semanticwb.portal.resources.Controls");
            ptype.setPortletBundle("org.semanticwb.portal.resources.Controls");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso Controls");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("Controls");
            
            PortletRef portletref=site.createPortletRef();
            portletref.setActive(true);
            portletref.setPortlet(portlet);
            portletref.setPriority(3);
            
            home.addPortletRef(portletref);
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
        
        if(site.getPortletType("JSPResource")==null)
        {
            
            PortletType ptype=site.createPortletType("JSPResource");
            ptype.setPortletClassName("org.semanticwb.portal.resources.JSPResource");
            ptype.setPortletBundle("org.semanticwb.portal.resources.JSPResource");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso JSPResource");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("JSPResource");
            
            PortletRef portletref=site.createPortletRef();
            portletref.setActive(true);
            portletref.setPortlet(portlet);
            portletref.setPriority(3);
            
            home.addPortletRef(portletref);
        }  
        
        if(site.getPortletType("TematicIndexXSL")==null)
        {
            
            PortletType ptype=site.createPortletType("TematicIndexXSL");
            ptype.setPortletClassName("org.semanticwb.portal.resources.TematicIndexXSL");
            ptype.setPortletBundle("org.semanticwb.portal.resources.TematicIndexXSL");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso TematicIndexXSL");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("TematicIndexXSL");
            
            PortletRef portletref=site.createPortletRef();
            portletref.setActive(true);
            portletref.setPortlet(portlet);
            portletref.setPriority(3);
            
            home.addPortletRef(portletref);
        }  
        
        
        if(site.getPortletType("FrameContent")==null)
        {
            
            PortletType ptype=site.createPortletType("FrameContent");
            ptype.setPortletClassName("org.semanticwb.portal.resources.FrameContent");
            ptype.setPortletBundle("org.semanticwb.portal.resources.FrameContent");
            ptype.setPortletMode(1);
            ptype.setTitle("Recurso FrameContent");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("FrameContent");
            
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
        
        if(site.getPortletType("SWBASemObjectEditor")==null)
        {
            
            PortletType ptype=site.createPortletType("SWBASemObjectEditor");
            ptype.setPortletClassName("org.semanticwb.portal.admin.resources.SWBASemObjectEditor");
            ptype.setPortletBundle("org.semanticwb.portal.admin.resources.SWBASemObjectEditor");
            ptype.setPortletMode(1);
            ptype.setTitle("SemanticObject Editor");
                    
            Portlet portlet=site.createPortlet();
            portlet.setActive(true);
            portlet.setCreator(user);
            portlet.setPortletType(ptype);
            portlet.setTitle("SWBASemObjectEditor");
            
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
    public void Test4()
    {
        WebSite site=SWBContext.getWebSite("sep");
        if(!site.isActive()) site.setActive(true);
        
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
    
    //@Test
    public void TestAdmin2()
    {    
        //if(SWBContext.getWebSite("SWBAdmin")==null)
        {
            WebSite site=SWBContext.getWebSite("SWBAdmin");
            //Asignar platilla a system 
            WebPage menus=site.getWebPage("WBAd_System");
            TemplateRef tplref=site.createTemplateRef();
            tplref.setTemplate(site.getTemplate("2"));
            tplref.setActive(true);
            tplref.setPriority(3);
            menus.addTemplateRef(tplref);    
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
      
    
    //@Test
    public void SetAtributesDefaultUserRepository()
    {
        UserRepository urep=SWBContext.getDefaultRepository();
        urep.setProperty("SWBUR_AuthMethod", "FORM2"); //BASIC
        urep.setProperty("SWBUR_LoginContext", "swb4TripleStoreModule");
        urep.setProperty("SWBUR_CallBackHandlerClassName", "org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");
        urep.setDescription("3 Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei");
    }

}
