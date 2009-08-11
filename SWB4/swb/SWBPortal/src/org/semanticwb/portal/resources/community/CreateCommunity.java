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
package org.semanticwb.portal.resources.community;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.catalogs.LocationEntity;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class CreateCommunity extends GenericResource
{

    public static final String WEBPAGE_TOPIC_LOCATION = "Tlalpan";
    public static final String TEMAS_TOPIC_ID = "Temas";

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        WebPage currentWebPage = response.getWebPage();
        WebSite site=currentWebPage.getWebSite();
        if(request.getParameter("swbtp")!=null && site.getWebPage(request.getParameter("swbtp"))!=null)
        {
            currentWebPage=site.getWebPage(request.getParameter("swbtp"));
        }
        String type = request.getParameter("selecttype");

        if (request.getParameter("selecttype") != null && request.getParameter("title") != null && request.getParameter("description") != null)
        {          
            
            String title = request.getParameter("title");
            String description=request.getParameter("description");
            if (type.equals("t"))
            {
                if (currentWebPage.getParent() != null && currentWebPage.getParent().getParent() != null && currentWebPage.getParent().getParent().getParent() != null && currentWebPage.getParent().getParent().getParent().getId().equals(TEMAS_TOPIC_ID))
                {
                    try
                    {
                        LocationEntity entity = LocationEntity.getLocationEntity(WEBPAGE_TOPIC_LOCATION, site);
                        WebPage topic = currentWebPage;
                        String resByDefault = request.getParameter("resbydefault");
                        CommunityConfiguration.createCommunityTopic(site, title, entity, topic, resByDefault,description);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    System.out.println("La página web no es un tema");
                }
            }
            if (type.equals("p"))
            {                

                LocationEntity entity = LocationEntity.getLocationEntity("Tlalpan", site);
                WebPage topic = null;
                String resByDefault = request.getParameter("resbydefault");
                try
                {
                    CommunityConfiguration.createComunnityPerson(site, title, entity, topic, resByDefault);
                }
                catch (Exception e)
                {
                    e.printStackTrace();

                }
            }
            if (type.equals("o"))
            {
                try
                {                    
                    LocationEntity entity = LocationEntity.getLocationEntity("Tlalpan", site);
                    WebPage topic = currentWebPage;
                    String resByDefault = request.getParameter("resbydefault");
                    CommunityConfiguration.createComunnityOrganization(site, title, entity, topic, resByDefault, response, description);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            response.sendRedirect(currentWebPage.getUrl());
        }
        response.setMode(response.Mode_VIEW);        
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if(request.getParameter("modeswbcreatecommunity")!=null && request.getParameter("modeswbcreatecommunity").equals("true"))
        {
            doAdmin(request, response, paramRequest);
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebPage currentWebPage = paramRequest.getWebPage();
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        if (request.getParameter("selecttype") == null)
        {
            out.println("<div>");
            out.println("<form name='frmselecttype' method='post' action=''>");
            out.println("<fieldset><label for='selecttype'>Seleccione el tipo de comunidad a crear:</label>");
            out.println("<select id='selecttype' name='selecttype'>");
            if (currentWebPage.getParent() != null && currentWebPage.getParent().getParent() != null && currentWebPage.getParent().getParent().getParent() != null && currentWebPage.getParent().getParent().getParent().getId().equals(TEMAS_TOPIC_ID))
            {
                out.println("<option value='t'>Tema</option>");
            }
            else
            {
                out.println("<option value='p'>Persona</option>");
                out.println("<option value='o'>Organizaci&oacute;n</option>");
            }
            out.println("</select>");
            out.println("<input type='submit' name='enviar' value='Crear'>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("</div>");
        }
        else
        {
            String type = request.getParameter("selecttype");
            String topic=request.getParameter("swbtp");
            if(topic==null || topic.trim().equals(""))
            {
                topic="";
            }
            if (type.equals("t"))
            {
                if (request.getParameter("title") == null)
                {
                    out.println("<div>");
                    out.println("<table width='50%'>");
                    out.println("<form name='frmselecttype' method='post' action='" + paramRequest.getActionUrl() + "'><fieldset>");
                    out.println("<input type='hidden' name='swbtp' id='swbtp' value='"+ topic +"'>");
                    out.println("<input type='hidden' name='selecttype' value='" + type + "'><br>");
                    out.println("<tr>");
                    out.println("<td>");
                    out.println("<label for='title'>Indique el tema a crear:</label>");
                    out.println("</td>");
                    out.println("<td>");
                    out.println("<input type='text' size='60' maxlength='255' name='title' id='title'><br>");
                    out.println("</td>");
                    out.println("</tr>");

                    out.println("<tr>");
                    out.println("<td>");
                    out.println("<label for='description'>Indique la descripción del tema:</label>");
                    out.println("</td>");
                    out.println("<td>");
                    out.println("<textarea rows='7' cols='40' name='description' id='description'></textarea>");
                    out.println("</td>");
                    out.println("</tr>");

                    
                    out.println("<tr>");
                    out.println("<td colspan='2' align='center'>");
                    out.println("<input type='button' onClick='ValidaTitulo()' name='enviar' value='Crear'>");
                    out.println("<td>");
                    out.println("</tr>");
                    
                    out.println("</fieldset>");
                    out.println("</form>");
                    out.println("</table>");
                    out.println("</div>");
                    out.println("<script>");
                    out.println("function ValidaTitulo()");
                    out.println("{");
                    out.println("   if(frmselecttype.title.value=='')");
                    out.println("   {");
                    out.println("       alert('Debe indicar el título');");
                    out.println("       return;");
                    out.println("   }");
                    out.println("   if(frmselecttype.description.value=='')");
                    out.println("   {");
                    out.println("       alert('Debe indicar la descripción');");
                    out.println("       return;");
                    out.println("   }");
                    out.println("   frmselecttype.submit();");
                    out.println("}");
                    out.println("</script>");
                }

            }
            else if (type.equals("p"))
            {
                if (request.getParameter("title") == null)
                {
                    out.println("<div>");
                    out.println("<form name='frmselecttype' method='post' action='"+ paramRequest.getActionUrl() +"'>");
                    out.println("<fieldset><label for='title'>Indique el nombre de la persona:</label>");
                    out.println("<input type='text' name='title' id='title'>");
                    out.println("<input type='hidden' name='selecttype' value='" + type + "'>");
                    out.println("<select name='resbydefault'>");
                    out.println("<option value='" + CommunityConfiguration.BLOG_RESOURCE_TYPE_ID + "'>Blog");
                    out.println("<option value='" + CommunityConfiguration.FORO_RESOURCE_TYPE_ID + "'>Foro");
                    out.println("<option value='" + CommunityConfiguration.WIKI_RESOURCE_TYPE_ID + "'>Wiki");
                    out.println("<option value='n'>Ninguno");
                    out.println("</select>");
                    out.println("<input type='button' onClick='ValidaTitulo()' name='enviar' value='Crear'>");
                    out.println("</fieldset>");
                    out.println("</form>");
                    out.println("</div>");
                    out.println("<script>");
                    out.println("function ValidaTitulo()");
                    out.println("{");
                    out.println("   if(frmselecttype.title.value=='')");
                    out.println("   {");
                    out.println("       alert('Debe indicar el nombre de la persona');");
                    out.println("       return;");
                    out.println("   }");
                    out.println("   frmselecttype.submit();");
                    out.println("}");
                    out.println("</script>");
                }
            }
            else if (type.equals("o"))
                {
                    if (request.getParameter("title") == null)
                    {
                        out.println("<div>");
                        out.println("<table width='50%'>");
                        out.println("<form name='frmselecttype' method='post' action='"+ paramRequest.getActionUrl() +"'>");
                        out.println("<input type='hidden' name='selecttype' value='" + type + "'>");
                        out.println("<input type='hidden' name='swbtp' id='swbtp' value='"+ topic +"'>");

                        out.println("<tr>");
                        out.println("<td>");
                        out.println(" Indique el nombre organización:");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("<input type='text' size='60' maxlength='255' name='title' id='title'>");
                        out.println("</td>");
                        out.println("</tr>");

                        out.println("<tr>");
                        out.println("<td>");
                        out.println("Indique la descripción del tema:");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("<textarea rows='7' cols='40' name='description' id='description'></textarea>");
                        out.println("</td>");
                        out.println("</tr>");
                        
                        out.println("<tr>");
                        out.println("<td colspan='2' align='center'>");
                        out.println("<input type='button' onClick='ValidaTitulo()' name='enviar' value='Crear'>");
                        out.println("<td>");
                        out.println("</tr>");
                        
                        out.println("</form>");
                        out.println("</table>");
                        out.println("</div>");
                        out.println("<script>");
                        out.println("function ValidaTitulo()");
                        out.println("{");
                        out.println("   if(frmselecttype.title.value=='')");
                        out.println("   {");
                        out.println("       alert('Debe indicar el nombre de la organización');");
                        out.println("       return;");
                        out.println("   }");
                        out.println("   frmselecttype.submit();");
                        out.println("}");
                        out.println("</script>");
                    }
                }
        }
    }
}
