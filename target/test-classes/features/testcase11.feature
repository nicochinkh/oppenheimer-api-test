Feature: Verify the birthdate is a future date will be rejected by the hero creation api

  @regression
  Scenario: Verify the birthdate is a future date will be rejected by the hero creation api
    Given a hero creation request with birthdate which is a future date will be rejected by the hero creation api
    Then Check hero creation http response with invalid birthdate error message
