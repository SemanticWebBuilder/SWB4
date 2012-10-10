int CERO=8;
int READ=9;
int WRITE=7;
int LED=13;

int _cero=0;

void setup() {
  // initialize the serial communication:
  pinMode(READ, INPUT);  
  pinMode(LED, OUTPUT);
  digitalWrite(LED, LOW);   // turn the LED on (HIGH is the voltage level)
  delay(500);               // wait for a second
  digitalWrite(LED, HIGH);    // turn the LED off by making the voltage LOW
  delay(500);    
  digitalWrite(LED, LOW);   // turn the LED on (HIGH is the voltage level)
  Serial.begin(9600);
}

void waitUntil(int c)
{
  while(c!=digitalRead(CERO));
}

boolean readBitPart(unsigned long time)
{
  int r=0;
  while(micros()<time)
  {
     if(digitalRead(READ)>0)r++;
     else r--;     
  }
  return r>0;
}

int readBit()
{ 
  unsigned long time=micros();    
  int x=0;
  int r=0;  
  while(x<8)
  {
    time+=500;
    if(readBitPart(time))
    {
      r++;
    }else
    {
      r--;
    }
    time+=500;
    if(readBitPart(time))
    {
      r--;
    }else
    {
      r++;
    }
    x++;
  }
  if(r>2 || r<-2)digitalWrite(LED, HIGH);
  else digitalWrite(LED, LOW);
  return r;
}

void loop() {
  if(_cero==0)_cero=1;else _cero=0;
  waitUntil(_cero);
  int data=_cero+(readBit()<<2);
  Serial.println(data);
}
