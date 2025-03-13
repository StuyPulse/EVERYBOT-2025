package com.stuypulse.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * TODO:
 * Write LED Subsystem
 * Write some commands for LEDs
 * Figure out how to bind LED patterns to robot actions or controller buttons
 * IDK. Brainstorm some LED pattern ideas
 */

public class LEDController extends SubsystemBase {
    private static final LEDController instance;
    private final LEDPattern defaultPattern = LEDPattern.kOff;
    
    static {
        instance = new LEDController(Ports.LED.LED_PORT, Settings.LED.LED_LENGTH);
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
        pattern.applyTo(ledsBuffer);
    }

    @Override
    public void periodic() {
        
    }
}
