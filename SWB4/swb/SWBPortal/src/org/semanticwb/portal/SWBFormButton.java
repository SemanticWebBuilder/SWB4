/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author javier.solis
 */
public class SWBFormButton
{
    private HashMap<String,String> attributes=null;
    private HashMap<String,String> titles=null;
    private boolean busyButton=false;

    public SWBFormButton()
    {
        attributes=new HashMap();
        titles=new HashMap();
    }

    /**
     * Regresa String con el código del boton
     * @param request
     * @param codetype Tipo de codigo generado SWBFormMgr.TYPE_XHTML, SWBFormMgr.TYPE_IPHONE, SWBFormMgr.TYPE_DOJO
     * @param lang
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
//            if(busyButton)
//            {
//                ret.append(" dojoType=\"dojox.form.BusyButton\" busyLabel=\""+getTitle(lang)+"\" timeout=\"10000\"");
//            }else
//            {
                ret.append(" dojoType=\"dijit.form.Button\"");
//            }
        }
        ret.append(" "+getAttributes());
        ret.append(">");
        ret.append(getTitle(lang));
        ret.append("</button>");
        return ret.toString();
    }

    public String getTitle(String lang)
    {
        String ret=titles.get(lang);
        if(ret==null && titles.size()>0)
        {
            ret=titles.values().iterator().next();
        }
        return ret;
    }

    public SWBFormButton setAttribute(String name, String value)
    {
        attributes.put(name.toLowerCase(), value);
        return this;
    }

    public SWBFormButton setTitle(String title, String lang)
    {
        titles.put(lang, title);
        return this;
    }

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

    public static SWBFormButton newSaveButton()
    {
        return new SWBFormButton().setTitle("Guardar", "es").setTitle("Save", "en").setAttribute("type", "submit").setBusyButton(true);
    }

    public static SWBFormButton newCancelButtonDlg()
    {
        return new SWBFormButton().setTitle("Cancelar", "es").setTitle("Cancel", "en").setAttribute("onclick", "dijit.byId('swbDialog').hide();");
    }

    public static SWBFormButton newCloseButton()
    {
        return new SWBFormButton().setTitle("Cerrar", "es").setTitle("Close", "en").setAttribute("onclick", "window.close();");
    }

    public static SWBFormButton newCancelButton()
    {
        return new SWBFormButton().setTitle("Cancelar", "es").setTitle("Cancel", "en");
    }

    public static SWBFormButton newBackButton()
    {
        return new SWBFormButton().setTitle("Regresar", "es").setTitle("Return", "en").setAttribute("onclick", "history.back();");
    }

    public static SWBFormButton newDeleteButton()
    {
        return new SWBFormButton().setTitle("Eliminar", "es").setTitle("Delete", "en").setBusyButton(true);
    }

    public static SWBFormButton newResetButton()
    {
        return new SWBFormButton().setTitle("Restaurar", "es").setTitle("Reset", "en");
    }

    /**
     * @return the busyButton
     */
    public boolean isBusyButton() {
        return busyButton;
    }

    /**
     * @param busyButton the busyButton to set
     */
    public SWBFormButton setBusyButton(boolean busyButton) {
        this.busyButton = busyButton;
        return this;
    }

}
