package com.stuypulse.robot.subsystems.leds;

import com.stuypulse.robot.constants.Ports;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LEDController extends SubsystemBase {
    private static final LEDController instance;
    private final LEDPattern defaultPattern = LEDPattern.kOff;
    
    static {
        instance = new LEDController(Ports.LED.LED_PORT, Ports.LED.LED_LENGTH);
    }
    
    public static LEDController getInstance(){
        return instance;
    }

    private AddressableLED leds;
    private AddressableLEDBuffer ledsBuffer;

    protected LEDController(int port, int length) {
        leds = new AddressableLED(port);
        ledsBuffer = new AddressableLEDBuffer(length);

        leds.setLength(length);
        leds.setData(ledsBuffer);
        leds.start();

        applyPattern(defaultPattern);
        SmartDashboard.putData(instance);
    }
    
    public void applyPattern(LEDPattern pattern) {
        SmartDashboard.putString("LEDs/LED Pattern",pattern.toString());
        pattern.applyTo(ledsBuffer);
        leds.setData(ledsBuffer);
    }

    @Override
    public void periodic() {}
}
