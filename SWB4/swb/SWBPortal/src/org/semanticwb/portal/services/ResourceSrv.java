/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBException;
import org.semanticwb.model.PortletClass;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;

/**
 *
 * @author jorge.jimenez
 */
public class ResourceSrv {

    public boolean createContentPortlet(WebSite website, String title, String description, User user) throws SWBException {
        boolean doAction = false;
        PortletClass portlet = website.createPortletClass();
//        portlet.setTitle(title);
//        portlet.setDescription(description);
//        portlet.
//        doAction = true;
        
        //SWBPortal.log(user.getURI(), "create", cPortlet.getURI(), cPortlet.getURI(), "create cPortlet", null); 
        
        return doAction;
    }
//
//    public boolean createContentPortlet(WebSite website, String id, String title, String description, User user) throws SWBException {
//        boolean doAction = false;
//        ContentPortlet cPortlet = website.createContentPortlet(id);
//        cPortlet.setTitle(title);
//        cPortlet.setDescription(description);
//        cPortlet.setUserCreated(user);
//        doAction = true;
//        //logeo
//        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", cPortlet.getName(), cPortlet.getURI(), "create contentPortlet", null);
//        try {
//            swbAdmLog.create();
//        } catch (Exception e) {
//            throw new SWBException("Error creating language", e);
//        }
//        return doAction;
//    }
//
//    public boolean removeContentPortlet(WebSite website, String id, User user) throws SWBException {
//        boolean doAction = false;
//        website.removeContentPortlet(id);
//
//        doAction = true;
//        //logeo
//        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove contentPortlet", null);
//        try {
//            swbAdmLog.create();
//        } catch (Exception e) {
//            throw new SWBException("Error removing contentPortlet", e);
//        }
//        return doAction;
//    }
//
//    public boolean createApplicationPortlet(WebSite website, String title, String description, User user) throws SWBException {
//        boolean doAction = false;
//
//        ApplicationPortlet aPortlet = website.createApplicationPortlet();
//        aPortlet.setTitle(title);
//        aPortlet.setDescription(description);
//        aPortlet.setUserCreated(user);
//        doAction = true;
//        //logeo
//        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", aPortlet.getName(), aPortlet.getURI(), "create applicationPortlet", null);
//        try {
//            swbAdmLog.create();
//        } catch (Exception e) {
//            throw new SWBException("Error creating appPortlet", e);
//        }
//        return doAction;
//    }
//
//    public boolean createApplicationPortlet(WebSite website, String id, String title, String description, User user) throws SWBException {
//        boolean doAction = false;
//
//        ApplicationPortlet aPortlet = website.createApplicationPortlet(id);
//        aPortlet.setTitle(title);
//        aPortlet.setDescription(description);
//        aPortlet.setUserCreated(user);
//        doAction = true;
//        //logeo
//        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", aPortlet.getName(), aPortlet.getURI(), "create applicationPortlet", null);
//        try {
//            swbAdmLog.create();
//        } catch (Exception e) {
//            throw new SWBException("Error creating appPortlet", e);
//        }
//        return doAction;
//    }
//
//    public boolean removeApplicationPortlet(WebSite website, String id, User user) throws SWBException {
//        boolean doAction = false;
//        website.removeApplicationPortlet(id);
//
//        doAction = true;
//        //logeo
//        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove applicationPortlet", null);
//        try {
//            swbAdmLog.create();
//        } catch (Exception e) {
//            throw new SWBException("Error removing applicationPortlet", e);
//        }
//        return doAction;
//    }
//
//    public boolean createStrategyPortlet(WebSite website, String title, String description, User user) throws SWBException {
//        boolean doAction = false;
//
//        StrategyPortlet strPortlet = website.createStrategyPortlet();
//        strPortlet.setTitle(title);
//        strPortlet.setDescription(description);
//        strPortlet.setUserCreated(user);
//        doAction = true;
//        //logeo
//        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", strPortlet.getName(), strPortlet.getURI(), "create strategyPortlet", null);
//        try {
//            swbAdmLog.create();
//        } catch (Exception e) {
//            throw new SWBException("Error creating strategyPortlet", e);
//        }
//        return doAction;
//    }
//
//    public boolean createStrategyPortlet(WebSite website, String id, String title, String description, User user) throws SWBException {
//        boolean doAction = false;
//
//        StrategyPortlet strPortlet = website.createStrategyPortlet(id);
//        strPortlet.setTitle(title);
//        strPortlet.setDescription(description);
//        strPortlet.setUserCreated(user);
//        doAction = true;
//        //logeo
//        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", strPortlet.getName(), strPortlet.getURI(), "create strategyPortlet", null);
//        try {
//            swbAdmLog.create();
//        } catch (Exception e) {
//            throw new SWBException("Error creating StrategyPortlet", e);
//        }
//        return doAction;
//    }
//
//    public boolean removeStrategyPortlet(WebSite website, String id, User user) throws SWBException {
//        boolean doAction = false;
//        website.removeStrategyPortlet(id);
//        doAction = true;
//        //logeo
//        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove StrategyPortlet", null);
//        try {
//            swbAdmLog.create();
//        } catch (Exception e) {
//            throw new SWBException("Error removing StrategyPortlet", e);
//        }
//        return doAction;
//    }
//
//    public boolean createSystemPortlet(WebSite website, String title, String description, User user) throws SWBException {
//        boolean doAction = false;
//
//        SystemPortlet systemPortlet = website.createSystemPortlet();
//        systemPortlet.setTitle(title);
//        systemPortlet.setDescription(description);
//        systemPortlet.setUserCreated(user);
//        doAction = true;
//        //logeo
//        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", systemPortlet.getName(), systemPortlet.getURI(), "create SystemPortlet", null);
//        try {
//            swbAdmLog.create();
//        } catch (Exception e) {
//            throw new SWBException("Error creating SystemPortlet", e);
//        }
//        return doAction;
//    }
//
//    public boolean removeSystemPortlet(WebSite website, String id, User user) throws SWBException {
//        boolean doAction = false;
//        website.removeSystemPortlet(id);
//        doAction = true;
//        //logeo
//        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", id, id, "remove SystemPortlet", null);
//        try {
//            swbAdmLog.create();
//        } catch (Exception e) {
//            throw new SWBException("Error removing SystemPortlet", e);
//        }
//        return doAction;
//    }
// 
}
