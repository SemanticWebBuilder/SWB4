<%@page import="org.semanticwb.Logger"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.text.DateFormatSymbols"%><%@page import="java.net.*"%><%@page import="org.semanticwb.model.*"%><%@page import="java.text.SimpleDateFormat"%><%@page import="java.text.DecimalFormat"%><%@page import="org.semanticwb.portal.api.SWBResourceURL"%><%@page import="org.semanticwb.portal.api.SWBParamRequest"%><%@page import="org.semanticwb.resources.sem.forumcat.*"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%><%@page import="org.semanticwb.SWBPortal"%><%@page import="org.semanticwb.portal.SWBFormButton"%><%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%><%@page import="java.text.SimpleDateFormat"%><%@page import="java.util.Locale"%><%@page import="java.util.Date"%>
<%@page import="java.util.HashMap"%><%@page import="java.util.ArrayList"%><%@page import="org.semanticwb.platform.SemanticClass"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            String url = paramRequest.getWebPage().getUrl();
%>
<p><strong><a href="<%=url%>">Página de inicio de los foros</a></strong></p>
<%
            User user = paramRequest.getUser();
            if (!user.isSigned())
            {
                return;
            }
            String titulo = request.getParameter("title");
            if (titulo != null && !titulo.trim().isEmpty())
            {
                WebSite site = paramRequest.getWebPage().getWebSite();
                String id = SWBUtils.TEXT.replaceSpecialCharacters(titulo, true);
                id = SWBUtils.TEXT.encodeBase64(id);
                Logger log = SWBUtils.getLogger(SWBForumCatResource.class);

                if (id.indexOf("%") != -1)
                {
                    id = id.replace('%', 'A');
                }
                if (id.indexOf("\r") != -1)
                {
                    id = id.replace('\r', 'A');
                }
                if (id.indexOf("\n") != -1)
                {
                    id = id.replace('\n', 'A');
                }
                if (id.indexOf("=") != -1)
                {
                    id = id.replace('=', 'O');
                }
                log.error("id: " + id);


                if (!WebPage.ClassMgr.hasWebPage(id, site))
                {
                    Resource base = paramRequest.getResourceBase();
                    SemanticObject semanticBase = base.getResourceData();
                    String pid = semanticBase.getProperty(SWBForumCatResource.forumCat_idCatPage, paramRequest.getWebPage().getId());
                    WebPage wpp = paramRequest.getWebPage().getWebSite().getWebPage(pid);
                    if (wpp != null)
                    {
                        WebPage newTema = WebPage.ClassMgr.createWebPage(id, site);
                        newTema.setCreator(user);
                        newTema.setActive(true);
                        String desc = request.getParameter("desc");
                        if (desc != null && !desc.trim().isEmpty())
                        {
                            newTema.setDescription(desc);
                        }
                        newTema.setTitle(titulo);
                        newTema.setParent(wpp);
%>
<p>El tema con título "<%=titulo%>" fue creada correctamente.</p>
<%
                    }
                    else
                    {
%>
<p>No se encontró la página de inicio de temas, favor de reportarlo con su admistrador de sitio.</p>
<%                                                    }


                }
                else
                {
%>
Ya existe un tema con ese nombre, intente otro título.
<%                                            }
            }
            else
            {
%>
<p>No se pudo dar de alta el tema, razon: titulo no especificado.
    <%                                }

    %>

