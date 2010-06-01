<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.*" %>
<%@page import="java.text.*"%>
<%@page import="org.semanticwb.portal.resources.projectdriver.*" %>
<%@page import="java.io.PrintWriter" %>
<%@page import="org.semanticwb.model.*" %>
<%@page import="org.semanticwb.platform.*" %>
<%@page import="org.semanticwb.portal.api.*" %>
<%@page import="org.semanticwb.portal.SWBFormMgr" %>
<%@page import="org.semanticwb.portal.SWBFormButton" %>

<%
        User user=paramRequest.getUser();
        WebPage wp = paramRequest.getWebPage();
        SemanticObject obj=SemanticObject.createSemanticObject(wp.getURI());
        UserWebPage userwp = (UserWebPage)obj.createGenericInstance();
        Iterator<UserWebPage> it = UserWebPage.ClassMgr.listUserWebPageByParent(userwp, userwp.getWebSite());
        Iterator<WebPage> childPag=paramRequest.getWebPage().listVisibleChilds(paramRequest.getUser().getLanguage());
        SWBResourceURL url=paramRequest.getActionUrl();
        String speciality = "Sin Asignar";
        if(userwp.getSpeciality()!=null)
            speciality = userwp.getSpeciality();
%><script type="text/javascript">
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

   }else{//que hacer en caso de que no exista asociado un usuario a esta pagina
        Iterator<Activity> actResp=Activity.ClassMgr.listActivityByResponsible(userwp.getUserWP(),userwp.getWebSite());
        ArrayList actValid=new ArrayList();
        //Obtener las Actividades validas
        while(actResp.hasNext()){
          WebPage wp1=(WebPage)actResp.next();
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
              Iterator itA = wp1.listVisibleChilds(paramRequest.getUser().getLanguage());
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
              actValid.add(wp1);
        }

        ArrayList assig=new ArrayList();
        ArrayList canc=new ArrayList();
        ArrayList devel=new ArrayList();
        ArrayList paus=new ArrayList();
        ArrayList end =new ArrayList();
        Iterator itActs = actValid.iterator();

        while(itActs.hasNext()){
            Activity actRes=(Activity)itActs.next();
            if(actRes.getStatus().equals("assigned")){
                assig.add(actRes.getURI());
            }
            else if(actRes.getStatus().equals("canceled"))
                canc.add(actRes.getURI());
            else if(actRes.getStatus().equals("develop"))
                devel.add(actRes.getURI());
            else if(actRes.getStatus().equals("paused"))
                paus.add(actRes.getURI());
            else if(actRes.getStatus().equals("ended"))
                end.add(actRes.getURI());
        }
        //Obtiene el porcentaje actual y las horas planeadas para obtener la barra de progreso de avance general
        ArrayList listAct = new ArrayList();
        itActs=actValid.iterator();
        while(itActs.hasNext()){
          Activity acts = (Activity) itActs.next();
          listAct.add(acts.getCurrentPercentage());
          listAct.add(acts.getPlannedHour());
        }
        String avanTot=getProgressBar(listAct,null,null);
        if(avanTot==null)
          avanTot="Sin Avance";%>
        <%
         if(user.isRegistered()){
             ArrayList responsible = listUserRepository(wp.getWebSite());
             Iterator res=responsible.iterator();
             SWBFormMgr mgr=new SWBFormMgr(obj,null,SWBFormMgr.MODE_EDIT);
             mgr.setLang(user.getLanguage());
             mgr.setType(mgr.TYPE_XHTML);
             url.setParameter("uri", obj.getURI());
             url.setAction("updateus");
             mgr.addHiddenParameter(UserWebPage.swb_active.getName(), Boolean.toString(userwp.isActive()));
         %><form id="<%=mgr.getFormName()%>" name="<%=mgr.getFormName()%>" class="edit" action="<%=url.toString()%>" method="post">
                        <%=mgr.getFormHiddens()%>
                        <table class="detail">
                            <tr><td width="200px" align="right"><%=mgr.renderLabel(request, userwp.swbproy_userWP,mgr.MODE_EDIT)%></td>
                                <td><select name="<%=userwp.swbproy_userWP.getName()%>"><%
                                String uri="";
                                if(userwp.getUserWP()!=null)
                                    uri = userwp.getUserWP().getURI();
                                
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
                            <tr><td width="200px" align="right"><%=mgr.renderLabel(request, userwp.swbproy_speciality,mgr.MODE_EDIT)%></td>
                                <td><%=mgr.renderElement(request, userwp.swbproy_speciality,mgr.MODE_EDIT)%></td>
                            </tr>
                        </table>
                        <div>
                            <p align="center">
                                <%= SWBFormButton.newSaveButton().renderButton(request,SWBFormMgr.TYPE_XHTML,mgr.getLang())%>
                            </p>
                        </div>
           </form><%
        }else{
 %>
            <br>
            <label for="speciality">Especialidad: </label>
            <span name="speciality"><%=speciality%></span>
            <br>
<%
        }
        %>
         <%=avanTot%>
        <%
        if(!assig.isEmpty()){
        %>
        <h2>Actividades Asignadas</h2>
        <%String x=printStatusActivity(assig);
        out.println(x);
        }
        if(!devel.isEmpty()){
        %>
        <h2>Actividades En Desarrollo</h2>
        <%String x=printStatusActivity(devel);
        out.println(x);
        }
        if(!paus.isEmpty()){
        %>
        <h2>Actividades Pausadas</h2>
        <%String x=printStatusActivity(paus);
        out.println(x);
        }
        if(!canc.isEmpty()){
        %>
        <h2>Actividades Canceladas</h2>
        <%String x=printStatusActivity(canc);
        out.println(x);
        }
        if(!end.isEmpty()){
        %>
        <h2>Actividades Finalizadas</h2>
        <%String x=printStatusActivity(end);
        out.println(x);
        }

   }
%>
<%!
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
private String printStatusActivity(ArrayList listActs){
    StringBuffer buf = new StringBuffer();
    Iterator list = listActs.iterator();

    buf.append("");
    if(list.hasNext()){
        buf.append("<ul>");
        while(list.hasNext()){
            ArrayList lprogress= new ArrayList();
            String uri = (String) list.next();
            SemanticObject ob = SemanticObject.createSemanticObject(uri);
            Activity act = (Activity)ob.createGenericInstance();
            lprogress.add(act.getCurrentPercentage());
            lprogress.add(act.getPlannedHour());
            buf.append("<li><a href=\""+act.getUrl()+"\">"+act.getDisplayName()+"</a></li>");
            buf.append(getProgressBar(lprogress,null,null));
        }
        buf.append("</ul>");
    }

    return buf.toString();
}

private ArrayList checkVisibleChild(Iterator array){
    ArrayList ChildVisible =new ArrayList();
    while(array.hasNext()){
        WebPage wp=(WebPage)array.next();
        if(wp.isVisible())
            ChildVisible.add(wp);
    }
    return ChildVisible;
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
        ret.append("<table border=0 width=\"100%\" bgcolor=\"#FFFFFF\">\n");
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
        ret.append("          </table>\n");
        return ret.toString();
    }
    else
        return null;
}
    %>

