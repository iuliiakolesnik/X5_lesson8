import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainClass {
    public static void main(String[] argv) throws SQLException {

        String dbUrl = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM './schema.sql'\\;RUNSCRIPT FROM './data.sql'";

        AccountServiceImpl accountService = new AccountServiceImpl(dbUrl);
        accountService.printInfo();

        try {
            accountService.operation();
            accountService.closeConnection();
        } catch (UnknownAccountException | NotEnoughMoneyException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
