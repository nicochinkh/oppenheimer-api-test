Feature: Verify the deathDate with a future date will be rejected by the hero creation api

  @regression
  Scenario: Verify the deathDate with a future date will be rejected by the hero creation api
    Given a hero creation request with a future date will be rejected by the hero creation api
    Then Check hero creation http response with invalid deathDate error message

