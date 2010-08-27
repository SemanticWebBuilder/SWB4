<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.platform.SWBObjectFilter"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.HerarquicalNode"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    StringBuffer ret=new StringBuffer();

    public void addHerarquicalNode(HerarquicalNode node, SemanticObject obj,  ArrayList list, int p)
    {
        for(int x=0;x<p;x++)ret.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        ret.append(" "+node.getId()+"<br>");
        SemanticClass cls=null;
        if(node.getHClass()!=null)cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(node.getHClass().getURI());
        String pf=node.getPropertyFilter();

        Iterator<SemanticObject> it=null;
        if(cls!=null)
        {
            it=SWBObjectFilter.filter(obj.getModel().listInstancesOfClass(cls),pf);
        }else
        {
            it=new ArrayList().iterator();
        }

        Iterator<HerarquicalNode> sit=node.listHerarquicalNodes();
        while(sit.hasNext())
        {
            HerarquicalNode cnode=sit.next();
            //System.out.println("cnode:"+cnode);
            addHerarquicalNode(cnode, obj, list, p+1);
        }

        SemanticProperty herarprop=null;   //Herarquical property;
        //System.out.println(cls);
        if(cls!=null)
        {
            Iterator<SemanticProperty> hprops=cls.listInverseHerarquicalProperties();
            while(hprops.hasNext())
            {
                herarprop=hprops.next();
                //System.out.println("herarprop1:"+herarprop);
            }
        }

        while(it.hasNext())
        {
            SemanticObject so=it.next();
            if(herarprop!=null)
            {
                if(so.getObjectProperty(herarprop)==null)
                {
                    addSemanticObject(so,list,p+1);
                }
            }else
            {
                addSemanticObject(so,list,p+1);
            }
        }
    }

    public void addSemanticObject(SemanticObject obj, ArrayList list, int p)
    {
        list.add(obj);
        for(int x=0;x<p;x++)ret.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        ret.append(" "+obj.getId()+"<br>");

        Iterator<SemanticObject> it=obj.getSemanticClass().listHerarquicalNodes();
        while(it.hasNext())
        {
            HerarquicalNode node=new HerarquicalNode(it.next());
            addHerarquicalNode(node, obj, list, p+1);
        }

        it=obj.listHerarquicalChilds();
        while(it.hasNext())
        {
            SemanticObject child=it.next();
            if(!list.contains(child))
            {
                addSemanticObject(child, list,p+1);
            }else
            {
                ret.append("Cicle: "+child.getURI()+"-->"+obj.getURI()+"<br>");
                //System.out.println(obj.getURI()+"-->"+obj.getURI());
                //throw new RuntimeException(obj.getURI()+"-->"+obj.getURI());
            }
        }
    }


    public void checkPath(SemanticObject obj, ArrayList list)
    {
        if(obj==null)return;
        list.add(obj);
        ret.append(" "+obj.getId());
        //System.out.println(page);
        SemanticObject parent=obj.getHerarquicalParent();
        if(!list.contains(parent))
        {
            checkPath(parent, list);
        }else
        {
            throw new RuntimeException(obj.getURI()+"-.->"+parent.getURI());
        }
    }


    public void checkChilds(WebPage page, ArrayList list)
    {
        list.add(page);
        //checkPath(page,new ArrayList());
        //ret+=" "+page.getId();
        Iterator<WebPage> it=page.listVisibleChilds("es");
        while(it.hasNext())
        {
            WebPage child=it.next();
            if(!list.contains(child))
            {
                checkChilds(child, list);
            }else
            {
                throw new RuntimeException(page.getURI()+"-->"+child.getURI());
            }
        }
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Check Cicles</h1>
<%
    Iterator<WebSite> it=SWBContext.listWebSites();
    while (it.hasNext())
    {
        WebSite site=it.next();
        ret=new StringBuffer();
        addSemanticObject(site.getSemanticObject(), new ArrayList(),0);
        out.println(ret);
    }
%>
    </body>
</html>
