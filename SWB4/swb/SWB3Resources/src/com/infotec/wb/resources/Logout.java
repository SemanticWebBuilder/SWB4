/**  
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
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.infotec.wb.resources;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class Logout extends GenericResource
{
    
    /** TODO: VER 4.0
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        StringBuffer ret = new StringBuffer("");
        String acc = null != request.getParameter("acc") ? request.getParameter("acc").trim() : "";
        
        if ("logout".equals(acc))
        {
            request.getSession().removeAttribute(WBUserMgr.UserAtt+"-"+paramsRequest.getTopic().getMap().getDbdata().getRepository());
            String sCookNameLog = (String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/CookNameLog");
            WBUserMgr.getInstance().removeUser(paramsRequest.getUser().getSesid(),paramsRequest.getTopic().getMap().getDbdata().getRepository(),request, response);
            paramsRequest.getUser().logout();
            paramsRequest.getUser().setRecUser(DBUser.getInstance(paramsRequest.getTopic().getMap().getDbdata().getRepository()).getNewRecUser());
            ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0;URL=" + paramsRequest.getTopic().getUrl() + "\">");
        }
        else if (paramsRequest.getArguments().size() > 0 && paramsRequest.getUser().isLoged())
        {
            ret.append("<a href=\"" + getResourceBase().getUrl(paramsRequest.getTopic()) + "?acc=logout\">" + com.infotec.appfw.util.AFUtils.getLocaleString("locale_wb2_resources", "usrmsg_Logout_getHtml_logout") + "</a>");
        } 
        response.getWriter().print(ret.toString());
    }
     * */
}
