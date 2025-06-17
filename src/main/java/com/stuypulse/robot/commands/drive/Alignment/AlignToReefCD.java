package com.stuypulse.robot.commands.drive.Alignment;

import com.stuypulse.robot.constants.Paths;
import com.stuypulse.robot.util.alignment.AlignmentTranslator;

public class AlignToReefCD extends AlignToReef{
    public AlignToReefCD(boolean alignToLeftBranch) {
        super(Paths.paths.get(AlignmentTranslator.translate("CD", alignToLeftBranch)));
    }
}
