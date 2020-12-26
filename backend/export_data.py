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
    If game status has changed, initiates upload
    :param games: list of game objects
    :return: Creates game_data.json - no return value
    """
    json_obj = list()
    for game in games:
        json_obj.append(game.game_data)
    json_obj = json.dumps(json_obj, indent=4)
    if is_upload_needed(json_obj, FILE):
        with open(FILE, 'w') as file:
            file.write(json_obj)
        upload_data()


def is_upload_needed(json_obj, json_file):
    """
    Compares saved JSON file to newly created JSON object to determine if S3 upload is needed
    :param json_obj: Game data JSON object created in this execution
    :param json_file: Game data JSON file created in previous execution
    :return: Boolean determination of whether upload is needed
    """
    try:
        with open(json_file, 'r') as cached_json:
            cached_obj = json.dumps(json.load(cached_json), indent=4)
            if json.dumps(cached_obj, sort_keys=True) == json.dumps(json_obj, sort_keys=True):
                return False
            else:
                return True
    except (IOError, FileNotFoundError):
        return True


def upload_data():
    """
    Uploads game_data.json file to S3 bucket with defined permissions and parameters
    :return: None
    """
    client = boto3.client('s3',
                          aws_access_key_id=ACCESS_KEY_ID,
                          aws_secret_access_key=SECRET_ACCESS_KEY)
    client.upload_file(FILE, BUCKET, UPLOAD_FILE_KEY)

