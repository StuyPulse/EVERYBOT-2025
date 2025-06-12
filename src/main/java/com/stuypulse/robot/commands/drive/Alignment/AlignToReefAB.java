package com.stuypulse.robot.commands.drive.Alignment;

import com.stuypulse.robot.constants.Paths;
import com.stuypulse.robot.util.AlignmentTranslator;

public class AlignToReefAB extends AlignToReef {
    public AlignToReefAB(double direction) {
        super(Paths.paths.get(AlignmentTranslator.translate("AB", direction)));
    }
}