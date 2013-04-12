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
package org.semanticwb.portal.db;

import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;


// TODO: Auto-generated Javadoc
/** objeto: iterador de base de datos de la tabla wbadmlog
 * regresa un iterador el cual se utiliza para accesar a los registros de una tabla en forma secuencial...
 *
 * object: iterator of the data base from de table wbadmlog
 * return an iterator wich is used to access the records of a table in sequential form
 *
 * @author Javier Solis Gonzalez
 */
public class SWBIterAdmLog implements java.util.Iterator
{
    
    /** The rs. */
    private ResultSet rs;
    
    /** The con. */
    private Connection con;
    
    /** The st. */
    private Statement st;
    
    /** The closed. */
    private boolean closed = false;
    
    /** The next. */
    private boolean next = false;
    
    /** The checked. */
    private boolean checked = false;

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBIterAdmLog.class);

    /**
     * Creates a new instance of IterAdmLog.
     * 
     * @param con the con
     * @param st the st
     * @param rs the rs
     */
    public SWBIterAdmLog(Connection con, Statement st, ResultSet rs)
    {
        this.rs = rs;
        this.con = con;
        this.st = st;
    }

    /**
     * Checks for next.
     * 
     * @return true, if successful
     * @return
     */
    public boolean hasNext()
    {
        if(!closed)
        {
            try
            {
                if (!checked)
                {
                    next = rs.next();
                    checked = true;
                }
            } catch (Exception e)
            {
                log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_IterAdmLog_hasNext()_isAfter"), e);
            }
            if (!next) close();
            return next;
        }else return false;
    }

    /**
     * Next.
     * 
     * @return the object
     * @return
     */
    public Object next()
    {
        try
        {
            if (checked)
            {
                checked = false;
                return new SWBRecAdmLog(rs);
            } else
            {
                next=hasNext();
                checked = false;
                if (next) return new SWBRecAdmLog(rs);
            }
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_IterAdmLog_next_nextElementError"), e);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    public void remove()
    {
    }

    /**
     * Close.
     */
    public void close()
    {
        closed = true;
        try
        {
            rs.close();
            st.close();
            con.close();
            //System.out.println("Cerrando iterador...");
        } catch (Exception e)
        {
           log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_IterAdmLog_close_closeIteratorError"), e);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    @Override
    public void finalize()
    {
        if (!closed)
        {
            close();
            log.error(SWBUtils.TEXT.getLocaleString("locale_core", "log_IterAdmLog_finalize_norun"));
        }
    }

}
