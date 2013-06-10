<%--
    Document   : view Recurso DSPublisherResource
    Created on : 20/05/2013
    Author     : juan.fernandez
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="org.semanticwb.model.UserRepository"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="com.infotec.lodp.swb.Comment"%>
<%@page import="com.infotec.lodp.swb.resources.DSPublisherResource"%>
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
   // out.println("Clase a actualizar: "+DatasetVersion.lodpcg_DatasetVersion.getURI());
   // out.println("Propiedad a actualizar: "+DatasetVersion.lodp_filePath.getName());
    

    /*
    if(request.getParameter("createuser")!=null&&request.getParameter("createuser").equals("true")){
        UserRepository usrrep = wsite.getUserRepository();
        Publisher newpub = Publisher.ClassMgr.createPublisher(usrrep);
        newpub.setActive(true);
        newpub.setEmail("jafdeza@gmail.com");
        newpub.setFirstName("publicador");
        newpub.setLastName("sfp");
        newpub.setLogin("juan");
                }
    */



    Publisher pub = LODPUtils.getPublisher(usr);
    if (pub == null) {
        // no disponible la pagina para usuarios que no sean publicadores
        out.println("<h1>Se necesita ser PUBLICADOR....</h1>");
        return;
    }

    Institution inst = pub.getPubInstitution();


    Resource base = paramRequest.getResourceBase();
    String roladmin = base.getAttribute("rolid", "");
    Role role = wsite.getUserRepository().getRole(roladmin);

    String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";

    int luser = 1;
    long intSize = 0;

    String strNumItems = base.getAttribute("numpag", "10");
    String npage = request.getParameter("page");
    String orderby = request.getParameter("order");
    String filterby = request.getParameter("filter");
    String filteruri = request.getParameter("filteruri");
    String direction = request.getParameter("direction");
    String action = request.getParameter("act");
    String suri = request.getParameter("suri");

    if (filteruri == null && inst != null) {
        filteruri = inst.getURI();
    }


    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject so = null;
    GenericObject go = null;

    int numPages = 10;
    try {
        numPages = Integer.parseInt(strNumItems);
    } catch (Exception e) {
        numPages = 10;
    }

    if (orderby == null) {
        orderby = "date";
    }
    if (filterby == null) {
        filterby = "";
    }
    if (direction == null) {
        direction = "up";
    }
    if (action == null) {
        action = "";
    }

    //12 jun 2013
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", new Locale("es"));
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
                // panel de control
%>
<div class="institution_info">
    <%
    if(null!=inst){  
    %>
    <div><img src="<%=SWBPortal.getWorkPath() + inst.getWorkPath() + "/" +  inst.getInstitutionLogo()%>"/></div>
    <div>
        <label><%=inst.getInstitutionTitle()%></label><p><%=inst.getInstitutionDescription()%></p>
    </div>
    <%
    } else {
        %>
    <div>
        <label>No se encontró información de la institución</label>
    </div>
    <%
    }
    %>
</div>
<div class="panel">
    <fieldset><label>Mis datos</label>

        <ul>
            <%
                SWBResourceURL urlnew = paramRequest.getRenderUrl();
                urlnew.setParameter("act", SWBResourceURL.Action_ADD);
                urlnew.setMode(SWBResourceURL.Mode_EDIT);
            %>
            <li><a href="<%=urlnew.toString()%>">Nuevo Dataset</a></li>
                <%
                    SWBResourceURL urlmyds = paramRequest.getRenderUrl();
                    urlmyds.setParameter("act", "myds");

                %>
            <li><a href="<%=urlmyds.toString()%>">Mis Datasets</a></li>
        </ul>
    </fieldset>
    <fieldset><label>Estadísiticas de Mis datos</label>

        <ul>
            <%
                SWBResourceURL urlstat = paramRequest.getRenderUrl();
                urlstat.setMode(DSPublisherResource.MODE_STATS);

            %>
            <li><a href="<%=urlstat.toString()%>">Estadísticas</a></li>

            <%
                SWBResourceURL urlgra = paramRequest.getRenderUrl();
                urlgra.setMode(DSPublisherResource.MODE_GRAPH);

            %>
            <li><a href="<%=urlgra.toString()%>">Gráficas de actividad</a></li>

        </ul>
    </fieldset>

