<%-- 
    Document   : showFastCalendar
    Created on : 15-nov-2013, 12:29:53
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<%@page import="java.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<ul class="showMoreNets">
<%
    if (request.getAttribute("postOut") == null) {
        return;
    }

    SemanticObject semObj = (SemanticObject) request.getAttribute("postOut");
    if (semObj == null) {
        return;
    }
    WebSite wsite = WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if (wsite == null) {
        return;
    }

    PostOut postOut = (PostOut) semObj.getGenericInstance();
    
    if(postOut.getFastCalendar()!=null)
    {
        FastCalendar fastCalendar=postOut.getFastCalendar();
        Date inidate=fastCalendar.getFc_date();
        //System.out.println("inidate:"+inidate);
        StringTokenizer st = new StringTokenizer(inidate.toString(), "-");
        String nf = inidate.toString();
        String y = "";
        String m = "";
        String d = "";
        if (st.hasMoreTokens()) {
            y = st.nextToken();
            if (st.hasMoreTokens()) {
                m = st.nextToken();
            }
            if (st.hasMoreTokens()) {
                d = st.nextToken();
                int pos=-1;
                pos=d.indexOf(" ");
                if(pos>-1)
                {
                    d=d.substring(0, pos);
                }
            }
            nf = y + "-" + m + "-" + d;
        }
        //System.out.println("nf:"+nf);
        //java.util.Calendar cal = java.util.Calendar.getInstance();  
        //cal.setTime(inidate);
        //System.out.println("inidate Year:"+inidate.getYear()+",inidate Month:"+inidate.getMonth()+",inidate Day:"+inidate.getDay()+",inidate Min:"+inidate.getMinutes());
        //System.out.println(cal.);
        SWBResourceURL urlAction = paramRequest.getActionUrl();
        urlAction.setParameter("postOut", postOut.getURI());
        %>
        <div class="msgFastCalendar">
            <p id="msgTitle">Programar env&iacute;o de mensaje</p>
            <form id="<%=postOut.getId()%>/uploadFastCalendarForm" dojoType="dijit.form.Form" class="swbform" method="post" action="<%=urlAction.setAction("uploadFastCalendar")%>" method="post" onsubmit="submitForm('<%=postOut.getId()%>/uploadFastCalendarForm'); return false;"> 
            <%
                String minutes="00";
                if(inidate.getMinutes()!=0) minutes=""+inidate.getMinutes(); 
                String hour=""+inidate.getHours();
                if(hour.length()==1) hour="0"+hour; 
                String starthour=hour+":"+minutes;
                //System.out.println("Final starthour:"+starthour);
            %>
                D&iacute;a:<input type="text" name="postOut_inidate" id="inidate" dojoType="dijit.form.DateTextBox"  size="11" style="width:110px;" hasDownArrow="true" value="<%=nf%>"> 
                Hora:<input dojoType="dijit.form.TimeTextBox" name="postOut_starthour" id="postOut_starthour_<%=starthour%>"  value="<%=(starthour!=null&&starthour.trim().length() > 0 ? "T"+starthour+":00" : "T00:00:00")%>" constraints=constraints={formatLength:'short',selector:'timeOnly',timePattern:'HH:mm'} />
                <p><button dojoType="dijit.form.Button" type="submit" ><%=paramRequest.getLocaleString("btnSend")%></button></p>
            </form>
        </div>      
       <%         
    }else{
        %>
            Ooopss!!, This PostOut doesn't have a Fast Calendar attached..
        <%
    }
%>
