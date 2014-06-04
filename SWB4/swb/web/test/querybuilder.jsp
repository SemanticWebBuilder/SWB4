<%-- 
    Document   : querybuilder
    Created on : Mar 2, 2014, 7:19:01 PM
    Author     : javier.solis.g
--%><%@page import="com.hp.hpl.jena.ontology.OntProperty"%>
<%@page import="com.hp.hpl.jena.rdf.model.Statement"%>
<%@page import="com.hp.hpl.jena.rdf.model.StmtIterator"%><%@page import="com.infotec.appfw.util.URLEncoder"%><%@page import="com.hp.hpl.jena.rdf.model.RDFNode"%><%@page import="com.hp.hpl.jena.vocabulary.RDFS"%><%@page import="com.hp.hpl.jena.ontology.OntModel"%><%@page import="com.hp.hpl.jena.ontology.OntClass"%><%@page import="org.semanticwb.SWBPlatform"%><%@page import="java.util.Iterator"%><%@page contentType="text/html" pageEncoding="UTF-8"%><%!
    String encodeURI(String uri)
    {
        return URLEncoder.encode(uri);
    }

    String getNodeString(RDFNode node, String def)
    {
        if (node != null)
        {
            return node.asLiteral().getString();
        }
        return def;
    }

    String getLabel(OntClass cls, String lang)
    {
        String ret = cls.getLabel(lang);
        if (ret == null)
        {
            ret = cls.getLabel(null);
        }
        if (ret == null)
        {
            ret = cls.getLocalName();
        }
        return ret;
    }

    String getLabel(OntProperty cls, String lang)
    {
        String ret = cls.getLabel(lang);
        if (ret == null)
        {
            ret = cls.getLabel(null);
        }
        if (ret == null)
        {
            ret = cls.getLocalName();
        }
        return ret;
    }

    boolean filter(String name)
    {
        /*
         if (name.startsWith("swb:") || name.startsWith("swbres:") || name.startsWith("swbxf:"))
         {
         return true;
         }
         if (name.endsWith("Element") || name.startsWith("map:Select") || name.startsWith("map:Noticias"))
         {
         return true;
         }

         if (name.equals("map:AcuseRecibo") || name.equals("map:Bitacora") || name.equals("map:Usuario"))
         {
         return true;
         }
         if (name.equals("map:ConsolaEventoCapa") || name.equals("map:ContactoMetadatos") || name.equals("map:Favoritos"))
         {
         return true;
         }

         if (name.startsWith("map:Admin"))
         {
         return true;
         }
         */
        return false;
    }
%><%
    OntModel ontology = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel();
    //Props
    {
        String scls = request.getParameter("cls");
        if (scls != null)
        {
            OntClass cls = ontology.getOntClass(scls);
            Iterator<OntProperty> it = cls.listDeclaredProperties();
            while (it.hasNext())
            {
                OntProperty st = it.next();
                out.println(getLabel(st, "es"));
                out.println("<br>");
            }
            //out.println(scls+" "+cls);
            return;
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">
            function getSynchData(url)
            {
                if (typeof XMLHttpRequest === "undefined")
                {
                    XMLHttpRequest = function() {
                        try {
                            return new ActiveXObject("Msxml2.XMLHTTP.6.0");
                        }
                        catch (e) {
                        }
                        try {
                            return new ActiveXObject("Msxml2.XMLHTTP.3.0");
                        }
                        catch (e) {
                        }
                        try {
                            return new ActiveXObject("Microsoft.XMLHTTP");
                        }
                        catch (e) {
                        }
                        // Microsoft.XMLHTTP points to Msxml2.XMLHTTP and is redundant
                        throw new Error("This browser does not support XMLHttpRequest.");
                    };
                }

                var aRequest = new XMLHttpRequest();
                aRequest.open('GET', url, false);
                aRequest.send();
                return aRequest.responseText;
            }
        </script>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h3>Clases</h3>
        <div>
            <select name="cls" onchange="
                var data = getSynchData('/querybuilder.jsp?cls=' + value);
                this.nextElementSibling.innerHTML = data;
                //console.log(this);
                    ">
                <%
                    Iterator<OntClass> it = ontology.listClasses();
                    //Iterator<OntClass> it=ontology.listClasses();
                    while (it.hasNext())
                    {
                        OntClass cls = it.next();
                        if (cls.getURI() == null)
                        {
                            continue;
                        }
                        String name = ontology.getNsURIPrefix(cls.getNameSpace()) + ":" + cls.getLocalName();
                        String label = getLabel(cls, "es");
                        String comm = getNodeString(cls.getPropertyValue(RDFS.comment), "");

                        if ((comm != null && comm.startsWith("#")) || filter(name))
                        {
                            continue;
                        }

                %>
                <option value="<%=encodeURI(cls.getURI())%>" title="<%=comm%>">
                    <%=label%>
                </option> 
                <%
                    }
                %>
            </select>
            <div>

            </div>
        </div>
    </body>
</html>
