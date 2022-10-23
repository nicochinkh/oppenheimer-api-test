Feature: Verify blank browniePoints will be accepted by the hero creation api

  @regression
  Scenario Outline: Verify blank browniePoints will be accepted by the hero creation api
    Given a hero creation request with Verify blank browniePoints <blankBrowniePoints> will be accepted by the hero creation api
    Then Check the correct hero data http response
    And Verify the new created correct hero from the working class heroes table record
    Examples:
      | blankBrowniePoints |
      | ""                 |
      | " "                |

