package com.stuypulse.robot.subsystems.climber;

import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.util.RobotVisualizer;
import com.stuypulse.stuylib.math.SLMath;

import edu.wpi.first.math.geometry.Rotation2d;
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
        DEFAULT(Settings.Climb.DEFAULT_ANGLE, Settings.Climb.DEFAULT_VOLTAGE),
        STOW(Settings.Climb.STOW_ANGLE, Settings.Climb.STOW_VOLTAGE),
        CLIMBING(Settings.Climb.CLIMBED_ANGLE, Settings.Climb.CLIMBING_VOLTAGE);

        private Rotation2d targetAngle;
        private double targetMotorSpeed;

        private ClimbState(Rotation2d targetAngle, double targetMotorSpeed) {
            this.targetAngle = Rotation2d.fromDegrees(
                    SLMath.clamp(targetAngle.getDegrees(), Constants.Climb.MIN_ANGLE.getDegrees(),
                            Constants.Climb.MAX_ANGLE.getDegrees()));

            this.targetMotorSpeed = SLMath.clamp(targetMotorSpeed, -1.0, 1.0); // Motor speed can only be between -1 & 1
        }

        public Rotation2d getTargetAngle() {
            return this.targetAngle;
        }

        public double getTargetMotorSpeed() {
            return this.targetMotorSpeed;
        }
    }

    private ClimbState state;

    protected Climb() {
        this.state = ClimbState.DEFAULT;
    }

    public ClimbState getState() {
        return this.state;
    }

    public void setState(ClimbState state) {
        this.state = state;
    }

    public abstract Rotation2d getCurrentAngle();

    public abstract boolean atTargetAngle();

    @Override
    public void periodic() {
        SmartDashboard.putString("Climb/State", state.toString());

        RobotVisualizer.getInstance().updateClimb(getCurrentAngle(), atTargetAngle());
    }
}
