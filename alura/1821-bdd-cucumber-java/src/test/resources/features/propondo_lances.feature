Feature: Proposing bids
  Scenario: Proposing a single valid bid
    Given a valid bid
    When propose a bid
    Then the bid is accepted