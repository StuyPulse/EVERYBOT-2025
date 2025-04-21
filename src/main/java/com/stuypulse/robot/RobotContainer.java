package com.stuypulse.robot;

import com.stuypulse.robot.commands.auton.Combinations.PushBackwardsCoralAuton;
import com.stuypulse.robot.commands.auton.coral.DoubleCoralAuton;
import com.stuypulse.robot.commands.auton.coral.SingleCoralAuton;
import com.stuypulse.robot.commands.auton.Misc.DoNothingAuton;
import com.stuypulse.robot.commands.auton.Misc.MobilityAuton;
import com.stuypulse.robot.commands.auton.push.PushBackwardsAuton;
import com.stuypulse.robot.commands.auton.push.PushForwardsAuton;
import com.stuypulse.robot.commands.climb.ClimbToClimb;
import com.stuypulse.robot.commands.climb.ClimbToStow;
import com.stuypulse.robot.commands.drive.DriveDefault;
import com.stuypulse.robot.commands.leds.LEDApplyPattern;
import com.stuypulse.robot.commands.leds.LEDDeafultCommand;
import com.stuypulse.robot.commands.pivot.PivotLower;
import com.stuypulse.robot.commands.pivot.PivotRaise;
import com.stuypulse.robot.commands.pivot.PivotResetAngle;
import com.stuypulse.robot.commands.pivot.PivotToAlgaeIntake;
import com.stuypulse.robot.commands.pivot.PivotToAlgaeStow;
import com.stuypulse.robot.commands.pivot.PivotToCoralStow;
import com.stuypulse.robot.commands.pivot.SetPivotStateMode;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeIntake;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeOuttake;
import com.stuypulse.robot.commands.pivot.roller.PivotCoralOuttake;
import com.stuypulse.robot.commands.pivot.roller.PivotHoldCoral;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerStop;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.subsystems.leds.LEDController;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.constants.Ports;

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

    /***********************/
    /*** BUTTON BINDINGS ***/
    /***********************/

    private void configureButtonBindings() {
        //BUTTONS
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

        //TRIGGERS
        driver.getRightTriggerButton()
            .whileTrue(new PivotRaise())
            .onFalse(new PivotRollerStop());
        driver.getLeftTriggerButton()
            .whileTrue(new PivotLower())
            .onFalse(new PivotRollerStop());

        //BUMPERS
        driver.getRightBumper() 
            .whileTrue(new PivotAlgaeOuttake())
            .onFalse(new PivotHoldCoral());
        driver.getLeftBumper()
            .whileTrue(new PivotAlgaeIntake())
            .onFalse(new PivotRollerStop());

        //DPAD
        driver.getDPadRight()
            .onTrue(new SetPivotStateMode(true))
            .onTrue(new PivotToAlgaeStow())
            .whileFalse(new SetPivotStateMode(false));
        driver.getDPadDown()
            .onTrue(new SetPivotStateMode(true))
            .onTrue(new PivotToAlgaeIntake())
            .whileFalse(new SetPivotStateMode(false));
    }

    /**************/
    /*** AUTONS ***/
    /**************/

    public void configureAutons() {
        autonChooser.setDefaultOption("Misc - Mobility", new MobilityAuton());
        autonChooser.addOption("Misc - Do Nothing", new DoNothingAuton());
 
        autonChooser.addOption("Coral Only - Single", new SingleCoralAuton());
        autonChooser.addOption("Coral Only - Double", new DoubleCoralAuton());
 
        autonChooser.addOption("Push Only - Forwards", new PushForwardsAuton());
        autonChooser.addOption("Push Only - Backwards", new PushBackwardsAuton());
 
        autonChooser.addOption("Combination - Coral w/ Push", new PushBackwardsCoralAuton());

        SmartDashboard.putData("Autonomous", autonChooser);
    }

    public void configureSysId() {
        SysIdRoutine pivotSysIdRoutine = pivot.getSysIdRoutine();
        SysIdRoutine driveSysIdRoutine = driveSubsystem.getSysIdRoutine();
        
        autonChooser.addOption("z-SysID - Pivot - Dynamic Forwards (Up)", pivotSysIdRoutine.dynamic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("z-SysID - Pivot - Dynamic Backwards (Down)", pivotSysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse));
        autonChooser.addOption("z-SysID - Pivot - Quasistatic Forwards (Up)", pivotSysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("z-SysID - Pivot - Quasistatic Backwards (Down)", pivotSysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse));

        autonChooser.addOption("z-SysID - Drive - Dynamic Forward", driveSysIdRoutine.dynamic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("z-SysID - Drive - Dynamic Backwards", driveSysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse));
        autonChooser.addOption("z-SysID - Drive - Quasistatic Forward", driveSysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward));
        autonChooser.addOption("z-SysID - Drive - Quasistatic Backwards", driveSysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse));
    }

    public Command getAutonomousCommand() {
        return autonChooser.getSelected();
    }
}
