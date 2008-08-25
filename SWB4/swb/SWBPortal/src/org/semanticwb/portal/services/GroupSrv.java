/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.ObjectGroup;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;

/**
 *
 * @author jorge.jimenez
 */
public class GroupSrv 
{

    public boolean createGroup(WebSite webSite, String title, String description, String type, User user) throws SWBException {
        boolean doAction = false;
        ObjectGroup objGroup = webSite.createObjectGroup();
        objGroup.setTitle(title);
        objGroup.setDescription(description);
        objGroup.setProperty("type", type);
        objGroup.setCreator(user);
        doAction = true;

        SWBPortal.log(user.getURI(), "create", objGroup.getURI(), objGroup.getURI(), "create Group", null);
        
        return doAction;
    }

    public boolean updateGroup(ObjectGroup objectGroup, String title, String description, User user) throws SWBException {
        boolean doAction = false;
        if (title != null) {
            objectGroup.setTitle(title);
        }
        if (description != null) {
            objectGroup.setDescription(description);
        }
        objectGroup.setModifiedBy(user);
        doAction = true;

        SWBPortal.log(user.getURI(), "update", objectGroup.getURI(), objectGroup.getURI(), "update Group", null);
        
        return doAction;
    }

    /**
     * Borra grupos y sus elementos, pero sin borrar todo lo que cada elemento incluye (fyleSystem, BD, etc)
     * No serviría por ejemplo para templates, en donde se tienen q eleminar muchas cosas al eliminar un template
     * TODO:Revisar si borra cada elemento de los grupos inmersos
     * @param webSite
     * @param id
     * @param user
     * @return
     * @throws org.semanticwb.SWBException
     */
    public boolean removeGroup(WebSite webSite, String id, User user) throws SWBException {
        boolean doAction = false;
        ObjectGroup objGroup=webSite.getObjectGroup(id);
        objGroup.removeAllGroup();        
        webSite.removeObjectGroup(id);
        doAction = true;

        SWBPortal.log(user.getURI(), "remove", id, id, "remove Group", null);
        
        return doAction;
    }
    
}
