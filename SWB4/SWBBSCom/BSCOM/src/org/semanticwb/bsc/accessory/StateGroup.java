package org.semanticwb.bsc.accessory;

import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.accessory.base.StateGroupBase;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


public class StateGroup extends org.semanticwb.bsc.accessory.base.StateGroupBase 
{
    static
    {
        sclass.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                if("CREATE".equalsIgnoreCase(action)) {
                    SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
                    State undefined = State.ClassMgr.createState(model);
                    undefined.setOrden(0);
                    undefined.setTitle("No definido");
                    undefined.setTitle("No definido", lang);
                    undefined.setDescription("No aplica mediciones", lang);
                    //undefined.setIconClass("undefined");
                    //undefined.setIcon("holamundo");
                    StateGroup sg = (StateGroup)obj.createGenericInstance();
                    sg.addGroupedStates(undefined);
                }
            }
        });
    }
    
    public StateGroup(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public List<State> listValidStates() {
        List<State> validStates = SWBUtils.Collections.filterIterator(super.listGroupedStateses(), new GenericFilterRule<State>() {
                                                                        @Override
                                                                        public boolean filter(State s) {
                                                                            return !s.isValid();
                                                                        }            
                                                                    });
        return validStates;
    }
}
