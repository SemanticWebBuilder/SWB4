<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>

    <xsl:template match="/resource">
        <xsl:if test = "count(father)&gt;0">
            <div class="swb-tematicidx">
                <table>
                    <xsl:for-each select="father">
                        <tr>
                            <td colspan="2" class="idx_title">
                                <xsl:value-of select="fathertitle" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" class="idx_description">
                                <xsl:if test="@hasfatherdescription ='1'">
                                    <xsl:value-of select="fatherdescription" disable-output-escaping="yes"/>
                                </xsl:if>
                                <xsl:if test="@hasfatherdescription ='0'">
                                    Descripción idioma:<xsl:value-of select="fatherlanguage" />
                                    <br/>
                                    <xsl:value-of select="fatherdescription" disable-output-escaping="yes"/>
                                </xsl:if>
                            </td>
                        </tr>
                        <xsl:for-each select="son">
                            <tr>
                                <td colspan="2">
                                    <a href="{@sonref}" class="idx_link1" target="{@target}" title="{@sontitle}">
                                        <xsl:value-of select="sontitle" />
                                    </a>
                                </td>
                            </tr>
                            <tr class="idx_data">
                                <td>
                                    <xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;</xsl:text>
                                </td>
                                <td>
                                    <xsl:if test="@hassondescription ='1'">
                                        <xsl:value-of select="sondescription" disable-output-escaping="yes"/>
                                    </xsl:if>
                                    <xsl:if test="@hassondescription ='0'">
                                        Descripción idioma:<xsl:value-of select="sonlanguage" />
                                        <br/>
                                        <xsl:value-of select="sondescription" disable-output-escaping="yes"/>
                                    </xsl:if>
                                </td>
                            </tr>
                            <xsl:for-each select="grandson">
                                <tr>
                                    <td colspan="2">
                                        <a href="{@grandsonref}" class="idx_link2" target="{@target}" title="{@grandsontitle}">
                                            <font style="font-size: 10px;">&gt; </font><xsl:value-of select="grandsontitle" />
                                        </a>
                                    </td>
                                </tr>
                                <tr class="idx_data">
                                    <td>
                                        <xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;</xsl:text>
                                    </td>
                                    <td>
                                        <xsl:if test="@hasgrandsondescription ='1'">
                                            <xsl:value-of select="grandsondescription" disable-output-escaping="yes"/>
                                        </xsl:if>
                                        <xsl:if test="@hasgrandsondescription ='0'">
                                            Descripción el idioma:<xsl:value-of select="grandsonlanguage" />
                                            <br/>
                                            <xsl:value-of select="grandsondescription" disable-output-escaping="yes"/>
                                        </xsl:if>
                                    </td>
                                </tr>
                            </xsl:for-each>
                        </xsl:for-each>
                    </xsl:for-each>
                </table>
            </div>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>