Feature: Verify the birthdate with time can be stored in the database and the hero can be created successfully

  @regression
  Scenario: Verify the birthdate with time can be stored in the database and the hero can be created successfully
    Given a hero creation request with a birthdate with time will be accepted by the hero creation api
    Then Check the correct hero data http response
    And Verify the new created correct hero from the working class heroes table record

