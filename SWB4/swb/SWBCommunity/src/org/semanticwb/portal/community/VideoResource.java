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
package org.semanticwb.portal.community;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class VideoResource extends org.semanticwb.portal.community.base.VideoResourceBase
{

    private static Logger log = SWBUtils.getLogger(VideoResource.class);

    public VideoResource()
    {
    }

    public VideoResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String act = request.getParameter("act");
        if (act == null)
        {
            act = "view";
        }
        String path = "/swbadmin/jsp/microsite/VideoResource/videoView.jsp";
        if (act.equals("add"))
        {
            path = "/swbadmin/jsp/microsite/VideoResource/videoAdd.jsp";
        }
        if (act.equals("edit"))
        {
            path = "/swbadmin/jsp/microsite/VideoResource/videoEdit.jsp";
        }
        if (act.equals("detail"))
        {
            path = "/swbadmin/jsp/microsite/VideoResource/videoDetail.jsp";
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

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        WebPage page = response.getWebPage();
        Member mem = Member.getMember(response.getUser(), response.getWebPage());
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
        if ("remove".equals(action))
        {
            if (!isAdministrator)
            {
                if (!mem.canView())
                {
                    return;
                }
            }
        } else
        {
            if (!mem.canView())
            {
                return;                                       //si el usuario no pertenece a la red sale;
            }
        }

        System.out.println("act:" + action);
        if ("add".equals(action) && mem.canAdd())
        {
            String code = request.getParameter("video_code");
            VideoElement rec = VideoElement.ClassMgr.createVideoElement(getResourceBase().getWebSite());
            rec.setCode(code);
            rec.setPreview(getPreview(code));
            rec.setWebPage(page);
            //addVideoElement(rec);
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
            if (rec != null && rec.canModify(mem))
            {
                rec.setCode(request.getParameter("video_code"));
                rec.setTitle(SWBUtils.XML.replaceXMLChars(request.getParameter("video_title")));
                rec.setDescription(SWBUtils.XML.replaceXMLChars(request.getParameter("video_description")));
                rec.setTags(SWBUtils.XML.replaceXMLChars(request.getParameter("video_tags")));
                rec.setVisibility(Integer.parseInt(request.getParameter("level")));   //hace convercion a int en automatico
                if (page instanceof MicroSiteWebPageUtil)
                {
                    ((MicroSiteWebPageUtil) page).sendNotification(rec);
                }
            }
        } else if ("remove".equals(action))
        {

            String uri = request.getParameter("uri");
            VideoElement rec = (VideoElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

            if (rec != null && (rec.canModify(mem) || isAdministrator))
            {
                rec.remove();                                       //elimina el registro
            }
        } else
        {
            super.processAction(request, response);
        }
    }
}

