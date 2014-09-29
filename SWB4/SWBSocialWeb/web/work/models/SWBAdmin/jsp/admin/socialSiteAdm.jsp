<%-- 
    Document   : socialSiteAdm
    Created on : 25/09/2014, 03:27:18 PM
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.text.DecimalFormat"%> 
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<p>
<h1>Tipo de Licencia</h1>

</p>
<%!
     static DecimalFormat formater = new DecimalFormat("###,###,###,###"); 
%>
<%
    WebSite wsite=paramRequest.getWebPage().getWebSite();     
    SWBResourceURL url=paramRequest.getRenderUrl();
    String usrLang=paramRequest.getUser().getLanguage();
    if(request.getParameter("socialSite")==null)
    {
%>
    <h1>Marcas</h1>
    <ul>
<%
    Iterator<SocialSite> itSocialSites=SocialSite.ClassMgr.listSocialSites();
    while(itSocialSites.hasNext())
    {
        SocialSite socialSite=itSocialSites.next();
        String socialSiteDescr=socialSite.getDisplayDescription(usrLang);
        LicenseType licenseType=socialSite.getLicenseType();
        String slicenseType="Sin asignar";
        if(licenseType!=null) slicenseType=licenseType.getDisplayTitle(usrLang);
        
        %>
        <li>
            <a href="<%=url.setParameter("socialSite", socialSite.getId())%>"><%=socialSite.getDisplayTitle(usrLang)%></a>
            <%
                long dirSize = 0;
                File file=new java.io.File(SWBPortal.getWorkPath()+"/models/"+socialSite.getId()+"/");  
                if(file.exists() && file.isDirectory())
                {
                    dirSize=getDirSize(file, dirSize);
                }
            %>
            (<%=formater.format(dirSize/1024.0)%>)kbytes
            
            
            <p><%=socialSiteDescr!=null?socialSiteDescr:"SIN DESCRIPCIÓN"%></p>
            <p>Tipo de Licencia Asignada:<%=slicenseType%></p>
        </li>
        <%
    }    
%>
    </ul>
<%
    }else{
        SocialSite socialSite=SocialSite.ClassMgr.getSocialSite(request.getParameter("socialSite")); 
        if(socialSite!=null)
        {
            SWBResourceURL actionURL=paramRequest.getActionUrl();
            actionURL.setAction(SWBResourceURL.Action_EDIT);
            actionURL.setParameter("socialSite", socialSite.getId()); 
            String socialSiteDescr=socialSite.getDisplayDescription(usrLang);
            LicenseType licenseType=socialSite.getLicenseType();
            String slicenseType=""; 
            if(licenseType!=null) slicenseType=licenseType.getId();
            %>
            <form id="<%=socialSite.getId()%>/socialSiteAdm" class="swbform" method="post" action="<%=actionURL%>" method="post"> 
                <p><h1>Marca:</h1><%=socialSite.getDisplayTitle(usrLang)%></p>
                <p><h1>Descripción:</h1><%=socialSiteDescr!=null?socialSiteDescr:"SIN DESCRIPCIÓN"%></p>
                <p><h1>Tipo:</h1>
                    <select name="licenseType">
                        <%
                        Iterator<LicenseType> itLicenseType=LicenseType.ClassMgr.listLicenseTypes(wsite);
                        while(itLicenseType.hasNext())
                        {
                            LicenseType licenseTypeTmp=itLicenseType.next();
                         %>   
                            <option value="<%=licenseTypeTmp.getId()%>" <%=licenseType!=null&&licenseTypeTmp.getId().equals(licenseType.getId()) ?"selected":""%>><%=licenseTypeTmp.getId()%></option>
                         <% 
                        }
                        %>
                    </select>
                </p>
                <input type="submit" value="Guardar"/>
            </form>     
            <%
        }        
    }
%>
    <%!
        public long getDirSize(File dir, long dirSize){
            File[] listFiles = dir.listFiles();
            int dirLength = listFiles.length;
            for(int x = 0;x < dirLength;x ++){
                if(listFiles[x].isFile()){
                    dirSize += listFiles[x].length(); 
                }else{
                    dirSize=getDirSize(listFiles[x], dirSize);
                }
            }
            return dirSize;
        }
    %>