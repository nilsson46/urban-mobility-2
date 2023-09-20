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

import java.util.Optional;

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
                .id(1L)
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .isPaymentConfirmed(true)
                .paymentHistory(0)
                .activeOrders(0)
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

        // Verify that there were no other interactions with the accountRepository
        //verifyNoInteractions(accountRepository);

    }

   /* @Test
    public void Should_ReturnUpdatedDetails_When_UpdateAccount(){
        // Arrange
        String newUsername = "ishige";

        // Create an Account object with the same ID
        Account existingAccount = new Account();
        existingAccount.setId(account.getId());
        existingAccount.setUsername(account.getUsername()); // Set other properties as needed

        // Act
        account.setUsername(newUsername);
        Account updatedAccount = accountService.updateAccount(existingAccount, newUsername);

        // Assert
        assertThat(updatedAccount).isNotNull();  // Ensure updatedAccount is not null
        assertThat(updatedAccount.getUsername()).isEqualTo(newUsername);

        // Verify
        verify(accountRepository, times(1)).findById(account.getId());
        verify(accountRepository, times(1)).save(updatedAccount);
    } */

    @Test
    public void Should_ReturnUpdatedAccount_When_UpdateUsername() {
        // Arrange
        String newUsername = "ishige";

        // Set the ID of the account to match the expected value (1L)
        account.setId(1L);

        // Mock the behavior of accountRepository.findById to return the account
        given(accountRepository.findById(account.getId())).willReturn(Optional.of(account));

        // Mock the behavior of accountRepository.save to return the updated account
        given(accountRepository.save(any(Account.class))).willAnswer(invocation -> {
            Account updatedAccount = invocation.getArgument(0);
            // Simulate the save operation by updating the account's username
            account.setUsername(updatedAccount.getUsername());
            return account;
        });

        // Act
        Account updatedAccount = accountService.updateAccount(account, newUsername);

        // Assert
        assertThat(updatedAccount).isNotNull();
        assertThat(updatedAccount.getUsername()).isEqualTo(newUsername);

        // Verify that the accountRepository's findById method was called exactly once with the provided ID
        verify(accountRepository, times(1)).findById(account.getId());
        // Verify that the accountRepository's save method was called exactly once with the updated account
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void Should_ReturnAccount_When_FindAccountById() {
        // Arrange
        Long accountId = 1L;

        // Mock the behavior of accountRepository.findById to return the account
        given(accountRepository.findById(accountId)).willReturn(Optional.of(account));

        // Act
        Account foundAccount = accountService.getAccountById(accountId);

        // Assert
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getId()).isEqualTo(accountId);

        // Verify that the accountRepository's findById method was called exactly once with the provided ID
        verify(accountRepository, times(1)).findById(accountId);
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


}