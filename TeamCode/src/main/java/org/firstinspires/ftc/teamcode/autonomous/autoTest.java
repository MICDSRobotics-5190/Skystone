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

@Autonomous(name = "autoTest")
public class autoTest extends LinearOpMode {

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
        plow.lower();
        sleep(125);
        moveDistanceCm(MecanumDrive.Direction.UP,60);

        //grab block
        lift.raise();
        sleep(500);
        lift.stop();
        moveDistanceCm(MecanumDrive.Direction.UP, 10);
        lift.lower();
        sleep(500);
        grabber.clamp();

        //drive under skybridge to foundation
        moveDistanceCm(MecanumDrive.Direction.DOWN, 40);
        drivetrain.complexDrive(0,0,1);
        sleepDistance(132);
        moveDistanceCm(MecanumDrive.Direction.UP, 183);
        drivetrain.complexDrive(0,0,-1);
        sleepDistance(132);

        //place block on foundation
        lift.raise();
        sleep(200);
        plow.raise();
        sleep(200);
        moveDistanceCm(MecanumDrive.Direction.UP, 40);
        lift.lower();
        sleep(200);
        grabber.extend();
        lift.raise();
        sleep(500);

        //latch onto foundation
        moveDistanceCm(MecanumDrive.Direction.DOWN, 20);
        moveDistanceCm(MecanumDrive.Direction.LEFT, 50);
        drivetrain.complexDrive(0,0,1);
        sleepDistance(264);
        lift.lower();
        sleep(500);
        moveDistanceCm(MecanumDrive.Direction.DOWN, 20);
        foundationHook.lower();

        //drop off foundation at ?depot?
        moveDistanceCm(MecanumDrive.Direction.UP, 90);
        foundationHook.raise();

        //drive to under the bridge
        moveDistanceCm(MecanumDrive.Direction.LEFT, 175);


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

}