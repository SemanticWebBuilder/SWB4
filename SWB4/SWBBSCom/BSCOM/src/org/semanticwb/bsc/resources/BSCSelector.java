package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.bsc.BSC;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 * Permite al usuario seleccionar un Scorecard para visualizar en el navegador la informaci&oacute;n asociada.
 * @author Jose.Jimenez
 */
public class BSCSelector extends GenericResource {

    /**
     * Genera la interface para que el usuario seleccione el Scorecard deseado y se
     * muestre la nueva pesta&ntilde;a de navegador con la informaci&oacute;n del Scorecard seleccionado.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        StringBuilder html = new StringBuilder(254);
        //Obtiene el Scorecard actual
        BSC currentBsc = (BSC) paramRequest.getWebPage().getWebSite();
        //Obtiene el listado de instancias de Scorecards
        Iterator<BSC> todosBsc = BSC.ClassMgr.listBSCs();
        int bscCount = 0;
        final User user = paramRequest.getUser();
        
        if( user.isSigned() && todosBsc != null && todosBsc.hasNext()) {
            final String lang = user.getLanguage()==null?"es":user.getLanguage();
            StringBuilder aux = new StringBuilder(254);
            while (todosBsc.hasNext()) {
                BSC nextBsc = (BSC) todosBsc.next();
                if( nextBsc.isValid() && user.haveAccess(nextBsc) && user.getUserRepository()==nextBsc.getUserRepository() ) {
                    if(nextBsc == currentBsc) {
                        continue;
                    }
                    bscCount++;
                    aux.append(" <li><a href=\""+nextBsc.getHomePage().getUrl(lang)+"\" target=\""+nextBsc.getId()+"\" class=\"dropdown-menu-item\"");
                    aux.append(">");
                    aux.append(nextBsc.getTitle(lang)==null?nextBsc.getTitle():nextBsc.getTitle(lang));
                    aux.append("</a></li>\n");
		}
            } //while            
            html.append("  <li class=\"dropdown\">\n");
            html.append("    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><span class=\"hidden-xs\">");
            html.append(paramRequest.getLocaleString("lbl_title"));
            html.append(":&nbsp;</span>");
            html.append(currentBsc.getTitle(lang)==null?currentBsc.getTitle():currentBsc.getTitle(lang));
            if (bscCount > 0) {
                html.append("<span class=\"caret\"></span>");
            }
            html.append("</a>\n");
            if(!aux.toString().isEmpty()) {
                html.append("    <ul class=\"dropdown-menu\" role=\"menu\">\n");
                //se incluye la lista de scorecards encontrados
                html.append(aux);
                html.append("    </ul>\n");
            }
            html.append("  </li>\n");
            out.println(html.toString());
        } //if
    } //doView
    
}
