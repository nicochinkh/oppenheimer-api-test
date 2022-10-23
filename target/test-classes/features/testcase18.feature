Feature: Verify the salary with long decimal digits will be rejected by the hero creation api

  @regression
  Scenario: Verify the salary with long decimal digits will be rejected by the hero creation api
    Given a hero creation request with with long decimal digits salary will be rejected by the hero creation api
    Then Check hero creation http response with invalid salary error message

