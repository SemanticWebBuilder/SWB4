/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.resources.projectdriver;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;


// TODO: Auto-generated Javadoc
/**
 * The Class Activity.
 */
public class Activity extends org.semanticwb.portal.resources.projectdriver.base.ActivityBase 
{
    
    /**
     * Instantiates a new activity.
     * 
     * @param base the base
     */
    public Activity(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.ActivityBase#getStartDate()
     */
    @Override
    public Date getStartDate() {
        Activity act=(Activity)getSemanticObject().createGenericInstance();
        boolean valid=true;
        Date startDate =calculateDates(act,"startDate");
        if(hasChildPage(act))
            valid=false;
        if(valid)
            return super.getStartDate();
        else
            return startDate;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.ActivityBase#getEndDate()
     */
    @Override
    public Date getEndDate() {
        Activity act=(Activity)getSemanticObject().createGenericInstance();
        boolean valid=true;
        Date endDate =calculateDates(act,"endDate");
        if(hasChildPage(act))
            valid=false;
        if(valid)
            return super.getEndDate();
        else
            return endDate;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.ActivityBase#getCurrentPercentage()
     */
    @Override
    public float getCurrentPercentage() {
        Activity act=(Activity)getSemanticObject().createGenericInstance();
        ArrayList listActs=listActivities(act);
        boolean valid=true;
        int hour=0;
        float addHour=0,aux=0,partialHour=0,currentPercentage=0;
        Iterator it=listActs.iterator();
        if(it.hasNext())
            valid=false;
        while(it.hasNext()){
            Activity actsx = (Activity)it.next();
            hour = actsx.getPlannedHour();
            addHour += hour;
            aux = actsx.getCurrentPercentage();
            if(aux > 1)
               aux /= 100;
            partialHour += aux * hour;
        }
        currentPercentage = partialHour / addHour * 100;
        if(Float.isNaN(currentPercentage))
             currentPercentage=0;
        if(valid)
            return super.getCurrentPercentage();
        else
            return currentPercentage;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.ActivityBase#getCurrentHour()
     */
    @Override
    public int getCurrentHour() {
        boolean valid = true;
        int currentHour=0;
        Activity act=(Activity)getSemanticObject().createGenericInstance();
        ArrayList listActs=listActivities(act);
        Iterator it=listActs.iterator();
        if(it.hasNext())
            valid=false;
        while(it.hasNext()){
            Activity actsx = (Activity)it.next();
            currentHour = currentHour + actsx.getCurrentHour();
        }
        if(valid)
            return super.getCurrentHour();
        else
            return currentHour;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.resources.projectdriver.base.ActivityBase#getPlannedHour()
     */
    @Override
    public int getPlannedHour() {
        boolean valid=true;
        int plannedHour =0;
        Activity act=(Activity)getSemanticObject().createGenericInstance();
        ArrayList listActs=listActivities(act);
        Iterator it=listActs.iterator();
        if(it.hasNext())
            valid=false;
        while(it.hasNext()){
            Activity actsx = (Activity)it.next();
            plannedHour = plannedHour + actsx.getPlannedHour();
        }
        if(valid)
            return super.getPlannedHour();
        else{
            act.removeResponsible();
            return plannedHour;
        }
    }

    /**
     * List activities.
     * 
     * @param act the act
     * @return the array list
     */
    private ArrayList listActivities(Activity act){
        ArrayList listActs=getActivitiesByPage(act, act.getWebSite());
        Iterator it = listActs.iterator();
        it = listActs.iterator();
        listActs=new ArrayList();
        while(it.hasNext()){
            WebPage wp = (WebPage)it.next();
            if(wp.isActive()&&parentActive(wp)&&!hasChildPage(wp)){
                listActs.add(wp);
            }
        }
        return listActs;
    }

    /**
     * Gets the activities by page.
     * 
     * @param act the act
     * @param model the model
     * @return the activities by page
     */
    private ArrayList getActivitiesByPage(Activity act,WebSite model){
          ArrayList containActs = new ArrayList();
          Iterator contAct= Activity.ClassMgr.listActivities(model);
           while(contAct.hasNext()){
                 Activity activ=(Activity)contAct.next();
                 if(activ.isChildof(act))
                     containActs.add(activ);
           }
          return containActs;
    }
    
    /**
     * Parent active.
     * 
     * @param wp the wp
     * @return true, if successful
     */
    private boolean parentActive(WebPage wp){
        WebPage parent=wp.getParent();
        boolean haveParent=true;
        while(parent!=null){
            if(!parent.isActive())
               haveParent=false;
            parent=parent.getParent();
        }
        return haveParent;
    }

    /**
     * Checks for child page.
     * 
     * @param webpage the webpage
     * @return true, if successful
     */
    private boolean hasChildPage(WebPage webpage)
    {
        boolean result = false;
        ArrayList checks=new ArrayList();
        Iterator childs = webpage.listChilds();

        while(childs.hasNext()){
            WebPage child = (WebPage)childs.next();
            if(child != null && child.isVisible() && child.isActive() && !child.isHidden() && child.isValid() && !child.isDeleted())
                checks.add(true);
            else
                checks.add(false);
        }
        if(checks.contains(true))
            result = true;
       return result;
    }
    
    /**
     * Calculate dates.
     * 
     * @param act the act
     * @param valueDate the value date
     * @return the date
     */
    private Date calculateDates(Activity act,String valueDate){
            ArrayList validAct = listActivities(act);
            Iterator it;
            boolean first=true;
            long number2=0,number1=0,value=0;
            Date date = null;
            if(valueDate.equals("startDate")){
                //valida si obtiene la fecha de inicio
                boolean validStart=true;
                Iterator check = validAct.iterator();
                HashMap startDate = new HashMap();

                if(check.hasNext()){
                    while(check.hasNext()){
                        Activity cont = (Activity)check.next();
                        if(cont.getStatus()==null)
                            cont.setStatus("unassigned");
                        if(cont.getStatus().equals("paused")||cont.getStatus().equals("canceled")||cont.getStatus().equals("ended")||cont.getStatus().equals("develop")){
                                if(cont.getStartDate()!=null)
                                    startDate.put(cont.getStartDate().getTime(), cont.getStartDate().toString());
                        }
                    }
                }else
                    validStart=false;
                if(startDate.isEmpty())
                    validStart = false;
              if(validStart){
                 it = startDate.entrySet().iterator();
                first = true;
                while (it.hasNext()) {
                    Map.Entry e = (Map.Entry)it.next();
                    number1 = Long.parseLong(e.getKey().toString());
                    if(it.hasNext()&&first){
                        Map.Entry e1 = (Map.Entry)it.next();
                        number2 = Long.parseLong(e1.getKey().toString());
                        first=false;
                        if(number1<number2)
                            value=number1;
                        else
                            value=number2;
                    }else{
                        if(value!=0){
                            if(value>number1)
                                value=number1;
                        }else
                            value=number1;
                    }
                }
                 date = new Timestamp(value);
              }
            }else if(valueDate.equals("endDate")){
                //valida si obtiene la fecha de finalizacion
                Iterator listAct = validAct.iterator();
                boolean validEnd=true;
                HashMap endDate=new HashMap();
                while(listAct.hasNext()){
                    Activity acts = (Activity)listAct.next();
                    if(acts.getStatus()==null)
                        acts.setStatus("unassigned");
                    if(acts.getStatus().equals("assigned")||acts.getStatus().equals("unassigned")||acts.getStatus().equals("develop")||acts.getStatus().equals("paused")||acts.getEndDate()==null){
                        validEnd=false;
                    }else{
                         if(acts.getEndDate()!=null)
                                endDate.put(acts.getEndDate().getTime(), acts.getEndDate().toString());
                    }
                }
                //Obtiene la fecha de termino de todas las actividades
                if(validEnd){
                    it = endDate.entrySet().iterator();
                    first = true;
                    value=number1=number2=0;
                    while(it.hasNext()){
                        Map.Entry e = (Map.Entry)it.next();
                        number1 = Long.parseLong(e.getKey().toString());
                        if(it.hasNext()&&first){
                            Map.Entry e1 = (Map.Entry)it.next();
                            number2 = Long.parseLong(e1.getKey().toString());
                            first=false;
                            if(number1>number2)
                                value=number1;
                            else
                                value=number2;
                        }else{
                            if(value!=0){
                                if(value<number1)
                                    value=number1;
                            }else
                                value=number1;
                        }
                    }
                    date = new Timestamp(value);
                }
            }
            return date;
        }


}
