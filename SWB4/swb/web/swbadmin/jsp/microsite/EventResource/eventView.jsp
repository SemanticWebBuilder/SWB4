<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            String urlViewEvent = paramRequest.getRenderUrl().setParameter("act", "calendar").toString();

%>
<div class="columnaIzquierda">
    <div class="adminTools">
        <a class="adminTool" href="#">Agregar noticia</a><a class="adminTool" href="#">Administrar</a>
    </div>
    <%
        Iterator<EventElement> it = EventElement.listEventElementByEventWebPage(wpage, wpage.getWebSite());
        while (it.hasNext())
        {
            EventElement event = it.next();
            SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", event.getURI());
            if (event.canView(member))
            {
                java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
                String rank = df.format(event.getRank());
    %>
    <div class="noticia">
        <img src="<%=SWBPortal.getWebWorkPath() + event.getEventThumbnail()%>" alt="<%= event.getTitle()%>">
        <div class="noticiaTexto">
            <h2><%=event.getTitle()%></h2>
            <p>&nbsp;<br>Por:<%=event.getCreator().getFullName()%><br><%=dateFormat.format(event.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%></p>
            <p>
                <%=event.getDescription()%> | <a href="<%=viewUrl%>">Ver más</a>
            </p>
            <p class="stats">
            	Puntuación: <%=rank%><br>
                <%=event.getViews()%> vistas
            </p>
        </div>
    </div>
    <%
        }
    }
    %>

    <!--div id="paginacion">
     <a href="#"><img src="images/pageArrowLeft.gif" alt="anterior"></a> <a href="#">1</a><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a> <a href="#"><img src="images/pageArrowRight.gif" alt="siguiente"></a>
				</div-->
</div>
<div class="columnaCentro">
    <div id="calendario">
        <h2><a href="#">&lt;</a> Septiembre <a href="#">&gt;</a></h2>
        <ul class="dias semana">
            <li>D</li><li>L</li><li>M</li><li>M</li><li>J</li><li>V</li><li>S</li>
        </ul>
        <ul class="dias">
            <li>&nbsp;</li> <li>&nbsp;</li> <li>1</li> <li>2</li> <li><a href="#">3</a></li> <li>4</li> <li>5</li>
            <li>6</li> <li>7</li> <li>8</li> <li>9</li> <li>10</li> <li>11</li> <li>12</li>
            <li>13</li> <li>14</li> <li>15</li> <li><a href="#">16</a></li> <li><a href="#">17</a></li> <li>18</li> <li>19</li>
            <li>20</li> <li>21</li> <li>22</li> <li>23</li> <li>24</li> <li>25</li> <li>26</li>
            <li>27</li> <li>28</li> <li>29</li> <li>30</li> <li>&nbsp;</li> <li>&nbsp;</li> <li>&nbsp;</li>
        </ul>
        <div class="clear">&nbsp;</div>
    </div>
    <ul class="miContenido">
        <li><a class="rss" href="#">Suscribirse RSS</a></li>
    </ul>
    <h2>Noticias recientes</h2>
    <ul class="listaElementos">
        <li><a class="autor" href="#">Jei Solis</a> agregó la noticia <a class="elemento">Maremoto en Samoa</a> (03/10/10)</li>
        <li><a class="autor" href="#">Jei Solis</a> agregó la foto <a class="elemento">Cartel de Where The Wild Things Are</a> (03/10/10)</li>
        <li><a class="autor" href="#">Jei Solis</a> agregó la noticia <a class="elemento">Maremoto en Samoa</a> (03/10/10)</li>
        <li><a class="autor" href="#">Jei Solis</a> agregó la foto <a class="elemento">Cartel de Where The Wild Things Are</a> (03/10/10)</li>
    </ul>
</div>
<%--

<%
    if (member.canAdd()) {
%>
<br/>
<div id="panorama">
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "calendar")%>">Ver calendario</a></p></div>
    <div class="editarInfo">
        <p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar evento</a></p>
    </div>

<%
    if( member.canView() ) {
        if(!wputil.isSubscribed(member)) {
%>
    <div class="editarInfo">
        <p><a href="<%= paramRequest.getActionUrl().setParameter("act", "subscribe").toString()%>">Suscribirse</a></p>
    </div>
<%
        }else {
%>
    <div class="editarInfo">
        <p><a href="<%= paramRequest.getActionUrl().setParameter("act", "unsubscribe").toString()%>">Cancelar suscripción</a></p>
    </div>
<%
        }
    }
%>
</div>
<%  }%>

<br/><br/>
<div id="entriesWrapper">
<%
    Iterator<EventElement> it = EventElement.listEventElementByEventWebPage(wpage, wpage.getWebSite());
    while (it.hasNext()) {
        EventElement event = it.next();
        SWBResourceURL viewurl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", event.getURI());
        if (event.canView(member)) {
%>
    <div class="entry">
        <a href="<%=viewurl%>">
            <img src="<%=SWBPortal.getWebWorkPath()+event.getEventThumbnail()%>" alt="<%= event.getTitle()%>" border="0" />
        </a>
        <div class="entryInfo">            
            <p><%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%></p>
            <p class="tituloNaranja"><%=event.getTitle()%></p>
            <p class="eventoInicio">Inicia: <strong><%= (event.getStartDate()==null?"":dateFormat.format(event.getStartDate()))%></strong> a las <strong><%= (event.getStartTime()==null?"":timeFormat.format(event.getStartTime()))%></strong></p>
            <p class="eventoFinal">Termina: <strong><%= (event.getEndDate()==null?"":dateFormat.format(event.getEndDate()))%></strong> a las <strong><%= (event.getEndTime()==null?"":timeFormat.format(event.getEndTime()))%></strong></p>
            <p>Valoraci&oacute;n:<%=event.getRank()%></p>
            <p><%=event.getViews()%> vistas</p>
            <div class="clear">&nbsp;</div>
        </div>
    </div>
<%      }
    }
%>
</div>

--%>