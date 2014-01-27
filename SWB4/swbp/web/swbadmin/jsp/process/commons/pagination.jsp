<%@page import="org.semanticwb.portal.api.SWBResourceURL"%><%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
String pNum = request.getParameter("p");

int pageNum = 1;
int maxPages = 1;
if (request.getAttribute("maxPages") != null) maxPages = (Integer) request.getAttribute("maxPages");

if (pNum != null && !pNum.trim().equals("")) {
    pageNum = Integer.valueOf(pNum);
    if (pageNum > maxPages) {
        pageNum = maxPages;
    }
}

boolean showPageOfPage = request.getParameter("showPageOfPage")!= null ? true : false;

SWBResourceURL nav = paramRequest.getRenderUrl();

//Set mode from parameter
String navUrlMode = request.getParameter("navUrlMode");
if (navUrlMode != null && navUrlMode.length() > 0) nav.setMode(navUrlMode);

//Set params from parameter
String [] navParams = request.getParameterValues("navUrlParams");
if (navParams != null && navParams.length > 0) {
    for (int i = 0; i < navParams.length; i++) {
        String str = navParams[i];
        if (str != null && str.contains("|")) {
            String [] strSplit = str.split("\\|");
            if (strSplit.length == 2) {
                nav.setParameter(strSplit[0], strSplit[1]);
            }
        }
    }
}

%>
<div class="swbp-pagination">
    <%
    if (showPageOfPage) {
        %>
        <span class="swbp-pagination-info pull-left"><%=paramRequest.getLocaleString("pagPage")%> <%=pageNum%> <%=paramRequest.getLocaleString("pagDelim")%> <%=maxPages%></span>
        <%
    }
    if (maxPages > 1) {%>
        <div class="swbp-pagination-nav pull-right">
            <ul class="pagination pagination-sm">
                <%
                int pagSlice = 5;
                int sliceIdx = 1;
                int start = 1;
                int end = pagSlice * sliceIdx;

                if (pageNum > end) {
                    do {
                        sliceIdx++;
                        end = pagSlice * sliceIdx;
                    } while(pageNum > end);
                }
                end = pagSlice * sliceIdx;

                if (end > maxPages) {
                    end = maxPages;
                }

                start = (end-pagSlice)+1;
                if (start < 1) {
                    start = 1;
                }

                if (sliceIdx != 1) {
                    nav.setParameter("p", String.valueOf(pageNum-1));
                    %><li><a href="<%=nav%>">&laquo;</a></li><%
                }

                for(int k = start; k <= end; k++) {
                    nav.setParameter("p", String.valueOf(k));
                    %>
                    <li <%=(k==pageNum?"class=\"active\"":"")%>><a href="<%=nav%>"><%=k%></a></li>
                    <%
                }

                if (end < maxPages) {
                    nav.setParameter("p", String.valueOf(pageNum+1));
                    %><li><a href="<%=nav%>">&raquo;</a></li><%
                }
                %>
            </ul>
        </div>
    <%
    }
    %>
</div>