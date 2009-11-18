<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>

<%!
private final int I_PAGE_SIZE = 20;
%>

<%
Resource base = paramRequest.getResourceBase();
User user = paramRequest.getUser();
WebPage wpage = paramRequest.getWebPage();
String perfilPath = wpage.getWebSite().getWebPage("perfil").getUrl();
Iterator<DirectoryObject> itObjs = (Iterator) request.getAttribute("itDirObjs");
SemanticObject sobj = (SemanticObject) request.getAttribute("sobj");
SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sobj.getURI());
SWBResourceURL url = paramRequest.getRenderUrl();
boolean toggleOrder = true;

if (sobj != null) {
    url.setParameter("uri", sobj.getURI());
    url.setParameter("act","add");
%>
    <div class="twoColContent">
      <div class="adminTools">
          <%if(user.isRegistered() && user.isSigned()){%>
            <a class="adminTool" href="<%=url%>">Agregar elemento al indice</a>
          <%}else{%>
            <span class="adminTool">Registrese para publicar</span>
          <%}%>
          <span class="adminTool "id="toggle_link" href="#" onclick="toggle('togle_div')">Mostrar Filtros</span>
      </div>
      <div id="entriesList">
        <%
        //Empieza paginación
        SWBResourceURL urlPag=paramRequest.getRenderUrl();
        int actualPage = 1;
        if (request.getParameter("actualPage") != null) {
            actualPage = Integer.parseInt(request.getParameter("actualPage"));
        }

        //Leer parametros que se envían para filtrado
        String sparams = "";
        HashMap hdirParams = new HashMap();
        Map mParams = request.getParameterMap();
        Iterator itParams = mParams.keySet().iterator();
        while(itParams.hasNext()) {
            String pname = (String)itParams.next();
            sparams += "&" + pname + "=" + request.getParameter(pname);
            if(pname.startsWith("swbdirParam_")) {
                String param = pname.substring(12);
                if(request.getParameter(param) != null){
                     hdirParams.put(param, request.getParameter(param));
                }
            }
        }

        //System.out.println("PARAMS SIZE:"+hdirParams.size());
         //Termina leida de parametros para filtrar
        ArrayList<DirectoryObject> alFilterTmp = new ArrayList();
        if(hdirParams.size() > 0) {//Se desea filtrar información en los resultados
            while(itObjs.hasNext())
            {
                ArrayList alFilter=new ArrayList();
                DirectoryObject dirObj=(DirectoryObject)itObjs.next();
                SemanticObject semObject = dirObj.getSemanticObject();
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_VIEW);
                Iterator<SemanticProperty> ipsemProps=semObject.listProperties();
                while(ipsemProps.hasNext())
                {
                   SemanticProperty semProp=ipsemProps.next();
                   if(semProp.isDataTypeProperty()){
                       String propValue=semObject.getProperty(semProp);
                       if(propValue!=null && !propValue.equals("null")){
                            if(hdirParams.containsKey(semProp.getName())) //Filtrado
                            {
                                if(semProp.getName().equals("dirPhoto")) //La foto x ser de tipo text,no se puede controlar dinamicamente
                                {
                                    alFilter.add(semProp.getName());
                                }else if(semProp.isBoolean() && propValue.equals("true")){
                                     //System.out.println("***Boolean-->semProp:"+semProp+",valor:"+propValue);
                                     alFilter.add(semProp.getName());
                                }else {
                                     FormElement element=mgr.getFormElement(semProp);
                                     if(element!=null && element.getId()!=null){
                                        if(element.getId().indexOf("selectOne")>-1){
                                            if(request.getParameter(semProp.getName())!=null && request.getParameter(semProp.getName()).equals(propValue)){
                                                //System.out.println("***selectOne-->semProp:"+semProp+",valor:"+propValue);
                                                alFilter.add(semProp.getName());
                                            }
                                        }
                                     }
                                }
                            }
                        }
                    }
                }
                if(alFilter.size()==hdirParams.size()) //Si el elemento cumple con todos los filtros, se agrega a los elementos a listar
                {
                    alFilterTmp.add(dirObj);
                }
             }
         }else sparams="";
         if(alFilterTmp.size()>0){ //Si se encontraron elementos para listar en el resultado de la busqueda por filtro, seran los tomados al final
             itObjs=alFilterTmp.iterator();
         }
         //Comienza ordenamiento de elementos, por defecto se ordena por título del elemento del directorio
        Set setResult=null;
        SemanticProperty semPropOrder=DirectoryObject.swb_title;
        if(request.getParameter("orderBy")!=null && request.getParameter("orderBy").equals("date"))
        {
            urlPag.setParameter("orderBy", "date");
            setResult=SWBComparator.sortByCreatedSet(itObjs, false);
        } else if(request.getParameter("orderBy")!=null && request.getParameter("orderBy").equals("pricel")) {
            setResult = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                        ClasifiedBuySell ob1 = (ClasifiedBuySell)o1;
                        ClasifiedBuySell ob2 = (ClasifiedBuySell)o2;
                        return Float.compare(ob1.getPrice(), ob2.getPrice());
                    }
                });
            toggleOrder = false;
            while (itObjs.hasNext()) {
                DirectoryObject o = itObjs.next();
                setResult.add(o);
            }
        } else if(request.getParameter("orderBy")!=null && request.getParameter("orderBy").equals("priceh")) {
            setResult = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                        ClasifiedBuySell ob1 = (ClasifiedBuySell)o1;
                        ClasifiedBuySell ob2 = (ClasifiedBuySell)o2;
                        return Float.compare(ob2.getPrice(), ob1.getPrice());
                    }
                });
            toggleOrder = true;
            while (itObjs.hasNext()) {
                DirectoryObject o = itObjs.next();
                setResult.add(o);
            }
        } else {
            setResult=SWBComparator.sortByDisplayNameSet(itObjs, user.getLanguage());
        }
        //Ya sabiendo cuantos y cuales son los elementos a listar (ya que puede ser una busqueda filtrada), se página
        String pagination = getPageRange(setResult.size(), actualPage);
        int iIniPage = 0;
        int iFinPage = 0;
        int iTotPage = 0;
        int contTokens=0;
        StringTokenizer strTokens=new StringTokenizer(pagination,"|");
        while(strTokens.hasMoreTokens()){
            String token=strTokens.nextToken();
            contTokens++;
            if(contTokens==1) iIniPage=Integer.parseInt(token);
            else if(contTokens==2) iFinPage=Integer.parseInt(token);
            else if(contTokens==3) iTotPage=Integer.parseInt(token);
        }

        %>
        <p align="center">
        <%
        if(iTotPage>1)%>Página(<%
        if (actualPage > 1) {
             int gotop = (actualPage - 1);
             urlPag.setParameter("actualPage", ""+gotop);

             %>
                <a class="link" href="<%=urlPag%><%=sparams%>"><<</a>&nbsp;
             <%
        }
        if(iTotPage>1){
            for (int i = 1; i <= iTotPage; i++) {
                if (i == actualPage) {
                    %><strong><%=i%></strong><%;
                } else {
                    urlPag.setParameter("actualPage", "" + i);
                    %>
                        <a href="<%=urlPag%><%=sparams%>"><%=i%></a>
                    <%
                }
            }
        }
        if (actualPage > 0 && (actualPage + 1 <= iTotPage)) {
             int gotop = (actualPage + 1);
             urlPag.setParameter("actualPage", ""+gotop);
             %>
                <a class="link" href="<%=urlPag%><%=sparams%>">>></a>&nbsp;
             <%
        }
        if(iTotPage>1)%>)
        </p>
        <%
        //Termina paginación
        //Comienza criterios de busqueda y ordenamiento (x los elementos que el usuario puede filtrar en sus busquedas, dependiendo del tipo de objeto)
        SWBResourceURL urlOrder = paramRequest.getRenderUrl();
        String dirPhotoCheck="";
        if(request.getParameter("dirPhoto")!=null) dirPhotoCheck="checked";
        %>
        <script type="text/javascript">
            dojo.require("dojo.fx");
            dojo.require("dijit.ColorPalette");
            dojo.require("dijit.form.Button");

            function expande(oId) {
                    var anim1 = dojo.fx.wipeIn( {node:oId, duration:500 });
                    var anim2 = dojo.fadeIn({node:oId, duration:500});
                    dojo.fx.combine([anim1,anim2]).play();
                    dojo.byId('toggle_link').innerHTML = "Ocultar Filtros";
            }

            function collapse(oId) {
                    var anim1 = dojo.fx.wipeOut( {node:oId, duration:500 });
                    var anim2 = dojo.fadeOut({node:oId, duration:600});
                    dojo.fx.combine([anim1, anim2]).play();
                    dojo.byId('toggle_link').innerHTML = "Mostrar Filtros";
            }

            function toggle(oId) {
                    var o = dojo.byId(oId);
                    if(o.style.display=='block' || o.style.display==''){
                            collapse(oId);
                    } else {
                            expande(oId);
                }
            }
        </script>
        <div id="togle_div" style="display:none">
        <form action="<%=urlOrder.setAction("filter")%>" method="post">
            <fieldset>
        <table align="center">
        <tr><td>
        Solo anuncios con foto  </td><td><input type="checkbox" name="dirPhoto" <%=dirPhotoCheck%>></td>
        <input type="hidden" name="swbdirParam_dirPhoto" value="1">
        </tr>
        <%
        SWBFormMgr mgr = new SWBFormMgr(cls, wpage.getSemanticObject(), null);
        mgr.setFilterRequired(false);
        Iterator<SemanticProperty> itProps=cls.listProperties();
        while(itProps.hasNext()){
             SemanticProperty semProp1=itProps.next();
             if(semProp1.isBoolean()){
                %>
                <tr><td>
                <%=semProp1.getDisplayName(user.getLanguage())%>  </td><td><%=mgr.renderElement(request, semProp1,mgr.MODE_CREATE)%></td>
                    <input type="hidden" name="swbdirParam_<%=semProp1.getName()%>" value="1">
                </tr>
                <%
              }
             FormElement element=mgr.getFormElement(semProp1);
             if(element!=null && element.getId()!=null){
                if(element.getId().indexOf("selectOne")>-1){
                    mgr.setType(mgr.TYPE_XHTML);
                    %>
                     <tr><td><%=semProp1.getDisplayName(user.getLanguage())%></td><td><%=mgr.renderElement(request, semProp1,mgr.MODE_CREATE)%></td>
                    <input type="hidden" name="swbdirParam_<%=semProp1.getName()%>" value="1">
                    </tr>
                   <%
                   continue;
                }
             }
        }
        %>
        <tr><td colspan="2" align="center"><input type="submit" value="Filtrar"></td></tr>
        </table>
            </fieldset>
        </form>
                   </div>
        <p align="right">Ordenar por
            <%if (cls.equals(ClasifiedBuySell.sclass)) {
                if (toggleOrder) {
                %>
                    <a href="<%=urlOrder.setParameter("orderBy", "pricel")%>">menor precio</a> |
                <%
                } else {
                %>
                    <a href="<%=urlOrder.setParameter("orderBy", "priceh")%>">mayor precio</a> |
                <%
                }
             }
             %>
            <a href="<%=urlOrder.setParameter("orderBy", "title")%>">nombre</a> | <a href="<%=urlOrder.setParameter("orderBy", "date")%>">fecha</a></p>
        <!--Termina desplagado de criterios (filtros) de busqueda-->
        <%
         //Comienza Desplegado de elementos filtrados (si aplica) y ordenados
         SWBResourceURL urlDetail=paramRequest.getRenderUrl();
         SWBResourceURL urlEdit=paramRequest.getRenderUrl();
         SWBResourceURL urlRemove=paramRequest.getRenderUrl();

         urlDetail.setParameter("act", "detail");
         urlEdit.setParameter("act", "edit");
         urlRemove.setParameter("act", "detail");

         int cont=0;
         boolean exist=false;
         HashMap map=new HashMap();
         map.put("separator", "-");
         Iterator itResult=setResult.iterator();
         while(itResult.hasNext())
         {
            cont++;
            DirectoryObject dirObj=(DirectoryObject)itResult.next();
            if(cont<=iIniPage) continue;
            else if(cont>iFinPage) break;
            exist=true;
            String img="", title="", description="", tags="", price=null, creator="", created="";
            String claimerUrl = "";
            urlDetail.setParameter("uri", dirObj.getURI());
            urlEdit.setParameter("uri", dirObj.getURI());
            urlRemove.setParameter("uri", dirObj.getURI());
            User userObj=null;
            User claimer = null;
            SemanticObject semObject = dirObj.getSemanticObject();
            Iterator<SemanticProperty> ipsemProps=semObject.listProperties();
            while(ipsemProps.hasNext())
            {
               SemanticProperty semProp=ipsemProps.next();
               if(semProp.isDataTypeProperty()){
                   String propValue=semObject.getProperty(semProp);
                   if(propValue!=null && !propValue.equals("null")){
                        if(semProp==DirectoryObject.swbcomm_dirPhoto)
                        {
                            img="<img src=\""+SWBPortal.getWebWorkPath()+base.getWorkPath()+"/"+semObject.getId()+"/"+propValue+ "\" width=\"90\" height=\"90\">";
                        }if(semProp==DirectoryObject.swb_title) {
                             title=propValue;
                        }else if(semProp==DirectoryObject.swb_description){
                             description=propValue;
                        }else if(semProp==DirectoryObject.swb_tags){
                             tags=propValue;
                        }else if(semProp==ClasifiedBuySell.swbcomm_Price){
                             price=propValue;
                        }else if(semProp==DirectoryObject.swb_created){
                            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
                            Date date=new Date();
                            date=formatoDelTexto.parse(propValue);
                            propValue= SWBUtils.TEXT.getTimeAgo(date, user.getLanguage());
                            created=propValue;
                        }
                    }
                } /*else if (semProp==DirectoryObject.swbcomm_claimer) {
                    SemanticObject claimerUsr = semObject.getObjectProperty(DirectoryObject.swbcomm_claimer);
                    if (claimerUsr != null) {
                        claimer = (User)claimerUsr.createGenericInstance();
                        claimerUrl = "<a href=\""+perfilPath+"?user="+claimer.getEncodedURI()+"\">"+claimer.getFullName()+"</a>";
                    }
               }*/else if(semProp==DirectoryObject.swb_creator){
                        SemanticObject semUser=semObject.getObjectProperty(DirectoryObject.swb_creator);
                        if(semUser!=null){
                            userObj=(User)semUser.createGenericInstance();
                            creator="<a href=\""+perfilPath+"?user="+userObj.getEncodedURI()+"\">"+userObj.getFullName()+"</a>";
                         }
                }
            }
            %>
              <div class="listEntry" onmouseover="this.className='listEntryHover'" onmouseout="this.className='listEntry'">
              <%if(!img.equals("")){%><%=img%><%}else{%><img src="<%=SWBPortal.getContextPath()%>/swbadmin/images/noDisponible.gif" /><%}%>
              <div class="listEntryInfo">
                    <p class="tituloRojo">
                        <%=title%>
                    </p>
                        <!--%=wpage.getPath(map)%-->
                    <p>
                        <%=description%>
                    </p><br/>
                    <%if(price!=null && price.trim().length()>0 && !price.equals("null")){%><p><span class="itemTitle">Precio: </span><%=price%></p><%}%>
                    <%if (!tags.trim().equals("")) {%><p><span class="itemTitle">Palabras clave: </span><%=tags%></p><%}%>
                    <p><span class="itemTitle">Creado por: </span><%=creator%>, <%=created%></p>
                    <%
                    if (claimer != null) {%>
                        <p><b>Este elemento ha sido reclamado por <a href="<%=claimerUrl%>">Mi</a></b></p><%
                    }
                    %>
                    <p class="tituloRojo"><p class="vermas"><a href="<%=urlDetail%>"><%=paramRequest.getLocaleString("seeMore")%></a></p>
                    <%
                    if(user.isRegistered() && user.isSigned()) {
                        UserGroup group=user.getUserRepository().getUserGroup("admin");
                        if((userObj!=null && userObj.getURI().equals(user.getURI())) || group!=null && user.hasUserGroup(group)){
                    %>
                        <div>
                            <p><a href="<%=urlEdit%>"><%=paramRequest.getLocaleString("editInfo")%></a></p>
                            <p><a href="<%=urlRemove.setAction(urlRemove.Action_REMOVE)%>"><%=paramRequest.getLocaleString("remove")%></a></p>
                        </div>
                    <%  }
                    }
                    %>
                    <div class="clear">&nbsp;</div>
              </div>
             </div>
            <%
            }
            if(!exist){
                %>
                <div class="listEntry" onmouseover="this.className='listEntryHover'" onmouseout="this.className='listEntry'">
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/images/anunciate.gif">
                    <div class="listEntryInfo">
                        <p class="tituloRojo">
                            Título de tu anuncio
                        </p>
                            <!--%=wpage.getPath(map)%-->
                        <p>
                            Descripci&oacute;n de tu anuncio
                        </p><br/>
                        <p><span class="itemTitle">Palabras clave: </span>Palabras clave</p>
                        <p><span class="itemTitle">Creado por: </span>Tu nombre aquí</p>

                        <div class="vermasFloat"><p class="tituloNaranja"><p class="vermas"><%=paramRequest.getLocaleString("seeMore")%></p></div>
                        <div class="clear">&nbsp;</div>
                   </div>
                </div>
                <%
            }
            %>
      </div>
    </div>
<%
 }
%>

<%!
    //Función utilizada para manejo de paginación
    private String getPageRange(int iSize, int iPageNum) {
        int iTotPage = 0;
        int iPage = 1;
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
        return iIniPage + "|" + iFinPage + "|" + iTotPage;
    }
%>