
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.*" %>
<%@page import="java.io.PrintWriter" %>
<%@page import="java.text.*"%>
<%@page import="org.semanticwb.model.*" %>
<%@page import="org.semanticwb.platform.*" %>
<%@page import="org.semanticwb.portal.resources.projectdriver.*" %>
<%@page import="org.semanticwb.portal.api.*" %>
<%@page import="org.semanticwb.portal.SWBFormMgr" %>
<%@page import="org.semanticwb.portal.SWBFormButton" %>
<%
        Iterator it;
        WebPage wp=paramRequest.getWebPage();
        User user=paramRequest.getUser();
        it=wp.listVisibleChilds(user.getLanguage());
        SemanticObject obj = SemanticObject.createSemanticObject(wp.getURI());
        Project wpPro = (Project)obj.createGenericInstance();
        SWBResourceURL url = paramRequest.getActionUrl();
        
%>
<script type="text/javascript">
            function hideDiv(objDIV) {
                document.getElementById(objDIV).style.visibility = 'hidden';
            }
            function showDiv(objDIV) {
                document.getElementById(objDIV).style.visibility = 'visible';
            }
</script>
<%
   if(it.hasNext())
   {
       ArrayList actPageCon=new ArrayList();
       ArrayList webPage=new ArrayList();
       ArrayList proPage=new ArrayList();
       ArrayList usPageCon=new ArrayList();
       ArrayList hours = new ArrayList();
       ArrayList dates = new ArrayList();
       Iterator<WebPage> childPag;
       WebPage page1;
       String namewp="";

       hours = calculateHours(wp,user);
       Iterator c = hours.iterator();
       String hourPlan="", hourCurren="", percen="", start="",end="";
       while(c.hasNext()){
           hourPlan = c.next().toString();
           hourCurren = c.next().toString();
           percen = c.next().toString();
       }
       dates=calculateDates(wp,user);
       c=dates.iterator();
       while(c.hasNext()){
           start = c.next().toString();
           if(start.equals(""))
              start = "Sin fecha asignada";
           if(c.hasNext())
               end = c.next().toString();
           if(end.equals(""))
               end = "Sin fecha asignada";
       }
       childPag=paramRequest.getWebPage().listVisibleChilds(paramRequest.getUser().getLanguage());
       while(childPag.hasNext())
        {
            page1=childPag.next();
            namewp=page1.getSemanticObject().getSemanticClass().getName();
            if(namewp.equals("ActivityContainer"))
               actPageCon.add(page1);
            else if(namewp.equals("WebPage"))
               webPage.add(page1);
            else if(namewp.equals("Project"))
               proPage.add(page1);
            else if(namewp.equals("UserWebPageContainer"))
               usPageCon.add(page1);
        }
       if(user.isRegistered())
       {
           ArrayList responsible = listUserRepository(wp.getWebSite());
           Iterator res=responsible.iterator();
           SWBFormMgr mgr=new SWBFormMgr(obj,null,SWBFormMgr.MODE_EDIT);
           mgr.setLang(user.getLanguage());
           mgr.setType(mgr.TYPE_XHTML);
           url.setParameter("uri", obj.getURI());
           url.setAction("updatepro");
           mgr.addHiddenParameter(Project.swb_active.getName(), Boolean.toString(wpPro.isActive()));
           %>
           <form id="<%=mgr.getFormName()%>" name="<%=mgr.getFormName()%>" class="edit" action="<%=url.toString()%>" method="post">
                        <table class="detail">
                            <tr><td width="80px" align="left"><b><%=mgr.renderLabel(request, wpPro.swbproy_leader,mgr.MODE_EDIT)%></b></td>
                                <td width="120px"><select name="<%=wpPro.swbproy_leader.getName()%>"><%
                                String uri="";
                                if(wpPro.getLeader()!=null)
                                    uri = wpPro.getLeader().getURI();
                                 while(res.hasNext()){
                                    SemanticObject sob = SemanticObject.createSemanticObject(res.next().toString());
                                    if (sob.getURI() != null) {
                                     %><option value="<%=sob.getURI()%>"<%
                                        if (sob.getURI().equals(uri)) {
                                            %>selected<%
                                        }
                                            %>><%=sob.getDisplayName(user.getLanguage())%></option><%
                                    }
                                 }
                               %></select>
                                </td>
                                <td>
                                    <%= SWBFormButton.newSaveButton().renderButton(request,SWBFormMgr.TYPE_XHTML,mgr.getLang())%>
                                </td>
                             </tr>
                        </table>
           </form>
       <%}else{
       %>
                <p class="indentation">
                <label for="project"><b>  Líder :         </b></label>
                <span name="project"><%=wpPro.getLeader()!=null?wpPro.getLeader().getFullName():"Sin Asignar"%></span>
                </p>
<%    }%>
                <table class="dates" width="90%">
                    <tr>
                        <th><b>Fecha Inicial:</b></th>
                        <td><%=start%></td>
                        <th><b>Horas Actuales:</b></th>
                        <td width="20%"><%=hourCurren%></td>
                    </tr>
                    <tr>
                        <th><b>Fecha Final:</b></th>
                        <td><%=end%></td>
                        <th><b>Avance:</b></th>
                        <td></td>
                    </tr>
                </table>
                <table width="95%">
                    <tr>
                        <td width="5%"></td>
                        <td><%=getProgressBar(getListLeaf(wp,user,false),"66CCFF",null)%></td>
                    </tr>
                </table>
                <br>
<%
      if(!proPage.isEmpty())
       out.println(printPage(proPage,"Subproyectos",user,true));
      if(!actPageCon.isEmpty())
      {
          Iterator itA=actPageCon.iterator();
          while(itA.hasNext()){
            WebPage tp=(WebPage)itA.next();
            out.println(getWebPageListLevel(tp,user,"Lista de Actividades"));
          }
      }
      if(!usPageCon.isEmpty()){%><br><%
          WebPage tp;Iterator itList;WebPage tpChild;
          UserWebPage uwpi;
          Iterator itUs=usPageCon.iterator();
          Iterator itA;
          while(itUs.hasNext()){
            tp = (WebPage)itUs.next();
            out.println(getListUser(tp,user));
          }
      }%><br><%
      if(!webPage.isEmpty())
       out.println(printPage(webPage,"Secciones",user,false));
   }
