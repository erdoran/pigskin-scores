"""
Parses scraped data from CBS NFL scoreboard page to create Game objects

:Author: Evan Doran
:Email: evanrdoran@gmail.com
"""
# TODO: Determine specific Excpetions to catch instead of broad "except" blocks
# TODO: Logging errors for further review instead of just charging ahead
import datetime
from Game import Game
from constants import FINAL as FINAL
from constants import TODAY as TODAY
from constants import IN_PROGRESS as IN_PROGRESS
from constants import MONTHS as MONTHS


def set_game_status(game, game_soup):
    """
    Sets games status represented by integer value
    pregame = 0
    in progress = 1
    post game = 2
    :param game: instance of the game class
    :param game_soup: soup object containing data for single NFL game
    :return: None
    """
    try:
        # Uses BeautifulSoup module to search soup object for
        # explicit pre/postgame sections
        # If neither is present, game is in progress
        if not (game_soup.find("div", class_="game-status pregame")) is None:
            game.game_data["game_status"] = 0
        elif not (game_soup.find("div", class_="game-status postgame")) is None:
            game.game_data["game_status"] = 2
        else:
            game.game_data["game_status"] = 1

    except:
        game.game_data["game_valid"] = False


def set_team_names(game, game_soup):
    """
    Sets names of teams playing in game object
    :param game: instance of the game class
    :param game_soup: soup object containing data for single NFL game
    :return: None
    """
    try:
        team_names = game_soup.find_all("a", class_="team helper-team-name")
        game.game_data["away_team"] = team_names[0].get_text()
        game.game_data["home_team"] = team_names[1].get_text()

        # Change "Football Team" to "Washington"
        if game.game_data["away_team"] == "Football Team":
            game.game_data["away_team"] = "Washington"
        elif game.game_data["home_team"] == "Football Team":
            game.game_data["home_team"] = "Washington"

    except:
        game.game_data["game_valid"] = False


# TODO: ERROR - PREGAME FORMAT DOES NOT HAVE RECORDS LISTED UNTIL PREVIOUS WEEK GAMES ARE ALL FINAL ~
#  THEY ARE LISTED CORRECTLY IN THE PREVIOUS WEEK THOUGH. Current solution is try to set them, if they aren't there than
#  not my problem. Also, records don't seem to update immediately once a game goes final
#  BECAUSE OF THIS - This method is NOT error checked, FRONT END WILL BUILD AROUND POSSIBILITY OF NOT HAVING RECORDS
def set_team_records(game, game_soup):
    """
    Sets teams records
    :param game: instance of the game class
    :param game_soup: soup object containing data for single NFL game
    :return: None
    """
    try:
        records = game_soup.find_all("span", class_="record")
        game.game_data["away_record"] = records[0].get_text()
        game.game_data["home_record"] = records[1].get_text()

    except:
        # CBS' inability to update records in a timely fashion
        # makes this a non invalidator
        # Work around in the front end for now
        game.game_data["away_record"] = None
        game.game_data["home_record"] = None


def set_game_start_time(game, game_soup):
    """
    Sets the day, date, month, and kickoff time for games  in pregame state
    Sets these fields to "IN PROGRESS" for ongoing games
    Sets these fields to "FINAL" once game is final
    :param game: instance of the game class
    :param game_soup: soup object containing data for single NFL game
    :return: None
    """

    try:
        # Pregame conditional
        if game.game_data["game_status"] < 1:
            start_time = \
                game_soup.find("span", class_="game-status pregame-date")
            start_time = start_time.get_text().strip().split()

            # Conditional because source does not list weekday if game is scheduled that day
            if len(start_time) > 2:
                game.game_data["game_day"] = start_time[0]
                game.game_data["game_month"] = start_time[1]
                game.game_data["game_date"] = start_time[2][:-1]  # Remove trailing comma on date
                game.game_data["start_time"] = start_time[3]

            else:
                game.game_data["game_day"] = TODAY
                game.game_data["game_month"] = \
                    MONTHS[datetime.datetime.today().month]
                game.game_data["game_date"] =\
                    str(datetime.datetime.today().day)  # Convert to string
                game.game_data["start_time"] = start_time[0]

        # In progress conditional
        elif game.game_data["game_status"] == 1:
            game.game_data["game_day"] = IN_PROGRESS
            game.game_data["game_month"] = IN_PROGRESS
            game.game_data["game_date"] = IN_PROGRESS
            game.game_data["start_time"] = IN_PROGRESS

        # Post game conditional
        else:
            game.game_data["game_day"] = FINAL
            game.game_data["game_month"] = FINAL
            game.game_data["game_date"] = FINAL
            game.game_data["start_time"] = FINAL

    except:
        game.game_data["game_valid"] = False


def set_game_broadcast_network(game, game_soup):
    """
    Sets the broadcast network the game is televised on
    :param game: instance of the game class
    :param game_soup: soup object containing data for single NFL game
    :return: None
    """
    try:
        # Broadcast network is unlisted/irrelevant post game
        if game.game_data["game_status"] < 2:
            game.game_data["broadcast_network"] = \
                game_soup.find("div", class_="broadcaster").get_text().strip()
            # CBS is listed via a non parsable image with their logo
            # Other networks are just text listed
            # If network, is not NFLN, FOX, or ESPN, it is CBS
            if not any(letter in ['N', 'F'] for letter in game.game_data["broadcast_network"]):
                game.game_data["broadcast_network"] = "CBS"
        else:
            game.game_data["broadcast_network"] = FINAL

    except:
        game.game_data["game_valid"] = False


def set_game_scores(game, game_soup):
    """
    Sets game score
    :param game: instance of the game class
    :param game_soup: soup object containing data for single NFL game
    :return: None
    """
    try:
        if game.game_data["game_status"] > 0:
            scores = game_soup.find_all("td", class_="total-score")
            game.game_data["away_score"] = scores[0].get_text()
            game.game_data["home_score"] = scores[1].get_text()

    except:
        game.game_data["game_valid"] = False


# TODO: Find out what happens at halftime
def set_game_clock(game, game_soup):
    """
    Sets the quarter and game time for the game
    :param game: instance of the game class
    :param game_soup: soup object containing data for single NFL game
    :return: None
    """
    try:
        # Conditional for games in progress
        if game.game_data["game_status"] == 1:
            game_time = game_soup.find("div", class_="game-status emphasis")\
                .get_text().strip().split()
            game.game_data["quarter"] = game_time[0]
            game.game_data["time"] = game_time[1]

            # Append "quarter" after 1st, 2nd, 3rd, or 4th
            if any(string in ["1st", "2nd", "3rd", "4th"]
               for string in game.game_data["quarter"]):
                game.game_data["quarter"] = game.game_data["quarter"] + " Quarter"

        # Conditional for post game
        elif game.game_data["game_status"] == 2:
            game.game_data["quarter"] = FINAL
            game.game_data["time"] = FINAL

    except:
        game.game_data["game_valid"] = False


def compile_game_data(game_soup):
    """
    Creates game object and calls methods to parse data used
    to set game object fields
    :param game_soup: BeautifulSoup object containing web scraped data
    :return: Filled in game object
    """
    game = Game()
    set_game_status(game, game_soup)
    set_team_names(game, game_soup)
    set_team_records(game, game_soup)
    set_game_start_time(game, game_soup)
    set_game_broadcast_network(game, game_soup)
    set_game_scores(game, game_soup)
    set_game_clock(game, game_soup)
    return game
