package org.semanticwb.bsc.formelement;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.accessory.Differentiator;
import org.semanticwb.bsc.accessory.DifferentiatorGroup;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.accessory.StateGroup;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.bsc.element.Perspective;
import org.semanticwb.bsc.element.Theme;
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
            State state = (State)obj.createGenericInstance();
            StateGroup parent = state.getStateGroup();
            if(parent instanceof StateGroup) {
                GenericIterator<State> it = parent.listGroupedStateses();
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
        else if(genObj instanceof Theme)
        {
            Theme theme = (Theme)genObj;
            Perspective parent = theme.getPerspective();
            GenericIterator<Theme> it = parent.listThemes();
            while(it.hasNext()) {
                Theme t = it.next();
                if( theme.equals(t) ) {
                    continue;
                }
                if(ordinal == t.getIndex())
                {
                    throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
                }
            }
        }
        else if(genObj instanceof Differentiator)
        {
            Differentiator dif = (Differentiator)genObj;
            DifferentiatorGroup parent = dif.getDifferentiatorGroup();
            GenericIterator<Differentiator> it = parent.listDifferentiators();
            while(it.hasNext()) {
                Differentiator d = it.next();
                if( dif.equals(d) ) {
                    continue;
                }
                if(ordinal == d.getIndex())
                {
                    throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
                }
            }
        }
        else if(genObj instanceof Objective)
        {
            Objective objective = (Objective)genObj;
            Theme parent = objective.getTheme();
            GenericIterator<Objective> it = parent.listObjectives();
            while(it.hasNext()) {
                Objective o = it.next();
                if( objective.equals(o) ) {
                    continue;
                }
                if(ordinal == o.getIndex())
                {
                    throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
                }
            }
        }
        else
        {
            super.validate(request, obj, prop, propName);
        }        
    }
}
