package org.semanticwb.bsc.accessory;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.platform.SemanticProperty;


public class State extends org.semanticwb.bsc.accessory.base.StateBase implements Comparable<State>
{
    public State(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

//    @Override
//    public String getIcon() {
//        if(super.getIcon()==null) {
//            return SWBPlatform.getContextPath()+"/org/semanticwb/bsc/admin/images/icons/icon_outtime.gif";
//        }else {
//            return SWBPortal.getWebWorkPath()+this.getWorkPath()+"/"+super.getIcon();
//        }
//    }
    
    @Override    
    public int compareTo(State anotherState) {
        if(anotherState==null) {
            return 1;
        }
        int compare;
        compare = getIndex() > anotherState.getIndex() ? 1 : -1;
        return compare;
    }

    @Override
    public void validOrder(HttpServletRequest request, SemanticProperty prop, String propName) throws FormValidateException
    {
        int ordinal;
        try {
            String value = request.getParameter(propName);
            ordinal = Integer.parseInt(value);
        }catch(NumberFormatException pe) {            
            throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
        }
        
        GenericIterator<State> it = getStateGroup().listGroupedStateses();
        while(it.hasNext()) {
            State s = it.next();
            if( this.equals(s) ) {
                continue;
            }
            if(s.getIndex() == ordinal) {
                throw new FormValidateException("El valor ordinal debe ser numérico y no puede repetirse");
            }

        }
    }
}
