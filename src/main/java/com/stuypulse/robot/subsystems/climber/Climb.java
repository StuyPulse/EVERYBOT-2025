package com.stuypulse.robot.subsystems.climber;

import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.util.RobotVisualizer;
import com.stuypulse.stuylib.math.SLMath;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Climb extends SubsystemBase {
    private static final Climb instance;

    static {
        instance = new ClimbImpl();
    }

    public static Climb getInstance() {
        return instance;
    }

    public enum ClimbState {
        DEFAULT(Settings.Climb.DEFAULT_ANGLE_DEG, Settings.Climb.DEFAULT_VOLTAGE),
        DEPLOYED(Settings.Climb.DEPLOY_ANGLE_DEG, Settings.Climb.DEPLOY_VOLTAGE),
        CLIMBING(Settings.Climb.CLIMBED_ANGLE_DEG, Settings.Climb.CLIMB_VOLTAGE);

        private double targetAngle;
        private double targetMotorSpeed;

        private ClimbState(double targetAngle, double targetMotorSpeed) {
            this.targetAngle = 
                    SLMath.clamp(targetAngle, Constants.Climb.MIN_ANGLE_DEG,
                            Constants.Climb.MAX_ANGLE_DEG);

            this.targetMotorSpeed = SLMath.clamp(targetMotorSpeed, -1.0, 1.0); // Motor speed can only be between -1 & 1
        }

        public double getTargetAngleDeg() {
            return this.targetAngle;
        }

        public double getTargetMotorSpeed() {
            return this.targetMotorSpeed;
        }
    }

    private ClimbState state;

    protected Climb() {
        this.state = ClimbState.CLIMBING;
    }

    public ClimbState getState() {
        return this.state;
    }

    public void setState(ClimbState state) {
        this.state = state;
    }

    public abstract double getCurrentAngleDeg();

    public abstract boolean atTargetAngle();

    @Override
    public void periodic() {
        SmartDashboard.putString("Climb/State", state.toString());

        RobotVisualizer.getInstance().updateClimb(getCurrentAngleDeg(), atTargetAngle());
    }
}