</div>    

<%
} else if (action.equals("myds")) {  // mis datasets
    String txtTitleSearch = paramRequest.getLocaleString("lbl_searchDS1"); // Búsqueda de Datasets

    Date startSearch = new Date(System.currentTimeMillis());

    // ordenamiento orderby y filtrado de DataSets
    Iterator<Dataset> itds1 = null;
    if (null != filteruri && filteruri.trim().length() > 0) {
        go = ont.getGenericObject(filteruri);
        if (go != null) {
            if (go instanceof Institution) { //filterby.equals(DataSetResource.FILTER_INSTITUTION) &&
                itds1 = Dataset.ClassMgr.listDatasetByInstitution((Institution) go, wsite);
            } else {
                //System.out.println("SIN Filtro....");
                itds1 = Dataset.ClassMgr.listDatasets(wsite);
            }
        } else {
            itds1 = Dataset.ClassMgr.listDatasets(wsite);
        }
    } else {
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
            for (int j = 0; j < arrKeys.length; j++) {
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
                txtDS.append(LODPUtils.getDSTagList(ds, ","));
                String reviewTXT = txtDS.toString().trim().toLowerCase(); // texto completo en donde se buscará la ocurrencia
                if ((DataSetResource.reviewQuery(hmquery, reviewTXT)) && hmresults.get(ds.getURI()) == null) {  //||txtAuto.indexOf(queryinput)>-1 
                    hmresults.put(ds.getURI(), ds);
                }
            }
            itds1 = hmresults.values().iterator();  // pone en el iterador los DS obtenidos
        }
    }

    // dejo en hm los ds
    HashMap<String, Dataset> hmcp = new HashMap<String, Dataset>();
    while (itds1.hasNext()) {
        Dataset ds = itds1.next();
        hmcp.put(ds.getURI(), ds);
    }
    Iterator<Dataset> itds = null;
    //System.out.println("size: " + hmcp.size());
    if (hmcp.size() > 0) {
        itds = DataSetResource.orderDS(hmcp.values().iterator(), orderby);
        intSize = hmcp.size();
    } else {
        intSize = 0;
    }

    Date endSearch = new Date(System.currentTimeMillis());
    long searchTime = endSearch.getTime() - startSearch.getTime();
    long searchTimeSecs = searchTime / 1000;
