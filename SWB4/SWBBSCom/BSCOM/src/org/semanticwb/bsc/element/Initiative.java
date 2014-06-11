package org.semanticwb.bsc.element;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import static org.semanticwb.bsc.element.Deliverable.names;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


public class Initiative extends org.semanticwb.bsc.element.base.InitiativeBase 
{
    static
    {
        bsc_hasDeliverable.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                if("ADD".equalsIgnoreCase(action)) {
                    SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
                    Initiative initiative = (Initiative)obj.createGenericInstance();
                    List<State> states = initiative.listValidStates();
                    Deliverable deliverable = initiative.getLastDeliverable();
                    if(!states.isEmpty() && deliverable!=null)
                    {
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
                            deliverable.addSeries(series);
                        }
                    }
                }
            }
        });
    }
    
    public Initiative(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public Deliverable getLastDeliverable() {
        return getDeliverable();
    }

    @Override
    public synchronized Deliverable getDeliverable() {
        Iterator<Deliverable> it = SWBComparator.sortByCreated(listDeliverables(), false);
        return it.hasNext()?it.next():null;
    }
    
    @Override
    public Iterator<Period> listAvailablePeriods() {
        return getBSC().listPeriods();
    }
    
    @Override
    public Iterator<Period> listAvailablePeriods(boolean ascendent) {
        return getBSC().listPeriods(ascendent);
    }
    
    public List<Deliverable> listValidDeliverables() {
        List<Deliverable> validDeliverables = SWBUtils.Collections.filterIterator(super.listDeliverables(), new GenericFilterRule<Deliverable>() {
                                                        @Override
                                                        public boolean filter(Deliverable d) {
                                                            User user = SWBContext.getSessionUser(getBSC().getUserRepository().getId());
                                                            if(user==null) {
                                                                user = SWBContext.getAdminUser();
                                                            }
                                                            return !d.isValid() || !user.haveAccess(d);
                                                        }            
                                                    });
        return validDeliverables;
    }
    
    public List<State> listValidStates()
    {
        List<State> validStates = SWBUtils.Collections.filterIterator(super.listStates(), new GenericFilterRule<State>() {
                                                        @Override
                                                        public boolean filter(State s) {
                                                            if(s==null) {
                                                                return true;
                                                            }
                                                            User user = SWBContext.getSessionUser(getBSC().getUserRepository().getId());
                                                            if(user==null) {
                                                                user = SWBContext.getAdminUser();
                                                            }
                                                            return !s.isValid() || !user.haveAccess(s);
                                                        }            
                                                    });
        return validStates;
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
        List<State> states = sortStates(false);
        try {
            return states.get(0);
        }catch(IndexOutOfBoundsException e) {
        
        }
        return null;
    }
    
    @Override
    public PeriodStatus getPeriodStatus(Period period) {
        Iterator<PeriodStatus> appraisals = listPeriodStatuses();
        PeriodStatus appraisal;
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
    
    public boolean updateAppraisal(Period period)
    {
System.out.println("\n\nIniciativa.updateappraisal()....");
System.out.println("period="+period.getTitle());
        boolean res = Boolean.FALSE;
        
        // Determinar el estatus del período especificado
        State status;
        PeriodStatus appraisal = getPeriodStatus(period);
        if(appraisal==null) {
System.out.println("if");
            appraisal = PeriodStatus.ClassMgr.createPeriodStatus(getBSC());
            appraisal.setPeriod(period);
            addPeriodStatus(appraisal);
            status = getMaximumState();
        }else {
System.out.println("else");
            status = appraisal.getStatus();
        }
System.out.println("status="+status.getTitle());
        
        // Determinar el estatus de la iniciativa
        List<Deliverable> deliverables = listValidDeliverables();
        for(Deliverable deliverable:deliverables) {
System.out.println("deliverable="+deliverable);
            Series star = deliverable.getStar();
System.out.println("star="+star);
            if(star==null || star.getMeasure(period)==null || star.getMeasure(period).getEvaluation().getStatus()==null) {
                continue;
            }
            if( star.getMeasure(period).getEvaluation().getStatus().compareTo(status)<0 ) {
                status = star.getMeasure(period).getEvaluation().getStatus();
                res = Boolean.TRUE;
            }
        }
System.out.println("status="+status.getTitle());
        appraisal.setStatus(status);
        
        // Calcular el porcentaje de avance
        float m;
        float weighingSum = 0;
        float xwSum = 0;        
        for(Deliverable deliverable:deliverables) {
            if(deliverable.getPriority()==null) {
                continue;
            }
            xwSum += deliverable.getProgress()*deliverable.getPriority().getWeighing();
            weighingSum += deliverable.getPriority().getWeighing();
        }
        m = xwSum/weighingSum;
        setPercentageProgress(m);
        
        return res;
    }
    
    private List<State> sortStates() {
        return sortStates(true);
    }
    
    private List<State> sortStates(boolean ascendent) {
        List<State> states = listValidStates();
        if(ascendent) {
            Collections.sort(states);
        }else {            
            Collections.sort(states, Collections.reverseOrder());
        }
        return states;
    }
}
