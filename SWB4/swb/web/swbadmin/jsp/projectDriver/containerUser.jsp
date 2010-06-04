<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.*,java.io.PrintWriter,java.text.*,org.semanticwb.model.*,org.semanticwb.platform.*,org.semanticwb.portal.resources.projectdriver.*,org.semanticwb.portal.api.*,org.semanticwb.portal.*,java.sql.Timestamp"%>
<%
        WebPage wp = paramRequest.getWebPage();
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
  <%if(it.hasNext())
   {
      ArrayList userValid= new ArrayList();//Lista los usuarios validos, paginas web
      Iterator array;
      ArrayList actUser=new ArrayList();
      ArrayList actValid=new ArrayList();//Lista los actividades validos, Paginas web
      WebPage parent;
      //Obtiene usuarios activos, visibles, que no tengan hijos, que no esten escondidos, que sean validos y que no esten borrados
      it=UserWebPage.ClassMgr.listUserWebPageByParent(wp,wp.getWebSite());
      while(it.hasNext()){
          UserWebPage uwp1=it.next();
          if(uwp1.isActive()&& uwp1!=null && uwp1.isVisible()&& uwp1.getChild()==null && !uwp1.isHidden() && uwp1.isValid() && !uwp1.isDeleted())
            userValid.add(uwp1);
      }
      //Obtiene de los usuarios validos, las actividades correspondientes a cada uno
      it=userValid.iterator();
      while(it.hasNext()){
          UserWebPage uwp2=(UserWebPage)it.next();
          if(uwp2.getUserWP()!=null){
           //Obtiene la lista de las actividads por responsable
            array=Activity.ClassMgr.listActivityByResponsible(uwp2.getUserWP(),uwp2.getWebSite());
            while(array.hasNext())
              actUser.add(array.next());
          }
      }
      //Obtiene las actividades válidas que no sean hijas y que ninguno de sus papas este desactivado
      array = actUser.iterator();
      while(array.hasNext()){
          WebPage wp1=(WebPage)array.next();
          boolean valid=true;
          parent = wp1.getParent();
          //Actividad sin papa desactivado
          while(parent!=null){
              if(!parent.isActive())
                  valid=false;
              parent=parent.getParent();
          }
          //Actividad con hijos activos
          if(wp1.getChild()!=null){
              Iterator itA = wp1.listVisibleChilds(paramRequest.getUser().getLanguage());
              while(itA.hasNext()){
                  WebPage ch = (WebPage)itA.next();
                  if(ch.isActive())
                     valid = false;
              }
          }
          if(!wp1.isActive()|| wp1==null || !wp1.isVisible()||wp1.isHidden() || !wp1.isValid() || wp1.isDeleted())
             valid=false;
          if(valid)
              actValid.add(wp1);
      }
      //Obtiene el porcentaje actual y las horas planeadas para obtener la barra de progreso de avance general
      ArrayList listAct = new ArrayList();
      array=actValid.iterator();
      while(array.hasNext()){
          Activity acts = (Activity) array.next();
          listAct.add(acts.getCurrentPercentage());
          listAct.add(acts.getPlannedHour());
      }
      String avanTot=getProgressBar(listAct,"66CCFF",null);
      if(avanTot==null)
          avanTot="Sin Avance";
        %>
      <fieldset><legend>Avance Total</legend>
         <%=avanTot%>
      </fieldset>
<%
        array=userValid.iterator();
        if(array.hasNext()){
%>
      <h2>Personal Asociado</h2>
         <ul>
<%      //Recorre los usuarios validos y recorre las actividades validas asociadas a cada usuario, obtiene la barra de progreso para cada usuario
        while(array.hasNext()){
            UserWebPage wpu=(UserWebPage)array.next();
            Iterator itA = actValid.iterator();
            listAct = new ArrayList();
            while(itA.hasNext()){
                Activity actU = (Activity)itA.next();
                if(actU.getResponsible().equals(wpu.getUserWP()))
                {
                    listAct.add(actU.getCurrentPercentage());
                    listAct.add(actU.getPlannedHour());
                }
            }
            String avan=getProgressBar(listAct,null,null);
            if(avan==null)
                avan="Sin avance";
%>
            <li><a href="<%=wpu.getUrl()%>"><%=wpu.getDisplayName()%></a></li>
            <%=avan%>
         <%
            }%>
         </ul>
<%
        }
        if(!webPage.isEmpty())
            out.println(printPage(webPage,"Secciones"));
}%>
<%!     private String printPage(ArrayList array, String title)
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
                ret.append("        <table border=0 width=\"90%\" bgcolor=\"#FFFFFF\">\n");
                ret.append("          <tr >\n");
                ret.append("            <td align=\"left\" width=\"75%\">\n");
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
%>
