<%@page contentType="text/html"%>
<%@page import="java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String urladd = paramRequest.getRenderUrl().setParameter("act", "add").toString();
            boolean canadd = member.canAdd();
            String suscribeURL = paramRequest.getActionUrl().setParameter("act", "subcribe").toString();
            String unSuscribeURL = paramRequest.getActionUrl().setParameter("act", "unsubcribe").toString();
%>

<div class="columnaIzquierda">
    <div class="adminTools">
        <%
            if (canadd)
            {
        %>
        <a class="adminTool" href="<%=urladd%>">Agregar noticia</a>
        <%
            }
            if (wputil != null && member.canView())
            {
                if (!wputil.isSubscribed(member))
                {
        %>
        <a class="adminTool" href="<%=suscribeURL%>">Suscribirse</a>
        <%
                    }
                    else
                    {
        %>
        <a class="adminTool" href="<%=unSuscribeURL%>">Cancelar suscripción</a>
        <%
                        }
                    }


        %>



    </div>
    <%
            Iterator<NewsElement> eit = NewsElement.listNewsElementByNewsWebPage(wpage, wpage.getWebSite());
            while (eit.hasNext())
            {
                NewsElement anew = eit.next();
                if (anew.canView(member))
                {
                    SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", anew.getURI());
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
                    String rank = df.format(anew.getRank());
    %>
    <div class="noticia">
        <img src="<%= SWBPortal.getWebWorkPath() + anew.getNewsThumbnail()%>" alt="<%= anew.getTitle()%>">
        <div class="noticiaTexto">
            <h2><%=anew.getTitle()%></h2>
            <p>&nbsp;<br>Por:<%=anew.getAuthor()%><br><%=dateFormat.format(anew.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(anew.getCreated(), user.getLanguage())%></p>
            <p>
                <%=anew.getDescription()%> | <a href="<%=viewUrl%>">Ver más</a>
            </p>
            <p class="stats">
            	Puntuación: <%=rank%><br>
                <%=anew.getViews()%> vistas
            </p>
        </div>
    </div>
    <%
                }
            }
    %>


    <!-- <div id="paginacion">
        <a href="#"><img src="images/pageArrowLeft.gif" alt="anterior"></a> <a href="#">1</a><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a> <a href="#"><img src="images/pageArrowRight.gif" alt="siguiente"></a>
    </div >-->
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
    if (member.canAdd()) {
%>
    <br />
    <div class="editarInfo">
        <p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar noticia</a></p>
    </div>
<%
        if(wputil != null && member.canView()) {
            if(!wputil.isSubscribed(member)) {
%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "subcribe").toString()%>">Suscribirse</a></p></div>
<%
            }else {
%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "unsubcribe").toString()%>">Cancelar suscripción</a></p></div>
<%
            }
        }
    }
%>

<br /><br />
<div id="entriesWrapper">
<%
    Iterator<NewsElement> eit = NewsElement.listNewsElementByNewsWebPage(wpage, wpage.getWebSite());
    while (eit.hasNext()) {
        NewsElement anew = eit.next();
        if(anew.canView(member)) {
            SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", anew.getURI());
%>

<div class="entry">
    <a href="<%= viewUrl%>">
        <img src="<%= SWBPortal.getWebWorkPath()+anew.getNewsThumbnail() %>" alt="<%= anew.getTitle()%>" border="0" />
    </a>
    <div class="entryInfo">
        <p class="tituloNaranja"><%= anew.getTitle()%>&nbsp;(<%= anew.getCitation()%>)</p>
        <p>Por:&nbsp;<strong><%= anew.getAuthor()%></strong></p>
        <p class="eventoInicio">
            <strong><%= dateFormat.format(anew.getCreated()) %></strong> - <%=SWBUtils.TEXT.getTimeAgo(anew.getCreated(), user.getLanguage())%>
        </p>
        <p><%= anew.getDescription()%>&nbsp;|&nbsp;<a href="<%=viewUrl%>">Ver m&aacute;s</a></p>
        <p>Puntuación:&nbsp;<%= anew.getRank()%></p>
        <p><%= anew.getViews()%> vistas.</p>
        <div class="clear">&nbsp;</div>
    </div>
</div>
<%
        }
    }
%>

</div>

--%>