<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.*,java.io.PrintWriter,java.text.*,org.semanticwb.model.*,org.semanticwb.platform.*,org.semanticwb.portal.resources.projectdriver.*,org.semanticwb.portal.api.*,org.semanticwb.portal.SWBFormMgr,org.semanticwb.portal.SWBFormButton,java.util.Calendar" %>
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
                       <style type="text/css">
                            #proyecto .defaultPorcentaje{
                                width:2%;
                                float:left;
                                height:20px;
                                background-color: #0099FF;
                            }
                            #proyecto .text{
                               color:#FFFFFF;
                               font-weight: bold;
                               font-style: italic;
                            }
                            #proyecto .estatusBarra{
                               border: 1px none #000000;
                               visibility:hidden;
                               background-color:#008040;
                               margin-left:10px;
                               margin-top:2px;
                               color:#FFFFFF;
                               font-weight: bold;
                               text-indent: 15px;
                               position: absolute;
                               left: 10px;
                               height:18px;
                            }
                            #proyecto .porcentajeAvance {
                               float:left;
                               height: 20px;
                               background-color: #0099FF;
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
                            #proyecto .espa{
                                width:95%;
                                float:right;
                                height:38px;
                            }
                            #proyecto .liespa{
                                width:100%;
                                height:58px;
                            }
                            .indentation{
                                padding-left:10px;
                                width:100%;
                            }
                            #proyecto .avanceProy{
                                width: 100%;
                                padding:4px;
                                height:30px;
                            }
                            #proyecto .datosProyGral{
                                width: 100%;
                                padding:4px;
                                height:15px;
                            }
                            #proyecto .avanIzq{
                                width:13%;
                                float: left;
                                height: 30px;
                                padding-left:3px;
                            }
                            #proyecto .datosProyIzqGral{
                                width:15%;
                                float: left;
                                padding-left:3px;
                            }
                            #proyecto .avanDer{
                                width:85%;
                                float:right;
                                height: 30px;
                            }
                            #proyecto .fechas{
                               width: 100%;
                               padding: 2px;
                               height:30px;
                            }
                            #proyecto .fechas .columnasIzq{
                                width: 50%;
                                float:right;
                            }
                            #proyecto .fechas .columnasDer{
                                width:49%;
                                float:left;
                            }
                            #proyecto .fechas .columIzq{
                                width:50%;
                                float:right;
                            }
                            #proyecto .fechas .columDer{
                                width:49%;
                                float:left;
                            }
                            #proyecto .fechas .tag{
                                width:100%;
                            }
                            #proyecto .fechas .value{
                                width:100%;
                            }
                            #proyecto .avanDer .defaultPorcentaje{
                                width:2%;
                                float:left;
                                height:20px;
                                background-color: #006BD7;
                            }
                            #proyecto .avanDer .porcentajeAvance{
                               width: 95%;
                               float: left;
                               height: 20px;
                               background-color: #006BD7;
                            }
                            #proyecto .avanDer .estatusBarra{
                               border: 1px none #000000;
                               visibility:hidden;
                               background-color:#008040;
                               margin-left:10px;
                               margin-top:2px;
                               color:#FFFFFF;
                               font-weight: bold;
                               text-indent: 15px;
                               position: absolute;
                               left: 10px;
                               height:18px;
                            }
                       </style>
<div id="proyecto">
      <div class="datosProyGral">
          <div class="datosProyIzqGral"><label for="<%=wpPro.swbproy_leader.getName()%>"><%=wpPro.swbproy_leader.getDisplayName(user.getLanguage())%> </label></div>
          <%=wpPro.getLeader()!=null?wpPro.getLeader().getFullName():paramRequest.getLocaleString("msgUnassigned")%>
      </div>
