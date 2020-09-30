import java.io.*;
import java.util.*;
import java.sql.*;


public class AccountServiceImpl implements AccountService {

    //private ArrayList<Account> accountList = new ArrayList<Account>();
    private static String dbUrl;
    private static String user;
    private static String pass;

    private Connection connection = null;

    public AccountServiceImpl(String dbUrl){
        this.dbUrl = dbUrl;
        try {
            this.connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
    }


    public AccountServiceImpl(String dbUrl, String user, String pass) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.pass = pass;

        try {
            this.connection = DriverManager.getConnection(dbUrl, user, pass);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void printInfo() throws SQLException {
        if (this.connection != null) {
            ResultSet resultSet;
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM public.Account");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("account_id") + " " + resultSet.getString("holder") + " " + resultSet.getInt("amount"));
            }
            statement.close();
        } else {
            System.out.println("Failed to make connection to database");
        }
    }

    @Override
    public void balance(int accountId) throws UnknownAccountException, SQLException {
        if (this.connection != null) {
            ResultSet resultSet;

            String select = "SELECT * FROM public.Account where account_id = ? ";
            PreparedStatement statement = this.connection.prepareStatement(select);
            statement.setInt(1, accountId);
            statement.execute();
            resultSet = statement.getResultSet();

            if (resultSet.next()) {
                System.out.println(resultSet.getInt("account_id") + " " + resultSet.getString("holder") + " " + resultSet.getInt("amount"));
            } else {
                throw new UnknownAccountException("Unknown holder");
            }
            statement.close();
        } else {
            System.out.println("Failed to make connection to database");
        }

    }

    @Override
    public void withdraw(int accountId, int amount) throws NotEnoughMoneyException, UnknownAccountException, SQLException {
        if (this.connection != null) {
            ResultSet resultSet;
            String select = "SELECT * FROM public.Account where account_id = ? ";
            PreparedStatement statement = this.connection.prepareStatement(select);
            statement.setInt(1, accountId);
            statement.execute();
            resultSet = statement.getResultSet();

            if (resultSet.next()) {
                int accountAmount = resultSet.getInt("amount");
                if (accountAmount < amount) {
                    throw new NotEnoughMoneyException("Not Enough Money");
                } else {
                    // int accountAmount = resultSet.getInt("amount");
                    String selectUpdate = "UPDATE public.Account SET amount=? WHERE account_id=?";
                    PreparedStatement statementUpdate = this.connection.prepareStatement(selectUpdate);
                    statementUpdate.setInt(1, accountAmount - amount);
                    statementUpdate.setInt(2, accountId);
                    statementUpdate.execute();
                    statementUpdate.close();
                }
            } else {
                throw new UnknownAccountException("Unknown holder");
            }
            statement.close();
        } else {
            System.out.println("Failed to make connection to database");
        }
    }

    @Override
    public void deposit(int accountId, int amount) throws NotEnoughMoneyException, UnknownAccountException, SQLException {
        if (this.connection != null) {
            ResultSet resultSet;
            String select = "SELECT * FROM public.Account where account_id = ? ";
            PreparedStatement statement = this.connection.prepareStatement(select);
            statement.setInt(1, accountId);
            statement.execute();
            resultSet = statement.getResultSet();

            if (resultSet.next()) {
                if (amount == 0) {
                    throw new NotEnoughMoneyException("Not Enough Money");
                } else {
                    int accountAmount = resultSet.getInt("amount");
                    String selectUpdate = "UPDATE public.Account SET amount=? WHERE account_id=?";
                    PreparedStatement statementUpdate = this.connection.prepareStatement(selectUpdate);
                    statementUpdate.setInt(1, accountAmount + amount);
                    statementUpdate.setInt(2, accountId);
                    statementUpdate.execute();
                    statementUpdate.close();
                }
            } else {
                throw new UnknownAccountException("Unknown holder");
            }
            statement.close();
        } else {
            System.out.println("Failed to make connection to database");
        }
    }

    public void transfer(int from, int to, int amount) throws NotEnoughMoneyException, UnknownAccountException, SQLException {
        withdraw(from, amount);
        deposit(to, amount);
    }

    public void operation() throws NotEnoughMoneyException, UnknownAccountException, SQLException {
        int accountId, amount;

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter operation (balance, withdraw, deposit, transfer): ");
        String operation = reader.nextLine();

        switch (operation) {
            case "balance":
                System.out.println("Enter accountID: ");
                // accountId = reader.nextInt();
                balance(reader.nextInt());
                break;
            case "withdraw":
                System.out.println("Enter accountID and Amount ");
                accountId = reader.nextInt();
                amount = reader.nextInt();
                withdraw(accountId, amount);
                balance(accountId);
                break;
            case "deposit":
                System.out.println("Enter accountID and Amount ");
                accountId = reader.nextInt();
                amount = reader.nextInt();
                deposit(accountId, amount);
                balance(accountId);
                break;
            case "transfer":
                System.out.println("Enter accountID from and account to and Amount ");
                int accountIdFrom = reader.nextInt();
                int accountIdTo = reader.nextInt();
                amount = reader.nextInt();
                transfer(accountIdFrom, accountIdTo, amount);
                balance(accountIdFrom);
                balance(accountIdTo);
                break;
            default:
                System.out.println("Please try again. We don't recognize operation.");
                operation();
        }

        this.connection.close();

    }
}