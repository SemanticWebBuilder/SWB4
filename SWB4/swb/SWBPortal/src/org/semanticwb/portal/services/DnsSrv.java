/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBInstance;
import org.semanticwb.model.Dns;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.SWBDBAdmLog;

/**
 *
 * @author jorge.jimenez
 */
public class DnsSrv {

    private static Logger log = SWBUtils.getLogger(WebSiteSrv.class);

    public Dns createDNS(SemanticModel model, String title, String description, String value, User user) throws SWBException {
        Dns dns = null;
        dns = SWBContext.createDns(model);
        dns.setTitle(title);
        dns.setDescription(description);
        dns.setValue(value);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", dns.getURI(), dns.getURI(), "create DNS", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating dns", e);
        }
        return dns;
    }
    
    public Dns createDNS(SemanticModel model, String dnsUri, String title, String description, String value, User user) throws SWBException {
        Dns dns = null;
        dns = SWBContext.createDns(model, dnsUri);
        dns.setTitle(title);
        dns.setDescription(description);
        dns.setValue(value);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "create", dns.getURI(), dns.getURI(), "create DNS", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating dns", e);
        }
        return dns;
    }

    public boolean removeDNS(Dns dns, User user) throws SWBException {
        boolean deleted = false;
        SWBContext.removeObject(dns.getURI());
        deleted = true;
        //logeo.creat
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "remove", dns.getURI(), dns.getURI(), "remove DNS", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing dns:" + dns.getURI(), e);
        }
        return deleted;
    }

    public boolean updateDNS(SemanticModel model, Dns dns, String title, String description, String value, User user) throws SWBException {
        boolean updated = false;
        
        if (title != null) {
            dns.setTitle(title);
        }
        if (description != null) {
            dns.setDescription(description);
        }
        if (value != null) {
            dns.setValue(value);
        }
        updated = true;
        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getURI(), "update", dns.getURI(), dns.getURI(), "update DNS", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error updating Dns", e);
        }
        return updated;
    }
}
