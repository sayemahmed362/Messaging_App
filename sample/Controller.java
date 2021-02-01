package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller extends Thread implements Initializable{

    BufferedReader reader;
    BufferedWriter writer;
    Socket socket;

    public static ArrayList<peoples> users = new ArrayList<>();
    public static String Username ;

@FXML
    Button signup_;
@FXML
    PasswordField pass1,pass_one,pass_two;
@FXML
    TextField username1,f_name,l_name,u_name,email;
@FXML
    CheckBox male,female,others;
@FXML
    Text show_it,show_it2,displayName;
@FXML
    TextArea in_text,out_text;
@FXML
    ImageView send;
String gender="";
    public Controller()
    {
        String name = "Name";
        try {
            Socket socket = new Socket("localhost", 5000);
            OutputStreamWriter ow = new OutputStreamWriter(socket.getOutputStream());
            writer = new BufferedWriter(ow);

            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);


            Thread t = new Thread()
            {
                public void run()
                {
                    try
                    {
                        String line =  reader.readLine();
                        while(line!=null){
                            out_text.appendText(line+"\n");
                            line = reader.readLine();
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void signup_btn(ActionEvent actionEvent) throws IOException {

        String gen1 = male.getText();
        String gen2 = female.getText();

        if(gen1.contains("true"))
        {
            gender="Male";
        }
        else if(gen2.contains("true"))
        {
            gender="Female";
        }
        else
        {
            gender="Others";
        }


        if(!f_name.getText().equalsIgnoreCase("") &&
                !l_name.getText().equalsIgnoreCase("") &&
                !u_name.getText().equalsIgnoreCase("") &&
                !email.getText().equalsIgnoreCase("") &&
                !pass_one.getText().equalsIgnoreCase("") &&
                !pass_two.getText().equalsIgnoreCase("") &&
                pass_one.getText().contains(pass_two.getText())
        )
        {
            peoples people = new peoples(f_name.getText(),l_name.getText(),u_name.getText(),email.getText(),pass_one.getText());
            users.add(people);


            Parent tableViewParent = FXMLLoader.load(getClass().getResource("signin.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
        else
        {
            show_it2.setText("Complete all fields, confirm password again !!");
            show_it2.setFill(Color.RED);
        }

    }

    @FXML
    public void sup_btn(ActionEvent actionEvent) throws Exception {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("signup.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();


    }

    @FXML
    public void sendbtn() {

        String s = in_text.getText();
        in_text.setText("");
        in_text.setPromptText("Write your text !!");
        out_text.appendText(s+"\n");
    }


    @FXML
    void login_btn(ActionEvent actionEvent) throws Exception
    {
        Username = username1.getText();

        String input_pass = pass1.getText();

        int flag=0;

        for (peoples x : users)
        {
            if(Username.contains(x.u_name) && input_pass.contains(x.pass_one))
            {
                flag=1;
                break;
            }
        }

        if(flag==1)
        {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("chat.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();

        }
        else
        {
            show_it.setText("Username & Password Mismatched !!");
            show_it.setFill(Color.RED);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
