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
package org.semanticwb.portal.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class FavoriteWebPageByCookie extends GenericAdmResource
{

    private static Logger log = SWBUtils.getLogger(FavoriteWebPageByCookie.class);

    public void addCookie(HttpServletRequest request, HttpServletResponse response, String idportal, String idwebpage)
    {
        Cookie[] cookies = request.getCookies();
        List<String> values = getIds(cookies, idportal);

        if (!values.contains(idwebpage))
        {
            values.add(idwebpage);
        }
        if (values.size() > 5)
        {
            int end = values.size();
            for (int i = 5; i < end; i++)
            {
                values.remove(0);
            }
        }
        String valuecookie = "";
        for (String id : values)
        {
            valuecookie += id + "|";
        }
        if (valuecookie.endsWith("|"))
        {
            valuecookie = valuecookie.substring(0, valuecookie.length() - 1);
        }
        Cookie cookie = null;
        boolean found = false;
        if (cookies != null)
        {
            for (Cookie c : cookies)
            {
                if (c.getName().equals(idportal))
                {
                    cookie = c;
                    found = true;
                    break;
                }
            }
        }
        if (found)
        {            
            cookie.setMaxAge(-1);
        }

        cookie = new Cookie(idportal, valuecookie);
        cookie.setMaxAge(60 * 60 * 24 * 365);
        cookie.setPath(SWBPortal.getContextPath());
        response.addCookie(cookie);
        cookie.setValue(valuecookie);
        //cookie.setPath("/");
        //response.addCookie(cookie);
        request.setAttribute("cookie_" + idportal, values);
    }

    public List<String> getIds(Cookie[] cookies, String idportal)
    {
        ArrayList<String> values = new ArrayList<String>();
        if (cookies != null && cookies.length > 0)
        {
            for (Cookie cookie : cookies)
            {
                if (cookie.getName() != null && cookie.getName().equals(idportal))
                {
                    String s_value = cookie.getValue();
                    StringTokenizer st = new StringTokenizer(s_value, "|");
                    while (st.hasMoreTokens())
                    {
                        String id = st.nextToken();
                        if (isValid(id, idportal))
                        {
                            values.add(id);
                        }
                    }
                }
            }
        }
        return values;
    }

    public boolean isValid(String id, String idportal)
    {
        WebSite site = WebSite.ClassMgr.getWebSite(idportal);
        if (site != null)
        {
            WebPage page = site.getWebPage(id);
            if (page != null && page.isValid())
            {
                return true;
            }
        }
        return false;
    }

    /*public void setCookie(HttpServletRequest request, HttpServletResponse response, String idportal)
    {
    Cookie[] cookies = request.getCookies();
    List<String> values = getIds(cookies, idportal);
    String valuecookie = "";
    for (String id : values)
    {
    valuecookie += id + ",";
    }
    if (valuecookie.endsWith(","))
    {
    valuecookie = valuecookie.substring(0, valuecookie.length() - 1);
    }
    Cookie cookie = null;
    boolean found = false;
    if (cookies != null)
    {
    for (Cookie c : cookies)
    {
    if (c.getName().equals(idportal))
    {
    cookie = c;
    found = true;
    break;
    }
    }
    }
    if (!found)
    {
    cookie = new Cookie(idportal, valuecookie);
    response.addCookie(cookie);
    }
    cookie.setMaxAge(60 * 60 * 24 * 365);
    cookie.setValue(valuecookie);
    response.addCookie(cookie);
    }*/
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String idportal = paramRequest.getWebPage().getWebSiteId();
        String path = "/models/" + idportal + "/jsp/FavoriteWebPageByCookie/";

        String base = SWBPortal.getWorkPath() + path;
        File dir = new File(base);
        if (!dir.exists())
        {
            path = "/swbadmin/jsp/FavoriteWebPageByCookie/";
        }
        else
        {
            path = "/work" + path;
        }
        if (request.getParameter("addthis") != null && request.getParameter("addthis").endsWith("true"))
        {

            String mode = paramRequest.getArgument("mode", "view");
            if (mode.equals("view"))
            {
                try
                {

                    RequestDispatcher dispatcher = request.getRequestDispatcher(path + "view.jsp");
                    request.setAttribute("paramRequest", paramRequest);
                    dispatcher.include(request, response);
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
            if (mode.equals("add"))
            {
                String _idportal = paramRequest.getWebPage().getWebSiteId();
                String _idpage = paramRequest.getWebPage().getId();
                addCookie(request, response, _idportal, _idpage);
                try
                {

                    RequestDispatcher dispatcher = request.getRequestDispatcher(path + "add.jsp");
                    request.setAttribute("paramRequest", paramRequest);
                    dispatcher.include(request, response);

                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
        }
        else
        {

            //setCookie(request, response, idportal);
            String mode = paramRequest.getArgument("mode", "view");
            if (mode.equals("view"))
            {
                try
                {

                    RequestDispatcher dispatcher = request.getRequestDispatcher(path + "view.jsp");
                    request.setAttribute("paramRequest", paramRequest);
                    dispatcher.include(request, response);


                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }

            if (mode.equals("add"))
            {
                try
                {

                    RequestDispatcher dispatcher = request.getRequestDispatcher(path + "add.jsp");
                    request.setAttribute("paramRequest", paramRequest);
                    dispatcher.include(request, response);
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }

        }
    }
}
