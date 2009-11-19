<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.net.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%!
    private static final String VER_COMENTARIOS="Ver comentarios";
    private static final String OCULTAR_COMENTARIOS="Ocultar comentarios";
%>
<%
        MicroSiteElement mse=(MicroSiteElement)request.getAttribute("MicroSiteElement");
        SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
        if(paramRequest==null)
        {
            return;
        }
        WebPage webPage  = paramRequest.getWebPage();
        Member mem = Member.getMember(paramRequest.getUser(), webPage);
        String suri = request.getParameter("uri");        
        //String abusedDesc = mse.isAbused() ? "Inapropiado" : "Apropiado";
        //String abusedDesc ="Inapropiado" ;
        int rank = 0;
        long pageNumber = 0;
        boolean showComments = false;

        try {
            pageNumber = Long.parseLong(request.getParameter("pn"));
            showComments = true;
        } catch (Exception e) {
            pageNumber = 1;
        }

        if (suri == null && this != null) {
            suri = mse.getURI();
        }
        //tmpUrl = "uri=\"+escape('" + suri + "')";
        rank = (int) Math.round(Math.floor(mse.getRank()));
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("vote");
        url.setMode(paramRequest.getMode());
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        %>
        <link rel='stylesheet' type='text/css' href="<%=SWBPortal.getContextPath()%>/swbadmin/jsp/microsite/css/ciudad_digital.css" />
        <script type="text/javascript" src="<%=SWBPortal.getContextPath()%>/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false"></script>
        <link rel='stylesheet' type='text/css' media='all' href="<%=SWBPortal.getContextPath()%>/swbadmin/js/dojo/dojox/form/resources/Rating.css" />
        <script type="text/javascript">
  dojo.require("dojo.parser");
  dojo.require("dojox.form.Rating");
