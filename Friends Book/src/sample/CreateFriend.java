package sample;

import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;

public class CreateFriend {

    private static ArrayList<Friend> friends = new ArrayList<>();

    public static ArrayList<Friend> createAllFriends(String fileName) throws IOException{
        friends.clear();
        //Reads the file
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int i = 0;
        String[] fields = new String[7];
        //We add every new line to the array of fields. When seven fields are added, we reset the index to the start and adds a new friend with the seven fields
        //We repeat this until all lines have been read (when the line becomes null)
        while((line = br.readLine()) != null){
            fields[i++] = line;
            if (i==7){
                i=0;
                friends.add(new Friend(fields[0], fields[1], fields[2], fields[3], Color.web(fields[4]), fields[5], Color.web(fields[6])));
            }
        }
        //returns the ArrayList of friends from the group that we want
        return friends;
    }

    //writes a new file to avoid having an error
    public static void write(String fileName, boolean append) throws IOException{
        FileWriter fw = new FileWriter(fileName, append);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("");
        bw.close();
    }
}