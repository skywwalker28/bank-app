package com.skyww28.bank.DTO;

public class TransactionDTO {
    private BankAccountDTO fromAccount;
    private BankAccountDTO toAccount;

    public TransactionDTO(BankAccountDTO fromAccount, BankAccountDTO toAccount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public BankAccountDTO getFromAccount() { return fromAccount; }
    public void setFromAccount(BankAccountDTO fromAccount) { this.fromAccount = fromAccount; }

    public BankAccountDTO getToAccount() { return toAccount; }
    public void setToAccount(BankAccountDTO toAccount) { this.toAccount = toAccount; }
}
