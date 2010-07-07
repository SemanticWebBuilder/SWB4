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
    String avan = getProgressBar(getListLeaf(wp,user),paramRequest.getLocaleString("msgTotalHours"));
    if(avan==null)avan = paramRequest.getLocaleString("msgNoProgress");//"Sin avance";
%><script type="text/javascript">
    function hideDiv(objDIV) {
        document.getElementById(objDIV).style.visibility = 'hidden';
    }
    function showDiv(objDIV) {
        document.getElementById(objDIV).style.visibility = 'visible';
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
   left:10px;
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
    position: relative;
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
#proyecto .datos .barraDatos{
    width: 68%;
    float: left;
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
</style>
    <div id="proyecto">
        <div class="datos">
            <div class="global">
                <div class="etiquetas"><%=paramRequest.getLocaleString("titleProject")%>: </div>
                <div class="elementos"><%=parent%></div>
            </div>
            <div class="global">
                <div class="etiquetas"><%=paramRequest.getLocaleString("labelActivitiesProgress")%>: </div>
                <div class="barraDatos"><%=avan%></div>
            </div>
        </div>
<%
          if(it.hasNext())
          {//Si tiene hijos muestra una lista de ellos
            ArrayList webPage=new ArrayList();
            while(it.hasNext()){
              WebPage page1=(WebPage)it.next();
              String namewp=page1.getSemanticObject().getSemanticClass().getName();
              if(namewp.equals("WebPage"))
              webPage.add(page1);
            }
            out.println(getWebPageListLevel(wp,user,4,paramRequest.getLocaleString("titleActivities"),"",paramRequest.getLocaleString("msgTotalHours")));
            if(!webPage.isEmpty())
              out.println(printPage(webPage,paramRequest.getLocaleString("titleSections")));
          }%>
    </div>
<%!
        private String printPage(ArrayList mpag, String title)
        {
            Iterator it=mpag.iterator();
            StringBuffer strb = new StringBuffer();
            strb.append("");
            if(it.hasNext())
            {
                strb.append("<h2>"+title+"</h2>\n");
                strb.append("<br>\n");
                strb.append("   <ul>\n");
                while(it.hasNext()){
                    WebPage wpage=(WebPage)it.next();
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
        public ArrayList getListLeaf(WebPage wp, User user){
            Iterator<Activity> it = Activity.ClassMgr.listActivities(wp.getWebSite());
            ArrayList result = new ArrayList();
            while(it.hasNext()){
                Activity act = it.next();
                if((act.isVisible()&&parentActive(act)&&!hasChild(act,user)&&act.isChildof(wp))||(act.isVisible()&&parentActive(act)&&!hasChild(act,user)&&act.equals(wp))){
                    result.add(act.getCurrentPercentage());
                    result.add(act.getPlannedHour());
                }
            }
            return result;
        }
        private boolean parentActive(WebPage wp){
            WebPage parent=wp.getParent();
            boolean haveParent=true;
            while(parent!=null){
                if(!parent.isActive())
                   haveParent=false;
                parent=parent.getParent();
            }
            return haveParent;
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
        public String getWebPageListLevel(WebPage wp1,User user, int level, String title, String indentation,String titleLan)
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
                WebPage wp=(WebPage)it.next();
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
%>
