/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
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
            if(obj!=null && obj instanceof MicroSite) return (MicroSite)obj;
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
            if(obj!=null && obj instanceof User) return (User)obj;
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
                if(ms!=null&&getUser()!=null)
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


            if(obj!=null && obj instanceof MicroSiteElement) return (MicroSiteElement)obj;
            
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
