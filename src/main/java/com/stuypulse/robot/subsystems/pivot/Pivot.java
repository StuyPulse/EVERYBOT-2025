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

    private RollerState rollerState;
    
    public enum RollerState {
        INTAKE_ALGAE(Settings.Pivot.ALGAE_INTAKE_SPEED),
        ALGAE_HOLD(Settings.Pivot.ALGAE_HOLDING_SPEED),
        SHOOT_ALGAE(Settings.Pivot.ALGAE_SHOOT_SPEED),
        SHOOT_CORAL(Settings.Pivot.CORAL_SHOOT_SPEED);

        double targetSpeed;

        private RollerState(SmartNumber algaeIntakeSpeed) {
            this.targetSpeed = algaeIntakeSpeed.get();
        }

        public double getTargetSpeed() {
            return this.targetSpeed;
        }
    }

    public RollerState getRollerState() {
        return this.rollerState;
    }

    public void setRollerState(RollerState rollerState) {
        this.rollerState = rollerState;
    }

    protected Pivot() {
        this.rollerState = RollerState.ALGAE_HOLD;
    }

    public abstract void rollersAcquire();

    public abstract void rollersDeacquire();

    public abstract void setRollerMotor(double speed);

    public abstract void setRollersStill();

    public abstract void setPivotMotor(double speed);

    @Override
    public void periodic() {
        SmartDashboard.putString("Pivot/Roller State",rollerState.toString());
    }
    
}