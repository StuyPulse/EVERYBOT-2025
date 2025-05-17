package com.stuypulse.robot.constants;

/** The class that contains controller values for all subsystems that require the use of 
 *  one or multiple controllers such as Feed Forward (FF) and Proportional-Integral-Derivative (PID)
 */
public class Gains {
    public interface Climb {
        public interface FF {
            double kS = 0.0;
            double kV = 0.0;
            double kA = 0.0;
            double kG = 0.0;
        }

        public interface PID {
            double kP = 0.0;
            double kI = 0.0;
            double kD = 0.0;
        }
    }

    public interface Pivot {
         public interface FF { //NO kA, kV, kS. PID + kG only.
            double kG = 0.21;
        }
        public interface PID {
            double kP = 0.08;
            double kI = 0.01;
            double kD = 0.0;
        }       
    }

    public interface Drivetrain {
        public interface FF {
            double kS = 0.18997;
            double kV = 3.3276;
            double kA = 0.70584;
        }
        public interface PID {
            public interface left {
                double kP = 0.016975;
                double kI = 0.0;
                double kD = 0.0;
            }
            public interface right {
                double kP = 0.016975;
                double kI = 0.0;
                double kD = 0.0;
            }
        }
    }
}
    