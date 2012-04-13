<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.social.Videoable"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

<%
WebSite wsite=paramRequest.getWebPage().getWebSite();
String action=paramRequest.getAction();
SWBResourceURL url=paramRequest.getRenderUrl();
SWBResourceURL urlAction=paramRequest.getActionUrl();

if(action.equals("step4"))
{
    String toPost=request.getParameter("toPost");

    String socialUri="";
    Enumeration <String> enumParams=request.getParameterNames();
    while(enumParams.hasMoreElements())
    {
         String paramName=enumParams.nextElement();
         if(paramName.startsWith("snetcta_")) //Es una red social a la cual se desea enviar el post
         {
             if(socialUri.trim().length()>0) socialUri+="|";
             socialUri+=paramName.substring(paramName.indexOf("snetcta_")+8);
         }
    }
    
    if(toPost.equals("msg"))
    {
    }else if(toPost.equals("photo"))
    {
    }if(toPost.equals("video"))
    {
        %>
            <%@include file="videoable/videoable.jsp" %>
        <%
    }
}else if(action.equals("step3"))
{
     url.setAction("step4");
     %>
     <form action="<%=url%>" method="post">
         <ul><b>Seleccione las redes sociales a las cuales desea enviar el post</b></ul>
     <%
     Enumeration <String> enumParams=request.getParameterNames();
     while(enumParams.hasMoreElements())
     {
         String paramName=enumParams.nextElement();
         if(paramName.startsWith("snet_")) //Es una red social a la cual se desea enviar el post
         {

             String snetUri=paramName.substring(paramName.indexOf("snet_")+5);
             String snetName=snetUri;
             try{
                snetName=snetUri.substring(snetUri.indexOf("#")+1);
             }catch(Exception e){snetName=snetUri;}
             SemanticObject semObj=wsite.getSemanticModel().getSemanticObject(snetUri);
             %>
             <ul>Red Social:<b><%=snetName%></b>
             <%
             SemanticClass swbClass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(semObj.getURI());
             Iterator<SemanticObject> itSebObj=wsite.getSemanticObject().getModel().listInstancesOfClass(swbClass);
             while(itSebObj.hasNext())
             {
                 SemanticObject semObjInstance=itSebObj.next();
                 %>
                    <li>
                        <input type="checkbox" name="snetcta_<%=semObjInstance.getURI()%>" > <%=semObjInstance.getProperty(wsite.swb_title)%>
                    </li>
                 <%
             }
             %>
                </ul>
             <%
         }
     }
     %>
     <input type="hidden" name="toPost" value="<%=request.getParameter("toPost")%>">
     <input type="submit" name="send" value="Enviar">
     </form>
     <%
}else if(action.equals("step2"))
{
    url.setAction("step3");
    String toPost=request.getParameter("toPost");
    ArrayList <String> aSocialNets=new ArrayList();
    %>
    <form name="formStep2" action="<%=url%>" method="post">
    <%
    Iterator <SocialNetWork> itSocialNets=SocialNetWork.ClassMgr.listSocialNetWorks(wsite);
    while(itSocialNets.hasNext())
    {
        SocialNetWork socialNet=itSocialNets.next();
        if(toPost.equals("msg") && socialNet instanceof Messageable)
        {
           if(!aSocialNets.contains(socialNet.getSemanticObject().getSemanticClass().getURI())) aSocialNets.add(socialNet.getSemanticObject().getSemanticClass().getURI());
        }else if(toPost.equals("photo") && socialNet instanceof Photoable)
        {
           if(!aSocialNets.contains(socialNet.getSemanticObject().getSemanticClass().getURI())) aSocialNets.add(socialNet.getSemanticObject().getSemanticClass().getURI());
        }else if(toPost.equals("video") && socialNet instanceof Videoable)
        {
           if(!aSocialNets.contains(socialNet.getSemanticObject().getSemanticClass().getURI())) aSocialNets.add(socialNet.getSemanticObject().getSemanticClass().getURI());
        }
    }
    if(aSocialNets.size()>0)
    {
        Iterator<String> itArraySocialNets=aSocialNets.iterator();
        while(itArraySocialNets.hasNext())
        {
            String strSocialNet=itArraySocialNets.next();
            String tmpstrSocialNet=strSocialNet;
            try{
                tmpstrSocialNet=strSocialNet.substring(strSocialNet.lastIndexOf("#")+1);
            }catch(Exception e){
                tmpstrSocialNet=strSocialNet;
            }
            %>
                <input type="checkbox" name="snet_<%=strSocialNet%>" ><%=tmpstrSocialNet%>
            <%
        }
    }
    %>
    <input type="hidden" name="toPost" value="<%=request.getParameter("toPost")%>">
    <input type="submit" name="send" value="Enviar">
    </form>
    <%
}
else
{
    url.setAction("step2");
%>
<form name="formStep1" action="<%=url%>">
    Que quieres postear?
    <input type="radio" name="toPost" value="msg">Mensaje
    <input type="radio" name="toPost" value="Photo">Foto
    <input type="radio" name="toPost" value="video">Video
    <input type="submit" name="send" value="Enviar">
</form>
<%
}
%>



