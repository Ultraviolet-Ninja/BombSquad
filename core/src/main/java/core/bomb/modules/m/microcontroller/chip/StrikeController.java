package core.bomb.modules.m.microcontroller.chip;


import static core.bomb.modules.m.microcontroller.Pin.AIN;
import static core.bomb.modules.m.microcontroller.Pin.DIN;
import static core.bomb.modules.m.microcontroller.Pin.GND;
import static core.bomb.modules.m.microcontroller.Pin.PWM;
import static core.bomb.modules.m.microcontroller.Pin.RST;
import static core.bomb.modules.m.microcontroller.Pin.VCC;

public class StrikeController extends AbstractController {
    public static final String ACRONYM = "STRK";

    public StrikeController(int pinCount) {
        super(pinCount, ACRONYM);
    }

    @Override
    protected void setSixPin() {
        pinOrder.add(AIN);
        pinOrder.add(VCC);
        pinOrder.add(RST);
        pinOrder.add(DIN);
        pinOrder.add(PWM);
        pinOrder.add(GND);
    }

    @Override
    protected void setEightPin() {
        pinOrder.add(AIN);
        pinOrder.add(PWM);
        pinOrder.add(GND);
        pinOrder.add(DIN);
        pinOrder.add(VCC);
        pinOrder.add(GND);
        pinOrder.add(RST);
        pinOrder.add(GND);
    }

    @Override
    protected void setTenPin() {
        pinOrder.add(GND);
        pinOrder.add(GND);
        pinOrder.add(GND);
        pinOrder.add(GND);
        pinOrder.add(AIN);
        pinOrder.add(DIN);
        pinOrder.add(GND);
        pinOrder.add(VCC);
        pinOrder.add(RST);
        pinOrder.add(PWM);
    }
}
