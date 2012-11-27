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
package org.semanticwb.bigdata.test;

import com.hp.hpl.jena.mem.faster.GraphMemFaster;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author jei
 */
public class TestGrapMem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println("mem:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
        
        Model model = new ModelCom(new GraphMemFaster());  
        
        long ntime,time=System.nanoTime();
      
        int x=0;
        
        for(x=0;x<1000000;x++)
        {            
            model.add(model.getResource("http://www.google.com#webpage:page"+x), model.getProperty("http://www.google.com#type"), model.getResource("http://www.google.com#webpage"));
            model.add(model.getResource("http://www.google.com#webpage:page"+x), model.getProperty("http://www.google.com#name"), model.createLiteral("Pagina "+x));
            model.add(model.getResource("http://www.google.com#webpage:page"+x), model.getProperty("http://www.google.com#active"), model.createLiteral("true"));
            model.add(model.getResource("http://www.google.com#webpage:page"+x), model.getProperty("http://www.google.com#valid"), model.createLiteral("true"));
            model.add(model.getResource("http://www.google.com#webpage:page"+x), model.getProperty("http://www.google.com#date"), model.createLiteral((new Date()).toString()));
            if(x%100000==0)System.out.println("Loading triples:"+x);
        }
        
        ntime=System.nanoTime();System.out.println("Load:"+model.size()+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        System.out.println("mem:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));time=System.nanoTime();
        
        //x=store.count();
        //ntime=System.nanoTime();System.out.println("count:"+x+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();        
        
        Iterator it=model.listStatements(model.getResource("http://www.google.com#webpage:page50000"), null, (String)null);
        ntime=System.nanoTime();System.out.println("find subj:"+"x"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();

        it=model.listStatements(null, model.getProperty("http://www.google.com#type"), (String)null);
        ntime=System.nanoTime();System.out.println("find prop:"+"x"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(null, null, "true");
        ntime=System.nanoTime();System.out.println("find obj:"+"0"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(model.getResource("http://www.google.com#webpage:page50000"), model.getProperty("http://www.google.com#type"), (String)null);
        ntime=System.nanoTime();System.out.println("find subj prop:"+"0"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();

        it=model.listStatements(null, model.getProperty("http://www.google.com#type"), "true");
        ntime=System.nanoTime();System.out.println("find prop obj:"+"0"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(model.getResource("http://www.google.com#webpage:page50000"), null, "true");
        ntime=System.nanoTime();System.out.println("find subj obj:"+"0"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(model.getResource("http://www.google.com#webpage:page50000"), model.getProperty("http://www.google.com#type"), "true");
        ntime=System.nanoTime();System.out.println("find triple:"+"0"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(model.getResource("http://www.google.com#webpage:page50000"), null, (String)null);
        ntime=System.nanoTime();System.out.println("find subj:"+"x"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        System.out.println("mem:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));                       
        
        model.close();

    }



}
