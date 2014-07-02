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
package org.semanticwb.store.jenaimp;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.GraphCached;
import org.semanticwb.store.Graph;

/**
 *
 * @author jei
 */
public class SWBTSModelMaker
{
    private static Logger log=SWBUtils.getLogger(SWBTSModelMaker.class);
    
    protected HashMap<String, org.semanticwb.store.Graph> map;
    
    private String path=null;
    
    protected String clsname;

    public SWBTSModelMaker(String clsname)
    {
        this.clsname=clsname;
        map=new HashMap();
        path=SWBPlatform.getEnv("swb/tripleStorePath",SWBUtils.getApplicationPath()+"/work/data/");
    }

    public List<String> listModelNames()
    {
        ArrayList<String> arr=new ArrayList();
        File dir=new File(path);
        dir.mkdirs();
        
        File dirs[]=dir.listFiles();  
        for(int x=0;x<dirs.length;x++)
        {
            File d=dirs[x];
            if(d.isDirectory())
            {
                arr.add(d.getName());
            }
        }        
        return arr;
    }

    public Model getModel(String name)
    {
        if(listModelNames().contains(name))
        {
            return createModel(name);
        }else
        {
            return null;
        }
    }

    public Model createModel(String name)
    {
        Model model=null;
        org.semanticwb.store.Graph g=map.get(name);
        if(g==null || g.isClosed())
        {
            try
            {
                Class cls=Class.forName(clsname);
                Constructor c=cls.getConstructor(String.class, Map.class);                
                HashMap map=new HashMap();
                map.put("path", path);
                g=(org.semanticwb.store.Graph)c.newInstance(name,map);
                map.put(name, g);
            }catch(Exception e2)
            {
                log.error(e2);
            }
        }
        
        int size=Integer.parseInt(SWBPlatform.getEnv("swb/tripleStoreResourceCache","0"));

        if(size>0)
        {
            model=new ModelCom(new SWBTSGraphCache(new SWBTSGraph(g),size));
        }else if(size==0)
        {
            model=new ModelCom(new SWBTSGraph(g));
        }else if(size<0)
        {
            model=new ModelCom(new GraphCached(new SWBTSGraph(g)));
        }
        return model;
    }

    public void removeModel(String name)
    {
        org.semanticwb.store.Graph g=map.get(name);
        if(g!=null)
        {
            try
            {
                g.close();                
                SWBUtils.IO.removeDirectory(path+name);                
                map.remove(name);
            }catch(Exception e2)
            {
                log.error(e2);
            }
        }
    }
   
    public void close()
    {
        Iterator<org.semanticwb.store.Graph> it=map.values().iterator();
        while (it.hasNext())
        {
            Graph graph = it.next();
            graph.close();
        }
    }

}
