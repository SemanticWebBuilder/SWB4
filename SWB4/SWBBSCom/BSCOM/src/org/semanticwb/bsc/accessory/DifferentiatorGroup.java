package org.semanticwb.bsc.accessory;

import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;


   /**
   * Un DifferentiatorGroup es una clase que permitir contener uno o varios Differentiator que se dibujan en el mapa estratégico del scorecard. 
   */
public class DifferentiatorGroup extends org.semanticwb.bsc.accessory.base.DifferentiatorGroupBase
{
    public DifferentiatorGroup(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public List<Differentiator> listValidDifferentiators() {
        List<Differentiator> validDiffs = SWBUtils.Collections.filterIterator(super.listDifferentiators(), new GenericFilterRule<Differentiator>() {
                                                                        @Override
                                                                        public boolean filter(Differentiator diff) {
                                                                            if(diff==null) {
                                                                                return true;
                                                                            }
                                                                            User user = SWBContext.getSessionUser(getPerspective().getBSC().getUserRepository().getId());
                                                                            return !diff.isValid() || !user.haveAccess(diff);
                                                                        }            
                                                                    });
        return validDiffs;
    }
}
