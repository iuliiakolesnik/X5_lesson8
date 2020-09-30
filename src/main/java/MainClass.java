import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainClass {
    public static void main(String[] argv) throws SQLException {

        /*try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            //Connection connection = DriverManager.getConnection("jdbc:h2:mem:test");
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;INIT=RUNSCRIPT FROM './schema.sql'\\;RUNSCRIPT FROM './data.sql'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/


        String table = "Account";
        String dbUrl = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM './schema.sql'\\;RUNSCRIPT FROM './data.sql'";
        String user = "postgres";
        String pass = "Pummel!1";

       // AccountServiceImpl accountService = new AccountServiceImpl(dbUrl, user, pass);
        AccountServiceImpl accountService = new AccountServiceImpl(dbUrl);
        accountService.printInfo();

        try {
            accountService.operation();
        } catch (UnknownAccountException | NotEnoughMoneyException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
