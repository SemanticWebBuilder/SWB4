/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.test.repository;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.version.Version;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr170.implementation.SWBRepository;
import static org.junit.Assert.*;

/**
 *
 * @author victor.lorenzana
 */
public class TestRepository
{

    public TestRepository()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        String base = SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
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
    @Ignore
    public void getWorkspaceTest()
    {
        try
        {
            Repository repository = new SWBRepository();
            SimpleCredentials credentials = new SimpleCredentials("victor", "victor".toCharArray());
            Session session = repository.login(credentials);
            //session.getWorkspace().getObservationManager().addEventListener(arg0, arg1, arg2, arg3, arg4, arg5, arg6)
            Node root = session.getRootNode();
            System.out.println(session.getWorkspace().getName());
            System.out.println(root.getName());
            System.out.println(root.getPath());
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    @Ignore
    public void lockNodeTest()
    {
        try
        {
            Repository repository = new SWBRepository();
            SimpleCredentials credentials = new SimpleCredentials("admin", "webbuilder".toCharArray());
            Session session = repository.login(credentials);
            Node root = session.getRootNode();
            root.lock(true, false);
            root.unlock();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    //@Ignore
    public void loadNode()
    {
        String id = "8bcb4ab0-9aa3-42cd-83d7-399f459216b5";
        Session session = null;
        try
        {
            System.setProperty("org.semanticwb.jcr170.implementation.repositoryTrusted", "true");
            SWBRepository repository = new SWBRepository();
            SimpleCredentials credentials = new SimpleCredentials("admin", "webbuilder".toCharArray());
            session = repository.login(credentials);
            Node node = session.getNodeByUUID(id);
            Version version=node.getVersionHistory().getVersionByLabel("version1");
            Assert.assertNotNull(version);            
            System.out.println("name: "+version.getName());
        }
        catch (Throwable e)
        {
            e.printStackTrace(System.out);
            fail(e.getMessage());
        } finally
        {
            if (session != null)
            {
                session.logout();
            }

        }
    }

    @Test
    @Ignore
    public void addNode()
    {
        String UUID = "";
        Session session = null;
        try
        {
            System.setProperty("org.semanticwb.jcr170.implementation.repositoryTrusted", "true");
            SWBRepository repository = new SWBRepository();
            SimpleCredentials credentials = new SimpleCredentials("admin", "webbuilder".toCharArray());
            session = repository.login(credentials);
            String title = "Deportes";
            Workspace ws = session.getWorkspace();
            Node root = session.getRootNode();
            NodeIterator it = root.getNodes();
            while (it.hasNext())
            {
                Node node = it.nextNode();
                node.remove();
            }
            QueryManager qmanager = ws.getQueryManager();
            Query query;
            if (repository.getDescriptor(Repository.REP_NAME_DESC).toLowerCase().indexOf("webbuilder") != -1)
            {

                String statement = "SELECT ?x WHERE {?x cm:title ?title FILTER regex(?title, \"" + title + "\")  }";
                query = qmanager.createQuery(statement, "SPARQL");
            }
            else
            {
                query = qmanager.createQuery("//" + title + "[@cm:title='" + title + "']", Query.XPATH);
            }
            QueryResult result = query.execute();
            NodeIterator nodeIterator = result.getNodes();
            while (nodeIterator.hasNext())
            {
                Node node = nodeIterator.nextNode();
                node.remove();
            }
            Node newNode = root.addNode(title, "swboffice:OfficeCategory");
            newNode.addMixin("mix:versionable");

            newNode.setProperty("swboffice:user", credentials.getUserID());
            newNode.setProperty("swboffice:title", title);
            newNode.setProperty("swboffice:description", "a");
            root.save();
            Version version = newNode.checkin();
            newNode.getVersionHistory().addVersionLabel(version.getName(), "version1", true);
            newNode.save();
            Version versionByLabel = newNode.getVersionHistory().getVersionByLabel("version1");
            Assert.assertNotNull(versionByLabel);
            String id = newNode.getUUID();
            UUID = newNode.getUUID();
            System.out.println("uuid : " + UUID);
//            session.getNodeByUUID(id);
//            Node content = newNode.addNode("Contenido1", "cm:Content");
//            content.setProperty("cm:title", "Contenido 1");
//            content.setProperty("cm:description", "Contenido de prueba");
//            UUID = newNode.getUUID();
//            root.save();
//            content.checkin();
//            
//            content.checkout();
//            content.setProperty("cm:title", "Contenido 2");
//            content.setProperty("cm:description", "Contenido de prueba 2");
//            content.save();
//            content.checkin();
            if (UUID == null || UUID.equals(""))
            {
                Assert.fail("UUDI incorrecto");
            }

        }
        catch (Throwable e)
        {
            e.printStackTrace(System.out);
            fail(e.getMessage());
        } finally
        {
            if (session != null)
            {
                session.logout();
            }

        }
    }
}
