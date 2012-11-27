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
package org.semanticwb.xforms.drop;


import java.util.*;
// TODO: Auto-generated Javadoc

/**
 * The Class RDFElement.
 * 
 * @author  jorge.jimenez
 */
public class RDFElement {
    
    /** The value. */
    protected String value=null;
    
    /** The size. */
    protected int size=-1;
    
    /** The maxlength. */
    protected int maxlength=-1;
    
    /** The accesskey. */
    protected String accesskey=null;
    
    /** The align. */
    protected String align=null;
    
    /** The isdisabled. */
    protected boolean isdisabled=false;
    
    /** The isreadonly. */
    protected boolean isreadonly=false;
    
    /** The isautocomplete. */
    protected boolean isautocomplete=true;
    
    /** The width. */
    protected int width=-1;
    
    /** The height. */
    protected int height=-1;
    
    /** The alert. */
    protected String alert=null;
    
    /** The hint. */
    protected String hint=null;
    
    /** The type. */
    protected String type;
    
    /** The stype. */
    protected String stype;
    
    /** The id. */
    protected String id;
    
    /** The label. */
    protected String label;
    
    /** The required. */
    protected boolean required=false;
    
    /** The constraint. */
    protected String constraint=null;
    
    /** The help. */
    protected String help=null;
    
    //TextArea
    /** The incremental. */
    private boolean incremental=false;
    
    /** The cols. */
    private int cols=-1;
    
    /** The rows. */
    private int rows=-1;
    
    /** The wrap. */
    private String wrap=null;
    
    /** The mediatype. */
    private String mediatype=null;
    
    //group
    /** The appearance. */
    private String appearance=null;
    
    //upload
    /** The filename. */
    private String filename=null;
    
    //case-swith
    /** The actioncase. */
    private String actioncase=null;
    
    /** The selected. */
    private boolean selected=false;
    
    /** The cssclass. */
    private String cssclass=null;
    
    //Range
    /** The start. */
    protected int start=0;
    
    /** The end. */
    protected int end=0;
    
    /** The step. */
    protected int step=0;
    
    //StaticText
    /** The is inhead. */
    protected boolean isInhead=false;
    
    
    //Para agregar elementos dentro de un elemento-contenedor (ej. group)
    /** The elements. */
    private ArrayList elements=new ArrayList();
    
