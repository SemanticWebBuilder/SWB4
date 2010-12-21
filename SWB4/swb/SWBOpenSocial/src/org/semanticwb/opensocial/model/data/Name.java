package org.semanticwb.opensocial.model.data;

import org.json.JSONException;
import org.json.JSONObject;


public class Name extends org.semanticwb.opensocial.model.data.base.NameBase implements JSONConvertible
{
    public Name(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public JSONObject toJSONObject()
    {
        JSONObject obj=new JSONObject();
        try
        {
            obj.put("formatted", this.getFormatted());
        }
        catch(JSONException jse)
        {
            jse.printStackTrace();
        }
        return obj;
    }

    @Override
    public String toString()
    {
        if(this.getFormatted()!=null)
        {
            return this.getFormatted();
        }
        return super.toString();
    }

}
