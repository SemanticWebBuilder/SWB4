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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class LastMicrositeElements extends GenericAdmResource
{

    private static Logger log = SWBUtils.getLogger(LastMicrositeElements.class);
    private static final String NL = "\r\n";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        // long lastTime = System.currentTimeMillis();
        int limit = 3;
        String slimit = this.getResourceBase().getAttribute("limit", "3");
        try
        {
            limit = Integer.parseInt(slimit);
        }
        catch (NumberFormatException nfe)
        {
            log.error(nfe);

        }
        ArrayList<MicroSiteElement> elements = new ArrayList<MicroSiteElement>();

        WebSite site = paramRequest.getWebPage().getWebSite();


        Connection con = null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            Calendar today = Calendar.getInstance();
            today.setTime(new java.util.Date(System.currentTimeMillis()));
            Calendar dayago14 = Calendar.getInstance();
            dayago14.add(Calendar.DAY_OF_MONTH, -14);
            dayago14.set(Calendar.HOUR, 0);
            dayago14.set(Calendar.MINUTE, 0);
            dayago14.set(Calendar.SECOND, 0);

            PreparedStatement pt = con.prepareStatement("SELECT log_objuri FROM swb_admlog where log_action='create' and log_modelid=? and log_date BETWEEN ? AND ? order by log_date desc");
            pt.setString(1, site.getId());
            pt.setTimestamp(3, new java.sql.Timestamp(today.getTime().getTime()));
            pt.setTimestamp(2, new java.sql.Timestamp(dayago14.getTime().getTime()));
            ResultSet rs = pt.executeQuery();
            int i = 0;
            while (rs.next())
            {
                String uri = rs.getString("log_objuri");
                SemanticObject obj = SemanticObject.createSemanticObject(uri);
                if (obj != null && obj.getSemanticClass() != null && obj.getSemanticClass().isSubClass(MicroSiteElement.sclass))
                {
                    MicroSiteElement element=(MicroSiteElement) obj.createGenericInstance();
                    elements.add(element);
                    i++;
                    if (i == limit)
                    {
                        break;
                    }
                }
            }
            rs.close();
            pt.close();
        }
        catch (SQLException e)
        {
            log.error(e);
        }
        finally
        {
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
        }




        String path = "/swbadmin/jsp/microsite/LastMicrositeElements/LastMicrositeElementsView.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("elements", elements);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
//        System.out.println("procece vista:"+(System.currentTimeMillis()-lastTime));
//        lastTime = System.currentTimeMillis();


    }
}
