<%--
    Document   : view Recurso DataSetResource
    Created on : 03/05/2013
    Author     : juan.fernandez
--%>

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

    String strNumItems = base.getAttribute("numpag", "10");
    String npage = request.getParameter("page");
    String orderby = request.getParameter("order");
    String filterby = request.getParameter("filter");
    String filteruri = request.getParameter("filteruri");
    String direction = request.getParameter("direction");
    String action = request.getParameter("act");

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

    //12 Jun 2013, 11:35
    //SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, h:mm a", new Locale("es"));
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

%>
<script type="text/javascript">
    <!--
    // scan page for widgets and instantiate them
    dojo.require("dojo.parser");
    dojo.require("dijit._Calendar");
    dojo.require("dijit.ProgressBar");
    dojo.require("dijit.TitlePane");
    dojo.require("dijit.TooltipDialog");
    dojo.require("dijit.Dialog");
    // editor:
    dojo.require("dijit.Editor");

    // various Form elemetns
    dojo.require("dijit.form.Form");
    dojo.require("dijit.form.CheckBox");
    dojo.require("dijit.form.Textarea");
    dojo.require("dijit.form.FilteringSelect");
    dojo.require("dijit.form.TextBox");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.form.NumberSpinner");
    dojo.require("dijit.form.Slider");
    dojo.require("dojox.form.BusyButton");
    dojo.require("dojox.form.TimeSpinner");
    dojo.require("dijit.form.ValidationTextBox");
    dojo.require("dijit.layout.ContentPane");
    //dojo.require("dijit.form.Select");
    dojo.require("dijit.form.NumberTextBox");
    dojo.require("dijit.form.DropDownButton");

    -- ></script>
    <%
        if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {
            // llamada como estrategia
        } else {
            // llamado como contenido
            if (action.equals("")) {
                String txtTitleSearch = "Búsqueda de Data Sets";

                Date startSearch = new Date(System.currentTimeMillis());

                // ordenamiento orderby y filtrado de DataSets
                Iterator<Dataset> itds1 = null;
                if (null != filteruri && filteruri.trim().length() > 0) {
                    go = ont.getGenericObject(filteruri);
                    if (go != null) {
                        if (filterby.equals(DataSetResource.FILTER_INSTITUTION) && go instanceof Institution) {
                            itds1 = Dataset.ClassMgr.listDatasetByInstitution((Institution) go,wsite); 
                        } else if (filterby.equals(DataSetResource.FILTER_TOPIC) && go instanceof Topic) {
                            itds1 = Dataset.ClassMgr.listDatasetByTopic((Topic) go,wsite);
                        } else if (filterby.equals(DataSetResource.FILTER_SECTOR) && go instanceof Sector) {
                            itds1 = Dataset.ClassMgr.listDatasetByDatasetSector((Sector) go,wsite);
                        } else {
                            itds1 = Dataset.ClassMgr.listDatasets(wsite);
                        }
                    } else {
                        itds1 = Dataset.ClassMgr.listDatasets(wsite);
                    }
                } else {
                    itds1 = Dataset.ClassMgr.listDatasets(wsite);
                }

                //List<Dataset> listsize = SWBUtils.Collections.copyIterator(itds1);  
                
                System.out.println("Listado DS .... ");
                
                // obteniendo Datasets que coincidan con el texto a buscar
                String queryinput = request.getParameter("search");
                System.out.println("query..."+queryinput);
                String queryOriginal = queryinput != null ? queryinput : "Search";
                if (null == queryinput) {
                    queryinput = "";
                } else {
                    queryinput = queryinput.trim().toLowerCase();
                    txtTitleSearch = "Resultado de la búsqueda";
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
                            txtDS.append(LODPUtils.getDSTagList(ds));
                            String reviewTXT = txtDS.toString().trim().toLowerCase(); // texto completo en donde se buscará la ocurrencia
                            //System.out.println("Texto DS: "+reviewTXT);
                            if ((reviewQuery(hmquery, reviewTXT)) && hmresults.get(ds.getURI()) == null) {  //||txtAuto.indexOf(queryinput)>-1 
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
                    hmcp.put(ds.getURI(), ds);
                }
//                System.out.println("Antes de ordenar DS....");
                
                Iterator<Dataset> itds = DataSetResource.orderDS(hmcp.values().iterator(), orderby);
                intSize = SWBUtils.Collections.sizeOf(itds);

//                 System.out.println("Tamaño DS...."+intSize);

                 itds = DataSetResource.orderDS(hmcp.values().iterator(), orderby);

                Date endSearch = new Date(System.currentTimeMillis());
                long searchTime = endSearch.getTime() - startSearch.getTime();
                long searchTimeSecs = searchTime / 1000;
    %>
<div class="buscar_ds">
    <form method="post" action="" id="ds_search">
        <%
            if (filterby != null && filterby.trim().length() > 0) {
        %>
        <input type="hidden" name="filter" value="<%=filterby%>"/>
        <%
            }
            if (queryinput != null && queryinput.trim().length() > 0) {
        %>
        <input type="hidden" name="search" value="<%=queryinput%>"/>
        <%
            }
        %>
        <ul>
            <li>
                <label for="txt_search"><%=txtTitleSearch%></label><input type="text" name="search" value="<%=queryOriginal%>" onfocus="if (this.value == 'Search') {this.value = ''};"><button type="submit">Buscar</button>
                <%
                    // si hubo búsqueda
                    if (null != queryinput && queryinput.trim().length() > 0) {
                        if (intSize > 0) {
                %>
                <br/>
                Cerca de <%=intSize%> resultados
                <%
                } else {
                %>
                <br/>
                No hubo resultados 
                <%                            }
                    // tiempo en segundos de lo que se tardó la búsqueda
                %>
                (<%=searchTimeSecs%> segundos)
                <%
                    }
                %>        
            </li>
        </ul>
    </form>
</div>
<div class="izquierdo">
    <div class="izq_sector">
        <ul>
            <li><h3>Filtrar por Sector</h3></li>
                    <%
                        Iterator<Sector> itsec = Sector.ClassMgr.listSectors(wsite);
                        while (itsec.hasNext()) {
                            Sector sec = itsec.next();

                            SWBResourceURL url = paramRequest.getRenderUrl();
                            url.setParameter("filteruri", sec.getEncodedURI());
                            if (null != orderby) {
                                url.setParameter("order", orderby);
                            }
                            if (queryinput != null && queryinput.trim().length() > 0) {
                                url.setParameter("search", queryinput);
                            }

                    %>
            <li><a href="<%=url.toString()%>" title="<%=sec.getSectorDescription() != null ? sec.getSectorDescription().trim() : sec.getSectorTitle()%>"><%=sec.getSectorTitle()%></a></li>  
                <%
                    }
                %>
        </ul>   
    </div>
    <div class="izq_institucion">
        <ul>
            <li><h3>Filtrar por Institucion</h3></li>
                <%
                    Iterator<Institution> itins = Institution.ClassMgr.listInstitutions(wsite);
                    while (itins.hasNext()) {
                        Institution inst = itins.next();

                        SWBResourceURL url = paramRequest.getRenderUrl();
                        url.setParameter("filteruri", inst.getEncodedURI());
                        if (null != orderby) {
                            url.setParameter("order", orderby);
                        }
                        if (queryinput != null && queryinput.trim().length() > 0) {
                            url.setParameter("search", queryinput);
                        }
                %>
            <li><a href="<%=url.toString()%>" title="<%=inst.getInstitutionDescription() != null ? inst.getInstitutionDescription().trim() : inst.getInstitutionTitle()%>"><%=inst.getInstitutionTitle()%></a></li> 
                <%
                    }
                %>
        </ul>  
    </div>
    <div class="izq_tema">
        <ul>
            <li><h3>Filtrar por Tema</h3></li>
                <%
                    Iterator<Topic> ittop = Topic.ClassMgr.listTopics(wsite);
                    while (ittop.hasNext()) {
                        Topic inst = ittop.next();
                        SWBResourceURL url = paramRequest.getRenderUrl();
                        url.setParameter("filteruri", inst.getEncodedURI());
                        if (null != orderby) {
                            url.setParameter("order", orderby);
                        }
                        if (queryinput != null && queryinput.trim().length() > 0) {
                            url.setParameter("search", queryinput);
                        }
                %>
            <li><a href="<%=url.toString()%>" title="<%=inst.getTopicDescription() != null ? inst.getTopicDescription().trim() : inst.getTopicTitle()%>"><%=inst.getTopicTitle()%></a></li> 
                <%
                    }
                %>
        </ul>  
    </div>
</div>
<div class="derecho">
    <div class="derecho_ordena">
        <%
            SWBResourceURL urlorder = paramRequest.getRenderUrl();
            urlorder.setParameter("act", "");
            if (null != filterby) {
                urlorder.setParameter("filter", filterby);
            }
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
                    ckdCreated = "checked";
                } else if (orderby.equals(DataSetResource.ORDER_VIEW)) {
                    ckdView = "checked";
                } else if (orderby.equals(DataSetResource.ORDER_DOWNLOAD)) {
                    ckdDownload = "checked";
                } else if (orderby.equals(DataSetResource.ORDER_RANK)) {
                    ckdRank = "checked";
                }
            }

        %>
        <label>Ordenar</label>
        <p>
            <input type="radio" id="ordercreated" name="order" value="created" <%=ckdCreated%> onclick="window.location = '<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_CREATED%>';"><label for="ordercreated">Más reciente</label>
            <input type="radio" id="orderview" name="order" value="view" <%=ckdView%> onclick="window.location = '<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_VIEW%>';"><label for="orderview">Más visitado</label>
            <input type="radio" id="orderdownload" name="order" value="download" <%=ckdDownload%> onclick="window.location = '<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_DOWNLOAD%>';"><label for="orderdownload">Más descargado</label>
            <input type="radio" id="orderrank" name="order" value="value" <%=ckdRank%> onclick="window.location = '<%=urlorder.toString()%>&order=<%=DataSetResource.ORDER_RANK%>';"><label for="orderrank">Mejor calificado</label>
        </p>
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
    <div class="listadataset">
        <ul>
            <%
                if (intSize == 0) {
            %>
            <li><h3>No se encontraron Data-Sets</h3></li>
                    <%                                } else {

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
                            SWBResourceURL urldet = paramRequest.getRenderUrl();
                            urldet.setParameter("act", "detail");
                            urldet.setParameter("suri", ds.getURI());
                    %>
            <li>
                <label><a href="<%=urldet.toString()%>"><%=ds.getDatasetTitle()%></a></label> 
                Publicador:<%=ds.getInstitution().getInstitutionTitle()%>&nbsp;&nbsp;&nbsp;Formats:<%=ds.getDatasetFormat()%>&nbsp;&nbsp;&nbsp;Actualización:<%=sdf.format(ds.getDatasetUpdated())%><br/>
                <p><%=ds.getDatasetDescription()%></p>
            </li>
            <%
                    }
                }
            %>
        </ul>
    </div>
