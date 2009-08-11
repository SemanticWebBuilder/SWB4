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
 
package org.semanticwb.portal.community;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.*;

public class Videos extends org.semanticwb.portal.community.base.VideosBase 
{

    public Videos()
    {
    }

    public Videos(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
//    {
//        User user=paramRequest.getUser();
//        WebPage page=paramRequest.getWebPage();
//        PrintWriter out=response.getWriter();
//        out.println("Hello Videos...");
//
//        //Para ver, editar, agragr, eliminar
//        int userLevel=CommUtils.getUerLevel(user,page);
//
//        Iterator<Video> it=Video.listVideosByWebPage(page.getWebSite(),page);
//        while(it.hasNext())
//        {
//            Video video=it.next();
//            int vis=video.getVisibility();
//            //if user puede ver un elemento de comunidad
//                out.println("Titulo:"+video.getTitle());
//                out.println("Descrip:"+video.getDescription());
//        }
//
//    }
//
//    public void add(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
//    {
//        WebPage page=paramRequest.getWebPage();
//
//        Video video=Video.createVideo(page.getWebSite());
//        video.setWebPage(page);
//        video.setVisibility(visibility);
//
//    }

}
