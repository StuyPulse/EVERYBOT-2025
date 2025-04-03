package com.stuypulse.robot.subsystems.pivot;

import com.stuypulse.robot.constants.Settings;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

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
        DEFAULT(Settings.Pivot.DEFAULT_ANGLE),
        STOW_CORAL(Settings.Pivot.CORAL_STOW_ANGLE),
        INTAKE_ALGAE(Settings.Pivot.ALGAE_INTAKE_ANGLE),
        HOLD_ALGAE(Settings.Pivot.ALGAE_HOLDING_ANGLE);

        Rotation2d targetAngle;

        // TODO: Need to fix the following a bit...
        private PivotState(Rotation2d coralStowAngle) {
            this.targetAngle = coralStowAngle;
        }

        public Rotation2d getTargetAngle() {
            return this.targetAngle;
        }
    }

    public void setPivotState(PivotState pivotState) {
        this.pivotState = pivotState;
    }

    protected Pivot() {
        this.pivotState = PivotState.STOW_CORAL;
    }

    public abstract void setRollerMotor(double speed);

    public abstract void setPivotMotor(double speed);

    @Override
    public void periodic() {
        SmartDashboard.putString("Pivot/Pivot State", pivotState.toString());
    }

    public abstract SysIdRoutine getSysIdRoutine();
    
}