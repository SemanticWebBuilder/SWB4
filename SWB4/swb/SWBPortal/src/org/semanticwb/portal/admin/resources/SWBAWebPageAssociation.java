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
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.AssMember;
import org.semanticwb.model.Association;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Topic;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * Este recurso sirve para el despliegue de asociaciones existentes.
 * Se pueden agregar nuevas asociaciones.
 *
 * @author juan.fernandez
 */
public class SWBAWebPageAssociation extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBAWebPageAssociation.class);
    
    /** The base. */
    private Resource base = null;

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        base = getResourceBase();
        User user = paramRequest.getUser();
        String lang = user.getLanguage();
        WebPage wp = paramRequest.getWebPage();

        String suri = request.getParameter("suri");

        SemanticObject so = SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(suri);
        if (so.getGenericInstance() instanceof WebPage) {

            WebPage wpso = (WebPage) so.getGenericInstance();
            WebSite ws = wpso.getWebSite();

            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<legend>");
            out.println("Asociaciones existentes");
            out.println("</legend>");
            out.println("<table>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("Role");
            out.println("</th>");
            out.println("<th>");
            out.println("Type");
            out.println("</th>");
            out.println("<th>");
            out.println("Member");
            out.println("</th>");
            out.println("<th>");
            out.println("Member Role");
            out.println("</th>");
            out.println("</tr>");
            out.println("</thead>");

            //lista de asociaciones existentes de la página

            out.println("<tbody>");
            Iterator<Association> itassoc = wpso.listThisTypeAssociations();
            while (itassoc.hasNext()) {
                Association assoc = itassoc.next();

                out.println("<tr>");
                out.println("<td>");
                out.println(wpso.getThisRoleAssMember().getRole().getDisplayTitle(lang));
                out.println("</td>");
                out.println("<td>");
                out.println(assoc.getType().getDisplayTitle(lang));
                out.println("</td>");
                out.println("<td>");
                out.println(assoc.getMember().getMember().getDisplayTitle(lang));
                out.println("</td>");
                out.println("<td>");
                out.println(assoc.getMember().getRole().getDisplayTitle(lang));
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("</fieldset>");

            out.println("<fieldset>");
            SWBResourceURL urlAdd = paramRequest.getRenderUrl();
            urlAdd.setMode(SWBResourceURL.Mode_EDIT);
            urlAdd.setAction("add");
            urlAdd.setParameter("suri",suri);
            out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" name=\"btn_addAssoc\" onclick=\"window.location='" + urlAdd + "';\">Agregar Asociación</button>");
            out.println("</fieldset>");
            out.println("</div>");
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        base = getResourceBase();
        User user = paramRequest.getUser();
        String lang = user.getLanguage();
        WebPage wp = paramRequest.getWebPage();

        String suri = request.getParameter("suri");

        SemanticObject so = SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(suri);
        if (so.getGenericInstance() instanceof WebPage) {

            WebPage wpso = (WebPage) so.getGenericInstance();
            WebSite ws = wpso.getWebSite();

            out.println("<div class=\"swbform\">");

            SWBResourceURL urlAdd = paramRequest.getActionUrl();
            urlAdd.setMode(SWBResourceURL.Mode_VIEW);
            urlAdd.setAction("new");

            out.println("<form name=\"frmAddAsoc\" method=\"post\" action=\""+urlAdd+"\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\""+suri+"\" />");

            out.println("<fieldset>");
            out.println("<legend>");
            out.println("Agregar asociación");
            out.println("</legend>");

            out.println("<table>");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td>");
            out.println("<label for=\"selRole\">Role</label><select id=\"selRole\" name=\"selRole\">");

            Iterator<AssMember> it = wpso.listThisRoleAssMembers();
            while(it.hasNext())
            {
                AssMember am = it.next();
                out.println("<option value=\""+am.getURI()+"\">");
                out.println(am.getMember().getDisplayTitle(lang));
                out.println("</option>");
            }

            out.println("</select>");
            out.println("</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>");
            out.println("<label for=\"selType\">Type</label><select id=\"selType\" name=\"selType\">");

            Iterator<Association> ita = wpso.listThisTypeAssociations();
            while(ita.hasNext())
            {
                Association assoc = ita.next();
                out.println("<option value=\""+assoc.getType().getURI()+"\">");
                out.println(assoc.getType().getDisplayTitle(lang));
                out.println("</option>");
            }

            out.println("</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>");
            out.println("<label for=\"selMember\">Member</label><select id=\"selMember\" name=\"selMember\">");

            Iterator<Association> itm = wpso.listThisTypeAssociations();
            while(itm.hasNext())
            {
                Association assoc = itm.next();
                out.println("<option value=\""+assoc.getMember().getMember().getURI()+"\">");
                out.println(assoc.getMember().getMember().getDisplayTitle(lang));
                out.println("</option>");
            }

            out.println("</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>");
            out.println("<label for=\"selMemberRole\">Member Role</label><select id=\"selMemberRole\" name=\"selMemberRole\">");

            Iterator<Association> itmr = wpso.listThisTypeAssociations();
            while(itmr.hasNext())
            {
                Association assoc = itmr.next();
                out.println("<option value=\""+assoc.getMember().getRole().getURI()+"\">");
                out.println(assoc.getMember().getRole().getDisplayTitle(lang));
                out.println("</option>");
            }

            out.println("</td>");
            out.println("</tr>");

            out.println("</tbody>");
            out.println("</table>");

            out.println("</fieldset>");

            out.println("<fieldset>");
            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btn_newAssoc\" >Agregar Asociación</button>");
            out.println("</fieldset>");

            out.println("</form>");
            out.println("</div>");
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        String suri = request.getParameter("suri");
        if(action==null) action="";
        if(action.equals("new"))
        {
            String selRole = request.getParameter("selRole");
            String selType = request.getParameter("selType");
            String selMember = request.getParameter("selMember");
            String selMemberRole = request.getParameter("selMemberRole");

            SemanticObject so = SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(suri);
            if (so.getGenericInstance() instanceof WebPage)
            {
                WebPage wpso = (WebPage) so.getGenericInstance();
                WebSite ws = wpso.getWebSite();

                Association assoc = Association.ClassMgr.createAssociation(ws);
                assoc.setType((Topic)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(selType));

                AssMember amember1 = AssMember.ClassMgr.createAssMember(ws);
                amember1.setMember((Topic)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri));
                amember1.setRole((Topic)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(selRole));

                AssMember amember2 = AssMember.ClassMgr.createAssMember(ws);
                amember2.setMember((Topic)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(selMember));
                amember2.setRole((Topic)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(selMemberRole));

                assoc.addMember(amember1);
                assoc.addMember(amember2);

            }


        }
    }
}
