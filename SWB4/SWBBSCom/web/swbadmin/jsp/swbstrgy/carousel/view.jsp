<%@page import="java.util.Date"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.User"%><%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.ResourceSubType"%><%@page import="java.util.HashMap"%><%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%><%@page import="org.semanticwb.portal.resources.sem.genericCalendar.Event"%>
<%@page import="java.util.Iterator"%><%@page import="org.semanticwb.portal.resources.sem.genericCalendar.ResourceCalendar"%>
<%@page import="org.semanticwb.portal.api.SWBResource"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.model.Resource"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%!
    class ResourceComparator implements Comparator<Resource>
    {

        public int compare(Resource o1, Resource o2)
        {
            Date d1 = o1.getCreated();
            Date d2 = o2.getCreated();
            if (d1 == null)
            {
                d1 = o1.getCreated();
            }
            if (d2 == null)
            {
                d2 = o2.getCreated();
            }
            if (d1 != null && d2 != null)
            {
                return d2.compareTo(d1);
            } else
            {
                return o1.getIndex() >= o2.getIndex() ? 1 : -1;
            }
        }
    }
%>
<%
    User user = paramRequest.getUser();
    String lang = user.getLanguage();
    WebPage topic = paramRequest.getWebPage();
    if (topic == null)
    {
        return;
    }
    HashMap params = (HashMap) paramRequest.getArguments();
    if (params == null)
    {
        return;
    }
    WebSite site = topic.getWebSite();
    if (site == null)
    {
        return;
    }
    String bannertype = (String) params.get("bannersubtype");
    ResourceSubType stype = site.getResourceSubType(bannertype);
    if (stype == null)
    {
        return;

    }
    List<Resource> resources2 = new ArrayList<Resource>();
    Iterator<Resource> it = stype.listResources();
    int contResources = 0;
    while (it.hasNext())
    {
        Resource res = it.next();
        if (!res.isDeleted() && res.isActive() && user.haveAccess(res) && res.getAttribute("img") != null && res.evalFilterMap(topic))
        {
            contResources++;
            resources2.add(res);
        }
    }
    Collections.sort(resources2, new ResourceComparator());

%>

                <div class="carousel slide" id="carousel-983111">

                    <ol class="carousel-indicators">
                        <li class="active" data-slide-to="0" data-target="#carousel-983111"></li>
    <%
    int i = 1;
    while (i < contResources)
        {
    %>
    
                    <li data-slide-to="<%=i%>" data-target="#carousel-983111"></li>
                               
    <%
        i++;
    }
    %>
                    </ol>
                    <div class="carousel-inner">
 <% int j = 0;
        for (Resource res2 : resources2)
        {
                String title = SWBUtils.TEXT.encodeExtendedCharacters( res2.getTitle(lang)==null?res2.getTitle():res2.getTitle(lang) );
                String img = SWBPortal.getWebWorkPath() + res2.getWorkPath() + "/" + res2.getAttribute("img");
                String desc = SWBUtils.TEXT.encodeExtendedCharacters(res2.getDescription());
    %>
                    <div class="item<%=j==0 ? " active" : ""%>">
			<img alt="" src="<%=img%>" class="img-responsive">
                            <div class="carousel-caption">
				<h4><%=title%></h4>
				<p><%=desc%>
				</p>
                            </div>
                    </div>
    <%  j++;
    }
    %>    
                </div>
                <a class="left carousel-control" href="#carousel-983111" data-slide="prev">
		<span class="glyphicon glyphicon-chevron-left"></span>
		</a>
		<a class="right carousel-control" href="#carousel-983111" data-slide="next">
		<span class="glyphicon glyphicon-chevron-right"></span>
		</a>
            </div><!-- carousel -->

