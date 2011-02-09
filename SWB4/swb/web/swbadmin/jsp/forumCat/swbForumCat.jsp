<!--
Creator: Jorge Jiménez
Modified by: Hasdai Pacheco {haxdai@gmail.com}
-->

<%@page import="org.semanticwb.model.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.resources.sem.forumcat.*"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.resources.sem.forumcat.SWBForumCatResource"%>

<script type="text/javascript">
    function addOption(olist, value, text) {
        if (value.length>0) {
                olist[olist.length] = new Option(text, value);
        }
    }

    function clearList(list) {
        for(x = list.length; x >= 0 ; x--) {
            list[x] = null;
        }
    }

    function clearSelected(list) {
        var i = 0;
        var newVals = [];
        for(x = 0; x < list.length; x++) {
            if (list[x].selected == false) {
                newVals[i++] = list[x].value;
            }
        }
        clearList(list);
        
        for(x = 0; x < i; x++) {
            addOption(list, newVals[x], newVals[x]);
        }
    }

    function addToList(olist, ibox) {
        addOption(olist, ibox.value, ibox.value);
        ibox.value="";
    }

    function setReferences(list, field) {
        var refs = "";
        for (x = 0; x < list.length; x++) {
            refs = refs + list[x].value + ",";
        }
        field.value = refs.substring(0,refs.length-1);
        field.form.submit();
    }
