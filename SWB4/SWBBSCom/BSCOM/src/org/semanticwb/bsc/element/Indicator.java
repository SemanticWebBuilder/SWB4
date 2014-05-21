package org.semanticwb.bsc.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.tracing.EvaluationRule;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.bsc.utils.InappropriateFrequencyException;
import org.semanticwb.bsc.utils.UndefinedFrequencyException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;

/*
 * Clase que persiste informaci&oacute;n de un indicador. Un indicador permite informar sobre el avance para alcanzar un objetivo.
 */
public class Indicator extends org.semanticwb.bsc.element.base.IndicatorBase
{
    public static final String names[] = {"Actual","Meta","Actual Acumulado","Meta Acumulada"};
    static
    {        
        bsc_hasSeries.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                if("ADD".equalsIgnoreCase(action)) {
                    Indicator indicator = (Indicator)obj.createGenericInstance();
                    // Funcionan exactamente igual indicator.getSeries() y indicator.getLastSeries()
                    Series series = indicator.getSeries();
                    if(series!=null && series.getEvaluationRule()==null)
                    {
                        State state;
                        List<State> validStates = indicator.listValidStates();
                        for(int i=0; i<validStates.size(); i++) {
                            state = validStates.get(i);
                            EvaluationRule rule = EvaluationRule.ClassMgr.createEvaluationRule(indicator.getBSC());
                            rule.setTitle("Regla para "+state.getTitle());
                            rule.setTitle("Regla para "+state.getTitle(lang), lang);
                            rule.setDescription("Regla para evaluar serie "+names[i]);
                            rule.setDescription("Regla para evaluar serie "+names[i], lang);
                            rule.setAppraisal(state);
                            series.addEvaluationRule(rule);
                        }                            
                    }
                }
            }
        });
    }
   
    public Indicator(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Obtiene el prefijo para identificar un indicador. 
     * En caso de que el prefijo sea nulo, asigna un prefijo en autom&aacute;tico. 
     * @return {@code String} Regresa un string con el prefijo asociado al indicador, 
     * si el prefijo es asignado en autom&aacute;tico utiliza el prefijo del objetivo
     * seguido de un n&uacute;mero consecutivo asignado por un motor.
     * <p>
     * Ejemplo:
     * </p> 
     * <p>
     * El motor asigna un n&uacute;mero 1 para el Objetivo F1, se obtendr&iacute;a el siguiente prefijo: 
     *  {@literal  F1.1 }
     * </p>
     */
    @Override
    public String getPrefix() {
        String prefix = super.getPrefix();
        if(prefix==null) {
            try {
                prefix = getObjective().getPrefix().trim() + "." + getObjective().getSerial();
            }catch(Exception e) {
                prefix = getObjective().getTitle().substring(0, 1).toUpperCase() + "." + getObjective().getSerial();
            }
            setPrefix(prefix);
        }
        return prefix;
    }

    /**
     * Asigna un prefijo al indicador
     * @param value 
     */
    @Override
    public void setPrefix(String value) {
        super.setPrefix(value);
    }
    
    private List<Period> sortValidPeriods() {
        return sortValidPeriods(true);
    }
    
    private List<Period> sortValidPeriods(boolean ascendent) {
        //List<Period> periods = SWBUtils.Collections.copyIterator(listPeriods());
        List<Period> periods = listValidPeriods();
        if(ascendent) {
            Collections.sort(periods);
        }else {            
            Collections.sort(periods, Collections.reverseOrder());            
        }
        return periods;
    }
    
    private List<Period> sortPeriods() {
        return sortPeriods(true);
    }
    
    private List<Period> sortPeriods(boolean ascendent) {
        List<Period> periods = SWBUtils.Collections.copyIterator(super.listPeriods());
        if(ascendent) {
            Collections.sort(periods);
        }else {            
            Collections.sort(periods, Collections.reverseOrder());            
        }
        return periods;
    }
    
    private List<State> sortStates() {
        return sortStates(true);
    }
    
    private List<State> sortStates(boolean ascendent) {
        //List<State> states = SWBUtils.Collections.copyIterator(listStates());
        List<State> states = listValidStates();
        if(ascendent) {
            Collections.sort(states);
        }else {            
            Collections.sort(states, Collections.reverseOrder());
        }
        return states;
    }
    
    /**
     * Devuelve todos los períodos de medición ordenados ascendentemente
     * @return A GenericIterator with all the org.semanticwb.bsc.accessory.Period sorted
     */
    public Iterator<Period> listMeasurablesPeriods() throws UndefinedFrequencyException, InappropriateFrequencyException {
        return listMeasurablesPeriods(true);
    }
    
    /**
     * Devuelve todos los períodos de medición ordenados.
     * @param ascendent - para ordenar ascendente use ascendent=true y descendentemente=false para orden descendente
     * @return A GenericIterator with all the org.semanticwb.bsc.accessory.Period sorted
     */
    public Iterator<Period> listMeasurablesPeriods(boolean ascendent) throws UndefinedFrequencyException, InappropriateFrequencyException {
        int f = 0;
        try {
            f = getPeriodicity().getNumberOfPeriods();
        }catch(Exception e) {
            throw new UndefinedFrequencyException("Frecuencia de medición indefinida.");
        }
        if(f<1) {
            throw new InappropriateFrequencyException("Frecuencia de medición inapropiada");
        }
        
        List<Period> periods = sortValidPeriods();        
        List<Period> measurablesPeriods = new ArrayList<Period>();
        for(int i=1; i<=periods.size(); i++) {                 
            if(i%f==0) {                
                measurablesPeriods.add(periods.get(i-1));
            }
        }
        if(ascendent) {
            Collections.sort(measurablesPeriods);
        }else {
            Collections.sort(measurablesPeriods, Collections.reverseOrder());
        }
        return measurablesPeriods.iterator();
    }
    
    /**
     * Recupera el primer período de la lista de períodos asignados al indicador.
     * @return El período más anterior
     */
    public Period getFirstPeriod()
    {
        List<Period> periods = sortValidPeriods();
        try {
            return periods.get(0);
        }catch(IndexOutOfBoundsException e) {            
        }
        return null;
    }
    
    /**
     * Recupera el último período de la lista de períodos asignados al indicador.
     * @return El período más posterior
     */
    public Period getLastPeriod()
    {
        List<Period> periods = sortValidPeriods();
        try {
            return periods.get(periods.size()-1);
        }catch(IndexOutOfBoundsException e) {            
        }
        return null;
    }
    
    public State getMinimumState() {
        List<State> states = sortStates();
        try {
            return states.get(0);
        }catch(IndexOutOfBoundsException e) {
        
        }
        return null;
    }
    
    public State getMaximumState() {
        List<State> states = sortStates();
        try {
            return states.get(states.size()-1);
        }catch(IndexOutOfBoundsException e) {
        
        }
        return null;
    }
    
    public Series getLastSeries()
    {
        return getSeries();
    }
    
    @Override
    public Series getSeries()
    {
        Iterator<Series> it = SWBComparator.sortByCreated(listSerieses(), false);
        return it.hasNext()?it.next():null;
    }

    @Override
    public GenericIterator<Period> listPeriods() {
        return (GenericIterator)listPeriods(true);
    }
    
    public Iterator<Period> listPeriods(boolean ascendent) {
        return sortPeriods(ascendent).iterator();
    }
    
    public List<Series> listValidSerieses() {
        List<Series> validSerieses = SWBUtils.Collections.filterIterator(super.listSerieses(), new GenericFilterRule<Series>() {
                                                                        @Override
                                                                        public boolean filter(Series s) {
                                                                            User user = SWBContext.getSessionUser(getBSC().getUserRepository().getId());
                                                                            if(user==null) {
                                                                                user = SWBContext.getAdminUser();
                                                                            }
                                                                            return !s.isValid() || !user.haveAccess(s);
                                                                        }            
                                                                    });
        return validSerieses;
    }
    
    public List<Period> listValidPeriods() {
        List<Period> validPeriods = SWBUtils.Collections.filterIterator(super.listPeriods(), new GenericFilterRule<Period>() {
                                                                        @Override
                                                                        public boolean filter(Period p) {
                                                                            User user = SWBContext.getSessionUser(getBSC().getUserRepository().getId());
                                                                            if(user==null) {
                                                                                user = SWBContext.getAdminUser();
                                                                            }
                                                                            return !p.isValid() || !user.haveAccess(p);
                                                                        }            
                                                                    });
        return validPeriods;
    }
    
    public List<State> listValidStates() {
        return getObjective().listValidStates();
    }
    
    /**
     * Recupera el período más próximo de medición en base a la fecha actual. 
     * La diferencia con nextMeasurementPeriod() es que si actualmente es tiempo de medición,
     * nextMeasurementPeriod() devolverá no este período sino el siguiente.
     * Siempre devuelve el siguiente.
     * @return El período más próximo de medición en base a la fecha actual.
     */
    public Period currentMeasurementPeriod()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }    
}
