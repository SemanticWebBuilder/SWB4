<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.forum.FrmCategory"%>
<%@page import="org.semanticwb.forum.FrmForum"%>

<%
        SWBResourceURL url = paramRequest.getRenderUrl();
        SWBResourceURL urlAction = paramRequest.getActionUrl();
        String lang=paramRequest.getUser().getLanguage();
%>
<table>
    <tr><td>
            <table>
                <tr><td>Tareas a realizar:</td></tr>
                <tr><td><a href="<%=url.setMode("addCategory").toString()%>">add new category</a></td></tr>
                <tr><td><a href="<%=url.setMode("addForum").toString()%>">add new forum</a></td></tr>
            </table>
    </td></tr>
    <tr><td>
        <%
        url.setMode("addForum");
        url.setAction("newCatForum");
        WebSite website = paramRequest.getTopic().getWebSite();
        %>
            <table border="1" width="95%" cellspacing="0" cellpadding="3" align="center">
                <tr bgcolor="#666669">
                    <td colspan="2"><font color="#FFFFFF">Nombre/Descripciónsddd</font></td>
                    <td align="center"><font color="#FFFFFF">Dueño</font></td>
                    <td align="center"><font color="#FFFFFF">Fecha de creación</font></td>
                    <td align="center"><font color="#FFFFFF">Tipo de Foro</font></td>
                    <td align="center"><font color="#FFFFFF">Modo de Moderación</font></td>
                    <td align="center"><font color="#FFFFFF">Agregar</font></td>
                    <td align="center"><font color="#FFFFFF">Editar</font></td>
                    <td align="center"><font color="#FFFFFF">Borrar</font></td>
                </tr>
                <%
                Iterator itCategories = FrmCategory.listFrmCategorys(website);
                while (itCategories.hasNext())
                {
                FrmCategory category = (FrmCategory) itCategories.next();
                %>
                    <tr bgcolor="#C0C0C0" font-size="12px" font-weight="normal" ine-height="20px">
                        <td colspan="2"><b><%=category.getName()%></b><br/><%=category.getDescription()%><br/></td>
                        <td align="center">
                         <%
                            if (category.getCreator() != null) {
                         %>
                            <%=category.getCreator().getId()%>
                         <%
                          } else {
                         %>
                            &nbsp
                         <%
                          }
                         %>
                        </td>
                        <td align="center"><%=category.getCreated()%></td>
                        <%
                            url.setParameter("caturi", category.getURI());
                        %>
                            <td align="center">&nbsp</td><td>&nbsp</td><td><a href="<%=url.toString()%>">Agregar nuevo foro</a></td>
                            <%urlAction.setParameter("categoryUri", category.getURI());
                              url.setParameter("categoryUri", category.getURI());
                            %>
                            <td align="center"><a href="<%=url.setMode("editCategory").toString()%>">Editar</a></td>
                            <td align="center"><a href="<%=urlAction.setAction("removeCategory").toString()%>">Borrar</a></td>
                    </tr>
                    <%
                        GenericIterator<FrmForum> gitForums = category.listForums();
                        while (gitForums.hasNext())
                        {
                            FrmForum forum = gitForums.next();
                    %>
                            <tr bgcolor="#FFFFFF" font-size="12px">
                                <td width="20" align="center" nowrap="nowrap">&nbsp</td>
                                <td width="30%"><b><%=forum.getTitle()%></b><br/>
                                <%if(forum.getDescription()!=null){%>
                                   <%=forum.getDescription()%><br/></td>
                                <%}%>
                                <td align="center">
                                <%
                                User creator=forum.getCreator();
                                if(creator!=null){
                                %>
                                <%=forum.getCreator().getId()%>
                                <%}else{%> &nbsp; <%}%>
                                </td>
                                <td align="center"><%=forum.getCreated()%></td>
                                <td align="center"><%=forum.getType().getTitle(lang)%></td>
                                <td align="center"><%=forum.getModerationMode().getTitle(lang)%></td>
                                <td align="center">&nbsp;</td>
                                <%urlAction.setParameter("forumUri", forum.getURI());
                                  url.setParameter("forumUri", forum.getURI());
                                %>
                                <td align="center"><a href="<%=url.setMode("editForum").toString()%>">Editar</a></td>
                                <td align="center"><a href="<%=urlAction.setAction("removeForum").toString()%>">Borrar</a></td>
                            </tr>
                    <%
                        }
                   }
                %>
            </table>
    </td></tr>
</table>
