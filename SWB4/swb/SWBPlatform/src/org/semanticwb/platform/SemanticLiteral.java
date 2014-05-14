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
package org.semanticwb.platform;

import com.hp.hpl.jena.datatypes.BaseDatatype;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Statement;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticLiteral.
 * 
 * @author Jei
 */
public class SemanticLiteral 
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SemanticLiteral.class);

    /** The literal. */
    Literal literal;
    
    /** The m_lang. */
    String m_lang=null;
    
    /** The m_obj. */
    Object m_obj=null;
            
    /**
     * Instantiates a new semantic literal.
     * 
     * @param stmt the stmt
     */
    public SemanticLiteral(Statement stmt)
    {
        literal=stmt.getLiteral();
    }
    
    /**
     * Instantiates a new semantic literal.
     * 
     * @param literal the literal
     */
    public SemanticLiteral(Literal literal)
    {
        this.literal=literal;
    }    

    /**
     * Instantiates a new semantic literal.
     * 
     * @param value the value
     */
    public SemanticLiteral(Object value)
    {
        //System.out.println("SemanticLiteral:"+value);
        m_obj=value;
    }

    /**
     * Instantiates a new semantic literal.
     * 
     * @param value the value
     * @param lang the lang
     */
    public SemanticLiteral(Object value, String lang)
    {
        //System.out.println("Val:"+value+" lang:"+lang);
        m_obj=value;
        if(lang!=null && lang.length()==0)lang=null;
        m_lang=lang;
    }

    /**
     * Value of.
     * 
     * @param prop the prop
     * @param value the value
     * @return the semantic literal
     */
    public static SemanticLiteral valueOf(SemanticProperty prop, String value)
    {
        SemanticLiteral ret=null;
        if (value != null)
        {
            //System.out.println("valueOf:"+value);
            if(prop.isString())
            {
                 ret=new SemanticLiteral(value);
            }else if(prop.isBoolean())
            {
                ret=new SemanticLiteral(Boolean.valueOf(value));
            }else if(prop.isDouble())
            {
                ret=new SemanticLiteral(Double.valueOf(value));
            }else if(prop.isFloat())
            {
                ret=new SemanticLiteral(Float.valueOf(value));
            }else if(prop.isInt())
            {
                ret=new SemanticLiteral(Integer.valueOf(value));
            }else if(prop.isLong())
            {
                ret=new SemanticLiteral(Long.valueOf(value));
            }else if(prop.isDecimal())
            {
                ret=new SemanticLiteral(new BigDecimal(value));
            }else if(prop.isDate())
            {
                try
                {
                    ret=new SemanticLiteral(SWBUtils.TEXT.iso8601DateParse(value));
                }catch(Exception e)
                {
                    log.error(e);
                }
            }else if(prop.isDateTime())
            {
                try
                {
                    ret=new SemanticLiteral(new Timestamp(SWBUtils.TEXT.iso8601DateParse(value).getTime()));
                }catch(Exception e)
                {
                    log.error(e);
                }
            }
        }
        return ret;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public Object getValue()
    {
        Object ret=null;
        if(literal!=null)
        {
            ret=literal.getValue();
        }else
        {
            ret=m_obj;
        }
        return ret;
    }
    
    /**
     * Gets the boolean.
     * 
     * @return the boolean
     */
    public boolean getBoolean()
    {
        Boolean ret=null;
        if(literal!=null)
        {
            //Validar 
            if(literal.getDatatypeURI()!=null)
            {
                if(literal.getDatatypeURI().endsWith("#boolean"))
                {
                    ret=literal.getBoolean();
                }else if(literal.getDatatypeURI().endsWith("#integer"))
                {
                    ret=(literal.getInt()==1);
                }
            }
        }else if(m_obj instanceof Boolean)
        {
            ret=(Boolean)m_obj;
        }else if(m_obj instanceof Integer)
        {
            ret=((Integer)m_obj)==1;
        }
        if(ret==null)ret=false;
        return ret;
    }
    
    /**
     * Gets the string.
     * 
     * @return the string
     */
    public String getString()
    {
        String ret=null;
        if(literal!=null)
        {
            ret=literal.getString();
        }else if(m_obj!=null)
        {
            ret=m_obj.toString();
        }
        return ret;
    }    

    /**
     * Gets the byte.
     * 
     * @return the byte
     */
    public byte getByte()
    {
        Byte ret=null;
        if(literal!=null)
        {
            String lf=literal.getLexicalForm();
            if(lf!=null && lf.length()>0)
            {                
                ret=literal.getByte();
            }
        }else if(m_obj instanceof Byte)
        {
            ret=(Byte)m_obj;
        }
        if(ret==null)ret=0;
        return ret;
    }    
    
    /**
     * Gets the char.
     * 
     * @return the char
     */
    public char getChar()
    {
        Character ret=null;
        if(literal!=null)
        {
            ret=literal.getChar();
        }else if(m_obj instanceof Character)
        {
            ret=(Character)m_obj;
        }
        if(ret==null)ret=0;
        return ret;
    }    
    
    /**
     * Gets the double.
     * 
     * @return the double
     */
    public double getDouble()
    {
        Double ret=null;
        if(literal!=null)
        {
            try
            {            
                String lf=literal.getLexicalForm();
                if(lf!=null && lf.length()>0)
                {                
                    ret=literal.getDouble();
                }
            }catch(NumberFormatException e)
            {
                log.error("Error parsing double value...",e);
                ret=0D;
            }
        }else if(m_obj instanceof Double)
        {
            ret=(Double)m_obj;
        }
        if(ret==null)ret=0D;
        return ret;
    }    
    
    /**
     * Gets the float.
     * 
     * @return the float
     */
    public float getFloat()
    {
        Float ret=null;
        if(literal!=null)
        {            
            try
            {   
                String lf=literal.getLexicalForm();
                if(lf!=null && lf.length()>0)
                {
                    ret=literal.getFloat();
                }
            }catch(NumberFormatException e)
            {
                log.error("Error parsing float value...",e);
                ret=0F;
            }
        }else if(m_obj instanceof Float)
        {
            ret=(Float)m_obj;
        }
        if(ret==null)ret=0F;
        return ret;
    }    

    /**
     * Gets the int.
     * 
     * @return the int
     */
    public int getInt()
    {
        Integer ret=null;
        if(literal!=null)
        {
            try
            {            
                String lf=literal.getLexicalForm();
                if(lf!=null && lf.length()>0)
                {                
                    ret=literal.getInt();
                }
            }catch(NumberFormatException e)
            {
                log.error("Error parsing int value...",e);
                ret=0;
            }
        }else if(m_obj instanceof Long)
        {
            ret=((Long)m_obj).intValue();
        }else if(m_obj instanceof Integer)
        {
            ret=(Integer)m_obj;
        }
        if(ret==null)ret=0;
        return ret;
    }    
    
    /**
     * Gets the short.
     * 
     * @return the short
     */
    public short getShort()
    {
        Short ret=null;
        if(literal!=null)
        {
            String lf=literal.getLexicalForm();
            if(lf!=null && lf.length()>0)
            {                
                ret=literal.getShort();
            }
        }else if(m_obj instanceof Short)
        {
            ret=(Short)m_obj;
        }
        if(ret==null)ret=0;
        return ret;
    }    
    
    /**
     * Gets the language.
     * 
     * @return the language
     */
    public String getLanguage()
    {
        String ret=null;
        if(literal!=null)
        {
            ret=literal.getLanguage();
            if(ret!=null && ret.length()==0)ret=null;
        }else
        {
            ret=m_lang;
        }
        return ret;
    }     
    
    /**
     * Gets the long.
     * 
     * @return the long
     */
    public long getLong()
    {
        Long ret=null;
        if(literal!=null)
        {
            String lf=literal.getLexicalForm();
            if(lf!=null && lf.length()>0)
            {                
                ret=literal.getLong();
            }
        }else if(m_obj instanceof Long)
        {
            ret=(Long)m_obj;
        }
        if(ret==null)ret=0L;
        return ret;
    }      

    /**
     * Gets the date.
     * 
     * @return the date
     */
    public Date getDate()
    {
        Object obj=getValue();
        Date ret=null;
        if(obj instanceof Date)
        {
            ret=(Date)obj;
        }else if(obj!=null)
        {
            try
            {
                String aux=obj.toString();
                if(aux!=null && aux.length()>0)
                {
                    ret=new Date(SWBUtils.TEXT.iso8601DateParse(aux).getTime());
                }
            }catch(Exception e){log.error(e);}
        }
        return ret;
    }

    /**
     * Gets the date time.
     * 
     * @return the date time
     */
    public Timestamp getDateTime()
    {
        Object obj=getValue();
        Timestamp ret=null;
        if(obj instanceof Timestamp)
        {
            ret=(Timestamp)obj;
        }else
        {
            try
            {
                if(obj instanceof String)
                {
                    String aux=(String)obj;
                    if(aux!=null && aux.length()>0)
                    {
                        ret=new Timestamp(SWBUtils.TEXT.iso8601DateParse(aux).getTime());
                    }
                }else if(obj instanceof BaseDatatype.TypedValue)
                {
                    BaseDatatype.TypedValue tv=(BaseDatatype.TypedValue)obj;
                    String aux=tv.lexicalValue;
                    if(aux!=null && aux.length()>0)
                    {
                        ret=new Timestamp(SWBUtils.TEXT.iso8601DateParse(aux).getTime());
                    }
                }
            }catch(Exception e){log.error(e);System.out.println("obj:"+obj);}
        }
        return ret;
    }
}
