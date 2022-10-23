Feature: Verify wrong format browniePoints will be rejected by the hero creation api

  @regression
  Scenario: Verify wrong format browniePoints will be rejected by the hero creation api
    Given a hero creation request with wrong format browniePoints will be rejected by the hero creation api
    Then Check hero creation http response with wrong format browniePoints message

