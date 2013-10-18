package org.semanticwb.bsc.formelement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


/**
 * Utiliza una máscara de captura en forma de calendario, a fin de almacenar fechas.
 * La fecha seleccionada se valida para que dos períodos no se traslapen. 
 * @author jose.jimenez
 */
public class Periodicity extends org.semanticwb.bsc.formelement.base.PeriodicityBase {
    
    
    /**
     * Realiza operaciones de escritura a la bitacora.
     */
    private static Logger log = SWBUtils.getLogger(Periodicity.class);
    
    /**
     * Establecel el formato en que se utilizaran las fechas
     */
    private static final String formatPattern = "yyyy-MM-dd";
    
    /**
     * Aplica el formato establecido por {@code formatPattern} 
     */
    private static SimpleDateFormat format = new SimpleDateFormat(formatPattern);
    
    /**
     * Crea una instancia de esta clase
     * @param base 
     */
    public Periodicity(org.semanticwb.platform.SemanticObject base) {
        
        super(base);
    }
    
    /**
     * Genera el codigo HTML para representar el elemento de forma para la captura de la fecha correspondiente
     * @param request la peticion HTTP enviada por el cliente
     * @param obj el objeto semantico al que pertenece la propiedad asociada a este FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar
     * @param mode el modo en que debe presentarse el tipo de despliegue
     * @param lang el lenguaje a utilizar para la interface
     * @return un {@code String} que representa el codigo HTML para el elemento de forma correspondiente
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {

        if (obj == null) {
            obj = new SemanticObject();
        }

        boolean dojo = false;

        if (type.equals("dojo")) {
            dojo = true;
        }

        StringBuilder ret = new StringBuilder(128);
        String name = propName;
        String label = prop.getDisplayName(lang);
        SemanticObject sobj = prop.getDisplayProperty();
        boolean required = prop.isRequired();
        String pmsg = null;
        String imsg = null;
        boolean disabled = false;

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
            pmsg = dobj.getPromptMessage();
            imsg = dobj.getInvalidMessage();
            disabled = dobj.isDisabled();
        }

        if (dojo) {
            if (imsg == null) {
                if (required) {
                    imsg = label + " es requerido.";
                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
                    imsg = "Formato incorrecto o la fecha ya forma parte de otro periodo.";
                    if (lang.equals("en")) {
                        imsg = "Invalid Format or this date is already part of another period.";
                    }
                }
            }
            if (pmsg == null) {
                pmsg = "Captura " + label + ".";
                if (lang.equals("en")) {
                    pmsg = "Enter " + label + ".";
                }
            }
        }

        String ext = "";
        if (disabled) {
            ext += " disabled=\"disabled\"";
        }

        String value = request.getParameter(propName);
        if (value == null) {
            Date dt = obj.getDateProperty(prop);
            if (dt != null) {
                value = format.format(dt);
            }
        }
        if (value == null) {
            value = "";
        }

        if (type.equals("dojo")) {
            setAttribute("isValid",
                    "return validateElement('" + propName + "','" + getValidateURL(obj, prop)
                    + "',this.textbox.value);");
        } else {
            setAttribute("isValid", null);
        }
        
        if (mode.equals("edit") || mode.equals("create")) {
            ret.append("<input name=\"" + name + "\" value=\"" + value + "\"");

            if (dojo) {
                String elementId = (mode.equals("edit") ? obj.getId() : "1");
                ret.append(" dojoType=\"dijit.form.DateTextBox\"");
                ret.append(" required=\"" + required + "\"");
                ret.append(" promptMessage=\"" + pmsg + "\"");
                ret.append(" invalidMessage=\"" + imsg + "\"");
                if (getConstraints() != null) {
                    ret.append(" constraints=\"" + getConstraints() + "\"");
                }
                if (getDateId() != null) {
                    ret.append(" id=\"" + getDateId() + elementId + "\"");
                }
                if (getDateOnChange() != null) {
                    String attributeValue = getDateOnChange();
                    ret.append(" onchange=\"");
                    ret.append((attributeValue.indexOf("{replaceId}") != -1 
                                ? attributeValue.replace("{replaceId}", elementId)
                                : attributeValue));
                    ret.append("\"");
                }
            }

            ret.append(" " + getAttributes());

            if (dojo) {
                ret.append(" trim=\"true\"");
            }

            ret.append(ext);
            ret.append("/>");
System.out.println("\n\nfecha:\n"+ret);
        } else if (mode.equals("view")) {
            ret.append("<span name=\"" + name + "\">" + value + "</span>");
        }
        return ret.toString();
    }
    
    /**
     * Realiza validaciones al dato capturado por el usuario
     * @param request la petición HTTP enviada por el cliente
     * @param obj el objeto semantico al que pertenece la propiedad relacionada a este FormElement
     * @param prop la propiedad relacionada a este FormElement
     * @param propName el nombre de la propiedad relacionada a este FormElement
     * @throws FormValidateException generada cuando el valor proporcionado por el usuario no es valido
     */
    @Override 
    public void validate(HttpServletRequest request, SemanticObject obj, 
            SemanticProperty prop, String propName) throws FormValidateException {
        
        String value = request.getParameter(propName);
        Date date = null;

        try {
            date = format.parse(value);
        } catch (ParseException pe) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date = dateFormat.parse(value);
            } catch (ParseException p) {
                throw new FormValidateException("El valor es incorrecto");
            }
        }

        SWBModel model = (SWBModel) obj.getModel().getModelObject().createGenericInstance();
        Period current = (Period)obj.createGenericInstance();
        Iterator<Period> iperiods = Period.ClassMgr.listPeriods(model);
        while (iperiods.hasNext()) {
            Period p = iperiods.next();
            if (current.equals(p) || p.getStart() == null || p.getEnd() == null) {
                continue;
            }
            
            Date fromDate = null;
            Date toDate = null;
            try {
                fromDate = format.parse(p.getStart().toString());
                toDate = format.parse(p.getEnd().toString());
            } catch (ParseException pe) {
                fromDate = p.getStart();
                toDate = p.getEnd();
            }            
            if(  fromDate.getTime()<=date.getTime() && toDate.getTime()>=date.getTime()  ) {
               throw new FormValidateException("Esta fecha ya forma parte de otro periodo");
            }
            
//            if ((fromDate.before(date) || fromDate.equals(date)) &&
//                    (toDate.after(date) || toDate.equals(date))) {
//                throw new FormValidateException("Esta fecha ya forma parte de otro periodo");
//            }
        }
    }
    
    /**
     * Realiza el almacenamiento del dato capturado por el usuario, en la propiedad y objeto indicados 
     * @param request la peticion HTTP enviada por el cliente
     * @param obj el objeto semantico al que pertenece la propiedad relacionada a este FormElement
     * @param prop la propiedad relacionada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     */
    @Override
    public void process(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName) {
        
        String value = request.getParameter(propName);
        Date fvalue = null;
        try {
            fvalue = format.parse(value);
            obj.setDateProperty(prop, fvalue);
        } catch (Exception e) {
            try {
                SimpleDateFormat otherFormat = new SimpleDateFormat("dd/MM/yyyy");
                fvalue = otherFormat.parse(value);
                obj.setDateProperty(prop, fvalue);
            } catch (Exception p) {
            }
        }
    }
    
    /**
     * Obtiene el valor de la propiedad {@code constraints}
     * @return el valor almacenado en la propiedad {@code constraints}
     */
    @Override
    public String getConstraints() {
        
        String ret = super.getConstraints();

        if (ret != null) {
            ret = SWBUtils.TEXT.replaceAll(ret, "{today}", 
                    SWBUtils.TEXT.getStrDate(new Date(), "es", "yyyy-mm-dd"));    // 2006-12-31
        }        return ret;
    }
}
