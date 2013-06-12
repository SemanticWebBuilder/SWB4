package org.semanticwb.bsc.formelement;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.accessory.StateGroup;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 * OrdinalCategorical maneja un valores ordinales que no se repiten para 
 * instancias de una clase que comparten la misma instancia de la super clase
 * inmediata. 
*/
public class OrdinalCategorical extends org.semanticwb.bsc.formelement.base.OrdinalCategoricalBase 
{
    private static Logger log = SWBUtils.getLogger(OrdinalCategorical.class);
    
    public OrdinalCategorical(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) throws FormValidateException
    {
System.out.println("\n\nvalidate.....");
        int ordinal;
        try            
        {
            String value = request.getParameter(propName);
            ordinal = Integer.parseInt(value);
        }   
        catch(Exception pe)
        {            
            throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
        }
        
        SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
        SemanticObject parent = null;
System.out.println("obj="+obj);
        if(obj.getGenericInstance() instanceof State)
        {
            State state = (State)obj.getGenericInstance();
            StateGroup sg = state.getStateGroup();
    System.out.println("sg="+sg);
            Iterator<State> it = sg.listStates();
            while(it.hasNext()) {
                State s = it.next();
    System.out.println("estate="+s);        
            }
        }
        else if(obj.getGenericInstance() instanceof Period)
        {    
            
        }
        /*Iterator<SemanticObject> it = model.getSemanticModel().listInstancesOfClass(obj.getSemanticClass());
        while(it.hasNext()) {
            SemanticObject so = it.next();
            if( obj.equals(so) ) {
                continue;
            }
            if(ordinal == so.getIntProperty(prop))
            {
                throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
            }
        }*/     
    }
}
