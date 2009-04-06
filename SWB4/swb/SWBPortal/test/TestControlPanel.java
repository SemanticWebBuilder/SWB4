
import java.util.Iterator;
import org.junit.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.resources.cp.ControlPanel;
import org.semanticwb.portal.resources.cp.PromotionPage;
import org.semanticwb.portal.resources.cp.Image;

/**
 *
 * @author Jei
 */
public class TestControlPanel {
    

    public TestControlPanel()
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
        WebSite site=SWBContext.getGlobalWebSite();

        Iterator<ControlPanel> it=ControlPanel.listControlPanels(site);
        while(it.hasNext())
        {
            ControlPanel cp=it.next();
            System.out.println("cp:"+cp);
            System.out.println("cp.alert:"+cp.getAlertImage().getImageAlt());
            System.out.println("cp.promo:"+cp.getPromotionPage().getImage().getImageAlt());
            System.out.println("**************************************");
        }

//        Image img=Image.getImage("2", site);
//        img.setImageAlt("Imagen del 2");

//        SWBFormMgr fm=new SWBFormMgr(img.getSemanticObject(),null,SWBFormMgr.MODE_EDIT);
//        String str=fm.renderForm(null);
//        System.out.println(str);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void Test2()
    {
        WebSite site=SWBContext.getGlobalWebSite();
        System.out.println("site:"+site);

        ControlPanel cp=ControlPanel.createControlPanel(site);
        PromotionPage pp=PromotionPage.createPromotionPage(site);
        cp.addPromotionPage(pp);
        Image img=Image.createImage(site);
        img.setImageAlt("imagen de Prueba");
        pp.setImage(img);
        cp.setAlertImage(img);
        cp.setRecomendImage(img);

    }


}
