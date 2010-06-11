
    <jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
    <%@page import="java.util.*,java.io.PrintWriter,java.text.*,org.semanticwb.model.*,org.semanticwb.platform.*,org.semanticwb.portal.resources.projectdriver.*,org.semanticwb.portal.api.*,org.semanticwb.portal.*,java.sql.Timestamp"%>
    <%
            Iterator it,res,listobj;
            Iterator<SemanticObject> itso;
            ArrayList proBar=new ArrayList();
            Activity act1;
            User us;
            String id1="";
            String lang="es";
            WebPage wp=paramRequest.getWebPage();
            User user=paramRequest.getUser();
            WebPage wp1=wp.getParent();
            String parent=getParent(wp1);
            it=wp.listVisibleChilds(user.getLanguage());
            SemanticObject obj=SemanticObject.createSemanticObject(wp.getURI());
            Activity act = (Activity)obj.createGenericInstance();
            ArrayList responsible = listUserRepository(act.getWebSite());
            validAct(act);
            proBar.add(act.getCurrentPercentage());
            proBar.add(act.getPlannedHour());
            if(paramRequest.getUser()!=null)
              lang=paramRequest.getUser().getLanguage();

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
                            alert('Agrega horas planeadas');
                            forma.plannedHour.focus();
                            val = false;
                        }
                        if(forma.currentHour.value==""){
                            alert('Agrega horas actuales');
                            forma.currentHour.focus();
                            val=false;
                        }
                        if(forma.currentPercentage.value==""){
                            alert('Agrega porcentaje de Avance');
                            forma.currentPercentage.focus();
                            val =false;
                        }
                    }else if(forma.status.value=='ended'||forma.status.value=='paused'){
                        if(forma.plannedHour.value==""||forma.plannedHour.value<=0){
                            alert('Agrega horas planeadas');
                            forma.plannedHour.focus();
                            val = false;
                        }
                        if(forma.currentHour.value==""||forma.currentHour.value<=0){
                            alert('Agrega horas actuales');
                            forma.currentHour.focus();
                            val=false;
                        }
                        if(forma.currentPercentage.value==""||forma.currentPercentage.value<=0){
                            alert('Agrega porcentaje de Avance');
                            forma.currentPercentage.focus();
                            val =false;
                        }
                        if(forma.status.value=='ended'){
                            forma.currentPercentage.value=100;
                        }
                    }
                    if(forma.plannedHour.value!=""&&isNaN(forma.plannedHour.value)==true){
                        alert('Agrega un valor númerico para Horas Planeadas');
                        val=false;
                    }
                    if(forma.currentHour.value!=""&&isNaN(forma.currentHour.value)==true){
                        alert('Agrega un valor númerico para Horas Actuales');
                        val=false;
                    }
                    if(forma.currentPercentage.value!=""&&isNaN(forma.currentPercentage.value)==true){
                        alert('Agrega un valor númerico para Porcentaje Actual');
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
<%
          if(it.hasNext())
          {//Si tiene hijos muestra una lista de ellos
            HashMap webPage=new HashMap();
            WebPage page1;
            String namewp="";
            while(it.hasNext()){
              page1=(WebPage)it.next();
              namewp=page1.getSemanticObject().getSemanticClass().getName();
              if(namewp.equals("WebPage"))
              webPage.put(page1, page1);
            }
    %>
        <div>
        <table  width="91%">
          <tr>
              <td width="180px">Proyecto: </td>
            <td><%=parent%></td></tr>
          <tr><td width="180px">Avance Actividad:</td>
            <td><%=getProgressBar(getListLeaf(act,user),"66CCFF",null)%></td>
          </tr>
        </table>
        </div>
      <%
          out.println(getWebPageListLevel(act,user,4,"Subactividades",""));
          if(!webPage.isEmpty())
            out.println(printPage(webPage,"Secciones"));
          }else{//Sino tiene hijos
            if(user.isRegistered()){//Si esta registrado
              SWBFormMgr mgr = new SWBFormMgr(act.getSemanticObject(),null,SWBFormMgr.MODE_EDIT);
              mgr.setLang(lang);
              mgr.setType(mgr.TYPE_XHTML);
              SWBResourceURL url=paramRequest.getActionUrl();
              url.setParameter("uri", obj.getURI());
              url.setAction("update");
              mgr.addHiddenParameter(Activity.swb_active.getName(), Boolean.toString(act.isActive()));//dojoType="dijit.form.Form"
              boolean checkPrede=checkPredecesor(act);%>
        <fieldset><legend>Seguimiento</legend>
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
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_status, mgr.MODE_EDIT)%></td>
            <%  boolean un,as,ca,de,pa,en; un=as=ca=de=pa=en=false;
                SemanticObject sobj=act.swbproy_status.getDisplayProperty();
                String selectValues=null;
                if(sobj!=null){
                    DisplayProperty dobj=new DisplayProperty(sobj);
                    selectValues=dobj.getDisplaySelectValues(user.getLanguage());
                }
                if(selectValues!=null){
                    String value=act.swbproy_status.getName();String value1=act.getStatus().toString();
                    if(value==null)value="";if(value1==null)value1="";
                    if(value1.equals("unassigned")) un=ca=as=true;
                    else if(value1.equals("assigned")) as=de=ca=true;
                    else if(value1.equals("develop")) de=pa=en=ca=true;
                    else if(value1.equals("paused")) pa=de=ca=true;
                    else if(value1.equals("ended")) en=true;
                    else if(value1.equals("canceled"))
                        ca=true;%>
                        <td><select name="<%=Activity.swbproy_status.getName()%>"<%if(checkPrede){%>disabled<%}%>>
                     <%StringTokenizer st = new StringTokenizer(selectValues, "|");
                        while (st.hasMoreTokens()) {
                            String tok = st.nextToken();
                            int    ind = tok.indexOf(':');
                            String id  = tok;
                            String val = tok;
                            if (ind > 0) {
                                id  = tok.substring(0, ind);
                                val = tok.substring(ind + 1);
                            }
                            if(id.equals("unassigned")&&un){
                            %><option value="<%=id%>"<%
                            if (id.equals(value1)){
                            %>selected<%}%>><%=val%></option><%
                            }else if(id.equals("assigned")&&as){
                            %><option value="<%=id%>"<%
                            if (id.equals(value1)){
                            %>selected<%}%>><%=val%></option><%
                            }else if(id.equals("canceled")&&ca){
                            %><option value="<%=id%>"<%
                            if (id.equals(value1)){
                            %>selected<%}%>><%=val%></option><%
                            }else if(id.equals("develop")&&de){
                            %><option value="<%=id%>"<%
                            if (id.equals(value1)){
                            %>selected<%}%>><%=val%></option><%
                            }else if(id.equals("paused")&&pa){
                            %><option value="<%=id%>"<%
                            if (id.equals(value1)){
                            %>selected<%}%>><%=val%></option><%
                            }else if(id.equals("ended")&&en){
                            %><option value="<%=id%>"<%
                            if (id.equals(value1)){
                            %>selected<%}%>><%=val%></option><%
                            }
                       }
                %></select></td><%
                }%>
              </tr>
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_hasPredecessor, mgr.MODE_EDIT)%></td>
                  <td><%
                    //Obtiene las actividades predecesoras relacionadas a la actividad y que sean validas
                    ArrayList actPre=new ArrayList();
                    ArrayList<String> vals1=new ArrayList();
                    listobj=act.listPredecessors();
                    while(listobj.hasNext())
                        actPre.add(listobj.next());
                    actPre=validActivities(actPre);
                    listobj = actPre.iterator();
                    while(listobj.hasNext())
                        vals1.add(listobj.next().toString());

                    //Obtiene las actividades relacionadas al modelo o sitio web y que sean validas
                    ArrayList listAll =new ArrayList();
                    ArrayList container=getContainerActs(act,user.getLanguage());
                    ArrayList actsByProj = getActivitiesByProject(container,act.getWebSite());
                    Iterator<Activity> listActall=actsByProj.iterator();
                    while(listActall.hasNext()){
                        Activity act2 = listActall.next();
                        if(!act2.equals(act))
                            listAll.add(act2);
                    }
                    listAll = validActivities(listAll);
                    listActall = listAll.iterator();
                   %><select name="<%=act.swbproy_hasPredecessor.getName()%>" multiple="true" size="4" style="width:300px;" <%if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")||checkPrede){%>disabled<%}%>><%
                   if(listActall.hasNext())%><option value=""<%
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
                    }%>><%if(res.hasNext())%><option value=""<%
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
                     out.println("<input id=\""+act.swbproy_plannedHour.getName()+"\" name=\""+act.swbproy_plannedHour.getName()+"\" value=\""+act.getPlannedHour()+"\" style=\"width:300px\" dojoType=\"dijit.form.ValidationTextBox\" regExp=\"\\d+\" invalidMessage=\"Sólo números\">");
