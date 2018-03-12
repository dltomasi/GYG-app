
============================

Introduction
-------------

The app was made using MVVM architecture and reactive programming.

The UI is very simple, I tried to make it similar to the website.
The app works offline as expected. It stores data in a database after it gets it from web. And also loads it to memory as well, so we dont need to query on database every time using Repository Pattern.

The app is very modular and each class can be tested separately. Unit tests were done for view model and repository using mockito.



### Technologies
Kotlin, Databing, Retrofit, Rx, Mockito, Dagger2, Room

### Next steps
If I had more time I would implement filters in the app by date and rating.
Also would allow user to change the page size, now fixed in 10 results per page.
On testing I would cover more cases and implement UI tests using Roboletric or Espresso.
