<%@page import="com.hp.hpl.jena.graph.Node"%>
<%@page import="com.hp.hpl.jena.vocabulary.RDF"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,java.util.*,org.semanticwb.base.util.*,com.hp.hpl.jena.ontology.*,com.hp.hpl.jena.rdf.model.*"%>
<%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String suri=request.getParameter("suri");
    String prop=request.getParameter("prop");
    String hash=request.getParameter("hash");
    String val=request.getParameter("val");
    String act = request.getParameter("act");
    if(act==null) act="";

    SemanticOntology ont=(SemanticOntology)session.getAttribute("ontology");
    OntResource res=ont.getRDFOntModel().getOntResource(suri);
    OntProperty p=ont.getRDFOntModel().getOntProperty(prop);

    System.out.println("suri:"+suri);
    System.out.println("prop:"+prop);
    System.out.println("hash:"+hash);
    System.out.println("val:"+val);

    if(prop!=null)
    {
        if(act.equals("add")||act.equals(""))
        {
            System.out.print("Add prop value...");
            if(prop.endsWith("#comment"))
            {
                System.out.println("comment");
                res.addComment(val, "es");
            }
            else if(prop.endsWith("#label"))
            {
                System.out.println("label");
                res.addLabel(val, "es");
            }
            else
            {
                System.out.print("rango:");
                if(p!=null&&p.getRDFType().isLiteral())
                {
                    System.out.println("Literal :");
                    res.addLiteral(p, Boolean.valueOf(val));
                }
                else
                {

                    System.out.println("DataType :");
                    res.addProperty(p, val);
                }
            }
        }
        else if(act.equals("remove"))
        {
            String nuri = request.getParameter("nuri");
            System.out.println("Remove prop value...");

            Iterator<Statement>  it = res.listProperties(p);
            while (it.hasNext())
            {

                Statement stmt=it.next();
                RDFNode node=stmt.getObject();
                if(node.toString().equals(nuri))
                {
                    res.removeProperty(p, node);
                    break;
                }

            }


            /*
            if(prop.endsWith("comment"))
                res.removeComment(val,"es");
            else if(prop.endsWith("label"))
                res.removeLabel(val,"es");
            else
            {
                Resource res2remove = ont.getResource(val);
                res.removeProperty(p, (RDFNode)res2remove.asNode());
                System.out.println("remueve nodo:");
            }
            */
        }

    } 
%>
<jsp:forward page="resourceInfo.jsp">
    <jsp:param name="suri" value="<%=suri%>" />
</jsp:forward>
OK