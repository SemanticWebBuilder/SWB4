package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.lib.WBResponse;

/**
 *
 * @author Serch sergio.martinez@computer.org
 */
public class SWBACreateUser extends GenericResource {
    private Logger log = SWBUtils.getLogger(SWBACreateUser.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException
    {
        StringBuffer ret = new StringBuffer();
        SWBResourceURL url = paramRequest.getActionUrl();
        ret.append("<form id=\"http://www.semanticwebbuilder.org/swb4/ontology#User/create\" dojoType=\"dijit.form.Form\" class=\"swbform\"");
        ret.append("action=\""+url+"\" ");
        ret.append("onSubmit=\"submitForm('http://www.semanticwebbuilder.org/swb4/ontology#User/create');return false;\" method=\"POST\">");
        ret.append("\t<fieldset>\n\t<table>\n\t\t<tr>\n\t\t\t<td width=\"200px\" align=\"right\">\n\t\t\t\t<label>Repositorio de Usuarios</label>");
        ret.append("\n\t\t\t</td>\n\t\t\t<td>");
        Iterator<UserRepository> itur = SWBContext.listUserRepositorys();
        ret.append("\n\t\t\t\t<select dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" name=\"userRepository\" id=\"userRepository\" >");
        while (itur.hasNext())
        {
            UserRepository ur = itur.next();
            ret.append("\n\t\t\t\t\t<option value=\"" + ur.getId() + "\">" + ur.getTitle() + "</option>"); //todo Add Language
        }
        ret.append("\n\t\t\t\t</select>\n\t\t\t</td>\n\t\t</tr>");
        ret.append("\n\t\t<tr>\n\t\t\t<td width=\"200px\" align=\"right\">\n\t\t\t\t<label>Usuario <em>*</em></label>\n\t\t\t</td>\n\t\t\t<td>");
        ret.append("<input type=\"text\" name=\"login\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" " +
                "promptMessage=\"Captura identificador de usuario.\" invalidMessage=\"El identificador de usuario es requerido.\" trim=\"true\" />");
        ret.append("\n\t\t\t</td>\n\t\t</tr>");
        ret.append("\n\t\t<tr>\n\t\t\t<td width=\"200px\" align=\"right\">\n\t\t\t\t<label>Contrase&ntilde;a <em>*</em></label>\n\t\t\t</td>\n\t\t\t<td>");
        ret.append("<input type=\"password\" name=\"passwd\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" ");
        ret.append("promptMessage=\"Captura contrase&ntilde;a de usuario.\" invalidMessage=\"La contrase&ntilde;a de usuario es requerido.\" trim=\"true\" />");
        ret.append("\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n\t</fieldset>\n\t<fieldset>\n\t\t<span align=\"center\">");
        ret.append("\n\t\t\t<button dojoType='dijit.form.Button' type=\"submit\">Guardar</button>\n\t\t</span>\n\t</fieldset>\n</form>");
        response.getWriter().write(ret.toString());
    }



    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        //System.out.println("PA:UserCreate");
        String usrep = request.getParameter("userRepository");
        String login = request.getParameter("login");
        String password = request.getParameter("passwd");
        if (null==usrep||null==login||null==password) {
            response.setMode(SWBResourceURL.Mode_VIEW);
            return;
        }
        UserRepository ur = SWBContext.getUserRepository(usrep);
        //System.out.println("UR:"+ur);
        if (null!=ur.getUserByLogin(login)){
            response.setMode(SWBResourceURL.Mode_VIEW);
            return;
        }
        User user = ur.createUser();
        //System.out.println("UC:"+user);
        user.setUsrLogin(login);
        user.setUsrPassword(password);
        response.setMode(SWBResourceURL.Mode_EDIT);
        response.setRenderParameter("suri", user.getURI());
        response.setRenderParameter("login", user.getUsrLogin());
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        StringBuffer ret = new StringBuffer();
        ret.append("<script type=\"text/javascript\">\ndijit.byId('swbDialog').hide();\nreloadTreeNode();\nshowStatus('Usuario creado');\n");
        ret.append("addNewTab('"+request.getParameter("suri")+"','/swb/swbadmin/jsp/objectTab.jsp','"+request.getParameter("login")+"');");
        ret.append("</script>");
        response.getWriter().write(ret.toString());

    }


}
