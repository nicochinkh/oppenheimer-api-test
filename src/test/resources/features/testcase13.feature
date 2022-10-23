Feature: Verify the deathDate with time will be accepted by the hero creation api

  @regression
  Scenario: Verify the deathDate with time will be accepted by the hero creation api
    Given a hero creation request with the deathDate with time will be accepted by the hero creation api
    Then Check the correct hero data http response
    And Verify the new created correct hero from the working class heroes table record
