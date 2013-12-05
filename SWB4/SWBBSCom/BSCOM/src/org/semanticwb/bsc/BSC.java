package org.semanticwb.bsc;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.accessory.Period;
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
    
    private List<Period> sortPeriods() {
        return sortPeriods(true);
    }
    
    private List<Period> sortPeriods(boolean ascendent) {
        List<Period> periods = SWBUtils.Collections.copyIterator(super.listPeriods());
        if(ascendent) {
            Collections.sort(periods);
        }else {            
            Collections.sort(periods, Collections.reverseOrder());            
        }
        return periods;
    }

    @Override
    public Iterator<Period> listPeriods() {
        return sortPeriods().iterator();
    }
    
    public Iterator<Period> listPeriods(boolean ascendent) {
        return sortPeriods(ascendent).iterator();
    }
    
    public List<Period> listValidPeriods() {
        List<Period> validPeriods = SWBUtils.Collections.filterIterator(listPeriods(), new GenericFilterRule<Period>() {
                                                                        @Override
                                                                        public boolean filter(Period p) {
                                                                            if(p==null) {
                                                                                return true;
                                                                            }
                                                                            User user = SWBContext.getSessionUser();
                                                                            return !p.isValid() || !user.haveAccess(p);
                                                                        }            
                                                                    });
        return validPeriods;
    }
}
