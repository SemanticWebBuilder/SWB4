/**  
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
**/ 
 


package com.infotec.wb.resources.database;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Types;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;

import oracle.sql.BLOB;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBObserver;


/**
 * Representacion en objeto de cualquier registro de base de datos.
 *
 * @author: Sergio Tellez
 * @version 1.2
 */
public class RecordGeneric {
Logger log = SWBUtils.getLogger(RecordGeneric.class);
	/**
	 * Nombre de la base de datos.
	 */
    private String dbcon = new String();
    
	/**
	 * Datos de un registro.
	 */
    private HashMap data = new HashMap();
    
	/**
	 * Datos de un registro.
	 */
    private HashMap backdata = new HashMap();
    
	/**
	 * tabla a la que se instancia.
	 */
    private TableGeneric table = null;
    
	/**
	 * Lista de observadores.
	 */
    private ArrayList observers = new ArrayList();;

	/**
	 * 
	 * Default Constructor.
	 */
    public RecordGeneric() {
    }

	/**
	 * Constructor.
	 * 
	 * @param table TableGeneric sobre el que se trabaja.
	 */
    public RecordGeneric(TableGeneric table) {
    	this.table = table;
        this.dbcon = table.getConexion();
        registerObserver(table);
    }

	/**
	 * Constructor.
	 * 
	 * @param table TableGeneric sobre el que se trabaja.
	 * @param rs ResultSet que contiene los registros que se estaran manejando.
	 */
    public RecordGeneric(TableGeneric table, ResultSet rs) {
        this.table = table;
        try {
            readResultSet(rs, table.getColumnNames());
            backdata = new HashMap(data);
        }
        catch(SQLException e) {
            log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.RecordGeneric", "error_RecordGeneric_resultsetError") + "...", e);
        }
        registerObserver(table);
    }

	/**
	 * Obtiene el conjunto de registros.
	 * 
	 * @return <b>HashMap</b> Registros de la consulta.
	 */
    public HashMap getData() {
        return data;
    }

	/**
	 * Establece los valores de un registro.
	 * 
	 * @param data HashMap con los nombres de la columna y su valor.
	 */
    public void setData(HashMap data) {
         this.data = data;
    }

	/**
	 * Obtiene el objeto asociado al RecordGeneric que lo instancia.
	 * 
	 * @return <b>TableGeneric</b>
	 */
	public TableGeneric getTable() {
		return table;
	}
	 
