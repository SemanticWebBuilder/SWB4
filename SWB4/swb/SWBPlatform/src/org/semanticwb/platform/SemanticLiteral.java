/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Statement;
import java.sql.Date;
import java.sql.Timestamp;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author Jei
 */
public class SemanticLiteral 
{
    private static Logger log=SWBUtils.getLogger(SemanticLiteral.class);

    Literal literal;
    String m_lang=null;
    Object m_obj=null;
            
    public SemanticLiteral(Statement stmt)
    {
        literal=stmt.getLiteral();
    }
    
    public SemanticLiteral(Literal literal)
    {
        this.literal=literal;
    }    

    public SemanticLiteral(Object value)
    {
        m_obj=value;
    }

    public SemanticLiteral(Object value, String lang)
    {
        System.out.println("Val:"+value+" lang:"+lang);
        m_obj=value;
        m_lang=lang;
    }

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
    
    public boolean getBoolean()
    {
        Boolean ret=null;
        if(literal!=null)
        {
            ret=literal.getBoolean();
        }else if(m_obj instanceof Boolean)
        {
            ret=(Boolean)m_obj;
        }
        return ret;
    }
    
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

    public byte getByte()
    {
        Byte ret=null;
        if(literal!=null)
        {
            ret=literal.getByte();
        }else if(m_obj instanceof Byte)
        {
            ret=(Byte)m_obj;
        }
        return ret;
    }    
    
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
        return ret;
    }    
    
    public double getDouble()
    {
        Double ret=null;
        if(literal!=null)
        {
            ret=literal.getDouble();
        }else if(m_obj instanceof Double)
        {
            ret=(Double)m_obj;
        }
        return ret;
    }    
    
    public float getFloat()
    {
        Float ret=null;
        if(literal!=null)
        {
            ret=literal.getFloat();
        }else if(m_obj instanceof Float)
        {
            ret=(Float)m_obj;
        }
        return ret;
    }    

    public int getInt()
    {
        Integer ret=null;
        if(literal!=null)
        {
            ret=literal.getInt();
        }else if(m_obj instanceof Integer)
        {
            ret=(Integer)m_obj;
        }
        return ret;
    }    
    
    public short getShort()
    {
        Short ret=null;
        if(literal!=null)
        {
            ret=literal.getShort();
        }else if(m_obj instanceof Short)
        {
            ret=(Short)m_obj;
        }
        return ret;
    }    
    
    public String getLanguage()
    {
        String ret=null;
        if(literal!=null)
        {
            ret=literal.getLanguage();
        }else
        {
            ret=m_lang;
        }
        return ret;
    }     
    
    public long getLong()
    {
        Long ret=null;
        if(literal!=null)
        {
            ret=literal.getLong();
        }else if(m_obj instanceof Long)
        {
            ret=(Long)m_obj;
        }
        return ret;
    }      

    public Date getDate()
    {
        Object obj=getValue();
        Date ret=null;
        if(obj instanceof Date)
        {
            ret=(Date)obj;
        }else
        {
            try
            {
                String aux=(String)obj;
                if(aux!=null)
                {
                    ret=new Date(SWBUtils.TEXT.iso8601DateParse(aux).getTime());
                }
            }catch(Exception e){log.error(e);}
        }
        return ret;
    }

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
                String aux=(String)obj;
                if(aux!=null)
                {
                    ret=new Timestamp(SWBUtils.TEXT.iso8601DateParse(aux).getTime());
                }
            }catch(Exception e){log.error(e);}
        }
        return ret;
    }
}
