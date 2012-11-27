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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import javax.servlet.RequestDispatcher;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author juan.fernandez
 */
public class CummunityListResource extends GenericAdmResource {

    private static Logger log=SWBUtils.getLogger(CummunityListResource.class);

//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
//        PrintWriter out = response.getWriter();
//        WebPage wpp = paramsRequest.getWebPage();
//        WebPage wpch = null;
//        WebPage wpgs = null;
//
//        int numcomm = 0;
//        int nums = 0;
//        String nummsg = "";
//        User user = paramsRequest.getUser();
//        Iterator<WebPage> itwp = wpp.listChilds(user.getLanguage(), Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
//        while(itwp.hasNext())
//        {
//            wpch = itwp.next();
//            if(wpch.isActive()&&!wpch.isDeleted())
//            {
//                if(nums==0) out.println("<div class=\"groupInteres\">");
//                nums++;
//                out.println("<div class=\"groupInteres\">");
//                out.println("<h3 class=\"titulo\">"+wpch.getDisplayTitle(user.getLanguage())+"</h3>");
//                Iterator<WebPage> itwpch = wpch.listChilds(user.getLanguage(), Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
//                if(itwpch.hasNext())
//                {
//                    out.println("<ul>");
//                    while(itwpch.hasNext())
//                    {
//                        wpgs = itwpch.next();
//                        if(wpgs.isActive()&&!wpgs.isDeleted())
//                        {
//                            numcomm= getExistingCommunities(wpgs,user);
//                            nummsg = "";
//                            if(numcomm>0)  nummsg = "("+numcomm+")";
//                            out.println("<li><a href=\""+wpgs.getUrl()+"\">"+wpgs.getDisplayTitle(user.getLanguage())+"&nbsp;"+nummsg+"</a></li>");
//                        }
//                        numcomm=0;
//
//                    }
//
//                    out.println("</ul>");
//                    //out.println("<p class=\"vermas\"><a href=\""+wpch.getUrl()+"\">Ver todos</a></p>");
//                }
//                out.println("</div>");
//                if(nums==3)
//                {
//                    out.println("</div>");
//                    nums=0;
//                }
//            }
//
//        }
//        if(nums%3>0) out.println("</div>");
//    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String act=request.getParameter("act");
        if(act==null)act="view";
        String path="/swbadmin/jsp/microsite/CommunityListResource/commListView.jsp";
        RequestDispatcher dis=request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){log.error(e);}
    }

//    private int getExistingCommunities(WebPage wp, User user)
//    {
//        int numc=0;
//        Iterator<WebPage> itwp = wp.listChilds(user.getLanguage(), Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
//        while(itwp.hasNext())
//        {
//            WebPage wpit = itwp.next();
//            if(wpit.getSemanticObject().getGenericInstance() instanceof MicroSite)
//            {
//               numc++;
//            }
//        }
//        return numc;
//    }

}
