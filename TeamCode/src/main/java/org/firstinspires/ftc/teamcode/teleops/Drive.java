package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.micdsrobotics.robotplus.hardware.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.SkyStoneRobot;

@TeleOp(name = "Drivetrain")
public class Drive extends OpMode {

    private SkyStoneRobot robot = new SkyStoneRobot();
    private MecanumDrive drivetrain;

    @Override
    public void init() {
        robot.initHardware(hardwareMap);
        drivetrain = robot.getDrivetrain();
    }

    @Override
    public void loop() {
        drivetrain.complexDrive(gamepad1, telemetry, gamepad1.left_bumper ? 0.5 : 1);
    }

}
