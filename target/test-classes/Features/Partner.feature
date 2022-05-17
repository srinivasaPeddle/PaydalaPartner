@DryRun
Feature: Partner_Login

  @Login1
  Scenario: Login into Unity Dashboard
    Given I Navigate to Partner Page
    When I login to application with USER1_VALUE, PASSWORD1_VALUE
    And I logout from the application

  @Login2
  Scenario Outline: Login into Unity Dashboard <Index>@<userName>
    Given I Navigate to Partner Page
    When I login to application with <userName>, <password>
    And I logout from the application

    Examples: 
      | Index | userName       | password  |
      |     1 |test4@test.com|DGdaXVpva|
      |     2 |test4@test.com|DGdaXVpva|
