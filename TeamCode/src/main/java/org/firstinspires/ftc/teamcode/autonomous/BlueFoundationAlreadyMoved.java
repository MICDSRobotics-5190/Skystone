package org.firstinspires.ftc.teamcode.autonomous;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.micdsrobotics.robotplus.autonomous.TimeOffsetVoltage;
import org.firstinspires.ftc.micdsrobotics.robotplus.hardware.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.FoundationHook;
import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.Lift;
import org.firstinspires.ftc.teamcode.components.Plow;
import org.firstinspires.ftc.teamcode.components.SkyStoneRobot;

@Autonomous(name = "Blue - Foundation already moved")
public class BlueFoundationAlreadyMoved extends LinearOpMode {

    private SkyStoneRobot robot = new SkyStoneRobot();
    private MecanumDrive drivetrain;
    private Lift lift;
    private Grabber grabber;
    private Plow plow;
    private FoundationHook foundationHook;

    @Override
    public void runOpMode(){
        robot.initHardware(hardwareMap);
        drivetrain = robot.getDrivetrain();
        lift = new Lift(hardwareMap);
        grabber = new Grabber(hardwareMap);
        grabber.close();
        plow = new Plow(hardwareMap);
        foundationHook = new FoundationHook(hardwareMap);

        waitForStart();

        //drive forward
        lift.raise();
        sleep(400);
        lift.stop();
        plow.raise();
        sleep(400);
        plow.stop();
        moveDistanceCm(MecanumDrive.Direction.UP,68);
        moveSlowDistance(MecanumDrive.Direction.UP, 15, 0.4);
        sleep(100);

        //grab block
        grabber.extend();
        sleep(2000);
        lift.lower();
        sleep(600);
        lift.stop();
        grabber.clamp();
        sleep(500);

        //drive under skybridge to foundation
        //moveDistanceCm(MecanumDrive.Direction.DOWN, 10);
        //grabber.clamp();
        //sleep(300);
        moveDistanceCm(MecanumDrive.Direction.DOWN,25);
        drivetrain.complexDrive(0,0,1);
        sleepDistance(24);
        //drivetrain.complexDrive(0,0,1);
        //sleepDistance(32.5);
        moveDistanceCm(MecanumDrive.Direction.LEFT, 220);
        //drivetrain.complexDrive(0,0,-1);
        //sleepDistance(32.5);

        //place block on foundation
        lift.raise();
        sleep(200);
        lift.stop();
        plow.lower();
        sleep(400);
        plow.stop();
        sleep(200);
        lift.lower();
        sleep(200);
        lift.stop();
        grabber.extend();
        sleep(200);
        lift.raise();
        sleep(500);
        lift.stop();

        //drive to under the bridge
        moveDistanceCm(MecanumDrive.Direction.DOWN, 60);
    }

    private void sleepDistance(double distance) {
        double voltage = hardwareMap.voltageSensor.get("Expansion Hub 2").getVoltage();
        long sleepTime = TimeOffsetVoltage.calculateDistance(robot, voltage, distance);
        Log.d("OpMode", "sleep time: " + sleepTime);
        sleep(sleepTime);
        robot.stopMoving();
    }

    private void moveDistanceCm(MecanumDrive.Direction direction, double distance) {
        if (distance <= 0) return;
        drivetrain.complexDrive(direction.angle(), 1, 0);
        sleepDistance(distance);
    }

    private void moveSlowDistance(MecanumDrive.Direction direction, double distance, double velocity) {
        if (distance <= 0) return;
        drivetrain.complexDrive(direction.angle(), velocity, 0);
        sleepDistance(distance);
    }

}
