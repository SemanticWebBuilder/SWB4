/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package org.semanticwb.util;

import java.math.*;

/**
 * TEA Encryption Algoritm.
 * <P>
 * It is a Feistel cipher which uses operations from mixed
 * (orthogonal) algebraic groups - XORs and additions in this case. It
 * encrypts 64 data bits at a time using a 128-bit key. It seems highly
 * resistant to differential cryptanalysis, and achieves complete
 * diffusion (where a one bit difference in the plaintext will cause
 * approximately 32 bit differences in the ciphertext) after only six
 * rounds. Performance on a modern desktop computer or workstation is
 * very impressive.
 * <P>
 * It takes 64 bits of data in v[0] and v[1], and 128 bits of key in
 * k[0] - k[3]. The result is returned in w[0] and w[1]. Returning the
 * result separately makes implementation of cipher modes other than
 * Electronic Code Book a little bit easier.
 * <P>
 * TEA can be operated in any of the modes of DES.
 * <P>
 * n is the number of iterations. 32 is ample, 16 is sufficient, as few
 * as eight should be OK for most applications, especially ones where
 * the data age quickly (real-time video, for example). The algorithm
 * achieves good dispersion after six iterations. The iteration count
 * can be made variable if required.
 * <P>
 * Note this algorithm is optimised for 32-bit CPUs with fast shift
 * capabilities. It can very easily be ported to assembly language on
 * most CPUs.
 * <P>
 * delta is chosen to be the Golden ratio ((5/4)1/2 - 1/2 ~ 0.618034)
 * multiplied by 232. On entry to decipher(), sum is set to be delta *
 * n. Which way round you call the functions is arbitrary: DK(EK(P)) =
 * EK(DK(P)) where EK and DK are encryption and decryption under key K
 * respectively.
 * <P>
 * <B>This is the extension, or variant, of TEA.</B>
 *
 * <P> Example of use:
 * <PRE>
 * byte key[] = new BigInteger("39e858f86df9b909a8c87cb8d9ad599", 16).toByteArray();
 * Encryptor t = new Encryptor(key);
 * <BR>
 * String src = "hello world!";
 * System.out.println("input = " + src);
 * byte plainSource[] = src.getBytes();
 * int enc[] = t.encode(plainSource, plainSource.length);
 * System.out.println(t.padding() + " bytes added as padding.");
 * byte dec[] = t.decode(enc);
 * System.out.println("output = " + new String(dec));
 * </PRE>
 *
 * <P> Example of use:
 * <PRE>
 * String keyString = "5fe858d86df4b909a8c87cb8d9ad596";
 * byte key[] = new BigInteger(keyString, 16).toByteArray();
 * Encryptor t = new Encryptor(key);
 * String src = "hello world!";
 * System.out.println("input = [" + src + "]");
 * String hexStr = t.encode(src);
 * System.out.println("Encoding as Hex string: " + hexStr);
 * System.out.println("output = [" + t.decode(hexStr) + "]");
 * </PRE>
 *
 * @author Modify Jei, Translated by Michael Lecuyer (mjl@theorem.com) from the C Language.
 * @version 1.0 Sep 17, 2003
 * @since JDK1.1
 */

public class Encryptor
{
    private int _key[];	// The 128 bit key.
    private byte _keyBytes[];	// original key as found
    private int _padding;		// amount of padding added in byte --> integer conversion.

