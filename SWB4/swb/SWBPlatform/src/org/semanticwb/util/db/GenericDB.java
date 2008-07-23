/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.util.db;

import org.semanticwb.SWBUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import java.util.StringTokenizer;
import org.hibernate.dialect.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBInstance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author juan.fernandez
 */
public class GenericDB {

    static Logger log=SWBUtils.getLogger(GenericDB.class);
    static final String DB_MYSQL = "MYSQL";
    static final String DB_ORACLE = "ORACLE";
    static final String DB_INFORMIX = "INFORMIX";
    static final String DB_SQLSERVER = "SQLSERVER";
    static final String DB_POSTGRESSQL = "POSTGRESSQL";
    static final String DB_HSSQL = "HSQL";
    static final String DB_POINTBASE = "POINTBASE";
    static final String DB_SYBASE = "SYBASE"; //
    static final String DB_DB2 = "DB2";
    
    //static final String SQL_ARRAY = "ARRAY";
    //static final String SQL_TEXT = "TEXT";
    static final String SQL_CHAR = "CHAR";
    static final String SQL_VARCHAR = "VARCHAR";
    //static final String SQL_LONGVARCHAR = "LONGVARCHAR";
    static final String SQL_NUMERIC = "NUMERIC";
    //static final String SQL_DECIMAL = "DECIMAL";
    static final String SQL_BIT = "BIT";
    static final String SQL_BLOB = "BLOB";
    //static final String SQL_TINYINT = "TINYINT";
    static final String SQL_SMALLINT = "SMALLINT";
    static final String SQL_INTEGER = "INTEGER";
    //static final String SQL_BIGINT = "BIGINT";
    //static final String SQL_REAL = "REAL";
    static final String SQL_FLOAT = "FLOAT";
    static final String SQL_DOUBLE = "DOUBLE";
    //static final String SQL_BINARY = "BINARY";
    //static final String SQL_VARBINARY = "VARBINARY";
    //static final String SQL_LONGVARBINARY = "LONGVARBINARY";
    static final String SQL_DATE = "DATE";
    static final String SQL_TIME = "TIME";
    static final String SQL_TIMESTAMP = "TIMESTAMP";
    //static final String SQL_BOOLEAN = "BOOLEAN";
    static final String SQL_CLOB = "CLOB";
    //static final String SQL_DATALINK = "DATALINK";
    //static final String SQL_DISTINCT = "DISTINCT";
    //static final String SQL_JAVA_OBJECT = "JAVA_OBJECT";
    //static final String SQL_NULL = "NULL";
    //static final String SQL_OTHER = "OTHER";
    //static final String SQL_REF = "REF";
    //static final String SQL_STRUCT = "STRUCT";
    
    private static final String PK = "PRIMARYKEY_INI";
    private static final String COLUMN = "COLUMN";
    private static final String INDTYPE = "INDEX_TYPE";
    private static final String INDORDER = "INDEX_ORDER";
    private static final String PRIMARYKEY = "#COLUMN_NAME#";
    private static final String FK = "ALTER TABLE #TABLE_NAME# ADD CONSTRAINT #CNAME# ";
    private static final String FOREIGNKEY = "FOREIGN KEY ( ";
    private static final String FK_COLUMN = "#COLUMN_NAME#";
    private static final String FK_REFERENCE = "REFERENCES #TABLE_NAME# ( ";
    private static final String INDEX_INI = "CREATE #INDEX_TYPE# INDEX #INDEX_NAME# ON #TABLE_NAME# ( ";
    private static final String INDEX = "#COLUMN_NAME# #ORDER#";
    
    private HashMap hmDialect=null;
    private HashMap hmSQLType=null;
    private HashMap hmSyntax=null;

    public void GenericDB() {

        if (null == hmDialect) {
            loadDialects();
        }
        if (null == hmSQLType) {
            loadSQLTypes();
        }
        if (null == hmSyntax) {
            loadSyntax();
        }
    }

