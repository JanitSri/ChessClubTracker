package javafxapplication3;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.Node;
import javafx.util.Pair;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ButtonBar.ButtonData;


public class JavaFXApplication3 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        MemberManager chessClub = new MemberManager(1);
        
        // CREATE TABLE
        TableView table = new TableView();
        table.setPlaceholder(new Label("NO PLAYERS IN THE SYSTEM"));
        
        // CREATE COLUMNS 
        TableColumn<String, Member> memberIdCol = makeColumn("Member ID","memberId",100);
        TableColumn<String, Member> firstNameCol = makeColumn("firstName","firstName",100);
        TableColumn<String, Member> lastNameCol = makeColumn("Last Name","lastName",100);
        TableColumn<String, Member> winCol = makeColumn("Wins","numWins",100);
        TableColumn<String, Member> lossCol = makeColumn("Loss","numLosses",100);
        TableColumn<String, Member> gamesPlayedCol = makeColumn("Games Played","numGames",150);
        TableColumn<String, Member> winRateCol = makeColumn("Win Rate","winRate",100);
        
        table.setItems(chessClub.getMembers());
        table.getColumns().addAll(memberIdCol,firstNameCol,lastNameCol,winCol,lossCol,gamesPlayedCol,winRateCol);
        
        
        // CREATE TEXT FIELDS 
        TextField fName = createTextFIeld("First Name", 20);
        TextField lName = createTextFIeld("Last Name", 20);
        TextField win = createTextFIeld("# of Wins", 20);
        TextField loss = createTextFIeld("# of Losses", 20);
        
      
        // DELETE BUTTON AND EVENT HANDLER       
        Button deleteMember = new Button("Delete Member");
        deleteMember.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override 
            public void handle(ActionEvent event)
            {
                TextInputDialog deleteTextDialog = new TextInputDialog();
                deleteTextDialog.setHeaderText("Enter the ID of the Member to delete");
                deleteTextDialog.setTitle("Delete Member");
                Optional<String> deleteResult = deleteTextDialog.showAndWait();
                boolean cancelPress = deleteResult.isPresent();
                
                if(!cancelPress)
                {
                    deleteResult = Optional.of("-1");
                }
                
                int checkID = deleteResult.get().matches("-?[0-9]+(\\.[0-9]+)?") ? Integer.parseInt(deleteResult.get()) : -1;
                boolean validID = chessClub.memberExists(checkID);
                
                
                if(!validID && cancelPress)
                {
                    showAlert(Alert.AlertType.ERROR, "Invalid Entry", primaryStage, "The Member Does Not Exist");
                }
                
                if(validID)
                {
                    chessClub.deleteMember(checkID);
                }
                
                deleteTextDialog.getEditor().clear();
            }
        });
        
        
        // ADD MEMBER BUTTON AND EVENT HANDLER
        Button addMember = new Button("Add Member");
        addMember.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                String output = "The Form Cannot be Submitted:\n";
                boolean alert = false;
                
                if(fName.getText().isEmpty())
                {
                    output += "\"First Name\" Cannot Be Empty\n";
                    alert = true;
                }
                
                if(lName.getText().isEmpty())
                {
                    output += "\"Last Name\" Cannot Be Empty\n";
                    alert = true;
                }
                
                if(!win.getText().matches("-?[0-9]+(\\.[0-9]+)?"))
                {
                    output += "\"# of Wins\" is not a Valid Entry (if empty, set to 0)\n";
                    alert = true;
                }
                
                if(!loss.getText().matches("-?[0-9]+(\\.[0-9]+)?"))
                {
                    output += "\"# of Losses\" is not a Valid Entry (if empty, set to 0)\n";
                    alert = true;
                }
                
                if(alert)
                {
                    showAlert(Alert.AlertType.ERROR, "ERROR!", primaryStage,output);
                }
                else
                {
                    int numGames = Integer.parseInt(win.getText()) + Integer.parseInt(loss.getText());
                    double winRate = chessClub.calculateWinRate(Integer.parseInt(win.getText()),numGames);
                    chessClub.addMember(fName.getText(), lName.getText(), Integer.parseInt(win.getText()), Integer.parseInt(loss.getText()), numGames,winRate);
                    
                    fName.clear();
                    lName.clear();
                    win.clear();
                    loss.clear();
                    
                    fName.setPromptText("First Name");
                    lName.setPromptText("Last Name");
                    win.setPromptText("# of Wins");
                    loss.setPromptText("# of Losses");
                }
            }            
        });
        
        HBox hBox1 = new HBox();
        hBox1.setSpacing(5);
        hBox1.setPadding(new Insets(10, 10, 10, 10));
        hBox1.getChildren().addAll(fName,lName,win,loss,addMember,deleteMember);
        
        
        // MOST WINS AND EVENT HANDLER
        Button memberMostWins = new Button(" View Member With Most Wins");
        memberMostWins.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                showAlert(Alert.AlertType.INFORMATION, "Most Wins", primaryStage, chessClub.getMostWins());
            }            
        });
        
        
        // HIGHEST WIN RATE AND EVENT HANDLER
        Button highestWinRate = new Button(" View Member With Highest Win Rate");
        highestWinRate.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                showAlert(Alert.AlertType.INFORMATION, "Highest Win Rate", primaryStage, chessClub.getHighestWinrate());
            }            
        });
        
        
        // UPDATE MEMBER AND EVENT HANDLER
        Button updateMember = new Button("Update Member");
        updateMember.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                
                // DIALOG BOX FOR UPDATE MEMBER
                TextInputDialog textDialog = new TextInputDialog();
                textDialog.setHeaderText("Enter the ID of the Member to update");
                textDialog.setTitle("Update Member");
                Optional<String> idResult = textDialog.showAndWait();
                boolean cancelPress = idResult.isPresent();
                
                if(!cancelPress)
                {
                    idResult = Optional.of("-1");
                }
                
                // CHECK FOR VALID MEMBER ID
                int checkID = idResult.get().matches("-?[0-9]+(\\.[0-9]+)?") ? Integer.parseInt(idResult.get()) : -1;
                boolean validID = chessClub.memberExists(checkID);
                
                
                // NOT VALID MEMBER HANDLE
                if(!validID && cancelPress)
                {
                    showAlert(Alert.AlertType.ERROR, "Invalid Entry", primaryStage, "The Member Does Not Exist");
                }
                
                // VALID MEMBER HANDLE
                if(validID)
                {
                    Dialog<Pair<String, String>> updateDialog = new Dialog<>();
                    updateDialog.setTitle("Update Member");
                    updateDialog.setHeaderText(chessClub.getMember(checkID));
                    
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 150, 10, 10));

                    TextField addWins = new TextField("0");
                    TextField addLosses = new TextField("0");
                    
                    ButtonType sumbitUpdate = new ButtonType("Update", ButtonData.OK_DONE);
                    ButtonType sumbitCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    updateDialog.getDialogPane().getButtonTypes().addAll(sumbitUpdate, sumbitCancel);
                    
                    
                    grid.add(new Label("Add Wins {negative number to decrease}:"), 0, 0);
                    grid.add(addWins, 1, 0);
                    grid.add(new Label("Add Loss {negative number to decrease}:"), 0, 1);
                    grid.add(addLosses, 1, 1);
                    
                    
                    Node sumbitButton = updateDialog.getDialogPane().lookupButton(sumbitUpdate);
                    sumbitButton.setDisable(false);
                    
                    int winValueLimit = 0 - chessClub.getMemberWins(checkID);
                    int lossValueLimit = 0 - chessClub.getMemberLosses(checkID);
                    
                    addWins.textProperty().addListener((observable, oldValue, newValue) -> {
                        sumbitButton.setDisable(newValue.trim().isEmpty() || !newValue.matches("-?[0-9]+(\\.[0-9]+)?") || (!newValue.trim().isEmpty() && newValue.matches("-?[0-9]+(\\.[0-9]+)?") && Integer.parseInt(newValue) < winValueLimit));
                        if(addLosses.getText().trim().isEmpty()){addLosses.setText("0");}
                    });
                    addWins.focusedProperty().addListener((observable, oldValue, newValue) -> {
                        if(!newValue)
                        {
                          if(addWins.getText().trim().isEmpty()){addWins.setText("0");}
                        }
                    });
                    
                    
                    addLosses.textProperty().addListener((observable, oldValue, newValue) -> {
                        sumbitButton.setDisable(newValue.trim().isEmpty() || !newValue.matches("-?[0-9]+(\\.[0-9]+)?") || (!newValue.trim().isEmpty() && newValue.matches("-?[0-9]+(\\.[0-9]+)?") && Integer.parseInt(newValue) < lossValueLimit));
                         if(addWins.getText().trim().isEmpty()){addWins.setText("0");}
                    });
                    addLosses.focusedProperty().addListener((observable, oldValue, newValue) -> {
                        if(!newValue)
                        {
                          if(addLosses.getText().trim().isEmpty()){addLosses.setText("0");}
                        }
                    });
                    
                    
                    updateDialog.getDialogPane().setContent(grid);
                    Optional<Pair<String, String>> result = updateDialog.showAndWait();
                    
                    chessClub.addWins(checkID, Integer.parseInt(addWins.getText()));
                    chessClub.addLosses(checkID, Integer.parseInt(addLosses.getText()));
                }
                
                
                textDialog.getEditor().clear();
                
            }            
        });
        
        
        HBox hBox2 = new HBox();
        hBox2.setSpacing(5);
        hBox2.setPadding(new Insets(15, 20, 10, 20));
        hBox2.getChildren().addAll(memberMostWins,highestWinRate,updateMember);
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox1, hBox2);
        
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("GBC Chess Club");
        primaryStage.show();
    }
    
    public void showAlert(Alert.AlertType type, String title, Window owner, String message)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
    
    public static TableColumn<String, Member> makeColumn(String colName, String propertyName, int width){
        TableColumn<String, Member> column = new TableColumn<>(colName);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        column.setMinWidth(width);
        return column;
    }
    
    public static TextField createTextFIeld(String name, int width){
        TextField tField = new TextField();
        tField.setPromptText(name);
        tField.setMinWidth(width);
        return tField;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
