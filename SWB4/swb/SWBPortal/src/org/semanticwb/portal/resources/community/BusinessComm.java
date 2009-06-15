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
                WebPage comunityContainer=WebPage.getWebPage(CommunityConfiguration.COMMUNITY_CONTAINER_ID, paramRequest.getTopic().getWebSite());
                String params="?modeswbcreatecommunity=true" +
                "&swbtp="+ paramRequest.getTopic().getId() +
                "&selecttype=o";

                out.println("<input name=\"acces\" type=\"button\" value=\"Crea tu comunidad\" onclick=\"window.location='"+comunityContainer.getUrl() + params+"';\">");
            }else{ //El usuario tiene comunidad de negocios, hay que presentarsela (presentar boton para que se vaya a su comunidad)
                WebPage page=paramRequest.getTopic().getWebSite().getWebPage(bcomm);
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