/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticClass;

/**
 *
 * @author javier.solis.g
 */
public class ProcessUtils
{
    
    public static Iterator listInstances(Class cls)
    {
        return listInstances(cls, null);
    }   
    
    public static Iterator listInstances(Class cls, String smodel)
    {
        SemanticClass scls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassByJavaName(cls.getName());
        SWBModel model=null;
        if(smodel!=null)
        {
            model=SWBContext.getWebSite(smodel);
            return model.listInstancesOfClass(scls);
        }else
        {
            return scls.listGenericInstances();
        }
    }
    
    public static GenericObject getLastInstance(Class cls, String smodel)
    {
        Iterator it=SWBComparator.sortByCreated(listInstances(cls,smodel),false);
        if(it.hasNext())
        {
            return (GenericObject)it.next();
        }else
        {
            return null;
        }
    }
    
}
