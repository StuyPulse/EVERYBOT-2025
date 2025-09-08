package com.stuypulse.robot.subsystems.pivot;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.util.RobotVisualizer;
import com.stuypulse.stuylib.math.SLMath;

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

    protected PivotState pivotState;
    protected PivotControlMode pivotControlMode;
    
    protected Pivot() {
        this.pivotState = PivotState.STOW_CORAL;
        this.pivotControlMode = PivotControlMode.MANUAL;
    }
    
    public enum PivotState {
        DEFAULT(Settings.Pivot.DEFAULT_ANGLE_DEG),
        STOW_CORAL(Settings.Pivot.CORAL_STOW_ANGLE_DEG),
        SCORE_CORAL(Settings.Pivot.CORAL_SCORE_ANGLE_DEG),
        INTAKE_ALGAE(Settings.Pivot.ALGAE_GROUND_ANGLE_DEG),
        STOW_ALGAE(Settings.Pivot.ALGAE_HOLDING_ANGLE_DEG),
        INTAKE_ALGAE_FROM_LOLIPOP(Settings.Pivot.ALGAE_LOLLIPOP_ANGLE_DEG),
        RESEAT_CORAL(Settings.Pivot.CORAL_RESEAT_ANGLE_DEG);

        double targetAngleDeg;

        private PivotState(double targetAngleDeg) {
            this.targetAngleDeg = 
                    SLMath.clamp(
                    targetAngleDeg, 
                    Settings.Pivot.DEFAULT_ANGLE_DEG, 
                    Settings.Pivot.MAX_ANGLE_DEG);
        }

        public double getTargetAngleDeg() {
            return this.targetAngleDeg;
        }
    }

    public enum PivotControlMode {
        MANUAL(Settings.Pivot.CTRLMODE_MANUAL),
        USING_STATES(Settings.Pivot.CTRLMODE_STATES);

        String controlMode;

        private PivotControlMode(String controlMode) {
            this.controlMode = controlMode;
        }

        public String getPivotControlMode() {
            return this.controlMode;
        }

    }

    public PivotControlMode PivotControlMode() {
        return this.pivotControlMode;
    }

    public abstract void setPivotState(PivotState pivotState);

    public abstract PivotState getPivotState();

    public abstract void setRollerMotor(double speed);

    public abstract double getRollerMotor();

    public abstract void setPivotMotor(double speed);
    
    public abstract void resetPivotEncoder(double newEncoderPosition);

    public abstract void setPivotControlMode(PivotControlMode SetPivotStateMode);

    public abstract double getPivotRotationDeg();

    public abstract boolean atTargetAngle();

    @Override
    public void periodic() {
        SmartDashboard.putString("Pivot/Pivot State", pivotState.toString());
        SmartDashboard.putNumber("Pivot/Target Angle", this.pivotState.getTargetAngleDeg());
        SmartDashboard.putBoolean("Pivot/At Target Angle", atTargetAngle());

        if(Settings.DEBUG_MODE) {
        RobotVisualizer.getInstance().updatePivotAngle(getPivotRotationDeg(), atTargetAngle());
        RobotVisualizer.getInstance().updateRollers(getRollerMotor());
    }
}

    public abstract SysIdRoutine getSysIdRoutine();
}
