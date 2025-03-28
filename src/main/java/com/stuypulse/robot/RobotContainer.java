package com.stuypulse.robot;

import com.stuypulse.robot.commands.auton.DoNothingAuton;
import com.stuypulse.robot.commands.auton.DoubleL1Auton;
import com.stuypulse.robot.commands.auton.MobilityAuton;
import com.stuypulse.robot.commands.auton.SingleL1Auton;
import com.stuypulse.robot.commands.auton.PushBackwardsL1Auton;

import com.stuypulse.robot.commands.climb.ClimbToClimb;
import com.stuypulse.robot.commands.climb.ClimbToStow;

import com.stuypulse.robot.commands.drive.DriveDefault;
import com.stuypulse.robot.commands.drive.DriveJoystick;

import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.leds.LEDDeafultCommand;

import com.stuypulse.robot.commands.pivot.PivotAlgaeIntake;
import com.stuypulse.robot.commands.pivot.PivotAlgaeOutake;
import com.stuypulse.robot.commands.pivot.PivotCoralOut;
import com.stuypulse.robot.commands.pivot.PivotLower;
import com.stuypulse.robot.commands.pivot.PivotRaise;
import com.stuypulse.robot.commands.pivot.PivotStop;

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

public class RobotContainer {

    // Gamepads
    public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
    public final Gamepad operator = new AutoGamepad(Ports.Gamepad.OPERATOR);

    // public final Stick joystick = new Stick(Ports.Gamepad.JOYSTICK);
    
    // Subsystem
    private final LEDController ledSubsystem = LEDController.getInstance();
    private final Drivetrain driveSubsystem = Drivetrain.getInstance();

    // Autons
    private static SendableChooser<Command> autonChooser = new SendableChooser<>();


    // Robot container

    public RobotContainer() {
        configureDefaultCommands();
        configureButtonBindings();
        configureAutons();
    }

    /****************/
    /*** DEFAULTS ***/
    /****************/

    private void configureDefaultCommands() {
        ledSubsystem.setDefaultCommand(new LEDDeafultCommand());
        //if(Settings.DriveMode.GAMEPAD.toString() == "XBOX") {
        driveSubsystem.setDefaultCommand(new DriveDefault(driver, true));
        //}// else if (Settings.DriveMode.GAMEPAD.toString() == "JOYSTICK") {
        //    driveSubsystem.setDefaultCommand(new DriveJoystick(joystick, true));
    //    }
    }

    /***************/
    /*** BUTTONS ***/
    /***************/

    private void configureButtonBindings() {
            driver.getTopButton()
                .whileTrue(new PivotCoralOut())
                .whileTrue(new LEDApplyPattern(Settings.LEDPatterns.CORAL_OUT))
                .onFalse(new PivotStop());
            driver.getLeftButton()
                .onTrue(new ClimbToClimb());
            driver.getRightButton()
                .onTrue(new ClimbToStow());
            // driver.getBottomButton()
            //     .whileTrue(new VisionAlignToReef());

            driver.getRightTriggerButton()
                .whileTrue(new PivotRaise())
                .onFalse(new PivotStop());
            driver.getLeftTriggerButton()
                .whileTrue(new PivotLower())
                .onFalse(new PivotStop());

            driver.getRightBumper() 
                .whileTrue(new PivotAlgaeOutake())
                .onFalse(new PivotStop());
            driver.getLeftBumper()
                .whileTrue(new PivotAlgaeIntake())
                .onFalse(new PivotStop());
        }
        // else if(Settings.DriveMode.GAMEPAD.toString() == "JOYSTICK") {     
        //     joystick.getTriggerTriggered()
        //         .whileTrue(new PivotCoralOut())
        //         .whileTrue(new LEDApplyPattern(Settings.LEDPatterns.CORAL_OUT));
        
        //     joystick.getTop_TopRightButton()
        //         .onTrue(new ClimbToStow());
        //     joystick.getTop_TopLeftButton()
        //         .onTrue(new ClimbToStow());
        //     joystick.getTop_BottomRightButton()
        //         .onTrue(new ClimbToClimb());
        //     joystick.getTop_BottomLeftButton()
        //         .onTrue(new ClimbToClimb());

        //     joystick.getHatUp()
        //         .whileTrue(new PivotLower())
        //         .onFalse(new PivotStop());
        //     joystick.getHatDown()
        //         .whileTrue(new PivotRaise())
        //         .onFalse(new PivotStop());

        //     joystick.getThrottleUp()
        //         .whileTrue(new PivotAlgaeIntake());
        //     joystick.getThrottleDown()
        //         .whileTrue(new PivotAlgaeOutake());


        //     /*   
        //     while(joystick.triggerTriggered())                                              new PivotCoralOut();
        //     if(joystick.getTop_TopLeftButton() || joystick.getTop_TopRightButton())         new ClimbToStow();
        //     if(joystick.getTop_BottomLeftButton() || joystick.getTop_BottomRightButton())   new ClimbToClimb();
        //     while(joystick.getHatUp())                                                      new PivotLower();
        //     while(joystick.getHatDown())                                                    new PivotRaise();
        //     if(!joystick.getHatUp() && !joystick.getHatDown())                              new PivotStop();
        //     */
        // }
    

    /**************/
    /*** AUTONS ***/
    /**************/

    public void configureAutons() {

        autonChooser.setDefaultOption("Mobility Auton", new MobilityAuton());
        autonChooser.addOption("Single L1", new SingleL1Auton());
        autonChooser.addOption("Double L1", new DoubleL1Auton());
        autonChooser.addOption("Push Backwards", new PushBackwardsL1Auton());
        autonChooser.addOption("Do Nothing", new DoNothingAuton());

        SmartDashboard.putData("Autonomous", autonChooser);
    }

    public Command getAutonomousCommand() {
        return autonChooser.getSelected();
    }
}

