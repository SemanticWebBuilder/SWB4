/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.util.Date;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author javier.solis
 */
public class SWBServiceMgr implements SemanticObserver
{
    private static Logger log=SWBUtils.getLogger(SWBServiceMgr.class);

    public void notify(SemanticObject obj, Object prop, String action) {
        User usr=SWBPortal.getSessionUser();
        long reqid=SWBPortal.getSessionUserID();
        
        System.out.println("obj:"+obj+" cls:"+obj.getSemanticClass()+" prop:"+prop+" action:"+action+" "+usr+" "+reqid);

        //Avoid recursivity
        if(prop instanceof SemanticProperty && prop!=null && Traceable.swb_Traceable.hasProperty(((SemanticProperty)prop).getName()))return;


        if(obj.instanceOf(Traceable.swb_Traceable))
        {
            Traceable aux=(Traceable)obj.createGenericInstance();
            Date date=new Date();
            if(aux.getCreated()==null)aux.setCreated(date);
            aux.setUpdated(date);
            if(usr.isRegistered())
            {
                if(aux.getCreator()==null)aux.setCreator(usr);
                aux.setModifiedBy(usr);
            }
        }
        
    }

    public void init()
    {
        log.event("Initializing SWBServiceMgr...");
        SWBPlatform.getSemanticMgr().registerObserver(this);
    }

}