<%
   if((hasChild(wp,user)))
   {
       ArrayList actPageCon=new ArrayList();
       ArrayList actContName=new ArrayList();
       ArrayList webPage=new ArrayList();
       ArrayList proPage=new ArrayList();
       ArrayList usPageCon=new ArrayList();

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
       
       long f1a=0,f1b=0,projectdays2=0,useddays=-1;
       String val=paramRequest.getLocaleString("msgNoProgress");
       if(wpPro.getStartDatep()!=null)
           f1a=wpPro.getStartDatep().getTime();
       if(wpPro.getEndDatep()!=null)
           f1b=wpPro.getEndDatep().getTime();
       if(f1a>0&&f1b>0){
                projectdays2 = calcuteLaborableDays(f1a,f1b);// días laborables disponibles entre las dos fechas definidas
                Calendar hoy = Calendar.getInstance();
                hoy.setTimeInMillis(System.currentTimeMillis());
                useddays = calcuteLaborableDays(f1a,System.currentTimeMillis()); // días laborables al día de hoy
                float avance = ((useddays * 100) / projectdays2);
                if (avance > 100)
                    avance = 100;
                else if(avance<0)
                    avance=0;
                val =getProgressHtml(avance);
                if(val==null) val = paramRequest.getLocaleString("msgNoProgress");
       }
       String avan = getProgressBar(getListLeaf(wp,user,false),paramRequest.getLocaleString("msgTotalHours"));
       if(avan==null) avan = paramRequest.getLocaleString("msgNoProgress");
           %>
                    <div class="datosProy">
                          <div class="avanceProy">
                              <div class="avanIzq"><%=paramRequest.getLocaleString("labelRealProgress")%>:</div>
                              <div class="avanDer"><%=avan%></div>
                          </div>
                          <div class="avanceProy">
                              <div class="avanIzq"><%=paramRequest.getLocaleString("labelExpectedProgress")%>:</div>
                              <div class="avanDer"><%=val%></div>
                          </div>
                          <div class="fechas">
                              <div class="columnasDer">
                                  <div class="columDer">
                                    <div class="tag"><%=paramRequest.getLocaleString("labelStartDate")%>:</div>
                                    <div class="value"><%=wpPro.getStartDatep()!=null?wpPro.getStartDatep().toString().substring(8, 10)+"/" + wpPro.getStartDatep().toString().substring(5, 7)+"/"+wpPro.getStartDatep().toString().substring(0, 4):paramRequest.getLocaleString("msgUnassigned")%></div>
                                  </div>
                                  <div class="columIzq">
                                      <div class="tag"><%=paramRequest.getLocaleString("labelEndDate")%>:</div>
                                      <div class="value"><%=wpPro.getEndDatep()!=null?wpPro.getEndDatep().toString().substring(8, 10)+"/" + wpPro.getEndDatep().toString().substring(5, 7)+"/"+wpPro.getEndDatep().toString().substring(0, 4):paramRequest.getLocaleString("msgUnassigned")%></div>
                                  </div>
                              </div>
                              <div class="columnasIzq">
                                  <div class="columDer">
                                      <div class="tag"><%=paramRequest.getLocaleString("labelDaysRemaining")%>:</div>
                                      <div class="value"><%= projectdays2 %></div>
                                  </div>
                                  <div class="columIzq">
                                      <div class="tag"><%=paramRequest.getLocaleString("labelElapsedDays")%>: </div>
                                      <div class="value"><%= useddays==-1?0:useddays%></div>
                                  </div>
                              </div>
                          </div>
                    </div>
                       <br>
<%
          if(!proPage.isEmpty())
           out.println(printPage(proPage,paramRequest.getLocaleString("titleSubproject"),user,true,paramRequest.getLocaleString("msgTotalHours")));
          if(!actPageCon.isEmpty())
          {
              it=actPageCon.iterator();
              while(it.hasNext()){
                WebPage tp=(WebPage)it.next();
                out.println(getWebPageListLevel(tp,user,paramRequest.getLocaleString("titleListActivities"),paramRequest.getLocaleString("msgTotalHours")));
              }
          }
          if(!usPageCon.isEmpty()){%>                      <br>
<%
              it=usPageCon.iterator();
              while(it.hasNext()){
                WebPage tp = (WebPage)it.next();
                out.println(getListUser(tp,user,actContName,wp,paramRequest.getLocaleString("msgNoProgress"),paramRequest.getLocaleString("titleAssociatedPersonnel"),paramRequest.getLocaleString("msgTotalHours")));
              }
          }%>                      <br>
<%
          if(!webPage.isEmpty())
           out.println(printPage(webPage,paramRequest.getLocaleString("titleSections"),user,false,paramRequest.getLocaleString("msgTotalHours")));
   }
