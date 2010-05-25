<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.*,java.io.PrintWriter,java.text.*,org.semanticwb.model.*,org.semanticwb.platform.*,org.semanticwb.portal.resources.projectdriver.*,org.semanticwb.portal.api.*,org.semanticwb.portal.*,java.sql.Timestamp"%>
<%

            WebPage wp=paramRequest.getWebPage();
            String parent="";
            WebPage wp1=wp.getParent();
            User user=paramRequest.getUser();
            Iterator it=wp.listVisibleChilds(user.getLanguage());
            boolean p;p=false;
            while(!p){
                SemanticObject obj1= SemanticObject.createSemanticObject(wp1.getURI());
                if(obj1.instanceOf(Project.sclass)){
                    parent=wp1.getDisplayName();p=true;}
                wp1=wp1.getParent();
            }
%><script type="text/javascript">
    function hideDiv(objDIV) {
        document.getElementById(objDIV).style.visibility = 'hidden';
    }
    function showDiv(objDIV) {
        document.getElementById(objDIV).style.visibility = 'visible';
    }
</script>
   <div id="fondoGrande">
      <div id="subgral">
        <h1><%=wp.getDisplayName()%></h1><%
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
        <table width="100%">
          <tr>
            <td>Proyecto: </td>
            <td><%=parent%></td></tr>
          <tr><td>Procentaje de Avance: </td>
            <td>
            <%=getProgressBar(getListLeaf(wp,user),"66CCFF",null)%>
            </td></tr>
        </table>
      <%
          out.println(getWebPageListLevel(wp,user,4,"Actividades"));
          if(!webPage.isEmpty())
            out.println(printPage(webPage,"Secciones"));
          }%>
      </div>
   </div>
<%!
        private String printPage(HashMap mpag, String title)
        {
            Iterator itpr=mpag.values().iterator();
            StringBuffer strb = new StringBuffer();
            WebPage wpage;
            strb.append("");
            if(itpr.hasNext())
            {
                strb.append("<h2>"+title+"</h2>\n");
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
                ret.append("        <table border=0 width=\"100%\" bgcolor=\"#FFFFFF\">\n");
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
        public ArrayList getListLeaf(WebPage wp,User user)
        {
            ArrayList result = new ArrayList();
            getListLeafProcess(wp, user, result);
            return result;
        }
        private void getListLeafProcess(WebPage wp1, User user, ArrayList al)
        {
            Iterator<Activity> it = Activity.ClassMgr.listActivityByParent(wp1,wp1.getWebSite());
            Activity act;
            ArrayList ChildVisible=new ArrayList();
            while(it.hasNext())
            {
                WebPage wp=(WebPage)it.next();
                if(wp.isVisible())
                    ChildVisible.add(wp);
            }
            it=ChildVisible.iterator();
            if(it.hasNext()){
                while(it.hasNext())
                {
                    act = (Activity)it.next();
                    if(hasChild(act,user)){
                        getListLeafProcess(act, user, al);}
                    else{
                            al.add(act.getCurrentPercentage());
                            al.add(act.getPlannedHour());
                    }
                }
            }else{
                    SemanticObject obj= SemanticObject.createSemanticObject(wp1.getURI());
                        act=(Activity)obj.createGenericInstance();
                        al.add(act.getCurrentPercentage());
                        al.add(act.getPlannedHour());
            }
        }
        private boolean hasChild(Activity act,User user)
        {
            boolean result = false;
            WebPage webpage = (WebPage)act;
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
        public String getWebPageListLevel(WebPage wp1,User user, int level, String title)
        {
            level=level+wp1.getLevel();
            Iterator<Activity> it = Activity.ClassMgr.listActivityByParent(wp1,wp1.getWebSite());
            Activity act;
            ArrayList ChildVisible=new ArrayList();
            while(it.hasNext())
            {
                WebPage wp=(WebPage)it.next();
                if(wp.isVisible())
                    ChildVisible.add(wp);
            }
            it=ChildVisible.iterator();
            StringBuffer st=new StringBuffer();
            if(it.hasNext()){
                st.append("<h2>"+title+"</h2>\n");
                st.append("   <ul>\n");
                while(it.hasNext())
                {
                    act = (Activity)it.next();
                        String pgrb = getProgressBar(getListLeaf(act,user),null,null);
                        if(level == act.getLevel()){
                            st.append("      <li><a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a></li>\n");
                            st.append(pgrb);
                        }
                        else if(pgrb!=null){
                            st.append("      <li><a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a></li>\n");
                            st.append(pgrb);
                            StringBuffer st1=new StringBuffer();
                            String childp=getWebPageListLevel1(act,user,level,st1);
                            st.append(childp);
                        }
                }
                st.append("   </ul>\n");
            }
            return st.toString();
        }
        public String getWebPageListLevel1(Activity act, User user, int level,StringBuffer st1)
        {
            Iterator<Activity> it = Activity.ClassMgr.listActivityByParent(act,act.getWebSite());
            ArrayList ChildVisible=new ArrayList();
            while(it.hasNext())
            {
                WebPage wp=(WebPage)it.next();
                if(wp.isVisible())
                    ChildVisible.add(wp);
            }
            it=ChildVisible.iterator();
            if(it.hasNext()){
                st1.append("         <ul>\n");
                while(it.hasNext())
                {
                    act = (Activity)it.next();
                        String pgrb = getProgressBar(getListLeaf(act,user),null,null);
                        if(level == act.getLevel()){
                            st1.append("            <li><a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a></li>\n");//+itx.next()+"</li>");
                            st1.append(pgrb);
                        }
                        else if (pgrb!=null){
                            st1.append("            <li><a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a></li>\n");
                            st1.append(pgrb);
                            getWebPageListLevel1(act,user,level,st1);
                        }
                }
                st1.append("         </ul>\n");
            }
            return st1.toString();
        }
%>
