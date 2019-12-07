package org.firstinspires.ftc.teamcode;

/* BASICALLY: USE CAMERA TO FIND POSITION AND REPORT IT FOR USE IN OTHER THINGS
Shamelessly borrowed and improved upon from FTC examples. In theory, it might work.
*/

//SUCCESS!

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
import static org.firstinspires.ftc.teamcode.Constants.mmPerInch;
import static java.lang.Math.*;

/* see skystone/doc/tutorial/FTC_FieldCoordinateSystemDefinition.pdf */

/* 
 * This file uses vuforia to move places.
 *
 * @author Owen Peterson
 */
@Autonomous(name="Vuforia Test")
public class VuforiaSkyStoneNavigation extends LinearOpMode {

    // IMPORTANT:  For Phone Camera, set 1) the camera source and 2) the orientation, based on how your phone is mounted:
    // 1) Camera Source.  Valid choices are:  BACK (behind screen) or FRONT (selfie side)
    // 2) Phone Orientation. Choices are: PHONE_IS_PORTRAIT = true (portrait) or PHONE_IS_PORTRAIT = false (landscape)
    //
    // NOTE: If you are running on a CONTROL HUB, with only one USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    //

    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    public void initiate() { //this is bad and should be done in hardware, same with above defining of dcmotor

        //Initializes motor
        this.leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        this.leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        this.rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        this.rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");

        //Sets motor direction
        this.leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        this.leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        this.rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        this.rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    private HardwareSPQR robot = new HardwareSPQR();

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;

    private static final String VUFORIA_KEY =
            "AWtXaxz/////AAABmRR0jgdlWk2FthkZ9SvkJ8xNzumIjMaBRLmAXai+mjVdcWIftTV1og2Xbg51XvRrhlChUqboMX6KQrV3r+myUDmbmPrdOpdHETrcgLAXQbKvPBHSHXFn5kOVhAwKJYaXjWpGe/XzIKLZ9bIDVpdKBw01+Kf49X0YzY1y+lBtFAlSqe4AntJfG/j9PDK+OMNieRKUnoreXdf1EG2EYjebeLOww935ME3RP8N9O7STAwNcs/I00TexOjgfIPACWX14r3OVY3Cij1LXMT2RP+LtzizsM6UdMYAZwWukw6YQ3Toni9aC//gjHwehLLlzsgkoxDaVW2G5VrER/8Sm0pC9wdfgTUq6bMrWsZRvRyud8Rsk";

    private static final double hexaBotSpeed = -0.15;
    private static final double spqrBotSpeed = 0.5;
    private static final double speed = hexaBotSpeed;
    double powerMultiplier;
    private String robotActivity;

    //Define constants for conversions
    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;
    
    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField  = 36 * mmPerInch;

    //Constants for autonomous
    private static final double mmTolerance = 100;
    private static final double radianTolerance = (Math.PI/180);
    private static final double angleTolerance = 7;

    // Class Members
    public OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;
    
    private double TargetXmm = 0;
    private double TargetYmm = 0;
    private double TargetZmm = 0;
    double[] targetCoordsmm = new double[]{TargetXmm, TargetYmm ,TargetZmm};
    double xDistance = 0;
    double yDistance = 0;
    double distance = 0;

double desiredAngle = 0;
double angleVariance = 0;

    List<VuforiaTrackable> allTrackables;

    public void setHeading (double heading, double tolerance){ //called in gotoVuforiaPosistion, it in theory turns the robot onto the desired heading.
        updateLastLocation ();
        powerMultiplier = 1.1;
        double powerMultiplierModifier = -0.05;
        while(checkVuforiaPosistion("angle", getHeading(TargetXmm, TargetYmm, TargetZmm), 0, 0, tolerance) && opModeIsActive()){
            robotActivity = "Turning";
            howAngle(getHeading(TargetXmm, TargetYmm, TargetZmm));
            powerMultiplier += powerMultiplierModifier;
            if (powerMultiplier <= 0.5 || powerMultiplier >= 1.1){
                powerMultiplierModifier = -powerMultiplierModifier/1.5;
            }
            updateLastLocation();
            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            if ((rotation.thirdAngle - heading) > 0){ //this is to make the turn direction the fastest, may not be functional
                this.leftFrontDrive.setPower(-speed);
                this.leftBackDrive.setPower(-speed);
                this.rightFrontDrive.setPower(speed);
                this.rightBackDrive.setPower(speed);
                /*this.robot.leftFrontDrive.setPower(-speed);
                this.robot.leftBackDrive.setPower(-speed);
                this.robot.rightFrontDrive.setPower(speed);
                this.robot.rightBackDrive.setPower(speed);*/
            } else {
                this.leftFrontDrive.setPower(speed);
                this.leftBackDrive.setPower(speed);
                this.rightFrontDrive.setPower(-speed);
                this.rightBackDrive.setPower(-speed);
                /*this.robot.leftFrontDrive.setPower(speed);
                this.robot.leftBackDrive.setPower(speed);
                this.robot.rightFrontDrive.setPower(-speed);
                this.robot.rightBackDrive.setPower(-speed);*/
            }
        }
    }
    
    public void goForward (double targetX, double targetY, double targetZ) { //called in gotoVuforiaPosistion, it in theory moves the robot forward until it hits the desired posistion.
        if (checkVuforiaPosistion ("position", targetX, targetY, targetZ, mmTolerance) && opModeIsActive()) {
            robotActivity = "Driving Forward";
            this.leftFrontDrive.setPower(-speed*0.5);
            this.leftBackDrive.setPower(-speed*0.5);
            this.rightFrontDrive.setPower(-speed*0.5);
            this.rightBackDrive.setPower(-speed*0.5);
            /*this.robot.setPowers(-speed);*/
            howClose(targetX, targetY, targetZ);
        }
    }
    
    public boolean checkVuforiaPosistion (String type, double TargetAngleorX, double TargetY, double TargetZ, double tolerance) {
        type = type.toLowerCase(Locale.ENGLISH);
        updateLastLocation();
        VectorF translation = lastLocation.getTranslation();
        Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);

        //splitting the checks into bite size pieces in order to evaluate what is going wrong.

        double anglePlusTolerance = TargetAngleorX + tolerance;
        double angleMinusTolerance = TargetAngleorX - tolerance;

        boolean angleOverTolerance = anglePlusTolerance < rotation.thirdAngle;
        boolean angleUnderTolerance = angleMinusTolerance > rotation.thirdAngle;

        boolean returnAngleB = angleOverTolerance || angleUnderTolerance;

        double xPlusTolerance = TargetAngleorX + tolerance;
        double xMinusTolerance = TargetAngleorX - tolerance;
        double yPlusTolerance = TargetY + tolerance;
        double yMinusTolerance = TargetY - tolerance;

        boolean xOverTolerance = xPlusTolerance < translation.get(0);
        boolean xUnderTolerance = xMinusTolerance > translation.get(0);
        boolean yOverTolerance = yPlusTolerance < translation.get(1);
        boolean yUnderTolerance = yMinusTolerance > translation.get(1);

        boolean returnCoordsB = xOverTolerance || xUnderTolerance || yOverTolerance || yUnderTolerance;

        boolean returnBoolean;
        if (type == "angle"){
                    returnBoolean = (returnAngleB);
                try {
                    TimeUnit.SECONDS.sleep(0);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
        } else {
            returnBoolean = (returnCoordsB);
        }
        return returnBoolean;
    }

    public double getHeading (double targetX, double targetY, double targetZ){
        updateLastLocation();
        VectorF translation = lastLocation.getTranslation();
        double xLength = (targetX - translation.get(0)); //delta x
        double yLength = (targetY - translation.get(1)); //delta y
        double returnAngle = toDegrees(atan2(yLength, xLength));
        updateLastLocation();
        return returnAngle;
    }

    public void gotoVuforiaPosistion(double TargetX, double TargetY, double TargetZ, double endAngle){ //Set a posistion and travel to it. Requires constant Vuforia updates, unsure if this has that (needs testing). This does not allow actions to be taken inside of this
        TargetXmm = TargetX * mmPerInch;
        TargetYmm = TargetY * mmPerInch;
        TargetZmm = TargetZ * mmPerInch;
        targetCoordsmm = new double[]{TargetXmm, TargetYmm, TargetZmm};
        updateLastLocation ();
        robotActivity = "Checking if not in area";
        while (checkVuforiaPosistion("position", TargetXmm, TargetYmm, TargetZmm,mmTolerance) && opModeIsActive()){ //see if already within target area, if is, then stop
            desiredAngle = getHeading(TargetXmm, TargetYmm, TargetZmm);
            robotActivity = "Checking if oriented correctly, already checked and found it was not in the right place";
            if (checkVuforiaPosistion("angle", desiredAngle, 0, 0, angleTolerance)){ //see if orientation is facing desired point from current position CURRENT CODE IS TRASH (now it might not be)
                setHeading(getHeading(TargetXmm, TargetYmm, TargetZmm), angleTolerance);
            }
            goForward (TargetXmm, TargetYmm, TargetZmm); //go straight until in area (not very good but is what we have for now)
        }
        boolean endAngleCheck = 180 >= endAngle && endAngle >= -180;
        double reducedAngleTolerance = angleTolerance/1.5;
        desiredAngle = endAngle;
        while(endAngleCheck && checkVuforiaPosistion("angle", endAngle, 0, 0, angleTolerance) && opModeIsActive()) {
            setHeading(endAngle, angleTolerance);
        }
        this.leftFrontDrive.setPower(0);
        this.leftBackDrive.setPower(0);
        this.rightFrontDrive.setPower(0);
        this.rightBackDrive.setPower(0);
        /*this.robot.setPowers(0);*/
        robotActivity = "Robot is in the desired posistion, yay! ;)";
        try {
        TimeUnit.SECONDS.sleep(0);
        } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
        }
    }
    
