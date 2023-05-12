package com.example.jawad2_assignment1; // Define Package

// Import Necessary Libraries
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.Random;

// Start of GameController Class
public class GameController {

    // Import Necessary Scene Items
    @FXML
    private Button resetB;
    @FXML
    private Button startB;
    @FXML
    private Slider headSizeS;
    @FXML
    private GridPane gameBoard;
    @FXML
    private Label endScreen;

    // Initialize Required Variables
    int initialHeadSize;
    Random rand = new Random();
    boolean full=true;
    int cuts = 0;
    ImageView check;

    // Getting Images from files
    Image head1 = new Image("File:HeadSize1.png", 40, 40, false, false);
    Image head2 = new Image("File:HeadSize2.png", 40, 40, false, false);
    Image head3 = new Image("File:HeadSize3.png", 40, 40, false, false);
    Image head4 = new Image("File:HeadSize4.png", 40, 40, false, false);
    Image head5 = new Image("File:HeadSize5.png", 40, 40, false, false);
    Image head6 = new Image("File:HeadSize6.png", 40, 40, false, false);

    // Changing Images to Image Views
    ImageView head1v = new ImageView(head1);
    ImageView head2v = new ImageView(head2);
    ImageView head3v = new ImageView(head3);
    ImageView head4v = new ImageView(head4);
    ImageView head5v = new ImageView(head5);
    ImageView head6v = new ImageView(head6);

    // Putting Images and ImageViews into arrays to speed up accessing them later
    ImageView[] heads1v = {head1v, head2v, head3v, head4v, head5v, head6v};
    Image[] heads1 = {head1, head2, head3, head4, head5, head6};

    // Reset Button Procedure
    @FXML
    protected void onResetButtonClick() {
        gameBoard.getChildren().clear();
        headSizeS.setValue(4);
        headSizeS.setDisable(false);
        startB.setDisable(false);
        endScreen.setText("");
        endScreen.setDisable(true);
        cuts=0;
    }

    // Start Button Procedure
    @FXML
    protected void onStartButtonClick() {
        initialHeadSize= (int) headSizeS.getValue();
        headSizeS.setDisable(true);
        startB.setDisable(true);
        int[]coord1 = locationFinder();
        gameBoard.add(getImageView(initialHeadSize),coord1[0],coord1[1]);

    }

    // Procedure activates on Hydra Click
    @FXML
    protected void onHydraClick(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();

        // Check that I clicked on Correct Node
        if (clickedNode != gameBoard) {
            // click on descendant node if not
            Node parent = clickedNode.getParent();
            // Loop to get correct Node
            while (parent != gameBoard) {
                clickedNode = parent;
                parent = clickedNode.getParent();
            }
            // Get Coordinates of Clicked Node
            Integer colIndex = gameBoard.getColumnIndex(clickedNode);
            Integer rowIndex = gameBoard.getRowIndex(clickedNode);
            // Get Clicked Node as Object
            ObservableList<Node> childrens = gameBoard.getChildren();
            for(Node node : childrens) {
                if (node instanceof ImageView && gameBoard.getRowIndex(node) == rowIndex && gameBoard.getColumnIndex(node) == colIndex) {
                    // Take node as ImageView
                    ImageView imageView = new ImageView();
                    imageView = (ImageView) node; // use what you want to remove
                    // Find Size of clicked Hydra Head
                    int size = 0;
                    for (int i = 0; i < heads1v.length; i++) {
                        if (heads1v[i].getImage() == imageView.getImage()) {
                            size = i + 1;
                        }
                    }
                    // Remove Clicked Head and add it to cuts
                    gameBoard.getChildren().remove(imageView);
                    cuts++;
                    // If Statement to spawn new Hydra Heads or check if Game over if Hydra does not grow back head
                    if (size != 1){
                        int numHeads = rand.nextInt(2)+2;
                        int[] coord1 = new int[2];
                        for (int i = 0; i < numHeads; i++) {
                            coord1 = locationFinder();
                            gameBoard.add(getImageView(size-1),coord1[0],coord1[1]);

                        }
                    }
                    else{
                        isHydraDead();
                    }
                    break;
                }
            }
        }



    }


    // Method to find random Un-used location for next Hydra Head
    private int[] locationFinder(){
        full = true;
        int x = 0;
        int y = 0;
        while (full) {
            // Generate Random Location
            x = rand.nextInt(17);
            y = rand.nextInt(17);
            // Check if location is used or not
            check = (ImageView) getNodeFromGridPane(gameBoard,x,y);
            if (check==null) {
                // Break out of loop if empty spot is found
                full=false;
            }
        }
        // Format return array
        int[] coord = new int[2];
        coord[0]=x;
        coord[1]=y;
        return coord;
    }

    // Method to get Node as Object from coordinates or Null if Node is Empty
    private Node getNodeFromGridPane(GridPane gameBoard, int colIndex, int rowIndex) {
        for (Node nodeFound : gameBoard.getChildren()) {
            if (nodeFound instanceof ImageView && gameBoard.getColumnIndex(nodeFound) == colIndex && gameBoard.getRowIndex(nodeFound) == rowIndex) {
                return nodeFound;
            }
        }
        return null;
    }

    // Get new Image View to place in game to avoid "Duplicate Children" error
    private ImageView getImageView(int size) {
            ImageView imageView = new ImageView(heads1[size-1]);
        return imageView;
    }

    // Check if game is over and Display End Screen Text, if Game is not over, do nothing
    private void isHydraDead() {
        boolean gameOver = true;
        for (int c = 0;c<17;c++){
            for (int r = 0;r<17;r++){
                if (getNodeFromGridPane(gameBoard,c,r)!=null){
                    gameOver = false;
                    break;
                }
            }
        }
        // Display Score at Game's end
        if (gameOver){
            endScreen.setText("\t\tGood Job!\nYou have Cut " + cuts + " Hydra Heads!");
            endScreen.setDisable(false);
        }
    }
}