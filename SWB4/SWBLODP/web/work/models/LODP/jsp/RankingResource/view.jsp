<%-- 
Document   : view
Created on : 24/05/2013, 01:05:00 PM
Author     : rene.jara
--%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.List"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="com.infotec.lodp.swb.Application"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.base.util.URLEncoder"%>
<%@page import="com.infotec.lodp.swb.resources.RankingResource"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURLImp"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" /><html>
    <%
                WebPage wpage = paramRequest.getWebPage();
                Resource base = paramRequest.getResourceBase();
                String suri = request.getParameter("suri");
                String action= request.getParameter("act");
                SWBResourceURLImp urlrank = new SWBResourceURLImp(request, base, wpage, paramRequest.UrlType_ACTION);
                if (suri != null && !suri.equals("")&& action!=null &&action.equals("detail") ) {
                    urlrank.setCallMethod(paramRequest.Call_DIRECT);
                    urlrank.setMode(RankingResource.Mode_AJAX);
                    urlrank.setAction(RankingResource.Action_RANK);
                    int average = 0;
                    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                    GenericObject gobj = ont.getGenericObject(SemanticObject.shortToFullURI(suri));
                    if (gobj != null && gobj instanceof Application) {
                        Application ap = (Application) gobj;
                        average = Math.round(ap.getAverage());
                    } else if (gobj != null && gobj instanceof Dataset) {
                        Dataset ds = (Dataset) gobj;
                        average = Math.round(ds.getAverage());
                    }
                    boolean canRank = false;
                    List lir = (List) request.getSession().getAttribute("ro");
                    if (lir == null || !lir.contains(suri)) {
                        canRank = true;
                    }
                    if(canRank){
    %>
    <script type="text/javascript">
        //<!--
        function rank(value){
            var tag=dojo.byId("ranking");
            getRanking("<%=urlrank%>?suri=<%=suri%>&rank="+value,tag);
//            getHtml("<%=urlrank%>?suri=<%=suri+"&rank="%>"+value, "ranking", false, false);
        }
      function getRanking(url, tag)
      {
          dojo.xhrGet({
              url: url,
              load: function(response, ioArgs)
              {
                  tag.innerHTML = response;
                  return response;
              },
              error: function(response, ioArgs)
              {
                  if(tag) {
                      tag.innerHTML = "<p>Ocurrió un error con respuesta:<br />" + response + "</p>";
                  }else {
                      alert("No existe ningún elemento con id " + tagid);
                  }
                  return response;
              },
              handleAs: "text"
          });
      }
        // -->
    </script>
    <div id="ranking">
        <ul>
            <li><a href="" onclick="javascript:rank('1'); return false;"><%=average == 1 ? "*" : ""%>1</a></li>
            <li><a href="" onclick="javascript:rank('2'); return false;"><%=average == 2 ? "*" : ""%>2</a></li>
            <li><a href="" onclick="javascript:rank('3'); return false;"><%=average == 3 ? "*" : ""%>3</a></li>
            <li><a href="" onclick="javascript:rank('4'); return false;"><%=average == 4 ? "*" : ""%>4</a></li>
            <li><a href="" onclick="javascript:rank('5'); return false;"><%=average == 5 ? "*" : ""%>5</a></li>
        </ul>
    </div>
    <%
                    }else{
%>
   <div id="ranking">
        <ul>
            <li><%=average == 1?"1":"-"%></li>
            <li><%=average == 2?"2":"-"%></li>
            <li><%=average == 3?"3":"-"%></li>
            <li><%=average == 4?"4":"-"%></li>
            <li><%=average == 5?"5":"-"%></li>
        </ul>
    </div>
    <%
                    }
                }
    %>