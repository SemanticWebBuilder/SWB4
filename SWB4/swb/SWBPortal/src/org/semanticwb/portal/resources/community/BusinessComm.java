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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources.community;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author Jorge Jiménez
 */
public class BusinessComm extends GenericResource
{

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        User usr=paramRequest.getUser();
        if (usr != null && usr.isSigned()) {
            String bcomm=usr.getProperty("BusinessComm");
            if(bcomm==null || bcomm.trim().length()==0){ //El usuario esta registrado pero no tiene comunidad, puede crearla (presentar boton)
                WebPage comunityContainer=WebPage.getWebPage(CommunityConfiguration.COMMUNITY_CONTAINER_ID, paramRequest.getWebPage().getWebSite());
                String params="?modeswbcreatecommunity=true" +
                "&swbtp="+ paramRequest.getWebPage().getId() +
                "&selecttype=o";

                out.println("<input name=\"acces\" type=\"button\" value=\"Crea tu comunidad\" onclick=\"window.location='"+comunityContainer.getUrl() + params+"';\">");
            }else{ //El usuario tiene comunidad de negocios, hay que presentarsela (presentar boton para que se vaya a su comunidad)
                WebPage page=paramRequest.getWebPage().getWebSite().getWebPage(bcomm);
                if(page!=null){
                  out.println("<input name=\"acces\" type=\"button\" value=\"Accede a tu comunidad Ahora\" onclick=\"window.location='"+page.getUrl()+"';\">");
                }
            }
       }else { //Enviar a registrar
            out.println("¡Empieza a usar tu comunidad ya!");
            out.println("<br><br>");
            out.println("<input name=\"Regístrate\" type=\"button\" value=\"Regístrate ahora\" onclick=\"window.location='/swb/swb/Tlalpan/Registro_de_Usuarios';\">");
       }
    }

}