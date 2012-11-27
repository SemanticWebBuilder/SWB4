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
package org.semanticwb.triplestore;

import gnu.crypto.hash.HashFactory;
import gnu.crypto.hash.IMessageDigest;
import java.io.UnsupportedEncodingException;

/**
 * ChecksumCreator IMessageDigest Object Pool to speed up
 * generation of digest values for values stored in SWBStore
 * @author serch
 */
public class ChecksumCreator
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