%>
<div class="buscar_ds">
    <form method="post" action="" id="ds_search">
                <label for="txt_search"><%=txtTitleSearch%></label>
                <input type="text" name="search" value="<%=queryOriginal%>" onfocus="if (this.value == 'Search' || this.value == 'Buscar') { this.value = ''; }">
                <button type="submit"><%=paramRequest.getLocaleString("btn_search")%></button>
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
    <div class="panel">
    <div class="orden">
        <%
            SWBResourceURL urlorder = paramRequest.getRenderUrl();
            urlorder.setParameter("act", "myds");
            if (null != filteruri) {
                urlorder.setParameter("filteruri", filteruri);
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
            <a <%=ckdCreated%> href="<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_CREATED%>" ><%=paramRequest.getLocaleString("lbl_byrecent")%></a> 
            <a <%=ckdView%> href="<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_VIEW%>"><%=paramRequest.getLocaleString("lbl_byvisited")%></a> 
            <a <%=ckdDownload%> href="<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_DOWNLOAD%>"><%=paramRequest.getLocaleString("lbl_bydownload")%></a>
            <a <%=ckdRank%> href="<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_RANK%>"><%=paramRequest.getLocaleString("lbl_byvaluated")%></a>
    </div>

    <%

        //PAGINACION
                /*
         int ps = numPages;
         long l = intSize;

         int p = 0;
         if (npage != null) {
         p = Integer.parseInt(npage);
         }
         int x = 0;
         * */
    %>
    <table class="panel-table">
        <caption>Mis Datasets</caption>
            <thead>
                <tr>
                    <th>Título</th>
                    <th>Descripción</th>
                    <th>Formato</th>
                    <th>Fecha</th>
                    <th>Etiquetas</th>
                    <th>Estadísticas</th>
                    <th>Acciones</th>
                </tr>
            </thead>

            <tbody>
                <%
                    if (intSize == 0) {
                %>
                <tr class="r1">
                    <td colspan="7">No se encontraron Data-Sets</td>
                </tr>
                <%                                } else {

                    boolean paintBackColor = Boolean.FALSE;
                    String toPaint = "";
                    while (itds.hasNext()) {

                        //PAGINACION ////////////////////
                        /*
                         if (x < p * ps) {
                         x++;
                         continue;
                         }
                         if (x == (p * ps + ps) || x == l) {
                         break;
                         }
                         x++;
                         * */
                        /////////////////////////////////

                        Dataset ds = itds.next();

                        SWBResourceURL urlsummary = paramRequest.getRenderUrl();
                        urlsummary.setParameter("summary", "show");
                        urlsummary.setParameter("act", "myds");
                        urlsummary.setParameter("order", orderby);
                        urlsummary.setParameter("suri", ds.getShortURI());

                        SWBResourceURL urldet = paramRequest.getRenderUrl();
                        urldet.setMode(SWBResourceURL.Mode_EDIT);
                        urldet.setParameter("act", "detail");
                        urldet.setParameter("suri", ds.getShortURI());

                        SWBResourceURL urlremove = paramRequest.getActionUrl();
                        urlremove.setParameter("act", "remove");
                        urlremove.setParameter("suri", ds.getEncodedURI());


                        if (paintBackColor) {
                            toPaint = "r1";
                        } else {
                            toPaint = "r2";
                        }

                       // if (null != suri && ds.getShortURI().equals(suri)) {
                       //     toPaint = selectedcolor;
                       // }
                        paintBackColor=!paintBackColor;
                %>
                <tr <%=toPaint%>>
                    <td ><%=ds.getDatasetTitle()%></td> 
                    <td ><%=ds.getDatasetDescription()%></td>    
                    <td ><%=ds.getDatasetFormat()%></td>
                    <td ><%=sdf.format(ds.getDatasetUpdated())%></td>
                    <td ><%=LODPUtils.getDSTagList_UL(ds)%></td> 
                    <td >
                        <ul>
                            <li class="visita" title="Visitas"><strong>Visitas:</strong><%=ds.getViews()%></li>
                            <li class="descar" title="Descargas"><strong>Descargas:</strong><%=ds.getDownloads()%></li>
                            <li class="valora" title="Valoración"><strong>Valoración:</strong><%=ds.getAverage()%></li>
                            <%
                                long numcomm = 0;
                                if (ds.listComments().hasNext()) {
                                    numcomm = SWBUtils.Collections.sizeOf(ds.listComments());
                                }
                                long numapps = 0;
                                Iterator<Application> iteapp = Application.ClassMgr.listApplicationByRelatedDataset(ds);
                                if (iteapp.hasNext()) {
                                    numapps = SWBUtils.Collections.sizeOf(iteapp);
                                }

                                DecimalFormat dft = new DecimalFormat("#,###,###");
                            %>
                            <li class="contri" title="Contribuciones"><strong>Contribuciones:</strong><%=dft.format(numapps)%></li>
                            <li class="coment" title="Comentarios"><strong>Comentarios:</strong><%=dft.format(numcomm)%></li>
                        </ul>
                    </td>
                    <td><a class="editar" href="<%=urldet.toString()%>"><span>Editar</span></a>&nbsp;<a class="eliminar" href="<%=urlremove.toString()%>"><span>Eliminar</span></a></td> 
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
            

    <% 
}
    }
    // Termina llamado como contenido
%>


