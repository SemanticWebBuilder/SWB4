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

import java.sql.*;
import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBObserver;

/**
 * Clase que permite administrar una tabla de base de datos.
 *
 * @author Infotec
 * @version 1.1
 */
public class TableGeneric implements SWBObserver {
    Logger log = SWBUtils.getLogger(TableGeneric.class);

	/**
	 * Numero de registros de la tabla.
	 */
    private int length;
    
	/**
	 * Conjunto de tipos de dato de las columnas de la tabla.
	 */
    private int types[];
    
	/**
	 * Nivel de cache.
	 */                                                                                                        
	private int cachelevel;
	
	/**
	 * Nombre de la base de datos.
	 */
    private String dbcon;
    
	/**
	 * Datos de un registro.
	 */
	private HashMap recs;
	
	/**
	 * Schema de la base de datos.
	 */
	private HashMap schema;
	
	/**
	 * Argumentos externos.
	 */
	private HashMap data;
	
	/**
	 * Nombre de la tabla.
	 */
    private String tablename;
    
	/**
	 * Lista de nombres de columnas.
	 */
    private ArrayList names;
    
    /**
	 * Lista de nombres de tablas.
	 */
    private ArrayList tables;
    
    /**
	 * Lista de claves primarias.
	 */
    private ArrayList primaryKeys;
    
	/**
	 * Conjunto de resultados de una consulta
	 */
	private ArrayList results;
	
	/**
	 * Metadatos de las columnas de la tabla de base de datso
	 */
	private Vector columns;
			
	/**
	 * Indica que el recurso no esta en memoria.
	 */
    public static final int NO_CACHE = 0;
    
	/**
	 * Indica que el recurso tiene al menos un registro en memoria.
	 */
    public static final int FLY_CACHE = 1;
    
	/**
	 * Indica que todos los registros de la tabla estan en memoria.
	 */
    public static final int FULL_CACHE = 2;

    /**
	 * Constructor.
	 *
	 * @param db Nombre de la base de datos.
	 */
    public TableGeneric(String dbcon) {
        this.dbcon = dbcon;
        schema = new HashMap();
        setTables();
    }
    
    /**
	 * Constructor.
	 * 
	 * @param name Nombre de la tabla.
	 * @param db Nombre de la base de datos.
	 * 
	 * @throws <b>SQLException</b> si ocurre error al obtener MetaData.
	 */
    public TableGeneric(String name, String db) throws SQLException {
        this(name, db, 0);
    }

	/**
	 * Constructor.
	 * 
	 * @param name Nombre de la tabla.
	 * @param db Nombre de la base de datos.
	 * @param cachelevel Nivel de cache para manejo en memoria.
	 * 
	 * @throws <b>SQLException</b> si ocurre error al obtener MetaData.
	 */
    public TableGeneric(String name, String db, int cachelevel) throws SQLException {
        names = null;
        length = 0;
        dbcon = "";
        tablename = "";
        recs = new HashMap();
        this.cachelevel = 0;
        this.cachelevel = cachelevel;
        primaryKeys = new ArrayList();
        Connection con = SWBUtils.DB.getConnection(db);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM " + name);
        setMetadata(rs.getMetaData());
        rs.close();
        st.close();
        con.close();
        dbcon = db;
        tablename = name;
    }

	/**
	 * Consulta el nivel de cache en memoria.
	 * 
	 * @return <b>int</b> Nivel actual de cache.
	 */
    public int getCacheSize() {
        return recs.size();
    }

	/**
	 * Obtiene informacion de una consulta a la tabla.
	 * 
	 * @return <b>HashMap</b> Registros de la consulta.
	 */
    public HashMap getHashData() {
        return recs;
    }
    
    /**
	 * Obtiene schema de la base de datos.
	 * 
	 * @return <b>HashMap</b> Schema de la base de datos.
	 */
    public HashMap getSchema() {
        return schema;
    }
    
    public void setSchema(HashMap schema) {
        this.schema=schema;
        setTables();
    }
    
    
	/**
	 * Obtiene nombre de la base de datos.
	 * 
	 * @return <b>String</b> Nombre de la base de datos.
	 */
    public String getConexion() {
        return dbcon;
    }

