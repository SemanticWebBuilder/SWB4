<%-- 
    Document   : Ranking
    Created on : 19/02/2010, 01:01:20 AM
    Author     : carlos.ramos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*,org.semanticwb.pymtur.MicroSitePyme,org.semanticwb.pymtur.ServiceProvider" %>

<%
    SemanticObject sobj = null;
    WebPage community = null;
    WebPage currentpage = (WebPage) request.getAttribute("webpage");

    if (currentpage instanceof MicroSitePyme) {
        community = currentpage;
    }else {
        community = currentpage.getParent();
    }

    if (community != null) {
        MicroSitePyme ms=(MicroSitePyme)community;
        String uri = ms.getURI();
        if (null != uri) {
            sobj = SemanticObject.createSemanticObject(uri);
        }
        if (sobj!=null && sobj.getGenericInstance() instanceof MicroSitePyme) {
            MicroSitePyme msp = (MicroSitePyme) sobj.getGenericInstance();
            int vote;
            try {
                vote = Integer.parseInt(request.getParameter("rating"));
            }catch (NumberFormatException nfe) {
                vote = 0;
            }
            double rank = msp.getRank();
            long rev = msp.getReviews();
            rank = rank * rev;
            rev++;
            rank = rank + vote;
            rank = rank / rev;
            msp.setRank(rank);
            msp.setReviews(rev);

            SWBResourceURL url = paramRequest.getActionUrl();
            url.setAction("vote");
            url.setMode(paramRequest.getMode());
            url.setCallMethod(SWBResourceURL.Call_DIRECT);
        %>            
            <script type="text/javascript">
                <!--
                dojo.require("dojox.form.Rating");

                var request = false;
                try {
                    request = new XMLHttpRequest();
                }catch (trymicrosoft) {
                    try {
                        request = new ActiveXObject("Msxml2.XMLHTTP");
                    }catch (othermicrosoft) {
                        try {
                            request = new ActiveXObject("Microsoft.XMLHTTP");
                        }catch (failed) {
                            request = false;
                        }
                    }
                }
                if (!request) {
                    alert("Error al inicializar XMLHttpRequest!");
                }
                var invoke = true;
                var count = 0;

                function vote(val) {
                    if(count == 0) {
                        if(!invoke)
                            return;
                        var uri='<%=uri%>';
                        uri=escape(uri);
                        var url = '<%=url%>?act=vote&value='+escape(val)+'&uri='+uri;
                        console.log(url);
                        request.open("GET", url, true);
                        var obj=dijit.byId("rating");
                        obj.onChange=function(){return;};
                        obj._onMouse=function(){return;};
                        obj.onStarClick=function(){return;};
                        count++;
                        request.onreadystatechange = ranked;
                        request.send(null);
                    }else {
                        alert('¡Sólo es posible votar una vez!');
                    }
                }
                function ranked() {
                    if(request.readyState!=4) return;
                    if(request.status==200) {
                        var response = request.responseText;
                        if ('Not OK'!=response && ''!=response) {
                            var ranking = Math.floor(response.split('|')[0]);
                            var votes = response.split('|')[1];
                            var obj=dijit.byId("rank_stars");
                            obj.attr("value",ranking);
                            document.getElementById("reviews").innerHTML = votes;
                            alert('¡Gracias por su voto!');
                            invoke = false;
                        }else {
                            alert('Lo sentimos, ha ocurrido un problema al contabilizar la calificación!');
                        }
                    }
                }

                dojo.addOnLoad(
                    function() {
                        var props = {numStars:5, value:<%=rank%>, onChange:function() { vote(this.value); return; } };
                        new dojox.form.Rating(props,"rating");
                    }
                );
            </script>
            <div class="rank_label">Calificar:</div>
            <div class="rank_stars" id="rating"></div>
        <%
        }
    }
%>
