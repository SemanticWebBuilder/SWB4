/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author serch
 */
public class UserRepositoryTest {
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
        SWBPlatform.createInstance(null);
    }
    
     @Test
    public void Test()
    {
         UserRepository urep=SWBContext.getDefaultRepository();
         SemanticProperty prop = urep.createIntExtendedAttribute("Edad");

    }

}
