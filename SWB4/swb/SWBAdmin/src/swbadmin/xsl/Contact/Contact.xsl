<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="ISO-8859-1"/>
    <xsl:template match="apologies">
        <p><xsl:apply-templates/></p>
    </xsl:template>
    <xsl:template match="contact">
        <h3><xsl:value-of select="@title"/></h3>
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="instructions">
        <p><xsl:apply-templates/></p><hr />
    </xsl:template>
    <xsl:template match="name">
        <p>
            <label for="{@id}"><xsl:value-of select="name"/></label>
            <input name="{@id}" id="{@id}" value="{@fullname}" class="swb-contact-field" />
        </p>
    </xsl:template>
    <xsl:template match="email">
        <p>
            <label for="{@id}"><xsl:value-of select="email"/></label>
            <input name="{@id}" id="{@id}" value="{@email}" class="swb-contact-field" />
        </p>
    </xsl:template>
    <xsl:template match="subject">
        <p>
            <label for="{@id}"><xsl:value-of select="subject"/></label>
            <input name="{@id}" id="{@id}" class="swb-contact-field" />
        </p>
    </xsl:template>
    <xsl:template match="message">
        <p>
            <label for="{@id}"><xsl:value-of select="message"/></label>
            <textarea name="{@id}" id="{@id}" cols="40" rows="5"></textarea>
        </p>
    </xsl:template>
    <xsl:template match="command">
        <p class="swb-contact-cmd">commands</p>
    </xsl:template>
    
</xsl:stylesheet>
