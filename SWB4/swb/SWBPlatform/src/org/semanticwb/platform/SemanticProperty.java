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
package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.URLEncoder;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticProperty.
 * 
 * @author Jei
 */
public class SemanticProperty
{

    /** The log. */
    private static Logger log=SWBUtils.getLogger(SemanticProperty.class);

    /** The m_prop. */
    private Property m_prop;
    
    /** The m_inverse. */
    private SemanticProperty m_inverse;
    
    /** The is object property. */
    private Boolean isObjectProperty = null;
    
    /** The is data type property. */
    private Boolean isDataTypeProperty = null;
    
    /** The has inverse. */
    private Boolean hasInverse = null;
    
    /** The is inverse. */
    private boolean isInverse = false;
    
    /** The is external invocation. */
    private Boolean isExternalInvocation = null;
    
    /** The is inherit property. */
    private Boolean isInheritProperty = null;
    
    /** The is not observable. */
    private Boolean isNotObservable = null;
    
    /** The is not code generation. */
    private Boolean isNotCodeGeneration = null;
    
    /** The is remove dependency. */
    private Boolean isRemoveDependency = null;
    
    /** The is clone dependency. */
    private Boolean isCloneDependency = null;
    
    /** The is heraquical relation. */
    private Boolean isHeraquicalRelation = null;
    
    private Boolean hasHerarquicalFilterClass=null;
    private SemanticClass herarquicalFilterClass =null;
    
    /** The is required. */
    private Boolean isRequired = null;
    
    /** The is used as name. */
    private Boolean isUsedAsName = null;
    
    /** The is localeable. */
    private Boolean isLocaleable = null;
    
    /** The m_property code name. */
    private String m_propertyCodeName = null;
    
    /** The m_default value. */
    private String m_defaultValue = null;
    
    /** The display property. */
    private SemanticObject displayProperty = null;
    
    /** The disp property. */
    private boolean dispProperty = false;
    
    /** The cardinality. */
    private int cardinality = 0;
    
    /** The cardinality check. */
    private boolean cardinalityCheck = false;
    
    /** The range check. */
    private boolean rangeCheck = false;
    
    /** The range. */
    private Resource range = null;
    
    /** The restrictions. */
    private HashMap<String, ArrayList<SemanticRestriction>> restrictions = null;
    
    /** The allvalues. */
    private HashMap<String, SemanticRestriction> frestrictions = null;

    /** The m_observers. */
    private List<SemanticObserver> m_observers = null;

    /**
     * Instantiates a new semantic property.
     * 
     * @param prop the prop
     */
    public SemanticProperty(Property prop)
    {
        this.m_prop = prop;
        if (m_prop instanceof OntProperty)
        {
            if (hasInverse())
            {
                m_inverse = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(((OntProperty) m_prop).getInverse());
                m_inverse.isInverse = true;
                m_inverse.m_inverse = this;
                //System.out.println(prop+" hasInverse "+m_inverse);
            }

        }

        m_observers=Collections.synchronizedList(new ArrayList());
    }

    /**
     * Load restrictions.
     */
    private void loadRestrictions()
    {
        restrictions = new HashMap();
        frestrictions = new HashMap();
        Iterator<Restriction> it = ((OntProperty) m_prop).listReferringRestrictions();
        while (it.hasNext())
        {
            Restriction restriction = it.next();
            //System.out.println(prop+" restriction "+restriction);
            Iterator<OntClass> it2 = restriction.listSubClasses();
            while (it2.hasNext())
            {
                OntClass ontClass = it2.next();
                ArrayList<SemanticRestriction> list = restrictions.get(ontClass.getURI());
                if (list == null)
                {
                    list = new ArrayList();
                    restrictions.put(ontClass.getURI(), list);
                }
                list.add(new SemanticRestriction(restriction));
                //System.out.println(prop+" restrictioncls "+ontClass);
            }
        }
    }

