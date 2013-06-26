<%-- 
    Document   : streamJavaCloud
    Created on : 13-jun-2013, 18:23:32
    Author     : jorge.jimenez
--%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.*"%>
<%@page import="org.mcavallo.opencloud.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<html>
<body>
    
<%
    
    System.out.println("Entra Jsp...StreamCloud-J1:"+request.getParameter("suri")+",wordJ:"+request.getParameter("word")); 
    
    Stream stream=null;
    
    if(request.getParameter("suri")!=null) {
         stream = (Stream)SemanticObject.getSemanticObject(request.getParameter("suri")).getGenericInstance();   
    }else if(request.getParameter("sID")!=null && request.getParameter("wsite")!=null) 
    {
        WebSite wsite=WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
        stream = Stream.ClassMgr.getStream(request.getParameter("sID"), wsite);  
    }
    
    
    
    
    //System.out.println("Stream-id/streamCloudJsp:"+uri);

    
    
    SWBResourceURL url=paramRequest.getRenderUrl();  
    url.setParameter("sID", stream.getId());
    url.setParameter("doView", "1");
    url.setParameter("wsite", stream.getSemanticObject().getModel().getName());
    
    
    SocialAdmin socialAdminSite=(SocialAdmin)SWBContext.getAdminWebSite();
    
    //HashMap<String, Integer> hmWords=new HashMap(); 
    List<Tag> listTags=new ArrayList<Tag>();
    Iterator <PostIn> itPostIns=stream.listPostInStreamInvs();
    itPostIns = SWBComparator.sortByCreatedSet(itPostIns, false).iterator();
    while(itPostIns.hasNext()) 
    {
        if(listTags.size()>=50) break; 
        PostIn postIn=itPostIns.next();
        //System.out.println("postInJ-1:"+postIn); 
        if(postIn.getTags()!=null)
        {
            String[] tags=postIn.getTags().split("\\ ");  //Dividir valores
            //System.out.println("postInJ-tags:"+tags); 
            for(int i=0;i<tags.length;i++)
            {
               String word=tags[i]; 
               if(!Arrays.asList(SWBSocialUtil.Classifier.getSpanishStopWords()).contains(word))
               {
                    if(!isInteger(word))
                    {
                        Tag tag = new Tag(word);   
                        listTags.add(tag);
                    }
               }
            }
        }
        if(postIn.getMsg_Text()!=null)
        {
            String msg=postIn.getMsg_Text();
            //System.out.println("postInJ-getMsg_Text-1:"+msg); 
            //TODO:ver si mejor desde que se clasifica el mensaje, se guarda en alguna nueva propiedad
            //el mensaje normalizado, replaceSpecialCharacters y removePuntualSigns.
            //msg=SWBSocialUtil.Classifier.normalizer(msg).getNormalizedPhrase();
            msg=SWBSocialUtil.Strings.replaceSpecialCharacters(msg);
            msg=SWBSocialUtil.Strings.removePuntualSigns(msg, socialAdminSite);
            
            //System.out.println("postInJ-getMsg_Text-2:"+msg); 
            String[] mgsWords=msg.split("\\ ");  //Dividir valores
            for(int i=0;i<mgsWords.length;i++)
            {
               String word=mgsWords[i]; 
               if(!Arrays.asList(SWBSocialUtil.Classifier.getSpanishStopWords()).contains(word))
               {
                    if(!isInteger(word))
                    {
                        Tag tag = new Tag(word);   
                        listTags.add(tag);
                    }
               }
            }
        }
    }
    
    Cloud cloud = new Cloud();  // create cloud
    cloud.setMaxWeight(82.0);   // max font size
    cloud.setMaxTagsToDisplay(50);  // the displayed tag cloud will be composed by at most 50 tags
    cloud.setTagCase(cloud.getTagCase().PRESERVE_CASE);
    cloud.setDefaultLink(url.toString()+"&word=%s");  


    cloud.addTags(listTags);
%>   
<div>
<%
// cycle through output tags
for (Tag tagD : cloud.tags()) {
    // add an HTML link for each tag
%>
    <a href="<%= tagD.getLink() %>" class="nube" style="font-size: <%= tagD.getWeight() %>px;"><%= tagD.getName() %></a>
<%

}

%>
</div>


</body>
</html>


<%!

    public static boolean isInteger(String s) {
    try { 
        Integer.parseInt(s); 
    } catch(NumberFormatException e) { 
        return false; 
    }
    // only got here if we didn't return false
    return true;
}
    
%>