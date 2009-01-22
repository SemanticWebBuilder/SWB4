<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

<%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String suri=request.getParameter("suri");
    String act=request.getParameter("act");
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

    //TODO:
    //User user=SWBContext.getDefaultRepository().getUserByLogin("admin");
    User user=SWBPortal.getSessionUser();

    if(obj!=null && act!=null && user!=null && user.getURI()!=null)
    {
        if(act.equals("active"))
        {
            UserFavorites fav=user.getUserFavorites();
            if(fav==null)
            {
                fav=user.getUserRepository().createUserFavorites();
                user.setUserFavorites(fav);
            }
            fav.addObject(obj);
            out.println(obj.getSemanticClass().getDisplayName(lang)+" fue agregado...");
            out.println("<script type=\"text/javascript\">");
            out.println("   addItemByURI(mfavoStore, null, '"+obj.getURI()+"');");
            out.println("   updateTreeNodeByURI('"+suri+"');");
            out.println("</script>");
        }else
        {
            UserFavorites fav=user.getUserFavorites();
            if(fav!=null)
            {
                fav.removeObject(obj);
            }
            out.println(obj.getSemanticClass().getDisplayName(lang)+" fue eliminado...");
            out.println("<script type=\"text/javascript\">");
            out.println("   removeTreeNode(mfavoStore, getItem(mfavoStore,'"+suri+"'),false);");
            out.println("   updateTreeNodeByURI('"+suri+"');");
            out.println("</script>");

        }
    }
%>
<!-- a href="#" onclick="submitUrl('/swb/swb',this); return false;">click</a -->