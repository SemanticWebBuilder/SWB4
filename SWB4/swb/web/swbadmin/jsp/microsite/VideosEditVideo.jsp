<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);
%>
<%
        String uri=request.getParameter("uri");
        Video rec=(Video)SemanticObject.createSemanticObject(uri).createGenericInstance();
        if(rec==null)
        {
%>
            Error: Elemento no encontrado...
<%
            return;
        }
%>
<form method="post" action="<%=paramRequest.getActionUrl()%>">
    <div>
        <h3>Añádele el título, descripción y otra información al video que agregaste.</h3>
<!--        
        <p class="last-child"><strong><a href="http://webbuilder.ning.com/video/video/show?id=2034909%3AVideo%3A102">&#171; Cancelar y volver al video</a></strong></p>
-->        
    </div>
    <div>
        <fieldset><legend></legend>
            <div>
                <p>
                    <img  src="<%=rec.getPreview()%>" alt="" width="218"/>
                </p>
            </div>
            <div>
                <div>
                    <p>
                        <label for="video_title">Título:</label><br />
                        <input id="video_title" style="width: 98%;" type="text" class="textfield" size="25" name="video_title" maxlength="200" value="<%=rec.getTitle()%>" />
                    </p>
                    <p>
                        <label for="video_description">Descripción</label><br/>
                        <textarea id="video_description" style="width: 98%" rows="5" cols="23" name="video_description"><%=rec.getDescription()%></textarea>
                     </p>
                     <p>
                        <label for="video_tags">Etiquetas:</label><br />
                        <input id="video_tags" type="text" style="width: 90%;" class="textfield tags" size="22" name="video_tags" value="<%=rec.getTags()%>" maxlength="2000" />
<!--                        
                        <span class="context_help"><a dojoType="ContextHelpToggler" href="#"><img src="/xn_resources/widgets/index/gfx/icon/help.gif" alt="?" title="¿Qué es esto?" /></a>
                            <span class="context_help_popup" style="display:none">
                                <span class="context_help_content">
                                                Tags are short descriptions that let you find your videos. Separate tags with commas or put multiple-word tags in quotes, such as "San Francisco".                                                <small><a dojoType="ContextHelpToggler" href="#">Cerrar</a></small>
                                </span>
                            </span>
                        </span>
-->
                    </p>
                </div>
            </div>
            <div>
                <div>
                    <fieldset>
                        <legend><strong>¿Quién puede ver este video?</strong></legend>
                        <ul class="options">
                            <%String chk="checked=\"checked\"";%>                            
                            <li><label><input type="radio" class="radio" name="level" value="0" <%if(rec.getVisibility()==0)out.println(chk);%>/> Cualquiera</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="1" <%if(rec.getVisibility()==1)out.println(chk);%>/> Sólo los miembros</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="3" <%if(rec.getVisibility()==3)out.println(chk);%>/> Sólo yo</label></li>
                        </ul>
                    </fieldset>
<!--                    
                    <p>
                        <label for="location">Ubicación:</label><br/>
                        <input id="location" style="width: 98%;" type="text" class="textfield" size="30" name="location" maxlength="200" value="" />
                    </p>
                    <div>
                        <div class="locationMap" dojoType="MapItLink" _locationInputId="location" _open="false">
                            <a href="#">Map It</a>
                            <div class="mapbox xg_lightborder" style="display:none;">
                                <div class="errordesc" style="display:none"></div>
                                <div style="width:205px; height:205px;"></div>
                                <p>
                                    <input type="text" class="textfield" />&nbsp;
                                    <input type="button" class="button" value="Find" />
                                </p>
                            </div>
                            <input type="hidden" name="lat" id="video-latInput" value="25"/>
                            <input type="hidden" name="lng" id="video-lngInput" value="-40"/>
                            <input type="hidden" name="zoomLevel" id="video-zoomLevelInput" value="1"/>
                            <input type="hidden" name="locationType" value="skip"/>
                        </div>
                    </div>
-->                    
                </div>
            </div>            
        </fieldset>
        <p>
            <strong><input type="submit" value="Guardar cambios" class="button"/></strong>
            <a class="button" href="<%=paramRequest.getRenderUrl()%>">Cancelar</a>
        </p>
    </div>
    <input type="hidden" name="uri" value="<%=rec.getURI()%>"/>
    <input type="hidden" name="act" value="edit"/>
</form>