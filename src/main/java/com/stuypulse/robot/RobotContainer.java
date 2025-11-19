package com.stuypulse.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.stuypulse.robot.commands.oldAutons.coral.SingleCoralAuton;
import com.stuypulse.robot.commands.oldAutons.misc.DoNothingAuton;
import com.stuypulse.robot.commands.oldAutons.misc.MobilityAuton;
import com.stuypulse.robot.commands.climb.ClimbToClimb;
import com.stuypulse.robot.commands.climb.ClimbToDeployed;
import com.stuypulse.robot.commands.drive.DriveDefault;
import com.stuypulse.robot.commands.drive.setSpeedModifiers.DriveSetFullSpeed;
import com.stuypulse.robot.commands.drive.setSpeedModifiers.DriveSetHalfSpeed;
import com.stuypulse.robot.commands.pivot.pivot.PivotLower;
import com.stuypulse.robot.commands.pivot.pivot.PivotRaise;
import com.stuypulse.robot.commands.pivot.pivot.PivotStop;
import com.stuypulse.robot.commands.pivot.pivot.PivotToAlgaeIntake;
import com.stuypulse.robot.commands.pivot.pivot.PivotToAlgaeStow;
import com.stuypulse.robot.commands.pivot.pivot.PivotToCoralReseat;
import com.stuypulse.robot.commands.pivot.pivot.PivotToCoralStow;
import com.stuypulse.robot.commands.pivot.pivot.PivotToDefault;
import com.stuypulse.robot.commands.pivot.pivot.SetPivotControlMode;
import com.stuypulse.robot.commands.pivot.pivotCombos.PivotCoralScore;
import com.stuypulse.robot.commands.pivot.pivotCombos.PivotCoralScoreAuto;
import com.stuypulse.robot.commands.pivot.pivotCombos.PivotLollipopAlgaeIntake;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeHold;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeIntake;
import com.stuypulse.robot.commands.pivot.roller.PivotAlgaeOuttake;
import com.stuypulse.robot.commands.pivot.roller.PivotHoldCoral;
import com.stuypulse.robot.commands.pivot.roller.PivotRollerReseat;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotControlMode;
import com.stuypulse.robot.util.Clearances;
import com.stuypulse.robot.util.alignment.AlignmentPathLoader;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class RobotContainer {
	// Gamepads
	public final CommandXboxController driver = new CommandXboxController(Ports.Gamepad.DRIVER);

	// Subsystem
	private final Drivetrain driveSubsystem = Drivetrain.getInstance();
	private final Pivot pivot = Pivot.getInstance();

	// Autons
	private static SendableChooser<Command> autonChooser = new SendableChooser<>();

	// Robot container
	public RobotContainer() {
		configureAutons(); // MAKE SURE THIS IS RUN FIRST TO ADD IN COMMANDS INTO PATHPLANNER
		configureDefaultCommands();
		configureButtonBindings();
		configureSysId();
	}

	/****************/
	/*** DEFAULTS ***/
	/****************/

	private void configureDefaultCommands() {
		pivot.setDefaultCommand(new PivotHoldCoral());
		driveSubsystem.setDefaultCommand(new DriveDefault(driver, true));
	}

	/***********************/
	/*** BUTTON BINDINGS ***/
	/***********************/

	private void configureButtonBindings() {
		// TRIGGERS
		driver.rightTrigger() // Algae Ground Intake
				.onTrue(new SetPivotControlMode(PivotControlMode.USING_STATES))
				.onTrue(new PivotToAlgaeIntake())
				.whileTrue(new PivotAlgaeIntake().repeatedly())
				.onFalse(new PivotToAlgaeStow())
				.onFalse(new PivotAlgaeHold().repeatedly());
		driver.leftTrigger() // Algae Outtake
				.whileTrue(new PivotAlgaeOuttake().repeatedly())
				.onFalse(new WaitUntilCommand(() -> Clearances.isClearFromProc())
						.andThen(new SetPivotControlMode(PivotControlMode.USING_STATES))
						.andThen(new PivotToCoralStow())
						.andThen(new PivotHoldCoral()));

		// BUMPERS
		driver.rightBumper() // Lolipop Intake
				.onTrue(new SetPivotControlMode(Pivot.PivotControlMode.USING_STATES))
				.whileTrue(new PivotLollipopAlgaeIntake())
				.onFalse(new PivotToAlgaeStow())
				.onFalse(new PivotAlgaeHold().repeatedly());
		driver.leftBumper() // Score Coral
				.onTrue(new SetPivotControlMode(PivotControlMode.USING_STATES))

				.toggleOnTrue(new ConditionalCommand(
						new SequentialCommandGroup(new PivotRollerReseat(),
								Commands.repeatingSequence(new PivotToCoralReseat(), new PivotToCoralStow()))
								.onlyWhile(() -> driver.leftBumper().getAsBoolean()),

						new PivotCoralScore().onlyWhile(() -> !Clearances.isClearFromReef())
								.andThen(new WaitUntilCommand(() -> Clearances.isClearFromReef()))
								.andThen(new PivotToCoralStow().andThen(new PivotHoldCoral())),

						() -> (Clearances.isClearFromReef())));

		// BACK BUTTONS (REMAPPED ON CONTROLLER TO BE JOYSTICK BUTTONS)
		driver.rightStick() // pivot lower
				.onTrue(new SetPivotControlMode(PivotControlMode.MANUAL))
				.whileTrue(new PivotLower())
				.onFalse(new PivotStop());
		driver.leftStick() // pivot raise
				.onTrue(new SetPivotControlMode(PivotControlMode.MANUAL))
				.whileTrue(new PivotRaise())
				.onFalse(new PivotStop());

		// // ABXY BUTTONS
		// driver.x() // Climb
		// 		.whileTrue(new ClimbToClimb())
		// 		.onTrue(new DriveSetFullSpeed());
		// driver.b() // Deploy Climb
		// 		.whileTrue(new ClimbToDeployed())
		// 		.onTrue(new DriveSetHalfSpeed());

		// MENU BUTTONS
		// Alignment disabled for robo replay
		// driver.getRightMenuButton() // Drive to Nearest April Tag
		// .onTrue(
		// new SequentialCommandGroup(
		// new SetPivotControlMode(PivotControlMode.USING_STATES)
		// .withTimeout(0.01),
		// new PivotToDefault()
		// .withTimeout(0.01),
		// new AlignToReefNearest(driver.getDPadLeft().getAsBoolean()))
		// );
	}

	/**************/
	/*** AUTONS ***/
	/**************/

	public void configureAutons() {
		// PATHPLANNER
		driveSubsystem.configureAutoBuilder();
		registerAutonNamedCommands();

		AlignmentPathLoader.loadAlignmentpaths();

		// OLD - w/o pathplanner
		autonChooser.addOption("[OLD] Single", new SingleCoralAuton());
		autonChooser.addOption("[OLD] Do Nothing", new DoNothingAuton());
		autonChooser.addOption("[OLD] Mobility", new MobilityAuton());

		// NEW - w/ pathplanner
		autonChooser.addOption("COMP Center 1PC", new PathPlannerAuto("Center 1Pc"));
		autonChooser.setDefaultOption("COMP Processor 2 Pc", new PathPlannerAuto("Processor 2 Pc"));
		autonChooser.addOption("COMP Non-Processor 2 Pc",
				new PathPlannerAuto("Testing Non-Processor 2 Pc + AlgaePickup"));
		autonChooser.addOption("Procceser to E", new PathPlannerAuto("Procceser to E"));
		autonChooser.addOption("Center to Reef curve", new PathPlannerAuto("Center to Reef curve"));
		autonChooser.addOption("PP IJKLKL (non-proc 3pc 25 sec)", new PathPlannerAuto("IJKLKL"));

		SmartDashboard.putData("Autonomous", autonChooser);
	}

	private void registerAutonNamedCommands() {
		NamedCommands.registerCommand("PivotCoralScore", new PivotCoralScoreAuto());
		NamedCommands.registerCommand("PivotCoralScore2nd",
				new SequentialCommandGroup(new WaitUntilCommand(() -> !Clearances.isClearFromReef()),
						new PivotCoralScoreAuto()));

		NamedCommands.registerCommand("PivotLollipopAlgaeIntake", new PivotLollipopAlgaeIntake());
		NamedCommands.registerCommand("PivotAlgaeHold",
				new SequentialCommandGroup(new PivotAlgaeHold(), new PivotToAlgaeStow()));

		NamedCommands.registerCommand("PivotAlgaeOuttake", new PivotAlgaeOuttake());
		NamedCommands.registerCommand("Pivot default and Rotisserie",
				new SequentialCommandGroup(
						new PivotToDefault().withTimeout(0.01),
						new PivotHoldCoral().withTimeout(0.01)));

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
