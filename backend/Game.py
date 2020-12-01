"""
Game class to hold information pertaining to an NFL game

:Author: Evan Doran
:Email: evanrdoran@gmail.com
"""


class Game:

    def __init__(self):
        self.game_data = dict()

        self.game_data["game_valid"] = True  # Boolean flag for whether game object is valid
        self.game_data["game_status"] = None  # Integer value (0,1,2) denoting game status (pre/in progress/post)

        self.game_data["start_time"] = None  # Kickoff time for game
        self.game_data["game_day"] = None  # Day of week game is scheduled
        self.game_data["game_month"] = None  # Month game is scheduled
        self.game_data["game_date"] = None  # Date game is scheduled
        self.game_data["broadcast_network"] = None  # Network game is being broadcast on

        self.game_data["home_team"] = None  # Home team name
        self.game_data["away_team"] = None  # Away team name

        self.game_data["home_record"] = None  # Home team record
        self.game_data["away_record"] = None  # Away team record

        self.game_data["home_score"] = None  # Home team score
        self.game_data["away_score"] = None  # Away team score

        self.game_data["quarter"] = None  # Current game quarter
        self.game_data["time"] = None  # Current game time remaining in quarter
