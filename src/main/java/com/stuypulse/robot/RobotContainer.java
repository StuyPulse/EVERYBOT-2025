package com.stuypulse.robot;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.stuypulse.robot.commands.auton.DoNothingAuton;
import com.stuypulse.robot.commands.auton.DoubleL1Auton;
import com.stuypulse.robot.commands.auton.MobilityAuton;
import com.stuypulse.robot.commands.auton.SingleL1Auton;
import com.stuypulse.robot.commands.auton.PushBackwardsL1Auton;
import com.stuypulse.robot.commands.auton.PushForwardsAuton;
import com.stuypulse.robot.commands.climb.ClimbToClimb;
import com.stuypulse.robot.commands.climb.ClimbToStow;

import com.stuypulse.robot.commands.drive.DriveDefault;
import com.stuypulse.robot.commands.drive.DriveJoystick;

import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.leds.LEDDeafultCommand;
import com.stuypulse.robot.commands.pivot.PivotLower;
import com.stuypulse.robot.commands.pivot.PivotRaise;
import com.stuypulse.robot.commands.pivot.PivotResetAngle;
import com.stuypulse.robot.commands.pivot.PivotStop;
import com.stuypulse.robot.commands.pivot.PivotToAlgaeIntake;
import com.stuypulse.robot.commands.pivot.PivotToAlgaeStow;
import com.stuypulse.robot.commands.pivot.PivotToCoralStow;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeIntake;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeOuttake;
import com.stuypulse.robot.commands.pivot.roller.PivotCoralOuttake;
import com.stuypulse.robot.commands.pivot.roller.PivotHoldCoral;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerToDirection;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerStop;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.climber.Climb;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.subsystems.leds.LEDController;
import com.stuypulse.robot.subsystems.pivot.Pivot;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class RobotContainer {

    // Gamepads
    public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
    public final Gamepad operator = new AutoGamepad(Ports.Gamepad.OPERATOR);

    // public final Stick joystick = new Stick(Ports.Gamepad.JOYSTICK);
    
    // Subsystem
    private final LEDController ledSubsystem = LEDController.getInstance();
    private final Drivetrain driveSubsystem = Drivetrain.getInstance();
    private final Pivot pivot = Pivot.getInstance();

    // Autons
    private static SendableChooser<Command> autonChooser = new SendableChooser<>();


    // Robot container

    public RobotContainer() {
        configureDefaultCommands();
        configureButtonBindings();
        configureAutons();
        configureSysId();
    }

    /****************/
    /*** DEFAULTS ***/
    /****************/

    private void configureDefaultCommands() {
        //ledSubsystem.setDefaultCommand(new LEDDeafultCommand());
        pivot.setDefaultCommand(new PivotHoldCoral());
        driveSubsystem.setDefaultCommand(new DriveDefault(driver, true));
    }

    /***************/
    /*** BUTTONS ***/
    /***************/

    private void configureButtonBindings() {
            driver.getTopButton()
                .whileTrue(new PivotRollerStop())
                .whileTrue(new PivotCoralOuttake())
                .onFalse(new PivotHoldCoral());
            driver.getLeftButton()
                .whileTrue(new ClimbToClimb());
            driver.getRightButton()
                .whileTrue(new ClimbToStow());
            // driver.getBottomButton()
            //     .whileTrue(new VisionAlignToReef())

            driver.getRightTriggerButton()
                .whileTrue(new PivotRaise())
                .onFalse(new PivotStop());
            driver.getLeftTriggerButton()
                .whileTrue(new PivotLower())
                .onFalse(new PivotStop());

            driver.getRightBumper() 
                .whileTrue(new PivotAlgaeOuttake())
                .onFalse(new PivotHoldCoral());
            driver.getLeftBumper()
                .whileTrue(new PivotAlgaeIntake())
                .onFalse(new PivotRollerStop());

            driver.getDPadUp()
                .onTrue(new PivotToCoralStow());
    
            driver.getDPadRight()
                .onTrue(new PivotToAlgaeStow());

            driver.getDPadDown()
                .onTrue(new PivotToAlgaeIntake());

        }
        /*else if(Settings.DriveMode.GAMEPAD.toString() == "JOYSTICK") {     
            joystick.getTriggerTriggered()
                .whileTrue(new PivotCoralOut())
                .whileTrue(new LEDApplyPattern(Settings.LEDPatterns.CORAL_OUT));
        
            joystick.getTop_TopRightButton()
                .onTrue(new ClimbToStow());
            joystick.getTop_TopLeftButton()
                .onTrue(new ClimbToStow());
            joystick.getTop_BottomRightButton()
                .onTrue(new ClimbToClimb());
            joystick.getTop_BottomLeftButton()
                .onTrue(new ClimbToClimb());

            joystick.getHatUp()
                .whileTrue(new PivotLower())
                .onFalse(new PivotStop());
            joystick.getHatDown()
                .whileTrue(new PivotRaise())
                .onFalse(new PivotStop());

            joystick.getThrottleUp()
                .whileTrue(new PivotAlgaeIntake());
            joystick.getThrottleDown()
                .whileTrue(new PivotAlgaeOutake());
   
            while(joystick.triggerTriggered())                                              new PivotCoralOut();
            if(joystick.getTop_TopLeftButton() || joystick.getTop_TopRightButton())         new ClimbToStow();
            if(joystick.getTop_BottomLeftButton() || joystick.getTop_BottomRightButton())   new ClimbToClimb();
            while(joystick.getHatUp())                                                      new PivotLower();
            while(joystick.getHatDown())                                                    new PivotRaise();
            if(!joystick.getHatUp() && !joystick.getHatDown())                              new PivotStop();           
        }*/
    
    /**************/
    /*** AUTONS ***/
    /**************/

    public void configureAutons() {
        autonChooser.setDefaultOption("Mobility", new MobilityAuton());
        autonChooser.addOption("1xL1", new SingleL1Auton());
        autonChooser.addOption("2xL1", new DoubleL1Auton());
        autonChooser.addOption("Push Forwards", new PushForwardsAuton());
        autonChooser.addOption("Push Backwards + L1", new PushBackwardsL1Auton());
        autonChooser.addOption("Do Nothing", new DoNothingAuton());

        SmartDashboard.putData("Autonomous", autonChooser);
    }
    public void configureSysId() {
        SysIdRoutine pivotSysIdRoutine = pivot.getSysIdRoutine();
        autonChooser.addOption("Pivot Dynamic Up (Forwards)", pivotSysIdRoutine.dynamic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("Pivot Dynamic Down (Backwards)", pivotSysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse));
        autonChooser.addOption("Pivot Quasistatic Up (Forwards)", pivotSysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("Pivot Quasistatic Down (Backwards)", pivotSysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse));

        SysIdRoutine driveSysIdRoutine = driveSubsystem.getSysIdRoutine();
        autonChooser.addOption("Drive Dynamic Forward", driveSysIdRoutine.dynamic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("Drive Dynamic Backwards", driveSysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse));
        autonChooser.addOption("Drive Quasistatic Forward", driveSysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("Drive Quasistatic Backwards", driveSysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse));
    }

    public Command getAutonomousCommand() {
        return autonChooser.getSelected();
    }
}