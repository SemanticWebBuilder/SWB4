/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community.utilresources;

import java.sql.Timestamp;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.community.Member;
import org.semanticwb.portal.community.MicroSite;
import org.semanticwb.portal.community.MicroSiteElement;

/**
 *
 * @author juan.fernandez
 */
public class CommunityActivity {
    private Logger log = SWBUtils.getLogger(CommunityActivity.class);
    private String commURI = null;
    private String usrURI = null;
    private String elementURI = null;
    private Timestamp modified = null;

    public CommunityActivity(String commURI, String usrURI, String elementURI, Timestamp modified)
    {

        if(commURI!=null && usrURI!=null && elementURI!=null && modified!=null)
        {
            this.commURI = commURI;
            this.usrURI = usrURI;
            this.elementURI = elementURI;
            this.modified = modified;
        }
    }

    /**
     * @return the commURI
     */
    public String getCommURI() {
        return commURI;
    }

    /**
     * @return the usrURI
     */
    public String getUsrURI() {
        return usrURI;
    }

    /**
     * @return the elementURI
     */
    public String getElementURI() {
        return elementURI;
    }

    public MicroSite getCommunity()
    {
        
        try {
            SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
            GenericObject obj = ont.getGenericObject(this.commURI);
            if(obj instanceof MicroSite) return (MicroSite)obj;
        } catch (Exception e) {
            log.error("Error al obtener la comunidad.",e);
        }
        return null;
    }

    public User getUser()
    {
        try {
            SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
            GenericObject obj = ont.getGenericObject(this.usrURI);
            if(obj instanceof User) return (User)obj;
        } catch (Exception e) {
            log.error("Error al obtener el usuario de la comunidad.",e);
        }
        return null;
    }

    public Member getMember()
    {
        Member member = null;
        try {
                MicroSite ms = getCommunity();
                member = Member.getMember(getUser(), ms);
            
        } catch (Exception e) {
            log.error("Error al obtener el miembro de la comunidad.",e);
        }
        return member;
    }

    public MicroSiteElement getElement()
    {
        try {
            SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
            GenericObject obj = ont.getGenericObject(this.elementURI);

            if(obj instanceof MicroSiteElement) return (MicroSiteElement)obj;
            
        } catch (Exception e) {
            log.error("Error al obtener el elemento de la comunidad.",e);
        }

        return null;
    }

    /**
     * @return the modified
     */
    public Timestamp getModified() {
        return modified;
    }

}
