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
package org.semanticwb.opensocial.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class Proxy
{

    private static final Logger log = SWBUtils.getLogger(Proxy.class);

    private void writeContent(URL url, HttpServletResponse response) throws IOException
    {
        try
        {
            OutputStream out = response.getOutputStream();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            String content_type = con.getContentType();
            if (content_type != null)
            {
                response.setContentType(content_type);
            }
            InputStream in = con.getInputStream();
            byte[] buffer = new byte[1024 * 8];
            int read = in.read(buffer);
            while (read != -1)
            {
                out.write(buffer, 0, read);
                read = in.read(buffer);
            }
            response.setStatus(con.getResponseCode());
            in.close();
        }
        catch (FileNotFoundException e)
        {
            response.sendError(500);
            log.debug(e);
            throw e;
        }
        catch (IOException e)
        {
            response.sendError(500);
            log.debug("url :"+url);
            log.debug(e);
            throw e;
        }
        catch (Exception e)
        {
            log.debug("url :"+url);
            response.sendError(500);
            log.debug(e);            
        }
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        //log.debug("Proxy request.getQueryString(): " + request.getQueryString());
        String url = request.getParameter("url");
        if (url != null && !"".equals(url))
        {
            writeContent(new URL(url), response);
        }
    }
}
