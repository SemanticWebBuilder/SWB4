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
package org.semanticwb.triplestore.virtuoso;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.AbstractStore;

/**
 *
 * @author jei
 */
public class SWBTSVirtuoso implements AbstractStore
{
    private static Logger log= SWBUtils.getLogger(SWBTSVirtuoso.class);
    
    ArrayList<String> models=null; 

    @Override
    public void init()
    {
        log.event("SWBTSVirtuoso Initializing...");
    }

    @Override
    public void removeModel(String name)
    {
        //maker.removeModel(name);
    }

    @Override
    public Model loadModel(String name)
    {
        Model ret=getModel(name);
        if(ret==null)
        {   
            SWBVirtGraph graph = new SWBVirtGraph ("swb_"+name);
            ret=new SWBVirtModel(graph);
            //ret=new ModelCom(graph);
        }
        return ret;
    }

    @Override
    public Iterator<String> listModelNames()
    {
        models=new ArrayList();   
        SWBVirtDataSource ds=new SWBVirtDataSource();
        Iterator<String> it=ds.listNames();
        while (it.hasNext())
        {
            String str = it.next();
            if(str.startsWith("swb_"))
            {
                models.add(str.substring(4));
                //System.out.println("name swb:"+str.substring(4));
            }
        }     
        
/*        
        SWBVirtGraph set = new SWBVirtGraph (null);
        Query sparql = QueryFactory.create("SELECT DISTINCT ?g\n" +
            "WHERE {\n" +
            "  GRAPH ?g {\n" +
            "    ?s ?p ?o\n" +
            "  }\n" +
            "}");

        QueryExecution vqe = SWBVirtuosoQueryExecutionFactory.create(sparql, set);

        ResultSet results = vqe.execSelect();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            RDFNode graph = result.get("g");
            System.out.println("name:"+graph.toString());
            if(graph.toString().startsWith("swb_"))
            {
                models.add(graph.toString().substring(4));
                System.out.println("name swb:"+graph.toString().substring(4));
            }
        }        
*/ 
        return models.iterator();
    }
    
    @Override
    public Model getModel(String name) 
    {
        if(models==null)listModelNames();
        if(models.contains(name))
        {
            SWBVirtGraph graph = new SWBVirtGraph ("swb_"+name);
            return new SWBVirtModel(graph);            
            //return new ModelCom(graph);
        }
        //return maker.getModel(name);
        return null;
    }    

    @Override
    public void close()
    {

    }

    @Override
    public Dataset getDataset()
    {
        return null;
    }
    

}
