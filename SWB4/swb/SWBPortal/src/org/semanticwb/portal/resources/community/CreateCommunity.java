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
    private static final String TEMAS_TOPIC_ID = "Temas";

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        WebPage currentWebPage = response.getTopic();
        if (request.getParameter("selecttype") != null && request.getParameter("title") != null)
        {
            String type = request.getParameter("selecttype");
            String title = request.getParameter("title");
            if (type.equals("t"))
            {
                if (currentWebPage.getParent() != null && currentWebPage.getParent().getParent() != null && currentWebPage.getParent().getParent().getParent() != null && currentWebPage.getParent().getParent().getParent().getId().equals(TEMAS_TOPIC_ID))
                {
                    try
                    {
                        WebSite site = response.getTopic().getWebSite();
                        LocationEntity entity = LocationEntity.getLocationEntity(WEBPAGE_TOPIC_LOCATION, site);
                        WebPage topic = currentWebPage;
                        String resByDefault = request.getParameter("resbydefault");
                        CommunityConfiguration.createCommunityTopic(site, title, entity, topic, resByDefault);
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
                WebSite site = response.getTopic().getWebSite();

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
                    WebSite site = response.getTopic().getWebSite();
                    LocationEntity entity = LocationEntity.getLocationEntity("Tlalpan", site);
                    WebPage topic = null;
                    String resByDefault = request.getParameter("resbydefault");
                    CommunityConfiguration.createComunnityOrganization(site, title, entity, topic, resByDefault);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        response.setMode(response.Mode_VIEW);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        doAdmin(request, response, paramRequest);
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {

        WebPage currentWebPage = paramRequest.getTopic();
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
                out.println("<option value='o'>Organización</option>");
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
            if (type.equals("t"))
            {
                if (request.getParameter("title") == null)
                {
                    out.println("<div>");
                    out.println("<form name='frmselecttype' method='post' action='" + paramRequest.getActionUrl() + "'>");
                    out.println("<fieldset><label for='title'>Indique el tema a crear:</label>");
                    out.println("<input type='text' name='title' id='title'>");
                    out.println("<input type='hidden' name='selecttype' value='" + type + "'><br>");
                    out.println("<label for='title'>Indique el el contenido por defecto:</label>");
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
                    out.println("       alert('Debe indicar el título');");
                    out.println("       return;");
                    out.println("   }");
                    out.println("   frmselecttype.submit();");
                    out.println("}");
                    out.println("</script>");
                }

            }
            if (type.equals("p"))
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
                if (type.equals("o"))
                {
                    if (request.getParameter("title") == null)
                    {
                        out.println("<div>");
                        out.println("<form name='frmselecttype' method='post' action='"+ paramRequest.getActionUrl() +"'>");
                        out.println("<fieldset><label for='title'>Indique el nombre organización:</label>");
                        out.println("<input type='text' name='title' id='title'>");
                        out.println("<input type='hidden' name='selecttype' value='" + type + "'>");
                        out.println("<input type='button' onClick='ValidaTitulo()' name='enviar' value='Crear'>");
                        out.println("</fieldset>");
                        out.println("</form>");
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
}
