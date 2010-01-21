/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.jcr.Binary;
import javax.jcr.RepositoryException;

/**
 *
 * @author victor.lorenzana
 */
public class BinaryImp implements Binary {

    private final byte[] cont;
    public BinaryImp(byte[] cont)
    {
        this.cont=cont;
    }
    public InputStream getStream() throws RepositoryException
    {
        return new ByteArrayInputStream(cont);
    }

    public int read(byte[] b, long position) throws IOException, RepositoryException
    {
        long toread=cont.length-position;
        b=new byte[(int)toread];
        int j=0;
        for(int i=(int)position;i<cont.length;i++)
        {
            b[j]=cont[i];
            j++;
        }
        return j;
    }

    public long getSize() throws RepositoryException
    {
        return cont.length;
    }

    @Override
    public String toString()
    {
        try
        {
            return new String(this.cont,"utf-8");
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public void dispose()
    {
        
    }

}
