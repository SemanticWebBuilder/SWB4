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
package org.semanticwb;
import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.db.impl.Driver_MySQL_SWB;
import com.hp.hpl.jena.db.impl.IRDBDriver;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Resource;
import java.sql.Connection;
import java.util.Iterator;
import org.junit.*;
import org.semanticwb.base.db.DBConnectionPool;

/**
 *
 * @author javier.solis
 */
public class TestRDB {

    public TestRDB()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        //SWBPlatform.createInstance(null);
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

    public Model getModel(String name)
    {
        Model ret=null;

        IDBConnection conn;
        ModelMaker maker;
        DBConnectionPool pool=SWBUtils.DB.getDefaultPool();
        String M_DB_URL         = pool.getURL();
        String M_DB_USER        = pool.getUser();
        String M_DB_PASSWD      = pool.getPassword();
        String M_DB             = SWBUtils.DB.getDatabaseType(pool.getName());

        // Create database connection
        conn = new DBConnection(SWBUtils.DB.getDefaultPool().newAutoConnection(), M_DB);

        if(M_DB.equals("MySQL"))
        {
            IRDBDriver driver=new Driver_MySQL_SWB();
            driver.setConnection(conn);
            conn.setDriver(driver);
        }
        conn.getDriver().setTableNamePrefix("swb_");
        conn.getDriver().setDoDuplicateCheck(false);

        maker = ModelFactory.createModelRDBMaker(conn);

        ret=maker.openModel(name);
        ((ModelRDB)(ret)).setDoFastpath(false);
        ((ModelRDB)(ret)).setQueryOnlyAsserted(true);

        return ret;
    }

    @Test
    public void test()
    {
        Model ret=null;

        IDBConnection conn;
        ModelMaker maker;
        DBConnectionPool pool=SWBUtils.DB.getDefaultPool();
        String M_DB_URL         = pool.getURL();
        String M_DB_USER        = pool.getUser();
        String M_DB_PASSWD      = pool.getPassword();
        String M_DB             = SWBUtils.DB.getDatabaseType(pool.getName());

        // Create database connection
        Connection con=SWBUtils.DB.getDefaultPool().newAutoConnection();
        conn = new DBConnection(con, M_DB);

        if(M_DB.equals("MySQL"))
        {
            IRDBDriver driver=new Driver_MySQL_SWB();
            driver.setConnection(conn);
            conn.setDriver(driver);
        }
        conn.getDriver().setTableNamePrefix("swb_");
        conn.getDriver().setDoDuplicateCheck(false);

        maker = ModelFactory.createModelRDBMaker(conn);

        Model model=maker.openModel("demo");
        ((ModelRDB)(model)).setDoFastpath(false);
        ((ModelRDB)(model)).setQueryOnlyAsserted(true);


        for(int x=0;x<10;x++)
        {
            Iterator<Resource> it=model.listSubjects();
            while(it.hasNext())
            {
                Resource res=it.next();
                System.out.println("res:"+res);
            }
            System.out.println("*********************************************** x:"+x);
            try
            {
                con.close();
            }catch(Exception e){e.printStackTrace();}
        }
        model.close();
    }

}
