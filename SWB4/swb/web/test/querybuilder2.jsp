<%@page import="com.hp.hpl.jena.vocabulary.*"%><%@page import="java.util.*"%><%@page import="org.semanticwb.*"%><%@page import="com.hp.hpl.jena.ontology.*"%><%@page import="com.hp.hpl.jena.rdf.model.*"%><%@page import="com.infotec.appfw.util.*"%><%@page contentType="text/html" pageEncoding="UTF-8"%><%!
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
    
    String expandURI(OntModel ontology, String suri)
    {
        return ontology.expandPrefix(suri);
    }
    
    String getShortURI(OntResource res)
    {
        return res.getOntModel().shortForm(res.getURI());
    }

    String getLabel(OntResource res, String lang)
    {
        String ret = res.getLabel(lang);
        if (ret == null)
        {
            ret = res.getLabel(null);
        }
        if (ret == null)
        {
            ret = res.getLocalName();
        }
        return ret;
    }
    
    Iterator sortIterator(Iterator it, final String lang)
    {
        TreeSet<OntResource> ret=new TreeSet(new Comparator<OntResource>() 
        {
            @Override
            public int compare(OntResource t, OntResource t1)
            {
                int i=SWBUtils.TEXT.replaceSpecialCharacters(getLabel(t,lang), false).compareToIgnoreCase(SWBUtils.TEXT.replaceSpecialCharacters(getLabel(t1,lang), false));
                //getLabel(t,lang).compareToIgnoreCase(getLabel(t1,lang));
                //System.out.println(i+"-->"+t+"-->"+t1);
                return i;
            }
        });        
        
        while(it.hasNext())
        {
            OntResource res=(OntResource)it.next();
            if(res.getURI()!=null)
            {
                ret.add(res);
                //System.out.println(getLabel(res,lang));
            }
        }        
        
        return ret.iterator();
    }
    
    Iterator<OntProperty> listProperties(OntClass cls)
    {
        ArrayList<OntProperty> arr=new ArrayList();
        Iterator<OntProperty> it=cls.listDeclaredProperties();
        while(it.hasNext())
        {
            OntProperty p=(OntProperty)it.next();        
            //Filtra propiedades sin dominio
            boolean filter=false;
            if(p.getDomain()==null)
            {
                filter=true;
                OntProperty inv=p.getInverse();
                if(inv!=null && inv.getRange()!=null)
                {                        
                    if(inv.getRange().equals(cls) || inv.getRange().asClass().hasSubClass(cls))
                    {
                        filter=false;
                        System.out.println(p+"-->"+p.getDomain()+"----->"+inv+"-->"+inv.getRange());
                    }
                }
            }
            if(filter)continue;            
            arr.add(p);
        }        
        return arr.iterator();
    }    

    boolean filter(OntResource res)
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
    String lang="es";
    OntModel ontology = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel();

    //System.out.println("ontology:"+ontology);
    //Props
    {
        String t = request.getParameter("t");
        if (t != null)
        {
            response.setContentType("text/xml;charset=UTF-8");
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<options>");
            
            if (t.equals("cls"))
            {
                Iterator<OntClass> it = sortIterator(ontology.listClasses(),lang);
                //Iterator<OntClass> it=ontology.listClasses();
                while (it.hasNext())
                {
                    OntClass cls = (OntClass)it.next();
                    //System.out.println("cla:"+cls);
                    if (cls.getURI() == null)
                    {
                        continue;
                    }
                    //String name = ontology.getNsURIPrefix(cls.getNameSpace()) + ":" + cls.getLocalName();
                    String label = getLabel(cls, lang);
                    label=label+" ("+getShortURI(cls)+")";

                    
                    String comm = getNodeString(cls.getPropertyValue(RDFS.comment), "");

                    if ((comm != null && comm.startsWith("#")) || filter(cls))
                    {
                        continue;
                    }
                    //System.out.println("cla:"+cls);

                    //out.println("<option value=\"" + encodeURI(cls.getURI()) + "\" title=\"" + comm.replace("\"", "&#34;") + "\">");
                    out.println("<option value=\"" + getShortURI(cls) + "\">");
                    out.println(label);
                    out.println("</option>");

                }
            }else if(t.equals("prop"))
            {
                String scls = request.getParameter("subj");
                if (scls != null)
                {
                    OntClass cls = ontology.getOntClass(ontology.expandPrefix(scls));
                    Iterator<OntProperty> it = sortIterator(listProperties(cls),lang);
                    while (it.hasNext())
                    {
                        OntProperty st = it.next();
                        String label = getLabel(st, lang);
                        label=label+" ("+getShortURI(st)+")";
                        out.println("<option value=\"" + getShortURI(st) + "\">");
                        out.println(label);
                        out.println("</option>");
                    }
                }
            }else if(t.equals("obj"))
            {
                String sprop = request.getParameter("prop");
                if (sprop != null)
                {
                    OntProperty prop = ontology.getOntProperty(ontology.expandPrefix(sprop));
                    if(prop.isObjectProperty())
                    {
                        OntResource res = prop.getRange();
                        if(res!=null)
                        {
                            String label = getLabel(res, lang);
                            if(res!=null && res.isClass())
                            {
                                out.println("<option value=\"" + getShortURI(res) + "\">");
                                out.println(label);
                                out.println("</option>");
                            }
                        }
                    }
                }
            }
            out.println("</options>");
            return;
        }


        String scls = request.getParameter("cls");
        if (scls != null)
        {
            OntClass cls = ontology.getOntClass(scls);
            Iterator<OntProperty> it = cls.listDeclaredProperties();
            while (it.hasNext())
            {
                OntProperty st = it.next();
                out.println(getLabel(st, lang));
                out.println("<br>");
            }
            //out.println(scls+" "+cls);
            return;
        }
    }
    
    StringBuilder namespaces=new StringBuilder();

    Iterator nit=ontology.listNameSpaces();
    while(nit.hasNext())
    {
        String ns=(String)nit.next();
        namespaces.append("PREFIX ");
        namespaces.append(ontology.getNsURIPrefix(ns));
        namespaces.append(": <");
        namespaces.append(ns);
        namespaces.append(">\\n");
    }    
