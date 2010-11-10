<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.resources.sem.forumcat.*"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.resources.sem.forumcat.SWBForumCatResource"%>
<style>

    #swbforumcat{
        font-family:Verdana, Arial, Helvetica, sans-serif;
        color:#009999;
        font-size:12px;
    }

    #swbforumcat a{
        color:#007777;
        text-decoration:none;
        clear: both;
    }

    #swbforumcat a:hover {
        color:#007777;
        text-decoration:underline;
    }

    #swbforumcat .sfcQuestList li{
        list-style-image:url(/swbadmin/jsp/forumCat/qst.jpg);
        padding:10px;
    }

    #swbforumcat .sfcQuest{
        border: 1px gold solid;
        color:#007777;
    }

/*    #swbforumcat .sfcQuest:hover{
        font-size: 20px;
    }

    #swbforumcat .sfcQuest:hover .sfcOptions li{
        display:inline;
        font-size:10px;
    }
    #swbforumcat .sfcQuest:hover .sfcInfo li{
        display:inline;
        font-size:10px;
    }

    #swbforumcat .sfcQuest:hover .sfcInfo li + li {
        color: red;
    }

    #swbforumcat .sfcQuestList .sfcQuest:hover  {
        color: lime;
        padding:0px;
        font-size: 18px;
    }
    #_swbforumcat .sfcQuestList .sfcQuest:hover li + li{
        color: yellow;
        font-size: 28px;
    }

    #swbforumcat .sfcQuestList .sfcQuest:hover li:before{
        color: orange;
        font-size: 8px;
    }
        #swbforumcat .sfcQuestList .sfcQuest:hover li:after{
        color: grey;
        font-size: 8px;
    }

    #swbforumcat .sfcQuest:hover .sfcQuestList li {
        color: green;
        padding:0px;
    }


    #swbforumcat .sfcQuest:hover .sfcQuestList li + .sfcQuestList li{
        color: yellow;
    }

    #swbforumcat .sfcQuest:hover .sfcQuest:before{
        color: orange;
    }
*/
    #swbforumcat .sfcQuestSpe{
        font-size:11px;
    }

    #swbforumcat .sfcAnsList {
        font-size:11px;
        color:#559999;

    }
    #swbforumcat .sfcAnsList li{
        list-style-image:url(/swbadmin/jsp/forumCat/ans.jpg);
        margin:10px;
    }

    #swbforumcat .sfcAns{
        border: 1px coral solid
    }

    #swbforumcat .sfcOptions li{
        display:inline;
        font-size:10px;
        _display:none;
    }

    #swbforumcat .sfcInfo li{
        display:inline;
        font-size:10px;
        _display:none;
    }

    #swbforumcat .sfcOptions a{
        display:inline-block;
        background-color:#CCCCCC;
        text-align:center;
        width:80px;

        background-color:#F5F5F5;
        border:1px solid #EBEBEB;
        color:#0072BC;
        margin-left:10px;
        padding:2px 7px;
        text-decoration:none;
    }

    #swbforumcat .sfcOptions a:hover {
        background-color:#DDEEFF;
        border:1px solid #BBDDFF;
        color:#0072BC;
        cursor:default;
        text-decoration:none;

    }

    #swbforumcat .sfcPag li{
        display:inline;
    }

    #swbforumcat .sfcEtiqueta , .sfcCampo{
        display: inline-block;
    }
    #swbforumcat .sfcCampo textarea{
        font-family:Verdana, Arial, Helvetica, sans-serif;
        font-size:12px;
        _color:#009999;
    }
    #swbforumcat .sfcEtiqueta{
        color:#007777;
        width: 200px;
    }

    #swbforumcat .sfcLinea{
        clear:both;
    }

</style>

<%
    SimpleDateFormat dateFormat;
    SimpleDateFormat iso8601dateFormat;
    DecimalFormat df = new java.text.DecimalFormat("#0.0#");


    String lang = "es";
    Locale locale = new Locale(lang);
    dateFormat = new SimpleDateFormat("yyyy-MMM-dd", locale);
    String[] months =
    {
        "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
    };
    java.text.DateFormatSymbols fs = dateFormat.getDateFormatSymbols();
    fs.setShortMonths(months);
    dateFormat.setDateFormatSymbols(fs);
    String defaultFormat = "d 'de' MMMM 'del' yyyy 'a las' HH:mm";
    iso8601dateFormat = new SimpleDateFormat(defaultFormat, locale);
