# LurkBaitCatchStats [![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0) [![Discord](https://img.shields.io/discord/418432278113550337.svg?logo=discord&logoWidth=18&colorB=7289DA)](https://discordapp.com/invite/eHBxk5q)
LurkBaitCatchStats is a simple Java program for [LurkBait](https://store.steampowered.com/app/2767520/LurkBait_Twitch_Fishing/) to allow streamers to tabulate and visualize their users' catches!

Program Features (See images below):
- Individualized reward tracking
- Date range restrictions for monthly tracking
- Supports making multiple tables at once (*no really... AS MANY AS YOU WANT!*)
- Table sort ordering based on alphabetical user or total reward counts
- More to come!

## Program Use Scenario
The primary reason I coded this was to allow streamers to create rewards for users based on how much they've fished! For example, I set up a custom catch (a Lucky Ticket). I wanted to give the viewer with the MOST lucky tickets at the end of each month a free subscription. But how do you track such stats with just LurkBait and Twitch bots??? Enter LurkBaitCatchStats!

## Program Images

### Main Landing Page
![mainpage](https://github.com/CoolLord22/LurkBaitCatchStats/assets/16751239/1494b731-91fe-4f22-aeb1-e179eeb58532)
### File Directory Browser
![directory browser](https://github.com/CoolLord22/LurkBaitCatchStats/assets/16751239/86d8f979-a3e5-4039-8ef7-79c040ccfe47)
### Date Picker Element
![date picker](https://github.com/CoolLord22/LurkBaitCatchStats/assets/16751239/b9d2ea14-5ee7-429a-ac09-c1d6448f7825)
### Selecting which Rewards to Tabulate Data for
![multireward selection](https://github.com/CoolLord22/LurkBaitCatchStats/assets/16751239/f528ad10-838c-448d-be54-5d722693825d)
### Table sorted based on Reward Stats
![sorted table](https://github.com/CoolLord22/LurkBaitCatchStats/assets/16751239/58968a67-1541-431b-a590-62a388ae1a91)
### Table sorted based on Username
![sorted table username](https://github.com/CoolLord22/LurkBaitCatchStats/assets/16751239/62573600-793f-467d-abd8-1aa5e3bced25)
### Multiple tables generated, All Time Stats vs Monthly Stats
![multitable date range](https://github.com/CoolLord22/LurkBaitCatchStats/assets/16751239/a4260985-14d3-4f86-a5c0-e54003d865e7)


## Running the program
The easiest way to run the program is to first download the necessary `.jar` and `run.bat` files from the [Releases](https://github.com/CoolLord22/LurkBaitCatchStats/releases) tab.

Extract both of these files into any directory of your choice. 

Double click `run.bat` to start the program. View images attached below for further usage details!

## Building the jar with Maven
These instructions assume you have already forked and/or cloned the project and have on your computer.

Then build using your IDE with the Maven targets `clean install` or:

    $ mvn clean install

To actually run the generated jar file, open a command prompt/PowerShell window in the directory containing the jar and run

    $ java -jar LurkBaitCatchStats-1.0-SNAPSHOT.jar

The same line `java -jar %PATH_TO_JAR%/LurkBaitCatchStats-1.0-SNAPSHOT.jar` can be added to a `run.bat` file so it is not necessary to open a command prompt window each time.

When attempting to run the program, ensure you are targetting the `LurkBaitCatchStats-1.0-SNAPSHOT.jar`. The `original-LurkBaitCatchStats-1.0-SNAPSHOT.jar` file does **NOT** contain the dependencies needed for the program to run.

## Contact Us
If you have a problem please create a ticket and include the error (if there was one). Feel free to join the Discord Server linked above! I'm super active there and tend to respond faster on it.

## Donation Link
If you appreciate this resource, consider donating and showin' some love <3 

[![ko-fi](https://www.ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/O4O425D12)
