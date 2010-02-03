
import java.util.HashMap;
import java.util.Map;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
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
            Node root=session.getRootNode();
            NodeIterator it=root.getNodes();
            long i=it.getSize();
            System.out.println(i);
            while(it.hasNext())
            {
                Node node=it.nextNode();
                System.out.println(node.getUUID());
                System.out.println(node.getIdentifier());
                System.out.println(node.getPath());
            }
            Node demo=root.addNode("demo:demo");
            demo.addMixin("mix:referenceable");
            demo.addMixin("mix:versionable");
            System.out.println(demo.getUUID());
            System.out.println(demo.getPath());
            session.save();
        }
        catch(Exception cnfe)
        {
            cnfe.printStackTrace();
        }
        
    }
}
