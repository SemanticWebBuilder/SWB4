/**
* Very small library for family RFM12 devices...
* You can use this library with the Attiny chips 
* Libreria muy simple de comunicación inalámbrica utilizando la familia de dispositivos RFM12
* El tamaño es muy pequeño para poder ser utilizo con dispositivos de poca memoria como los Attiny
* Javier Solís González
* softjei@gmail.com.mx
* 01/01/2013
* version 0.1
*/
/*
  #define _SCK 13		//Digital pin of SCK
  #define _SDO 12		//Digital pin of SDO
  #define _SDI 11		//Digital pin of SDI
  #define _nSEL 10		//Digital pin of SEL
  #define _nIRQ 2       //Digital pin of IRQ
  #define _iIRQ 0  		//Internal Interruption 0 of pin digital pin 2
*/
#include <RFMXX.h>

  Rfm rfm;
  
  void setup()
  {    
    Serial.begin(9600);
    while (!Serial) {
      ; // wait for serial port to connect. Needed for Leonardo only
    }
    
    rfm.initRFM(RFM_433,1,1);   
  }  
  
  
  byte data[]={10,11,12,13};
  void loop()
  { 
     Serial.println("SendData:");
     rfm.sendData(0,0, data, sizeof(data));
     delay(500);
     if(rfm.hasData())
     {  
        Serial.print("recv: ");
        for(byte j=0;j<rfm.RF_RXBUF[0];j++)
        {
          Serial.print(rfm.RF_RXBUF[j+1]);
          Serial.print(" ");
        }
        Serial.println();
     }
  }