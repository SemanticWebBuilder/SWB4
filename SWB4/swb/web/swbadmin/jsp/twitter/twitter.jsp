<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.*"%>
<%@page import="twitter4j.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>


<%
        String imgPath = SWBPlatform.getContextPath() + "/swbadmin/images/";
        SWBResourceURL urlAction = paramRequest.getActionUrl();
        SWBResourceURL url = paramRequest.getRenderUrl();
        User owner = paramRequest.getUser();
        User user = owner;
        if (request.getParameter("user") != null) {
            SemanticObject semObj = SemanticObject.createSemanticObject(request.getParameter("user"));
            user = (User) semObj.createGenericInstance();
        }
        String data = null;
        Resource base = paramRequest.getResourceBase();
        String twitterConf = base.getAttribute("twitterConf", "1");
        if (twitterConf.equals("1")) {
            data = base.getData();
        }
        if (twitterConf.equals("2")) {
            data = base.getData(user);
        }
        if (twitterConf.equals("3")) {
            data = base.getData(paramRequest.getWebPage());
        }
        if (data != null) {
            int pos = data.indexOf("|");
            if (pos > -1) {
                String userLogin = data.substring(0, pos);
                String userPass = data.substring(pos + 1);
                if (paramRequest.getAction().equals("conf")) {
                    urlAction.setAction("saveUserData");
                    getForm(urlAction.toString(), out, userLogin, userPass);
                } else {
                    url.setAction("conf");
                    urlAction.setAction("send2Twitter");
                    try {
                        Twitter twitter = new Twitter(userLogin, userPass);
                        Iterator<Status> itStatuses = (twitter.getFriendsTimeline()).iterator();
                        %>
                        <a href="http://twitter.com" target="_new"><img src="<%=imgPath%>twitter_logo.png" valign="top"/></a>
                        <a href="<%=url.toString()%>">Configurar</a>
                        <form action="<%=urlAction.toString()%>">
                            <table>
                                <tr>
                                    <td>¿Que estas haciendo ahora?</td>
                                </tr>
                                <tr>
                                    <td><textarea name="status" cols="40" rows="3"></textarea></td>
                                </tr>
                                <tr>
                                    <td><input type="submit" value="enviar"></td>
                                </tr>
                            </table>
                        </form>
                        <%
                            int max = Integer.parseInt(base.getAttribute("noMsg", "" + 10));
                            int cont = 0;
                            while (itStatuses.hasNext()) {
                                Status twitt = itStatuses.next();
                                String timeAgo = SWBUtils.TEXT.getTimeAgo(twitt.getCreatedAt(), "es");
                                String poster = twitt.getUser().getScreenName();
                                imgPath = twitt.getUser().getProfileImageURL().toString();

                                String text = twitt.getText();
                                Iterator<String> itFounds = SWBUtils.TEXT.findInterStr(twitt.getText(), "@", " ");
                                while (itFounds.hasNext()) {
                                    String strFound = itFounds.next();
                                    text = text.replaceAll("@" + strFound, "<a href=\"" + twitter.getBaseURL() + strFound + "\" target=\"_new\">" + "@" + strFound + "</a>");
                                }
                        %>
                                <p class="addOn">
                                    <a href="<%=twitter.getBaseURL()%><%=poster%>" target="_new"><img src="<%=imgPath%>" valign="top" width="40" height="40"/> <%=poster%></a>  <%=text%><br><a href="<%=twitter.getBaseURL()%><%=poster%>/status/<%=twitt.getId()%>" target="_new"><%=timeAgo%></a><br/>
                                </p>
                        <%
                                cont++;
                                if (cont >= max) {
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            url.setAction("conf");
                        %>
                            No existe información para la cuenta de usuario proprcionada, o no se pudo autenticar el usuario en Twitter...
                            <br/><a href="<%=url.toString()%>">Configurar</a>
                        <%
                        }
                       }
                     }
                } else if (owner.getURI().equals(user.getURI())) { //Forma para que el usuario proporcione login y password de twitter
                    urlAction.setAction("saveUserData");
                    getForm(urlAction.toString(), out, "", "");
                } else {%>
                    <%=user.getFirstName()%><%=user.getLastName()%><br>
                    No tiene una cuenta de twitter configuarada en este portal
                    <%
                }
%>

<%!
    private void getForm(String formRedirect, javax.servlet.jsp.JspWriter out, String login, String password) {
        try {
            out.println("<form action=\"" + formRedirect + "\" method=\"post\">" +
                    "<table>" +
                    "<tr>" +
                    "<td colspan=\"2\" align=\"center\">Proporciona los datos de tu cuenta de twitter</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>Login:</td><td><input type=\"text\" name=\"twitterLogin\" size=\"25\" value=\"" + login + "\" /></td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>Password:</td><td><input type=\"password\" name=\"twitterPass\" size=\"25\" value=\"" + password + "\"/></td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td colspan=\"2\" align=\"center\"><input type=\"button\" value=\"regresar\" onClick=\"history.go(-1);\">          " +
                    "&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"submit\" value=\"enviar\"></td>" +
                    "</tr>" +
                    "</table>" +
                    "</form>");
        } catch (Exception e) {
        }
    }
%>