%>
<div id="swbforumcat">
    <%
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
    <%= SWBFormMgr.DOJO_REQUIRED%>
    <form id="<%--=mgr.getFormName()--%>formaCaptura" name="datosRegistro" dojoType="dijit.form.Form" class="swbform" action="<%=actionURL%>" method="post">
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
    </form>
    <%
     } else if (action != null && action.equals("edit")) {
         if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isModerate)) {
             if (isAdmin) {
                 renderURL.setAction("moderate");
    %>
    <a href="<%=renderURL%>">Moderar foro</a>
    <%
             }
         }
         int recPerPage = 5; //TODO: poner el numero de registros por paginas en la administracion
         int nRec = 0;
         int nPage;
         try {
             nPage = Integer.parseInt(request.getParameter("page"));
         } catch (Exception ignored) {
             nPage = 1;
         }
         Iterator itQuestions = (Iterator) request.getAttribute("listQuestions");
         int nAnswer;
         int nLike;
         int nUnlike;
    %>
    <ul class="sfcQuestList">
        <%
             while (itQuestions.hasNext()) {
                 Question question = (Question) itQuestions.next();

                 if (question.getQueStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                     nRec++;
                     if ((nRec > (nPage - 1) * recPerPage) && (nRec <= (nPage) * recPerPage)) {
        %>
        <li>
            <%
                 renderURL.setParameter("uri", question.getURI());
                 actionURL.setParameter("uri", question.getURI());
                 renderURL.setAction("showDetail");
                 //System.out.println("Photo->" + question.getCreator().getPhoto());
            %>
            <div class="sfcQuest">
                <a href="<%=renderURL%>"><%=question.getQuestion()%></a>
                <ul class="sfcInfo">
                    <% if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_selectCategory)) {%>
                    <li><%=question.getWebpage() != null ? question.getWebpage().getDisplayName() : "---"%></li>
                    <%}%>
                    <li>De:<%=question.getCreator() != null ? question.getCreator().getFullName() : "---"%></li>
                    <%
                         nAnswer = 0;
                         Iterator<Answer> itAnswers = question.listAnswerInvs();
                         while (itAnswers.hasNext()) {
                             Answer answer = itAnswers.next();
                             if (answer.getAnsStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                                 nAnswer++;
                             }
                         }
                    %>
                    <li>Respuestas:<%=nAnswer%></li>
                    <%
                         if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionVotable)) {

                             nLike = 0;
                             nUnlike = 0;
                             Iterator<QuestionVote> itQuestionVote = QuestionVote.ClassMgr.listQuestionVoteByQuestionVote(question);
                             while (itQuestionVote.hasNext()) {
                                 QuestionVote questionVote = itQuestionVote.next();
                                 if (questionVote.isLikeVote()) {
                                     nLike++;
                                 } else {

                                     nUnlike++;
                                 }
                             }

                    %>
                    <li>Me gusta:<%=nLike%></li>
                    <li>No me gusta:<%=nUnlike%></li>
                    <%
                         }
                    %>
                    <li><%=iso8601dateFormat.format(question.getCreated())%></li>
                </ul>
                <ul class="sfcOptions">
                    <%
                         if (isAdmin || user.isSigned() && question.getCreator() != null && user.getURI().equals(question.getCreator().getURI())) {
                             renderURL.setAction("editQuestion");
                             renderURL.setParameter("org", "edit");
                    %>
                    <li><a href="<%=renderURL%>">Editar</a></li>
                    <%
                         }
                         if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateQuestions)) {
                             actionURL.setAction("markQuestionAsInnapropiate");
                    %>
                    <li><a href="<%=actionURL%>">Inapropiado</a></li>
                    <%
                         }
                         if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionVotable)) {
                             if (!user.getURI().equals(question.getCreator().getURI())) {

                                 actionURL.setAction("voteQuestion");
                                 actionURL.setParameter("likeVote", "true");
                    %>
                    <li><a href="<%=actionURL%>">Me gusta</a></li>
                    <%
                         actionURL.setParameter("likeVote", "false");
                    %>
                    <li><a href="<%=actionURL%>">No me gusta</a></li>
                    <%
                            }
                         }

                         if (isAdmin) {
                             actionURL.setAction("removeQuestion");
                             actionURL.setParameter("uri", question.getEncodedURI());
                             actionURL.setParameter("org", "edit");
          String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";%>
                    <li><a href="<%=removeUrl%>">Eliminar</a></li>
                    <%
                         }
                         if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionSubscription)) {
                             actionURL.setAction("subcribe2question");
                             actionURL.setParameter("uri", question.getURI());
                             actionURL.setParameter("org", "edit");
                    %>
                    <li><a href="<%=actionURL%>">Subscribir</a></li>
                    <%
                         }
                    %>
                </ul>
            </div>
            <%
                     }
                 }
            %>
        </li>
        <%
             }
        %>
    </ul>
    <ul class="sfcPag">
        <%
             for (int countPage = 1; countPage < (Math.ceil((double) nRec / (double) recPerPage) + 1); countPage++) {
                 pageURL.setParameter("page", "" + (countPage));
                 if (countPage == nPage) {
        %>
        <li><%=countPage%></li>
        <%
         } else {
        %>
        <li><a href="<%=pageURL%>"><%=countPage%></a></li>
        <%
                 }
             }
        %>
    </ul>
    <%
         if (user.isSigned() || semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptGuessComments)) {
             renderURL.setAction("add");
    %>
    <a href="<%=renderURL%>">Realizar una nueva pregunta</a>
    <%
         }
     } else if (action != null && action.equals("showDetail")) {
    %>
    <ul class="sfcQuestList">
        <%
             {
                 SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                 Question question = (Question) semObject.createGenericInstance();
                 if (question.getQueStatus() == SWBForumCatResource.STATUS_ACEPTED) {
        %>
        <li>
            <%
                 renderURL.setParameter("uri", question.getURI());
                 actionURL.setParameter("uri", question.getURI());
            %>
            <div class="sfcQuest"><%=question.getQuestion()%><p class="sfcQuestSpe"><%=question.getSpecifications()%></p>
                <ul class="sfcInfo">
                    <li><%=question.getWebpage() != null ? question.getWebpage().getDisplayName() : "---"%></li>
                    <li>De:<%=question.getCreator() != null ? question.getCreator().getFullName() : "---"%></li>
                    <%
                         int nLike;
                         int nUnlike;

                         if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionVotable)) {
                             nLike = 0;
                             nUnlike = 0;
                             Iterator<QuestionVote> itQuestionVote = QuestionVote.ClassMgr.listQuestionVoteByQuestionVote(question);
                             while (itQuestionVote.hasNext()) {
                                 QuestionVote questionVote = itQuestionVote.next();
                                 if (questionVote.isLikeVote()) {
                                     nLike++;
                                 } else {
                                     nUnlike++;
                                 }
                             }
                    %>
                    <li>Me gusta:<%=nLike%></li>
                    <li>No me gusta:<%=nUnlike%></li>
                    <%
                         }
                    %>
                    <li><%=iso8601dateFormat.format(question.getCreated())%></li>
                </ul>
                <ul class="sfcOptions">
                    <%
                         if (isAdmin || user.isSigned() && question.getCreator() != null && user.getURI().equals(question.getCreator().getURI())) {
                             renderURL.setAction("editQuestion");
                             renderURL.setParameter("org", "showDetail");
                    %>
                    <li><a href="<%=renderURL%>">Editar</a></li>
                    <%
                         }
                         if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateQuestions)) {
                             actionURL.setAction("markQuestionAsInnapropiate");
                             actionURL.setParameter("org", "showDetail");
                    %>
                    <li><a href="<%=actionURL%>">Inapropiado</a></li>
                    <%
                         }
                         if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionVotable)) {
                             if (!user.getURI().equals(question.getCreator().getURI())) {
                                 actionURL.setAction("voteQuestion");
                                 actionURL.setParameter("org", "showDetail");
                                 actionURL.setParameter("likeVote", "true");
                    %>
                    <li><a href="<%=actionURL%>">Me gusta</a></li>
                    <%
                         actionURL.setParameter("likeVote", "false");
                    %>
                    <li><a href="<%=actionURL%>">No me gusta</a></li>
                    <%
                             }
                         }
                         if (isAdmin) {
                             actionURL.setAction("removeQuestion");
                             actionURL.setParameter("uri", question.getEncodedURI());
                             actionURL.setParameter("org", "edit");
          String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";%>
                    <li><a href="<%=removeUrl%>">Eliminar</a></li>
                    <%
                         }
                         if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isQuestionSubscription)) {
                             actionURL.setAction("subcribe2question");
                             actionURL.setParameter("uri", question.getURI());
                             actionURL.setParameter("org", "showDetail");
                    %>
                    <li><a href="<%=renderURL%>">Subscribir</a></li>
                    <%
                         }
                    %>
                </ul>
            </div>
            <%
                 {
            %>
            <ul class="sfcAnsList">
                <%
                     Iterator<Answer> itAnswers = question.listAnswerInvs();
                     while (itAnswers.hasNext()) {
                         Answer answer = itAnswers.next();
                         if (answer.getAnsStatus() == SWBForumCatResource.STATUS_ACEPTED) {
                             renderURL.setParameter("uri", answer.getURI());
                             actionURL.setParameter("uri", answer.getURI());
                %>
                <li><div class="sfcAns"><%=answer.getAnswer()%>
                        <ul class="sfcInfo">
                            <li>De:<%=answer.getCreator() != null ? answer.getCreator().getFullName() : "---"%></li>
                            <%
                                 if (semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                     nLike = 0;
                                     nUnlike = 0;
                                     Iterator<AnswerVote> itAnswerVote = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(answer);
                                     while (itAnswerVote.hasNext()) {
                                         AnswerVote answerVote = itAnswerVote.next();
                                         if (answerVote.isLikeAnswer()) {
                                             nLike++;
                                         } else {
                                             nUnlike++;
                                         }
                                     }
                            %>
                            <li>Me gusta:<%=nLike%></li>
                            <li>No me gusta:<%=nUnlike%></li>
                            <%
                                 }
                            %>
                            <li><%=iso8601dateFormat.format(answer.getCreated())%></li>
                        </ul>
                        <ul class="sfcOptions">
                            <%
                                 if (isAdmin || user.isSigned() && answer.getCreator() != null && user.getURI().equals(answer.getCreator().getURI())) {
                                     renderURL.setAction("editAnswer");
                            %>
                            <li><a href="<%=renderURL%>">Editar</a></li>
                            <%
                                 }
                                 if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_markInnapropiateAnswers)) {
                                     actionURL.setAction("markAnswerAsInnapropiate");
                            %>
                            <li><a href="<%=actionURL%>">Inapropiado</a></li>
                            <%
                                 }
                                 if (user.isSigned() && semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_isAnswerVotable)) {
                                     if (!user.getURI().equals(answer.getCreator().getURI())) {
                                        actionURL.setAction("voteAnswer");
                                        actionURL.setParameter("likeVote", "true");
                            %>
                            <li><a href="<%=actionURL%>">Me gusta</a></li>
                            <%
                                 actionURL.setParameter("likeVote", "false");
                            %>
                            <li><a href="<%=actionURL%>">No me gusta</a></li>
                            <%
                                     }
                                 }
                                 if (isAdmin) {
                                     actionURL.setAction("removeAnswer");
                                     actionURL.setParameter("uri", answer.getEncodedURI());
                                     actionURL.setParameter("org", "showDetail");
                                     String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";
                            %>
                            <li><a href="<%=removeUrl%>">Eliminar</a></li>
                            <%
                                 }
                            %>
                        </ul>
                    </div>
                </li>
                <%
                         } //if
                     } //while answer
