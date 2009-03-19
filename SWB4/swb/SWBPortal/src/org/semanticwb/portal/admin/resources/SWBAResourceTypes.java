/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import com.sun.org.apache.xpath.internal.XPathAPI;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author juan.fernandez
 */
public class SWBAResourceTypes extends GenericResource {

    private Logger log=SWBUtils.getLogger(SWBAResourceTypes.class);
    private boolean portletSupport = false;
    private final int I_PAGE_SIZE = 10;
    private final int I_INIT_PAGE = 1;
    private int iPage=0;
    private int iTotPage=0;
    private int iIniPage=0;
    private int iFinPage=0;
    //private String strUrl= null;
    private String[] iArray = null;
    private String[] strArray = null;
    private Properties propResourceTypes = null;


    /** Nombre del recurso */
    public String strRscType;

    public void init(){
        try
        {
            portletSupport = Boolean.valueOf(SWBPlatform.getEnv("wb/portletSupport", "false")).booleanValue();
        } catch (Exception e)
        {
            log.error( "Error while reading portletSupport in web.properties", e);
            portletSupport = false;
        }

        String path="/WEB-INF/classes/";
        strRscType = getResourceBase().getResourceType().getTitle();
        try {
            InputStream in = new FileInputStream(SWBUtils.getApplicationPath()+path+"resourcetypes.properties");
            propResourceTypes = new Properties();
            propResourceTypes.load(in);
        } catch (Exception e) {
            System.err.println("Can't read the resourcetypes.properties file. " +
                               "Make sure resourcetypes.properties is in the CLASSPATH");
            return;
        }
    }

