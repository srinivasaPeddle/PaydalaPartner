@AccountsRun
Feature: Partner_Login

  @Accounts
  Scenario: Login into Unity Dashboard
    Given I Navigate to Partner Page
    When I login to application with USER1_VALUE, PASSWORD1_VALUE
    And I logout from the application
