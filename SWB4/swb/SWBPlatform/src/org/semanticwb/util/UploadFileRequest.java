package org.semanticwb.util;

import java.util.HashMap;

/**
 *
 * @author serch
 */
public class UploadFileRequest
{

    private final HashMap<String, String> filtros;
    private final boolean multiples;
    private final long size;

    public UploadFileRequest(HashMap<String, String> filtros, boolean multiples, long size)
    {
        this.filtros = filtros;
        this.multiples = multiples;
        this.size = size;

    }

    public HashMap<String, String> getFiltros()
    {
        return filtros;
    }

    public boolean isMultiples()
    {
        return multiples;
    }

    public long size()
    {
        return size;
    }
}
