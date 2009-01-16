
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
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateGroup;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


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
        WebSite sep=SWBContext.getWebSite("sep");
        WebPage page=sep.getHomePage();
        SemanticObject obj=page.getSemanticObject();
        Iterator<SemanticProperty> itp=obj.getSemanticClass().listProperties();
        while(itp.hasNext())
        {
            SemanticProperty prop=itp.next();
            if(prop.isRemoveDependency())
            {
                System.out.println(prop+" "+prop.isRemoveDependency());
                if(prop.getCardinality()==1)
                {
                    SemanticObject dep=obj.getObjectProperty(prop);
                    if(dep!=null)
                    {
                        System.out.println(dep);
                    }
                }else
                {
                    Iterator<SemanticObject> it=obj.listObjectProperties(prop);
                    while(it.hasNext())
                    {
                        SemanticObject dep=it.next();
                        System.out.println(dep);
                    }
                }
            }
        }

    }


    

}