</div>
<div class="paginar">
    <p>
        <%
            if (p > 0 || x < l) //Requiere paginacion
            {

                int pages = (int) (l / ps);
                if ((l % ps) > 0) {
                    pages++;
                }

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

                    out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">Ir al inicio</a> ");
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
                    out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">Ir al final</a> ");
                }

            }


        %>
    </p></div>
    <%
    } else if (action.equals("detail")) {  // detalle del Dataset seleccionado

        System.out.println("Detalle DS.........");
        String suri = request.getParameter("suri");
        System.out.println("URI........."+suri);
        go = ont.getGenericObject(suri);
        if (go instanceof Dataset) {
    %>
<div>
    <%
        Dataset ds = (Dataset) go;
        //ds.getDatasetDescription()
        boolean bupdated = LODPUtils.updateDSViews(ds);  // se actualiza los views
%>
    <div>
        <h2><%=ds.getDatasetTitle()%></h2> 
        <ul>
            <li>
                <label>Descripción:</label><p><%=ds.getDatasetDescription()%></p>
            </li>
            <li>
                <label>Institución:</label><p><%=ds.getInstitution() != null ? ds.getInstitution().getInstitutionTitle() : ""%></p> 
            </li>
            <li>
                <label>Enlace Técnico:</label><p><%=ds.getInstitution() != null ? ds.getInstitution().getTopLevelName() : "No disponible"%></p>
            </li>
            <li>
                <label>Creación:</label><p><%=sdf2.format(ds.getDatasetCreated())%></p>
            </li>
            <li>
                <label>Actualización:</label><p><%=sdf2.format(ds.getDatasetUpdated())%></p>
            </li>
            <li>
                <label>Sitio Web del emisor</label><p><%=ds.getInstitution() != null ? ds.getInstitution().getInstitutionHome() : ""%></p> 
            </li>
            <li>
                <label>Email contacto:</label><p><%=ds.getPublisher() != null ? ds.getPublisher().getEmail() : ""%></p> 
            </li>
            <li>
                <label>Licencia de uso:</label><p title="<%=ds.getLicense() != null ? ds.getLicense().getLicenseDescription() : ""%>"><%=ds.getLicense() != null ? ds.getLicense().getLicenseTitle() : "No asignada"%></p>
            </li>
            <li>
                <label>Versión:</label><p><%=ds.getActualVersion() != null ? ds.getActualVersion().getVersion() : "---"%></p>
            </li>
            <li>
                <%
                    String taglist = LODPUtils.getDSTagList(ds);
                %>
                <label>Etiquetas:</label><p><%=taglist%></p>
            </li>
            <li>
                <label>Formato:</label><p><%=ds.getDatasetFormat()%></p> 
            </li>
            <li>
                <label>URL:</label><p><%=ds.getDatasetDescription()%></p>
            </li>
            <li>
                <label>Valoración:</label><p><%=ds.getRanks()%></p>
                <div>
                    5 Excelente<br/>
                    4 Recomendable<br/>
                    3 Bueno<br/>
                    2 Regular<br/>
                    1 No recomendable<br/>
                </div>
            </li>
            <%
                DatasetVersion dslv = ds.getLastVersion();
                DatasetVersion ver = null;

                //para obtener la primera version
                if (null != dslv) {
                    ver = dslv;
                    while (ver.getPreviousVersion() != null) { //
                        ver = ver.getPreviousVersion();
                    }
                }


            %>
            <li>
                <label>Actualizaciones del dataset:</label>
                <p>
                <table>
                    <thead>
                        <tr>
                            <th>Número de versión / revisión</th><th>Fecha de actualización</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            while (ver != null) {
                                int vernum = ver.getVersion();
                                Date verdate = ver.getVersionCreated();
                        %>
                        <tr>
                            <td><%=vernum%></td>
                            <td><%=sdf2.format(verdate)%></td>
                        </tr>
                        <%
                                ver = ver.getNextVersion();
                            }
                        %>
                    </tbody>
                </table>
                </p>
            </li>
            <li>
                <label>Exportar fichas del dataset:</label><p><button>RDF</button>&nbsp;<button>JSON</button></p>
            </li>
            <li>
                <label>Visitas:</label><p><%=ds.getViews()%></p>
            </li>
            <li>
                <label>Descargas:</label><p><%=ds.getDownloads()%></p>
            </li>
            <li>
                <%
                    SWBResourceURL urlstats = paramRequest.getRenderUrl();
                    urlstats.setParameter("suri", ds.getURI());
                    urlstats.setParameter("act", "stats");
                %>
                <p><a href="<%=urlstats.toString()%>">Estadísticas del dataset</a></p> 
            </li>
        </ul>
    </div>   
    <div>
        <label>Aplicaciones relacionadas</label>
        <ul>
            <%  // lista de aplicaciones relacionadas
            Iterator<Application> itapp = Application.ClassMgr.listApplicationByRelatedDataset(ds, wsite);
            if(itapp.hasNext()){
                while(itapp.hasNext()){
                    Application appli = itapp.next();
                    
            %>
            <li title="<%=appli.getAppDescription()%>"><%=appli.getAppTitle()%></li>
            <%
                }
            } else {

            %>
            <li>No se encntraron aplicaciones relacionadas.</li>
                <%
            }
                %>
        </ul>
    </div>
</div>               
<%
            }
        } else if("stats".equals(action)){
     %>
<div>
    <table>
        <thead>
            <tr>
                <th>Total de descargas</th><th>Institución</th><th>Dataset</th><th>Descargas</th><th>Última descarga</th><th>Visitas</th><th>Última visita</th>
            </tr>
        </thead>
    </table>
</div>
    <%
        }

    }
    // Termina llamado como contenido
%>

<%!
    public boolean reviewQuery(HashMap<String, String> hm, String texto) {
        boolean res = Boolean.FALSE;
        System.out.println("Revisando query....");
        if (null != hm) {
            Iterator<String> itstr = hm.keySet().iterator();
            while (itstr.hasNext()) {
                String skey = itstr.next();
                System.out.println("key..."+texto.indexOf(skey));
                if (texto.indexOf(skey) > -1) {
                    res = Boolean.TRUE;
                    break;
                } 
            }
        }
        return res;
    }

%>
