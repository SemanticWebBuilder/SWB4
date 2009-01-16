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
import org.semanticwb.model.Portlet;
import org.semanticwb.model.Template;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author javier.solis
 */
public class SWBServiceMgr implements SemanticObserver {

    private static Logger log = SWBUtils.getLogger(SWBServiceMgr.class);

    public void notify(SemanticObject obj, Object prop, String action)
    {
        User usr = SWBPortal.getSessionUser();
        log.trace("obj:" + obj + " prop:" + prop + " action:" + action + " " + usr);
        SWBPortal.getDBAdmLog().saveAdmLog(usr, obj, prop, action);

        SemanticClass cls = obj.getSemanticClass();
        if(cls.isSWB())
        {
            GenericObject gen = obj.createGenericInstance();
            if (prop == null) //se modifico un objeto CREATE o REMOVE
            {
                if (action.equals("CREATE")) //CREATE
                {
                    updateTraceable(gen,usr);
                    if (gen instanceof WebSite) {
                        WebSite aux = (WebSite) gen;
                        java.io.File dir=new java.io.File(SWBPlatform.getWorkPath() + "/"+ aux.getId() + "/Template");
                        dir.mkdirs();
                        dir=new java.io.File(SWBPlatform.getWorkPath() + "/" + aux.getId() + "/Portlet");
                        dir.mkdirs();
                    }
                } else //REMOVES
                {
                    if (gen instanceof WebSite) //Removes website
                    {
                        WebSite aux = (WebSite) gen;
                        System.out.println("Entra a eliminar sitio:"+aux.getId()+"path:"+SWBPlatform.getWorkPath() + aux.getWorkPath());
                        SWBPlatform.getSemanticMgr().removeModel(aux.getId());
                        SWBUtils.IO.removeDirectory(SWBPlatform.getWorkPath() + aux.getWorkPath());
                    } else if (gen instanceof Template) // Removes Template
                    {
                        Template aux = (Template) gen;
                        SWBUtils.IO.removeDirectory(SWBPlatform.getWorkPath() + aux.getWorkPath());
                    } else if (gen instanceof Portlet) // Removes Portlet
                    {
                        Portlet aux = (Portlet) gen;
                        SWBUtils.IO.removeDirectory(SWBPlatform.getWorkPath() + aux.getWorkPath());
                    }
                }
            } else if (prop instanceof SemanticProperty) {
                if (obj.instanceOf(Traceable.swb_Traceable) && Traceable.swb_Traceable.hasProperty(((SemanticProperty) prop).getName())) {
                    return;
                } else {
                    updateTraceable(gen,usr);
                }
            }else
            {
                //TODO: SemanticClass
            }
        }
    }

    public void updateTraceable(GenericObject gen, User usr)
    {
        if (gen instanceof Traceable)
        {
            Traceable aux = (Traceable) gen;
            Date date = new Date();
            if (aux.getCreated() == null) {
                aux.setCreated(date);
            }
            aux.setUpdated(date);
            if (usr != null && usr.isRegistered()) {
                if (aux.getCreator() == null) {
                    aux.setCreator(usr);
                }
                aux.setModifiedBy(usr);
            }
        }
    }

    public void init() {
        log.event("Initializing SWBServiceMgr...");
        SWBPlatform.getSemanticMgr().registerObserver(this);
    }
}
