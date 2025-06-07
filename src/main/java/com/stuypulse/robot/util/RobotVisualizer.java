package com.stuypulse.robot.util;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.pivot.Pivot;
import com.stuypulse.robot.subsystems.pivot.Pivot.PivotControlMode;

public class RobotVisualizer {
    
    public static RobotVisualizer instance;

    static {
        instance = new RobotVisualizer();
    }

    public static RobotVisualizer getInstance() {
        return instance;
    }

    private final Mechanism2d canvas;
    private double width, height;

    /* BUMPER AND STRUCTURE */
    private final MechanismRoot2d bumper;
    private final MechanismLigament2d bumperLine;
    private final MechanismRoot2d superStructureBase;
    private final MechanismLigament2d superStructureLength;

    /* PIVOT */
    private final MechanismLigament2d pivot;
    private final MechanismRoot2d pivotVertex;
    private final MechanismRoot2d pivotSupportEnd;
    private final MechanismLigament2d pivotSupport;

    private double pivotAngle;

    private final MechanismLigament2d[] rollerLigaments;

    private double rollerSpeed;

    private final MechanismRoot2d cageRoot;
    private final MechanismLigament2d cageBottom;

    /* CLIMB */
    private double climbAngle;

    private final MechanismRoot2d climbBaseRoot;
    private final MechanismLigament2d climbBase;

    private final MechanismRoot2d climbSupportRoot;
    private final MechanismLigament2d climbSupport;

    private final MechanismRoot2d climbRoot;
    private final MechanismLigament2d climber;
    private final MechanismLigament2d climbHooks;
    private final MechanismLigament2d climbStringGripper;

    private RobotVisualizer() {
        width = 150;
        height = 100;

        canvas = new Mechanism2d(width, height, new Color8Bit(Color.kSlateGray));

        /* Bumper */
        bumper = canvas.getRoot("z - Bumper", 60, 10);

        bumperLine = new MechanismLigament2d(
            "z - Bumper Line",
            80,
            0,
            75,
            new Color8Bit(Color.kBlue)
        );

        /* Pivot and SuperStructure */
        superStructureBase = canvas.getRoot("b - Super Structure Base", 80, 10);
        
        superStructureLength = new MechanismLigament2d(
            "b - Super Structure",
            65,
            90,
            15,
            new Color8Bit(Color.kLightGray)
        );

        pivotAngle = 90+Settings.Pivot.DEFAULT_ANGLE.getDegrees();
        
        pivotVertex = canvas.getRoot("a - Pivot Rotation Vertex", 70, 25);
        
        pivot = new MechanismLigament2d(
            "a - Pivot",
            40,
            pivotAngle,
            15,
            new Color8Bit(Color.kGreen)
        );

        pivotSupportEnd = canvas.getRoot("d - Pivot Support Vertex", 68, 24);
        
        pivotSupport = new MechanismLigament2d(
            "d - Pivot Support",
            15,
            0,
            30,
            new Color8Bit(Color.kDarkGray)
        );


        /* Rollers */
        rollerLigaments = new MechanismLigament2d[4];
        for(int i = 0; i < 4; i++){
            rollerLigaments[i] = new MechanismLigament2d(
                "z - Roller " + (i+1), 
                4, 
                90 * i,
                8,
                new Color8Bit(Color.kBlack)
            );
            pivot.append(rollerLigaments[i]);
        }

        /* Coral Cage */
        cageRoot = canvas.getRoot("a - Cage Root", 80, 65);
        cageBottom = new MechanismLigament2d(
            "a - Cage Bottom",
            10,
            180,
            10,
            new Color8Bit(Color.kDimGray)
        );

        /* Climb */
        climbBaseRoot = canvas.getRoot("a - Climb Base Root", 132, 10);
        climbBase = new MechanismLigament2d(
            "a - Climb Base",
            20,
            90,
            15,
            new Color8Bit(Color.kLightGray)
        );

        climbSupportRoot = canvas.getRoot("z - Climb Holder Thing Root", 134, 25);
        climbSupport = new MechanismLigament2d(
            "z - Climb Holder Thing",
            7,
            180,
            20,
            new Color8Bit(Color.kLightGray)
        );

        climbAngle = Settings.Climb.DEFAULT_ANGLE.getDegrees();
        climbRoot = canvas.getRoot("a - Climb Root", 128, 25);
        climber = new MechanismLigament2d(
            "a - Climber",
            20,
            climbAngle,
            15,
            new Color8Bit(Color.kGray)
        );

        climbHooks = new MechanismLigament2d(
            "b - Climb Latching Hooks",
            6,
            270,
            17,
            new Color8Bit(Color.kGreen)
        );
        climbStringGripper = new MechanismLigament2d(
            "b - Climb Amsteel Gripper",
            8,
            90,
            10,
            new Color8Bit(Color.kGreen)
        );
        

        /* Appending Stuff */
        bumper.append(bumperLine);
        superStructureBase.append(superStructureLength);
        pivotVertex.append(pivot);
        pivotSupportEnd.append(pivotSupport);
        cageRoot.append(cageBottom);
        climbBaseRoot.append(climbBase);
        climbSupportRoot.append(climbSupport);
        climbRoot.append(climber);
        climber.append(climbHooks);
        climber.append(climbStringGripper);
        
    }

    public void updatePivotAngle(Rotation2d angle, boolean atTargetAngle) {
        this.pivotAngle = 90+angle.getDegrees();
        pivot.setAngle(pivotAngle); 
        if(Pivot.getInstance().PivotControlMode() == PivotControlMode.MANUAL){
            pivot.setColor(new Color8Bit(Color.kViolet));
        } else if(Pivot.getInstance().PivotControlMode() == PivotControlMode.USING_STATES) {
            pivot.setColor(atTargetAngle ? new Color8Bit(Color.kGreen) : new Color8Bit(Color.kRed));
        }

        if(Settings.DEBUG_MODE) {
            SmartDashboard.putData("Visualizers/Robot", canvas);
        }
    }

    public void updateRollers(double speed) {
        this.rollerSpeed = speed;
        for(int i = 0; i < 4; i++) {
            rollerLigaments[i].setAngle(rollerLigaments[i].getAngle() + (-rollerSpeed) * 20);
        }
        
        if(Settings.DEBUG_MODE) {
            SmartDashboard.putData("Visualizers/Robot", canvas);
        }
    }

    public void updateClimb(Rotation2d angle, boolean atTargetAngle) {
        this.climbAngle = angle.getDegrees() + 90;
        climber.setAngle(this.climbAngle);
        climbHooks.setColor(atTargetAngle ? new Color8Bit(Color.kGreen) : new Color8Bit(Color.kRed));
        climbStringGripper.setColor(atTargetAngle ? new Color8Bit(Color.kGreen) : new Color8Bit(Color.kRed));

        if(Settings.DEBUG_MODE) {
            SmartDashboard.putData("Visualizers/Robot", canvas);
        }
    }
}