    /**
     * Gets the semantic object.
     * 
     * @return the semantic object
     */
    public SemanticObject getSemanticObject()
    {
        return SemanticObject.createSemanticObject(getURI());
    }

    /**
     * Gets the rDF property.
     * 
     * @return the rDF property
     */
    public Property getRDFProperty()
    {
        return m_prop;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName()
    {
        return m_prop.getLocalName();
    }

    /**
     * Gets the prefix.
     * 
     * @return the prefix
     */
    public String getPrefix()
    {
        return m_prop.getModel().getNsURIPrefix(m_prop.getNameSpace());
    }

    /**
     * Gets the prop id.
     * 
     * @return the prop id
     */
    public String getPropId()
    {
        return getPrefix() + ":" + getName();
    }

    /**
     * Gets the label.
     * 
     * @return the label
     */
    public String getLabel()
    {
        return getLabel(null);
    }

    /**
     * Gets the Comment.
     *
     * @return the label
     */
    public String getComment()
    {
        return getComment(null);
    }

    /**
     * Gets the label.
     *
     * @param lang the lang
     * @return the label
     */
    public String getComment(String lang)
    {
        String ret = null;
        if (m_prop instanceof OntProperty)
        {
            ret = ((OntProperty) m_prop).getComment(lang);
        }
        return ret;
    }

    /**
     * Gets the label.
     * 
     * @param lang the lang
     * @return the label
     */
    public String getLabel(String lang)
    {
        String ret = null;
        if (m_prop instanceof OntProperty)
        {
            ret = ((OntProperty) m_prop).getLabel(lang);
        }
        return ret;
    }

    /**
     * Gets the property code name.
     * 
     * @return the property code name
     */
    public String getPropertyCodeName()
    {
        if (m_propertyCodeName == null)
        {
            try
            {
                Property prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_PROPERTYCODENAME).getRDFProperty();
                //System.out.println("Class:"+m_class+" ->"+className);
                m_propertyCodeName = m_prop.getRequiredProperty(prop).getString();
                //System.out.println("Class:"+m_class+" ->"+className);
                if (m_propertyCodeName == null)
                {
                    m_propertyCodeName = getName();
                }
            }
            catch (Exception pnf)
            {
                m_propertyCodeName = getName();
            }
            //log.trace("getClassName:"+m_className);
        }
        return m_propertyCodeName;
    }

