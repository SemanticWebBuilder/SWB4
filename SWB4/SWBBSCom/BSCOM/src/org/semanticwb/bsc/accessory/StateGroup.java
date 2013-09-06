package org.semanticwb.bsc.accessory;

import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.accessory.base.StateGroupBase;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


public class StateGroup extends org.semanticwb.bsc.accessory.base.StateGroupBase 
{
    static
    {
        
        StateGroupBase.swb_active.registerObserver(new SemanticObserver() {
                @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
System.out.println("activaste/desactivas el grupo de estados");
            }
        });
        
        
        
        
        sclass.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
System.out.println("\n\n..............");
System.out.println("notify stategroup....");
System.out.println(SWBPlatform.getContextPath() + "/swbadmin/icons/");                
                
                System.out.println("creando objetivo");
                System.out.println("obj="+obj);
                System.out.println("prop="+prop);
                System.out.println("lang="+lang);
                System.out.println("action="+action);
                if("CREATE".equals(action)) {
                    SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
System.out.println("modelo="+model);
                    State undefined = State.ClassMgr.createState(model);
System.out.println("estado indefinido="+undefined);                    
                    undefined.setOrden(0);
                    undefined.setTitle("No definido");
                    undefined.setTitle("No definido", lang);
                    undefined.setDescription("No aplica mediciones", lang);
//                    undefined.setActive(true);
                    //undefined.setIconClass("undefined");
                    //undefined.setIcon("holamundo");

                    StateGroup sg = (StateGroup)obj.createGenericInstance();
System.out.println("grupo="+sg);
                    sg.addGroupedStates(undefined);
                }
            }
        });
    }
    
    public StateGroup(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
