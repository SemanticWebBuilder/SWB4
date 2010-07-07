    <jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
    <%@page import="java.util.*,org.semanticwb.portal.SWBFormMgr,java.io.PrintWriter,java.text.*,org.semanticwb.model.*,org.semanticwb.platform.*,org.semanticwb.portal.resources.projectdriver.*,org.semanticwb.portal.api.*,org.semanticwb.portal.*,java.sql.Timestamp"%>
    <%
            Iterator it,res,listobj;
            ArrayList proBar=new ArrayList();
            Activity act1;
            String lang="es";
            WebPage wp=paramRequest.getWebPage();
            User user=paramRequest.getUser();
            WebPage parent=getProject(wp.getParent());
            it=wp.listVisibleChilds(user.getLanguage());
            SemanticObject obj=SemanticObject.createSemanticObject(wp.getURI());
            Activity act = (Activity)obj.createGenericInstance();
            ArrayList responsible = listUserRepository(act.getWebSite());
            validAct(act);
            proBar.add(act.getCurrentPercentage());
            proBar.add(act.getPlannedHour());
            if(paramRequest.getUser()!=null)
              lang=paramRequest.getUser().getLanguage();
            boolean editAct = false;
            if(user.getURI()!=null&&act.getResponsible()!=null){
                if(user.getURI().equals(act.getResponsible().getURI())){
                    editAct = true;
                }
            }
            //String avanTot = "'Agrega horas planeadas'";
    %><script type="text/javascript">
                function hideDiv(objDIV) {
                    document.getElementById(objDIV).style.visibility = 'hidden';
                }
                function showDiv(objDIV) {
                    document.getElementById(objDIV).style.visibility = 'visible';
                }
                function validar(forma){
                    var val=true;
                    if(forma.status.value=='develop'){
                        if(forma.plannedHour.value==""||forma.plannedHour.value<=0){
                            alert('<%=paramRequest.getLocaleString("msgAddPlannedh")%>');
                            forma.plannedHour.focus();
                            val = false;
                        }
                        if(forma.currentHour.value==""){
                            alert('<%=paramRequest.getLocaleString("msgAddCurrenth")%>');
                            forma.currentHour.focus();
                            val=false;
                        }
                        if(forma.currentPercentage.value==""){
                            alert('<%=paramRequest.getLocaleString("msgAddPercentagep")%>');
                            forma.currentPercentage.focus();
                            val =false;
                        }
                    }else if(forma.status.value=='ended'||forma.status.value=='paused'){
                        if(forma.plannedHour.value==""||forma.plannedHour.value<=0){
                            alert('<%=paramRequest.getLocaleString("msgAddPlannedh")%>');
                            forma.plannedHour.focus();
                            val = false;
                        }
                        if(forma.currentHour.value==""||forma.currentHour.value<=0){
                            alert('<%=paramRequest.getLocaleString("msgAddCurrenth")%>');
                            forma.currentHour.focus();
                            val=false;
                        }
                        if(forma.currentPercentage.value==""||forma.currentPercentage.value<=0){
                            alert('<%=paramRequest.getLocaleString("msgAddPercentagep")%>');
                            forma.currentPercentage.focus();
                            val =false;
                        }
                        if(forma.status.value=='ended'){
                            forma.currentPercentage.value=100;
                        }
                    }
                    if(forma.plannedHour.value!=""&&isNaN(forma.plannedHour.value)==true){
                        alert('<%=paramRequest.getLocaleString("msgAddnumericPlannedh")%>');
                        val=false;
                    }
                    if(forma.currentHour.value!=""&&isNaN(forma.currentHour.value)==true){
                        alert('<%=paramRequest.getLocaleString("msgAddnumericCurrenth")%>');
                        val=false;
                    }
                    if(forma.currentPercentage.value!=""&&isNaN(forma.currentPercentage.value)==true){
                        alert('<%=paramRequest.getLocaleString("msgAddnumericCurrentp")%>');
                        val=false;
                    }
                    return val;
                }
                function calcular(forma){
                    var pH=forma.plannedHour.value;
                    var cH=forma.currentHour.value;
                    if(pH>0&&cH>0){
                        var res=(cH/pH)*100;
                        forma.currentPercentage.value=res.toFixed(2);
                    }
                }
