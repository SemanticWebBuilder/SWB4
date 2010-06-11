<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.*,java.io.PrintWriter,java.text.*,org.semanticwb.model.*,org.semanticwb.platform.*,org.semanticwb.portal.resources.projectdriver.*,org.semanticwb.portal.api.*,org.semanticwb.portal.SWBFormMgr,org.semanticwb.portal.SWBFormButton" %>
<%
    Iterator it;
    WebPage wp=paramRequest.getWebPage();
    User user=paramRequest.getUser();
    SemanticObject obj = SemanticObject.createSemanticObject(wp.getURI());
    Project wpPro = (Project)obj.createGenericInstance();
    SWBResourceURL url = paramRequest.getActionUrl();
%>                       <script type="text/javascript">
                           function hideDiv(objDIV) {
                               document.getElementById(objDIV).style.visibility = 'hidden';
                           }
                           function showDiv(objDIV) {
                               document.getElementById(objDIV).style.visibility = 'visible';
                           }
                       </script>
<%
   if((hasChild(wp,user)))
   {
       ArrayList actPageCon=new ArrayList();
       ArrayList actContName=new ArrayList();
       ArrayList webPage=new ArrayList();
       ArrayList proPage=new ArrayList();
       ArrayList usPageCon=new ArrayList();
       String hourPlan="", hourCurren="", percen="", start="",end="";

       ArrayList hours = calculateHours(wp,user);
       it = hours.iterator();
       while(it.hasNext()){
           hourPlan = it.next().toString();
           hourCurren = it.next().toString();
           percen = it.next().toString();
       }

       it=paramRequest.getWebPage().listVisibleChilds(paramRequest.getUser().getLanguage());
       while(it.hasNext())
       {
           WebPage page1=(WebPage)it.next();
           String namewp=page1.getSemanticObject().getSemanticClass().getName();
           if(namewp.equals("ActivityContainer")){
               actPageCon.add(page1);
               actContName.add(page1.getDisplayName());
           }
           else if(namewp.equals("WebPage"))
               webPage.add(page1);
           else if(namewp.equals("Project"))
               proPage.add(page1);
           else if(namewp.equals("UserWebPageContainer"))
               usPageCon.add(page1);
       }

       ArrayList dates=calculateDates(wp,user,actContName,actPageCon);
       it=dates.iterator();
       while(it.hasNext()){
           start = it.next().toString();
           if(start.equals(""))
              start = "Sin fecha asignada";
           if(it.hasNext())
               end = it.next().toString();
           if(end.equals(""))
               end = "Sin fecha asignada";
       }

        ArrayList responsible = listUserRepository(wp.getWebSite());
        it=responsible.iterator();
        url.setParameter("uri", obj.getURI());
        url.setAction("updatepro");
           %>
                       <form id="<%=obj.getURI()+"/form"%>" name="<%=obj.getURI()+"/form"%>" class="edit" action="<%=url.toString()%>" method="post">
                          <table class="dates" width="97%">
                            <tr><td width="5%"></td><th><label for="<%=wpPro.swbproy_leader.getName()%>"><%=wpPro.swbproy_leader.getDisplayName()%> </label></th>
                                <%if(user.isRegistered()){%>
                                <td><select style="width:120px" name="<%=wpPro.swbproy_leader.getName()%>"><%
                                String uri="";
                                if(wpPro.getLeader()!=null)
                                    uri = wpPro.getLeader().getURI();
                                 while(it.hasNext()){
                                    SemanticObject sob = SemanticObject.createSemanticObject(it.next().toString());
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
                                    <%= SWBFormButton.newSaveButton().renderButton(request,SWBFormMgr.TYPE_XHTML,user.getLanguage())%>
                                </td>
                               <%}else{%>
                               <td><%=wpPro.getLeader()!=null?wpPro.getLeader().getFullName():"Sin Asignar"%></td>
                               <%}%>
                            </tr>
                            <tr>
                               <td width="5%"></td>
                               <th><label>Fecha Inicial:</label></th>
                               <td><%=start%></td>
                               <th><label>Horas Actuales:</label></th>
                               <td width="20%"><%=hourCurren%></td>
                            </tr>
                            <tr>
                               <td width="5%"></td>
                               <th><label>Fecha Final:</label></th>
                               <td><%=end%></td>
                               <th><label>Avance:</label></th>
                               <td></td>
                            </tr>
                            <tr>
                               <td width="5%"></td>
                               <th colspan="5">
          <%=getProgressBar(getListLeaf(wp,user,false),"66CCFF",null)%>
                               </th>
                            </tr>
                          </table>
                       </form>
                       <br>
<%
          if(!proPage.isEmpty())
           out.println(printPage(proPage,"Subproyectos",user,true));
          if(!actPageCon.isEmpty())
          {
              it=actPageCon.iterator();
              while(it.hasNext()){
                WebPage tp=(WebPage)it.next();
                out.println(getWebPageListLevel(tp,user,"Lista de Actividades"));
              }
          }
          if(!usPageCon.isEmpty()){%>                      <br>
<%
              it=usPageCon.iterator();
              while(it.hasNext()){
                WebPage tp = (WebPage)it.next();
                out.println(getListUser(tp,user,actContName));
              }
          }%>                      <br>
<%
          if(!webPage.isEmpty())
           out.println(printPage(webPage,"Secciones",user,false));
   }
%>
<%!
        public String getListUser(WebPage wpUs,User user,ArrayList containers){
              StringBuffer buff = new StringBuffer();
              ArrayList listUser=new ArrayList();
              UserWebPage uwpi;
              Iterator itA;
              ArrayList listActu = new ArrayList();

              //Obtiene los usuarios del sitio que esten tengan activas las paginas de usuario activa
              Iterator<UserWebPage> itU = UserWebPage.ClassMgr.listUserWebPageByParent(wpUs, wpUs.getWebSite());
              while(itU.hasNext()){
                   uwpi= itU.next();
                  if(uwpi.isActive()&& uwpi!=null && uwpi.isVisible()&& uwpi.getChild()==null && !uwpi.isHidden() && uwpi.isValid() && !uwpi.isDeleted())
                    listUser.add(uwpi);
              }

              //obtiene las actividades por contenedor
             ArrayList containActs = getActivitiesByProject(containers,wpUs.getWebSite());

              //Llena un HashMap, un usuario y la lista de actividades asignadas a el, validas(sin hijos, activas, sin papas activos) y que esten en el contenedor de ese proyecto
              itU = listUser.iterator();
              HashMap users = new HashMap();
              ArrayList listActu1 = new ArrayList();
              UserWebPage uwpi1;
              while(itU.hasNext()){
                  uwpi1 = itU.next();
                  if(uwpi1.getUserWP()!=null){
                      itA=Activity.ClassMgr.listActivityByResponsible(uwpi1.getUserWP(),uwpi1.getWebSite());
                      while(itA.hasNext()){
                          WebPage acts= (WebPage)itA.next();
                          boolean valid=validaPage(acts,user.getLanguage());
                          if(valid&&containActs.contains(acts))
                              listActu1.add(acts);
                      }
                  }
                  users.put(uwpi1.getURI(), listActu1);
                  listActu1=new ArrayList();
              }

              //Lista el avance de los usuarios
              itU = listUser.iterator();
              if(itU.hasNext()){
                  buff.append("                       <h2>Personal Asociado</h2>\n");
                  buff.append("                        <br>\n");
                  buff.append("                           <ul>\n");
                  while(itU.hasNext()){
                    UserWebPage wpu=(UserWebPage)itU.next();
                    ArrayList activ=(ArrayList)users.get(wpu.getURI());
                    itA = activ.iterator();
                    listActu = new ArrayList();
                    if(wpu.getUserWP()!=null){
                        while(itA.hasNext()){
                            Activity actU = (Activity)itA.next();
                            listActu.add(actU.getCurrentPercentage());
                            listActu.add(actU.getPlannedHour());
                        }
                    }
                    String avan=getProgressBar(listActu,null,null);
                    if(avan==null)
                        avan="Sin avance";
                    buff.append("                             <li><a href=\""+wpu.getUrl()+"\">"+wpu.getDisplayName()+"</a></li>\n");
                    buff.append(avan+"\n");
                  }
                  buff.append("                          </ul>\n");
              }
              return buff.toString();
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

        public String getWebPageListLevel(WebPage wp,User user, String title)
        {
            Iterator<Activity> it = Activity.ClassMgr.listActivityByParent(wp,wp.getWebSite());
            ArrayList ChildVisible=new ArrayList();
            while(it.hasNext())
            {
                Activity wp1=(Activity)it.next();
                if(wp1.isVisible())
                    ChildVisible.add(wp1);
            }
            it=ChildVisible.iterator();
            StringBuffer st=new StringBuffer();
            if(it.hasNext()){
                st.append("                       <h2>"+title+"</h2>\n");
                st.append("                        <br>\n");
                st.append("                          <ul>\n");
                while(it.hasNext())
                {
                    wp = (WebPage)it.next();
                    st.append("                             <li><a href=\""+wp.getUrl()+"\">"+wp.getDisplayName()+"</a></li>\n");
                    st.append( getProgressBar(getListLeaf(wp,user,false),null,null));
                }
                st.append("                          </ul>\n");
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

        private ArrayList calculateDates(WebPage proy, User user, ArrayList containerActs,ArrayList containers){
            ArrayList Dates=new ArrayList();
            ArrayList validAct = new ArrayList();
            String startDateS="",endDateS="";

            //Obtiene las actividades por contenedores de proyecto
            ArrayList actsProy=getActivitiesByProject(containerActs,proy.getWebSite());

            //Obtiene las actividades validas para el sitio
            Iterator listAct=actsProy.iterator();
            while(listAct.hasNext()){
                WebPage wp1=(WebPage)listAct.next();
                 boolean valid=validaPage(wp1,user.getLanguage());
                 if(valid)
                   validAct.add(wp1);
            }

            //valida si obtiene la fecha de finalizacion
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

            //valida si obtiene la fecha de inicio
            boolean validStart=true;
            Iterator check = containers.iterator();
            if(check.hasNext()){
            while(check.hasNext()){
                WebPage cont = (WebPage)check.next();
                if(!cont.isActive())
                    validStart=false;
                if(check.hasNext()&&validStart==false)
                    validStart=true;
            }
            }else
                validStart=false;

            if(validStart){
                //Obtiene las fechas Inicio/Fin de las actividades que esten en Desarrollo, terminadas, pauasadas o canceladas
                listAct = validAct.iterator();
                HashMap startDate = new HashMap();
                HashMap endDate=new HashMap();
                while(listAct.hasNext()){
                    Activity actx1 = (Activity)listAct.next();
                    if(actx1.getStatus()!=null&&(actx1.getStatus().equals("develop")||actx1.getStatus().equals("ended")||actx1.getStatus().equals("canceled")||actx1.getStatus().equals("paused"))){
                        if(actx1.getStartDate()!=null)
                            startDate.put(actx1.getStartDate().getTime(), actx1.getStartDate().toString());
                        if(actx1.getEndDate()!=null)
                            endDate.put(actx1.getEndDate().getTime(), actx1.getEndDate().toString());
                    }
                }
                //Obtiene la fecha de inicio de todas las actividades
                Iterator it = startDate.entrySet().iterator();
                boolean first = true;
                long number2=0;
                long number1=0;
                long value=0;
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry)it.next();
                    number1 = Long.parseLong(e.getKey().toString());
                    if(it.hasNext()&&first){
                        Map.Entry e1 = (Map.Entry)it.next();
                        number2 = Long.parseLong(e1.getKey().toString());
                        first=false;
                        if(number1<number2)
                            value=number1;
                        else
                            value=number2;
                    }else{
                        if(value!=0){
                            if(value>number1)
                                value=number1;
                        }else
                            value=number1;
                    }
                }
                if(value!=0)
                   startDateS=(String)startDate.get(value);
                //Obtiene la fecha de termino de todas las actividades
                if(validEnd){
                    it = startDate.entrySet().iterator();
                    first = true;
                    value=number1=number2=0;
                    while(it.hasNext()){
                        Map.Entry e = (Map.Entry)it.next();
                        number1 = Long.parseLong(e.getKey().toString());
                        if(it.hasNext()&&first){
                            Map.Entry e1 = (Map.Entry)it.next();
                            number2 = Long.parseLong(e1.getKey().toString());
                            first=false;
                            if(number1>number2)
                                value=number1;
                            else
                                value=number2;
                        }else{
                            if(value!=0){
                                if(value<number1)
                                    value=number1;
                            }else
                                value=number1;
                        }
                    }
                    if(value!=0)
                        endDateS=(String)endDate.get(value);
                }
            }
            Dates.add(startDateS);
            Dates.add(endDateS);
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
            strb.append("");
            if(itpr.hasNext())
            {
                strb.append("               <h2>"+title+"</h2>\n");
                strb.append("                  <ul>\n");
                while(itpr.hasNext()){
                    WebPage wpage=(WebPage)itpr.next();
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
                ret.append("                             <table border=0 width=\"91%\" bgcolor=\"#FFFFFF\">\n");
                ret.append("                                <tr >\n");
                ret.append("                                   <td align=\"left\" width=\"75%\">\n");
                ret.append("                                      <div id=\"divStatusBar" + uuid + "\" name=\"divStatusBar" + uuid + "\" style=\"position: absolute; border: 1px none #000000; visibility:hidden; background-color:#008040; margin-left:10px; margin-top:7px;\">\n");
                ret.append("                                             <font color=\"#FFFFFF\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Horas Totales: <i> " + horasTotales + " </i></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
                ret.append("                                             </font>\n");
                ret.append("                                      </div>\n");
                ret.append("                                      <table width=\"100%\" border=\"\" cellpadding=\"1\" cellspacing=\"1\" bordercolor=\"#FFFFFF\" bgcolor=\"#FFFFFF\"\n");
                ret.append("                                             onmouseover=\"javascript:showDiv('divStatusBar" + uuid + "'); return true;\"\n");
                ret.append("                                             onmouseout=\"javascript:hideDiv('divStatusBar" + uuid + "'); return true;\">\n");
                ret.append("                                         <tr>\n");
                ret.append("                                             <td width=\"100%\" bgcolor=\"#" + colorFondoBarra + "\">\n");
                ret.append("                                                 <table class=\"darkBar\" width=\"" + df.format(porcentajeTotal) + "%\" border=\"0\" bgcolor=\"#" + colorBarra + "\">\n");
                ret.append("                                                    <tr><td><b>&nbsp;</b></td></tr>\n");
                ret.append("                                                 </table>\n");
                ret.append("                                             </td>\n");
                ret.append("                                         </tr>\n");
                ret.append("                                      </table>\n");
                ret.append("                                   </td>\n");
                ret.append("                                   <td align=\"left\" width=\"15%\">         " + df.format(porcentajeTotal) + "%\n");
                ret.append("                                   </td>\n");
                ret.append("                                </tr>\n");
                ret.append("                             </table>\n");
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
                        getListLeafProcess(webpage, user, al,valid);
                    }else{
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