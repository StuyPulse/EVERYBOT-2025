package com.stuypulse.robot.subsystems.pivot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import com.stuypulse.robot.util.SysId;
import com.stuypulse.robot.constants.Gains;
import com.stuypulse.robot.constants.Motors;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.ArmFeedforward;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.booleans.BStream;
import com.stuypulse.stuylib.streams.booleans.filters.BDebounce;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class PivotImpl extends Pivot {
    private SparkMax pivotMotor;
    private SparkMax rollerMotor;
    private RelativeEncoder pivotEncoder;

    private Controller controller;
    
    private BStream stallDetector;
    
    private SmartNumber CurrentRollerSetSpeed = new SmartNumber("CurrentRollerSetSpeed", 0);
    private SmartNumber CurrentPivotSetSpeed = new SmartNumber("CurrentPivotSetSpeed", 0);

    public PivotImpl() {
        super();
        pivotMotor = new SparkMax(Ports.Pivot.PIVOT_MOTOR,MotorType.kBrushless);
        rollerMotor = new SparkMax(Ports.Pivot.ROLLER_MOTOR, MotorType.kBrushed);

        Motors.PivotConfig.PIVOT_MOTOR_CONFIG.encoder
            .positionConversionFactor(Settings.Pivot.PIVOT_MOTOR_GEAR_RATIO * Settings.Pivot.PIVOT_MOTOR_REDUCTION_FACTOR);  
        
        pivotMotor.configure(Motors.PivotConfig.PIVOT_MOTOR_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rollerMotor.configure(Motors.PivotConfig.PIVOT_ROLLER_MOTOR_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        pivotEncoder = pivotMotor.getEncoder();
      
        controller = new ArmFeedforward(Gains.Pivot.FF.kG)
                .add(new PIDController(Gains.Pivot.PID.kP, Gains.Pivot.PID.kI, Gains.Pivot.PID.kD));

        stallDetector = BStream.create(() -> pivotMotor.getOutputCurrent() > Settings.Pivot.PIVOT_STALL_CURRENT)
            .filtered(new BDebounce.Rising(Settings.Pivot.PIVOT_STALL_DEBOUNCE));
    }
    
    @Override
    public SysIdRoutine getSysIdRoutine() {
        return SysId.getSysIdRoutine(
            pivotMotor.toString(),
            pivotMotor,
            getPivotRotation(),
            Pivot.getInstance(),
            0.5,
            .5,
            10
        );
    }

    @Override
    public void setRollerMotor(double speed) {
        CurrentRollerSetSpeed.set(speed);
        rollerMotor.set(speed);
    }

    @Override
    public void setPivotMotor(double speed) {
        CurrentPivotSetSpeed.set(speed);
        pivotMotor.set(speed);
    }

    @Override
    public Rotation2d getPivotRotation() {
        return Rotation2d.fromRotations(pivotEncoder.getPosition());
    }

    @Override
    public void resetPivotEncoder(double newEncoderPosition) {
        pivotEncoder.setPosition(newEncoderPosition);
    }

    @Override
    public void setPivotState(PivotState pivotState) { 
        this.pivotState = pivotState; 
    }

    @Override
    public PivotState getPivotState() { 
        return pivotState;
    }

    @Override
    public void setPivotControlMode(PivotControlMode pivotControlMode) {
        this.pivotControlMode = pivotControlMode;
    }

    @Override
    public void periodic() {
        super.periodic();
      
        if (stallDetector.getAsBoolean()){
            if(rollerMotor.get() > 0) { //Check Stalling Direction: Check if hitting top hard stop
                resetPivotEncoder(Settings.Pivot.DEFAULT_ANGLE.getRotations());
            } else if(rollerMotor.get() < 0) { //Check Stalling Direction: Check if hitting bottom hard stop
                resetPivotEncoder(Settings.Pivot.MAX_ANGLE.getRotations());
            }
        }

        if (pivotControlMode == PivotControlMode.USING_STATES) {
            pivotMotor.setVoltage(controller.update(pivotState.targetAngle.getDegrees(), getPivotRotation().getDegrees()));
        }
      
        SmartDashboard.putNumber("Pivot/Number of Rotations", getPivotRotation().getRotations());
        SmartDashboard.putNumber("Pivot/Current Angle", getPivotRotation().getDegrees());
        SmartDashboard.putNumber("Pivot/Supply Current", pivotMotor.getOutputCurrent());
        SmartDashboard.putString("Pivot/Control mode", pivotControlMode.getPivotControlMode());
    }       
}
