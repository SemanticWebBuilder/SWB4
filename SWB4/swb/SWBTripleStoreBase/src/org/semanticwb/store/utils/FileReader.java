/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author javier.solis.g
 */
public class FileReader
{
    public static int SIZE=1024*1024;
    
    FileInputStream in;
    FileChannel ch;
    ByteBuffer bb;
    int bsize;                          //tamaño del bloque
    int pointer;                        //apuntador del bloque

    byte[] barray;
    byte[] temp;
    int temppointer;
    
    public FileReader(String fileName, int buffer) throws IOException
    {
        barray = new byte[buffer];
        temp = new byte[buffer*10];
        in = new FileInputStream(fileName);
        ch=in.getChannel();
        bb = ByteBuffer.wrap( barray );   

    }
    
    public FileReader(String fileName) throws IOException
    {
       this(fileName,SIZE); 
    }
    
    private int nextBlock() throws IOException
    {
        //do{
            bb.clear( );
            bsize=ch.read( bb );
            pointer=0;
        //}while(bsize==0);
        return bsize;
    }
    
    public String nextSegment(byte separator) throws IOException
    {
        boolean sep=false;
        if(bsize==-1 && temppointer==0)
        {
            return null;
        }
        
        for ( ; pointer<bsize; pointer++ )
        {
            byte b=barray[pointer];
            if(b==separator)
            {
                pointer++;
                sep=true;
                break;
            }
            temp[temppointer]=b;
            temppointer++;
        }
        
        if(!sep && pointer==bsize)
        {
            nextBlock();
            return nextSegment(separator);
        }
        
        String ret=new String(temp,0,temppointer);
        temppointer=0;
        
        return ret;
    }
    
    public void close() 
    {
        try
        {
            ch.close();
            in.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
