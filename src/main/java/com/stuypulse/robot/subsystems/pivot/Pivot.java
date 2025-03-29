package com.stuypulse.robot.subsystems.pivot;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Pivot extends SubsystemBase {
    
    private static final Pivot instance;

    static { 
        instance = new PivotImpl();
    }

    public static Pivot getInstance() {
        return instance;
    }

    private PivotState pivotState;
    
    public enum PivotState {
        STOW_CORAL(Settings.Pivot.CORAL_STOW_ANGLE),
        INTAKE_ALGAE(Settings.Pivot.ALGAE_INTAKE_ANGLE),
        HOLD_ALGAE(Settings.Pivot.ALGAE_HOLDING_ANGLE);

        double targetAngle;

        private PivotState(SmartNumber pivotAngle) {
            this.targetAngle = pivotAngle.get();
        }

        public double getTargetAngle() {
            return this.targetAngle;
        }
    }

    public PivotState getPivotState() {
        return this.pivotState;
    }

    public void setPivotState(PivotState pivotState) {
        this.pivotState = pivotState;
    }

    protected Pivot() {
        this.pivotState = PivotState.STOW_CORAL;
    }

    public abstract void rollersAcquire();

    public abstract void rollersDeacquire();

    public abstract void setRollerMotor(double speed);

    public abstract void setRollersStill();

    public abstract void setPivotMotor(double speed);

    @Override
    public void periodic() {
        SmartDashboard.putString("Pivot/Pivot State", pivotState.toString());
    }
    
}