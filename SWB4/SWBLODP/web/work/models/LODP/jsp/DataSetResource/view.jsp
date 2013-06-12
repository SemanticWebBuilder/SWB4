<%--
    Document   : view Recurso DataSetResource
    Created on : 03/05/2013
    Author     : juan.fernandez
--%>

<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="com.infotec.lodp.swb.Developer"%>
<%@page import="com.infotec.lodp.swb.Publisher"%>
<%@page import="java.util.TreeSet"%>
<%@page import="com.infotec.lodp.swb.DatasetLog"%>
<%@page import="com.infotec.lodp.swb.DatasetVersion"%>
<%@page import="java.util.Date"%>
<%@page import="com.infotec.lodp.swb.Tag"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="com.infotec.lodp.swb.utils.LODPUtils"%>
<%@page import="com.infotec.lodp.swb.Application"%>
<%@page import="com.infotec.lodp.swb.Sector"%>
<%@page import="com.infotec.lodp.swb.Topic"%>
<%@page import="com.infotec.lodp.swb.Institution"%>
<%@page import="org.semanticwb.model.Ontology"%>
<%@page import="com.infotec.lodp.swb.resources.DataSetResource"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURLImp"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="org.semanticwb.model.VersionInfo"%>
<%@page import="org.semanticwb.model.Versionable"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.Set"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.Role"%>
<%@page import="org.semanticwb.model.Resource"%> 
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.SWBUtils"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />

