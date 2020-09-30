import java.sql.SQLException;

/*
∎ Реализовать интерфейс AccountService, который производит манипуляцию со счетами пользователей. Информация о счете должна хранится в файловой системе. Доступ к файловому хранилищу осуществлять с помощью символьных потоков ввода/вывода.
public class AccountService {
void withdraw(int accountId, int amount) throws
NotEnoughMoneyException, UnknownAccountException;
void balance(int accountId) throws UnknownAccountException; void deposit(int accountId, int amount) throws
NotEnoughMoneyException, UnknownAccountException; void transfer(int from, int to, int amount) throws
NotEnoughMoneyException, UnknownAccountException; }
 */
public interface AccountService {

    void withdraw(int accountId, int amount) throws NotEnoughMoneyException, UnknownAccountException, SQLException;

    void balance(int accountId) throws UnknownAccountException, SQLException;

    void deposit(int accountId, int amount) throws NotEnoughMoneyException, UnknownAccountException, SQLException;

    void transfer(int from, int to, int amount) throws NotEnoughMoneyException, UnknownAccountException, SQLException;
}
