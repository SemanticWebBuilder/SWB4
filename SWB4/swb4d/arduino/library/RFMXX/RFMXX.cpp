/**
* Very small library for family RFM12 devices...
* You can use this library with the Attiny chips 
* Libreria muy simple de comunicación inalámbrica utilizando la familia de dispositivos RFM12
* El tamaño es muy pequeño para poder ser utilizo con dispositivos de poca memoria como los Attiny
* Javier Solís González
* softjei@gmail.com.mx
* Paquete (grpdest, iddest, len, grpsrc, idsrc, data)
* 01/01/2013
* version 0.1
*/
#include "RFMXX.h" 
#if ARDUINO >= 100
#include <Arduino.h> // Arduino 1.0
#else
#include <WProgram.h> // Arduino 0022
#endif

unsigned char Rfm::RF_GRP;
unsigned char Rfm::RF_ID;
unsigned char Rfm::RF_RXBUF[_RXBUF+6];
unsigned char Rfm::RF_RXI;
bool Rfm::RF_RXDATA;

//Rfm::Rfm()
//{
//}

void Rfm::initRFM(unsigned int band, unsigned char grp, unsigned char id)
{
	RF_GRP=grp;
	RF_ID=id;
    //Inicializacion de pines 
    pinMode(_SCK, OUTPUT);
    pinMode(_SDO, INPUT);
    pinMode(_SDI, OUTPUT);
    pinMode(_nSEL, OUTPUT);
    pinMode(_nIRQ, INPUT);
  
    digitalWrite(_nSEL, HIGH);
    digitalWrite(_SDI, HIGH);
    digitalWrite(_SCK, LOW);
        
    writeCMD(0x0000);
    writeCMD(0x8205);  // DC (disable clk pin), enable lbd
    writeCMD(0xB800);  // in case we're still in OOK mode
    
    while (digitalRead(_nIRQ) == 0)writeCMD(0x0000);    
    
    writeCMD(band);
    //writeCMD(0x80D7);//EL,EF,433band,12.0pF
    //writeCMD(0x80F7);//EL,EF,915band,12.0pF    
    writeCMD(0x82D9);//!er,!ebb,ET,ES,EX,!eb,!ew,DC
    writeCMD(0xA640);//A140=430.8MHz
    //writeCMD(0xA7D0);//2000=915.0MHz
    //writeCMD(0xC647);//4.8kbps
    writeCMD(0xC623);//9.579kbps
    //writeCMD(0x94A0);//VDI,FAST,134kHz,0dBm,-103dBm 
    writeCMD(0x94A2);//VDI,FAST,134kHz,0dBm,-91dBm 
    writeCMD(0xC2AC);//AL,!ml,DIG,DQD4
    writeCMD(0xCA80);//FIFO8,SYNC,
    writeCMD(0xCA83);//FIFO8,SYNC,
    writeCMD(0xC483); //@PWR,NO RSTRIC,!st,!fi,OE,EN
    writeCMD(0xCED4);//SYNC=2DD4;
    writeCMD(0x9850);//!mp,9810=30kHz,MAX OUT 
    writeCMD(0xCC77);//OB1,OB0,!lpx,!ddy,DDIT,BW0
    writeCMD(0xE000);//NOT USE 
    writeCMD(0xC800);//NOT USE 
    writeCMD(0xC040);//1.66MHz,2.2V    
    
    attachInterrupt(_iIRQ, getByte, LOW);
}


void Rfm::writeCMD(unsigned int cmd)
{
    unsigned char x=0;
    digitalWrite(_SCK, LOW);
    digitalWrite(_nSEL, LOW);
    for(x=0;x<16;x++)
    {
      if(bitRead(cmd, 15))digitalWrite(_SDI, HIGH);
      else digitalWrite(_SDI, LOW);
      digitalWrite(_SCK, HIGH);
      //delayMicroseconds(1);
      cmd=cmd<<1;
      digitalWrite(_SCK, LOW);
    }
    digitalWrite(_nSEL, HIGH);
}
  
void Rfm::writeByte( unsigned char data ) 
{
    unsigned char rgit=0;
    unsigned int temp=0xB800;
    temp|=data;
    Loop: 
    digitalWrite(_SCK, LOW);
    digitalWrite(_nSEL, LOW);
    digitalWrite(_SDI, LOW);
    digitalWrite(_SCK, HIGH);
    if(digitalRead(_SDO)>0)//Polling SDO
    {
      rgit=1;
    } else
    {
      rgit=0;
    }
    digitalWrite(_SCK, LOW);
    digitalWrite(_SDI, HIGH);
    digitalWrite(_nSEL, HIGH);
    if(rgit==0)
    {
      goto Loop;
    } else
    {
       rgit=0;
       writeCMD(temp);
    }
}
  
