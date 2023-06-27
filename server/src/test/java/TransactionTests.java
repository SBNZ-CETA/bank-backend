import facts.Transaction;
import facts.UserTransactions;
import io.jsonwebtoken.lang.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TransactionTests {
    private KieContainer kieContainer;
    private KieSession kieSession;

    @Before
//    public void start() throws IOException {
//        KieServices kieServices = KieServices.Factory.get();
//        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//
//        // Specify the rule file's exact path
//        String a = System.getProperty("user.dir");
//        int index = a.lastIndexOf("\\");
//        a = a.substring(0, index);
//        String ruleFilePath = a + "\\rules\\src\\main\\resources\\sbnz\\bank.rules\\suspisiousTransaction.drl";
//
//        // Load the rule file into the KieFileSystem
//        kieFileSystem.write(ResourceFactory.newClassPathResource(ruleFilePath));
//
//        // Set the KieModule coordinates
//        ReleaseId releaseId = kieServices.newReleaseId("sbnz.bank.rules", "rules", "1.0.0");
//
//        // Create the KieContainer using the KieFileSystem and KieModule coordinates
//        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
//        kieBuilder.buildAll();
//        KieModule kieModule = kieBuilder.getKieModule();
//        kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
//    }
    public void start() {
        kieSession = kieContainer().newKieSession();
    }

    @Test
    public void testSuspiciousSecondWithinHour500kmAwayNot1h(){
        Transaction transaction1 = Transaction.builder()
                .location("102.38.248.0")
                .latitude(39.0)
                .longitude(22.0)
                .transactionTime(LocalDateTime.of(2023, 6, 24, 14, 0))
                .build();
        Transaction transaction2 = Transaction.builder()
                .location("102.38.248.0")
                .latitude(39.0)
                .longitude(22.0)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 12, 20))
                .build();
        Transaction transaction = Transaction.builder()
                .location("102.38.248.0")
                .latitude(39.0)
                .longitude(22.0)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 20))
                .build();
        UserTransactions userTransactions = UserTransactions.builder()
                .transactionList(Arrays.asList(transaction1, transaction2))
                .build();
        kieSession.insert(transaction);
        kieSession.insert(userTransactions);
        kieSession.getAgenda().getAgendaGroup("location").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(!transaction.isSuspicious());
    }

    @Test
    public void testSuspiciousSecondWithinHour500kmAwayNot500km(){
        Transaction transaction1 = Transaction.builder()
                .location("102.38.248.0")
                .latitude(39.0)
                .longitude(22.0)
                .transactionTime(LocalDateTime.of(2023, 6, 24, 14, 0))
                .build();
        Transaction transaction2 = Transaction.builder()
                .location("102.38.248.0")
                .latitude(39.0)
                .longitude(22.0)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 20))
                .build();
        Transaction transaction = Transaction.builder()
                .location("102.38.248.0")
                .latitude(39.0)
                .longitude(22.0)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 20))
                .build();
        UserTransactions userTransactions = UserTransactions.builder()
                .transactionList(Arrays.asList(transaction1, transaction2))
                .build();
        kieSession.insert(transaction);
        kieSession.insert(userTransactions);
        kieSession.getAgenda().getAgendaGroup("location").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(!transaction.isSuspicious());
    }

    // Suspicious true
    @Test
    public void testSuspiciousSecondWithinHour500kmAway(){
        Transaction transaction1 = Transaction.builder()
                .location("102.38.248.0")
                .latitude(39.0)
                .longitude(22.0)
                .transactionTime(LocalDateTime.of(2023, 6, 24, 14, 0))
                .build();
        Transaction transaction2 = Transaction.builder()
                .location("103.38.248.0")
                .latitude(28.0)
                .longitude(84.0)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 20))
                .build();
        Transaction transaction = Transaction.builder()
                .location("102.38.248.0")
                .latitude(39.0)
                .longitude(22.0)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 20))
                .build();
        UserTransactions userTransactions = UserTransactions.builder()
                .transactionList(Arrays.asList(transaction1, transaction2))
                .build();
        kieSession.insert(transaction);
        kieSession.insert(userTransactions);
        kieSession.getAgenda().getAgendaGroup("location").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(transaction.isSuspicious());
    }

    @Test
    public void testSuspicious7thWithin3hoursNotIn7th(){
        Transaction transaction3 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 0))
                .build();
        Transaction transaction4 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 20))
                .build();
        Transaction transaction5 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 30))
                .build();
        Transaction transaction6 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 40))
                .build();
        Transaction transaction = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 15, 0))
                .build();
        UserTransactions userTransactions = UserTransactions.builder()
                .transactionList(Arrays.asList(transaction3, transaction4, transaction5, transaction6))
                .build();
        kieSession.insert(transaction);
        kieSession.insert(userTransactions);
        kieSession.getAgenda().getAgendaGroup("locationIndependent").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(!transaction.isSuspicious());
    }

    @Test
    public void testSuspicious7thWithin3hoursNotIn3h(){
        Transaction transaction1 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 11, 0))
                .build();
        Transaction transaction2 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 13, 10))
                .build();
        Transaction transaction3 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 0))
                .build();
        Transaction transaction4 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 20))
                .build();
        Transaction transaction5 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 30))
                .build();
        Transaction transaction6 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 40))
                .build();
        Transaction transaction = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 15, 0))
                .build();
        Transaction transaction7 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 40))
                .build();
        UserTransactions userTransactions = UserTransactions.builder()
                .transactionList(Arrays.asList(transaction1, transaction2, transaction3, transaction4, transaction5, transaction6, transaction7))
                .build();
        kieSession.insert(transaction);
        kieSession.insert(userTransactions);
        kieSession.getAgenda().getAgendaGroup("locationIndependent").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(!transaction.isSuspicious());
    }

    // Suspicious true
    @Test
    public void testSuspicious7thWithin3hours(){
        Transaction transaction1 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 13, 0))
                .build();
        Transaction transaction2 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 13, 10))
                .build();
        Transaction transaction3 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 0))
                .build();
        Transaction transaction4 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 20))
                .build();
        Transaction transaction5 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 30))
                .build();
        Transaction transaction6 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 40))
                .build();
        Transaction transaction7 = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 14, 40))
                .build();
        Transaction transaction = Transaction.builder()
                .location("102.38.248.0")
                .amount(200)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 15, 0))
                .build();
        UserTransactions userTransactions = UserTransactions.builder()
                .transactionList(Arrays.asList(transaction1, transaction2, transaction3, transaction4, transaction5, transaction6, transaction7))
                .build();
        kieSession.insert(transaction);
        kieSession.insert(userTransactions);
        kieSession.getAgenda().getAgendaGroup("locationIndependent").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(transaction.isSuspicious());
    }

    @Test
    public void testSuspiciousNewLocationNotNew(){
        Transaction transaction1 = Transaction.builder()
                .location("102.38.248.0")
                .build();
        Transaction transaction2 = Transaction.builder()
                .location("102.38.248.0")
                .build();
        Transaction transaction3 = Transaction.builder()
                .location("102.38.248.0")
                .transactionTime(LocalDateTime.of(2023, 6, 26, 15, 0))
                .build();
        UserTransactions userTransactions = UserTransactions.builder()
                .transactionList(Arrays.asList(transaction1, transaction2))
                .build();
        kieSession.insert(transaction3);
        kieSession.insert(userTransactions);
        kieSession.getAgenda().getAgendaGroup("locationNew").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(!transaction3.isSuspicious());
    }

    // Suspicious true
    @Test
    public void testSuspiciousNewLocation(){
        Transaction transaction1 = Transaction.builder()
                .location("102.38.248.0")
                .build();
        Transaction transaction2 = Transaction.builder()
                .location("102.38.248.0")
                .build();
        Transaction transaction3 = Transaction.builder()
                .location("103.18.238.0")
                .transactionTime(LocalDateTime.of(2023, 6, 26, 15, 0))
                .build();
        UserTransactions userTransactions = UserTransactions.builder()
                .transactionList(Arrays.asList(transaction1, transaction2))
                .build();
        kieSession.insert(transaction3);
        kieSession.insert(userTransactions);
        kieSession.getAgenda().getAgendaGroup("locationNew").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(transaction3.isSuspicious());
    }

    @Test
    public void testSuspiciousNightNotFridayDay(){
        Transaction transaction = Transaction.builder()
                .transactionTime(LocalDateTime.of(2023, 6, 26, 15, 0))
                .build();
        kieSession.insert(transaction);
        kieSession.getAgenda().getAgendaGroup("locationIndependent").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(!transaction.isSuspicious());
    }

    @Test
    public void testSuspiciousNightFridayNight(){
        Transaction transaction = Transaction.builder()
                .transactionTime(LocalDateTime.of(2023, 6, 24, 2, 0))
                .build();
        kieSession.insert(transaction);
        kieSession.getAgenda().getAgendaGroup("locationIndependent").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(!transaction.isSuspicious());
    }

    // Suspicious true
    @Test
    public void testSuspiciousNightNotFridayNight(){
        Transaction transaction = Transaction.builder()
                .transactionTime(LocalDateTime.of(2023, 6, 26, 2, 0))
                .build();
        kieSession.insert(transaction);
        kieSession.getAgenda().getAgendaGroup("locationIndependent").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(transaction.isSuspicious());
    }

    @Test
    public void testLarge2TimesBiggerThanMaxNotBig(){
        Transaction transaction1 = Transaction.builder()
                .amount(2000)
                .build();
        Transaction transaction2 = Transaction.builder()
                .amount(1500)
                .build();
        Transaction transaction3 = Transaction.builder()
                .amount(3500)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 15, 0))
                .build();
        UserTransactions userTransactions = UserTransactions.builder()
                .transactionList(Arrays.asList(transaction1, transaction2))
                .build();
        kieSession.insert(transaction3);
        kieSession.insert(userTransactions);
        kieSession.getAgenda().getAgendaGroup("locationIndependent").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(!transaction3.isSuspicious());
    }

    // Suspicious true
    @Test
    public void testLarge2TimesBiggerThanMax(){
        Transaction transaction1 = Transaction.builder()
                .amount(2000)
                .build();
        Transaction transaction2 = Transaction.builder()
                .amount(1500)
                .build();
        Transaction transaction3 = Transaction.builder()
                .amount(5000)
                .transactionTime(LocalDateTime.of(2023, 6, 26, 15, 0))
                .build();
        UserTransactions userTransactions = UserTransactions.builder()
                .transactionList(Arrays.asList(transaction1, transaction2))
                .build();
        kieSession.insert(transaction3);
        kieSession.insert(userTransactions);
        kieSession.getAgenda().getAgendaGroup("locationIndependent").setFocus();
        kieSession.fireAllRules();
        Assert.isTrue(transaction3.isSuspicious());
    }

    public KieContainer kieContainer() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("bank", "rules", "0.0.1-SNAPSHOT"));
        KieScanner kScanner = ks.newKieScanner(kContainer);
        kScanner.start(10_000);
        return kContainer;
    }
}
