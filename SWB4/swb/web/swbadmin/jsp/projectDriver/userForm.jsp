<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.*,java.text.*,org.semanticwb.portal.resources.projectdriver.*,java.io.PrintWriter,org.semanticwb.model.*,org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.SWBFormMgr,org.semanticwb.portal.SWBFormButton" %>
<%
        User user=paramRequest.getUser();
        UserWebPage userwp = (UserWebPage)paramRequest.getWebPage();
        Iterator<UserWebPage> it = UserWebPage.ClassMgr.listUserWebPageByParent(userwp, userwp.getWebSite());
        SWBResourceURL url=paramRequest.getActionUrl();
        String speciality = paramRequest.getLocaleString("msgUnassigned");//"Sin Asignar";
        if(userwp.getSpeciality()!=null)
            speciality = userwp.getSpeciality();
        boolean uservalid = false;
        if(userwp.getURI()!=null && user.getURI()!=null && userwp.getUserWP()!=null){
           if(user.getURI().equals(userwp.getUserWP().getURI()))
               uservalid = true;
        }
%><script type="text/javascript">
    function hideDiv(objDIV) {
        document.getElementById(objDIV).style.visibility = 'hidden';
    }
    function showDiv(objDIV) {
        document.getElementById(objDIV).style.visibility = 'visible';
    }
</script>
<style type="text/css">
#proyecto .barraProgreso{
   width:82%;
   float:left;
   background-color : #EFEFEF;
   padding: 0px;
   border: 2px outset;
   border-top: 1px solid #242424;
   border-right: 1px solid #DDDDDD;
   border-bottom: 1px solid #DDDDDD;
   border-left: 1px solid #242424;
   height: 20px;
}
#proyecto .tag_porcentaje{
   float:right;
   background-color:#FFFFFF;
   padding: 1px;
   width:12%;
}
#proyecto .contenedor {width: 95%; height: 25px;
   background-color: #FFFFFF;
   padding: 4px;
}
#proyecto .porcentaje{
    width:98%;
    float:left;
    height:20px;
    background-color: #EFEFEF;
    position: relative;
}
#proyecto .defaultPorcentaje{
    width:2%;
    float:left;
    height:20px;
    background-color: #0099FF;
}
#proyecto .estatusBarra{
   border: 1px none #000000;
   visibility:hidden;
   background-color:#008040;
   margin-left:10px;
   margin-top:4px;
   color:#FFFFFF;
   font-weight: bold;
   text-indent: 15px;
   position: absolute;
   left: 10px;
}
#proyecto .porcentajeAvance {
   float:left;
   height: 20px;
   background-color: #0099FF;
}
#proyecto .espa{
    width:95%;
    float:right;
    height:38px;
}
#proyecto .liespa{
    padding-top: 10px;
    width:100%;

}
.indentation{
    padding-left:10px;
}
#proyecto .datosUsuario{
    width: 100%;
    padding:4px;
    height:35px;
}
#proyecto .datosUsuIzq{
    width:19%;
    float: left;
    padding-left:11px;
    padding-bottom: 4px;
}
#proyecto .datosUsuDer{
    width:77%;
    float: right;
    padding-left:3px;
    padding-bottom: 4px;
}
#proyecto .avanceUsuario{
    width: 100%;
    padding:4px;
    height:30px;
}
#proyecto .datosUsuDer .defaultPorcentaje{
    width:2%;
    float:left;
    height:20px;
    background-color: #006BD7;
}
#proyecto .datosUsuDer .porcentajeAvance{
   float: left;
   height: 20px;
   background-color: #006BD7;
}
#proyecto .datosUsuDer .estatusBarra{
   border: 1px none #000000;
   visibility:hidden;
   background-color:#008040;
   margin-left:10px;
   margin-top:4px;
   color:#FFFFFF;
   font-weight: bold;
   text-indent: 15px;
   position: absolute;
   left: 10px;
}

