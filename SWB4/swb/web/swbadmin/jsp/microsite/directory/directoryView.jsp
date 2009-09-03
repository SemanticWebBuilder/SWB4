<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Date"%>
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
User user=paramRequest.getUser();
WebPage wpage=paramRequest.getWebPage();
String perfilPath=wpage.getWebSite().getWebPage("perfil").getUrl();
String action=paramRequest.getAction();
Iterator<DirectoryObject> itObjs=(Iterator)request.getAttribute("itDirObjs");

SemanticObject sobj = (SemanticObject) request.getAttribute("sobj");
if (sobj != null) {    
SWBResourceURL url=paramRequest.getRenderUrl();
SWBResourceURL urlExcel=paramRequest.getRenderUrl();
url.setParameter("uri", sobj.getURI());
%>
   
       <%url.setParameter("act","add");%>
       <div class="editarInfo">
            <p align="right"><a href="<%=url.toString()%>">Agregar elemento</a></p>
       </div><div class="clear">&nbsp;</div>
 <div id="entriesList">
        <%
        //Empieza paginación
        SWBResourceURL urlPag=paramRequest.getRenderUrl();
        int actualPage = 1;
        if (request.getParameter("actualPage") != null) {
            actualPage = Integer.parseInt(request.getParameter("actualPage"));
        }
        String strResTypes[] = getCatSortArray(itObjs, actualPage, request.getParameter("txtFind"));
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

         HashMap map=new HashMap();
         map.put("separator", "-");
         String img="", title="", description="", tags="", creator="", created="";
         for (int i = iIniPage; i < iFinPage; i++)
         {
            String[] strFields = strResTypes[i].toString().split(":swbp4g1:");
            String orderField = strFields[0];
            String ObjUri = strFields[1];
            urlDetail.setParameter("uri", ObjUri);
            urlEdit.setParameter("uri", ObjUri);
            urlRemove.setParameter("uri", ObjUri);
            %>
            <div class="listEntry" onmouseover="this.className='listEntryHover'" onmouseout="this.className='listEntry'">
            <%
            SemanticObject semObject = SemanticObject.createSemanticObject(ObjUri);
            Iterator<SemanticProperty> ipsemProps=semObject.listProperties();
            while(ipsemProps.hasNext())
            {
               SemanticProperty semProp=ipsemProps.next();
               if(semProp.isDataTypeProperty()){
                   String propValue=semObject.getProperty(semProp);
                   if(propValue!=null && !propValue.equals("null")){
                        if(semProp==DirectoryObject.swbcomm_dirPhoto)
                        {
                            img="<img src=\""+SWBPlatform.getWebWorkPath()+base.getWorkPath()+"/"+semObject.getId()+"/"+propValue+ "\" width=\"90\" height=\"90\">";
                        }if(semProp==DirectoryObject.swb_title) {
                             title=propValue;
                        }else if(semProp==DirectoryObject.swb_description){
                             description=propValue;
                        }else if(semProp==DirectoryObject.swb_tags){
                             tags=propValue;
                        }else if(semProp==DirectoryObject.swb_created){
                            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
                            Date date=new Date();
                            date=formatoDelTexto.parse(propValue);
                            propValue= SWBUtils.TEXT.getTimeAgo(date, user.getLanguage());
                            created=propValue;
                        }
                    }
                }else if(semProp==DirectoryObject.swb_creator){
                        SemanticObject semUser=semObject.getObjectProperty(DirectoryObject.swb_creator);
                        if(semUser!=null){
                            User userObj=(User)semUser.createGenericInstance();
                            creator="<a href=\""+perfilPath+"?user="+userObj.getEncodedURI()+"\">"+userObj.getFullName()+"</a>";
                         }
                }
            }
            %>
                <%=img%>
              <div class="listEntryInfo">
                    <p class="tituloNaranja">
                        <%=title%>
                    </p>
                        <%=wpage.getPath(map)%>
                    <p>
                        <%=description%>
                    </p><br/>
                    <p>-Palabras clave:<%=tags%></p>
                    <p>-Creado por:<%=creator%></p>
                    <p>-creado:<%=created%></p>

                    <div class="vermasFloat"><p class="tituloNaranja"><p class="vermas"><a href="<%=urlDetail%>"><%=paramRequest.getLocaleString("seeMore")%></a></p></div>
                    <div class="editarInfo"><p><a href="<%=urlEdit%>"><%=paramRequest.getLocaleString("editInfo")%></a></p></div>
                    <div class="editarInfo"><p><a href="<%=urlRemove.setAction(urlRemove.Action_REMOVE)%>"><%=paramRequest.getLocaleString("remove")%></a></p></div>
                    <div class="clear">&nbsp;</div>
              </div>
             </div>
            <%
            }
            %>
      </div>
<%
 }
%>

<%!
private String[] getCatSortArray(Iterator<DirectoryObject> itSebObj, int actualPage, String txtFind)
{
    String[] strArray=null;
    try{
        Vector vRO = new Vector();
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
