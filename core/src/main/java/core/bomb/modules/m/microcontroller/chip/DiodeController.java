package core.bomb.modules.m.microcontroller.chip;

import static core.bomb.modules.m.microcontroller.Pin.AIN;
import static core.bomb.modules.m.microcontroller.Pin.DIN;
import static core.bomb.modules.m.microcontroller.Pin.GND;
import static core.bomb.modules.m.microcontroller.Pin.PWM;
import static core.bomb.modules.m.microcontroller.Pin.RST;
import static core.bomb.modules.m.microcontroller.Pin.VCC;

public class DiodeController extends AbstractController {
    public static final String ACRONYM = "LEDS";

    public DiodeController(int pinCount) {
        super(pinCount, ACRONYM);
    }

    @Override
    protected void setSixPin() {
        pinOrder.add(PWM);
        pinOrder.add(RST);
        pinOrder.add(VCC);
        pinOrder.add(DIN);
        pinOrder.add(AIN);
        pinOrder.add(GND);
    }

    @Override
    protected void setEightPin() {
        pinOrder.add(PWM);
        pinOrder.add(DIN);
        pinOrder.add(VCC);
        pinOrder.add(GND);
        pinOrder.add(AIN);
        pinOrder.add(GND);
        pinOrder.add(RST);
        pinOrder.add(GND);
    }

    @Override
    protected void setTenPin() {
        pinOrder.add(PWM);
        pinOrder.add(AIN);
        pinOrder.add(DIN);
        pinOrder.add(GND);
        pinOrder.add(GND);
        pinOrder.add(GND);
        pinOrder.add(GND);
        pinOrder.add(RST);
        pinOrder.add(VCC);
        pinOrder.add(GND);
    }
}
