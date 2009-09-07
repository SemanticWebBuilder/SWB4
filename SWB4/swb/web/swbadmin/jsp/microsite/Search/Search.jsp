<%@page import="java.net.URLEncoder, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.text.SimpleDateFormat, org.semanticwb.portal.resources.sem.BookmarkEntry, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Iterator<String> results = (Iterator<String>) request.getAttribute("results");
    String searchUrl = (String) request.getAttribute("rUrl");
    SemanticProperty swbcomm_dirPhoto = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto");
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("separator", "-");
%>

<%
if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) {    
%>
    <div id="busqueda">
        <h2>B&uacute;squeda</h2>
        <div class="clear">&nbsp;</div>
        <form id="busqueda_form" action="<%=searchUrl%>" method="get">
            <p>
                <input id="busqueda_input" type="text" name="q"/>
                <input id="busqueda_enviar" type="submit"/>
            </p>
        </form>
    </div>
<%
} else if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {    
    if(results != null && results.hasNext()){
    %>
        <h3>Resultados de la b&uacute;squeda <i><%=request.getParameter("q")%></i></h3>
        <br>
        <p>
            Mostrando resultados <b>XX</b> al <b>XX</b> de <b>XX</b>.
        </p>
        <div class="entriesList">
        <%
            while(results.hasNext()) {
        %>
                <div class="listEntry" onmouseout="this.className='listEntry'" onmouseover="this.className='listEntryHover'">
                <%
                    String r = results.next();
                    SemanticObject obj = SemanticObject.createSemanticObject(r);
                    DirectoryObject c = (DirectoryObject) obj.createGenericInstance();
                    //Hotel c = (Hotel)obj.createGenericInstance();
                    String photo = obj.getProperty(swbcomm_dirPhoto);
                    if(photo != null && !photo.equals("null")) {
                    %>
                        <img height="90" width="90" src="<%=SWBPlatform.getWebWorkPath()+c.getDirectoryResource().getWorkPath()+"/"+obj.getId()+"/"+photo%>"/>
                    <%
                    } else {
                    %>
                        <img height="90" width="90" src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/noDisponible.gif"/>
                    <%
                    }
                    %>
                    <div class="listEntryInfo">
                        <p class="tituloNaranja"><a href ="<%=c.getWebPage().getUrl() + "?act=detail&uri=" + URLEncoder.encode(c.getURI())%>"><%=c.getTitle()%>&nbsp;(<%=obj.getSemanticClass().getDisplayName("es")%>)</a></p>
                        <p>
                            <%=c.getWebPage().getPath(map)%>
                        </p>
                        <p>
                            <%=(c.getDescription()==null)?"":c.getDescription()%>
                        </p>
                        <br/>
                        <!--p>-Palabras clave:%=c.getTags()%></p-->
                    </div>
                    <div class="clear"> </div>
                </div>
                <%
            }
        %>
        </div>
        <%
    } else {
    %>
        <h3>Resultados de la búsqueda <i><%=request.getParameter("q")%></i></h3>
        <br>
        <hr/>
        <p>
            No hay resultados.
        </p>
    <%
    }
}
%>