	/**
	 * Obtiene el nombre de la tabla.
	 * 
	 * @return <b>String</b> Nombre de la tabla.
	 */
	public String getTablename() {
		return table.getTablename();
	}
	
	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Boolean</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public boolean getBoolean(int x) {
        return getBoolean(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Boolean</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public boolean getBoolean(String name) {
        return getBoolean(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Boolean</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public boolean getBoolean(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        return ((Boolean)obj).booleanValue();
    }

	/**
     * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>byte</b> valor contenido en la columna.
	 * 
	 * @param int Indice de la columna.
	 */
    public byte getByte(int x) {
        return getByte(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>byte</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public byte getByte(String name) {
        return getByte(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>byte</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public byte getByte(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        if (obj instanceof Long)
			return ((Long)obj).byteValue();
		else {
		    String l = (String)obj;
		    if (l == null || (l != null && "".equals(l.trim())))
		        l="0";
			return new Long(l).byteValue();
        }
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>long</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public long getLong(int x) {
        return getLong(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>long</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public long getLong(String name) {
        return getLong(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>long</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public long getLong(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        if(obj instanceof Double)
            return ((Double)obj).longValue();
        else {
            if (obj instanceof Long)
                 return ((Long)obj).longValue();
            else {
            	if (obj instanceof Float) {
            		return ((Long)obj).longValue();
            	}else {
            		if (obj instanceof Integer) {
            			return ((Long)obj).longValue();
            		}else {
						if (obj instanceof String) {
							String l = (String)obj;
							if (l.length() > 0)
								return new Long(l).longValue();
							else
								return 0;
						}else
							return 0;
            		}
            	}
            }
        }
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>byte[]</b> valor contenido en la columna.
	 * 
	 * @param int Indice de la columna.
	 */
    public byte[] getBytes(int x) {
        return getBytes(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>byte[]</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public byte[] getBytes(String name) {
        return getBytes(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>byte[]</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public byte[] getBytes(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        if (obj == null)
        	return new byte[0];
        else
        	return (byte[])obj;
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>InputStream</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public InputStream getAsciiStream(int x) {
        return getAsciiStream(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>InputStream</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public InputStream getAsciiStream(String name) {
        return getAsciiStream(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>InputStream</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public InputStream getAsciiStream(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        return SWBUtils.IO.getStreamFromString((String) obj);
    }
    
	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>InputStream</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
	public InputStream getBinaryStream(int x) {
		return getBinaryStream(table.getColumnName(x));
	}

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>InputStream</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
	public InputStream getBinaryStream(String name) {
		return getBinaryStream(name, false);
	}
	 
	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>InputStream</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
	 public InputStream getBinaryStream(String name, boolean back) {
	 	Object obj = null;
		if(back && backdata.size() > 0)
			obj = backdata.get(name);
		else
			obj = data.get(name);
		return (InputStream)obj;
	 }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>String</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public String getString(int x) {
        return getString(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>String</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public String getString(String name) {
        return getString(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>String</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public String getString(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        if(obj == null)
            return null;
        if(obj instanceof byte[])
            return new String((byte[])obj);
        else
            return obj.toString();
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>BigDecimal</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public BigDecimal getBigDecimal(int x) {
        return getBigDecimal(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>BigDecimal</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public BigDecimal getBigDecimal(String name) {
        return getBigDecimal(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>BigDecimal</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public BigDecimal getBigDecimal(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        if(obj instanceof Long)
            return new BigDecimal(((Long)obj).doubleValue());
        else {
        	if (obj instanceof Double)
				return new BigDecimal(((Double)obj).doubleValue());
			else {
				if (obj instanceof String) {
					String decimal = (String)obj;
					if (decimal.length() > 0)
						return new BigDecimal(decimal);
					else
						return null;
				}else
					return null;
			}
        }
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Object</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public Object getObject(int x) {
        return getObject(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Object</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public Object getObject(String name) {
        return getObject(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Object</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public Object getObject(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        return obj;
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>int</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public int getInt(int x) {
        return getInt(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>int</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public int getInt(String name) {
        return getInt(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>int</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public int getInt(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        if(obj instanceof Double)
            return ((Double)obj).intValue();
        else {
            if (obj instanceof Long)
                 return ((Long)obj).intValue();
            else {
            	if (obj instanceof String) {
            	    String l = (String)obj;
            	    if (l.length()>0) {
            	        Integer i = new Integer((String)obj);
                		return i.intValue();
            	    }else
            	        return 0;
            	}else
            		return 0;
            }
         }
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>short</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public short getShort(int x) {
        return getShort(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>short</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public short getShort(String name) {
        return getShort(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>short</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public short getShort(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        if(obj instanceof Double)
            return ((Double)obj).shortValue();
        else {
        	if (obj instanceof Long) {
				return ((Long)obj).shortValue();
        	}else {
        		if (obj instanceof String) {
        			String sh = (String)obj;
        			if (sh.length() > 0)
        				return new Double((String)obj).shortValue();
        			else
        				return 0;
        		}else
        			return 0;
        	}
        }
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>double</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public double getDouble(int x) {
        return getDouble(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>double</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public double getDouble(String name) {
        return getDouble(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>double</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public double getDouble(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else 
            obj = data.get(name);
        if (obj instanceof Double) 
        	return ((Double)obj).doubleValue();
        else {
        	if (obj instanceof String) {
        		String d = (String)obj;
        		if (d.length() > 0) {
					Double db = new Double(d);
					return db.doubleValue();
        		}else
        			return 0;
        	}else
        		return 0;
        }
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>float</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public float getFloat(int x) {
        return getFloat(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>float</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public float getFloat(String name) {
        return getFloat(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>float</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public float getFloat(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        if (obj instanceof Double) 
			return ((Double)obj).floatValue();
		else {
			if (obj instanceof String) {
				String ft = (String)obj;
				if (ft.length() > 0) {
					Double d = new Double(ft);
					return d.floatValue();
				}else
					return 0;
			}else
				return 0;
		}
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Date</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public Date getDate(int x) {
        return getDate(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Date</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public Date getDate(String name) {
        return getDate(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Date</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public Date getDate(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        if(obj instanceof Timestamp)
            return new Date(((Timestamp)obj).getTime());
        else {
        	if (obj instanceof Date)
        		return (Date)obj;
        	else {
        		if (obj instanceof String) {
        			String date = (String)obj;
        			if (date.length() > 0) 
						return Date.valueOf((String)obj);
        			else
        				return null;
        		}
        		else
        			return null;
        	}
        }
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Time</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public Time getTime(int x) {
        return getTime(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Time</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public Time getTime(String name) {
        return getTime(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Time</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public Time getTime(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        if (obj instanceof Time)
        	return (Time)obj;
        else {
        	if (obj instanceof String) {
        		String time = (String)obj;
        		if (time.length() > 0)
					return Time.valueOf(time);
        		else 
        			return null;
        	}else 
        		return null;
        }
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Timestamp</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public Timestamp getTimestamp(int x) {
        return getTimestamp(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Timestamp</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public Timestamp getTimestamp(String name) {
        return getTimestamp(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Timestamp</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public Timestamp getTimestamp(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        if(obj instanceof Date)
            return new Timestamp(((Date)obj).getTime());
        else {
        	if (obj instanceof Timestamp)
				return (Timestamp)obj;
			else
				return Timestamp.valueOf((String)obj);
        }
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Blob</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public Blob getBlob(int x) {
        return getBlob(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Blob</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public Blob getBlob(String name) {
        return getBlob(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Blob</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public Blob getBlob(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else 
            obj = data.get(name);
        return (Blob)obj;
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
     * 
     * @return <b>Clob</b> valor contenido en la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public Clob getClob(int x) {
        return getClob(table.getColumnName(x));
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Clob</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public Clob getClob(String name) {
        return getClob(name, false);
    }

	/**
	 * Obtiene valor asociado al tipo de la columna.
	 * 
	 * @return <b>Clob</b> valor contenido en la columna.
	 * 
	 * @param name Nombre de la columna.
	 * @param back Indicador de si el dato ha sido requerido.
	 */
    public Clob getClob(String name, boolean back) {
        Object obj = null;
        if(back && backdata.size() > 0)
            obj = backdata.get(name);
        else
            obj = data.get(name);
        return (Clob)obj;
    }
    
    /**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setBoolean(int x, boolean d) {
        setBoolean(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setBoolean(String name, boolean d) {
        data.put(name, new Boolean(d));
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setByte(int x, byte d) {
        setByte(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setByte(String name, byte d) {
        data.put(name, new Long(d));
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setLong(int x, long d) {
        setLong(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setLong(String name, long d) {
        data.put(name, new Long(d));
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setBytes(int x, byte d[]) {
        setBytes(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setBytes(String name, byte d[]) {
        data.put(name, d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setAsciiStream(int x, InputStream d) {
        setAsciiStream(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setAsciiStream(String name, InputStream d)
    {
        try {
            data.put(name, SWBUtils.IO.readInputStream(d));
        } catch (Exception e) {
        }
        
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
	public void setBinaryStream(int x, InputStream d) {
		setBinaryStream(table.getColumnName(x), d);
	}

    /**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
	public void setBinaryStream(String name, InputStream d) {
		data.put(name, d);
	}
	
	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setString(int x, String d) {
        setString(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	*/
    public void setString(String name, String d) {
        try {
            switch(table.getColumnType(name)) {
            case java.sql.Types.BIT:						//-7
            case 16: 										//java.sql.Types.BOOLEAN:
                data.put(name, new Boolean(d));
                break;
            case java.sql.Types.TINYINT:					//-6 
            case java.sql.Types.BIGINT:						//-5
            case java.sql.Types.INTEGER:					//4
            case java.sql.Types.SMALLINT:					//5
                data.put(name, new Long(d));
                break;
            case java.sql.Types.LONGVARBINARY:				//-4 
            case java.sql.Types.VARBINARY:					//-3 
            case java.sql.Types.BINARY:						//-2 
                data.put(name, d.getBytes());
                break;
            case java.sql.Types.LONGVARCHAR:				//-1
            case java.sql.Types.CHAR:						//1
            case java.sql.Types.VARCHAR:					//12
                data.put(name, d);
                break;
            case java.sql.Types.NULL:						//0
                data.put(name, null);
                break;
            case java.sql.Types.FLOAT:						//6
            case java.sql.Types.DOUBLE:						//8
            case java.sql.Types.NUMERIC:					//2
            case java.sql.Types.DECIMAL:					//3
            case java.sql.Types.REAL:						//7
                data.put(name, new Double(d));
                break;
            case 9:
            case java.sql.Types.DATE:						//91
            	break;
            case 10:
            case java.sql.Types.TIME:						//92
            	break;
            case 11:
            case java.sql.Types.TIMESTAMP:					//93
            	break;
            case java.sql.Types.BLOB:						//2004
            	break;
            case java.sql.Types.CLOB:						//2005
                break;
            default:
                log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.RecordGeneric", "log_RecordGeneric_setString_typeNotFound") + "..." + table.getColumnType(name) + ":" + name);
                break;
            }
        }
        catch(Exception e) {
            data.put(name, d);
        }
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setBigDecimal(int x, BigDecimal d) {
        setBigDecimal(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setBigDecimal(String name, BigDecimal d) {
        data.put(name, new Double(d.doubleValue()));
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setObject(int x, Object d) {
        setObject(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setObject(String name, Object d) {
        data.put(name, d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setInt(int x, int d) {
        setInt(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setInt(String name, int d) {
        data.put(name, new Long(d));
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setShort(int x, short d) {
        setShort(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setShort(String name, short d) {
        data.put(name, new Long(d));
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setDouble(int x, double d) {
        setDouble(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setDouble(String name, double d) {
        data.put(name, new Double(d));
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setFloat(int x, float d) {
        setFloat(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setFloat(String name, float d) {
        data.put(name, new Double(d));
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setDate(int x, Date d) {
        setDate(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setDate(String name, Date d) {
        data.put(name, d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setTime(int x, Time d) {
        setTime(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setTime(String name, Time d) {
        data.put(name, d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setTimestamp(int x, Timestamp d) {
        setTimestamp(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setTimestamp(String name, Timestamp d) {
        data.put(name, d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setBlob(int x, Blob d) {
        setBlob(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna
	 */
    public void setBlob(String name, Blob d) {
        data.put(name, d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param x Indice de la columna.
	 * @param d valor que se asigna a la columna.
	 */
    public void setClob(int x, Clob d) {
        setClob(table.getColumnName(x), d);
    }

	/**
	 * Establece el valor en la columna.
	 *  
	 * @param name Nombre de la columna.
	 * @param d valor que se asigna a la columna.
	 */
    public void setClob(String name, Clob d) {
        data.put(name, d);
    }

	/**
	 * Registra el objeto observador para que pueda recibir notificaciones de cambios
	 *  
	 * @param obs TableGeneric que se registra.
	 */
	public void registerObserver(SWBObserver obs) {
		if (obs != null)
			observers.add(obs);
	}
	
	/**
	 * Elimina la tabla del registro.
	 *  
	 * @param obs TableGeneric que se elimina.
	 */
	public void removeObserver(SWBObserver obs) {
		observers.remove(obs);
	}
	
	/**
	 * Inserta un nuevo registro en la base de datos.
	 * 
	 * @throws <b>Exception</b> si ocurre error al realizar la insercion.
	 */
	public void insert() throws Exception {
	    boolean blob = false;
		Connection con = SWBUtils.DB.getConnection(dbcon);
		Statement st = con.createStatement();
		if(table.existColumnName("lastupdate"))
			setTimestamp("lastupdate", new Timestamp((new java.util.Date()).getTime()));
		String na = "";
		String va = "";
		for(Iterator it = table.getColumnNames(); it.hasNext();) {
			String key = (String)it.next();
			if (table.getColumnType(key) == Types.BLOB)
			    blob=true;
			na = na + "," + key;
			va = va + ",?";
		}
		if(na.length() > 0)
			na = na.substring(1);
		if(va.length() > 0)
			va = va.substring(1);
		String ins = "insert into " + getTablename() + " (" + na + ") values(" + va + ")";
		PreparedStatement pst = con.prepareStatement(ins);
		setPrepareStatement(pst, table.getColumnNames());
		if (blob)
		    setBlob(con, st, pst);
		else {
		    if(pst.executeUpdate() == 0) {
				pst.close();
				st.close();
				con.close();
				throw new SQLException(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.RecordGeneric", "error_RecordGeneric_create_createElementError") + "...");
			}else{
				pst.close();
				st.close();
				con.close();
				backdata = new HashMap(data);
				sendNotify("create", false);
				return;
			} 
		}
	}

	/**
	 * Inserta los bytes del BLOB en el registro en la base de datos (Oracle).
	 * 
	 * @param con Conexion con la base de datos especifica.
	 * @param st Statement usado para ejecutar declaraciones estaticas SQL.
	 * @param pst Sentencia precompilada SQL.
	 * 
	 * @throws <b>Exception</b> si ocurre error al realizar la insercion.
	 */
	public void setBlob(Connection con, Statement st, PreparedStatement pst) throws Exception {
	    String query = new String();
	    ByteArrayInputStream file=null;
	    try {
	        if(pst.executeUpdate() == 0) {
    			throw new SQLException(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.RecordGeneric", "error_RecordGeneric_create_createElementError") + "...");
    		}else {
    		    con.setAutoCommit(false);
    		    for(Iterator it = table.getColumnNames(); it.hasNext();) {
    		        String key = (String)it.next();
    		        if (table.getColumnType(key) == Types.BLOB) {
    		            byte d[] = (byte[])data.get(key);
    		            file = new ByteArrayInputStream(d);
    		            query = "SELECT " + key + " FROM " + getTablename() + " WHERE ";
    		        }
    		    }
    		    query += setPrepareLocator();
    		    ResultSet rs = st.executeQuery(query);
    		    if(rs.next()) {
    		        BLOB blob = ((oracle.jdbc.driver.OracleResultSet)rs).getBLOB(1);
    		        OutputStream outstream = blob.getBinaryOutputStream();
    		        int size = blob.getBufferSize();
    		        byte[] buffer=new byte[size];
    		        int length =-1;
    		        while((length = file.read(buffer))!=-1)
    		            outstream.write(buffer,0,length);
    		        file.close();
    		        outstream.flush();
    		        outstream.close();
    		    }
    		    rs.close();
    		    pst.close();
    		    st.close();
    		    con.commit();
    		    con.close();
    		    backdata = new HashMap(data);
    		    sendNotify("create", false);
    		}
	    }catch (Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.RecordGeneric", "error_RecordGeneric_create_createElementError"),e);
        }
	}
	
	/**
	 * Carga un registro de la base de datos.
	 * 
	 * @throws <b>SQLException</b> si ocurre error al realizar la consulta.
	 */
    public void load() throws SQLException {
    	Connection con = SWBUtils.DB.getConnection(dbcon);
        String na = "";
        for(Iterator it = table.getPrimaryKeys(); it.hasNext();) {
            String key = (String)it.next();
            na = na + " and " + key + "=?";
        }
        if(na.length() > 0)
            na = na.substring(4);
        String ins = "select * from " + getTablename() + " where" + na;
        PreparedStatement pst = con.prepareStatement(ins);
        setPrepareStatement(pst, table.getPrimaryKeys());
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            readResultSet(rs, table.getColumnNames());
        }else{
            rs.close();
            pst.close();
            con.close();
            throw new SQLException(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.RecordGeneric", "error_RecordGeneric_load_ElementNotFound") + "...");
        }
        rs.close();
        pst.close();
        con.close();
        backdata = new HashMap(data);
        sendNotify("load", false);
    }
    
    /**
	 * Carga un registro de la base de datos recibiendo una consulta parcial externa.
	 * 
	 * @throws <b>SQLException</b> si ocurre error al realizar la consulta.
	 */
    public void load(String query, Iterator columnNames) throws SQLException {
    	Connection con = SWBUtils.DB.getConnection(dbcon);
        String na = "";
        for(Iterator it = table.getPrimaryKeys(); it.hasNext();) {
            String key = (String)it.next();
            na = na + " and " + key + "=?";
        }
        if(na.length() > 0)
            na = na.substring(4);
        String ins = "select " + query + " from " + getTablename() + " where" + na;
        PreparedStatement pst = con.prepareStatement(ins);
        setPrepareStatement(pst, table.getPrimaryKeys());
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            readResultSet(rs, columnNames);
        }else{
            rs.close();
            pst.close();
            con.close();
            throw new SQLException(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.RecordGeneric", "error_RecordGeneric_load_ElementNotFound") + "...");
        }
        rs.close();
        pst.close();
        con.close();
        backdata = new HashMap(data);
        sendNotify("load", false);
    }

	/**
	 * Elimina un registro en la base de datos.
	 * 
	 * @return <b>int</b> Indicador de eliminacion. <b>0</b> Si no se efectuo. <b>1</b> En caso contrario.
	 * 
	 * @throws <b>Exception</b> si ocurre error al realizar la eliminacion.
	 */
    public int remove() throws Exception {
		int result = 0;
		Connection con = SWBUtils.DB.getConnection(dbcon);
        if(table.existColumnName("lastupdate"))
            setTimestamp("lastupdate", new Timestamp((new java.util.Date()).getTime()));
        String na = "";
        for(Iterator it = table.getPrimaryKeys(); it.hasNext();) {
            String key = (String)it.next();
            na = na + " and " + key + "=?";
        }
        if(na.length() > 0)
            na = na.substring(4);
        String ins = "delete from " + getTablename() + " where" + na;
        PreparedStatement st = con.prepareStatement(ins);
        setPrepareStatement(st, table.getPrimaryKeys());
        result = st.executeUpdate();
        st.close();
        con.close();
        sendNotify("remove", false);
		return result;
    }
    
	/**
	 * Actualiza un registro en la base de datos.
	 * 
	 * @throws <b>Exception</b> si ocurre error al realizar la actualizacion.
	 */
    public void update() throws Exception {
        boolean blob = false;
    	Connection con = SWBUtils.DB.getConnection(dbcon);
    	Statement st = con.createStatement();
        if(table.existColumnName("lastupdate"))
            setTimestamp("lastupdate", new Timestamp((new java.util.Date()).getTime()));
        String na = "";
        for(Iterator it = table.getColumnNames(); it.hasNext();) {
            String key = (String)it.next();
            if (table.getColumnType(key) == Types.BLOB)
			    blob=true;
            na = na + "," + key + "=?";
        }
        if(na.length() > 0)
            na = na.substring(1);
        String ka = "";
        for(Iterator it = table.getPrimaryKeys(); it.hasNext();) {
            String key = (String)it.next();
            ka = ka + " and " + key + "=?";
        }
        if(ka.length() > 0)
            ka = ka.substring(4);
        PreparedStatement pst = con.prepareStatement("update " + getTablename() + " set " + na + " where" + ka);
        setPrepareStatement(pst, table.getColumnNames());
        setPrepareStatement(pst, table.getPrimaryKeys(), true, table.getLength() + 1);
        if (blob)
		    setBlob(con, st, pst);
		else {
		    pst.executeUpdate();
		    pst.close();
		    st.close();
		    con.close();
		    backdata = new HashMap(data);
		    sendNotify("update", false);
		}
    }
    
    /**
	 * Actualiza un registro en la base de datos.
	 * 
	 * @param primaryKeys Claves primarias de la tabla que se desea actualizar.
	 *  
	 * @throws <b>Exception</b> si ocurre error al realizar la actualizacion.
	 */
    public void update(Vector primaryKeys) throws Exception {
        boolean blob = false;
    	Connection con = SWBUtils.DB.getConnection(dbcon);
    	Statement st = con.createStatement();
        if(table.existColumnName("lastupdate"))
            setTimestamp("lastupdate", new Timestamp((new java.util.Date()).getTime()));
        String na = "";
        for(Iterator it = table.getColumnNames(); it.hasNext();) {
            String key = (String)it.next();
            if (table.getColumnType(key) == Types.BLOB)
			    blob=true;
            na = na + "," + key + "=?";
        }
        if(na.length() > 0)
            na = na.substring(1);
        String ka = "";
        for(Iterator it = primaryKeys.iterator(); it.hasNext();) {
            String key = (String)it.next();
            ka = ka + " and " + key + "=?";
        }
        if(ka.length() > 0)
            ka = ka.substring(4);
        PreparedStatement pst = con.prepareStatement("update " + getTablename() + " set " + na + " where" + ka);
        setPrepareStatement(pst, table.getColumnNames());
        setPrepareStatement(pst, primaryKeys.iterator(), true, table.getLength() + 1);
        if (blob)
		    setBlob(con, st, pst);
		else {
		    pst.executeUpdate();
		    pst.close();
		    st.close();
		    con.close();
		    backdata = new HashMap(data);
		    sendNotify("update", false);
		}
    }
    
    /**
	 * Actualiza un registro en la base de datos.
	 * 
	 * @param columnNames Campos de la tabla que se desean actualizar.
	 * @param primaryKeys Claves primarias de la tabla que se desea actualizar.
	 *  
	 * @throws <b>Exception</b> si ocurre error al realizar la actualizacion.
	 */
    public void update(Vector columnNames, Vector primaryKeys) throws Exception {
        boolean blob = false;
    	Connection con = SWBUtils.DB.getConnection(dbcon);
    	Statement st = con.createStatement();
        if(table.existColumnName("lastupdate"))
            setTimestamp("lastupdate", new Timestamp((new java.util.Date()).getTime()));
        String na = "";
        for(Iterator it = columnNames.iterator(); it.hasNext();) {
            String key = (String)it.next();
            if (table.getColumnType(key) == Types.BLOB)
			    blob=true;
            na = na + "," + key + "=?";
        }
        if(na.length() > 0)
            na = na.substring(1);
        String ka = "";
        for(Iterator it = primaryKeys.iterator(); it.hasNext();) {
            String key = (String)it.next();
            ka = ka + " and " + key + "=?";
        }
        if(ka.length() > 0)
            ka = ka.substring(4);
        PreparedStatement ps = con.prepareStatement("update " + getTablename() + " set " + na + " where" + ka);
        setPrepareStatement(ps, columnNames.iterator());
        setPrepareStatement(ps, primaryKeys.iterator(), true, columnNames.size() + 1);
        if (blob)
		    setBlob(con, st, ps);
		else {
		    ps.executeUpdate();
		    ps.close();
		    st.close();
		    con.close();
		    backdata = new HashMap(data);
		    sendNotify("update", false);
		}
    }
    
	/**
	 * Elimina el registro de datos.
	 */
    public void rollBack() {
        if(backdata.size() > 0)
            data = new HashMap(backdata);
    }

	/**
	 * Obtiene la(s) clave(s) primaria(s) de la tabla.
	 *  
	 * @return <b>String</b> Clave(s) primaria(s).
	 */
    public String getHashKey() {
        String key = "";
        for(Iterator it = table.getPrimaryKeys(); it.hasNext();) {
            String nm = (String)it.next();
            Object ob = data.get(nm);
            if(ob instanceof Double)
                key = key + "|" + getInt(nm);
            else
                key = key + "|" + data.get(nm);
        }
        if(key.length() > 0)
            key = key.substring(1);
        return key;
    }

	/**
	 * Sincroniza las operaciones que se realizan en la tabla.
	 *  
	 * @param action Accion que se realiza.
	 * @param date Estampa de tiempo de la operacion.
	 * 
	 * @throws <b>SQLException</b> Si ocurre error en la sincronizacion.
	 */
    public void syncFromExternalAction(String action, Timestamp date) throws SQLException {
        Timestamp lastupdate = null;
        if(table.existColumnName("lastupdate"))
            lastupdate = getTimestamp("lastupdate");
        if(lastupdate == null || lastupdate != null && lastupdate.before(date))
            if(action.equals("remove"))
                sendNotify(action, false);
            else
            if(action.equals("create") || action.equals("update"))
                load();
    }

	/**
	 * Envia la notificacion a los observadores de que hubo una operacion en la tabla y cual fue.
	 *  
	 * @param message Accion que se realiza.
	 * @param synch Indicador de sincronizacion. true si esta activo, false en caso contrario.
	 */
    public void sendNotify(String message, boolean synch) {
        Iterator it = observers.iterator();
        while (it.hasNext())
        {
            ((SWBObserver) it.next()).sendDBNotify(message, this);
        }
        if (synch)
        {
            //TODO:
            //DBDbSync.getInstance().saveChange(table.getTablename(), message, 0, getHashKey(), getTimestamp("lastupdate"));
        }
    }

	/**
	 * Establece la precompilacion SQL.
	 *  
	 * @param pst Objeto PreparedStatement que se va a procesar.
	 * @param keys Conjunto de claves primarias.
	 * 
	 * @throws <b>SQLException</b> Si ocurre error en la operacion SQL.
	 */
    private void setPrepareStatement(PreparedStatement pst, Iterator keys) throws SQLException {
        setPrepareStatement(pst, keys, false);
    }

	/**
	 * Establece la precompilacion SQL.
	 *  
	 * @param pst Objeto PreparedStatement que se va a procesar.
	 * @param keys Conjunto de claves primarias.
	 * @param back Indicador de si el dato ha sido requerido.
	 * 
	 * @throws <b>SQLException</b> Si ocurre error en la operacion SQL.
	 */
    private void setPrepareStatement(PreparedStatement pst, Iterator keys, boolean back) throws SQLException {
        setPrepareStatement(pst, keys, back, 1);
    }

	/**
	 * Establece la precompilacion SQL.
	 *  
	 * @param pst Objeto PreparedStatement que se va a procesar.
	 * @param keys Conjunto de claves primarias.
	 * @param back Indicador de si el dato ha sido requerido.
	 * @param start Indicador de en cual clave va a comenzar a armar la consulta.
	 * 
	 * @throws <b>SQLException</b> Si ocurre error en la operacion SQL.
	 */
    private void setPrepareStatement(PreparedStatement pst, Iterator keys, boolean back, int start) throws SQLException {
    	int x = start;
    	while (keys.hasNext()) {
            String key = (String)keys.next();
            switch(table.getColumnType(key)) {
            case java.sql.Types.BIT:											//-7 
            case 16:															//java.sql.Types.BOOLEAN:
                pst.setBoolean(x, getBoolean(key, back));
                break;

            case java.sql.Types.TINYINT:										//-6
                pst.setByte(x, getByte(key, back));
                break;

            case java.sql.Types.BIGINT:											//-5 
                pst.setLong(x, getLong(key, back));
                break;

            case java.sql.Types.LONGVARBINARY:                                  //-4
            	byte[] data = getBytes(key, back);
            	if (data!=null) {
                    ByteArrayInputStream file = new ByteArrayInputStream(data);
                    pst.setBinaryStream(x, file, data.length);	
            	}else
                    pst.setBinaryStream(x, null, 0);
            	break;
            		 
            case java.sql.Types.VARBINARY:										//-3 
            case java.sql.Types.BINARY:											//-2 
                pst.setBytes(x, getBytes(key, back));
                break;

            case java.sql.Types.LONGVARCHAR:                                                                            //-1 
                String ax = getString(key, back);
                if(ax != null)
                    pst.setAsciiStream(x, getAsciiStream(key, back), ax.length());
                else
                    pst.setString(x, ax);
                break;

            case java.sql.Types.NULL:											//0 
                pst.setString(x, null);
                break;

            case java.sql.Types.CHAR:											//1
            case java.sql.Types.VARCHAR:										//12
                pst.setString(x, getString(key, back));
                break;

            case java.sql.Types.NUMERIC:										//2
            case java.sql.Types.DECIMAL:										//3
                pst.setBigDecimal(x, getBigDecimal(key, back));
                break;

            case java.sql.Types.INTEGER:										//4
                pst.setInt(x, getInt(key, back));
                break;

            case java.sql.Types.SMALLINT:										//5
                pst.setShort(x, getShort(key, back));
                break;

            case java.sql.Types.FLOAT:											//6
            case java.sql.Types.DOUBLE:											//8
                pst.setDouble(x, getDouble(key, back));
                break;

            case java.sql.Types.REAL:											//7
                pst.setFloat(x, getFloat(key, back));
                break;

            case 9:
            case java.sql.Types.DATE:											//91
				pst.setDate(x, getDate(key, back));         	
                break;

            case 10:
            case java.sql.Types.TIME:											//92
                pst.setTime(x, getTime(key, back));
                break;

            case 11:
            case java.sql.Types.TIMESTAMP:										//93
                pst.setTimestamp(x, getTimestamp(key, back));
                break;

            case java.sql.Types.BLOB:											//2004
                //pst.setBlob(x, getBlob(key, back));
                pst.setBlob(x, BLOB.empty_lob());
                break;

            case java.sql.Types.CLOB:											//2005
                pst.setClob(x, getClob(key, back));
                break;

            default:
            	log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.RecordGeneric", "log_RecordGeneric_setPreparedStament_tipeNotFound") + "..." + table.getColumnType(key) + ":" + key);
                break;
            }
            x++;
        }
    }

    /**
	 * Establece la precompilacion SQL.
	 *  
	 * @param pst Objeto PreparedStatement que se va a procesar.
	 * @param keys Conjunto de claves primarias.
	 * @param back Indicador de si el dato ha sido requerido.
	 * @param start Indicador de en cual clave va a comenzar a armar la consulta.
	 * 
	 * @throws <b>SQLException</b> Si ocurre error en la operacion SQL.
	 */
    private String setPrepareLocator() throws SQLException {
        String set = new String();
        boolean back = false;
        for (int i=1; i<table.getLength(); i++) {
            if (!(table.getColumnType(i) == Types.BLOB) && !(table.getColumnType(i) == Types.LONGVARBINARY)
                   && !(table.getColumnType(i) == Types.VARBINARY) && !(table.getColumnType(i) == Types.BINARY)
                   && !(table.getColumnType(i) == Types.NULL) && !(table.getColumnType(i) == Types.CLOB)
                   && !(table.getColumnType(i) == Types.DATE) && !(table.getColumnType(i) == Types.TIME)
                   && !(table.getColumnType(i) == Types.TIMESTAMP)) {
                set += " AND " + table.getColumnName(i) + " = ";
                switch(table.getColumnType(i)) {
                	case java.sql.Types.BIT:											//-7 
                	case 16:															//java.sql.Types.BOOLEAN:
                	    set += getBoolean(table.getColumnName(i), back);
                    break;

                	case java.sql.Types.TINYINT:										//-6
                	    set += getByte(table.getColumnName(i), back);
                    break;

                	case java.sql.Types.BIGINT:											//-5 
                	    set += getLong(table.getColumnName(i), back);
                    break;

                	case java.sql.Types.LONGVARCHAR:                                                                            //-1 
                	    set += "'"+getString(table.getColumnName(i), back)+"'";
                    break;

                	case java.sql.Types.CHAR:											//1
                	case java.sql.Types.VARCHAR:										//12
                	    set += "'"+getString(table.getColumnName(i), back)+"'";
                    break;

                	case java.sql.Types.NUMERIC:										//2
                	case java.sql.Types.DECIMAL:										//3
                	    set += getBigDecimal(table.getColumnName(i), back);
                    break;

                	case java.sql.Types.INTEGER:										//4
                	    set += getInt(table.getColumnName(i), back);
                    break;

                	case java.sql.Types.SMALLINT:										//5
                	    set += getShort(table.getColumnName(i), back);
                    break;

                	case java.sql.Types.FLOAT:											//6
                	case java.sql.Types.DOUBLE:											//8
                	    set += getDouble(table.getColumnName(i), back);
                    break;

                	case java.sql.Types.REAL:											//7
                	    set += getFloat(table.getColumnName(i), back);
                    break;

                	default:
                	    log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.RecordGeneric", "log_RecordGeneric_setPreparedStament_tipeNotFound") + "..." + table.getColumnType(i) + ":" + table.getColumnName(i));
                    break;
                }
            }
        }
        if(set.length() > 0)
			set = set.substring(5);
        return set + " FOR UPDATE";
    }
    
    /**
	 * Lee los resultados de la consulta con los tipos adecuados a la tabla.
	 *  
	 * @param rs Objeto ResultSet que se va a leer.
	 * @param keys Conjunto de claves primarias.
	 * 
	 * @throws <b>SQLException</b> Si ocurre error en la operacion SQL.
	 */
    private void readResultSet(ResultSet rs, Iterator keys) throws SQLException {
        while (keys.hasNext()) {
            String key = (String)keys.next();
            switch(table.getColumnType(key)) {
            case java.sql.Types.BIT:							//-7 
            case 16:											//java.sql.Types.BOOLEAN:
                setBoolean(key, rs.getBoolean(key));
                break;
            case java.sql.Types.TINYINT:						//-6 
                setByte(key, rs.getByte(key));
                break;
            case java.sql.Types.BIGINT:							//-5 
                setLong(key, rs.getLong(key));
                break;
            case java.sql.Types.LONGVARBINARY:					//-4
            	setBinaryStream(key, rs.getBinaryStream(key));
            	break; 
            case java.sql.Types.VARBINARY:						//-3 
            case java.sql.Types.BINARY:							//-2 
                setBytes(key, rs.getBytes(key));
                break;
            case java.sql.Types.LONGVARCHAR:					//-1 
                setAsciiStream(key, rs.getAsciiStream(key));
                break;
            case java.sql.Types.NULL:							//0
                setObject(key, null);
                break;
            case java.sql.Types.CHAR:							//1
            case java.sql.Types.VARCHAR:						//12
                setString(key, rs.getString(key));
                break;
            case java.sql.Types.NUMERIC:						//2
            case java.sql.Types.DECIMAL:						//3
                setBigDecimal(key, rs.getBigDecimal(key));
                break;
            case java.sql.Types.INTEGER:						//4
                    setInt(key, rs.getInt(key));
                break;
            case java.sql.Types.SMALLINT:						//5
                setShort(key, rs.getShort(key));
                break;
            case java.sql.Types.FLOAT:							//6
            case java.sql.Types.DOUBLE:							//8
                setDouble(key, rs.getDouble(key));
                break;
            case java.sql.Types.REAL:							//7
                setFloat(key, rs.getFloat(key));
                break;
            case 9:
            case java.sql.Types.DATE:							//91
                setDate(key, rs.getDate(key));
                break;
            case 10:
            case java.sql.Types.TIME:							//92
                setTime(key, rs.getTime(key));
                break;
            case 11:
            case java.sql.Types.TIMESTAMP:						//93
                setTimestamp(key, rs.getTimestamp(key));
                break;
            case java.sql.Types.BLOB:							//2004 
                setBlob(key, rs.getBlob(key));
                break;
            case java.sql.Types.CLOB:							//2005 
                setClob(key, rs.getClob(key));
                break;
            default:
            	log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.RecordGeneric", "log_RecordGeneric_readResultSet_typeNotFound") + "..." + table.getColumnType(key) + ":" + key);
                break;
            }
        }
    }
}