    public void updateLastLocation () {
        for (VuforiaTrackable trackable : allTrackables) {
            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                telemetry.addData("Visible Target", trackable.getName());
                targetVisible = true;
                // getUpdatedRobotLocation() will return null if no new information is available since
                // the last time that call was made, or if the trackable is not currently visible.
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
                break;
            } else {
                targetVisible = false;
            }
        }
        updateVuforiaTelemetry ();
    }
    
    public void updateVuforiaTelemetry (){
        // Provide feedback as to where the robot is located (if we know).
        telemetry.addData("Target is visible", targetVisible);
        if (targetVisible) {
            // express position (translation) of robot in inches.
            VectorF translation = lastLocation.getTranslation();
            telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

            // express the rotation of the robot in degrees.
            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            telemetry.addData("Attitude of robot (deg), target/variance", "{Target/Variance} = %.0f, %.0f", desiredAngle, angleVariance);
            telemetry.addData("Distance from target in (mm) X/Y/Direct", "{X, Y, Direct} = %.0f, %.0f, %.0f", xDistance, yDistance, distance);
            telemetry.addData("Robot is...", robotActivity);
            telemetry.addData("Targeting (in)", "{X, Y, Z} = %.0f, %.0f, %.0f", targetCoordsmm[0], targetCoordsmm[1], targetCoordsmm[2]);
        }
        else {
            telemetry.addData("Visible Target", "none");
        }
        telemetry.update();
    }

    public void howClose (double targetX, double targetY, double targetZ){
        updateLastLocation();
        VectorF translation = lastLocation.getTranslation();

        double deltaX = abs((targetX - translation.get(0)));
        double deltaY = abs((targetY - translation.get(1)));

        double deltaX2 = pow(deltaX, 2);
        double deltaY2 = pow(deltaY, 2);

        double crowFlies = sqrt((deltaX2 + deltaY2));

        xDistance = deltaX;
        yDistance = deltaY;
        distance = crowFlies;
        updateLastLocation();
    }

