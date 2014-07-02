/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.test;

import org.semanticwb.store.utils.FileReader;

/**
 *
 * @author javier.solis.g
 */
public class TestReader
{
    public static void main(String args[])
    {
        try
        {
            FileReader r=new FileReader("/lenguaje/bibm/dataset_100.nt",200);

            for(int x=0;x<102;x++)
            {
                String s=r.nextSegment((byte)' ');
                String p=r.nextSegment((byte)' ');
                String o=r.nextSegment((byte)'\n');
            
                System.out.println(""+s+" "+p+" "+o+"");
            }
            r.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
