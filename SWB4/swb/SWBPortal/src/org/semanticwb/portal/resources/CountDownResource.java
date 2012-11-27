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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBParamRequest;

public class CountDownResource extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(CountDownResource.class);
    private static final long MILLISECS_PER_DAY = (1000*60*60*24);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        Resource base = getResourceBase();
        
//        int width;
//        try {
//            width = Integer.parseInt(base.getAttribute("width","120"));
//        }catch(NumberFormatException nfe) {
//            width = 120;
//        }
//        int height;
//        try {
//            height = Integer.parseInt(base.getAttribute("height","25"));
//        }catch(NumberFormatException nfe) {
//            height = 25;
//        }
//        String backgrownd = base.getAttribute("backgrownd","FFFFFF");       
        
        String date = base.getAttribute("endtime");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        GregorianCalendar latDate, curDate = new GregorianCalendar();
        try {
            Date dt = sdf.parse(date);
            latDate = new GregorianCalendar();
            latDate.setTime(dt);
        }catch(ParseException pe) {
            latDate = new GregorianCalendar();
        }
        long end = latDate.getTimeInMillis()+latDate.getTimeZone().getOffset(latDate.getTimeInMillis());
        long start = curDate.getTimeInMillis()+curDate.getTimeZone().getOffset(curDate.getTimeInMillis());
        long diff = end-start;
        long res = diff/MILLISECS_PER_DAY;
        
        if(diff%MILLISECS_PER_DAY>0)
           res += 1;
        
        PrintWriter out=response.getWriter();
        out.println("<div class=\"swb-cntdwn\">"+res+"</div>");
    }
}
