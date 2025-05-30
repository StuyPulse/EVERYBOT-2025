package com.stuypulse.robot.commands.drive.Alignment;

import com.stuypulse.robot.constants.Paths;

public class AlignToReefAB extends AlignToReef {
    public AlignToReefAB() {
        super(Paths.paths.get("AB"));
    }
}