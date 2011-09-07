/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.db.impl.Driver_Derby_SWB;
import com.hp.hpl.jena.db.impl.Driver_HSQLDB_SWB;
import com.hp.hpl.jena.db.impl.Driver_MsSQL2008_SWB;
import com.hp.hpl.jena.db.impl.Driver_MsSQL_SWB;
import com.hp.hpl.jena.db.impl.Driver_MySQL_SWB;
import com.hp.hpl.jena.db.impl.Driver_Oracle_SWB;
import com.hp.hpl.jena.db.impl.Driver_PostgreSQL_SWB;
import com.hp.hpl.jena.db.impl.IRDBDriver;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.db.DBConnectionPool;

/**
 *
 * @author jei
 */
public class RDBStore implements AbstractStore
{
    private static Logger log=SWBUtils.getLogger(RDBStore.class);

    /** The maker. */
    private ModelMaker maker;

    /** The conn. */
    private IDBConnection conn;

    public void init()
    {
        //System.out.println("initializeDB");
        DBConnectionPool pool = SWBUtils.DB.getDefaultPool();
//        String M_DB_URL         = pool.getURL();
//        String M_DB_USER        = pool.getUser();
//        String M_DB_PASSWD      = pool.getPassword();
        String M_DB = SWBUtils.DB.getDatabaseType(pool.getName());

        // create a database connection
        //conn = new DBConnection(M_DB_URL, M_DB_USER, M_DB_PASSWD, M_DB);

        // Create database connection
        conn = new DBConnection(SWBUtils.DB.getDefaultPool().newAutoConnection(), M_DB);

        //System.out.println("DB:"+M_DB);

        IRDBDriver driver = null;
        if (M_DB.equals(SWBUtils.DB.DBTYPE_MySQL)) {
            driver = new Driver_MySQL_SWB();
        } else if (M_DB.equals(SWBUtils.DB.DBTYPE_Derby)) {
            driver = new Driver_Derby_SWB();
        } else if (M_DB.equals(SWBUtils.DB.DBTYPE_HSQLDB)) {
            driver = new Driver_HSQLDB_SWB();
        } //else if(M_DB.equals(SWBUtils.DB.DBTYPE_HSQL)){driver=new Driver_HSQL_SWB();}
        else if (M_DB.equals(SWBUtils.DB.DBTYPE_MsSQL)) {
            driver = new Driver_MsSQL_SWB();
        } else if (M_DB.equals(SWBUtils.DB.DBTYPE_MsSQL2008)) {
            driver = new Driver_MsSQL2008_SWB();
        } else if (M_DB.equals(SWBUtils.DB.DBTYPE_Oracle)) {
            driver = new Driver_Oracle_SWB();
        } else if (M_DB.equals(SWBUtils.DB.DBTYPE_PostgreSQL)) {
            driver = new Driver_PostgreSQL_SWB();
        }

        driver.setConnection(conn);
        conn.setDriver(driver);
        conn.getDriver().setTableNamePrefix("swb_");
        conn.getDriver().setDoDuplicateCheck(false);
        //conn.getDriver().setIsTransactionDb(true);

        maker = ModelFactory.createModelRDBMaker(conn);
    }

    public void removeModel(String name)
    {
        maker.removeModel(name);
    }

    public Model loadModel(String name)
    {
        Model ret = maker.openModel(name);
        ((ModelRDB) (ret)).setDoFastpath(false);
        ((ModelRDB) (ret)).setQueryOnlyAsserted(true);
        return ret;
    }

    public Iterator<String> listModelNames()
    {
        return maker.listModels();
    }

    public void close()
    {
        maker.close();
        if (conn != null) {
            try {
                //Close the database connection
                conn.close();
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    public Dataset getDataset()
    {
        return null;
    }

    public Model getModel(String name) 
    {
        if(maker.hasModel(name))
        {
            return loadModel(name);
        }
        return null;
    }
}
