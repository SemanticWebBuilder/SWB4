<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>

    <xsl:template match="/resource">
        <xsl:if test = "count(father)&gt;0">
            <div id="indiceTematico">
                <xsl:apply-templates select="father"/>
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template match="father">

        <xsl:for-each select="son">
            <div class="entrada_indiceTematico">
                <h3>
                    <a href="{@sonref}" target="{@target}" title="{@sontitle}">
                        <xsl:value-of select="sontitle" />
                    </a>
                </h3>
                <p class="descripcion_indiceTematico">
                    <xsl:if test="@hassondescription ='1'">
                        <xsl:value-of select="sondescription" disable-output-escaping="yes"/>
                    </xsl:if>
                    <xsl:if test="@hassondescription ='0'">
                        Descripción idioma:<xsl:value-of select="sonlanguage" />
                        <br/>
                        <xsl:value-of select="sondescription" disable-output-escaping="yes"/>
                    </xsl:if>
                </p>
                <xsl:for-each select="grandson">
                    <xsl:if test="position()=1">
                        <xsl:text  disable-output-escaping="yes">
                            &lt;ul>
                        </xsl:text>
                    </xsl:if>
                        <li>
                            <a href="{@grandsonref}" target="{@target}" title="{@grandsontitle}">
                                <xsl:value-of select="grandsontitle" />
                            </a>
                        </li>
                    <xsl:if test="position()=last()">
                        <xsl:text  disable-output-escaping="yes">
                            &lt;/ul>
                        </xsl:text>
                    </xsl:if>
                </xsl:for-each>
                <div class="clearindiceTematico">
                    <xsl:text  disable-output-escaping="yes">
                        &amp;nbsp;
                    </xsl:text>
                </div>
            </div>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>