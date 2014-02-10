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
import org.semanticwb.*;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * Recurso para la administarci�n de WebBuilder que permite seleccionar los
 * t�picos en los cuales se mostrar� el recurso seleccionado.
 *
 * WebBuilder administration resource that allows select the topics to show the
 * selected resource.
 */
public class SWBAUserFilterExt extends SWBAUserFilter
{

    /**
     * The log.
     */
    private Logger log = SWBUtils.getLogger(SWBAFilterResource.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();

        User user = paramRequest.getUser();

        String lang = user.getLanguage();
        String contx = SWBPortal.getContextPath();
        String userRep = user.getUserRepository().getId(); //request.getParameter("userRep");
        String users[] = request.getParameterValues("users");
        String search = request.getParameter("s");
        
        if(search!=null)
        {
            if (userRep != null)
            {
                search=search.toLowerCase();
                Iterator<User> it2 = UserRepository.ClassMgr.getUserRepository(userRep).listUsers();
                while (it2.hasNext())
                {
                    User ur = it2.next();
                    String txt=ur.getLogin()+" ("+ur.getFullName()+")";
                    if(search.length()==0 || txt.toLowerCase().indexOf(search)>-1)
                    {
                        out.println("<option value=\""+ur.getId()+"\">"+txt+"</option>");
                    }
                }
            }
            return;
        }        

        if (users == null)
        {

            out.println("<style type=\"text/css\">\n"
                    + "    #uf_select, #uf_select2 {\n"
                    + "        width:255px;\n"
                    + "        height:300px;\n"
                    + "        overflow:auto;\n"
                    + "    }\n"
                    + "    div#uf_sel1, div#uf_sel2 {\n"
                    + "        float: left;\n"
                    + "    }\n"
                    + "    div#uf_leftRightButtons {\n"
                    + "        float: left;\n"
                    + "        padding: 10em 0.5em 0 0.5em;\n"
                    + "    }\n"
                    + "</style>");

            out.println("<form id=\"userFilter\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\"" + paramRequest.getRenderUrl() + "\"  onsubmit_=\"submitForm('userFilter');\n"
                    + "        return false;\" method=\"post\">\n"
                    + "    <fieldset>\n"
                    + "        <table>\n"
                    + "            <tr>\n"
                    + "                <td width=\"200px\" align=\"right\"><label for=\"uf_search\">Buscador de Usuarios:</label></td>\n"
                    + "                <td>\n"
                    + "                    <input id=\"uf_search\" type=\"text\" dojoType=\"dijit.form.TextBox\"> &nbsp; \n"
                    + "                    <button onclick=\"\n" 
                    + "                            var s=document.getElementById('uf_search').value;\n"
                    + "                            var rep='"+userRep+"';\n"
                    + "                            var cont=getSyncHtml('" + paramRequest.getRenderUrl() + "?userRep='+rep+'&s='+s);\n"
                    + "                            var sel = document.getElementById('uf_select');\n"
                    + "                            sel.innerHTML =cont;\n"
                    + "                            return false;\n"
                    + "                            \" title=\"Buscar Usuarios\">Buscar</button>\n"
                    + "                </td>\n"
                    + "            </tr>"
                    + "            <tr>\n"
                    + "                <td width=\"200px\" align=\"right\">\n"
                    + "                    <label>Usuarios:</label>\n"
                    + "                </td>                \n"
                    + "                <td> \n"
                    + "                    <div>\n"
                    + "                        <div id=\"uf_sel1\" role=\"presentation\">\n"
                    + "                            <label for=\"allusers\">Lista de Usuarios:</label><br>\n"
                    + "                            <select multiple=\"true\" dojoType=\"dijit.form.MultiSelect\" id=\"uf_select\">\n"
                    + "");
            out.println("                            </select>\n"
                    + "                        </div>\n"
                    + "                        <div id=\"uf_leftRightButtons\" role=\"presentation\">\n"
                    + "                            <span>\n"
                    + "                                <button class=\"switch\" onclick=\"dijit.byId('uf_select').addSelected(dijit.byId('uf_select2'));\n"
                    + "        return false;\" title=\"Move Items to First list\">&lt;</button>\n"
                    + "                                <button class=\"switch\" onclick=\"dijit.byId('uf_select2').addSelected(dijit.byId('uf_select'));\n"
                    + "        return false;\" title=\"Move Items to Second list\">&gt;</button>\n"
                    + "                            </span>\n"
                    + "                        </div>\n"
                    + "                        <div id=\"uf_sel2\" role=\"presentation\">\n"
                    + "                            <label for=\"users\">Usuarios seleccionados:</label><br>\n"
                    + "                            <select multiple=\"true\" name=\"users\" dojoType=\"dijit.form.MultiSelect\" id=\"uf_select2\">\n"
                    + "                            </select>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                </td>\n"
                    + "            </tr>\n"
                    + "        </table>\n"
                    + "    </fieldset>\n"
                    + "    <fieldset>\n"
                    + "        <span align=\"center\">\n"
                    + "            <button dojoType=\"dijit.form.Button\" name=\"save\" type=\"submit\" value=\"save\">Editar Filtro</button>\n"
                    + "        </span>\n"
                    + "    </fieldset>\n"
                    + "</form>\n"
                    + "");
        } else //Update
        {
            String maps = paramRequest.getWebPage().getWebSiteId();
//            Iterator<WebSite> it = SWBContext.listWebSites();
//            while (it.hasNext())
//            {
//                WebSite webSite = it.next();
//                if (webSite.getUserRepository().equals(userRep))
//                {
//                    maps += "|" + webSite.getId();
//                }
//            }
            String id = SWBUtils.TEXT.join("|", users);
            //System.out.println("id:"+id);

            UserRepository rep=UserRepository.ClassMgr.getUserRepository(userRep);

            out.println("<form id=\"userFilter/form\" dojoType=\"dijit.form.Form\" action=\"" + paramRequest.getRenderUrl() + "\" class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<legend>Usuarios seleccionados</legend>");
            out.println("<ul>");
            for(int x=0;x<users.length;x++)
            {
                User usr=rep.getUser(users[x]);
                out.println(usr.getLogin()+" ("+usr.getFullName()+")");
                if(x+1<users.length)out.println(", ");
            }
            out.println("</ul>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<legend>Filtro a aplicar</legend>");
            

            out.println("<div class=\"applet\">\n"
                    + "    <applet name=\"editfilter\" code=\"applets.filterSection.FilterSection.class\" codebase=\"/\" archive=\"swbadmin/lib/SWBAplFilterSection.jar, swbadmin/lib/SWBAplCommons.jar\" width=\"100%\" height=\"500\">\n"
                    + "        <param name=\"jsess\" value=\""+request.getSession().getId()+"\">\n"
                    + "        <param name=\"cgipath\" value=\""+paramRequest.getRenderUrl().setCallMethod(3).setMode("gateway")+"\">\n"
                    + "        <param name=\"locale\" value=\""+lang+"\">\n"
                    + "        <param name=\"tm\" value=\""+userRep+"\">\n"
                    + "        <param name=\"idresource\" value=\"mv:"+id+"\">\n"
                    + "        <param name=\"maps\" value=\""+maps+"\">\n"
                    + "        <param name=\"isGlobalTM\" value=\"true\">\n"
                    + "    </applet>\n"
                    + "</div>");
            
            out.println("</fieldset>");

            out.println("<fieldset>");
            out.println("    <span>");
            out.println("        <button dojoType=\"dijit.form.Button\" name=\"back\" type=\"submit\" value=\"back\">Regresar</button>");
            out.println("    </span>");
            out.println("</fieldset>");   

            out.println("</form>");            
            
        }


    }
}
