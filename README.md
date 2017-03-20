# Transactions viewer
Transactions viewer shows a list of products ordered alphabetically as well as its detail view whose transactions reflect their
corresponding exchange rate to GBP.

This project uses Material design with *Clean architecture* by means of *Model-View-Presenter (MVP)* with *Repository pattern*
as well as `Dagger 2` for *Dependency Injection*, trying to respect *SOLID principles* as much as possible.

### Screenshots
![Screencast UX](./art/transactions_viewer.gif)

## Overview
At this open-source project, it is decoupled between `android` and `domain` modules or high level layers.

This means, `android` is strongly coupled with the Android framework and `domain` is decoupled from it, and can be re-used for other purposes when
required as is purely *Java* based, but not framework coupled.

Inside the those modules, there are some good practices being employed, for instance:
- There is an implementation of the *repository pattern* with a JSON datasource (it could be extended to have others if required such as InternetDataSource and so on).

*Gson* library is the responsible of parsing all JSON information to Domain models, for this purpose and properly handling 
the different requests on background threads with a pool of threads which passes their use cases result and avoids to lock the
UI thread.

## Architecture design overview
The exchange between the different *layers* is as follows:
- **Repository layer**:
 - from the models coming from a concrete *data source* to the *Repository* (repository is the responsible of managing from 1 to n datasources) as well as processing information is there is some, since is Caching in RAM memory the information parsed from each DataSource coming from the 
 `rates1.json` and `transaction1.json`.
 - from the *Repository* to their associated *Interactor* (or use case)
- **Interactor layer**: from the *Interactor*, which is responsible of the *business logic* and communicating results to the *Presenter*
- **Presenter layer**: from the *Presenter*, which provides the final formatted info to a passive `View` from a UI element (fragments / activities).
Finally, this information would be passed through the UI thread.


### Material design
- This code test uses a wide range of Material design widgets from the Design support library such as:
- `AppBarLayout`, `CoordinatorLayout`, `Toolbar`, `RecyclerView` as well as Material theme styles.

### Features
- For an easy UI test, on the first screen, a switcher has been hold at the toolbar, to switch between `datasource1` (the biggest) and the minimal `datasource2`
- At the main screen a summary of products sorted alphabetically is shown, as well as the number of transactions per product as subtitle.
- Once clicked an item, the transactions detail view of an item is shown, which shows all transactions amounts with the initial currency
as well as its corresponding amount on `GBP`. At this detail view, there is a total amount of all transactions being added.
- Error handling integrated for `JSON` or IO issues or indicating no results.
- To avoid memory problems between Intents (from main to detail activities) there is the minimal set of information, required to retrieve new
info, such as the SKU identifier per product, the kind of dataset to look at, etc.
- There is a small amount of Unit test cases for the most critical parts: mainly the Graph logic, then repository as well as view interaction.

### SDK support
Support SDKs from **15** to **23**

# Disclosure - Libraries used
- [Dagger 2](http://google.github.io/dagger) for Dependency Injection
- [ButterKnife](http://jakewharton.github.io/butterknife) v6.1.0 for Views Injection
- [Gson](https://github.com/google/gson/blob/master/UserGuide.md) v2.2.4 for Json parsing
- [Mockito](http://site.mockito.org/) for Mocking artifacts
- [JUnit](http://junit.org/) for Unit testing Graph algorithm and its datastructure or rounding.

# References (special thanks) - those are the same I indicated at my personal blog ([Insights and projects](https://raulh82vlc.github.io/Movies-Finder)): 
- [Uncle Bob: The Clean Architecture](https://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html) by Uncle Bob
- [The Repository pattern](https://msdn.microsoft.com/en-us/library/ff649690.aspx) by Microsoft
- [Effective Android UI](https://github.com/pedrovgs/EffectiveAndroidUI) by Pedro Gomez
- [Android Clean Architecture](https://github.com/android10/Android-CleanArchitecture) by Fernando Cejas

### Contributions
Please read first [CONTRIBUTING](./CONTRIBUTING.md)

## About the author
**Raul Hernandez Lopez**,
- [Insights and projects (Personal projects blog)](https://raulh82vlc.github.io/Movies-Finder)
- [@RaulHernandezL (Twitter)](https://twitter.com/RaulHernandezL)
- [raul.h82@gmail.com](mailto:raul.h82@gmail.com)

# License
```
Copyright (C) 2017 Raul Hernandez Lopez

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
