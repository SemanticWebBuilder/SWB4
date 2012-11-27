/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.admin.resources;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;


// TODO: Auto-generated Javadoc
/**
 * The Class SWBAIPFilter2.
 * 
 * @author juan.fernandez
 */
public class SWBAIPFilter2 extends GenericResource {

    
    /** The I_ pag e_ size. */
    private final int I_PAGE_SIZE = 10;
    
    /** The I_ ini t_ page. */
    private final int I_INIT_PAGE = 1;
    
    /** The i page. */
    private int iPage=0;
    
    /** The i tot page. */
    private int iTotPage=0;
    
    /** The i ini page. */
    private int iIniPage=0;
    
    /** The i fin page. */
    private int iFinPage=0;
    
    /** The str url. */
    private String strUrl= null; 
    
    /** The i array. */
    private String[] iArray = null;
    
    /** The str array. */
    private String[] strArray = null;

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
     
        PrintWriter out=response.getWriter();
        String iResId=request.getParameter("id")!=null?request.getParameter("id"):"0";
        int iWBPage=1;
        String idtm=paramRequest.getWebPage().getWebSiteId();
        String strOrder="order";
        String strWBAction="view";
        String strConfirm=request.getParameter("confirm");          

        try { iWBPage=Integer.parseInt(request.getParameter("WBPage")); }
        catch(NumberFormatException e) { iWBPage=1; }
        if (request.getParameter("sort")!=null && !request.getParameter("sort").trim().equals("")) strOrder=request.getParameter("sort");
        if(request.getParameter("act")!=null && !request.getParameter("act").trim().equals(""))
            strWBAction=request.getParameter("act");
        if(request.getParameter("idtm")!=null && !request.getParameter("idtm").trim().equals("")) idtm=request.getParameter("idtm");
        int iArraySize = getArraySize(idtm);
        strUrl =SWBPlatform.getContextPath() + SWBPlatform.getEnv("wb/distributor") + "/" + paramRequest.getWebPage().getWebSiteId() + "/";

