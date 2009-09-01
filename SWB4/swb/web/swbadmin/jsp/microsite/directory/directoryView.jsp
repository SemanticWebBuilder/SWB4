<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.portal.community.*"%>

<%!
private final int I_PAGE_SIZE = 5;
private final int I_INIT_PAGE = 1;
%>

<%
Resource base=paramRequest.getResourceBase();
WebPage wpage=paramRequest.getWebPage();
String action=paramRequest.getAction();
String scope = (String) request.getAttribute("scope");
Iterator<DirectoryObject> itObjs=(Iterator)request.getAttribute("itDirObjs");

SemanticObject sobj = (SemanticObject) request.getAttribute("sobj");
SemanticClass semClass=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sobj.getURI());

ArrayList aprops2display=new ArrayList();
//String props2display=(String)request.getAttribute("props2display");
String props2display="dirPhoto,title,description,tags";
if(props2display!=null && semClass!=null){
    StringTokenizer strTokens=new StringTokenizer(props2display,",");
    while(strTokens.hasMoreTokens()){
        String token=strTokens.nextToken();
        if(token==null) return;
        SemanticProperty semProp=semClass.getProperty(token);
        if(semProp!=null){
            aprops2display.add(semProp);
        }
    }
}

if (sobj != null) {
    if(action.equals("excel")){
        %>
        <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="application/vnd.ms-excel"> 
        <table>
            <tr><td>
                <table>
                    <tr><td>
                        <table>
                            <tr>
                                <%
                                Iterator<SemanticProperty> itProps=aprops2display.iterator();
                                while(itProps.hasNext())
                                {
                                   SemanticProperty semProp=itProps.next();
                                %>
                                    <th><%=semProp.getDisplayName()%></th>
                                <%
                                }
                                %>
                            </tr>
                            <%
                            int actualPage = 1;
                            String strResTypes[] = getCatSortArray(itObjs, sobj, actualPage, request.getParameter("txtFind"), paramRequest, scope);
                            String[] pageParams = strResTypes[strResTypes.length - 1].toString().split(":swbp4g1:");
                            int iIniPage = Integer.parseInt(pageParams[0]);
                            int iFinPage = Integer.parseInt(pageParams[1]);
                            int iTotPage = Integer.parseInt(pageParams[2]);
                            if (iFinPage == strResTypes.length) {
                                iFinPage = iFinPage - 1;
                            }
                            for (int i = iIniPage; i < strResTypes.length-1; i++)
                            {
                                try{
                                    %>
                                       <tr>
                                    <%
                                    String[] strFields = strResTypes[i].toString().split(":swbp4g1:");
                                    String orderField = strFields[0];
                                    String ObjUri = strFields[1];
                                    SemanticObject semObject = SemanticObject.createSemanticObject(ObjUri);
                                    Iterator <SemanticProperty> itObjProps=semObject.getSemanticClass().listProperties();
                                    while(itObjProps.hasNext()){
                                        SemanticProperty semProp=itObjProps.next();
                                        System.out.println("property:"+semProp.getName());
                                        //if(aprops2display.contains(semProp))
                                        if(semProp.isDataTypeProperty())
                                        {
                                            String propValue=semObject.getProperty(semProp);
                                        %>
                                                 <td>
                                                     <%if(propValue!=null && !propValue.equals("null")){%>
                                                    <%=propValue%>
                                                <%}%>
                                                </td>
                                        <%
                                        }
                                     }
                                    %>
                                         </tr>
                                    <%
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                  }
                               %>
                </table>
             </td>
           </tr>
         </table>
      </td>
    </tr>
</table>
<%
}else{
SWBResourceURL url=paramRequest.getRenderUrl();
SWBResourceURL urlExcel=paramRequest.getRenderUrl();
url.setParameter("uri", sobj.getURI());
%>
        <%url.setParameter("act","add");%>
        <form action="<%=url.toString()%>">
            <%=paramRequest.getLocaleString("find")%>:<input type="text" name="txtFind"/><button type="submit"><%=paramRequest.getLocaleString("go")%></button>
            <p align="right">
                <a href="<%=url.toString()%>"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/icons/nueva_version.gif" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("addInstance")%>" TITLE="<%=paramRequest.getLocaleString("addInstance")%>"></a>
                <%
                    urlExcel.setAction("excel");
                    url.setParameter("txtFind", request.getParameter("txtFind"));
                %>
                <a href="<%=urlExcel%>"><img width="21" height="21" src="<%=SWBPlatform.getContextPath()%>/swbadmin/icons/Excel-32.gif" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("exportExcel")%>" TITLE="<%=paramRequest.getLocaleString("exportExcel")%>"></a>
            </p>
        </form>
        
        <%
            //Empieza paginación
            SWBResourceURL urlPag=paramRequest.getRenderUrl();
            int actualPage = 1;
            if (request.getParameter("actualPage") != null) {
                actualPage = Integer.parseInt(request.getParameter("actualPage"));
            }
            String strResTypes[] = getCatSortArray(itObjs, sobj, actualPage, request.getParameter("txtFind"), paramRequest, scope);
            String[] pageParams = strResTypes[strResTypes.length - 1].toString().split(":swbp4g1:");
            
            int iIniPage = Integer.parseInt(pageParams[0]);
            int iFinPage = Integer.parseInt(pageParams[1]);
            int iTotPage = Integer.parseInt(pageParams[2]);
            if (iFinPage == strResTypes.length) {
                iFinPage = iFinPage - 1;
            }
            if (actualPage > 1) {
                 int gotop = (actualPage - 1);
                 urlPag.setParameter("actualPage", ""+gotop);
                 out.println("<a class=\"link\" href=\""+urlPag.toString()+"\"><<</a>&nbsp;");
            }
            if(iTotPage>1){
                for (int i = 1; i <= iTotPage; i++) {
                    if (i == actualPage) {
                        out.println(i);
                    } else {
                        urlPag.setParameter("actualPage", "" + i);
                        out.println("<a href=\"" + urlPag.toString() + "\">" + i + "</a> \n");
                    }
                }
            }
            if (actualPage > 0 && (actualPage + 1 <= iTotPage)) {
                 int gotop = (actualPage + 1);
                 urlPag.setParameter("actualPage", ""+gotop);
                 out.println("<a class=\"link\" href=\""+urlPag.toString()+"\">>></a>&nbsp;");
            }
            //Termina paginación
             SWBResourceURL urlDetail=paramRequest.getRenderUrl();
             SWBResourceURL urlEdit=paramRequest.getRenderUrl();
             SWBResourceURL urlRemove=paramRequest.getRenderUrl();

             urlDetail.setParameter("act", "detail");
             urlEdit.setParameter("act", "edit");
             urlRemove.setParameter("act", "detail");

             for (int i = iIniPage; i < iFinPage; i++)
             {
                String[] strFields = strResTypes[i].toString().split(":swbp4g1:");
                String orderField = strFields[0];
                String ObjUri = strFields[1];
                //url.setCallMethod(url.Call_CONTENT);
                urlDetail.setParameter("uri", ObjUri);
                urlEdit.setParameter("uri", ObjUri);
                urlRemove.setParameter("uri", ObjUri);
                %>
                <div id="catalogo">
                 <div class="entryCatalogo">
                <%
                HashMap map=new HashMap();
                map.put("separator", "-");
                SemanticObject semObject = SemanticObject.createSemanticObject(ObjUri);
                //DirectoryObject dirObj=(DirectoryObject)sobj.createGenericInstance();
                Iterator<SemanticProperty> itProps1=aprops2display.iterator();
                while(itProps1.hasNext())
                {
                   SemanticProperty semProp=itProps1.next();
                   SemanticProperty semProp1=semObject.getSemanticClass().getProperty(semProp.getName());
                   String propValue=semObject.getProperty(semProp1);
                   if(propValue!=null && !propValue.equals("null")){
                        if(semProp.getName().equals("dirPhoto"))
                        {%>
                            <p><img src="<%=SWBPlatform.getWebWorkPath()%><%=base.getWorkPath()%>/<%=semObject.getId()%>/<%=propValue%>"width="90" height="90" ></p>
                        <%}if(semProp.getName().equals("title")) {
                        %>
                          <p class="tituloNaranja"><%=propValue%></p>
                          <p><%=wpage.getPath(map)%></p>
                        <%
                        }else if(semProp.getName().equals("description")){
                        %>
                          <p><%=propValue%></p>
                        <%}else if(semProp.getName().equals("tags")){
                        %>
                            <p>Palabras clave:<%=propValue%></p>
                        <%}%>                       
                    <%}
                }
                %>
                  <div class="vermasFloat"><p class="vermas"><a href="<%=urlDetail%>"><%=paramRequest.getLocaleString("seeMore")%></a></p></div>
                  <div class="editarInfo"><p><a href="<%=urlEdit%>"><%=paramRequest.getLocaleString("editInfo")%></a></p></div>
                  <div class="editarInfo"><p><a href="<%=urlRemove.setAction(urlRemove.Action_REMOVE)%>"><%=paramRequest.getLocaleString("remove")%></a></p></div>
                  <div class="clear">&nbsp;</div>
                 </div>
                </div>
                <%
                }
                %>              
    <%
    }
 }
%>


<%!
private String[] getCatSortArray(Iterator<DirectoryObject> itSebObj, SemanticObject sobj, int actualPage, String txtFind, org.semanticwb.portal.api.SWBParamRequest paramRequest, String scope)
{
    String[] strArray=null;
    try{
        Vector vRO = new Vector();
        //SemanticClass swbClass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sobj.getURI());
        //Iterator<SemanticObject> itSebObj=swbClass.listInstances();
        //if(scope!=null && scope.equals("gl")) itSebObj = swbClass.listInstances();  //Para cuando se pide el objeto de global (todos los modelos)
        //else if(scope!=null && scope.equals("ml")) itSebObj = paramRequest.getWebPage().getSemanticObject().getModel().listInstancesOfClass(swbClass);

        if(itSebObj!=null)
        {
            while (itSebObj.hasNext()) {
                 DirectoryObject dirObj = itSebObj.next();
                 if(txtFind!=null && txtFind.trim().length()>0){
                     String sprop=dirObj.getProperty("title");
                     //revisar esto con George
                     if(sprop==null)sprop=dirObj.getProperty("nombre");
                     if(sprop.toLowerCase().startsWith(txtFind.toLowerCase())){
                         vRO.add(dirObj);
                     }
                 }else{
                     vRO.add(dirObj);
                 }
            }

            strArray = new String[vRO.size() + 1];

            Iterator<DirectoryObject> itSObjs=vRO.iterator();
            int cont=0;
            while(itSObjs.hasNext()){
                DirectoryObject dirObj=(DirectoryObject)itSObjs.next();
                String sPropName=dirObj.getProperty("title");
                //revisar esto con George
                if(sPropName==null)sPropName=dirObj.getProperty("nombre");
                String value=sPropName+":swbp4g1:"+dirObj.getURI();
                strArray[cont]=value;
                cont++;
            }
            strArray[cont] = "zzzzz:zzzz:zzzzz";

            Arrays.sort(strArray, String.CASE_INSENSITIVE_ORDER);
            String pageparams = getPageRange(strArray.length-1, actualPage);
            strArray[cont] = pageparams;
        }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return strArray;
    }

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
        return iIniPage + ":swbp4g1:" + iFinPage + ":swbp4g1:" + iTotPage;
    }
%>
