package org.semanticwb.bsc.accessory;


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
        int compare = 0;
        compare = getOrden() > anotherState.getOrden() ? 1 : -1;
        return compare;
    }
}
