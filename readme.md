# Codex Naturalis - board game
![codex](src/main/resources/graphics/CODEX_wallpaper_1080.jpg)

## Built by
- Lorenzo Bardelli 10831941 `lorenzo1.bardelli@mail.polimi.it` 
- Pietro Pizzoccheri 10797420 `pietro.pizzoccheri@mail.polimi.it` 
- Alex Hathaway 10829946 `alex.hathaway@mail.polimi.it` 
- Mattia Brianti 10773859 `mattia.brianti@mail.polimi.it` 

## Functionalities
|     Feature      | state |
|:----------------:|:-----:|
| complete ruleset |   ✅   |
| socket handling  |   ✅   |
|  GUI interface   |   ✅   |
|  CLI interface   |   ✅   |
|   in-game chat   |   ✅   |

[documentation](https://github.com/omgbarde/IS24-LB04/tree/master/deliverables/Archive)

## Main testing progress
(% methods covered)\
test model:\
  🟦🟦🟦🟦🟦🟦🟦⬜⬜ 83% \
test view:\
  🟦🟦🟦🟦🟦🟦🟦⬜⬜⬜ 67% \
test controller:\
  🟦🟦🟦🟦⬜⬜⬜⬜⬜⬜ 39%\
test observer:\
  🟦🟦🟦🟦🟦🟦🟦🟦🟦🟦 100%\
overall:\
  🟦🟦🟦🟦⬜⬜⬜⬜⬜⬜ 43%

## Todo list
- [ ] make board resizable
- [ ] more testing

## Requirements
- A working LAN connection is required to play with multiple pc.
- OpenJDK 21 and Junit 4.13.1

## Getting started
1. first launch the serverApp on the desired port (omit arguments to use the default)
    ```sh
    java -jar serverApp.jar "port-number"
    ```
2. then launch the clients in the desired mode (omit arguments to play in GUI)\
   start CLI:
   
    ```sh
    java -jar codexClientApp.jar -cli
    ```
    
    start GUI:
  
    ```sh
    java -jar codexClientApp.jar -gui
    ```
3. lastly create or join a game
  - to create a game use the port displayed by the serverApp in the console.
  - to join a game use the correct ip address and port of the person hosting the game (the network interfaces are also displayed in the console by the serverApp)

## How to play
- [codex rulebook](https://github.com/omgbarde/IS24-LB04/blob/bd00eed9bb266829cce41ff64434404faad6cba2/src/main/resources/CODEX_Rulebook_IT.pdf)
- the controls are displayed in the view based on the graphic mode
