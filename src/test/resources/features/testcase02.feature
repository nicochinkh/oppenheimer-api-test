Feature: Verify sending correct request can create a single workingclass hero successfully(nullable fields with values)

  @regression
  Scenario: Send correct request can create a single workingclass
    Given A request with correct fields with null deathDate and browniePoints
    Then Check the correct hero data http response
    And Verify the new created correct hero from the working class heroes table record