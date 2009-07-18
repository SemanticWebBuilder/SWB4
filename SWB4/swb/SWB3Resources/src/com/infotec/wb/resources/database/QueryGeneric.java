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


package com.infotec.wb.resources.database;

import java.io.InputStream;
import java.sql.*;
import java.util.*;


import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBObserver;

/**
 * Clase que permite realizar una consulta a base de datos.
 *
 * @author Infotec
 * @version 1.1
 */
public class QueryGeneric implements SWBObserver
{
    private int length;           // Numero de columnas que contiene la tabla que representa la consulta.
    private int types[];          // Tipos de dato en las columnas de la tabla que contiene los resultados de la consulta.
    private String dbcon;         // Nombre de la base de datos.
    private HashMap recs;         // Registro obtenido al realizar la consulta.
    private String message;       // Mensaje de error si ocurre una excepcion al realizar la consulta.
    private Vector results;       // Registros obtenidos al realizar la consulta.
    private ArrayList names;      // Lista de nombres de las columnas de la tabla que contiene los resultados de la consulta. 
    private ArrayList primaryKeys;// Lista de claves primarias.
    private int cachelevel;       // Nivel de cache.
    private ArrayList set;        // Conjunto de registros de una consulta
    public static final int NO_CACHE = 0; // Indica que el recurso no esta en memoria.
    public static final int FLY_CACHE= 1; // Indica que el recurso tiene al menos un registro en memoria.
    public static final int FULL_CACHE=2; // Indica que todos los registros de la tabla estan en memoria.
    Logger log = SWBUtils.getLogger(QueryGeneric.class);

    /**
     * Constructor.
     * 
     * @param db Nombre de la base de datos.
     */
    public QueryGeneric(String db) {
        this(db, NO_CACHE);
    }

	/**
	 * Constructor.
	 * 
	 * @param dbcon Nombre de la base de datos.
	 * @param cachelevel Nivel de cache para manejo en memoria.
	 */
    public QueryGeneric(String dbcon, int cachelevel) {
        this.length = 0;
		this.names = null;
		this.message = "";
		this.dbcon = dbcon;
		this.recs = new HashMap();
		this.results = new Vector();
        this.cachelevel = cachelevel;
        this.primaryKeys = new ArrayList();
    }
    
