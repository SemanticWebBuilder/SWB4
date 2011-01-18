/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.resources.sem.forumcat;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.indexer.IndexTerm;
import org.semanticwb.portal.indexer.parser.GenericParser;

/**
 * Parser para respuestas del foro.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class AnswerParser extends GenericParser {
    public static String ATT_CREATOR = "creator";
    public static String ATT_FORUM = "forum";
    private static Logger log = SWBUtils.getLogger(QuestionParser.class);

    @Override
    public Map<String, IndexTerm> getIndexTerms(Searchable gen) {
        Map map = super.getIndexTerms(gen);
        map.put(ATT_CREATOR, new IndexTerm(ATT_CREATOR, getIndexCreator(gen), false, IndexTerm.INDEXED_NO_ANALYZED));
        map.put(ATT_FORUM, new IndexTerm(ATT_FORUM, getIndexForum(gen), false, IndexTerm.INDEXED_NO_ANALYZED));
        return map;
    }

    public String getIndexForum(Searchable gen) {
        return ((Answer)gen).getAnsQuestion().getForumResource().getId();
    }

    @Override
    public String getIndexTitle(Searchable gen) {
        return ((Answer)gen).getAnswer();
    }

    @Override
    public String getTitle(Searchable gen, String lang) {
        return ((Answer)gen).getAnswer();
    }

    public String getIndexCreator(Searchable gen) {
        return ((Answer)gen).getAnsQuestion().getCreator().getURI();
    }

    @Override
    public String getType(Searchable gen) {
        return "Answer";
    }

    @Override
    public String getUpdated(Searchable gen) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(((Answer)gen).getUpdated());
    }

    @Override
    public String getSummary(Searchable gen, String lang) {
        String ret = null;
        Answer answer = (Answer) gen;
        if (answer != null) {
            try {
                ret = SWBUtils.TEXT.parseHTML(answer.getAnswer());
            } catch (Exception e) {
                log.error(e);
            }
        }
        return ret;
    }

    @Override
    public String getUrl(Searchable gen) {
        //TODO:Revisar si es la URL correcta
        Answer a = (Answer)gen;
        String ret = null;
        WebPage page = getWebPage((Resource) a.getAnsQuestion().getForumResource().getResourceBase());
        if (page != null) {
            ret = super.getUrl(page);
        }
        return ret + "?searchAct=showDetail&uri=" + a.getAnsQuestion().getEncodedURI();
    }

    private WebPage getWebPage(Resource res) {
        WebPage ret = null;
        if (res.getResourceType() != null && res.getResourceType().getResourceMode() == ResourceType.MODE_CONTENT) {
            Iterator<Resourceable> it = res.listResourceables();
            while (it.hasNext()) {
                Resourceable ob = it.next();
                if (ob instanceof WebPage) {
                    ret = (WebPage) ob;
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public String getIndexCategory(Searchable gen) {
        String ret = "";
        Answer a = (Answer) gen;
        WebPage page = getWebPage(a.getAnsQuestion().getForumResource().getResource());
        if (page != null) {
            ret = super.getIndexCategory(page);
        }
        return ret;
    }

    @Override
    public String getTypeDisplayLabel(Searchable gen) {
        return "Respuesta";
    }

    @Override
    public boolean canUserView(Searchable gen, User user) {
        Answer a = (Answer) gen;
        boolean ret = super.canUserView(gen, user);
        return ret && a.getAnsStatus() == SWBForumCatResource.STATUS_ACEPTED;
    }
}