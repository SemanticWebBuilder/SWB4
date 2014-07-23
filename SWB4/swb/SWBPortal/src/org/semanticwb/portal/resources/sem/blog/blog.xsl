<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" version="1.0" encoding="ISO-8859-1" omit-xml-declaration="yes" />

    <!-- Esta plantilla requiere DOJO para su funcionamiento -->
    <xsl:template match="/">

        <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.9.1/dijit/themes/nihilo/nihilo.css" type="text/css" media="screen" id="themeCss"></link>
        <!--<link rel="stylesheet" href="{/blog/@webpath}/swbadmin/js/dojo/dijit/themes/nihilo/nihilo.css" type="text/css" media="screen" id="themeCss"></link>-->
        <!-- script TYPE="text/javascript" SRC="{blog/@webpath}/swbadmin/js/dojo/dojo/dojo.js"></script -->
        <link rel="stylesheet" href="{/blog/@webpath}/swbadmin/js/dojo/dojo/resources/dojo.css" type="text/css" media="screen" id="themeCss"></link>
		<link rel="stylesheet" href="{/blog/@webpath}/swbadmin/js/dojo/dijit/themes/dijit.css" type="text/css" media="screen" id="themeCss"></link>
		<link rel="stylesheet" href="{/blog/@webpath}/swbadmin/js/dojo/dojox/layout/resources/FloatingPane.css" type="text/css" media="screen" id="themeCss"></link>
		<link rel="stylesheet" href="{/blog/@webpath}/swbadmin/js/dojo/dojox/layout/resources/ResizeHandle.css" type="text/css" media="screen" id="themeCss"></link>
        <link rel="stylesheet" href="{/blog/@webpath}/swbadmin/js/dojo/dojox/grid/_grid/nihiloGrid.css" type="text/css" media="screen" id="themeCss"></link>
                
                
                
                <script type="text/javascript">
                
                var djConfig = {
					isDebug: false
				};

                dojo.require("dijit.dijit");

                    dojo.require("dojox.layout.FloatingPane"); 
                    dojo.require("dijit.form.Button"); 
                    dojo.require("dijit.form.TextBox");
                    
                    dojo.require("dijit.Editor");
                    dojo.require("dijit._editor.plugins.LinkDialog");
                    dojo.require("dijit._editor.plugins.FontChoice");
                    dojo.require("dijit._editor.plugins.TextColor");
                    dojo.require("dojo.parser");
                    dojo.require("dojox.xml.parser");
                </script>                
                
                <style>
                    body
                    {
                        font-family: Verdana, "Times New Roman", serif;
                    }

                    .postDate
                    {
                        color: #800000;
                        font-size: 10px;
                        font-weight: normal;
                        font-style:italic;
                    }
                    .postAutor
                    {
                        color: gray;
                        font-size: 11px;
                        font-weight: normal;
                        font-style:italic;
                    }

                    .postDescription
                    {
                        font-size: 12px;
                        font-weight: normal;
                    }

                    .postTitle
                    {
                        color: #000000;
                        font-size: 20px;
                    }
                    .commentDate
                    {
                        color: #800000;
                        font-size: 10px;
                        font-weight: normal;
                        font-style:italic;
                    }

                    .commentUser
                    {
                        color: gray;
                        font-size: 11px;
                        font-weight: normal;
                        font-style:italic;
                    }

                    .commentText
                    {
                        font-size: 12px;
                        font-weight: normal;
                    }

                    .titleBlog
                    {
                        color: #005C89;
                        text-align: center;
                        font-size: 24px;
                        font-family: Verdana, "Times New Roman", serif;
                    }

                    fieldset
                    {
                        border-style: solid;
                        border-color: silver;
                        border-width: thin;
                        margin:2em;
                        padding-left:1em;
                        padding-right:1em;
                        padding-bottom:1em;
                        padding-top:1em;
                        text-align:justify;
                    }

                    a
                    {
                        font-weight:lighter;
                        color:teal;
                        text-decoration:none;
                        font-size:11px;
                        font-style:italic;
                    }

                </style>
            
                <script type="text/javascript">                      
                    function cancel()
                    {
                       resetForma();
                       hideForm();
                    }
                    function resetForma()
                    {
                    dijit.byId('_title').setValue('');
                    dijit.byId('editor').setValue('');
                    var mode=dojo.byId('mode').value;
                    if(mode=='update')
                    {
                    dijit.byId('buttoncancel').setLabel('Cancelar');
                    dijit.byId('buttonsubmit').setLabel('Guardar');
                    }
                    dojo.byId('mode').value='create';
                    dojo.byId('postid').value=''; 
                    dojo.byId('description').value=''; 
                    }
                    function editPost(postid)
                    {
                        showForm();                        
                        var urltitle=urleditpost+'?postid='+ postid +'&amp;mode=getTitle';                                                
                        var bindArgs = 
                        {
                            sync: true,
                            handleAs: 'xml',
                            url: urltitle,
                            error: function(data)
                            {
                                alert("No existe conexión en el sitio web");
                            },
                            load: function(response, ioArgs)
                            {
                                var title=response.getElementsByTagName('title')[0].firstChild.nodeValue;
                                dijit.byId('_title').setValue(title);
                            },
                            mimetype: "text/xml"
                        };
                        dojo.xhrGet(bindArgs);
                    
                        var urlContent=urleditpost+'?postid='+ postid +'&amp;mode=getContent';

                        var bindArgs2 = 
                        {
                        sync: true,
                            handleAs: 'xml',
                        url: urlContent,
                        error: function(data)
                        {
                        alert("No existe conexión en el sitio web");
                        },
                        load: function(response, ioArgs)
                        {       
                        var data=response.getElementsByTagName('content')[0].firstChild.nodeValue;                                                
                        dijit.byId('editor').setValue(data);
                        },
                        mimetype: "text/xml"
                        };
                        dojo.xhrGet(bindArgs2);
                        dojo.require('dijit.form.Button');                       
                        dojo.byId('mode').value='update';
                        dojo.byId('postid').value=postid;  
                        dijit.byId('buttoncancel').setLabel('Cancelar edici&amp;oacute;n');
                        dijit.byId('buttonsubmit').setLabel('Actualizar Post');
                    
                    }
                    function showForm()
                    {
                        dojo.byId('forma').style.display='inline';
                    }
                    function hideForm()
                    {
                        dojo.byId('forma').style.display='none';                        
                    }
                    function addPost()
                    {
                        showForm();
                        resetForma();
                    }
                    function deletePost(postid,blogid)
                    {
                    var url=urladd+'?postid='+postid+'&amp;blogid='+blogid+'&amp;mode=delete';
                    var msg='¿Desea eliminar el post?';
                    if(confirm(msg))
                    {
                    this.document.location=url;
                    }
                    }
                    function addComent(postid,blogid)
                    {
                    var url=urladdComments+'?postid='+postid+'&amp;blogid='+blogid;
                    this.document.location=url;
                    }
                    function viewComents(postid,blogid)
                    {
                    var url=urlviewComments+'?postid='+postid+'&amp;blogid='+blogid;
                    this.document.location=url;
                    }
                    
                    
		    var _editor=document.getElementById('editor');
		 
		    dojo.addOnLoad(
		    function() { 
		    
		    /*new dijit.Editor(
		    	            			{  
		    	            			
		    	            				plugins:['bold','italic','underline', 'strikethrough','|','insertUnorderedList','insertOrderedList','|','createLink','unlink']},
	            				'editor');*/
	            				
	            var _title=new dijit.form.TextBox({			            						
		    	            				required:"true",
		    	            				type: "text"
							},
	            				'_title');
	            				
	            
		    
	            				
	            var _buttonsubmit=new dijit.form.Button({},'buttonsubmit');				
	            var _buttoncancel=new dijit.form.Button({},'buttoncancel');
	            
	            dojo.connect(_buttonsubmit, 'onClick', 'validaForma');
	            dojo.connect(_buttoncancel, 'onClick', 'cancel');
			
		    
		    });

                    
                    function validaForma()
                    {
                        var forma=document.frmaddpost;
                    	var textTitle=dijit.byId('_title');                    	                    	
                    	var content = textTitle.attr("value");                    	                    	
                    	if(!content)
                    	{
                    		alert('¡Debe ingresar el título de la entrada!');                    	
                    		return;
                    	}
                    	var contentEditor = dijit.byId('editor').getValue(false);                    	
                    if(!contentEditor)
                    {
                    alert('Debe ingresar la entrada del blog');
                    return;
                    }      
                    var msg='¿Estan los datos correctos de la entrada del blog?';
                    if(confirm(msg))
                    {
                        dojo.byId('description').value=contentEditor;                                            
                        dojo.byId('title').value=content;                                            
                        dojo.byId('frmaddpost').action=urladd+'?title='+content;                                                                                                             
                        dojo.byId('frmaddpost').submit();
                    }
                    }                        
                </script>
        <xsl:for-each select="blog">
            <span class="titleBlog" style="margin:2em;"><xsl:value-of select='@name' /><br/>
                <xsl:if test="@level = '3'">
                    <span style="margin:2em;">
                        <xsl:element name="a">
                            <xsl:attribute name="href">javascript:addPost();</xsl:attribute>
                            <!--img alt="Agregar Entrada" border="0">
                                <xsl:attribute name="src"><xsl:value-of select='/blog/@addimage' /></xsl:attribute>
                            </img-->
                            &#160;&#160;[ Agregar Entrada ]
                        </xsl:element>
                    </span>
                </xsl:if>
            </span>
            <xsl:if test="@level = '3'">
                <div id="forma" align="center" style="display:none;">
                    <form id="frmaddpost" method="post" action="" >
                        <input type="hidden" id="description" name="description"></input>
                        <input type="hidden" id="mode" name="mode" value="create"></input>
                        <input type="hidden" id="postid" name="postid" value=""></input>
                        <input type="hidden" id="title" name="title" value=""></input>
                        <fieldset>
                            <div class="nihilo" id="text">
                                Título de la entrada: <input type="text" id="_title"  name="title" size="80" maxlength="255" value="Título de la entrada"></input>
                            </div>
                            <br></br><br></br>
                            <div class="nihilo" id="editor3"  >
      <!--                          <textarea id="editor" name="editor" rows="50" cols="30">Nueva entrada del blog
                                </textarea> -->
                                <div data-dojo-type="dijit/Editor" id="editor"> <p>Nueva entrada del blog</p></div>
                            </div>
                            <div class="nihilo">
                                <button id="buttonsubmit" type="button" onClick="javascript:validaForma()">
                                    Guardar
                                </button>
                                <button id="buttoncancel" type="button" onClick="javascript:cancel()">
                                    Cancelar
                                </button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </xsl:if>
        </xsl:for-each>
        <xsl:for-each select="blog/post">
        <fieldset>
            <!--xsl:if test="/blog/@level  &gt; 0"-->
                <span class="titleBlog"><xsl:value-of select='@name' /></span><br/>
                <a><xsl:attribute name="href"><xsl:value-of select='@url' /></xsl:attribute><xsl:value-of select='@name' /></a>
                    
                        <span class="postTitle" ><xsl:value-of select='@title' /></span><br/><br/>
                        <span class="postAutor" >Entrada publicada por <span style="color:black;font-weight:bold;"><xsl:value-of select='@author' /></span></span><br/>
                        <span class="postDate" ><xsl:value-of select='@date' /></span><br/>
                        <xsl:if test="/blog/@level &gt; '1'">
                            <br/>
                            <xsl:element name="a">
                                <xsl:attribute name="href">javascript:viewComents('<xsl:value-of select='@id' />','<xsl:value-of select='@blogid' />');</xsl:attribute>
                                comentarios(<xsl:value-of select='@comments' />)&#160;&#160;
                            </xsl:element>
                        </xsl:if>
                        <xsl:if test="/blog/@level= '3'">
                            [
                            <xsl:element name="a">
                                <xsl:attribute name="href">javascript:editPost('<xsl:value-of select='@id' />');</xsl:attribute>
                                <!--img alt="Editar Entrada" border="0">
                                    <xsl:attribute name="src"><xsl:value-of select='/blog/@editimage' /></xsl:attribute>
                                </img-->
                                Editar
                            </xsl:element>
                        |
                            <xsl:element name="a">
                                <xsl:attribute name="href">javascript:deletePost('<xsl:value-of select='@id' />','<xsl:value-of select='@blogid' />');</xsl:attribute>
                                <!--img alt="Borrar Entrada" border="0">
                                    <xsl:attribute name="src"><xsl:value-of select='/blog/@deleteimage' /></xsl:attribute>
                                </img-->
                                Borrar
                            </xsl:element>
                            ]
                        </xsl:if>

                        <p class="postDescription"><xsl:value-of select='description' /></p>

                
            <!-- /xsl:if -->
            </fieldset>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
