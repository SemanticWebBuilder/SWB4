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
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.resources.sem.forumcat.SWBForumCatResource"%>
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
    // boolean isQuesCreator=false;
    // boolean isAnsCreator = false;
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

    if (action != null && action.equals("add")) {
        SWBFormMgr mgr = new SWBFormMgr(Question.forumCat_Question, base.getSemanticObject(), SWBFormMgr.MODE_CREATE);
        mgr.setLang(user.getLanguage());
        mgr.setFilterRequired(false);
        mgr.setType(mgr.TYPE_DOJO);
        actionURL.setAction("addQuestion");
        SemanticClass semClass = Question.sclass;
%>
        <h1>Realizar una nueva pregunta</h1>
        <%= SWBFormMgr.DOJO_REQUIRED%>
        <form id="<%--=mgr.getFormName()--%>formaCaptura" name="datosRegistro" dojoType="dijit.form.Form" class="swbform" action="<%=actionURL%>" method="post">
            <fieldset>
            <%= mgr.getFormHiddens()%>
            <input type="hidden" value="<%=paramRequest.getWebPage()%>" name="categoryuri" id="categoryuri" />
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
                    <%=mgr.renderLabel(request, semClass.getProperty(Question.forumCat_specifications.getName()), mgr.MODE_CREATE)%>:
                </div>
                <div class="sfcCampo">
                    <%=mgr.renderElement(request, semClass.getProperty(Question.forumCat_specifications.getName()), mgr.MODE_CREATE)%>
                </div>
            </div>
            <div class="sfcLinea">
            <% if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_selectCategory)) {%>
                <div class="sfcEtiqueta">
                    <%=mgr.renderLabel(request, semClass.getProperty(Question.forumCat_webpage.getName()), mgr.MODE_CREATE)%>:
                </div>
                <div class="sfcCampo">
                    <%=mgr.renderElement(request, semClass.getProperty(Question.forumCat_webpage.getName()), mgr.MODE_CREATE)%>
                </div>
            </div>
            <div class="sfcLinea">
                <%@include file="Tree.jsp" %>
            </div>
            <%}%>
            <div class="sfcLinea">
                <input type="submit" class="boton" value="Guardar">
                <input type="button" class="boton" value="Regresar" onclick="javascript:history.go(-1);">
            </div>
            </fieldset>
        </form>
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
        Iterator itQuestions = (Iterator) request.getAttribute("listQuestions");
%>
        <div class="tborder">
            <table class="bordercolor" cellspacing="1" cellpadding="4" border="0" width="100%">
                <tbody>
                    <tr>
                        <td class="catbg3">Pregunta</td>
                        <td class="catbg3" width="20%">Publicada por</td>
                        <td class="catbg3" align="center" width="3%">Respuestas</td>
                        <td class="catbg3" align="center" width="20%">&Uacute;ltima respuesta</td>
                        <td class="catbg3" width="10%">Votos</td>
                    </tr>
