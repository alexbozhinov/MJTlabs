package bg.sofia.uni.fmi.mjt.lab3.udemy.account;

import bg.sofia.uni.fmi.mjt.lab3.udemy.account.type.AccountType;

public class StandardAccount extends AccountBase{
    public StandardAccount(String username, double balance) {
        super(username, balance);
        type = AccountType.STANDARD;
    }
}