public void howAngle (double targetAngle) {

updateLastLocation();
Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
double variance = abs((rotation.thirdAngle - targetAngle));

angleVariance = variance;

updateLastLocation();
}

    public void initializeVuforia () {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = CAMERA_CHOICE;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));
        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT  = 6.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 4.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }
    }

    private Boolean isInitialized = false;

    @Override
    public void runOpMode() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */

        if (!isInitialized){
            isInitialized = true;
            initiate();
            /*this.robot.init(hardwareMap);*/
//            this.robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = CAMERA_CHOICE;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));
        
        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT  = 1.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 4.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        // WARNING:
        // In this sample, we do not wait for PLAY to be pressed.  Target Tracking is started immediately when INIT is pressed.
        // This sequence is used to enable the new remote DS Camera Preview feature to be used with this sample.
        // CONSEQUENTLY do not put any driving commands in this loop.
        // To restore the normal opmode structure, just un-comment the following line:

        waitForStart();

        // Note: To use the remote camera preview:
        // AFTER you hit Init on the Driver Station, use the "options menu" to select "Camera Stream"
        // Tap the preview window to receive a fresh image.
        targetsSkyStone.activate();
        while (!isStopRequested() && opModeIsActive()) {

            // check all the trackable targets to see which one (if any) is visible.
            updateLastLocation ();// Provide feedback as to where the robot is located (if we know).
            if (targetVisible) {
                gotoVuforiaPosistion(-7, 0, 0, 0);
            }
        }

        // Disable Tracking when we are done;
        targetsSkyStone.deactivate();
    }
}