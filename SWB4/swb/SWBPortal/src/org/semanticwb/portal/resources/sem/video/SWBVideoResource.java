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
package org.semanticwb.portal.resources.sem.video;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBVideoResource.
 */
public class SWBVideoResource extends org.semanticwb.portal.resources.sem.video.base.SWBVideoResourceBase 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBVideoResource.class);

    /**
     * Instantiates a new sWB video resource.
     */
    public SWBVideoResource()
    {
    }

    /**
     * Instantiates a new sWB video resource.
     * 
     * @param base the base
     */
    public SWBVideoResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String act = request.getParameter("act");
        if (act == null)
        {
            act = "view";
        }
        String path = "/swbadmin/jsp/SWBVideoResource/viewView.jsp";
        if (act.equals("detail"))
        {
            path = "/swbadmin/jsp/SWBVideoResource/viewDetail.jsp";
        }

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericSemResource#doAdmin(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("wpconfig");
        String idwp=getResourceBase().getAttribute("idwp","");
        out.println("<form action=\""+url+"\" method=\"post\">");
        out.println("<div>");
        out.println("<label for=\"idwp\">Id WebPage para mostrar lista de videos:</label><input type=\"text\" id=\"idwp\" name=\"idwp\" value=\""+idwp+"\">");
        out.println("<input type=\"submit\" value=\"Guardar\">");
        out.println("</div>");
        out.println("</form>");
        String act = request.getParameter("act");
        if (act == null)
        {
            act = "view";
        }
        String path = "/swbadmin/jsp/SWBVideoResource/adminView.jsp";
        if (act.equals("add"))
        {
            path = "/swbadmin/jsp/SWBVideoResource/adminAdd.jsp";
        }
        if (act.equals("edit"))
        {
            path = "/swbadmin/jsp/SWBVideoResource/adminEdit.jsp";
        }
        if (act.equals("detail"))
        {
            path = "/swbadmin/jsp/SWBVideoResource/adminDetail.jsp";
        }

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    /**
     * Gets the preview.
     * 
     * @param code the code
     * @return the preview
     */
    private String getPreview(String code)
    {
        String ret = null;
        //******************  is YouTube  ***********************************
        String pre = "http://www.youtube.com/v/";
        String post = "\">";
        int s = code.indexOf(pre);
        if (s >= 0)
        {
            int f = code.indexOf(post, s);
            if (f > s)
            {
                ret = code.substring(s + pre.length(), f);
                int a = ret.indexOf('&');
                if (a > 0)
                {
                    ret = ret.substring(0, a);
                }
            }
        }
        if (ret != null)
        {
            ret = "http://i.ytimg.com/vi/" + ret + "/default.jpg";
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericSemResource#processAction(HttpServletRequest, SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        WebPage page = response.getWebPage();

        User user = response.getUser();
        boolean isAdministrator = false;
        if (user != null)
        {
            GenericIterator<UserGroup> groups = user.listUserGroups();
            while (groups.hasNext())
            {
                UserGroup group = groups.next();
                if (group != null && group.getId().equals("admin"))
                {
                    isAdministrator = true;
                    break;
                }
            }
        }
        String action = request.getParameter("act");
        String resaction=response.getAction();
        String idwp = request.getParameter("idwp");
        if ("wpconfig".equals(resaction))
        {
            if(idwp!=null)
                getResourceBase().setAttribute("idwp", idwp);

            else
                getResourceBase().removeAttribute("idwp");
            try {
                getResourceBase().updateAttributesToDB();
            } catch (Exception e) {
            }

        }
        idwp=getResourceBase().getAttribute("idwp","");
        WebSite ws = getResourceBase().getWebSite();
        WebPage wpconfig = ws.getWebPage(idwp);

        System.out.println("act:" + action);
        if ("add".equals(action) )
        {

            String code = request.getParameter("video_code");
            VideoElement rec = VideoElement.ClassMgr.createVideoElement(getResourceBase().getWebSite());
            rec.setVideoCode(code);
            rec.setPreview(getPreview(code));
            if(wpconfig!=null)
                rec.setWebPage(wpconfig);

            //addVideoElement(rec);

            System.out.println("page..."+page.getURI());


            try
            {
                response.setRenderParameter("act", "edit");
                response.setRenderParameter("uri", rec.getURI());
            } catch (Exception e)
            {
                log.error(e);
                response.setRenderParameter("act", "add");               //regresa a agregar codigo
                response.setRenderParameter("err", "true");              //envia parametro de error
            }
        } else if ("edit".equals(action))
        {
            String uri = request.getParameter("uri");
            VideoElement rec = (VideoElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
            //if (rec != null && rec.canModify(user))
            {
                rec.setVideoCode(request.getParameter("video_code"));
                rec.setTitle(SWBUtils.XML.replaceXMLChars(request.getParameter("video_title")));
                rec.setDescription(SWBUtils.XML.replaceXMLChars(request.getParameter("video_description")));
                rec.setTags(SWBUtils.XML.replaceXMLChars(request.getParameter("video_tags")));
                //rec.setVisibility(Integer.parseInt(request.getParameter("level")));   //hace convercion a int en automatico

            }
        } else if ("remove".equals(action))
        {

            String uri = request.getParameter("uri");
            VideoElement rec = (VideoElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

            if (rec != null  || isAdministrator)
            {
                rec.remove();                                       //elimina el registro
            }
        } else
        {
            super.processAction(request, response);
        }
    }

}
