package com.stuypulse.robot.commands.pivot;
import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class PivotCoralOut extends InstantCommand {
    private Pivot pivot;    
    public PivotCoralOut() {
        pivot = Pivot.getInstance();
        addRequirements(pivot);
    }
    public void initialize() {
        pivot.setRollerMotor(Settings.Pivot.CORAL_SHOOT_SPEED.get());
    }
}