/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 */
public class SWBASocialAbout extends GenericResource{


    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException
    {
        PrintWriter out=response.getWriter();
        /*
        out.println("<BR><BR><BR><BR><center>");
        out.println("<table width=\"500\" height=\"300\" border=\"0\" background=\""+SWBPlatform.getContextPath()+"/swbadmin/images/splash.jpg\" >");
        out.println("  <tr height=\"220\">");
        out.println("    <td height=\"220\">&nbsp;</td>");
        out.println("  </tr>");
        out.println("  <tr height=\"80\">");
        out.println("    <td height=\"80\" align=\"center\" valign=\"top\" >");
        out.println("	   <font color=\"#000000\" size=\"3\" face=\"Arial, Helvetica, sans-serif\"><B>Version: "+SWBPlatform.getVersion()+"</B></font>");
        out.println("    </td>");
        out.println("  </tr>");
        out.println("  <tr height=\"80\">");
        out.println("    <td height=\"80\" align=\"center\" valign=\"top\" >");
        out.println("	   <font color=\"#000000\" size=\"3\" face=\"Arial, Helvetica, sans-serif\"><B>Version: "+SWBSocialUtil.getEnv("versionNo", "1.0") +"</B></font>");
        out.println("    </td>");
        out.println("  </tr>");
        out.println("</table></center>");
        * */
        out.println("<div id=\"version\">");
        out.println("<p class=\"versionmark\">SWB Social</p>");
        out.println("<p class=\"versionsocial\">Versión "+SWBSocialUtil.getEnv("versionNo", "1.0")+"</p>");
        out.println("<p class=\"versionmark\">SemanticWebBuilder</p>");
        out.println("<p class=\"versionswb\">Versión "+SWBPlatform.getVersion()+"</p>");
        out.println("<p class=\"versioncopy\">SemanticWebBuilder y SWB Social son marcas registradas</p>");
        out.println("</div>");


    }
}