<%

    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    User usr = paramRequest.getUser();
    Resource base = paramRequest.getResourceBase();
    String roladmin = base.getAttribute("rolid", "");
    Role role = wsite.getUserRepository().getRole(roladmin);

    String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";

    int luser = 1;

    long intSize = 0;

    String strNumItems = base.getAttribute("numpag", "10"); // número de elementos por página
    String npage = request.getParameter("page"); // página a mostrar
    String orderby = request.getParameter("order"); // tipo de ordenamiento
    String filtertopic= request.getParameter("filtertopic"); // uri del tema
    String filteruri = request.getParameter("filteruri");  // uri de la institucion
    String filtertopicshort= filtertopic; // uri del tema
    String filterurishort = filteruri;  // uri de la institucion
    if(null!=filtertopic){
        filtertopic = SemanticObject.shortToFullURI(filtertopic);
    }
    if(null!=filteruri){
        filteruri = SemanticObject.shortToFullURI(filteruri);
    }
    
    
    
    
    String direction = request.getParameter("direction");
    String action = request.getParameter("act"); // accion

    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject so = null;
    GenericObject go = null;
    GenericObject got = null;

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
    if (action == null) {
        action = "";
    }

    //12 Jun 2013, 11:35
    //SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, h:mm a", new Locale("es"));
    //12 jun 2013
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es"));
    //12/junio/2013
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MMMMM/yyyy", new Locale("es"));
    if (request.getParameter("alertmsg") != null) {
        String strMsg = request.getParameter("alertmsg");
        strMsg = strMsg.replace("<br>", "\\n\\r");
%>
<script type="text/javascript">
    alert('<%=strMsg%>');
</script>
<%
    }

        if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {
            // llamada como estrategia
        } else {
            // llamado como contenido
            if (action.equals("")) {
                String txtTitleSearch = paramRequest.getLocaleString("lbl_searchDS1"); // Búsqueda de Datasets

                Date startSearch = new Date(System.currentTimeMillis());

                // ordenamiento orderby y filtrado de DataSets por institución
                // revisar si viene filteruri y filteruritopic
                Iterator<Dataset> itds1 = null;
                
                 if (null != filteruri && filteruri.trim().length() > 0 && null != filtertopic && filtertopic.trim().length() > 0)  { // por tema y por institucion
                     
                     //System.out.println("Filtro por tema y por Institucion...");
                     //System.out.println("Institucion..."+filteruri);
                     //System.out.println("Tema..."+filtertopic); 
                     
                     go = ont.getGenericObject(filteruri);
                     got = ont.getGenericObject(filtertopic);
                      itds1 = Dataset.ClassMgr.listDatasetByInstitution((Institution) go,wsite); 
                      HashMap<String,Dataset> set = new HashMap<String,Dataset>(); 
                      while(itds1.hasNext()){  // revisando si el dtaset tiene el tema asociado.
                          Dataset dsfiltered = itds1.next();
                          //System.out.println(dsfiltered.getInstitution().getInstitutionTitle()); 
                          if(null!=got&&dsfiltered.hasTopic((Topic)got)){ 
                              //System.out.println("Se agregó dataset...");
                              set.put(dsfiltered.getShortURI(),dsfiltered);
                          }
                      }
                      itds1 = set.values().iterator(); // pasando el resultado de los dos filtros al iterador
                 } else  if (null != filteruri && filteruri.trim().length() > 0 && null == filtertopic)  {  // por institucion
                     //System.out.println("Filtro Institucion...");
                     go = ont.getGenericObject(filteruri);
                      itds1 = Dataset.ClassMgr.listDatasetByInstitution((Institution) go,wsite); 
                 } else if (null == filteruri && null != filtertopic && filtertopic.trim().length() > 0)  {  // por tema
                     //System.out.println("Filtro por tema...");
                     got = ont.getGenericObject(filtertopic);
                     itds1 = Dataset.ClassMgr.listDatasetByTopic((Topic) got,wsite);
                 } else { // sin filtro
                     //System.out.println("Sin filtro...");
                     itds1 = Dataset.ClassMgr.listDatasets(wsite);
                 }

                // obteniendo Datasets que coincidan con el texto a buscar
                String queryinput = request.getParameter("search");
                String queryOriginal = queryinput != null ? queryinput : paramRequest.getLocaleString("btn_search");//"Search"
                if (null == queryinput) {
                    queryinput = "";
                } else {
                    queryinput = queryinput.trim().toLowerCase();
                    txtTitleSearch = paramRequest.getLocaleString("lbl_searchDS2"); //Resultado de la búsqueda
                }

                // Se guardan ds que coinciden con la búsqueda
                HashMap<String, Dataset> hmresults = new HashMap<String, Dataset>();

                // revisar DS si hay texto abuscar
                if (queryinput != null && queryinput.trim().length() > 0) {
                    //System.out.println("Texto recibido...."+queryinput);
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
                        for(int j = 0 ; j < arrKeys.length; j++) 
                         { 
                            String s = arrKeys[j];
                            //System.out.println("word by word:" + s); //muestra cada palabra encontrada en queryinput separada por espacios
                            if (s != null && s.trim().length() > 0) {
                                if (hmquery.get(s) == null) {
                                    hmquery.put(s, s);
                                }
                            }
                        }
                    }

                    if (itds1.hasNext()) {
                        while (itds1.hasNext()) {
                            // filtrar DS deacuerdo al texto recibido
                            // separar palabras ya sea por "," o por espacios y si coincide por lo menos una guardar ese dataset. 
                            Dataset ds = itds1.next();
                            StringBuilder txtDS = new StringBuilder(ds.getDatasetTitle() != null ? ds.getDatasetTitle().trim() : "");
                            txtDS.append(" ");
                            txtDS.append(ds.getDatasetDescription() != null ? ds.getDatasetDescription().trim() : "");
                            txtDS.append(" ");
                            txtDS.append(LODPUtils.getDSTagList(ds,","));
                            String reviewTXT = txtDS.toString().trim().toLowerCase(); // texto completo en donde se buscará la ocurrencia
                            //System.out.println("Texto DS: "+reviewTXT);
                            if ((DataSetResource.reviewQuery(hmquery, reviewTXT)) && hmresults.get(ds.getURI()) == null) {  //||txtAuto.indexOf(queryinput)>-1 
                                hmresults.put(ds.getURI(), ds);
                            }
                        }
                        itds1 = hmresults.values().iterator();  // pone en el iterador los DS obtenidos
                    }
                }

                // dejo en hm los ds
                HashMap<String, Dataset> hmcp = new HashMap<String, Dataset>();
                while(itds1.hasNext()){
                    Dataset ds = itds1.next();
                    if(ds.isDatasetActive()&&ds.isApproved()){
                        hmcp.put(ds.getURI(), ds);
                    }
                }
                Iterator<Dataset> itds = null;
               // System.out.println("size: "+hmcp.size());
                if(hmcp.size()>0){
                    itds = DataSetResource.orderDS(hmcp.values().iterator(), orderby);
                    intSize=hmcp.size();
                } else {
                //itds = DataSetResource.orderDS(hmcp.values().iterator(), orderby);
                intSize = 0;
                }

                Date endSearch = new Date(System.currentTimeMillis());
                long searchTime = endSearch.getTime() - startSearch.getTime();
                long searchTimeSecs = searchTime / 1000;
    %>
<div class="buscar_ds">
    <form method="post" action="" id="ds_search">
        <%
            if (filtertopic != null && filtertopic.trim().length() > 0) {
        %>
        <input type="hidden" name="filtertopic" value="<%=filtertopic%>"/>
        <%
            }
        %>
        <%
            if (filteruri != null && filteruri.trim().length() > 0) {
        %>
        <input type="hidden" name="filteruri" value="<%=filteruri%>"/>
        <%
            }
        %>

                <label for="txt_search"><%=txtTitleSearch%></label><input type="text" name="search" value="<%=queryOriginal%>" onfocus="if (this.value == 'Search'  || this.value == 'Buscar') {this.value = ''};"><button type="submit"><%=paramRequest.getLocaleString("btn_search")%></button>
                <%
                    // si hubo búsqueda
                    if (null != queryinput && queryinput.trim().length() > 0) {
                        if (intSize > 0) {
                %>
                <br/>
                <%=paramRequest.getLocaleString("btn_almost")%> <%=intSize%> <%=paramRequest.getLocaleString("btn_results")%>
                <%
                } else {
                %>
                <br/>
                <%=paramRequest.getLocaleString("msg_noresults")%>
                <%                            }
                    // tiempo en segundos de lo que se tardó la búsqueda
                %>
                (<%=searchTimeSecs%> <%=paramRequest.getLocaleString("msg_secs")%>)
                <%
                    }
                %>        

    </form>
</div>
<div class="izq-data1">
    <!--
    <div class="izq_sector">
        <ul>
            <li><h3><%//=paramRequest.getLocaleString("lbl_sectorFilter")%></h3></li>
                    <%/*                        Iterator<Sector> itsec = Sector.ClassMgr.listSectors(wsite);
                        while (itsec.hasNext()) {
                            Sector sec = itsec.next();

                            SWBResourceURL url = paramRequest.getRenderUrl();
                            url.setParameter("filteruri", sec.getURI());
                            if (null != orderby) {
                                url.setParameter("order", orderby);
                            }
                            if (queryinput != null && queryinput.trim().length() > 0) {
                                url.setParameter("search", queryinput);
                            }
*/
                    %>
            <li><a href="<%//=url.toString()%>" title="<%//=sec.getSectorDescription() != null ? sec.getSectorDescription().trim() : sec.getSectorTitle()%>"><%//=sec.getSectorTitle()%></a></li>  
                <%
                  //  }
                %>
        </ul>   
    </div>
        -->
        <p class="rubro"><%=paramRequest.getLocaleString("lbl_institFilter")%></p>
        <ul>
                <%
                if(null!=filteruri&&filteruri.trim().length()>0){
                    go = ont.getGenericObject(filteruri);
                    Institution inst = (Institution)go;
                        %>
                <li><a href="#" class="tema-inst"><%=inst.getInstitutionTitle()%></a></li>
                <%
                    SWBResourceURL url = paramRequest.getRenderUrl();
                        if(null!=filtertopic && filtertopic.trim().length()>0) {
                            url.setParameter("filtertopic", filtertopicshort); 
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
                    Iterator<Institution> itins = Institution.ClassMgr.listInstitutions(wsite);
                    while (itins.hasNext()) {
                        Institution inst = itins.next();

                        SWBResourceURL url = paramRequest.getRenderUrl();
                        url.setParameter("filteruri", inst.getShortURI());
                        if (null != orderby) {
                            url.setParameter("order", orderby);
                        }
                         if (null != filtertopic) {
                            url.setParameter("filtertopic", filtertopicshort); 
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

        <p class="rubro"><%=paramRequest.getLocaleString("lbl_topicFilter")%></p>
        <ul>
                <%
                if(null!=filtertopic&&filtertopic.trim().length()>0){
                    go = ont.getGenericObject(filtertopic);
                    Topic topic = (Topic)go;
                    String nomclass = topic.getTopicTitle();
                        if(null==nomclass) {
                            nomclass="default";
                         } else {
                            nomclass=nomclass.trim();
                            if(nomclass.trim().length()>5){
                                nomclass=nomclass.trim().substring(0, 5);
                            } 
                        }
                        nomclass = SWBUtils.TEXT.replaceSpecialCharacters(nomclass,true);
                        %>
                <li><a class="tema-<%=nomclass%>" href="#"><%=topic.getTopicTitle()%></a></li>
                <%
                    SWBResourceURL url = paramRequest.getRenderUrl();
                        if(null!=filtertopic && filtertopic.trim().length()>0) {
                            url.setParameter("filteruri", filterurishort); 
                        }
                        if (null != orderby) {
                            url.setParameter("order", orderby);
                        }
                        if (queryinput != null && queryinput.trim().length() > 0) {
                            url.setParameter("search", queryinput);
                        }                    
                    %>
                <li><a class="tema-todos" href="<%=url.toString()%>"><%=paramRequest.getLocaleString("lbl_all")%></a></li>  
                <%
                } else {
                    Iterator<Topic> ittop = Topic.ClassMgr.listTopics(wsite);
                    while (ittop.hasNext()) {
                        Topic inst = ittop.next();
                        SWBResourceURL url = paramRequest.getRenderUrl();
                        url.setParameter("filtertopic", inst.getShortURI());
                        if (null != orderby) {
                            url.setParameter("order", orderby);
                        }
                        if(null!=filteruri){
                            url.setParameter("filteruri", filterurishort); 
                        }
                        if (queryinput != null && queryinput.trim().length() > 0) { 
                            url.setParameter("search", queryinput);
                        }
                        String nomclass = inst.getTopicTitle();
                        if(null==nomclass) {
                            nomclass="default";
                         } else {
                            nomclass=nomclass.trim();
                            if(nomclass.trim().length()>5){
                                nomclass=nomclass.trim().substring(0, 5);
                            } 
                        }
                        nomclass = SWBUtils.TEXT.replaceSpecialCharacters(nomclass,true);
                %>
            <li><a class="tema-<%=nomclass%>" href="<%=url.toString()%>" title="<%=inst.getTopicDescription() != null ? inst.getTopicDescription().trim() : inst.getTopicTitle()%>"><%=inst.getTopicTitle()%></a></li> 
                <%
                    }
            }
                %>
        </ul>  
</div>
<div class="der-data1">
    <div id="temasdatos">
         <div class="listado">
    <div class="orden">
        <%
            SWBResourceURL urlorder = paramRequest.getRenderUrl();
            urlorder.setParameter("act", "");
            if (null != filteruri) {
                urlorder.setParameter("filteruri", filterurishort);
            }
            if (null != filtertopic) {
                urlorder.setParameter("filtertopic", filtertopicshort);
            }
            if (queryinput != null && queryinput.trim().length() > 0) {
                urlorder.setParameter("search", queryinput);
            }

            String ckdCreated = "", ckdView = "", ckdDownload = "", ckdRank = "";
            if (null != orderby) {
                //urlorder.setParameter("order", orderby);
                if (orderby.equals(DataSetResource.ORDER_CREATED)) {
                    ckdCreated = "class=\"selected\""; 
                } else if (orderby.equals(DataSetResource.ORDER_VIEW)) {
                    ckdView = "class=\"selected\""; 
                } else if (orderby.equals(DataSetResource.ORDER_DOWNLOAD)) {
                    ckdDownload = "class=\"selected\""; 
                } else if (orderby.equals(DataSetResource.ORDER_RANK)) {
                    ckdRank = "class=\"selected\""; 
                }
            }

        %>
            <a href="<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_CREATED%>" <%=ckdCreated%>><%=paramRequest.getLocaleString("lbl_byrecent")%></a> 
            <a <%=ckdView%> href="<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_VIEW%>"><%=paramRequest.getLocaleString("lbl_byvisited")%></a> 
            <a <%=ckdDownload%> href="<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_DOWNLOAD%>"><%=paramRequest.getLocaleString("lbl_bydownload")%></a>
            <a <%=ckdRank%> href="<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_RANK%>"><%=paramRequest.getLocaleString("lbl_byvaluated")%></a>
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
            <li><%=paramRequest.getLocaleString("lbl_notDSfound")%></li>
                    <%                                } else {

                    String wpurl = wpage.getUrl()+"?act=detail&suri=";
                        while (itds.hasNext()) {

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


                            Dataset ds = itds.next();
                           // SWBResourceURL urldet = paramRequest.getRenderUrl();
                           // urldet.setParameter("act", "detail");
                           // urldet.setParameter("suri", ds.getURI());
                            String icontype = "default";
                            if (ds.getDatasetFormat() != null&&ds.getDatasetFormat().trim().length()>0) {
                                icontype = ds.getDatasetFormat();
                            }
                            
                    %>
            <li><a class="ico-<%=icontype%>" href="<%=wpurl+ ds.getShortURI()%>"><%=ds.getDatasetTitle()%></a><br/>
                <p><em><%=sdf.format(ds.getDatasetUpdated())%> - <%=ds.getInstitution().getInstitutionTitle()%></em></p>
                <p><%=ds.getDatasetDescription()%></p>
            </li>
            <%
                    }
                }
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
                    if (null != filteruri) {
                        urlNext.setParameter("filteruri", filterurishort);
                    }
                    if (null != filtertopic) {
                        urlNext.setParameter("filtertopic", filtertopicshort);
                    }
                    if (queryinput != null && queryinput.trim().length() > 0) {
                        urlNext.setParameter("search", queryinput);
                    }

                    out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">&lt; "+paramRequest.getLocaleString("pg_gofirst")+"</a> ");
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
                    if (null != filteruri) {
                        urlNext.setParameter("filteruri", filterurishort);
                    }
                    if (null != filtertopic) {
                        urlNext.setParameter("filtertopic", filtertopicshort);
                    }
                    if (queryinput != null && queryinput.trim().length() > 0) {
                        urlNext.setParameter("search", queryinput);
                    }
                    if (z != p) {
                        out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">" + (z + 1) + "</a> ");
                    } else {
                        out.println("<span>"+(z + 1) + "</span> ");
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
                    if (null != filteruri) {
                        urlNext.setParameter("filteruri", filterurishort);
                    }
                    if (null != filtertopic) {
                        urlNext.setParameter("filtertopic", filtertopicshort);
                    }
                    if (queryinput != null && queryinput.trim().length() > 0) {
                        urlNext.setParameter("search", queryinput);
                    }
                    out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">"+paramRequest.getLocaleString("pg_golast")+" &gt;</a> ");
                }
%>
 </div>
</div>
<%
            }
        %>
    </div>
    </div>
