package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    public Arc bangs;
    public Ellipse eyeLeft;
    public Ellipse eyeRight;
    public Rectangle hair;

    public ListView friendList;

    public Label lblName;
    public Label lblPronoun;
    public Label lblPhone;
    public Label lblEmail;

    public Button btnDelete;

    public ChoiceBox choicePron;
    public ChoiceBox choiceHairLen;
    public ChoiceBox loadGroup;

    public ColorPicker pickHairColor;
    public ColorPicker pickEyeColor;

    public TextField textGroup;
    public TextField textPhoneNum;
    public TextField textEmail;
    public TextField textName;

    public Friend friend;
    public String group = "Default Group";

    ObservableList<String> pronounList = FXCollections.observableArrayList( "she/her", "he/him", "they/them");
    ObservableList<String> hairLenList = FXCollections.observableArrayList( "bald", "short", "long");
    ObservableList<String> loadGroupList = FXCollections.observableArrayList("Default Group");

    //Initialize: Lets all the items including choice box and list view be initialized.
    public void initialize() throws IOException{
        //Sets the items of all choice boxes to the intended list
        choicePron.setItems(pronounList);
        choiceHairLen.setItems(hairLenList);
        loadGroup.setItems(loadGroupList);
        //Creates new files for group and Default Group to avoid error when loading
        CreateFriend.write("group.txt", true);
        CreateFriend.write("Default Group.txt", true);
        //Reads the group text file and makes new choices for the group choice box
        FileReader fr = new FileReader("group.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null) {
            loadGroupList.add(line);
        }
        br.close();
        //Let all choice boxes and color pickers be the intended choice or color.
        choicePron.getSelectionModel().select(2);
        choiceHairLen.getSelectionModel().select(1);
        loadGroup.getSelectionModel().select(0);
        pickHairColor.setValue(Color.BLACK);
        pickEyeColor.setValue(Color.BLACK);
        //Displays the friends in the current group
        String fileName = loadGroup.getValue().toString() + ".txt";
        ArrayList<Friend> friends = CreateFriend.createAllFriends(fileName);
        for(Friend f : friends){
            friendList.getItems().add(f);
        }
    }

    public void addFriend(ActionEvent actionEvent) throws IOException {
        //Make a new friend f with the correct fields
        Friend f = new Friend(textName.getText(), choicePron.getValue().toString(), textPhoneNum.getText(), textEmail.getText(), pickHairColor.getValue(), choiceHairLen.getValue().toString(), pickEyeColor.getValue());
        //Make an Arraylist friends for all the friends in the current group
        ArrayList<Friend> friends = CreateFriend.createAllFriends(loadGroup.getValue().toString()+".txt");
        //If a friend in the current group is equal to the friend that the user wants to add, then we simply return and does not add anything.
        for(Friend f2 : friends){
            if (f.equals(f2)){
                System.out.println("The same friend has been added.");
                return;
            }
        }
        //After the loop, the friend that the user wants to add is not equal to any of the current friends.
        //We can now add the friend and record it in its group file. Then, we clear all the fields and reset the choices and colors.
        friendList.getItems().add(f);
        textName.clear();
        textEmail.clear();
        textPhoneNum.clear();
        choicePron.getSelectionModel().select(2);
        choiceHairLen.getSelectionModel().select(1);
        pickHairColor.setValue(Color.BLACK);
        pickEyeColor.setValue(Color.BLACK);
        String fileName = loadGroup.getValue().toString() + ".txt";
        f.writeToFile(fileName);
    }

    //Method to display the friend when clicked on it in the list. Sets all the corresponding fields to its appropriate label or shape
    public void displayFriend(MouseEvent mouseEvent) {
        btnDelete.setDisable(false);
        friend = (Friend) friendList.getSelectionModel().getSelectedItem();
        lblName.setText(friend.getName());
        lblEmail.setText(friend.getEmail());
        lblPhone.setText(friend.getPhoneNum());
        lblPronoun.setText(friend.getPronoun());
        bangs.setFill(friend.getHairCol());
        hair.setFill(friend.getHairCol());
        eyeLeft.setFill(friend.getEyeCol());
        eyeRight.setFill(friend.getEyeCol());
        if(friend.getHairLen().equals("bald")){
            bangs.setVisible(false);
            hair.setVisible(false);
        }
        else if(friend.getHairLen().equals("short")){
            bangs.setVisible(true);
            hair.setVisible(false);
        }
        else{
            bangs.setVisible(true);
            hair.setVisible(true);
        }
    }

    public void deleteFriend(ActionEvent actionEvent) throws IOException{
        //We cannot simply delete a friend. Instead, we rewrite the file without the friend that we want to delete
        //We create two ArrayLists. "temp" is the ArrayList of all the friends in the current group. "friends" is a new ArrayList.
        String fileName = group + ".txt";
        ArrayList<Friend> temp = CreateFriend.createAllFriends(fileName);
        ArrayList<Friend> friends = new ArrayList<>();
        //For every friend in the current group, if it is not the chosen friend that the user wants to delete, we add it to the friends ArrayList.
        for (Friend f : temp){
            if (!f.equals(friend))friends.add(f);
        }
        friendList.getItems().clear();
        //We rewrite the file without appending it(erases and rewrites from the start) with all the friends from the friends ArrayList (all friends that isn't the friend we want to delete)
        CreateFriend.write(fileName, false);
        for (Friend f : friends){
            f.writeToFile(fileName);
            friendList.getItems().add(f);
        }
        //Resets all the displays including labels and shapes to their default values
        btnDelete.setDisable(true);
        lblName.setText("");
        lblEmail.setText("");
        lblPhone.setText("");
        lblPronoun.setText("");
        bangs.setFill(new Color(0,0,0,1));
        hair.setFill(new Color(0,0,0,1));
        eyeLeft.setFill(new Color(0,0,0,1));
        eyeRight.setFill(new Color(0,0,0,1));
        bangs.setVisible(true);
        hair.setVisible(true);
        btnDelete.setDisable(true);
    }

    public void loadFriend(ActionEvent actionEvent) throws IOException {
        //Disables the delete button
        btnDelete.setDisable(true);
        //sets the group to the group we want to load and creates a filename, which is the group name plus ".txt"
        group = loadGroup.getValue().toString();
        String fileName = group + ".txt";
        //clears the friendlist listView and adds all friends from the group we want to load.
        friendList.getItems().clear();
        ArrayList<Friend> friends = CreateFriend.createAllFriends(fileName);
        for(Friend f : friends){
            friendList.getItems().add(f);
        }
    }

    public void addGroup(ActionEvent actionEvent) throws IOException{
        //If the group contains the group that we want to add, then we clear the text box and doesn't add anything
        //If it doesn't, then we add the group to the group text file and group choice box
        if(!loadGroupList.contains(textGroup.getText())){
            loadGroupList.add(textGroup.getText());
            FileWriter fw = new FileWriter("group.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(textGroup.getText() + "\n");
            bw.close();
            //TWe create a new file for the group to ensure no error happens when loading the group.
            fw = new FileWriter(textGroup.getText()+".txt", true);
            bw = new BufferedWriter(fw);
            bw.write("");
            bw.close();
        }
        textGroup.clear();
    }
}