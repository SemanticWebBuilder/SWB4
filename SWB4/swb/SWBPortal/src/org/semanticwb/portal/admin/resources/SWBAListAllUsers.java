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

// TODO: Auto-generated Javadoc
/**
 * The Class SWBAListAllUsers.
 * 
 * @author juan.fernandez
 */
public class SWBAListAllUsers extends GenericResource {

    /**
     * Creates a new instance of SWBAListAllUsers.
     */
    public SWBAListAllUsers() {
    }

    /**
     * User view of the WBAListAllUsers resource.
     * 
     * @param request the input parameters
     * @param response the response to the request
     * @param paramRequest the param request
     * @throws IOException an exception of type IOException
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
    
        PrintWriter out = response.getWriter();
        Iterator<UserRepository> iteRep = SWBContext.listUserRepositories();//DBUser.getInstance().getRepositories();
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
