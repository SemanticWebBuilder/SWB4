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
 * CountDownResource.java
 *
 * Created on 25 de julio de 2005, 01:47 PM
 */

package org.semanticwb.portal.resources;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import org.semanticwb.SWBPlatform;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBParamRequest;


/**
 *
 * @author Javier Solis Gonzalez
 */
public class CountDownResource extends GenericAdmResource
{
    
    /** Creates a new instance of CountDownResource */
    public CountDownResource()
    {
    }
    
    
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
        String width=getResourceBase().getAttribute("width","120");
        String height=getResourceBase().getAttribute("height","25");
        String date=getResourceBase().getAttribute("endtime","12/12/2012");
        String backgrownd=getResourceBase().getAttribute("backgrownd","FFFFFF");
        Date d=new Date(date);
        
        out.println("<APPLET code=\"applets/clocks/CountDown.class\" codebase=\""+SWBPlatform.getContextPath()+"/\" ARCHIVE=\"swbadmin/lib/SWBAplClock.jar, swbadmin/lib/SWBAplCommons.jar\" width="+width+" height="+height+">");
        out.println("<param name=\"actualTime\" value=\""+(new Date()).getTime()+"\">");
        out.println("<param name=\"endTime\" value=\""+d.getTime()+"\">");
        out.println("<param name=\"backgrownd\" value=\""+backgrownd+"\">");
        out.println("</APPLET>");
    }
    
}