    /**
	 * Establece esquema de tablas de la base de datos.
	 */
    public void setTables() {
        Connection con = null;
        ResultSet resultSet = null;
        tables = new ArrayList(length);
        try {
            String[] types = {"TABLE"};
	        con = SWBUtils.DB.getConnection(dbcon);
	        DatabaseMetaData dbmd = con.getMetaData();
	        resultSet = dbmd.getTables(null, null, "%", types);
	        while (resultSet.next()) {
	            schema.put(resultSet.getString(3), resultSet.getString(2));
	            tables.add(resultSet.getString(3));
	        }
	        con.close();
	        resultSet.close();
	    }catch (SQLException e) {
	        try {
    			if (resultSet != null)
    				resultSet.close();
    			if (con != null)
    				con.close();
    		}catch (SQLException ex){
    		    log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "error_TableGeneric_closeSets"), ex);
    		}
	        log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "error_TableGeneric_getTables"), e);
	    }
    }

    /**
	 * Establece esquema de columnas de la tabla de base de datos.
	 */
    public void setColumns() {
        Connection con = null;
        columns = new Vector();
        ResultSet resultSet = null;
        try {
	        con = SWBUtils.DB.getConnection(dbcon);
	        DatabaseMetaData dbmd = con.getMetaData();
	        resultSet = dbmd.getColumns(null, "", tablename, null);
	        while (resultSet.next()) {
	            HashMap columnMetaData = new HashMap();
	            columnMetaData.put("DATA_TYPE", resultSet.getString("DATA_TYPE"));
	            columnMetaData.put("COLUMN_SIZE", resultSet.getString("COLUMN_SIZE"));
	            columnMetaData.put("IS_NULLABLE", resultSet.getString("IS_NULLABLE"));
	            columnMetaData.put("COLUMN_DEF", resultSet.getString("COLUMN_DEF"));
	            columnMetaData.put("COLUMN_NAME", resultSet.getString("COLUMN_NAME"));
	            columns.add(columnMetaData);
	        }
	        con.close();
	        resultSet.close();
	    }catch (SQLException e) {
	        try {
    			if (resultSet != null)
    				resultSet.close();
    			if (con != null)
    				con.close();
    		}catch (SQLException ex){
    		    log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "error_TableGeneric_closeSets"), ex);
    		}
	        log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "error_TableGeneric_getColumns"), e);
	    }
    }
    
    /**
	 * Obtiene el numero de registros que contiene la tabla.
	 * 
	 * @return <b>int</b> Nivel de cache.
	 */
    public int loadCache() throws SQLException {
        int x = 0;
        if(cachelevel == 2) {
            Connection con = SWBUtils.DB.getConnection(dbcon);
            Statement st = con.createStatement();
            ResultSet rs;
            for(rs = st.executeQuery("SELECT * FROM " + tablename); rs.next();) {
                RecordGeneric rec = new RecordGeneric(this, rs);
                recs.put(rec.getHashKey(), rec);
                x++;
            }
            log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "log_TableGeneric_loadCache_ReginMem") + " " + tablename + ":\t" + recs.size());
            rs.close();
            st.close();
            con.close();
        }
        return x;
    }
	
	 /**
	  * Obtiene la clave primaria de la tabla.
	  * 
	  * @param table Nombre de la tabla de la que se desea obtener la clave.
	  */
	 public String getKey(String table) {
	     Connection con = null;
	     ResultSet resultSet = null;
	     String nameKey = new String();
	     try {
	         con = SWBUtils.DB.getConnection(dbcon);
		     DatabaseMetaData dbmd = con.getMetaData();
		     resultSet = dbmd.getPrimaryKeys(null, (String)schema.get(table), table);				    
		     while (resultSet.next()) {
		         nameKey += "," + resultSet.getString(4);
		     }
		     if (!"".equals(nameKey))
		         nameKey = nameKey.substring(1);
		     con.close();
		     resultSet.close();
	     }catch (SQLException e) {
	         try {
	             if (resultSet != null)
	                 resultSet.close();
	             if (con != null)
	                 con.close();
	         }catch (SQLException ex){
	             log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "error_TableGeneric_closeSets"), e);
	         }
	         log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "error_TableGeneric_getKey"),e);
	     }
	     return nameKey;
	 }
	 
	/**
	 * Obtiene el nombre de la tabla.
	 * 
	 * @return <b>String</b> Nombre de la tabla.
	 */
    public String getTablename() {
        return tablename;
    }

	/**
	 * Establece el nombre de la tabla.
	 * 
	 * @param tableName Nombre de la tabla.
	 */
    public void setTablename(String tablename) {
        this.tablename = tablename;
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
            tablename = md.getTableName(1);
            for(int x = 0; x < length; x++) {
                names.add(md.getColumnName(x + 1));
                types[x] = md.getColumnType(x + 1);
            }

        }
        catch(Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "error_TableGeneric_setMetadata_ReadMetadataError") + "...", e);
        }
    }

	/**
	 * Obtiene el valor maximo en una columna de la tabla que lo llama.
	 * 
	 * @return <b>long</b> Valor maximo.
	 * 
	 * @param x Numero de la columna.
	 */
    public long getMax(int x) {
        return getMax(getColumnName(x));
    }

	/**
	 * Obtiene el valor maximo en una columna de la tabla que lo llama.
	 * 
	 * @return <b>long</b> Valor maximo.
	 * 
	 * @param name Nombre de la columna.
	 */
    public long getMax(String name) {
        long max = -1L;
        try {
            Connection con = SWBUtils.DB.getConnection(dbcon);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT max(" + name + ") FROM " + tablename);
            if(rs.next())
                max = rs.getLong(1);
            else
                max = 0L;
            rs.close();
            st.close();
            con.close();
        }
        catch(Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "error_TableGeneric_getMax_ReadMaxError") + name + SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "error_TableGeneric_getMax_ReadMaxErrorTable") + " " + tablename,e);
        }
        return max;
    }

	/**
     * Obtiene las claves primarias de la tabla.
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
     * Obtiene el nombre de las tabla de la base de datos que lo instancia.
	 * 
	 * @return <b>Iterator</b> Nombres de las tablas.
	 */
    public Iterator getTables() {
        return tables.iterator();
    }
    
    /**
     * Obtiene los metadatos de las columnas de la tabla de la base de datos que lo instancia.
	 * 
	 * @return <b>Enumeration</b> Metadatos de las columnas.
	 */
    public Enumeration getColumnsMetaData() {
        return columns.elements();
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
        return types[names.indexOf(name)];
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
	 * Obtiene una nueva instancia de RecordGeneric.
	 * 
	 * @return <b>RecordGeneric</b>
	 */
	public RecordGeneric getNewRecordGeneric() {
		return new RecordGeneric(this);
	}

	/**
	 * Actualiza los datos disponibles.
	 * 
	 * @param s Accion que se realiza.
	 * @param obj RecordGeneric para actualizar la base de datos.
	 */
    public void sendDBNotify(String s, Object obj) {
        if(obj instanceof RecordGeneric) {
            RecordGeneric rec = (RecordGeneric)obj;
            String key = rec.getHashKey();
            if(cachelevel == 2 || cachelevel == 1)
                if(s.equals("remove"))
                    recs.remove(key);
                else
                if(s.equals("create")) {
                    if(recs.get(key) == null)
                        recs.put(key, obj);
                }else
                if((s.equals("load") || s.equals("update")) && recs.get(key) == null)
                    recs.put(key, obj);
        }
    }

	/**
	 * Obtiene una nueva instancia de RecordGeneric con las claves primarias.
	 * 
	 * @return <b>RecordGeneric</b> 
	 * 
	 * @param key Clave(s) primaria(s)
	 */
    public RecordGeneric getNewRecordGeneric(String key) {
        RecordGeneric rec = new RecordGeneric(this);
        if(primaryKeys.size() > 1) {
            StringTokenizer st = new StringTokenizer(key, "|");
            for(int x = 0; st.hasMoreTokens(); x++) {
                String t = st.nextToken();
                rec.setString((String)primaryKeys.get(x), t);
            }

        }else
        if(primaryKeys.size() == 1)
            rec.setString((String)primaryKeys.get(0), key);
        return rec;
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
	 * @param cacheLevel Nivel de cache.
	 */
    public void setCacheLevel(int cachelevel) {
        this.cachelevel = cachelevel;
    }
    
	/**
	 * Obtiene el RecordGeneric cargado con la clave que se le solicita.
	 * 
	 * @return <b>RecordGeneric</b>
	 * 
	 * @param key Clave primaria.
	 */
    public RecordGeneric getRecordGeneric(long key) {
        RecordGeneric rec = null;
        try {
            if(cachelevel == 0) {
                rec = getNewRecordGeneric();
                rec.setLong((String)primaryKeys.get(0), key);
                rec.load();
            }else
            if(cachelevel == 1) {
                rec = (RecordGeneric)recs.get("" + key);
                if(rec == null) {
                    rec = getNewRecordGeneric();
                    rec.setLong((String)primaryKeys.get(0), key);
                    rec.load();
                }
            }else
            if(cachelevel == 2)
                rec = (RecordGeneric)recs.get("" + key);
        }
        catch(Exception e) {
            rec = null;
        }
        return rec;
    }

	/**
	 * Obtiene el registro de la clave que se le solicita.
	 * 
	 * @return <b>RecordGeneric</b> Registro de base de datos
	 * 
	 * @param key Clave(s) primaria(s).
	 */
    public RecordGeneric getRecordGeneric(String key) {
        RecordGeneric rec = null;
        try {
            if(cachelevel == 2 || cachelevel == 1)
                rec = (RecordGeneric)recs.get(key);
            if(cachelevel == 0 || cachelevel == 1 && rec == null) {
                rec = getNewRecordGeneric();
                if(primaryKeys.size() > 1) {
                    StringTokenizer st = new StringTokenizer(key, "|");
                    for(int x = 0; st.hasMoreTokens(); x++) {
                        String t = st.nextToken();
                        rec.setString((String)primaryKeys.get(x), t);
                    }
                }else
                if(primaryKeys.size() == 1)
                    rec.setString((String)primaryKeys.get(0), key);
                rec.load();
            }
        }
        catch(Exception e) {
            rec = null;
        }
        return rec;
    }

	/**
	 * Obtiene el registro asociado a una consulta a la tabla.
	 * 
	 * @return <b>HashMap</b> nombre de columna - valor de columna.
	 * 
	 * @param name Nombre del atributo.
	 * @param value Valor del atributo.
	 */
    public HashMap getRecordGenerics(String name, String value) {
        HashMap data = new HashMap();
        data.put(name, value);
        if(cachelevel == 0 || cachelevel == 1)
            try {
                primaryKeys = new ArrayList();
            	addPrimaryKey(name);
                RecordGeneric record = new RecordGeneric(this);
                record.setData(data);
                record.load();
                data = record.getData();
            }
            catch(Exception e) {
            	data.clear();
                log.error(SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "error_TableGeneric_getRecordGenerics_GenerateIteratorError") + " " + tablename, e);
            }
        else
            try {
                if (recs.containsKey(name)) {
                    data.put(name, recs.get(name)); 
                }
            }
            catch(Exception e) {
                log.error( SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.database.TableGeneric", "error_TableGeneric_getRecordGenerics_GenerateIteratorError") + " " + tablename, e);
            }
        return data;
    }

	/**
	 * Obtiene el registro asociado a una consulta a la tabla.
	 * 
	 * @return <b>HashMap</b> nombre de columna - valor de columna.
	 * 
	 * @param name Nombre del atributo.
	 * @param value Valor del atributo.
	 */
    public HashMap getRecordGenerics(String name, Long value) {
        HashMap data = new HashMap();
        data.put(name, value);
        if(cachelevel == 0 || cachelevel == 1)
            try {
                primaryKeys = new ArrayList();
				addPrimaryKey(name);
				RecordGeneric record = new RecordGeneric(this);
				record.setData(data);
				record.load();
                data = record.getData();
            }
            catch(Exception e) {
				data.clear();
                log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_TableGeneric_getRecordGenerics_GenerateIteratorError") + " " + tablename, e);
            }
        else
            try {
                if (recs.containsKey(name)) {
                    data.put(name, recs.get(name)); 
                }
            }
            catch(Exception e) {
                log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_TableGeneric_getRecordGenerics_GenerateIteratorError") + " " + tablename, e);
            }
        return data;
    }
}