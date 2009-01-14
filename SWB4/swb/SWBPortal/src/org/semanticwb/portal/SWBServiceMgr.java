/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Calendar;
import org.semanticwb.model.Camp;
import org.semanticwb.model.Device;
import org.semanticwb.model.Dns;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.IPFilter;
import org.semanticwb.model.Language;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.PortletSubType;
import org.semanticwb.model.PortletType;
import org.semanticwb.model.Reference;
import org.semanticwb.model.Role;
import org.semanticwb.model.Rule;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateGroup;
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
public class SWBServiceMgr implements SemanticObserver {

    private static Logger log = SWBUtils.getLogger(SWBServiceMgr.class);
    private ArrayList list = null;

    public void notify(SemanticObject obj, Object prop, String action) {
        User usr = SWBPortal.getSessionUser();
        long reqid = SWBPortal.getSessionUserID();

        System.out.println("obj:" + obj + " cls:" + obj.getSemanticClass() + " prop:" + prop + " action:" + action + " " + usr + " " + reqid);

        if (prop == null) {
            notify(obj, action);
        } else if (prop instanceof SemanticProperty) {
            if (obj.instanceOf(Traceable.swb_Traceable) && Traceable.swb_Traceable.hasProperty(((SemanticProperty) prop).getName())) {
                return;
            } else {
                if (!list.contains(reqid)) {
                    notify(obj, "UPDATE");
                    list.add(reqid);
                }
            }
        }
        if (list.size() > 200) {
            list.subList(0, 100).clear();
        }
    }

    public void notify(SemanticObject obj, String action) {
        //Actions CREATE UPDATE REMOVE
        User usr = SWBPortal.getSessionUser();
        System.out.println("-->obj:" + obj + " cls:" + obj.getSemanticClass() + " action:" + action + " " + usr);

        SemanticClass cls = obj.getSemanticClass();
        if (cls.isSWB()) {
            GenericObject gen = obj.createGenericInstance();
            if (!action.equals("REMOVE")) //CREATE o UPDATE
            {
                if (gen instanceof Traceable) {
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
                if (gen instanceof WebSite) {
                    WebSite aux = (WebSite) gen;
                    java.io.File dir=new java.io.File(SWBPlatform.getWorkPath() + aux.getId() + "/Template");
                    dir.mkdirs();
                    dir=new java.io.File(SWBPlatform.getWorkPath() + aux.getId() + "/Portlet");
                    dir.mkdirs();
                }
            } else //REMOVES
            {
                if (gen instanceof WebSite) //Removes website
                {
                    WebSite aux = (WebSite) gen;
                    SWBPlatform.getSemanticMgr().removeModel(aux.getId());
                    SWBUtils.IO.removeDirectory(SWBUtils.getApplicationPath() + aux.getWorkPath());
                } else if (gen instanceof WebPage) // Removes webpage
                {
                    WebPage aux = (WebPage) gen;
                    //Removes relations (roles, templates, rules, etc), assotiations 2 the webpage
                    removeRelatedObject(aux.listRelatedObjects());
                    //Removes portlets associated to the webpage
                    Iterator<Portlet> pit = aux.listPortlets();
                    while (pit.hasNext()) {
                        ((Portlet) pit.next()).remove();
                    }
                    //Removes childs (webpages)
                    Iterator<WebPage> it = aux.listChilds();
                    while (it.hasNext()) {
                        ((WebPage) it.next()).remove();
                    }
                } else if (gen instanceof TemplateGroup) // Removes TemplateGroup
                {
                    TemplateGroup aux = (TemplateGroup) gen;
                    GenericIterator<Template> itTpls = aux.listTemplates();
                    while (itTpls.hasNext()) {
                        ((Template) itTpls.next()).remove();
                    }
                } else if (gen instanceof Template) // Removes Template
                {
                    Template aux = (Template) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                    SWBUtils.IO.removeDirectory(SWBUtils.getApplicationPath() + aux.getWorkPath());
                } else if (gen instanceof Rule) // Removes Rule
                {
                    Rule aux = (Rule) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                } else if (gen instanceof Role) // Removes Role
                {
                    Role aux = (Role) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                } else if (gen instanceof Language) // Removes Language
                {
                    Language aux = (Language) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                } else if (gen instanceof Device) // Removes Device
                {
                    Device aux = (Device) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                } else if (gen instanceof IPFilter) // Removes IPFilter
                {
                    IPFilter aux = (IPFilter) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                } else if (gen instanceof Calendar) // Removes Calendar
                {
                    Calendar aux = (Calendar) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                } else if (gen instanceof Dns) // Removes Template
                {
                    Dns aux = (Dns) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                } else if (gen instanceof Camp) // Removes Dns
                {
                    Camp aux = (Camp) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                } else if (gen instanceof PortletType) // Removes Camp
                {
                    PortletType aux = (PortletType) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                    GenericIterator<PortletSubType> itPSubType = aux.listSubTypes();
                    while (itPSubType.hasNext()) {
                        PortletSubType pSType = itPSubType.next();
                        pSType.remove();
                    }
                } else if (gen instanceof PortletSubType) // Removes PortletSubType
                {
                    PortletSubType aux = (PortletSubType) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                    GenericIterator<Portlet> itPortlet = aux.listPortlets();
                    while (itPortlet.hasNext()) {
                        Portlet portlet = itPortlet.next();
                        removeRelatedObject(portlet.listRelatedObjects());
                        portlet.remove();
                    }
                } else if (gen instanceof Portlet) // Removes Portlet
                {
                    Portlet aux = (Portlet) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                    SWBUtils.IO.removeDirectory(SWBUtils.getApplicationPath() + aux.getWorkPath());
                } else if (gen instanceof User) // Removes User
                {
                    User aux = (User) gen;
                    removeRelatedObject(aux.listRelatedObjects());
                }
            }
        }
    }

    public void removeRelatedObject(Iterator<GenericObject> itgo) {
        while (itgo.hasNext()) {
            GenericObject go = itgo.next();
            if (go instanceof Reference) {
                ((Reference) go).remove();
            }
        }
    }

    public void init() {
        log.event("Initializing SWBServiceMgr...");
        SWBPlatform.getSemanticMgr().registerObserver(this);
        list = new ArrayList();
    }
}
