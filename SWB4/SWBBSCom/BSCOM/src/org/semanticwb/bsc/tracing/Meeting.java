package org.semanticwb.bsc.tracing;

import java.util.Collections;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;

   /**
   * Persiste la información de una Sesión. Existen  dos tipos de sesiones: RAE y NOA 
   */
public class Meeting extends org.semanticwb.bsc.tracing.base.MeetingBase 
{
    public Meeting(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public int getSerial() {
        return Integer.parseInt(getId());
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
                prefix = getBSC().getId()+"-"+(getMeetingType()!= null
                        ?getMeetingType().toUpperCase() : "")+"-"+(getSerial()>9
                        ?"":"0")+getSerial();
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
    public State getMinimumState() {
        List<State> states = sortStates();
        try {
            return states.get(0);
        }catch(IndexOutOfBoundsException e) {
        
        }
        return null;
    }
    
    @Override
    public State getMaximumState() {
        List<State> states = sortStates(false);
        try {
            return states.get(0);
        }catch(IndexOutOfBoundsException e) {
        
        }
        return null;
    }
    
    @Override
    public List<State> sortStates() {
        return sortStates(true);
    }
    
    @Override
    public List<State> sortStates(boolean ascendent) {
        List<State> states = listValidStates();
        if(ascendent) {
            Collections.sort(states);
        }else {            
            Collections.sort(states, Collections.reverseOrder());
        }
        return states;
    }
    
    @Override
    public List<State> listValidStates() {
        List<State> validStates = SWBUtils.Collections.filterIterator(super.listStates(), new GenericFilterRule<State>() {
                                                        @Override
                                                        public boolean filter(State s) {
                                                            if(s==null) {
                                                                return true;
                                                            }
                                                            User user = SWBContext.getSessionUser(getBSC().getUserRepository().getId());
                                                            if(user==null) {
                                                                user = SWBContext.getAdminUser();
                                                            }
                                                            return !s.isValid() || !user.haveAccess(s);
                                                        }            
                                                    });
        return validStates;
    }
}
