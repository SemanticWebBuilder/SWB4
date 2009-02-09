package org.semanticwb.model;

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class UserGroup extends UserGroupBase 
{
    public UserGroup(SemanticObject base)
    {
        super(base);
    }

    public Iterator<UserGroup> listAllChilds()
    {
        ArrayList<UserGroup> arr=new ArrayList();
        addChilds(arr);
        return arr.iterator();
    }

    private void addChilds(ArrayList<UserGroup> arr)
    {
        arr.add(this);
        Iterator<UserGroup> it=listChilds();
        while(it.hasNext())
        {
            UserGroup aux=it.next();
            aux.addChilds(arr);
        }
    }


}
