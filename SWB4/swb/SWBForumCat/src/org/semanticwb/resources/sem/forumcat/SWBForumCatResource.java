package org.semanticwb.resources.sem.forumcat;


import java.io.IOException;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

public class SWBForumCatResource extends org.semanticwb.resources.sem.forumcat.base.SWBForumCatResourceBase
{
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
            request.setAttribute("acceptguesscomments", isAcceptGuessComments());
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
        if(isAcceptGuessComments() || user.isSigned())
        {
            String action=response.getAction();
            response.setAction(response.Action_EDIT);
            System.out.println("action:"+action);
            System.out.println("uri:"+request.getParameter("uri"));
            if(action.equals("addQuestion")){
                SWBFormMgr mgr = new SWBFormMgr(Question.forumCat_Question, getResourceBase().getSemanticObject(), SWBFormMgr.MODE_CREATE);
                try
                {
                    SemanticObject semObj = mgr.processForm(request);
                    Question question=(Question)semObj.createGenericInstance();
                    if (user != null && user.isSigned()) {
                        question.setCreator(user);
                    }
                    question.setForumResource(this);
                    question.setQueStatus(STATUS_REGISTERED);
                }catch(FormValidateException e) { log.error(e); }
            }else if(action.equals("editQuestion")){
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                try
                {
                    mgr.processForm(request);
                }catch(FormValidateException e) { log.error(e); }
            }else if(action.equals("removeQuestion")){
                try
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    semObject.remove();
                }catch(Exception e) { log.error(e); }
            }else if(action.equals("answerQuestion")){
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question=(Question)semObject.createGenericInstance();
                SWBFormMgr mgr = new SWBFormMgr(Answer.forumCat_Answer, semObject, SWBFormMgr.MODE_CREATE);
                try
                {
                    SemanticObject semObj = mgr.processForm(request);
                    Answer answer=(Answer)semObj.createGenericInstance();
                    answer.setAnsQuestion(question);
                    answer.setAnsStatus(STATUS_REGISTERED);
                }catch(FormValidateException e) { log.error(e); }
            }else if(action.equals("editAnswer")){
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                try
                {
                    mgr.processForm(request);
                }catch(FormValidateException e) { log.error(e); }
            }else if(action.equals("markQuestionAsInnapropiate")){
                System.out.println("entra a markQuestionAsInnapropiate");
                try
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    Question question=(Question)semObject.createGenericInstance();
                    if(!question.isQueIsApropiate()){
                        int innapropiateCount=question.getQueInappropriate()+1;
                        question.setQueInappropriate(innapropiateCount);
                        if(innapropiateCount>=getMaxInnapropiateCount()){//Enviar correo a administradores del foro
                            Role role=website.getUserRepository().getRole("adminForum");
                            Iterator <GenericObject> itGo=role.listRelatedObjects();
                            while(itGo.hasNext()){
                                GenericObject GenericObject=itGo.next();
                                if(GenericObject instanceof User){
                                    User userAdminForum=(User)GenericObject;
                                    if(userAdminForum.getEmail()!=null){
                                        //Enviar correo a este usuario para avisarleque esta Pregunta ya sobrepaso el umbral de lo considerado como inapropiado
                                        //TODO
                                    }
                                }
                            }
                        }
                    }
                    System.out.println("Inapropiados que:"+question.getQueInappropriate());
                }catch(Exception e) { log.error(e); }
            }else if(action.equals("markAnswerAsInnapropiate")){
                System.out.println("entra a markAnswerAsInnapropiate");
                try
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    Answer answer=(Answer)semObject.createGenericInstance();
                    if(!answer.isAnsIsAppropiate()){
                        int innapropiateCount=answer.getAnsInappropriate()+1;
                        answer.setAnsInappropriate(innapropiateCount);
                        if(innapropiateCount>=getMaxInnapropiateCount()){//Enviar correo a administradores del foro
                            Role role=website.getUserRepository().getRole("adminForum");
                            Iterator <GenericObject> itGo=role.listRelatedObjects();
                            while(itGo.hasNext()){
                                GenericObject GenericObject=itGo.next();
                                if(GenericObject instanceof User){
                                    User userAdminForum=(User)GenericObject;
                                    if(userAdminForum.getEmail()!=null){
                                        //Enviar correo a este usuario para avisarleque esta Respuesta ya sobrepaso el umbral de lo considerado como inapropiado
                                        //TODO
                                    }
                                }
                            }
                        }
                    }
                    System.out.println("Inapropiados Ans:"+answer.getAnsInappropriate());
                }catch(Exception e) { log.error(e); }
            }else if(action.equals("voteQuestion")){
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question=(Question)semObject.createGenericInstance();

                QuestionVote questionVote = QuestionVote.ClassMgr.createQuestionVote(website);
                questionVote.setQuestionVote(question);
                questionVote.setUserVote(user);
                if(request.getParameter("commentVote")!=null && request.getParameter("commentVote").trim().length()>0){
                    questionVote.setCommentVote(request.getParameter("commentVote"));
                }
                if(request.getParameter("likeVote")!=null){
                    boolean likeVote=Boolean.parseBoolean(request.getParameter("likeVote"));
                    questionVote.setLikeVote(likeVote);
                }
            }else if(action.equals("voteAnswer")){
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Answer answer=(Answer)semObject.createGenericInstance();

                AnswerVote answerVote = AnswerVote.ClassMgr.createAnswerVote(website);
                answerVote.setAnswerVote(answer);
                answerVote.setAnsUserVote(user);
                if(request.getParameter("commentVote")!=null && request.getParameter("commentVote").trim().length()>0){
                    answerVote.setAnsCommentVote(request.getParameter("commentVote"));
                }
                if(request.getParameter("likeVote")!=null){
                    boolean likeVote=Boolean.parseBoolean(request.getParameter("likeVote"));
                    answerVote.setLikeAnswer(likeVote);
                }
            }else if(action.equals("removeAnswer")){
                try
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    semObject.remove();
                }catch(Exception e) { log.error(e); }
            }else if(action.equals("subcribe2question")){
                try
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    Question question=(Question)semObject.createGenericInstance();
                    QuestionSubscription questionSubs = QuestionSubscription.ClassMgr.createQuestionSubscription(website);
                    questionSubs.setQuestionObj(question);
                    questionSubs.setUserObj(user);
                }catch(Exception e) { log.error(e); }
            }else if(action.equals("subcribe2category")){
                try
                {
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
