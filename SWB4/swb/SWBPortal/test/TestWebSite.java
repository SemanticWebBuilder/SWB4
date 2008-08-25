
import org.junit.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.SWBContext;
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
        String name="sep";
        String nsp="http://www.sep.gob.mx";
        //WebSite website=SWBContext.createWebSite(name, nsp);
        WebSite website=SWBContext.getWebSite(name);
        WebPage wp=website.createWebPage("home");
        website.setHomePage(wp);
    }

}
