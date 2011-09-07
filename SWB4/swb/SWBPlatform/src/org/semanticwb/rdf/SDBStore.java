/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.sdb.StoreDesc;
import com.hp.hpl.jena.sdb.sql.SDBConnection;
import com.hp.hpl.jena.sdb.store.DatabaseType;
import com.hp.hpl.jena.sdb.store.LayoutType;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.db.DBConnectionPool;

/**
 *
 * @author jei
 */
public class SDBStore implements AbstractStore
{
    private static Logger log=SWBUtils.getLogger(SDBStore.class);

    /** The Dataset */
    private Dataset set;
    /** The store. */
    private Store store;

    public void init()
    {
        //System.out.println("initializeDB");
        DBConnectionPool pool = SWBUtils.DB.getDefaultPool();
//        String M_DB_URL         = pool.getURL();
//        String M_DB_USER        = pool.getUser();
//        String M_DB_PASSWD      = pool.getPassword();
        String M_DB = SWBUtils.DB.getDatabaseType(pool.getName());

        try
        {
            //StoreDesc sd = new StoreDesc(LayoutType.LayoutTripleNodesHash, DatabaseType.fetch(M_DB));
            StoreDesc sd = new StoreDesc(LayoutType.LayoutTripleNodesHash, DatabaseType.fetch(M_DB));
            //SDBConnection con=new SDBConnection(M_DB_URL, M_DB_USER, M_DB_PASSWD);
            SDBConnection con = new SDBConnection(SWBUtils.DB.getDefaultPool().newAutoConnection());
            //SDBConnection con=new SDBConnection_SWB();

            store = SDBFactory.connectStore(con, sd);
            //Revisar si las tablas existen
            List list = store.getConnection().getTableNames();
            //System.out.println("list:"+list);

            if (!(list.contains("nodes") || list.contains("NODES") || list.contains("Nodes"))
                    && !(list.contains("triples") || list.contains("TRIPLES") || list.contains("Triples"))
                    && !(list.contains("quads") || list.contains("QUADS") || list.contains("Quads"))) //MAPS74 Oracle maneja los nombres en MAYUSCULAS, MySQL usa Capitalizados
            {
                log.event("Formating Database Tables...");
                store.getTableFormatter().create();
            }

            set = SDBFactory.connectDataset(store);
        }catch(Throwable e){log.error(e);}
    }

    public void removeModel(String name)
    {
        Model model=loadModel(name);
        if(model!=null)
        {
            model.removeAll();
        }
    }

    public Model loadModel(String name)
    {
        return set.getNamedModel(name);
        //return SDBFactory.connectNamedModel(store, name);
    }

    public Iterator<String> listModelNames()
    {
        return set.listNames();
    }
    
    public Model getModel(String name) 
    {
        Iterator<String> it=listModelNames();
        while (it.hasNext()) {
            String mname = it.next();
            if(mname.equals(name))
            {
                return loadModel(name);
            }
        }
        return null;
    }    

    public void close()
    {
        set.close();
    }

    public Dataset getDataset()
    {
        return set;
    }

}
