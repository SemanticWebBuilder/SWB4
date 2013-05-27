<%-- 
    Document   : detailPublisher
    Created on : 27/05/2013, 09:34:45 AM
    Author     : Lennin
--%>


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
           
String suri = request.getParameter("suri");
            GenericObject go = ont.getGenericObject(suri);
            long numDs = 0;
            long numPub = 0;
            long numViews = 0;
            long numAPPRelatedDS = 0;
            Institution inOb = (Institution)go;
            Iterator<Dataset> itds =  Dataset.ClassMgr.listDatasetByInstitution(inOb, wsite);
            
                while(itds.hasNext()){
                    
                    Dataset ds = itds.next();
                    numPub++;
                    numDs = numDs  + ds.getDownloads();
                    numViews = numViews + ds.getViews();
                    
                    Iterator<Application> itApsDS =  Application.ClassMgr.listApplicationByRelatedDataset(ds, wsite);
                    
                    while(itApsDS.hasNext()){
                        numAPPRelatedDS++;                       
                    }
                    
                }
 %> 
            
   <div>
       <img src="<%=SWBPortal.getWorkPath() + inOb.getWorkPath() + "/" +  inOb.getInstitutionLogo()%>" width="200" height="150" />
        <h2><%=inOb.getInstitutionTitle()%></h2> 
        <h3><%=inOb.getInstitutionDescription()%></h3>
        <ul>
        
            <li>
                <label><%=paramRequest.getLocaleString("lbl_dsPub")%></label>&nbsp;&nbsp;&nbsp;&nbsp;<%=numPub%>
            </li>
            <li>
                <label><%=paramRequest.getLocaleString("lbl_dsDescarga")%></label>&nbsp;&nbsp;&nbsp;&nbsp;<%=numDs%> 
            </li>
            <li>
                <label><%=paramRequest.getLocaleString("lbl_numViews")%></label>&nbsp;&nbsp;&nbsp;&nbsp;<%=numViews%>
            </li>
            <li>
                <label><%=paramRequest.getLocaleString("lbl_appRelated")%></label>&nbsp;&nbsp;&nbsp;&nbsp;<%=numAPPRelatedDS%>
            </li>
             
        </ul>
    </div>            
