package com.stuypulse.robot.commands.climb;

import com.stuypulse.robot.subsystems.climber.Climb.ClimbState;

public class ClimbToExtend extends ClimbToState{
    public ClimbToExtend() {
        super(ClimbState.EXTEND);
    }
}
