<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%><%@page import="org.semanticwb.*,org.semanticwb.model.*,org.semanticwb.platform.*,org.semanticwb.portal.*,java.util.*,org.semanticwb.base.util.*,com.hp.hpl.jena.ontology.*,com.hp.hpl.jena.rdf.model.*"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");

    User user = SWBContext.getAdminUser();
    if (user == null)
    {
        response.sendError(403);
        return;
    }
    String lang = user.getLanguage();
    String contx = SWBPortal.getContextPath();
    String userRep = request.getParameter("userRep");
    String users[] = request.getParameterValues("users");

    if (users == null)
    {
%>

<style type="text/css">
    #select, #select2 {
        width:255px;
        height:300px;
        overflow:auto;
    }
    div#sel1, div#sel2 {
        float: left;
    }
    div#leftRightButtons {
        float: left;
        padding: 10em 0.5em 0 0.5em;
    }
</style>

<form id="userFilter/form" dojoType="dijit.form.Form" class="swbform" action="<%=contx%>/swbadmin/jsp/userFilter.jsp"  onsubmit="submitForm('userFilter/form');
        return false;" method="post">
    <fieldset>
        <table>
            <tr>
                <td width="200px" align="right"><label for="userRep">Repositorio de Usuarios:</label></td>
                <td>
                    <select name="userRep" dojoType="dijit.form.FilteringSelect" onchange="submitForm('userFilter/form')" autoComplete="true" invalidMessage="Dato invalido." value="<%=userRep%>" >
                        <%
                            Iterator<UserRepository> it = SWBContext.listUserRepositories();
                            while (it.hasNext())
                            {
                                UserRepository ur = it.next();
                                String selected = "";
                                if (ur.getId().equals(userRep))
                                {
                                    selected = "selected=\"selected\"";
                                }
                        %>
                        <option value="<%=ur.getId()%>" <%=selected%>><%=ur.getDisplayTitle(lang)%></option>
                        <%
                            }
                        %>                        
                    </select>
                </td>
            </tr>
            <tr>
                <td width="200px" align="right">
                    <label>Usuarios:</label>
                </td>                
                <td> 
                    <div>
                        <div id="sel1" role="presentation">
                            <label for="allusers">Lista de Usuarios:</label><br>
                            <select multiple="true" dojoType="dijit.form.MultiSelect" id="select">
                                <%
                                    if (userRep != null)
                                    {
                                        Iterator<User> it2 = UserRepository.ClassMgr.getUserRepository(userRep).listUsers();
                                        while (it2.hasNext())
                                        {
                                            User ur = it2.next();
                                            String selected = "";
                                            if (ur.getId().equals(userRep))
                                            {
                                                selected = "selected=\"selected\"";
                                            }
                                %>
                                <option value="<%=ur.getId()%>" <%=selected%>><%=ur.getFullName()%></option>
                                <%
                                        }
                                    }
                                %>                                           
                            </select>
                        </div>
                        <div id="leftRightButtons" role="presentation">
                            <span>
                                <button class="switch" id="left" onclick="dijit.byId('select').addSelected(dijit.byId('select2'));
        return false;" title="Move Items to First list">&lt;</button>
                                <button class="switch" id="right" onclick="dijit.byId('select2').addSelected(dijit.byId('select'));
        return false;" title="Move Items to Second list">&gt;</button>
                            </span>
                        </div>
                        <div id="sel2" role="presentation">
                            <label for="users">Usuarios seleccionados:</label><br>
                            <select multiple="true" name="users" dojoType="dijit.form.MultiSelect" id="select2">
                            </select>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </fieldset>
    <fieldset>
        <span align="center">
            <button dojoType="dijit.form.Button" name="save" type="submit" value="save">Guardar</button>
            <button dojoType="dijit.form.Button" name="delete" type="submit" value="delete" onclick="if (!confirm('¿Eliminar el elemento?'))
            return false;">Eliminar</button>
        </span>
    </fieldset>
</form>
<%
    } else //Update
    {
        String maps = "";
        Iterator<WebSite> it = SWBContext.listWebSites();
        while (it.hasNext())
        {
            WebSite webSite = it.next();
            if (webSite.getUserRepository().equals(userRep))
            {
                maps += "|" + webSite.getId();
            }
        }
        String id=SWBUtils.TEXT.join("|", users);
        //System.out.println("id:"+id);
%>
<div class="applet">
    <applet id="editfilter" name="editfilter" code="applets.filterSection.FilterSection.class" codebase="/" archive="swbadmin/lib/SWBAplFilterSection.jar, swbadmin/lib/SWBAplCommons.jar" width="100%" height="500">
        <param name="jsess" value="<%=request.getSession().getId()%>">
        <param name="cgipath" value="/es/SWBAdmin/bh_userSectionFilter/_rid/121/_mto/3/_mod/gateway">
        <param name="locale" value="<%=lang%>">
        <param name="tm" value="<%=userRep%>">
        <param name="idresource" value="mv:<%=id%>">
        <param name="maps" value="<%=maps%>">
        <param name="isGlobalTM" value="true">
    </applet>
</div>
<%
    }
%>