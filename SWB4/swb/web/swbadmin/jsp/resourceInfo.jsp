<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,java.util.*,org.semanticwb.base.util.*,com.hp.hpl.jena.ontology.*,com.hp.hpl.jena.rdf.model.*"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    String suri=request.getParameter("suri");

    //System.out.println("suri:"+suri);
    String pathView=SWBPlatform.getContextPath()+"/swbadmin/jsp/resourceTab.jsp";

    //out.println("suri:"+suri);
    SemanticOntology ont=(SemanticOntology)session.getAttribute("ontology");
    Resource res=SWBPlatform.JENA_UTIL.getBaseModelResource(suri, ont.getRDFOntModel());
    //out.println("res:"+res);
    boolean isbase=SWBPlatform.JENA_UTIL.isInBaseModel(res,ont.getRDFOntModel());

    //System.out.println("isbase:"+isbase);

%>

<form id="<%=suri%>/form" dojoType="dijit.form.Form" class="swbform">
    <input type="hidden" name="suri" value="<%=suri%>"/>
    <fieldset>
        <table>
            <tr>
                <td width="200px" align="right">
                    <label>Name: </label>
                </td>
                <td>
<%
    if(isbase)
    {
        out.println("<input type='text'  dojoType='dijit.form.TextBox' style='width:400px;font-size:12;' value='"+SWBPlatform.JENA_UTIL.getId(res)+"'/>");
    }else
    {
        out.println("<span>"+SWBPlatform.JENA_UTIL.getLink(res,pathView)+"</span>");
    }
%>
                </td>
            </tr>
        </table>
    </fieldset>
    <fieldset>
        <legend>Datos de la Clase</legend>
        <table>
<%
    //System.out.println("paso 2");
    Iterator<Property> it=SWBPlatform.JENA_UTIL.getClassProperties(res, ont.getRDFOntModel());
    while(it.hasNext())
    {
        Property prop=it.next();
        OntProperty oprop=null;
        try{oprop=(OntProperty)prop.as(OntProperty.class);}catch(Exception noe){}
        //System.out.println("paso 3:"+prop+" "+oprop);
        out.print("<tr><td width=\"200px\" align=\"right\"><label>"+SWBPlatform.JENA_UTIL.getLink(prop,pathView)+"&nbsp;</label></td><td><span>");
        if(isbase)
        {
            out.print("<a href=\"\">add empty</a>");
            if(oprop!=null && oprop.isObjectProperty())
            {
                out.print(" | ");
                out.print("<a href=\"\">add existing</a>");
                out.print(" | ");
                out.print("<a href=\"\">create and add</a>");
            }
        }
        out.print("</span></td></tr>");
        Iterator<Statement> itp=res.listProperties(prop);
        if(itp.hasNext())
        {
            while(itp.hasNext())
            {
                Statement stmt=itp.next();
                RDFNode node=stmt.getObject();
                String val="";
                String link1=null;
                String link2="";
                if(node.isResource())
                {
                    val=SWBPlatform.JENA_UTIL.getId(stmt.getResource());
                    link1=SWBPlatform.JENA_UTIL.getLink(stmt.getResource(),pathView);
                    link2=SWBPlatform.JENA_UTIL.getLink(stmt.getResource(),"view",pathView);
                }else
                {
                    val=stmt.getString();
                    String lang=stmt.getLanguage();
                    if(lang!=null && lang.length()>0)val+=" {@"+lang+"}";
                }
                if(isbase)
                {
                    out.println("<tr><td width=\"200px\" align=\"right\"></td><td><input type='text' dojoType='dijit.form.ValidationTextBox' style=\"width:400px;font-size:12;\" value='"+val+"'/> <a href=\"\">remove</a> "+link2+" </td></tr>");
                }else
                {
                    if(link1!=null)
                    {
                        out.println("<tr><td width=\"200px\" align=\"right\"></td><td><span>"+link1+"</span></td></tr>");
                    }else
                    {
                        out.println("<tr><td width=\"200px\" align=\"right\"></td><td><span>"+val+"</span></td></tr>");
                    }
                }
                //if(itp.hasNext())out.println("<br/>");
            }
        }else
        {
            if(isbase)
            {
                out.println("<tr><td width=\"200px\" align=\"right\"></td><td>");
                out.print("<input type='text' dojoType='dijit.form.TextBox' style=\"width:400px;font-size:12;\" value='' onChange=\"submitUrl('/swb/swbadmin/jsp/resourceUpdProp.jsp?");
                out.print("suri="+URLEncoder.encode(res.getURI()));
                out.print("&prop="+URLEncoder.encode(prop.getURI()));
                out.print("&val='+this.value+'");
                out.println("');\"/>");
                out.println("<a href=\"\">remove</a></td></tr>");
            }else
            {
                out.println("<tr><td width=\"200px\" align=\"right\"></td><td></td></tr>");
            }
        }
    }
%>
        </table>
    </fieldset>
</form>
