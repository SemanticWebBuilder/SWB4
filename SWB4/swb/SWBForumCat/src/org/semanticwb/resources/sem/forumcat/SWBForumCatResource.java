package org.semanticwb.resources.sem.forumcat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

public class SWBForumCatResource extends org.semanticwb.resources.sem.forumcat.base.SWBForumCatResourceBase
{

    private static Logger log = SWBUtils.getLogger(SWBForumCatResource.class);
    public static final int STATUS_REGISTERED = 1;
    public static final int STATUS_ACEPTED = 2;
    public static final int STATUS_REMOVED = 3;

    public SWBForumCatResource()
    {
    }

    public SWBForumCatResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    private static String clean(String html)
    {
        html = html.replace("&lt;", "<");
        html = html.replace("&gt;", ">");
        String text = html;
        int pos = text.indexOf("<");
        while (pos != -1)
        {
            int pos2 = text.indexOf(">", pos);
            if (pos2 == -1)
            {
                text = text.substring(0, pos).trim();
            } else
            {
                String tmp = text.substring(0, pos).trim();
                String tmp2 = text.substring(pos2 + 1).trim();
                text = tmp + " " + tmp2;
            }
            pos = text.indexOf("<");
        }
        text = text.trim();
        return text;
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String jsp = getViewJSP();
        if (jsp == null || jsp.trim().equals(""))
        {
            jsp = "/swbadmin/jsp/forumCat/swbForumCat.jsp";
        }
        try
        {
            request.setAttribute("listQuestions", listQuestionInvs());
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher(jsp);
            rd.include(request, response);
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        User user = response.getUser();
        WebSite website = response.getWebPage().getWebSite();

        if (isAcceptGuessComments() || user.isSigned())
        {
            String action = response.getAction();
            response.setAction(response.Action_EDIT);
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
            if (action.equals("addQuestion"))
            {
                addQuestion(request, user, website, response);
            } else if (action.equals("editQuestion"))
            {
                editQuestion(request, user, response);
            } else if (action.equals("removeQuestion"))
            {
                removeQuestion(request, user, response);
            } else if (action.equals("answerQuestion"))
            {
                answerQuestion(request, user, response, website);
            } else if (action.equals("editAnswer"))
            {
                editAnswer(request, response, user);
            } else if (action.equals("markQuestionAsInnapropiate"))
            {
                markQuestionAsInnapropiate(request, website, response);
            } else if (action.equals("markAnswerAsInnapropiate"))
            {
                markAnswerAsInnapropiate(request, website, response);
            } else if (action.equals("bestAnswer"))
            {
                bestAnswer(request, response, user, website);
            } else if (action.equals("closeQuestion"))
            {
                closeQuestion(request, user, response);
            } else if (action.equals("openQuestion"))
            {
                openQuestion(request, user, response);
            } else if (action.equals("voteQuestion"))
            {
                voteQuestion(request, user, website, response);
            } else if (action.equals("voteAnswer"))
            {
                voteAnswer(request, user, website, response);
            } else if (action.equals("removeAnswer"))
            {
                removeAnswer(request, user, response);
            } else if (action.equals("subcribe2question"))
            {
                subcribe2question(request, user, website, response);
            } else if (action.equals("subcribe2category"))
            {
                subcribe2category(request, website, user);
            } else if (action.equals("AcceptQuestion"))
            {
                AcceptQuestion(user, request, response);
            } else if (action.equals("AcceptAnswer"))
            {
                AcceptAnswer(user, request, response);
            } else if (action.equals("RejectQuestion"))
            {
                RejectQuestion(user, request, response);
            } else if (action.equals("RejectAnswer"))
            {
                RejectAnswer(user, request, response);
            }
        }
        response.setMode(response.Mode_VIEW);
    }

    private void RejectAnswer(User user, HttpServletRequest request, SWBActionResponse response)
    {
        boolean isAdmin = false;
        Role role = user.getUserRepository().getRole("adminForum");
        UserGroup group = user.getUserRepository().getUserGroup("admin");
        if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
        {
            isAdmin = true;
        }
        if (isAdmin)
        {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            if (semObject != null)
            {
                Answer answer = (Answer) semObject.createGenericInstance();
                answer.setAnsStatus(STATUS_REMOVED);
                if (answer.getCreator() != null && answer.getCreator().getEmail() != null)
                {
                    String text = clean(answer.getAnswer());
                    String toemail = answer.getCreator().getEmail();
                    try
                    {
                        SWBMail swbMail = new SWBMail();
                        ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
                        aAddress.add(new InternetAddress(toemail));
                        swbMail.setAddress(aAddress);
                        swbMail.setSubject("Respuesta rechazada en el foro " + response.getWebPage().getWebSite().getTitle());
                        swbMail.setData("Su respuesta fue rechazada por no cumplir con las politicas de uso del portal.<br>" + text);
                        swbMail.setContentType("text/html");
                        swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                        swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));
                        SWBUtils.EMAIL.sendBGEmail(swbMail);
                    } catch (Exception e)
                    {
                        log.debug(e);
                    }
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                response.setAction("moderate");
            } else
            {
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
            }
        }
    }

