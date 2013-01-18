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

#ifndef RFM_h
  #define RFM_h
  
  #define RFM_433 0x80D7
  #define RFM_868 0x80E7
  #define RFM_915 0x80F7

#if defined(__AVR_ATtiny4313__) || defined(__AVR_ATtiny2313__) 
  #define _RXBUF 25
  #define _SCK 16
  #define _SDO 15
  #define _SDI 14
  #define _nSEL 13
  #define _nIRQ 4
  #define _iIRQ 0  		//Internal Interruption 0 of pin digital pin 4
#elif defined(__AVR_ATmega2560__)
  #define _RXBUF 55
  #define _SCK 7
  #define _SDO 6
  #define _SDI 5
  #define _nSEL 4
  #define _nIRQ 3   
  #define _iIRQ 1  		//Internal Interruption 1 of pin digital pin 3
#else 
  //UNO
  #define _RXBUF 55  
  #define _SCK 13		//Digital pin of SCK
  #define _SDO 12		//Digital pin of SDO
  #define _SDI 11		//Digital pin of SDI
  #define _nSEL 10		//Digital pin of SEL
  #define _nIRQ 2       //Digital pin of IRQ
  #define _iIRQ 0  		//Internal Interruption 0 of pin digital pin 2
#endif  
  
  class Rfm {
    public:
  		static unsigned char RF_RXBUF[_RXBUF+6];
      	//Rfm();
		void initRFM(unsigned int band, unsigned char grp, unsigned char id);
		static void writeCMD(unsigned int cmd);
		void writeByte( unsigned char data );
		unsigned char sendData(unsigned char grp, unsigned char id, unsigned char str[], unsigned char len);
		unsigned char sendData(unsigned char grp, unsigned char id, unsigned char grp_src, unsigned char id_src, unsigned char str[], unsigned char len);
		bool hasData();
		char * getMsg();
    private:
    	static unsigned char RF_GRP;	//Grupo del dispositivo
    	static unsigned char RF_ID;		//Identificador del dispositivo
  		static unsigned char RF_RXI;	//Contador de bytes recibidos
  		static bool RF_RXDATA;			//Mensaje recibido
		static void getByte();
		static unsigned char readFIFO();
		static void  resetRXBuffer();
  };

#endif