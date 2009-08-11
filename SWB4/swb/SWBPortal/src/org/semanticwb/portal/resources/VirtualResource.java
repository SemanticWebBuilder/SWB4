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
 


package org.semanticwb.portal.resources;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

import org.semanticwb.SWBPortal;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.api.SWBResourceException;

/** Esta clase muestra el contenido de cualquier recurso de WebBuilder, recibe el
 * identificador del recurso a desplegar.
 *
 * This class displays the content of any WebBuilder resource, receives the id
 * resource to show.
 * @author Javier Solis Gonzalez
 * @since : January 24th 2005, 19:27
 */
public class VirtualResource extends GenericAdmResource
{ 
    
    /** Creates a new instance of VirtualResource */
    public VirtualResource() {
    }
   
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        
        
        Resource base=getResourceBase();
        String tmid=base.getAttribute("tmid","0");
        String id=base.getAttribute("resid","0");
        //TODO quitar la siguiente línea
        //SWBResource res=SWBPortal.getResourceMgr().getResource( base.getURI());
        //TODO descomentar la siguiente línea
        SWBResource res=SWBPortal.getResourceMgr().getResource(tmid, id);
                
        if(res!=null) {
            //base=res.getResourceBase();
            // TODO agregar validación base.haveAccess(paramsRequest.getUser()) al siguiente if
            // TODO agregar validación SWBIntervalEvaluation.eval(new java.util.Date(),base) al siguiente if
            if(base.isActive() && !base.isDeleted() ) {
                ((SWBParamRequestImp)paramRequest).setResourceBase(base);
                ((SWBParamRequestImp)paramRequest).setVirtualResource(base);                
                res.render(request, response, paramRequest);                
            }
        }else {
            out.println(paramRequest.getLocaleString("notfound") + "...");
        }
    }
}
