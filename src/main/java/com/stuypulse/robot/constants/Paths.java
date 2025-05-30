package com.stuypulse.robot.constants;

import java.util.Map;

import com.pathplanner.lib.path.PathPlannerPath;

public class Paths {
    public static Map<String, PathPlannerPath> paths;

    public static void loadPath(String ID, PathPlannerPath path) {
        paths.put(ID, path);
    }
}
