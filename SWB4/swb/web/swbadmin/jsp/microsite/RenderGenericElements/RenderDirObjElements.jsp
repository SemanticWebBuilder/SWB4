<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.net.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%!
    private static final String VER_COMENTARIOS="Ver comentarios";
    private static final String OCULTAR_COMENTARIOS="Ocultar comentarios";
%>

<%
    DirectoryObject mse = (DirectoryObject) request.getAttribute("DirectoryObject");
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    if (paramRequest == null) return;

    User mem = paramRequest.getUser();
    String suri = request.getParameter("uri");
    //String abusedDesc = mse.isAbused() ? "Inapropiado" : "Apropiado";
    String abusedDesc ="Inapropiado";
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

    rank = (int) Math.round(Math.floor(mse.getRank()));
    SWBResourceURL url = paramRequest.getActionUrl();

    url.setAction("vote");
    url.setMode(paramRequest.getMode());
    url.setCallMethod(SWBResourceURL.Call_DIRECT);
%>

<link rel='stylesheet' type='text/css' href="<%=SWBPortal.getContextPath()%>/swbadmin/jsp/microsite/css/ciudad_digital.css" />
<link rel='stylesheet' type='text/css' media='all' href="<%=SWBPortal.getContextPath()%>/swbadmin/js/dojo/dojox/form/resources/Rating.css" />
<script type="text/javascript" src="<%=SWBPortal.getContextPath()%>/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false"></script>
<script type="text/javascript">
  dojo.require("dojo.parser");
  dojo.require("dojox.form.Rating");
</script>

<script type="text/javascript">
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
    function cancelComment()
    {
        document.getElementById("comentario").value="";
        document.getElementById("addComment").style.display="none";
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

<h2>Evaluaci&oacute;n</h2>
<div class="common_funcs" style="padding:10px;">
    <div style="float:left; width:200px;">
        <%if (mem.isSigned()) {%>
            <div class="rank_label" style="margin-left:8px;">
                Calificar:
            </div>
            <div class="rank_stars" dojoType="dojox.form.Rating" numStars="5" value="<%=rank%>">
                <script type="dojo/event" event="onChange">vote(this.value);</script>
            </div>
        <%} else {%>
            <div class="rank_label" style="margin-left:8px;">
                Calificaci&oacute;n:
            </div>
            <div class="rank_stars" dojoType="dojox.form.Rating" numStars="5" value="<%=rank%>">
                    <script type="dojo/event" event="_onMouse">return;</script>
                    <script type="dojo/event" event="onStarClick">return;</script>
            </div>
        <%
        }
        %>
    </div>
    <div class="rec_votes">
        <div class="rec_votes_num" id="reviews"><%=mse.getReviews()%></div>
        <div class="rec_votes_label"> votos</div>
    </div>
    <span class="abused">P&uacute;blicamente <span id="abused"><%=abusedDesc%></span></span>
    <%--if (mem.isSigned() && !mse.isAbused()) {%>
        <div class="clearL"></div>
        <div id="markop">
            <div class="editarInfo"><p><a class="userTool" onclick="javascript:changeAbusedState();" href="#">Marcar como inapropiado</a></p></div>
        </div>
    <%}--%>

    <%if (mem.isSigned()) {%>
        <div class="clearL"></div>
        <div id="markop">
            <div class="editarInfo"><p><a class="userTool" onclick="javascript:changeAbusedState();" href="#">Marcar como inapropiado</a></p></div>
        </div>
    <%}%>
</div>
<%if (!mem.isSigned()) {%>
    <br/><br/>
<%}%>
<h2>Comentarios</h2>
<div class="commentBox">
    <!--p-->
    <%
    url.setAction("addComment");
    url.setCallMethod(SWBResourceURL.Call_CONTENT);
    if (mem.isSigned()) {%>
        <a class="userTool" href="javascript:addComment()">Escribir comentario</a>
    <%
    } if (mse.listComments() != null && mse.listComments().hasNext()) {%>
        <a class="userTool" id="ctrlComments" href="javascript:showComments();"><%=OCULTAR_COMENTARIOS%></a>
    <%
    }
    url.setAction("addComment");
    url.setCallMethod(SWBResourceURL.Call_CONTENT);
    if (mem.isSigned()) {%>
        <div id="addComment">
            <form name="addCommentForm" id="addCommentForm" action="<%=url%>" method="post">
                <div>
                    <label for="comentario">Comentario</label>
                    <input type="hidden" name="uri" value="<%=suri%>">
                    <input type="hidden" name="act" value="addComment">
                    <textarea style="border:1px solid #CACACA;" name="comentario" id="comentario" cols="45" rows="5"></textarea>
                </div>
            </form>
            <!--p-->
                <a class="userTool" href="javascript:sendComment()">Publicar</a>
                <a class="userTool" href="javascript:cancelComment()">Cancelar</a>
            <!--/p-->
        </div>
   <%}%>
</div>
<%
request.setAttribute("page",pageNumber);
request.setAttribute("suri",suri);

if (mse.listComments() != null && mse.listComments().hasNext()) {%>
    <jsp:include flush="true" page="ListDirObjComments.jsp"></jsp:include>
<%} else if (!mem.isSigned()) {
%><p>A&uacute;n no hay comentarios, reg&iacute;strese para comentar</p><%
}%>