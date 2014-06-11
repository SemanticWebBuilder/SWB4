package org.semanticwb.bsc.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.tracing.EvaluationRule;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.bsc.utils.InappropriateFrequencyException;
import org.semanticwb.bsc.utils.UndefinedFrequencyException;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;


public class Deliverable extends org.semanticwb.bsc.element.base.DeliverableBase 
{
    public static final String names[] = {"Esfuerzo real","Esfuerzo planeado"};
    
    public Deliverable(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public BSC getBSC() {
        return getInitiative().getBSC();
    }
    
    @Override
    public void setProgress(int value) {
System.out.println("setProgress. value="+value);
        //1. asignar nuevo valor
        super.setProgress(value);
        //2. recalcular estatus de entregable
        //evaluate();
        //3. recalcular estatus de iniciativa
    }
    
    /*public void evaluate() {
//        getEvaluation().setStatus(getSeries().getIndicator().getObjective().getMinimumState());        
        Iterator<EvaluationRule> rules = listValidEvaluationRules(false).iterator();
	while(rules.hasNext())
	{
            EvaluationRule rule = rules.next();
            if(rule.evaluate(null)) {
                setAutoStatus(rule.getAppraisal());
                break;
            }
	}
    }*/
    
    
    @Override
    public boolean updateAppraisal(Period period) {
        return getInitiative().updateAppraisal(period);
    }
    
    /**
     * Recupera el último período de la lista de períodos asignados al indicador.
     * @return El período más posterior
     */
    @Override
    public Period getLastPeriod()
    {
        List<Period> periods = sortValidPeriods();
        try {
            return periods.get(periods.size()-1);
        }catch(IndexOutOfBoundsException e) {            
        }
        return null;
    }
    
    @Override
    public Iterator<Period> listAvailablePeriods() {
        return getInitiative().listAvailablePeriods();
    }
    
    @Override
    public Iterator<Period> listAvailablePeriods(boolean ascendent) {
        return getInitiative().listAvailablePeriods(ascendent);
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
    
    @Override
    public State getMinimumState() {
        List<State> states = sortStates();
        try {
            return states.get(0);
        }catch(IndexOutOfBoundsException e) {
        
        }
        return null;
    }
    
    @Override
    public State getMaximumState() {
        List<State> states = sortStates();
        try {
            return states.get(states.size()-1);
        }catch(IndexOutOfBoundsException e) {
        
        }
        return null;
    }
    
    @Override
    public boolean hasState(State value) {
        return getInitiative().hasState(value);
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
    
    @Override
    public List<State> listValidStates() {
        return getInitiative().listValidStates();
    }
    
    @Override
    public List<Series> listValidSerieses() {
        List<Series> validSerieses = SWBUtils.Collections.filterIterator(super.listSerieses(), new GenericFilterRule<Series>() {
                                                                        @Override
                                                                        public boolean filter(Series s) {
                                                                            //s.getUserGroup().getUserRepository().getId()
                                                                            BSC scorecard = (BSC) s.getSemanticObject().getModel().getModelObject().createGenericInstance();
                                                                            User user = SWBContext.getSessionUser(scorecard.getUserRepository().getId());
                                                                            if(user==null) {
                                                                                user = SWBContext.getAdminUser();
                                                                            }
                                                                            return !s.isValid() || !user.haveAccess(s);
                                                                        }            
                                                                    });
        return validSerieses;
    }
    
    /**
     * Devuelve todos los períodos de medición ordenados ascendentemente
     * @return A GenericIterator with all the org.semanticwb.bsc.accessory.Period sorted
     * @throws org.semanticwb.bsc.utils.UndefinedFrequencyException
     * @throws org.semanticwb.bsc.utils.InappropriateFrequencyException
     */
    @Override
    public Iterator<Period> listMeasurablesPeriods() throws UndefinedFrequencyException, InappropriateFrequencyException {
        return listMeasurablesPeriods(true);
    }
    
    /**
     * Devuelve todos los períodos de medición ordenados.
     * @param ascendent - para ordenar ascendente use ascendent=true y descendentemente=false para orden descendente
     * @return A GenericIterator with all the org.semanticwb.bsc.accessory.Period sorted
     * @throws org.semanticwb.bsc.utils.UndefinedFrequencyException
     * @throws org.semanticwb.bsc.utils.InappropriateFrequencyException
     */
    @Override
    public Iterator<Period> listMeasurablesPeriods(boolean ascendent) throws UndefinedFrequencyException, InappropriateFrequencyException {
        int f = 0;
        try {
            //f = getPeriodicity().getNumberOfPeriods();
            f = 1;
        }catch(Exception e) {
            throw new UndefinedFrequencyException("Frecuencia de medición indefinida.");
        }
        if(f<1) {
            throw new InappropriateFrequencyException("Frecuencia de medición inapropiada");
        }
        
        List<Period> periods = sortValidPeriods();        
        List<Period> measurablesPeriods = new ArrayList<>();
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
}
