"""
Backend driver for NFL Score app
Scrapes and parses NFL game info from CBS scoreboard
Compiles and converts data in JSON format
Interfaces with AWS services for storage

:Source file dependencies:
 make_soup.py, parse_game.py, export_data.py,
 constants.py, s3_credentials.py

:Author: Evan Doran
:Email: evanrdoran@gmail.com
"""

import make_soup
import parse_game
import export_data


def main():
    games = list()
    for soup in make_soup.pull_games():
        game = parse_game.compile_game_data(soup)
        if game.game_data["game_valid"]:
            games.append(game)
    export_data.create_json_file(games)
    export_data.upload_data()


main()