    //sets
    
    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value) {
        this.value=value;
    }
    
    /**
     * Sets the type.
     * 
     * @param type the new type
     */
    public void setType(String type) {
        this.type=type;
    }
    
    /**
     * Sets the s type.
     * 
     * @param stype the new s type
     */
    public void setSType(String stype) {
        this.stype=stype;
    }
    
    /**
     * Sets the id.
     * 
     * @param id the new id
     */
    public void setId(String id) {
        this.id=id;
    }
    
    /**
     * Sets the label.
     * 
     * @param label the new label
     */
    public void setLabel(String label) {
        this.label=label;
    }
    
    /**
     * Sets the size.
     * 
     * @param size the new size
     */
    public void setSize(int size) {
        this.size=size;
    }
    
    /**
     * Sets the max length.
     * 
     * @param maxlength the new max length
     */
    public void setMaxLength(int maxlength) {
        this.maxlength=maxlength;
    }
    
    /**
     * Sets the alert.
     * 
     * @param alert the new alert
     */
    public void setAlert(String alert) {
        this.alert=alert;
    }
    
    /**
     * Sets the hint.
     * 
     * @param hint the new hint
     */
    public void setHint(String hint) {
        this.hint=hint;
    }
    
    /**
     * Sets the required.
     * 
     * @param required the new required
     */
    public void setRequired(boolean required) {
        this.required=required;
    }
    
    /**
     * Sets the constraint.
     * 
     * @param constraint the new constraint
     */
    public void setConstraint(String constraint) {
        this.constraint=constraint;
    }
    
    /**
     * Sets the help.
     * 
     * @param help the new help
     */
    public void setHelp(String help) {
        this.help=help;
    }
    
    /**
     * Sets the incremental.
     * 
     * @param incremental the new incremental
     */
    public void setIncremental(boolean incremental) {
        this.incremental=incremental;
    }
    
    /**
     * Sets the cols.
     * 
     * @param cols the new cols
     */
    public void setCols(int cols) {
        this.cols=cols;
    }
    
    /**
     * Sets the rows.
     * 
     * @param rows the new rows
     */
    public void setRows(int rows) {
        this.rows=rows;
    }
    
    /**
     * Sets the wrap.
     * 
     * @param wrap the new wrap
     */
    public void setWrap(String wrap) {
        this.wrap=wrap;
    }
    
    /**
     * Sets the mediatype.
     * 
     * @param mediatype the new mediatype
     */
    public void setMediatype(String mediatype) {
        this.mediatype=mediatype;
    }
    
    /**
     * Sets the appearance.
     * 
     * @param appearance the new appearance
     */
    public void setAppearance(String appearance) {
        this.appearance=appearance;
    }
    
    /**
     * Adds the element.
     * 
     * @param element the element
     */
    public void addElement(Object element) {
        elements.add(element);
    }
    
    /**
     * Sets the file name.
     * 
     * @param filename the new file name
     */
    public void setFileName(String filename) {
        this.filename=filename;
    }
    
    /**
     * Sets the action case.
     * 
     * @param actioncase the new action case
     */
    public void setActionCase(String actioncase) {
        this.actioncase=actioncase;
    }
    
    /**
     * Sets the selected.
     * 
     * @param selected the new selected
     */
    public void setSelected(boolean selected) {
        this.selected=selected;
    }
    
    /**
     * Sets the css class.
     * 
     * @param cssclass the new css class
     */
    public void setCssClass(String cssclass) {
        this.cssclass=cssclass;
    }
    
    /**
     * Sets the start.
     * 
     * @param start the new start
     */
    public void setStart(int start){
        this.start=start;
    }
    
    /**
     * Sets the end.
     * 
     * @param end the new end
     */
    public void setEnd(int end){
        this.end=end;
    }
    
    /**
     * Sets the step.
     * 
     * @param step the new step
     */
    public void setStep(int step){
        this.step=step;
    }
    
    /**
     * Sets the checks if is inhead.
     * 
     * @param isInhead the new checks if is inhead
     */
    public void setisInhead(boolean isInhead){
        this.isInhead=isInhead;
    }
    
    //gets
    
    /**
     * Gets the type.
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }
    
    /**
     * Gets the s type.
     * 
     * @return the s type
     */
    public String getSType() {
        return stype;
    }
    
    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }
    
    /**
     * Gets the label.
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }
    
    /**
     * Gets the size.
     * 
     * @return the size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Gets the max length.
     * 
     * @return the max length
     */
    public int getMaxLength() {
        return maxlength;
    }
    
    /**
     * Gets the alert.
     * 
     * @return the alert
     */
    public String getAlert() {
        return alert;
    }
    
    /**
     * Gets the hint.
     * 
     * @return the hint
     */
    public String getHint() {
        return hint;
    }
    
    /**
     * Checks if is required.
     * 
     * @return true, if is required
     */
    public boolean isRequired() {
        return required;
    }
    
    /**
     * Gets the constraint.
     * 
     * @return the constraint
     */
    public String getConstraint() {
        return constraint;
    }
    
    /**
     * Gets the help.
     * 
     * @return the help
     */
    public String getHelp() {
        return help;
    }
    
    /**
     * Checks if is incremental.
     * 
     * @return true, if is incremental
     */
    public boolean isIncremental() {
        return incremental;
    }
    
    /**
     * Gets the cols.
     * 
     * @return the cols
     */
    public int getCols() {
        return cols;
    }
    
    /**
     * Gets the rows.
     * 
     * @return the rows
     */
    public int getRows() {
        return rows;
    }
    
    /**
     * Gets the wrap.
     * 
     * @return the wrap
     */
    public String getWrap() {
        return wrap;
    }
    
    /**
     * Gets the mediatype.
     * 
     * @return the mediatype
     */
    public String getMediatype() {
        return mediatype;
    }
    
    /**
     * Gets the appearance.
     * 
     * @return the appearance
     */
    public String getAppearance() {
        return appearance;
    }
    
    /**
     * Gets the elements.
     * 
     * @return the elements
     */
    public ArrayList getElements() {
        return elements;
    }
    
    /**
     * Gets the file name.
     * 
     * @return the file name
     */
    public String getFileName() {
        return filename;
    }
    
    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Gets the action case.
     * 
     * @return the action case
     */
    public String getActionCase() {
        return actioncase;
    }
    
    /**
     * Checks if is selected.
     * 
     * @return true, if is selected
     */
    public boolean isSelected() {
        return selected;
    }
    
    /**
     * Gets the css class.
     * 
     * @return the css class
     */
    public String getCssClass() {
        return cssclass;
    }
    
    /**
     * Gets the start.
     * 
     * @return the start
     */
    public int getStart(){
        return start;
    }
    
    /**
     * Gets the end.
     * 
     * @return the end
     */
    public int getEnd(){
        return end;
    }
    
    /**
     * Gets the step.
     * 
     * @return the step
     */
    public int getStep(){
        return step;
    }
    
    /**
     * Checks if is inhead.
     * 
     * @return true, if is inhead
     */
    public boolean isInhead(){
        return isInhead;
    }
    
}
