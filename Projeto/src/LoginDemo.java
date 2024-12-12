import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDemo {
    public static void main(String[] args){
        JFrame frame = new JFrame("Login");
        
        JLabel userLabel = new JLabel("Usuário: ");
        userLabel.setBounds(20,20,80,30);
        JLabel passLabel = new JLabel("Senha: ");
        passLabel.setBounds(20,75,80,30);

        JTextField userField = new JTextField();
        userField.setBounds(100,20,150,30);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(100,75,150,30);

        JButton loginButton = new JButton("Logar");
        loginButton.setBounds(100,120,100,30);

        frame.add(userLabel);
        frame.add(passLabel);
        frame.add(userField);
        frame.add(passField);
        frame.add(loginButton);

        frame.setSize(300, 200);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String username = userField.getText();
                String password = new String(passField.getPassword());
                if(authenticate(username, password)){
                    JOptionPane.showMessageDialog(frame, "Login efetuado com sucesso");
                }else{
                    JOptionPane.showMessageDialog(frame, "Usuário ou senha inválidos");
                }
            }
        });
    }

    public static boolean authenticate(String username, String password){
        boolean isAuthenticated = false;
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users","root","root");
            String query = "SELECT * FROM users WHERE user = ? AND password =?";
            PreparedStatement statement  =connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                isAuthenticated = true;
            }
            statement.close();
            connection.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return isAuthenticated;
    }
}


