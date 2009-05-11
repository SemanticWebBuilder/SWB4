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
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>


<%!
private final int I_PAGE_SIZE = 20;
private final int I_INIT_PAGE = 1;
%>

<%
String action=paramRequest.getAction();
SemanticObject sobj = (SemanticObject) request.getAttribute("sobj");
if (sobj != null) {
    if(action.equals("excel")){
        %>
        <table>
            <tr><td>
                <table>
                    <tr><td>
                        <table>
                            <tr>
                            <th><%=paramRequest.getLocaleString("code")%></th>
                                <th><%=paramRequest.getLocaleString("description")%></th>
                            </tr>
                            <%
                            int actualPage = 1;
                            String strResTypes[] = getCatSortArray(sobj, actualPage, request.getParameter("txtFind"));
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
                                    String[] strFields = strResTypes[i].toString().split(":swbp4g1:");
                                    String title = strFields[0];
                                    String description = strFields[1];
                            %>
                            <tr>
                                <td><%=title%></td>
                                <td>
                                <%if(description!=null && !description.equals("null")){%>
                                    <%=description%>
                                <%}%>
                                </td>
                            </tr>
                            <%
                            }catch(Exception e){
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
    url.setParameter("objUri", sobj.getURI());
%>
<link href="/swb/swbadmin/css/directory.css" rel="stylesheet" type="text/css" />
<table border="0" cellspacing="1" cellpadding="2" width="100%">
    <tr>
        <td align="left" valign="top"  height="25" class="block-title">
            <form action="<%=url.toString()%>">
                <%=paramRequest.getLocaleString("find")%>:<input type="text" name="txtFind"/><button type="submit"><%=paramRequest.getLocaleString("go")%></button>
            </form>
        </td>
        <td nowrap>
            <table width="60%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <%url.setMode("add");%>
                        <a href="<%=url.toString()%>"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/icons/nueva_version.gif" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("addInstance")%>" TITLE="<%=paramRequest.getLocaleString("addInstance")%>"></a>
                        <%url.setCallMethod(url.Call_DIRECT);
                        url.setAction("excel");
                        url.setParameter("txtFind", request.getParameter("txtFind"));
                        url.setMode(url.Mode_VIEW);%>
                        <a href="<%=url.toString()%>"><img width="21" height="21" src="<%=SWBPlatform.getContextPath()%>/swbadmin/icons/Excel-32.gif" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("exportExcel")%>" TITLE="<%=paramRequest.getLocaleString("exportExcel")%>"></a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<table id="K2BTABLEWWPAGECONTENT" class="K2BTableWWPageContent" border="  0" cellpadding="0" cellspacing="0" style="border-width:  0;" > 
    <TBODY> <TR> <TD>
        <table id="K2BTABLEWWGRIDCONTENT" class="K2BTableWWGridContent" border="  0" cellpadding="0" cellspacing="0" style="border-width:  0;" >
            <TBODY> <TR> <TD>
                <table id="GRID1" class="K2BWorkWithGrid" cellpadding="0" cellspacing="1">
                    <tr>
                        <th align="CENTER" width=14px class="K2BWorkWithGridTitle"></th>
                        <th align="CENTER" width=14px class="K2BWorkWithGridTitle"></th>
                        <th align="CENTER" width=14px class="K2BWorkWithGridTitle"></th>
                        <th align="LEFT" width=125px class="K2BWorkWithGridTitle"><%=paramRequest.getLocaleString("code")%></th>
                        <th align="LEFT" nowrap class="K2BWorkWithGridTitle"><%=paramRequest.getLocaleString("description")%></th>
                    </tr>
        <%
            //Empieza paginación
            SWBResourceURL urlPag=paramRequest.getRenderUrl();
            int actualPage = 1;
            if (request.getParameter("actualPage") != null) {
                actualPage = Integer.parseInt(request.getParameter("actualPage"));
            }
            String strResTypes[] = getCatSortArray(sobj, actualPage, request.getParameter("txtFind"));

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

             for (int i = iIniPage; i < iFinPage; i++)
             {
                String[] strFields = strResTypes[i].toString().split(":swbp4g1:");
                String title = strFields[0];
                String description = strFields[1];
                String uri = strFields[2];
                url.setCallMethod(url.Call_CONTENT);
                url.setParameter("objInstUri", uri);
                            %>
                            <tr class="K2BWorkWithGridOdd"><td valign=top align="CENTER">
                                <%url.setMode(url.Mode_VIEW+"2");%>
                                <a href="<%=url.toString()%>"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/icons/gridopen.gif" title="<%=paramRequest.getLocaleString("open")%>" class="K2BWorkWithGridOdd" style=";width: 14"  usemap="''"/></a></td>
                                <td valign=top align="CENTER">
                                <%url.setMode(url.Mode_EDIT);%>
                                <a href="<%=url.toString()%>"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/icons/gridupdate.gif" title="<%=paramRequest.getLocaleString("edit")%>" class="K2BWorkWithGridOdd" style=";width: 14"  usemap="''"/></a></td>
                                <td valign=top align="CENTER">
                                <%url.setMode(url.Mode_VIEW+"2");url.setAction(url.Action_REMOVE);%>
                                <a href="<%=url.toString()%>"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/icons/griddelete.gif" title="<%=paramRequest.getLocaleString("remove")%>" class="K2BWorkWithGridOdd" style=";width: 14"  usemap="''"/></a></td>
                                <td valign=top align="RIGHT" style="display:none;" >
                                <td valign=top align="LEFT">
                                <span id="" class="ReadonlyK2BGridAttribute"><%=title%></span></td>
                                <td valign=top align="LEFT">
                                <span id="span_CUENTACONTABLEDESCRIPCION_0001" class="ReadonlyK2BGridAttribute">
                                    <%if(description!=null && !description.equals("null")){%>
                                    <%=description%>
                                <%}%>
                                </span>
                                </td>
                            </tr>
                            <%
              }
                            %>

                </table>
             </TD>
           </TR>
         </table>
      </TD>
    </TR>
</table>
<%
    }
 }
%>


<%!
private String[] getCatSortArray(SemanticObject sobj, int actualPage, String txtFind) {
        Vector vRO = new Vector();
        SemanticClass swbClass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sobj.getURI());
        Iterator<SemanticObject> itSebObj = swbClass.listInstances();
        while (itSebObj.hasNext()) {
             SemanticObject semObj = itSebObj.next();
             if(txtFind!=null && txtFind.trim().length()>0){
                 String title=semObj.getProperty(Descriptiveable.swb_title).toLowerCase();
                 if(title.startsWith(txtFind.toLowerCase())){
                     vRO.add(semObj);
                 }
             }else{
                 vRO.add(semObj);
             }
        }

        String[] strArray = new String[vRO.size() + 1];

        Iterator itSObjs=vRO.iterator();
        int cont=0;
        while(itSObjs.hasNext()){
            SemanticObject semObj=(SemanticObject)itSObjs.next();
            String value=semObj.getProperty(Descriptiveable.swb_title)+":swbp4g1:"+semObj.getProperty(Descriptiveable.swb_description)+":swbp4g1:"+semObj.getURI();
            strArray[cont]=value;
            cont++;
        }
        strArray[cont] = "zzzzz:zzzz:zzzzz";

        Arrays.sort(strArray, String.CASE_INSENSITIVE_ORDER);
        String pageparams = getPageRange(strArray.length-1, actualPage);
        strArray[cont] = pageparams;
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