    /**
     * Array of hex char mappings.
     * hex[0] = '0', hex[15] = 'F'.
     */
    protected static final char hex[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * Encodes and decodes "Hello world!" for your personal pleasure.
     */
    public static void main(String args[])
    {
        String keyString = "05fe858d86df4b909a8c87cb8d9ad596";
        byte key[] = new BigInteger(keyString, 16).toByteArray();
        Encryptor t = new Encryptor(key);

        String src = "hello world!";
        //System.out.println("input = [" + src + "]");

        String hexStr = t.encode(src);
        //System.out.println("Encoding as Hex string: " + hexStr);

        //System.out.println("output = [" + t.decode(hexStr) + "]");

        //System.out.println("Checksum of: " + src + " -->" + Long.toHexString(t.getChecksum(src)));

        System.exit(0);
    }

    /**
     * Accepts key for enciphering/deciphering.
     *
     * @throws ArrayIndexOutOfBoundsException if the key isn't the correct length.
     *
     * @param key 128 bit (16 byte) key.
     */
    public Encryptor(byte[] key)
    {
        int klen = key.length;
        _key = new int[4];

        // Incorrect key length throws exception.
        if (klen != 16)
            throw new ArrayIndexOutOfBoundsException(this.getClass().getName() + ": Key is not 16 bytes");

        int j, i;
        for (i = 0, j = 0; j < klen; j += 4, i++)
            _key[i] = (key[j] << 24) | (((key[j + 1]) & 0xff) << 16) | (((key[j + 2]) & 0xff) << 8) | ((key[j + 3]) & 0xff);

        _keyBytes = key;	// save for toString.
    }

    public Encryptor(int[] key)
    {
        _key = key;
    }

    /*
/**
* Representation of TEA class
*
public String toString()
{
    String tea = this.getClass().getName();
    tea +=  ": Tiny Encryption Algorithm (TEA)  key: " + getHex(_keyBytes);
    return tea;
}
    */

    /**
     * Encipher two <code>int</code>s.
     * Replaces the original contents of the parameters with the results.
     * The integers are usually created from 8 bytes.
     * The usual way to collect bytes to the int array is:
     * <PRE>
     * byte ba[] = { .... };
     * int v[] = new int[2];
     * v[0] = (ba[j] << 24 ) | (((ba[j+1])&0xff) << 16) | (((ba[j+2])&0xff) << 8) | ((ba[j+3])&0xff);
     * v[1] = (ba[j+4] << 24 ) | (((ba[j+5])&0xff) << 16) | (((ba[j+6])&0xff) << 8) | ((ba[j+7])&0xff);
     * v = encipher(v);
     * </PRE>
     *
     * @param v two <code>int</code> array as input.
     *
     * @return array of two <code>int</code>s, enciphered.
     */
    private int[] encipher(int v[])
    {
        int y = v[0];
        int z = v[1];
        int sum = 0;
        int delta = 0x9E3779B9;

        int n = 32;

        while (n-- > 0)
        {
            y += (z << 4 ^ z >>> 5) + z ^ sum + _key[(int) (sum & 3)];
            sum += delta;
            z += (y << 4 ^ y >>> 5) + y ^ sum + _key[(int) (sum >>> 11) & 3];
        }

        int w[] = new int[2];
        w[0] = (int) y;
        w[1] = (int) z;

        return w;
    }

    /**
     * Decipher two <code>int</code>s.
     * Replaces the original contents of the parameters with the results.
     * The integers are usually decocted to 8 bytes.
     * The decoction of the <code>int</code>s to bytes can be done
     * this way.
     * <PRE>
     * int x[] = decipher(ins);
     * outb[j]   = (byte)(x[0] >>> 24);
     * outb[j+1] = (byte)(x[0] >>> 16);
     * outb[j+2] = (byte)(x[0] >>> 8);
     * outb[j+3] = (byte)(x[0]);
     * outb[j+4] = (byte)(x[1] >>> 24);
     * outb[j+5] = (byte)(x[1] >>> 16);
     * outb[j+6] = (byte)(x[1] >>> 8);
     * outb[j+7] = (byte)(x[1]);
     * </PRE>
     *
     * @param v <code>int</code> array of 2
     *
     * @return deciphered <code>int</code> array of 2
     */
    private int[] decipher(int v[])
    {
        int y = v[0];
        int z = v[1];
        int sum = 0xC6EF3720;
        int delta = 0x9E3779B9;

        int n = 32;

        // sum = delta<<5, in general sum = delta * n

        while (n-- > 0)
        {
            z -= (y << 4 ^ y >>> 5) + y ^ sum + _key[(sum >>> 11) & 3];
            sum -= delta;
            y -= (z << 4 ^ z >>> 5) + z ^ sum + _key[sum & 3];
        }

        int w[] = new int[2];
        w[0] = (int) y;
        w[1] = (int) z;

        return w;
    }


    /**
     * String for encoding.
     *
     * @param str String
     *
     * @return encoded HexString.
     *
     */
    public String encode(String str)
    {
// Pad the plaintext with spaces.
//String src = padPlaintext(str);

// Get bytes from the text and encode it.
        byte plainSource[] = str.getBytes();
        int enc[] = encode(plainSource, plainSource.length);

// Report on padding, it should be zero since we originally padded the string with spaces.
//System.out.println(padding() + " bytes added as padding.");

// Display what the encoding would be in a hex string.
        return binToHex(enc);
    }

    /**
     * Byte wrapper for encoding.
     * Converts bytes to ints.
     * Padding will be added if required.
     *
     * @param b incoming <code>byte</code> array
     *
     * @param byte count
     *
     * @return integer conversion array, possibly with padding.
     *
     * @see #padding
     */
    public int[] encode(byte b[], int count)
    {
        int j ,i;
        int bLen = count;
        byte bp[] = b;

        _padding = bLen % 8;
        if (_padding != 0)	// Add some padding, if necessary.
        {
            _padding = 8 - (bLen % 8);
            bp = new byte[bLen + _padding];
            System.arraycopy(b, 0, bp, 0, bLen);
            bLen = bp.length;
        }

        int intCount = bLen / 4;
        int r[] = new int[2];
        int out[] = new int[intCount];

        for (i = 0, j = 0; j < bLen; j += 8, i += 2)
        {
            r[0] = (bp[j] << 24) | (((bp[j + 1]) & 0xff) << 16) | (((bp[j + 2]) & 0xff) << 8) | ((bp[j + 3]) & 0xff);
            r[1] = (bp[j + 4] << 24) | (((bp[j + 5]) & 0xff) << 16) | (((bp[j + 6]) & 0xff) << 8) | ((bp[j + 7]) & 0xff);
            r = encipher(r);
            out[i] = r[0];
            out[i + 1] = r[1];
        }

        return out;
    }

    /**
     * Report how much padding was done in the last encode.
     *
     * @return bytes of padding added
     *
     * @see #encode
     *
     private int padding()
     {
     return _padding;
     }
     */

    /**
     * Decode HexString.
     *
     * @param hexStr String Coded
     *
     * @return <code>String</code> of decoded HexString.
     */
    public String decode(String hexStr)
    {
// Just to prove that converting it to a hex string a back works decode the hex string.
        int enc2[] = hexToBin(hexStr);
        byte dec[] = decode(enc2);
// Display the resulting decoded string, trimmed.to remove padding.
        return new String(dec);
    }

    /**
     * Convert a byte array to ints and then decode.
     * There may be some padding at the end of the byte array from
     * the previous encode operation.
     *
     * @param b bytes to decode
     * @param count number of bytes in the array to decode
     *
     * @return <code>byte</code> array of decoded bytes.
     */
    public byte[] decode(byte b[], int count)
    {
        int i, j;

        int intCount = count / 4;
        int ini[] = new int[intCount];
        for (i = 0, j = 0; i < intCount; i += 2, j += 8)
        {
            ini[i] = (b[j] << 24) | (((b[j + 1]) & 0xff) << 16) | (((b[j + 2]) & 0xff) << 8) | ((b[j + 3]) & 0xff);
            ini[i + 1] = (b[j + 4] << 24) | (((b[j + 5]) & 0xff) << 16) | (((b[j + 6]) & 0xff) << 8) | ((b[j + 7]) & 0xff);
        }
        return decode(ini);
    }

    /**
     * Decode an integer array.
     * There may be some padding at the end of the byte array from
     * the previous encode operation.
     *
     * @param b bytes to decode
     *
     * @return <code>byte</code> array of decoded bytes.
     */
    private byte[] decode(int b[])
    {
        // create the large number and start stripping ints out, two at a time.
        int intCount = b.length;

        byte outb[] = new byte[intCount * 4];
        int tmp[] = new int[2];

        // decipher all the ints.
        int i, j;
        for (j = 0, i = 0; i < intCount; i += 2, j += 8)
        {
            tmp[0] = b[i];
            tmp[1] = b[i + 1];
            tmp = decipher(tmp);
            outb[j] = (byte) (tmp[0] >>> 24);
            outb[j + 1] = (byte) (tmp[0] >>> 16);
            outb[j + 2] = (byte) (tmp[0] >>> 8);
            outb[j + 3] = (byte) (tmp[0]);
            outb[j + 4] = (byte) (tmp[1] >>> 24);
            outb[j + 5] = (byte) (tmp[1] >>> 16);
            outb[j + 6] = (byte) (tmp[1] >>> 8);
            outb[j + 7] = (byte) (tmp[1]);
        }

        return outb;
    }

    /**
     * Convert a string into an integer array form suitable for decoding.
     *
     * @param hexStr String of hexadecimal digits.
     * @return integer array.
     * @throws ArrayIndexOutOfBoundsException if the string length is not divisible into integer length pieces.
     */
    public int[] hexToBin(String hexStr) throws ArrayIndexOutOfBoundsException
    {
        int hexStrLen = hexStr.length();

        // Decode a hex string into a collection of ints.
        if ((hexStrLen % 8) != 0)
            throw new ArrayIndexOutOfBoundsException("Hex string has incorrect length, required to be divisible by eight: " + hexStrLen);

        int outLen = hexStrLen / 8;
        int out[] = new int[outLen];
        byte nibble[] = new byte[2];
        byte b[] = new byte[4];
        int posn = 0;
        for (int i = 0; i < outLen; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                for (int k = 0; k < 2; k++)
                {
                    switch (hexStr.charAt(posn++))
                    {
                        case '0':
                            nibble[k] = (byte) 0;
                            break;
                        case '1':
                            nibble[k] = (byte) 1;
                            break;
                        case '2':
                            nibble[k] = (byte) 2;
                            break;
                        case '3':
                            nibble[k] = (byte) 3;
                            break;
                        case '4':
                            nibble[k] = (byte) 4;
                            break;
                        case '5':
                            nibble[k] = (byte) 5;
                            break;
                        case '6':
                            nibble[k] = (byte) 6;
                            break;
                        case '7':
                            nibble[k] = (byte) 7;
                            break;
                        case '8':
                            nibble[k] = (byte) 8;
                            break;
                        case '9':
                            nibble[k] = (byte) 9;
                            break;
                        case 'A':
                            nibble[k] = (byte) 0xA;
                            break;
                        case 'B':
                            nibble[k] = (byte) 0xB;
                            break;
                        case 'C':
                            nibble[k] = (byte) 0xC;
                            break;
                        case 'D':
                            nibble[k] = (byte) 0xD;
                            break;
                        case 'E':
                            nibble[k] = (byte) 0xE;
                            break;
                        case 'F':
                            nibble[k] = (byte) 0xF;
                            break;
                        case 'a':
                            nibble[k] = (byte) 0xA;
                            break;
                        case 'b':
                            nibble[k] = (byte) 0xB;
                            break;
                        case 'c':
                            nibble[k] = (byte) 0xC;
                            break;
                        case 'd':
                            nibble[k] = (byte) 0xD;
                            break;
                        case 'e':
                            nibble[k] = (byte) 0xE;
                            break;
                        case 'f':
                            nibble[k] = (byte) 0xF;
                            break;
                    }
                }

                b[j] = (byte) (nibble[0] << 4 | nibble[1]);
            }
            out[i] = (b[0] << 24) | (((b[1]) & 0xff) << 16) | (((b[2]) & 0xff) << 8) | ((b[3]) & 0xff);
        }

        return out;
    }


