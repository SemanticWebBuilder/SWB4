<%--
    Document   : view Recurso DataSetResource
    Created on : 03/05/2013
    Author     : juan.fernandez
--%>

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

    -- >
</script>
<%
    if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {
    } else {
        // llamado como contenido
%>
<div class="izquierdo">
    <div class="izq_sector">
        <ul>
            <%
            Iterator<Sector> itsec = Sector.ClassMgr.listSectors(wsite);
            while(itsec.hasNext()){
                Sector sec = itsec.next();
                
                SWBResourceURL url = paramRequest.getRenderUrl();
                url.setParameter("filteruri", sec.getEncodedURI());
            %>
            <li><a href="<%=url.toString()%>"><%=sec.getSectorTitle()%></a></li> 
            <%
            }
            %>
        </ul>   
    </div>
    <div class="izq_institucion">
        <ul>
            <%
            Iterator<Institution> itins = Institution.ClassMgr.listInstitutions(wsite);
            while(itins.hasNext()){
                Institution inst = itins.next();
                
                SWBResourceURL url = paramRequest.getRenderUrl();
                url.setParameter("filteruri", inst.getEncodedURI());
            %>
            <li><a href="<%=url.toString()%>"><%=inst.getInstitutionTitle()%></a></li> 
            <%
            }
            %>
        </ul>  
    </div>
    <div class="izq_tema">
         <ul>
            <%
            Iterator<Topic> ittop = Topic.ClassMgr.listTopics(wsite);  
            while(ittop.hasNext()){
                Topic inst = ittop.next();
                SWBResourceURL url = paramRequest.getRenderUrl();
                url.setParameter("filteruri", inst.getEncodedURI());
            %>
            <li><a href="<%=url.toString()%>"><%=inst.getTopicTitle()%></a></li> 
            <%
            }
            %>
        </ul>  
    </div>
</div>
<div class="derecho">
    <%
        // ordenamiento orderby y filtrado de DataSets
        Iterator<Dataset> itds1 = null;
        if (filteruri.length() > 0) {
            go = ont.getGenericObject(filteruri);
            if (go != null) {
                if (filterby.equals(DataSetResource.FILTER_INSTITUTION) && go instanceof Institution) {
                    itds1 = Dataset.ClassMgr.listDatasetByInstitution((Institution) go);
                } else if (filterby.equals(DataSetResource.FILTER_TOPIC) && go instanceof Topic) {
                    itds1 = Dataset.ClassMgr.listDatasetByTopic((Topic) go);
                } else if (filterby.equals(DataSetResource.FILTER_SECTOR) && go instanceof Sector) {
                    itds1 = Dataset.ClassMgr.listDatasetByDatasetSector((Sector) go);
                } else {
                    itds1 = Dataset.ClassMgr.listDatasets();
                }
            } else {
                itds1 = Dataset.ClassMgr.listDatasets();
            }
        } else {
            itds1 = Dataset.ClassMgr.listDatasets();
        }

        Iterator<Dataset> itds = DataSetResource.orderDS(itds1, orderby);
        intSize = SWBUtils.Collections.sizeOf(itds);
         itds = DataSetResource.orderDS(itds1, orderby);
        //PAGINACION
        int ps = numPages;
        long l = intSize;

        int p = 0;
        if (npage != null) {
            p = Integer.parseInt(npage);
        }
        int x = 0;
    %>
    <ul>
        <%
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

        %>
        <li>
            <label><%=ds.getDatasetTitle()%></label>
            Publicador:<%=ds.getInstitution().getInstitutionTitle()%>&nbsp;&nbsp;&nbsp;Formats:<%=ds.getDatasetFormat()%>&nbsp;&nbsp;&nbsp;Actualización:<%=sdf.format(ds.getDatasetUpdated())%><br/>
            <p><%=ds.getDatasetDescription()%></p>
        </li>
        <%    }
        %>
    </ul>

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
                    out.println("<a href=\"#\" onclick=\"window.location='" + urlNext + "';\">Ir al final</a> ");
                }

            }


        %>
    </p></div>
    <%
        }
    // Termina llamado como contenido
    %>


