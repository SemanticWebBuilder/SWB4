package org.semanticwb.triplestore.gemfire;

import gnu.crypto.hash.HashFactory;
import gnu.crypto.hash.IMessageDigest;
import java.io.UnsupportedEncodingException;

/**
 * SWBTSGemFireChecksumCreator IMessageDigest Object Pool to speed up
 * generation of digest values for values stored in SWBStore
 * @author serch
 */
public class SWBTSGemFireChecksumCreator
{
    private static volatile int cont=0;
    private static final int max=25;
    private static IMessageDigest[] mdArr = new IMessageDigest[max] ;

    static
    {
        for (int i = 0; i<max; i++)
        {
            mdArr[i]= HashFactory.getInstance("Tiger");
        }
    }

    private static synchronized IMessageDigest getMD()
    {
        if (cont==max) cont=0;
        return mdArr[cont++];
    }

    /**
     * Get Hash from a given String
     * @param val String to hash
     * @return Hash of the val
     * @throws UnsupportedEncodingException if val can't get UTF8 encoded
     */
    public static byte[] getHash(String val) throws UnsupportedEncodingException
    {
        IMessageDigest md = getMD();
        byte[]cnt;
        synchronized(md)
        {
            cnt = val.getBytes("UTF8");
            md.update(cnt, 0, cnt.length);
            cnt = md.digest();
            md.reset();
        }
        return cnt;
    }

}
