// Реализовать класс Account c полями: id – уникальный идентификатор счета, holder – владелец счета, amount – сумма на счете
public class Account {
    private int id;
    private String holder;
    private int amount;

    public Account(int id, String holder, int amount){
        this.id = id;
        this.holder = holder;
        this.amount = amount;
    }

    public void printInfo(){
        System.out.println("ID: " + this.id + " Holder: " + this.holder + " amount: " + this.amount);
    }

    public int getId(){
        return this.id;
    }

    public String getHolder(){
        return this.holder;
    }

    public int getAmount(){
        return this.amount;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setHolder(String holder){
        this.holder = holder;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }
}
