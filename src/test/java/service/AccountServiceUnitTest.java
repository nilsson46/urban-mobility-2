package service;

import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.repository.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AccountServiceUnitTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    public void setup(){
        account = Account.builder()
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                //.isPaymentConfirmed(true)
                .paymentHistory(0)
                //.activeOrders(0)
                .build();
    }

    @Test
    public void Should_ReturnAccountDetails_When_CreateAccount(){

         //Arrange

        // Mock the behavior of accountRepository.save(account) to return the account
        given(accountRepository.save(account)).willReturn(account);

        // Act
        // Call the createAccount method to create an account and get the savedAccount
        Account savedAccount = accountService.createAccount(account);

        // Assert
        // Check that the savedAccount is not null, indicating a successful account creation
        assertThat(savedAccount).isNotNull();

        // Verify that the accountRepository's save method was called exactly once with the provided account
        verify(accountRepository, times(1)).save(account);
        //TESTING
        // Verify that there were no other interactions with the accountRepository
       verifyNoInteractions(accountRepository);

    }
    @Test
    public void Should_ThrowIllegalArgumentException_IfUsernameAlreadyExists(){
        //Arrange
        given(accountRepository.findByUsername(account.getUsername())).willReturn(account);

        //Act
        assertThrows(IllegalArgumentException.class,
                () -> accountService.createAccount(account));

        //Assert
        verify(accountRepository, times(1)).findByUsername(account.getUsername());
    }

    @Test
    public void Should_ThrowIllegalArgumentException_IfEmailAlreadyExists(){

    }

    //Check so the email and username are already in the database.
    //Check so a parameter is not null.
}