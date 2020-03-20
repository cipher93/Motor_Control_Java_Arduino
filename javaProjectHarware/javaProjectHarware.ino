#define rightDirection  7
#define leftDirection   8
#define motorSpeed      9
#define directionLed   13
#define baudRate       9600
#define msgLength      12

void setup()
{
  // Open serial communications and wait for port to open:
  pinMode(rightDirection  , OUTPUT);
  pinMode(leftDirection   , OUTPUT);
  pinMode(directionLed    , OUTPUT);
  pinMode(motorSpeed      , OUTPUT);
  Serial.begin(baudRate);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for Native USB only
  }
}

void loop() // run over and over
{


  /*Variables
     tempSliderValue: Holds motor speed value
     input2: holds incoming input coming from Java as chars
  */
  int tempSliderValue;
  char input2[msgLength];

  delay(200);
  /*If there is serial data coming from source
     Add incoming data to input2, where we will read each char byte by byte
     and store it in corressponding location
  */
  if (Serial.available() > 0) {
    for (int i = 0; i < msgLength; i++) {
      input2[i] = Serial.read();
    }

    /*
       This line adds terminator character to the end of
       the array so that we can know we have reached the end
       of incoming data
    */
    input2[msgLength - 1] = '\0';
    for (int i = 0; i < msgLength; i++) {
      Serial.print(input2[i]);
    }

    /*****************Motor ON OFF with Direction**************************/
    //Case 1 motor ON and ClockWise
    if (input2[2] == '1' && input2[4] == '1') {
      digitalWrite(leftDirection   , LOW);
      digitalWrite(rightDirection  , HIGH);
    }
    //Case 2 moto ON and AntiClockWise
    if (input2[2] == '1' && input2[4] == '0') {
      digitalWrite(leftDirection   , HIGH);
      digitalWrite(rightDirection  , LOW);
    }
    //Case 3 moton off
    if (input2[2] == '0') {
      digitalWrite(leftDirection   , LOW);
      digitalWrite(rightDirection  , LOW);
    }
    //case 4 changing motor rotation to clockWise
    if (input2[4] == '1') {
      digitalWrite(leftDirection   , LOW);
      digitalWrite(rightDirection  , HIGH);
      digitalWrite(directionLed   , HIGH);
    }
    //case 5 changing motor rotation to AnticlockWise
    if (input2[4] == '0') {
      digitalWrite(leftDirection   , HIGH);
      digitalWrite(rightDirection  , LOW);
      digitalWrite(directionLed   , LOW);
    }

    /**********************changing motor speed *******************************/
    /*
       This equation is because the input data comes as ascii code
       so we subtract it from 48 so that we can start at zero
       Then we map this data from 0 to 255 to match speed that the motor will use
    */
    tempSliderValue = (input2[6] - 48) * 100 + (input2[7] - 48) * 10 + (input2[8] - 48);
    tempSliderValue = map(tempSliderValue, 0, 100, 0, 255);

    /*
      This condition is made so that the motor is triggered by on/off button
      without it, if the motor is given speed, it will run even if it is on the
      off state
    */
    if (input2[2] == '0')
      analogWrite(motorSpeed, 0);
    else
      analogWrite(motorSpeed, tempSliderValue );


  }
}
