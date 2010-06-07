<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.model.User"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,java.util.*,org.semanticwb.base.util.*,com.hp.hpl.jena.ontology.*,com.hp.hpl.jena.rdf.model.*"%>
<%
    User user=SWBContext.getAdminUser();
    if(user==null)
    {
        response.sendError(403);
        return;
    }
    String lang=user.getLanguage();
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    String suri=request.getParameter("suri");

    String pathView=SWBPlatform.getContextPath()+"/swbadmin/jsp/resourceTab.jsp";

    //out.println(suri+" "+Thread.currentThread().getName());
    SemanticOntology ont=(SemanticOntology)session.getAttribute("ontology");
    SemanticClass cls=new SemanticClass(ont.getRDFOntModel().getOntClass(suri));
%>
    <table width="100%">
        <thead><tr><th>Propiedad</th><th>Tipo</th><th>Rango</th><th>Dominio</th></tr></thead>
    <tbody>
<%
    Property ptype=ont.getRDFOntModel().getProperty(SemanticVocabulary.RDF_TYPE);
    Iterator<SemanticProperty> itp=org.semanticwb.model.SWBComparator.sortSermanticProperties(cls.listProperties());
    //Iterator<Property> itp=cls.getOntClass().listDeclaredProperties();
    while(itp.hasNext())
    {
        SemanticProperty prop=itp.next();
        SemanticClass dom=prop.getDomainClass();
        //if(dom!=null)
        if(dom!=null && (dom.equals(cls) || dom.isSuperClass(cls)))
        {
            //Buscar Tipo
            String type=SWBPlatform.JENA_UTIL.getObjectLink(prop.getRDFProperty(), ptype, ont.getRDFOntModel(), pathView);

            //Buscar Rango
            String rang="";
            SemanticClass rcls=prop.getRangeClass();
            if(rcls!=null)
            {
                rang=SWBPlatform.JENA_UTIL.getLink(rcls.getOntClass(), pathView);
                //rang=rcls.getPrefix()+":"+rcls.getName();
            }else
            {
                if(prop.getRange()!=null)
                {
                    rang=SWBPlatform.JENA_UTIL.getLink(prop.getRange(), pathView);
                    //rang=ont.getRDFOntModel().getNsURIPrefix(prop.getRange().getNameSpace())+":"+prop.getRange().getLocalName();
                }
            }

            boolean direct=dom.equals(cls);
            String style="style=\"font-weight: lighter;\"";

            String domin=SWBPlatform.JENA_UTIL.getLink(dom.getOntClass(), pathView);

            if(direct)style="style=\"font-weight: bolder; background-color: #f0f0ff;\"";
            out.print("<tr>");
            out.print("<td "+style+">"+SWBPlatform.JENA_UTIL.getLink(prop.getRDFProperty() ,pathView)+"</td>");
            out.print("<td "+style+">"+type+"</td>");
            out.print("<td "+style+">"+rang+"</td>");
            out.print("<td "+style+">"+domin+"</td>");
            out.println("</tr>");
        }
    }
%>
    </tbody>
    </table>
