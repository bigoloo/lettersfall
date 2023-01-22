# LettersFall
![](/app/src/main)
## Game Description
This application is the bi-langauge transaltion game. user should select a langauage (English / Spanish) then A sequence of word in selected language are shown to user (Each per screen) and a word in another language is falling from top to bottom. user has limited amount a time to choose this word is correct translation or not.
At the end User can see the counts of Correct, wrong and un answered questions and also chosen language.
### Win function
it is simple, if user clicked on Correct button it means that translation is correct and if he clicks on Wrong button it means that translation is wrong.

## Technical Information
### Specification of App
Libraries and technologies of application
- Compose
    - Compose UI Test
- Coroutine Flow
- Version Catalog
- LeakCanary
- Koin
- Kotlin Serialization

### Atchitecture

This application is built based on Clean Archituecture. and in UI pattern I use MVI for screens.
For answering Question UseCase I choose to use Redux to handle answering question

![](/docs/lettersfall-redux.png)




### Next Imporvement
- Save Application State in database (Room)
- Add UI test for Animation in Compose
- Improve Animation in UI
- Refactor UI
- Add Github Action to project (to run tests and deploy to GooglePlay Using Fastlane)
- Save Statistics