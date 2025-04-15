package com.stuypulse.robot.subsystems.climber;

import com.stuypulse.robot.constants.Constants;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.stuylib.math.SLMath;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Climb extends SubsystemBase{
    private static final Climb instance;
    
    static {
        instance = new ClimbImpl();
    }

    public static Climb getInstance() {
        return instance;
    }

    public enum ClimbState {
        STOW(Settings.Climb.STOW_ANGLE),
        CLIMBING(Settings.Climb.CLIMBED_ANGLE);

        private Rotation2d targetAngle;

        private ClimbState(Rotation2d targetAngle) {
            this.targetAngle = Rotation2d.fromDegrees(
                SLMath.clamp(targetAngle.getDegrees(),Constants.Climb.MIN_ANGLE.getDegrees(), Constants.Climb.MAX_ANGLE.getDegrees()));
        }

        public Rotation2d getTargetAngle() {
            return this.targetAngle;
        }
    }

    private ClimbState state;

    protected Climb() {
        this.state = ClimbState.STOW;
    }

    public ClimbState getState(){
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
        SmartDashboard.putNumber("Climb Angle", Climb.getInstance().getCurrentAngle().getDegrees());
    }
}
