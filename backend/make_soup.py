"""
Support scripts for building the correct link, making the request,
and creating the BeautifulSoup objects

:Author: Evan Doran
:Email: evanrdoran@gmail.com
"""

from bs4 import BeautifulSoup
import requests
import sys
import datetime
from datetime import date
from constants import WEEK_OFFSET as WEEK_OFFSET


# TODO: Making links for previous weeks? Current week could just use the base format URL
def create_link():
    """
    Creates the url to the CBS scoreboard based on the current date
    :return: url to current CBS scoreboard as a string
    """
    week_number = date.today().isocalendar()[1]  # Get current week number of the year
    if datetime.datetime.today().weekday() < 2:  # League week starts on Wednesday
        week_number = week_number - 1  # Decrease NFL week by one before Wednesday
    week_number = week_number - WEEK_OFFSET  # Determine the current NFL week
    url = "https://www.cbssports.com/nfl/scoreboard/all/2020/regular/%s/"\
          % week_number
    return url


def cook_soup(url):
    """
    Makes request with supplied url. If response code is
    valid, uses BS4 module to create soup object from response
    content. If response is invalid, log error and exit.
    :param url: URL to request info and create soup from
    :return soup: soup object of url parameter
    """
    response = requests.get(url)
    if response.status_code != 200:
        sys.exit("ERROR RETRIEVING REQUEST:" + str(response.status_code))
    soup = BeautifulSoup(response.content, "lxml")
    return soup


def pull_games():
    """
    Wrapper function for make_soup.py
    Creates link, makes request, makes BeautifulSoup object,
    and finds individual game instances
    :return: list of individual game instances to be parsed
    """
    url = create_link()
    soup = cook_soup(url).find_all("div", class_="live-update")
    return soup