%>
            </ul>
            <%
                 }
                 if (user.isSigned() || semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptGuessComments)) {
                     renderURL.setAction("answerQuestion");
                     renderURL.setParameter("uri", question.getURI());
            %>
            <a href="<%=renderURL%>">Responder a esta pregunta</a><br/>
            <%
                     }
                 }
            %>
        </li>
        <%
             }
        %>
    </ul>
    <%
         if (user.isSigned() || semanticBase.getBooleanProperty(SWBForumCatResource.forumCat_acceptGuessComments)) {
             renderURL.setAction("add");
    %>
    <a href="<%=renderURL%>">Realizar una nueva pregunta</a>
    <a href="<%=paramRequest.getRenderUrl()%>">Regresar</a>
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
    <%= SWBFormMgr.DOJO_REQUIRED%>
    <form id="<%=mgr.getFormName()%>" name="datosRegistro" dojoType="dijit.form.Form" class="swbform" action="<%=actionURL%>" method="post">
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
        <div class="sfcLinea">
            <div class="sfcEtiqueta">
                <%=mgr.renderLabel(request, semClass.getProperty(Question.forumCat_webpage.getName()), mgr.MODE_EDIT)%>
            </div>
            <div class="sfcCampo">
                <%=mgr.renderElement(request, semClass.getProperty(Question.forumCat_webpage.getName()), mgr.MODE_EDIT)%>
            </div>
        </div>
        <div class="sfcLinea">
            <%@include file="Tree.jsp" %>
        </div>
        <div class="sfcLinea">
            <input type="submit" class="boton" value="Guardar">
            <input type="button" class="boton" value="Regresar" onclick="javascript:history.go(-1);">
        </div>
    </form>
    <%

     } else if (action != null && action.equals("answerQuestion")) {
         SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
         SWBFormMgr mgr = new SWBFormMgr(Answer.forumCat_Answer, semObject, SWBFormMgr.MODE_CREATE);
         mgr.setLang(user.getLanguage());
         mgr.setFilterRequired(false);
         mgr.setType(mgr.TYPE_DOJO);
         actionURL.setAction("answerQuestion");
         actionURL.setParameter("uri", semObject.getURI());
         mgr.setAction(actionURL.toString());
         mgr.addButton(SWBFormButton.newSaveButton());
         mgr.addButton(SWBFormButton.newCancelButton());
    // mgr.addHiddenParameter("uri", semObject.getURI());
    %>
    <%=mgr.renderForm(request)%>
    <%
         SemanticClass semClass = Answer.sclass;
    %>
    <%//= SWBFormMgr.DOJO_REQUIRED%>
    <!--form id="<%//=mgr.getFormName()%>" name="datosRegistro" dojoType="dijit.form.Form" class="swbform" action="<%//=actionURL%>" method="post" >
    <%//= mgr.getFormHiddens()%>
    <%//=mgr.renderLabel(request, semClass.getProperty(Answer.forumCat_answer.getName()), mgr.MODE_CREATE)%>:
    <%//=mgr.renderElement(request, semClass.getProperty(Answer.forumCat_answer.getName()), mgr.MODE_CREATE)%>
    <%//=mgr.renderLabel(request, semClass.getProperty(Answer.forumCat_attachements.getName()), mgr.MODE_CREATE)%>:
    <%//=mgr.renderElement(request, semClass.getProperty(Answer.forumCat_attachements.getName()), mgr.MODE_CREATE)%>
    <input type="submit" class="boton" value="Guardar">
    <input type="button" class="boton" value="Regresar" onclick="javascript:history.go(-1);">
   </form-->
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
    <%= SWBFormMgr.DOJO_REQUIRED%>
    <form id="<%=mgr.getFormName()%>" name="datosRegistro" dojoType="dijit.form.Form" class="swbform" action="<%=actionURL%>" method="post" >
        <%= mgr.getFormHiddens()%>

        <div class="sfcLinea">
            <div class="sfcEtiqueta">
                <%=mgr.renderLabel(request, semClass.getProperty(Answer.forumCat_answer.getName()), mgr.MODE_EDIT)%>:
            </div>
            <div class="sfcCampo">

                <%=mgr.renderElement(request, semClass.getProperty(Answer.forumCat_answer.getName()), mgr.MODE_EDIT)%>
            </div>
        </div>
        <div class="sfcLinea">
            <div class="sfcEtiqueta">
                <%=mgr.renderLabel(request, semClass.getProperty(Answer.forumCat_attachements.getName()), mgr.MODE_EDIT)%>:
            </div>
            <div class="sfcCampo">

                <%=mgr.renderElement(request, semClass.getProperty(Answer.forumCat_attachements.getName()), mgr.MODE_EDIT)%>
            </div>
        </div>
        <div class="sfcLinea">
            <input type="submit" class="boton" value="Guardar">
            <input type="button" class="boton" value="Regresar" onclick="javascript:history.go(-1);">
        </div>
    </form>
    <%
     } else if (action != null && action.equals("moderate")) { //Moderar el foro
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
</div>
