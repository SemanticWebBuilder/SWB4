/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author juan.fernandez
 */
public class SWBAListAllUsers extends GenericResource {

    /** Creates a new instance of SWBAListAllUsers */
    public SWBAListAllUsers() {
    }

    /** User view of the WBAListAllUsers resource
     * @param request the input parameters
     * @param response the response to the request
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException an exception of type AFException
     * @throws IOException an exception of type IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
    
        PrintWriter out = response.getWriter();
        Iterator<UserRepository> iteRep = SWBContext.listUserRepositorys();//DBUser.getInstance().getRepositories();
        out.println("<div class=\"swbform\">");
        out.println("<fieldset>");
        out.println("<table width=98% cellpadding=10 cellspacing=0 border=0>");
        out.println("<tr >");
        out.println("<th>"+"id"+"</th>");
        out.println("<th>"+"Nombre"+"</th>");
        out.println("<th>"+"A. Paterno"+"</th>");
        out.println("<th>"+"A. Materno"+"</th>");
        out.println("<th>"+"E-mail"+"</th>");
        out.println("<th>"+"Lenguaje"+"</th>");
        out.println("<th>"+"Activo"+"</th>");
        out.println("<th>"+"Creado"+"</th>");
        out.println("<th>"+"Modificado"+"</th>");
        out.println("<th>"+"Repositorio"+"</th></tr>");
        String rowColor="";
        boolean cambiaColor = true;
        while(iteRep.hasNext()){
            UserRepository repository = iteRep.next();

            Iterator<User> enuUsers = repository.listUsers();
            while(enuUsers.hasNext()){
                rowColor="#EFEDEC";
                if(!cambiaColor) rowColor="#FFFFFF";
                cambiaColor = !(cambiaColor);
                User rUser = enuUsers.next();
                out.println("<tr bgcolor=\""+rowColor+"\"  class=valores>");
                out.println("<td>"+rUser.getId()+"</td>");
                out.println("<td>"+rUser.getName()+"</td>");
                out.println("<td>"+rUser.getFirstName()+"</td>");
                out.println("<td>"+rUser.getLastName()+"</td>");
                out.println("<td>"+rUser.getEmail()+"</td>");
                out.println("<td>"+rUser.getLanguage()+"</td>");
                out.println("<td>"+rUser.isActive()+"</td>");
                //TODO: dateFormat()
                out.println("<td>"+rUser.getCreated()+"</td>");
                out.println("<td>"+rUser.getUpdated()+"</td>");
                out.println("<td>"+rUser.getUserRepository().getId()+"</td>");
                out.println("</tr>");
            }
        }
        out.println("</table>");
        out.println("</fieldset>");
        out.println("</div>");
    }
}
