Interview Test App
======================


## Prerequisites

In order to run this project you need the following:
- Android Studio 4.1.0 or better
- Gradle 6.5 or better
- JDK 1.8
- [Android SDK](https://developer.android.com/studio/index.html)



# Architecture

The project implemented with
[MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)
and [The Clean Architecture](https://www.raywenderlich.com/3595916-clean-architecture-tutorial-for-android-getting-started)


## Domain
We have repository and entities here I didn't implement UseCase or Interact to
reduce boilerplate codes. Adding UseCases is okay, if we know we have lots of pure
logic implementation or we want to have some kind of Interacts and share logic
between different modules but in this case we don't have enough evidence for that.
The entity keyword has been used as a suffix to describe data classes in domain

**Downside:** using repository interface instead of abstract class cause things like coroutine dispatcher
and execute function are visible for feature level modules.

### Refactor Strategy:

Put all domain level logic in a function when input received by both ViewModel
and repository.
later if we find out we have lots of domain level logic we could add this layer easily.


## Data

We have repository and data source implementations.
Also we have our models for network with Response and Request suffix.
Creating different models for data layers cause lots of boilerplate but we can develop tools to
generate that automatically. If we don't have time we could use entity models. In my experience
importing some android related SDKs to domain layer cause other careless import and make testing hard.
We could also control it with lints and merge request templates for review.

**Note:** I've used retrofit interface as DataSource. We could create another layer here but it's
possible we violate YANG principle in lots of scenarios.

**Advantage:** we can easily change our data models and change API or migrate to other technologies
like GRPC. In this scenario we have


### Refactor Strategy:

We can set some guidelines in the beginning.
So later if we develop a tool for that we would migrate easily and without change to domain layer entities



## Ui Modules

With this strategy we reduce build time in long term and in CI/CD.
We avoid unwanted access to other features class and we can review more effectively. and later if we want have different apps.

**Note:** We should change our dependency direction with the app module
if we want to have Instant app with Android App Bundle.
BTW it seems that it's not hard to refactor this type to that.

# Technologies And Decisions
In this section I'll try to explain reason behind some of my decisions

## Hilt

We Use hilt because of less boilerplate codes. Also, generally I think it's easier for integration tests as well.
For ViewModel injection there's alpha library from google
that I'm not sure if it's stable or not.
So I've used some boilerplate codes but we can easily refactor to use that library

**Create Hilt modules in the related gradle modules:**
This scenario could help us, if we want to reuse our gradle modules in different apps or
if we don't want to mock them in some integration tests.

**Create Hilt modules in the app module:**
This scenario is easier to test for component testing but it needs more boilerplate codes.


## Coroutine

For the threading and observing I could use technologies like Livedata with Thread pools, Rx and Coroutine.

It's hard to handle things like back pressure
or debounce with Livedata and ThreadPools.

On the other hand, Coroutine is lighter than Rx. It's native and somehow
it's easier to test because Google has created some libraries for that.


## Test

I wrote 2 tests for the repository in my data layer and one unit test for my view model in ui layer.
It tries to check multiple cases using Mock and JUnit.

I tried to use stubs. [Stubs do better in terms of state verification](https://martinfowler.com/articles/mocksArentStubs.html)
, but I added Mockk for Repository test and show how we can Leverage behavior verification tools in a good way.

