<%-- 
    Document   : view
    Created on : 8/05/2013, 09:48:06 AM
    Author     : Lennin
--%>

<%@page import="com.infotec.lodp.swb.Publisher"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="com.infotec.lodp.swb.Developer"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.Date"%>
<%@page import="com.infotec.lodp.swb.Category"%>
<%@page import="com.infotec.lodp.swb.Institution"%>
<%@page import="com.infotec.lodp.swb.utils.LODPUtils"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.infotec.lodp.swb.resources.ApplicationResource"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="com.infotec.lodp.swb.Application"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.List"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.Resource"%> 
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

<!DOCTYPE html>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />

<%
    WebPage wpage = paramRequest.getWebPage();
    SWBResourceURL renderURL = paramRequest.getRenderUrl();
    WebSite wsite = wpage.getWebSite();
    Resource base = paramRequest.getResourceBase();
    Iterator<Application> itAppList = Application.ClassMgr.listApplications(wsite);
    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    User user = paramRequest.getUser();
    
    String strNumItems = base.getAttribute("numpag", "10");
    String npage = request.getParameter("page");
    String orderby = request.getParameter("order");
    String filterby = request.getParameter("filter");
    String direction = request.getParameter("direction");
    String filteruri = request.getParameter("filteruri");
    String filterCat= request.getParameter("filterCat");
    String action = request.getParameter("act");
    String msgExitoGuardado = request.getParameter("msgExitoAPP");
    
    String filterCatShort= filterCat;
    String filterUriShort = filteruri;
    
    GenericObject go = null;
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MMMMM/yyyy", new Locale("es"));
    long intSize = 0;

    int numPages = 10;
    try {
        numPages = Integer.parseInt(strNumItems);
    } catch (Exception e) {
        numPages = 10;
    }
    
    if (orderby == null) {
        orderby = "date";
    }
    
    if (direction == null) {
        direction = "up";
    }
    
    if (filterby == null) {
        filterby = "";
    }
    if (action == null) {
        action = "";
    }
    
    if(null!=filteruri){
        filteruri = SemanticObject.shortToFullURI(filteruri);
    }
    
    if(null!=filterCat){
        filterCat = SemanticObject.shortToFullURI(filterCat);
    }
    
    if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {
        // llamada como estrategia
    } else {
        // llamado como contenido
        if (action.equals("")) {
            String txtTitleSearch = paramRequest.getLocaleString("lbl_busqueda");

            Date startSearch = new Date(System.currentTimeMillis());

            // ordenamiento orderby y filtrado de DataSets
            Iterator<Application> itAp1 = null;
            if (null != filteruri && filteruri.trim().length() > 0) {
                
                go = ont.getGenericObject(filteruri);
 
                if (go != null) {

                    if (go instanceof Category) {
                        
                        itAp1 = Application.ClassMgr.listApplicationByCategory((Category) go, wsite);
                        
                    } else if (go instanceof Institution) {

                        
                        Iterator<Dataset> itIns = Dataset.ClassMgr.listDatasetByInstitution((Institution) go, wsite);
                        HashMap<String, Application> hmFilterInst = new HashMap<String, Application>();

                        while (itIns.hasNext()) {

                            Dataset ins = itIns.next();
                            Iterator<Application> itApp = Application.ClassMgr.listApplicationByRelatedDataset(ins, wsite);

                            while (itApp.hasNext()) {
                                Application app = itApp.next();
                                hmFilterInst.put(app.getURI(), app);
                            }
                        }

                        itAp1 = hmFilterInst.values().iterator();
 
                    }
//                    else if (go instanceof Developer) {
//                        Developer devAutor = (Developer) go;
//                        Iterator<Application> listApp = Application.ClassMgr.listApplications(wsite);
//                        HashMap<String, Application> hmFilterAutor = new HashMap<String, Application>();
//                        
//                        while(listApp.hasNext()){
//                            Application apAutor = listApp.next();
//                            GenericObject goAutorDes = apAutor.getAppAuthor().createGenericInstance();
//                            User usAutorDes = (User)goAutorDes;
//                            
//                            if(devAutor.getFullName().equals(usAutorDes.getFullName())){
//                                hmFilterAutor.put(apAutor.getURI(), apAutor);                   
//                            }
//                        }
//                        itAp1 = hmFilterAutor.values().iterator();
//                        
//                    } else if(go instanceof Publisher){
//                                          
//                        Iterator<Dataset> dsPub = Dataset.ClassMgr.listDatasetByPublisher((Publisher) go, wsite);
//                        HashMap<String, Application> hmFilterPub = new HashMap<String, Application>();
//                        
//                        while (dsPub.hasNext()) {
//
//                            Dataset appPub = dsPub.next();
//                            Iterator<Application> itAppPub = Application.ClassMgr.listApplicationByRelatedDataset(appPub, wsite);
//
//                            while (itAppPub.hasNext()) {
//                                Application appliPub = itAppPub.next();
//                                hmFilterPub.put(appliPub.getURI(), appliPub);                              
//                            }
//                            
//                        }
//                       itAp1 = hmFilterPub.values().iterator(); 
//                    } 
                    else {
                        itAp1 = Application.ClassMgr.listApplications(wsite);
                    }
                } else {
                    itAp1 = Application.ClassMgr.listApplications(wsite);
                }
            } else {
                itAp1 = Application.ClassMgr.listApplications(wsite);
            }

            // obteniendo Aplicaciones que coincidan con el texto a buscar
            String queryinput = request.getParameter("search");
            String queryOriginal = queryinput != null ? queryinput : paramRequest.getLocaleString("lbl_buscar");
            
            if (null == queryinput) {
                queryinput = "";
            } else {
                queryinput = queryinput.trim().toLowerCase();
                txtTitleSearch = paramRequest.getLocaleString("lbl_busqueda");
            }

            // Se guardan ds que coinciden con la búsqueda
            HashMap<String, Application> hmresults = new HashMap<String, Application>();

            // revisar DS si hay texto abuscar
            if (queryinput != null && queryinput.trim().length() > 0) {
            
                queryinput = queryinput.replaceAll(",", " ");  // si la búsqueda viene separado por comas, las cambio por espacios en blanco
                String REGEX = "\\s";  //espacio en blanco
                Pattern p = Pattern.compile(REGEX);
                String[] arrKeys = p.split(queryinput); // se separa query por los espacios en blanco

                //Se guardan lista de palabras puestas en la búsqueda
                HashMap<String, String> hmquery = new HashMap();

                // para una única palabra
                if (arrKeys == null && queryinput != null && queryinput.trim().length() > 0) {
                    hmquery.put(queryinput, queryinput);
                } else {  // existen mas de una palabra en el queryinput
                    for (int j = 0; j < arrKeys.length; j++) {
                        String s = arrKeys[j];
                       
                        if (s != null && s.trim().length() > 0) {
                            if (hmquery.get(s) == null) {
                                hmquery.put(s, s);
                            }
                        }
                    }
                }

                if (itAp1.hasNext()) {
                    while (itAp1.hasNext()) {
                        // filtrar AP deacuerdo al texto recibido
                        // separar palabras ya sea por "," o por espacios y si coincide por lo menos una guarda ese AP. 
                        Application ap = itAp1.next();
                        StringBuilder txtAP = new StringBuilder(ap.getAppTitle() != null ? ap.getAppTitle().trim() : "");
                        txtAP.append(" ");
                        txtAP.append(ap.getAppDescription() != null ? ap.getAppDescription().trim() : "");
                        String reviewTXT = txtAP.toString().trim().toLowerCase(); // texto completo en donde se buscará la ocurrencia
             
                        if ((reviewQuery(hmquery, reviewTXT)) && hmresults.get(ap.getURI()) == null) {  //||txtAuto.indexOf(queryinput)>-1 
                            hmresults.put(ap.getURI(), ap);
                        }
                    }
                    itAp1 = hmresults.values().iterator();  // pone en el iterador las AP obtenidas
                }
            }

            // dejo en hm los ds
            HashMap<String, Application> hmcp = new HashMap<String, Application>();

            while (itAp1.hasNext()) {
                Application apl = itAp1.next();
                
                if(apl.isAppValid()){
                    hmcp.put(apl.getURI(), apl);
                }
            }
            
            Iterator<Application> itAp = null;
            
            if(hmcp.size()>0){
                itAp = ApplicationResource.orderDS(hmcp.values().iterator(), orderby);
                intSize=hmcp.size();
            } else {
            intSize = 0;
            }

            Date endSearch = new Date(System.currentTimeMillis());
            long searchTime = endSearch.getTime() - startSearch.getTime();
            long searchTimeSecs = searchTime / 1000;
            
        if (msgExitoGuardado != null) {
               
            String strMsg = msgExitoGuardado;
            strMsg = msgExitoGuardado.replace("<br>", "\\n\\r");
            
%>

    <script type="text/javascript">
        alert('<%=strMsg%>');
    </script>

        <%}%>
<div class="buscar_ds">
    <form method="post" action="" id="aps_search">
        <%
            if (filterby != null && filterby.trim().length() > 0) {
        %>
        <input type="hidden" name="filter" value="<%=filterby%>"/>
        <%
            }
        %>
                <label for="txt_search"><%=txtTitleSearch%></label><input type="text" name="search" value="<%=queryOriginal%>" onfocus="if (this.value == 'Search') {
                            this.value = ''
                        }
                        ;"><button type="submit"><%=paramRequest.getLocaleString("lbl_buscar")%></button>
                <%
                    // si hubo búsqueda
                    if (null != queryinput && queryinput.trim().length() > 0) {
                        if (intSize > 0) {
                %>
                <br/>
               <%=paramRequest.getLocaleString("lbl_time1")%> <%=intSize%> <%=paramRequest.getLocaleString("lbl_resTime")%>
                <%
                } else {
                %>
                <br/>
                <%=paramRequest.getLocaleString("lbl_sinResultados")%>
                <%     
                }
                // tiempo en segundos de lo que se tardó la búsqueda
                %>
                (<%=searchTimeSecs%> <%=paramRequest.getLocaleString("lbl_segundos")%>)
                <%
                    }
                %>        
    </form>
</div>

<div class="izq">
        
        <p class="rubro"><%=paramRequest.getLocaleString("lbl_filterCategory")%></p>
        <ul>
           <%
                if(null!=filterCat&&filterCat.trim().length()>0){
                    go = ont.getGenericObject(filterCat);
                    Category cat = (Category)go;
                        %>
                <li><a href="#" class="tema-inst"><%=cat.getCatName()%></a></li>
                <%
                    SWBResourceURL url = paramRequest.getRenderUrl();
                    
                        if(null!=filterCat && filterCat.trim().length()>0) {
                            url.setParameter("filteruri", filterUriShort); 
                        }
                        if (null != orderby) {
                            url.setParameter("order", orderby);
                        }
                        if (queryinput != null && queryinput.trim().length() > 0) {
                            url.setParameter("search", queryinput);
                        }                    
                    %>
                <li><a href="<%=url.toString()%>" class="tema-todos"><%=paramRequest.getLocaleString("lbl_all")%></a></li>  
                <%
                } else {
                    Iterator<Category> listCat = Category.ClassMgr.listCategories(wsite);
                    while (listCat.hasNext()) {
                        
                        Category category = listCat.next();
                     
                        SWBResourceURL url = paramRequest.getRenderUrl();
                        url.setParameter("filterCat", category.getShortURI());
                        
                        if(null!=filteruri){
                            url.setParameter("filteruri", filterUriShort); 
                        }
                        if (null != orderby) {
                            url.setParameter("order", orderby);
                        }
                        if (queryinput != null && queryinput.trim().length() > 0) {
                            url.setParameter("search", queryinput);
                        }
                %>
            <li><a href="<%=url.toString()%>" class="tema-inst" ><%=category.getCatName()%></a></li> 
                <%
                    }
                }
                
                %>
        </ul>   
    
        <p class="rubro"><%=paramRequest.getLocaleString("lbl_filterIntitution")%></p>
        <ul>
                <%
                if(null!=filteruri&&filteruri.trim().length()>0){
                    go = ont.getGenericObject(filteruri);
                    Institution inst = (Institution)go;
                        %>
                <li><a href="#" class="tema-inst"><%=inst.getInstitutionTitle()%></a></li>
                <%
                    SWBResourceURL url = paramRequest.getRenderUrl();
                        if (null != orderby) {
                            url.setParameter("order", orderby);
                        }
                        if (queryinput != null && queryinput.trim().length() > 0) {
                            url.setParameter("search", queryinput);
                        }                    
                    %>
                <li><a href="<%=url.toString()%>" class="tema-todos"><%=paramRequest.getLocaleString("lbl_all")%></a></li>  
                <%
                } else {
                    Iterator<Institution> itins = Institution.ClassMgr.listInstitutions(wsite);
                    while (itins.hasNext()) {
                        Institution inst = itins.next();

                        SWBResourceURL url = paramRequest.getRenderUrl();
                        url.setParameter("filteruri", inst.getShortURI());
                        if (null != orderby) {
                            url.setParameter("order", orderby);
                        }
                        if (queryinput != null && queryinput.trim().length() > 0) {
                            url.setParameter("search", queryinput);
                        }
                %>
            <li><a href="<%=url.toString()%>" class="tema-inst" title="<%=inst.getInstitutionDescription() != null ? inst.getInstitutionDescription().trim() : inst.getInstitutionTitle()%>"><%=inst.getInstitutionTitle()%></a></li> 
                <%
                    }
                }
                
                %>
        </ul>  
    </div>
<!--    <div class="izq_autor">
     <ul>
        <li><h3><%--=paramRequest.getLocaleString("lbl_filterAuthor")--%></h3></li>-->
     <%--
        Iterator<Application> itAppAuthor = Application.ClassMgr.listApplications(wsite);
        
        while (itAppAuthor.hasNext()) {

            Application aplication = itAppAuthor.next();
            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setParameter("filteruri", aplication.getEncodedURI());
            GenericObject objAutor = aplication.getAppAuthor().createGenericInstance();
            String descPb = "";
            String filterAuthor = "";
            
            if( objAutor instanceof Publisher){
                Publisher pb = (Publisher)objAutor;
                if(pb.getPubInstitution() != null){
                   filterAuthor = pb.getPubInstitution().getInstitutionTitle();
                   descPb = pb.getPubInstitution().getInstitutionDescription();
                }
            }
            
            if(objAutor instanceof Developer){
                Developer dv = (Developer)objAutor;
                filterAuthor = dv.getFullName();
            }
            
            if (null != orderby) {
                url.setParameter("order", orderby);
            }
            
            System.out.println("Descripcion institucion" + " " + descPb);
            System.out.println("nombre autor" + " " + filterAuthor);
            
            if(!(descPb.equals("") && filterAuthor.equals(""))){

        %>
            <li><a href="<%=url.toString()%>" title="<%=descPb != null ? descPb.trim() : ""%>"><%=filterAuthor%></a></li>  
        <%
            }}
        --%>
<!--    </ul>  
   </div>
</div>-->

<div class="der">
    <div id="temasdatos">
         <div class="listado">
    <div class="orden">
        <%
            SWBResourceURL urlOrder = paramRequest.getRenderUrl();
            urlOrder.setParameter("act", "");
            if (null != filterby) {
                urlOrder.setParameter("filter", filterby);
            }
            if (null != filteruri) {
                urlOrder.setParameter("filteruri", filteruri);
            }
            if (queryinput != null && queryinput.trim().length() > 0) {
                urlOrder.setParameter("search", queryinput);
            }
            String ckdCreated = "", ckdComment = "", ckdRank = "";
            if (null != orderby) {
                if (orderby.equals(ApplicationResource.ORDER_RANK)) {
                    ckdRank = "class=\"selected\"";
                } else if (orderby.equals(ApplicationResource.ORDER_COMMENTS)) {
                    ckdComment = "class=\"selected\""; 
                } else if (orderby.equals(ApplicationResource.ORDER_CREATED)) {
                    ckdCreated = "class=\"selected\""; 
                }
            }

        %>
        
        <a <%=ckdRank%> href="<%=urlOrder.toString()%>&order=<%=ApplicationResource.ORDER_RANK%>';" <%=ckdCreated%>><%=paramRequest.getLocaleString("lbl_mejorCalificada")%></a> 
        <a <%=ckdComment%> href="<%=urlOrder.toString()%>&order=<%=ApplicationResource.ORDER_COMMENTS%>"><%=paramRequest.getLocaleString("lbl_masComentadas")%></a> 
        <a <%=ckdCreated%> href="<%=urlOrder.toString()%>&order=<%=ApplicationResource.ORDER_CREATED%>"><%=paramRequest.getLocaleString("lbl_fecha")%></a>
            
    </div>
        
    <%

        //PAGINACION
        int ps = numPages;
        long l = intSize;

        int p = 0;
        if (npage != null) {
            p = Integer.parseInt(npage);
        }
        int x = 0;
    %>
        
    <div class="lista10">
         <ol>
            <%
                if (intSize == 0) {
            %>
            <li><%=paramRequest.getLocaleString("lbl_notAppFound")%></li>
            <% } else {
                String wpurl = wpage.getUrl()+"?act=detail&suri=";
                while (itAp.hasNext()) {
                    //PAGINACION ////////////////////
                            if (x < p * ps) {
                                x++;
                                continue;
                            }
                            if (x == (p * ps + ps) || x == l) {
                                break;
                            }
                            x++;
                            /////////////////////////////////
                    Application apls = itAp.next();
                    
                    if(apls.isAppValid()){
            %>
                    <li>
                        <a href="<%=wpurl + apls.getShortURI()%>"><%=apls.getAppTitle()%></a><br/>
                        <p><%=apls.getAppDescription()%></p>
                    </li>
            <%}}}
            %>
         </ol>
    </div>
         <div class="pager">
        
        <%
            if (p > 0 || x < l) //Requiere paginacion
            {

                int pages = (int) (l / ps);
                if ((l % ps) > 0) {
                    pages++;
                }
       %>
       
  <div class="pager-total">Página <%=p%>/<%=pages%></div>       
  <div class="pager-index">
      
       <%

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                int inicia = 0;
                int finaliza = pages;
                int rangoinicial = p - 5;
                int rangofinal = p + 5;
                if (pages <= 10) {
                    inicia = 0;
                    finaliza = pages;
                } else {
                    if (rangoinicial < 0) {
                        inicia = 0;
                        finaliza = Math.abs(rangoinicial) + rangofinal;
                    } else if (rangofinal > pages) {
                        inicia = pages - 10;
                        finaliza = pages;
                    } else {
                        inicia = rangoinicial;
                        finaliza = rangofinal;
                    }
                }

                if (pages > 10) {
                    SWBResourceURL urlNext = paramRequest.getRenderUrl();
                    urlNext.setParameter("page", "" + 0);
                    if (null != orderby) {
                        urlNext.setParameter("order", orderby);
                    }
                    if (null != direction) {
                        urlNext.setParameter("direction", direction);
                    }
                    if (null != filterby) {
                        urlNext.setParameter("filter", filterby);
                    }
                    if (null != filteruri) {
                        urlNext.setParameter("filteruri", filteruri);
                    }
                    if (queryinput != null && queryinput.trim().length() > 0) {
                        urlNext.setParameter("search", queryinput);
                    }

                    out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">"+paramRequest.getLocaleString("pg_gofirst")+"</a> ");
                }

                for (int z = inicia; z < finaliza; z++) {
                    SWBResourceURL urlNext = paramRequest.getRenderUrl();
                    urlNext.setParameter("page", "" + z);
                    if (null != orderby) {
                        urlNext.setParameter("order", orderby);
                    }
                    if (null != direction) {
                        urlNext.setParameter("direction", direction);
                    }

                    if (null != filterby) {
                        urlNext.setParameter("filter", filterby);
                    }
                    if (null != filteruri) {
                        urlNext.setParameter("filteruri", filteruri);
                    }
                    if (queryinput != null && queryinput.trim().length() > 0) {
                        urlNext.setParameter("search", queryinput);
                    }
                    if (z != p) {
                        out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">" + (z + 1) + "</a> ");
                    } else {
                        out.println((z + 1) + " ");
                    }

                }
                if (pages > 10) {
                    SWBResourceURL urlNext = paramRequest.getRenderUrl();
                    urlNext.setParameter("page", "" + (pages - 1));
                    if (null != orderby) {
                        urlNext.setParameter("order", orderby);
                    }
                    if (null != direction) {
                        urlNext.setParameter("direction", direction);
                    }
                    if (null != filterby) {
                        urlNext.setParameter("filter", filterby);
                    }
                    if (null != filteruri) {
                        urlNext.setParameter("filteruri", filteruri);
                    }
                    if (queryinput != null && queryinput.trim().length() > 0) {
                        urlNext.setParameter("search", queryinput);
                    }
                    out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">"+paramRequest.getLocaleString("pg_golast")+"</a> ");
                }
            
        %>
    </div>
    </div>
    
    <%}%>
    
    </div>
    </div>
    </div>

