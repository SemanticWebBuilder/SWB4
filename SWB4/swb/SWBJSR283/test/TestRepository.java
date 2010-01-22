
import java.util.HashMap;
import java.util.Map;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryFactory;
import javax.jcr.Session;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author victor.lorenzana
 */
public class TestRepository {

    public static void main(String[] args)
    {
        String base=SWBUtils.getApplicationPath();
        //SemanticMgr.setSchemaModel(SemanticMgr.ModelSchema.OWL_DL_MEM_RDFS_INF);
        SWBPlatform.createInstance();
        
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/jcr283.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
        SWBPlatform.getSemanticMgr().initializeDB();
        Map parameters = new HashMap();
        try
        {            
            RepositoryFactory factory = (RepositoryFactory) Class.forName("org.semanticwb.jcr283.implementation.RepositoryFactoryImp").newInstance();
            Repository repo = factory.getRepository(parameters);
            Session session=repo.login();
            Node node=session.getRootNode();
            Node demo=node.addNode("demo:demo");
            demo.addMixin("mix:referenceable");            
        }
        catch(Exception cnfe)
        {
            cnfe.printStackTrace();
        }
        
    }
}
