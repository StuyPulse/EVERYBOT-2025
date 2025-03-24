import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;

public class OdometryImpl extends Odometry {
    private final Field2d field;

    private final FieldObject2d odometryPose2d;
    private final FieldObject2d poseEstimatorPose2d;

    protected OdometryImpl() {
        
    }
}