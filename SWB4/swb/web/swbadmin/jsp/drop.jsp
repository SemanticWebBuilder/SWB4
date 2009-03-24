<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<!--
    String lang="es";
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");

    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject obj=ont.getSemanticObject(request.getParameter("suri"));
    SemanticObject newp=ont.getSemanticObject(request.getParameter("newp"));
    SemanticObject oldp=ont.getSemanticObject(request.getParameter("oldp"));

    //System.out.println("suri;"+obj+" newp:"+newp+" oldp:"+oldp);

    SemanticProperty oprop=null;
    SemanticProperty nprop=null;

    Iterator<SemanticProperty> it=obj.getSemanticClass().listInverseHerarquicalProperties();
    while(it.hasNext())
    {
        SemanticProperty prop=it.next();
        if(oprop==null && obj.getObjectProperty(prop)==oldp)
        {
            oprop=prop;
            if(prop.getRangeClass().equals(newp.getSemanticClass()))
            {
                nprop=prop;
            }
        }
        if(nprop==null && prop.getRangeClass().equals(newp.getSemanticClass()))
        {
            nprop=prop;
        }
    }

    if(oprop!=null && nprop!=null)
    {
        boolean ret=false;
        if(oprop.isInverseOf())
        {
            if(oprop.getCardinality()==1)
            {
                obj.removeProperty(oprop);
            }else
            {
                obj.removeObjectProperty(oprop, oldp);
            }
        }else if(oprop.hasInverse())
        {
            SemanticProperty iprop=oprop.getInverse();
            if(iprop.getCardinality()==1)
            {
                oldp.removeProperty(iprop);
            }else
            {
                oldp.removeObjectProperty(iprop,obj);
            }
        }
        if(nprop.isInverseOf())
        {
            if(nprop.getCardinality()==1)
            {
                if(newp!=null)obj.setObjectProperty(nprop, newp);
            }else
            {
                if(newp!=null)obj.addObjectProperty(nprop, newp);
            }
            ret=true;
        }else if(nprop.hasInverse())
        {
            SemanticProperty iprop=nprop.getInverse();
            if(iprop.getCardinality()==1)
            {
                newp.setObjectProperty(iprop, obj);
            }else
            {
                newp.addObjectProperty(iprop, obj);
            }
            ret=true;
        }
        if(ret)
        {
            out.println("Elemento fué movido...");
            return;
        }
    }
    throw new Exception("No se encontro el elemento...");
-->
<%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache");

    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject obj=ont.getSemanticObject(request.getParameter("suri"));
    SemanticObject newp=ont.getSemanticObject(request.getParameter("newp"));
    SemanticObject oldp=ont.getSemanticObject(request.getParameter("oldp"));

    System.out.println("suri;"+obj+" newp:"+newp+" oldp:"+oldp);


    SemanticProperty oprop=null;
    SemanticProperty nprop=null;

    Iterator<SemanticProperty> it=obj.getSemanticClass().listInverseHerarquicalProperties();
    while(it.hasNext())
    {
        SemanticProperty prop=it.next();
        if(oprop==null && obj.getObjectProperty(prop)==oldp)
        {
            oprop=prop;
            if(newp==null || prop.getRangeClass().equals(newp.getSemanticClass()))
            {
                nprop=prop;
            }
        }
        if(nprop==null && prop.getRangeClass().equals(newp.getSemanticClass()))
        {
            nprop=prop;
        }
    }
    System.out.println("oprop:"+oprop+" nprop:"+nprop);

    if(oprop!=null && nprop!=null)
    {
        boolean ret=false;
        if(oprop.isInverseOf())
        {
            if(oprop.getCardinality()==1)
            {
                if(newp==null || oprop!=nprop)obj.removeProperty(oprop);
            }else
            {
                obj.removeObjectProperty(oprop, oldp);
            }
        }else if(oprop.hasInverse())
        {
            SemanticProperty iprop=oprop.getInverse();
            if(iprop.getCardinality()==1)
            {
                if(newp==null || oprop!=nprop)oldp.removeProperty(iprop);
            }else
            {
                oldp.removeObjectProperty(iprop,obj);
            }
        }
        if(nprop.isInverseOf())
        {
            if(nprop.getCardinality()==1)
            {
                if(newp!=null)obj.setObjectProperty(nprop, newp);
            }else
            {
                if(newp!=null)obj.addObjectProperty(nprop, newp);
            }
            ret=true;
        }else if(nprop.hasInverse())
        {
            SemanticProperty iprop=nprop.getInverse();
            if(iprop.getCardinality()==1)
            {
                if(newp!=null)newp.setObjectProperty(iprop, obj);
            }else
            {
                if(newp!=null)newp.addObjectProperty(iprop, obj);
            }
            ret=true;
        }
        if(ret)
        {
            out.println("Elemento fué movido...");
            return;
        }
    }
    throw new Exception("No se encontro el elemento...");


/*
    it=obj.getSemanticClass().listInverseHerarquicalProperties();
    while(it.hasNext())
    {
        SemanticProperty prop=it.next();
        if(obj.getObjectProperty(prop)==oldp)
        {
            System.out.println("prop:"+prop+" car:"+prop.getCardinality()+" isInverseOf:"+prop.isInverseOf()+" hasInverse:"+prop.hasInverse()+" inv:"+prop.getInverse());

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
    throw new Exception("No se movio el elemento...");
 */
%>