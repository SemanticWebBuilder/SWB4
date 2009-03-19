/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.junit.*;
import java.util.Iterator;
import org.semanticwb.platform.*;
import org.semanticwb.model.*;
import org.semanticwb.*;
/**
 *
 * @author Jei
 */
public class TestFilter {
    private Logger log=SWBUtils.getLogger(TestFilter.class);

    static public final String NL = System.getProperty("line.separator") ; 

    public TestFilter()
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
    public void test()
    {
        
        Iterator<SemanticObject> it=SWBObjectFilter.filter(ResourceType.sclass.listInstances(),"resourceMode=1");
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            System.err.println("obj:"+obj);
        }
        
    }

}