<%
        while (itQuestions.hasNext()) {
            Question question = (Question)itQuestions.next();
            if (question.getQueStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                nRec++;
                if ((nRec > (nPage - 1) * recPerPage) && (nRec <= (nPage) * recPerPage)) {
                    renderURL.setParameter("uri", question.getURI());
                    actionURL.setParameter("uri", question.getURI());
                    renderURL.setAction("showDetail");

                    int nAnswer = 0;
                    Iterator<Answer> itAnswers = question.listAnswerInvs();
                    while (itAnswers.hasNext()) {
                        Answer answer = itAnswers.next();
                        if (answer.getAnsStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                            nAnswer++;
                        }
                    }
                    int nLike = 0;
                    int nUnlike = 0;
                    String baseimg = SWBPortal.getWebWorkPath()+"/models/"+wpage.getWebSiteId()+"/css/images/";
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
                    %>
                    <tr>
                        <td class="windowbg" valign="middle"><a href="<%=renderURL%>"><%=question.getQuestion()%></a></td>
                        <td class="windowbg"><%=question.getCreator() != null ? question.getCreator().getFullName() : "---"%></td>
                        <td class="windowbg" align="center"><%=nAnswer%></td>
                        <td class="windowbg" align="center">12-12-12 Por: hola</td>
                        <%
                        if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionVotable)) {
                            %><td class="windowbg" align="center"><img src="<%=baseimg%>like.gif"><%=nLike%><img src="<%=baseimg%>nolike.gif"><%=nUnlike%></td><%
                        } else {
                            %><td class="windowbg" align="center">--</td><%
                        }
                        %>
                    </tr>
                    <%
                }
            }
        }
        %>
                </tbody>
            </table>
            <table cellspacing="0" cellpadding="0" border="0" width="100%">
                <tbody>
                    <tr>
                        <td class="middletext">
                            P&aacute;ginas: [
                            <%
                            for (int countPage = 1; countPage < (Math.ceil((double) nRec / (double) recPerPage) + 1); countPage++) {
                                pageURL.setParameter("page", "" + (countPage));
                                if (countPage == nPage) {
                                    %><%=countPage%>&nbsp;<%
                                } else {
                                    %><b><a href="<%=pageURL%>"><%=countPage%></a></b><%
                                }
                            }
                            %>
                            ]
                        </td>
                        <td align="right" style="padding-right:1ex;">
                            <table cellspacing="0" cellpadding="0">
                                <tbody>
                                    <tr>
                                        <td class="mainstrip_first"></td>
                                        <td class="mainstrip">
                                        <%if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isModerate)) {
                                                if (isAdmin) {
                                                    renderURL.setAction("moderate");
                                                    %><a href="<%=renderURL%>">Moderar foro</a>&nbsp;|&nbsp;<%
                                                }
                                          }
                                          if (user.isSigned() || semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptGuessComments)) {
                                                renderURL.setAction("add");
                                                %><a href="<%=renderURL%>">Realizar una nueva pregunta</a><%
                                          }
                                        %>
                                        </td>
                                        <td class="mainstrip_last"></td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <%
    } else if (action != null && action.equals("showDetail")) {
        String baseimg = SWBPortal.getWebWorkPath()+"/models/"+wpage.getWebSiteId()+"/css/images/";
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        Question question = (Question) semObject.createGenericInstance();
        if (question.getQueStatus() == SWBForumCatResource.STATUS_ACEPTED) {
            int nAnswer = 0;
            renderURL.setParameter("uri", question.getURI());
            actionURL.setParameter("uri", question.getURI());

            boolean hasBestAnswer = false;
            Iterator<Answer> itAnswers = question.listAnswerInvs();
            while (itAnswers.hasNext()) {
                Answer answer = itAnswers.next();
                if (answer.getAnsStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                    if (answer.isBestAnswer()) {
                        hasBestAnswer = true;
                    }
                    nAnswer++;
                }
            }

            int nLike = 0;
            int nUnlike = 0;
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
            %>
            <table class="tborder" cellspacing="0" cellpadding="3" border="0" width="100%" style="border-bottom:0pt none;">
                <tbody>
                    <tr class="catbg3">
                        <td width="15%">Autor</td>
                        <td id="top_subject" width="85%" valign="middle" style="padding-left:6px;">Pregunta: <%=question.getQuestion()%></td>
                    </tr>
                </tbody>
            </table>
            <table class="bordercolor" cellspacing="0" cellpadding="0" border="0" width="100%">
                <tbody>
                    <tr>
                        <td style="padding: 1px 1px 0pt;">
                            <table cellspacing="0" cellpadding="3" border="0"width="100%">
                                <tbody>
                                    <tr>
                                        <td class="windowbg">
                                            <table cellspacing="0" cellpadding="5" width="100%" style="table-layout:fixed;">
                                                <tbody>
                                                    <tr>
                                                        <td width="16%" valign="top" style="overflow:hidden;">
                                                            <b><%=question.getCreator() != null ? question.getCreator().getFullName() : "---"%></b>
                                                            <div class="smalltext">
                                                                <br>
                                                                <span class="smalltext"><%=(question.getCreator().isSigned())?"Conectado":"Desconectado"%></span>
                                                                <br>
                                                                Respuestas: <%=nAnswer%>
                                                                <br>
                                                                <br>
                                                                <div style="overflow:auto; width:100%;">
                                                                    <img class="avatar" border="0" alt="" src="<%=SWBPortal.getWebWorkPath() + question.getCreator().getPhoto()%>">
                                                                </div>
                                                                <br>
                                                                <%if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionVotable)) {%>
                                                                    <img src="<%=baseimg%>like.gif"><%=nLike%>&nbsp;&nbsp;<img src="<%=baseimg%>nolike.gif"><%=nUnlike%>
                                                                <%}%>
                                                            </div>
                                                        </td>
                                                        <td width="85%" valign="top" height="100%">
                                                            <table border="0" width="100%">
                                                                <tbody>
                                                                    <tr>
                                                                        <td valign="middle">
                                                                            <div style="font-weight:bold"><%=question.getQuestion()%></div>
                                                                            <div class="smalltext"><%=iso8601dateFormat.format(question.getCreated())%></div>
                                                                        </td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                            <hr class="hrcolor" width="100%" size="1">
                                                            <div class="post">
                                                                <%=question.getSpecifications()==null?"--":question.getSpecifications()%>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="smalltext" width="85%" valign="bottom" colspan="2">
                                                            <table border="0" width="100%" style="table-layout:fixed;">
                                                                <tbody>
                                                                    <tr><td class="smalltext" width="100%"></td></tr>
                                                                    <tr>                                                                        
                                                                        <td class="smalltext" align="right" valign="bottom">
                                                                            <%
                                                                            if (isAdmin || user.isSigned() && question.getCreator() != null && user.getURI().equals(question.getCreator().getURI())) {
                                                                                renderURL.setAction("editQuestion");
                                                                                renderURL.setParameter("org", "showDetail");
                                                                                %><a href="<%=renderURL%>">Editar</a>&nbsp;<%
                                                                            }
                                                                            if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateQuestions)) {
                                                                                actionURL.setAction("markQuestionAsInnapropiate");
                                                                                actionURL.setParameter("org", "showDetail");
                                                                                %><a href="<%=actionURL%>">Inapropiado</a>&nbsp;<%
                                                                            }
                                                                            if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionVotable)) {
                                                                                if (!user.getURI().equals(question.getCreator().getURI()) && !question.userHasVoted(user)) {
                                                                                    actionURL.setAction("voteQuestion");
                                                                                    actionURL.setParameter("org", "showDetail");
                                                                                    actionURL.setParameter("likeVote", "true");
                                                                                    %><a href="<%=actionURL%>">Me gusta</a>&nbsp;<%
                                                                                    actionURL.setParameter("likeVote", "false");
                                                                                    %><a href="<%=actionURL%>">No me gusta</a>&nbsp;<%
                                                                                }
                                                                            }
                                                                            if (isAdmin) {
                                                                                actionURL.setAction("removeQuestion");
                                                                                actionURL.setParameter("org", "edit");
                                                                                String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";
                                                                                %><a href="<%=removeUrl%>">Eliminar</a>&nbsp;<%
                                                                            }
                                                                            if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionSubscription)) {
                                                                                if (!question.isUserSubscribed(user)) {
                                                                                     actionURL.setAction("subcribe2question");
                                                                                     actionURL.setParameter("org", "showDetail");
                                                                                     %><a href="<%=actionURL%>">Suscribirse</a>&nbsp;<%
                                                                                }
                                                                            }
                                                                            if (user.isSigned() || semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptGuessComments)) {
                                                                                if (!question.isClosed()) {
                                                                                    renderURL.setAction("answerQuestion");
                                                                                    %><a href="<%=renderURL%>">Responder a esta pregunta</a>&nbsp;<%
                                                                                }
                                                                            }
                                                                            if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_questionClosable)) {
                                                                                if (user.getURI().equals(question.getCreator().getURI())) {
                                                                                    if (!question.isClosed()) {
                                                                                        actionURL.setAction("closeQuestion");
                                                                                        actionURL.setParameter("org", "showDetail");
                                                                                        %><a href="<%=actionURL%>">Cerrar pregnta</a>&nbsp;<%
                                                                                    } else {
                                                                                        actionURL.setAction("openQuestion");
                                                                                        actionURL.setParameter("org", "showDetail");
                                                                                        %><a href="<%=actionURL%>">Abrir pregnta</a>&nbsp;<%
                                                                                    }
                                                                                }
                                                                            }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <%
                    itAnswers = question.listAnswerInvs();
                    nAnswer = 0;
                    while (itAnswers.hasNext()) {
                         Answer answer = itAnswers.next();
                         if (answer.getAnsStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                            renderURL.setParameter("uri", answer.getURI());
                            actionURL.setParameter("uri", answer.getURI());
                            nAnswer++;
                            nLike = 0;
                            nUnlike = 0;
                            if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                Iterator<AnswerVote> itAnswerVote = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(answer);
                                while (itAnswerVote.hasNext()) {
                                    AnswerVote answerVote = itAnswerVote.next();
                                    if (answerVote.isLikeAnswer()) {
                                        nLike++;
                                    } else {
                                        nUnlike++;
                                    }
                                }
                            }
                            %>
                            <tr>
                                <td style="padding:1px 1px 0pt;">
                                    <table cellspacing="0" cellpadding="3" border="0" width="100%">
                                        <tbody>
                                            <tr>
                                                <td class="windowbg2">
                                                    <table cellspacing="0" cellpadding="5" width="100%" style="table-layout:fixed;">
                                                        <tbody>
                                                            <tr>
                                                                <td width="16%" valign="top" style="overflow:hidden;" rowspan="2">
                                                                    <b><%=answer.getCreator() != null ? answer.getCreator().getFullName() : "---"%></b>
                                                                    <div class="smalltext">
                                                                        <br>
                                                                        <span class="smalltext"><%=(answer.getCreator().isSigned())?"Conectado":"Desconectado"%></span>
                                                                        <br>
                                                                        <br>
                                                                        <br>
                                                                        <div style="overflow:auto; width:100%;">
                                                                            <img class="avatar" border="0" alt="" src="<%=SWBPortal.getWebWorkPath() + answer.getCreator().getPhoto()%>">
                                                                        </div>
                                                                        <br>
                                                                        <%if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {%>
                                                                            <img src="<%=baseimg%>like.gif"><%=nLike%>&nbsp;&nbsp;<img src="<%=baseimg%>nolike.gif"><%=nUnlike%>
                                                                        <%}%>
                                                                    </div>
                                                                </td>
                                                                <td width="85%" valign="top" height="100%">
                                                                    <table border="0" width="100%">
                                                                        <tbody>
                                                                            <tr>
                                                                                <td valign="middle">
                                                                                    <div style="font-weight:bold;">Re: <%=question.getQuestion()%></div>
                                                                                    <div class="smalltext"><b>Respuesta No. <%=nAnswer%>:</b> <%=iso8601dateFormat.format(question.getCreated())%></div>
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                    <hr class="hrcolor" width="100%" size="1">
                                                                    <div class="post">
                                                                        <p>
                                                                            <%=answer.getAnswer()%>
                                                                        </p>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="smalltext" width="85%" valign="bottom">
                                                                    <table border="0" width="100%" style="table-layout:fixed;">
                                                                        <tbody>
                                                                            <tr><td class="smalltext" width="100%"></td></tr>
                                                                            <tr>
                                                                                <td class="smalltext" align="right" valign="bottom">
                                                                                    <%
                                                                                    if (isAdmin || user.isSigned() && answer.getCreator() != null && user.getURI().equals(answer.getCreator().getURI())) {
                                                                                        renderURL.setAction("editAnswer");
                                                                                        %><a href="<%=renderURL%>">Editar</a>&nbsp;<%
                                                                                    }
                                                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateAnswers)) {
                                                                                        actionURL.setAction("markAnswerAsInnapropiate");
                                                                                        %><a href="<%=actionURL%>">Inapropiado</a>&nbsp;<%
                                                                                    }
                                                                                    if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                                                                        if (!user.getURI().equals(answer.getCreator().getURI()) && !answer.userHasVoted(user)) {
                                                                                            actionURL.setAction("voteAnswer");
                                                                                            actionURL.setParameter("likeVote", "true");
                                                                                            actionURL.setParameter("org", "showDetail");
                                                                                            %><a href="<%=actionURL%>">Me gusta</a>&nbsp;<%
                                                                                            actionURL.setParameter("likeVote", "false");
                                                                                            %><a href="<%=actionURL%>">No me gusta</a>&nbsp;<%
                                                                                        }
                                                                                    }
                                                                                    if (isAdmin) {
                                                                                         actionURL.setAction("removeAnswer");
                                                                                         actionURL.setParameter("org", "showDetail");
                                                                                         String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";
                                                                                         %><a href="<%=removeUrl%>">Eliminar</a>&nbsp;<%
                                                                                    }
                                                                                    if (user.isSigned() && question.getCreator().getURI().equals(user.getURI())) {
                                                                                        if (!hasBestAnswer && !answer.isBestAnswer() && !answer.getCreator().getURI().equals(user.getURI()) ) {
                                                                                            actionURL.setAction("bestAnswer");
                                                                                            actionURL.setParameter("org", "showDetail");
                                                                                            %><a href="<%=actionURL%>">Mejor respuesta</a>&nbsp;<%
                                                                                        }
                                                                                    }
                                                                                    %>
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                            <%
                         }
                    }
                    %>
                </tbody>
            </table>
                <table cellspacing="0" cellpadding="0" border="0" width="100%">
                <tbody>
                    <tr>
                        <td align="right" style="padding-right:1ex;">
                            <table cellspacing="0" cellpadding="0">
                                <tbody>
                                    <tr>
                                        <td class="mainstrip_first"></td>
                                        <td class="mainstrip">
                                        <%if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isModerate)) {
                                                if (isAdmin) {
                                                    renderURL.setAction("moderate");
                                                    %><a href="<%=renderURL%>">Moderar foro</a>&nbsp;|&nbsp;<%
                                                }
                                          }
                                          if (user.isSigned() || semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptGuessComments)) {
                                                renderURL.setAction("add");
                                                %><a href="<%=renderURL%>">Realizar una nueva pregunta</a>&nbsp;|&nbsp;<%
                                          }
                                        %>
                                        <a href="<%=paramRequest.getRenderUrl()%>">Regresar</a>
                                        </td>
                                        <td class="mainstrip_last"></td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
            <%
        }
    } else if (action != null && action.equals("editQuestion")) {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        mgr.setLang(user.getLanguage());
        mgr.setFilterRequired(false);
        mgr.setType(mgr.TYPE_DOJO);
        actionURL.setAction("editQuestion");
        actionURL.setParameter("org", request.getParameter("org"));
        mgr.addHiddenParameter("uri", semObject.getURI());
        SemanticClass semClass = Question.sclass;
        %>
        <h1>Editar pregunta</h1>
        <%= SWBFormMgr.DOJO_REQUIRED%>
        <form id="<%=mgr.getFormName()%>" name="datosRegistro" dojoType="dijit.form.Form" class="swbform" action="<%=actionURL%>" method="post">
            <fieldset>
            <%= mgr.getFormHiddens()%>
            <input type="hidden" value="" name="categoryuri" id="categoryuri" />

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
                    <%=mgr.renderLabel(request, semClass.getProperty(Question.forumCat_specifications.getName()), mgr.MODE_EDIT)%>:
                </div>
                <div class="sfcCampo">
                    <%=mgr.renderElement(request, semClass.getProperty(Question.forumCat_specifications.getName()), mgr.MODE_EDIT)%>
                </div>
            </div>
            <% if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_selectCategory)) {%>
                <div class="sfcEtiqueta">
                    <%=mgr.renderLabel(request, semClass.getProperty(Question.forumCat_webpage.getName()), mgr.MODE_CREATE)%>:
                </div>
                <div class="sfcCampo">
                    <%=mgr.renderElement(request, semClass.getProperty(Question.forumCat_webpage.getName()), mgr.MODE_CREATE)%>
                </div>
                <div class="sfcLinea">
                    <%@include file="Tree.jsp" %>
                </div>
            <%}%>
            <div class="sfcLinea">
                <input type="submit" class="boton" value="Guardar">
                <input type="button" class="boton" value="Regresar" onclick="javascript:history.go(-1);">
            </div>
            </fieldset>
        </form>
        <%
    } else if (action != null && action.equals("answerQuestion")) {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        SWBFormMgr mgr = new SWBFormMgr(Answer.forumCat_Answer, semObject, SWBFormMgr.MODE_CREATE);
        mgr.setLang(user.getLanguage());
        mgr.setFilterRequired(false);
        //mgr.setType(mgr.TYPE_DOJO);
        actionURL.setAction("answerQuestion");
        actionURL.setParameter("uri", semObject.getURI());
        mgr.setAction(actionURL.toString());
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newCancelButton());
        SemanticClass semClass = Answer.sclass;
        %>
        <h1>Responder a la pregunta</h1>
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
                        <%=mgr.renderLabel(request, semClass.getProperty(Answer.forumCat_attachements.getName()), mgr.MODE_EDIT)%>:
                    </div>
                    <div class="sfcCampo">

                        <%=mgr.renderElement(request, semClass.getProperty(Answer.forumCat_attachements.getName()), mgr.MODE_EDIT)%>
                    </div>
                </div>
            <%}%>
            <div class="sfcLinea">
                <input type="submit" class="boton" value="Guardar">
                <input type="button" class="boton" value="Regresar" onclick="javascript:history.go(-1);">
            </div>
            </fieldset>
        </form>
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
                        <%=mgr.renderLabel(request, semClass.getProperty(Answer.forumCat_attachements.getName()), mgr.MODE_EDIT)%>:
                    </div>
                    <div class="sfcCampo">

                        <%=mgr.renderElement(request, semClass.getProperty(Answer.forumCat_attachements.getName()), mgr.MODE_EDIT)%>
                    </div>
                </div>
            <%}%>
            <div class="sfcLinea">
                <input type="submit" class="boton" value="Guardar">
                <input type="button" class="boton" value="Regresar" onclick="javascript:history.go(-1);">
            </div>
            </fieldset>
        </form>
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
