package com.stuypulse.robot.constants;

import java.util.HashMap;

import com.pathplanner.lib.path.PathPlannerPath;

public class Paths {
    public static HashMap<String, PathPlannerPath> paths = new HashMap<String, PathPlannerPath>();

    public static void loadPath(String ID, PathPlannerPath path) {
        paths.put(ID, path);
    }
}
