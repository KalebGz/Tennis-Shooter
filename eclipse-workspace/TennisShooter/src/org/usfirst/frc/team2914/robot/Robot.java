package org.usfirst.frc.team2914.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
    private RobotDrive drive = new RobotDrive(0,1);

    private  Talon shooterA = new  Talon(2);
    private  Talon shooterB = new  Talon(3);
    private  Talon shooterX = new  Talon(4);
    private  Talon shooterY = new  Talon(5);

    
    private Solenoid feeder = new Solenoid(0); //pneumatics

	
	@Override
	public void robotInit() {
	}
	Joystick driveStick = new Joystick(0);
	Joystick shootStick = new Joystick(1);

	Timer timer = new Timer();
	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {


		// Drive for 2 seconds
		if (timer.get() < 4.0) {
            drive.arcadeDrive(0.4, 0);
		} else {
            drive.arcadeDrive(0, 0);
		}
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	private boolean feed = false;

	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	
	
	/* shooterX: makes shooter turn ( Movement on the X- axis)
	 * shooterY: changes shooters' angle ( //	//    Y- 	//)
	 * shooterA: Left shooter wheel
	 * shooterB: right shooter wheel
	 */
	
	private int isInverted = -1;
	private double  slowDown = .8;

	@Override
	public void teleopPeriodic() {

        double driveY = slowDown * isInverted*driveStick .getY() ;
        double twist =   slowDown * driveStick .getRawAxis(2) ;
        drive.arcadeDrive(driveY , twist);
        
        
        // tennis ball feeder 
        if (shootStick .getRawButton(2)) {
            feed=true;
        } else {
            feed=false;
        }
        
        	// Pneumatics operator
        if (feed){   
            feeder.set(true);
        } else {
            feeder.set(false);
        }
        
        // tennis ball shooter
        if (shootStick .getRawButton(1)) {
            shooterA.set(-1);
            shooterB.set(1);
        } else {
        	shooterA.set(0);
            shooterB.set(0);

        }
        
       
        double xMove = -.2* shootStick .getRawAxis(2) ;
        double yMove = .3 *shootStick .getY() ;

        // motors for shooter
        shooterX.set(xMove);
        shooterY.set(yMove);

        
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
