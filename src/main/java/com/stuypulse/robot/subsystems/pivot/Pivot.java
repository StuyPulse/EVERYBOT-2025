package com.stuypulse.robot.subsystems.pivot;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Pivot extends SubsystemBase {
    
    private static final Pivot instance;

    static { // no sim
        instance = new PivotImpl();
    }

    public static Pivot getInstance() {
        return instance;
    }

    private PivotState pivotState;
    private RollerState rollerState;

    public enum PivotState {
        STOW(Settings.Pivot.STOW_ANGLE),
        SCORE_ANGLE(Settings.Pivot.SCORE_ANGLE);

        private Rotation2d targetAngle;

        private PivotState(Rotation2d targetAngle) {
            this.targetAngle = targetAngle;
        }

        public Rotation2d getTargetAngle() {
            return this.targetAngle;
        }
    }
    
    
    
    
    public enum RollerState {
        INTAKE_ALGAE(Settings.Rollers.ALGAE_INTAKE_SPEED),
        ALGAE_STILL(Settings.Rollers.ALGAE_STAYING_STILL_SPEED),
        SHOOT_ALGAE(Settings.Rollers.ALGAE_SHOOT_SPEED);

        double targetSpeed;

        private RollerState(SmartNumber algaeIntakeSpeed) {
            this.targetSpeed = algaeIntakeSpeed.get();
        }

        public double getTargetSpeed() {
            return this.targetSpeed;
        }

    }

    public PivotState getPivotState() {
        return this.pivotState;
    }
    public RollerState getRollerState() {
        return this.rollerState;
    }
    
    public void setPivotState(PivotState pivotState) {
        this.pivotState = pivotState;
    }
    public void setRollerState(RollerState rollerState) {
        this.rollerState = rollerState;
    }

    protected Pivot() {
        this.pivotState = PivotState.STOW;
        this.rollerState = RollerState.ALGAE_STILL;
    }

    public abstract void rollersAcquire();

    public abstract void rollersDeacquire();

    public abstract boolean atTargetAngle();

    public abstract double getCurrentAngle();

    public abstract void setRollerMotor(double targetSpeed);

    public abstract void setRollersStill();

    @Override
    public void periodic() {
        SmartDashboard.putString("Pivot/State", pivotState.toString());
        SmartDashboard.putString("Pivot/Rollers/State",rollerState.toString());
    }
    
}