</script>
<script language="javascript" type="text/javascript">  //dojo.require("dojo.parser");
    var request = false;
    try {
        request = new XMLHttpRequest();
    } catch (trymicrosoft) {
        try {
            request = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (othermicrosoft) {
            try {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (failed) {
                request = false;
            }
        }
    }
    if (!request)
    alert("Error al inicializar XMLHttpRequest!");
    var invoke = true;
    var count = 0;

    function vote(val) {
        if (!invoke) return;
        //alert('En funcion para votar');
        var uri='<%=suri%>';
        uri=escape(uri);
        var url = '<%=url%>?act=vote&value='+escape(val)+'&uri='+uri;
        request.open("GET", url, true);
        request.onreadystatechange = ranked;
        request.send(null);
    }

    function ranked() {
      var response = request.responseText;
      if (count == 0) {
        if(request.readyState!=4) return;
        if(request.status==200) {
          if ('Not OK'!=response && ''!=response) {
              var ranking = Math.floor(response.split('|')[0]);
              var votes = response.split('|')[1];
              document.getElementById("reviews").innerHTML = votes;
              invoke = false;
          } else {
              alert('Lo sentimos, ha ocurrido un problema al contabilizar la calificación!');
          }
          count++;
        }
      }
    }
    var invokeAbused = true;

    function changeAbusedState() {
        if (!invokeAbused) return;
        var uri='<%=suri%>';
        uri=escape(uri);
        var url = '<%=url%>?act=abuseReport&uri='+uri;
        request.open("GET", url, true);
        request.onreadystatechange = abusedStateChanged;
        request.send(null);
        document.getElementById("markabused").style.visibility='hidden';
    }

    function abusedStateChanged() {
      if (request.readyState != 4) return;
      if (request.status == 200) {
        var response = request.responseText;
        if ('' != response && 'Not OK' != response) {
          var etiqueta = document.getElementById("abused").innerHTML;
          if (response == 'true') {
            document.getElementById("abused").innerHTML = 'Inapropiado';
            var divmarkup=document.getElementById("markop");
            if(divmarkup)
            {
                divmarkup.style.display='none';
            }
          } else {
            document.getElementById("abused").innerHTML = 'Apropiado';
          }
          invokeAbused = false;
        }
      }
    }
    function sendComment()
    {
        document.addCommentForm.submit();
    }
    function addComment() {
        document.getElementById("addComment").style.display="inline";
    }
    function showComments() {
        var x = document.getElementById("commentsList").style.display;
        if (x == 'none') {
          document.getElementById("commentsList").style.display="inline";
          document.getElementById("ctrlComments").innerHTML="<%=OCULTAR_COMENTARIOS%>";
        } else {
          document.getElementById("commentsList").style.display="none";
          document.getElementById("ctrlComments").innerHTML="<%=VER_COMENTARIOS%>";
        }
    }

    var invokeSpam = true;
    var spamId = 0;

    function spam(commentId) {
        spamId = commentId;
        if (!invokeSpam) return;
        var uri='<%=suri%>';
        uri=escape(uri);
        var url = '<%=url%>?act=spamReport&commentId='+commentId+'&uri='+uri;
        request.open("GET", url, true);
        request.onreadystatechange = spamStateChanged;
        request.send(null);
        document.getElementById("divspamMark"+commentId).style.visibility='hidden';

    }

    function spamStateChanged() {
      if (request.readyState != 4) return;
      if (request.status == 200) {
        var response = request.responseText;
        if ('' != response && 'Not OK' != response) {
          var etiqueta = document.getElementById("spamMark"+spamId).innerHTML;
          if (response == 'false') {
            document.getElementById("spamMark"+spamId).innerHTML = '[marcar como spam]';
          } else {
            document.getElementById("spamMark"+spamId).innerHTML = '[es spam]';
          }
          invokeSpam = false;
        }
      }
      invokeSpam = true;
    }
</script>
    
<div class="common_funcs">
    <span style="float:left; width:200px;"> 
        <div class="rank_label">Calificar:</div>
        <%
        if (mem.canView()) {
        %>
            <div class="rank_stars" dojoType="dojox.form.Rating" numStars="5" value="<%=rank%>">
                <script type="dojo/event" event="onChange">vote(this.value);</script>
            </div>
        <%
        } else {
        %>
            <div class="rank_stars" dojoType="dojox.form.Rating" numStars="5" value="<%=rank%>">
                    <script type="dojo/event" event="_onMouse">return;</script>
                    <script type="dojo/event" event="onStarClick">return;</script>
            </div>
        <%
        }
        %>
    </span> 
    <div class="rec_votes">
        <div class="rec_votes_num" id="reviews"><%=mse.getReviews()%></div>
        <div class="rec_votes_label"> votos</div>
    </div>
    <span class="abused">
        <%
            java.text.DecimalFormat df=new java.text.DecimalFormat("###,###");
            String data=df.format(mse.getAbused());
        %>
        <%=data%> veces marcado como inapropiado
        <span id="abused">
        
        </span>
    </span>
    <%
    
    if (mem.canView() && request.getSession().getAttribute(mse.getURI())==null) {
    %>
        <div class="clearL"></div>
        <div id="markop">            
            <div id="markabused" class="editarInfo"><p><a class="userTool" href="javascript:changeAbusedState();">Marcar como inapropiado</a></p></div>
        </div>
    <%
    }
    %>
</div><br/><br/>
<div class="commentBox">
    <!--p-->
    <%
    url.setAction("addComment");
    url.setCallMethod(SWBResourceURL.Call_CONTENT);
    if (mem.canView()) {
    %>
        <a class="userTool" href="javascript:addComment()">Escribir comentario</a>
    <%
    }
    %>
    <a class="userTool" id="ctrlComments" href="javascript:showComments();"><%=OCULTAR_COMENTARIOS%></a>
    <!--/p-->
    <%
    url.setAction("addComment");
    url.setCallMethod(SWBResourceURL.Call_CONTENT);
    if (mem.canView()) {
    %>
        <div id="addComment">
            <form name="addCommentForm" id="addCommentForm" action="<%=url%>" method="post">
                <div>
                    <label for="comentario">Comentario</label>
                    <input type="hidden" name="uri" value="<%=suri%>">
                    <input type="hidden" name="act" value="addComment">
                    <textarea name="comentario" id="comentario" cols="45" rows="5"></textarea>
                </div>
            </form>
            <!--p-->
                <a class="userTool" href="javascript:sendComment()">Publicar</a>
            <!--/p-->
        </div>
    <%
    }
    %>
</div> 
<h2>Comentarios</h2>
        <%
        request.setAttribute("page",pageNumber);
        request.setAttribute("suri",suri);
        %>        
        <jsp:include flush="true" page="RenderListComments.jsp"></jsp:include>
        <%
%>
