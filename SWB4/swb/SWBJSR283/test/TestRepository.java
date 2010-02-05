
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryFactory;
import javax.jcr.Session;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import org.apache.log4j.PropertyConfigurator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBUtils.IO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author victor.lorenzana
 */
public class TestRepository
{

    @SuppressWarnings(value = "deprecation")
    public static void main(String[] args)
    {

        SWBUtils.createInstance(TestRepository.class.getResource("/").getFile());

        String base = SWBUtils.getApplicationPath();
        //SemanticMgr.setSchemaModel(SemanticMgr.ModelSchema.OWL_DL_MEM_RDFS_INF);
        SWBPlatform.createInstance();

        SWBPlatform.getSemanticMgr().addBaseOntology(base + "../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "../../../web/WEB-INF/owl/jcr283.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
        SWBPlatform.getSemanticMgr().initializeDB();
        Map parameters = new HashMap();
        try
        {

            RepositoryFactory factory = (RepositoryFactory) Class.forName("org.semanticwb.jcr283.implementation.RepositoryFactoryImp").newInstance();
            Repository repo = factory.getRepository(parameters);

            Session session = repo.login();
            Node root = session.getRootNode();
            NodeIterator it = root.getNodes();
            long i = it.getSize();
            System.out.println(i);
            while (it.hasNext())
            {
                Node node = it.nextNode();

                System.out.println(node.getUUID());
                System.out.println(node.isNew());
                System.out.println(node.isModified());
                System.out.println(node.getIdentifier());
                System.out.println(node.getPath());
                if (node.isNodeType("mix:versionable"))
                {
                    VersionHistory history = session.getWorkspace().getVersionManager().getVersionHistory(node.getPath());
                    VersionIterator itVersions = history.getAllVersions();
                    while (itVersions.hasNext())
                    {
                        Version version = itVersions.nextVersion();
                        System.out.println("Version : " + version.getName());

                    }
                }
            }
            Node demo = root.addNode("demo:demo");
            System.out.println(demo.isModified());
            demo.addMixin("mix:referenceable");
            demo.addMixin("mix:versionable");
            System.out.println(demo.isNew());

            System.out.println(demo.getUUID());
            System.out.println(demo.getIdentifier());
            System.out.println(demo.getPath());
            //System.out.println(demo.getAncestor(0).getPath());
            //System.out.println(demo.getAncestor(1).getPath());
            {
                long init = System.currentTimeMillis();
                session.save();
                long fin = System.currentTimeMillis() - init;
                System.out.println("Tiempo save:" + fin + " ms");
            }
            {
                long init = System.currentTimeMillis();
                Version version = session.getWorkspace().getVersionManager().checkin(demo.getPath());
                long fin = System.currentTimeMillis() - init;
                System.out.println("Tiempo version :" + fin + " ms");
                System.out.println(version.getName());
            }
            System.out.println(demo.isCheckedOut());
            System.out.println(demo.isNew());
            System.out.println(demo.isModified());


        }
        catch (Exception cnfe)
        {
            cnfe.printStackTrace();
        }

    }
}
