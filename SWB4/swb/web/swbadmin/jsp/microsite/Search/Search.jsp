<%@page import="java.net.URLEncoder, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.text.SimpleDateFormat, org.semanticwb.portal.resources.sem.BookmarkEntry, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SemanticProperty swbcomm_dirPhoto = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto");
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    ArrayList<String> results = (ArrayList<String>) request.getAttribute("results");
    String searchUrl = (String) request.getAttribute("rUrl");
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
    Iterator<String> it = results.iterator();
    if(results != null && it.hasNext()){
        int total = (Integer) request.getAttribute("t");
        int maxr = Integer.valueOf(paramRequest.getResourceBase().getAttribute("maxResults", "10"));
        int pageNumber = 1;
        
        if (request.getParameter("p") != null)
            pageNumber = Integer.valueOf(request.getParameter("p"));

        int start = (pageNumber - 1) * maxr;
        int end = start + maxr - 1;
        if (end > total - 1)
            end = total - 1;

        String sliceUrl = paramRequest.getRenderUrl().setMode("slice") + "?q=" + request.getParameter("q");

        System.out.println("....... "+maxr+", page: "+pageNumber+"total: "+total+", end: "+end);
    %>
        <h3>Resultados de la b&uacute;squeda <i><%=request.getParameter("q")%></i></h3>
        <br>
        <p>
            Mostrando resultados <b><%=start+1%></b> al <b><%=end+1%></b> de <b><%=total%></b>.
            <br>
            <%
            double pages = Math.ceil((double) total / (double) maxr);
                for (int i = 1; i <= pages; i++) {
                    start = maxr * (i - 1);
                    if ((start + maxr) - 1 > total - 1) {
                        end = total - 1;
                    } else {
                        end = (start + maxr) - 1;
                    }
                    if (pageNumber == i) {
                        %>
                        <span><%=i%></span>
                        <%
                    } else {
                        %>
                        <a href="<%=sliceUrl + "&p=" + i%>"><%=i%></a>
                        <%
                    }
                }
            %>
        </p>
        <div class="entriesList">
        <%
            while(it.hasNext()) {
        %>
                <div class="listEntry" onmouseout="this.className='listEntry'" onmouseover="this.className='listEntryHover'">
                <%
                    String r = it.next();
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
                        <p class="vermas"><a href ="<%=c.getWebPage().getUrl() + "?act=detail&uri=" + URLEncoder.encode(c.getURI())%>">Ver mas</a></p>
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

<%!
private ArrayList<String> getSlice (ArrayList<String> rSet, int page, int max) {
        ArrayList<String> pageData = new ArrayList<String>();
        int offset = (page - 1) * max;

        for (int i = 0; i < max; i++) {
            pageData.add(rSet.get(offset + i));
        }

        return pageData;
    }
%>