        if (strWBAction.equals("view") && iResId.equals("0")) 
        {
            iArray = new String[iArraySize];
            strArray = new String[iArraySize];
            if (strOrder.equals("order")) iArray=getIntSortArray(idtm,iArray,iWBPage);
            else  strArray=getStrSortArray(idtm,strArray,strOrder,iWBPage);
            out.println(getIniForm(strOrder, idtm, paramRequest));
            if (strConfirm!=null && (strConfirm.equals("removed") || strConfirm.equals("updated") || strConfirm.equals("added"))) 
            {
                out.println("<script>");
                out.println("alert('"+ request.getParameter("message").replace('\'', '`')+"');");
                out.println("location='"+strUrl+paramRequest.getWebPage().getId()+"';");
                out.println("</script>");
            }            
        }
        else if (strWBAction.equals("add") || strWBAction.equals("edit")) 
        {
            out.println(getForm(iResId, idtm, paramRequest));
        }
        else if (strWBAction.equals("remove") && !iResId.equals("0")) 
        {
            SWBResourceURL urlResAct=paramRequest.getActionUrl();
            out.println("<form name=\"frmIPFilter\" method=\"post\"> \n");
            out.println("<input type=hidden name=act2 value=\"remove\"> \n");
            out.println("<input type=hidden name=idtm value=\""+idtm+"\"> \n");
            out.println("<input type=hidden name=id value=\""+iResId+"\"> \n");
            out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
            out.println("      document.frmIPFilter.action='"+urlResAct+"'; \n");
            out.println("      document.frmIPFilter.submit(); \n");
            out.println("</script> \n");
            out.println("</form> \n");

            out.println(getForm(iResId, idtm, paramRequest));
        }
    }
    
    /**
     * Gets the obj id.
     * 
     * @param strRec the str rec
     * @return the obj id
     */
    private String getObjId(String strRec) 
    {
        String id="0";
        String[] strFields=strRec.split(":");
        try { id=strFields[1]; }
        catch(Exception e) { id="0"; }
        return id;
    }

    /**
     * Gets the str sort array.
     * 
     * @param idtm the idtm
     * @param strArray the str array
     * @param strOrder the str order
     * @param iWBPage the i wb page
     * @return the str sort array
     */
    private String [] getStrSortArray(String idtm, String [] strArray, String strOrder, int iWBPage) 
    {
        int cont=0;
        String sort="";
        WebSite ws = SWBContext.getWebSite(idtm);
        Iterator<IPFilter> en = ws.listIPFilters();        
        while (en.hasNext()) 
        {
            IPFilter rec = en.next();
            if(rec!=null)
            {
                if(idtm!=null && !idtm.trim().equals(""))
                {
                    if(rec.getWebSite().getId().equals(idtm))
                    {
                        sort=rec.getId();
                        
                        //TODO: falta saber si el IPFilter tiene descripcion, IP y action
                        if (strOrder.equals("ip")) sort=rec.getIpNumber(); 
                        else if (strOrder.equals("description"))  sort = rec.getDescription();
//                        else if (strOrder.equals("action")) sort=String.valueOf(rec.getAction());
                        else if (strOrder.equals("lastupdate")) sort=String.valueOf(rec.getUpdated());
                        sort+=":"+rec.getId();
                        strArray[cont]=sort;
                        cont++;
                    }
                }
                else
                {
                    sort=String.valueOf(rec.getId());
                    //TODO: falta saber si IPFilter va tener Description y action
                    if (strOrder.equals("ip")) sort=rec.getIpNumber();
                    else if (strOrder.equals("description"))  sort=rec.getDescription();
//                    else if (strOrder.equals("action")) sort=String.valueOf(rec.getAction());
                    else if (strOrder.equals("lastupdate")) sort=String.valueOf(rec.getUpdated());
                    sort+=":"+rec.getId();
                    strArray[cont]=sort;
                    cont++;
                }
            }
        }
        Arrays.sort(strArray,String.CASE_INSENSITIVE_ORDER);
        getPageRange(strArray.length,iWBPage);
        return strArray;
    }

    /**
     * Gets the int sort array.
     * 
     * @param idtm the idtm
     * @param iArray the i array
     * @param iWBPage the i wb page
     * @return the int sort array
     */
    private String[] getIntSortArray(String idtm, String[] iArray, int iWBPage) 
    {
        int cont=0;
        Iterator<IPFilter> en = SWBContext.getWebSite(idtm).listIPFilters();
        while (en.hasNext()) 
        {
            IPFilter rec = en.next();
            if(rec!=null)
            {
                if(idtm!=null && !idtm.trim().equals(""))
                {
                    if(rec.getWebSite().getId().equals(idtm))
                    {
                        iArray[cont] = rec.getId();
                        cont++;
                    }
                }
                else
                {
                    iArray[cont]=rec.getId();
                    cont++;
                }
            }
        }
        Arrays.sort(iArray);
        getPageRange(iArray.length,iWBPage);
        return iArray;
    }

    /**
     * Gets the array size.
     * 
     * @param idtm the idtm
     * @return the array size
     */
    private int getArraySize(String idtm) 
    {
        int cont=0;
        Iterator<IPFilter> en = SWBContext.getWebSite(idtm).listIPFilters();
        while (en.hasNext()) 
        {
            IPFilter rec = en.next();
            cont++;
        }
        return cont;
    }

    /**
     * Gets the str obj table.
     * 
     * @param rec the rec
     * @param row the row
     * @return the str obj table
     */
    private String getStrObjTable(IPFilter rec, int row) 
    {
        if(rec==null) return "";
        StringBuffer sbRet=new StringBuffer();
        String bgcolor="#FFFFFF";
        if(row%2==0) bgcolor="#EFEDEC";
        sbRet.append("<tr class=\"valores\" bgcolor=\""+bgcolor+"\"> \n");
        sbRet.append("<td> \n");
        sbRet.append("<input type=\"radio\" name=\"id\" value=\""+rec.getIpNumber()+"\" title=\""+rec.getIpNumber()+"\"> \n");
        sbRet.append("</td> \n");
        sbRet.append("<td> \n");
        sbRet.append(rec.getId());
        sbRet.append("</td> \n");        
        sbRet.append("<td> \n");
        sbRet.append(rec.getIpNumber());
        sbRet.append("</td> \n");
        sbRet.append("<td> \n");
        sbRet.append(rec.getDescription());
        sbRet.append("</td> \n");
        sbRet.append("<td> \n");
        //TODO: getAction()
        //sbRet.append(rec.getAction());
        sbRet.append("Acción???");
        sbRet.append("</td> \n");
        sbRet.append("<td> \n");
        sbRet.append(rec.getUpdated());
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        return sbRet.toString();
    }

    /**
     * Gets the page range.
     * 
     * @param iSize the i size
     * @param iPageNum the i page num
     * @return the page range
     */
    private void getPageRange (int iSize, int iPageNum) 
    {
        iPage = I_INIT_PAGE;
        if (iPageNum > 1) iPage = iPageNum;
        if(iSize > I_PAGE_SIZE)
        {
            iTotPage = iSize / I_PAGE_SIZE;
            int i_ret = iSize % I_PAGE_SIZE;
            if(i_ret > 0) iTotPage = iTotPage + 1;
        }
        else iTotPage=1;
     
        iIniPage =  (I_PAGE_SIZE*iPage) - I_PAGE_SIZE ;
        iFinPage = I_PAGE_SIZE*iPage;
        if(iSize < I_PAGE_SIZE*iPage) iFinPage = iSize;
    }

    /**
     * Gets the java script.
     * 
     * @param paramsRequest the params request
     * @return the java script
     */
    private String getJavaScript (SWBParamRequest paramsRequest) 
    {
        StringBuffer sbRet=new StringBuffer();
        try 
        {
            sbRet.append("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
            sbRet.append("  if (document.frmIPFilter.idtm.length==undefined || document.frmIPFilter.idtm.length==1) { \n");
            sbRet.append("     document.frmIPFilter.Open.disabled=true;\n");
            sbRet.append("     document.frmIPFilter.Add.disabled=true;\n");
            sbRet.append("     document.frmIPFilter.Remove.disabled=true;\n");
            sbRet.append("     alert('"+paramsRequest.getLocaleString("msgNotAccess")+"')\n");
            sbRet.append("  } \n");
            sbRet.append("  var strResName=''; \n");
            sbRet.append("  var strResId=''; \n");
            sbRet.append("  function sort(col,page) { \n");
            sbRet.append("      document.frmIPFilter.sort.value = col; \n");
            sbRet.append("      document.frmIPFilter.WBPage.value = page; \n");
            sbRet.append("      document.frmIPFilter.submit(); \n");
            sbRet.append("  } \n");
            sbRet.append("  function doPaging(page,col) { \n");
            sbRet.append("      document.frmIPFilter.WBPage.value = page; \n");
            sbRet.append("      document.frmIPFilter.sort.value = col; \n");
            sbRet.append("      document.frmIPFilter.submit(); \n");
            sbRet.append("  } \n");
            sbRet.append("function send(action) { \n");
            //SWBResourceURL urlResAct=paramsRequest.getActionUrl();
            sbRet.append("    var agree=false; \n");
            sbRet.append("\n    if(document.frmIPFilter.idtm.selectedIndex==0 || document.frmIPFilter.idtm.options[document.frmIPFilter.idtm.selectedIndex].value=='') { ");
            sbRet.append("\n        alert('"+paramsRequest.getLocaleString("msgSiteRequired")+"')");
            sbRet.append("\n        document.frmIPFilter.idtm.focus();");
            sbRet.append("\n        return false;");
            sbRet.append("\n    }");            
            sbRet.append("    if (action=='view'){ \n");
            sbRet.append("      document.frmIPFilter.act.value = action; \n");
            sbRet.append("      agree=true; \n");
            sbRet.append("    } \n"); 
            sbRet.append("    if (action=='remove') \n");
            sbRet.append("    { \n");
            sbRet.append("      var selected=radioselected(); \n");
            sbRet.append("      if (selected) agree=confirm('"+paramsRequest.getLocaleString("msgConfirmRemove")+" '+strResName+'?'); \n");
            sbRet.append("      else alert ('"+paramsRequest.getLocaleString("msgIPFilterRequired")+"');\n");
            sbRet.append("      document.frmIPFilter.act.value = action; \n");
            sbRet.append("      document.frmIPFilter.ResId.value = strResId; \n");
            sbRet.append("    } \n");
            sbRet.append("    if (action=='add'){ \n");
            sbRet.append("      document.frmIPFilter.act.value = action; \n");
            sbRet.append("      agree=true; \n");
            sbRet.append("    } \n");        
            sbRet.append("    if (action=='edit'){ \n");
            sbRet.append("      var selected=radioselected(); \n");
            sbRet.append("      var agree=false; \n");
            sbRet.append("      if (!selected) alert ('"+paramsRequest.getLocaleString("msgIPFilterRequired")+"');\n");
            sbRet.append("      else agree=true; \n");
            sbRet.append("      document.frmIPFilter.act.value = action; \n");
            sbRet.append("      document.frmIPFilter.ResId.value = strResId; \n");
            sbRet.append("    } \n");
            sbRet.append("    if (action=='save'){ \n");
            sbRet.append("      var agree=validateForm(); \n");
            //sbRet.append("      document.frmIPFilter.action='"+urlResAct+"'; \n");
            //sbRet.append("      document.frmIPFilter.act.value='"+paramsRequest.getAction()+"'; \n");
            sbRet.append("    } \n");
            sbRet.append("    if (agree) document.frmIPFilter.submit();\n");
            sbRet.append("} \n");
            sbRet.append("function radioselected() { \n");
            sbRet.append("    if (document.frmIPFilter.id.value!=\"\") { \n");
            sbRet.append("       if (document.frmIPFilter.id.length!=undefined) { \n");
            sbRet.append("          for(i=0;i<document.frmIPFilter.id.length;i++) { \n");
            sbRet.append("             if(document.frmIPFilter.id[i].checked==true) { \n");
            sbRet.append("                 strResId=document.frmIPFilter.id[i].value;  \n");
            sbRet.append("                 strResName=document.frmIPFilter.id[i].title;  \n");
            sbRet.append("                 return true;  \n");
            sbRet.append("             } \n");
            sbRet.append("          } \n");
            sbRet.append("       } \n");
            sbRet.append("       else if(document.frmIPFilter.id.checked) { \n");
            sbRet.append("          strResId=document.frmIPFilter.id.value;  \n");
            sbRet.append("          strResName=document.frmIPFilter.id.title;  \n");
            sbRet.append("          return true;  \n");        
            sbRet.append("       } \n");
            sbRet.append("    } \n");        
            sbRet.append("} \n");
            sbRet.append("function radioselect() { \n");
            sbRet.append("    if (document.frmIPFilter.res.value!=\"\")  \n");
            sbRet.append("      alert (document.frmIPFilter.res.value);  \n");
            sbRet.append("} \n");
            sbRet.append("\n function validateForm() {");
            sbRet.append("\n    var frm=document.frmIPFilter;");
            /*
            sbRet.append("\n    for (var i=0; i < frm.elements.length; i++ ) { ");
            sbRet.append("\n        if(frm.elements[i]!=undefined) {");
            // Valida los objetos de la forma tipo text
            sbRet.append("\n            if(frm.elements[i].type==\"text\") {");
            sbRet.append("\n               if (frm.elements[i].value=='') {");
            sbRet.append("\n                  alert ('"+paramsRequest.getLocaleString("msgIPRequired")+"');");
            sbRet.append("\n                  frm.elements[i].focus();");
            sbRet.append("\n                  return false;");
            sbRet.append("\n               }");
            sbRet.append("\n            }");
            sbRet.append("\n        }");
            sbRet.append("\n    }");
            */
            sbRet.append("\n    if (frm.ip1.value=='') {");
            sbRet.append("\n        alert ('"+paramsRequest.getLocaleString("msgIPRequired")+"');");
            sbRet.append("\n        frm.ip1.focus();");
            sbRet.append("\n        return false;");
            sbRet.append("\n    }");
            sbRet.append("\n    if(!isRange(frm.ip1,1) || !isRange(frm.ip2,0) || !isRange(frm.ip3,0) || !isRange(frm.ip4,0)) return false;");
            sbRet.append("\n    else {");
            sbRet.append("\n        frm.ip.value=frm.ip1.value;");
            sbRet.append("\n        if (frm.ip2.value!='') frm.ip.value+='.'+frm.ip2.value;");
            sbRet.append("\n        if (frm.ip3.value!='') {");
            sbRet.append("\n            if (frm.ip2.value=='') frm.ip.value+='.0';");
            sbRet.append("\n            frm.ip.value+='.'+frm.ip3.value;");
            sbRet.append("\n        }");
            sbRet.append("\n        if (frm.ip4.value!='') {");
            sbRet.append("\n            if (frm.ip2.value=='') frm.ip.value+='.0';");
            sbRet.append("\n            if (frm.ip3.value=='') frm.ip.value+='.0';");
            sbRet.append("\n            frm.ip.value+='.'+frm.ip4.value;");
            sbRet.append("\n        }");
            sbRet.append("\n    }");
            sbRet.append("\n    if(frm.idtm.selectedIndex==0 ||  frm.idtm.options[frm.idtm.selectedIndex].value=='') { ");
            sbRet.append("\n        alert('"+paramsRequest.getLocaleString("msgSiteRequired")+"')");
            sbRet.append("\n        frm.idtm.focus();");
            sbRet.append("\n        return false;");
            sbRet.append("\n    }");
            sbRet.append("\n    return true;");
            sbRet.append("\n }");
            sbRet.append("\n function isRange(ip, initial) { ");
            sbRet.append("\n    if(ip.value.length > 0)");
            sbRet.append("\n        if(parseInt(ip.value) < initial || parseInt(ip.value) > 255) {");
            sbRet.append("\n            alert ('"+paramsRequest.getLocaleString("msgSubnetMask")+"');");
            sbRet.append("\n            ip.focus();");
            sbRet.append("\n            return false;");
            sbRet.append("\n        }");
            sbRet.append("\n    return true;");
            sbRet.append("\n }");
            sbRet.append("</script> \n");
        } catch(Exception e){}            
        return sbRet.toString();
    }

    /**
     * Gets the ini form.
     * 
     * @param strOrder the str order
     * @param idtm the idtm
     * @param paramsRequest the params request
     * @return the ini form
     * @throws SWBResourceException the sWB resource exception
     */
    private String getIniForm(String strOrder, String idtm, SWBParamRequest paramsRequest) throws SWBResourceException
    {
        StringBuffer sbRet=new StringBuffer();
        //User user= paramsRequest.getUser();
        sbRet.append("<div class=\"box\">");
        sbRet.append("<form name=\"frmIPFilter\" method=\"post\" action=\"\"> \n");
        sbRet.append("<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">");
        sbRet.append("<tr> \n");
        sbRet.append("<td class=\"valores\">"+paramsRequest.getLocaleString("msgSite")+"</td>");
        sbRet.append("<td colspan=\"5\" align=\"left\" class=\"valores\">");
        sbRet.append("<select name=\"idtm\" onChange='javascript:send(\"view\")'>");
        sbRet.append("<option value=\"\">"+paramsRequest.getLocaleString("msgSiteOption")+"</option>\n");
        Iterator<WebSite> it=SWBContext.listWebSites();
        boolean bOk=false;
        String js="";
        while(it.hasNext())
        {
            WebSite tm=(WebSite)it.next();
            //TODO: AdmFilterMgr.getInstance().haveAccess2TopicMap
            //if(2 == AdmFilterMgr.getInstance().haveAccess2TopicMap(user, tm.getId()))
            {
                js+="<option value=\""+tm.getId()+"\"";
                if(tm.getId().equals(idtm)) { js+=" selected"; bOk=true; }
                js+=">"+tm.getTitle()+"</option>\n";
            }
        }
        if(!bOk && js.indexOf(">"+paramsRequest.getWebPage().getWebSiteId()+"</option>")>-1)
            js=js.replaceFirst(">"+paramsRequest.getWebPage().getWebSiteId()+"</option>", "selected>"+paramsRequest.getWebPage().getWebSiteId()+"</option>");
        sbRet.append(js);
        sbRet.append("</select></td> \n");
        sbRet.append("</tr> \n");        
        sbRet.append("<tr align=\"center\"> \n");
        sbRet.append("<td colspan=\"6\" class=\"datos\">"+paramsRequest.getLocaleString("msgPage")+" "+ iPage +" "+paramsRequest.getLocaleString("msgOf")+" " + iTotPage+"&nbsp;&nbsp;&nbsp;&nbsp;");
        if(iPage > 1) sbRet.append("<a href=\"javascript:doPaging(" + (iPage - 1) + ",'"+strOrder+"');\" class=\"link\">&lt;&lt;</a>&nbsp;");
        if(iPage > 0 && (iPage + 1 <= iTotPage)) 
            sbRet.append("<a href=\"javascript:doPaging(" + (iPage + 1) +",'"+strOrder+"');\" class=\"link\">&gt;&gt;</a>");
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr class=\"tabla\"> \n");
        sbRet.append("<td>&nbsp;</td> \n");
        sbRet.append("<td><a href=\"javascript:sort('order',"+iPage+")\" class=\"link\">"+paramsRequest.getLocaleString("msgID")+"</a></td> \n");
        sbRet.append("<td><a href=\"javascript:sort('ip',"+iPage+")\" class=\"link\">"+paramsRequest.getLocaleString("msgIP")+"</a></td> \n");
        sbRet.append("<td><a href=\"javascript:sort('description',"+iPage+")\" class=\"link\">"+paramsRequest.getLocaleString("msgDescription")+"</a></td> \n");
        sbRet.append("<td><a href=\"javascript:sort('action',"+iPage+")\" class=\"link\">"+paramsRequest.getLocaleString("msgRestriction")+"</a></td> \n");
        sbRet.append("<td><a href=\"javascript:sort('lastupdate',"+iPage+")\">"+paramsRequest.getLocaleString("msgLastUpdate")+"</a></td> \n");
        //sbRet.append("<td>"+paramsRequest.getLocaleString("msgLastUpdate")+"</td> \n");
        sbRet.append("</tr> \n");
        if (strOrder.equals("order")) 
        {
            for (int i=iIniPage; i < iFinPage; i++) 
            {
                IPFilter rec = SWBContext.getWebSite(idtm).getIPFilter(iArray[i]);
                sbRet.append(getStrObjTable(rec, i));
            }
        }
        else
        {
            for (int i=iIniPage; i < iFinPage; i++) 
            {
                IPFilter rec = SWBContext.getWebSite(idtm).getIPFilter(getObjId(strArray[i])); //DBCatalogs.getInstance().getIpFilter(idtm, getObjId(strArray[i]));
                sbRet.append(getStrObjTable(rec, i));
            }
        }
        sbRet.append("<tr> \n");
        sbRet.append("<td colspan=\"6\" align=right> \n");
        sbRet.append("<hr size=1 noshade> \n");
        sbRet.append("\n <input type=button  class=\"boton\" name=Open onClick='javascript:send(\"edit\")' value="+paramsRequest.getLocaleString("btnEdit")+">");        
        //sbRet.append("&nbsp;&nbsp;&nbsp;");
        sbRet.append("\n <input type=button  class=\"boton\" name=Add onClick='javascript:send(\"add\");' value="+paramsRequest.getLocaleString("btnAdd")+">");
        //sbRet.append("&nbsp;&nbsp;&nbsp;");
        sbRet.append("\n <input type=button  class=\"boton\" name=Remove onClick='javascript:send(\"remove\");' value="+paramsRequest.getLocaleString("btnRemove")+">");
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("</table> \n");
        sbRet.append("<input type=hidden name=sort> \n");
        sbRet.append("<input type=hidden name=act> \n");
        sbRet.append("<input type=hidden name=ResId> \n");
        sbRet.append("<input type=hidden name=WBPage> \n");
        sbRet.append("</form> \n");
        sbRet.append("</div>");
        sbRet.append(getJavaScript(paramsRequest));        
        return sbRet.toString();
    }

    /**
     * Gets the form.
     * 
     * @param id the id
     * @param idtm the idtm
     * @param paramsRequest the params request
     * @return the form
     * @throws SWBResourceException the sWB resource exception
     */
    private String getForm(String id, String idtm, SWBParamRequest paramsRequest) throws SWBResourceException
    {
        StringBuffer sbRet=new StringBuffer();
        IPFilter rec=null;
        if(!id.equals("0")) rec=SWBContext.getWebSite(idtm).getIPFilter(id);
        SWBResourceURL urlResAct=paramsRequest.getActionUrl();
        String[] ip=null;
        //if(rec!=null && (rec.getIp()!=null && rec.getIp().matches("(((0)|([1-9]([0-9]){0,1})|(1([0-9]){2})|(2[0-4][0-9])|(25[0-5]))\\.){3}((0)|([1-9]([0-9]){0,1})|(1([0-9]){2})|(2[0-4][0-9])|(25[0-5]))$"))) 
        if(rec!=null && rec.getIpNumber()!=null) 
        {
            ip=rec.getIpNumber().split("\\.");
            //if(ip.length < 1) ip=new String[]{rec.getIp()};
        }
        sbRet.append(getJavaScript(paramsRequest));
        sbRet.append("<div class=box>");
        sbRet.append("<form name=\"frmIPFilter\" method=\"post\" action=\""+urlResAct+"\"> \n");
        sbRet.append("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\">");
        sbRet.append("<tr> \n");
        sbRet.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgIP")+":</td> \n");
        sbRet.append("<td class=\"valores\"><input name=\"ip1\" type=\"text\" size=3 maxlength=3 dir=rtl value=\""+(ip!=null && ip.length > 0 ? ip[0] : "")+ "\">.\n");
        sbRet.append("<input name=\"ip2\" type=\"text\" size=3 maxlength=3 dir=rtl value=\""+(ip!=null && ip.length > 1 ? ip[1] : "")+ "\">.\n");
        sbRet.append("<input name=\"ip3\" type=\"text\" size=3 maxlength=3 dir=rtl value=\""+(ip!=null && ip.length > 2 ? ip[2] : "")+ "\">.\n");
        sbRet.append("<input name=\"ip4\" type=\"text\" size=3 maxlength=3 dir=rtl value=\""+(ip!=null && ip.length > 3 ? ip[3] : "")+ "\">\n");
        sbRet.append("<input name=\"ip\" type=\"hidden\"");
        if(rec!=null && rec.getIpNumber()!=null) sbRet.append(" value=\""+rec.getIpNumber()+"\"");
        sbRet.append("></td> \n");
        sbRet.append("</tr> \n");

        sbRet.append("<tr> \n");
        sbRet.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgSite")+"</td> \n");
        sbRet.append("<td class=\"valores\"><select name=idtm>");
        sbRet.append("<option value=\"\">"+paramsRequest.getLocaleString("msgSiteOption")+"</option>\n");
        Iterator<WebSite> it=SWBContext.listWebSites();
        boolean bOk=false;
        String js="";
        while(it.hasNext())
        {
            WebSite tm=(WebSite)it.next();
            js+="<option value=\""+tm.getId()+"\"";
            if(rec!=null && rec.getWebSite().getId().equals(tm.getId())) { js+=" selected"; bOk=true; }
            js+=">"+tm.getId()+"</option>\n";
        }
        if(!bOk && js.indexOf(">"+idtm+"</option>")>-1)  
            js=js.replaceFirst(">"+idtm+"</option>", "selected>"+idtm+"</option>");
        sbRet.append(js);
        sbRet.append("</select></td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr> \n");
        sbRet.append("<td class=\"datos\">"+paramsRequest.getLocaleString("msgDescription")+":</td> \n");
        sbRet.append("<td class=\"valores\"><textarea name=\"description\" cols=\"38\" rows=\"8\" wrap=\"VIRTUAL\">");
        if(rec!=null && rec.getDescription()!=null) sbRet.append(rec.getDescription());
        sbRet.append("</textarea></td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr> \n");
        sbRet.append("<td>&nbsp;</td> \n");
        sbRet.append("<td class=\"valores\"><input name=\"action\" type=\"radio\" value=0");
        //TODO: getAction() IPFilter ???
        //if(rec==null || (rec!=null && rec.getAction() == 0)) sbRet.append(" checked");
        sbRet.append("> "+paramsRequest.getLocaleString("msgNotCount")+"</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr> \n");
        sbRet.append("<td>&nbsp;</td> \n");
        sbRet.append("<td class=\"valores\"><input name=\"action\" type=\"radio\" value=1");
        //TODO: getAction() IPFilter ???
        //if(rec!=null && rec.getAction() == 1) sbRet.append(" checked");
        sbRet.append("> "+paramsRequest.getLocaleString("msgNotAllow")+"</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr><td colspan=\"6\"><HR size=\"1\" noshade></td></tr> \n");
        sbRet.append("<tr> \n");
        sbRet.append("<td colspan=\"6\" align=\"right\"> \n");
        sbRet.append("<input type=button class=\"boton\" name=Save onClick='javascript:send(\"save\");' value=");
        if(rec!=null && !rec.getId().equals("0")) sbRet.append(paramsRequest.getLocaleString("btnUpdate"));
        else sbRet.append(paramsRequest.getLocaleString("btnSave"));
        sbRet.append("> \n");
        //sbRet.append("&nbsp;&nbsp;&nbsp; \n");
        sbRet.append("<input type=reset class=\"boton\" name=Reset value="+paramsRequest.getLocaleString("btnReset")+"> \n");
        //sbRet.append("&nbsp;&nbsp;&nbsp; \n");
        sbRet.append("<input type=button class=\"boton\" name=Back onClick=location='"+strUrl+paramsRequest.getWebPage().getId()+"'; value="+paramsRequest.getLocaleString("msgBack")+"> ");
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("</table> \n");
        sbRet.append("<input type=hidden name=sort> \n");
        sbRet.append("<input type=hidden name=act>");
        sbRet.append("<input type=hidden name=act2 value=\"");
        if(rec!=null && !rec.getId().equals("0")) sbRet.append("edit");
        else sbRet.append("add");
        sbRet.append("\"> \n");
        sbRet.append("<input type=hidden name=id value=\""+id+"\"> \n");
        sbRet.append("</form> \n");
        sbRet.append("</div> \n");
        return sbRet.toString();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
    
        HashMap params=new HashMap();

        User user = response.getUser();
        String id=request.getParameter("id")!=null?request.getParameter("id"):"0";
        String strWBAction=request.getParameter("act2");
        String msg=null;
        String idtm=request.getParameter("idtm");
        WebSite ws = SWBContext.getWebSite(idtm);

        if (strWBAction!=null && strWBAction.equals("remove")) 
        {
            try{
                ws.removeIPFilter(id);
                msg=response.getLocaleString("msgOkRemove")+" "+id+".";
            }
            catch(Exception e)
            {
               msg=response.getLocaleString("msgErrRemove")+" "+id+"."; 
            }
            params.put("confirm","removed");
        }
        else 
        {
            int action=0;
            String ip=request.getParameter("ip");
            String description=request.getParameter("description");
            Timestamp lastupdate = new Timestamp(System.currentTimeMillis());        
            try { action=Integer.parseInt(request.getParameter("action")); }
            catch(NumberFormatException e) { action=0; }  
            
            if (strWBAction!=null && strWBAction.equals("add"))  
            {
                IPFilter rec=ws.createIPFilter();
                //TODO: IPFilter Description, Action, IP, user, created, updated
                    rec.setDescription(description);
                    rec.setIpNumber(ip);
//                    rec.setAction(action);
                    rec.setCreator(user);
                //IPFilter rec=srv.createIPFilter(idtm,ip,description,action,lastupdate,response.getUser().getId());
                if(rec!=null && !rec.getId().equals("0")) msg=response.getLocaleString("msgOkAdd")+" "+rec.getId()+".";
                else msg=response.getLocaleString("msgErrAdd")+" "+id+".";
                params.put("confirm","added");
            }
            else if (strWBAction!=null && strWBAction.equals("edit")) 
            {
                IPFilter rec=ws.getIPFilter(id);
                try{
                //if(srv.updateIPFilter(idtm,id,ip,description,action,lastupdate,response.getUser().getId())) 
                    //TODO: IPFilter Description, Action, IP, user, created, updated
                    rec.setDescription(description);
                    rec.setIpNumber(ip);
//                    rec.setAction(action);
                    rec.setModifiedBy(user);
                    msg=response.getLocaleString("msgOkUpdate")+" "+id+".";
                }
                catch(Exception e)
                {
                    msg=response.getLocaleString("msgErrUpdate")+" "+id+".";
                }
                params.put("confirm","updated");
            }
        }
        params.put("message", msg);
        params.put("idtm", idtm);
        response.setRenderParameters(params);
    }
}