</script>
<style type="text/css">
#proyecto .list .porcentajeAvance {
   width: 95%;
   float: left;
   height: 20px;
   background-color: #0099FF;
}
#proyecto .list .defaultPorcentaje{
    width:2%;
    float:left;
    height:20px;
    background-color: #0099FF;
}
#proyecto .list .activity{
   width:100%;
   float:left;
   padding: 2px;
}
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
#proyecto .contPorcentaje{
    float:right;
    width:92%;
}
#proyecto .espacio{
    float:right;
    width:97%;
}
#proyecto .list .estatusBarra{
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
#proyecto .list .text{
   color:#FFFFFF;
   font-weight: bold;
   font-style: italic;
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
#proyecto .list{
    width:99%;
}
#proyecto .porcentaje{
    width:98%;
    float:left;
    height:20px;
    background-color: #EFEFEF;
    position:relative;
}
#proyecto .datos .estatusBarra{
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
#proyecto .datos .etiquetas{
    text-align: left;
    float:left;
    width: 30%;
    padding-left:2%;
}
#proyecto .datos .porcentajeAvance {
   width: 95%;
   float: left;
   height: 20px;
   background-color: #006BD7;
}
#proyecto .datos .defaultPorcentaje{
    width:2%;
    float:left;
    height:20px;
    background-color: #006BD7;
}
#proyecto .barraDatos{
    width: 68%;
    float: left;
}
#proyecto .barraGeneral{
    width: 100%;
    float: left;
    padding-bottom: 6px;
    padding-top:6px;
    padding-left:20px;
    padding-right:6px;
    height: 30px;
}
#proyecto .datos .global{
    width:100%;
}
#proyecto .datos{
    padding-bottom:2%;
    padding-top: 2%;
}
#proyecto .datos .elementos{
    padding-bottom: 2%;
}
#proyecto .detalles{
    width: 100%;
    padding: 6px;
    height: 25px;
}
#proyecto .botones{
    padding:20px;
    width:80%;
}
#proyecto .btnIzq{
    width:60%;
    float: right;
}
#proyecto .btnDer{
    width:40%;
    text-align:center;
}
table.detail {
	font-size: 100%;
	color: black;
}
table.detail td {
	padding: 0.4em 0.5em 0.4em 0.5em;
}
table.detail th {
	padding: 0.4em 0.5em 0.4em 0.5em;
}
#proyecto .porcentajeAvance {
   float:left;
   height: 20px;
   background-color: #006BD7;
}
#proyecto .defaultPorcentaje{
    width:2%;
    float:left;
    height:20px;
    background-color: #006BD7;
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
</style>
    <div id="proyecto">
