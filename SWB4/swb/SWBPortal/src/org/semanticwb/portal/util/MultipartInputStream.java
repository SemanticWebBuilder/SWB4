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
package org.semanticwb.portal.util;

import java.io.*;

// TODO: Auto-generated Javadoc
/**
 * Objeto que identifica y separa los archivos recibidos por POST.
 * @author Javier Solis Gonzalez
 * @version
 */
public class MultipartInputStream extends InputStream
{
    
    /** The in. */
    InputStream in = null;
    
    /** The boundary. */
    byte boundary[] = null;
    
    /** The buffer. */
    byte buffer[] = null;
    
    /** The part end. */
    boolean partEnd = false;
    
    /** The file end. */
    boolean fileEnd = false;
    
    /** The size. */
    public long size = 0;

    // Read boundary bytes of input in buffer
    // Return true if enough bytes available, false otherwise.

    /**
     * Read boundary bytes.
     * 
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private final boolean readBoundaryBytes()
            throws IOException
    {
        int pos = 0;
        while (pos < buffer.length)
        {
            int got = in.read(buffer, pos, buffer.length - pos);
            if (got < 0)
                return false;
            pos += got;
        }
        return true;
    }

    // Skip to next input boundary, set stream at begining of content:
    // Returns true if boundary was found, false otherwise.

    /**
     * Skip to boundary.
     * 
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected boolean skipToBoundary()
            throws IOException
    {
        int ch = in.read();
        skip:
      while (ch != -1)
      {
          if (ch != '-')
          {
              ch = in.read();
              continue;
          }
          if ((ch = in.read()) != '-')
              continue;
          in.mark(boundary.length);
          if (!readBoundaryBytes())
          {
              in.reset();
              ch = in.read();
              continue skip;
          }
          for (int i = 0; i < boundary.length; i++)
          {
              if (buffer[i] != boundary[i])
              {
                  in.reset();
                  ch = in.read();
                  continue skip;
              }
          }
          // FIXME: should we check for a properly syntaxed part, which
          // means that we should expect '\r\n'. For now, we just skip
          // as much as we can.
          if ((ch = in.read()) == '\r')
          {
              ch = in.read();
          }
          in.mark(3);
          if (in.read() == '-')
          {      // check fileEnd!
              if (in.read() == '\r' && in.read() == '\n')
              {
                  fileEnd = true;
                  return false;
              }
          }
          in.reset();
          return true;
      }
        fileEnd = true;
        return false;
    }

    /**
     * Read one byte of data from the current part.
     * 
     * @return A byte of data, or  if end of file.
     * @throws IOException Signals that an I/O exception has occurred.
     * @exception java.io.IOException If some IO error occured.
     */

    public int read() throws java.io.IOException
    {
        int ch;
        if (partEnd)
            return -1;
        switch (ch = in.read())
        {
            case '\r':
                // check for a boundary
                in.mark(boundary.length + 3);
                int c1 = in.read();
                int c2 = in.read();
                int c3 = in.read();
                if ((c1 == '\n') && (c2 == '-') && (c3 == '-'))
                {
                    if (!readBoundaryBytes())
                    {
                        in.reset();
                        size++;
                        return ch;
                    }
                    for (int i = 0; i < boundary.length; i++)
                    {
                        if (buffer[i] != boundary[i])
                        {
                            in.reset();
                            size++;
                            return ch;
                        }
                    }
                    partEnd = true;
                    if ((ch = in.read()) == '\r')
                    {
                        in.read();
                    } else if (ch == '-')
                    {
                        // FIXME, check the second hyphen
                        if (in.read() == '-')
                            fileEnd = true;
                    } else
                    {
                        fileEnd = (ch == -1);
                    }
                    return -1;
                } else
                {
                    in.reset();
                    size++;
                    return ch;
                }
                // not reached
            case -1:
                fileEnd = true;
                return -1;
            default:
                size++;
                return ch;
        }
    }

    /**
     * Read n bytes of data from the current part.
     * 
     * @param b the b
     * @param off the off
     * @param len the len
     * @return the number of bytes data, read or 
     * if end of file.
     * @throws IOException Signals that an I/O exception has occurred.
     * @exception java.io.IOException If some IO error occured.
     */
    @Override
    public int read(byte b[], int off, int len)
            throws IOException
    {
        int got = 0;
        int ch;

        while (got < len)
        {
            if ((ch = read()) == -1)
                return (got == 0) ? -1 : got;
            b[off + (got++)] = (byte) (ch & 0xFF);
        }
        return got;
    }

    /* (non-Javadoc)
     * @see java.io.InputStream#skip(long)
     */
    @Override
    public long skip(long n)
            throws IOException
    {
        while ((--n >= 0) && (read() != -1))
            ;
        return n;
    }

    /* (non-Javadoc)
     * @see java.io.InputStream#available()
     */
    public int available()
            throws IOException
    {
        return in.available();
    }

    /**
     * Switch to the next available part of data.
     * One can interrupt the current part, and use this method to switch
     * to next part before current part was totally read.
     * 
     * @return A boolean  if there next partis ready,
     * or  if this was the last part.
     * @throws IOException Signals that an I/O exception has occurred.
     */

    public boolean nextInputStream()
            throws IOException
    {
        if (fileEnd)
        {
            return false;
        }
        if (!partEnd)
        {
            return skipToBoundary();
        } else
        {
            partEnd = false;
            return true;
        }
    }

    /**
     * Construct a new multipart input stream.
     * @param in The initial (multipart) input stream.
     * @param boundary The input stream MIME boundary.
     */

    public MultipartInputStream(InputStream in, byte boundary[])
    {
        this.in = (in.markSupported()
                ? in
                : new BufferedInputStream(in, boundary.length + 4));
        //System.out.println(in.markSupported());
        this.boundary = boundary;
        this.buffer = new byte[boundary.length];
        this.partEnd = false;
        this.fileEnd = false;
    }

}
