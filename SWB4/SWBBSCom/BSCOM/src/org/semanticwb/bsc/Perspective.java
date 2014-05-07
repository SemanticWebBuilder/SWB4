package org.semanticwb.bsc;

import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.accessory.DifferentiatorGroup;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;


public class Perspective extends org.semanticwb.bsc.base.PerspectiveBase implements Comparable<Perspective>
{
    public Perspective(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public synchronized int getSerial() {
        setSerial(super.getSerial()+1);
        return super.getSerial();
    }

    @Override
    public synchronized void setSerial(int value) {
        super.setSerial(value);
    }
    
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            try {
                prefix = getTitle().trim().substring(0, 1).toUpperCase();
            }catch(Exception e) {
                prefix = "Untitled";
            }
            setPrefix(prefix);
        }
        return prefix;
    }

    @Override
    public void setPrefix(String value) {
        super.setPrefix(value);           
    }
    
    @Override
    public int compareTo(Perspective anotherPerspective)
    {
        int compare = getIndex()>anotherPerspective.getIndex()?1:-1;
        return compare;
    }
    
    public List<Theme> listValidThemes() {
        List<Theme> validThemes = SWBUtils.Collections.filterIterator(super.listThemes(), new GenericFilterRule<Theme>() {
                                                                        @Override
                                                                        public boolean filter(Theme t) {
                                                                            if(t==null) {
                                                                                return true;
                                                                            }
                                                                            User user = SWBContext.getSessionUser(getBSC().getUserRepository().getId());
                                                                            if(user==null) {
                                                                                user = SWBContext.getAdminUser();
                                                                            }
                                                                            return !t.isValid() || !user.haveAccess(t);
                                                                        }            
                                                                    });
        return validThemes;
    }
    
    public List<DifferentiatorGroup> listValidDifferentiatorGroups() {
        List<DifferentiatorGroup> validDiffGroups = SWBUtils.Collections.filterIterator(super.listDifferentiatorGroups(), new GenericFilterRule<DifferentiatorGroup>() {
                                                                        @Override
                                                                        public boolean filter(DifferentiatorGroup diffgroup) {
                                                                            if(diffgroup==null) {
                                                                                return true;
                                                                            }
                                                                            User user = SWBContext.getSessionUser(getBSC().getUserRepository().getId());
                                                                            if(user==null) {
                                                                                user = SWBContext.getAdminUser();
                                                                            }
                                                                            return !diffgroup.isValid() || !user.haveAccess(diffgroup);
                                                                        }            
                                                                    });
        return validDiffGroups;
    }
}
