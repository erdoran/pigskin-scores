"""
Support methods for converting Game objects into JSON format and interfacing
with AWS services via API calls

:Author: Evan Doran
:Email: evanrdoran@gmail.com
"""

import json
import boto3
from s3_credentials import ACCESS_KEY_ID as ACCESS_KEY_ID
from s3_credentials import SECRET_ACCESS_KEY as SECRET_ACCESS_KEY
from s3_credentials import BUCKET as BUCKET
from s3_credentials import UPLOAD_FILE_KEY as UPLOAD_FILE_KEY
from s3_credentials import FILE as FILE


def create_json_file(games):
    """
    Takes list of game objects and converts each game instance into JSON format
    :param games: list of game objects
    :return: Creates game_data.json - no return value
    """
    json_obj = list()
    for game in games:
        json_obj.append(game.game_data)
    json_obj = json.dumps(json_obj, indent=4)
    with open("game_data.json", 'w') as file:
        file.write(json_obj)


def upload_data():
    """
    Uploads game_data.json file to S3 bucket with defined permissions and parameters
    :return: None
    """
    client = boto3.client('s3',
                          aws_access_key_id=ACCESS_KEY_ID,
                          aws_secret_access_key=SECRET_ACCESS_KEY)
    client.upload_file(FILE, BUCKET, UPLOAD_FILE_KEY)