</style>
<div id="proyecto">
<%
    if(!it.hasNext()){
        ArrayList listAct = new ArrayList();
        ArrayList assig=new ArrayList();
        ArrayList canc=new ArrayList();
        ArrayList devel=new ArrayList();
        ArrayList paus=new ArrayList();
        ArrayList end =new ArrayList();

        if(userwp.getUserWP()!=null){
            WebPage project = getProject(userwp);
            Iterator ita=Activity.ClassMgr.listActivityByResponsible(userwp.getUserWP(),userwp.getWebSite());
            while(ita.hasNext()){
                WebPage acts= (WebPage)ita.next();
                boolean valid=validaPage(acts,user.getLanguage());
                if(valid&&acts.isChildof(project))
                    listAct.add(acts);
            }
            ita = listAct.iterator();
            listAct=new ArrayList();
            while(ita.hasNext()){
                Activity act=(Activity)ita.next();
                listAct.add(act.getCurrentPercentage());
                listAct.add(act.getPlannedHour());
                if(act.getStatus().equals("assigned"))
                    assig.add(act.getURI());
                else if(act.getStatus().equals("canceled"))
                    canc.add(act.getURI());
                else if(act.getStatus().equals("develop"))
                    devel.add(act.getURI());
                else if(act.getStatus().equals("paused"))
                    paus.add(act.getURI());
                else if(act.getStatus().equals("ended"))
                    end.add(act.getURI());
            }
        }
        String avanTot=getProgressBar(listAct,paramRequest.getLocaleString("msgTotalHours"));
        if(avanTot==null)
          avanTot=paramRequest.getLocaleString("msgNoProgress");//"Sin Avance";
        url.setAction("upduser");
        url.setParameter("uri", userwp.getURI());
        %>
                    <form id="frmUser" name="frmUser" action="<%=url.toString()%>" method ="post">
                        <div class="datosUsuario">
                            <div class="datosUsuIzq"><label for="<%=userwp.swbproy_userWP.getName()%>"><%=userwp.swbproy_userWP.getDisplayName(user.getLanguage())%> </label></div>
                            <div class="datosUsuDer"><%=userwp.getUserWP()!=null?userwp.getUserWP().getFullName():paramRequest.getLocaleString("msgUnassigned")%></div>
                            <div class="datosUsuIzq"><label for="<%=userwp.swbproy_speciality.getName()%>"><%=userwp.swbproy_speciality.getDisplayName(user.getLanguage())%> </label></div>
                            <%if(uservalid){
                            %><div class="datosUsuDer"><input id="<%=userwp.swbproy_speciality.getName()%>" name="<%=userwp.swbproy_speciality.getName()%>" value="<%=userwp.getSpeciality()!=null?userwp.getSpeciality():""%>" style="width:200px" ></div><%}else{%>
                            <div class="datosUsuDer"><%=speciality%></div><%}%>
                        </div>
                        <div class="avanceUsuario">
                            <div class="datosUsuIzq"><%=paramRequest.getLocaleString("labelTotalProgress")%>:</div>
                            <div class="datosUsuDer"> <%=avanTot%></div>
                        </div>
                    </form>
        <%
        if(!assig.isEmpty()){
        %>
        <h3><%=paramRequest.getLocaleString("titleNoBeginActivities")%></h3>
        <%out.println(printStatusActivity(assig,paramRequest.getLocaleString("msgTotalHours")));
        }
        if(!devel.isEmpty()){
        %>
        <h3><%=paramRequest.getLocaleString("titleDevelopmentActivities")%></h3>
        <%out.println(printStatusActivity(devel,paramRequest.getLocaleString("msgTotalHours")));
        }
        if(!paus.isEmpty()){
        %>
        <h3><%=paramRequest.getLocaleString("titleInterruptedActivities")%></h3>
        <%out.println(printStatusActivity(paus,paramRequest.getLocaleString("msgTotalHours")));
        }
        if(!canc.isEmpty()){
        %>
        <h3><%=paramRequest.getLocaleString("titleCanceledActivities")%></h3>
        <%out.println(printStatusActivity(canc,paramRequest.getLocaleString("msgTotalHours")));
        }
        if(!end.isEmpty()){
        %>
        <h3><%=paramRequest.getLocaleString("titleCompletedActivities")%></h3>
        <%out.println(printStatusActivity(end,paramRequest.getLocaleString("msgTotalHours")));
        }
   }else{%>
                    <form id="frmUser" name="frmUser" action="<%=url.toString()%>" method ="post">
                        <div class="datosUsuario">
                            <div class="datosUsuIzq"><label for="<%=userwp.swbproy_userWP.getName()%>"><%=userwp.swbproy_userWP.getDisplayName(user.getLanguage())%> </label></div>
                            <div class="datosUsuDer"><%=userwp.getUserWP()!=null?userwp.getUserWP().getFullName():paramRequest.getLocaleString("msgUnassigned")%></div>
                            <div class="datosUsuIzq"><label for="<%=userwp.swbproy_speciality.getName()%>"><%=userwp.swbproy_speciality.getDisplayName(user.getLanguage())%> </label></div>
                            <%if(uservalid){
                            %><div class="datosUsuDer"><input id="<%=userwp.swbproy_speciality.getName()%>" name="<%=userwp.swbproy_speciality.getName()%>" value="<%=userwp.getSpeciality()!=null?userwp.getSpeciality():""%>" style="width:200px" ></div><%}else{%>
                            <div class="datosUsuDer"><%=speciality%></div>
                            <div class="datosUsuIzq"><%=paramRequest.getLocaleString("labelTotalProgress")%>:</div>
                            <div class="datosUsuDer"><%=paramRequest.getLocaleString("msgNoProgress")%>Sin Avance</div><%}%>
                        </div>
                    </form>
<%  }%>
</div>
<%!
        private boolean validaPage(WebPage wp, String lang){
             boolean valid=true;
             WebPage parent = wp.getParent();
             //Actividad sin papa desactivado
             while(parent!=null){
                 if(!parent.isActive())
                     valid=false;
                 parent=parent.getParent();
             }
             //Actividad con hijos activos
             if(wp.getChild()!=null){
                 Iterator itA = wp.listVisibleChilds(lang);
                 while(itA.hasNext()){
                     WebPage ch = (WebPage)itA.next();
                     if(ch.isActive())
                        valid = false;
                 }
             }
             //La misma Actividad
             if(!wp.isActive()|| wp==null || !wp.isVisible()||wp.isHidden() || !wp.isValid() || wp.isDeleted())
                 valid=false;
             return valid;
        }

        private WebPage getProject(WebPage wp){
           boolean p;p=false;
           WebPage parent=wp;
           while(!p){
                SemanticObject obj1= SemanticObject.createSemanticObject(wp.getURI());
                if(obj1.instanceOf(Project.sclass)){
                    parent=wp;p=true;}
                wp=wp.getParent();
            }
            return parent;
        }

        private String printStatusActivity(ArrayList listActs,String titleLan){
            StringBuffer buf = new StringBuffer();
            Iterator list = listActs.iterator();
            buf.append("");
            if(list.hasNext()){
                while(list.hasNext()){
                    ArrayList lprogress= new ArrayList();
                    String uri = (String) list.next();
                    SemanticObject ob = SemanticObject.createSemanticObject(uri);
                    Activity act = (Activity)ob.createGenericInstance();
                    lprogress.add(act.getCurrentPercentage());
                    lprogress.add(act.getPlannedHour());
                    buf.append("<div class=\"liespa\">");
                    buf.append("<span class=\"indentation\"><a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a></span>");
                    buf.append("<div class=\"espa\">"+getProgressBar(lprogress,titleLan)+"</div>");
                    buf.append("</div>");
                }
            }
            return buf.toString();
        }

        private String getProgressBar(ArrayList info,String titleLan)
        {
            String porcentaje = "", horas = "";
            float porcentajeTotal = 0, horasTotales = 0, horasParciales = 0;
            UUID uuid = UUID.randomUUID();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("###.##", dfs);
            StringBuffer ret = new StringBuffer();
            if(!info.isEmpty())
            {
                boolean bandera = true;
                Iterator it = info.iterator();
                while(it.hasNext())
                {
                    if(bandera) //nodo contiene porcentaje
                    {
                        porcentaje = String.valueOf(it.next());
                        horas = "";
                        bandera = false;
                    }
                    else //nodo contiene horas
                    {
                        horas = String.valueOf(it.next());
                        horasTotales += Float.valueOf(horas);
                        float dummy = Float.valueOf(porcentaje);
                        if(dummy > 1)
                            dummy /= 100;
                        horasParciales += dummy * Float.valueOf(horas);
                        porcentaje = "";
                        horas = "";
                        bandera = true;
                    }
                }
                porcentajeTotal = horasParciales / horasTotales * 100;
                if(Float.isNaN(porcentajeTotal))
                    porcentajeTotal=0;
                ret.append("        <div class=\"contenedor\">\n");
                ret.append("            <div class=\"barraProgreso\" onmouseover=\"javascript:showDiv('divStatusBar" + uuid + "'); return true;\" onmouseout=\"javascript:hideDiv('divStatusBar" + uuid + "'); return true;\">\n");
                ret.append("                 <div class=\"defaultPorcentaje\"></div>\n");
                ret.append("                 <div class=\"porcentaje\">\n");
                ret.append("                     <div class=\"estatusBarra\" id=\"divStatusBar" + uuid + "\" name=\"divStatusBar" + uuid + "\">"+titleLan+":<span class=\"text\">" + horasTotales + "</span>&nbsp;&nbsp;&nbsp;&nbsp;</div>\n");
                ret.append("                     <div class=\"porcentajeAvance\" style=\"width:"+df.format(porcentajeTotal)+"%\"></div>\n");
                ret.append("                 </div>\n");
                ret.append("            </div>\n");
                ret.append("            <div class=\"tag_porcentaje\">"+ df.format(porcentajeTotal) +"%</div>\n");
                ret.append("         </div>\n");
                return ret.toString();
            }
            else
                return null;
        }
    %>

