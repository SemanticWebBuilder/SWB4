<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache");

    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject obj=ont.getSemanticObject(request.getParameter("suri"));
    SemanticObject newp=ont.getSemanticObject(request.getParameter("newp"));
    SemanticObject oldp=ont.getSemanticObject(request.getParameter("oldp"));

    System.out.println("suri;"+obj+" newp:"+newp+" oldp:"+oldp);

    Iterator<SemanticProperty> it=obj.getSemanticClass().listInverseHerarquicalProperties();
    while(it.hasNext())
    {
        SemanticProperty prop=it.next();
        if(obj.getObjectProperty(prop)==oldp)
        {
            boolean ret=false;
            if(prop.isInverseOf())
            {
                if(prop.getCardinality()==1)
                {
                    if(newp!=null)obj.setObjectProperty(prop, newp);
                    else obj.removeProperty(prop);
                }else
                {
                    obj.removeObjectProperty(prop, oldp);
                    if(newp!=null)obj.addObjectProperty(prop, newp);
                }
                ret=true;
            }else if(prop.hasInverse())
            {
                if(prop.getCardinality()==1)
                {
                    oldp.removeObjectProperty(prop.getInverse(), obj);
                    newp.setObjectProperty(prop.getInverse(), obj);
                }else
                {
                    oldp.removeObjectProperty(prop.getInverse(),obj);
                    newp.addObjectProperty(prop.getInverse(), obj);
                }
                ret=true;
            }
            if(ret)
            {
                out.println("Elelemento fué movido...");
                return;
            }
        }
    }
    throw new Exception("No se encontro el elemento...");
%>