<%
          if(it.hasNext())
          {//Si tiene hijos muestra una lista de ellos
            HashMap webPage=new HashMap();
            while(it.hasNext()){
              WebPage page1=(WebPage)it.next();
              String namewp=page1.getSemanticObject().getSemanticClass().getName();
              if(namewp.equals("WebPage"))
              webPage.put(page1, page1);
            }
    %>
         <div class="datos">
            <div class="global">
                <div class="etiquetas"><%=paramRequest.getLocaleString("titleProject")%>: </div>
                <div class="elementos"><%=parent.getDisplayName()%></div>
            </div>
            <div class="global">
                <div class="etiquetas"><%=paramRequest.getLocaleString("labelActivityProgress")%>: </div>
                <div class="barraDatos"><%=getProgressBar(getListLeaf(act,user),paramRequest.getLocaleString("msgTotalHours"))%></div>
            </div>
         </div>
      <%
          out.println(getWebPageListLevel(act,user,4,paramRequest.getLocaleString("titleSubactivities"),"",paramRequest.getLocaleString("msgTotalHours")));
          if(!webPage.isEmpty())
            out.println(printPage(webPage,paramRequest.getLocaleString("titleSections")));
          }else{//Sino tiene hijos
            ArrayList labels = getLabelStatus(act,user);
            if(editAct){//Si esta registrado
              SWBFormMgr mgr = new SWBFormMgr(act.getSemanticObject(),null,SWBFormMgr.MODE_EDIT);
              mgr.setLang(lang);
              mgr.setType(mgr.TYPE_XHTML);
              SWBResourceURL url=paramRequest.getActionUrl();
              url.setParameter("uri", obj.getURI());
              url.setAction("update");
              mgr.addHiddenParameter(Activity.swb_active.getName(), Boolean.toString(act.isActive()));//dojoType="dijit.form.Form"
              boolean checkPrede=checkPredecesor(act);%>
        <fieldset><legend><%=paramRequest.getLocaleString("labelMonitoring")%></legend>
        <form id="<%=mgr.getFormName()%>" name="<%=mgr.getFormName()%>" class="edit" action="<%=url.toString()%>" method="post">
            <%=mgr.getFormHiddens()%>
            <input type="hidden" name="status_ini" value="<%=act.getStatus()%>">
            <table class="detail">
              <tbody>
              <tr><td width="200px"><%=mgr.renderLabel(request,act.swbproy_critical,mgr.MODE_EDIT)%></td>
                  <td><%
                  if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")||checkPrede){String checked="";
                      if(act.isCritical()){checked="checked=\"checked\"";}
                      out.println("<input type=\"checkbox\" id=\""+act.swbproy_critical.getName()+"\" name=\""+act.swbproy_critical.getName()+"\""+checked+"\" disabled=\"disabled\">");
                  }else
                       out.println(mgr.renderElement(request, act.swbproy_critical,mgr.MODE_EDIT));
    %>             </td>
              </tr>
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_actType, mgr.MODE_EDIT)%></td>
                  <td><%
                  if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")||checkPrede){
                     String value=act.getActType();
                     if(value==null)value="";
                     out.println("<input id=\""+act.swbproy_actType.getName()+"\" name=\""+act.swbproy_actType.getName()+"\" value=\""+value+"\" style=\"width:300px\" disabled=\"disabled\">");
                  }else
                   out.println(mgr.renderElement(request, act.swbproy_actType,mgr.MODE_EDIT));
    %>             </td>
              </tr>
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_status, mgr.MODE_EDIT)%></td><td><select name="<%=Activity.swbproy_status.getName()%>"<%if(checkPrede){%>disabled<%}%>>
                     <%boolean un,as,ca,de,pa,en; un=as=ca=de=pa=en=false;
                        String value1=act.getStatus().toString();
                        if(value1.equals("unassigned")) un=ca=as=true;
                        else if(value1.equals("assigned")) as=de=ca=true;
                        else if(value1.equals("develop")) de=pa=en=ca=true;
                        else if(value1.equals("paused")) pa=de=ca=true;
                        else if(value1.equals("ended")) en=true;
                        else if(value1.equals("canceled"))ca=true;
                        Iterator itz = labels.iterator();
                        while(itz.hasNext()){
                            String as1 = itz.next().toString();
                            String s = itz.next().toString();
                            boolean s1 = Boolean.parseBoolean(itz.next().toString());
                            if(as1.equals("unassigned")&&un){
                            %><option value="<%=as1%>"<%
                            if (s1){%>selected<%}%>><%=s%></option><%
                            }else if(as1.equals("assigned")&&as){
                            %><option value="<%=as1%>"<%
                            if (s1){%>selected<%}%>><%=s%></option><%
                            }else if(as1.equals("canceled")&&ca){
                            %><option value="<%=as1%>"<%
                            if (s1){%>selected<%}%>><%=s%></option><%
                            }else if(as1.equals("develop")&&de){
                            %><option value="<%=as1%>"<%
                            if (s1){%>selected<%}%>><%=s%></option><%
                            }else if(as1.equals("paused")&&pa){
                            %><option value="<%=as1%>"<%
                            if (s1){%>selected<%}%>><%=s%></option><%
                            }else if(as1.equals("ended")&&en){
                            %><option value="<%=as1%>"<%
                            if (s1){%>selected<%}%>><%=s%></option><%
                            }
                        }
                %></select></td>
              </tr>
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_hasPredecessor, mgr.MODE_EDIT)%></td>
                  <td><%
                    ArrayList vals1=getActsPrede(act);
                    ArrayList listAll=getActsForPrec(act, parent);
                    Iterator listActall = listAll.iterator();
                   %><select name="<%=act.swbproy_hasPredecessor.getName()%>" multiple="true" size="4" style="width:300px;" <%if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")||checkPrede){%>disabled<%}%>><%
                   if(listActall.hasNext())%><option value=""><%
                    while (listActall.hasNext()) {
                        WebPage sob = (WebPage)listActall.next();
                        if (!sob.isParentof(act) &&sob.getURI() != null) {
                            %><option value="<%=sob.getURI()%>"<%
                            if (vals1.contains(sob.getURI())) {
                                %>selected="selected"<%}
                            %>><%=sob.getDisplayName(user.getLanguage())%></option><%}
                    }%></select><%
    %>               </td>
              </tr>
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_responsible, mgr.MODE_EDIT)%></td>
                  <td><%
                  res = responsible.iterator();
                   %><select name="<%=act.swbproy_responsible.getName()%>"<%if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")||checkPrede){ %>disabled<%}%>><%
                        String uri="";
                        if(act.getResponsible()!=null)
                           uri = act.getResponsible().getURI();
                        while(res.hasNext()){
                            String uri1 = res.next().toString();
                            SemanticObject sob = SemanticObject.createSemanticObject(uri1);
                            if (sob.getURI() != null) {
                  %><option value="<%=sob.getURI()%>"<%
                                if (sob.getURI().equals(uri)) {
                                    %>selected<%
                                }
                                    %>><%=sob.getDisplayName(user.getLanguage())%></option><%
                            }
                        }
                    %></select><%
    %>             </td>
              </tr>
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_hasParticipants, mgr.MODE_EDIT)%></td>
                  <td><%
                    ArrayList<String> vals=new ArrayList();
                    listobj=act.listParticipantses();
                    while(listobj.hasNext())
                        vals.add(listobj.next().toString());
                    res = responsible.iterator();
                  %><select name="<%=act.swbproy_hasParticipants.getName()%>" multiple="true"  size="4" style="width:300px;"<%
                    if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")||checkPrede){
                            %>disabled<%
                    }%>><%if(res.hasNext())%><option value=""><%
                    while (res.hasNext()) {
                        SemanticObject sob = SemanticObject.createSemanticObject(res.next().toString());//SemanticObject.createSemanticObject(res.next().toString());
                        if (sob.getURI() != null) {
                            %><option value="<%=sob.getURI()%>"<%
                            if (vals.contains(sob.getURI())) {
                                %>selected="selected"<%}
                            %>><%=sob.getDisplayName(user.getLanguage())%></option><%}
                    }%></select><%
    %>               </td>
              </tr>
              <tr>
                  <td width="200px"><%=mgr.renderLabel(request, act.swb_created, mgr.MODE_VIEW)%></td>
                  <td><%=mgr.renderElement(request, act.swb_created,mgr.MODE_VIEW)%></td>
              </tr><%         if(act.getStatus().equals("develop")||act.getStatus().equals("ended")||act.getStatus().equals("canceled")||act.getStatus().equals("paused")){%>
               <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_startDate, mgr.MODE_VIEW)%></td>
                   <td><%=mgr.renderElement(request, act.swbproy_startDate,mgr.MODE_VIEW)%></td>
               </tr>
    <%         }%>
    <%         if(act.getStatus().equals("ended")||act.getStatus().equals("canceled")){%>
               <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_endDate, mgr.MODE_VIEW)%></td>
                   <td><%=mgr.renderElement(request, act.swbproy_endDate,mgr.MODE_VIEW)%></td>
               </tr>
    <%         }%>
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_plannedHour, mgr.MODE_EDIT)%></td>
                  <td><%
                  if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")||checkPrede){
                     out.println("<input id=\""+act.swbproy_plannedHour.getName()+"\" name=\""+act.swbproy_plannedHour.getName()+"\" value=\""+act.getPlannedHour()+"\" style=\"width:300px\" disabled=\"disabled\">");
                  }else
                     out.println("<input id=\""+act.swbproy_plannedHour.getName()+"\" name=\""+act.swbproy_plannedHour.getName()+"\" value=\""+act.getPlannedHour()+"\" style=\"width:300px\" dojoType=\"dijit.form.ValidationTextBox\" regExp=\"\\d+\" invalidMessage=\""+paramRequest.getLocaleString("msgOnlyIntegers")+"\">");
    %>             </td>
              </tr>
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_currentHour, mgr.MODE_EDIT)%></td>
                  <td><%
                  if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")||checkPrede){
                     out.println("<input id=\""+act.swbproy_currentHour.getName()+"\" name=\""+act.swbproy_currentHour.getName()+"\" value=\""+act.getCurrentHour()+"\" style=\"width:300px\" disabled=\"disabled\">");
                  }else
                    out.println("<input id=\""+act.swbproy_currentHour.getName()+"\" name=\""+act.swbproy_currentHour.getName()+"\" value=\""+act.getCurrentHour()+"\" style=\"width:300px\" dojoType=\"dijit.form.ValidationTextBox\" regExp=\"\\d+\" invalidMessage=\""+paramRequest.getLocaleString("msgOnlyIntegers")+"\">");
    %>             </td>
              </tr>
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_currentPercentage, mgr.MODE_EDIT)%></td>
                  <td><%String form="\\d+([.])\\d+";
                  if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")||checkPrede){
                     out.println("<input id=\""+act.swbproy_currentPercentage.getName()+"\" name=\""+act.swbproy_currentPercentage.getName()+"\" value=\""+act.getCurrentPercentage()+"\" style=\"width:300px\" disabled=\"disabled\">");
                  }else
                     out.println("<input id=\""+act.swbproy_currentPercentage.getName()+"\" name=\""+act.swbproy_currentPercentage.getName()+"\" value=\""+act.getCurrentPercentage()+"\" style=\"width:300px\" dojoType=\"dijit.form.ValidationTextBox\" regExp=\""+form+"\" invalidMessage=\"hoal nariz de bola\"/>");//paramRequest.getLocaleString("msgOnlyfloatingn")

        %>              </td>
              </tr>
            </tbody>
           </table>
              <div class="barraGeneral"><%=getProgressBar(proBar,paramRequest.getLocaleString("msgTotalHours"))%></div>
               <% if(!act.getStatus().equals("canceled")&&!act.getStatus().equals("ended")||checkPrede){%><div class="botones"><div class="btnIzq"><%
                    out.println("<button type=\"button\" onclick=\"calcular(this.form)\">"+paramRequest.getLocaleString("btnCalculate")+"</button>");%></div><div class="btnDer"><%
                    out.println("<button type=\"submit\" onclick=\"return validar(this.form)\">"+paramRequest.getLocaleString("btnUpdate")+"</button>");%></div></div><%
                   }
                %>
           </form>

        </fieldset>
    <%      }else{//si no esta registrado
              SWBFormMgr mgr = new SWBFormMgr(obj, null, SWBFormMgr.MODE_VIEW);
              mgr.setLang(lang);
              mgr.setType(mgr.TYPE_XHTML);
    %>
        <fieldset><legend><%=paramRequest.getLocaleString("labelActivityDetail")%></legend>
            <br><label><%=paramRequest.getLocaleString("labelActivityProgress")%>:</label><br>
          <div class="detalles">
          <%=getProgressBar(proBar,paramRequest.getLocaleString("msgTotalHours"))%>
          </div>
          <br>
          <table  class="detail">
            <tbody>
                <tr><th width="150"><label for="proyect"><%=paramRequest.getLocaleString("titleProject")%> </label></th>
                   <td><%=parent.getDisplayName()%></td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_actType, mgr.MODE_VIEW)%></th><%
                  if(act.getActType().equals("")){%>
                  <td><%=paramRequest.getLocaleString("msgUnassigned")%></td>
                  <%}else{%>
                  <td><%=mgr.renderElement(request, act.swbproy_actType,mgr.MODE_VIEW)%></td>
                  <%}
                   %>
               </tr>
               <tr><th><%=mgr.renderLabel(request,act.swbproy_critical,mgr.MODE_VIEW)%></th>
                   <td><%=mgr.renderElement(request, act.swbproy_critical,mgr.MODE_VIEW)%></td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_hasPredecessor, mgr.MODE_VIEW)%></th>
                   <td>
                    <%GenericIterator <Activity> it1 = act.listPredecessors();
                    if(it1.hasNext()){%>
                     <select name="<%=act.swbproy_hasPredecessor.getName()%>"  size="2" multiple="true" style="width:200px;">
                      <%while(it1.hasNext()){
                       act1=it1.next();%>
                       <option value="<%=act1.getDisplayName()%>"><%=act1.getDisplayName()%></option>
                      <%}%>
                     </select>
                    <%}
                    else
                       out.println(paramRequest.getLocaleString("msgNoPredecessors"));%>
                   </td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_status, mgr.MODE_VIEW)%></th><%
                    String label = act.getStatus()!=null?act.getStatus():paramRequest.getLocaleString("msgUnassigned");
                    Iterator itz1 = labels.iterator();
                    String value = paramRequest.getLocaleString("msgUnassigned");
                    while(itz1.hasNext()){
                        String aitz1 = itz1.next().toString();
                        if(aitz1.equals(label))
                           value = itz1.next().toString();
                    }
                    %>
                   <td><%=value%></td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_responsible, mgr.MODE_VIEW)%></th>
                   <td>
                     <%if(act.getResponsible()!=null){%>
                        <%=act.getResponsible().getFullName()%>
                     <%}else{
                       out.println(paramRequest.getLocaleString("msgUnassigned"));}%>
                   </td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_hasParticipants, mgr.MODE_VIEW)%></th>
                   <td>
                    <%GenericIterator<User> it2 = act.listParticipantses();
                    if(it2.hasNext()){%>
                     <select name="<%=act.swbproy_hasParticipants.getName()%>"  size="2" multiple="true" style="width:200px;">
                      <%while(it2.hasNext()){
                        User us=it2.next();%>
                        <option value="<%=us.getFullName()%>"><%=us.getFullName()%></option>
                      <%}%>
                     </select>
                    <%}
                    else{
                     out.println(paramRequest.getLocaleString("msgNoParticipants"));}%>
                   </td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_plannedHour, mgr.MODE_VIEW)%></th>
                   <td><%=mgr.renderElement(request, act.swbproy_plannedHour.getName(),mgr.MODE_VIEW)%></td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_currentHour, mgr.MODE_VIEW)%></th>
                   <td><%=mgr.renderElement(request, act.swbproy_currentHour,mgr.MODE_VIEW)%></td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_currentPercentage, mgr.MODE_VIEW)%></th>
                   <td><%=mgr.renderElement(request,act.swbproy_currentPercentage,mgr.MODE_VIEW)%></td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swb_created, mgr.MODE_VIEW)%></th>
                   <td><%=mgr.renderElement(request, act.swb_created,mgr.MODE_VIEW)%></td>
               </tr>
    <%         if(act.getStatus().equals("develop")||act.getStatus().equals("ended")||act.getStatus().equals("canceled")||act.getStatus().equals("paused")){%>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_startDate, mgr.MODE_VIEW)%></th>
                   <td><%=mgr.renderElement(request, act.swbproy_startDate,mgr.MODE_VIEW)%></td>
               </tr>
    <%         }%>
    <%         if(act.getStatus().equals("ended")||act.getStatus().equals("canceled")){%>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_endDate, mgr.MODE_VIEW)%></th>
                   <td><%=mgr.renderElement(request, act.swbproy_endDate,mgr.MODE_VIEW)%></td>
               </tr>
    <%         }%>
            </tbody>
          </table>
        </fieldset>
    <%      }
          }%>
     </div>
    <%!
        private ArrayList getActsPrede(Activity act){
            //Obtiene las actividades predecesoras relacionadas a la actividad y que sean validas
            ArrayList actPre=new ArrayList();
            ArrayList<String> vals1=new ArrayList();
            Iterator listobj=act.listPredecessors();
            while(listobj.hasNext())
                actPre.add(listobj.next());
            actPre=validActivities(actPre);
            listobj = actPre.iterator();
            while(listobj.hasNext())
                vals1.add(listobj.next().toString());
            return vals1;
        }
        private ArrayList getActsForPrec(Activity act, WebPage parent){
            //Obtiene las actividades relacionadas al modelo o sitio web y que sean validas
            ArrayList listAll =new ArrayList();
            ArrayList actsByProj = getActivitiesByProject(parent,act.getWebSite());
            Iterator<Activity> listActall=actsByProj.iterator();
            while(listActall.hasNext()){
                Activity act2 = listActall.next();
                if(!act2.equals(act))
                    listAll.add(act2);
            }
            listAll = validActivities(listAll);
            return listAll;
        }
        private ArrayList getLabelStatus(Activity act, User user){
            String value1=act.getStatus().toString();
            ArrayList labels = new ArrayList();
            SemanticObject sobj=act.swbproy_status.getDisplayProperty();
            String selectValues=null;
            boolean valid = true;
            if(sobj!=null){
                DisplayProperty dobj=new DisplayProperty(sobj);
                selectValues=dobj.getDisplaySelectValues(user.getLanguage());
            }
            StringTokenizer st = new StringTokenizer(selectValues, "|");
            while (st.hasMoreTokens()) {
                String tok = st.nextToken();
                int    ind = tok.indexOf(':');
                String id  = tok;
                String val = tok;
                if (ind > 0) {
                    id  = tok.substring(0, ind);
                    val = tok.substring(ind + 1);
                    if(id.equals(value1))valid=true;
                    else valid=false;
                    labels.add(id);
                    labels.add(val);
                    labels.add(valid);
                }
            }
            return labels;
        }
        private ArrayList getActivitiesByProject(WebPage project,WebSite model){
             ArrayList containActs = new ArrayList();
             Iterator contAct= Activity.ClassMgr.listActivities(model);
             while(contAct.hasNext()){
                 Activity activ=(Activity)contAct.next();
                 if(activ.isChildof(project))
                     containActs.add(activ);
             }
             return containActs;
        }
        private void validAct(Activity act){
             if(act.getStatus()==null){
                if(act.getResponsible()==null)
                    act.setStatus("unassigned");
                else
                    act.setStatus("assigned");
            }
            if(act.getStatus().equals("unassigned")){
                if(act.getResponsible()!=null) act.setStatus("assigned");
            }else if(act.getStatus().equals("develop")||act.getStatus().equals("canceled")||act.getStatus().equals("paused")||act.getStatus().equals("ended")){
                if(act.getStartDate()==null)act.setStartDate(new Timestamp(new Date().getTime()));
                if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")){
                    if(act.getEndDate()==null)act.setEndDate(new Timestamp(new Date().getTime()));
                }
            }
            if(act.getActType()==null)
              act.setActType("");
            if(act.getCurrentHour()==0)
             act.setCurrentHour(0);
            if(act.getCurrentPercentage()==0)
              act.setCurrentPercentage(0);
            if(act.getPlannedHour()==0)
              act.setPlannedHour(0);
        }
        private ArrayList listUserRepository(WebSite wp){
            ArrayList usrs =new ArrayList();
            UserRepository urep=wp.getUserRepository();
            Iterator users = urep.listUsers();
            while(users.hasNext()){
                User us = (User)users.next();
                usrs.add(us.getURI());
            }
            return usrs;
        }
        private boolean checkPredecesor(Activity act){
            boolean check=false;
            Iterator prede = act.listPredecessors();
            while(prede.hasNext())
            {
                Activity predecessor = (Activity)prede.next();
                if(predecessor.getChild()==null){
                    if(predecessor.getStatus()==null||predecessor.getStatus().equals("assigned")||predecessor.getStatus().equals("unassigned")||predecessor.getStatus().equals("develop")||predecessor.getStatus().equals("paused")){
                        check = true;}
                }
            }
            return check;
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
        private ArrayList validActivities(ArrayList allActivities)
        {
            ArrayList valActs=new ArrayList();
            Iterator all = allActivities.iterator();
            while(all.hasNext()){
              WebPage wp1=(WebPage)all.next();
              boolean valid=true;
              WebPage parent = wp1.getParent();
              //Actividad sin papa desactivado
              while(parent!=null){
                  if(!parent.isActive())
                      valid=false;
                  parent=parent.getParent();
              }
              //La misma Actividad
              if(!wp1.isActive()|| wp1==null || !wp1.isVisible()||wp1.isHidden() || !wp1.isValid() || wp1.isDeleted())
                 valid=false;
              if(valid)
                  valActs.add(wp1);
            }
            return valActs;
        }
        private String printPage(HashMap mpag, String title)
        {
            Iterator itpr=mpag.values().iterator();
            StringBuffer strb = new StringBuffer();
            WebPage wpage;
            strb.append("");
            if(itpr.hasNext())
            {
                strb.append("<h2>"+title+"</h2>\n");
                strb.append("<br>\n");
                strb.append("   <ul>\n");
                while(itpr.hasNext()){
                    wpage=(WebPage)itpr.next();
                    strb.append("      <li>\n         <a href=\""+wpage.getUrl()+"\">"+wpage.getDisplayName()+"</a>\n      </li>\n");
                }
                strb.append("   </ul>");
            }
            return strb.toString();
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
        public String getWebPageListLevel(Activity act,User user, int level, String title, String indentation,String titleLan)
        {
            level=level+act.getLevel();
            Iterator<Activity> it = Activity.ClassMgr.listActivityByParent(act,act.getWebSite());
            ArrayList ChildVisible=new ArrayList();
            while(it.hasNext())
            {
                Activity wp=it.next();
                if(wp.isVisible())
                    ChildVisible.add(wp);
            }
            it=ChildVisible.iterator();
            StringBuffer st=new StringBuffer();
            if(it.hasNext()){
                st.append("<h2>"+title+"</h2>\n");
                st.append("<br>\n");
                st.append("  <div class=\"list\">\n");
                while(it.hasNext())
                {
                    act = (Activity)it.next();
                    String pgrb = getProgressBar(getListLeaf(act,user),titleLan);
                    st.append("  <div class=\"activity\">"+indentation+"      <a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a></div>\n");
                    st.append("<div class=\"espacio\">\n");
                    st.append("<div class=\"contPorcentaje\">\n");
                    st.append(pgrb);
                    st.append("</div>\n");
                    st.append("</div>\n");
                    if(level!=act.getLevel()&&pgrb!=null){
                        StringBuffer st1=new StringBuffer();
                        indentation=indentation+"&nbsp;&nbsp;&nbsp;&nbsp;";
                        String childp=getWebPageListLevel1(act,user,level,st1,indentation,titleLan);
                        indentation=indentation.substring(0, indentation.length()-24);
                        st.append(childp);
                    }
                }
                st.append("   </div>\n");
            }
            return st.toString();
        }
        public String getWebPageListLevel1(Activity act, User user, int level,StringBuffer st1, String indentation,String titleLan)
        {
            Iterator<Activity> it = Activity.ClassMgr.listActivityByParent(act,act.getWebSite());
            ArrayList ChildVisible=new ArrayList();
            while(it.hasNext())
            {
                Activity wp=it.next();
                if(wp.isVisible())
                    ChildVisible.add(wp);
            }
            it=ChildVisible.iterator();
            if(it.hasNext()){
                while(it.hasNext())
                {
                    act = (Activity)it.next();
                    String pgrb = getProgressBar(getListLeaf(act,user),titleLan);
                    st1.append("  <div class=\"activity\">"+indentation+"      <a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a></div>\n");
                    st1.append("<div class=\"espacio\">\n");
                    st1.append("<div class=\"contPorcentaje\">\n");
                    st1.append(pgrb);
                    st1.append("</div>\n");
                    st1.append("</div>\n");
                    if(level != act.getLevel()&&pgrb!=null){
                        indentation=indentation+"&nbsp;&nbsp;&nbsp;&nbsp;";
                        getWebPageListLevel1(act,user,level,st1,indentation,titleLan);
                        indentation=indentation.substring(0, indentation.length()-24);
                     }
                }
            }
            return st1.toString();
        }
        public ArrayList getListLeaf(WebPage wp,User user)
        {
            ArrayList result = new ArrayList();
            getListLeafProcess(wp, user, result);
            return result;
        }
        private void getListLeafProcess(WebPage webpage, User user, ArrayList al)
        {
            Iterator<WebPage> it = webpage.listVisibleChilds(user.getLanguage());
            SemanticObject obj;
            Activity act1;
            if(it.hasNext()){
                while(it.hasNext())
                {
                    webpage = (WebPage)it.next();
                    if(hasChild(webpage,user)){
                        getListLeafProcess(webpage, user, al);}
                    else{
                        obj= SemanticObject.createSemanticObject(webpage.getURI());
                        if(obj.instanceOf(Activity.sclass))
                        {
                            act1=(Activity)obj.createGenericInstance();
                            al.add(act1.getCurrentPercentage());
                            al.add(act1.getPlannedHour());}
                    }
                }
            }else{
                    obj= SemanticObject.createSemanticObject(webpage.getURI());
                    if(obj.instanceOf(Activity.sclass)){
                        act1=(Activity)obj.createGenericInstance();
                        al.add(act1.getCurrentPercentage());
                        al.add(act1.getPlannedHour());
                    }
            }
        }
        private boolean hasChild(WebPage webpage,User user)
        {
            boolean result = false;
            ArrayList checks=new ArrayList();
            Iterator childs = webpage.listVisibleChilds(user.getLanguage());
            while(childs.hasNext()){
                WebPage child = (WebPage)childs.next();
                if(child != null && child.isVisible() && child.isActive() && !child.isHidden() && child.isValid() && !child.isDeleted())
                    checks.add(true);
                else
                    checks.add(false);
            }
            if(checks.contains(true))
                result = true;
           return result;
        }
    %>