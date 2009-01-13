/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * IterAdmLog.java
 *
 * Created on 24 de septiembre de 2002, 13:08
 */

package org.semanticwb.portal.db;

import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;


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
    private ResultSet rs;
    private Connection con;
    private Statement st;
    private boolean closed = false;
    private boolean next = false;
    private boolean checked = false;

    private Logger log = SWBUtils.getLogger(SWBIterAdmLog.class);

    /** Creates a new instance of IterAdmLog
     * @param con
     * @param st
     * @param rs  */
    public SWBIterAdmLog(Connection con, Statement st, ResultSet rs)
    {
        this.rs = rs;
        this.con = con;
        this.st = st;
    }

    /**
     * @return  */
    public boolean hasNext()
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
    }

    /**
     * @return  */
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
                next = rs.next();
                checked = false;
                if (next) return new SWBRecAdmLog(rs);
            }
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_IterAdmLog_next_nextElementError"), e);
        }
        return null;
    }

    public void remove()
    {
    }

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