	/**
	 * Realiza la consulta dada a la base de datos.
	 * 
	 * @param query Sentencia SQL que representa la consulta.
	 */
    public void setQuery(String query) {
    	Connection con = null;
    	Statement st = null;
    	try {
			con = SWBUtils.DB.getConnection(dbcon);
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			setMetadata(rs.getMetaData());
			setData(rs);
			rs.close();
			st.close();
			con.close();
    	}catch (SQLException e){    		
    		message = e.getMessage();
			log.error( SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.QueryGeneric", "error_QueryGeneric_setQuery") + "...", e);
    	}finally {
    		try {
    			if (st != null)
    				st.close();
    			if (con != null)
    				con.close();
    		}catch (SQLException e){
    			message = e.getMessage();
				log.error( SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.QueryGeneric", "error_QueryGeneric_setQuery") + "...", e);
    		}
    	}
    }

    /**
     * Indica si el catalogo existe en la base de datos.
     * 
     * @return <b>boolean</b> true Si existe la tabla; false En caso contrario.
     */
    public boolean categoryExist(String query, String db) {
        boolean exist = false;
        Statement st = null;
        Connection con = null;
        try {
            con = SWBUtils.DB.getConnection(db);
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            setMetadata(rs.getMetaData());
            exist = setCategory(rs);
            rs.close();
            st.close();
            con.close();
            return exist;
        }catch (Exception e) {
            return false;
        }finally {
            try {
                if (st != null)
                    st.close();
                if (con != null)
                    con.close();
            }catch (Exception e) {
                return false;
            }
        }
    }
    
    /**
     * Establece los valores del catalogo.
     * 
     * @param rs Registros de una consulta.
     * @param table Nombre de la tabla que contiene los valores del catalogo.
     */
    public boolean setCategory(ResultSet rs) {
        boolean exist = false;
        this.set = new ArrayList();
        try {
            while (rs.next()) {
                exist = true;
                Vector container = new Vector();
                String option = new String();
                String value = rs.getString(1);
                container.add(value);
                for (int i=2; i<=getLength(); i++)
                    option += " " + rs.getString(i);
                container.add(option);
                set.add(container);
            }
            return exist;
        }catch (SQLException e) {
            log.error( SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.QueryGeneric", "error_QueryGeneric_setCategory") + "...", e);
            return false;
        }
    }
    
    /**
     * Regresa los valores del catalogo.
     * 
     * @return <b>Vector</b> Registros de una consulta.
     */
    public Iterator getCategory() {
        return set.iterator();
    }
    
    /**
	 * Obtiene mensaje de excepcion.
	 * 
	 * @return <b>String</b> Mensaje de error.
	 */
	public String getMessageException() {
		return message;
	}
	
	/**
	 * Regresa los resultados de la consulta.
	 * 
	 * @return <b>Enumeration</b> Resultados de la consulta.
	 * 
	 * @param key Sentencia SQL que representa la consulta.
	 */
	public Enumeration getResults(String key) {
		if(getCacheSize() == 0)
			setQuery(key);
		return results.elements();
	}
        
	public Vector getVectorResults(String key) {
		if(getCacheSize() == 0)
			setQuery(key);
		return results;
	}        
    
	/**
	 * Consulta el nivel de cache en memoria.
	 * 
	 * @return <b>int</b> Nivel actual de cache.
	 */
    public int getCacheSize() {
        return results.size();
    }
    
    /**
     * Establece los resultados de la consulta.
     * 
     * @param rs ResultSet de la instancia que lo esta llamando.
     */
    public void setData(ResultSet rs)
    {
        try 
        {
            while (rs.next()) 
            {
                HashMap recs = new HashMap();
                for (int i=0; i < getLength(); i++) 
                {
                    String columnName = getColumnName(i+1);
                    if (getColumnType(i+1) == Types.LONGVARBINARY) 
                    {
                        InputStream stream = rs.getBinaryStream(i+1);
                        recs.put(columnName, stream);
                    }
                    else if (getColumnType(i+1) == Types.BLOB) 
                    {
                        Blob blob = (Blob)rs.getBlob(i+1);
                        recs.put(columnName, blob);
                    }                      
                    else 
                    {
                        String columnValue = rs.getString(i+1);
                        recs.put(columnName, columnValue);
                    }
                }
                results.add(recs);
            }
    	}
        catch (SQLException e) 
        {
            message = e.getMessage();
            log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.QueryGeneric", "error_QueryGeneric_setData") + "...", e);
    	}
    }

	/**
	 * Regresa el numero de registros que contiene la tabla.
	 * 
	 * @return <b>int</b> Nivel de cache.
	 */
    public int loadCache() {
        int x = results.size();
        return x;
    }

	/**
	 * Establece los metadatos de la tabla.
	 * 
	 * @param md ResultSetMetaData de la instancia que lo esta llamando.
	 */
    public void setMetadata(ResultSetMetaData md) {
        try {
            length = md.getColumnCount();
            types = new int[length];
            names = new ArrayList(length);
            for(int x = 0; x < length; x++) {
                names.add(md.getColumnName(x + 1));
                types[x] = md.getColumnType(x + 1);
            }
        }
        catch(Exception e) {
			message = e.toString();
            log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.QueryGeneric", "error_QueryGeneric_setMetadata_ReadMetadataError") + "...", e);
        }
    }

	/**
	 * Obtiene las claves primarias de la tabla que representa la consulta.
	 * 
	 * @return <b>Iterator</b> Claves primarias.
	 */
    public Iterator getPrimaryKeys() {
        return primaryKeys.iterator();
    }

	/**
	 * Agrega una clave primaria.
	 * 
	 * @param pk Indice de la columna que contiene la clave primaria.
	 */
    public void addPrimaryKey(int pk) {
        primaryKeys.add(names.get(pk - 1));
    }

	/**
	 * Agrega una clave primaria.
	 * 
	 * @param name Nombre de la columna que contiene la clave primaria.
	 */
    public void addPrimaryKey(String name) {
        primaryKeys.add(name);
    }

	/**
	 * Obtiene el nombre de columna de la tabla que lo llama.
	 * 
	 * @return <b>String</b> Nombre de la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public String getColumnName(int x) {
        return (String)names.get(x - 1);
    }

	/**
	 * Obtiene los nombres de las columnas de la tabla que lo llama.
	 * 
	 * @return <b>Iterator</b> Nombres de las columnas.
	 */
    public Iterator getColumnNames() {
        return names.iterator();
    }

	/**
	 * Indica si la columna existe en la tabla que lo llama.
	 * 
	 * @return <b>boolean</b> true si la tabla contiene la columna, false en caso contrario.
	 * 
	 * @param name Nombre de la columna.
	 */
    public boolean existColumnName(String name) {
        return names.contains(name);
    }

	/**
	 * Obtiene el tipo de la columna.
	 * 
	 * @return <b>int</b> Tipo de la columna.
	 * 
	 * @param x Indice de la columna.
	 */
    public int getColumnType(int x) {
        return types[x - 1];
    }

	/**
	 * Obtiene el tipo de la columna.
	 * 
	 * @return <b>int</b> Tipo de la columna.
	 * 
	 * @param name Nombre de la columna.
	 */
    public int getColumnType(String name) {
        try {
            return types[names.indexOf(name)];
        }catch (Exception e) {
            return -1;
        }
    }

	/**
	 * Actualiza los datos disponibles.
	 * 
	 * @param query Consulta que se realiza.
	 * @param results Vector de resultados.
	 */
    public void sendDBNotify(String query, Object results) {
        if(cachelevel == 2 || cachelevel == 1) {
			recs.put(query, results);
        }
    }

	/**
	 * Obtiene el numero de columnas.
	 * 
	 * @return <b>int</b> Numero de columnas.
	 */
    public int getLength() {
        return length;
    }

	/**
	 * Obtiene el nivel de cache actual.
	 * 
	 * @return <b>int</b> Nivel de cache.
	 */
    public int getCacheLevel() {
        return cachelevel;
    }

	/**
	 * Establece el nivel de cache.
	 *  
	 * @param cachelevel Nivel de cache.
	 */
    public void setCacheLevel(int cachelevel) {
        this.cachelevel = cachelevel;
    }
}