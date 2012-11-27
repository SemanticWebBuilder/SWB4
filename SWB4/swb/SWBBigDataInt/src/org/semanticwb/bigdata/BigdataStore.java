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
package org.semanticwb.bigdata;

import com.hp.hpl.jena.rdf.model.Model;
import java.io.File;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.AbstractStore;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import java.util.HashMap;
import org.semanticwb.rdf.GraphCached;

/**
 *
 * @author jei
 */
public class BigdataStore implements AbstractStore
{
    private static Logger log=SWBUtils.getLogger(BigdataStore.class);

    BigdataModelMaker maker;
    
    HashMap<String,Model> models=new HashMap();

    public void init()
    {
        String path=SWBPlatform.createInstance().getPlatformWorkPath() + "/data";
        log.info("Bigdata Detected...," + path);
        maker=new BigdataModelMaker(path);
    }

    public void removeModel(String name)
    {
        String path=SWBPlatform.createInstance().getPlatformWorkPath() + "/data";
        File journal = new File(path+"/"+name+".jnl");
        journal.delete();
        models.remove(name);
    }

    public Model loadModel(String name)
    {
        //System.out.println("loadModel:"+name);    
        //Model model=models.get(name);
        //if(model==null)
        Model model=null;
        {        
            model=maker.createModel(name, false);
            if(!SWBPlatform.isPortalLoaded() && SWBPlatform.getSemanticMgr().isTripleFullCache())
            {
                log.event("Loading cache of model:"+name);
                model=new ModelCom(new GraphCached((model.getGraph())));
            }
            models.put(name, model);
        }
        return model;
    }

    public Iterator<String> listModelNames()
    {
        return maker.listModelNames();
    }
    

    public void close()
    {
        //set.close();
    }

    public Dataset getDataset()
    {
        return null;
    }

    public Model getModel(String name) 
    {
        //System.out.println("getModel:"+name);
        Model model=models.get(name);     
        return model;
    }    
}
