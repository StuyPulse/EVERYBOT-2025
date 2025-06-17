package com.stuypulse.robot.commands.drive.Alignment;

import com.stuypulse.robot.constants.Paths;
import com.stuypulse.robot.util.alignment.AlignmentTranslator;

public class AlignToReefAB extends AlignToReef {
    public AlignToReefAB(boolean alignToLeftBranch) {
        super(Paths.paths.get(AlignmentTranslator.translate("AB", alignToLeftBranch)));
    }
}