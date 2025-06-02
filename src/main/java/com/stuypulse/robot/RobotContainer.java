package com.stuypulse.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;
import com.stuypulse.robot.commands.auton.combinations.CoralgaeAuton;
import com.stuypulse.robot.commands.auton.combinations.PushBackwardsCoralAuton;
import com.stuypulse.robot.commands.auton.coral.DoubleCenterCoralAuton;
import com.stuypulse.robot.commands.auton.coral.SingleCoralAuton;
import com.stuypulse.robot.commands.auton.misc.DoNothingAuton;
import com.stuypulse.robot.commands.auton.misc.MobilityAuton;
import com.stuypulse.robot.commands.auton.push.PushBackwardsAuton;
import com.stuypulse.robot.commands.auton.push.PushForwardsAuton;
import com.stuypulse.robot.commands.climb.ClimbToClimb;
import com.stuypulse.robot.commands.climb.ClimbToStow;
import com.stuypulse.robot.commands.drive.DriveDefault;
import com.stuypulse.robot.commands.pivot.PivotCombos.PivotCoralScore;
import com.stuypulse.robot.commands.pivot.PivotCombos.PivotLolipopAlgeaIntake;
import com.stuypulse.robot.commands.pivot.PivotLower;
import com.stuypulse.robot.commands.pivot.PivotRaise;
import com.stuypulse.robot.commands.pivot.PivotReseatCoral;
import com.stuypulse.robot.commands.pivot.PivotResetAngle;
import com.stuypulse.robot.commands.pivot.PivotStop;
import com.stuypulse.robot.commands.pivot.PivotToAlgaeIntake;
import com.stuypulse.robot.commands.pivot.PivotToAlgaeStow;
import com.stuypulse.robot.commands.pivot.PivotToCoralStow;
import com.stuypulse.robot.commands.pivot.SetPivotControlMode;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeHold;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeIntake;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeOuttake;
import com.stuypulse.robot.commands.pivot.roller.PivotHoldCoral;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerStop;
import com.stuypulse.robot.constants.Paths;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.subsystems.leds.LEDController;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotControlMode;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class RobotContainer {
	// Gamepads
	public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
	public final Gamepad operator = new AutoGamepad(Ports.Gamepad.OPERATOR);

	// Subsystem
	private final LEDController ledSubsystem = LEDController.getInstance();
	private final Drivetrain driveSubsystem;
	private final Pivot pivot = Pivot.getInstance();
	//private final LimelightVision limelightVision;

	// Autons
	private static SendableChooser<Command> autonChooser = new SendableChooser<>();

	// Robot container
	public RobotContainer() {
		driveSubsystem = Drivetrain.getInstance();
		//limelightVision  = LimelightVision.getInstance();

		configureAutons(); // MAKE SURE THIS IS RUN FIRST TO ADD IN COMMANDS INTO PATHPLANNER
		configureDefaultCommands();
		configureButtonBindings();
		configureSysId();
	}

	/****************/
	/*** DEFAULTS ***/
	/****************/

	private void configureDefaultCommands() {
		// ledSubsystem.setDefaultCommand(new LEDDeafultCommand());
		pivot.setDefaultCommand(new PivotHoldCoral());
		driveSubsystem.setDefaultCommand(new DriveDefault(driver, true));
	}

	/***********************/
	/*** BUTTON BINDINGS ***/
	/***********************/

	private void configureButtonBindings() {
		// BUTTONS
		driver.getTopButton() // Coral Score
                .onTrue(new SetPivotControlMode(PivotControlMode.USING_STATES))
                .whileTrue(new PivotCoralScore())
                .onFalse(new PivotToCoralStow())
                .onFalse(new PivotHoldCoral());
		driver.getLeftButton() // Climb
				.whileTrue(new ClimbToClimb());
		driver.getRightButton() // Stow Climb
				.whileTrue(new ClimbToStow());
		driver.getBottomButton()
				.whileTrue(new PivotAlgaeOuttake())
				.onFalse(new PivotHoldCoral());

		// TRIGGERS
		driver.getLeftTriggerButton() // Pivot Up
				.onTrue(new SetPivotControlMode(PivotControlMode.MANUAL))
				.whileTrue(new PivotRaise())
				.onFalse(new PivotStop());
		driver.getRightTriggerButton() // Pivot Down
				.onTrue(new SetPivotControlMode(PivotControlMode.MANUAL))
				.whileTrue(new PivotLower())
				.onFalse(new PivotStop());

		// BUMPERS
		driver.getRightBumper() // Algae Outtake
				.whileTrue(new PivotAlgaeOuttake())
				.onFalse(new PivotHoldCoral());
		driver.getLeftBumper() // Algae Intake
				.whileTrue(new PivotAlgaeIntake())
				.onFalse(new PivotAlgaeHold());

		// DPAD
		driver.getDPadRight() // Pivot to Hold Algae
				.onTrue(new SetPivotControlMode(PivotControlMode.USING_STATES))
				.onTrue(new PivotToAlgaeStow())
				.onTrue(new PivotAlgaeHold());
		driver.getDPadDown() // Pivot to Intake Algae
				.onTrue(new SetPivotControlMode(PivotControlMode.USING_STATES))
				.onTrue(new PivotToAlgaeIntake());
		driver.getDPadLeft() // Pivot to Lollipop Intake
				// .onTrue(new SetPivotControlMode(Pivot.PivotControlMode.USING_STATES))
				.onTrue(new PivotLolipopAlgeaIntake())
				.onFalse(new PivotAlgaeHold())
				.onFalse(new PivotToAlgaeStow());
		

		// MENU BUTTONS
		driver.getRightMenuButton() // Reset Relative Encoder
				.onTrue(new PivotResetAngle());
		driver.getLeftMenuButton() // Reseat Coral
				.whileTrue(new PivotReseatCoral());

		// JOYSTICK BUTTONS
		// driver.getLeftStickButton() // Drive to Nearest April Tag
		// .whileTrue(new VisionDriveToNearestApriltag())
		// .onFalse(new DriveArcade(0, 0, true));
	}

	/**************/
	/*** AUTONS ***/
	/**************/

	public void configureAutons() {
		// Coral
		autonChooser.setDefaultOption("Coral Only - Single", new SingleCoralAuton());
		autonChooser.addOption("Coral Only - Center Double", new DoubleCenterCoralAuton());

		// Misc
		autonChooser.addOption("Misc - Do Nothing", new DoNothingAuton());
		autonChooser.addOption("Misc - Mobility", new MobilityAuton());

		// Push
		autonChooser.addOption("Push Only - Forwards", new PushForwardsAuton());
		autonChooser.addOption("Push Only - Backwards", new PushBackwardsAuton());

		autonChooser.addOption("Combination - Coral w/ Push", new PushBackwardsCoralAuton());
		autonChooser.addOption("Combination - Coralgae", new CoralgaeAuton());

		// PATHPLANNER
		driveSubsystem.configureAutoBuilder();
		registerAutoCommands();
		
		try {
			PathPlannerPath AB = PathPlannerPath.fromPathFile("AB Drive");

			Paths.loadPath("AB", AB);
		} catch (Exception e) {}

		autonChooser.addOption("PP Coral 1PC", new PathPlannerAuto("Center 1Pc"));
		autonChooser.addOption("pp 2 piece", new PathPlannerAuto("Non-Processor 2 Pc + AlgaePickup"));
		autonChooser.addOption("pp Processor Coralgae", new PathPlannerAuto("Processor Coralgae"));
		autonChooser.addOption("PP Procceser to E", new PathPlannerAuto("Procceser to E"));
		autonChooser.addOption("PP Center to Reef curve", new PathPlannerAuto("Center to Reef curve"));
		autonChooser.addOption("PP Processor 2 Pc", new PathPlannerAuto("Processor 2 Pc"));
		autonChooser.addOption("PP Testing Non-Processor 2 Pc + AlgaePickup", new PathPlannerAuto("Testing Non-Processor 2 Pc + AlgaePickup"));
		
		SmartDashboard.putData("Autonomous", autonChooser);
	}

	private void registerAutoCommands() {
		NamedCommands.registerCommand("PivotCoralScore",
				new SequentialCommandGroup(
						new PivotCoralScore().withTimeout(1), new PivotToCoralStow().withTimeout(.02), new PivotRollerStop().withTimeout(0.02)));
		NamedCommands.registerCommand("PivotLollipopAlgaeIntake", new PivotLolipopAlgeaIntake());
		NamedCommands.registerCommand("PivotAlgaeHold",
				new SequentialCommandGroup(new PivotAlgaeHold(), new PivotToAlgaeStow()));
		NamedCommands.registerCommand("PivotAlgaeOuttake", new PivotAlgaeOuttake());
	}

	public void configureSysId() {
		SysIdRoutine pivotSysIdRoutine = pivot.getSysIdRoutine();
		SysIdRoutine driveSysIdRoutine = driveSubsystem.getSysIdRoutine();

		autonChooser.addOption("z-SysID - Pivot - Dynamic Forwards (Up)",
				pivotSysIdRoutine.dynamic(SysIdRoutine.Direction.kForward));
		autonChooser.addOption("z-SysID - Pivot - Dynamic Backwards (Down)",
				pivotSysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse));
		autonChooser.addOption("z-SysID - Pivot - Quasistatic Forwards (Up)",
				pivotSysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward));
		autonChooser.addOption("z-SysID - Pivot - Quasistatic Backwards (Down)",
				pivotSysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse));

		autonChooser.addOption("z-SysID - Drive - Dynamic Forward",
				driveSysIdRoutine.dynamic(SysIdRoutine.Direction.kForward));
		autonChooser.addOption("z-SysID - Drive - Dynamic Backwards",
				driveSysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse));
		autonChooser.addOption("z-SysID - Drive - Quasistatic Forward",
				driveSysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward));
		autonChooser.addOption("z-SysID - Drive - Quasistatic Backwards",
				driveSysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse));
	}

	public Command getAutonomousCommand() {
		return autonChooser.getSelected();
	}
}
