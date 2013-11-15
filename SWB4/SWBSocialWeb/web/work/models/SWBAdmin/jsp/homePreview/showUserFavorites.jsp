<%-- 
    Document   : showUserFavorites
    Created on : 01-oct-2013, 15:27:56
    Author     : jorge.jimenez
--%>

<%@page import="java.lang.reflect.Array"%>
<%@page import="org.semanticwb.social.util.SocialLoader"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.json.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.social.SocialFlow.SocialPFlowMgr"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>


<%
    User user = (User)request.getAttribute("user");  
    UserFavorite fav = user.getUserFavorite();
    HashMap sSite = new HashMap();
    ArrayList streamArray = new ArrayList();
    ArrayList rssArray = new ArrayList();
    ArrayList socialTopicArray = new ArrayList();
    ArrayList socialNetworkArray = new ArrayList();
    if (fav != null) {
%>

<ul>
    <%
        Iterator<SemanticObject> ite = SWBComparator.sortSermanticObjects(fav.listObjects());
        while (ite.hasNext()) {
            SemanticObject objw = ite.next();
            String favorites = "";
            if (objw.getGenericInstance() instanceof SocialSite) {
                SocialSite socialSite = (SocialSite) objw.getGenericInstance();
                System.out.println("<--------socialSite" + socialSite);
                sSite.put(socialSite.getURI(), cad(socialSite.getURI(), fav));
            }

        }
        Iterator it = sSite.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            String s = e.getKey().toString();
            System.out.println("s" + s);
            SemanticObject objS = SemanticObject.createSemanticObject(s);
            SocialSite socialSite = (SocialSite) objS.getGenericInstance();
    %>
    <h3>
        <a href="javascript:parent.addNewTab('<%=socialSite.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(socialSite.getDisplayTitle(user.getLanguage()))%>');">
            <img class="swbIconSocialSite" src="/swbadmin/css/images/trans.png"/>  
            <%=socialSite.getTitle()%>
        </a>
    </h3>

    <%
        ArrayList list = (ArrayList) e.getValue();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            String uri = i.next().toString();
            SemanticObject obj = SemanticObject.createSemanticObject(uri);

            if (obj.getGenericInstance() instanceof Stream) {
                Stream stream = (Stream) obj.getGenericInstance();
    %>
    <li>
        <a href="javascript:parent.addNewTab('<%=stream.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(stream.getDisplayTitle(user.getLanguage()))%>');">
            <img class="swbIconStream" src="/swbadmin/css/images/trans.png"/><%=stream.getTitle()%>
        </a>
    </li>
    <%
    } else if (obj.getGenericInstance() instanceof SocialTopic) {
        SocialTopic socialTopic = (SocialTopic) obj.getGenericInstance();
    %>
    <li>
        <a href="javascript:parent.addNewTab('<%=socialTopic.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(socialTopic.getDisplayTitle(user.getLanguage()))%>');">
            <img class="swbIconSocialTopic" src="/swbadmin/css/images/trans.png"/>  <%=socialTopic.getTitle()%>
        </a>
    </li>
    <%
    } else if (obj.getGenericInstance() instanceof Rss) {
        Rss rss = (Rss) obj.getGenericInstance();
    %>
    <li>
        <a href="javascript:parent.addNewTab('<%=rss.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(rss.getDisplayTitle(user.getLanguage()))%>');">
            <img class="swbIconRss" src="/swbadmin/css/images/trans.png"/><%=rss.getTitle()%>
        </a>
    </li>
    <%
    } else if (obj.getGenericInstance() instanceof SocialNetwork) {
        SocialNetwork socialNet = (SocialNetwork) obj.getGenericInstance();
    %>
    <li>
        <a href="javascript:parent.addNewTab('<%=socialNet.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(socialNet.getDisplayTitle(user.getLanguage()))%>');">
            <%if (socialNet instanceof Facebook) {
                    System.out.println("es facebook");%>
            <img class="swbIconFacebook" src="/swbadmin/css/images/trans.png"/>
            <%} else if (socialNet instanceof Twitter) {
                System.out.println("es twitter");%>
            <img class="swbIconTwitter" src="/swbadmin/css/images/trans.png"/>
            <%} else if (socialNet instanceof Youtube) {
                System.out.println("es youtube");%>
            <img class="swbIconYouTube" src="/swbadmin/css/images/trans.png"/>
            <%}%>                       
            <%=socialNet.getTitle()%>
        </a>
    </li>
    <%           }
        }
    %>


    <%
            }
        }


    %>
</ul>

<%!
    public ArrayList cad(String cadena, UserFavorite fav) {
        ArrayList list = new ArrayList();
        Iterator<SemanticObject> ite = SWBComparator.sortSermanticObjects(fav.listObjects());
        while (ite.hasNext()) {
            SemanticObject obj = ite.next();
            
            if (obj.getGenericInstance() instanceof Stream) {
                Stream streamA = (Stream) obj.getGenericInstance();
                SocialSite social = streamA.getSocialSite();
                String uri = social.getURI();
                System.out.println("uri stream" + uri);
                if (uri.equals(cadena)) {
                    list.add(streamA.getURI());
                }
            }
            if (obj.getGenericInstance() instanceof SocialNetwork) {
                SocialNetwork socialNetA = (SocialNetwork) obj.getGenericInstance();
                String social = socialNetA.getSemanticObject().getModel().getModelObject().getURI();
                //SemanticObject
                System.out.println("SOCIALNETWORK" + social);
                if (cadena.equals(social)) {
                    list.add(socialNetA.getURI());
                }

            }
            if (obj.getGenericInstance() instanceof Rss) {
                Rss rssA = (Rss) obj.getGenericInstance();
                SocialSite social = rssA.getSocialSite();
                String uri = social.getURI();
                System.out.println("uri rss" + uri);
                if (uri.equals(cadena)) {
                    list.add(rssA.getURI());
                }

            }
            if (obj.getGenericInstance() instanceof SocialTopic) {
                SocialTopic social = (SocialTopic) obj.getGenericInstance();
                SocialSite socialS = social.getSocialSite();
                String uri = socialS.getURI();
                System.out.println("uri rss" + uri);
                if (uri.equals(cadena)) {
                    list.add(socialS.getURI());
                }

            }
        }

        /*
        Iterator i = list.iterator();
        while (i.hasNext()) {
            System.out.println("++++++" + i.next());
        }*/

        System.out.println("-------------->cade" + list);
        return list;
    }
    
    
    public HashMap favoritesSocialSite(HashMap hm, SemanticObject sobj){
        
        HashMap map = new HashMap();       
        
        return map;
    }
%>