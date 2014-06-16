package org.semanticwb.bsc;

import java.util.Iterator;
import java.util.List;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.bsc.utils.InappropriateFrequencyException;
import org.semanticwb.bsc.utils.UndefinedFrequencyException;

public interface SM extends org.semanticwb.bsc.base.SMBase
{
    public List<Series> listValidSerieses();
    
    public State getMinimumState();
    
    public State getMaximumState();
    
    public BSC getBSC();
    
    /**
     * Recupera el último período de la lista de períodos asignados al indicador.
     * @return El período más posterior
     */
    public Period getLastPeriod();
    
    public List<State> listValidStates();
    
    public boolean updateAppraisal(Period period);
    
    public boolean hasState(State value);
    
    public Iterator<Period> listMeasurablesPeriods() throws UndefinedFrequencyException, InappropriateFrequencyException;
    
    public Iterator<Period> listMeasurablesPeriods(boolean ascendent) throws UndefinedFrequencyException, InappropriateFrequencyException;
    
    public List<Period> listValidPeriods();
}
