
import org.junit.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.Dns;
import org.semanticwb.model.Language;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticModel;

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
    public void Test()
    {
//            WebSite site=SWBContext.createWebSite("sep", "http://www.sep.gob.mx");
//            site.setTitle("Sep");
//            site.setDescription("Sep WebSite");
//            site.setActive(true);
//            
//            //Crear lenguajes por defecto
//            Language lang=site.createLanguage("es");
//            lang.setTitle("Español", "es");
//            lang.setTitle("Spanish", "en");
//            lang=site.createLanguage("en");
//            lang.setTitle("Ingles", "es");
//            lang.setTitle("English", "en");
//            
//            //Create HomePage
//            WebPage home=site.createWebPage("home");
//            site.setHomePage(home);
//            home.setActive(true);

            WebSite site=SWBContext.getWebSite("sep");
            WebPage home=site.getHomePage();
            home.setTitle("Pagina Principal");
            
            //Set User Repository
//            UserRepository urep=SWBContext.getDefaultRepository();
//            site.setUserRepository(urep);

            //Create DNS
//            WebSite global=SWBContext.getGlobalWebSite();
//            Dns dns=global.getDns("localhost");
//            dns.setWebPage(home);

//            Template tpl=site.createTemplate();
//            tpl.setActive(true);
//            tpl.setTitle("Platilla1");
            
//            Template tpl=site.getTemplate("1");
//            
//            VersionInfo version=site.createVersionInfo();
//            version.setVersionNumber(1);
//            version.setVersionFile("template.html");
//            tpl.setActualVersion(version);
//            tpl.setLastVersion(version);
            
//            TemplateRef tplref=site.createTemplateRef();
//            tplref.setTemplate(tpl);
//            tplref.setActive(true);
//            tplref.setPriority(3);
//            
//            home.addTemplateRef(tplref);

    }

}
