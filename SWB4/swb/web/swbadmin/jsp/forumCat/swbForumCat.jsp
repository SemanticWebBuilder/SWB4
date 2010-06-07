<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.resources.sem.forumcat.*"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>

<%
    User user=paramRequest.getUser();
    
    boolean isAdmin=false;
    Role role = user.getUserRepository().getRole("adminForum");
    UserGroup group = user.getUserRepository().getUserGroup("admin");
    if (role != null && user.hasRole(role) || user.hasUserGroup(group)){
        isAdmin=true;
    }
    WebPage wpage=paramRequest.getWebPage();
    Resource base=paramRequest.getResourceBase();
    boolean isAcceptGuessComments=false;
    if(request.getAttribute("isAcceptGuessComments")!=null) isAcceptGuessComments=((Boolean)request.getAttribute("isAcceptGuessComments")).booleanValue();

    SWBResourceURL renderURL=paramRequest.getRenderUrl();
    SWBResourceURL actionURL=paramRequest.getActionUrl();
    String action=paramRequest.getAction();
    if(action!=null && action.equals("add")){
        SWBFormMgr mgr = new SWBFormMgr(Question.forumCat_Question, base.getSemanticObject(), SWBFormMgr.MODE_CREATE);
        mgr.setLang(user.getLanguage());
        mgr.setFilterRequired(false);
        mgr.setType(mgr.TYPE_DOJO);
        actionURL.setAction("addQuestion");
        mgr.setAction(actionURL.toString());
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newCancelButton());
        %>
            <%=mgr.renderForm(request)%>
        <%
    }else if(action!=null && action.equals("edit")){
        if(isAdmin){
            renderURL.setAction("moderate");
            %>
                <a href="<%=renderURL%>">Moderar foro</a>
            <%
        }

        Iterator itQuestions=(Iterator)request.getAttribute("listQuestions");
        while(itQuestions.hasNext()){
            Question question=(Question)itQuestions.next();
            if(question.getQueStatus()==SWBForumCatResource.STATUS_ACEPTED)
            {
                renderURL.setParameter("uri", question.getURI());
                actionURL.setParameter("uri", question.getURI());
                %>
                    Pregunta:<%=question.getQuestion()%>
                <%
                if(user.isSigned() && question.getCreator()!=null && user.getURI().equals(question.getCreator().getURI())){
                    renderURL.setAction("editQuestion");
                    actionURL.setAction("markQuestionAsInnapropiate");
                    %>
                        (<a href="<%=renderURL%>">editar</a>|
                        <%
                           if(!question.isQueIsApropiate()){%>
                            <a href="<%=actionURL%>">Inapropiado</a>|
                        <%
                            }
                            actionURL.setAction("voteQuestion");
                        %>
                        <a href="<%=actionURL%>">Me gusta</a>|
                        <%actionURL.setAction("removeQuestion");actionURL.setParameter("uri", question.getEncodedURI());String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";%><a href="<%=removeUrl%>">Eliminar</a>)<br/>
                    <%
                }
                if(user.isSigned()){
                    renderURL.setAction("subcribe2question");
                    %>
                        <a href="<%=renderURL%>">subscribir</a>
                    <%
                }
                Iterator <Answer> itAnswers=question.listAnswerInvs();
                while(itAnswers.hasNext()){
                    Answer answer=itAnswers.next();
                    if(answer.getAnsStatus()==SWBForumCatResource.STATUS_ACEPTED)
                    {
                        %>
                            <br/>Respuesta:<%=answer.getAnswer()%><br/>
                        <%
                        if(user.isRegistered() && answer.getCreator()!=null && user.getURI().equals(answer.getCreator().getURI())){
                            renderURL.setAction("editAnswer");
                            renderURL.setParameter("uri", answer.getURI());
                            actionURL.setAction("markAnswerAsInnapropiate");
                            actionURL.setParameter("uri", answer.getURI());
                            %>
                                (<a href="<%=renderURL%>">editar</a>|
                                <%
                                    if(!answer.isAnsIsAppropiate()){%>
                                        <a href="<%=actionURL%>">Inapropiado</a>|
                                    <%
                                    }
                                    actionURL.setAction("voteAnswer");
                                %>
                                <a href="<%=actionURL%>">Me gusta</a>|
                                <%actionURL.setAction("removeAnswer");actionURL.setParameter("uri", answer.getEncodedURI());String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";%><a href="<%=removeUrl%>">Eliminar</a>)<br/>
                            <%
                         }
                    }
                }
                if(isAcceptGuessComments || user.isSigned())
                {
                    renderURL.setAction("answerQuestion");
                    renderURL.setParameter("uri", question.getURI());
                    %>
                        <a href="<%=renderURL%>">Responder a esta pregunta</a><br/>
                    <%
                }
            }
        }
        if(isAcceptGuessComments || user.isSigned())
        {
            renderURL.setAction("add");
            %>
                <br/><br/><a href="<%=renderURL%>">Realizar una nueva pregunta</a><br/>
            <%
        }
    }else if(action!=null && action.equals("editQuestion")){
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));        
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        mgr.setLang(user.getLanguage());
        mgr.setFilterRequired(false);
        mgr.setType(mgr.TYPE_DOJO);
        actionURL.setAction("editQuestion");
        actionURL.setParameter("uri", semObject.getURI());
        mgr.setAction(actionURL.toString());
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newCancelButton());
        %>
            <%=mgr.renderForm(request)%>
        <%
    }else if(action!=null && action.equals("answerQuestion")){
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
        %>
            <%=mgr.renderForm(request)%>
        <%
    }else if(action!=null && action.equals("editAnswer")){
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        mgr.setLang(user.getLanguage());
        mgr.setFilterRequired(false);
        mgr.setType(mgr.TYPE_DOJO);
        actionURL.setAction("editAnswer");
        actionURL.setParameter("uri", semObject.getURI());
        mgr.setAction(actionURL.toString());
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newCancelButton());
        %>
            <%=mgr.renderForm(request)%>
        <%
    }else if(action!=null && action.equals("moderate")){ //Moderar el foro
        System.out.println("entra a moderate");
        boolean flag=false;
        Iterator itQuestions=(Iterator)request.getAttribute("listQuestions");
        while(itQuestions.hasNext()){
            Question question=(Question)itQuestions.next();
            System.out.println("entra a moderate-J1:"+question.getQuestion());
            System.out.println("entra a moderate-1:"+question.listAnswerInvs().hasNext());
            renderURL.setParameter("uri", question.getURI());
            actionURL.setParameter("uri", question.getURI());

            if(question.getQueStatus()==SWBForumCatResource.STATUS_REGISTERED)
            {
                    %>
                    Pregunta:<%=question.getQuestion()%>
                    <%
                    renderURL.setAction("editQuestion");
                    String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";
                    %>
                        (<%if(question.getQueStatus()==SWBForumCatResource.STATUS_REGISTERED){actionURL.setAction("AcceptQuestion");%><a href="<%=actionURL%>">Aceptar</a>|<%actionURL.setAction("RejectQuestion");%><a href="<%=actionURL%>">Rechazar</a><%}%>|<a href="<%=renderURL%>">editar</a>|<%actionURL.setAction("removeQuestion");%><a href="<%=removeUrl%>">Eliminar</a>)<br/>
                    <%

                    renderURL.setAction("subcribe2question");
                    %>
                        <a href="<%=renderURL%>">subscribir</a>
                    <%
                    flag=true;
            }


            Iterator <Answer> itAnswers=question.listAnswerInvs();
            while(itAnswers.hasNext()){
                Answer answer=itAnswers.next();
                System.out.println("J2 flag:"+flag+",answer Sta:"+answer.getAnsStatus());
                if(!flag && answer.getAnsStatus()==SWBForumCatResource.STATUS_REGISTERED)
                {
                        %>
                        Pregunta:<%=question.getQuestion()%>
                        <%
                        renderURL.setAction("editQuestion");
                        String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";
                        %>
                            (<%if(question.getQueStatus()==SWBForumCatResource.STATUS_REGISTERED){actionURL.setAction("AcceptQuestion");%><a href="<%=actionURL%>">Aceptar</a>|<%actionURL.setAction("RejectQuestion");%><a href="<%=actionURL%>">Rechazar</a>|<%}%>
                            <a href="<%=renderURL%>">editar</a>|<%actionURL.setAction("removeQuestion");%><a href="<%=removeUrl%>">Eliminar</a>|
                        <%
                        renderURL.setAction("subcribe2question");
                        %>
                            <a href="<%=renderURL%>">subscribir</a>)<br/>
                        <%
                        flag=true;
                }


                
                if(answer.getAnsStatus()==SWBForumCatResource.STATUS_REGISTERED)
                {
                    %>
                        <br/>Respuesta:<%=answer.getAnswer()%><br/>
                    <%
                    renderURL.setAction("editAnswer");
                    renderURL.setParameter("uri", answer.getURI());
                    actionURL.setAction("AcceptAnswer");
                    actionURL.setParameter("uri", answer.getURI());
                    %>
                        (<a href="<%=actionURL%>">Aceptar</a>|<%actionURL.setAction("RejectAnswer");%><a href="<%=actionURL%>">Rechazar</a>|<a href="<%=renderURL%>">editar</a>|
                        <%actionURL.setAction("removeAnswer");actionURL.setParameter("uri", answer.getEncodedURI());String removeUrl = "javascript:validateRemove('" + actionURL.toString(true) + "');";%><a href="<%=removeUrl%>">Eliminar</a>)<br/>
                    <%
                 }
            }
        }
        if(!flag) {//
            %>
                No existen preguntas, ni respuestas para moderar<br/>
                <a href="javascript:history.go(-1);">Regresar</a>
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

