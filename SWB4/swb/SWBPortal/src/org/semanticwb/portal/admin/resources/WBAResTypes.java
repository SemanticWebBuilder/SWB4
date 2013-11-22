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

import com.sun.org.apache.xpath.internal.XPathAPI;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import java.util.Arrays;
import java.util.HashMap;
import org.semanticwb.SWBPortal;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class WBAResTypes.
 * 
 * @author jorge.jimenez
 */
public class WBAResTypes extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBAResTypes.class);
    
    /** The prop resource types. */
    private Properties propResourceTypes = null;
    
    /** The I_ pag e_ size. */
    private final int I_PAGE_SIZE = 10;
    
    /** The I_ ini t_ page. */
    private final int I_INIT_PAGE = 1;

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#init()
     */
    @Override
    public void init() {
        String path = "/WEB-INF/classes/";
        try {
            InputStream in = new FileInputStream(SWBUtils.getApplicationPath() + path + "resourcetypes.properties");
            propResourceTypes = new Properties();
            propResourceTypes.load(in);
        } catch (Exception e) {
            System.err.println("Can't read the resourcetypes.properties file. " +
                    "Make sure resourcetypes.properties is in the CLASSPATH");
            return;
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
     public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("editForm")) {
            doEditForm(request, response, paramRequest);
        }else{
            super.processRequest(request, response, paramRequest);
        }
     }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        //SemanticObject sem=SemanticObject.createSemanticObject("sref");
        //SemanticModel model=sem.getModel();
        //WebSite website = WebSite.getWebSite();
        String tm="sep";
        String resType="Strategy";

        if(request.getParameter("tm")!=null) tm=request.getParameter("tm");
        if(request.getParameter("resType")!=null) resType=request.getParameter("resType");

        WebSite website = SWBContext.getWebSite(tm);
        
        int actualPage = 1;
        if (request.getParameter("actualPage") != null) {
            actualPage = Integer.parseInt(request.getParameter("actualPage"));
        }
        String strResTypes[] = getCatSortArray(website, actualPage, resType);
        getAddCat(website, strResTypes, resType, request, response, paramsRequest);
    }

    /**
     * Gets the cat sort array.
     * 
     * @param wsite the wsite
     * @param actualPage the actual page
     * @param resType the res type
     * @return the cat sort array
     */
    private String[] getCatSortArray(WebSite wsite, int actualPage, String resType) {
        Vector vRO = new Vector();
        Iterator<ResourceType> en1 = SWBContext.getGlobalWebSite().listResourceTypes();
        while (en1.hasNext()) {
            ResourceType ROT = en1.next();
            vRO.add(ROT);
        }
        en1 = wsite.listResourceTypes();
        while (en1.hasNext()) {
            boolean iE = false;
            ResourceType ROT = en1.next();
            for (int i = 0; i < vRO.size(); i++) {
                ResourceType RO = (ResourceType) vRO.get(i);
                if (RO.getResourceClassName().equals(ROT.getResourceClassName())) {
                    iE = true;
                    break;
                }
            }
            if (!iE) {
                vRO.add(ROT);
            }
        }
        
        HashMap hResTypes=getResourcesTypes(resType);
        ArrayList aTypes=new ArrayList();
        Iterator itResTypes = hResTypes.keySet().iterator();
        int cont = 0;
        while (itResTypes.hasNext()) {
            String strPropName = (String)itResTypes.next();
            String value=(String)hResTypes.get(strPropName);
            boolean isExist = false;
            Enumeration en = vRO.elements();
            while (en.hasMoreElements()) {
                ResourceType RO = (ResourceType) en.nextElement();
                if (value.equalsIgnoreCase(RO.getResourceClassName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                try {
                    aTypes.add(strPropName + ":" + value);
                } catch (Exception e) {
                    log.debug(e.toString(), e);
                }
                cont++;
            }
        }
        String[] strArray = new String[aTypes.size() + 1];

        Iterator ittypes=aTypes.iterator();
        cont=0;
        while(ittypes.hasNext()){
            String value=(String)ittypes.next();
            strArray[cont]=value;
            cont++;
        }
        strArray[cont] = "zzzzz:zzzz";

        Arrays.sort(strArray, String.CASE_INSENSITIVE_ORDER);
        String pageparams = getPageRange(strArray.length-1, actualPage);
        strArray[cont] = pageparams;
        return strArray;
    }

    /**
     * Gets the resources types.
     * 
     * @param resType the res type
     * @return the resources types
     */
    private HashMap getResourcesTypes(String resType){
        HashMap hResTypes=new HashMap();
        
        Iterator itKeys=propResourceTypes.keySet().iterator();
        while(itKeys.hasNext()){
            String strKey=(String)itKeys.next();
            String [] value=propResourceTypes.getProperty(strKey).split(",");
            if(value[1].equalsIgnoreCase(resType)){
                hResTypes.put(strKey, value[0]);
            }
        }        
        return hResTypes;
    }
    

    /**
     * Gets the adds the cat.
     * 
     * @param wsite the wsite
     * @param strResTypes the str res types
     * @param resType the res type
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @return the adds the cat
     * @throws SWBResourceException the sWB resource exception
     */
    private String getAddCat(WebSite wsite, String[] strResTypes, String resType, HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException {
        try {
            PrintWriter out = response.getWriter();
            int actualPage = 1;
            if (request.getParameter("actualPage") != null) {
                actualPage = Integer.parseInt(request.getParameter("actualPage"));
            }
            String[] pageParams = strResTypes[strResTypes.length - 1].toString().split(":");
            int iIniPage = Integer.parseInt(pageParams[0]);
            int iFinPage = Integer.parseInt(pageParams[1]);
            int iTotPage = Integer.parseInt(pageParams[2]);
            if (iFinPage == strResTypes.length) {
                iFinPage = iFinPage - 1;
            }

            SWBResourceURL url = paramsRequest.getRenderUrl();
            url.setMode("editForm");
            out.println("<form name=\"Resources\" method=\"post\" action=\""+url.toString()+"\"> \n");
            out.println("<table> \n");
            out.println("<tr><td colspan=\"3\" align=\"center\"> \n");

            url.setMode(url.Mode_VIEW);

            if (actualPage > 1) {
                 int gotop = (actualPage - 1);
                 url.setParameter("actualPage", ""+gotop);
                 out.println("<a class=\"link\" href=\""+url.toString()+"\"><<</a>&nbsp;");
            }

            if(iTotPage>1){
                for (int i = 1; i <= iTotPage; i++) {
                    if (i == actualPage) {
                        out.println(i);
                    } else {
                        url.setParameter("actualPage", "" + i);
                        out.println("<a href=\"" + url.toString() + "\">" + i + "</a> \n");
                    }
                }
            }

            if (actualPage > 0 && (actualPage + 1 <= iTotPage)) {
                 int gotop = (actualPage + 1);
                 url.setParameter("actualPage", ""+gotop);
                 out.println("<a class=\"link\" href=\""+url.toString()+"\">>></a>&nbsp;");
            }

            out.println("</td></tr> \n");

             String rowColor = "";
            boolean cambiaColor = true;
            for (int i = iIniPage; i < iFinPage; i++) {
                rowColor = "#EFEDEC";
                if (!cambiaColor) {
                    rowColor = "#FFFFFF";
                }
                cambiaColor = !(cambiaColor);
                String[] strFields = strResTypes[i].toString().split(":");
                String strName = strFields[0];
                String strClass = strFields[1];
                out.println("<tr class=\"valores\" bgcolor=\"" + rowColor + "\"> \n");
                out.println("<td> \n");
                out.println("<input type=\"radio\" name=\"id\" value=\"" + strClass + "\" title=\"" + strName + "\"> \n");
                out.println("</td> \n");
                out.println("<td> \n");
                out.println(strName);
                out.println("</td> \n");
                out.println("<td> \n");
                out.println(strClass);
                out.println("</td> \n");
                out.println("</tr> \n");
            }
            out.println("<tr><td colspan=\"3\" align=\"right\"> <HR size=\"1\" noshade> \n");
            out.println("\n <input type=\"button\"  class=\"boton\" name=Open onClick='javascript:send()' value=" + paramsRequest.getLocaleString("btnAdd") + ">");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("<input type=\"hidden\" name=\"tm\" value=\"" + wsite.getId() + "\"> \n");
            out.println("<input type=\"hidden\" name=\"resType\" value=\"" + resType + "\"> \n");
            out.println("</form>");

            out.println("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
            out.println("function send() { \n");
            out.println("      var selected=radioselected(); \n");
            out.println("      if (!selected) \n");
            out.println("         alert ('" + paramsRequest.getLocaleString("jsChooseRes") + "');  \n");
            out.println("      else{ \n");
            out.println("       document.Resources.submit(); \n");
            out.println("       }" );
            out.println("    }" );
            out.println("function radioselected() { \n");
            out.println("  if(document.Resources.id.checked==true) { \n");
            out.println("      return true;  \n");
            out.println("  } \n");
            out.println("  if (document.Resources.id.value!=\"\") { \n");
            out.println("      for(i=0;i<document.Resources.id.length;i++) { \n");
            out.println("          if(document.Resources.id[i].checked==true) { \n");
            out.println("              strResId=document.Resources.id[i].value;  \n");
            out.println("              return true;  \n");
            out.println("          } \n");
            out.println("      } \n");
            out.println("  } \n");
            out.println("} \n");
            out.println("</script>");
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e);
        }
        return "";
    }

    /**
     * Gets the page range.
     * 
     * @param iSize the i size
     * @param iPageNum the i page num
     * @return the page range
     */
    private String getPageRange(int iSize, int iPageNum) {
        int iTotPage = 0;
        int iPage = I_INIT_PAGE;
        if (iPageNum > 1) {
            iPage = iPageNum;
        }
        if (iSize > I_PAGE_SIZE) {
            iTotPage = iSize / I_PAGE_SIZE;
            int i_ret = iSize % I_PAGE_SIZE;
            if (i_ret > 0) {
                iTotPage = iTotPage + 1;
            }
        } else {
            iTotPage = 1;
        }
        int iIniPage = (I_PAGE_SIZE * iPage) - I_PAGE_SIZE;
        int iFinPage = I_PAGE_SIZE * iPage;
        if (iSize < I_PAGE_SIZE * iPage) {
            iFinPage = iSize;
        }
        return iIniPage + ":" + iFinPage + ":" + iTotPage;
    }

    /**
     * Do edit form.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void doEditForm(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        String strResId=request.getParameter("id");
        String tm=request.getParameter("tm");
        StringBuffer sbRet = new StringBuffer();
        String strTitle = "";
        String strDisplayName = "";
        String strDescription = "";
        String strClass = "";
        String strBundle = "";
        String strExtendendAtt = "";
        int iType = 0;
        int iCache = 0;
        String strTitleLabel = paramsRequest.getLocaleString("frmTitle");
        if (paramsRequest.getAction().equals("resource")) {
            strTitleLabel = paramsRequest.getLocaleString("frmResourceGUI");
        }

        try {
            String strClassPath = "/" + strResId.replace('.', '/') + "Conf.xml";
            InputStream fis = getClass().getResourceAsStream(strClassPath);

            String xml = SWBUtils.IO.readInputStream(fis);
            Document doc = SWBUtils.XML.xmlToDom(xml);
            String xpath = "/resource";
            NodeList nlTools = XPathAPI.selectNodeList(doc, xpath);
            for (int i = 0; i < nlTools.getLength(); i++) {
                Node nTool = (Node) nlTools.item(i);
                strTitle = XPathAPI.eval(nTool, "resource-name/text()").toString();
                strDisplayName = XPathAPI.eval(nTool, "display-name[@xml:lang='" + paramsRequest.getWebPage().getWebSite().getLanguage().getId() + "']/text()").toString(); //REVISAR SI NO MANDA UN INDEX-OUT OF BOUND
                strDescription = XPathAPI.eval(nTool, "description[@xml:lang='" + paramsRequest.getWebPage().getWebSite().getLanguage().getId() + "']/text()").toString();
                strClass = XPathAPI.eval(nTool, "resource-class/text()").toString();
                strBundle = XPathAPI.eval(nTool, "resource-bundle/text()").toString();
                iType = Integer.parseInt(XPathAPI.eval(nTool, "resource-type/text()").toString());
                iCache = Integer.parseInt(XPathAPI.eval(nTool, "expiration-cache/text()").toString());
                strExtendendAtt = XPathAPI.eval(nTool, "extended-attributes/text()").toString();
            }
            if (strExtendendAtt != null && strExtendendAtt.trim().length() > 0) {
                String strXML = SWBUtils.XML.domToXml(doc);
                strExtendendAtt = strXML.substring(strXML.indexOf("<extended-attributes>"), strXML.lastIndexOf("</extended-attributes>") + 22).trim();
            } else {
                strExtendendAtt = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("Error in WBAResourceTypes.getEditForm();", e);
        }
        
        sbRet.append("<form name=\"Resources\" method=\"post\" action=\""+paramsRequest.getActionUrl().toString()+"\"> \n");
        sbRet.append("<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"\" width=100%> \n");
        sbRet.append("<tr align=\"left\" bgcolor=\"\"> \n");
        sbRet.append("<td colspan=\"2\"><span class=\"tabla\">" + paramsRequest.getLocaleString("frmResTitle") + "</span>");
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=150>" + strTitleLabel + ":</td> \n");
        sbRet.append("<td class=\"valores\"><input name=\"nom_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"40\" value=\"" + strTitle + "\"></td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=150>" + paramsRequest.getLocaleString("frmDisplayName") + ":</td> \n");
        sbRet.append("<td class=\"valores\"><input name=\"nom_dis_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"40\" value=\"" + strDisplayName + "\"></td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=150>" + paramsRequest.getLocaleString("frmDesc") + ":</td> \n");
        sbRet.append("<td class=\"valores\"><textarea name=\"desc_res\" cols=\"38\" rows=\"8\" wrap=\"VIRTUAL\" class=\"campos\">" + strDescription + "</textarea></td> \n");
        sbRet.append("</tr> \n");
        if (iType < 4) {
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=150>" + paramsRequest.getLocaleString("frmClassName") + ":</td> \n");
            sbRet.append("<td class=\"valores\"><input name=\"class_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"100\" value=\"" + strClass + "\"></td> \n");
            sbRet.append("</tr> \n");
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=150>" + paramsRequest.getLocaleString("frmBundleFile") + ":</td> \n");
            sbRet.append("<td class=\"valores\"><input name=\"bundle_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"100\" value=\"" + strBundle + "\"></td> \n");
            sbRet.append("</tr> \n");
        }
        sbRet.append("<tr bgcolor=\"\"> \n");
        sbRet.append("<td align=\"right\" class=\"datos\" width=150>" + paramsRequest.getLocaleString("frmResType") + ":</td> \n");
        sbRet.append("<td class=\"valores\">");
        sbRet.append("<select name=\"txtResType\" class=\"campos\" size=\"1\"> \n");
        if (iType < 4) {
            sbRet.append("  <option value=\"1\">" + paramsRequest.getLocaleString("frmComboContent") + "</option> \n");
            sbRet.append("  <option value=\"2\">" + paramsRequest.getLocaleString("frmComboStrategy") + "</option> \n");
            sbRet.append("  <option value=\"3\">" + paramsRequest.getLocaleString("frmComboSystem") + "</option> \n");
            sbRet.append("  <option value=\"4\">" + paramsRequest.getLocaleString("frmComboInner") + "</option> \n");
        } else {
            sbRet.append("  <option value=\"5\">" + paramsRequest.getLocaleString("frmResourceContent") + "</option> \n");
            sbRet.append("  <option value=\"6\">" + paramsRequest.getLocaleString("frmResourceStrategy") + "</option> \n");
        }
        sbRet.append("</select>");
        sbRet.append("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
        sbRet.append("document.Resources.txtResType.selectedIndex=" + (iType - 1) + "; \n");
        sbRet.append("</script> \n");
        sbRet.append("<td>");
        sbRet.append("</tr> \n");
        if (iType < 4) {
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=150>" + paramsRequest.getLocaleString("frmCachedTime") + ":</td> \n");
            if (iCache == 0) {
                sbRet.append("<td class=\"valores\"><input name=\"time_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"20\" disabled=\"true\" value=\"" + iCache + "\" onkeypress=\"return onlyDigits(event);\"></td> \n");
                sbRet.append("</tr> \n");
                sbRet.append("<tr bgcolor=\"\"> \n");
                sbRet.append("<td align=\"right\" class=\"datos\" width=150>" + paramsRequest.getLocaleString("frmIsCached") + " </td> \n");
                sbRet.append("<td class=\"valores\"><input name=\"cache_res\" type=\"checkbox\" class=\"campos\" onClick=\"javascript:cache();\"></td> \n");
            } else {
                sbRet.append("<td class=\"valores\"><input name=\"time_res\" type=\"text\" class=\"campos\" size=\"50\" maxlength=\"40\" value=\"" + iCache + "\" onkeypress=\"return onlyDigits(event);\"></td> \n");
                sbRet.append("</tr> \n");
                sbRet.append("<tr bgcolor=\"\"> \n");
                sbRet.append("<td align=\"right\" class=\"datos\" width=150>" + paramsRequest.getLocaleString("frmIsCached") + " </td> \n");
                sbRet.append("<td class=\"valores\"><input name=\"cache_res\" type=\"checkbox\" class=\"campos\" onClick=\"javascript:cache();\" checked></td> \n");
            }
            sbRet.append("</tr> \n");
            sbRet.append("<tr bgcolor=\"\"> \n");
            sbRet.append("<td align=\"right\" class=\"datos\" width=150>" + paramsRequest.getLocaleString("frmExtAtt") + ":</td> \n");
            sbRet.append("<td class=\"valores\"><textarea name=\"ext_att_res\" cols=\"38\" rows=\"8\" wrap=\"VIRTUAL\">" + strExtendendAtt + "</textarea></td> \n");
            sbRet.append("</tr> \n");
        }
        sbRet.append("<tr align=\"center\" bgcolor=\"\"> \n");
        sbRet.append("<td colspan=\"2\" align=right><HR size=\"1\" noshade> \n");

        SWBResourceURL url=paramsRequest.getRenderUrl();
        url.setMode(url.Mode_VIEW);
        sbRet.append("\n <input type=button  class=\"boton\" name=Back onClick=location='" + url.toString() + "'; value=" + paramsRequest.getLocaleString("btnBack") + ">");
        sbRet.append("&nbsp;");
        sbRet.append("\n <input type=button  class=\"boton\" name=Edit onClick='javascript:if(validateForm()) this.form.submit();' value=" + paramsRequest.getLocaleString("btnAdd") + ">");
        sbRet.append("</td> \n");
        sbRet.append("</tr> \n");
        sbRet.append("</table> \n");
        
        sbRet.append("<input type=hidden name=tmSel value=\"" + tm + "\"> \n");
        sbRet.append("</form>");

        sbRet.append("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
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
        sbRet.append("\n                            alert ('" + paramsRequest.getLocaleString("jsEmptyField") + "');");
        sbRet.append("\n                            _f.elements[i].focus();");
        sbRet.append("\n                            return false;");
        sbRet.append("\n                        }");
        sbRet.append("\n                    }");
        sbRet.append("\n                }");
        sbRet.append("\n                }");
        sbRet.append("\n            }");
        sbRet.append("\n        }");
        sbRet.append("\n    }");
        sbRet.append("\n    return true;");
        sbRet.append("\n }");
        sbRet.append("</script> \n");

        response.getWriter().println(sbRet.toString());
        
    }


    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebSite website=SWBContext.getWebSite(request.getParameter("tmSel"));
        ResourceType rec = null;
        String ObjClass = null, ClassName = null, BundleFile = null, ClassDisName = null, description = null;
        int type = 0, cache = 0;
        ObjClass = (request.getParameter("class_res") != null) ? request.getParameter("class_res") : "";
        ClassName = (request.getParameter("nom_res") != null) ? request.getParameter("nom_res") : "";
        BundleFile = (request.getParameter("bundle_res") != null) ? request.getParameter("bundle_res") : "";
        ClassDisName = (request.getParameter("nom_dis_res") != null) ? request.getParameter("nom_dis_res") : "";
        description = (request.getParameter("desc_res") != null) ? request.getParameter("desc_res") : "";
        type = (request.getParameter("txtResType") != null) ? Integer.parseInt(request.getParameter("txtResType")) : 0;
        if (request.getParameter("cache_res") != null && request.getParameter("cache_res").equals("on")) {
            cache = (request.getParameter("time_res") != null) ? Integer.parseInt(request.getParameter("time_res")) : 0;
        } else {
            cache = 0;
        }

        StringBuffer strXML = new StringBuffer();
        if (request.getParameter("ext_att_res") == null || (request.getParameter("ext_att_res") != null && request.getParameter("ext_att_res").equals(""))) {
            strXML.append("");
        } else {
            strXML.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
            strXML.append(request.getParameter("ext_att_res"));
        }
        try {
                rec=createResourceType(website, ObjClass, ClassName, BundleFile, ClassDisName, description, type, cache, strXML.toString(), response.getUser().getId());
                if (rec != null) {
                    response.setRenderParameter("confirm", "added");
                } else {
                    response.setRenderParameter("confirm", "notadded");
                }
            } catch (Exception e) {
                response.setRenderParameter("confirm", "notadded");
            }
        response.setMode(response.Mode_VIEW);
        response.setRenderParameter("tm", website.getId());
        response.setRenderParameter("resType", request.getParameter("resType"));
    }

    /**
     * Creates the resource type.
     * 
     * @param website the website
     * @param objClass the obj class
     * @param classname the classname
     * @param bundle the bundle
     * @param classDisName the class dis name
     * @param description the description
     * @param mode the mode
     * @param cache the cache
     * @param xml the xml
     * @param userid the userid
     * @return the resource type
     */
    private ResourceType createResourceType(WebSite website,String objClass,String classname, String bundle, String classDisName, String description,int mode,int cache,String xml,String userid) {
        try{
            ResourceType ptype = website.createResourceType(classname);
            if(objClass!=null){
                ptype.setResourceClassName(objClass);
            }
            if(bundle!=null){
                ptype.setResourceBundle(bundle);
            }
            if(mode>0){
                ptype.setResourceMode(mode);
            }
            if(classDisName!=null){
                ptype.setTitle(classDisName);
            }
            if(description!=null){
                ptype.setDescription(description);
            }
            if(cache>0){
                ptype.setResourceCache(cache);
            }
            if(mode!=4){
                String clsname=ptype.getResourceClassName();
                SWBPortal.getResourceMgr().createSWBResourceClass(clsname, true);

                 //TODO:VER COMO QUEDARIA ESTO EN V4
//               WBResource obj = (WBResource) ResourceMgr.getInstance().convertOldWBResource(cls.newInstance());
//
//                if (obj != null)
//                {
//                   /* recObj.setDescription(description);
//                    recObj.setTopicMapId(tmnid);*/
//                    recObj.create(userid, com.infotec.appfw.util.AFUtils.getLocaleString("locale_Gateway", "admlog_Gateway_getService_ResourceCreated") + ":" + recObj.getId() + " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_Gateway", "admlog_Gateway_getService_andClass") + recObj.getObjclass());
//                    try
//                    {
//                        obj.install(recObj);
//                    } catch (Exception e)
//                    {
//                        recObj.remove();
//                        AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_Gateway", "error_Gateway_getService_ChangeResourcePriorityError"), true);
//                    }
//                    return recObj;
//                }
            }
            return ptype;
        }catch(Exception e){
            log.debug(e);
        }
        return null;
    }

}