    /**
     * Gets the default value.
     * 
     * @return the default value
     */
    public String getDefaultValue()
    {
        if (m_defaultValue == null)
        {
            Property prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_DEFAULTVALUE).getRDFProperty();
            Statement st = m_prop.getProperty(prop);
            if (st != null)
            {
                m_defaultValue = st.getString();
            }
        }
        if(m_defaultValue!=null && m_defaultValue.charAt(0)=='{')
        {
            if(m_defaultValue.equals("{invtime}"))return ""+(Long.MAX_VALUE-System.currentTimeMillis());
            if(m_defaultValue.equals("{time}"))return ""+(System.currentTimeMillis());
        }
        return m_defaultValue;
    }

    /**
     * Gets the uRI.
     * 
     * @return the uRI
     */
    public String getURI()
    {
        return m_prop.getURI();
    }

    /**
     * Regresa URI codificado para utilizar en ligas de html.
     * 
     * @return URI Codificado
     */
    public String getEncodedURI()
    {
        return URLEncoder.encode(getURI());
    }

    /**
     * Gets the required property.
     * 
     * @param prop the prop
     * @return the required property
     */
    public SemanticLiteral getRequiredProperty(SemanticProperty prop)
    {
        SemanticLiteral ret = null;
        Statement st = m_prop.getProperty(prop.getRDFProperty());
        if (st != null)
        {
            ret = new SemanticLiteral(st);
        }
        return ret;
    }

    /**
     * Checks if is localeable.
     * 
     * @return true, if is localeable
     */
    public boolean isLocaleable()
    {
        if (isLocaleable == null)
        {
            isLocaleable = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_LOCALEABLE));
            if (st != null)
            {
                isLocaleable = st.getBoolean();
            }
        }
        return isLocaleable;
    }

    /**
     * Checks if is used as name.
     * 
     * @return true, if is used as name
     */
    public boolean isUsedAsName()
    {
        if (isUsedAsName == null)
        {
            isUsedAsName = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_ANNOT_CANUSEDASNAME));
            if (st != null)
            {
                isUsedAsName = st.getBoolean();
            }
        }
        return isUsedAsName;
    }

    /**
     * Checks if is required.
     * 
     * @return true, if is required
     */
    public boolean isRequired()
    {
        if (isRequired == null)
        {
            isRequired = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_REQUIRED));
            if (st != null)
            {
                isRequired = st.getBoolean();
            }
        }
        return isRequired;
    }

    /**
     * Si esta propiedad se utiliza para definir la relacio padre-hijo en el arbol de navegacion.
     * 
     * @return true, if is heraquical relation
     * @return
     */
    public boolean isHeraquicalRelation()
    {
        if (isHeraquicalRelation == null)
        {
            isHeraquicalRelation = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_HERARQUICALRELATION));
            if (st != null)
            {
                isHeraquicalRelation = st.getBoolean();
            }
        }
        return isHeraquicalRelation;
    }
    
    /**
     * Si esta propiedad se utiliza para definir la relacio padre-hijo en el arbol de navegacion.
     * 
     * @return true, if is heraquical relation
     * @return
     */
    public SemanticClass getHerarquicalRelationFilterClass()
    {
        if (hasHerarquicalFilterClass == null)
        {
            hasHerarquicalFilterClass = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_HERARQUICALRELATIONFILTERCLASS));
            if (st != null)
            {
                Resource res=st.getResource();       
                if(res!=null)
                {
                    herarquicalFilterClass=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(res.getURI());
                }
                hasHerarquicalFilterClass=true;
            }
        }
        return herarquicalFilterClass;
    }    
    
    /**
     * Esta propiedad se utiliza para eliminar el objeto relacionado, si el objeto de dominio se elimina.
     * 
     * @return true, if is removes the dependency
     * @return
     */
    public boolean isRemoveDependency()
    {
        if (isRemoveDependency == null)
        {
            isRemoveDependency = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_REMOVEDEPENDENCY));
            if (st != null)
            {
                isRemoveDependency = st.getBoolean();
            }
        }
        return isRemoveDependency;
    }

    /**
     * Esta propiedad se utiliza para clonar el objeto relacionado, si el objeto de dominio se clona.
     * 
     * @return true, if is clone dependency
     * @return
     */
    public boolean isCloneDependency()
    {
        if (isCloneDependency == null)
        {
            isCloneDependency = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_CLONEDEPENDENCY));
            if (st != null)
            {
                isCloneDependency = st.getBoolean();
            }
        }
        return isCloneDependency;
    }

    /**
     * Esta propiedad se utiliza para desabilitar el log de cambios de la propiedad.
     * 
     * @return true, if is not observable
     * @return
     */
    public boolean isNotObservable()
    {
        if (isNotObservable == null)
        {
            isNotObservable = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_NOTOBSERVABLE));
            if (st != null)
            {
                isNotObservable = st.getBoolean();
            }
        }
        return isNotObservable;
    }

    /**
     * Esta propiedad se utiliza para desabilitar la generacion de código de la propiedad.
     * 
     * @return true, if is not code generation
     * @return
     */
    public boolean isNotCodeGeneration()
    {
        if (isNotCodeGeneration == null)
        {
            isNotCodeGeneration = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_NOTCODEGENERATION));
            if (st != null)
            {
                isNotCodeGeneration = st.getBoolean();
            }
        }
        return isNotCodeGeneration;
    }

    /**
     * Define si la apropiedad es heredable a los hijos.
     * 
     * @return true, if is inherit property
     * @return
     */
    public boolean isInheritProperty()
    {
        if (isInheritProperty == null)
        {
            isInheritProperty = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_INHERITPROPERTY));
            if (st != null)
            {
                isInheritProperty = st.getBoolean();
            }
        }
        return isInheritProperty;
    }

    /**
     * Si esta propiedad se utiliza para definir la relacio hijo-padre en el arbol de navegacion.
     * 
     * @return true, if is inverse heraquical relation
     * @return
     */
    public boolean isInverseHeraquicalRelation()
    {
        boolean ret = false;
        SemanticProperty inv = getInverse();
        if (inv != null && inv.isHeraquicalRelation())
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is external invocation.
     * 
     * @return true, if is external invocation
     */
    public boolean isExternalInvocation()
    {
        if (isExternalInvocation == null)
        {
            isExternalInvocation = false;
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_EXTERNALINVOCATION));
            if (st != null)
            {
                isExternalInvocation = st.getBoolean();
            }
        }
        return isExternalInvocation;
    }

    /**
     * Gets the display property.
     * 
     * @return the display property
     */
    public SemanticObject getDisplayProperty()
    {
        if (!dispProperty)
        {
            Statement st = m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DISPLAYPROPERTY));
            if (st != null)
            {
                displayProperty = SemanticObject.createSemanticObject(st.getResource());
                dispProperty = true;
            }
        }
        return displayProperty;
    }

    /**
     * Gets the display name.
     * 
     * @return the display name
     */
    public String getDisplayName()
    {
        return getDisplayName(null);
    }

    /**
     * Gets the display name.
     * 
     * @param lang the lang
     * @return the display name
     */
    public String getDisplayName(String lang)
    {
        String ret = null;
        SemanticObject obj = getDisplayProperty();
        if (obj != null)
        {
            if (lang != null)
            {
                ret = obj.getProperty(obj.getModel().getSemanticProperty(SemanticVocabulary.RDFS_LABEL), null, lang);
            }
            else
            {
                ret = obj.getProperty(obj.getModel().getSemanticProperty(SemanticVocabulary.RDFS_LABEL), null, "es");
            }
            if (ret == null)
            {
                ret = obj.getProperty(obj.getModel().getSemanticProperty(SemanticVocabulary.RDFS_LABEL));
            }
        }
        if (ret == null)
        {
            ret = getLabel(lang);
        }
        if (ret == null)
        {
            ret = getLabel();
        }
        if (ret == null)
        {
            ret = getName();
        }
        //System.out.println("Prop:"+obj+" "+ret);
        return ret;
    }
