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
        out.println("<div class=box>");
        out.println("<table width=100% cellpadding=10 cellspacing=0 border=0>");
        out.println("<tr class=tabla>");
        out.println("<td>"+"id"+"</td>");
        out.println("<td>"+"Nombre"+"</td>");
        out.println("<td>"+"A. Paterno"+"</td>");
        out.println("<td>"+"A. Materno"+"</td>");
        out.println("<td>"+"E-mail"+"</td>");
        out.println("<td>"+"Lenguaje"+"</td>");
        out.println("<td>"+"Activo"+"</td>");
        out.println("<td>"+"Creado"+"</td>");
        out.println("<td>"+"Modificado"+"</td>");
        out.println("<td>"+"Repositorio"+"</td></tr>");
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
                out.println("<td>"+rUser.getUsrFirstName()+"</td>");
                out.println("<td>"+rUser.getUsrLastName()+"</td>");
                out.println("<td>"+rUser.getUsrEmail()+"</td>");
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
        out.println("</div>");
    }
}
