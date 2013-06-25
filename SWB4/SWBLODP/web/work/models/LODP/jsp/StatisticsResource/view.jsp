<%-- 
    Document   : view
    Created on : 3/06/2013, 12:34:14 PM
    Author     : Sabino
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Set"%>
<%@page import="com.infotec.lodp.swb.resources.UseAppBean"%>
<%@page import="com.infotec.lodp.swb.resources.UsersSatBean"%>
<%@page import="com.infotec.lodp.swb.resources.UseDSBean"%>
<%@page import="com.infotec.lodp.swb.resources.AplByDSBean"%>
<%@page import="com.infotec.lodp.swb.resources.DSByIntBean"%>
<%@page import="java.lang.reflect.Method"%>
<%@page import="java.lang.reflect.Field"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="com.infotec.lodp.swb.Comment"%>
<%@page import="com.infotec.lodp.swb.Application"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="com.infotec.lodp.swb.Institution"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.infotec.lodp.swb.resources.StatisticsResource"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
<%
    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    String statistic = request.getParameter("statistic");
    String urlSite = wpage.getUrl();
    String urlstatistic = urlSite+"?statistic=";
%>
<%  if(statistic==null){%>
    <div class="est-list">        
        <ul>
            <li>
                <a href="<%=urlstatistic+"1"%>">
                    <%=paramRequest.getLocaleString("lbl_tabla1")%>
                </a>
            </li>
            <li>
                <a href="<%=urlstatistic+"2"%>">
                    <%=paramRequest.getLocaleString("lbl_tabla2")%>
                </a>
            </li>
            <li>
                <a href="<%=urlstatistic+"3"%>">
                    <%=paramRequest.getLocaleString("lbl_tabla3")%>
                </a>
            </li>
            <li>
                <a href="<%=urlstatistic+"4"%>">
                    <%=paramRequest.getLocaleString("lbl_tabla4")%>
                </a>
            </li>
            <li>
                <a href="<%=urlstatistic+"5"%>">
                    <%=paramRequest.getLocaleString("lbl_tabla5")%>
                </a>
            </li>
        </ul>
    </div>        
<%  }else{
        List columns = StatisticsResource.getNameOfColumns(statistic, paramRequest); 
        String column = request.getParameter("column");        
        String asc = request.getParameter("asc");
        if(asc==null){asc="true";}
        if(column==null){column="1";}
        String classTable = "dsxinst";
        if(statistic.trim().equals("1")){classTable="dsxinst";}
        if(statistic.trim().equals("2")){classTable="dsxapp";}
        if(statistic.trim().equals("3")){classTable="usods";}
        if(statistic.trim().equals("4")){classTable="usoapp";}
        if(statistic.trim().equals("5")){classTable="califusr";}
%>
        
        <table class="estadisticas <%=classTable%>">
            <caption><%=columns.get(0)%></caption>
            <tr>
        <%for(int i=1; i<columns.size(); i++){%>                
                <th class="est-instit">                    
                    <span><%=columns.get(i)%></span>
                    <%String urlstatistic2 = urlstatistic+statistic+"&column="+i+"&asc=";%>
                    <%if(i==Integer.parseInt(column)){ 
                        if(asc.equals("true")){
                    %>                       
                        <a href="<%=urlstatistic2+"false"%>" title="Ordenar descendente" 
                           class="ico-descendente"><span>ordenar descendente</span></a>
                    <%  }
                        if(asc.equals("false")){
                    %>   
                        <a href="<%=urlstatistic2+"true"%>" title="Ordenar ascendente" 
                           class="ico-ascendente"><span>Ordenar ascendente</span></a>
                    <%  } 
                     }else{%>    
                        <a href="<%=urlstatistic2+"true"%>"  title="Ordenar ascendente">
                            <span>ordenar ascendente</span>
                        </a>
                    <%}%>
                    
                </th>
        <%}%> 
            </tr>
        <% 
        long count = 0;                
        if(statistic.trim().equals("1")){
            Set<DSByIntBean> list = StatisticsResource.getDSByIntBean(wsite,column,asc);            
            for(DSByIntBean dsbyint: list){ 
                count += dsbyint.getNumDS();
        %>
            <tr>
                <td class="est-instit"><%=dsbyint.getInstitution()%></td>
                <td class="est-datatot"><%=dsbyint.getNumDS()%></td>
            </tr>
        <%  }%>
        <tfoot><tr><td colspan="2"><%=paramRequest.getLocaleString("lbl_TotalDS")%> <%=count%>
         <%
         }
        if(statistic.trim().equals("2")){
            Set<AplByDSBean> list = StatisticsResource.getAplByDS(wsite,column,asc);            
            for(AplByDSBean aplbyds : list){ 
                count+= aplbyds.getNumApp();
        %>    
            <tr>
                <td class="est-data"><%=aplbyds.getDataset()%></td>
                <td class="est-instit"><%=aplbyds.getInstitution()%></td>
                <td class="est-apptot"><%=aplbyds.getNumApp()%></td>
            </tr>
        <% }%>
            <tfoot><tr><td colspan="3"><%=paramRequest.getLocaleString("lbl_TotalApp")%> <%=count%>
         <%
         }
        if(statistic.trim().equals("3")){
            Set<UseDSBean> list = StatisticsResource.getDSUse(wsite,column,asc);            
            for(UseDSBean dataset : list){
                count++;
        %> 
            <tr>                
                <td class="est-instit"><span><%=dataset.getInstitution()%></span></td>
                <td class="est-instdw"><span><%=dataset.getTotalHits()%></span></td>
                <td class="est-data"><span><%=dataset.getDataset()%></span></td>
                <td class="est-datavisit"><span><%=dataset.getHits()%></span></td>
                <td class="est-datavisitlst"><span><%=StatisticsResource.formatFecha(dataset.getLastDownload())%></span></td>
                <td class="est-datadw"><span><%=dataset.getViews()%></span></td>
                <td class="est-datadwnlst"><span><%=StatisticsResource.formatFecha(dataset.getLastView())%></span></td>
            </tr>
        <%  }%>
             <tfoot><tr><td colspan="7"><%=paramRequest.getLocaleString("lbl_TotalDS")%> <%=count%>
         <%}
         if(statistic.trim().equals("4")){
            Set<UseAppBean> list = StatisticsResource.getAppUse(wsite,column,asc);            
            for(UseAppBean appl : list){
                count++;
        %>
            <tr>
                <td class="est-instit"><span><%=appl.getInstitution()%></span></td>
                <td class="est-instdw"><span><%=appl.getTotalHits()%></span></td>                
                <td class="est-app"><span><%=appl.getApplication()%></span></td>
                <td class="est-appvisit"><span><%=appl.getHits()%></span></td>
                <td class="est-appvisitlst"><span><%=StatisticsResource.formatFecha(appl.getLastDownload())%></span></td>
                <td class="est-appdw"><span><%=appl.getViews()%></span></td>
                <td class="est-appdwnlst"><span><%=StatisticsResource.formatFecha(appl.getLastView())%></span></td>
            </tr>            
         <%  }%>
            <tfoot><tr><td colspan="7"><%=paramRequest.getLocaleString("lbl_TotalApp")%> <%=count%></span>
         <%}
         if(statistic.trim().equals("5")){
            if(asc==null){asc="true";}
            if(column==null){column="1";}            
            Set<UsersSatBean> list = StatisticsResource.getUsersSat(wsite,column,asc);             
            for(UsersSatBean userssat : list){
                count++;
        %>   
            <tr>                
                <td class="est-instit"><%=userssat.getInstitution()%></td>
                <td class="est-data"><%=userssat.getDataset()%></td>
                <%DecimalFormat df = new DecimalFormat("0.##");%>
                <td class="est-datacalif"><%=df.format(userssat.getAverage())%></td>                   
                <td class="est-datacomen"><%=userssat.getNumComments()%></td>                
            </tr>           
         <%  }%>
            <tfoot><tr><td colspan="4"><%=paramRequest.getLocaleString("lbl_TotalDS")%> <%=count%></span>
         <%}%>
       
        <%
         SWBResourceURL urlCSV = paramRequest.getRenderUrl();
         urlCSV.setCallMethod(SWBResourceURL.Call_DIRECT);
         urlCSV.setParameter("statistic",statistic);
         urlCSV.setParameter("column",column);
         urlCSV.setParameter("asc",asc);
         urlCSV.setMode(StatisticsResource.MODE_FILE);         
        %>
        
         <a class="ico-csv" href="<%=urlCSV.toString()%>">
             <%=paramRequest.getLocaleString("lbl_DowmloadCSV")%>
         </a>
         </td></tr></tfoot>
            </table> 
<%}%>
