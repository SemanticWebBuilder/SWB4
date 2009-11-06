<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%@page import="org.semanticwb.model.Resource" %>
<%@page import="java.text.*,org.semanticwb.portal.SWBFormMgr"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);
            String urlAddPhoto = paramRequest.getRenderUrl().setParameter("act", "add").toString();
%>
<script type="text/javascript">
    dojo.require("dojox.image.Lightbox");
    dojo.require("dojo.parser");
</script>

<div class="columnaIzquierda">
    <div class="adminTools">
        <a class="adminTool" href="<%=urlAddPhoto%>">Agregar foto</a>
        <a class="adminTool" href="#">Administrar</a>
        <a class="adminTool" href="#">Administrar</a>
    </div>
    <%
            Iterator<PhotoElement> it = PhotoElement.listPhotoElementByPhotoWebPage(wpage, wpage.getWebSite());
            int i = 0;
            while (it.hasNext())
            {
                PhotoElement photo = it.next();
                if (photo.canView(member))
                {
                    String postAuthor = photo.getCreator().getFullName();
                    SWBResourceURL urlDetail = paramRequest.getRenderUrl();
                    urlDetail.setParameter("act", "detail");
                    urlDetail.setParameter("uri", photo.getURI());
                    String created = SWBUtils.TEXT.getTimeAgo(photo.getCreated(), user.getLanguage());
                    SWBResourceURL viewurl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", photo.getURI());
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
                    String rank = df.format(photo.getRank());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    %>
    <div class="noticia">
        <img src="<%=SWBPortal.getWebWorkPath() + photo.getPhotoThumbnail()%>" alt="<%=photo.getTitle()%>">
        <div class="noticiaTexto">
            <h2><%=photo.getTitle()%></h2>
            <p>&nbsp;<br>Por:<%=postAuthor%><br><%=dateFormat.format(photo.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(photo.getCreated(), user.getLanguage())%></p>
            <p>
                <%=photo.getDescription()%> | <a href="<%=viewurl%>">Ver más</a>
            </p>
            <p class="stats">
            	Puntuación: <%=rank%><br>
                <%=photo.getViews()%> vistas
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

<%-- <div class="columnaIzquierda">
    <%
            if (member.canAdd())
            {
    %>

    <div class="editarInfo">
        <p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar foto</a></p>
    </div>

    <%
                    if (member.canView())
                    {
                        if (!wputil.isSubscribed(member))
                        {
    %>
    <div class="editarInfo">
        <p><a href="<%= paramRequest.getActionUrl().setParameter("act", "subscribe").toString()%>">Suscribirse</a></p>
    </div>
    <%
                }
                else
                {
    %>
    <div class="editarInfo">
        <p><a href="<%= paramRequest.getActionUrl().setParameter("act", "unsubscribe").toString()%>">Cancelar suscripción</a></p>
    </div>
    <%
                    }
                }
            }%>

    <br /><br />
    <div class="listaFotos">
        <%
            Iterator<PhotoElement> it = PhotoElement.listPhotoElementByPhotoWebPage(wpage, wpage.getWebSite());
            int i = 0;
            while (it.hasNext())
            {
                PhotoElement photo = it.next();
                if (photo.canView(member))
                {
                    String postAuthor = photo.getCreator().getFullName();
                    SWBResourceURL urlDetail = paramRequest.getRenderUrl();
                    urlDetail.setParameter("act", "detail");
                    urlDetail.setParameter("uri", photo.getURI());
                    String created = SWBUtils.TEXT.getTimeAgo(photo.getCreated(), user.getLanguage());
                    SWBResourceURL viewurl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", photo.getURI());
        %>
        <div class="entry_listadoFotos">
            <a dojoType="dojox.image.Lightbox" title="<%= photo.getTitle()%>" href="<%= SWBPortal.getWebWorkPath() + photo.getImageURL()%>">
                <img id="img_<%=i + base.getId()%>" src="<%= SWBPortal.getWebWorkPath() + photo.getPhotoThumbnail()%>" alt="<%= photo.getTitle()%>" border="0" />
            </a>
            <a href="<%=viewurl%>"></a>
            <p class="tituloFoto"><%= photo.getTitle()%></p>
            <p class="tituloFoto"><%=postAuthor%></p>
            <p class="tituloFoto"><%= created%></p>
            <p class="autor-visitasFoto"><span class="autorFoto"><%= photo.getCreator().getFirstName()%></span>&nbsp;|&nbsp;<%= photo.getViews()%> vistas</p>
            <div class="vermasFloat"><p class="vermas"><a href="<%=urlDetail%>">Ver más</a></p></div>

        </div>
        <%
                    i++;
                }
            }
        %>
    </div>

    <%

            /*it = PhotoElement.listPhotoElementByPhotoWebPage(wpage, wpage.getWebSite());
            PhotoElement photo = it.next();

            String mapa=null;
            SemanticObject semObject = photo.getSemanticObject();
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_VIEW);
            Iterator<SemanticProperty> itProps=semObject.listProperties();
            System.out.println("afuera");
            while(itProps.hasNext()){
            System.out.println("entra");
            SemanticProperty semProp=itProps.next();
            if(semProp==Geolocalizable.swb_latitude){
            mapa=mgr.renderElement(request, semProp.getName());
            break;
            }
            }
            out.print("<hr>");
            out.print(mapa);*/
    %>
</div>
<div class="columnaCentro">


</div>
--%>