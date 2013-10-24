package org.semanticwb.bsc.formelement;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.Status;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.accessory.StateGroup;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 * OrdinalCategorical es un FormElement para manejar valores ordinales que no
 * se repiten para instancias de una clase que comparten la misma instancia de
 * la super clase inmediata.
 * 
 * Se usa principalmente para elementos que implementan la interface StateMachinable
 * 
 * @author      Carlos Ramos Incháustegui
 * @version     %I%, %G%
 * @since       1.0
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
System.out.println("\n\n OrdinalCategorical  validate.....");
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
        
        GenericObject genObj = obj.getGenericInstance();
        if(genObj instanceof State)
        {
System.out.print("State...");
            State state = (State)obj.createGenericInstance();
            //StateGroup parent = state.getStateGroup();
System.out.println(" "+state);
            StateGroup parent = state.getStateGroup();
System.out.println("parent="+parent);
            if(parent instanceof StateGroup) {
System.out.println("ordinal="+ordinal);
                GenericIterator<State> it = parent.listGroupedStateses();
                while(it.hasNext()) {
                    State so = it.next();
System.out.println("so="+so);
System.out.println("orden="+so.getOrden());
                    if( state.equals(so) ) {
                        continue;
                    }
                    if(ordinal == so.getOrden())
                    {
                        throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
                    }

                }
            }
        }
        else if(genObj instanceof Series)
        {
System.out.println("Serie...");
            Series series = (Series)genObj;
            Indicator parent = series.getIndicator();
            GenericIterator<Series> it = parent.listSerieses();
            while(it.hasNext()) {
                Series s = it.next();
                if( series.equals(s) ) {
                    continue;
                }
                if(ordinal == s.getIndex())
                {
                    throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
                }
            }
        }
        else
        {
System.out.println("else");
            super.validate(request, obj, prop, propName);
        }        
    }
}
