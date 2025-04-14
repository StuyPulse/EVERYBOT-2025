package com.stuypulse.robot;

import com.stuypulse.robot.commands.auton.Combinations.PushBackwardsL1Auton;

import com.stuypulse.robot.commands.auton.CoralOnly.DoubleCoralAuton;
import com.stuypulse.robot.commands.auton.CoralOnly.SingleCoralAuton;

import com.stuypulse.robot.commands.auton.Misc.DoNothingAuton;
import com.stuypulse.robot.commands.auton.Misc.MobilityAuton;

import com.stuypulse.robot.commands.auton.PushOnly.PushBackwardsAuton;
import com.stuypulse.robot.commands.auton.PushOnly.PushForwardsAuton;

import com.stuypulse.robot.commands.climb.ClimbToClimb;
import com.stuypulse.robot.commands.climb.ClimbToStow;

import com.stuypulse.robot.commands.pivot.PivotCoralOuttake;
import com.stuypulse.robot.commands.pivot.PivotLower;
import com.stuypulse.robot.commands.pivot.PivotRaise;
import com.stuypulse.robot.commands.pivot.PivotResetAngle;
import com.stuypulse.robot.commands.pivot.PivotStop;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeIntake;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeOuttake;
import com.stuypulse.robot.commands.pivot.roller.PivotHoldCoral;
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
        //pivot.setDefaultCommand(new PivotHoldCoral());
        //driveSubsystem.setDefaultCommand(new DriveDefault(driver, true));
    }

    /***************/
    /*** BUTTONS ***/
    /***************/

    private void configureButtonBindings() {
            driver.getTopButton()
                .onTrue(new PivotCoralOuttake())
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
                .onTrue(new PivotResetAngle());
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
        autonChooser.setDefaultOption("Auton - Mobility", new MobilityAuton());

        autonChooser.addOption("Auton - Coral - Single", new SingleCoralAuton());
        autonChooser.addOption("Auton - Coral - Single - With Push", new PushBackwardsL1Auton());
        autonChooser.addOption("Auton - Coral - Double", new DoubleCoralAuton());

        autonChooser.addOption("Auton - Push - Forwards", new PushForwardsAuton());
        autonChooser.addOption("Auton - Push - Backwards", new PushBackwardsAuton());

        autonChooser.addOption("Auton - Stay Still", new DoNothingAuton());

        SmartDashboard.putData("Autonomous", autonChooser);
    }
    public void configureSysId() {
        SysIdRoutine pivotSysIdRoutine = pivot.getSysIdRoutine();
        autonChooser.addOption("SysID - Pivot - Dynamic Forwards (Up)", pivotSysIdRoutine.dynamic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("SysID - Pivot - Dynamic Backwards (Down)", pivotSysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse));
        autonChooser.addOption("SysID - Pivot - Quasistatic Forwards (Up)", pivotSysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("SysID - Pivot - Quasistatic Backwards (Down)", pivotSysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse));

        SysIdRoutine driveSysIdRoutine = driveSubsystem.getSysIdRoutine();
        autonChooser.addOption("SysID - Drive - Dynamic Forward", driveSysIdRoutine.dynamic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("SysID - Drive - Dynamic Backwards", driveSysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse));
        autonChooser.addOption("SysID - Drive - Quasistatic Forward", driveSysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("SysID - Drive - Quasistatic Backwards", driveSysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse));
    }

    public Command getAutonomousCommand() {
        return autonChooser.getSelected();
    }
}