</script>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SimpleDateFormat dateFormat;
    SimpleDateFormat iso8601dateFormat;
    DecimalFormat df = new java.text.DecimalFormat("#0.0#");

    String lang = "es";
    Locale locale = new Locale(lang);
    dateFormat = new SimpleDateFormat("yyyy-MMM-dd", locale);
    String[] months = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
    
    java.text.DateFormatSymbols fs = dateFormat.getDateFormatSymbols();
    fs.setShortMonths(months);
    dateFormat.setDateFormatSymbols(fs);
    String defaultFormat = "d 'de' MMMM 'del' yyyy 'a las' HH:mm";
    iso8601dateFormat = new SimpleDateFormat(defaultFormat, locale);

    User user = paramRequest.getUser();
    boolean isAdmin = false;
    Role role = user.getUserRepository().getRole("adminForum");
    UserGroup group = user.getUserRepository().getUserGroup("admin");
    if (role != null && user.hasRole(role) || user.hasUserGroup(group)) {
        isAdmin = true;
    }
    WebPage wpage = paramRequest.getWebPage();
    Resource base = paramRequest.getResourceBase();
    SemanticObject semanticBase = base.getResourceData();
    SWBResourceURL renderURL = paramRequest.getRenderUrl();
    SWBResourceURL pageURL = paramRequest.getRenderUrl();
    SWBResourceURL actionURL = paramRequest.getActionUrl();
    String action = paramRequest.getAction();
    if (request.getParameter("searchAct") != null && request.getParameter("searchAct").equals("showDetail")) {
        action = "showDetail";
    }
    String baseimg = SWBPortal.getWebWorkPath()+"/models/"+wpage.getWebSiteId()+"/css/images/";

    if (action != null && action.equals("add")) {
        SWBFormMgr mgr = new SWBFormMgr(Question.forumCat_Question, base.getSemanticObject(), SWBFormMgr.MODE_CREATE);
        mgr.setLang(user.getLanguage());
        mgr.setFilterRequired(false);
        mgr.setType(mgr.TYPE_XHTML);
        actionURL.setAction("addQuestion");
        SemanticClass semClass = Question.sclass;
        %>
        <div class="foro_gral">
            <form id="formaCaptura" name="datosRegistro" action="<%=actionURL%>" method="post">
                <input type="hidden" name="<%=Question.forumCat_questionReferences.getName()%>">
                <fieldset>
                    <%= mgr.getFormHiddens()%>
                    <div class="sfcLinea">
                        <div class="sfcEtiqueta">
                            <%=mgr.renderLabel(request, semClass.getProperty(Question.forumCat_question.getName()), mgr.MODE_CREATE)%>:
                        </div>
                        <div class="sfcCampo">
                            <%=mgr.renderElement(request, semClass.getProperty(Question.forumCat_question.getName()), mgr.MODE_CREATE)%>
                        </div>
                    </div>
                    <div class="sfcLinea">
                        <div class="sfcEtiqueta">
                            <%=mgr.renderLabel(request, semClass.getProperty(Tagable.swb_tags.getName()), mgr.MODE_CREATE)%>:
                        </div>
                        <div class="sfcCampo">
                            <%=mgr.renderElement(request, semClass.getProperty(Tagable.swb_tags.getName()), mgr.MODE_CREATE)%>
                        </div>
                    </div>
                    <div class="sfcLinea">
                    <% if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_selectCategory)) {%>
                        <div class="sfcEtiqueta">
                            <%=mgr.renderLabel(request, semClass.getProperty(Question.forumCat_webpage.getName()), mgr.MODE_CREATE)%>:
                        </div>
                    </div>
                    <div class="sfcLinea">
                        <select name="categoryuri">
                            <%
                            String pid = semanticBase.getProperty(SWBForumCatResource.forumCat_idCatPage, paramRequest.getWebPage().getId());
                            WebPage wpp = paramRequest.getWebPage().getWebSite().getWebPage(pid);
                            if (wpp != null) {
                                Iterator<WebPage> childs = SWBComparator.sortByDisplayName(wpp.listChilds(), user.getLanguage());
                                while (childs.hasNext()) {
                                    WebPage child = childs.next();
                                    %><option value="<%=child.getURI()%>"><%=child.getTitle()%></option><%
                                }
                            }
                            %>
                        </select>
                    </div>
                </fieldset>
                    <%}%>
                <fieldset>
                    <div class="sfcLinea">
                        <div class="sfcEtiqueta">
                            <label>Referencias a Youtube</label>:<br>
                        </div>
                        <div class="sfcCampo">
                            <input type="text" name="inputBox">
                            <input type="button" value="Agregar" onclick="addToList(this.form.referenceList, this.form.inputBox);"><br>
                            <select multiple name="referenceList"></select>
                            <input type="button" value="Limpiar" onclick="clearList(this.form.referenceList);">
                            <input type="button" value="Limpiar seleccionados" onclick="clearSelected(this.form.referenceList);"><br>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="sfcLinea">
                        <input type="button" class="boton" value="Guardar" onclick="setReferences(this.form.referenceList, this.form.<%=Question.forumCat_questionReferences.getName()%>);">
                        <!--input type="submit" class="boton" value="Guardar"-->
                        <input type="button" class="boton" value="Regresar" onclick="javascript:history.go(-1);">
                    </div>
                </fieldset>
            </form>
        </div>
        <%
    } else if (action != null && action.equals("edit")) {
        int recPerPage = 5; //TODO: poner el numero de registros por paginas en la administracion
        int nRec = 0;
        int nPage;
        try {
            nPage = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignored) {
             nPage = 1;
        }
        boolean paginate = false;
        Iterator itQuestions_uo = (Iterator) request.getAttribute("listQuestions");
        Iterator itQuestions = SWBComparator.sortByCreated(itQuestions_uo, false);
            if (user.isSigned() || semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptGuessComments)) {
                renderURL.setAction("add");
                %>
                <img width="100%" height="11" alt="separacion" src="<%=baseimg%>separa_foro.png">
                <a href="<%=renderURL%>">Publicar un problema</a>
                <img width="100%" height="11" alt="separacion" src="<%=baseimg%>separa_foro.png">
                <%
            }
        
            while (itQuestions.hasNext()) {
                Question question = (Question)itQuestions.next();
                renderURL.setParameter("uri", question.getURI());
                actionURL.setParameter("uri", question.getURI());
                Answer favAnswer = null;
                Answer comAnswer = null;
                int nLike = 0;
                int nUnlike = 0;
                if (question.getQueStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                    paginate = true;
                    nRec++;
                    if ((nRec > (nPage - 1) * recPerPage) && (nRec <= (nPage) * recPerPage)) {
                        String photo = baseimg + "profilePlaceholder.jpg";
                        if (question.getCreator().getPhoto() != null) {
                            photo = SWBPortal.getWebWorkPath() + question.getCreator().getPhoto();
                        }
                        Iterator<Answer> answers = question.listAnswerInvs();
                        int ansVotes = 0;
                        while (answers.hasNext()) {
                            Answer answer = answers.next();
                            if (answer.getAnsStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                                if (answer.isBestAnswer()) {
                                    favAnswer = answer;
                                }

                                Iterator<AnswerVote> itAnswerVote = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(answer);
                                while (itAnswerVote.hasNext()) {
                                    AnswerVote answerVote = itAnswerVote.next();
                                    if (answerVote.isLikeAnswer() && !answerVote.isIrrelevantVote()) {
                                        ansVotes++;
                                    }
                                }

                                if (ansVotes > nLike) {
                                    nLike = ansVotes;
                                    comAnswer = answer;
                                }
                            }
                        }
                        nLike=0;
                        if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionVotable)) {
                            Iterator<QuestionVote> itQuestionVote = QuestionVote.ClassMgr.listQuestionVoteByQuestionVote(question);
                            while (itQuestionVote.hasNext()) {
                                QuestionVote questionVote = itQuestionVote.next();
                                if (questionVote.isLikeVote()) {
                                    nLike++;
                                } else {
                                    nUnlike++;
                                }
                            }
                        }
                        renderURL.setAction("showDetail");
                        renderURL.setParameter("page", request.getParameter("page"));
                        %>
                        <ul>
                            <li>
                                <div class="foto_foro_pregunta"><img width="40" height="40" src="<%=photo%>"></div>
                                <span class="usuario"><%=question.getCreator().getFullName()%>.</span>
                                (<%=SWBUtils.TEXT.getTimeAgo(question.getCreated(), user.getLanguage())%>). <a href="<%=renderURL%>" class="ligapregunta"><%=question.getQuestion()%></a>
                                Vista <%=question.getViews()%> veces.
                                <%
                                if (user.isSigned() || semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptGuessComments)) {
                                    if (!question.isClosed()) {
                                        renderURL.setAction("answerQuestion");
                                        renderURL.setParameter("org", "edit");
                                        %><a href="<%=renderURL%>">Responder a esta pregunta</a>&nbsp;<%
                                    }
                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionSubscription)) {
                                        if (!question.isUserSubscribed(user)) {
                                             actionURL.setAction("subcribe2question");
                                             actionURL.setParameter("org", "edit");
                                             %><a href="<%=actionURL%>">Agregar a mis pendientes</a>&nbsp;<%
                                        }
                                    }
                                    if (user.isSigned() && user.getURI().equals(question.getCreator().getURI())) {
                                        renderURL.setAction("editQuestion");
                                        renderURL.setParameter("org", "edit");
                                        %><a href="<%=renderURL%>">Editar pregunta</a>&nbsp;<%
                                    }
                                    /*if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateQuestions)) {
                                        if (!question.isQueIsApropiate() && !question.getCreator().getURI().equals(user.getURI())) {
                                             actionURL.setAction("markAnswerAsIrrelevant");
                                             actionURL.setParameter("org", "edit");
                                             ><!--a href="<%=actionURL>">Y esto que</a-->&nbsp;
                                        }
                                    }*/
                                }
                                %>
                            </li>
                            <ul>
                                <%
                                if (favAnswer != null) {
                                    String starimg = baseimg;
                                    String alt = "";
                                    int points = getUserPoints(favAnswer.getCreator(), wpage.getWebSite());
                                    if (points == 0) {
                                        starimg += "star_vacia.png";
                                        alt = "Vacía";
                                    } else if (points > 0 && points <= 150) {
                                        starimg += "star_plata.png";
                                        alt = "Plata";
                                    } else if (points >= 151 && points <= 600) {
                                        starimg += "star_oro.png";
                                        alt = "Oro";
                                    } else if (points >= 601) {
                                        starimg += "star_diamante.png";
                                        alt = "Diamante";
                                    }

                                    photo = baseimg + "profilePlaceholder.jpg";
                                    if (favAnswer.getCreator().getPhoto() != null) {
                                        photo = SWBPortal.getWebWorkPath() + favAnswer.getCreator().getPhoto();
                                    }
                                    
                                    if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                        Iterator<AnswerVote> itAnswerVote = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(favAnswer);
                                        while (itAnswerVote.hasNext()) {
                                            AnswerVote answerVote = itAnswerVote.next();
                                            if (answerVote.isLikeAnswer()) {
                                                nLike++;
                                            } else if (!answerVote.isIrrelevantVote()) {
                                                nUnlike++;
                                            }
                                        }
                                    }
                                    %>
                                    <li class="titulo">Mi respuesta favorita</li>
                                    <li class="grupo_respuestas_gral">
                                        <div class="respuestas_gral">
                                            <div class="puntos">
                                                <ul>
                                                    <li><img width="70" height="30" alt="<%=alt%>" title="<%=alt%> - <%=points%> puntos" src="<%=starimg%>"></li>
                                                    <li><%=countUserQuestions(favAnswer.getCreator(), wpage.getWebSite())%> preguntas</li>
                                                    <li><%=countUserAnswers(favAnswer.getCreator(), wpage.getWebSite())%> respuestas</li>
                                                </ul>
                                            </div>
                                            <div class="foto">
                                                <img width="40" height="40" src="<%=photo%>">
                                            </div>
                                            <div class="respuesta_gral">
                                                <span class="usuario"><%=favAnswer.getCreator().getFullName()%></span>
                                                (<%=SWBUtils.TEXT.getTimeAgo(favAnswer.getCreated(), user.getLanguage())%>). <%=favAnswer.getAnswer()%>
                                            </div>
                                            <div class="herramientas_foro_gral">
                                                <ul>
                                                    <%
                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                                        if (!user.getURI().equals(favAnswer.getCreator().getURI()) && !favAnswer.userHasVoted(user)) {
                                                            actionURL.setParameter("uri", favAnswer.getURI());
                                                            actionURL.setAction("voteAnswer");
                                                            actionURL.setParameter("likeVote", "true");
                                                            actionURL.setParameter("org", "edit");
                                                            actionURL.setParameter("page", request.getParameter("page"));
                                                            %>
                                                            <li>
                                                                <a href="<%=actionURL%>"><img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png"></a>(<%=nLike%>)
                                                            </li>
                                                            <%actionURL.setParameter("likeVote", "false");%>
                                                            <li>
                                                                <a href="<%=actionURL%>"><img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png"></a>(<%=nUnlike%>)
                                                            </li>
                                                        } else {
                                                           <li>
                                                                <img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png">(<%=nLike%>)
                                                            </li>
                                                            <li>
                                                                <img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png">(<%=nUnlike%>)
                                                            </li>
                                                        <%
                                                        }
                                                    }
                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markIrrelevantAnswers)) {
                                                        if (!favAnswer.getCreator().getURI().equals(user.getURI()) && !favAnswer.userHasVoted(user)) {
                                                             actionURL = paramRequest.getActionUrl();
                                                             actionURL.setParameter("uri", favAnswer.getURI());
                                                             actionURL.setAction("voteAnswer");
                                                             actionURL.setParameter("irrelevant", "true");
                                                             actionURL.setParameter("org", "edit");
                                                             actionURL.setParameter("page", request.getParameter("page"));
                                                             %>
                                                             <li>
                                                                 ¿Esto qu&eacute;?<a href="<%=actionURL%>"><img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png" ></a>(<%=favAnswer.getAnsIrrelevant()%>)
                                                             </li>
                                                             <%
                                                        } else {
                                                           %>
                                                            <li>
                                                                 ¿Esto qu&eacute;?<img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png">(<%=favAnswer.getAnsIrrelevant()%>)
                                                            </li>
                                                             <%
                                                        }
                                                    }
                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateAnswers)) {
                                                        if (!favAnswer.isAnsIsAppropiate() && !favAnswer.getCreator().getURI().equals(user.getURI())) {
                                                             actionURL.setAction("markAnswerAsInnapropiate");
                                                             actionURL.setParameter("org", "edit");
                                                             %>
                                                             <li>
                                                                 <a href="<%=actionURL%>">Marcar como inapropiado</a>(<%=favAnswer.getAnsInappropriate()%>)
                                                             </li>
                                                             <%
                                                        } else {
                                                           %>
                                                           <li>
                                                                 Inapropiado(<%=favAnswer.getAnsInappropriate()%>)
                                                             </li>
                                                             <%
                                                        }
                                                    }
                                                    %>
                                                </ul>
                                            </div>
                                        </div>
                                    </li>
                                    <%
                                }
                                if (comAnswer != null) {
                                    photo = baseimg + "profilePlaceholder.jpg";
                                    String starimg = baseimg;
                                    String alt = "";
                                    int points = getUserPoints(comAnswer.getCreator(), wpage.getWebSite());
                                    if (points == 0) {
                                        starimg += "star_vacia.png";
                                        alt = "Vacía";
                                    } else if (points > 0 && points <= 150) {
                                        starimg += "star_plata.png";
                                        alt = "Plata";
                                    } else if (points >= 151 && points <= 600) {
                                        starimg += "star_oro.png";
                                        alt = "Oro";
                                    } else if (points >= 601) {
                                        starimg += "star_diamante.png";
                                        alt = "Diamante";
                                    }

                                    if (comAnswer.getCreator().getPhoto() != null) {
                                        photo = SWBPortal.getWebWorkPath() + comAnswer.getCreator().getPhoto();
                                    }
                                    
                                    if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                        Iterator<AnswerVote> itAnswerVote = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(comAnswer);
                                        while (itAnswerVote.hasNext()) {
                                            AnswerVote answerVote = itAnswerVote.next();
                                            if (answerVote.isLikeAnswer()) {
                                                nLike++;
                                            } else if (!answerVote.isIrrelevantVote()) {
                                                nUnlike++;
                                            }
                                        }
                                    }
                                    %>
                                    <li class="titulo">Respuesta favorita de la comunidad</li>
                                    <li class="grupo_respuestas_gral">
                                        <div class="respuestas_gral">
                                            <div class="puntos">
                                                <ul>
                                                    <li><img width="70" height="30" alt="<%=alt%>" title="<%=alt%> - <%=points%> puntos" src="<%=starimg%>"></li>
                                                    <li><%=countUserQuestions(comAnswer.getCreator(), wpage.getWebSite())%> preguntas</li>
                                                    <li><%=countUserAnswers(comAnswer.getCreator(), wpage.getWebSite())%> respuestas</li>
                                                </ul>
                                            </div>
                                            <div class="foto">
                                                <img width="40" height="40" src="<%=photo%>">
                                            </div>
                                            <div class="respuesta_gral">
                                                <span class="usuario"><%=comAnswer.getCreator().getFullName()%></span>
                                                (<%=SWBUtils.TEXT.getTimeAgo(comAnswer.getCreated(), user.getLanguage())%>). <%=comAnswer.getAnswer()%>
                                                <%
                                                if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateAnswers)) {
                                                    if (!comAnswer.isAnsIsAppropiate() && !comAnswer.getCreator().getURI().equals(user.getURI())) {
                                                         actionURL.setAction("markAnswerAsInnapropiate");
                                                         actionURL.setParameter("org", "edit");
                                                         %>  <a href="<%=actionURL%>">Marcar como inapropiado</a>&nbsp;<%
                                                    } else {
                                                       %>- Inapropiado: <%=comAnswer.getAnsInappropriate()%><%
                                                    }
                                                }
                                                %>
                                            </div>
                                            <div class="herramientas_foro_gral">
                                                <ul>
                                                    <%
                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                                        if (!user.getURI().equals(comAnswer.getCreator().getURI()) && !comAnswer.userHasVoted(user)) {
                                                            actionURL.setParameter("uri", comAnswer.getURI());
                                                            actionURL.setAction("voteAnswer");
                                                            actionURL.setParameter("likeVote", "true");
                                                            actionURL.setParameter("org", "edit");
                                                            actionURL.setParameter("page", request.getParameter("page"));
                                                            %>
                                                            <li>
                                                                <a href="<%=actionURL%>"><img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png"></a>(<%=nLike%>)
                                                            </li>
                                                            <%actionURL.setParameter("likeVote", "false");%>
                                                            <li>
                                                                <a href="<%=actionURL%>"><img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png"></a>(<%=nUnlike%>)
                                                            </li>
                                                            <%
                                                        } else {
                                                            %>
                                                           <li>
                                                                <img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png">(<%=nLike%>)
                                                            </li>
                                                            <li>
                                                                <img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png">(<%=nUnlike%>)
                                                            </li>
                                                            <%
                                                        }
                                                    }
                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markIrrelevantAnswers)) {
                                                        if (!comAnswer.getCreator().getURI().equals(user.getURI()) && !comAnswer.userHasVoted(user)) {
                                                             actionURL = paramRequest.getActionUrl();
                                                             actionURL.setParameter("uri", comAnswer.getURI());
                                                             actionURL.setAction("voteAnswer");
                                                             actionURL.setParameter("irrelevant", "true");
                                                             actionURL.setParameter("org", "edit");
                                                             actionURL.setParameter("page", request.getParameter("page"));
                                                             %>
                                                             <li>
                                                                 ¿Esto qu&eacute;?<a href="<%=actionURL%>"><img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png"></a>(<%=comAnswer.getAnsIrrelevant()%>)
                                                             </li>
                                                             <%
                                                        } else {
                                                           %>
                                                           <li>
                                                                 ¿Esto qu&eacute;?<img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png">(<%=comAnswer.getAnsIrrelevant()%>)
                                                            </li>
                                                            <%
                                                        }
                                                    }
                                                %>
                                                </ul>
                                            </div>
                                        </div>
                                    </li>
                                    <%
                                }
                                answers = SWBComparator.sortByCreated(question.listAnswerInvs(), false);
                                ArrayList<Answer> answers_a = new ArrayList<Answer>();
                                while (answers.hasNext()) {
                                    Answer a = answers.next();
                                    if (!a.equals(favAnswer) && !a.equals(comAnswer)) {
                                        answers_a.add(a);
                                    }
                                }

                                answers = answers_a.iterator();
                                if (answers.hasNext()) {
                                    %><li class="titulo">Respuestas</li><%
                                    while (answers.hasNext()) {
                                        Answer answer = answers.next();                                        
                                        if (answer != null && answer.getAnsStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                                            photo = baseimg + "profilePlaceholder.jpg";
                                            String starimg = baseimg;
                                            String alt="";
                                            int points = getUserPoints(answer.getCreator(), wpage.getWebSite());
                                            if (points == 0) {
                                                starimg += "star_vacia.png";
                                                alt = "Vacía";
                                            } else if (points > 0 && points <= 150) {
                                                starimg += "star_plata.png";
                                                alt = "Plata";
                                            } else if (points >= 151 && points <= 600) {
                                                starimg += "star_oro.png";
                                                alt = "Oro";
                                            } else if (points >= 601) {
                                                starimg += "star_diamante.png";
                                                alt = "Diamante";
                                            }
                                            if (answer.getCreator().getPhoto() != null) {
                                                photo = SWBPortal.getWebWorkPath() + answer.getCreator().getPhoto();
                                            }
                                            renderURL.setParameter("uri", answer.getURI());
                                            actionURL.setParameter("uri", answer.getURI());
                                            nLike = 0;
                                            nUnlike = 0;
                                            if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                                Iterator<AnswerVote> itAnswerVote = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(answer);
                                                while (itAnswerVote.hasNext()) {
                                                    AnswerVote answerVote = itAnswerVote.next();
                                                    if (answerVote.isLikeAnswer()) {
                                                        nLike++;
                                                    } else if (!answerVote.isIrrelevantVote()) {
                                                        nUnlike++;
                                                    }
                                                }
                                            }
                                            %>
                                            <li class="grupo_respuestas_gral">
                                            <div class="respuestas_gral">
                                                <div class="puntos">
                                                    <ul>
                                                        <li><img width="70" height="30" alt="<%=alt%>" title="<%=alt%> - <%=points%> puntos" src="<%=starimg%>"></li>
                                                        <li><%=countUserQuestions(answer.getCreator(), wpage.getWebSite())%> preguntas</li>
                                                        <li><%=countUserAnswers(answer.getCreator(), wpage.getWebSite())%> respuestas</li>
                                                    </ul>
                                                </div>
                                                <div class="foto">
                                                    <img width="40" height="40" src="<%=photo%>">
                                                </div>
                                                <div class="respuesta_gral">
                                                    <span><%=answer.getCreator().getFullName()%></span>
                                                    (<%=SWBUtils.TEXT.getTimeAgo(answer.getCreated(), user.getLanguage())%>). <%=answer.getAnswer()%><br>
                                                    <%
                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateAnswers)) {
                                                        if (!answer.isAnsIsAppropiate() && !answer.getCreator().getURI().equals(user.getURI())) {
                                                             actionURL.setAction("markAnswerAsInnapropiate");
                                                             actionURL.setParameter("org", "edit");
                                                             %>  <a href="<%=actionURL%>">Marcar como inapropiado</a>&nbsp;<%
                                                        } else {
                                                           %>- Inapropiado: <%=answer.getAnsInappropriate()%><%
                                                        }
                                                    }
                                                    if (user.isSigned() && question.getCreator().getURI().equals(user.getURI())) {
                                                        if (!answer.getCreator().getURI().equals(user.getURI()) &&  semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markBestAnswer)) {
                                                            actionURL.setAction("bestAnswer");
                                                            actionURL.setParameter("org", "edit");
                                                            %><a href="<%=actionURL%>">Mejor respuesta</a>&nbsp;<%
                                                        }
                                                    }
                                                    %>
                                                </div>
                                                <div class="herramientas_foro_gral">
                                                    <ul>
                                                    <%
                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                                        if (!user.getURI().equals(answer.getCreator().getURI()) && !answer.userHasVoted(user)) {
                                                            actionURL.setParameter("uri", answer.getURI());
                                                            actionURL.setAction("voteAnswer");
                                                            actionURL.setParameter("likeVote", "true");
                                                            actionURL.setParameter("org", "edit");
                                                            actionURL.setParameter("page", request.getParameter("page"));
                                                            %><li>
                                                                <a href="<%=actionURL%>"><img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png"></a>(<%=nLike%>)
                                                            </li>
                                                            <%actionURL.setParameter("likeVote", "false");%>
                                                            <li>
                                                                <a href="<%=actionURL%>"><img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png"></a>(<%=nUnlike%>)
                                                            </li>
                                                            <%
                                                        } else {
                                                           %>
                                                            <li>
                                                                <img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png">(<%=nLike%>)
                                                            </li>
                                                            <li>
                                                                <img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png">(<%=nUnlike%>)
                                                            </li>
                                                            <%
                                                        }
                                                    }
                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markIrrelevantAnswers)) {
                                                        if (!answer.getCreator().getURI().equals(user.getURI()) && !answer.userHasVoted(user)) {
                                                             actionURL = paramRequest.getActionUrl();
                                                             actionURL.setParameter("uri", answer.getURI());
                                                             actionURL.setAction("voteAnswer");
                                                             actionURL.setParameter("irrelevant", "true");
                                                             actionURL.setParameter("org", "edit");
                                                             actionURL.setParameter("page", request.getParameter("page"));
                                                             %>
                                                             <li>
                                                                 ¿Esto qu&eacute;?<a href="<%=actionURL%>"><img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png"></a>(<%=answer.getAnsIrrelevant()%>)
                                                             </li>
                                                             <%
                                                        } else {
                                                           %>
                                                           <li>
                                                                 ¿Esto qu&eacute;?<img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png">(<%=answer.getAnsIrrelevant()%>)
                                                            </li>
                                                             <%
                                                        }
                                                    }
                                                    %>
                                                    </ul>
                                                </div>
                                            </div>
                                        </li>
                                    <%
                                        }
                                    }
                                }
                                %>
                            </ul>
                            <li>
                                <img width="730" height="11" alt="separacion" src="<%=baseimg%>separa_foro.png">
                            </li>
                        </ul>
                        <%
                        }
                    }
                }
                if (paginate) {
                    %>
                    P&aacute;ginas:
                    <%
                    for (int countPage = 1; countPage < (Math.ceil((double) nRec / (double) recPerPage) + 1); countPage++) {
                        pageURL.setParameter("page", "" + (countPage));
                        if (countPage == nPage) {
                            %><%=countPage%>&nbsp;<%
                        } else {
                            %><b><a href="<%=pageURL%>"><%=countPage%></a></b><%
                        }
                    }
                }
    } else if (action != null && action.equals("showDetail")) {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        Question question = (Question) semObject.createGenericInstance();
        long views = question.getViews();
        question.setViews(views + 1);
        if (question.getQueStatus() == SWBForumCatResource.STATUS_ACEPTED) {
            int ansVotes = 0;
            int nLike = 0;
            int nUnlike = 0;
            Answer favAnswer = null;
            Answer comAnswer = null;
            String photo = baseimg + "profilePlaceholder.jpg";
            if (question.getCreator().getPhoto() != null) {
                photo = SWBPortal.getWebWorkPath() + question.getCreator().getPhoto();
            }
            
            renderURL.setParameter("uri", question.getURI());
            actionURL.setParameter("uri", question.getURI());

            Iterator<Answer> answers = question.listAnswerInvs();
            while (answers.hasNext()) {
                Answer answer = answers.next();
                if (answer.getAnsStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                    if (answer.isBestAnswer()) {
                        favAnswer = answer;
                    }

                    Iterator<AnswerVote> itAnswerVote = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(answer);
                    while (itAnswerVote.hasNext()) {
                        AnswerVote answerVote = itAnswerVote.next();
                        if (!answerVote.isIrrelevantVote() && answerVote.isLikeAnswer()) {
                            ansVotes++;
                        }
                    }

                    if (ansVotes > nLike) {
                        nLike = ansVotes;
                        comAnswer = answer;
                    }
                }
            }
            
            nLike=0;
            if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionVotable)) {
                Iterator<QuestionVote> itQuestionVote = QuestionVote.ClassMgr.listQuestionVoteByQuestionVote(question);
                while (itQuestionVote.hasNext()) {
                    QuestionVote questionVote = itQuestionVote.next();
                    if (questionVote.isLikeVote()) {
                        nLike++;
                    } else {
                        nUnlike++;
                    }
                }
            }
                if (user.isSigned() || semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptGuessComments)) {
                    renderURL.setAction("add");
                    %>
                    <img width="100%" height="11" alt="separacion" src="<%=baseimg%>separa_foro.png">
                    <a href="<%=renderURL%>">Publicar un problema</a>
                    <img width="100%" height="11" alt="separacion" src="<%=baseimg%>separa_foro.png">
                    <%
                }
                if (question.getQueStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                %>
                    <ul>
                        <li>
                            <div class="foto_foro_pregunta"><img width="40" height="40" src="<%=photo%>"></div>
                            <span class="usuario"><%=question.getCreator().getFullName()%>.</span>
                            (<%=SWBUtils.TEXT.getTimeAgo(question.getCreated(), user.getLanguage())%>). <%=question.getQuestion()%>
                            Vista <%=question.getViews()%> veces.
                            <%
                            if (user.isSigned() || semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptGuessComments)) {
                                if (!question.isClosed()) {
                                    renderURL.setAction("answerQuestion");
                                    renderURL.setParameter("org", "showDetail");
                                    %><a href="<%=renderURL%>">Responder a esta pregunta</a>&nbsp;<%
                                }
                                if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionSubscription)) {
                                    if (!question.isUserSubscribed(user)) {
                                         actionURL.setAction("subcribe2question");
                                         actionURL.setParameter("org", "showDetail");
                                         %><a href="<%=actionURL%>">Agregar a mis pendientes</a>&nbsp;<%
                                    }
                                }
                                if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateQuestions)) {
                                    if (!question.isQueIsApropiate() && !question.getCreator().getURI().equals(user.getURI())) {
                                         actionURL.setAction("markQuestionAsInnapropiate");
                                         actionURL.setParameter("org", "showDetail");
                                         %><a href="<%=actionURL%>">Marcar como inapropiado</a>&nbsp;<%
                                    }
                                }
                                if (user.isSigned() && user.getURI().equals(question.getCreator().getURI())) {
                                    renderURL.setAction("editQuestion");
                                    renderURL.setParameter("org", "edit");
                                    %><a href="<%=renderURL%>">Editar pregunta</a>&nbsp;<%
                                }
                            }
                            if (question.getQuestionReferences() != null) {
                                String prefix = "http://www.youtube.com/watch?v=";
                                String references[] = question.getQuestionReferences().split(",");
                                if (references.length > 0) {
                                    %>
                                    <ul>
                                        <li>Referencias</li>
                                        <ul>
                                            <%
                                            for (int idx = 0; idx < references.length; idx++) {
                                                if (references[idx].length() > 10 && references[idx].contains(prefix)) {
                                                    String ref = getVideoThumbnail(references[idx], wpage);
                                                    %><li><a href="<%=references[idx]%>"><img src="<%=ref%>"></a></li><%
                                                }
                                            }
                                            %>
                                        </ul>
                                    </ul>
                                    <%
                                }
                            }
                            %>
                        </li>
                        <ul>
                            <%
                            if (favAnswer != null) {
                                photo = baseimg + "profilePlaceholder.jpg";
                                String starimg = baseimg;
                                String alt="";
                                int points = getUserPoints(favAnswer.getCreator(), wpage.getWebSite());
                                if (points == 0) {
                                    starimg += "star_vacia.png";
                                    alt = "Vacía";
                                } else if (points > 0 && points <= 150) {
                                    starimg += "star_plata.png";
                                    alt = "Plata";
                                } else if (points >= 151 && points <= 600) {
                                    starimg += "star_oro.png";
                                    alt = "Oro";
                                } else if (points >= 601) {
                                    starimg += "star_diamante.png";
                                    alt = "Diamante";
                                }
                                if (favAnswer.getCreator().getPhoto() != null) {
                                    photo = SWBPortal.getWebWorkPath() + favAnswer.getCreator().getPhoto();
                                }
                                nLike=0;
                                nUnlike=0;
                                if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                    Iterator<AnswerVote> itAnswerVote = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(favAnswer);
                                    while (itAnswerVote.hasNext()) {
                                        AnswerVote answerVote = itAnswerVote.next();
                                        if (answerVote.isLikeAnswer()) {
                                            nLike++;
                                        } else if (!answerVote.isIrrelevantVote()) {
                                            nUnlike++;
                                        }
                                    }
                                }
                                %>
                                <li class="titulo">Mi respuesta favorita</li>
                                <li class="grupo_respuestas_gral">
                                    <div class="respuestas_gral">
                                        <div class="puntos">
                                            <ul>
                                                <li><img width="70" height="30" alt="<%=alt%>" title="<%=alt%> - <%=points%> puntos" src="<%=starimg%>"></li>
                                                <li><%=countUserQuestions(favAnswer.getCreator(), wpage.getWebSite())%> preguntas</li>
                                                <li><%=countUserAnswers(favAnswer.getCreator(), wpage.getWebSite())%> respuestas</li>
                                            </ul>
                                        </div>
                                        <div class="foto">
                                            <img width="40" height="40" src="<%=photo%>">
                                        </div>
                                        <div class="respuesta_gral">
                                            <span class="usuario"><%=favAnswer.getCreator().getFullName()%></span>
                                            (<%=SWBUtils.TEXT.getTimeAgo(favAnswer.getCreated(), user.getLanguage())%>). <%=favAnswer.getAnswer()%>
                                            <%
                                            if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateAnswers)) {
                                                if (!favAnswer.isAnsIsAppropiate() && !favAnswer.getCreator().getURI().equals(user.getURI())) {
                                                     actionURL.setAction("markAnswerAsInnapropiate");
                                                     actionURL.setParameter("org", "showDetail");
                                                     %> <a href="<%=actionURL%>">Marcar como inapropiado</a>&nbsp;<%
                                                } else {
                                                   %>- Inapropiado: <%=favAnswer.getAnsInappropriate()%><%
                                                }
                                            }
                                            %>
                                        </div>
                                        <div class="herramientas_foro_gral">
                                            <ul>
                                                <%
                                                if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                                    if (!user.getURI().equals(favAnswer.getCreator().getURI()) && !favAnswer.userHasVoted(user)) {
                                                        actionURL.setParameter("uri", favAnswer.getURI());
                                                        actionURL.setAction("voteAnswer");
                                                        actionURL.setParameter("likeVote", "true");
                                                        actionURL.setParameter("org", "showDetail");
                                                        %>
                                                            <li>
                                                                <a href="<%=actionURL%>"><img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png"></a>(<%=nLike%>)
                                                            </li>
                                                            <%actionURL.setParameter("likeVote", "false");%>
                                                            <li>
                                                                <a href="<%=actionURL%>"><img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png"></a>(<%=nUnlike%>)
                                                            </li>
                                                        <%
                                                    } else {
                                                       %>
                                                       <li>
                                                            <img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png">(<%=nLike%>)
                                                        </li>
                                                        <li>
                                                            <img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png">(<%=nUnlike%>)
                                                        </li>
                                                        <%
                                                    }
                                                }
                                                if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markIrrelevantAnswers)) {
                                                    if (!favAnswer.getCreator().getURI().equals(user.getURI()) && !favAnswer.userHasVoted(user)) {
                                                         actionURL = paramRequest.getActionUrl();
                                                         actionURL.setParameter("uri", favAnswer.getURI());
                                                         actionURL.setAction("voteAnswer");
                                                         actionURL.setParameter("irrelevant", "true");
                                                         actionURL.setParameter("org", "edit");
                                                         actionURL.setParameter("page", request.getParameter("page"));
                                                         %>
                                                         <li>
                                                                 ¿Esto qu&eacute;?<a href="<%=actionURL%>"><img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png"></a>(<%=favAnswer.getAnsIrrelevant()%>)
                                                         </li>
                                                        <%
                                                    } else {
                                                       %>
                                                       <li>
                                                             ¿Esto qu&eacute;?<img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png">(<%=favAnswer.getAnsIrrelevant()%>)
                                                        </li>
                                                        <%
                                                    }
                                                }

                                                %>
                                            </ul>
                                        </div>
                                    </div>
                                </li>
                                <%
                                if (favAnswer.getReferences() != null) {
                                    String prefix = "http://www.youtube.com/watch?v=";
                                    String references[] = favAnswer.getReferences().split(",");
                                    if (references.length > 0) {
                                        %>
                                        <ul>
                                            <li>Referencias</li>
                                            <ul>
                                                <%
                                                for (int idx = 0; idx < references.length; idx++) {
                                                    if (references[idx].length() > 10 && references[idx].contains(prefix)) {
                                                        String ref = getVideoThumbnail(references[idx], wpage);
                                                        %><li><a href="<%=references[idx]%>"><img src="<%=ref%>"></a></li><%
                                                    }
                                                }
                                                %>
                                            </ul>
                                        </ul>
                                        <%
                                    }
                                }
                            }
                            if (comAnswer != null) {
                                photo = baseimg + "profilePlaceholder.jpg";
                                String starimg = baseimg;
                                String alt="";
                                int points = getUserPoints(comAnswer.getCreator(), wpage.getWebSite());
                                if (points == 0) {
                                    starimg += "star_vacia.png";
                                    alt = "Vacía";
                                } else if (points > 0 && points <= 150) {
                                    starimg += "star_plata.png";
                                    alt = "Plata";
                                } else if (points >= 151 && points <= 600) {
                                    starimg += "star_oro.png";
                                    alt = "Oro";
                                } else if (points >= 601) {
                                    starimg += "star_diamante.png";
                                    alt = "Diamante";
                                }
                                if (comAnswer.getCreator().getPhoto() != null) {
                                    photo = SWBPortal.getWebWorkPath() + comAnswer.getCreator().getPhoto();
                                }

                                nLike=0;
                                nUnlike=0;
                                if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                    Iterator<AnswerVote> itAnswerVote = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(comAnswer);
                                    while (itAnswerVote.hasNext()) {
                                        AnswerVote answerVote = itAnswerVote.next();
                                        if (answerVote.isLikeAnswer()) {
                                            nLike++;
                                        } else if (!answerVote.isIrrelevantVote()) {
                                            nUnlike++;
                                        }
                                    }
                                }
                                %>
                                <li class="titulo">Respuesta favorita de la comunidad</li>
                                <li class="grupo_respuestas_gral">
                                    <div class="respuestas_gral">
                                        <div class="puntos">
                                            <ul>
                                                <li><img width="70" height="30" alt="<%=alt%>" title="<%=alt%> - <%=points%> puntos" src="<%=starimg%>"></li>
                                                <li><%=countUserQuestions(comAnswer.getCreator(), wpage.getWebSite())%> preguntas</li>
                                                <li><%=countUserAnswers(comAnswer.getCreator(), wpage.getWebSite())%> respuestas</li>
                                            </ul>
                                        </div>
                                        <div class="foto">
                                            <img width="40" height="40" src="<%=photo%>">
                                        </div>
                                        <div class="respuesta_gral">
                                            <span><%=comAnswer.getCreator().getFullName()%></span>
                                            (<%=SWBUtils.TEXT.getTimeAgo(comAnswer.getCreated(), user.getLanguage())%>). <%=comAnswer.getAnswer()%>
                                            <%
                                                if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateAnswers)) {
                                                    if (!comAnswer.isAnsIsAppropiate() && !comAnswer.getCreator().getURI().equals(user.getURI())) {
                                                         actionURL.setAction("markAnswerAsInnapropiate");
                                                         actionURL.setParameter("org", "showDetail");
                                                         %>  <a href="<%=actionURL%>">Marcar como inapropiado</a>&nbsp;<%
                                                    } else {
                                                       %>- Inapropiado: <%=favAnswer.getAnsInappropriate()%><%
                                                    }
                                                }
                                            %>
                                        </div>
                                        <div class="herramientas_foro_gral">
                                            <ul>
                                                <%
                                                if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                                    if (!user.getURI().equals(comAnswer.getCreator().getURI()) && !comAnswer.userHasVoted(user)) {
                                                        actionURL.setParameter("uri", comAnswer.getURI());
                                                        actionURL.setAction("voteAnswer");
                                                        actionURL.setParameter("likeVote", "true");
                                                        actionURL.setParameter("org", "showDetail");
                                                        %>
                                                        <li>
                                                            <a href="<%=actionURL%>"><img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png"></a>(<%=nLike%>)
                                                        </li>
                                                        <%actionURL.setParameter("likeVote", "false");%>
                                                        <li>
                                                            <a href="<%=actionURL%>"><img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png"></a>(<%=nUnlike%>)
                                                        </li>
                                                        <%
                                                    } else {
                                                       %>
                                                       <li>
                                                            <img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png">(<%=nLike%>)
                                                        </li>
                                                        <li>
                                                            <img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png">(<%=nUnlike%>)
                                                        </li>
                                                        <%
                                                    }
                                                }

                                                if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markIrrelevantAnswers)) {
                                                    if (!comAnswer.getCreator().getURI().equals(user.getURI()) && !comAnswer.userHasVoted(user)) {
                                                        actionURL = paramRequest.getActionUrl();
                                                         actionURL.setParameter("uri", comAnswer.getURI());
                                                        actionURL.setAction("voteAnswer");
                                                         actionURL.setParameter("irrelevant", "true");
                                                         actionURL.setParameter("org", "edit");
                                                         actionURL.setParameter("page", request.getParameter("page"));
                                                         %>
                                                         <li>
                                                                 ¿Esto qu&eacute;?<a href="<%=actionURL%>"><img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png"></a>(<%=comAnswer.getAnsIrrelevant()%>)
                                                         </li>
                                                        <%
                                                    } else {
                                                       %>
                                                       <li>
                                                             ¿Esto qu&eacute;?<img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png">(<%=comAnswer.getAnsIrrelevant()%>)
                                                        </li>
                                                        <%
                                                    }
                                                }
                                                %>
                                            </ul>
                                        </div>
                                    </div>
                                </li>
                                <div>
                                <%
                                if (comAnswer.getReferences() != null) {
                                    String prefix = "http://www.youtube.com/watch?v=";
                                    String references[] = comAnswer.getReferences().split(",");
                                    if (references.length > 0) {
                                        %>
                                        <ul>
                                            <li>Referencias</li>
                                            <ul>
                                                <%
                                                for (int idx = 0; idx < references.length; idx++) {
                                                    if (references[idx].length() > 10 && references[idx].contains(prefix)) {
                                                        String ref = getVideoThumbnail(references[idx], wpage);
                                                        %><li><a href="<%=references[idx]%>"><img src="<%=ref%>"></a></li><%
                                                    }
                                                }
                                                %>
                                            </ul>
                                        </ul>
                                        <%
                                    }
                                }
                                %></div><%
                            }

                            answers = SWBComparator.sortByCreated(question.listAnswerInvs(), false);
                            ArrayList<Answer> answers_a = new ArrayList<Answer>();
                            while (answers.hasNext()) {
                                Answer a = answers.next();
                                if (!a.equals(favAnswer) && !a.equals(comAnswer)) {
                                    answers_a.add(a);
                                }
                            }

                            answers = answers_a.iterator();
                            if (answers.hasNext()) {
                                %><li class="titulo">Respuestas</li><%
                                while (answers.hasNext()) {
                                    Answer answer = answers.next();
                                    if (answer.getAnsStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                                        photo = baseimg + "profilePlaceholder.jpg";
                                        String starimg = baseimg;
                                        String alt="";
                                        int points = getUserPoints(answer.getCreator(), wpage.getWebSite());
                                        if (points == 0) {
                                            starimg += "star_vacia.png";
                                            alt = "Vacía";
                                        } else if (points > 0 && points <= 150) {
                                            starimg += "star_plata.png";
                                            alt = "Plata";
                                        } else if (points >= 151 && points <= 600) {
                                            starimg += "star_oro.png";
                                            alt = "Oro";
                                        } else if (points >= 601) {
                                            starimg += "star_diamante.png";
                                            alt = "Diamante";
                                        }
                                        
                                        if (answer.getCreator().getPhoto() != null) {
                                            photo = SWBPortal.getWebWorkPath() + answer.getCreator().getPhoto();
                                        }
                                        renderURL.setParameter("uri", answer.getURI());
                                        actionURL.setParameter("uri", answer.getURI());
                                        nLike = 0;
                                        nUnlike = 0;
                                        if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                            Iterator<AnswerVote> itAnswerVote = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(answer);
                                            while (itAnswerVote.hasNext()) {
                                                AnswerVote answerVote = itAnswerVote.next();
                                                if (answerVote.isLikeAnswer()) {
                                                    nLike++;
                                                } else if (!answerVote.isIrrelevantVote()) {
                                                    nUnlike++;
                                                }
                                            }
                                        }
                                        %>
                                        <li class="grupo_respuestas_gral">
                                        <div class="respuestas_gral">
                                            <div class="puntos">
                                                <ul>
                                                    <li><img width="70" height="30" alt="<%=alt%>" title="<%=alt%> - <%=points%> puntos" src="<%=starimg%>"></li>
                                                    <li><%=countUserQuestions(answer.getCreator(), wpage.getWebSite())%> preguntas</li>
                                                    <li><%=countUserAnswers(answer.getCreator(), wpage.getWebSite())%> respuestas</li>
                                                </ul>
                                            </div>
                                            <div class="foto">
                                                <img width="40" height="40" src="<%=photo%>">
                                            </div>
                                            <div class="respuesta_gral">
                                                <span class="usuario"><%=answer.getCreator().getFullName()%></span>
                                                (<%=SWBUtils.TEXT.getTimeAgo(answer.getCreated(), user.getLanguage())%>). <%=answer.getAnswer()%><br>
                                                <%
                                                if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateAnswers)) {
                                                    if (!answer.isAnsIsAppropiate() && !answer.getCreator().getURI().equals(user.getURI())) {
                                                         actionURL.setAction("markAnswerAsInnapropiate");
                                                         actionURL.setParameter("org", "showDetail");
                                                         %>  <a href="<%=actionURL%>">Marcar como inapropiado</a>&nbsp;<%
                                                    } else {
                                                       %>- Inapropiado: <%=answer.getAnsInappropriate()%><%
                                                    }
                                                }
                                                %>                                                
                                            </div>
                                            <div class="herramientas_foro_gral">
                                                <ul>
                                                    <%
                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                                        if (!user.getURI().equals(answer.getCreator().getURI()) && !answer.userHasVoted(user)) {
                                                            actionURL.setParameter("uri", answer.getURI());
                                                            actionURL.setAction("voteAnswer");
                                                            actionURL.setParameter("likeVote", "true");
                                                            actionURL.setParameter("org", "showDetail");
                                                            %>
                                                            <li>
                                                                <a href="<%=actionURL%>"><img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png"></a>(<%=nLike%>)
                                                            </li>
                                                            <%actionURL.setParameter("likeVote", "false");%>
                                                            <li>
                                                                <a href="<%=actionURL%>"><img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png"></a>(<%=nUnlike%>)
                                                            </li>
                                                            <%
                                                        } else {
                                                           %>
                                                           <li>
                                                                <img width="18" height="18" alt="Me gusta" src="<%=baseimg%>icon_me_gusta.png">(<%=nLike%>)
                                                            </li>
                                                            <li>
                                                                <img width="18" height="18" alt="No me gusta" src="<%=baseimg%>icon_no_gusta.png">(<%=nUnlike%>)
                                                            </li>
                                                            <%
                                                        }
                                                    }

                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markIrrelevantAnswers)) {
                                                        if (!answer.getCreator().getURI().equals(user.getURI()) && !answer.userHasVoted(user)) {
                                                            actionURL = paramRequest.getActionUrl();
                                                             actionURL.setParameter("uri", answer.getURI());
                                                            actionURL.setAction("voteAnswer");
                                                             actionURL.setParameter("irrelevant", "true");
                                                             actionURL.setParameter("org", "edit");
                                                             actionURL.setParameter("page", request.getParameter("page"));
                                                             %>
                                                             <li>
                                                                 ¿Esto qu&eacute;?<a href="<%=actionURL%>"><img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png"></a>(<%=answer.getAnsIrrelevant()%>)
                                                            </li>
                                                            <%
                                                        } else {
                                                           %>
                                                           <li>
                                                             ¿Esto qu&eacute;?<img width="18" height="18" alt="¿Eso qué?" src="<%=baseimg%>icon_eso_que.png">(<%=answer.getAnsIrrelevant()%>)
                                                        </li>
                                                            <%
                                                        }
                                                    }
                                                    if (user.isSigned() && question.getCreator().getURI().equals(user.getURI())) {
                                                        if (!answer.getCreator().getURI().equals(user.getURI()) &&  semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markBestAnswer)) {
                                                            actionURL.setAction("bestAnswer");
                                                            actionURL.setParameter("org", "showDetail");
                                                            %><a href="<%=actionURL%>">Mejor respuesta</a>&nbsp;<%
                                                        }
                                                    }
                                                    %>
                                                </ul>
                                            </div>
                                        </div>
                                    </li>
                                <%
                                if (answer.getReferences() != null) {
                                    String prefix = "http://www.youtube.com/watch?v=";
                                String references[] = answer.getReferences().split(",");
                                if (references.length > 0) {
                                    %>
                                    <ul>
                                        <li>Referencias</li>
                                        <ul>
                                            <%
                                            for (int idx = 0; idx < references.length; idx++) {
                                                if (references[idx].length() > 10 && references[idx].contains(prefix)) {
                                                    String ref = getVideoThumbnail(references[idx], wpage);
                                                    %><li><a href="<%=references[idx]%>"><img src="<%=ref%>"></a></li><%
                                                }
                                            }
                                            %>
                                        </ul>
                                    </ul>
                                    <%
                                }
                                }
                                    }
                                }
                            }
                            renderURL = paramRequest.getRenderUrl().setParameter("page", request.getParameter("page"));
                            %>
                        </ul>
                        <li>
                            <img width="100%" height="11" alt="separacion" src="<%=baseimg%>separa_foro.png">
                        </li>
                        <a href="<%=renderURL%>">Regresar</a>
                    </ul>
                    <%
                }
        }
    } else if (action != null && action.equals("editQuestion")) {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        Question q = (Question) semObject.createGenericInstance();
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        mgr.setLang(user.getLanguage());
        mgr.setFilterRequired(false);
        mgr.setType(mgr.TYPE_XHTML);
        actionURL.setAction("editQuestion");
        actionURL.setParameter("org", request.getParameter("org"));
        mgr.addHiddenParameter("uri", semObject.getURI());
        SemanticClass semClass = Question.sclass;
        %>
        <div class="foro_gral">
            <h1>Editar pregunta</h1>
            <form id="<%=mgr.getFormName()%>" name="datosRegistro" action="<%=actionURL%>" method="post">
                <input type="hidden" name="<%=Question.forumCat_questionReferences.getName()%>">
                <fieldset>
                <%= mgr.getFormHiddens()%>
                <div class="sfcLinea">
                    <div class="sfcEtiqueta">
                        <%=mgr.renderLabel(request, semClass.getProperty(Question.forumCat_question.getName()), mgr.MODE_EDIT)%>:
                    </div>
                    <div class="sfcCampo">
                        <%=mgr.renderElement(request, semClass.getProperty(Question.forumCat_question.getName()), mgr.MODE_EDIT)%>
                    </div>
                </div>
                <div class="sfcLinea">
                    <div class="sfcEtiqueta">
                        <%=mgr.renderLabel(request, semClass.getProperty(Tagable.swb_tags.getName()), mgr.MODE_EDIT)%>:
                    </div>
                    <div class="sfcCampo">
                        <%=mgr.renderElement(request, semClass.getProperty(Tagable.swb_tags.getName()), mgr.MODE_EDIT)%>
                    </div>
                </div>
                <% if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_selectCategory)) {%>
                    <div class="sfcEtiqueta">
                        <%=mgr.renderLabel(request, semClass.getProperty(Question.forumCat_webpage.getName()), mgr.MODE_EDIT)%>:
                    </div>
                    <div class="sfcLinea">
                        <select name="categoryuri">
                            <%
                            String pid = semanticBase.getProperty(SWBForumCatResource.forumCat_idCatPage, paramRequest.getWebPage().getId());
                            WebPage wpp = paramRequest.getWebPage().getWebSite().getWebPage(pid);
                            if (wpp != null) {
                                Iterator<WebPage> childs = SWBComparator.sortByDisplayName(wpp.listChilds(), user.getLanguage());
                                while (childs.hasNext()) {
                                    WebPage child = childs.next();
                                    String selected = "";
                                    if(child.getURI().equals(q.getWebpage().getURI())) {
                                        selected = "selected";
                                    }
                                    %><option <%=selected%> value="<%=child.getURI()%>"><%=child.getTitle()%></option><%
                                }
                            }
                            %>
                        </select>
                    </div>
                    <%}%>
                <fieldset>
                    <div class="sfcLinea">
                        <div class="sfcEtiqueta">
                            <label>Referencias a Youtube</label>:<br>
                        </div>
                        <div class="sfcCampo">
                            <input type="text" name="inputBox">
                            <input type="button" value="Agregar" onclick="addToList(this.form.referenceList, this.form.inputBox);"><br>
                            <select multiple name="referenceList">
                                <%
                                String refs = "";
                                if (q.getQuestionReferences() != null) {
                                    refs = q.getQuestionReferences().trim();
                                    if (!refs.equals("")) {
                                        String arefs[] = refs.split(",");
                                        for (int idx = 0; idx < arefs.length; idx++) {
                                            %><option value="<%=arefs[idx]%>"><%=arefs[idx]%></option><%
                                        }
                                    }
                                }
                                %>
                            </select>
                            <input type="button" value="Limpiar" onclick="clearList(this.form.referenceList);">
                            <input type="button" value="Limpiar seleccionados" onclick="clearSelected(this.form.referenceList);"><br>
                        </div>
                    </div>
                </fieldset>
                <div class="sfcLinea">
                    <input type="button" class="boton" value="Guardar" onclick="setReferences(this.form.referenceList, this.form.<%=Question.forumCat_questionReferences.getName()%>);">
                    <!--input type="submit" class="boton" value="Guardar"-->
                    <input type="button" class="boton" value="Regresar" onclick="javascript:history.go(-1);">
                </div>
                </fieldset>
            </form>
        </div>
        <%
    } else if (action != null && action.equals("answerQuestion")) {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        SWBFormMgr mgr = new SWBFormMgr(Answer.sclass, semObject, SWBFormMgr.MODE_CREATE);
        mgr.setLang(user.getLanguage());
        mgr.setFilterRequired(false);
        Question q = (Question) semObject.createGenericInstance();
        //mgr.setType(mgr.TYPE_DOJO);
        actionURL.setAction("answerQuestion");
        actionURL.setParameter("uri", semObject.getURI());
        actionURL.setParameter("org", request.getParameter("org"));
        mgr.setAction(actionURL.toString());
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newCancelButton());
        SemanticClass semClass = Answer.sclass;
        %>
        <div class="foro_gral">
            <h1>Responder a la pregunta <b><%=q.getQuestion()%></b></h1>
            <form id="<%=mgr.getFormName()%>" name="datosRegistro" class="swbform" action="<%=actionURL%>" method="post" >
                <input type="hidden" name="<%=Answer.forumCat_references.getName()%>">
                <fieldset>
                    <%= mgr.getFormHiddens()%>
                    <div class="sfcLinea">
                        <div class="sfcEtiqueta">
                            <%=mgr.renderLabel(request, semClass.getProperty(Answer.forumCat_answer.getName()), mgr.MODE_CREATE)%>:
                        </div>
                        <div class="sfcCampo">
                            <textarea cols="42" rows="6" name="<%=Answer.forumCat_answer.getName()%>"></textarea>
                            <!--%=mgr.renderElement(request, semClass.getProperty(Answer.forumCat_answer.getName()), mgr.MODE_CREATE)%-->
                        </div>
                    </div>
                </fieldset>
                    <%if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptAttachements)) {%>
                        <div class="sfcLinea">
                            <div class="sfcEtiqueta">
                                <%=mgr.renderLabel(request, semClass.getProperty(Answer.forumCat_hasAttachements.getName()), mgr.MODE_CREATE)%>:
                            </div>
                            <div class="sfcCampo">
                                <%=mgr.renderElement(request, semClass.getProperty(Answer.forumCat_hasAttachements.getName()), mgr.MODE_CREATE)%>
                            </div>
                        </div>
                    <%}%>
                <fieldset>
                    <div class="sfcLinea">
                        <div class="sfcEtiqueta">
                            <label>Referencias a Youtube</label>:<br>
                        </div>
                        <div class="sfcCampo">
                            <input type="text" name="inputBox">
                            <input type="button" value="Agregar" onclick="addToList(this.form.referenceList, this.form.inputBox);"><br>
                            <select multiple name="referenceList" disabled="disabled"></select>
                            <input type="button" value="Limpiar" onclick="clearList(this.form.referenceList);"><br>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="sfcLinea">
                        <input type="button" class="boton" value="Guardar" onclick="setReferences(this.form.referenceList, this.form.<%=Answer.forumCat_references.getName()%>);">
                        <!--input type="submit" class="boton" value="Guardar"-->
                        <input type="button" class="boton" value="Regresar" onclick="javascript:history.go(-1);">
                    </div>
                </fieldset>
            </form>
        </div>
        <%
    } else if (action != null && action.equals("editAnswer")) {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        mgr.setLang(user.getLanguage());
        mgr.setFilterRequired(false);
        mgr.setType(mgr.TYPE_DOJO);
        actionURL.setAction("editAnswer");
        actionURL.setParameter("uri", semObject.getURI());
        actionURL.setParameter("org", request.getParameter("org"));
        mgr.setAction(actionURL.toString());
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newCancelButton());
        SemanticClass semClass = Answer.sclass;
        %>
            <div class="foro_gral">
            <h1>Editar respuesta</h1>
            <%= SWBFormMgr.DOJO_REQUIRED%>
            <form id="<%=mgr.getFormName()%>" name="datosRegistro" dojoType="dijit.form.Form" class="swbform" action="<%=actionURL%>" method="post" >
                <fieldset>
                <%= mgr.getFormHiddens()%>

                <div class="sfcLinea">
                    <div class="sfcEtiqueta">
                        <%=mgr.renderLabel(request, semClass.getProperty(Answer.forumCat_answer.getName()), mgr.MODE_EDIT)%>:
                    </div>
                    <div class="sfcCampo">

                        <%=mgr.renderElement(request, semClass.getProperty(Answer.forumCat_answer.getName()), mgr.MODE_EDIT)%>
                    </div>
                </div>
                <%if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptAttachements)) {%>
                    <div class="sfcLinea">
                        <div class="sfcEtiqueta">
                            <%=mgr.renderLabel(request, semClass.getProperty(Answer.forumCat_hasAttachements.getName()), mgr.MODE_EDIT)%>:
                        </div>
                        <div class="sfcCampo">

                            <%=mgr.renderElement(request, semClass.getProperty(Answer.forumCat_hasAttachements.getName()), mgr.MODE_EDIT)%>
                        </div>
                    </div>
                <%}%>
                <div class="sfcLinea">
                    <input type="submit" class="boton" value="Guardar">
                    <input type="button" class="boton" value="Regresar" onclick="javascript:history.go(-1);">
                </div>
                </fieldset>
            </form>
        </div>
    <%
    } else if (action != null && action.equals("moderate")) {
        boolean notEmpty = false;
        boolean quesHeader = false;
        if (isAdmin) {
            %>
            <ul class="sfcQuestList">
                <%
                     Iterator itQuestions = (Iterator) request.getAttribute("listQuestions");
                     while (itQuestions.hasNext()) {
                         Question question = (Question) itQuestions.next();
                         renderURL.setParameter("uri", question.getURI());
                         actionURL.setParameter("uri", question.getURI());
                         if (question.getQueStatus() == SWBForumCatResource.STATUS_REGISTERED) {
                %>
                <li><div class="sfcQuest"><%=question.getQuestion()%><p><%=question.getSpecifications()%></p>
                        <ul class="sfcInfo">
                            <li><%=question.getWebpage() != null ? question.getWebpage().getDisplayName() : "---"%></li>
                            <li>De:<%=question.getCreator() != null ? question.getCreator().getFullName() : "---"%></li>
                            <li><%=question.getCreated()%></li>
                        </ul>
                        <ul class="sfcOptions">
                            <% actionURL.setAction("AcceptQuestion");%>
                            <li><a href="<%=actionURL%>">Aceptar</a></li>
                            <%actionURL.setAction("RejectQuestion");%>
                            <li><a href="<%=actionURL%>">Rechazar</a></li>
                            <%
                                 renderURL.setAction("editQuestion");
                                 renderURL.setParameter("org", "moderate");
                            %>
                            <li><a href="<%=renderURL%>">Editar</a></li>
                            <%
                                 actionURL.setAction("removeQuestion");
                                 actionURL.setParameter("uri", question.getEncodedURI());
                                 actionURL.setParameter("org", "moderate");
                                 String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";
                            %>
                            <li><a href="<%=removeUrl%>">Eliminar</a></li>
                        </ul>
                    </div>
                </li>

                <%
                         notEmpty = true;
                     }
                     quesHeader = false;
                     Iterator<Answer> itAnswers = question.listAnswerInvs();

                     while (itAnswers.hasNext()) {
                         Answer answer = itAnswers.next();
                         if (!quesHeader && answer.getAnsStatus() == SWBForumCatResource.STATUS_REGISTERED) {
                %>
                <li>
                    <div class="sfcQuest"><%=question.getQuestion()%><p><%=question.getSpecifications()%></p>
                        <ul class="sfcInfo">
                            <li><%=question.getWebpage() != null ? question.getWebpage().getDisplayName() : "---"%></li>
                            <li>De:<%=question.getCreator() != null ? question.getCreator().getFullName() : "---"%></li>
                            <li><%=question.getCreated()%></li>
                        </ul>
                        <%
                             notEmpty = true;
                             quesHeader = true;
                        %>
                    </div>
                    <ul class="sfcAnsList">
                        <%
                             }
                             if (answer.getAnsStatus() == SWBForumCatResource.STATUS_REGISTERED) {
                        %>
                        <li><div class="sfcAns"><%=answer.getAnswer()%>
                                <ul class="sfcInfo">
                                    <li>De:<%=answer.getCreator() != null ? answer.getCreator().getFullName() : "---"%></li>
                                    <li><%=answer.getCreated()%></li>
                                </ul>
                                <%
                                     actionURL.setAction("AcceptAnswer");
                                     actionURL.setParameter("uri", answer.getURI());
                                %>
                                <ul class="sfcOptions">
                                    <li><a href="<%=actionURL%>">Aceptar</a></li>
                                    <%
                                         actionURL.setAction("RejectAnswer");
                                    %>
                                    <li><a href="<%=actionURL%>">Rechazar</a></li>
                                    <%
                                         renderURL.setAction("editAnswer");
                                         renderURL.setParameter("uri", answer.getURI());
                                         renderURL.setParameter("org", "moderate");
                                    %>
                                    <li><a href="<%=renderURL%>">Editar</a></li>
                                    <%
                                         actionURL.setAction("removeAnswer");
                                         actionURL.setParameter("uri", answer.getEncodedURI());
                                         actionURL.setParameter("org", "moderate");
                                         String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";
                                    %>
                                    <li><a href="<%=removeUrl%>">Eliminar</a></li>
                                </ul>
                            </div>
                        </li>
                        <%
                             }
                             if (!itAnswers.hasNext() && quesHeader) {
                        %>
                    </ul>
                </li>
                <%              }
                         }
                     }
                %>
            </ul>
            <%
        }
        if (!notEmpty) {
    %>
    No existen preguntas, ni respuestas para moderar
    <a href="javascript:history.go(-1);">Regresar</a>
    <% } else {
    %>
    <a href="<%=paramRequest.getRenderUrl()%>">Regresar</a>
    <%
         }
     }
    %>