    public String getSQLScript(String XML, String dbname) throws SQLException {
        String retSQL = null;
        Document dom = null;

        if (null != XML) {
            dom = SWBUtils.XML.xmlToDom(XML);
        }
        dbname = dbname.toLowerCase();
        if (dbname.lastIndexOf("informix") > -1) {
            dbname = DB_INFORMIX;
        } 
        else if (dbname.lastIndexOf("microsoft sql server") > -1) {
            dbname = DB_SQLSERVER;
        }
        else if (dbname.lastIndexOf("mysql") > -1) {
            dbname = DB_MYSQL;
        }
        else if (dbname.lastIndexOf("adaptive server enterprise") > -1) {
            dbname = DB_SYBASE;
        }
        else if (dbname.lastIndexOf("postgresql") > -1) {
            dbname = DB_POSTGRESSQL;
        }
        else if (dbname.lastIndexOf("oracle") > -1) {
            dbname = DB_ORACLE;
        }
        else if (dbname.lastIndexOf("hsql") > -1) {
            dbname = DB_HSSQL;
        }
        else if (dbname.lastIndexOf("pointbase") > -1) {
            dbname = DB_POINTBASE;
        }
        else if (dbname.lastIndexOf("db2") > -1) {
            dbname = DB_DB2;
        }
        if (null != dom) {
            try 
            {
                retSQL = getSQLScript(XML, dbname);       
            } 
            catch (Exception e) {
                log.error("Error al generar el SCRIPT SQL. GenericDB.getSQLScript()",e);
            }
        }
        return retSQL;
    }

    public boolean executeSQLScript(String XML, String dbname, String poolname) throws SQLException {

        Connection conn = null;
        Statement st = null;
        StringTokenizer sto = null;
        String sqlScript = null;
        String tmp_conn = null;
        String query = null;
        int x = 0;
        try
            {
                sqlScript = getSQLScript(XML, dbname);
                tmp_conn = null;
                if(poolname==null)
                {
                    tmp_conn = (String) SWBInstance.getEnv("wb/db/nameconn","wb");
                }
                else
                {
                    tmp_conn = poolname;
                }
                
                conn = SWBUtils.DB.getConnection(tmp_conn,"GenericDB.executeSQLScript()");
                st = conn.createStatement();
                if(sqlScript!=null)
                {
                    sto=new StringTokenizer(sqlScript,";");
                    while(sto.hasMoreTokens())
                    {
                        query = sto.nextToken();
                        try
                        {
                            if(query.trim().length()>0)
                                x = st.executeUpdate(query);
                        }
                        catch(Exception e)
                        {
                            log.error("Error on method executeSQLScript() of GenericDB trying to execute next code : "+ query,e);
                            throw e;
                        }
                    }
                }
                if(st != null) st.close();
                if(conn != null) conn.close();
                
            }
            catch(Exception e)
            {
                log.error("Error on method executeSQLScript() on GenericDB util.",e);
            }
            finally
            {
                try{if(st != null) st.close();st = null;}catch(Exception e1){}
                try{if(conn != null) conn.close();conn = null;}catch(Exception e2){}
            }
        
        return true;
    }
    
    public String getColumnSyntax(String dbtype, String attr) 
    {
        if(null==hmSyntax)
        {
            loadSyntax();
        }
        return (String) ((HashMap) hmSyntax.get(dbtype.toUpperCase())).get(attr);
    }

    public int getSQLType(String coltype) {
        if(null==hmSQLType)
        {
            loadSQLTypes();
        }
        return hmSQLType.get(coltype) != null ? Integer.parseInt((String) hmSQLType.get(coltype)) : -1;
    }

    public String getDBDialect(String DBName) {
        if(null==hmDialect)
        {
            loadDialects();
        }
        return (String) hmDialect.get(DBName.toUpperCase());
    }
    
