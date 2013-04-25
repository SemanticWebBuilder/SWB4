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
package org.semanticwb.model;

// TODO: Auto-generated Javadoc

import java.util.Iterator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;

/**
 * The Class DisplayObject.
 */
public class DisplayObject extends org.semanticwb.model.base.DisplayObjectBase 
{
    public static final String DISPLAYMODE_FULL_ACCESS="full_access";
    public static final String DISPLAYMODE_FINAL="final";
    public static final String DISPLAYMODE_EDIT_ONLY="edit_only";
    public static final String DISPLAYMODE_HERARQUICAL_EDIT_ONLY="herarquical_edit_only";
    
    
    /**
     * Instantiates a new display object.
     * 
     * @param base the base
     */
    public DisplayObject(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    public static DisplayObject getDisplayObject(SemanticClass cls)
    {
        SemanticObject dispobj=cls.getDisplayObject();
        if(dispobj!=null)
        {
            return (DisplayObject)dispobj.createGenericInstance();
        }
        return null;
    }
    
    public static boolean canCreate(SemanticClass cls)
    {
        if(getDisplayMode(cls)==DISPLAYMODE_FULL_ACCESS)
        {
            return true;
        }
        else if(getDisplayMode(cls)==DISPLAYMODE_EDIT_ONLY)
        {
            return false;
        }
        else if(getDisplayMode(cls)==DISPLAYMODE_FINAL)
        {
            return true;
        }
        return false;
    }
    
    public static boolean canDelete(SemanticClass cls)
    {
        if(getDisplayMode(cls)==DISPLAYMODE_FULL_ACCESS)
        {
            return true;
        }
        else if(getDisplayMode(cls)==DISPLAYMODE_EDIT_ONLY)
        {
            return false;
        }
        else if(getDisplayMode(cls)==DISPLAYMODE_FINAL)
        {
            return true;
        }
        return false;       
    }
    
    public static boolean canCreateChilds(SemanticClass cls)
    {
        if(getDisplayMode(cls)==DISPLAYMODE_FULL_ACCESS)
        {
            return true;
        }
        else if(getDisplayMode(cls)==DISPLAYMODE_EDIT_ONLY)
        {
            return false;
        }
        else if(getDisplayMode(cls)==DISPLAYMODE_FINAL)
        {
            return false;
        }
        return false;        
    }
    
    
    public static String getDisplayMode(SemanticClass cls)
    {
        String ret=DISPLAYMODE_FULL_ACCESS;
        DisplayObject dp=getDisplayObject(cls);
        String mode=null;
        if(dp!=null)
        {
            mode=dp.getDisplayMode();
        }

        if(mode==null)
        {
            Iterator<SemanticClass> it=cls.listSuperClasses(true);
            while (it.hasNext()) {
                SemanticClass semanticClass = it.next();
                if(semanticClass.isSWBClass())
                {
                    return getDisplayMode(semanticClass);
                }
            }
        }
        //else if(mode.equals(DISPLAYMODE_FULL_ACCESS))return true;
        else if(mode.equals(DISPLAYMODE_EDIT_ONLY))
        {
            return DISPLAYMODE_EDIT_ONLY;
        }
        else if(mode.equals(DISPLAYMODE_HERARQUICAL_EDIT_ONLY))
        {
            return DISPLAYMODE_HERARQUICAL_EDIT_ONLY;
        }
        else if(mode.equals(DISPLAYMODE_FINAL))
        {
            return DISPLAYMODE_FINAL;
        }
        return ret;
    }
}

