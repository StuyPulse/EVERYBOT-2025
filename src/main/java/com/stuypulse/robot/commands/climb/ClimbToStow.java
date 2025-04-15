package com.stuypulse.robot.commands.climb;

import com.stuypulse.robot.subsystems.climber.Climb.ClimbState;

public class ClimbToStow extends ClimbToState {
    public ClimbToStow() {
        super(ClimbState.STOW);
    }
}