    /** Método que despliega la parte pública del recurso
     * @param request Objeto HttpServletRequest
     * @param response Objeto HttpServletResponse
     * @param paramRequest Objeto WBParamRequest
     * @throws AFException Excepción del Aplication Framework
     * @throws IOException Excepción de Entrada/Salida
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        String strOrder=request.getParameter("sort");
        String strTm=request.getParameter("tm");

        String strTmSel=null;
        if (request.getParameter("txtMap")!=null)
            strTmSel=request.getParameter("txtMap");
        if (strTm!=null)
            strTmSel=strTm;
        String strConfirm=request.getParameter("confirm");
        WebSite tpm = null;
        if (strTmSel!=null){
            tpm=SWBContext.getWebSite(strTmSel);
        }
        else {
            Iterator<WebSite> enumTopicMaps = SWBContext.listWebSites();
            if (enumTopicMaps.hasNext()) {
                tpm=enumTopicMaps.next();
            }
        }
        //if(tpm!=null) {
        //    iTmnId=tpm.getDbdata().getNId();
        //}
        String iResId="";
        //int iResId=0;
        //if (request.getParameter("id")!=null && !request.getParameter("id").equals(""))
            //iResId=Integer.parseInt(request.getParameter("id"));
        iResId=request.getParameter("id");
        if (strOrder==null)
            strOrder="order";
        String strWBAction=request.getParameter("act");
        if(strWBAction==null || (strWBAction!=null && strWBAction.equals("")))strWBAction="view";
        if (strWBAction!=null && strWBAction.equals(""))
            strWBAction=null;
        int iWBPage=1;
        if (request.getParameter("WBPage")!=null && !request.getParameter("WBPage").equals("")){
            iWBPage=Integer.parseInt(request.getParameter("WBPage"));
        }
        //--strUrl =SWBPlatform.getContextPath() + SWBPlatform.getEnv("wb/distributor") + "/" + paramRequest.getTopic().getWebSiteId() + "/";
        //int iArraySize = getArraySize(iTmnId);
        int iArraySize = getArraySize(tpm.getId(),false);
        out.println("<form name=\"Resources\" method=\"post\" action=\"\" class=\"box\"> \n");
        //if (strWBAction.equals("view") && iResId==0) {
        if (strWBAction.equals("view")) {
            if ((strOrder!=null && !strOrder.equals(""))) {
                iArray = new String[iArraySize];
                strArray = new String[iArraySize];
                if (strOrder==null || (strOrder!=null && strOrder.equals("order") || strOrder.equals("map"))) {
                    //iArray=getIntSortArray(iArray,iWBPage,iTmnId);
                    iArray=getIntSortArray(iArray,iWBPage,tpm.getId());
                }
                else if (strOrder!=null) {
                    //strArray=getStrSortArray(strArray,strOrder,iWBPage,iTmnId);
                    strArray=getStrSortArray(strArray,strOrder,iWBPage,tpm.getId());
                }
                out.println(getIniForm(strOrder, tpm.getId(), strTmSel, paramRequest));
            }
        }
        else if (strWBAction!=null && strWBAction.equals("add") || strWBAction.equals("portlet")) {

            /*if (strTm!=null)
              out.println(getAddForm(paramRequest.getTopic(), paramRequest, strTm));
          else if (strTmSel!=null)*/
            out.println(getAddForm(paramRequest.getTopic(), paramRequest, strTmSel,request));

        }
        else if (strWBAction!=null && strWBAction.equals("addCat")) {

            /*if (strTm!=null)
                out.println(getAddForm(paramRequest.getTopic(), paramRequest, strTm));
            else if (strTmSel!=null)*/
            out.println(getAddCat(strOrder,tpm.getId(), request, paramRequest));

        }
        else if (strWBAction!=null && strWBAction.equals("edit")) {
            if (strTm!=null)
                out.println(getEditForm(iResId,paramRequest.getTopic(), paramRequest, strTm));
            /*else if (strTmSel!=null)
                out.println(getEditForm(iResId,paramRequest.getTopic(), paramRequest, strTmSel));*/

        }
        else if (strWBAction!=null && strWBAction.equals("remove")) {
            SWBResourceURL urlResAct=paramRequest.getActionUrl();
            out.println("<form name=\"Resources\" method=\"post\" action=\"\"> \n");
            out.println("<input type=hidden name=act2 value=\"remove\"> \n");
            out.println("<input type=hidden name=id value=\""+iResId+"\"> \n");
            if (strTm!=null)
                out.println("<input type=hidden name=tmSel value=\""+strTm+"\"> \n");
            else if (strTmSel!=null)
                out.println("<input type=hidden name=tmSel value=\""+strTmSel+"\"> \n");
            out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
            out.println("      document.Resources.action='"+urlResAct+"'; \n");
            out.println("      document.Resources.submit(); \n");
            out.println("</script> \n");
            out.println("</form> \n");
            if (strTm!=null)
                out.println(getEditForm(iResId,paramRequest.getTopic(), paramRequest,strTm));
            else if (strTmSel!=null)
                out.println(getEditForm(iResId,paramRequest.getTopic(), paramRequest,strTmSel));
        }

        else if (strWBAction!=null && strWBAction.equals("reload")) {
            SWBResourceURL urlResAct=paramRequest.getActionUrl();
            out.println("<form name=\"Resources\" method=\"post\" action=\"\"> \n");
            out.println("<input type=hidden name=act2 value=\"reload\"> \n");
            out.println("<input type=hidden name=id value=\""+iResId+"\"> \n");
            if (strTm!=null)
                out.println("<input type=hidden name=tmSel value=\""+strTm+"\"> \n");
            else if (strTmSel!=null)
                out.println("<input type=hidden name=tmSel value=\""+strTmSel+"\"> \n");
            out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
            out.println("      document.Resources.action='"+urlResAct+"'; \n");
            out.println("      document.Resources.submit(); \n");
            out.println("</script> \n");
            out.println("</form> \n");
            if (strTm!=null)
                out.println(getEditForm(iResId,paramRequest.getTopic(), paramRequest,strTm));
            else if (strTmSel!=null)
                out.println(getEditForm(iResId,paramRequest.getTopic(), paramRequest,strTmSel));
        }


        if (strConfirm!=null && strConfirm.equals("removed")) {
            out.println("<script>wbTree_executeAction('gotopath."+strTm+"');wbTree_reload();</script>");
            out.println("<font face=\"Verdana\" size=\"1\">");
            out.println(paramRequest.getLocaleString("frmResRemoved"));
            out.println("</font>");
            //return;
        }
        else if (strConfirm!=null && strConfirm.equals("updated")) {
            out.println("<script>");
            out.println("wbTree_executeAction('gotopath."+strTm+".dns."+iResId+"');");
            out.println("wbTree_reload();");
            out.println("</script>");
        }
        else if (strConfirm!=null && strConfirm.equals("notupdated")) {
            out.println("<font face=\"Verdana\" size=\"1\">");
            out.println(paramRequest.getLocaleString("frmResnotUpdated"));
            out.println("</font>");
        }
        else if (strConfirm!=null && strConfirm.equals("added")) {
            out.println("<script>");
            out.println("wbTree_executeAction('gotopath."+strTm+"');");
            out.println("wbTree_reload();");
            out.println("wbTree_executeAction('gotopath."+strTm+".dns."+iResId+"');");
            out.println("</script>");
        }
        else if (strConfirm!=null && strConfirm.equals("notadded")) {
            out.println("<script language=\"javascript\">");
            out.println(" wbStatus('"+paramRequest.getLocaleString("frmResnotAdded")+"');");
            out.println("</script>");
        }
        out.println("</form> \n");
    }

    private int getObjId(String strRec) {
        int iId=0;
        String[] strFields=strRec.split(":");
        iId=Integer.parseInt(strFields[1]);
        return iId;
    }

    private String [] getCatSortArray(String [] strArray, String strOrder, int iWBPage, String tmid) {
        Vector vRO = new Vector();
        Iterator<ResourceType> en1 = SWBContext.getGlobalWebSite().listResourceTypes();
        while (en1.hasNext()) {
            ResourceType ROT = en1.next();
            vRO.add(ROT);
        }
        en1 = SWBContext.getWebSite(tmid).listResourceTypes();
        while (en1.hasNext()) {
            boolean iE=false;
            ResourceType ROT = en1.next();
            for (int i=0; i<vRO.size();i++) {
                ResourceType RO = (ResourceType)vRO.get(i);
                if (RO.getResourceClassName().equals(ROT.getResourceClassName())) {
                    iE=true;
                    break;
                }
            }
            if (!iE) {
                vRO.add(ROT);
            }
        }
        Iterator it = propResourceTypes.keySet().iterator();
        int cont=0;
        while (it.hasNext()) {
            String strPropName="";
            boolean isExist=false;
            Object element = it.next();
            //Enumeration en = DBResourceType.getInstance().getResourceTypes(tmid).elements();
            Enumeration en = vRO.elements();
            while (en.hasMoreElements()) {
                ResourceType RO = (ResourceType)en.nextElement();
                strPropName=element.toString();
                //if (propResourceTypes.getProperty(element.toString()).equals(RO.getObjclass()) && RO.getTopicMapId().equals(tmid)) {
                if (propResourceTypes.getProperty(element.toString()).equals(RO.getResourceClassName())) {
                    isExist=true;
                    break;
                }
            }
            if (!isExist) {
                try {
                strArray[cont]=strPropName+":"+propResourceTypes.getProperty(strPropName);
                }
                catch( Exception e) {
                    log.error(e);
                }
                cont++;
            }
        }
        Arrays.sort(strArray,String.CASE_INSENSITIVE_ORDER);
        getPageRange(strArray.length,iWBPage);
        return strArray;
    }

    private String [] getStrSortArray(String [] strArray, String strOrder, int iWBPage, String tmid) {
        Iterator<ResourceType> en = SWBContext.getWebSite(tmid).listResourceTypes();
        int cont=0;
        if (strOrder.equals("tit")) {
            while (en.hasNext()) {
                ResourceType RO = en.next();
                if (RO.getWebSite().getId().equals(tmid)) {
                    strArray[cont]=RO.getTitle()+":"+RO.getId();
                    cont++;
                }
            }
        }
        else if (strOrder.equals("desc")) {
            while (en.hasNext()) {
                ResourceType RO = (ResourceType)en.next();
                if (RO.getWebSite().getId().equals(tmid)) {
                    strArray[cont]=RO.getDescription()+":"+RO.getId();
                    cont++;
                }
            }
        }

        else if (strOrder.equals("class")) {
            while (en.hasNext()) {
                ResourceType RO = (ResourceType)en.next();
                if (RO.getWebSite().getId().equals(tmid)) {
                    strArray[cont]=RO.getResourceClassName()+":"+RO.getId();
                    cont++;
                }
            }
        }
        else if (strOrder.equals("type")) {
            while (en.hasNext()) {
                ResourceType RO = (ResourceType)en.next();
                if (RO.getWebSite().getId().equals(tmid)) {
                    strArray[cont]=RO.getResourceMode()+":"+RO.getId();
                    cont++;
                }
            }
        }
        Arrays.sort(strArray,String.CASE_INSENSITIVE_ORDER);
        getPageRange(strArray.length,iWBPage);
        return strArray;
    }

    private String[] getIntSortArray(String[] iArray, int iWBPage, String tmid) {
        int cont=0;
        Iterator<ResourceType> en = SWBContext.getWebSite(tmid).listResourceTypes();
        while (en.hasNext()) {
            ResourceType RO = en.next();
            if (RO.getWebSite().getId().equals(tmid)) {
                iArray[cont]=RO.getId();
                cont++;
            }
        }
        Arrays.sort(iArray);
        getPageRange(iArray.length,iWBPage);
        return iArray;
    }

    private Vector getAllReources (String strTmnid) {
        Vector vRO = new Vector();
        Iterator<ResourceType> en1 = SWBContext.getGlobalWebSite().listResourceTypes();
        while (en1.hasNext()) {
            ResourceType ROT = en1.next();
            vRO.add(ROT);
        }
        en1 = SWBContext.getWebSite(strTmnid).listResourceTypes();
        while (en1.hasNext()) {
            boolean iE=false;
            ResourceType ROT = en1.next();
            for (int i=0; i<vRO.size();i++) {
                ResourceType RO = (ResourceType)vRO.get(i);
                if (RO.getResourceClassName().equals(ROT.getResourceClassName())) {
                    iE=true;
                    break;
                }
            }
            if (!iE) {
                vRO.add(ROT);
            }
        }
        return vRO;
    }

    private int getArraySize(String strTmnid, boolean fromCat) {
        Vector vRO = new Vector();
        int cont=0;
        if (!fromCat) {
            Iterator<ResourceType> en = SWBContext.getWebSite(strTmnid).listResourceTypes();
            while (en.hasNext()) {
                ResourceType RO = en.next();
                if (RO.getWebSite().getId().equals(strTmnid)) {
                    cont++;
                }
            }
        }
        else {
            vRO=getAllReources(strTmnid);
            Iterator it = propResourceTypes.keySet().iterator();
            while (it.hasNext()) {
                boolean isExist=false;
                Object element = it.next();
                Enumeration en = vRO.elements();
                while (en.hasMoreElements()) {
                    ResourceType RO = (ResourceType)en.nextElement();
                    if (propResourceTypes.getProperty(element.toString()).equals(RO.getResourceClassName())) {
                        isExist=true;
                        break;
                    }
                }
                if (!isExist) {
                    cont++;
                }
            }
        }
        return cont;
    }

    private String getStrObjTable(ResourceType RO, String bgcolor) {
        StringBuffer sbRet=new StringBuffer();
        sbRet.append("<tr class=\"valores\" bgcolor=\""+bgcolor+"\"> \n");
        sbRet.append("<td> \n");
        sbRet.append("<input type=\"radio\" name=\"id\" value=\""+RO.getId()+"\" title=\""+RO.getTitle()+"\" style=\"background:"+bgcolor+";\"> \n");
        sbRet.append("</td> \n");
        sbRet.append("<td> \n");
        sbRet.append(RO.getId());
        sbRet.append("</td> \n");
        sbRet.append("<td> \n");
        sbRet.append(RO.getTitle());
        sbRet.append("</td> \n");
        sbRet.append("<td> \n");
        sbRet.append(RO.getTitle());
        sbRet.append("</td> \n");
        sbRet.append("<td> \n");
        sbRet.append(RO.getDescription());
        sbRet.append("</td> \n");
        sbRet.append("<td> \n");
        sbRet.append(RO.getResourceClassName());
        sbRet.append("</td> \n");
        sbRet.append("<td> \n");
        if(RO.getResourceMode()==1) sbRet.append(SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBAResourceTypes","frmComboContent"));
        if(RO.getResourceMode()==2) sbRet.append(SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBAResourceTypes","frmComboStrategy"));
        if(RO.getResourceMode()==3) sbRet.append(SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBAResourceTypes","frmComboSystem"));
        if(RO.getResourceMode()==4) sbRet.append(SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBAResourceTypes","frmComboInner"));
        if(RO.getResourceMode()==5) sbRet.append(SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBAResourceTypes","frmPortletContent"));
        if(RO.getResourceMode()==6) sbRet.append(SWBUtils.TEXT.getLocaleString("com.infotec.wb.admin.resources.WBAResourceTypes","frmPortletStrategy"));
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        return sbRet.toString();
    }

    private void getPageRange(int iSize, int iPageNum) {
        iPage = I_INIT_PAGE;
        if (iPageNum>1)
            iPage = iPageNum;
        if(iSize > I_PAGE_SIZE){
            iTotPage = iSize / I_PAGE_SIZE;
            int i_ret = iSize % I_PAGE_SIZE;
            if(i_ret > 0)
                iTotPage = iTotPage + 1;
        }
        else
            iTotPage=1;
        iIniPage =  (I_PAGE_SIZE*iPage) - I_PAGE_SIZE ;
        iFinPage = I_PAGE_SIZE*iPage;
        if(iSize < I_PAGE_SIZE*iPage)
            iFinPage = iSize;
    }

    /** Método en donde se ejecuta el Java Script
     * @param paramsRequest Objeto WBParamRequest
     */
    private String getJavaScript(SWBParamRequest paramsRequest) {
        StringBuffer sbRet=new StringBuffer();
        try {
            sbRet.append("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
            sbRet.append("  var strResName=''; \n");
            sbRet.append("  var strResId=''; \n");
            sbRet.append("  function sort(col,page) { \n");
            sbRet.append("      document.Resources.sort.value = col; \n");
            sbRet.append("      document.Resources.WBPage.value = page; \n");
            sbRet.append("      document.Resources.submit(); \n");
            sbRet.append("  } \n");
            sbRet.append("  function sendMapFilter(col,page) { \n");
            sbRet.append("      document.Resources.sort.value = col; \n");
            sbRet.append("      document.Resources.WBPage.value = page; \n");
            sbRet.append("      document.Resources.submit(); \n");
            sbRet.append("  } \n");
            sbRet.append("  function doPaging(page,col) { \n");
            sbRet.append("      document.Resources.WBPage.value = page; \n");
            sbRet.append("      document.Resources.sort.value = col; \n");
            sbRet.append("      document.Resources.submit(); \n");
            sbRet.append("  } \n");
            sbRet.append("function send(action) { \n");
            SWBResourceURL urlResAct=paramsRequest.getActionUrl();
            sbRet.append("    var agree=false; \n");
            sbRet.append("    if (action=='remove'){ \n");
            sbRet.append("      var selected=radioselected(); \n");
            sbRet.append("      if (selected) \n");
            sbRet.append("         agree=confirm('¿"+paramsRequest.getLocaleString("jsConfirmRemove")+" '+strResName+'?'); \n");
            sbRet.append("      else \n");
            sbRet.append("         alert ('"+paramsRequest.getLocaleString("jsChooseRes")+"');  \n");
            sbRet.append("      document.Resources.act.value = action; \n");
            sbRet.append("      document.Resources.ResId.value = strResId; \n");
            sbRet.append("    } \n");
            sbRet.append("    if (action=='add'){ \n");
            sbRet.append("      document.Resources.act.value = action; \n");
            sbRet.append("      agree=true; \n");
            sbRet.append("    } \n");
            sbRet.append("    if (action=='portlet'){ \n");
            sbRet.append("      document.Resources.act.value = action; \n");
            sbRet.append("      agree=true; \n");
            sbRet.append("    } \n");
            sbRet.append("    if (action=='add'){ \n");
            sbRet.append("      var agree=validateForm(); \n");
            sbRet.append("      document.Resources.act.value = action; \n");
            sbRet.append("    } \n");
            sbRet.append("    if (action=='addCat'){ \n");
            sbRet.append("      document.Resources.act.value = action; \n");
            sbRet.append("      agree=true; \n");
            sbRet.append("    } \n");
            sbRet.append("    if (action=='edit'){ \n");
            sbRet.append("      var selected=radioselected(); \n");
            sbRet.append("      if (!selected) \n");
            sbRet.append("         alert ('"+paramsRequest.getLocaleString("jsChooseRes")+"');  \n");
            sbRet.append("      else \n");
            sbRet.append("         var agree=true; \n");
            sbRet.append("      document.Resources.act.value = action; \n");
            sbRet.append("      document.Resources.ResId.value = strResId; \n");
            sbRet.append("    } \n");
            sbRet.append("    if (action=='reload'){ \n");
            sbRet.append("      var selected=radioselected(); \n");
            sbRet.append("      if (selected) \n");
            sbRet.append("         agree=confirm('¿"+paramsRequest.getLocaleString("jsConfirmReload")+" '+strResName+'?'); \n");
            sbRet.append("      else \n");
            sbRet.append("         alert ('"+paramsRequest.getLocaleString("jsChooseRes")+"');  \n");
            sbRet.append("      document.Resources.act.value = action; \n");
            sbRet.append("      document.Resources.ResId.value = strResId; \n");
            sbRet.append("    } \n");
            sbRet.append("    if (action=='save'){ \n");
            sbRet.append("      var agree=validateForm(); \n");
            sbRet.append("      document.Resources.action='"+urlResAct+"'; \n");
            sbRet.append("      document.Resources.act.value = action; \n");
            sbRet.append("    } \n");
            sbRet.append("    if (agree) \n");
            sbRet.append("       document.Resources.submit(); \n");
            sbRet.append("} \n");
            sbRet.append("function radioselected() { \n");
            sbRet.append("  if(document.Resources.id.checked==true) { \n");
            sbRet.append("      return true;  \n");
            sbRet.append("  } \n");
            sbRet.append("  if (document.Resources.id.value!=\"\") { \n");
            sbRet.append("      for(i=0;i<document.Resources.id.length;i++) { \n");
            sbRet.append("          if(document.Resources.id[i].checked==true) { \n");
            sbRet.append("              strResId=document.Resources.id[i].value;  \n");
            sbRet.append("              strResName=document.Resources.id[i].title;  \n");
            sbRet.append("              return true;  \n");
            sbRet.append("          } \n");
            sbRet.append("      } \n");
            sbRet.append("  } \n");
            sbRet.append("} \n");
            sbRet.append("function radioselect() { \n");
            sbRet.append("    if (document.Resources.res.value!=\"\")  \n");
            sbRet.append("      alert (document.Resources.res.value);  \n");
            sbRet.append("} \n");
            sbRet.append("function cache() { \n");
            sbRet.append("    if (eval('document.Resources.cache_res.checked')) {  \n");
            sbRet.append("      document.Resources.time_res.disabled=false;  \n");
            sbRet.append("      document.Resources.time_res.focus();  \n");
            sbRet.append("    }  \n");
            sbRet.append("    else  \n");
            sbRet.append("      document.Resources.time_res.disabled=true;  \n");
            sbRet.append("} \n");
            sbRet.append("function onlyDigits(e) { \n");
            sbRet.append("  var isIE = navigator.appName == 'Microsoft Internet Explorer'?true:false; \n");
            sbRet.append("  var isNS = navigator.appName == 'Netscape'?true:false; \n");
            sbRet.append("  var key = (isIE) ? window.event.keyCode : e.which; \n");
            sbRet.append("  var isNum = (key > 47 && key < 58) ? true:false; \n");
            sbRet.append("  if(!isNum) {\n");
            sbRet.append("      alert('"+paramsRequest.getLocaleString("jsOnlyDigits")+"'); \n");
            sbRet.append("      return false; \n");
            sbRet.append("  } \n");
            sbRet.append("  else \n");
            sbRet.append("      return true; \n");
            sbRet.append("} \n");

            sbRet.append("\n function validateForm() {");
            sbRet.append("\n    var _f=document.Resources;");
            sbRet.append("\n    var _regExp=/select/i;");
            sbRet.append("\n    for (i=0; i<_f.elements.length; i++ ) { ");
            sbRet.append("\n        if(_f.elements[i]!=undefined) {");
            // Valida los objetos de la forma tipo text o textarea
            sbRet.append("\n            if(_f.elements[i].type==\"text\" || _f.elements[i].type==\"textarea\") {");
            sbRet.append("\n                if (_f.elements[i].name!='ext_att_res') {");
            sbRet.append("\n                if (_f.elements[i].name!='time_res') {");
            sbRet.append("\n                    if (_f.elements[i].name!='bundle_res') {");
            sbRet.append("\n                        if (_f.elements[i].value=='') {");
            sbRet.append("\n                            alert ('"+paramsRequest.getLocaleString("jsEmptyField")+"');");
            sbRet.append("\n                            _f.elements[i].focus();");
            sbRet.append("\n                            return false;");
            sbRet.append("\n                        }");
            sbRet.append("\n                    }");
            sbRet.append("\n                }");
            sbRet.append("\n                }");
            sbRet.append("\n            }");
            // Valida los objetos de la forma tipo select
            /*sbRet.append("\n            if(!id=='create') {");
            sbRet.append("\n                if(_f.elements[i].type.match(_regExp)) {");
            sbRet.append("\n                    alert (_f.elements[i].name);");
            sbRet.append("\n                    if (_f.elements[i].value=='') {");
            sbRet.append("\n                        _f.elements[i].focus();");
            sbRet.append("\n                        return false;");
            sbRet.append("\n                    }");
            sbRet.append("\n                }");
            sbRet.append("\n            }");    */
            sbRet.append("\n        }");
            sbRet.append("\n    }");
            sbRet.append("\n    return true;");
            sbRet.append("\n }");
            sbRet.append("</script> \n");
        } catch (SWBResourceException e) {
            log.error("Error on method getForma() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(),e);
        }
        return sbRet.toString();
    }

    /** Método que genera la forma de vista al usuario de la forma inicial
     * @param strOrder Objeto HttpServletRequest
     * @param strTm String del topicmap actual
     * @param strTmSel String del topicmap seleccionado
     * @param paramsRequest Objeto WBParamRequest
     * @throws AFException Excepción del Aplication FrameworkparamsRequest.getLocaleString("frmComboContent")+"</option> \n");
     * sbRet.append("  <option value=\"2\">"+paramsRequest.getLocaleString("frmComboStrategy")+"</option> \n");
     * sbRet.append("  <option value=\"3\">"+paramsRequest.getLocaleString("frmComboSystem")+"</option> \n");
     * sbRet.append("  <option value=\"4\">"+paramsRequest.getLocaleString("frmComboInner")
     */
    private String getIniForm(String strOrder, String strTm, String strTmSel, SWBParamRequest paramsRequest) throws SWBResourceException {
        StringBuffer sbRet=new StringBuffer();
        sbRet.append(getJavaScript(paramsRequest));
        sbRet.append("<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"\" width=100%> \n");
        sbRet.append("<tr align=\"center\" bgcolor=\"\"> \n");
        if (strTm!=null) {
            //Iterator<WebSite> enumTopicMaps = SWBContext.listWebSites();
            sbRet.append("<td align=\"left\" class=\"datos\"> ");
            sbRet.append("<input type=hidden name=\"txtMap\" value=\""+strTm+"\"> ");

            /*
           sbRet.append("<select name=\"txtMap\" class=\"campos\" onChange=\"sendMapFilter('map',"+iPage+")\"> ");
           while (enumTopicMaps.hasNext()) {
               RecTopicMap recMap = ((WebSite) enumTopicMaps.next()).getDbdata();
               if (recMap.getActive() == 1 && recMap.getId().equals(strTmSel) && AdmFilterMgr.getInstance().haveAccess2TopicMap(paramsRequest.getUser(),recMap.getId())==2)
                   sbRet.append(" <option value=\""+recMap.getId()+"\" selected>"+recMap.getTitle()+"</option> ");
               else if (recMap.getActive() == 1 && !recMap.getId().equals(strTmSel) && AdmFilterMgr.getInstance().haveAccess2TopicMap(paramsRequest.getUser(),recMap.getId())==2)
                   sbRet.append(" <option value=\""+recMap.getId()+"\">"+recMap.getTitle()+"</option> ");
           }
           sbRet.append("</select>");
            */
            sbRet.append("</td> ");
        }
        sbRet.append("<td colspan=\"7\" class=\"datos\"><span class=\"datos\">"+paramsRequest.getLocaleString("frmResTitle")+"</span><br><br>");
        sbRet.append("&nbsp;&nbsp;&nbsp;&nbsp;"+paramsRequest.getLocaleString("frmPage")+" " + iPage + " "+paramsRequest.getLocaleString("frmOf")+" " + iTotPage+"&nbsp;&nbsp;&nbsp;&nbsp;");
        if(iPage > 1){
            sbRet.append("<a class=\"link\" href=\"javascript:doPaging(" + (iPage - 1) + ",'"+strOrder+"');\"><<</a>&nbsp;");
        }
        for (int i=iPage-9;i<iPage;i++) {
            if (i>0)
                sbRet.append("<a class=\"link\" href=\"javascript:doPaging(" + (i) +",'"+strOrder+"');\">"+i+"&nbsp;</a>");
        }
        for (int i=iPage;i<=iPage+10;i++) {
            if (i<=iTotPage)
                sbRet.append("<a class=\"link\" href=\"javascript:doPaging(" + (i) +",'"+strOrder+"');\">"+i+"&nbsp;</a>");
        }
        if(iPage > 0 && (iPage + 1 <= iTotPage)) {
            sbRet.append("<a class=\"link\" href=\"javascript:doPaging(" + (iPage + 1) +",'"+strOrder+"');\">>></a>");
        }
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td>&nbsp;</td> \n");
        sbRet.append("<td class=\"tabla\"><a class=\"link\" href=\"javascript:sort('order',"+iPage+")\">"+paramsRequest.getLocaleString("frmColId")+"</a></td> \n");
        sbRet.append("<td class=\"tabla\"><a class=\"link\" href=\"javascript:sort('tit',"+iPage+")\">"+paramsRequest.getLocaleString("frmColTitle")+"</a></td> \n");
        sbRet.append("<td class=\"tabla\"><a class=\"link\" href=\"javascript:sort('tit',"+iPage+")\">"+paramsRequest.getLocaleString("frmColName")+"</a></td> \n");
        sbRet.append("<td class=\"tabla\"><a class=\"link\" href=\"javascript:sort('desc',"+iPage+")\">"+paramsRequest.getLocaleString("frmColDesc")+"</a></td> \n");
        sbRet.append("<td class=\"tabla\"><a class=\"link\" href=\"javascript:sort('class',"+iPage+")\">"+paramsRequest.getLocaleString("frmColClass")+"</a></td> \n");
        sbRet.append("<td class=\"tabla\"><a class=\"link\" href=\"javascript:sort('type',"+iPage+")\">"+paramsRequest.getLocaleString("frmColResType")+"</a></td> \n");
        sbRet.append("</tr> \n");
        String rowColor="";
        boolean cambiaColor = true;
        if (strOrder==null || (strOrder!=null && (strOrder.equals("order") || strOrder.equals("map")))) {
            for (int i=iIniPage; i<iFinPage; i++) {
                rowColor="#EFEDEC";
                if(!cambiaColor) rowColor="#FFFFFF";
                cambiaColor = !(cambiaColor);
                ResourceType RO = SWBContext.getWebSite(strTm).getResourceType(iArray[i]);
                sbRet.append(getStrObjTable(RO,rowColor));
            }
        }
        if (strOrder!=null && (strOrder.equals("tit")||strOrder.equals("desc")||strOrder.equals("class")||strOrder.equals("type"))) {
            for (int i=iIniPage; i<iFinPage; i++) {
                rowColor="#EFEDEC";
                if(!cambiaColor) rowColor="#FFFFFF";
                cambiaColor = !(cambiaColor);
                ResourceType RO = SWBContext.getWebSite(strTm).getResourceType(Integer.toString(getObjId(strArray[i])));
                sbRet.append(getStrObjTable(RO,rowColor));
            }
        }
        sbRet.append("<tr  bgcolor=\"\" > \n");
        sbRet.append("<td colspan=\"7\" class=\"valores\" align=\"right\"><HR size=\"1\" noshade> \n");
        if (SWBPortal.getResourceMgr().isResurceReloader()) {
            sbRet.append("\n <input type=button  class=\"boton\" name=Reload onClick='javascript:send(\"reload\")' value="+paramsRequest.getLocaleString("btnReload")+">");
            sbRet.append("&nbsp;");
        }
        if (portletSupport) {
            sbRet.append("\n <input type=button  class=\"boton\" name=Portlet onClick='javascript:send(\"portlet\")' value=\""+paramsRequest.getLocaleString("btnPortlet")+"\">");
            sbRet.append("&nbsp;");
        }
        sbRet.append("\n <input type=button  class=\"boton\" name=Remove onClick='javascript:send(\"remove\");' value="+paramsRequest.getLocaleString("btnRemove")+">");
        sbRet.append("&nbsp;");
        sbRet.append("\n <input type=button  class=\"boton\" name=Add onClick='javascript:send(\"add\");' value="+paramsRequest.getLocaleString("btnAdd")+">");
        sbRet.append("&nbsp;");
        sbRet.append("\n <input type=button  class=\"boton\" name=AddCat onClick='javascript:send(\"addCat\");' value=\""+paramsRequest.getLocaleString("btnAddfromCat")+"\">");
        sbRet.append("&nbsp;");
        sbRet.append("\n <input type=button  class=\"boton\" name=Open onClick='javascript:send(\"edit\")' value="+paramsRequest.getLocaleString("btnOpen")+">");
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("</table> \n");
        sbRet.append("<input type=hidden name=sort value=\"\"> \n");
        sbRet.append("<input type=hidden name=act value=\"\"> \n");
        sbRet.append("<input type=hidden name=ResId value=\"\"> \n");
        sbRet.append("<input type=hidden name=WBPage value=\"\"> \n");
        if (strTm!=null)
            sbRet.append("<input type=hidden name=tmSel value=\""+strTm+"\"> \n");
        else if (strTmSel!=null)
            sbRet.append("<input type=hidden name=tmSel value=\""+strTmSel+"\"> \n");
        return sbRet.toString();
    }

    /** Método que genera la forma de vista al usuario de la forma de captura
     * @param topic Topico donde se asiganará el recurso
     * @param tm String del topicmap seleccionado
     * @param paramsRequest Objeto WBParamRequest
     * @throws AFException Excepción del Aplication Framework
     */
    private String getAddForm(WebPage topic, SWBParamRequest paramsRequest, String tm, HttpServletRequest request) throws SWBResourceException {
        StringBuffer sbRet=new StringBuffer();
        String ObjClass="",ClassName="",BundleFile="",ClassDisName="",description="", strcached="";
        int type=0,cachetime=0;

        if(request.getParameter("class_res")!=null) ObjClass =  request.getParameter("class_res");
        if(request.getParameter("nom_res")!=null) ClassName =  request.getParameter("nom_res");
        if(request.getParameter("bundle_res")!=null) BundleFile =  request.getParameter("bundle_res");
        if(request.getParameter("nom_dis_res")!=null) ClassDisName = request.getParameter("nom_dis_res");
        if(request.getParameter("desc_res")!=null) description = request.getParameter("desc_res");
        if(request.getParameter("txtResType")!=null) type = Integer.parseInt(request.getParameter("txtResType"));
        if (request.getParameter("cache_res")!=null && request.getParameter("cache_res").equals("on")) strcached="checked";
        if(request.getParameter("time_res")!=null) cachetime = Integer.parseInt(request.getParameter("time_res"));

        sbRet.append(getJavaScript(paramsRequest));
        String strTitle=paramsRequest.getLocaleString("frmTitle");
        if (request.getParameter("act").equals("portlet"))
            strTitle=paramsRequest.getLocaleString("frmPortletGUI");
        sbRet.append("<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"\" width=100%> \n");
        sbRet.append("<tr align=\"left\" bgcolor=\"\"> \n");
        sbRet.append("<td colspan=\"7\"><span class=\"tabla\">"+paramsRequest.getLocaleString("frmResTitle")+"</span>");
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr bgcolor=\"\" > \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=250>"+strTitle+":</td> \n");
        if (request.getParameter("act").equals("portlet"))
        {
            sbRet.append("<td class=\"valores\">");
            sbRet.append("<select name=\"nom_res\" class=\"campos\" size=\"1\">");
            //TODO: FactoryManager
//            Iterator it=SWBPortal.WBFactoryMgr.getServletContextNames().iterator();
//            while(it.hasNext())
//            {
//                String context=(String)it.next();
//                sbRet.append("<option value=\""+context+"\">"+context+"</option>\n");
                  sbRet.append("<option value=\"\">Pendiente</option>\n");  // temporal, quitar despues de que este listo FactoryManager
//            }
            sbRet.append("</select>");
            sbRet.append("</td> \n");
        }else
        {
            sbRet.append("<td class=\"valores\"><input name=\"nom_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"40\" value=\""+ClassName+"\"></td> \n");
        }
        sbRet.append("</tr> \n");
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=250>"+paramsRequest.getLocaleString("frmDisplayName")+":</td> \n");
        sbRet.append("<td class=\"valores\"><input name=\"nom_dis_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"40\" value=\""+ClassDisName+"\"></td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=250>"+paramsRequest.getLocaleString("frmDesc")+":</td> \n");
        sbRet.append("<td class=\"valores\"><textarea name=\"desc_res\" cols=\"38\" rows=\"8\" wrap=\"VIRTUAL\" class=\"campos\">"+description+"</textarea></td> \n");
        sbRet.append("</tr> \n");
        if (!request.getParameter("act").equals("portlet")) {
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=250>"+paramsRequest.getLocaleString("frmClassName")+":</td> \n");
            sbRet.append("<td class=\"valores\"><input name=\"class_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"100\" value=\""+ObjClass+"\"></td> \n");
            sbRet.append("</tr> \n");
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=250>"+paramsRequest.getLocaleString("frmBundleFile")+":</td> \n");
            sbRet.append("<td class=\"valores\"><input name=\"bundle_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"100\" value=\""+BundleFile+"\"></td> \n");
            sbRet.append("</tr> \n");
        }
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=250>"+paramsRequest.getLocaleString("frmResType")+":</td> \n");
        sbRet.append("<td class=\"valores\">");
        sbRet.append("<select name=\"txtResType\" class=\"campos\" size=\"1\"> \n");
        String strSelected1 = "";
        if(type==1) strSelected1 = "selected";
        String strSelected2 = "";
        if(type==2) strSelected2 = "selected";
        String strSelected3 = "";
        if(type==3) strSelected3 = "selected";
        String strSelected4 = "";
        if(type==4) strSelected4 = "selected";
        String strSelected5 = "";
        if(type==5) strSelected5 = "selected";
        String strSelected6 = "";
        if(type==6) strSelected6 = "selected";
        if (!request.getParameter("act").equals("portlet")) {
            sbRet.append("  <option value=\"1\" "+strSelected1+">"+paramsRequest.getLocaleString("frmComboContent")+"</option> \n");
            sbRet.append("  <option value=\"2\" "+strSelected2+">"+paramsRequest.getLocaleString("frmComboStrategy")+"</option> \n");
            sbRet.append("  <option value=\"3\" "+strSelected3+">"+paramsRequest.getLocaleString("frmComboSystem")+"</option> \n");
            sbRet.append("  <option value=\"4\" "+strSelected4+">"+paramsRequest.getLocaleString("frmComboInner")+"</option> \n");
        }
        else {
            sbRet.append("  <option value=\"5\" "+strSelected5+">"+paramsRequest.getLocaleString("frmPortletContent")+"</option> \n");
            sbRet.append("  <option value=\"6\" "+strSelected6+">"+paramsRequest.getLocaleString("frmPortletStrategy")+"</option> \n");
        }
        sbRet.append("</select>");
        if (!request.getParameter("act").equals("portlet")) {
            sbRet.append("&nbsp;&nbsp;&nbsp;<input name=\"cache_res\" type=\"checkbox\" class=\"campos\" "+strcached+" onClick=\"javascript:cache();\">&nbsp;&nbsp;"+paramsRequest.getLocaleString("frmIsCached")+"<td>");
            sbRet.append("</tr> \n");
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=250>"+paramsRequest.getLocaleString("frmCachedTime")+":</td> \n");
            sbRet.append("<td class=\"valores\"><input name=\"time_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"20\" disabled=\"true\" onkeypress=\"return onlyDigits(event);\" value=\""+cachetime+"\"></td> \n");
            sbRet.append("</tr> \n");
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmExtAtt")+":</td> \n");
            sbRet.append("<td class=\"valores\"><textarea name=\"ext_att_res\" cols=\"38\" rows=\"8\" wrap=\"VIRTUAL\"></textarea></td> \n");
            sbRet.append("</tr> \n");
        }
        sbRet.append("<tr align=\"center\" bgcolor=\"\"> \n");
        sbRet.append("<td colspan=\"2\" align=\"right\" class=\"valores\"><HR size=\"1\" noshade> \n");
        sbRet.append("\n <input type=reset  class=\"boton\" name=Clean value="+paramsRequest.getLocaleString("btnReset")+">");
        sbRet.append("&nbsp;");

        SWBResourceURL urlBack = paramsRequest.getRenderUrl();
        urlBack.setParameter("tm",tm);

        //sbRet.append("\n <input type=button  class=\"boton\" name=Back onClick=location='"+strUrl+topic.getId()+"'; value="+paramsRequest.getLocaleString("btnBack")+">");
        sbRet.append("\n <input type=button  class=\"boton\" name=Back onClick=location='"+urlBack.toString()+"'; value="+paramsRequest.getLocaleString("btnBack")+">");
        sbRet.append("&nbsp;");
        sbRet.append("\n <input type=button  class=\"boton\" name=Add onClick='javascript:send(\"save\");' value="+paramsRequest.getLocaleString("btnAdd")+">");
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        if(request.getParameter("confirm")!=null &&  request.getParameter("confirm").equals("notadded")){
            sbRet.append("<tr align=\"center\" bgcolor=\"\"> \n");
            sbRet.append("<td colspan=\"2\" align=left class=\"tabla\"> \n");
            sbRet.append("No se pudo agregar el Tipo de Recurso");
            sbRet.append("</td> \n");
            sbRet.append("</tr> \n");
        }
        sbRet.append("</table> \n");
        sbRet.append("<input type=hidden name=sort value=\"\"> \n");
        sbRet.append("<input type=hidden name=act value=\"\"> \n");
        sbRet.append("<input type=hidden name=act2 value=\"add\"> \n");
        sbRet.append("<input type=hidden name=tmSel value=\""+tm+"\"> \n");
        if (request.getParameter("act").equals("portlet")) {
            sbRet.append("<input type=hidden name=class_res value=\"com.infotec.wb.portlet.PortletResource\"> \n");
        }
        if(!strcached.equals("")) sbRet.append("\n<script language=\"javascript\"> cache(); </script>\n");
        return sbRet.toString();
    }

    /** Método que genera la forma de vista al usuario de la forma de captura
     * @param strTm String del topicmap seleccionado
     * @param paramsRequest Objeto WBParamRequest
     * @throws AFException Excepción del Aplication Framework
     */
    private String getAddCat(String strOrder, String strTm, HttpServletRequest request, SWBParamRequest paramsRequest) throws SWBResourceException {
        if (strOrder==null || strOrder.equals(""))
            strOrder="order";
        int iWBPage=1;
        if (request.getParameter("WBPage")!=null && !request.getParameter("WBPage").equals("")){
            iWBPage=Integer.parseInt(request.getParameter("WBPage"));
        }
        int iArraySize = getArraySize(strTm,true);
        if ((strOrder!=null && !strOrder.equals(""))) {
            strArray = new String[iArraySize];
            strArray=getCatSortArray(strArray,strOrder,iWBPage,strTm);
        }
        StringBuffer sbRet=new StringBuffer();
        sbRet.append(getJavaScript(paramsRequest));
        sbRet.append("<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"\" width=100%> \n");
        sbRet.append("<tr align=\"center\" bgcolor=\"\"> \n");
        if (strTm!=null) {
            sbRet.append("<td align=\"left\" class=\"datos\"> ");
            sbRet.append("<input type=hidden name=\"txtMap\" value=\""+strTm+"\"> ");
            sbRet.append("</td> ");
        }
        sbRet.append("<td colspan=\"3\" class=\"datos\"><span class=\"datos\">"+paramsRequest.getLocaleString("frmResTitle")+"</span><br><br>");
        sbRet.append("&nbsp;&nbsp;&nbsp;&nbsp;"+paramsRequest.getLocaleString("frmPage")+" " + iPage + " "+paramsRequest.getLocaleString("frmOf")+" " + iTotPage+"&nbsp;&nbsp;&nbsp;&nbsp;");
        if(iPage > 1){
            sbRet.append("<a class=\"link\" href=\"javascript:doPaging(" + (iPage - 1) + ",'"+strOrder+"');\"><<</a>&nbsp;");
        }
        for (int i=iPage-9;i<iPage;i++) {
            if (i>0)
                sbRet.append("<a class=\"link\" href=\"javascript:doPaging(" + (i) +",'"+strOrder+"');\">"+i+"&nbsp;</a>");
        }
        for (int i=iPage;i<=iPage+10;i++) {
            if (i<=iTotPage)
                sbRet.append("<a class=\"link\" href=\"javascript:doPaging(" + (i) +",'"+strOrder+"');\">"+i+"&nbsp;</a>");
        }
        if(iPage > 0 && (iPage + 1 <= iTotPage)) {
            sbRet.append("<a class=\"link\" href=\"javascript:doPaging(" + (iPage + 1) +",'"+strOrder+"');\">>></a>");
        }
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td>&nbsp;</td> \n");
        sbRet.append("<td class=\"tabla\"><a class=\"link\" href=\"javascript:sort('tit',"+iPage+")\">"+paramsRequest.getLocaleString("frmColTitle")+"</a></td> \n");
        sbRet.append("<td class=\"tabla\"><a class=\"link\" href=\"javascript:sort('class',"+iPage+")\">"+paramsRequest.getLocaleString("frmColClass")+"</a></td> \n");
        sbRet.append("</tr> \n");
        String rowColor="";
        boolean cambiaColor = true;
        if (strOrder!=null) {
            for (int i=iIniPage; i<iFinPage; i++) {
                rowColor="#EFEDEC";
                if(!cambiaColor) rowColor="#FFFFFF";
                cambiaColor = !(cambiaColor);
                String[] strFields=strArray[i].toString().split(":");
                String strName=strFields[0];
                String strClass=strFields[1];
                sbRet.append("<tr class=\"valores\" bgcolor=\""+rowColor+"\"> \n");
                sbRet.append("<td> \n");
                sbRet.append("<input type=\"radio\" name=\"id\" value=\""+strClass+"\" title=\""+strName+"\" style=\"background:"+rowColor+";\"> \n");
                sbRet.append("</td> \n");
                sbRet.append("<td> \n");
                sbRet.append(strName);
                sbRet.append("</td> \n");
                sbRet.append("<td> \n");
                sbRet.append(strClass);
                sbRet.append("</td> \n");
                sbRet.append("</tr> \n");
            }
        }
        sbRet.append("<tr  bgcolor=\"\" > \n");
        sbRet.append("<td colspan=\"3\" class=\"valores\" align=\"right\"><HR size=\"1\" noshade> \n");
        if (SWBPortal.getResourceMgr().isResurceReloader()) {
            sbRet.append("\n <input type=button  class=\"boton\" name=Reload onClick='javascript:send(\"reload\")' value="+paramsRequest.getLocaleString("btnReload")+">");
            sbRet.append("&nbsp;");
        }
        SWBResourceURL urlBack = paramsRequest.getRenderUrl();
        urlBack.setParameter("tm",strTm);
        sbRet.append("\n <input type=button  class=\"boton\" name=Back onClick=location='"+urlBack.toString()+"'; value="+paramsRequest.getLocaleString("btnBack")+">");
        sbRet.append("&nbsp;");
        sbRet.append("\n <input type=button  class=\"boton\" name=Open onClick='javascript:send(\"edit\")' value="+paramsRequest.getLocaleString("btnAdd")+">");
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("</table> \n");
        sbRet.append("<input type=hidden name=sort value=\"\"> \n");
        sbRet.append("<input type=hidden name=act value=\"addCat\"> \n");
        sbRet.append("<input type=hidden name=ResId value=\"\"> \n");
        sbRet.append("<input type=hidden name=WBPage value=\"\"> \n");
        sbRet.append("<input type=hidden name=tmSel value=\""+strTm+"\"> \n");
        return sbRet.toString();
    }

    /** Método que genera la forma de vista al usuario de la forma de edición
     * @param strResId string del indentificador numérico del recurso
     * @param tm String del topicmap seleccionado
     * @param paramsRequest Objeto WBParamRequest
     * @throws AFException Excepción del Aplication Framework
     */
    private String getEditForm(String strResId, WebPage topic, SWBParamRequest paramsRequest, String tm) throws SWBResourceException {
        StringBuffer sbRet=new StringBuffer();
        int iResId=0;
        try {
        if (strResId!=null && !strResId.equals(""))
            iResId=Integer.parseInt(strResId);
        }
        catch(NumberFormatException e) {
            log.error("It's from catalog - WBAResourceTypes.getEditForm();",e);
        }
        ResourceType recObj=null;
        String strTitle="";
        String strDisplayName="";
        String strDescription="";
        String strClass="";
        String strBundle="";
        String strExtendendAtt="";
        int iType=0;
        int iCache=0;
        String strTitleLabel=paramsRequest.getLocaleString("frmTitle");
        if (paramsRequest.getAction().equals("portlet"))
            strTitleLabel=paramsRequest.getLocaleString("frmPortletGUI");
        if (iResId>0) {
            recObj=SWBContext.getWebSite(tm).getResourceType(Integer.toString(iResId));
            if (recObj.getTitle()!=null)
                strTitle=recObj.getTitle();
            if (recObj.getTitle()!=null)
                strDisplayName=recObj.getTitle();
            if (recObj.getDescription()!=null)
                strDescription=recObj.getDescription();
            if (recObj.getResourceClassName()!=null)
                strClass=recObj.getResourceClassName();
            if (recObj.getResourceBundle()!=null)
                strBundle=recObj.getResourceBundle();
            if (recObj!=null) {
                iType=recObj.getResourceMode();
                iCache=recObj.getResourceCache();
            }
            //TODO: Resource.getXML()
//            if (recObj.getXml()!=null)
//                strExtendendAtt=recObj.getXml();
        }
        else {
            try {
                //String strPath="/WEB-INF/classes/";
                String strClassPath="/"+strResId.replace('.','/')+"Conf.xml";
                InputStream fis = getClass().getResourceAsStream(strClassPath);
                Document doc=SWBUtils.XML.xmlToDom(fis);
                String xpath = "/resource";
                NodeList nlTools= XPathAPI.selectNodeList(doc, xpath);
                for (int i=0;i<nlTools.getLength();i++) {
                    Node nTool = (Node)nlTools.item(i);
                    strTitle=XPathAPI.eval(nTool, "resource-name/text()").toString();
                    strDisplayName=XPathAPI.eval(nTool, "display-name[@xml:lang='"+paramsRequest.getTopic().getWebSite().getLanguage().getId().substring(6)+"']/text()").toString();
                    strDescription=XPathAPI.eval(nTool, "description[@xml:lang='"+paramsRequest.getTopic().getWebSite().getLanguage().getId().substring(6)+"']/text()").toString();
                    strClass=XPathAPI.eval(nTool, "resource-class/text()").toString();
                    strBundle=XPathAPI.eval(nTool, "resource-bundle/text()").toString();
                    iType=Integer.parseInt(XPathAPI.eval(nTool, "resource-type/text()").toString());
                    iCache=Integer.parseInt(XPathAPI.eval(nTool, "expiration-cache/text()").toString());
                    strExtendendAtt=XPathAPI.eval(nTool, "extended-attributes/text()").toString();
                }
                if(strExtendendAtt!=null && strExtendendAtt.trim().length()>0)
                {
                    String strXML=SWBUtils.XML.domToXml(doc);
                    strExtendendAtt=strXML.substring(strXML.indexOf("<extended-attributes>"),strXML.lastIndexOf("</extended-attributes>")+22).trim();
                }else
                {
                    strExtendendAtt="";
                }
            }
            catch (Exception e) {
                log.error("Error in WBAResourceTypes.getEditForm();",e);
            }
        }
        sbRet.append(getJavaScript(paramsRequest));
        sbRet.append("<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"\" width=100%> \n");
        sbRet.append("<tr align=\"left\" bgcolor=\"\"> \n");
        sbRet.append("<td colspan=\"2\"><span class=\"tabla\">"+paramsRequest.getLocaleString("frmResTitle")+"</span>");
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=150>"+strTitleLabel+":</td> \n");
        sbRet.append("<td class=\"valores\"><input name=\"nom_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"40\" value=\""+strTitle+"\"></td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmDisplayName")+":</td> \n");
        sbRet.append("<td class=\"valores\"><input name=\"nom_dis_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"40\" value=\""+strDisplayName+"\"></td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmDesc")+":</td> \n");
        sbRet.append("<td class=\"valores\"><textarea name=\"desc_res\" cols=\"38\" rows=\"8\" wrap=\"VIRTUAL\" class=\"campos\">"+strDescription+"</textarea></td> \n");
        sbRet.append("</tr> \n");
        if (iType<4) {
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmClassName")+":</td> \n");
            sbRet.append("<td class=\"valores\"><input name=\"class_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"100\" value=\""+strClass+"\"></td> \n");
            sbRet.append("</tr> \n");
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmBundleFile")+":</td> \n");
            sbRet.append("<td class=\"valores\"><input name=\"bundle_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"100\" value=\""+strBundle+"\"></td> \n");
            sbRet.append("</tr> \n");
        }
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmResType")+":</td> \n");
        sbRet.append("<td class=\"valores\">");
        sbRet.append("<select name=\"txtResType\" class=\"campos\" size=\"1\"> \n");
        if (iType<4) {
            sbRet.append("  <option value=\"1\">"+paramsRequest.getLocaleString("frmComboContent")+"</option> \n");
            sbRet.append("  <option value=\"2\">"+paramsRequest.getLocaleString("frmComboStrategy")+"</option> \n");
            sbRet.append("  <option value=\"3\">"+paramsRequest.getLocaleString("frmComboSystem")+"</option> \n");
            sbRet.append("  <option value=\"4\">"+paramsRequest.getLocaleString("frmComboInner")+"</option> \n");
        }
        else {
            sbRet.append("  <option value=\"5\">"+paramsRequest.getLocaleString("frmPortletContent")+"</option> \n");
            sbRet.append("  <option value=\"6\">"+paramsRequest.getLocaleString("frmPortletStrategy")+"</option> \n");
        }
        sbRet.append("</select>");
        sbRet.append("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
        sbRet.append("document.Resources.txtResType.selectedIndex="+(iType-1)+"; \n");
        sbRet.append("</script> \n");
        sbRet.append("<td>");
        sbRet.append("</tr> \n");
        if (iType<4) {
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmCachedTime")+":</td> \n");
            if (iCache==0) {
                sbRet.append("<td class=\"valores\"><input name=\"time_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"20\" disabled=\"true\" value=\""+iCache+"\" onkeypress=\"return onlyDigits(event);\"></td> \n");
                sbRet.append("</tr> \n");
                sbRet.append("<tr bgcolor=\"\"> \n");
                sbRet.append("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmIsCached")+" </td> \n");
                sbRet.append("<td class=\"valores\"><input name=\"cache_res\" type=\"checkbox\" class=\"campos\" onClick=\"javascript:cache();\"></td> \n");
            }
            else {
                sbRet.append("<td class=\"valores\"><input name=\"time_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"40\" value=\""+iCache+"\" onkeypress=\"return onlyDigits(event);\"></td> \n");
                sbRet.append("</tr> \n");
                sbRet.append("<tr bgcolor=\"\"> \n");
                sbRet.append("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmIsCached")+" </td> \n");
                sbRet.append("<td class=\"valores\"><input name=\"cache_res\" type=\"checkbox\" class=\"campos\" onClick=\"javascript:cache();\" checked></td> \n");
            }
            sbRet.append("</tr> \n");
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=150>"+paramsRequest.getLocaleString("frmExtAtt")+":</td> \n");
            sbRet.append("<td class=\"valores\"><textarea name=\"ext_att_res\" cols=\"38\" rows=\"8\" wrap=\"VIRTUAL\">"+strExtendendAtt+"</textarea></td> \n");
            sbRet.append("</tr> \n");
        }
        sbRet.append("<tr align=\"center\" bgcolor=\"\"> \n");
        sbRet.append("<td colspan=\"2\" align=right><HR size=\"1\" noshade> \n");

        SWBResourceURL urlBack = paramsRequest.getRenderUrl();
        urlBack.setParameter("tm",tm);

        sbRet.append("\n <input type=button  class=\"boton\" name=Back onClick=location='"+urlBack.toString()+"'; value="+paramsRequest.getLocaleString("btnBack")+">");
        sbRet.append("&nbsp;");
        sbRet.append("\n <input type=button  class=\"boton\" name=Edit onClick='javascript:send(\"save\");' value="+paramsRequest.getLocaleString("btnAdd")+">");
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("</table> \n");
        sbRet.append("<input type=hidden name=sort value=\"\"> \n");
        sbRet.append("<input type=hidden name=act value=\"\"> \n");
        if (iResId>0)
            sbRet.append("<input type=hidden name=act2 value=\"edit\"> \n");
        else
            sbRet.append("<input type=hidden name=act2 value=\"add\"> \n");
        sbRet.append("<input type=hidden name=id value=\""+strResId+"\"> \n");
        sbRet.append("<input type=hidden name=tmSel value=\""+tm+"\"> \n");
        if (iType>3) {
            sbRet.append("<input type=hidden name=class_res value=\"com.infotec.wb.portlet.PortletResource\"> \n");
        }
        return sbRet.toString();
    }

    /** Método en donde se procesan las diferentes acciones del recurso
     * @param request Objeto HttpServletRequest
     * @param response Objeto WBActionResponse
     * @throws AFException Excepción del Aplication Framework
     * @throws IOException Excepción de Entrada/Salida
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String act=request.getParameter("act2");
        User user = response.getUser();
        String iResId="0";
        if (!act.equals("add") && request.getParameter("id")!=null && !request.getParameter("id").equals(""))
            iResId=request.getParameter("id");
        String tm=request.getParameter("tmSel");
        /*int iTmnId=0;
        WebSite tpm = TopicMgr.getInstance().getTopicMap(tm);
        if(tpm!=null) {
            iTmnId=tpm.getDbdata().getNId();
        }*/
        ResourceType rec=null;
        //ObjSrv srvObj=new ObjSrv();
        String ObjClass=null,ClassName=null,BundleFile=null,ClassDisName=null,description=null;
        int type=0,cache=0;
        ObjClass = (request.getParameter("class_res")!=null) ? request.getParameter("class_res") : "";
        ClassName = (request.getParameter("nom_res")!=null) ? request.getParameter("nom_res") : "";
        BundleFile = (request.getParameter("bundle_res")!=null) ? request.getParameter("bundle_res") : "";
        ClassDisName = (request.getParameter("nom_dis_res")!=null) ? request.getParameter("nom_dis_res") : "";
        description = (request.getParameter("desc_res")!=null) ? request.getParameter("desc_res") : "";
        type = (request.getParameter("txtResType")!=null) ? Integer.parseInt(request.getParameter("txtResType")):0;
        if (request.getParameter("cache_res")!=null && request.getParameter("cache_res").equals("on"))
            cache = (request.getParameter("time_res")!=null) ? Integer.parseInt(request.getParameter("time_res")) : 0;
        else
            cache = 0;

        StringBuffer strXML=new StringBuffer();
        if (request.getParameter("ext_att_res")==null || (request.getParameter("ext_att_res")!=null && request.getParameter("ext_att_res").equals("")))
            strXML.append("");
        else {
            strXML.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
            strXML.append(request.getParameter("ext_att_res"));
        }
        //Document doc=AFUtils.getInstance().XmltoDom(strXML.toString());
        //System.out.println(AFUtils.getInstance().DomtoXml(doc));
        /*response.setRenderParameter("class_res",ObjClass);
        response.setRenderParameter("nom_res",ClassName);
        response.setRenderParameter("bundle_res",BundleFile);
        response.setRenderParameter("nom_dis_res",ClassDisName);
        response.setRenderParameter("desc_res",description);
        response.setRenderParameter("txtResType",Integer.toString(type));

        if (request.getParameter("cache_res")!=null ) response.setRenderParameter("cache_res",request.getParameter("cache_res"));
        response.setRenderParameter("time_res",Integer.toString(cache)); */
        if (act!=null && act.equals("add")) {
            try {
                //rec=srvObj.createObjResource(ObjClass,ClassName,BundleFile,ClassDisName,description,type,cache,iTmnId,response.getUser().getId());
                //rec=srvObj.createObjResource(tm,ObjClass,ClassName,BundleFile,ClassDisName,description,type,cache,strXML.toString(),response.getUser().getId());
                ResourceType pt = SWBContext.getWebSite(tm).createResourceType(ObjClass);
                pt.setCreator(user);
                pt.setTitle(ClassDisName);
                pt.setDescription(description);
                pt.setResourceMode(type);
                pt.setResourceClassName(ObjClass);
                pt.setResourceCache(cache);
                pt.setResourceBundle(BundleFile);
                
                if(rec!=null){
                    response.setRenderParameter("confirm","added");
                    //response.setRenderParameter("act","view");

                }
                else{
                    response.setRenderParameter("confirm","notadded");
                    //response.setRenderParameter("act","add");
                }
                //response.setRenderParameter("id",Integer.toString(rec.getId()));

            }
            catch(Exception e) {
                //System.out.println("No se agregó la clase");
                response.setRenderParameter("confirm","notadded");
                //response.setRenderParameter("act","add");

            }
        }
        else if (act!=null && act.equals("edit")) {
            try { //if(srvObj.UpdateObjResource(tm,iResId,ObjClass,ClassName,BundleFile,ClassDisName,description,type,cache,strXML.toString(),response.getUser().getId())) {
                
                ResourceType pt = SWBContext.getWebSite(tm).getResourceType(ObjClass);
                pt.setModifiedBy(user);
                pt.setTitle(ClassDisName);
                pt.setDescription(description);
                pt.setResourceMode(type);
                pt.setResourceClassName(ObjClass);
                pt.setResourceCache(cache);
                pt.setResourceBundle(BundleFile);
                
                
                
                response.setRenderParameter("confirm","updated");
                //response.setRenderParameter("id",Integer.toString(iResId));
                //response.setRenderParameter("act","view");

            }
            catch(Exception e) {
                response.setRenderParameter("confirm","notupdated");
                //response.setRenderParameter("id",Integer.toString(iResId));
                //response.setRenderParameter("act","view");
            }
        }
        else if (act!=null && act.equals("remove")) {
            try{ 
                //if (srvObj.RemoveObjResource(tm,iResId,response.getUser().getId())) {
            
                SWBContext.getWebSite(tm).removeResourceType(iResId);
                response.setRenderParameter("confirm","removed");
                //response.setRenderParameter("act","view");
                //response.setRenderParameter("status","true");
                //System.out.println("Render parameter tm:"+tm);
            }
            catch(Exception e) {
                response.setRenderParameter("confirm","notremoved");
                response.setRenderParameter("id",iResId);
                //response.setRenderParameter("act","view");
            }
        }
        else if (act!=null && act.equals("reload")) {
            try { //if (srvObj.reloadResourceClass(tm,iResId,response.getUser().getId())) {
                response.setRenderParameter("confirm","reload");
                //response.setRenderParameter("act","view");
                //response.setRenderParameter("status","true");
                //System.out.println("Render parameter tm:"+tm);
            }
            catch(Exception e) {
                response.setRenderParameter("confirm","notreloaded");
                response.setRenderParameter("id",iResId);
                //response.setRenderParameter("act","view");
            }
        }

        response.setRenderParameter("tm",tm);
        response.setMode(response.Mode_VIEW);
    }
    
}
