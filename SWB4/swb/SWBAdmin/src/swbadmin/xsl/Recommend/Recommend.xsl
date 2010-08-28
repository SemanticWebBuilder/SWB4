<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>
    
    <xsl:template match="/form">
        <div class="rec_box">
            <xsl:choose>
                <xsl:when test="@email ='1'">
                    <table>
                        <tr>
                            <td>
                                <font face="Verdana, Arial, Helvetica, sans-serif" size="2">
                                ----------------------------------------------------------------------<br />
                                Recomendación especial<BR/>
                                ----------------------------------------------------------------------<br />
                                <xsl:if test="count(headermsg) &gt; 0">
                                    <p><xsl:value-of select="headermsg" disable-output-escaping="yes" /></p>
                                </xsl:if>
                                <p>Hola <i><xsl:value-of select="toname"/></i>,</p>
                                <p>Tu amigo(a) <i><xsl:value-of select="fromname"/></i> te recomienda que visites la siguiente dirección:</p>
                                <xsl:text disable-output-escaping="yes">&lt;a href=&quot;</xsl:text>
                                <xsl:value-of select="topicurl"/>
                                <xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
                                    <xsl:value-of select="topic"/>
                                <xsl:text disable-output-escaping="yes">&lt;/a&gt;</xsl:text>
                                <xsl:if test="count(message) &gt; 0">
                                    <p><xsl:value-of select="message" /></p>
                                </xsl:if>
                                <xsl:if test="count(footermsg) &gt; 0">
                                    <p><xsl:value-of select="footermsg" disable-output-escaping="yes" /></p>
                                </xsl:if>
                                <p>----------------------------------------------------------------------</p>
                                <p>Te invitamos a que visites nuestro portal en la siguiente dirección:</p>
                                <p>
                                    <xsl:text disable-output-escaping="yes">&lt;a href=&quot;</xsl:text>
                                    <xsl:value-of select="siteurl"/>
                                    <xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
                                        <xsl:value-of select="site"/>
                                    <xsl:text disable-output-escaping="yes">&lt;/a&gt;</xsl:text>
                                </p>
                                </font>
                            </td>
                        </tr>
                    </table>
                </xsl:when>
                <xsl:otherwise>
                    <form name="frmSendEmail" id="frmSendEmail" method="post" action="{@accion}" >
                        <xsl:for-each select="ftext">
                            <p>
                                <label for="{@inname}"><xsl:value-of select="@tag" /></label>
                                <input type="text" name="{@inname}" id="{@inname}" size="25" value="{@invalue}" class="rec_input" />
                            </p>
                        </xsl:for-each>
                        <xsl:for-each select="ftextarea">
                            <p>
                                <label for=""><xsl:value-of select="@tag" /></label>
                                <textarea name="{@inname}" rows="7" cols="29" wrap="virtual" class="rec_input"></textarea>
                            </p>
                        </xsl:for-each>
                            <p>
                                <xsl:for-each select="fsubmit">
                                    <xsl:if test="@img ='1'">
                                            <a href="javascript:if(jsValida(document.frmSendEmail)) document.frmSendEmail.submit();" >
                                                <img src="{@src}" alt="{@alt}" />
                                            </a>
                                    </xsl:if>
                                    <xsl:if test="@img ='0'">
                                            <input type="submit" name="btnEnviar" onClick="if(jsValida(this.form)) return true; else return false;" class="rec_button" value="{@tag}" />
                                    </xsl:if>
                                </xsl:for-each>
                                <xsl:for-each select="freset">
                                    <xsl:if test="@img ='1'">
                                        <a href="javascript:document.frmSendEmail.reset();" >
                                            <img src="{@src}" alt="{@alt}" />
                                        </a>
                                    </xsl:if>
                                    <xsl:if test="@img ='0'">
                                        <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                                        <input type="reset" name="btnLimpiar" class="rec_button" value="{@tag}" />
                                    </xsl:if>
                                </xsl:for-each>
                            </p>
                    </form>
                    <script type="text/javascript" >
                        <xsl:text disable-output-escaping="yes">
                            function jsValida(pForm)
                            {
                               if(pForm.txtFromName.value==null || pForm.txtFromName.value=='' || pForm.txtFromName.value==' ')
                               {
                                   alert('Debe especificar el nombre del remitente.');
                                   pForm.txtFromName.focus();
                                   return false;
                               }
                               if(!isEmail(pForm.txtFromEmail)) return false;
                               if(pForm.txtToName.value==null || pForm.txtToName.value=='' || pForm.txtToName.value==' ')
                               {
                                   alert('Debe especificar el nombre del destinatario.');
                                   pForm.txtToName.focus();
                                   return false;
                               }
                               if(!isEmail(pForm.txtToEmail)) return false;
                               return true;
                            }
                            function isEmail(pEmail)
                            {
                               var swOK=2;
                               pCaracter=pEmail.value.replace(" ","\0");
                               for (var i = 0; i &lt; pCaracter.length; i++)
                               {
                                  var sByte = pCaracter.substring(i, i + 1);
                                  if (sByte=="@" || sByte==".") { swOK = swOK - 1; }
                               }
                               if (swOK &gt; 0 || pCaracter.length &lt; 5 || pCaracter.charAt(0) == '@' || pCaracter.charAt(0) == '.' || pCaracter.charAt(pCaracter.length-1)=='@' || pCaracter.charAt(pCaracter.length-1)=='.' || pCaracter.charAt(pCaracter.indexOf("@")+1)=='.')
                               {
                                  pEmail.focus();
                                  alert('Este campo debe ser una dirección de correo electrónico y es obligatorio.');
                                  return false;
                               }
                               return true;
                            }
                        </xsl:text>
                    </script>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>
</xsl:stylesheet>