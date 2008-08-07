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

    public Dns createDNS(String modelUri, String uri, String title, String description, String value, User user) throws SWBException {
        Dns dns = null;
        SemanticModel model = SWBInstance.getSemanticMgr().loadDBModel(modelUri);
        dns = SWBContext.createDns(model, uri);
        dns.setTitle(title);
        dns.setDescription(description);
        dns.setValue(value);

        //logeo
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", uri, uri, "create DNS", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error creating dns", e);
        }
        return dns;
    }

    public boolean removeDNS(String dnsUri, User user) throws SWBException {
        boolean deleted = false;
        SWBContext.removeObject(dnsUri);
        deleted = true;
        //logeo.creat
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "remove", dnsUri, dnsUri, "remove DNS", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error removing dns:" + dnsUri, e);
        }
        return deleted;
    }

    public boolean updateDNS(String modelUri, String uri, String title, String description, String value, User user) throws SWBException {
        boolean updated = false;
        Dns dns = null;
        SemanticModel model = SWBInstance.getSemanticMgr().loadDBModel(modelUri);
        dns = SWBContext.getDns(uri);

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
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(user.getName(), "create", uri, uri, "create DNS", null);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            throw new SWBException("Error adding template to WebPage", e);
        }
        return updated;
    }
}
