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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.portal.community.MicroSite;

/**
 *
 * @author juan.fernandez
 */
public class CommunityActivityUtil {
    private Logger log = SWBUtils.getLogger(CommunityActivityUtil.class);

    public CommunityActivityUtil(){}
    
    public Iterator<CommunityActivity> getCommunityActivities(MicroSite community)
    {
        ArrayList al = new ArrayList();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {

            conn = SWBUtils.DB.getDefaultConnection("MicroSiteWebPageUtil.getComminityActivities");
            pst = conn.prepareStatement("select commuri,usruri,elementuri,modified from swb_commlog where commuri = ? order by modified desc");
            pst.setString(1,community.getURI());
            rs = pst.executeQuery();
            while(rs.next())
            {
                String commURI = rs.getString("commuri");
                String usrURI = rs.getString("usruri");
                String elementURI = rs.getString("elementuri");
                Timestamp modified = rs.getTimestamp("modified");
                al.add(new CommunityActivity(commURI, usrURI, elementURI, modified));
            }
            rs.close();
            pst.close();
            conn.close();
        } catch (Exception e) {
            log.error("Error al traer las actividades de la comunidad.",e);
        } finally
        {
            try { rs.close(); } catch (Exception e) { }
            try { pst.close(); } catch (Exception e) { }
            try { conn.close(); } catch (Exception e) { }
        }
        return al.iterator();
    }

    public Iterator<CommunityActivity> getMemberActivities(User user)
    {
        ArrayList al = new ArrayList();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        if(user!=null)
        {
            try {
            conn = SWBUtils.DB.getDefaultConnection("MicroSiteWebPageUtil.getMemberActivities");
            pst = conn.prepareStatement("select commuri,usruri,elementuri,modified from swb_commlog where usruri = ? order by modified desc");
            pst.setString(1,user.getURI());
            rs=pst.executeQuery();
            while(rs.next())
            {
                String commURI = rs.getString("commuri");
                String usrURI = rs.getString("usruri");
                String elementURI = rs.getString("elementuri");
                Timestamp modified = rs.getTimestamp("modified");
                al.add(new CommunityActivity(commURI, usrURI, elementURI,modified));
            }
            rs.close();
            pst.close();
            conn.close();
            } catch (Exception e) {
                log.error("Error al traer las actividades del miembro en la comunidad.",e);
            } finally {
                try { rs.close(); } catch (Exception e) { }
                try { pst.close(); } catch (Exception e) { }
                try { conn.close(); } catch (Exception e) { }
            }
        }
        return al.iterator();

    }

}
