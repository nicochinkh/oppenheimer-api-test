Feature: Verify repeated natid won't create a new hero

  @regression
  Scenario: Verify repeated natid won't create a new hero
    Given A request with last natid
    Then Check hero creation http response with repeated natid error message