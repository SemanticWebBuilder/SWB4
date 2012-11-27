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
package org.semanticwb.portal.admin.resources;

import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jorge.jimenez
 */
public class SWBModelAdminThreads {

    private static Logger log=SWBUtils.getLogger(SWBModelAdminThreads.class);
    private static SWBModelAdminThreads instance = null;
    private static HashMap hmthreads=new HashMap();


    public SWBModelAdminThreads SWBModelAdminThreads()
    {
        if (instance == null)
        {
            instance = new SWBModelAdminThreads();

        }
        return instance;
    }


    public void addThread(long threadID, Thread thread)
    {
        //Talvez se puso a correr el proceso y se abandonó la página, lo que causaría que
        //el thread siga corriendo y cuando termina el proceso al 100% destruiría y el nuevo
        //sitio ya estaría creado,pero nunca se eliminaría del hash, y despues si se quisiera
        //generar un nuevo sitio de plantilla, el sistema pudiera asignar el mismo id que alguna
        //vez existio al nuevo thread, lo cual causaría que si ya existe en el hash, haya problemas
        //es por ello que aqui reviso si ya existe un id para un thread, si es así primero lo elimino del hash
        if(hmthreads.containsKey(threadID)) hmthreads.remove(threadID); 

        hmthreads.put(threadID, thread);
    }


    public Thread getThread(long threadID)
    {
        if(hmthreads.containsKey(threadID))
        {
            return (Thread)hmthreads.get(threadID);
        }
        return null;
    }


    public Iterator getThreads()
    {
        return hmthreads.values().iterator();
    }

    public void removeThread(long threadID)
    {
        try{
            Thread thread=(Thread)hmthreads.get(threadID);
            if(thread.isAlive()) thread.interrupt();
            hmthreads.remove(threadID);
        }catch(Exception e){
            log.error(e);
        }
    }

}
