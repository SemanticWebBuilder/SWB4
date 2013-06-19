<%-- 
    Document   : view
    Created on : 8/05/2013, 09:48:06 AM
    Author     : Lennin
--%>

<%@page import="org.semanticwb.model.Role"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.SWBPortal"%>
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
    String idAdministrador = base.getAttribute("idRoleAdminAPP", "");
    String msjAprES = base.getAttribute("msjAPPAprobada_es", "msjArobadoES");
    String msjAprEN = base.getAttribute("msjAPPAprobada_en", "msjArobadoEN");
    String msjReES = base.getAttribute("msjAPPAprobada_es", "msjRechazadoES");
    String msjReEN = base.getAttribute("msjAPPAprobada_en", "msjRechazadoEN");
    String npage = request.getParameter("page");
    String orderby = request.getParameter("order");
    String filterby = request.getParameter("filter");
    String direction = request.getParameter("direction");
    String filteruri = request.getParameter("filteruri");
    String filterCat= request.getParameter("filterCat");
    String action = request.getParameter("act");
    String msgExitoGuardado = request.getParameter("msgExitoAPP");
    Publisher publisher = LODPUtils.getPublisher(user);
    Developer dev = LODPUtils.getDeveloper(user);
    String roladmin = base.getAttribute("idAdminAPP");
    Role role = wsite.getUserRepository().getRole(roladmin);
    
    System.out.println("roladmin" + roladmin);
    System.out.println("role" + role);
    
    System.out.println("publicador" + publisher);
    System.out.println("desarrollador" + dev);
    System.out.println("usiario" + user);
    
    boolean isAdmin = Boolean.FALSE;
    if(null!=role&&user.hasRole(role)||null!=publisher&&publisher.hasRole(role)||null!=dev&&dev.hasRole(role)){
        isAdmin = Boolean.TRUE;
    }
    System.out.println("isAdmin" + isAdmin);
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

                 if (go instanceof Institution) {
     
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
                
            } else if(null==filteruri && null!=filterCat) {
               go = ont.getGenericObject(filterCat);
               itAp1 = Application.ClassMgr.listApplicationByCategory((Category) go, wsite);
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
            //Aplicaciones por revizar
            List<Application> listAppReview= new ArrayList();

            while (itAp1.hasNext()) {
                Application apl = itAp1.next();
                
                if(apl.isApproved() && apl.isReviewed()){
                    hmcp.put(apl.getURI(), apl);
                }else if(isAdmin && !apl.isReviewed()){
                    listAppReview.add(apl);
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

<div class="izq-apps1">
        
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

<div class="der-apps2">
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
        
        <a <%=ckdRank%> href="<%=urlOrder.toString()%>&order=<%=ApplicationResource.ORDER_RANK%>"><%=paramRequest.getLocaleString("lbl_mejorCalificada")%></a> 
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
                List<Application> listAp = new ArrayList();
                
                while(itAp.hasNext()){
                    Application aplication = itAp.next();
                    listAp.add(aplication);
                }
                
                for(Application apls : listAp){
      
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
                    
                    if(apls.isApproved() && apls.isReviewed()){
            %>
                    <li>
                        <a href="<%=wpurl + apls.getShortURI()%>"><%=apls.getAppTitle()%></a><br/>
                        <p><%=apls.getAppDescription()%></p>
                    </li>
            <%}}}%>
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
    
    <%if(isAdmin){%>
    
    <div class="lista10">
        
        <h3><%=paramRequest.getLocaleString("lbl_appReview")%></h3>
        
        <ol>
            <%
                intSize = listAppReview.size();
                if (intSize == 0) {
            %>
            <li><%=paramRequest.getLocaleString("lbl_notAppFound")%></li>
                    <%} else {
                    
                    String wpurl = wpage.getUrl()+"?review=true&act=detail&suri=";
                    for(Application apliAdmin : listAppReview){                    
                        
                    %>
                <li>
                    <a href="<%=wpurl + apliAdmin.getShortURI()%>"><%=apliAdmin.getAppTitle()%></a><br/>
                    <p><%=apliAdmin.getAppDescription()%></p>
                </li>
            <%}}%>
        </ol>
    </div>  
     <%}
     if(user.isSigned()&& (user.getSemanticObject().createGenericInstance() instanceof Developer || user.getSemanticObject().createGenericInstance() instanceof Publisher)){
     %>

<p><a href="<%=renderURL.setMode(ApplicationResource.ADD_APPLICATION)%>"><%=paramRequest.getLocaleString("lbl_subirApp")%></a></p>

<%
 }
%>          
    </div>
    </div>

<%
} else if (action.equals("detail")) {  // detalle de la Apliacion seleccionada
    String suri = request.getParameter("suri");
    String review = request.getParameter("review");
    boolean isFromReview = Boolean.FALSE;
    
        if(null!=review && review.trim().equals("true")){
            isFromReview = Boolean.TRUE;
        }
        
    go = ont.getGenericObject(SemanticObject.shortToFullURI(suri));
    if (go instanceof Application) {
%>
<div>
    <%
        Application aps = (Application) go;
        GenericObject ob = aps.getAppAuthor().createGenericInstance();
        String fullName = "";
        String instlogo = "images/imagen.gif";
        Iterator<Dataset> app = aps.listRelatedDatasets();
        List<Dataset> listDS = new ArrayList();
        
        while(app.hasNext()){
            Dataset ds = app.next();
            listDS.add(ds);
        }
        
    if(ob instanceof Developer  ){
        Developer db = (Developer)ob ;
        fullName = db.getFullName();
    }
    
    if(ob instanceof Publisher){
        Publisher db = (Publisher)ob ;
        Institution inst = db !=null ? db.getPubInstitution():null; 
        
        if(null!=inst){
            fullName = inst.getInstitutionTitle()!=null?inst.getInstitutionTitle():"---";
            if(inst.getInstitutionLogo()!=null) {
                instlogo = SWBPortal.getWebWorkPath()+inst.getWorkPath()+"/"+Institution.lodp_institutionLogo.getName()+"_"+inst.getId()+"_"+inst.getInstitutionLogo();
            }
        }
    }
    
    if(ob instanceof User){
        User db = (User)ob ;
        fullName = db.getFullName();
    }   
    
// se actualiza los views
    
    if(!isFromReview){
        aps.sendView(request, user, wpage);
    }
    %>
    
    <h3><%=aps.getAppTitle()%></h3>
    
    <div class="izq-apps2">
        
        <div class="publicador">
              <img src="<%=instlogo%>" alt="<%=fullName%>" />
              <p class="pub-name"><%=fullName%></p>
        </div>
        
        <div class="detalle">
       	  <p><%=aps.getAppDescription()%></p>
          
          <%if(user.isSigned()&& (user.getSemanticObject().createGenericInstance() instanceof Developer || user.getSemanticObject().createGenericInstance() instanceof Publisher)){%>
          
          <p><em><a href="<%=renderURL.setMode(SWBResourceURL.Mode_EDIT).setParameter("uri", aps.getShortURI())%>" ><%=paramRequest.getLocaleString("lbl_editar")%></a></em></p>
          
          <%}%>
          
          <p><em><a href="mapas.opendata.gob.mx" class="api">Documentación del API</a></em></p>
          
          <%
          
          Dataset ds1 = listDS.get(0);
          String urlData = wsite.getWebPage("Datos").getUrl();
          String urlDataSet = urlData+"?suri="+ds1.getShortURI()+"&act=detail";
          
          %>
          
          <p><em><%=paramRequest.getLocaleString("lbl_DSUsado")%><a href="<%=urlDataSet%>"><%=ds1.getDatasetTitle()%></a></em></p>
          <p><em><%=paramRequest.getLocaleString("lbl_licenciaDetalle")%><%=aps.getAppLicense() != null ? aps.getAppLicense().getLicenseTitle() : ""%></em></p>
          
          <%if(isFromReview){
              
            SWBResourceURL urlreview = paramRequest.getActionUrl();
            urlreview.setAction(ApplicationResource.ADMIN_COMMENT);
            
          %>
            <fieldset>  
            
                <form id="seccCommentAdmin" action="<%=urlreview.toString()%>" method="post" >

                  <input type="hidden" name="appUri" value="<%=aps.getShortURI()%>" /> 
                  <label for="desc"><b>*</b><%=paramRequest.getLocaleString("lbl_CommentarioAdminAPP")%></label>
                  <textarea name="commentAPPAdmin" id="commentAPPAdmin" promptMessage="<%=paramRequest.getLocaleString("lbl_promtTextArea")%>" invalidMessage="<%=paramRequest.getLocaleString("lbl_invalidMsjTA")%>" trim="true" ></textarea>
                  <br/><br/>
                  <input type="submit" name="btnApprove" value="<%=paramRequest.getLocaleString("lbl_aprobarAPP")%>"/>
                  <input type="submit" name="btnReject" value="<%=paramRequest.getLocaleString("lbl_rechazarAPP")%>"/>

                </form>
                  
            </fieldset>
          <%}%>
          
        </div>
	</div>
          
        <div class="der-apps2">
            <div class="detalle-file">
		<a href="<%=renderURL.setMode(ApplicationResource.REDIRECT_URL).setParameter("uri", aps.getShortURI())%>" title="<%=aps.getAppURL()%>" class="descargar"><%=paramRequest.getLocaleString("lbl_descargarAPP")%></a>
            <ul>
                <li><strong><%=paramRequest.getLocaleString("lbl_fechaCracionDetalle")%></strong><%=sdf2.format(aps.getAppCreated())%></li>
                <li><strong><%=paramRequest.getLocaleString("lbl_visitasDetalle")%></strong><%=aps.getViews()%></li>
                <li><strong><%=paramRequest.getLocaleString("lbl_appDownloads")%></strong><%=aps.getDownloads()%></li>
                
                <%Long comment = SWBUtils.Collections.sizeOf(aps.listComments());%>
            
                <li><strong><%=paramRequest.getLocaleString("lbl_numComment")%></strong><%=comment%></li>
                <li><strong><%=paramRequest.getLocaleString("lbl_rated")%>:</strong>
           
                <%float average = Math.round(aps.getAverage());%>
                    
                <img src="/work/models/LODP/css/images/star-<%=average >= 1?"on":"off"%>.png" width="15" height="14" alt="*">
                <img src="/work/models/LODP/css/images/star-<%=average >= 2?"on":"off"%>.png" width="15" height="14" alt="*">
                <img src="/work/models/LODP/css/images/star-<%=average >= 3?"on":"off"%>.png" width="15" height="14" alt="*">
                <img src="/work/models/LODP/css/images/star-<%=average >= 4?"on":"off"%>.png" width="15" height="14" alt="*">
                <img src="/work/models/LODP/css/images/star-<%=average >= 5?"on":"off"%>.png" width="15" height="14" alt="*">
    
                </li>
            </ul>      
       </div>
       
	  <div class="new-dat tit">
            <h4><%=paramRequest.getLocaleString("lbl_DSRelacion")%></h4>
            <%  // lista de aplicaciones relacionadas
            int numDS = 0;
            
            if(!listDS.isEmpty()){
                 numDS = listDS.size();
                for(Dataset dsOBJ : listDS){
            %>
            
            <li><%=dsOBJ.getDatasetTitle()%><br />
                <strong><%=dsOBJ.getDatasetDescription()!=null?dsOBJ.getDatasetDescription():"---"%></strong>
            </li>
            
            <%}} else {%>
            
            <li><%=paramRequest.getLocaleString("lbl_NOrelatesApps")%></li>
            
            <%
            }
            String idWds = base.getAttribute("dsWebPage","Datos");
            WebPage wpds = wsite.getWebPage(idWds);
            String urlds = "#";
            
            if(wpds!=null){
                urlds = wpds.getUrl();
            }
            %>
        </ul>
                        
            <div>
                <span><%=numDS%> <%=paramRequest.getLocaleString("lbl_dsRelated")%></span> <a href="<%=urlds%>"><%=paramRequest.getLocaleString("lbl_viewAll")%></a>
            </div>
        </div>
            
	  <div class="new-app tit">
            <h4><%=paramRequest.getLocaleString("lbl_appRelacion")%></h4>
            <%  // lista de aplicaciones relacionadas
            int numapps = 0;
            List<Application> listApp = new ArrayList();
            
                for(Dataset dsOBJ : listDS){
                    Iterator<Application> itapp = Application.ClassMgr.listApplicationByRelatedDataset(dsOBJ, wsite);
                        while(itapp.hasNext()){
                            Application appli = itapp.next();
                            if(appli.isAppValid()){
                                listApp.add(appli);
                            }
                        }
                }
                
            numapps = listApp.size();
            
            if(!listApp.isEmpty()){
                for(Application appli : listApp){
               
            %>
            
            <li><%=appli.getAppTitle()%><br />
                <strong><%=appli.getAppDescription()!=null?appli.getAppDescription():"---"%></strong>
            </li>
            
            <%}}else {%>
            
            <li><%=paramRequest.getLocaleString("lbl_NOrelatesApps")%></li>
            
            <%}
            
            String idWPApps = base.getAttribute("appWebPage","Aplicaciones"); 
            WebPage wpApps = wsite.getWebPage(idWPApps); 
            String urlApps = "#"; 
            
            if(wpApps!=null){
                urlApps = wpApps.getUrl();
            }
                %>
        </ul>
                        
            <div>
                <span><%=numapps%> <%=paramRequest.getLocaleString("lbl_appRelated")%></span> <a href="<%=urlApps%>"><%=paramRequest.getLocaleString("lbl_viewAll")%></a>
            </div>
        </div>
      </div>
    
    

<%
}}}   
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

