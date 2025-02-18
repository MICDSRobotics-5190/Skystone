package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.micdsrobotics.robotplus.hardware.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.Lift;
import org.firstinspires.ftc.teamcode.components.Plow;
import org.firstinspires.ftc.teamcode.components.SkyStoneRobot;

@TeleOp(name = "Drivetrain + Lift + Grabber")
public class DriveLiftGrab extends OpMode {

    private SkyStoneRobot robot = new SkyStoneRobot();
    private MecanumDrive drivetrain;
    private Lift lift;
    private Grabber grabber;

    @Override
    public void init() {
        robot.initHardware(hardwareMap);
        drivetrain = robot.getDrivetrain();
        lift = new Lift(hardwareMap);
        grabber = new Grabber(hardwareMap);
        grabber.close();
    }

    @Override
    public void loop() {
        drivetrain.complexDrive(gamepad1, telemetry, gamepad1.left_bumper ? 0.5 : 1);

        if (gamepad2.a) {
            lift.raise();
        } else if (gamepad2.b) {
            lift.lower();
        } else {
            lift.stop();
        }

        if (gamepad2.x) {
            grabber.clamp();
        } else if (gamepad2.y) {
            grabber.extend();
        }
    }

}