    public String getSchema(String strXML, String DBName) {
        StringBuffer strBuff = new StringBuffer();
        String LFCR = " ";
        if (DBName == null) {
            return null;
        }
        final String eleFin = "); ";
        Document dom = SWBUtils.XML.xmlToDom(strXML);
        
        if (dom != null) {

            DialectFactory dialectF = new DialectFactory();
            Dialect dialect = dialectF.buildDialect(getDBDialect(DBName));

            Element root = dom.getDocumentElement();
            NodeList tbEle = root.getElementsByTagName("table");
            if (tbEle != null && tbEle.getLength() > 0) {

                for (int i = 0; i < tbEle.getLength(); i++) {
                    Element ele = (Element) tbEle.item(i);
                    if (ele.getNodeName().equals("table")) {
                        strBuff.append(dialect.getCreateTableString() + " " + ele.getAttribute("name") + " ( "+LFCR);
                        NodeList colEle = ele.getElementsByTagName("column");
                        if (colEle != null && colEle.getLength() > 0) {
                            String colSyntax = getColumnSyntax(DBName, COLUMN);
                            String tmpCol = "";
                            String comilla = "";
                            boolean haveCLOB = false;
                            for (int j = 0; j < colEle.getLength(); j++) {
                                Element col = (Element) colEle.item(j);
                                tmpCol = colSyntax;
                                comilla = "";
                                String tmpVal = col.getAttribute("id");

                                if (tmpVal != null) {
                                    tmpCol = tmpCol.replaceAll("#COLUMN_NAME#", tmpVal);
                                }

                                if (col.getAttribute("signed") != null && col.getAttribute("signed").toLowerCase().equals("false")) {
                                    tmpCol = tmpCol.replaceAll("#SIGNED#", "UNSIGNED");
                                } else {
                                    tmpCol = tmpCol.replaceAll("#SIGNED#", "");
                                }

                                tmpVal = col.getAttribute("type").trim().toUpperCase();

                                String tmpSize = col.getAttribute("size");  // para el tipo de datos correspondiente dependiendo el tama?o.
                                if (tmpVal != null && tmpSize != null && tmpSize.trim().length() > 0) //&&col.getAttribute("size")!=null
                                {
                                    int codigo = 0;
                                    String tipocol = "";
                                    if(DBName.equals(DB_INFORMIX))
                                    {
                                         if(tmpVal.equals(SQL_CLOB)&&!haveCLOB)
                                        {
                                            tipocol = "clob";
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);

                                        } else if(tmpVal.equals(SQL_CLOB)&&haveCLOB)
                                        {
                                            tipocol = "text in table";
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);

                                        } else if(tmpVal.equals(SQL_BLOB))
                                        {
                                            tipocol = "blob";
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);

                                        }
                                        else 
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(tmpVal), Integer.parseInt(col.getAttribute("size")), 0, 0));

                                        }
                                    }
                                    else if(DBName.equals(DB_SYBASE))
                                    {
                                        if(tmpVal.equals(SQL_CLOB)&&haveCLOB)
                                        {
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(SQL_VARCHAR), Integer.parseInt(col.getAttribute("size")), 0, 0));
                                        }
                                        else
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(tmpVal), Integer.parseInt(col.getAttribute("size")), 0, 0));
                                        }
                                    }
                                    else if(DBName.equals(DB_POINTBASE))
                                    {
                                         if(tmpVal.equals(SQL_CLOB)&&!haveCLOB)
                                        {
                                            tipocol = "clob";
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        } else if(tmpVal.equals(SQL_CLOB)&&haveCLOB)
                                        {
                                            codigo = getSQLType(SQL_VARCHAR);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(SQL_VARCHAR), 4000, 0, 0));
                                        } else if(tmpVal.equals(SQL_BLOB))
                                        {
                                            tipocol = "blob";
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        }
                                        else
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(tmpVal), Integer.parseInt(col.getAttribute("size")), 0, 0));
                                        }
                                    }
                                    else if(DBName.equals(DB_ORACLE))
                                    {
                                        if(tmpVal.equals(SQL_CLOB)&&haveCLOB)
                                        {
                                            codigo = getSQLType(SQL_VARCHAR);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(SQL_VARCHAR), 4000, 0, 0));
                                        } 
                                        else
                                        {
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(tmpVal), Integer.parseInt(col.getAttribute("size")), 0, 0));
                                        }
                                    }
                                    else
                                    {
                                        tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(tmpVal), Integer.parseInt(col.getAttribute("size")), 0, 0));
                                    }
                                    if (tmpVal.equals(SQL_VARCHAR) || tmpVal.equals(SQL_CHAR)) {
                                        comilla = "'";
                                    }
                                } else if (tmpVal != null && ((tmpSize != null && tmpSize.trim().length() == 0) || (col.getAttribute("size") == null))) {                                    
                                    int codigo = 0;
                                    String tipocol = "";
                                    if(DBName.equals(DB_INFORMIX))
                                    {
                                        if(tmpVal.equals(SQL_CHAR))
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(tmpVal), 1, 0, 0));
                                        } 
                                        else if(tmpVal.equals(SQL_CLOB)&&!haveCLOB)
                                        {
                                            tipocol = "clob";
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        } else if(tmpVal.equals(SQL_CLOB)&&haveCLOB)
                                        {
                                            tipocol = "text in table";
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        }else if(tmpVal.equals(SQL_BLOB))
                                        {
                                            tipocol = "blob";
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        }
                                        else
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        }
                                    }
                                    else if(DBName.equals(DB_SYBASE))
                                    {
                                        if(tmpVal.equals(SQL_CLOB)&&haveCLOB)
                                        {
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(SQL_VARCHAR), 4000, 0, 0));
                                        }
                                        else
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        }
                                    }
                                    else if(DBName.equals(DB_DB2))
                                    {
                                        if(tmpVal.equals(SQL_VARCHAR))
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(tmpVal), 4000, 0, 0));
                                        }
                                        else if(tmpVal.equals(SQL_CLOB))
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(tmpVal), 4000, 0, 0));
                                        } else if(tmpVal.equals(SQL_BLOB))
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType(tmpVal), 4000, 0, 0));
                                        }
                                        else
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        }
                                    }
                                    else if(DBName.equals(DB_POINTBASE))
                                    {
                                         if(tmpVal.equals(SQL_CLOB)&&!haveCLOB)
                                        {
                                            tipocol = "clob";
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        } else if(tmpVal.equals(SQL_CLOB)&&haveCLOB)
                                        {
                                            codigo = getSQLType(SQL_VARCHAR);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType("VARCHAR"), 4000, 0, 0));
                                        } else if(tmpVal.equals(SQL_BLOB))
                                        {
                                            tipocol = "blob";
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        }
                                        else
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        }
                                    }
                                    else if(DBName.equals(DB_ORACLE))
                                    {
                                        if(tmpVal.equals(SQL_CLOB)&&haveCLOB)
                                        {
                                            codigo = getSQLType(SQL_VARCHAR);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", dialect.getTypeName(getSQLType("VARCHAR"), 4000, 0, 0));
                                        } 
                                        else
                                        {
                                            codigo = getSQLType(tmpVal);
                                            tipocol = dialect.getTypeName(codigo);
                                            tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                        }
                                    }
                                    else
                                    {
                                        codigo = getSQLType(tmpVal);
                                        tipocol = dialect.getTypeName(codigo);
                                        tmpCol = tmpCol.replaceAll("#TYPE#", tipocol);
                                    }
                                        
                                    if (tmpVal.equals(SQL_VARCHAR) || tmpVal.equals(SQL_CHAR)) {
                                        comilla = "'";
                                    }
                                }
                                if (col.getAttribute("acceptNulls") != null && col.getAttribute("acceptNulls").toLowerCase().equals("false")) {
                                    tmpCol = tmpCol.replaceAll("#NULL#", "NOT NULL");
                                } else {
                                    tmpCol = tmpCol.replaceAll("#NULL#", "");
                                }

                                if (col.getAttribute("default") != null && col.getAttribute("default").length()>0 ) {
                                    tmpCol = tmpCol.replaceAll("#DEFAULT#", "DEFAULT " + comilla + col.getAttribute("default") + comilla);
                                } else {
                                    tmpCol = tmpCol.replaceAll("#DEFAULT#", "");
                                }

                                strBuff.append(tmpCol);

                                if ((j+1) < colEle.getLength()) {
                                    strBuff.append(","+LFCR);
                                }
                                if(tmpVal.equals(SQL_CLOB)&&(DBName.equals(DB_ORACLE)||DBName.equals(DB_SYBASE)||DBName.equals(DB_INFORMIX)||DBName.equals(DB_POINTBASE)))
                                {
                                    haveCLOB=true;
                                }
                            }
                        }
                        // Cerrando tabla
                        strBuff.append(eleFin + LFCR+LFCR);

                    }
                }
            }

            // PRIMARY KEY
            NodeList tbPK = root.getElementsByTagName("primarykey");
            if (tbPK != null && tbPK.getLength() > 0) {
                for (int i = 0; i < tbPK.getLength(); i++) {
                    Element ele = (Element) tbPK.item(i);
                    if (ele.getNodeName().equals("primarykey")) {
                        String tbname = ele.getAttribute("table");
                        String colINISyntax = getColumnSyntax(DBName, PK);
                        if (null != tbname) {
                            colINISyntax = colINISyntax.replaceAll("#TABLE_NAME#", tbname);
                            if (colINISyntax.indexOf("#CNAME#") > -1) {
                                colINISyntax = colINISyntax.replaceAll("#CNAME#", tbname + "_pk" + i);
                            }
                            strBuff.append(colINISyntax+LFCR);

                            NodeList colEle = ele.getElementsByTagName("colpk");
                            if (colEle != null && colEle.getLength() > 0) {
                                String colSyntax = PRIMARYKEY;
                                String tmpCol = "";

                                for (int j = 0; j < colEle.getLength(); j++) {
                                    Element col = (Element) colEle.item(j);
                                    tmpCol = colSyntax;
                                    String tmpVal = col.getAttribute("id");

                                    if (tmpVal != null) {
                                        tmpCol = tmpCol.replaceAll("#COLUMN_NAME#", tmpVal);
                                    }
                                    
                                    strBuff.append(tmpCol);
                                    if ((j+1) < colEle.getLength()) {
                                        strBuff.append(","+LFCR);
                                    }
                                }
                            }
                        }
                        // Cerrando alter primary key
                        strBuff.append(eleFin+LFCR+LFCR);
                    }
                }
            }
            
            // INDICES
            NodeList tbIND = root.getElementsByTagName("index");
            if (tbIND != null && tbIND.getLength() > 0) {
                for (int i = 0; i < tbIND.getLength(); i++) {
                    Element ele = (Element) tbIND.item(i);
                    if (ele.getNodeName().equals("index")) {
                        String tbname = ele.getAttribute("table");
                        String indType = ele.getAttribute("type");
                        String indName = ele.getAttribute("name");
                        String colINISyntax = INDEX_INI;
                        if (null != tbname && indType!=null) {
                            colINISyntax = colINISyntax.replaceAll("#TABLE_NAME#", tbname);
                            colINISyntax = colINISyntax.replaceAll("#INDEX_NAME#", indName);
                            String tmp = getColumnSyntax(DBName, INDTYPE);
                            if (indType!=null && indType.trim().length()>0&&tmp.indexOf(indType)>-1) {
                                
                                if(null!=tmp && tmp.trim().length()>0)
                                {
                                    colINISyntax = colINISyntax.replaceAll("#INDEX_TYPE#", indType);
                                }
                                else
                                {
                                    colINISyntax = colINISyntax.replaceAll("#INDEX_TYPE#", "");
                                }
                            }
                            else
                            {
                                colINISyntax = colINISyntax.replaceAll("#INDEX_TYPE#", "");
                            }
                            strBuff.append(colINISyntax);
                            
                            // revisando las columnas
                            NodeList colEle = ele.getElementsByTagName("colindex");
                            if (colEle != null && colEle.getLength() > 0) {
                                String colSyntax = INDEX;
                                String tmpCol = "";

                                for (int j = 0; j < colEle.getLength(); j++) {
                                    Element col = (Element) colEle.item(j);
                                    tmpCol = colSyntax;
                                    String tmpVal = col.getAttribute("id");

                                    if (tmpVal != null && tmpVal.trim().length()>0) {
                                        tmpCol = tmpCol.replaceAll("#COLUMN_NAME#", tmpVal);
                                    }
                                    
                                    tmpVal = col.getAttribute("order");
                                    tmp = getColumnSyntax(DBName, INDORDER);  // REVISANDO ORDENAMIENTO VALIDO
                                    if (tmpVal != null && tmpVal.trim().length()>0 && tmp.indexOf(tmpVal.trim().toUpperCase())>0) {
                                        
                                        if(null!=tmp && tmp.trim().length()>0 )
                                        {
                                            tmpCol = tmpCol.replaceAll("#ORDER#", tmpVal);
                                        }
                                        else
                                        {
                                            tmpCol = tmpCol.replaceAll("#ORDER#", "");
                                        }
                                    }
                                    else
                                        {
                                            tmpCol = tmpCol.replaceAll("#ORDER#", "");
                                        }
                                    
                                    strBuff.append(tmpCol);
                                    if ((j+1) < colEle.getLength()) {
                                        strBuff.append(","+LFCR);
                                    }
                                }
                            }
                        }
                        // Cerrando indice
                        strBuff.append(eleFin+LFCR+LFCR);
                    }
                }
            }
            // FOREIGN KEY  ---- FK = "ALTER TABLE #TABLE_NAME# ADD CONSTRAINT #CNAME# ";
            NodeList tbFK = root.getElementsByTagName("foreignkey");
            if (tbFK != null && tbFK.getLength() > 0) {
                for (int i = 0; i < tbFK.getLength(); i++) {
                    Element ele = (Element) tbFK.item(i);
                    if (ele.getNodeName().equals("foreignkey")) {
                        String tbname = ele.getAttribute("table");
                        String colINISyntax = FK;
                        if (null != tbname) {
                            colINISyntax = colINISyntax.replaceAll("#TABLE_NAME#", tbname);
                            colINISyntax = colINISyntax.replaceAll("#CNAME#", tbname + "_fk" + i);
                            strBuff.append(colINISyntax+LFCR);

                            NodeList columnsEle = ele.getElementsByTagName("columns");
                            if(columnsEle !=null && columnsEle.getLength() > 0)
                            {
                                strBuff.append(FOREIGNKEY+LFCR);
                                Element eleColumns = (Element) columnsEle.item(0);
                                NodeList colEle = eleColumns.getElementsByTagName("colpk");
                                if (colEle != null && colEle.getLength() > 0) {
                                    String colSyntax = FK_COLUMN;
                                    String tmpCol = "";
                                    for (int j = 0; j < colEle.getLength(); j++) {
                                        Element col = (Element) colEle.item(j);
                                        tmpCol = colSyntax;
                                        String tmpVal = col.getAttribute("id");

                                        if (tmpVal != null) {
                                            tmpCol = tmpCol.replaceAll("#COLUMN_NAME#", tmpVal);
                                        }
                                        else
                                        {
                                            tmpCol = tmpCol.replaceAll("#COLUMN_NAME#", "");
                                        }

                                        strBuff.append(tmpCol);
                                        if ((j+1) < colEle.getLength()) {
                                            strBuff.append(","+LFCR);
                                        }
                                    }
                                    strBuff.append(") ");
                                }
                            }
                            
                            NodeList refEle = ele.getElementsByTagName("reference");
                            if(refEle !=null && refEle.getLength() > 0)
                            {
                                for (int j = 0; j < refEle.getLength(); j++) 
                                {
                                    Element eleRef = (Element) refEle.item(i);
                                    tbname = eleRef.getAttribute("table");
                                    String refINISyntax = FK_REFERENCE;
                                    if (null != tbname) 
                                    {
                                        refINISyntax = refINISyntax.replaceAll("#TABLE_NAME#", tbname);
                                        strBuff.append(refINISyntax+LFCR);
                                        NodeList colsEle = eleRef.getElementsByTagName("columns");
                                        if(colsEle !=null && colsEle.getLength() > 0)
                                        {
                                                Element eleCols = (Element) colsEle.item(0);
                                                NodeList colEle = eleCols.getElementsByTagName("colpk");
                                                if (colEle != null && colEle.getLength() > 0) 
                                                {
                                                    String colSyntax = FK_COLUMN;
                                                    String tmpCol = "";
                                                    for (int k = 0; k < colEle.getLength(); k++) {
                                                        Element col = (Element) colEle.item(k);
                                                        tmpCol = colSyntax;
                                                        String tmpVal = col.getAttribute("id");
                                                        if (tmpVal != null) 
                                                        {
                                                            tmpCol = tmpCol.replaceAll("#COLUMN_NAME#", tmpVal);
                                                        }
                                                        else
                                                        {
                                                            tmpCol = tmpCol.replaceAll("#COLUMN_NAME#", "");
                                                        }
                                                        strBuff.append(tmpCol);
                                                        if ((k+1) < colEle.getLength()) 
                                                        {
                                                            strBuff.append(","+LFCR);
                                                        }
                                                    }
                                                }
                                           strBuff.append(")");
                                        }
                                    }
                                }
                            }                        
                            // Cerrando alter add foreign key
                            strBuff.append(";"+LFCR+LFCR);
                        }
                    }
                }
            }
        }
        return strBuff.toString();
    }
    
    
    
    public HashMap getDialects()
    {
        if(hmDialect==null)
        {
            loadDialects();
        }
        return hmDialect;
    }
    
    private void loadDialects()
    {
        hmDialect = new HashMap();

        hmDialect.put(DB_MYSQL, "org.hibernate.dialect.MySQLDialect");
        hmDialect.put(DB_ORACLE, "org.hibernate.dialect.Oracle9iDialect");
        hmDialect.put(DB_INFORMIX, "org.hibernate.dialect.InformixDialect");
        hmDialect.put(DB_SQLSERVER, "org.hibernate.dialect.SQLServerDialect");
        hmDialect.put(DB_HSSQL, "org.hibernate.dialect.HSQLDialect");
        hmDialect.put(DB_POINTBASE, "org.hibernate.dialect.PointbaseDialect");
        hmDialect.put(DB_POSTGRESSQL, "org.hibernate.dialect.PostgreSQLDialect");
        hmDialect.put(DB_SYBASE, "org.hibernate.dialect.SybaseDialect");
        hmDialect.put(DB_DB2, "org.hibernate.dialect.DB2Dialect");
    }
    
    private void loadSQLTypes()
    {
        hmSQLType = new HashMap();

        //hmSQLType.put(SQL_ARRAY, Integer.toString(java.sql.Types.ARRAY));
        //hmSQLType.put(SQL_BIGINT, Integer.toString(java.sql.Types.BIGINT));
        //hmSQLType.put(SQL_BINARY, Integer.toString(java.sql.Types.BINARY));
        hmSQLType.put(SQL_BIT, Integer.toString(java.sql.Types.BIT));
        hmSQLType.put(SQL_BLOB, Integer.toString(java.sql.Types.BLOB));
        //hmSQLType.put(SQL_BOOLEAN, Integer.toString(java.sql.Types.BOOLEAN)); 
        hmSQLType.put(SQL_CHAR, Integer.toString(java.sql.Types.CHAR));
        hmSQLType.put(SQL_CLOB, Integer.toString(java.sql.Types.CLOB));
        //hmSQLType.put(SQL_DATALINK, Integer.toString(java.sql.Types.DATALINK));
        hmSQLType.put(SQL_DATE, Integer.toString(java.sql.Types.DATE));
        //hmSQLType.put(SQL_DECIMAL, Integer.toString(java.sql.Types.DECIMAL));
        //hmSQLType.put(SQL_DISTINCT, Integer.toString(java.sql.Types.DISTINCT));
        hmSQLType.put(SQL_DOUBLE, Integer.toString(java.sql.Types.DOUBLE));
        hmSQLType.put(SQL_FLOAT, Integer.toString(java.sql.Types.FLOAT));
        hmSQLType.put(SQL_INTEGER, Integer.toString(java.sql.Types.INTEGER));
        //hmSQLType.put(SQL_JAVA_OBJECT, Integer.toString(java.sql.Types.JAVA_OBJECT));
        //hmSQLType.put(SQL_LONGVARBINARY, Integer.toString(java.sql.Types.LONGVARBINARY));
        //hmSQLType.put(SQL_LONGVARCHAR, Integer.toString(java.sql.Types.LONGVARCHAR));
        //hmSQLType.put(SQL_NULL, Integer.toString(java.sql.Types.NULL));
        hmSQLType.put(SQL_NUMERIC, Integer.toString(java.sql.Types.NUMERIC));
        //hmSQLType.put(SQL_OTHER, Integer.toString(java.sql.Types.OTHER));
        //hmSQLType.put(SQL_REAL, Integer.toString(java.sql.Types.REAL));
        //hmSQLType.put(SQL_REF, Integer.toString(java.sql.Types.REF));
        hmSQLType.put(SQL_SMALLINT, Integer.toString(java.sql.Types.SMALLINT));
        //hmSQLType.put(SQL_STRUCT, Integer.toString(java.sql.Types.STRUCT));
        //hmSQLType.put(SQL_TEXT, Integer.toString(java.sql.Types.LONGVARCHAR));
        hmSQLType.put(SQL_TIME, Integer.toString(java.sql.Types.TIME));
        hmSQLType.put(SQL_TIMESTAMP, Integer.toString(java.sql.Types.TIMESTAMP));
        //hmSQLType.put(SQL_TINYINT, Integer.toString(java.sql.Types.TINYINT));
        //hmSQLType.put(SQL_VARBINARY, Integer.toString(java.sql.Types.VARBINARY));
        hmSQLType.put(SQL_VARCHAR, Integer.toString(java.sql.Types.VARCHAR));
    }
    
    private void loadSyntax() {
        hmSyntax = new HashMap();

        HashMap hmMySQL = new HashMap();
        hmMySQL.put(COLUMN, "#COLUMN_NAME# #TYPE# #SIGNED# #NULL# #DEFAULT#");
        hmMySQL.put(PK, "ALTER TABLE #TABLE_NAME# ADD PRIMARY KEY ( ");
        hmMySQL.put(INDTYPE, "UNIQUE|FULLTEXT|SPATIAL");
        hmMySQL.put(INDORDER, "ASC|DESC");

        HashMap hmOracle = new HashMap();
        hmOracle.put(COLUMN, "#COLUMN_NAME# #TYPE# #DEFAULT# #NULL#");
        hmOracle.put(PK, "ALTER TABLE #TABLE_NAME# ADD PRIMARY KEY ( ");
        hmOracle.put(INDTYPE, "UNIQUE|BITMAP");
        hmOracle.put(INDORDER, "ASC|DESC");

        HashMap hmInformix = new HashMap();
        hmInformix.put(COLUMN, "#COLUMN_NAME# #TYPE# #DEFAULT# #NULL#");
        hmInformix.put(PK, "ALTER TABLE #TABLE_NAME# ADD PRIMARY KEY ( ");
        hmInformix.put(INDTYPE, "UNIQUE|CLUSTER");
        hmInformix.put(INDORDER, "ASC|DESC");

        HashMap hmSQLSERVER = new HashMap();
        hmSQLSERVER.put(COLUMN, "#COLUMN_NAME# #TYPE# #DEFAULT# #NULL#");
        hmSQLSERVER.put(PK, "ALTER TABLE #TABLE_NAME# ADD PRIMARY KEY ( ");
        hmSQLSERVER.put(INDTYPE, "UNIQUE");
        hmSQLSERVER.put(INDORDER, "ASC|DESC");

        HashMap hmHSQL = new HashMap();
        hmHSQL.put(COLUMN, "#COLUMN_NAME# #TYPE# #DEFAULT# #NULL#");
        hmHSQL.put(PK, "ALTER TABLE #TABLE_NAME# ADD CONSTRAINT #CNAME# PRIMARY KEY ( ");
        hmHSQL.put(INDTYPE, "UNIQUE");
        hmHSQL.put(INDORDER, "DESC");

        HashMap hmPOINTBASE = new HashMap();
        hmPOINTBASE.put(COLUMN, "#COLUMN_NAME# #TYPE# #DEFAULT# #NULL#");
        hmPOINTBASE.put(PK, "ALTER TABLE #TABLE_NAME# ADD CONSTRAINT #CNAME# PRIMARY KEY ( ");
        hmPOINTBASE.put(INDTYPE, "UNIQUE");
        hmPOINTBASE.put(INDORDER, "ASC|DESC");

        HashMap hmPOSTGRESSQL = new HashMap();
        hmPOSTGRESSQL.put(COLUMN, "#COLUMN_NAME# #TYPE# #DEFAULT# #NULL#");
        hmPOSTGRESSQL.put(PK, "ALTER TABLE #TABLE_NAME# ADD PRIMARY KEY ( ");
        hmPOSTGRESSQL.put(INDTYPE, "UNIQUE");
        hmPOSTGRESSQL.put(INDORDER, "ASC|DESC");

        HashMap hmDB2 = new HashMap();
        hmDB2.put(COLUMN, "#COLUMN_NAME# #TYPE# #NULL# #DEFAULT#");
        hmDB2.put(PK, "ALTER TABLE #TABLE_NAME# ADD PRIMARY KEY ( ");
        hmDB2.put(INDTYPE, "UNIQUE");
        hmDB2.put(INDORDER, "ASC|DESC");

        HashMap hmSYBASE = new HashMap();
        hmSYBASE.put(COLUMN, "#COLUMN_NAME# #TYPE# #NULL# #DEFAULT#");
        hmSYBASE.put(PK, "ALTER TABLE #TABLE_NAME# ADD CONSTRAINT #CNAME# PRIMARY KEY ( ");
        hmSYBASE.put(INDTYPE, "UNIQUE");
        hmSYBASE.put(INDORDER, "ASC|DESC");

        hmSyntax.put("MYSQL", hmMySQL);
        hmSyntax.put("ORACLE", hmOracle);
        hmSyntax.put("INFORMIX", hmInformix);
        hmSyntax.put("SQLSERVER", hmSQLSERVER);
        hmSyntax.put("HSQL", hmHSQL);
        hmSyntax.put("POINTBASE", hmPOINTBASE);
        hmSyntax.put("POSTGRESSQL", hmPOSTGRESSQL);
        hmSyntax.put("SYBASE", hmSYBASE);
        hmSyntax.put("DB2", hmDB2);

    }
}
