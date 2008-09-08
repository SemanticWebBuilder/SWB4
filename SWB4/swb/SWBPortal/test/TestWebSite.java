
import org.junit.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.semanticwb.SWBPlatform;
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
    }
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void Test3()
    {
            WebSite site=SWBContext.getWebSite("sep");
            WebPage home=site.getHomePage();
            UserRepository urep=SWBContext.getDefaultRepository();
            Template tpl=site.getTemplate("1");
            
            for(int x=0;x<10000;x++)
            {
                WebPage page=site.createWebPage("page"+x);
                page.setTitle("Pagina "+x);
                page.setParent(home);
            }
    }
    
    

}
