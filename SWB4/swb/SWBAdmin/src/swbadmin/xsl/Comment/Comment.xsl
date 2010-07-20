<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>

    <xsl:template match="/form">
        <xsl:choose>
            <xsl:when test="@email ='1'">
                <h2><xsl:value-of select="site" /></h2>
                <h3>
                    <xsl:value-of select="msgTo" disable-output-escaping="yes" /> <xsl:value-of select="responsable" />
                    <br/>
                    <xsl:value-of select="area" />
                </h3>
                <xsl:if test="count(headermsg) &gt; 0">
                    <p><xsl:value-of select="headermsg" disable-output-escaping="yes" /></p>
                </xsl:if>
                <table>
                    <tr>
                        <td><xsl:value-of select="msgName" disable-output-escaping="yes" /></td>
                        <td><xsl:value-of select="fromname" /></td>
                    </tr>
                    <tr>
                        <td><xsl:value-of select="msgViewEmail" disable-output-escaping="yes" /></td>
                        <td><xsl:value-of select="fromemail" /></td>
                    </tr>
                    <xsl:if test="count(phone) &gt; 0">
                        <tr>
                            <td><xsl:value-of select="msgPhone" disable-output-escaping="yes" /></td>
                            <td><xsl:value-of select="phone" /></td>
                        </tr>
                    </xsl:if>
                    <xsl:if test="count(fax) &gt; 0">
                        <tr>
                            <td><xsl:value-of select="msgFax" disable-output-escaping="yes" /></td>
                            <td><xsl:value-of select="fax" /></td>
                        </tr>
                    </xsl:if>
                </table>
                <fieldset>
                    <legend>
                        <xsl:value-of select="msgMessage" disable-output-escaping="yes" />
                    </legend>
                    <p><xsl:value-of select="message" /></p>
                </fieldset>
                <xsl:if test="count(footermsg) &gt; 0">
                    <p>
                        <xsl:value-of select="footermsg" disable-output-escaping="yes" />
                    </p>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                <div class="swb-comment">
                    <h2>
                        <xsl:value-of select="msgComments" disable-output-escaping="yes" />
                    </h2>
                    <form name="frmSendEmail" method="post" action="{@accion}">
                        <table>
                            <xsl:for-each select="fselect">
                                <tr>
                                    <td><label for="{@inname}"><xsl:value-of select="@tag" /></label></td>
                                    <td>
                                        <select name="{@inname}" id="{@inname}">
                                            <xsl:for-each select="foption">
                                                <option value="{@invalue}"><xsl:value-of select="@invalue" /></option>
                                            </xsl:for-each>
                                        </select>
                                    </td>
                                </tr>
                            </xsl:for-each>
                            <xsl:for-each select="ftext">
                                <tr>
                                    <td><label for="{@inname}"><xsl:value-of select="@tag" /></label></td>
                                    <td><input type="text" name="{@inname}" id="{@inname}" value="{@invalue}" /></td>
                                </tr>
                            </xsl:for-each>
                            <xsl:for-each select="ftextarea">
                                <tr>
                                    <td><label><xsl:value-of select="@tag" /></label></td>
                                    <td>
                                        <textarea name="{@inname}" wrap="virtual">
                                            <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                                        </textarea>
                                    </td>
                                </tr>
                            </xsl:for-each>
                        </table>
                        <p>
                            <xsl:for-each select="fsubmit">
                                <xsl:if test="@img ='1'">
                                    <a href="javascript:if(jsValida(document.frmSendEmail)) document.frmSendEmail.submit();" >
                                        <img src="{@src}" alt="{@alt}" title="{@alt}"/>
                                    </a>
                                </xsl:if>
                                <xsl:if test="@img ='0'">
                                    <input type="submit" name="btnEnviar" onclick="return jsValida(this.form)" value="{@tag}"/>
                                </xsl:if>
                            </xsl:for-each>
                            <xsl:for-each select="freset">
                                <xsl:if test="@img='1'">
                                    <a href="javascript:document.frmSendEmail.reset();" >
                                        <img src="{@src}" alt="{@alt}" title="{@alt}"/>
                                    </a>
                                </xsl:if>
                                <xsl:if test="@img ='0'">
                                    <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
                                    <input type="reset" name="btnLimpiar" value="{@tag}"/>
                                </xsl:if>
                            </xsl:for-each>
                        </p>
                    </form>
                </div>
                <script type="text/javascript">
                    <xsl:text disable-output-escaping="yes">
                        function jsValida(pForm) {
                            if(pForm.txtFromName.value==null || pForm.txtFromName.value=='' || pForm.txtFromName.value==' ') {
                                alert('Debe especificar el nombre del remitente.');
                                pForm.txtFromName.focus();
                                return false;
                            }
                            if(!isEmail(pForm.txtFromEmail))
                                return false;
                            if(pForm.tarMsg.value==null || pForm.tarMsg.value=='' || pForm.tarMsg.value==' ') {
                                alert('Debe capturar su mensaje.');
                                pForm.tarMsg.focus();
                                return false;
                            }
                            return true;
                        }

                        function isEmail(pEmail) {
                            var swOK=2;
                            pCaracter=pEmail.value.replace(" ","\0");
                            for(var i = 0; i &lt; pCaracter.length; i++) {
                                var sByte = pCaracter.substring(i, i + 1);
                                if(sByte=="@" || sByte==".") {
                                    swOK = swOK - 1;
                                }
                            }
                            if(swOK &gt; 0 || pCaracter.length &lt; 5 || pCaracter.charAt(0) == '@' || pCaracter.charAt(0) == '.' || pCaracter.charAt(pCaracter.length-1)=='@' || pCaracter.charAt(pCaracter.length-1)=='.' || pCaracter.charAt(pCaracter.indexOf("@")+1)=='.') {
                                pEmail.focus();
                                alert('La dirección de correo electrónico es obligatoria.');
                                return false;
                            }
                            return true;
                        }
                    </xsl:text>
                </script>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>