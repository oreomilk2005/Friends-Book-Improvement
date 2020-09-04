package sample;

import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Friend {
    //Fields
    private String name;
    private String pronoun;
    private String phoneNum;
    private String email;
    private String hairLen;
    private javafx.scene.paint.Color hairCol;
    private javafx.scene.paint.Color eyeCol;

    //Friend Constructor
    Friend(String name, String pronoun, String phoneNumber, String emailAddress, javafx.scene.paint.Color hairColor,String hairLength, javafx.scene.paint.Color eyeColor){
        if(name.equals("")) this.name = "unknown friend";
        else this.name= name;
        this.pronoun = pronoun;
        phoneNum = phoneNumber;
        email = emailAddress;
        hairCol= hairColor;
        hairLen = hairLength;
        eyeCol = eyeColor;
    }

    //Records the friend(its fields) to the file of the group that the friend belongs to. Each field is written in a new line.
    public void writeToFile(String fileName) throws IOException{
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(name + "\n");
        bw.write(pronoun + "\n");
        bw.write(phoneNum +"\n");
        bw.write(email + "\n");
        bw.write( hairCol + "\n");
        bw.write( hairLen + "\n");
        bw.write( eyeCol+ "\n");
        bw.flush();
        bw.close();
    }

    //Lets the display of the friend become its name
    public String toString(){ return name; }

    //A method to check if two friends are equal
    public boolean equals (Friend f){
        return name.equals(f.name) && pronoun.equals(f.pronoun) && phoneNum.equals(f.phoneNum) && email.equals(f.email) && hairLen.equals(f.hairLen) && hairCol.equals(f.hairCol) && eyeCol.equals(f.eyeCol);
    }

    //Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPronoun() { return pronoun; }
    public void setPronoun(String pronoun) { this.pronoun = pronoun; }
    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Color getHairCol() { return hairCol; }
    public void setHairCol(Color hairCol) { this.hairCol = hairCol; }
    public String getHairLen() { return hairLen; }
    public void setHairLen(String hairLen) { this.hairLen = hairLen; }
    public Color getEyeCol() { return eyeCol; }
    public void setEyeCol(Color eyeCol) { this.eyeCol = eyeCol; }
}