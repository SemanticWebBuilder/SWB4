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
System.out.println("\n\n ordinalcategorical....");   
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
System.out.println("OrdinalCategorical. estados");
            State state = (State)obj.getGenericInstance();
            //StateGroup parent = state.getStateGroup();
            Status parent = state.getStatus();
            if(parent instanceof StateGroup) {
                GenericIterator<State> it = parent.listStates();
                while(it.hasNext()) {
                    State so = it.next();
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
System.out.println("\n\n");
System.out.println("OrdinalCategorical. Series");
            Series series = (Series)genObj;
System.out.println("indicador padre="+series.getIndicator().getTitle());
            Indicator parent = series.getIndicator();
//            if(parent instanceof StateGroup) {
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
//            }
        }
        else
        {
System.out.println("OrdinalCategorical. otro");
            super.validate(request, obj, prop, propName);
        }        
    }
}
