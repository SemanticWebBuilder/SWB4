/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Dns;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;

/**
 *
 * @author jorge.jimenez
 */
public class DnsSrv {

    private static Logger log = SWBUtils.getLogger(WebSiteSrv.class);

   
    public Dns createDNS(WebSite website, String id, String title, String description,  boolean isdefault, User user) throws SWBException {
        Dns dns = null;
        dns = website.createDns(id);
        dns.setTitle(title);
        dns.setDescription(description);
        dns.setDefault(isdefault);

        SWBPortal.log(user.getURI(), "create", dns.getURI(), dns.getURI(), "create Dns", null);
        
        return dns;
    }

    public boolean removeDNS(WebSite website, String id, User user) throws SWBException {
        boolean deleted = false;
        website.removeDns(id);
        deleted = true;
        
        SWBPortal.log(user.getURI(), "remove", id, id, "remove Dns", null);
        
        return deleted;
    }

    public boolean updateDNS(Dns dns, String title, String description, boolean isdefault,User user) throws SWBException {
        boolean updated = false;
        
        if (title != null) {
            dns.setTitle(title);
        }
        if (description != null) {
            dns.setDescription(description);
        }
        dns.setDefault(isdefault);
       
        updated = true;
       
        SWBPortal.log(user.getURI(), "update", dns.getURI(), dns.getURI(), "update Dns", null);
        
        return updated;
    }
}
