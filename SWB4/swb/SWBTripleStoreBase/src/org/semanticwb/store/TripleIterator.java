/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store;

import java.util.Iterator;
//import java.util.function.Consumer;

/**
 *
 * @author javier.solis.g
 */
public class TripleIterator implements Iterator<Triple>
{

    @Override
    public boolean hasNext()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Triple next()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public void forEachRemaining(Consumer<? super Triple> action)
//    {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    public void close()
    {
        
    }
    
    public boolean isClosed()
    {
        return true;
    }
    
    public long count()
    {
        return 0;
    }
}
