package com.stuypulse.robot.commands.pivot;

import com.stuypulse.robot.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.Command;

public class SetPivotStateMode extends Command {
    Pivot pivot = Pivot.getInstance();
    
    public SetPivotStateMode (boolean SetPivotStateMode) {
        pivot.SetPivotStateMode(SetPivotStateMode);
    }
}
