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
import org.semanticwb.model.Tagable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.indexer.IndexTerm;
import org.semanticwb.portal.indexer.parser.GenericParser;

/**
 * Parser para preguntas del foro.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class QuestionParser extends GenericParser {
    public static String ATT_CREATOR = "creator";
    public static String ATT_FORUM = "forum";
    public static String ATT_CAT = "forumcat";
    public static String ATT_TAGS = "tags";
    private static Logger log = SWBUtils.getLogger(QuestionParser.class);

    @Override
    public Map<String, IndexTerm> getIndexTerms(Searchable gen) {
        Map map = super.getIndexTerms(gen);
        map.put(ATT_CREATOR, new IndexTerm(ATT_CREATOR, getIndexCreator(gen), false, IndexTerm.INDEXED_NO_ANALYZED));
        map.put(ATT_FORUM, new IndexTerm(ATT_FORUM, getIndexForum(gen), false, IndexTerm.INDEXED_NO_ANALYZED));
        map.put(ATT_CAT, new IndexTerm(ATT_CAT, getIndexForumCat(gen), false, IndexTerm.INDEXED_ANALYZED));
        map.put(ATT_TAGS, new IndexTerm(ATT_TAGS, getIndexTags(gen), true, IndexTerm.INDEXED_ANALYZED));
        return map;
    }

    public String getIndexForumCat(Searchable gen) {
        return ((Question)gen).getWebpage().getTitle();
    }

    public String getIndexForum(Searchable gen) {
        return ((Question)gen).getForumResource().getId();
    }

    @Override
    public String getIndexTags(Searchable gen) {
        String ret = "";
        if (gen instanceof Tagable) {
            ret = ((Question)gen).getTags();
        }
        return ret;
    }

    public String getIndexCreator(Searchable gen) {
        return ((Question)gen).getCreator().getURI();
    }

    @Override
    public String getIndexTitle(Searchable gen) {
        return ((Question) gen).getQuestion();
    }

    @Override
    public String getTitle(Searchable gen, String lang) {
        return ((Question) gen).getQuestion();
    }

    @Override
    public String getType(Searchable gen) {
        return "Question";
    }

    @Override
    public String getUpdated(Searchable gen) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(((Question)gen).getUpdated());
    }

    @Override
    public String getSummary(Searchable gen, String lang) {
        String ret = null;
        Question question = (Question) gen;
        if (question != null) {
            try {
                ret = SWBUtils.TEXT.parseHTML(question.getQuestion());
            } catch (Exception e) {
                log.error(e);
            }
        }
        return ret;
    }

    @Override
    public String getUrl(Searchable gen) {
        //TODO: Revisar si es la URL correcta
        Question q = (Question) gen;
        String ret = null;
        WebPage page = getWebPage((Resource) q.getForumResource().getResourceBase());
        if (page != null) {
            ret = super.getUrl(page);
        }
        return ret + "?searchAct=showDetail&uri=" + q.getEncodedURI();
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
        Question q = (Question) gen;
        WebPage page = getWebPage(q.getForumResource().getResource());
        if (page != null) {
            ret = super.getIndexCategory(page);
        }
        return ret;
    }

    @Override
    public String getTypeDisplayLabel(Searchable gen) {
        return "Pregunta";
    }

    @Override
    public boolean canUserView(Searchable gen, User user) {
        Question q = (Question) gen;
        boolean ret = super.canUserView(gen, user);
        return ret && q.getQueStatus() == SWBForumCatResource.STATUS_ACEPTED;
    }
}