    /**
     * Convert an array of ints into a hex string.
     *
     * @param enc Array of integers.
     * @return String hexadecimal representation of the integer array.
     * @throws ArrayIndexOutOfBoundsException if the array doesn't contain pairs of integers.
     */
    public String binToHex(int enc[]) throws ArrayIndexOutOfBoundsException
    {
        // The number of ints should always be a multiple of two as required by TEA (64 bits).
        if ((enc.length % 2) == 1)
            throw new ArrayIndexOutOfBoundsException("Odd number of ints found: " + enc.length);

        StringBuffer sb = new StringBuffer();
        byte outb[] = new byte[8];
        int tmp[] = new int[2];
        int counter = enc.length / 2;

        for (int i = 0; i < enc.length; i += 2)
        {
            outb[0] = (byte) (enc[i] >>> 24);
            outb[1] = (byte) (enc[i] >>> 16);
            outb[2] = (byte) (enc[i] >>> 8);
            outb[3] = (byte) (enc[i]);
            outb[4] = (byte) (enc[i + 1] >>> 24);
            outb[5] = (byte) (enc[i + 1] >>> 16);
            outb[6] = (byte) (enc[i + 1] >>> 8);
            outb[7] = (byte) (enc[i + 1]);

            sb.append(getHex(outb));
        }

        return sb.toString();
    }

