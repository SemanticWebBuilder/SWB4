<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%!
    private static final int COMMENTS_IN_PAGE = 5;
%>
<%
        MicroSiteElement mse=(MicroSiteElement)request.getAttribute("MicroSiteElement");
        SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
        WebPage webPage  = paramRequest.getWebPage();
        Member mem = Member.getMember(paramRequest.getUser(), webPage);
        long lpage=(Long)request.getAttribute("page");
        
        Iterator iterator = mse.listComments();        
        int ordinal = 0;        
        long firstInPage = ((lpage - 1) * COMMENTS_IN_PAGE) + 1;
        long lastInPage = lpage * COMMENTS_IN_PAGE;

        iterator = SWBComparator.sortByCreated(iterator, false);

        while (iterator.hasNext()) {
            Comment comment = (Comment) iterator.next();
            ordinal++;
            
            if (ordinal < firstInPage) {
                continue;
            } else if (ordinal > lastInPage) {
                break;
            }

            String spamMark = (comment.isSpam() ? "Es spam" : "Marcar como spam");
            %>
            <div id="comment<%=comment.getId()%>" class="comment-entry">
            <div class="comment-head">
            <div class="comment-info">
            <%=ordinal%>.
            <%
            
            try {
                if (comment.getCreator().getPhoto()!=null) {
                    %>
                    <img src="<%=SWBPlatform.getWebWorkPath()%><%=comment.getCreator().getPhoto()%>" alt="foto" width="50px" height="50px" border="0">&nbsp;
                    <%
                }
            } catch (NullPointerException npe) {}
            //out.write("<span class="comment-auth">");
            %>
            <span class="comment-auth">
            <%
            try {
                if (!comment.getCreator().getFullName().equals("")) {
                    %>
                    <%=comment.getCreator().getFullName()%>
                    <%
                    
                } else {
                    %>
                    Desconocido
                    <%
                    
                }
            } catch (NullPointerException npe) {
                %>
                    Desconocido
                    <%
            }
            %>
            </span>        </div>
            <span class="comment-time"><%=SWBUtils.TEXT.getTimeAgo(comment.getCreated(), mem.getUser().getLanguage())%></span>
            <%
            
            
            if (mem.canView()) {
                %>
                <span class="comment-spam"><a href="javascript:spam(<%=comment.getId()%>)" id="spamMark<%=comment.getId()%>"><%=spamMark%></a></span>
                <%
                
            } else if (comment.isSpam()) {
                %>
                <span class="comment-spam"><%=spamMark%></span>
                <%
                
            }
            %>
            </div>
            <div id="comment_body_<%=comment.getId()%>">");
            <div class="comment-body">
            <div><%=comment.getDescription()%></div>");
            </div>
            </div>
            </div>
            <%
           
        }

%>

 
