Feature: Verify the taxPaid with long decimal digits will be rejected by the hero creation api

  @regression
  Scenario: Verify the taxPaid with long decimal digits will be rejected by the hero creation api
    Given a hero creation request with long decimal digits taxPaid will be rejected by the hero creation api
    Then Check hero creation http response with invalid taxPaid error message