%>
<%!
        public String getListUser(WebPage wpUs,User user){
            StringBuffer buff = new StringBuffer();
              ArrayList listUser=new ArrayList();
              UserWebPage uwpi;
              Iterator itA;
              ArrayList listActu = new ArrayList();
              ArrayList listActiv =new ArrayList();
              Iterator<UserWebPage> itU = UserWebPage.ClassMgr.listUserWebPageByParent(wpUs, wpUs.getWebSite());
              while(itU.hasNext()){
                   uwpi= itU.next();
                  if(uwpi.isActive()&& uwpi!=null && uwpi.isVisible()&& uwpi.getChild()==null && !uwpi.isHidden() && uwpi.isValid() && !uwpi.isDeleted())
                    listUser.add(uwpi);
              }
              //Obtiene de los usuarios validos, las actividades relacionadas con dichos usuarios
              itU = listUser.iterator();
              while(itU.hasNext()){
                  uwpi = itU.next();
                  if(uwpi.getUserWP()!=null){
                      itA=Activity.ClassMgr.listActivityByResponsible(uwpi.getUserWP(),uwpi.getWebSite());
                      while(itA.hasNext()){
                          listActu.add(itA.next());
                      }
                  }
              }
              //Obtiene de las actividades asociadas a los usuarios validos, actividades que sean nodos finales y que no tengan papa desactivado
              itU = listActu.iterator();
              while(itU.hasNext()){
                  WebPage wp1 = (WebPage)itU.next();
                  boolean valid =true;
                  WebPage parent= wp1.getParent();
                  //Papa inactivo
                  while(parent!=null){
                      if(!parent.isActive())
                          valid=false;
                      parent=parent.getParent();
                  }
                  //Hijos inactivos
                  if(wp1.getChild()!=null)
                  {
                      itA = wp1.listVisibleChilds(user.getLanguage());
                      while(itA.hasNext()){
                          WebPage ch = (WebPage)itA.next();
                          if(ch.isActive())
                             valid = false;
                      }
                  }
                  //La misma actividad
                  if(!wp1.isActive()|| wp1==null || !wp1.isVisible()||wp1.isHidden() || !wp1.isValid() || wp1.isDeleted())
                      valid=false;
                  if(valid)
                      listActiv.add(wp1);
              }
              itU = listUser.iterator();
              if(itU.hasNext()){
                  buff.append("<h2>Personal Asociado</h2>\n");
                  buff.append("<br>\n");
                  buff.append("    <ul>\n");
                  while(itU.hasNext()){
                    UserWebPage wpu=(UserWebPage)itU.next();
                    itA = listActiv.iterator();
                    listActiv = new ArrayList();
                    while(itA.hasNext()){
                        Activity actU = (Activity)itA.next();
                        if(actU.getResponsible().equals(wpu.getUserWP()))
                        {
                            listActiv.add(actU.getCurrentPercentage());
                            listActiv.add(actU.getPlannedHour());
                        }
                    }
                    String avan=getProgressBar(listActiv,null,null);
                    if(avan==null)
                        avan="Sin avance";
                    buff.append("<li><a href=\""+wpu.getUrl()+"\">"+wpu.getDisplayName()+"</a></li>\n");
                    buff.append(avan+"\n");
                  }
                  buff.append("</ul>\n");
              }
              return buff.toString();
        }
        public String getWebPageListLevel(WebPage wp,User user, String title)
        {
            Iterator<Activity> it = Activity.ClassMgr.listActivityByParent(wp,wp.getWebSite());
            ArrayList ChildVisible=new ArrayList();
            it = Activity.ClassMgr.listActivityByParent(wp,wp.getWebSite());
            while(it.hasNext())
            {
                Activity wp1=(Activity)it.next();
                if(wp1.isVisible())
                    ChildVisible.add(wp1);
            }
            it=ChildVisible.iterator();
            StringBuffer st=new StringBuffer();
            if(it.hasNext()){
                st.append("<h2>"+title+"</h2>\n");
                st.append("<br>\n");
                st.append("   <ul>\n");
                while(it.hasNext())
                {
                    wp = (WebPage)it.next();
                    st.append("      <li><a href=\""+wp.getUrl()+"\">"+wp.getDisplayName()+"</a></li>\n");
                    st.append( getProgressBar(getListLeaf(wp,user,false),null,null));
                }
                st.append("   </ul>\n");
            }
            return st.toString();
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
    private ArrayList calculateDates(WebPage proy, User user){
        ArrayList Dates=new ArrayList();
        Iterator listAct=Activity.ClassMgr.listActivities(proy.getWebSite());
        ArrayList validAct = new ArrayList();
        String stime1="",stime2="",stime="",stimeE="",stimeE1="",stimeE2="";
        long time1=0,time2=0,time=0,timeE=0,timeE1=0,timeE2=0;
        //Valida Actividades
        while(listAct.hasNext()){
            WebPage wp1=(WebPage)listAct.next();
             boolean valid=true;
             WebPage parent = wp1.getParent();
             //Actividad sin papa desactivado
             while(parent!=null){
                 if(!parent.isActive())
                     valid=false;
                 parent=parent.getParent();
             }
             //Actividad con hijos activos
             if(wp1.getChild()!=null){
                 Iterator itA = wp1.listVisibleChilds(user.getLanguage());
                 while(itA.hasNext()){
                     WebPage ch = (WebPage)itA.next();
                     if(ch.isActive())
                        valid = false;
                 }
             }
             //La misma Actividad
              if(!wp1.isActive()|| wp1==null || !wp1.isVisible()||wp1.isHidden() || !wp1.isValid() || wp1.isDeleted())
                 valid=false;
              if(valid)
                  validAct.add(wp1);
        }
        listAct = validAct.iterator();
        boolean validEnd=true;
        while(listAct.hasNext()){
            Activity acts = (Activity)listAct.next();
         
            if(acts.getStatus()==null)
                acts.setStatus("unassigned");
            if(acts.getStatus().equals("assigned")||acts.getStatus().equals("unassigned")||acts.getStatus().equals("develop")||acts.getStatus().equals("paused")||acts.getEndDate()==null){
                validEnd=false;
            }
        }
        listAct = validAct.iterator();
        while(listAct.hasNext()){
            Activity act = (Activity)listAct.next();
            if(act.getStartDate()!=null){
                time1=0;
                time1=act.getStartDate().getTime();
                stime1=act.getStartDate().toString();
                if(act.getEndDate()!=null){
                    timeE1=0;
                    timeE1=act.getEndDate().getTime();
                    stimeE1=act.getEndDate().toString();
                }
                if(listAct.hasNext()){
                    Activity act1 = (Activity)listAct.next();
                    
                    if(act1.getStartDate()!=null){
                    time2=act1.getStartDate().getTime();
                    stime2=act1.getStartDate().toString();
                    }else
                        time2=0;
                    if(act1.getEndDate()!=null){
                        timeE2=0;
                        timeE2=act1.getEndDate().getTime();
                        stimeE2=act1.getEndDate().toString();
                    }else
                        timeE2=0;
                }
                if(time1>0&&time2>0){
                    if(time1<time2){
                        if(time>0){
                            if(time1<time){
                                time=time1;
                                stime=stime1;
                            }
                        }else{
                            time =time1;
                            stime =stime1;
                        }
                    }
                    else{
                        if(time>0){
                            if(time2<time){
                                time=time2;
                                stime=stime2;
                            }
                        }else{
                            time=time2;
                            stime=stime2;
                        }
                    }
                }else{
                    if(time1<=time||time==0){
                        time=time1;
                        stime = stime1;
                    }
                }
                if(validEnd){
                    if(timeE1>0&&timeE2>0){
                        if(timeE1>timeE2){
                            if(timeE>0){
                                if(timeE1>timeE){
                                    timeE = timeE1;
                                    stimeE=stimeE1;}
                             }else{
                                timeE=timeE1;
                                stimeE=stimeE1;
                             }
                        }
                        else{
                            if(timeE>0){
                                if(timeE2>timeE){
                                    timeE=timeE2;
                                    stimeE = stimeE2;
                                }
                            }else{
                                timeE=timeE2;
                                stimeE=stimeE2;
                            }
                        }
                    }else{
                        if(timeE1>=timeE||timeE==0){
                            timeE=timeE1;
                            stimeE = stimeE1;
                        }
                    }
                }
            }
        }
        Dates.add(stime);
        Dates.add(stimeE);
        return Dates;
    }

    private ArrayList calculateHours(WebPage wp,User user){
        ArrayList list = getListLeaf(wp,user,true);
        ArrayList hours = new ArrayList();
        float hourPlanned,percentage,hourCurrent,hourPartial;
        percentage=hourPlanned=hourCurrent=hourPartial=0;
        String value,percen="";
        Iterator it = list.iterator();
        if(it.hasNext()){
            while(it.hasNext()){
                value = String.valueOf(it.next());
                hourCurrent += Float.valueOf(value);
                value="";
                    percen = String.valueOf(it.next());
                    value=String.valueOf(it.next());
                    hourPlanned += Float.valueOf(value);
                    float dummy = Float.valueOf(percen);
                    if(dummy > 1)
                        dummy /= 100;
                    hourPartial += dummy * Float.valueOf(value);
                    percen="";
                    value="";
            }
            percentage = hourPartial/hourPlanned * 100;
            hours.add(hourPlanned);
            hours.add(hourCurrent);
            hours.add(percentage);
        }
        return hours;
    }
    private String printPage(ArrayList mpag, String title,User user,boolean progressBar)
    {
        Iterator itpr=mpag.iterator();
        StringBuffer strb = new StringBuffer();
        WebPage wpage;
        strb.append("");
        if(itpr.hasNext())
        {
            strb.append("               <h2>"+title+"</h2>\n");
            strb.append("                  <ul>\n");
            while(itpr.hasNext()){
                wpage=(WebPage)itpr.next();
                strb.append("                    <li>\n                        <a href=\""+wpage.getUrl()+"\">"+wpage.getDisplayName()+"</a>\n                    </li>\n");
                if(progressBar){
                    strb.append(getProgressBar(getListLeaf(wpage,user,false),null,null));
                }
            }
            strb.append("                  </ul>");
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
            ret.append("        <table border=0 width=\"91%\" bgcolor=\"#FFFFFF\">\n");
            ret.append("           <tr >\n");
            ret.append("              <td align=\"left\" width=\"75%\">\n");
            ret.append("                 <div id=\"divStatusBar" + uuid + "\" name=\"divStatusBar" + uuid + "\"\n");
            ret.append("                        style=\"position: absolute; border: 1px none #000000;\n");
            ret.append("                        visibility:hidden; background-color:#008040;\n");
            ret.append("                        margin-left:10px; margin-top:7px;\">\n");
            ret.append("                        <font color=\"#FFFFFF\">\n");
            ret.append("                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
            ret.append("                        <b>Horas Totales: <i> " + horasTotales + " </i></b>\n");
            ret.append("                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
            ret.append("                        </font>\n");
            ret.append("                 </div>\n");
            ret.append("                 <table width=\"100%\" border=\"\" cellpadding=\"1\" cellspacing=\"1\" bordercolor=\"#FFFFFF\" bgcolor=\"#FFFFFF\"\n");
            ret.append("                        onmouseover=\"javascript:showDiv('divStatusBar" + uuid + "'); return true;\"\n");
            ret.append("                        onmouseout=\"javascript:hideDiv('divStatusBar" + uuid + "'); return true;\">\n");
            ret.append("                    <tr>\n");
            ret.append("                        <td width=\"100%\" bgcolor=\"#" + colorFondoBarra + "\">\n");
            ret.append("                            <table class=\"darkBar\" width=\"" + df.format(porcentajeTotal) + "%\" border=\"0\" bgcolor=\"#" + colorBarra + "\">\n");
            ret.append("                               <tr>\n");
            ret.append("                                  <td><b>&nbsp;</b>\n");
            ret.append("                                  </td>\n");
            ret.append("                               </tr>\n");
            ret.append("                            </table>\n");
            ret.append("                        </td>\n");
            ret.append("                    </tr>\n");
            ret.append("                 </table>\n");
            ret.append("              </td>\n");
            ret.append("              <td align=\"left\" width=\"15%\">         " + df.format(porcentajeTotal) + "%\n");
            ret.append("              </td>\n");
            ret.append("           </tr>\n");
            ret.append("        </table>\n");
            ret.append("\n");
            return ret.toString();
        }
        else
            return null;
    }

    //Obtiene los nodos finales
    public ArrayList getListLeaf(WebPage webpage,User user, boolean valid)
    {
        ArrayList result = new ArrayList();
        getListLeafProcess(webpage, user, result,valid);
        return result;
    }
    private void getListLeafProcess(WebPage webpage, User user, ArrayList al, boolean valid)
    {
        Iterator<WebPage> it = webpage.listVisibleChilds(user.getLanguage());
        SemanticObject obj;
        Activity act1;
        if(it.hasNext()){
        while(it.hasNext())
        {
            webpage = (WebPage)it.next();
            if(hasChild(webpage,user)){
                getListLeafProcess(webpage, user, al,valid);}
            else{
                obj= SemanticObject.createSemanticObject(webpage.getURI());
                if(obj.instanceOf(Activity.sclass))
                {
                    act1=(Activity)obj.createGenericInstance();
                    if(valid){
                        al.add(act1.getCurrentHour());}
                    al.add(act1.getCurrentPercentage());
                    al.add(act1.getPlannedHour());
                }
            }
        }
        }else{
                obj= SemanticObject.createSemanticObject(webpage.getURI());
                if(obj.instanceOf(Activity.sclass)){
                    act1=(Activity)obj.createGenericInstance();
                    if(valid){
                        al.add(act1.getCurrentHour());}
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