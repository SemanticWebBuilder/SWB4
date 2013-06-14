/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.Stream;
import org.semanticwb.social.VideoIn;

/**
 *
 * @author jorge.jimenez
 */
public class StreamCloud extends StreamInBox {

    private Logger log = SWBUtils.getLogger(StreamCloud.class);
    
   
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        if (request.getParameter("doView") == null) {
            doEdit(request, response, paramRequest);
        } else {
            System.out.println("Entra StreamCloud-1");
            RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/reports/streamJavaCloud.jsp");
            try {
                System.out.println("Entra StreamCloud-2-word:"+request.getParameter("word")+",sID:"+request.getParameter("sID")+",wsite:"+request.getParameter("wsite"));
                request.setAttribute("paramRequest", paramRequest);
                rd.include(request, response);
                
                //Si viene una palabra seleccionada desde la nube de tags.....
                
                if (request.getParameter("word") != null && request.getParameter("sID")!=null && request.getParameter("wsite")!=null) {
                    String lang = paramRequest.getUser().getLanguage();
                   
                    /*
                    String id = request.getParameter("suri");
                    if (id == null) {
                        return;
                    }*/
                    WebSite wsite=WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));

                    
                    //Stream stream = (Stream) SemanticObject.getSemanticObject(id).getGenericInstance();
                    Stream stream = Stream.ClassMgr.getStream(request.getParameter("sID"), wsite);
                    String id=stream.getURI();

                    PrintWriter out = response.getWriter();

                    if (request.getParameter("dialog") != null && request.getParameter("dialog").equals("close")) {
                        out.println("<script type=\"javascript\">");
                        out.println(" hideDialog(); ");
                        if (request.getParameter("statusMsg") != null) {
                            out.println("   showStatus('" + request.getParameter("statusMsg") + "');");
                        }
                        if (request.getParameter("reloadTap") != null) {
                            out.println(" reloadTab('" + id + "'); ");
                        }
                        out.println("</script>");
                    }


                    SWBResourceURL urls = paramRequest.getRenderUrl();
                    urls.setParameter("act", "");
                    urls.setParameter("suri", id);


                    out.println("<div class=\"swbform\">");


                    out.println("<fieldset>");
                    out.println("<table width=\"98%\" >");
                    out.println("<thead>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("action"));
                    out.println("</th>");


                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("post"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("postType"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("network"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("topic"));
                    out.println("</th>");


                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("created"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("sentiment"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("intensity"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("emoticon"));
                    out.println("</th>");


                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("replies"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("user"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("followers"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("friends"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("klout"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("place"));
                    out.println("</th>");

                    out.println("<th>");
                    out.println(paramRequest.getLocaleString("prioritary"));
                    out.println("</th>");


                    out.println("</thead>");
                    out.println("<tbody>");


                    String word2Find=request.getParameter("word");
                    ArrayList<PostIn> postInsResult=new ArrayList(); 
                    Iterator<PostIn> itposts=stream.listPostInStreamInvs();
                    while(itposts.hasNext())
                    {
                        PostIn postIn=itposts.next();
                        if(postIn.getTags()!=null && postIn.getTags().contains(word2Find))
                        {
                            postInsResult.add(postIn);
                        }else if(postIn.getMsg_Text()!=null && postIn.getMsg_Text().contains(word2Find)){
                            postInsResult.add(postIn);
                        }
                    }
                    
                    Set<PostIn> setso = SWBComparator.sortByCreatedSet(postInsResult.iterator(), false);

                    itposts = null;
                    int ps = 20;
                    int l = setso.size();

                    //System.out.println("num cont: "+l);

                    int p = 0;
                    String page = request.getParameter("page");
                    if (page != null) {
                        p = Integer.parseInt(page);
                    }



                    int x = 0;
                    itposts = setso.iterator();
                    while (itposts.hasNext()) {
                        PostIn postIn = itposts.next();

                        if (x < p * ps) {
                            x++;
                            continue;
                        }
                        if (x == (p * ps + ps) || x == l) {
                            break;
                        }
                        x++;


                        out.println("<tr>");

                        //Show Actions
                        out.println("<td>");

                        //Remove
                        SWBResourceURL urlr = paramRequest.getActionUrl();
                        urlr.setParameter("suri", id);
                        urlr.setParameter("sval", postIn.getURI());
                        urlr.setParameter("page", "" + p);
                        urlr.setAction("remove");

                        out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("remove") + "\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " "
                                + SWBUtils.TEXT.scape4Script(postIn.getMsg_Text()) + "?'))" + "{ submitUrl('" + urlr + "',this); } else { return false;}\">"
                                + "<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("remove") + "\"></a>");

                        //Preview
                        SWBResourceURL urlpre = paramRequest.getRenderUrl();
                        urlpre.setParameter("suri", id);
                        urlpre.setParameter("page", "" + p);
                        urlpre.setParameter("sval", postIn.getURI());
                        urlpre.setParameter("preview", "true");
                        out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("previewdocument") + "\" onclick=\"submitUrl('" + urlpre + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/preview.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("previewdocument") + "\"></a>");


                        //ReClasifyByTpic
                        SWBResourceURL urlreClasifybyTopic = paramRequest.getRenderUrl().setMode(Mode_RECLASSBYTOPIC).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                        out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("reclasifyByTopic") + "\" onclick=\"showDialog('" + urlreClasifybyTopic + "','"
                                + paramRequest.getLocaleString("reclasifyByTopic") + "'); return false;\">RT</a>");


                        //ReClasyfyBySentiment & Intensity
                        SWBResourceURL urlrev = paramRequest.getRenderUrl().setMode(Mode_RECLASSBYSENTIMENT).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                        out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("reeval") + "\" onclick=\"showDialog('" + urlrev + "','" + paramRequest.getLocaleString("reeval")
                                + "'); return false;\">RV</a>");

                        //Respond
                        if (postIn.getSocialTopic() != null) {
                            SWBResourceURL urlresponse = paramRequest.getRenderUrl().setMode(Mode_RESPONSE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                            out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("respond") + "\" onclick=\"showDialog('" + urlresponse + "','" + paramRequest.getLocaleString("respond")
                                    + "'); return false;\">R</a>");
                        }

                        out.println("</td>");

                        //Show Message
                        out.println("<td>");
                        out.println(postIn.getMsg_Text());
                        out.println("</td>");


                        //Show PostType
                        out.println("<td>");
                        out.println(postIn instanceof MessageIn ? paramRequest.getLocaleString("message") : postIn instanceof PhotoIn ? paramRequest.getLocaleString("photo") : postIn instanceof VideoIn ? paramRequest.getLocaleString("video") : "---");
                        out.println("</td>");

                        //SocialNetwork
                        out.println("<td>");
                        out.println(postIn.getPostInSocialNetwork().getDisplayTitle(lang));
                        out.println("</td>");


                        //SocialTopic
                        out.println("<td>");
                        if (postIn.getSocialTopic() != null) {
                            out.println(postIn.getSocialTopic().getDisplayTitle(lang));
                        } else {
                            out.println("---");
                        }
                        out.println("</td>");

                        //Show Creation Time
                        out.println("<td>");
                        out.println(SWBUtils.TEXT.getTimeAgo(postIn.getCreated(), lang));
                        out.println("</td>");

                        //Sentiment
                        out.println("<td align=\"center\">");
                        if (postIn.getPostSentimentalType() == 0) {
                            out.println("---");
                        } else if (postIn.getPostSentimentalType() == 1) {
                            out.println("<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/images/feelpos.png" + "\">");
                        } else if (postIn.getPostSentimentalType() == 2) {
                            out.println("<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/images/feelneg.png" + "\">");
                        } else {
                            out.println("XXX");
                        }
                        out.println("</td>");

                        //Intensity
                        out.println("<td>");
                        out.println(postIn.getPostIntesityType() == 0 ? paramRequest.getLocaleString("low") : postIn.getPostSentimentalType() == 1 ? paramRequest.getLocaleString("medium") : postIn.getPostSentimentalType() == 2 ? paramRequest.getLocaleString("high") : "---");
                        out.println("</td>");

                        //Emoticon
                        out.println("<td align=\"center\">");
                        if (postIn.getPostSentimentalEmoticonType() == 1) {
                            out.println("<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/images/emopos.png" + "/>");
                        } else if (postIn.getPostSentimentalEmoticonType() == 2) {
                            out.println("<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/images/emoneg.png" + "/>");
                        } else if (postIn.getPostSentimentalEmoticonType() == 0) {
                            out.println("---");
                        } else {
                            out.println("XXX");
                        }
                        out.println("</td>");


                        //Replicas
                        out.println("<td align=\"center\">");
                        out.println(postIn.getPostRetweets());
                        out.println("</td>");


                        //Nunca debería un PostIn no tener un usuario, porque obvio las redes sociales simpre tienen un usuario que escribe los mensajes
                        //User
                        out.println("<td>");
                        out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_name() : paramRequest.getLocaleString("withoutUser"));   //Nunca debería un PostIn no tener un usuario 
                        out.println("</td>");

                        //Followers
                        out.println("<td align=\"center\">");
                        out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFollowers() : paramRequest.getLocaleString("withoutUser"));
                        out.println("</td>");

                        //Friends
                        out.println("<td align=\"center\">");
                        out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFriends() : paramRequest.getLocaleString("withoutUser"));
                        out.println("</td>");

                        //Klout
                        out.println("<td align=\"center\">");
                        out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getSnu_klout() : paramRequest.getLocaleString("withoutUser"));
                        out.println("</td>");

                        //Place
                        out.println("<td>");
                        out.println(postIn.getPostPlace() == null ? "---" : postIn.getPostPlace());
                        out.println("</td>");

                        out.println("<td align=\"center\">");
                        out.println(postIn.isIsPrioritary() ? "SI" : "NO");
                        out.println("</td>");


                        out.println("</tr>");
                    }
                    out.println("</tbody>");
                    out.println("</table>");
                    out.println("</fieldset>");


                    if (p > 0 || x < l) //Requiere paginacion
                    {
                        out.println("<fieldset>");
                        out.println("<center>");

                        //int pages=(int)(l+ps/2)/ps;

                        int pages = (int) (l / ps);
                        if ((l % ps) > 0) {
                            pages++;
                        }

                        for (int z = 0; z < pages; z++) {
                            SWBResourceURL urlNew = paramRequest.getRenderUrl();
                            urlNew.setParameter("suri", id);
                            urlNew.setParameter("page", "" + z);
                            if (z != p) {
                                out.println("<a href=\"#\" onclick=\"submitUrl('" + urlNew + "',this); return false;\">" + (z + 1) + "</a> ");
                            } else {
                                out.println((z + 1) + " ");
                            }
                        }
                        out.println("</center>");
                        out.println("</fieldset>");
                    }

                    out.println("</div>");


                    if (request.getParameter("preview") != null && request.getParameter("preview").equals("true")) {
                        if (request.getParameter("sval") != null) {
                            try {
                                doPreview(request, response, paramRequest);
                            } catch (Exception e) {
                                out.println("Preview not available...");
                            }
                        } else {
                            out.println("Preview not available...");
                        }
                    }
                }
            } catch (ServletException ex) {
                log.error("Error al enviar los datos a streamCloud.jsp " + ex.getMessage());
            }
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.getWriter().println("<iframe dojotype=\"dijit.layout.ContentPane\" src=\"" + paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW).setParameter("doView", "doView").setParameter("suri", request.getParameter("suri")).setParameter("word", request.getParameter("word")) + "\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"yes\" >");
        response.getWriter().println("</iframe>");
    }
}
