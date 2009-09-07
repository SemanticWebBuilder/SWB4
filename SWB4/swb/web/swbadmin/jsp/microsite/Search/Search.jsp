<%@page import="java.net.URLEncoder, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.text.SimpleDateFormat, org.semanticwb.portal.resources.sem.BookmarkEntry, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Iterator<String> results = (Iterator<String>) request.getAttribute("results");
            String searchUrl = (String) request.getAttribute("rUrl");
            SemanticProperty swb_title = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
            SemanticProperty swb_description = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
            SemanticProperty swbcomm_dirPhoto = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto");
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
        <!--div>
            <input id="busqueda_comercios" type="checkbox" checked="checked" name="comercios"/>
            <label for="busqueda_comercios">Comercios</label>
            <input id="busqueda_organizaciones" type="checkbox" name="organizaciones"/>
            <label for="busqueda_organizaciones">Organizaciones</label>
            <input id="busqueda_persons" type="checkbox" name="personas"/>
            <label for="busqueda_persons">Personas</label>
            <input id="busqueda_clasificados" type="checkbox" name="clasificados"/>
            <label for="busqueda_clasificados">Clasificados</label>
        </div-->
    </form>
</div>
<%
} else if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {    
    //int start = (Integer)request.getAttribute("s");
    //System.out.println("start: "+ start);
    System.out.println("start: " + request.getAttribute("s") + ", end: " + request.getAttribute("e") + ", total: " + request.getAttribute("t"));
    if(results != null && results.hasNext()){
        %>
        <h3>Resultados de la b&uacute;squeda</h3>
        <div class="entriesList">
        <%
            while(results.hasNext()) {
            String r = results.next();
            SemanticObject obj = SemanticObject.createSemanticObject(r);            
            if (obj.instanceOf(Hotel.sclass)) {
                Hotel c = (Hotel)obj.createGenericInstance();
                //String photo = SWBPlatform.getWebWorkPath()+c.getDirectoryResource().getWorkPath()+"/"+obj.getId()+"/"+ obj.getProperty(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto"));
                //System.out.println("-----" + photo);
                %>
                <div class="listEntry" onmouseout="this.className='listEntry'" onmouseover="this.className='listEntryHover'">
                    <%
                    String photo = obj.getProperty(swbcomm_dirPhoto);
                    if(photo != null && !photo.equals("null")) {
                    %>
                        <img height="90" width="90" src="<%=SWBPlatform.getWebWorkPath()+c.getDirectoryResource().getWorkPath()+"/"+obj.getId()+"/"+photo%>"/>
                    <%
                    }
                    %>
                    <div class="listEntryInfo">
                        <p class="tituloNaranja"><a href ="<%=c.getWebPage().getUrl() + "?act=detail&uri=" + URLEncoder.encode(c.getURI())%>"><%=c.getTitle()%>&nbsp;(Hotel)</a></p>
                        <p>
                            <%=(c.getDescription()==null)?"":c.getDescription()%>
                        </p>
                        <br/>
                        <p>-Palabras clave:<%=c.getTags()%></p>
                    </div>
                    <div class="clear"> </div>
                </div>
                <%
            }
            else if (obj.instanceOf(Commerce.sclass)) {
                Commerce c = (Commerce)obj.createGenericInstance();
                %>
                <div class="listEntry" onmouseout="this.className='listEntry'" onmouseover="this.className='listEntryHover'">
                    <%
                    String photo = obj.getProperty(swbcomm_dirPhoto);
                    if(photo != null && !photo.equals("null")) {
                    %>
                        <img height="90" width="90" src="<%=SWBPlatform.getWebWorkPath()+c.getDirectoryResource().getWorkPath()+"/"+obj.getId()+"/"+photo%>"/>
                    <%
                    }
                    %>
                    <div class="listEntryInfo">
                        <p class="tituloNaranja"><a href ="<%=c.getWebPage().getUrl() + "?act=detail&uri=" + URLEncoder.encode(c.getURI())%>"><%=c.getTitle()%>&nbsp;(Comercio)</a></p>
                        <p>
                            <%=(c.getDescription()==null)?"":c.getDescription()%>
                        </p>
                        <br/>
                        <p>-Palabras clave:<%=c.getTags()%></p>
                    </div>
                    <div class="clear"> </div>
                </div>
                <%
            }
        }
        %>
        </div>
        <%        
    }
}
%>