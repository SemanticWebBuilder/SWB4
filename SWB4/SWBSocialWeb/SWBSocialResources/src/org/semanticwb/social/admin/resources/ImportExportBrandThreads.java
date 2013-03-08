/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jorge.jimenez
 */
public class ImportExportBrandThreads {
    private static Logger log=SWBUtils.getLogger(ImportExportBrandThreads.class);
    private static ImportExportBrandThreads instance = null;
    private static HashMap hmthreads=new HashMap();


    public ImportExportBrandThreads SWBModelAdminThreads()
    {
        if (instance == null)
        {
            instance = new ImportExportBrandThreads();

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