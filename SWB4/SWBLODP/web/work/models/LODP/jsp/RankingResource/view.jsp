<%-- 
Document   : view
Created on : 24/05/2013, 01:05:00 PM
Author     : rene.jara
--%>
<%@page import="org.semanticwb.portal.SWBResourceMgr"%>
<%@page import="java.util.Iterator"%>
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
            var tag=dojo.byId("votacion");
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
    <div class="ranking">
    <div class="valorar">
        <!-- h4 --><%=paramRequest.getLocaleString("lblTitle")%><!-- /h4 -->
    </div>
    <div id="votacion">
        <div id="vota1" title="vota 1" onclick="javascript:rank('1'); return false;"><span>vota 1</span></div>
        <div id="vota2" title="vota 2" onclick="javascript:rank('2'); return false;"><span>vota 2</span></div>
        <div id="vota3" title="vota 3" onclick="javascript:rank('3'); return false;"><span>vota 3</span></div>
        <div id="vota4" title="vota 4" onclick="javascript:rank('4'); return false;"><span>vota 4</span></div>
        <div id="vota5" title="vota 5" onclick="javascript:rank('5'); return false;"><span>vota 5</span></div>
        <div id="votar"></div>
    </div>
</div>
    <%
      }else{
%>
<div class="ranking">
    <div class="valorar">
        <!-- h4 --><%=paramRequest.getLocaleString("lblTitle")%><!-- /h4 -->
    </div>
   <div id="votacion">
        <img src="/work/models/LODP/css/images/star-<%=average >= 1?"on":"off"%>.png" width="15" height="14" alt="*">
        <img src="/work/models/LODP/css/images/star-<%=average >= 2?"on":"off"%>.png" width="15" height="14" alt="*">
        <img src="/work/models/LODP/css/images/star-<%=average >= 3?"on":"off"%>.png" width="15" height="14" alt="*">
        <img src="/work/models/LODP/css/images/star-<%=average >= 4?"on":"off"%>.png" width="15" height="14" alt="*">
        <img src="/work/models/LODP/css/images/star-<%=average >= 5?"on":"off"%>.png" width="15" height="14" alt="*">
    </div>
</div>
    <%
                    }
                }
    %>