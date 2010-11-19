package org.semanticwb.resources.sem.forumcat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

public class SWBForumCatResource extends org.semanticwb.resources.sem.forumcat.base.SWBForumCatResourceBase {
    public static final int STATUS_REGISTERED=1;
    public static final int STATUS_ACEPTED=2;
    public static final int STATUS_REMOVED=3;
    private static Logger log = SWBUtils.getLogger(SWBForumCatResource.class);

    public SWBForumCatResource()
    {
    }

    public SWBForumCatResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try {
            request.setAttribute("listQuestions", listQuestionInvs());
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/forumCat/swbForumCat.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        User user=response.getUser();
        WebSite website=response.getWebPage().getWebSite();
        if(isAcceptGuessComments() || user.isSigned()) {
            String action=response.getAction();
            response.setAction(response.Action_EDIT);
            if(action.equals("addQuestion")) {
                SWBFormMgr mgr = new SWBFormMgr(Question.forumCat_Question, getResourceBase().getSemanticObject(), SWBFormMgr.MODE_CREATE);
                try {
                  SemanticObject semObject = mgr.processForm(request);
                    Question question=(Question)semObject.createGenericInstance();
                    if (user != null && user.isSigned()) {
                        question.setCreator(user);
                    }
                    question.setForumResource(this);
                    
                    if (request.getParameter("tags") != null) {
                        question.setTags(request.getParameter("tags"));
                    }
                    SemanticObject semObjectChild = SemanticObject.createSemanticObject((request.getParameter("categoryuri")));
                    WebPage webPage = (WebPage) semObjectChild.createGenericInstance();
                    question.setWebpage(webPage);
                   if (isIsModerate()) {
                    question.setQueStatus(STATUS_REGISTERED);
                    } else{
                        question.setQueStatus(STATUS_ACEPTED);
                    }
                } catch(FormValidateException e) { log.error(e); }
            } else if(action.equals("editQuestion")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                try {
                    mgr.processForm(request);
                    if (request.getParameter("categoryuri")!=null && !request.getParameter("categoryuri").equals("")){
                        Question question=(Question)semObject.createGenericInstance();
                        SemanticObject semObjectChild = SemanticObject.createSemanticObject((request.getParameter("categoryuri")));
                        WebPage webPage = (WebPage) semObjectChild.createGenericInstance();
                        question.setWebpage(webPage);
                        if (request.getParameter("tags") != null) {
                            question.setTags(request.getParameter("tags"));
                        }
                    }
                } catch(FormValidateException e) { log.error(e); }
                if (request.getParameter("org")!=null){
                  response.setAction(request.getParameter("org"));
                }else{
                  response.setAction("edit");
                }
                response.setRenderParameter("uri", request.getParameter("uri"));
            } else if(action.equals("removeQuestion")) {
                try {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
//TODO: revisar regla de negocio
                    Question question=(Question)semObject.createGenericInstance();
                    question.setQueStatus(STATUS_REMOVED);
//                    semObject.remove();
                    if (request.getParameter("org")!=null){
                      response.setAction(request.getParameter("org"));
                    }else{
                      response.setAction("edit");
                    }
                } catch(Exception e) { log.error(e); }
            } else if(action.equals("answerQuestion")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question=(Question)semObject.createGenericInstance();
                SWBFormMgr mgr = new SWBFormMgr(Answer.forumCat_Answer, semObject, SWBFormMgr.MODE_CREATE);
                try {
                    SemanticObject semObjectChild = mgr.processForm(request);
                    Answer answer=(Answer)semObjectChild.createGenericInstance();
                    answer.setAnsQuestion(question);
                    if (isIsModerate()){
                        answer.setAnsStatus(STATUS_REGISTERED);
                    }else{
                        answer.setAnsStatus(STATUS_ACEPTED);
                    }
                } catch(FormValidateException e) { log.error(e); }
                response.setAction("showDetail");
                response.setRenderParameter("uri", request.getParameter("uri"));
            } else if(action.equals("editAnswer")){
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                try {
                    mgr.processForm(request);
                } catch(FormValidateException e) { log.error(e); }
                if (request.getParameter("org")!=null){
                  response.setAction(request.getParameter("org"));
                }else{
                  response.setAction("showDetail");
                }
                Answer answer=(Answer)semObject.createGenericInstance();
                response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
            } else if(action.equals("markQuestionAsInnapropiate")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question=(Question)semObject.createGenericInstance();
                try {
                    if(!question.isQueIsApropiate()) {
                        int innapropiateCount=question.getQueInappropriate()+1;
                        question.setQueInappropriate(innapropiateCount);
                        if(innapropiateCount>=getMaxInnapropiateCount()) {//Enviar correo a administradores del foro
                            Role role=website.getUserRepository().getRole("adminForum");
                            Iterator <GenericObject> itGo=role.listRelatedObjects();
                            while(itGo.hasNext()) {
                                GenericObject GenericObject=itGo.next();
                                if(GenericObject instanceof User){
                                    User userAdminForum=(User)GenericObject;
                                    if(userAdminForum.getEmail()!=null){
                                      SWBMail swbMail = new SWBMail();
                                      InternetAddress address1 = new InternetAddress();
//TODO: agregar correos
                                      address1.setAddress("gsixtos@infotec.com.mx");
                                      InternetAddress address2 = new InternetAddress();
                                      address2.setAddress("gsixtos@hotmail.com");
                                      ArrayList aAddress = new ArrayList();
                                      aAddress.add(address1);
                                      aAddress.add(address2);
                                      swbMail.setAddress(aAddress);
                                      swbMail.setContentType("text/html");
//TODO: revisar mensaje
                                      swbMail.setData("Esta respuesta ya sobrepaso el umbral de lo considerado como inapropiado");
                                      swbMail.setSubject("Respuesta inapropiada");
                                      swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                                      swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));
                                      SWBUtils.EMAIL.sendBGEmail(swbMail);

                                    }
                                }
                            }
                        }
                    }
                if (request.getParameter("org")!=null){
                  response.setAction(request.getParameter("org"));
                   response.setRenderParameter("uri", request.getParameter("uri"));
                }else{
                  response.setAction("edit");
                }
                } catch(Exception e) { log.error(e); }
            } else if(action.equals("bestAnswer")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Answer answer=(Answer)semObject.createGenericInstance();
                try {
                    if(!answer.isBestAnswer()) {
                        answer.setBestAnswer(true);
                    }
                } catch(Exception e) { log.error(e); }
                response.setAction("showDetail");
                response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
            } else if(action.equals("markAnswerAsInnapropiate")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Answer answer=(Answer)semObject.createGenericInstance();
                try {
                    if(!answer.isAnsIsAppropiate()) {
                        int innapropiateCount=answer.getAnsInappropriate()+1;
                        answer.setAnsInappropriate(innapropiateCount);
                        if(innapropiateCount>=getMaxInnapropiateCount()) {//Enviar correo a administradores del foro
                            Role role=website.getUserRepository().getRole("adminForum");
                            Iterator <GenericObject> itGo=role.listRelatedObjects();
                            while(itGo.hasNext()) {
                                GenericObject GenericObject=itGo.next();
                                if(GenericObject instanceof User){
                                    User userAdminForum=(User)GenericObject;
                                    if(userAdminForum.getEmail()!=null) {
                                        //Enviar correo a este usuario para avisarleque esta Respuesta ya sobrepaso el umbral de lo considerado como inapropiado
                                        //TODO
                                    }
                                }
                            }
                        }
                    }
                } catch(Exception e) { log.error(e); }
                response.setAction("showDetail");
                response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
            } else if(action.equals("closeQuestion")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question=(Question)semObject.createGenericInstance();
                try {
                    if(!question.isClosed()) {
                        question.setClosed(true);
                    }
                } catch(Exception e) { log.error(e); }
                response.setAction("showDetail");
                response.setRenderParameter("uri", question.getURI());
            } else if(action.equals("openQuestion")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question=(Question)semObject.createGenericInstance();
                try {
                    if(question.isClosed()) {
                        question.setClosed(false);
                    }
                } catch(Exception e) { log.error(e); }
                response.setAction("showDetail");
                response.setRenderParameter("uri", question.getURI());
            } else if(action.equals("voteQuestion")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question=(Question)semObject.createGenericInstance();

                boolean hasVoted = false;
                Iterator<QuestionVote> itQuestionVote = QuestionVote.ClassMgr.listQuestionVoteByQuestionVote(question);
                while (itQuestionVote.hasNext() && !hasVoted) {
                    QuestionVote av = itQuestionVote.next();
                    if (av.getUserVote().getURI().equals(user.getURI())) {
                        hasVoted = true;
                    }
                }

                QuestionVote questionVote = null;
                if (!hasVoted) {
                    questionVote = QuestionVote.ClassMgr.createQuestionVote(website);
                    questionVote.setQuestionVote(question);
                    questionVote.setUserVote(user);
                }

                if (questionVote != null) {
                    if(request.getParameter("commentVote")!=null && request.getParameter("commentVote").trim().length()>0){
                        questionVote.setCommentVote(request.getParameter("commentVote"));
                    }
                    if(request.getParameter("likeVote")!=null){
                        boolean likeVote=Boolean.parseBoolean(request.getParameter("likeVote"));
                        questionVote.setLikeVote(likeVote);
                    }
                    if (request.getParameter("org")!=null){
                      response.setAction(request.getParameter("org"));
                      response.setRenderParameter("uri", request.getParameter("uri"));
                    }else{
                      response.setAction("edit");
                    }
                }
            } else if(action.equals("voteAnswer")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Answer answer=(Answer)semObject.createGenericInstance();

                boolean hasVoted = false;
                Iterator<AnswerVote> itAnswerVote = AnswerVote.ClassMgr.listAnswerVoteByAnswerVote(answer);
                while (itAnswerVote.hasNext() && !hasVoted) {
                    AnswerVote av = itAnswerVote.next();
                    if (av.getAnsUserVote().getURI().equals(user.getURI())) {
                        hasVoted = true;
                    }
                }

                AnswerVote answerVote = null;
                if (!hasVoted) {
                    answerVote = AnswerVote.ClassMgr.createAnswerVote(website);
                    answerVote.setAnswerVote(answer);
                    answerVote.setAnsUserVote(user);
                }

                if (answerVote != null) {
                    if(request.getParameter("commentVote")!=null && request.getParameter("commentVote").trim().length()>0){
                        answerVote.setAnsCommentVote(request.getParameter("commentVote"));
                    }
                    if(request.getParameter("likeVote")!=null){
                        boolean likeVote=Boolean.parseBoolean(request.getParameter("likeVote"));
                        answerVote.setLikeAnswer(likeVote);
                    }
                    response.setAction("showDetail");
                    response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
                }
            } else if(action.equals("removeAnswer")) {
                try
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    response.setAction("showDetail");
                    Answer answer=(Answer)semObject.createGenericInstance();
                    response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
//TODO: revisar regla de negocio
                    answer.setAnsStatus(STATUS_REMOVED);
//                    semObject.remove();
                }catch(Exception e) { log.error(e); }
            } else if(action.equals("subcribe2question")){
                boolean isSuscribed = false;
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question=(Question)semObject.createGenericInstance();
                Iterator<QuestionSubscription> it_subs = QuestionSubscription.ClassMgr.listQuestionSubscriptionByQuestionObj(question);
                while (it_subs.hasNext() && !isSuscribed) {
                    QuestionSubscription qs = it_subs.next();
                    if (qs.getUserObj().getURI().equals(user.getURI())) {
                        isSuscribed = true;
                    }
                }

                if (!isSuscribed) {
                    try
                    {
                        QuestionSubscription questionSubs = QuestionSubscription.ClassMgr.createQuestionSubscription(website);
                        questionSubs.setQuestionObj(question);
                        questionSubs.setUserObj(user);
                        //TODO: Enviar correo
                    }catch(Exception e) { log.error(e); }
                    if (request.getParameter("org")!=null){
                      response.setAction(request.getParameter("org"));
                       response.setRenderParameter("uri", request.getParameter("uri"));
                    }else{
                      response.setAction("edit");
                    }
                }
            }else if(action.equals("subcribe2category")){
                try
                {
//TODO: que se hace aqui
/* esta no supe cuando se debe de llamar  -RGJS*/
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    WebPage category=(WebPage)semObject.createGenericInstance();
                    CategorySubscription catSubs = CategorySubscription.ClassMgr.createCategorySubscription(website);
                    catSubs.setCategoryWebpage(category);
                    catSubs.setCategoryUser(user);
                }catch(Exception e) { log.error(e); }
            }else if(action.equals("AcceptQuestion")){
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question=(Question)semObject.createGenericInstance();
                question.setQueStatus(STATUS_ACEPTED);
                response.setAction("moderate");
            }else if(action.equals("AcceptAnswer")){
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Answer answer=(Answer)semObject.createGenericInstance();
                answer.setAnsStatus(STATUS_ACEPTED);
                response.setAction("moderate");
            }else if(action.equals("RejectQuestion")){
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question=(Question)semObject.createGenericInstance();
                question.setQueStatus(STATUS_REMOVED);
                response.setAction("moderate");
            }else if(action.equals("RejectAnswer")){
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Answer answer=(Answer)semObject.createGenericInstance();
                answer.setAnsStatus(STATUS_REMOVED);
                response.setAction("moderate");
            }
        }
        response.setMode(response.Mode_VIEW);
    }
}