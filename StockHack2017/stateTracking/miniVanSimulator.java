/* Robert Leslie
 * StockHack 2017
 * State Tracking Challenge
 */
package stateTracking;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class miniVanSimulator extends Application{
	
	double gear = 0;
	Boolean childLockOn = new Boolean(false); // Child Lock
	Boolean masterLockOn = new Boolean(false); // Master Lock
	Boolean leftDoorOpen = new Boolean(false); // Left door
	Boolean rightDoorOpen = new Boolean(false); // Right door
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO
		/* 		-Border Pane
		 * Left Pane: FOUR buttons for left sliding door 	|	INSIDE: DONE / OUTSIDE: DONE
		 * Right Pane: FOUR buttons for right sliding door	|	INSIDE: DONE / OUTSIDE: DONE
		 * Bottom Pane: Child Lock							|	DONE
		 * Bottom Pane: Master Lock							|	DONE
		 * Center Pane: Gear Shift 							|	DONE
		 */
	
		BorderPane carDash = new BorderPane();
			
	/* Gear Shift */
		Slider gearShift = new Slider();
		gearShift.setMax(6); // Number of gears
		gearShift.setMin(0);
		gearShift.setValue(0);
		gearShift.setMajorTickUnit(1);
		gearShift.setMinorTickCount(0);
		gearShift.setOrientation(Orientation.VERTICAL);
		gearShift.setShowTickLabels(true);
		gearShift.setShowTickMarks(true);
		gearShift.setSnapToTicks(true);
		gearShift.setMinHeight(250);

		// Gear Labels
		gearShift.setLabelFormatter(new StringConverter<Double>(){
			public String toString(Double n){
				if(n==0) return "Park";
				if(n==1) return "Neutral";
				if(n==2) return "Drive";
				if(n==3) return "1st Gear";
				if(n==4) return "2nd Gear";
				if(n==5) return "3rd Gear";
				if(n==6) return "Reverse";
				return "Park";
			}
			
			public Double fromString(String s){
				switch(s){
				case "Park": return 0d;
				case "Neutral": return 1d;
				case "Drive": return 2d;
				case "1st Gear": return 3d;
				case "2nd Gear": return 4d;
				case "3rd Gear": return 5d;
				case "Reverse": return 6d;
				default: return 0d;
				}
			}
			
			/* Gears:
			 * 	P = 0
			 * 	N = 1
			 * 	D = 2
			 * 	1 = 3
			 * 	2 = 4
			 * 	3 = 5
			 * 	R = 6
			 * 
			 */
		});
		
		TextField gearStatus = new TextField();
		gearStatus.setEditable(false);
		gearShift.valueProperty().addListener(e ->{
			gear = gearShift.getValue();
			gearStatus.setText(gearShiftToString(gear));
		});
		
		GridPane gearBox = new GridPane();
		gearBox.add(gearShift,0,0);
		gearBox.add(gearStatus, 0, 1);
		carDash.setCenter(gearBox);
	
	/* THE LOCKS */
		GridPane lockBox = new GridPane();
		
		// Master Lock
		Button btMasterLockOn = new Button("ON");
		Button btMasterLockOff = new Button("OFF");
		Label masterLockLabel = new Label("Master Lock");
		btMasterLockOn.setOnAction(e->{
			masterLockOn=true;
			System.out.println("Master Lock: "+masterLockOn);
		});
		btMasterLockOff.setOnAction(e->{
			masterLockOn=false;
			System.out.println("Master Lock: "+masterLockOn);
		});
		lockBox.add(masterLockLabel, 0,0);
		lockBox.add(btMasterLockOn, 1, 0);
		lockBox.add(btMasterLockOff, 2, 0);
		
		// Child Lock
		Button btChildLockOn = new Button("ON");
		Button btChildLockOff = new Button("OFF");
		Label childLockLabel = new Label("Child Lock");
		btChildLockOn.setOnAction(e ->{
			childLockOn=true;
			System.out.println("Child Lock: "+childLockOn);});
		btChildLockOff.setOnAction(e ->{
			childLockOn=false;
			System.out.println("Child Lock: "+childLockOn);});
		lockBox.add(childLockLabel, 0, 1);
		lockBox.add(btChildLockOn, 1,1);
		lockBox.add(btChildLockOff, 2, 1);
		lockBox.setAlignment(Pos.CENTER);
		lockBox.setHgap(10);
		lockBox.setVgap(10);
		
		carDash.setBottom(lockBox);
		
	/* THE DOORS */
		// Left Door
		GridPane leftDoor = new GridPane();
		Button btLeftSideOutOpen = new Button("Left, Out, Open");
		Button btLeftSideOutClose = new Button("Left, Out, Close");
		Button btLeftSideInOpen = new Button("Left, In, Open");
		Button btLeftSideInClose = new Button("Left, In, Close");
		Label leftIn = new Label("LEFT IN");
		Label leftOut = new Label("LEFT OUT");
		btLeftSideOutOpen.setOnAction(e ->{
			if(doorCheck(gear,masterLockOn,childLockOn)){
				leftDoorOpen=true;
				System.out.println("Left Door: "+leftDoorOpen);
			}else
				System.out.println("Unable to open Left door");
			
		});
		btLeftSideOutClose.setOnAction(e ->{
			leftDoorOpen=false;
			System.out.println("Left Door: "+leftDoorOpen);
		});
		btLeftSideInOpen.setOnAction(e ->{
			if(doorCheck(gear,masterLockOn,childLockOn)){
				leftDoorOpen=true;
				System.out.println("Left Door: "+leftDoorOpen);
			}else
				System.out.println("Unable to open Left door");
			
		});
		btLeftSideInClose.setOnAction(e ->{
			leftDoorOpen=false;
			System.out.println("Left Door: "+leftDoorOpen);
		});
		leftDoor.add(leftIn, 0,0);
		leftDoor.add(btLeftSideInOpen, 1, 0);
		leftDoor.add(btLeftSideInClose, 2, 0);
		leftDoor.add(leftOut, 0, 1);
		leftDoor.add(btLeftSideOutOpen, 1, 1);
		leftDoor.add(btLeftSideOutClose, 2, 1);
		leftDoor.setVgap(10);
		leftDoor.setHgap(10);
		leftDoor.setAlignment(Pos.CENTER_LEFT);
		carDash.setLeft(leftDoor);
		
		//Right Door
		GridPane rightDoor = new GridPane();
		Button btRightSideOutOpen = new Button("Right, Out, Open");
		Button btRightSideOutClose = new Button("Right, Out, Close");
		Button btRightSideInOpen = new Button("Right, In, Open");
		Button btRightSideInClose = new Button("Right, In, Close");
		Label rightIn = new Label("RIGHT IN");
		Label rightOut = new Label("RIGHT OUT");
		btRightSideOutOpen.setOnAction(e ->{
			if(doorCheck(gear,masterLockOn,childLockOn)){
				rightDoorOpen=true;
				System.out.println("Right Door: "+rightDoorOpen);
			}else
				System.out.println("Unable to open Right door");
			
		});
		btRightSideOutClose.setOnAction(e ->{
			rightDoorOpen=false;
			System.out.println("Right Door: "+rightDoorOpen);
		});
		btRightSideInOpen.setOnAction(e ->{
			if(doorCheck(gear,masterLockOn,childLockOn)){
				rightDoorOpen=true;
				System.out.println("Right Door: "+rightDoorOpen);
			}else
				System.out.println("Unable to open Right door");
			
		});
		btRightSideInOpen.setOnAction(e ->{
			rightDoorOpen=false;
			System.out.println("Right Door: "+rightDoorOpen);
		});
		rightDoor.add(rightIn, 0, 0);
		rightDoor.add(btRightSideInOpen, 1, 0);
		rightDoor.add(btRightSideInClose, 2, 0);
		rightDoor.add(rightOut, 0, 1);
		rightDoor.add(btRightSideOutOpen, 1, 1);
		rightDoor.add(btRightSideOutClose, 2, 1);
		rightDoor.setVgap(10);
		rightDoor.setHgap(10);
		rightDoor.setAlignment(Pos.CENTER_RIGHT);
		carDash.setRight(rightDoor);
		
		Scene miniVanSimulator = new Scene(carDash);
		primaryStage.setTitle("mini van");
		primaryStage.setScene(miniVanSimulator);
		primaryStage.show();
		
	/* Secondary Window as Console output */	
		TextArea consoleOutPut = new TextArea();
		consoleOutPut.setPrefWidth(800);
		consoleOutPut.setPrefHeight(600);
		consoleOutPut.setWrapText(true);
		
		Console console = new Console(consoleOutPut);
		
		PrintStream ps = new PrintStream(console, true);
		System.setOut(ps);
		System.setErr(ps);
		
		Scene consoleScene = new Scene(consoleOutPut);
		
		Stage consoleWindow = new Stage();
		consoleWindow.setScene(consoleScene);
		consoleWindow.setTitle("Console output");
		consoleWindow.show();
	}

	public Boolean inPark(double gear){
		if(gear == 0)
			return true;
		return false;
	}
	
	public String gearShiftToString(Double n){
		if(n==0) return "Park";
		if(n==1) return "Neutral";
		if(n==2) return "Drive";
		if(n==3) return "1st Gear";
		if(n==4) return "2nd Gear";
		if(n==5) return "3rd Gear";
		if(n==6) return "Reverse";
		return "*Ka Chunk*"; // It's a gear changing sound, okay?
	}
	
	public Boolean doorCheck(Double g, Boolean c, Boolean m){ // g for gear, c for child lock
		if(g==0&&c==true&&m==true)
			return true;	
		return false;
	}
	
	public static class Console extends OutputStream{
		
		private TextArea output;
		public Console(TextArea ta){
			this.output = ta;
		}
		
		@Override
		public void write(int i) throws IOException{
			output.appendText(String.valueOf((char)i));
		}
	}
	public static void main(String[] args){
		launch(args);
		System.out.println("Starting the car...");
	}
	
}
