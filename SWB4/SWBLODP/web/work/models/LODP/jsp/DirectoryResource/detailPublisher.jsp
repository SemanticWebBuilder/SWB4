<%-- 
    Document   : detailPublisher
    Created on : 27/05/2013, 09:34:45 AM
    Author     : Lennin
--%>


<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="com.infotec.lodp.swb.Institution"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="com.infotec.lodp.swb.Application"%>
<%@page import="java.util.Iterator"%>

<!DOCTYPE html>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />

<%
    
WebPage wpage = paramRequest.getWebPage();
WebSite wsite = wpage.getWebSite();
SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
String actionList = request.getParameter("actionList");
String value = request.getParameter("val");
SWBResourceURL urlConsulta = paramRequest.getRenderUrl();
SWBResourceURL modeURL = paramRequest.getRenderUrl();
           
String suri = request.getParameter("suri");
            GenericObject go = ont.getGenericObject(suri);
            long numDs = 0;
            long numPub = 0;
            long numViews = 0;
            long numAPPRelatedDS = 0;
            Institution inOb = (Institution)go;
            Iterator<Dataset> itds =  Dataset.ClassMgr.listDatasetByInstitution(inOb, wsite);
            List<Dataset> listaDS = new ArrayList();
            
                while(itds.hasNext()){
                    Dataset dslist = itds.next();
                    listaDS.add(dslist);
                }
                
                for(Dataset ds:listaDS){
                    
                    numPub++;
                    numDs = numDs  + ds.getDownloads();
                    numViews = numViews + ds.getViews();
                    
                    Iterator<Application> itApsDS =  Application.ClassMgr.listApplicationByRelatedDataset(ds, wsite);
                    
                    while(itApsDS.hasNext()){
                        numAPPRelatedDS++;                       
                    }
                    
                }
                
                String urlBase = "/work"+inOb.getWorkPath();
                String nameLogo= "institutionLogo_"+inOb.getId()+"_"+inOb.getInstitutionLogo();
                String urlLogo = urlBase + "/"+nameLogo;
                
                if(null!=suri){
                  urlConsulta.setParameter("suri", suri); 
                }
                
                if(actionList==null){
                    actionList = "";
                }
                
                
 %> 
            
   <div class="publicador">
       <img src="<%=urlLogo%>" width="200" height="150" />
        <h4><%=inOb.getInstitutionTitle()%></h4> 
        <p><%=inOb.getInstitutionDescription()!=null?inOb.getInstitutionDescription(): " "%></p>
        <ul>
            <%if(numPub>0){%>
           
            <li class="pub-publicado">
                <strong><a href="<%=urlConsulta.setParameter("actionList", "listaDatasets").toString()%>"><%=paramRequest.getLocaleString("lbl_dsPub")%></a></strong>
                <em><%=numPub%></em>
            </li>
            
            <%}else{%>
            
            <li class="pub-publicado">
                <strong><%=paramRequest.getLocaleString("lbl_dsPub")%></strong>
                <em><%=numPub%></em>
            </li>
            
            <%}%>
            
            <ul>
                
            <%
            
                if(actionList.equals("listaDatasets")){
               
                 if(!listaDS.isEmpty()){
                    for(Dataset dsOBJ : listaDS){
                      String urlData = wsite.getWebPage("Datos").getUrl();
                       String urlDataSet = urlData+"?suri="+dsOBJ.getShortURI()+"&act=detail";
            %>
            
                <li><a href="<%=urlDataSet%>"><%=dsOBJ.getDatasetTitle()%></a></li>
                
                <%}}else{%>
                    No hay datasets asociados al publicador
                <%}}%>
                
            </ul>
            <li class="pub-descargado">
                <strong><%=paramRequest.getLocaleString("lbl_dsDescarga")%></strong>
                <em><%=numDs%></em></li>
            </li>
            <li class="pub-visitas">
                <strong><%=paramRequest.getLocaleString("lbl_numViews")%></strong>
                <em><%=numViews%></em>
            </li>
            <li class="pub-appsrel">
                <strong><%=paramRequest.getLocaleString("lbl_appRelated")%></strong>
                <em><%=numAPPRelatedDS%></em>
            </li>
             
        </ul>
            <div class="regresar">
                <a href="#" onclick="javascript:document.back.submit()">Regresar</a>
            </div>
    </div>            
            
          <form action="<%=modeURL.setMode(SWBResourceURL.Mode_VIEW).setParameter("act", "arregloLetras").setParameter("val", value)%>" method="post" name="back"></form>