    private void RejectQuestion(User user, HttpServletRequest request, SWBActionResponse response)
    {
        boolean isAdmin = false;
        Role role = user.getUserRepository().getRole("adminForum");
        UserGroup group = user.getUserRepository().getUserGroup("admin");
        if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
        {
            isAdmin = true;
        }
        if (isAdmin)
        {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            if (semObject != null)
            {
                Question question = (Question) semObject.createGenericInstance();
                question.setQueStatus(STATUS_REMOVED);
                if (question.getCreator() != null && question.getCreator().getEmail() != null)
                {
                    String text = clean(question.getQuestion());
                    SWBMail swbMail = new SWBMail();
                    String toemail = question.getCreator().getEmail();
                    ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
                    try
                    {
                        aAddress.add(new InternetAddress(toemail));
                        swbMail.setAddress(aAddress);
                        swbMail.setSubject("Mensaje rechazado en el foro " + response.getWebPage().getWebSite().getTitle());
                        swbMail.setData("Su mensaje fue rechazado por no cumplir con las politicas de uso del portal.<br>" + text);
                        swbMail.setContentType("text/html");
                        swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                        swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));
                        SWBUtils.EMAIL.sendBGEmail(swbMail);
                    } catch (Exception e)
                    {
                        log.debug(e);
                    }
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                response.setAction("moderate");
            } else
            {
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
            }
        }
    }

    private void AcceptAnswer(User user, HttpServletRequest request, SWBActionResponse response)
    {
        boolean isAdmin = false;
        Role role = user.getUserRepository().getRole("adminForum");
        UserGroup group = user.getUserRepository().getUserGroup("admin");
        if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
        {
            isAdmin = true;
        }
        if (isAdmin)
        {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            if (semObject != null)
            {
                Answer answer = (Answer) semObject.createGenericInstance();
                answer.setAnsStatus(STATUS_ACEPTED);
                response.setAction("moderate");
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
            } else
            {
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
            }
        }
    }

    private void AcceptQuestion(User user, HttpServletRequest request, SWBActionResponse response)
    {
        boolean isAdmin = false;
        Role role = user.getUserRepository().getRole("adminForum");
        UserGroup group = user.getUserRepository().getUserGroup("admin");
        if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
        {
            isAdmin = true;
        }
        if (isAdmin)
        {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            if (semObject != null)
            {
                Question question = (Question) semObject.createGenericInstance();
                question.setQueStatus(STATUS_ACEPTED);
                response.setAction("moderate");
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
            } else
            {
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
            }
        }
    }

    private void subcribe2category(HttpServletRequest request, WebSite website, User user)
    {
        try
        {
            //TODO: que se hace aqui
            /* esta no supe cuando se debe de llamar  -RGJS*/
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            if (semObject != null)
            {
                WebPage category = (WebPage) semObject.createGenericInstance();
                CategorySubscription catSubs = CategorySubscription.ClassMgr.createCategorySubscription(website);
                catSubs.setCategoryWebpage(category);
                if (user != null)
                {
                    catSubs.setCategoryUser(user);
                }
            }
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    private void subcribe2question(HttpServletRequest request, User user, WebSite website, SWBActionResponse response)
    {
        boolean isSuscribed = false;
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        if (semObject != null)
        {
            Question question = (Question) semObject.createGenericInstance();
            Iterator<QuestionSubscription> it_subs = QuestionSubscription.ClassMgr.listQuestionSubscriptionByQuestionObj(question);
            while (it_subs.hasNext() && !isSuscribed)
            {
                QuestionSubscription qs = it_subs.next();
                if (user != null && qs.getUserObj() != null && qs.getUserObj().getURI().equals(user.getURI()))
                {
                    isSuscribed = true;
                }
            }
            if (!isSuscribed)
            {
                try
                {
                    QuestionSubscription questionSubs = QuestionSubscription.ClassMgr.createQuestionSubscription(website);
                    questionSubs.setQuestionObj(question);
                    if (user != null)
                    {
                        questionSubs.setUserObj(user);
                    }
                    //TODO: Enviar correo
                } catch (Exception e)
                {
                    log.error(e);
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                    if (request.getParameter("uri") != null)
                    {
                        response.setRenderParameter("uri", request.getParameter("uri"));
                    }
                } else
                {
                    response.setAction("edit");
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
            }
        } else
        {
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
        }
    }

    private void removeAnswer(HttpServletRequest request, User user, SWBActionResponse response)
    {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        if (semObject != null)
        {
            Answer answer = (Answer) semObject.createGenericInstance();
            boolean isAdmin = false;
            Role role = user.getUserRepository().getRole("adminForum");
            UserGroup group = user.getUserRepository().getUserGroup("admin");
            if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
            {
                isAdmin = true;
            }
            if (isAdmin || (answer.getCreator() != null && answer.getCreator().getId() != null && user.getId() != null && answer.getCreator().getId().equals(user.getId())))
            {
                try
                {
                    if (request.getParameter("org") != null)
                    {
                        response.setAction(request.getParameter("org"));
                    } else
                    {
                        response.setAction("showDetail");
                    }
                    if (answer.getAnsQuestion().getURI() != null)
                    {
                        response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
                    }
                    answer.remove();
                } catch (Exception e)
                {
                    log.error(e);
                }
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
        } else
        {
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
        }
    }

    private void voteAnswer(HttpServletRequest request, User user, WebSite website, SWBActionResponse response)
    {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        if (semObject != null)
        {
            Answer answer = (Answer) semObject.createGenericInstance();
            boolean hasVoted = answer.userHasVoted(user);
            AnswerVote answerVote = null;
            if (!hasVoted)
            {
                answerVote = AnswerVote.ClassMgr.createAnswerVote(website);
                answerVote.setAnswerVote(answer);
                if (user != null)
                {
                    answerVote.setAnsUserVote(user);
                }
            }
            if (answerVote != null)
            {
                if (request.getParameter("commentVote") != null && request.getParameter("commentVote").trim().length() > 0)
                {
                    answerVote.setAnsCommentVote(request.getParameter("commentVote"));
                }
                if (request.getParameter("likeVote") != null)
                {
                    boolean likeVote = Boolean.parseBoolean(request.getParameter("likeVote"));
                    answerVote.setLikeAnswer(likeVote);
                    if (isUseScoreSystem() && user != null)
                    {
                        UserPoints points = getUserPointsObject(user, website);
                        if (points == null)
                        {
                            points = UserPoints.ClassMgr.createUserPoints(website);
                            points.setPointsForum(this);
                            points.setPointsUser(user);
                        }
                        points.setPoints(points.getPoints() + getPointsVoteAnswer());
                        points = getUserPointsObject(answer.getCreator(), website);
                        if (points == null)
                        {
                            points = UserPoints.ClassMgr.createUserPoints(website);
                            points.setPointsForum(this);
                            points.setPointsUser(answer.getCreator());
                        }
                        if (likeVote)
                        {
                            points.setPoints(points.getPoints() + getPointsLikeAnswer());
                        } else
                        {
                            int p = points.getPoints() - getPointsDontLikeAnswer();
                            if (p < 0)
                            {
                                p = 0;
                            }
                            points.setPoints(p);
                        }
                    }
                }
                if (request.getParameter("irrelevant") != null)
                {
                    boolean likeVote = Boolean.parseBoolean(request.getParameter("irrelevant"));
                    answerVote.setIrrelevantVote(likeVote);
                    int irrelevantCount = answer.getAnsIrrelevant() + 1;
                    answer.setAnsIrrelevant(irrelevantCount);
                    if (isUseScoreSystem() && user != null)
                    {
                        UserPoints points = getUserPointsObject(answer.getCreator(), website);
                        if (points == null)
                        {
                            points = UserPoints.ClassMgr.createUserPoints(website);
                            points.setPointsForum(this);
                            points.setPointsUser(answer.getCreator());
                        }
                        int p = points.getPoints() - getPointsIrrelevantAnswer();
                        if (p < 0)
                        {
                            p = 0;
                        }
                        points.setPoints(p);
                    }
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                } else
                {
                    response.setAction("showDetail");
                }
                if (answer.getAnsQuestion().getURI() != null)
                {
                    response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
                }
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
        } else
        {
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
        }
    }

    private void voteQuestion(HttpServletRequest request, User user, WebSite website, SWBActionResponse response)
    {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        if (semObject != null)
        {
            Question question = (Question) semObject.createGenericInstance();
            boolean hasVoted = false;
            if (user != null && question.userHasVoted(user))
            {
                hasVoted = true;
            }
            QuestionVote questionVote = null;
            if (!hasVoted)
            {
                questionVote = QuestionVote.ClassMgr.createQuestionVote(website);
                questionVote.setQuestionVote(question);
                if (user != null)
                {
                    questionVote.setUserVote(user);
                }
            }
            if (questionVote != null)
            {
                if (request.getParameter("commentVote") != null && request.getParameter("commentVote").trim().length() > 0)
                {
                    questionVote.setCommentVote(request.getParameter("commentVote"));
                }
                if (request.getParameter("likeVote") != null)
                {
                    boolean likeVote = Boolean.parseBoolean(request.getParameter("likeVote"));
                    questionVote.setLikeVote(likeVote);
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                    if (request.getParameter("uri") != null)
                    {
                        response.setRenderParameter("uri", request.getParameter("uri"));
                    }
                } else
                {
                    response.setAction("edit");
                }
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
        } else
        {
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
        }
    }

    private void openQuestion(HttpServletRequest request, User user, SWBActionResponse response)
    {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        if (semObject != null)
        {
            Question question = (Question) semObject.createGenericInstance();
            boolean isAdmin = false;
            Role role = user.getUserRepository().getRole("adminForum");
            UserGroup group = user.getUserRepository().getUserGroup("admin");
            if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
            {
                isAdmin = true;
            }
            if (isAdmin || (question.getCreator() != null && question.getCreator().getId() != null && user.getId() != null && question.getCreator().getId().equals(user.getId())))
            {
                try
                {
                    if (question.isClosed())
                    {
                        question.setClosed(false);
                    }
                } catch (Exception e)
                {
                    log.error(e);
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                } else
                {
                    response.setAction("showDetail");
                }
                if (question.getURI() != null)
                {
                    response.setRenderParameter("uri", question.getURI());
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
            }
        } else
        {
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
        }
    }

    private void closeQuestion(HttpServletRequest request, User user, SWBActionResponse response)
    {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        if (semObject != null)
        {
            Question question = (Question) semObject.createGenericInstance();
            boolean isAdmin = false;
            Role role = user.getUserRepository().getRole("adminForum");
            UserGroup group = user.getUserRepository().getUserGroup("admin");
            if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
            {
                isAdmin = true;
            }
            if (isAdmin || (question.getCreator() != null && question.getCreator().getId() != null && user.getId() != null && question.getCreator().getId().equals(user.getId())))
            {
                question.isClosed();
                try
                {
                    if (!question.isClosed())
                    {
                        question.setClosed(true);
                    }
                } catch (Exception e)
                {
                    log.error(e);
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                } else
                {
                    response.setAction("showDetail");
                }
                if (question.getURI() != null)
                {
                    response.setRenderParameter("uri", question.getURI());
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
            }
        } else
        {
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
        }
    }

    private void bestAnswer(HttpServletRequest request, SWBActionResponse response, User user, WebSite website)
    {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        if (semObject != null)
        {
            Answer answer = (Answer) semObject.createGenericInstance();
            try
            {
                if (!answer.isBestAnswer())
                {
                    answer.setBestAnswer(true);
                }
            } catch (Exception e)
            {
                log.error(e);
            }
            if (request.getParameter("org") != null)
            {
                response.setAction(request.getParameter("org"));
            } else
            {
                response.setAction("showDetail");
            }
            if (isUseScoreSystem() && user != null)
            {
                UserPoints points = getUserPointsObject(user, website);
                if (points == null)
                {
                    points = UserPoints.ClassMgr.createUserPoints(website);
                    points.setPointsForum(this);
                    points.setPointsUser(user);
                }
                points.setPoints(points.getPoints() + getPointsMarkBestAnswer());
                points = getUserPointsObject(answer.getCreator(), website);
                if (points == null)
                {
                    points = UserPoints.ClassMgr.createUserPoints(website);
                    points.setPointsForum(this);
                    points.setPointsUser(answer.getCreator());
                }
                points.setPoints(points.getPoints() + getPointsBestAnswer());
            }
            if (answer.getAnsQuestion().getURI() != null)
            {
                response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
        } else
        {
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
        }
    }

    private void markAnswerAsInnapropiate(HttpServletRequest request, WebSite website, SWBActionResponse response)
    {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        if (semObject != null)
        {
            Answer answer = (Answer) semObject.createGenericInstance();
            try
            {
                if (!answer.isAnsIsAppropiate())
                {
                    int innapropiateCount = answer.getAnsInappropriate() + 1;
                    answer.setAnsInappropriate(innapropiateCount);
                    if (innapropiateCount % getMaxInnapropiateCount() == 0 && innapropiateCount > 0)
                    {
                        //Enviar correo a administradores del foro
                        HashSet<User> users = new HashSet<User>();
                        Role role = website.getUserRepository().getRole("adminForum");
                        if (role != null)
                        {
                            Iterator<User> itusers = User.ClassMgr.listUserByRole(role);
                            while (itusers.hasNext())
                            {
                                User ruser = itusers.next();
                                users.add(ruser);
                            }
                        }
                        if (users.size() > 0)
                        {
                            SWBMail swbMail = new SWBMail();
                            ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
                            for (User ouser : users)
                            {
                                if (ouser.getEmail() != null)
                                {
                                    InternetAddress address1 = new InternetAddress();
                                    address1.setAddress(ouser.getEmail());
                                    aAddress.add(address1);
                                }
                            }
                            String port = "";
                            if (request.getServerPort() != 80)
                            {
                                port = ":" + request.getServerPort();
                            }
                            SWBResourceURLImp imp = new SWBResourceURLImp(request, response.getResourceBase(), response.getWebPage(), SWBResourceURL.UrlType_RENDER);
                            imp.setAction("showDetail");
                            imp.setParameter("uri", answer.getAnsQuestion().getURI());
                            URL urilocal = new URL(request.getScheme() + "://" + request.getServerName() + port + imp.toString());
                            swbMail.setAddress(aAddress);
                            swbMail.setContentType("text/html");
                            swbMail.setData("Una respuesta ya sobrepaso el umbral de lo considerado como inapropiado en la dirección web " + urilocal + " con el texto: " + clean(answer.getAnswer()));
                            swbMail.setSubject("Respuesta inapropiada " + response.getWebPage().getTitle());
                            swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                            swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));
                            SWBUtils.EMAIL.sendBGEmail(swbMail);
                        }
                    }
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                    if (request.getParameter("uri") != null)
                    {
                        response.setRenderParameter("uri", request.getParameter("uri"));
                    }
                } else
                {
                    response.setAction("edit");
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
            } catch (Exception e)
            {
                log.error(e);
            }
        } else
        {
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
        }
    }

    private void markQuestionAsInnapropiate(HttpServletRequest request, WebSite website, SWBActionResponse response)
    {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        if (semObject != null)
        {
            Question question = (Question) semObject.createGenericInstance();
            try
            {
                if (!question.isQueIsApropiate())
                {
                    int innapropiateCount = question.getQueInappropriate() + 1;
                    question.setQueInappropriate(innapropiateCount);
                    if (innapropiateCount % getMaxInnapropiateCount() == 0 && innapropiateCount > 0)
                    {
                        //Enviar correo a administradores del foro
                        HashSet<User> users = new HashSet<User>();
                        Role role = website.getUserRepository().getRole("adminForum");
                        if (role != null)
                        {
                            Iterator<User> itusers = User.ClassMgr.listUserByRole(role);
                            while (itusers.hasNext())
                            {
                                User ruser = itusers.next();
                                users.add(ruser);
                            }
                        }
                        if (users.size() > 0)
                        {
                            SWBMail swbMail = new SWBMail();
                            ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
                            for (User ouser : users)
                            {
                                if (ouser.getEmail() != null)
                                {
                                    InternetAddress address1 = new InternetAddress();
                                    address1.setAddress(ouser.getEmail());
                                    aAddress.add(address1);
                                }
                            }
                            String port = "";
                            if (request.getServerPort() != 80)
                            {
                                port = ":" + request.getServerPort();
                            }
                            SWBResourceURLImp imp = new SWBResourceURLImp(request, response.getResourceBase(), response.getWebPage(), SWBResourceURL.UrlType_RENDER);
                            imp.setAction("showDetail");
                            imp.setParameter("uri", question.getURI());
                            URL urilocal = new URL(request.getScheme() + "://" + request.getServerName() + port + imp.toString());
                            swbMail.setAddress(aAddress);
                            swbMail.setContentType("text/html");
                            swbMail.setData("Un mensaje ya sobrepaso el umbral de lo considerado como inapropiado en la direccion web " + urilocal + " con el texto: " + clean(question.getQuestion()));
                            swbMail.setSubject("Mensaje inapropiado " + response.getWebPage().getTitle());
                            swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                            swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));
                            SWBUtils.EMAIL.sendBGEmail(swbMail);
                        }
                    }
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                    if (request.getParameter("uri") != null)
                    {
                        response.setRenderParameter("uri", request.getParameter("uri"));
                    }
                } else
                {
                    response.setAction("edit");
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
            } catch (Exception e)
            {
                log.error(e);
            }
        } else
        {
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
        }
    }

    private void editAnswer(HttpServletRequest request, SWBActionResponse response, User user)
    {
        String sAttr = "cs";
        if (request.getParameter("sAttr") != null)
        {
            sAttr = request.getParameter("sAttr");
        }
        String cadena = (String) request.getSession().getAttribute(sAttr);
        boolean pass = false;
        if (cadena != null)
        {
            String _cadena = request.getParameter("code");
            if (cadena.equalsIgnoreCase(_cadena))
            {
                pass = true;
                request.getSession().removeAttribute(sAttr);
            }
        } else
        {
            pass = false;
        }
        if (pass || !isCaptcha())
        {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            if (semObject != null)
            {
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                try
                {
                    mgr.processForm(request);
                } catch (FormValidateException e)
                {
                    log.error(e);
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                } else
                {
                    response.setAction("showDetail");
                }
                Answer answer = (Answer) semObject.createGenericInstance();
                boolean canedit = false;
                if (answer.getCreator() != null && answer.getCreator().getId() != null && user.getId() != null && user.getId() != null && answer.getCreator().getId().equals(user.getId()))
                {
                    canedit = true;
                }
                if (canedit)
                {
                    if (this.isIsModerate())
                    {
                        answer.setAnsStatus(STATUS_REGISTERED);
                    }
                    String validAnswer = placeAnchors(answer.getAnswer());
                    validAnswer = validAnswer.replaceAll("\r\n", "<br>");
                    answer.setAnswer(validAnswer);
                    //answer.setAnswer(placeAnchors(answer.getAnswer()));
                    if (answer.getAnsQuestion().getURI() != null)
                    {
                        response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
                    }
                    if (request.getParameter("page") != null)
                    {
                        response.setRenderParameter("page", request.getParameter("page"));
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                }
            } else
            {
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
            }
        } else
        {
            // no pass
            response.setAction("editAnswer");
            if (request.getParameter("uri") != null)
            {
                response.setRenderParameter("uri", request.getParameter("uri"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (isCaptcha())
            {
                response.setRenderParameter("cptcha", "true");
            }
        }
    }

    private void answerQuestion(HttpServletRequest request, User user, SWBActionResponse response, WebSite website) throws MalformedURLException
    {
        String sAttr = "cs";
        if (request.getParameter("sAttr") != null)
        {
            sAttr = request.getParameter("sAttr");
        }
        String cadena = (String) request.getSession().getAttribute(sAttr);
        boolean pass = false;
        if (cadena != null)
        {
            String _cadena = request.getParameter("code");
            if (cadena.equalsIgnoreCase(_cadena))
            {
                pass = true;
                request.getSession().removeAttribute(sAttr);
            }
        } else
        {
            pass = false;
        }
        if (pass || !isCaptcha())
        {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            if (semObject != null)
            {
                Question question = (Question) semObject.createGenericInstance();
                SWBFormMgr mgr = new SWBFormMgr(Answer.forumCat_Answer, semObject, SWBFormMgr.MODE_EDIT);
                try
                {
                    SemanticObject semObjectChild = mgr.processForm(request);
                    Answer answer = (Answer) semObjectChild.createGenericInstance();
                    answer.setAnsQuestion(question);
                    String validAnswer = placeAnchors(answer.getAnswer());
                    validAnswer = validAnswer.replaceAll("\r\n", "<br>");
                    answer.setAnswer(validAnswer);
                    //answer.setAnswer(placeAnchors(answer.getAnswer()));
                    answer.setReferences(request.getParameter("references"));
                    answer.setCreated(Calendar.getInstance().getTime());
                    notificaRespuestaMensaje(user, request, answer.getAnswer(), answer.getAnsQuestion().getQuestion());
                    if (isIsModerate())
                    {
                        answer.setAnsStatus(STATUS_REGISTERED);
                    } else
                    {
                        answer.setAnsStatus(STATUS_ACEPTED);
                    }
                } catch (FormValidateException e)
                {
                    log.error(e);
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                } else
                {
                    response.setAction("showDetail");
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (isUseScoreSystem() && user != null)
                {
                    UserPoints points = getUserPointsObject(user, website);
                    if (points == null)
                    {
                        points = UserPoints.ClassMgr.createUserPoints(website);
                        points.setPointsForum(this);
                        points.setPointsUser(user);
                    }
                    points.setPoints(points.getPoints() + getPointsAnswer());
                }
                if (request.getParameter("uri") != null)
                {
                    response.setRenderParameter("uri", request.getParameter("uri"));
                }
            } else
            {
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
            }
        } else
        {
            // no pass
            response.setAction("answerQuestion");
            if (request.getParameter("uri") != null)
            {
                response.setRenderParameter("uri", request.getParameter("uri"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (isCaptcha())
            {
                response.setRenderParameter("cptcha", "true");
            }
        }
    }

    private void removeQuestion(HttpServletRequest request, User user, SWBActionResponse response)
    {
        try
        {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            if (semObject != null)
            {
                Question question = (Question) semObject.createGenericInstance();
                boolean isAdmin = false;
                Role role = user.getUserRepository().getRole("adminForum");
                UserGroup group = user.getUserRepository().getUserGroup("admin");
                if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
                {
                    isAdmin = true;
                }
                if (isAdmin || (question.getCreator() != null && question.getCreator().getId() != null && user.getId() != null && question.getCreator().getId().equals(user.getId())))
                {
                    question.remove();
                    if (request.getParameter("org") != null)
                    {
                        response.setAction(request.getParameter("org"));
                    } else
                    {
                        response.setAction("edit");
                    }
                    if (request.getParameter("page") != null)
                    {
                        response.setRenderParameter("page", request.getParameter("page"));
                    }
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                }
            } else
            {
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
            }
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    private void editQuestion(HttpServletRequest request, User user, SWBActionResponse response)
    {
        String sAttr = "cs";
        if (request.getParameter("sAttr") != null)
        {
            sAttr = request.getParameter("sAttr");
        }
        String cadena = (String) request.getSession().getAttribute(sAttr);
        boolean pass = false;
        if (cadena != null)
        {
            String _cadena = request.getParameter("code");
            if (cadena.equalsIgnoreCase(_cadena))
            {
                pass = true;
                request.getSession().removeAttribute(sAttr);
            }
        } else
        {
            pass = false;
        }
        if (pass || !isCaptcha())
        {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            if (semObject != null)
            {
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                try
                {
                    mgr.processForm(request);
                    Question question = (Question) semObject.createGenericInstance();
                    String validQuestion = placeAnchors(question.getQuestion());
                    validQuestion = validQuestion.replaceAll("\r\n", "<br>");
                    question.setQuestion(validQuestion);
                    if (request.getParameter("title") != null)
                    {
                        String title = request.getParameter("title").toString().trim();
                        if (!title.isEmpty())
                        {
                            question.setTitle(title);
                        }
                    }
                    if (request.getParameter("categoryuri") != null && !request.getParameter("categoryuri").equals(""))
                    {
                        boolean canedit = false;
                        if (question.getCreator() != null && question.getCreator().getId() != null && user.getId() != null && user.getId() != null && question.getCreator().getId().equals(user.getId()))
                        {
                            canedit = true;
                        }
                        if (canedit)
                        {
                            SemanticObject semObjectChild = SemanticObject.createSemanticObject(request.getParameter("categoryuri"));
                            if (semObjectChild != null)
                            {
                                WebPage webPage = (WebPage) semObjectChild.createGenericInstance();
                                question.setWebpage(webPage);
                                if (this.isIsModerate())
                                {
                                    question.setQueStatus(STATUS_REGISTERED);
                                }
                                question.setQuestion(placeAnchors(question.getQuestion()));
                                if (request.getParameter("tags") != null)
                                {
                                    question.setTags(request.getParameter("tags"));
                                }
                            }
                        }
                    }
                } catch (FormValidateException e)
                {
                    log.error(e);
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                } else
                {
                    response.setAction("edit");
                }
                if (request.getParameter("uri") != null)
                {
                    response.setRenderParameter("uri", request.getParameter("uri"));
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
            } else
            {
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
            }
        } else
        {
            // no pass
            response.setAction("editQuestion");
            if (request.getParameter("uri") != null)
            {
                response.setRenderParameter("uri", request.getParameter("uri"));
            }
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
            if (isCaptcha())
            {
                response.setRenderParameter("cptcha", "true");
            }
        }
    }

    private void addQuestion(HttpServletRequest request, User user, WebSite website, SWBActionResponse response) throws MalformedURLException
    {
        String sAttr = "cs";
        if (request.getParameter("sAttr") != null)
        {
            sAttr = request.getParameter("sAttr");
        }
        String cadena = (String) request.getSession().getAttribute(sAttr);
        boolean pass = false;
        if (cadena != null)
        {
            String _cadena = request.getParameter("code");
            if (cadena.equalsIgnoreCase(_cadena))
            {
                pass = true;
                request.getSession().removeAttribute(sAttr);
            }
        } else
        {
            pass = false;
        }
        if (pass || !isCaptcha())
        {
            SWBFormMgr mgr = new SWBFormMgr(Question.forumCat_Question, getResourceBase().getSemanticObject(), SWBFormMgr.MODE_EDIT);
            try
            {
                SemanticObject semObject = mgr.processForm(request);
                Question question = (Question) semObject.createGenericInstance();
                if (user != null && user.isSigned())
                {
                    question.setCreator(user);
                }
                if (request.getParameter("title") != null)
                {
                    String title = request.getParameter("title").toString().trim();
                    if (!title.isEmpty())
                    {
                        question.setTitle(title);
                    }
                }
                question.setCreated(Calendar.getInstance().getTime());
                question.setForumResource(this);
                String validQuestion = placeAnchors(question.getQuestion());
                validQuestion = validQuestion.replaceAll("\r\n", "<br>");
                question.setQuestion(validQuestion);
                // question.setQuestion(placeAnchors(question.getQuestion()));
                question.setQuestionReferences(request.getParameter("questionReferences"));
                if (request.getParameter("tags") != null)
                {
                    question.setTags(request.getParameter("tags"));
                }
                if (request.getParameter("categoryuri") != null && !request.getParameter("categoryuri").trim().equals(""))
                {
                    SemanticObject semObjectChild = SemanticObject.createSemanticObject(request.getParameter("categoryuri"));
                    if (semObjectChild != null)
                    {
                        WebPage webPage = (WebPage) semObjectChild.createGenericInstance();
                        question.setWebpage(webPage);
                    }
                }
                notificaMensaje(user, request, question.getQuestion());
                if (isIsModerate())
                {
                    question.setQueStatus(STATUS_REGISTERED);
                } else
                {
                    question.setQueStatus(STATUS_ACEPTED);
                }
            } catch (FormValidateException e)
            {
                log.error(e);
            }
            if (isUseScoreSystem() && user != null)
            {
                UserPoints points = getUserPointsObject(user, website);
                if (points == null)
                {
                    points = UserPoints.ClassMgr.createUserPoints(website);
                    points.setPointsForum(this);
                    points.setPointsUser(user);
                }
                points.setPoints(points.getPoints() + getPointsPublishQuestion());
            }
        } else
        {
            // no pass
            response.setAction("add");
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
            if (isCaptcha())
            {
                response.setRenderParameter("cptcha", "true");
            }
        }
    }

    public String placeAnchors(String text)
    {
        if (text == null || text.trim().equals(""))
        {
            return "";
        }
        String ret = text;
        String regex = "https?://[\\+\\w+\\d+%#@:=/\\.\\?&_~-]+";
        Matcher m = Pattern.compile(regex, 0).matcher(text);

        while (m.find())
        {
            String urlt = m.group();
            if (urlt.endsWith(".") || urlt.endsWith(",") || urlt.endsWith(";") || urlt.endsWith(":"))
            {
                urlt = urlt.substring(0, urlt.length() - 1);
            }
            ret = ret.replaceAll(Pattern.quote(urlt), "<a href=\"" + urlt + "\">" + urlt + "</a>");
        }
        return ret;
    }

    private UserPoints getUserPointsObject(User user, WebSite model)
    {
        UserPoints ret = null;
        boolean found = false;

        if (user != null)
        {
            Iterator<UserPoints> itpoints = UserPoints.ClassMgr.listUserPointsByPointsUser(user, model);
            while (itpoints.hasNext() && !found)
            {
                UserPoints points = itpoints.next();
                if (points.getPointsForum().getURI().equals(getURI()))
                {
                    ret = points;
                    found = true;
                }
            }
        }
        return ret;
    }

    public void notificaMensaje(User user, HttpServletRequest request, String titulo) throws MalformedURLException
    {
        Role role = user.getUserRepository().getRole("adminForum");

        if (role != null)
        {
            ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
            Iterator<User> users = user.getUserRepository().listUsers();
            while (users.hasNext())
            {
                User userSite = users.next();
                if (userSite.getEmail() != null && userSite.hasRole(role))
                {
                    InternetAddress address1 = new InternetAddress();
                    address1.setAddress(userSite.getEmail());
                    aAddress.add(address1);

                }
            }
            SWBMail swbMail = new SWBMail();
            swbMail.setAddress(aAddress);
            swbMail.setContentType("text/html");
            String port = "";
            if (request.getServerPort() != 80)
            {
                port = ":" + request.getServerPort();
            }

            URL urilocal = new URL(request.getScheme() + "://" + request.getServerName() + port);
            swbMail.setData("Se creó el mensaje: " + titulo + " en el sitio: " + urilocal.toString());
            swbMail.setSubject("Mensaje de foro creado");
            swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
            swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));

            aAddress = new ArrayList<InternetAddress>();
            if (user.getEmail() != null)
            {
                InternetAddress address1 = new InternetAddress();
                address1.setAddress(user.getEmail());
                aAddress.add(address1);
            }
            swbMail = new SWBMail();
            swbMail.setAddress(aAddress);
            swbMail.setContentType("text/html");
            port = "";
            if (request.getServerPort() != 80)
            {
                port = ":" + request.getServerPort();
            }

            urilocal = new URL(request.getScheme() + "://" + request.getServerName() + port);
            swbMail.setData("Se creó el tema con el nombre " + titulo + " en el sitio: " + urilocal.toString());
            swbMail.setSubject("Mensaje de foro creado");
            swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
            swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));
        }
    }

    public void notificaRespuestaMensaje(User user, HttpServletRequest request, String respuesta, String mensaje) throws MalformedURLException
    {
        Role role = user.getUserRepository().getRole("adminForum");

        if (role != null)
        {
            ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
            Iterator<User> users = user.getUserRepository().listUsers();
            while (users.hasNext())
            {
                User userSite = users.next();
                if (userSite.getEmail() != null && userSite.hasRole(role))
                {
                    InternetAddress address1 = new InternetAddress();
                    address1.setAddress(userSite.getEmail());
                    aAddress.add(address1);

                }
            }
            SWBMail swbMail = new SWBMail();
            swbMail.setAddress(aAddress);
            swbMail.setContentType("text/html");
            String port = "";
            if (request.getServerPort() != 80)
            {
                port = ":" + request.getServerPort();
            }

            URL urilocal = new URL(request.getScheme() + "://" + request.getServerName() + port);
            swbMail.setData("Se creó una respuesta para un mensaje: " + respuesta + " mensaje: " + mensaje + " en el sitio: " + urilocal.toString());
            swbMail.setSubject("Respuesta a mensaje de foro creado");
            swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
            swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));

            try
            {
                SWBUtils.EMAIL.sendBGEmail(swbMail);
            } catch (Exception e)
            {
                log.error(e);
            }
            aAddress = new ArrayList<InternetAddress>();
            if (user.getEmail() != null)
            {
                InternetAddress address1 = new InternetAddress();
                address1.setAddress(user.getEmail());
                aAddress.add(address1);
            }
            swbMail = new SWBMail();
            swbMail.setAddress(aAddress);
            swbMail.setContentType("text/html");
            port = "";
            if (request.getServerPort() != 80)
            {
                port = ":" + request.getServerPort();
            }

            urilocal = new URL(request.getScheme() + "://" + request.getServerName() + port);
            swbMail.setData("Se creó el tema con el nombre " + mensaje + " en el sitio: " + urilocal.toString());
            swbMail.setSubject("Mensaje de foro creado");
            swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
            swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));
            try
            {
                SWBUtils.EMAIL.sendBGEmail(swbMail);
            } catch (Exception e)
            {
                log.error(e);
            }
        }
    }
}
