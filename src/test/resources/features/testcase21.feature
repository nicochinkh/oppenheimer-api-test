Feature: Verify the taxPaid is a negative number will be rejected by the hero creation api

  @regression
  Scenario: Verify the taxPaid is a negative number will be rejected by the hero creation api
    Given a hero creation request with the taxPaid is a negative number will be rejected by the hero creation api
    Then Check hero creation http response with negative taxPaid error message

