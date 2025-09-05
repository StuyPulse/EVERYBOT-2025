// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.stuypulse.robot.util.alignment;

import com.pathplanner.lib.path.PathPlannerPath;
import com.stuypulse.robot.constants.Paths;
import com.stuypulse.robot.util.Elastic;
import com.stuypulse.robot.util.Elastic.Notification;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Add your docs here. */
public class AlignmentPathLoader {
    public static void loadAlignmentpaths() {
		try {
			final PathPlannerPath AlignmentPathA = PathPlannerPath.fromPathFile("A Drive");
			final PathPlannerPath AlignmentPathB = PathPlannerPath.fromPathFile("B Drive");
			final PathPlannerPath AlignmentPathC = PathPlannerPath.fromPathFile("C Drive");
			final PathPlannerPath AlignmentPathD = PathPlannerPath.fromPathFile("D Drive");
			final PathPlannerPath AlignmentPathE = PathPlannerPath.fromPathFile("E Drive");
			final PathPlannerPath AlignmentPathF = PathPlannerPath.fromPathFile("F Drive");
			final PathPlannerPath AlignmentPathG = PathPlannerPath.fromPathFile("G Drive");
			final PathPlannerPath AlignmentPathH = PathPlannerPath.fromPathFile("H Drive");
			final PathPlannerPath AlignmentPathI = PathPlannerPath.fromPathFile("I Drive");
			final PathPlannerPath AlignmentPathJ = PathPlannerPath.fromPathFile("J Drive");
			final PathPlannerPath AlignmentPathK = PathPlannerPath.fromPathFile("K Drive");
			final PathPlannerPath AlignmentPathL = PathPlannerPath.fromPathFile("L Drive");

			Paths.loadPath("A", AlignmentPathA);
			Paths.loadPath("B", AlignmentPathB);
			Paths.loadPath("C", AlignmentPathC);
			Paths.loadPath("D", AlignmentPathD);
			Paths.loadPath("E", AlignmentPathE);
			Paths.loadPath("F", AlignmentPathF);
			Paths.loadPath("G", AlignmentPathG);
			Paths.loadPath("H", AlignmentPathH);
			Paths.loadPath("I", AlignmentPathI);
			Paths.loadPath("J", AlignmentPathJ);
			Paths.loadPath("K", AlignmentPathK);
			Paths.loadPath("L", AlignmentPathL);
		} catch (Exception e) {
			SmartDashboard.putString("CANNOT LOAD ALIGNMENT PATHS", e.toString());

			Notification errorNotif = new Notification(Elastic.Notification.NotificationLevel.ERROR,
					"CANNOT LOAD ALIGNMENT PATHS", e.toString());
			
			errorNotif.setDisplayTimeSeconds(30);

			Elastic.sendNotification(errorNotif);
		}
	}
}
