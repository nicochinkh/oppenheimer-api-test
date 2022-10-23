Feature: Verify the wrong format taxPaid will be rejected by the hero creation api

  @regression
  Scenario: Verify the wrong format taxPaid will be rejected by the hero creation api
    Given a hero creation request with the wrong format taxPaid will be rejected by the hero creation api
    Then Check hero creation http response with invalid format taxPaid error message

