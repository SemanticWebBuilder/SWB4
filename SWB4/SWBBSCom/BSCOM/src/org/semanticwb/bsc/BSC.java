package org.semanticwb.bsc;

import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;


public class BSC extends org.semanticwb.bsc.base.BSCBase 
{
    public BSC(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
     public List<Initiative> listValidInitiative() {

        List<Initiative> validInitiative = SWBUtils.Collections.filterIterator(listInitiatives(), new GenericFilterRule<Initiative>() {
                                                                        @Override
                                                                        public boolean filter(Initiative s) {
                                                                            User user = SWBContext.getSessionUser();
                                                                            return !s.isValid() || !user.haveAccess(s);
                                                                        }            
                                                                    });
        return validInitiative;
    }
}
