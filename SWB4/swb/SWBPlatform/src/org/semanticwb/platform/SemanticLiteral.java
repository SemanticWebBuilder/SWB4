/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Statement;

/**
 *
 * @author Jei
 */
public class SemanticLiteral 
{
    Literal literal;
            
    public SemanticLiteral(Statement stmt)
    {
        literal=stmt.getLiteral();
    }
    
    public SemanticLiteral(Literal literal)
    {
        this.literal=literal;
    }    
    
    public boolean getBoolean()
    {
        return literal.getBoolean();
    }
    
    public String getString()
    {
        return literal.getString();
    }    

    public byte getByte()
    {
        return literal.getByte();
    }    
    
    public char getChar()
    {
        return literal.getChar();
    }    
    
    public double getDouble()
    {
        return literal.getDouble();
    }    
    
    public float getFloat()
    {
        return literal.getFloat();
    }    

    public int getInt()
    {
        return literal.getInt();
    }    
    
    public short getShort()
    {
        return literal.getShort();
    }    
    
    public String getLanguage()
    {
        return literal.getLanguage();
    }     
    
    public long getLong()
    {
        return literal.getLong();
    }      
}
