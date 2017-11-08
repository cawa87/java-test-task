package ru.smarty.accountapi.services;

public class DummyAccountDetailService implements AccountDetailService {
    @Override
    public String getAccountDetails(int accountId) {
        return "Account #" + accountId + ". Yay!";
    }
}
