<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%!

    public void getAllChilds(List list, SemanticObject obj)
    {
        list.add(obj);
        Iterator<SemanticObject> it=obj.listHerarquicalChilds();
        while(it.hasNext())
        {
            SemanticObject ch=it.next();
            getAllChilds(list,ch);
        }
    }


%>
<%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String suri=request.getParameter("suri");
    //System.out.println("suri:"+suri);
    if(suri==null)
    {
        String code=SWBUtils.IO.readInputStream(request.getInputStream());
        System.out.println(code);
        String uri=SWBContext.getAdminWebSite().getHomePage().getEncodedURI();
%>
    Error params not found...
    <a href="?suri=<%=uri%>">edit</a>
<%
        return;
    }

    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject obj=ont.getSemanticObject(suri);
    if(obj!=null)
    {
        String type=obj.getSemanticClass().getDisplayName(lang);
        ArrayList list=new ArrayList();
        getAllChilds(list, obj);
        obj.remove();
        out.println(type+" fue eliminado...");
        Iterator<SemanticObject> it=list.iterator();
        while(it.hasNext())
        {
            SemanticObject ch=it.next();
            out.println("<script type=\"text/javascript\">removeTreeNodeByURI('"+ch.getURI()+"');</script>");
        }
    }
    //out.println(obj.getDisplayName(lang)+" "+act);
%>
<!-- a href="#" onclick="submitUrl('/swb/swb',this); return false;">click</a -->