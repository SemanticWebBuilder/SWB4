<%@page import="java.net.URLEncoder, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticClass, org.semanticwb.platform.SemanticProperty, java.text.SimpleDateFormat, org.semanticwb.portal.resources.sem.BookmarkEntry, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
    String lang = "es";
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Iterator<WebPage> childs = paramRequest.getWebPage().listChilds(lang, true, false, false, false, true);

    if (paramRequest.getUser() != null) {
        lang = paramRequest.getUser().getLanguage();
    }

    boolean showChilds = Boolean.valueOf(paramRequest.getArgument("showchilds", "true"));
    %>
    <div id="Accordion1" class="Accordion">
    <%
    if (showChilds) {
        while(childs.hasNext()) {
            WebPage p = childs.next();
            %>
                <div class="AccordionPanel">
                    <div class="AccordionPanelTab AccordionPanelClosed"><%=p.getTitle()%></div>
                    <div class="AccordionPanelContent">
                        <ul class="itemsCategoria">
                        <%
                            Iterator<WebPage> c = p.listChilds(lang, true, false, false, false, true);
                            while(c.hasNext()) {
                                WebPage wp = c.next();
                                %>
                                <li>
                                    <a href="<%=wp.getUrl()%>"><%=wp.getTitle()%></a>
                                </li>
                                <%
                            }
                        %>
                        </ul>
                    </div>
                </div>                
            <%
        }
    } else {
        while(childs.hasNext()) {
            WebPage p = childs.next();
            %>
                <div class="AccordionPanel AccordionPanelOpen">
                    <div class="AccordionPanelTab AccordionPanelClosed">
                        <a style="color:#D9D9B5; text-decoration:none" href="<%=p.getUrl()%>"><%=p.getTitle()%></a>
                    </div>
                </div>
            <%
        }
    }
%>
    </div>
<%
    if (showChilds) {
        %>
        <script type="text/javascript">
            var Accordion1 = new Spry.Widget.Accordion("Accordion1");
        </script>
    <%
    }
%>