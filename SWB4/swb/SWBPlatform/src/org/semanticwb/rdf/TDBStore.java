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
package org.semanticwb.rdf;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDB;
import com.hp.hpl.jena.tdb.TDBFactory;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jei
 */
public class TDBStore implements AbstractStore
{
    private static Logger log=SWBUtils.getLogger(TDBStore.class);

    /** The Dataset */
    private Dataset set;
    /** The timer. */
    private Timer timer;                        //Commiter


    public void init()
    {
        log.info("TDB Detected...," + SWBPlatform.createInstance().getPlatformWorkPath() + "/data");
        TDB.getContext().set(TDB.symUnionDefaultGraph,true);
        set = TDBFactory.createDataset(SWBPlatform.createInstance().getPlatformWorkPath() + "/data");

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                commit();
            }
        }, 60000, 30000);
    }

    public void removeModel(String name)
    {
        Model model=loadModel(name);
        if(model!=null)model.removeAll();
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

    /**
     * Commit all models.
     */
    private void commit()
    {
        log.trace("ServerMgr.Commit()");
        try {
            Iterator<String> it = listModelNames();
            while (it.hasNext()) {
                Model model = loadModel(it.next());
                model.commit();
            }
        } catch (ConcurrentModificationException noe) {
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void close()
    {
        //System.out.println("ServerMgr.Close()");
        timer.cancel();
        timer=null;
        commit();
        set.close();
    }

    public Dataset getDataset(String defaultName)
    {
        return set;
    }
    
}