unsigned char Rfm::readFIFO()
{
    unsigned char i, ret;
    digitalWrite(_SCK, LOW);
    digitalWrite(_SDI, LOW);
    digitalWrite(_nSEL, LOW);
    for(i=0;i<16;i++)
    {                     
      //skip status bits
      digitalWrite(_SCK, HIGH);
      //delayMicroseconds(1);
      digitalWrite(_SCK, LOW);
    }
    ret=0;
    for(i=0;i<8;i++)
    {
      //read fifo data unsigned char
      ret=ret<<1; 
      if(digitalRead(_SDO))
      {
        ret|=1; 
      }
      digitalWrite(_SCK, HIGH);
      //delayMicroseconds(1);
      digitalWrite(_SCK, LOW);
    }
    digitalWrite(_nSEL, HIGH);
    return(ret);
}
  
void  Rfm::resetRXBuffer()
{
        writeCMD(0xCA80);
        //delayMicroseconds(1);
        writeCMD(0xCA83);
        //delayMicroseconds(1);
        RF_RXI=0;
        //Serial.println();
}  
  
void Rfm::getByte()
{
      //digitalWrite(LED, HIGH);    
      //Serial.println("getByte");
      unsigned char c=readFIFO();
      //Serial.print(c);
      //Serial.print(" ");
      
      if(RF_RXI==0 && c>_RXBUF)
      {
      	resetRXBuffer();  //Verifica el tamaño del mensaje que no sea mayor a 60
      	return;
      }else if(RF_RXI==1 && !(c==0 || c==RF_GRP || RF_GRP==0))
      {
      	resetRXBuffer();
        return;
      }else if(RF_RXI==2 && !(c==0 || c==RF_ID  || RF_ID==0))
      {
    	resetRXBuffer();
        return;
      }
      
      RF_RXBUF[RF_RXI++]=c;
      if(RF_RXI>(RF_RXBUF[0]+5))
      {
        resetRXBuffer();
        unsigned int chksum=0;
        unsigned char j=0;
        for(;j<RF_RXBUF[0]+5;j++)
        {
          chksum+=RF_RXBUF[j]; //add 0x30-----0x3F
        }
        chksum&=0x0FF;
        if(chksum==RF_RXBUF[j])
        { 
          RF_RXDATA=true;
        }
        /*
        Serial.print(" recv NO: ");
        for(j=0;j<RF_RXBUF[0]+6;j++)
        {
          Serial.print(RF_RXBUF[j]);
          Serial.print(" ");
        }
        Serial.println();
        */
      }    
      //digitalWrite(LED, LOW);              
}  

unsigned char Rfm::sendData(unsigned char grp, unsigned char id, unsigned char str[], unsigned char len)
{
    sendData(grp,id,RF_GRP,RF_ID,str,len);
}
  
unsigned char Rfm::sendData(unsigned char grp, unsigned char id, unsigned char grp_src, unsigned char id_src, unsigned char str[], unsigned char len)
{  
    //digitalWrite(LED, HIGH);        
    while(RF_RXI>0)delay(1);
    noInterrupts();
    //Serial.println("sendData ");    
    unsigned int chksum=0;
    unsigned char ret=0;
    
    //writeCMD(0x82F9);
    //writeCMD(0x8229);
    //delayMicroseconds(4);
    writeCMD(0x8239);
    
    writeByte(0xAA);
    writeByte(0xAA);
    writeByte(0xAA);
    writeByte(0x2d);
    writeByte(0xd4);
    writeByte(len);
    chksum+=len;
    writeByte(grp);
    chksum+=grp;
    writeByte(id);
    chksum+=id;
    writeByte(grp_src);
    chksum+=RF_GRP;
    writeByte(id_src);
    chksum+=RF_ID;
    for(unsigned char x=0;x<len;x++)
    {
      writeByte(str[x]);
      chksum+=str[x];
    }
    chksum&=0xff;
    writeByte(chksum);
    writeByte(0xAA);
    writeByte(0xAA);
    writeCMD(0x82D9);
    //Serial.println("end send "); 
    interrupts();
    //digitalWrite(LED, LOW);            
}


char * Rfm::getMsg()
{
	RF_RXBUF[RF_RXBUF[0]+5]=0;
	return (char *)RF_RXBUF+5;
}
  
bool Rfm::hasData()
{
    if(RF_RXDATA)
    {
      RF_RXDATA=false;
      return true;
    }
    return false;
}
  
