package com.andreikuebar.bankingdemo;

import com.andreikuebar.bankingdemo.application.BankingDemoApplication;
import com.andreikuebar.bankingdemo.model.AccountStatementResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static com.andreikuebar.bankingdemo.TestDataInitializer.SOURCE_ACCOUNT_NUMBER;
import static com.andreikuebar.bankingdemo.TestDataInitializer.TARGET_ACCOUNT_NUMBER;

@ActiveProfiles("test")
@SpringBootTest(classes = {BankingDemoApplication.class, TestConfiguration.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankingDemoHttpTests {

    private static final String ACCOUNTS_STATEMENT_ENDPOINT = "/accounts/statement";
    private static final String ACCOUNTS_TRANSFER_ENDPOINT = "/accounts/transfer";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestDataInitializer testDataInitializer;

    @Test
    public void contextLoads() {

    }

    @Test
    public void verifyAccountTransfer(){
        String hostname = "http://localhost:" + port;
        long transferAmount = 10L;
        ObjectNode sendMoneyRequest = objectMapper.createObjectNode();
        sendMoneyRequest.put("sourceAccount", SOURCE_ACCOUNT_NUMBER);
        sendMoneyRequest.put("targetAccount", TARGET_ACCOUNT_NUMBER);
        sendMoneyRequest.put("amount", transferAmount);

        AccountStatementResponse sourceStatement =
                restTemplate.getForObject(hostname + ACCOUNTS_STATEMENT_ENDPOINT + "/" + SOURCE_ACCOUNT_NUMBER, AccountStatementResponse.class);
        Long sourceAccountInitialBalance = sourceStatement.getAccountInfo().getBalance();

        AccountStatementResponse targetStatement =
                restTemplate.getForObject(hostname + ACCOUNTS_STATEMENT_ENDPOINT + "/" + TARGET_ACCOUNT_NUMBER, AccountStatementResponse.class);
        Long targetAccountInitialBalance = targetStatement.getAccountInfo().getBalance();

        ResponseEntity<Object>  responseEntity = restTemplate.exchange(hostname + ACCOUNTS_TRANSFER_ENDPOINT,
                HttpMethod.POST, new HttpEntity<>(sendMoneyRequest), Object.class);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

        AccountStatementResponse resultingSourceAccountStatement =
                restTemplate.getForObject(hostname + ACCOUNTS_STATEMENT_ENDPOINT + "/" + SOURCE_ACCOUNT_NUMBER, AccountStatementResponse.class);
        Assertions.assertEquals(sourceAccountInitialBalance - transferAmount,
                resultingSourceAccountStatement.getAccountInfo().getBalance().longValue());

        AccountStatementResponse resultingTargetAccountStatement =
                restTemplate.getForObject(hostname + ACCOUNTS_STATEMENT_ENDPOINT + "/" + TARGET_ACCOUNT_NUMBER, AccountStatementResponse.class);
        Assertions.assertEquals(targetAccountInitialBalance + transferAmount,
                resultingTargetAccountStatement.getAccountInfo().getBalance().longValue());
    }

    @BeforeAll
    private void createTestAccounts() {
        testDataInitializer.createInitialAccounts();
    }

    @AfterAll
    private void cleanUp() {
        testDataInitializer.cleanUp();
    }
}
