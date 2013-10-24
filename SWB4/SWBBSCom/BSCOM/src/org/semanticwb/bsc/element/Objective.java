package org.semanticwb.bsc.element;

import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import static org.semanticwb.bsc.element.Indicator.*;

public class Objective extends org.semanticwb.bsc.element.base.ObjectiveBase 
{
    static
    {
        bsc_hasIndicator.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                if("ADD".equalsIgnoreCase(action)) {
                    SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
                    Objective objective = (Objective)obj.createGenericInstance();
                    List<State> states = objective.listValidStates();
                    // Funcionan exactamente igual objective.getIndicator() y objective.getLastIndicator()
                    Indicator indicator = objective.getIndicator();
                    if(!states.isEmpty() && indicator!=null)
                    {
                        indicator.addAllStates(states);
                        Series series;
                        for(int i=0; i<names.length; i++)
                        {
                            series = Series.ClassMgr.createSeries(model);
                            series.setTitle(names[i]);
                            series.setTitle(names[i], lang);
                            series.setDescription("Serie "+names[i]);
                            series.setDescription("Serie "+names[i], lang);
                            series.setIndex(i);
                            indicator.addSeries(series);
                        }
                    }
                }
            }
        });
    }
    
    
    public Objective(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }
    
    /**
     * Obtiene el n&uacute;mero consecutivo utilizado en la asignaci&oacute;n del prefijo de un 
     * indicador.
     * @return {@code Int} Retorna un n&uacute;mero consecutivo, obtenido del objetivo que 
     * contiene los indicadores
     */
    @Override
    public synchronized int getSerial() {
        setSerial(super.getSerial()+1);
        return super.getSerial();
    }
    
    /**
     * Actualiza el n&uacute;mero consecutivo a utilizar en el prefijo de un indicador.
     * @param value N&uacute;mero entero actualizado.
     */
    @Override
    public synchronized void setSerial(int value) {
        super.setSerial(value);
    }

    /**
     * Obtiene el prefijo que identifica un objetivo. El prefijo esta definido por el prefijo de la 
     * perspectiva a la que esta asociado el objetivo, seguido de un n&uacute;mero consecutivo. 
     * @return {@code String} Regresa un string con el prefijo asociado a un objetivo.
     * <p>
     * Ejemplo:
     * </p> 
     * <p>
     * El prefijo de la perspectiva es F y el n&uacute;mero consecutivo asignado para el objetivo es 
     * 1, se obtendr&iacute;a el siguiente prefijo: 
     *  {@literal  F1 }
     * </p> 
     */
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            try {
                prefix = getTheme().getPerspective().getPrefix().trim() + getTheme().getPerspective().getSerial();
            }catch(Exception e) {
                prefix = getTheme().getPerspective().getTitle().substring(0, 1).toUpperCase() + getTheme().getPerspective().getSerial();
            }
            setPrefix(prefix);
        }
        return prefix;
    }
    
    /**
     * Asigna el prefijo de un objetivo.
     */
    @Override
    public void setPrefix(String value) {
        super.setPrefix(value);
    }
    
    public Indicator getLastIndicator() {
        return getIndicator();
    }

    @Override
    public Indicator getIndicator() {
        Iterator<Indicator> it = SWBComparator.sortByCreated(listIndicators(), false);
        return it.hasNext()?it.next():null;
    }
    
    public List<State> listValidStates() {
        List<State> validStates = SWBUtils.Collections.filterIterator(listStates(), new GenericFilterRule<State>() {
                                                                        @Override
                                                                        public boolean filter(State s) {
                                                                            User user = SWBContext.getSessionUser();
                                                                            return !s.isValid() || !user.haveAccess(s);
                                                                        }            
                                                                    });
        return validStates;
    }

    @Override
    public void removeState(State value) {
        // Eliminar en cascada el estado de los indicadores del objetivo
        Iterator<Indicator> indicators = listIndicators();
        while(indicators.hasNext()) {
            indicators.next().removeState(value);
        }
        super.removeState(value);
    }
    
    
    
    @Override
    public void removeAllState()
    {
        // Eliminar en cascada los estados asignados a los indicadores del objetivo
        Iterator<Indicator> indicators = listIndicators();
        while(indicators.hasNext()) {
            indicators.next().removeAllState();
        }
        super.removeAllState();
    }

}