%>
<!DOCTYPE html>
<HTML>
    <HEAD>
        <TITLE>SPARQL Query Builder</TITLE>

        <META content="text/html; charset=utf-8" http-equiv="Content-Type">
        <style type="text/css">
            td {
                vertical-align: top;
                border: 1px dotted;
            }
        </style>

        <!--<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/prototype/1.6.0.3/prototype.js"></script>-->
        <script type="text/javascript" src="prototype.js"></script>
        <script type="text/javascript">
            var level = 0;
            
            function init() {
                var url = "?t=cls";

                var base = document.getElementById("subject(0,0)");
                var myAjax = new Ajax.Request(url, {method: "get", parameters: "", onComplete: function(originalRequest) {
                        var response = originalRequest.responseXML;
                        var options = response.getElementsByTagName("option");
                        for (i = 0; i < options.length; i++) {
                            base[base.length] = new Option(options[i].firstChild.data, options[i].getAttribute("value"));
                        }
                        var subdiv = document.getElementById("subject(0)");
                        subdiv.appendChild(document.createElement("br"));
                        var addprop = document.createElement("input");
                        addprop.type = "button";
                        addprop.value = "Add Property";
                        addprop.count = 0;
                        addprop.level = 0;
                        addprop.onclick = function() {
                            return getProperty(this);
                        }
                        subdiv.appendChild(addprop);
                        level++;
                    }
                }
                );
            }


            function getProperty(addprop) {
                var url = "?t=prop";
                var subject = document.getElementById("subject(" + addprop.level + ",0)").value;
                if (subject == "") {
                    alert("Please select a class...");
                }
                else {
                    var params = "subj=" + subject;
                    var myAjax = new Ajax.Request(url, {method: "get", parameters: params, onComplete: function(originalRequest) {
                            var response = originalRequest.responseXML;
                            var property = document.createElement("select");
                            property.id = "predicate(" + addprop.level + "," + addprop.count + ")";
                            property[property.length] = new Option("Properties", "");
                            var options = response.getElementsByTagName('option');

                            for (i = 0; i < options.length; i++) {
                                property[property.length] = new Option(options[i].firstChild.data, options[i].getAttribute("value"));
                            }

                            property.level = addprop.level;
                            property.count = addprop.count;

                            property.onchange = function() {
                                return getObject(this);
                            }

                            var prediv = document.getElementById("predicate(" + addprop.level + ")");
                            prediv.appendChild(property);

                            addprop.count += 1
                            prediv.appendChild(document.createElement("br"));
                        }
                    }
                    );
                }
            }

            function getObject(property) {
                var url = "?t=obj";


                var subject = document.getElementById("subject(" + property.level + ",0)").value;

                //DEL PROPERTY
                var delprop = document.createElement("input");
                delprop.type = "button";
                delprop.value = "Delete";
                delprop.count = property.count;
                delprop.level = property.level;
                delprop.onclick = function() {
                    return delProperty(this);
                }
                var prediv = document.getElementById("predicate(" + property.level + ")");
                prediv.insertBefore(delprop, property.nextSibling);


                var predicate = property.value;

                var params = "subj=" + subject + "&prop=" + predicate;

                var myAjax = new Ajax.Request(url, {method: "get", parameters: params, onComplete: function(originalRequest) {
                        var response = originalRequest.responseXML;
                        var objdiv = document.getElementById("object(" + property.level + ")");
                        var options = response.getElementsByTagName('option');
                        var obj = document.getElementById("object(" + property.level + "," + property.count + ")");
                        if (obj == null) {
                            if (options.length > 0) {
                                obj = document.createElement("select");
                                obj[obj.length] = new Option("Clases", "");
                                for (i = 0; i < options.length; i++) {
                                    obj[obj.length] = new Option(options[i].firstChild.data, options[i].getAttribute("value"));
                                }
                                obj.onchange = function() {
                                    return addClass(this);
                                }

                            }
                            else {
                                obj = document.createElement("input");
                                obj.type = "text";
                            }
                            obj.id = "object(" + property.level + "," + property.count + ")";

                            obj.level = property.level;
                            obj.count = property.count;
                            objdiv.appendChild(obj);
                            objdiv.appendChild(document.createElement("br"));
                        }
                        else {
                            var objpar = obj.parentNode;

                            if (options.length > 0) {
                                var newobj = document.createElement("select");
                                newobj[newobj.length] = new Option("Clases", "");
                                for (i = 0; i < options.length; i++) {
                                    newobj[newobj.length] = new Option(options[i].firstChild.data, options[i].getAttribute("value"));
                                }
                                newobj.onchange = function() {
                                    return addClass(this);
                                }

                            }
                            else {
                                newobj = document.createElement("input");
                                newobj.type = "text";
                            }
                            newobj.id = "object(" + property.level + "," + property.count + ")";

                            newobj.level = property.level;
                            newobj.count = property.count;
                            objpar.replaceChild(newobj, obj);
                        }
                    }
                }
                );
            }

            function addClass(obj) {
                addClazz();

                var subject = document.createElement("select");

                subject[subject.length] = new Option(obj.textContent, obj.value);
                subject.level = level;
                level++;
                subject.count = 0;
                subject.id = "subject(" + subject.level + "," + subject.count + ")";

                var subdiv = document.getElementById("subject(" + subject.level + ")");
                subdiv.appendChild(subject);

                var delclazz = document.createElement("input");
                delclazz.type = "button";
                delclazz.value = "Delete";
                delclazz.count = subject.count;
                delclazz.level = subject.level;
                delclazz.onclick = function() {
                    return delClazz(this.level);
                }
                subdiv.appendChild(delclazz);
                subdiv.appendChild(document.createElement("br"));

                var addprop = document.createElement("input");
                addprop.type = "button";
                addprop.value = "Add Property";
                addprop.count = subject.count;
                addprop.level = subject.level;
                addprop.onclick = function() {
                    return getProperty(this);
                }
                subdiv.appendChild(addprop);


            }

            function addClazz() {
                var builder = document.getElementById("builder");

                var clazz = document.createElement("tr");
                clazz.id = "clazz(" + level + ")";
                var subject = document.createElement("td");
                subject.id = "subject(" + level + ")";
                var predicate = document.createElement("td");
                predicate.id = "predicate(" + level + ")";
                var object = document.createElement("td");
                object.id = "object(" + level + ")";

                clazz.appendChild(subject);
                clazz.appendChild(predicate);
                clazz.appendChild(object);

                builder.appendChild(clazz);
            }

            function delClazz(level) {
                var clazz = document.getElementById("clazz(" + level + ")");
                var builder = document.getElementById("builder");
                builder.removeChild(clazz);
            }

            function delProperty(delprop) {
                var sub = document.getElementById("predicate(" + delprop.level + "," + delprop.count + ")");
                var obj = document.getElementById("object(" + delprop.level + "," + delprop.count + ")");
                var subdiv = document.getElementById("predicate(" + delprop.level + ")");
                var objdiv = document.getElementById("object(" + delprop.level + ")");


                subdiv.removeChild(sub.nextSibling.nextSibling);
                subdiv.removeChild(sub.nextSibling);
                subdiv.removeChild(sub);

                objdiv.removeChild(obj.nextSibling);
                objdiv.removeChild(obj);

            }

            function genQuery() {
                //var namespaces = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\nPREFIX swb: <http://www.semanticwebbuilder.org/swb4/ontology#>\n";
                //swb  http://www.semanticwebbuilder.org/swb4/ontology#
                //swbcomm http://www.semanticwebbuilder.org/swb4/community#
                var namespaces = "<%=namespaces%>";
                
                var items = new Array();
                var criterias = new Array();
                var clazz = new Array();
                var number = 0;
                var _sub;
                var _obj;


                for (i = 0; i < level; i++) {
                    var subjects = document.getElementById("subject(" + i + ")");
                    if (subjects == null) {
                        continue;
                    }
                    var subNodes = subjects.getElementsByTagName("select");
                    var sub = subNodes[0].value;
                    if (!clazz[sub.substring(sub.indexOf(":") + 1)]) {
                        clazz[sub.substring(sub.indexOf(":") + 1)] = 1;
                        _sub = sub.substring(sub.indexOf(":") + 1) + 1;
                    }
                    else {
                        _sub = sub.substring(sub.indexOf(":") + 1) + clazz[sub.substring(sub.indexOf(":") + 1)];
                    }
                    var subname = "?" + _sub;
                    criterias[criterias.length] = subname + " rdf:type " + sub + " .";


                    var predicates = document.getElementById("predicate(" + i + ")");
                    var preNodes = predicates.getElementsByTagName("select");
                    var num = preNodes.length;
                    var offset = 0;
                    for (j = 0; j < num; j++) {
                        var obj = document.getElementById("object(" + i + "," + j + ")");
                        while (obj == null) {
                            offset++;
                            var newj = j + offset;
                            obj = document.getElementById("object(" + i + "," + newj + ")");
                        }
                        var pre = preNodes[j];
                        if (obj.tagName == "INPUT") {
                            var objname = subname + "_" + pre.value.substring(pre.value.indexOf(":") + 1);

                            criterias[criterias.length] = subname + " " + pre.value + " " + objname + " .";
                            items[items.length] = objname;
                            if (obj.value != "") {
                                criterias[criterias.length] = "FILTER REGEX (str(" + objname + "), '" + obj.value + "', 'i')";
                            }
                        }
                        else {
                            if (!clazz[obj.value.substring(obj.value.indexOf(":") + 1)]) {
                                clazz[obj.value.substring(obj.value.indexOf(":") + 1)] = 1;
                                _obj = obj.value.substring(obj.value.indexOf(":") + 1) + 1;
                            }
                            else {
                                number = clazz[obj.value.substring(obj.value.indexOf(":") + 1)] + 1;
                                clazz[obj.value.substring(obj.value.indexOf(":") + 1)] = number
                                _obj = obj.value.substring(obj.value.indexOf(":") + 1) + number;
                            }
                            var objname = "?" + _obj;
                            criterias[criterias.length] = subname + " " + pre.value + " " + objname + " .";
                        }
                    }

                }
                var item = "distinct " + items.join(" ");
                var criteria = criterias.join("\n  ");

                var query = namespaces + "\nSELECT " + item + "\nWHERE\n{\n  " + criteria + "\n}\n";
                var quediv = document.getElementById("query");
                var quetextarea = document.getElementById("sparqlquery");
                quediv.style.visibility = "visible";
                quetextarea.value = query;
                //alert(query);
            }

        </script>
    </HEAD>
    <BODY onload="init()">
        <div id="sparql" style_="width: 800px; margin: 0 auto;">
            <div id="header">SPARQL Query Builder</div>
            <table id="builder" style_="width: 800px">
                <tr>
                    <td width="33%">Subject</td>
                    <td width="33%">Predicate</td>
                    <td width="34%">Object</td>
                </tr>
                <tr id="clazz(0)">
                    <td id="subject(0)" width="33%">
                        <select id="subject(0,0)">
                            <option value="">Thing</option>
                        </select>
                    </td>
                    <td id="predicate(0)">

                    </td>
                    <td id="object(0)" width="34%">

                    </td>
                </tr>
            </table>
            <div><input type="button" value="Generate Query" onclick="genQuery();"/></div>
            <br/>
            <div id="query" style="visibility:hidden;">
                <form action="sparqlquery.jsp" target="_new" method="post">
                    <textarea id="sparqlquery" name="sparqlquery" rows="20" cols="111"></textarea>
                    <br/>
                    <input type="submit" value="Query"/>
                </form>
            </div>
        </div>
    </BODY>
</HTML>