    // Display some bytes in HEX.
    //
    public String getHex(byte b[])
    {
        StringBuffer r = new StringBuffer();

        for (int i = 0; i < b.length; i++)
        {
            int c = ((b[i]) >>> 4) & 0xf;
            r.append(hex[c]);
            c = ((int) b[i] & 0xf);
            r.append(hex[c]);
        }

        return r.toString();
    }

    /*
/**
* Pad a string out to the proper length with the given character.
*
* @param str Plain text string.
* @param pc Padding character.
*
private String padPlaintext(String str, char pc)
{
    StringBuffer sb = new StringBuffer(str);
    int padding = sb.length() % 8;
    for (int i = 0; i < padding; i++)
        sb.append(pc);

    return sb.toString();
}

/**
* Pad a string out to the proper length with spaces.
*
* @param str Plain text string.
*
private String padPlaintext(String str)
{
    return padPlaintext(str, ' ');
}
    */

    public long getChecksum(String str)
    {
        java.util.zip.CRC32 crc = new java.util.zip.CRC32();
        byte b[] = str.getBytes();
        int y = 0;
        for (int x = 0; x < b.length; x++)
        {
            b[x] ^= _keyBytes[y];
            y++;
            if (y == _keyBytes.length) y = 0;
        }
        crc.update(b);
        return crc.getValue();
    }

}