/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer.parser;

import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.User;
import org.semanticwb.portal.indexer.IndexTerm;
import org.semanticwb.portal.indexer.SWBIndexer;

/**
 *
 * @author hasdai
 */
public class UserParser extends GenericParser {
    private static Logger log = SWBUtils.getLogger(UserParser.class);

    public static final String ATT_NAME = "name";
    public static final String ATT_EMAIL = "email";

    @Override
    public Map<String, IndexTerm> getIndexTerms(Searchable gen) {
       Map map=super.getIndexTerms(gen);
       try {
           map.put(ATT_NAME, new IndexTerm(ATT_NAME, getName((User)gen), false, IndexTerm.INDEXED_ANALYZED));
           map.put(ATT_EMAIL, new IndexTerm(ATT_EMAIL, getEmail((User)gen), false, IndexTerm.INDEXED_ANALYZED));
       }catch(Exception e) {log.error(e);}

       return map;
    }

    public String getName(User user) {
        return (user.getFullName()==null?"":user.getFullName());
    }

    public String getEmail (User user) {
        return (user.getEmail()==null?"":user.getEmail());
    }

}