//                   out.println(mgr.renderElement(request, act.swbproy_plannedHour,mgr.MODE_EDIT));
    %>             </td>
              </tr>
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_currentHour, mgr.MODE_EDIT)%></td>
                  <td><%
                  if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")||checkPrede){
                     out.println("<input id=\""+act.swbproy_currentHour.getName()+"\" name=\""+act.swbproy_currentHour.getName()+"\" value=\""+act.getCurrentHour()+"\" style=\"width:300px\" disabled=\"disabled\">");
                  }else
                     out.println("<input id=\""+act.swbproy_currentHour.getName()+"\" name=\""+act.swbproy_currentHour.getName()+"\" value=\""+act.getCurrentHour()+"\" style=\"width:300px\" dojoType=\"dijit.form.ValidationTextBox\" regExp=\"\\d+\" invalidMessage=\"Sólo números\">");
    %>             </td>
              </tr>
              <tr><td width="200px"><%=mgr.renderLabel(request, act.swbproy_currentPercentage, mgr.MODE_EDIT)%></td>
                  <td><%String form="\\d+([.])\\d+";
                  if(act.getStatus().equals("canceled")||act.getStatus().equals("ended")||checkPrede){
                     out.println("<input id=\""+act.swbproy_currentPercentage.getName()+"\" name=\""+act.swbproy_currentPercentage.getName()+"\" value=\""+act.getCurrentPercentage()+"\" style=\"width:300px\" disabled=\"disabled\">");
                  }else
                     out.println("<input id=\""+act.swbproy_currentPercentage.getName()+"\" name=\""+act.swbproy_currentPercentage.getName()+"\" value=\""+act.getCurrentPercentage()+"\" style=\"width:300px\" dojoType=\"dijit.form.ValidationTextBox\" regExp=\""+form+"\" invalidMessage=\"Sólo números flotantes\">");
        %>              </td>
              </tr>
              <tr><td width="200px">
    <%            if(!act.getStatus().equals("canceled")&&!act.getStatus().equals("ended")||checkPrede){%><%
                    out.println("<button type=\"submit\" onclick=\"calcular(this.form)\">Calcular</button>");%><br><%
                    out.println("<button type=\"submit\" onclick=\"return validar(this.form)\">Actualizar</button>");
                   }
                %></td>
                  <td><%=getProgressBar(proBar,null,null)%></td>
              </tr><%
           %></tbody>
           </table>
           </form>
        </fieldset>
    <%      }else{//si no esta registrado
              SWBFormMgr mgr = new SWBFormMgr(obj, null, SWBFormMgr.MODE_VIEW);
              mgr.setLang(lang);
              mgr.setType(mgr.TYPE_XHTML);
    %>
        <fieldset><legend>Detalles de Actividad</legend>
          <p class="indentation">
          <label for="progress">Avance Actividad:</label>
          <span name="progress"><%=getProgressBar(proBar,null,null)%></span>
          </p>
          <table class="detail">
            <tbody>
                <tr><th width="150"><label for="proyect">Proyecto </label></th>
                   <td><%=parent%></td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_actType, mgr.MODE_VIEW)%></th>
                   <td><%=mgr.renderElement(request, act.swbproy_actType,mgr.MODE_VIEW)%></td>
               </tr>
               <tr><th><%=mgr.renderLabel(request,act.swbproy_critical,mgr.MODE_VIEW)%></th>
                   <td><%=mgr.renderElement(request, act.swbproy_critical,mgr.MODE_VIEW)%></td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_hasPredecessor, mgr.MODE_VIEW)%></th>
                   <td>
                    <%GenericIterator <Activity> it1 = act.listPredecessors();
                    if(it1.hasNext()){%>
                     <select name="<%=act.swbproy_hasPredecessor.getName()%>"  size="4" multiple="true">
                      <%while(it1.hasNext()){
                       act1=it1.next();%>
                       <option value="<%=act1.getDisplayName()%>"><%=act1.getDisplayName()%></option>
                      <%}%>
                     </select>
                    <%}
                    else%>
                     Sin Predecesores
                   </td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_status, mgr.MODE_VIEW)%></th>
                   <td><%=mgr.renderElement(request, act.swbproy_status.getName(),mgr.MODE_VIEW)%></td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_responsible, mgr.MODE_VIEW)%></th>
                   <td>
                     <%if(act.getResponsible()!=null){%>
                        <%=act.getResponsible().getFullName()%>
                     <%}else%>
                        Sin Asignar
                   </td>
               </tr>
               <tr><th><%=mgr.renderLabel(request, act.swbproy_hasParticipants, mgr.MODE_VIEW)%></th>
                   <td>
                    <%GenericIterator<User> it2 = act.listParticipantses();
                    if(it2.hasNext()){%>
                     <select name="<%=act.swbproy_hasParticipants.getName()%>"  size="4" multiple="true">
                      <%while(it2.hasNext()){
                        us=it2.next();%>
                        <option value="<%=us.getFirstName()%>"><%=us.getFirstName()%></option>
                      <%}%>
                     </select>
                    <%}
                    else%>
                     Sin Participantes
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
    <%!
        private ArrayList getContainerActs(WebPage wp,String language)
        {
            WebPage site = wp;
            WebPage siteIni=wp;
            ArrayList containers=new ArrayList();
            boolean si=false;
            while(!si){
                if(site.getSemanticObject().getSemanticClass().getName().equals("Project")){
                    si=true;
                    siteIni=site;
                }
                site=site.getParent();
            }
            Iterator<WebPage> itwp = siteIni.listVisibleChilds(language);
            while(itwp.hasNext()){
                WebPage pag = itwp.next();
                String name = pag.getSemanticObject().getSemanticClass().getName();
                if(name.equals("ActivityContainer"))
                    containers.add(pag.getDisplayName());
            }
            return containers;
        }
        private ArrayList getActivitiesByProject(ArrayList containers,WebSite model){
              ArrayList containActs = new ArrayList();
              if(!containers.isEmpty()){
                 Iterator contAct= Activity.ClassMgr.listActivities(model);
                 while(contAct.hasNext()){
                     Activity activ=(Activity)contAct.next();
                     boolean p=false;
                     String containerS="";
                     WebPage parent=activ;
                     while(!p){
                         String clas=parent.getSemanticObject().getSemanticClass().getName();
                         if(clas.equals("ActivityContainer")){
                             containerS=parent.getDisplayName();p=true;
                         }
                         parent=parent.getParent();
                     }
                     if(containers.contains(containerS))
                        containActs.add(activ);
                 }
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

        private String getParent(WebPage wp){
           boolean p;p=false;
           String parent="";
           while(!p){
                SemanticObject obj1= SemanticObject.createSemanticObject(wp.getURI());
                if(obj1.instanceOf(Project.sclass)){
                    parent=wp.getDisplayName();p=true;}
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
        private String getProgressBar(ArrayList info, String colorBarra, String colorFondoBarra)
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
                if (colorBarra == null)
                    colorBarra = "006BD7";
                if (colorFondoBarra == null)
                    colorFondoBarra = "EFEFEF";
                ret.append("        <table border=0 width=\"85%\" bgcolor=\"#FFFFFF\">\n");
                ret.append("          <tr >\n");
                ret.append("            <td align=\"left\" width=\"70%\">\n");
                ret.append("              <div id=\"divStatusBar" + uuid + "\" name=\"divStatusBar" + uuid + "\"\n");
                ret.append("                 style=\"position: absolute; border: 1px none #000000;\n");
                ret.append("                 visibility:hidden; background-color:#008040;\n");
                ret.append("                 margin-left:10px; margin-top:7px;\">\n");
                ret.append("                 <font color=\"#FFFFFF\">\n");
                ret.append("                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
                ret.append("                   <b>Horas Totales: <i> " + horasTotales + " </i></b>\n");
                ret.append("                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
                ret.append("                 </font>\n");
                ret.append("               </div>\n");
                ret.append("               <table width=\"100%\" border=\"\" cellpadding=\"1\" cellspacing=\"1\" bordercolor=\"#FFFFFF\" bgcolor=\"#FFFFFF\"\n");
                ret.append("                 onmouseover=\"javascript:showDiv('divStatusBar" + uuid + "'); return true;\"\n");
                ret.append("                 onmouseout=\"javascript:hideDiv('divStatusBar" + uuid + "'); return true;\">\n");
                ret.append("                 <tr>\n");
                ret.append("                   <td width=\"100%\" bgcolor=\"#" + colorFondoBarra + "\">\n");
                ret.append("                     <table class=\"darkBar\" width=\"" + df.format(porcentajeTotal) + "%\" border=\"0\" bgcolor=\"#" + colorBarra + "\">\n");
                ret.append("                       <tr>\n");
                ret.append("                         <td><b>&nbsp;</b>\n");
                ret.append("                         </td>\n");
                ret.append("                       </tr>\n");
                ret.append("                     </table>\n");
                ret.append("                   </td>\n");
                ret.append("                 </tr>\n");
                ret.append("               </table>\n");
                ret.append("            </td>\n");
                ret.append("            <td align=\"left\" width=\"15%\">         " + df.format(porcentajeTotal) + "%\n");
                ret.append("            </td>\n");
                ret.append("          </tr>\n");
                ret.append("        </table>\n");
                ret.append("\n");
                return ret.toString();
            }
            else
                return null;
        }
        public String getWebPageListLevel(Activity act,User user, int level, String title, String indentation)
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
                st.append("<table width=\"91%\">\n");
                while(it.hasNext())
                {
                    act = (Activity)it.next();
                        String pgrb = getProgressBar(getListLeaf(act,user),null,null);
                        if(level == act.getLevel()){
                            st.append("     <tr>\n");
                            st.append("     <td colspan=3 width=\"100%\">\n");
                            st.append(indentation+"      <a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a>\n");
                            st.append("     </td>\n");
                            st.append("     </tr>\n");
                            st.append("     <tr>\n");
                            st.append("     <td width=\"15%\">\n");
                            st.append("     </td>\n");
                            st.append("     <td width=\"70%\">\n");
                            st.append(pgrb);
                            st.append("     </td>\n");
                            st.append("     </tr>\n");
                        }
                        else if(pgrb!=null){
                            st.append("     <tr>\n");
                            st.append("     <td colspan=3 width=\"100%\">\n");
                            st.append(indentation+"      <a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a>\n");
                            st.append("     </td>\n");
                            st.append("     </tr>\n");
                            st.append("     <tr>\n");
                            st.append("     <td width=\"15%\">\n");
                            st.append("     </td>\n");
                            st.append("     <td width=\"70%\">\n");
                            st.append(pgrb);
                            st.append("     </td>\n");
                            st.append("     </tr>\n");
                            StringBuffer st1=new StringBuffer();
                            indentation=indentation+"&nbsp;&nbsp;&nbsp;&nbsp;";
                            String childp=getWebPageListLevel1(act,user,level,st1,indentation);
                            indentation=indentation.substring(0, indentation.length()-24);
                            st.append(childp);
                        }
                }
                st.append("</table>\n");
            }
            return st.toString();
        }
        public String getWebPageListLevel1(Activity act, User user, int level,StringBuffer st1, String indentation)
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
                        String pgrb = getProgressBar(getListLeaf(act,user),null,null);
                        if(level == act.getLevel()){
                            st1.append("     <tr>\n");
                            st1.append("     <td colspan=3 width=\"100%\">\n");
                            st1.append(indentation+"      <a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a>\n");
                            st1.append("     </td>\n");
                            st1.append("     </tr>\n");
                            st1.append("     <tr>\n");
                            st1.append("     <td width=\"15%\">\n");
                            st1.append("     </td>\n");
                            st1.append("     <td width=\"70%\">\n");
                            st1.append(pgrb);
                            st1.append("     </td>\n");
                            st1.append("     </tr>\n");
                        }
                        else if (pgrb!=null){
                            st1.append("     <tr>\n");
                            st1.append("     <td colspan=3 width=\"100%\">\n");
                            st1.append(indentation+"      <a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a>\n");
                            st1.append("     </td>\n");
                            st1.append("     </tr>\n");
                            st1.append("     <tr>\n");
                            st1.append("     <td width=\"15%\">\n");
                            st1.append("     </td>\n");
                            st1.append("     <td width=\"70%\">\n");
                            st1.append(pgrb);
                            st1.append("     </td>\n");
                            st1.append("     </tr>\n");
                            indentation=indentation+"&nbsp;&nbsp;&nbsp;&nbsp;";
                            getWebPageListLevel1(act,user,level,st1,indentation);
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
