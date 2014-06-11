/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import com.hp.hpl.jena.rdf.model.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.Schedule;
import org.semanticwb.bsc.Seasonable;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Deliverable;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.bsc.tracing.Measure;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.lib.SWBRequest;

/**
 *
 * @author martha.jimenez
 */
public class ReportGeneratorTest {

    final private String modelId = "InfotecPEMP2";
    final private String userGroupId = "Actualizador";
    final private String viewGral = "org.semanticwb.bsc.element.Indicator";
    final private String links = "<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"http://localhost:8080//swbadmin/js/dojo/dijit/themes/soria/soria.css\" /><link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"http://localhost:8080//swbadmin/css/swb_portal.css\" /><link href=\"http://localhost:8080//swbadmin/css/estilo-strategy.css\" rel=\"stylesheet\" type=\"text/css\" />";
    private SWBRequest request = new SWBRequest();
    User user;

    @BeforeClass
    public static void setUpClass() throws Exception {
        SWBPlatform plat = SWBPlatform.createInstance();
        plat.setPersistenceType(SWBPlatform.PRESIST_TYPE_SWBTRIPLESTORE);
        SWBPlatform.getSemanticMgr().initializeDB();

        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/ext/bscom.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    public void setUpSessionUser(BSC model) {
        Iterator it = model.getUserRepository().listUsers();
        UserGroup userGroup = UserGroup.ClassMgr.getUserGroup(userGroupId, model);
        while (it.hasNext()) {
            user = (User) it.next();
            if (user.hasUserGroup(userGroup)) {
                SWBContext.setSessionUser(user);
                break;
            }
        }
    }

    @Test
    public void getReportGenerator() {
        WebSite ws = WebSite.ClassMgr.getWebSite(modelId);
        setUpSessionUser((BSC)ws);
        StringBuilder output = new StringBuilder(256);
        ReportCriteria criteria = this.createCriteria2();
        ArrayList<SemanticObject> results = this.processReport(criteria);
        int itemsCount = 0;
        int titleIndex = -1;

        output.append("<html>");
        output.append("<head>");
        output.append("<style type=\"text/css\">");
        int sizePdf = getSize(criteria.getProps2Show().size());
        output.append("      @page{ size:");
        output.append(sizePdf);
        output.append(".0cm 20.0cm; }\">");
        output.append("</style>");

        output.append(links);
        output.append("</head>");
        output.append("<body>");


        //Comentada para que en formelement.TextField se obtenga el Id desde el objeto semantico
        //request.setAttribute("websiteId", paramRequest.getWebPage().getWebSiteId());

        output.append("<table border=\"1\" style=\"width:100%\" >\n");
        output.append("  <tr>\n");
        for (SemanticProperty prop : criteria.getProps2Show()) {
            output.append("    <th>");
            output.append(prop.getLabel());
            output.append("</th>\n");
            if (prop.getURI().indexOf("title") != -1 && titleIndex == -1) {
                titleIndex = itemsCount;
            }
            itemsCount++;
        }
        output.append("  </tr>\n");

        for (SemanticObject item : results) {
            GenericObject gralItem = item.createGenericInstance();
            boolean sameKind = false;

            if (gralItem.getURI().contains(criteria.getElementType())) {
                sameKind = true;
            }
            itemsCount = 0;
            output.append("  <tr>\n");
            for (SemanticProperty prop : criteria.getProps2Show()) {
                output.append("    <td>\n");
                // Valida si la propiedad es boleana no se renderea el elemento (debido a que no son soportados los elementos de checkbox en la generacion de PDF's).
                // Se envia una etiqueta SI / NO dependiendo del valor boleano
                if (prop.isBoolean()) {
                    boolean value = item.getBooleanProperty(prop);
                    if (value) {
                        output.append("SI");
                    } else {
                        output.append("NO");
                    }
                } else {
                    if (sameKind) {
                        output.append(this.renderPropertyValue(request, item, prop.getURI(), "es"));
                    } else {
                        //de los objetos relacionados solo se va a mostrar el titulo en la tabla
                        if (itemsCount == titleIndex) {
                            output.append(this.renderPropertyValue(request, item, prop.getURI(), "es"));
                        } else {
                            output.append("      &nbsp;\n");
                        }
                    }
                }
                output.append("    </td>\n");
                itemsCount++;
            }
            output.append("  </tr>\n");
        }
        if (results.isEmpty()) {
            output.append("  <tr><td colspan=\"");
            output.append(criteria.getProps2Show().size());
            output.append("\">");
            output.append("Búsqueda sin datos");
            output.append("</td></tr>\n");
        }
        output.append("</table>");
        output.append("</body>");
        output.append("</html>");
        System.out.println("output: " + output.toString());
    }

    private ReportCriteria createCriteria2() {
        ReportCriteria criteria = new ReportCriteria();
        ArrayList<String> related = new ArrayList<String>(4);
        ArrayList<SemanticProperty> properties = new ArrayList<SemanticProperty>(16);

        criteria.setElementType("Objective");
        //criteria.setObjectTitle(request.getParameter(parameterName));
        related.add("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");
        related.add("http://www.semanticwebbuilder.org/swb4/bsc#Initiative");
        related.add("http://www.semanticwebbuilder.org/swb4/bsc#Deliverable");

        SemanticObject periodSem = SemanticObject.createSemanticObject("http://www.InfotecPEMP2.swb#bsc_Period:23");
        GenericObject periodGen = periodSem.createGenericInstance();
        if (periodGen instanceof Period) {
            criteria.setInitialPeriod((Period) periodGen);
        }
        SemanticObject periodSem1 = SemanticObject.createSemanticObject("http://www.InfotecPEMP2.swb#bsc_Period:34");
        GenericObject periodGen1 = periodSem1.createGenericInstance();
        if (periodGen1 instanceof Period) {
            criteria.setFinalPeriod((Period) periodGen1);
        }
//        SemanticObject statusSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
//        GenericObject statusGen = statusSem.createGenericInstance();
//        if (statusGen instanceof State) {
//            criteria.setStatus((State) statusGen);
//        }
//        SemanticObject championSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
//        GenericObject championGen = championSem.createGenericInstance();
//        if (championGen instanceof User) {
//            criteria.setChampion((User) championGen);
//        }
//        SemanticObject sponsorSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
//        GenericObject sponsorGen = sponsorSem.createGenericInstance();
//        if (sponsorGen instanceof User) {
//            criteria.setSponsor((User) sponsorGen);
//        }

        String[] selectedProps = {"http://www.semanticwebbuilder.org/swb4/ontology#description"};
        SemanticOntology ontology = SWBPlatform.getSemanticMgr().getOntology();
        for (String property : selectedProps) {
            SemanticObject propSO = SemanticObject.createSemanticObject(property);
            SemanticProperty prop = ontology.getSemanticProperty(property);
            if (prop == null) {
                prop = propSO.transformToSemanticProperty();
            }
            properties.add(prop);
        }
        criteria.setProps2Show(properties);
        if (!related.isEmpty()) {
            criteria.setRelatedElements(related);
        }

        if (criteria.getProps2Show().isEmpty() || !criteria.getProps2Show().contains(Descriptiveable.swb_title)) {
            criteria.getProps2Show().add(0, Descriptiveable.swb_title);
        }
        return criteria;
    }

    private ArrayList<SemanticObject> processReport(ReportCriteria criteria) {

        SWBModel model = WebSite.ClassMgr.getWebSite(modelId);
        Iterator initialSet = null;

        if (criteria.getElementType() != null) {
            ArrayList<SemanticObject> forNow = new ArrayList<SemanticObject>(128);
            String id = null;
            //se extrae el identificador del uri recibido
            if (criteria.getObjectTitle() != null && criteria.getObjectTitle().lastIndexOf(":") != -1) {
                id = criteria.getObjectTitle().substring(criteria.getObjectTitle().lastIndexOf(":") + 1);
            }
            if (criteria.getElementType().equals(Objective.sclass.getName())) {
                if (criteria.getObjectTitle() != null) {
                    Objective element = Objective.ClassMgr.getObjective(id, model);
                    if (element != null && element.isValid() && user.haveAccess(element)) {
                        forNow.add(element.getSemanticObject());
                    } else if (element == null) {
                        System.out.println("Objective con id: " + id + " --No encontrado");
                    }
                    initialSet = forNow.iterator();
                } else {
                    Iterator<Objective> firstSet = Objective.ClassMgr.listObjectives(model);
                    while (firstSet != null && firstSet.hasNext()) {
                        Objective obj = firstSet.next();
                        if (obj.isValid() && user.haveAccess(obj)) {
                            forNow.add(obj.getSemanticObject());
                        }
                    }
                    initialSet = forNow.iterator();
                }
            } else if (criteria.getElementType().equals(Indicator.sclass.getName())) {
                if (criteria.getObjectTitle() != null) {
                    Indicator element = Indicator.ClassMgr.getIndicator(id, model);
                    if (element != null && element.isValid() && user.haveAccess(element)) {
                        forNow.add(element.getSemanticObject());
                    } else if (element == null) {
                        System.out.println("Indicator con id: " + id + " --No encontrado");
                    }
                    initialSet = forNow.iterator();
                } else {
                    Iterator<Indicator> firstSet = Indicator.ClassMgr.listIndicators(model);
                    while (firstSet != null && firstSet.hasNext()) {
                        Indicator indi = firstSet.next();
                        if (indi.isValid() && user.haveAccess(indi)) {
                            forNow.add(indi.getSemanticObject());
                        }
                    }
                    initialSet = forNow.iterator();
                }
            } else if (criteria.getElementType().equals(Initiative.sclass.getName())) {
                if (criteria.getObjectTitle() != null) {
                    Initiative element = Initiative.ClassMgr.getInitiative(id, model);
                    if (element != null && element.isValid() && user.haveAccess(element)) {
                        forNow.add(element.getSemanticObject());
                    } else if (element == null) {
                        System.out.println("Initiative con id: " + id + " --No encontrado");
                    }
                    initialSet = forNow.iterator();
                } else {
                    Iterator<Initiative> firstSet = Initiative.ClassMgr.listInitiatives(model);
                    while (firstSet != null && firstSet.hasNext()) {
                        Initiative ini = firstSet.next();
                        if (ini.isValid() && user.haveAccess(ini)) {
                            forNow.add(ini.getSemanticObject());
                        }
                    }
                    initialSet = forNow.iterator();
                }
            } else if (criteria.getElementType().equals(Deliverable.sclass.getName())) {
                if (criteria.getObjectTitle() != null) {
                    Deliverable element = Deliverable.ClassMgr.getDeliverable(id, model);
                    if (element != null && element.isValid() && user.haveAccess(element)) {
                        forNow.add(element.getSemanticObject());
                    } else if (element == null) {
                        System.out.println("Deliverable con id: " + id + " --No encontrado");
                    }
                    initialSet = forNow.iterator();
                } else {
                    Iterator<Deliverable> firstSet = Deliverable.ClassMgr.listDeliverables(model);
                    while (firstSet != null && firstSet.hasNext()) {
                        Deliverable deli = firstSet.next();
                        if (deli.isValid() && user.haveAccess(deli)) {
                            forNow.add(deli.getSemanticObject());
                        }
                    }
                    initialSet = forNow.iterator();
                }
            }
        } else {
            ArrayList<SemanticObject> allTypes = new ArrayList<SemanticObject>(256);
            Iterator<Objective> objectives = Objective.ClassMgr.listObjectives(model);
            Iterator<Indicator> indicators = Indicator.ClassMgr.listIndicators(model);
            Iterator<Initiative> initiatives = Initiative.ClassMgr.listInitiatives(model);
            Iterator<Deliverable> deliverables = Deliverable.ClassMgr.listDeliverables(model);
            while (objectives != null && objectives.hasNext()) {
                Objective obj = objectives.next();
                if (obj != null && obj.isValid() && user.haveAccess(obj)) {
                    allTypes.add(obj.getSemanticObject());
                }
            }
            while (indicators != null && indicators.hasNext()) {
                Indicator indi = indicators.next();
                if (indi != null && indi.isValid() && user.haveAccess(indi)) {
                    allTypes.add(indi.getSemanticObject());
                }
            }
            while (initiatives != null && initiatives.hasNext()) {
                Initiative ini = initiatives.next();
                if (ini != null && ini.isValid() && user.haveAccess(ini)) {
                    allTypes.add((ini.getSemanticObject()));
                }
            }
            while (deliverables != null && deliverables.hasNext()) {
                Deliverable deli = deliverables.next();
                if (deli != null && deli.isValid() && user.haveAccess(deli)) {
                    allTypes.add(deli.getSemanticObject());
                }
            }
            initialSet = allTypes.iterator();
        }

        ArrayList<SemanticObject> processed = new ArrayList<SemanticObject>(256);
        int count = 0;

        //el conjunto inicial de objetos, se filtra con los criterios seleccionados por el usuario
        while (initialSet != null && initialSet.hasNext()) {
            GenericObject inTurn = ((SemanticObject) initialSet.next()).createGenericInstance();
            boolean mustBeAdded = false;

            //evaluacion de periodos; si se seleccionaron
            if (criteria.getInitialPeriod() != null && criteria.getFinalPeriod() != null) {
                if (inTurn instanceof Seasonable) {
                    //Sin seleccion de estatus
                    if (criteria.getStatus() == null) {
                        Seasonable seasonable = (Seasonable) inTurn;
                        if (seasonable.hasPeriod(criteria.getInitialPeriod()) && seasonable.hasPeriod(criteria.getFinalPeriod())) {
                            mustBeAdded = true;
                        }
                    } else {
                        //el estatus seleccionado, debe estar asignado en el intervalo indicado
                        Period thisPeriod = criteria.getInitialPeriod();
                        boolean intervalFinished = false;
                        boolean isObjective = false;
                        PeriodStatus periodStatus = null;
                        Objective objective = null;
                        Indicator indicator = null;

                        if (inTurn instanceof Objective) {
                            objective = (Objective) inTurn;
                            isObjective = true;
                        } else if (inTurn instanceof Indicator) {
                            indicator = (Indicator) inTurn;
                        }

                        while (!intervalFinished) {
                            if (isObjective) {
                                periodStatus = objective.getPeriodStatus(thisPeriod);
                            } else {
                                Measure measure = indicator != null && indicator.getStar() != null
                                        ? indicator.getStar().getMeasure(thisPeriod) : null;
                                if (measure != null && measure.getEvaluation() != null) {
                                    periodStatus = measure.getEvaluation();
                                }
                            }
                            //Si los estados de criterios y del periodo evaluado son iguales, se debe agregar el elemento
                            if (periodStatus != null && periodStatus.getStatus().equals(criteria.getStatus())) {
                                mustBeAdded = true;
                                break;
                            }
                            //Se obtiene el siguiente periodo del intervalo definido por el usuario
                            if (thisPeriod.equals(criteria.getFinalPeriod())) {
                                intervalFinished = true;
                            } else if (thisPeriod.getNext() != null) {
                                thisPeriod = (Period) thisPeriod.getNext();
                            } else {
                                //si el siguiente periodo no esta definido, se rompe el ciclo
                                break;
                            }
                        }
                    }
                } else if (inTurn instanceof Schedule) {
                    Schedule schedule = (Schedule) inTurn;
                    Date initial2Compare = schedule.getActualStart() != null ? schedule.getActualStart() : schedule.getPlannedStart();
                    Date final2Compare = schedule.getActualEnd() != null ? schedule.getActualEnd() : schedule.getPlannedEnd();
                    if (initial2Compare != null && final2Compare != null && criteria.getInitialPeriod().getStart().before(initial2Compare)
                            && initial2Compare.before(criteria.getFinalPeriod().getEnd()) && criteria.getInitialPeriod().getStart().before(final2Compare)
                            && final2Compare.before(criteria.getFinalPeriod().getEnd())) {
                        //Sin seleccion de estatus
                        if (criteria.getStatus() == null) {
                            mustBeAdded = true;
                        } else {
                            //el estatus debe estar asignado al entregable
                            if (inTurn instanceof Deliverable) {
                                Deliverable deli = (Deliverable) inTurn;
                                if ((deli.getAutoStatus() != null && deli.getAutoStatus().equals(criteria.getStatus()))
                                        || (deli.getStatusAssigned() != null && deli.getStatusAssigned().equals(criteria.getStatus()))) {
                                    mustBeAdded = true;
                                }
                            }
                        }
                    }
                }
            } else {
                //si los periodos no se indicaron, no es filtro de seleccion
                mustBeAdded = true;
            }
            //Se evalua filtro de Champion
            if (criteria.getChampion() != null && mustBeAdded) {
                mustBeAdded = false;
                if (inTurn instanceof Indicator) {
                    if (((Indicator) inTurn).getChampion().equals(criteria.getChampion())) {
                        mustBeAdded = true;
                    }
                } else {
                    if (inTurn instanceof Objective) {
                        Iterator<Indicator> indicators = ((Objective) inTurn).listIndicators();
                        while (indicators != null && indicators.hasNext()) {
                            if (indicators.next().getChampion().equals(criteria.getChampion())) {
                                mustBeAdded = true;
                                break;
                            }
                        }
                    }
                    //para iniciativas y entregables no se consideran relaciones indirectas para obtener el champion
                }
            } else if (criteria.getChampion() == null && mustBeAdded) {
                mustBeAdded = true;
            }
            //se evalua filtro de Sponsor
            if (criteria.getSponsor() != null && mustBeAdded) {
                mustBeAdded = false;
                if (inTurn instanceof Objective) {
                    if (((Objective) inTurn).getSponsor().equals(criteria.getSponsor())) {
                        mustBeAdded = true;
                    }
                } else {
                    if (inTurn instanceof Indicator) {
                        if (((Indicator) inTurn).getObjective().getSponsor().equals(criteria.getSponsor())) {
                            mustBeAdded = true;
                        }
                    } else if (inTurn instanceof Initiative) {
                        if (((Initiative) inTurn).getInitiativeAssignable() != null
                                && ((Initiative) inTurn).getInitiativeAssignable() instanceof Indicator) {
                            Indicator indi = (Indicator) ((Initiative) inTurn).getInitiativeAssignable();
                            if (indi.getObjective().getSponsor().equals(criteria.getSponsor())) {
                                mustBeAdded = true;
                            }
                        }
                    } else if (inTurn instanceof Deliverable) {
                        Deliverable deli = (Deliverable) inTurn;
//                        if (deli.getInitiative() != null && deli.getInitiative() instanceof Initiative) {
//                            Initiative ini = (Initiative) deli.getInitiative();
//                            if (ini.getInitiativeAssignable() != null
//                                    && ini.getInitiativeAssignable() instanceof Indicator) {
//                                Indicator indi = (Indicator) ini.getInitiativeAssignable();
//                                if (indi.getObjective().getSponsor().equals(criteria.getSponsor())) {
//                                    mustBeAdded = true;
//                                }
//                            }
//                        }
                    }
                }
            } else if (criteria.getSponsor() == null && mustBeAdded) {
                mustBeAdded = true;
            }

            if (mustBeAdded) {
                processed.add(inTurn.getSemanticObject());
                if (criteria.getRelatedElements() != null) {
                    addRelated(processed, inTurn, criteria.getRelatedElements());
                }
            }
            count++;
        }

        return processed;
    }
    
        private int getSize(int columns) {
        int value = 30;
        if (columns > 5) {
            int tmp = columns - 5;
            int tmp1 = tmp / 3;
            if (tmp % 3 > 0) {
                tmp1++;
            }
            value = value + (tmp1 * 10);
        }
        return value;
    }
    private void addRelated(ArrayList<SemanticObject> resultSet, GenericObject element, ArrayList<String> relatedTypes) {

        ArrayList<SemanticObject> additional = new ArrayList<SemanticObject>(64);
        if (element instanceof Objective) {
            Objective obj = (Objective) element;
            for (String type : relatedTypes) {
                if (type.endsWith(Indicator.bsc_Indicator.getName())
                        || type.endsWith(Initiative.bsc_Initiative.getName())
                        || type.endsWith(Deliverable.bsc_Deliverable.getName())) {
                    Iterator<Indicator> it = obj.listIndicators();
                    while (it != null && it.hasNext()) {
                        Indicator indi = it.next();
                        if (type.endsWith(Indicator.bsc_Indicator.getName())) {
                            additional.add(indi.getSemanticObject());
                        } else { //si se pidieron las iniciativas o entregables
                            Iterator<Initiative> itInitiatives = indi.listInitiatives();
                            while (itInitiatives != null && itInitiatives.hasNext()) {
                                Initiative initiative = itInitiatives.next();
                                if (type.endsWith(Initiative.bsc_Initiative.getName())) {
                                    additional.add(initiative.getSemanticObject());
                                } else {//si se pidieron los entregables
                                    Iterator<Deliverable> itDeliverables = initiative.listDeliverables();
                                    while (itDeliverables != null && itDeliverables.hasNext()) {
                                        additional.add(itDeliverables.next().getSemanticObject());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (element instanceof Indicator) {
            Indicator indi = (Indicator) element;
            for (String type : relatedTypes) {
                if (type.endsWith(Objective.bsc_Objective.getName())) {
                    additional.add(indi.getObjective().getSemanticObject());
                } else if (type.endsWith(Initiative.bsc_Initiative.getName())) {
                    //Se obtiene el listado de iniciativas
                    Iterator<Initiative> it = indi.listInitiatives();
                    while (it != null && it.hasNext()) {
                        additional.add(it.next().getSemanticObject());
                    }
                } else if (type.endsWith(Deliverable.bsc_Deliverable.getName())) {
                    //Se obtiene el listado de iniciativas
                    Iterator<Initiative> it = indi.listInitiatives();
                    while (it != null && it.hasNext()) {
                        Initiative ini = it.next();
                        //Se obtiene el listado de entregables de cada iniciativa
                        Iterator<Deliverable> itDeli = ini.listDeliverables();
                        while (itDeli != null && itDeli.hasNext()) {
                            additional.add(itDeli.next().getSemanticObject());
                        }
                    }
                }
            }
        } else if (element instanceof Initiative) {
            Initiative ini = (Initiative) element;
            for (String type : relatedTypes) {
                if (type.endsWith(Objective.bsc_Objective.getName())
                        && ini.getInitiativeAssignable() != null && ini.getInitiativeAssignable() instanceof Indicator) {
                    additional.add(((Indicator) ini.getInitiativeAssignable()).getObjective().getSemanticObject());
                } else if (type.endsWith(Indicator.bsc_Indicator.getName())
                        && ini.getInitiativeAssignable() != null && ini.getInitiativeAssignable() instanceof Indicator) {
                    additional.add(((Indicator) ini.getInitiativeAssignable()).getSemanticObject());
                } else if (type.endsWith(Deliverable.bsc_Deliverable.getName())) {
                    Iterator<Deliverable> it = ini.listDeliverables();
                    while (it != null && it.hasNext()) {
                        additional.add(it.next().getSemanticObject());
                    }
                }
            }
        } else if (element instanceof Deliverable) {
            Deliverable deli = (Deliverable) element;
            for (String type : relatedTypes) {
                if (type.endsWith(Objective.bsc_Objective.getName())) {
//                    if (deli.getInitiative() != null && deli.getInitiative() instanceof Initiative) {
//                        //Se hace la comparacion debido a la creacion de la interface DeliverableAssignable
//                        Initiative ini = (Initiative) deli.getInitiative();
//                        if (ini.getInitiativeAssignable() != null
//                                && ini.getInitiativeAssignable() instanceof Indicator) {
//                            //se obtiene el objetivo del entregable a partir de la iniciativa
//                            Indicator indi = (Indicator) ini.getInitiativeAssignable();
//                            additional.add(indi.getObjective().getSemanticObject());
//                        }
//                    }
                } else if (type.endsWith(Indicator.bsc_Indicator.getName())) {
//                    if (deli.getInitiative() != null && deli.getInitiative() instanceof Initiative) {
//                        Initiative ini = (Initiative) deli.getInitiative();
//                        if (ini.getInitiativeAssignable() != null
//                                && ini.getInitiativeAssignable() instanceof Indicator) {
//                            //se obtiene el indicador a partir de la iniciativa
//                            additional.add(((Indicator) ini.getInitiativeAssignable()).getSemanticObject());
//                        }
//                    }
                } 
//                else if (type.endsWith(Initiative.bsc_Initiative.getName()) && deli.getInitiative() != null) {
//                    additional.add(deli.getInitiative().getSemanticObject());
//                }
            }
        }
        //se agregan los elementos seleccionados a resultSet
        for (SemanticObject addition : additional) {
            resultSet.add(addition);
        }
    }
    private String renderPropertyValue(HttpServletRequest request, SemanticObject elementBSC,
            String propUri, String lang) {
        request.setAttribute("suri", elementBSC.getEncodedURI());
        String ret = null;
        SWBFormMgr formMgr = new SWBFormMgr(elementBSC, null, SWBFormMgr.MODE_VIEW);
        SemanticProperty semProp = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(propUri);

        //Codigo para obtener el displayElement
        Statement st = semProp.getRDFProperty().getProperty(
                SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(
                "http://www.semanticwebbuilder.org/swb4/bsc#displayElement"));
        if (st != null) {
            //Se obtiene: SemanticProperty: displayElement de la propiedad en cuestion (prop)
            SemanticObject soDisplayElement = SemanticObject.createSemanticObject(st.getResource());
            if (soDisplayElement != null) {
                SemanticObject formElement = soDisplayElement.getObjectProperty(
                        org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(
                        "http://www.semanticwebbuilder.org/swb4/xforms/ontology#formElement"));
                if (formElement != null) {
                    FormElement fe = (FormElement) formElement.createGenericInstance();

                    if (fe != null) {
                        if (formMgr.getSemanticObject() != null) {
                            fe.setModel(formMgr.getSemanticObject().getModel());
                        }
                        request.setAttribute("pdf", "true");
                        ret = fe.renderElement(request, elementBSC, semProp, semProp.getName(),
                                SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_VIEW, lang);
                    }
                }
            }
        }

        if (ret == null) {
            FormElement formElement = formMgr.getFormElement(semProp);
            if (formElement != null) {
                ret = formElement.renderElement(request, elementBSC, semProp, semProp.getName(),
                        SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_VIEW, lang);
            }
        }
        return ret != null ? ret : "";
    }

}
