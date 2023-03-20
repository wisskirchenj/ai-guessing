# IDEA EDU Course

Implemented in the <b>Java Core</b> Track of hyperskill.org JetBrain Academy.  

Project goal is to implement a simple AI-application (basic language processing) teaching the system
to guess an animal. Tree structures, node traversals are used.

## Technology / External Libraries

- Java 19
- Jackson Json, Xml and Yaml Serializing
- PicoCli - a great CLI library
- Lombok
- Slf4j
- Tests with Junit-Jupiter and Mockito
- Gradle 8.0.2

## Program description

The application will implement a simple interactive game where the computer will try to guess the animal,
that the person has in mind with the help of yes or no questions. During the game, the computer will extend
its knowledge base by learning new facts about animals and using this information in the next game.

## Project completion

[//]: # (Project was completed on 19.06.23.)

## Repository Contents

Sources for all project tasks (6 stages) with tests and configurations.

## Progress

27.02.23 Project started. Setup of build and repo with gradle on Kotlin basis.

06.03.23 Stage 1 completed. Simple language understanding, parsing and (random) reply generation.

06.03.23 Stage 2 completed. Ask for two different animals and a distinguishing fact, display learnings. 
First phrase creation and recognition.

11.03.23 Stage 3 completed. Full interactive guessing game, store learnings in binary decision tree,
not yet persisted.

14.03.23 Stage 4 completed. Deserialize and Store the decision tree on game end and reload at startup. Allow storage
formats JSON, YAML and XML specified by CLI-parameter and read in with picocli-library. Serialize Interface in tree
node using `@JsonSubTypes` and `@JsonTypeInfo`-annotations

20.03.23 Stage 5 completed. Add a main menu - split GuessingGame and Controller. Add third controller class, that
orchestrates the Knowledge Tree explore menu options. Implement 4 actions, that use - and inherit from - an abstract
Depth First Search implementing class providing a process hook. Add TreePrintAction.
