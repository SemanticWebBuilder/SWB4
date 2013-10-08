/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.indexer;

import java.io.File;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Searchable;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author javier.solis.g
 */
public class FileSearchWrapper implements Searchable
{
    private File file;
    private String title;
    private String description;
    private String url;
    private Searchable page;
    private String tags;
    
    
    public FileSearchWrapper(File file, String title, String description, String tags, String url, Searchable resource)
    {
        this.file=file;
        this.title=title==null?"":title;
        this.description=description==null?"":description;
        this.url=url;
        this.page=resource;
        this.tags=tags==null?"":tags;
    }

    @Override
    public boolean isValid()
    {
        return true;
    }

    @Override
    public String getURI()
    {
        return "file:"+file.getAbsolutePath();
    }

    @Override
    public String getShortURI()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SemanticObject getSemanticObject()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GenericObject setProperty(String prop, String value)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GenericObject removeProperty(String prop)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getWorkPath()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dispose()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Searchable getSearchable()
    {
        return page;
    }

    public File getFile()
    {
        return file;
    }

    public String getTitle()
    {
        //System.out.println("Título archivo..."+title);
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String getUrl()
    {
        return url;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setSearchable(Searchable resource)
    {
        this.page = resource;
    }

    public String getTags()
    {
        return tags;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }

    @Override
    public String getId()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String getModelId()
    {
        return page.getSemanticObject().getModel().getName();
    }
    
    
}
