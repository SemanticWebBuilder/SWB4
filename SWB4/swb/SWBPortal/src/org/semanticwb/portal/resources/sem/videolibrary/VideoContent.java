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
package org.semanticwb.portal.resources.sem.videolibrary;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoContent.
 */
public class VideoContent extends org.semanticwb.portal.resources.sem.videolibrary.base.VideoContentBase
{

    /**
     * The Constant log.
     */
    private static final Logger log = SWBUtils.getLogger(VideoContent.class);

    /**
     * Instantiates a new video content.
     */
    public VideoContent()
    {
    }

    /**
     * Instantiates a new video content.
     *
     * @param base the base
     */
    public VideoContent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the preview.
     *
     * @return the preview
     */
    public String getPreview()
    {
        String ret = null;
        if (this.getCode() != null)
        {
            String code = SWBUtils.TEXT.decodeExtendedCharacters(this.getCode());
            code = code.replace("http://www.youtube.com/embed/", "http://www.youtube.com/v/");
            //******************  is YouTube  ***********************************
            String pre = "http://www.youtube.com/v/";
            String post = "\"";
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
                    a = ret.indexOf('?');
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
        }
        else
        {
            if (ret == null)
            {
                String google = this.getVideoWebPage();
                if (google != null)
                {
                    int pos = google.indexOf("v=");
                    if (pos != -1)
                    {
                        String id = google.substring(pos + 2);
                        int pos2 = id.indexOf("&", pos + 2);
                        if (pos2 != -1)
                        {
                            id = id.substring(0, pos);
                        }
                        ret = "http://i.ytimg.com/vi/" + id + "/default.jpg";
                    }

                }
            }
        }
        return ret;
    }

   

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/SWBVideoLibrary/showvideo.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("content", this);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return;
    }
}