//    
//    public String getViewGroup()
//    {
//        String ret=null;
//        Statement st=m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_ANNOT_PROPGROUP));
//        if(st!=null)
//        {
//            return st.getString();
//        }
//        return ret;
//    }       
//    
//    public int getSortIndex()
//    {
//        int ret=99999999;
//        Statement st=m_prop.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_ANNOT_PROPINDEX));
//        if(st!=null)
//        {
//            return st.getInt();
//        }
//        return ret;
//    }    

    /* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
    public String toString()
    {
        return m_prop.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return m_prop.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj==null)return false;
        return hashCode() == obj.hashCode();
    }

    /**
     * Gets the domain class.
     * 
     * @return the domain class
     */
    public SemanticClass getDomainClass()
    {
        if (hasInverse())
        {
            return m_inverse.getRangeClass();
        }
        SemanticClass ret = null;
        Statement stm = m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDFS_DOMAIN));
        if (stm != null)
        {
            String domclsid = stm.getResource().getURI();
            if (domclsid != null)
            {
                ret = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(domclsid);
                //TODO: eliminar esto cuando se separe el vocabulario por ontologia
                if (ret == null)
                {
                    ret = new SemanticClass(((OntModel) stm.getResource().getModel()).getOntClass(domclsid));
                    SWBPlatform.getSemanticMgr().getVocabulary().registerClass(ret);
                }
            }

        }
        return ret;
    }

    /**
     * Gets the range class.
     * 
     * @return the range class
     */
    public SemanticClass getRangeClass()
    {
        if (hasInverse())
        {
            return m_inverse.getDomainClass();
        }
        SemanticClass ret = null;
        Statement stm = m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDFS_RANGE));
        if (stm != null)
        {
            ret = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
        }
        return ret;
    }

    /**
     * Gets the range.
     * 
     * @return the range
     */
    public Resource getRange()
    {
        if (!rangeCheck)
        {
            Statement stm = m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDFS_RANGE));
            if (stm != null)
            {
                range = stm.getResource();

            }
            rangeCheck = true;
        }
        return range;
    }

    /**
     * Gets the cardinality of the property
     * @return the cardinality, 0 for multiple and 1 for single
     */
    public int getCardinality()
    {
        if (!cardinalityCheck)
        {
            String n = getPropertyCodeName();
            if (n == null)
            {
                n = getName();
            }
            if (n.startsWith("has"))
            {
                cardinality = 0;
            }
            else
            {
                cardinality = 1;
            }
            cardinalityCheck = true;
        }
        return cardinality;
    }

    /**
     * Checks if is object property.
     * 
     * @return true, if is object property
     */
    public boolean isObjectProperty()
    {
        if (isObjectProperty == null)
        {
            isObjectProperty = false;
            Statement stm = m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
            if (stm != null)
            {
                isObjectProperty = SemanticVocabulary.OWL_OBJECTPROPERTY.equals(stm.getResource().getURI());
                if (!isObjectProperty)
                {
                    OntClass ontClassDataType = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().createClass(SemanticVocabulary.OWL_OBJECTPROPERTY);
                    if (ontClassDataType.hasSubClass(stm.getResource()))
                    {
                        isObjectProperty = Boolean.TRUE;
                    }
                }
            }
        }
        return isObjectProperty;
    }

    /**
     * Checks if is data type property.
     * 
     * @return true, if is data type property
     */
    public boolean isDataTypeProperty()
    {
        if (isDataTypeProperty == null)
        {
            isDataTypeProperty = false;
            Statement stm = m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
            if (stm != null)
            {
                isDataTypeProperty = SemanticVocabulary.OWL_DATATYPEPROPERTY.equals(stm.getResource().getURI());                
                if (!isDataTypeProperty)
                {
                    OntClass ontClassDataType = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().createClass(SemanticVocabulary.OWL_DATATYPEPROPERTY);
                    if (ontClassDataType.hasSubClass(stm.getResource()))
                    {
                        isDataTypeProperty = Boolean.TRUE;
                    }
                }
            }
        }
        return isDataTypeProperty;
    }

    /**
     * Esta propiedad es la inversa de otra (no genera statements).
     * 
     * @return true, if successful
     * @return
     */
    public boolean hasInverse()
    {
        if (hasInverse == null)
        {
            hasInverse = false;
            if (m_prop instanceof OntProperty)
            {
                hasInverse = ((OntProperty) m_prop).hasInverse();
            }
        }
        return hasInverse;
    }

    /**
     * Esta propiedad es normal pero tiene una inversa.
     * 
     * @return true, if is inverse of
     * @return
     */
    public boolean isInverseOf()
    {
        return isInverse;
    }

    /**
     * Gets the inverse.
     * 
     * @return the inverse
     */
    public SemanticProperty getInverse()
    {
        return m_inverse;
    }

    /**
     * Checks if is boolean.
     * 
     * @return true, if is boolean
     */
    public boolean isBoolean()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_BOOLEAN))
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is int.
     * 
     * @return true, if is int
     */
    public boolean isInt()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && (res.getURI().equals(SemanticVocabulary.XMLS_INTEGER)||res.getURI().equals(SemanticVocabulary.XMLS_INT)))
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is binary.
     * 
     * @return true, if is binary
     */
    public boolean isBinary()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_BASE64BINARY))
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is long.
     * 
     * @return true, if is long
     */
    public boolean isLong()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_LONG))
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is date.
     * 
     * @return true, if is date
     */
    public boolean isDate()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_DATE))
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is date time.
     * 
     * @return true, if is date time
     */
    public boolean isDateTime()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_DATETIME))
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is string.
     * 
     * @return true, if is string
     */
    public boolean isString()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_STRING))
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is float.
     * 
     * @return true, if is float
     */
    public boolean isFloat()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_FLOAT))
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is xML.
     * 
     * @return true, if is xML
     */
    public boolean isXML()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.RDF_XMLLITERAL))
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is double.
     * 
     * @return true, if is double
     */
    public boolean isDouble()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_DOUBLE))
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is byte.
     * 
     * @return true, if is byte
     */
    public boolean isByte()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_BYTE))
        {
            ret = true;
        }
        return ret;
    }

    /**
     * Checks if is short.
     * 
     * @return true, if is short
     */
    public boolean isShort()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_SHORT))
        {
            ret = true;
        }
        return ret;
    }
    
    /**
     * Checks if is short.
     * 
     * @return true, if is short
     */
    public boolean isDecimal()
    {
        boolean ret = false;
        Resource res = getRange();
        if (res != null && res.getURI().equals(SemanticVocabulary.XMLS_DECIMAL))
        {
            ret = true;
        }
        return ret;
    }    

    /**
     * Checks if is numeric.
     * 
     * @return true, if is numeric
     */
    public boolean isNumeric()
    {
        return isInt() || isLong() || isByte() || isDouble() || isFloat() || isShort();
    }
    
    /**
     * Gets restrictions from the property an the class.
     *
     * @param cls the cls
     * @return iterator of SemanticRestrictions
     */
    public Iterator<SemanticRestriction>listRestrictions(SemanticClass cls)
    {
        if(restrictions==null)loadRestrictions();
        Iterator it=new ArrayList().iterator();
        ArrayList list=restrictions.get(cls.getURI());
        if(list!=null)it=list.iterator();
        return it;
    }

    /**
     * Gets restriction from the property an the class or null if don´t have.
     * 
     * @param cls the cls
     * @return SemanticRestrictions
     */
    public SemanticRestriction getValuesFromRestriction(SemanticClass cls)
    {
        if(restrictions==null)loadRestrictions();
        int level=-1;
        SemanticRestriction rcls = frestrictions.get(cls.getURI());
        if (rcls == null && !frestrictions.containsKey(cls.getURI()))
        {
            ArrayList list = restrictions.get(cls.getURI());
            if (list != null)
            {
                Iterator<SemanticRestriction> it = list.iterator();
                while (it.hasNext())
                {
                    SemanticRestriction restriction = it.next();
                    if(restriction.isAllValuesFromRestriction() || restriction.isSomeValuesFromRestriction() || restriction.isHasValueRestriction())
                    {
                        int l=restriction.getSubClassLevel(cls);
                        if(level<0 || level>l)
                        {
                            rcls=restriction;
                            level=l;
                        }
                    }
                }
            }
            frestrictions.put(cls.getURI(), rcls);
        }
        return rcls;
    }

    /**
     * Register observer.
     *
     * @param obs the obs
     */
    public void registerObserver(SemanticObserver obs) {
        m_observers.add(obs);
    }

    /**
     * Removes the observer.
     *
     * @param obs the obs
     */
    public void removeObserver(SemanticObserver obs) {
        m_observers.remove(obs);
    }

    /**
     * Notify change.
     *
     * @param obj the obj
     * @param prop the prop
     * @param lang the lang
     * @param action the action
     */
    public void notifyChange(SemanticObject obj, Object prop, String lang, String action)
    {
        //log.trace("notifyChange: obj:" + obj + " prop:" + prop + " " + action);
        Iterator it = m_observers.iterator();
        while (it.hasNext()) {
            SemanticObserver obs = (SemanticObserver) it.next();
            try {
                obs.notify(obj, prop, lang, action);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }


}
