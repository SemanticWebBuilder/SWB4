<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.*,java.io.PrintWriter,java.text.*,org.semanticwb.model.*,org.semanticwb.platform.*,org.semanticwb.portal.resources.projectdriver.*,org.semanticwb.portal.api.*,org.semanticwb.portal.*,java.sql.Timestamp"%>
<%
        WebPage wp = paramRequest.getWebPage();
        User user = paramRequest.getUser();
        Iterator<UserWebPage> it = UserWebPage.ClassMgr.listUserWebPageByParent(wp, wp.getWebSite());
        Iterator<WebPage> itwp = wp.listVisibleChilds(paramRequest.getUser().getLanguage());
        ArrayList webPage = new ArrayList();
        
        while(itwp.hasNext()){
            WebPage pag = itwp.next();
            String name = pag.getSemanticObject().getSemanticClass().getName();
            if(name.equals("WebPage"))
                webPage.add(pag);
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
   left: 27%;
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
    width:100%;

}
.indentation{
    padding-left:10px;
}
#proyecto .avanTot{
    width:98%;
    float:right;
    height:38px;
}
#proyecto .avanTot .defaultPorcentaje{
    width:2%;
    float:left;
    height:20px;
    background-color: #006BD7;
}
#proyecto .avanTot .porcentajeAvance{

   float: left;
   height: 20px;
   background-color: #006BD7;
}
#proyecto .avanTot .estatusBarra{
   border: 1px none #000000;
   visibility:hidden;
   background-color:#008040;
   margin-left:10px;
   margin-top:4px;
   color:#FFFFFF;
   font-weight: bold;
   text-indent: 15px;
   position: absolute;
   left: 27%;
}
</style>
  <div id="proyecto">
  <%if(it.hasNext())
   {
      Iterator ita;
      ArrayList actValid=new ArrayList();//Lista los actividades validos, Paginas web
      

      //Obtiene usuarios activos, visibles, que no tengan hijos, que no esten escondidos, que sean validos y que no esten borrados
      ArrayList userValid= new ArrayList();
      it=UserWebPage.ClassMgr.listUserWebPageByParent(wp,wp.getWebSite());
      while(it.hasNext()){
          UserWebPage uwp1=it.next();
          if(uwp1.isActive()&& uwp1!=null && uwp1.isVisible()&& uwp1.getChild()==null && !uwp1.isHidden() && uwp1.isValid() && !uwp1.isDeleted())
            userValid.add(uwp1);
      }
      
      //Llena un HashMap, un usuario y la lista de actividades asignadas a el, validas(sin hijos, activas, sin papas activos) y que esten en el contenedor de ese proyecto
      it = userValid.iterator();
      WebPage project = getProject(wp);
      HashMap users = new HashMap();
      while(it.hasNext()){
          UserWebPage uwpi1 = it.next();
          actValid = new ArrayList();
          if(uwpi1.getUserWP()!=null){
              ita=Activity.ClassMgr.listActivityByResponsible(uwpi1.getUserWP(),uwpi1.getWebSite());
              while(ita.hasNext()){
                  WebPage acts= (WebPage)ita.next();
                  boolean valid=validaPage(acts,user.getLanguage());
                  if(valid&&acts.isChildof(project))
                      actValid.add(acts);
              }
          }
          users.put(uwpi1.getURI(), actValid);
      }
      //obtiene todas las actividades
      actValid = new ArrayList();
      it = users.entrySet().iterator();
      while (it.hasNext()) {
          Map.Entry e = (Map.Entry)it.next();
          ArrayList activ = (ArrayList)e.getValue();
          actValid.addAll(activ);
      }

      //Obtiene el porcentaje actual y las horas planeadas para obtener la barra de progreso de avance general
      ita=actValid.iterator();
      actValid=new ArrayList();
      while(ita.hasNext()){
          Activity acts = (Activity) ita.next();
          actValid.add(acts.getCurrentPercentage());
          actValid.add(acts.getPlannedHour());
      }
      String avanTot=getProgressBar(actValid,paramRequest.getLocaleString("msgTotalHours"));
      if(avanTot==null)
          avanTot=paramRequest.getLocaleString("msgNoProgress");//"Sin Avance";
        %>
      <fieldset><legend><%=paramRequest.getLocaleString("labelTotalProgress")%></legend>
          <div class="avanTot"><%=avanTot%></div>
      </fieldset>
<%
        ita=userValid.iterator();
        if(ita.hasNext()){
%>
      <h2><%=paramRequest.getLocaleString("titleAssociatedPersonnel")%></h2>
<%      //Recorre los usuarios validos y recorre las actividades validas asociadas a cada usuario, obtiene la barra de progreso para cada usuario
        while(ita.hasNext()){
            UserWebPage wpu=(UserWebPage)ita.next();
            actValid = new ArrayList();
            ArrayList activ=(ArrayList)users.get(wpu.getURI());
            Iterator itA= activ.iterator();
            while(itA.hasNext()){
                Activity actU = (Activity)itA.next();
                actValid.add(actU.getCurrentPercentage());
                actValid.add(actU.getPlannedHour());
            }
            String avan=getProgressBar(actValid,paramRequest.getLocaleString("msgTotalHours"));
            if(avan==null)
                avan=paramRequest.getLocaleString("msgNoProgress");//"Sin avance";
%>
            <div class="liespa">
                <span class="indentation"><a href="<%=wpu.getUrl()%>"><%=wpu.getDisplayName()%></a></span>
                <div class="espa"><%=avan%></div>
            </div>
         <%
        }
        }
        if(!webPage.isEmpty())
            out.println(printPage(webPage,paramRequest.getLocaleString("titleSections")));
   }else{%>
      <fieldset><legend><%=paramRequest.getLocaleString("labelTotalProgress")%></legend>
         <%=paramRequest.getLocaleString("msgNoUsers")%>
      </fieldset>
<%}%>
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
        private String printPage(ArrayList array, String title)
        {
            Iterator itpr=array.iterator();
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
%>
