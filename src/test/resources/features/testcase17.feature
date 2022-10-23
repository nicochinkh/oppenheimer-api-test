Feature: Verify the salary is a negative number will be rejected by the hero creation api

  @regression
  Scenario: Verify the salary is a negative number will be rejected by the hero creation api
    Given a hero creation request with the salary is a negative number will be rejected by the hero creation api
    Then Check hero creation http response with negative salary error message

