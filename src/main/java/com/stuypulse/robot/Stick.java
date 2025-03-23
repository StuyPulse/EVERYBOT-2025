package com.stuypulse.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Stick {
    private final Joystick stick;
    private static final double ANALOG_THRESHOLD = .5;

    public Stick(int stick_id) {
        this.stick = new Joystick(stick_id);
    }

    public Joystick getJoystick() {
        return stick;
    }

    public double getX() {
        return -stick.getRawAxis(1);
    }

    public double getY() {
        return -stick.getRawAxis(2);
    }

    public double getTwist() {
        return -stick.getRawAxis(3);
    }

    public double getThrottle() {
        return -stick.getRawAxis(4);
    }

    public boolean getRawTriggerTriggered() {
        return stick.getRawButton(1);
    }

    public boolean getRawSideButton() {
        return stick.getRawButton(2);
    }

    public boolean getRawTop_BottomLeftButton() {
        return stick.getRawButton(3);
    }

    public boolean getRawTop_BottomRightButton() {
        return stick.getRawButton(4);
    }

    public boolean getRawTop_TopLeftButton() {
        return stick.getRawButton(5);
    }

    public boolean getRawTop_TopRightButton() {
        return stick.getRawButton(6);
    }

    public boolean getRawHatUp() {
        if (stick.getRawAxis(5) == -1){
            return true;
        } else {
            return false;
        }
    }

    public boolean getRawHatDown() {
        if (stick.getRawAxis(5) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getRawHatLeft() {
        if (stick.getRawAxis(6) == -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getRawHatRight() {
        if (stick.getRawAxis(6) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getRawBottom_TopLeftButton() {
        return stick.getRawButton(8);
    }

    public boolean getRawBottom_BottomLeftButton() {
        return stick.getRawButton(7);
    }

    public boolean getRawBottom_TopMiddleButton() {
        return stick.getRawButton(10);
    }

    public boolean getRawBottom_BottomMiddleButton() {
        return stick.getRawButton(9);
    }

    public boolean getRawBottom_TopRightButton() {
        return stick.getRawButton(12);
    }

    public boolean getRawBottom_BottomRightButton() {
        return stick.getRawButton(11);
    }

    // Triggers
    public final Trigger getTriggerTriggered() {
        return new Trigger(this::getRawTriggerTriggered);
    }

    public final Trigger getSideButton() {
        return new Trigger(this::getRawSideButton);
    }

    public final Trigger getTop_BottomLeftButton() {
        return new Trigger(this::getRawTop_BottomLeftButton);
    }

    public final Trigger getTop_BottomRightButton() {
        return new Trigger(this::getRawTop_BottomRightButton);
    }

    public final Trigger getTop_TopLeftButton() {
        return new Trigger(this::getRawTop_TopLeftButton);
    }

    public final Trigger getTop_TopRightButton() {
        return new Trigger(this::getRawTop_TopRightButton);
    }

    public final Trigger getBottom_TopLeftButton() {
        return new Trigger(this::getRawBottom_TopLeftButton);
    }

    public final Trigger getBottom_TopMiddleButton() {
        return new Trigger(this::getRawBottom_TopMiddleButton);
    }

    public final Trigger getBottom_TopRightButton() {
        return new Trigger(this::getRawBottom_TopRightButton);
    }

    public final Trigger getBottom_BottomLeftButton() {
        return new Trigger(this::getRawBottom_TopLeftButton);
    }

    public final Trigger getBottom_BottomMiddleButton() {
        return new Trigger(this::getRawBottom_TopMiddleButton);
    }

    public final Trigger getBottom_BottomRightButton() {
        return new Trigger(this::getRawBottom_TopRightButton);
    }

    public final Trigger getHatUp() {
        return new Trigger(this::getRawHatUp);
    }

    public final Trigger getHatDown() {
        return new Trigger(this::getRawHatDown);
    }

    public final Trigger getHatLeft() {
        return new Trigger(this::getRawHatLeft);
    }

    public final Trigger getHatRight() {
        return new Trigger(this::getRawHatRight);
    }

    public final Trigger getThrottleUp() {
        return new Trigger(() -> getThrottle() > +ANALOG_THRESHOLD);
    }

    public final Trigger getThrottleDown() {
        return new Trigger(() -> getThrottle() < -ANALOG_THRESHOLD);
    }
}