<script type="text/javascript">
    function validateRemove(url) {
        if(confirm('¿Esta seguro de borrar el elemento?')) {
            window.location.href=url;
        }
    }
</script>

<%!
    public String getVideoThumbnail(String videoUrl, WebPage wpage) {
        String ret = SWBPortal.getWebWorkPath()+"/models/"+wpage.getWebSiteId()+"/css/images/profilePlaceholder.jpg";
        if (videoUrl.length() > 2) {
            ret = "http://i.ytimg.com/vi/" + videoUrl.substring(31, videoUrl.length()) + "/default.jpg";
        }
        return ret;
    }
%>

<%!
    public int countUserQuestions(User user, WebSite model) {
        int ret = 0;
        Iterator<Question> qit = Question.ClassMgr.listQuestionByCreator(user, model);
        while (qit.hasNext()) {
            ret++;
            qit.next();
        }
        return ret;
    }
%>

<%!
    public int countUserAnswers(User user, WebSite model) {
        int ret = 0;
        Iterator<Answer> qit = Answer.ClassMgr.listAnswerByCreator(user, model);
        while (qit.hasNext()) {
            ret++;
            qit.next();
        }
        return ret;
    }
%>

<%!
public int getUserPoints(User user, WebSite model) {
    int ret = 0;
    Iterator<UserPoints> itp = UserPoints.ClassMgr.listUserPointsByPointsUser(user, model);
    while (itp.hasNext()) {
        UserPoints up = itp.next();
        ret += up.getPoints();
    }
    return ret;
}
%>