<%
} else if (action.equals("detail")) {  // detalle de la Apliacion seleccionada
    String suri = request.getParameter("suri");
    go = ont.getGenericObject(SemanticObject.shortToFullURI(suri));
    if (go instanceof Application) {
%>
<div>
    <%
        Application aps = (Application) go;
        GenericObject ob = aps.getAppAuthor().createGenericInstance();
        String fullName = "";
        
    if(ob instanceof Developer  ){
        Developer db = (Developer)ob ;
        fullName = db.getFullName();
    }
    
    if(ob instanceof Publisher){
        Publisher db = (Publisher)ob ;
        fullName = db.getFullName();
    }
    
    if(ob instanceof User){
        User db = (User)ob ;
        fullName = db.getFullName();
    }   
    
// se actualiza los views
    aps.sendView(request, user, wpage);
        
    %>
    <div>
        <h2><%=aps.getAppTitle()%></h2> 
        <ul>
            <li>
                <label><%=paramRequest.getLocaleString("lbl_autorDetalle")%></label>&nbsp;&nbsp;&nbsp;&nbsp;<%=fullName%>
            </li>
            <li>
                <label><%=paramRequest.getLocaleString("lbl_fechaCracionDetalle")%></label>&nbsp;&nbsp;&nbsp;&nbsp;<%=sdf2.format(aps.getAppCreated())%> 
            </li>
            <li>
                <label><%=paramRequest.getLocaleString("lbl_licenciaDetalle")%></label>&nbsp;&nbsp;&nbsp;&nbsp;<%=aps.getAppLicense() != null ? aps.getAppLicense().getLicenseTitle() : ""%>
            </li>
            <li>
                <label><%=paramRequest.getLocaleString("lbl_urlDetalle")%></label>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=renderURL.setMode(ApplicationResource.REDIRECT_URL).setParameter("uri", aps.getShortURI())%>" ><%=aps.getAppURL()%></a>
            </li>
            <li>
                <label><%=paramRequest.getLocaleString("lbl_visitasDetalle")%></label>&nbsp;&nbsp;&nbsp;&nbsp;<%=aps.getViews()%>
            </li>
            
             <%
                    Long comment = SWBUtils.Collections.sizeOf(aps.listComments());
            %>
            
            <li>
                <label><%=paramRequest.getLocaleString("lbl_numComment")%></label>&nbsp;&nbsp;&nbsp;&nbsp;<%=comment%>
            </li>
            <p>
            <p>
            <li>
                <%=aps.getAppDescription()%>
            </li>
            <li>
                <label><%=paramRequest.getLocaleString("lbl_DSUsado")%></label>&nbsp;&nbsp;<%=aps.getRelatedDataset() != null ? aps.getRelatedDataset().getDatasetTitle() : "" %>
                
                &nbsp;
                &nbsp;
                <%
                if(user.isSigned()&& (user.getSemanticObject().createGenericInstance() instanceof Developer || user.getSemanticObject().createGenericInstance() instanceof Publisher)){
                %>
                    <a href="<%=renderURL.setMode(SWBResourceURL.Mode_EDIT).setParameter("uri", aps.getShortURI())%>" ><%=paramRequest.getLocaleString("lbl_editar")%></a>
                <%
                }             
                %>
            </li>
             
        </ul>
    </div>  
            
    <div>
        <label><%=paramRequest.getLocaleString("lbl_DSRelacion")%></label>
        <ul>
            <%  // lista de aplicaciones relacionadas
                Iterator<Dataset> itDataS = Dataset.ClassMgr.listDatasets(wsite);

                if (itDataS.hasNext()) {
                    while (itDataS.hasNext()) {
                        Dataset dt = itDataS.next();
                        Iterator<Application> itApp = Application.ClassMgr.listApplicationByRelatedDataset(dt, wsite);
                        while (itApp.hasNext()) {
                            Application ap = itApp.next();
            %>
            <li title="<%=ap.getAppDescription()%>"><%=ap.getAppTitle()%></li>
                <%
                        }
                    }
                } else {

                %>
            <li><%=paramRequest.getLocaleString("lbl_DSNoRelacion")%></li>
                <%                    }
                %>
        </ul>
    </div>
    <div>
        <label><%=paramRequest.getLocaleString("lbl_appRelacion")%></label>
        <ul>
            <%  // lista de aplicaciones relacionadas
                Iterator<Dataset> itDataSet = Dataset.ClassMgr.listDatasets(wsite);

                if (itDataSet.hasNext()) {
                    while (itDataSet.hasNext()) {
                        Dataset dt = itDataSet.next();
                        Iterator<Application> itApp = Application.ClassMgr.listApplicationByRelatedDataset(dt, wsite);
                        while (itApp.hasNext()) {
                            Application ap = itApp.next();
            %>
            <li title="<%=ap.getAppDescription()%>"><%=ap.getAppTitle()%></li>
                <%
                        }
                    }
                } else {

                %>
            <li><%=paramRequest.getLocaleString("lbl_appNoRealcion")%></li>
                <%                    }
                %>
        </ul>
    </div>  
</div> 

<%
}}}   
 if(user.isSigned()&& (user.getSemanticObject().createGenericInstance() instanceof Developer || user.getSemanticObject().createGenericInstance() instanceof Publisher)){
%>

<p><a href="<%=renderURL.setMode(ApplicationResource.ADD_APPLICATION)%>"><%=paramRequest.getLocaleString("lbl_subirApp")%></a></p>

<%
 }
%>

<%!
    public boolean reviewQuery(HashMap<String, String> hm, String texto) {
        boolean res = Boolean.FALSE;
 
        if (null != hm) {
            Iterator<String> itstr = hm.keySet().iterator();
            while (itstr.hasNext()) {
                String skey = itstr.next();
                if (texto.indexOf(skey) > -1) {
                    res = Boolean.TRUE;
                    break;
                }
            }
        }
        return res;
    }

%>

