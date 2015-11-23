import javafx.scene.Group;
import javafx.scene.paint.Color;

public class TetrisGrid extends Group {
	private TetrisGridCell[][] cells;
	private int width;
	private int height;
	private Location shapeLoc;			// �쁽�옱 �뀒�듃濡쒕�몃끂�쓽 �쐞移�
	private TetrisBlockShape currShape; // �쁽�옱 �뀒�듃濡쒕�몃끂�쓽 紐⑥뼇
	
	private class Location{
		public int x;
		public int y;
		public Location(int x, int y){
			this.x = x;
			this.y = y;
		}
		public boolean isValid(){
			return (x>=0&&x<height&&y>=0&&y<width);
		}
		public void move(int x, int y)
		{
			this.x += x;
			this.y += y;
		}
		@Override
		public Location clone(){
			Location newLocation = null;
			try {
				newLocation = (Location)super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			return newLocation;
		}
	}
	
	public TetrisGrid(int height, int width) {
		resize(width*20, height*20);
		this.width = width;
		this.height = height;
		cells = new TetrisGridCell[height][width];
		for(int row=0; row<height; row++)
			for(int col=0; col<width; col++){
				cells[row][col] = new TetrisGridCell(col*20, row*20);
				getChildren().add(cells[row][col]);
			}
	}
	
	public void clear(){
		for(int row=0; row<height; row++)
			for(int col=0; col<width; col++){
				cells[row][col].setColor(Color.DARKGRAY);
			}
	}
	
	private void drawBlock(){
		byte[][] blockShape = currShape.getCurrentBlock();
		Location curr = new Location(0,0);
		for(int r=0; r<4; r++){
			curr.x = r+shapeLoc.x;
			for(int c=0; c<4; c++){
				curr.y = c+shapeLoc.y;
				if(blockShape[r][c]==1&&curr.isValid())
					cells[curr.x][curr.y].setColor(currShape.getColor());
			}
		}
	}
	
	private void eraseBlock(){
		byte[][] blockShape = currShape.getCurrentBlock();
		Location curr = new Location(0,0);
		for(int r=0; r<4; r++){
			curr.x = r+shapeLoc.x;
			for(int c=0; c<4; c++){
				curr.y = c+shapeLoc.y;
				if(blockShape[r][c]==1&&curr.isValid())
					cells[curr.x][curr.y].setColor(Color.DARKGRAY);
			}
		}
	}
	
	public void insertShape(TetrisBlockShape currShape){
		this.currShape = currShape;
		int startY = (width-4)/2;
		shapeLoc = new Location(0,startY);
		drawBlock();
	}
	
	private boolean canMove(Location newLoc, byte[][] blockShape){
		return false;
	}
	
	public boolean moveShapeDown(){
		return false;
	}
	
	public void moveShapeLeft(){
		/*
		eraseBlock();
		// �깉 醫뚰몴 �솗蹂�
		// canMove �씠�슜�븯�뿬 �깉 醫뚰몴濡� �씠�룞 媛��뒫�븳吏� 寃��궗, canMove(newLoc, currShape.getCurrentBlock())
		// �씠�룞媛��뒫�븯硫� �씠�룞
		drawBlock();
		*/
	}
	
	public void moveShapeRight(){
	}
	
	public void rotateShape(){
		/*
		eraseBlock();
		// canMove �씠�슜�븯�뿬 �깉 醫뚰몴濡� �씠�룞 媛��뒫�븳吏� 寃��궗, canMove(newLoc, currShape.getNextBlock())
		// �씠�룞 媛��뒫�븯硫� �쉶�쟾
		drawBlock();
		*/
	}
	
	public void moveShapeToBottom(){
	}
	
	public void repaint(){	
		for(int row=0; row<height; row++)
			for(int col=0; col<width; col++){
				cells[row][col].draw();
			}
	}
}
