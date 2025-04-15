package com.stuypulse.robot.commands.climb;

import com.stuypulse.robot.subsystems.climber.Climb.ClimbState;

public class ClimbToClimb extends ClimbToState{
    public ClimbToClimb() {
        super(ClimbState.CLIMBING);
    }
}