%>
</div>
<%!
       public long calcuteLaborableDays(long a1, long a2) {
            long numdays = 0;
            long oneday = 24 * 60 * 60 * 1000;
            Calendar cal1;
            cal1 = Calendar.getInstance();
            cal1.setTimeInMillis(a1);
            Calendar cal2 = Calendar.getInstance();
            a2=a2+(23*59*59*1000);
            cal2.setTimeInMillis(a2);
            int nodays = 0;
            if (a1 < a2) {
                while (a1 < a2) {
                    if (cal1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                        numdays++;
                    } else {
                        nodays++;
                    }
                    a1 += oneday;
                    cal1.setTimeInMillis(a1);
                }
            } else {
                // Fechas inválidas
                numdays = -1;
            }
            return numdays;
        }

        public String getListUser(WebPage wpUs,User user,ArrayList containers,WebPage project,String label, String titlePersonnel,String titleLan){
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
                          if(valid&&acts.isChildof(project))
                              listActu1.add(acts);
                      }
                  }
                  users.put(uwpi1.getURI(), listActu1);
                  listActu1=new ArrayList();
              }

              //Lista el avance de los usuarios
              itU = listUser.iterator();
              if(itU.hasNext()){
                  buff.append("                       <h2><a href=\""+wpUs.getUrl()+"\">"+titlePersonnel+"</a></h2>\n");
                  buff.append("                        <br>\n");
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
                    String avan=getProgressBar(listActu,titleLan);
                    if(avan==null)
                        avan=label;//"Sin avance"
                    buff.append("                             <div class=\"liespa\">\n");
                    buff.append("                             <div class=\"indentation\"><a href=\""+wpu.getUrl()+"\">"+wpu.getDisplayName()+"</a></div>\n");
                    buff.append("                               <div class=\"espa\">"+avan+"</div>\n");
                    buff.append("                             </div>\n");
                  }
              }
              return buff.toString();
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

        public String getWebPageListLevel(WebPage wp,User user, String title,String titleLan)
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
                st.append("                       <h2><a href=\""+wp.getUrl()+"\">"+title+"</a></h2>\n");
                st.append("                        <br>\n");
                while(it.hasNext())
                {
                    wp = (WebPage)it.next();
                    st.append("                             <div class=\"liespa\">");
                    st.append("                               <div class=\"indentation\"><a href=\""+wp.getUrl()+"\">"+wp.getDisplayName()+"</a></div>\n");
                    st.append("                               <div class=\"espa\">"+ getProgressBar(getListLeaf(wp,user,false),titleLan)+"</div>");
                    st.append("                             </div>");
                }
            }
            return st.toString();
        }

        private String printPage(ArrayList mpag, String title,User user,boolean progressBar,String titleLan)
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
                        strb.append(getProgressBar(getListLeaf(wpage,user,false),titleLan));
                    }
                }
                strb.append("                  </ul>");
            }
            return strb.toString();
        }
        
        private String getProgressBar(ArrayList info, String titleLan)
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
        
        private String getProgressHtml(float porcentajeTotal){
            StringBuffer bf = new StringBuffer();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("###.##", dfs);
            bf.append("        <div class=\"contenedor\">\n");
            bf.append("            <div class=\"barraProgreso\">\n");
            bf.append("                 <div class=\"defaultPorcentaje\"></div>\n");
            bf.append("                 <div class=\"porcentaje\">\n");
            bf.append("                     <div class=\"porcentajeAvance\" style=\"width:"+df.format(porcentajeTotal)+"%\"></div>\n");
            bf.append("                 </div>\n");
            bf.append("            </div>\n");
            bf.append("            <div class=\"tag_porcentaje\">"+ df.format(porcentajeTotal) +"%</div>\n");
            bf.append("         </div>\n");
            return bf.toString();
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