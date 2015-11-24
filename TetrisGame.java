
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class TetrisGame extends Application{
	
	private Button startBtn = new Button("start");
	private Button pauseBtn = new Button("pause");
	private TetrisGrid mainGrid = new TetrisGrid(22,10);
	private TetrisGrid nextGrid = new TetrisGrid(6,6);
	//private Group mainGrid = new Group();
	//private Group nextGrid = new Group();
	private TextField levelField = new TextField();
	private TextField lineField = new TextField();
	private TextField scoreField = new TextField();
	private TetrisBlockFactory tetrisBlockFactory = new TetrisBlockFactory();
	private Timeline shoftDropTimer = new Timeline();
	private EventHandler<ActionEvent> btnHandler = new ButtonHandler();
		
	private class TimerHandler implements EventHandler<ActionEvent>{
		private boolean active = true;
		@Override
		public void handle(ActionEvent event) {
			active = mainGrid.moveShapeDown();
			mainGrid.repaint();
			if(!active){
				tetrisBlockFactory.changeBlock();
				startNewBlock();
				active = true;
			}
		}
	}
	
	private class KeyboardHandler implements EventHandler<KeyEvent>{
		@Override
		public void handle(KeyEvent event) {			
			switch(event.getCode()){
			case LEFT: mainGrid.moveShapeLeft(); event.consume(); break;
			case RIGHT: mainGrid.moveShapeRight(); event.consume(); break;
			case UP: mainGrid.rotateShape(); event.consume(); break;
			case DOWN: mainGrid.moveShapeDown(); event.consume(); break;
			case SPACE: mainGrid.moveShapeToBottom(); event.consume(); break;
			}
			mainGrid.repaint();
		}
	}
	
	private class ButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			Object source = event.getSource();
			if(source==startBtn){
				startNewBlock();
				mainGrid.requestFocus();
				//shoftDropTimer.play();
			}
		}
	}
	
	private HBox createActionPane(){
		HBox actionBox = new HBox();
		actionBox.setSpacing(10);
		actionBox.setPadding(new Insets(20,0,10,0));
		actionBox.setAlignment(Pos.CENTER);
		startBtn.setMinWidth(80);
		pauseBtn.setMinWidth(80);
		startBtn.setOnAction(btnHandler);
		pauseBtn.setOnAction(btnHandler);
		actionBox.getChildren().addAll(startBtn, pauseBtn);
		return actionBox;
	}
	
	private VBox createStatePane(){
		VBox stateBox = new VBox();
		stateBox.setSpacing(10);
		stateBox.setPadding(new Insets(10,20,0,0));
		stateBox.setAlignment(Pos.TOP_CENTER);
		//Rectangle nextRec = new Rectangle(0,0,120,120);
		Label levelLabel = new Label("level");
		Label lineLabel = new Label("line");
		Label scoreLabel = new Label("score");
		levelField.setMaxWidth(120);
		lineField.setMaxWidth(120);
		scoreField.setMaxWidth(120);
		levelField.setEditable(false);
		lineField.setEditable(false);
		scoreField.setEditable(false);
		VBox.setMargin(nextGrid, new Insets(0,0,80,0));
		stateBox.getChildren().addAll(nextGrid,levelLabel,levelField,
				lineLabel,lineField,scoreLabel,scoreField);
		return stateBox;
	}
	
	private Scene createMainTetrisScene(){
		BorderPane mainPane = new BorderPane();
		
		//Rectangle mainRec = new Rectangle(0,0,200,440);
		VBox mainBox = new VBox();
		mainBox.setPadding(new Insets(10,0,0,20));
		mainBox.getChildren().add(mainGrid);
		// �궎蹂대뱶 泥섎━�옄 �벑濡�
		mainGrid.setOnKeyPressed(new KeyboardHandler());
		
		mainPane.setCenter(mainBox);
		mainPane.setTop(createActionPane());
		mainPane.setRight(createStatePane());
		return new Scene(mainPane, 380, 540);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {		
		shoftDropTimer.setCycleCount(Animation.INDEFINITE);
		shoftDropTimer.getKeyFrames().add(new KeyFrame(Duration.millis(500),new TimerHandler()));
		shoftDropTimer.setDelay(Duration.millis(500));
		
		primaryStage.setTitle("Java Tetris");
		primaryStage.setScene(createMainTetrisScene());
		primaryStage.show();
	}
	
	public void startNewBlock(){
		TetrisBlockShape currShape = tetrisBlockFactory.getCurrent();
		mainGrid.insertShape(currShape);
		mainGrid.repaint();
		
		nextGrid.clear();
		nextGrid.insertShape(tetrisBlockFactory.getNext());
		nextGrid.repaint();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
