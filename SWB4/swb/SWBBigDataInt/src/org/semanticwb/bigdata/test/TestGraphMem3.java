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
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author javier.solis.g
 */
public class TestGraphMem3
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        System.out.println("mem:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
        
        Model model = new ModelCom(new GraphMemFaster());  
        
        long ntime,time=System.nanoTime();

        try
        {
            model.read(new FileInputStream("/Users/javier.solis.g/Desktop/triples/infoboxes-fixed.nt"),null,"N-TRIPLE");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        ntime=System.nanoTime();System.out.println("Load:"+model.size()+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        System.out.println("mem:"+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));time=System.nanoTime();
                
        long x=model.size();
        ntime=System.nanoTime();System.out.println("count:"+x+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();  
        
        
        Iterator it=model.listStatements(model.getResource("<http://dbpedia.org/resource/Metropolitan_Museum_of_Art>"), null, (String)null);
        ntime=System.nanoTime();System.out.println("find subj:"+"x"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
        it=model.listStatements(model.getResource("<http://dbpedia.org/resource/Metropolitan_Museum_of_Art>"), null, (String)null);
        ntime=System.nanoTime();System.out.println("find subj:"+"x"+" "+((ntime-time)/1000000.0)+"ms");time=System.nanoTime();
        
    }
}
