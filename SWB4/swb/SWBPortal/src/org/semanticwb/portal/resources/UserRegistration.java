/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import javax.security.auth.Subject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.GenericFormElement;
import org.semanticwb.model.PropertyGroup;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author serch
 */
public class UserRegistration extends GenericAdmResource
{

    static Logger log = SWBUtils.getLogger(UserRegistration.class);

    private FormElement getFormElement(SemanticProperty prop)
    {
        SemanticObject obj = prop.getDisplayProperty();
        FormElement ele = null;
        if (obj != null)
        {
            DisplayProperty disp = new DisplayProperty(obj);
            SemanticObject feobj = disp.getFormElement();
            if (feobj != null)
            {
                ele = (FormElement) feobj.createGenericInstance();
            }
        }
        //System.out.println("obj:"+obj+" prop:"+prop+" ele:"+ele);
        if (ele == null)
        {
            ele = new GenericFormElement();
        }
        return ele;
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {

        String jspfile = super.getResourceBase().getAttribute("viewJsp");
//        if (null == jspfile)
//        {
//            jspfile = "/resources/jsp/UserRegistration/view.jsp";
//        }
        UserRepository urep = paramsRequest.getTopic().getWebSite().getUserRepository();
//        User currUser = paramsRequest.getUser();
//        System.out.println("isRegistered:"+currUser.isRegistered());
//        System.out.println("isSigned:"+currUser.isSigned());
//        System.out.println("SOID:"+currUser.getSemanticObject().getId());
//        System.out.println("id:"+currUser.getId());
//        System.out.println("URI:"+currUser.getURI());
//        Iterator<String> itUserType = urep.getUserTypes();
        Iterator<SemanticProperty> itProps = urep.listBasicAttributes();
        HashMap<PropertyGroup, TreeSet> groups = new HashMap();
        while (itProps.hasNext())
        {
            boolean createGroup = false;
//            boolean addProp=false;
            SemanticProperty sp = itProps.next();
            if (!sp.getName().startsWith("usr") || null == sp.getDisplayProperty())
            {
                continue;
            }
            DisplayProperty dp = new DisplayProperty(sp.getDisplayProperty());
            TreeSet<SemanticProperty> props = groups.get(dp.getGroup());
            if (props == null)
            {
                props = new TreeSet(new Comparator()
                {

                    public int compare(Object o1, Object o2)
                    {
                        SemanticObject sobj1 = ((SemanticProperty) o1).getDisplayProperty();
                        SemanticObject sobj2 = ((SemanticProperty) o2).getDisplayProperty();
                        int v1 = 999999999;
                        int v2 = 999999999;
                        if (sobj1 != null)
                        {
                            v1 = new DisplayProperty(sobj1).getIndex();
                        }
                        if (sobj2 != null)
                        {
                            v2 = new DisplayProperty(sobj2).getIndex();
                        }
                        return v1 < v2 ? -1 : 1;
                    }
                });
                createGroup = true;
            }
            props.add(sp);
            if (createGroup)
            {
                groups.put(dp.getGroup(), props);
            }
//            System.out.println("DisplayInvalidMessage: " + dp.getDisplayInvalidMessage("es"));
//            System.out.println("DisplayPromptMessage:  " + dp.getDisplayPromptMessage("es"));
//            System.out.println("Group:                 " + dp.getGroup());
//            System.out.println("Index:                 " + dp.getIndex());
//            System.out.println("FormElement:           " + dp.getFormElement());
        }

        if (null != jspfile)
        {
            request.setAttribute("data", groups);
            request.setAttribute("paramRequest", paramsRequest);
            // System.out.println(currUser.getUsrFullName());
            RequestDispatcher dispatcher = request.getRequestDispatcher(jspfile);
            try
            {
                dispatcher.forward(request, response);
            //dispatcher.include(request, response);
            } catch (ServletException ex)
            {
                log.error("Can't include a jsp file " + jspfile, ex);
            }
        } else
        {
            PrintWriter writt = response.getWriter();
            writt.println("<form method=\"post\" action=\"" + paramsRequest.getActionUrl() + "\">");
            writt.println("    <input type=\"hidden\" name=\"type\" value=\"first\" />");

            String lang = paramsRequest.getUser().getLanguage();
            if (null != paramsRequest.getUser().getSemanticObject().getId())
            {
                writt.println("    <input type=\"hidden\" name=\"suri\" value=\"" +
                        paramsRequest.getUser().getSemanticObject().getId() +
                        "\" />");
            }
            try
            {

                Iterator<PropertyGroup> itgp = SWBComparator.sortSortableObject(groups.keySet().iterator());
                while (itgp.hasNext())
                {
                    PropertyGroup group = itgp.next();
                    String nombre = "Defecto";
                    if (null != group && null != group.getSemanticObject())
                    {
                        nombre = group.getSemanticObject().getDisplayName(lang);

                        writt.println("	<fieldset>");
                        writt.println("        <legend>" + nombre + "</legend>");
                        writt.println("	    <table>");

                        Iterator<SemanticProperty> it = groups.get(group).iterator();
                        while (it.hasNext())
                        {
                            SemanticProperty prop = it.next();
                            FormElement ele = getFormElement(prop);
                            System.out.println("Processing..."+prop.getName());
                            writt.println("                    <tr><td>" + ele.renderLabel(paramsRequest.getUser().getSemanticObject(), prop, null, null, lang) + "</td>");
                            writt.println("                    <td>" + ele.renderElement(paramsRequest.getUser().getSemanticObject(), prop, "web", "create", lang) + "</td></tr>");
                        }
                        writt.println("	    </table>");
                        writt.println("	</fieldset>");
                    }
                }
            } catch (Throwable es)
            {
                es.printStackTrace();
            }
            if (paramsRequest.getTopic().getWebSite().getUserRepository().getUserTypes().hasNext())
            {
                String tipoUsu = "Tipos de usuario";
                writt.println("    <fieldset>");
                writt.println("        <legend>" + tipoUsu + "</legend>");
                writt.println("	    <table>");

                Iterator itusrTypes = paramsRequest.getTopic().getWebSite().getUserRepository().getUserTypes();
                String labTU = "Tipo de usuario";
                writt.println("<tr><td><label>" + labTU + "</label></td><td>");
                writt.println("            <select name=\"wb_usr_type\" id=\"wb_usr_type\" multiple=\"yes\">");
                writt.println("                <option value=\"\"></option>");
                while (itusrTypes.hasNext())
                {
                    String tipo = (String) itusrTypes.next();
                    writt.print("            <option value=\"" + tipo + "\" ");
                    if (paramsRequest.getUser().isRegistered() && paramsRequest.getUser().hasUserType(tipo))
                    {
                        writt.print("selected=\"yes\"");
                    }
                    writt.print(">");
                    writt.print(paramsRequest.getTopic().getWebSite().getUserRepository().getUserType(tipo).getDisplayName(lang));
                    writt.println("</option>");
                }

                writt.println("</select></td></tr></table></fieldset>");
            } //else {System.out.println("Sin tipos");}

            writt.println("            <fieldset><span align=\"center\"><input type=\"submit\" value=\"Guardar\" /></span></fieldset>");
            writt.println("</form>");
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        if (null != paramsRequest.getUser().getSemanticObject().getId())
        {
            String jspfile = super.getResourceBase().getAttribute("extraJsp");
            UserRepository urep = paramsRequest.getTopic().getWebSite().getUserRepository();
            Iterator<SemanticProperty> itProps = urep.listExtendedAttributes();
            HashMap<String, SemanticProperty> datos = new HashMap<String, SemanticProperty>();
//            System.out.println("Extra...");
            while (itProps.hasNext())
            {
                SemanticProperty sp = itProps.next();
                if ( null == sp.getDisplayProperty())
                {
                    continue;
                }
                datos.put(sp.getName(), sp);
//                System.out.println("Adding: "+sp.getName());
            }
            Iterator<String> itur = urep.getUserTypes();
            while (itur.hasNext())
            {
                String curr = itur.next();
//                System.out.println("usuario: "+curr);
                if (paramsRequest.getUser().hasUserType(curr))
                {
//                    System.out.println("Procesando...");
                    itProps = urep.listAttributesofUserType(curr);
                    while (itProps.hasNext())
                    {
                        SemanticProperty sp = itProps.next();
//                        System.out.println("..."+sp.getName());
                        if (null == sp.getDisplayProperty())
                        {
                            continue;
                        }
                        datos.put(sp.getName(), sp);
//                        System.out.println("Adding: "+sp.getName());
                    }

                }
            }
            if (null != jspfile)
            {
                request.setAttribute("data", datos);
                request.setAttribute("paramRequest", paramsRequest);
                // System.out.println(currUser.getUsrFullName());
                RequestDispatcher dispatcher = request.getRequestDispatcher(jspfile);
                try
                {
                    dispatcher.forward(request, response);
                //dispatcher.include(request, response);
                } catch (ServletException ex)
                {
                    log.error("Can't include a jsp file " + jspfile, ex);
                }
            } else
            {
                PrintWriter writt = response.getWriter();
                writt.println("<form method=\"post\" action=\"" + paramsRequest.getActionUrl() + "\">");
                writt.println("    <input type=\"hidden\" name=\"type\" value=\"second\" />");

                String lang = paramsRequest.getUser().getLanguage();
                writt.println("    <input type=\"hidden\" name=\"suri\" value=\"" +
                        paramsRequest.getUser().getSemanticObject().getId() +
                        "\" />");
                writt.println("    <fieldset>");
                writt.println("        <legend>Atributos Adicionales</legend>");
                writt.println("	    <table>");
                Iterator<String> it = datos.keySet().iterator();
                while (it.hasNext())
                {
                    SemanticProperty prop = datos.get(it.next());
                    FormElement ele = getFormElement(prop);
                    writt.println("                    <tr><td>" + ele.renderLabel(paramsRequest.getUser().getSemanticObject(), prop, null, null, lang) + "</td>");
                    writt.println("                    <td>" + ele.renderElement(paramsRequest.getUser().getSemanticObject(), prop, "web", "create", lang) + "</td></tr>");
                }
                writt.println("	    </table>");
                writt.println("	</fieldset>");
                writt.println("            <fieldset><span align=\"center\"><input type=\"submit\" value=\"Guardar\" /></span></fieldset>");
                writt.println("</form>");
            }

        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        Enumeration enums = request.getParameterNames();
        User user = response.getUser();
        Subject subject = SWBPortal.getUserMgr().getSubject(request);
        UserRepository userRep = response.getTopic().getWebSite().getUserRepository();
        String usrLogin = request.getParameter("usrLogin");

        if ("first".equals(request.getParameter("type")))
        {
            if (null == usrLogin)
            {
                //TODO report Error
                return;
            }
//            System.out.println(">>>>>>>>>>"+user.getSemanticObject().getId());
//            System.out.println(">>>>>>>>>>"+user.getSemanticObject().getURI());
            if (null == user.getSemanticObject().getId())
            {
                if (null != userRep.getUserByLogin(usrLogin))
                {
                    //TODO report Error, Loging Already Exists!
                    return;
                }
                User newUser = userRep.createUser();
                newUser.setUsrLogin(usrLogin);
                subject.getPrincipals().clear();
                subject.getPrincipals().add(newUser);
                newUser.setLanguage(user.getLanguage());
                newUser.setIp(user.getIp());
                newUser.setActive(true);
                newUser.setDevice(user.getDevice());
//                System.out.println("*************** "+newUser.isActive());
                user = newUser;
//                System.out.println("*************** "+user.isActive());
            } else
            {
                if (!user.getUsrLogin().equalsIgnoreCase(usrLogin) && null != userRep.getUserByLogin(usrLogin))
                {
                    //TODO report Error, Loging Already Exists!
                    return;
                }
                if (!user.getUsrLogin().equalsIgnoreCase(usrLogin))
                {
                    user.setUsrLogin(usrLogin);
                }
            }
            Iterator<SemanticProperty> itprop = userRep.listBasicAttributes();
            SWBFormMgr fm = new SWBFormMgr(user.getSemanticObject(), "web", "update");
            while (itprop.hasNext())
            {
                SemanticProperty sp = itprop.next();
                if ("active".equals(sp.getName())) continue;
                SemanticObject dp = sp.getDisplayProperty();
//                System.out.println("Processing... " + sp.getDisplayName());
                fm.processElement(request, sp, "update");
            }
            ArrayList<String> utypessel = new ArrayList<String>();
            String[] lista = request.getParameterValues("wb_usr_type");
            if (null != lista)
            {
                for (String actual : lista)
                {
                    if (null == actual || "".equals(actual.trim()))
                    {
                        continue;
                    }
                    utypessel.add(actual);
                    if (!user.hasUserType(actual))
                    {
                        user.addUserType(actual);
                    }
                }
            }
            Iterator<String> itut = userRep.getUserTypes();
            while (itut.hasNext())
            {
                String acc = itut.next();
                if (user.hasUserType(acc) && !utypessel.contains(acc))
                {
                    user.removeUserType(acc);
                }
            }

//            System.out.println("responseClass:" + response.getClass().getName());
//            System.out.println("Es Igual: " + subject.getPrincipals().contains(user));
//
//            //response.getUser().getSemanticObject().
//            System.out.println("userSO:" + user.getSemanticObject());
//            while (enums.hasMoreElements())
//            {
//                String key = (String) enums.nextElement();
//                if ("usrLogin".equalsIgnoreCase(key)) continue;
//
//                System.out.println(key + ":" + request.getParameter(key));
//            }
            if ("1".equals(super.getResourceBase().getAttribute("target")))
            {
                response.setMode(SWBResourceURL.Mode_EDIT);
            }
        }
        if ("second".equals(request.getParameter("type")))
        {
            Iterator<SemanticProperty> itprop = userRep.listExtendedAttributes();
            SWBFormMgr fm = new SWBFormMgr(user.getSemanticObject(), "web", "update");
            while (itprop.hasNext())
            {
                SemanticProperty sp = itprop.next();
                SemanticObject dp = sp.getDisplayProperty();
//                System.out.println("Processing... " + sp.getDisplayName());
                fm.processElement(request, sp, "update");
            }
            Iterator<String> itur = userRep.getUserTypes();
            while (itur.hasNext())
            {
                String curr = itur.next();
                itprop = userRep.listAttributesofUserType(curr);
                while (itprop.hasNext())
                {
                    SemanticProperty sp = itprop.next();
                    SemanticObject dp = sp.getDisplayProperty();
//                    System.out.println("Processing... " + sp.getDisplayName());
                    fm.processElement(request, sp, "update");
                }
            }
            if (null != request.getParameter("destiny"))
            {
                response.sendRedirect(request.getParameter("destiny"));
            } else
            {
                response.setMode(SWBResourceURL.Mode_VIEW);
            }
        }
//        System.out.println("*************** "+user.isActive());
    }
}
