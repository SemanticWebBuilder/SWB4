/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
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
    private ArrayList list=null;

    public void notify(SemanticObject obj, Object prop, String action) {
        User usr=SWBPortal.getSessionUser();
        long reqid=SWBPortal.getSessionUserID();
        
        System.out.println("obj:"+obj+" cls:"+obj.getSemanticClass()+" prop:"+prop+" action:"+action+" "+usr+" "+reqid);

        if(prop==null)
        {
            notify(obj, action);
        }else if(prop instanceof SemanticProperty)
        {
            if(obj.instanceOf(Traceable.swb_Traceable) && Traceable.swb_Traceable.hasProperty(((SemanticProperty)prop).getName()))
            {
                return;
            }else
            {
                if(!list.contains(reqid))
                {
                    notify(obj, "UPDATE");
                    list.add(reqid);
                }
            }
        }
        if(list.size()>200)
        {
            list.subList(0,100).clear();
        }
    }

    public void notify(SemanticObject obj, String action)
    {
        //Actions CREATE UPDATE REMOVE
        User usr=SWBPortal.getSessionUser();
        System.out.println("-->obj:"+obj+" cls:"+obj.getSemanticClass()+" action:"+action+" "+usr);

        SemanticClass cls=obj.getSemanticClass();
        if(cls.isSWB())
        {
            GenericObject gen=obj.createGenericInstance();
            if(!action.equals("REMOVE")) //CREATE o UPDATE
            {
                if(gen instanceof Traceable)
                {
                    Traceable aux=(Traceable)gen;
                    Date date=new Date();
                    if(aux.getCreated()==null)aux.setCreated(date);
                    aux.setUpdated(date);
                    if(usr!=null && usr.isRegistered())
                    {
                        if(aux.getCreator()==null)aux.setCreator(usr);
                        aux.setModifiedBy(usr);
                    }
                }
            }else //REMOVES
            {
                if(gen instanceof WebSite)
                {
                     WebSite aux=(WebSite)gen;

                }else if(gen instanceof WebPage)
                {
                     WebPage aux=(WebPage)gen;

                     Iterator<Portlet> pit=aux.listPortlets();
                     while(pit.hasNext())
                     {
                         pit.next().remove();
                     }

                     Iterator<WebPage> it=aux.listChilds();
                     while(it.hasNext())
                     {
                         WebPage ch=it.next();
                         ch.remove();
                     }
                }

            }
        }
    }

    public void init()
    {
        log.event("Initializing SWBServiceMgr...");
        SWBPlatform.getSemanticMgr().registerObserver(this);
        list=new ArrayList();
    }

}
