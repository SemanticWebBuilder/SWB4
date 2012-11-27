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
package org.semanticwb.portal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBFormButton.
 * 
 * @author javier.solis
 */
public class SWBFormButton
{
    
    /** The attributes. */
    private HashMap<String,String> attributes=null;
    
    /** The titles. */
    private HashMap<String,String> titles=null;
    
    /** The busy button. */
    private boolean busyButton=false;
    
    /** The busy time out. */
    private int busyTimeOut=0;

    /**
     * Instantiates a new sWB form button.
     */
    public SWBFormButton()
    {
        attributes=new HashMap();
        titles=new HashMap();
    }

    /**
     * Regresa String con el código del boton.
     * 
     * @param request the request
     * @param codetype Tipo de codigo generado SWBFormMgr.TYPE_XHTML, SWBFormMgr.TYPE_IPHONE, SWBFormMgr.TYPE_DOJO
     * @param lang the lang
     * @return the string
     * @return
     */
    public String renderButton(HttpServletRequest request, String codetype, String lang)
    {
        boolean DOJO=false;
        boolean IPHONE=false;
        boolean XHTML=false;
        if(codetype.equals(SWBFormMgr.TYPE_XHTML))XHTML=true;
        if(codetype.equals(SWBFormMgr.TYPE_DOJO))DOJO=true;
        if(codetype.equals(SWBFormMgr.TYPE_IPHONE))IPHONE=true;
        StringBuffer ret=new StringBuffer();
        ret.append("<button");
        if(DOJO)
        {
            //TODO: Bug in IE
            if(busyButton)
            {
                ret.append(" dojoType=\"dojox.form.BusyButton\"");
                if(busyTimeOut>0)
                    ret.append(" timeout=\""+busyTimeOut+"\"");
            }else
            {
                ret.append(" dojoType=\"dijit.form.Button\"");
            }
        }
        ret.append(" "+getAttributes());
        ret.append(">");
        ret.append(getTitle(lang));
        ret.append("</button>");
        return ret.toString();
    }

    /**
     * Gets the title.
     * 
     * @param lang the lang
     * @return the title
     */
    public String getTitle(String lang)
    {
        String ret=titles.get(lang);
        if(ret==null && titles.size()>0)
        {
            ret=titles.values().iterator().next();
        }
        return ret;
    }

    /**
     * Sets the attribute.
     * 
     * @param name the name
     * @param value the value
     * @return the sWB form button
     */
    public SWBFormButton setAttribute(String name, String value)
    {
        attributes.put(name.toLowerCase(), value);
        return this;
    }

    /**
     * Sets the title.
     * 
     * @param title the title
     * @param lang the lang
     * @return the sWB form button
     */
    public SWBFormButton setTitle(String title, String lang)
    {
        titles.put(lang, title);
        return this;
    }

    /**
     * Gets the attributes.
     * 
     * @return the attributes
     */
    public String getAttributes()
    {
        StringBuffer ret=new StringBuffer();
        Iterator<Entry<String,String>> it=attributes.entrySet().iterator();
        while(it.hasNext())
        {
            Entry entry=it.next();
            ret.append(entry.getKey()+"="+"\""+entry.getValue()+"\"");
            if(it.hasNext())ret.append(" ");
        }
        return ret.toString();
    }

    /**
     * New save button.
     * 
     * @return the sWB form button
     */
    public static SWBFormButton newSaveButton()
    {
        return new SWBFormButton().setTitle("Guardar", "es").setTitle("Save", "en").setAttribute("type", "submit").setBusyButton(false);
    }

    /**
     * New cancel button dlg.
     * 
     * @return the sWB form button
     */
    public static SWBFormButton newCancelButtonDlg()
    {
        return new SWBFormButton().setTitle("Cancelar", "es").setTitle("Cancel", "en").setAttribute("onclick", "dijit.byId('swbDialog').hide();");
    }

    /**
     * New close button.
     * 
     * @return the sWB form button
     */
    public static SWBFormButton newCloseButton()
    {
        return new SWBFormButton().setTitle("Cerrar", "es").setTitle("Close", "en").setAttribute("onclick", "window.close();");
    }

    /**
     * New cancel button.
     * 
     * @return the sWB form button
     */
    public static SWBFormButton newCancelButton()
    {
        return new SWBFormButton().setTitle("Cancelar", "es").setTitle("Cancel", "en");
    }

    /**
     * New back button.
     * 
     * @return the sWB form button
     */
    public static SWBFormButton newBackButton()
    {
        return new SWBFormButton().setTitle("Regresar", "es").setTitle("Return", "en").setAttribute("onclick", "history.back();");
    }

    /**
     * New delete button.
     * 
     * @return the sWB form button
     */
    public static SWBFormButton newDeleteButton()
    {
        return new SWBFormButton().setTitle("Eliminar", "es").setTitle("Delete", "en").setBusyButton(false);
    }

    /**
     * New reset button.
     * 
     * @return the sWB form button
     */
    public static SWBFormButton newResetButton()
    {
        return new SWBFormButton().setTitle("Restaurar", "es").setTitle("Reset", "en");
    }


    public static SWBFormButton newAcceptProcessTaskButton()
    {
        return new SWBFormButton().setTitle("Aceptar Tarea","es").setTitle("Accept Task", "en").setAttribute("name", "accept");
    }

    public static SWBFormButton newRejectProcessTaskButton()
    {
        return new SWBFormButton().setTitle("Rechazar Tarea","es").setTitle("Reject Task", "en").setAttribute("name", "reject");
    }

    /**
     * Checks if is busy button.
     * 
     * @return the busyButton
     */
    public boolean isBusyButton() {
        return busyButton;
    }

    /**
     * Sets the busy button.
     * 
     * @param busyButton the busyButton to set
     * @return the sWB form button
     */
    public SWBFormButton setBusyButton(boolean busyButton) {
        this.busyButton = busyButton;
        return this;
    }


    /**
     * Gets the busy time out.
     * 
     * @return the busy time out
     */
    public int getBusyTimeOut() {
        return busyTimeOut;
    }

    /**
     * Sets the busy time out.
     * 
     * @param busyTimeOut the busy time out
     * @return the sWB form button
     */
    public SWBFormButton setBusyTimeOut(int busyTimeOut)
    {
        this.busyTimeOut = busyTimeOut;
        return this;
    }

}
