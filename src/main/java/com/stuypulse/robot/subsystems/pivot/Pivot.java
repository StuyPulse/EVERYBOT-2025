package com.stuypulse.robot.subsystems.pivot;

import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.math.geometry.Rotation2d;

public abstract class Pivot {
    
    private static final Pivot instance;

    static { // no sim
        instance = new PivotImpl();
    }

    public static Pivot getInstance() {
        return instance;
    }

    private PivotState state;

    public enum PivotState {
        STOW(Settings.Pivot.STOW_ANGLE);
        private Rotation2d targetAngle;

        private PivotState(Rotation2d targetAngle) {
            this.targetAngle = targetAngle;
        }
    }
    
    
    
    
    public enum RollerState {
        INTAKE_ALGAE(Settings.Pivot.GROUND_PICKUP),
        SHOOT_ALGAE(Settings.Pivot.PROCESSOR_SCORE_ANGLE);

    }

    public PivotState getState() {
        return this.state;
    }
    
    public void setState(PivotState state) {
        this.state = state;
    }

    public abstract void rollersAcquire();

    public abstract void rollersDeacquire();

    public abstract boolean atTargetAngle();

    public abstract double getCurrentAngle();

    public abstract void setTargetAngle();
    
}