package org.semanticwb.bsc.element;

import com.hp.hpl.jena.rdf.model.Property;
import java.util.Collections;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.model.GenericIterator;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import static org.semanticwb.bsc.base.SeasonableBase.bsc_hasPeriod;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import static org.semanticwb.bsc.element.Indicator.*;
import org.semanticwb.platform.SemanticProperty;

public class Objective extends org.semanticwb.bsc.element.base.ObjectiveBase implements Comparable<Objective>
{
    static
    {
//        bsc_hasState.registerObserver(new SemanticObserver() {
//            @Override
//            public void notify(SemanticObject obj, Object prop, String lang, String action)
//            {
//System.out.println("\nObjective hasState notify....");
//System.out.println("obj="+obj);
//System.out.println("prop="+prop);
//System.out.println("action="+action);
//            }
//        });
        
        /*bsc_hasPeriod.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
System.out.println("\nObjetivo bsc_hasPeriod notify....");
System.out.println("obj="+obj);
System.out.println("prop="+prop);
System.out.println("action="+action);
                if("REMOVE".equalsIgnoreCase(action)) {
System.out.println("Removing...");
                    Iterator<SemanticObject> it = obj.listObjectProperties(bsc_hasPeriod);
                    while(it.hasNext()) {
                        SemanticObject so = it.next();
                        System.out.println("semantic obj="+so.getId()+","+so);
                    }
                    
                    
                    SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
                    Objective objective = (Objective)obj.createGenericInstance();
                    Iterator<Indicator> indicators = Indicator.ClassMgr.listIndicatorByObjective(objective, model);
                    Indicator ind;
                    while(indicators.hasNext()) {
                        ind = indicators.next();
                    }
                }
                else if("ADD".equalsIgnoreCase(action))
                {
System.out.println("Adding...");
                    Iterator<SemanticObject> it = obj.listObjectProperties(bsc_hasPeriod);
                    while(it.hasNext()) {
                        SemanticObject so = it.next();
                        System.out.println("semantic obj="+so.getId()+","+so);
                    }
                }
            }
        });*/
        
        
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
                            series.setActive(true);
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
    
    public boolean updateAppraisal(Period period) {
        boolean res = Boolean.FALSE;
        State status = null;
        PeriodStatus appraisal = getPeriodStatus(period);
        if(appraisal==null) {
            appraisal = PeriodStatus.ClassMgr.createPeriodStatus(getBSC());
            appraisal.setPeriod(period);
            addPeriodStatus(appraisal);
            status = getMaximumState();
        }else {
            status = appraisal.getStatus();
        }
        
        Iterator<Indicator> indicators = listValidIndicators().iterator();
        while(indicators.hasNext()) {
            Indicator indicator = indicators.next();
            Series star = indicator.getStar();
            if(star==null || star.getMeasure(period)==null || star.getMeasure(period).getEvaluation().getStatus()==null) {
                continue;
            }
            //if( star.getMeasure(period).getEvaluation().getStatus().compareTo(status)<0 ) {
            //if( status.compareTo(star.getMeasure(period).getEvaluation().getStatus())>0 ) {
            if( star.getMeasure(period).getEvaluation().getStatus().compareTo(status)<0 ) {
                status = star.getMeasure(period).getEvaluation().getStatus();
                res = Boolean.TRUE;
            }
        }
        appraisal.setStatus(status);
        return res;
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
        List<State> states = sortStates(false);
        try {
            return states.get(0);
        }catch(IndexOutOfBoundsException e) {
        
        }
        return null;
    }
    
    public State getState(Period period) {
        State status = null;
        PeriodStatus ps = getPeriodStatus(period);
        if(ps!=null) {
            status = ps.getStatus()==null?getMinimumState():ps.getStatus();
        }
        return status;
    }
    
    public PeriodStatus getPeriodStatus(Period period) {
        Iterator<PeriodStatus> appraisals = listPeriodStatuses();
        PeriodStatus appraisal = null;
        while(appraisals.hasNext())
        {
            appraisal = appraisals.next();
            if(appraisal.getPeriod().equals(period))
            {
                return appraisal;
            }
        }
        return null;
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
        //List<State> states = SWBUtils.Collections.copyIterator(super.listStates());
        List<State> states = listValidStates();
        
        
        
        if(ascendent) {
            Collections.sort(states);
        }else {            
            Collections.sort(states, Collections.reverseOrder());
        }
        return states;
    }
    
    public Indicator getLastIndicator() {
        return getIndicator();
    }

    @Override
    public Indicator getIndicator() {
        Iterator<Indicator> it = SWBComparator.sortByCreated(listIndicators(), false);
        return it.hasNext()?it.next():null;
    }
    
    @Override
    public GenericIterator<Period> listPeriods() {
        return (GenericIterator)listPeriods(true);
    }
    
    public Iterator<Period> listPeriods(boolean ascendent) {
        return sortPeriods(ascendent).iterator();
    }
    
    public List<State> listValidStates() {
        List<State> validStates = SWBUtils.Collections.filterIterator(super.listStates(), new GenericFilterRule<State>() {
                                                        @Override
                                                        public boolean filter(State s) {
                                                            if(s==null) {
                                                                return true;
                                                            }
                                                            User user = SWBContext.getSessionUser();
                                                            return !s.isValid() || !user.haveAccess(s);
                                                        }            
                                                    });
        return validStates;
    }
    
    public List<Period> listValidPeriods() {
        List<Period> validPeriods = SWBUtils.Collections.filterIterator(super.listPeriods(), new GenericFilterRule<Period>() {
                                                        @Override
                                                        public boolean filter(Period s) {
                                                            User user = SWBContext.getSessionUser();
                                                            return !s.isValid() || !user.haveAccess(s);
                                                        }            
                                                    });
        return validPeriods;
    }
    
    public List<Indicator> listValidIndicators() {
        List<Indicator> validIndicators = SWBUtils.Collections.filterIterator(super.listIndicators(), new GenericFilterRule<Indicator>() {
                                                        @Override
                                                        public boolean filter(Indicator s) {
                                                            User user = SWBContext.getSessionUser();
                                                            return !s.isValid() || !user.haveAccess(s);
                                                        }            
                                                    });
        return validIndicators;
    }

    @Override
    public void removePeriod(Period value) {
        super.removePeriod(value);
        GenericIterator<Indicator> gi = listIndicators();
        while(gi.hasNext()) {
            gi.next().removePeriod(value);
        }
    }

    @Override
    public void removeAllPeriod() {
        super.removeAllPeriod();
        GenericIterator<Indicator> gi = listIndicators();
        while(gi.hasNext()) {
            gi.next().removeAllPeriod();
        }
    }
    
    @Override
    public void removeState(State value) {
        // Eliminar en cascada el estado de los indicadores del objetivo
        GenericIterator<Indicator> gi = listIndicators();
        while(gi.hasNext()) {
            gi.next().removeState(value);
        }
        super.removeState(value);
    }
    
    @Override
    public void removeAllState()
    {
        // Eliminar en cascada los estados asignados a los indicadores del objetivo
        GenericIterator<Indicator> gi = listIndicators();
        while(gi.hasNext()) {
            gi.next().removeAllState();
        }
        super.removeAllState();
    }
    
    @Override
    public int compareTo(Objective anotherObjective)
    {
        int compare = getIndex()>anotherObjective.getIndex()?1:-1;
        return compare;
    }
    
    @Override
    public boolean isValid() {
        return super.isValid() && getTheme().isValid() && getTheme().getPerspective().isValid();
    }
}