</div>
    <%
    } else if (action.equals("detail")) {  // detalle del Dataset seleccionado

        //System.out.println("Detalle DS.........");
        String suri = request.getParameter("suri");
        //System.out.println("URI........."+suri);
        String statswpid = base.getAttribute("statswebpage","EstadisticasEnTabla");
                WebPage wpurl = wsite.getWebPage(statswpid); 
                String statsurl = "#";
                if(null!=wpurl) {
                    statsurl = wpurl.getUrl();
                }
        go = ont.getGenericObject(SemanticObject.shortToFullURI(suri));
        if (go instanceof Dataset) {
    %>
<div class="cuerpo">
    <%
        Dataset ds = (Dataset) go;
        //ds.getDatasetDescription()
        ds.sendView(request, usr, wpage);
        //boolean bupdated = ds.incViews(); //LODPUtils.updateDSViews(ds);  // se actualiza los views
        Publisher pub = ds.getPublisher();
        String pubname = pub!=null?pub.getFullName():"--";
        if(null==pubname) pubname="---";
        Institution inst = pub !=null ? pub.getPubInstitution():null; 
        String instname = "";
        String instweburl = "";
        String instlogo = "images/imagen.gif";
        if(null!=inst){
            instname = inst.getInstitutionTitle()!=null?inst.getInstitutionTitle():"---";
            instweburl = inst.getInstitutionHome()!=null?inst.getInstitutionHome():"---"; 
            if(inst.getInstitutionLogo()!=null) {
                instlogo = SWBPortal.getWebWorkPath()+inst.getWorkPath()+"/"+Institution.lodp_institutionLogo.getName()+"_"+inst.getId()+"_"+inst.getInstitutionLogo();
            }
    }
        
%>
    
        <h3><%=ds.getDatasetTitle()%></h3> 
        <div class="izq-data2">
            <div class="publicador">
                <img src="<%=instlogo%>" alt="<%=instname%>" />
            <p class="pub-name"><%=instname%></p>
            <p class="pub-www"><a href="<%=instweburl%>" class="pub-www" title="<%=instweburl%>">Ir al sitio web</a></p>
          <p class="pub-mail"><a href="mailto:<%=pub!=null&&pub.getEmail()!=null?pub.getEmail():"---"%>" class="pub-mail" title="<%=pub!=null&&pub.getEmail()!=null?pub.getEmail():"---"%>"><%=paramRequest.getLocaleString("lbl_technicalContact")%> <br />
          <em><%=pub!=null&&pub.getFullName()!=null?pub.getFullName():"---"%></em></a></p>
        </div>
        <div class="detalle">
            <p><%=ds.getDatasetDescription()%></p>
            <p><em title="<%=ds.getLicense() != null ? ds.getLicense().getLicenseDescription(): ds.getLicense().getLicenseTitle()%>"><%=paramRequest.getLocaleString("lbl_licenseUse")%>: <%=ds.getLicense() != null ? ds.getLicense().getLicenseTitle() : "No asignada"%></em></p>
            <fieldset>
          	<legend>Etiquetas</legend>
            	<ul>
                    <%
                    if(null!=ds && ds.listTags().hasNext()){
            Iterator<Tag> ittag = ds.listTags();
            while (ittag.hasNext()) {
                Tag tag = ittag.next();
                %>
                <li><%=tag.getTagName().trim()%></li>
                <%
            }
        }
                    %>
                </ul>
          </fieldset>

        </div> <!-- detalle -->
        </div> <!-- izq -->
        
        <div class="der-data2">
            <div class="detalle-file">
                <%
                SWBResourceURL urldown = paramRequest.getRenderUrl();
                urldown.setCallMethod(SWBResourceURL.Call_DIRECT);
                urldown.setParameter("suri",ds.getShortURI());
                urldown.setParameter("act","file");
                urldown.setMode(DataSetResource.MODE_FILE);
                //ds.getDatasetFormat()
                %>
                <a href="<%=urldown.toString()%>" title="<%=urldown.toString()%>" class="descargar"><%=paramRequest.getLocaleString("lbl_download")%></a>
                
                <a class="ico-<%=ds.getDatasetFormat()%>" href="#"><%=ds.getActualVersion()!=null&&ds.getActualVersion().getFilePath()!=null?ds.getActualVersion().getFilePath():"No tiene archivo asignado"%></a>
                
        <ul>
            <li>
                <strong><%=paramRequest.getLocaleString("lbl_version")%>:</strong><%=ds.getActualVersion()!=null?ds.getActualVersion().getVersion():"---"%>
            </li>
            <li>
                <strong><%=paramRequest.getLocaleString("lbl_created")%>:</strong><%=sdf.format(ds.getDatasetCreated())%>
            </li>
            <li>
                <strong><%=paramRequest.getLocaleString("lbl_updated")%>:</strong><%=sdf.format(ds.getDatasetUpdated())%>
                <a href="#" class="vermas expand-one" title="Ver todas las actualizaciones" ><span>ver mas</span></a>
            </li>
            <li><div id="ver-versiones" class="content-one">
                    <table summary="Historial de Actualizaciones">
                        <thead>
                            <tr>
                                <th class="thversion"><%=paramRequest.getLocaleString("lbl_version")%></th>
                                <th class="thfecha"><%=paramRequest.getLocaleString("lbl_date")%></th>
                            </tr>
                        </thead>
            <%
                DatasetVersion dslv = ds.getActualVersion();
                DatasetVersion ver = null;

                //para obtener la primera version
                if (null != dslv) {
                    ver = dslv;
                    while (ver.getPreviousVersion() != null) { //
                        ver = ver.getPreviousVersion();
                    }
                }


            %>
                        <tbody>
                             <%
                            while (ver != null) {
                                int vernum = ver.getVersion();
                              //System.out.println(vernum);
                              if(vernum>0){
                                Date verdate = ver.getVersionCreated();
                        %>
                        <tr>
                            <td><%=vernum%></td>
                            <td><%=null!=verdate?sdf2.format(verdate):"---"%></td>
                        </tr>
                        <%
                              }
                                ver = ver.getNextVersion();
                             
                            }
                        %>
                        </tbody>                
                    </table>
                </div></li>
            
            <li><strong><%=paramRequest.getLocaleString("lbl_views")%>:</strong> <%=ds.getViews()%></li>
                <li><strong><%=paramRequest.getLocaleString("lbl_downloads")%>:</strong> <%=ds.getDownloads()%></li>
                <li><strong><%=paramRequest.getLocaleString("lbl_rated")%>:</strong> <%//=ds.getAverage()%>
                    <%
                        float average = Math.round(ds.getAverage());
                    
                    %>
                    
                    <!-- div id="ranking" -->
       <img src="/work/models/LODP/css/images/star-<%=average >= 1?"on":"off"%>.png" width="15" height="14" alt="*">
        <img src="/work/models/LODP/css/images/star-<%=average >= 2?"on":"off"%>.png" width="15" height="14" alt="*">
        <img src="/work/models/LODP/css/images/star-<%=average >= 3?"on":"off"%>.png" width="15" height="14" alt="*">
        <img src="/work/models/LODP/css/images/star-<%=average >= 4?"on":"off"%>.png" width="15" height="14" alt="*">
        <img src="/work/models/LODP/css/images/star-<%=average >= 5?"on":"off"%>.png" width="15" height="14" alt="*">
    <!-- /div -->
                </li>

            </ul>

                <%
                SWBResourceURL url = paramRequest.getRenderUrl();
                url.setCallMethod(SWBResourceURL.Call_DIRECT);
                url.setParameter("suri",ds.getShortURI());
                url.setParameter("act","meta");
                url.setMode(DataSetResource.MODE_FILE);

                %>
                
        <a href="<%=statsurl%>" title="<%=paramRequest.getLocaleString("lbl_statsDS")%>" class="ver-estad"><span><%=paramRequest.getLocaleString("lbl_statsDS")%></span></a> 
        <a href="<%=url.toString()%>&mformat=<%=DataSetResource.META_FORMAT_JSON%>" title="<%=paramRequest.getLocaleString("lbl_exportDSmeta")%> JSON" class="ver-exp-json"><span><%=paramRequest.getLocaleString("lbl_exportDSmeta")%> JSON</span></a> 
        <a href="<%=url.toString()%>&mformat=<%=DataSetResource.META_FORMAT_RDF%>" title="<%=paramRequest.getLocaleString("lbl_exportDSmeta")%> RDF" class="ver-exp-rdf"><span><%=paramRequest.getLocaleString("lbl_exportDSmeta")%> RDF</span></a>     
            
            </div>  
            
                <div class="new-app tit">
		<h4><%=paramRequest.getLocaleString("lbl_relatesApps")%></h4>
            <%  // lista de aplicaciones relacionadas
            int numapps = 0;
            Iterator<Application> itapp = Application.ClassMgr.listApplicationByRelatedDataset(ds, wsite);
            HashMap<String, Application> hmappli = new HashMap<String, Application>();
            if(itapp.hasNext()){
                while(itapp.hasNext()){
                    Application appli = itapp.next();
                    if(appli.isAppValid()){
                        hmappli.put(appli.getShortURI(), appli);
                    }
                }
            numapps = hmappli.size();
            itapp = hmappli.values().iterator();
            if(itapp.hasNext()){
                while(itapp.hasNext()){
                    Application appli = itapp.next(); 
            %>
            <li><%=appli.getAppTitle()%><br />
                <strong><%=appli.getAppDescription()!=null?appli.getAppDescription():"---"%></strong>
            </li>
            <%
                }
            } else {

            %>
            <li><%=paramRequest.getLocaleString("lbl_NOrelatesApps")%></li>
                <%
            }
            }
            String idWPapps = base.getAttribute("appwebpage","Aplicaciones");
            WebPage wpapps = wsite.getWebPage(idWPapps);
            String urlapps = "#";
            
            if(wpapps!=null&&ds.getInstitution()!=null){
                urlapps = wpapps.getUrl()+"?filteruri="+ds.getInstitution().getShortURI()+"&order=date";
            }
                %>
        </ul>
                        
            <div>
                <span><%=numapps%> aplicaciones relacionadas</span> <a href="<%=urlapps%>">Ver todas</a>
            </div>
		</div>
        </div> <!-- der -->

<div class="clear">&nbsp;</div> 
    </div>   

<%
            }
        } else if("stats".equals(action)){
        
            //System.out.println("Stats DS.........");
        String suri = request.getParameter("suri");
        //System.out.println("URI........."+suri);
        go = ont.getGenericObject(SemanticObject.shortToFullURI(suri));
        if (go instanceof Dataset) {
            
            HashMap<String, TreeSet<DatasetLog>> hmlogs = new HashMap<String, TreeSet<DatasetLog>>();
             Dataset ds = (Dataset) go; 
             TreeSet<DatasetLog> tsv =null;
             Iterator<DatasetLog> itdslog  = DatasetLog.ClassMgr.listDatasetLogByDataset(ds, wsite);
             while(itdslog.hasNext()){
                 DatasetLog dslog = itdslog.next();
                    if(LODPUtils.Log_Type_View==dslog.getLogType()){
                     if(hmlogs.get("views")==null){
                         // nuevo treeset para logs downloads
                         tsv = new TreeSet<DatasetLog>();
                     }
                     tsv = hmlogs.get("views"); 
                     tsv.add(dslog);
                 }
             }
             
             Set<DatasetLog> sdv = DataSetResource.sortDSLogByCreated(tsv.iterator(), false);
             // informacion registrada en el log
             long numviews = sdv.size();
             Date dateldload = null;
             Date datelviews = null;
             DatasetLog dslogtmp = null; 
             // obteniendo la fecha de la ultima vez que se vio el dataset
             if(numviews>0){
                 if(sdv.iterator().hasNext()){
                     Iterator<DatasetLog> itdslogv = sdv.iterator();
                     if(itdslogv.hasNext()){
                         dslogtmp = itdslogv.next();
                         datelviews = dslogtmp.getLogCreated();
                     }
                 }
             }
             // obteniendo la fecha de la ultima vez que se descargó el dataset
             dateldload = ds.getLastDownload();

     %>
<div>
    <table>
        <thead>
            <tr>
                <th><%=paramRequest.getLocaleString("lbl_downloads")%></th>
                <th><%=paramRequest.getLocaleString("lbl_lastDownload")%></th>
                <th><%=paramRequest.getLocaleString("lbl_views")%></th>
                <th><%=paramRequest.getLocaleString("lbl_lastView")%></th>
                <th><%=paramRequest.getLocaleString("lbl_numcomments")%></th>
                <th><%=paramRequest.getLocaleString("lbl_numappss")%></th>
            </tr>
        </thead>
        <tbody>
            <%
            long numcomm= SWBUtils.Collections.sizeOf(ds.listComments());
            long numapps= SWBUtils.Collections.sizeOf(Application.ClassMgr.listApplicationByRelatedDataset(ds)); 
            SWBResourceURL urlst2 = paramRequest.getRenderUrl();
            urlst2.setParameter("suri", suri);
            urlst2.setParameter("act", "stats2");  //statType         
            %>
            <tr>
                <td><a href="<%=urlst2.toString()%>&statType=<%=LODPUtils.Log_Type_Download%>" title="ver lista de descargas"><%=ds.getDownloads()%></a></td>
                <td><%=dateldload!=null?sdf2.format(dateldload):"---"%></td>
                <td><a href="<%=urlst2.toString()%>&statType=<%=LODPUtils.Log_Type_View%>" title="ver lista de visitas"><%= ds.getDatasetView()%></a></td>
                <td><%=datelviews!=null?sdf2.format(datelviews):"---"%></td>
                <td><%=numcomm%></td>
                <td><%=numapps%></td>
            </tr>
        </tbody>
    </table>
</div>
    <%
        }
        } else if("stats2".equals(action)){
            //System.out.println("Stats DownLoad / Views.........");
         String suri = request.getParameter("suri");
        String statType = request.getParameter("statType");
        String tmpType1 = ""+LODPUtils.Log_Type_Download; 
        String tmpType2 = ""+LODPUtils.Log_Type_View; 
        boolean isViews = Boolean.FALSE;
        if(null!=statType&&tmpType1.equals(statType)){
            isViews = Boolean.FALSE;
        }
        if(null!=statType&&tmpType2.equals(statType)){
            isViews = Boolean.TRUE;
        }
        //System.out.println("URI........."+suri);
        go = ont.getGenericObject(SemanticObject.shortToFullURI(suri));
        if (go instanceof Dataset) {
            
            HashMap<String, TreeSet<DatasetLog>> hmlogs = new HashMap<String, TreeSet<DatasetLog>>();
             Dataset ds = (Dataset) go; 
             TreeSet<DatasetLog> tsd = null;
             TreeSet<DatasetLog> tsv =null;
             Iterator<DatasetLog> itdslog  = DatasetLog.ClassMgr.listDatasetLogByDataset(ds, wsite);
             while(itdslog.hasNext()){
                 DatasetLog dslog = itdslog.next();
                 if(LODPUtils.Log_Type_Download==dslog.getLogType()){
                     if(hmlogs.get("downloads")==null){
                         // nuevo treeset para logs downloads
                         tsd =  new TreeSet<DatasetLog>();
                     } 
                     tsd = hmlogs.get("downloads") ;
                     tsd.add(dslog);
                 } else if(LODPUtils.Log_Type_View==dslog.getLogType()){
                     if(hmlogs.get("views")==null){
                         // nuevo treeset para logs downloads
                         tsv = new TreeSet<DatasetLog>();
                     }
                     tsv = hmlogs.get("views"); 
                     tsv.add(dslog);
                 }
             }
             
             Set<DatasetLog> sdv = DataSetResource.sortDSLogByCreated(tsv.iterator(), false);
             Set<DatasetLog> sdd = DataSetResource.sortDSLogByCreated(tsd.iterator(), false);
             // informacion registrada en el log
             long numviews = sdv.size();
             long numdload = sdd.size();
             
             Date dateldload = null;
             Date datelviews = null;
             DatasetLog dslogtmp = null; 
             // obteniendo la fecha de la ultima vez que se vio el dataset
             if(numviews>0){
                 if(sdv.iterator().hasNext()){
                     Iterator<DatasetLog> itdslogv = sdv.iterator();
                     if(itdslogv.hasNext()){
                         dslogtmp = itdslogv.next();
                         datelviews = dslogtmp.getLogCreated();
                     }
                 }
             }
             // obteniendo la fecha de la ultima vez que se descargó el dataset
             if(numdload>0){ 
                 if(sdd.iterator().hasNext()){
                     Iterator<DatasetLog> itdslogd = sdd.iterator();
                     if(itdslogd.hasNext()){
                         dslogtmp = itdslogd.next();
                         dateldload = dslogtmp.getLogCreated(); 
                     }
                 }
             }
             
     %>
<div>
    <table>
        <thead>
            <tr>
                <%
                if(!isViews){  // Son descargas
                %>
                <th><%=paramRequest.getLocaleString("lbl_downloads")%></th>
                <th><%=paramRequest.getLocaleString("lbl_description")%></th>
                <th><%=paramRequest.getLocaleString("lbl_date")%></th>    
                <%
                } else {  // Son visitas
                %>
                <th><%=paramRequest.getLocaleString("lbl_views")%></th>
                <th><%=paramRequest.getLocaleString("lbl_description")%></th>
                <th><%=paramRequest.getLocaleString("lbl_date")%></th>                
                <% 
                }
                %>
            </tr>
        </thead>
        <tbody>
            <%
            Iterator<DatasetLog> itdsl = null;
                if(!isViews){
                    itdsl = sdd.iterator();
                } else {
                    itdsl = sdv.iterator();
                }
                //itdsl = DatasetLog.ClassMgr.listDatasetLogByDataset(ds); 
                GenericObject gol = null;
                User usrlog = null;
                String usrname = "";
                
            while(itdsl.hasNext()){
                DatasetLog dslog = itdsl.next();
                Date dcreated = dslog.getLogCreated();
                String txtDescrip = dslog.getLogDescription()!=null?dslog.getLogDescription().trim():"---";
                // preguntar como se desplegarán las estadísticas
                gol = null;
                usrlog = dslog.getLogUser();
                if(null!=usrlog&&usrlog.getSemanticObject()!=null){
                    gol = usrlog.getSemanticObject().createGenericInstance();
                }
                if(null!=gol){
                    if(gol instanceof Publisher){
                        Institution inst =  ((Publisher)gol).getPubInstitution();
                        if(null!=inst&&inst.getInstitutionTitle()!=null)
                        {
                            usrname = inst.getInstitutionTitle() + " / "+ ((Publisher)gol).getFullName();
                        } else {
                            usrname = ((Publisher)gol).getFullName();
                        }
                    } else if( gol instanceof Developer) { 
                        usrname = ((Publisher)gol).getFullName();
                        
                    } else {
                        if(usrlog.isRegistered()){
                            usrname = usrlog.getFullName();
                        } else {
                            usrname = "Anónimo";
                        }
                    } 
                }
                %>
            <tr>
                <td><%=usrname%></td><td><%=txtDescrip%></td><td><%=dcreated!=null?sdf2.format(dcreated):"---"%></td>
            </tr>
                <%
            }
            
            %>
        </tbody>
    </table>
</div>
    <%
        }
        }

    }
    // Termina llamado como contenido
%>

