


             _______ .______        ______   .___  ___.     ________   _______ .______        ______   
            |   ____||   _  \      /  __  \  |   \/   |    |       /  |   ____||   _  \      /  __  \  
            |  |__   |  |_)  |    |  |  |  | |  \  /  |    `---/  /   |  |__   |  |_)  |    |  |  |  | 
            |   __|  |      /     |  |  |  | |  |\/|  |       /  /    |   __|  |      /     |  |  |  | 
            |  |     |  |\  \----.|  `--'  | |  |  |  |      /  /----.|  |____ |  |\  \----.|  `--'  | 
            |__|     | _| `._____| \______/  |__|  |__|     /________||_______|| _| `._____| \______/                                                                                                   
            .___________.  ______        ___     ___      
            |           | /  __  \      / _ \   / _ \   _ 
            `---|  |----`|  |  |  |    | (_) | | (_) | (_)
                |  |     |  |  |  |     \__, |  > _ <     
                |  |     |  `--'  |       / /  | (_) |  _ 
                |__|      \______/       /_/    \___/  (_)



Speed up your Testing with Live End-To-End Tests!

by Nick DiPatri
Comcast Corporation, Philadelphia

<https://github.com/ndipatri/ComposeZeroTo98/>







# Introduction

This is a code-lab and a presentation! After the brief introduction below, this code-lab proceeds with specific instructions to build up a working Android application that controls a remote Red Siren.  The code-lab also contains speaker notes so this can be done in front of an audience.

The focus of the code-lab is on testing, but in order for the code-lab to work, you will need to build the Red Siren.  It's based on a Particle Argon Microcontroller (https://store.particle.io/collections/wifi/products/argon-kit).  You can plug it in and it's ready.  Although the Red Siren is a bit more involved, the simple Particle Argon powered by USB can be used with the same Particle firmware code which is included here:

https://github.com/ndipatri/ComposeZeroTo98/tree/main/particleFirmware/workbench/redSiren.

The Particle is 'Arduino-based' so really any similar IOT device will do!


### If you are presenting this to others, here's your checklist:

1. Power-up siren and put it on desk (the app will crash if siren isn't on)
2. Use 'particle' CLI tool (particle token list) to make sure particleToken value in ParticleAPI.kt is still valid.
3. Restart IDE! (so it doesn't OOM during talk)
4. Code checked out

>Check out [Step 1 branch](https://github.com/ndipatri/ComposeZeroTo98/tree/step1_workingNoTests) if skipping building the init app.

5. Code deployed to phone BUT STOP THE APP.
6. Disconnect all real devices except test device.
7. IDE open, Project panel collapsed.
8. Use DeviceManager to WipeData and Cold Boot an API30 device (e.g. Pixel 5).
9. Turn off computer notifications
10. I recommend presenting this README on your IDE in Presentation Mode. All you're showing is the title screen.
11. Put IDE in Presentation Mode
12. Read this:

>Hello everyone, this is 'From Zero to 98...
>The prototype-to-production pipeline for mobile applications doesn’t always have the luxury of test-driven development. Even in large companies with institutionalized testing strategies we can find ourselves on a project that has very ***low unit test coverage***.  

>Today we’re going to learn how to use Espresso in new ways to ***quickly stand up live end-to end integration tests***.  These tests give us confidence so that we can safely move ahead with feature changes while we slowly ***build up our unit test coverage from 0 to 98 percent***, a process which can take weeks or months!

>This is a live-coding presentation and everything I'm goign to do is part of a code-lab available at the GitHub link shown here.

13. ***Take IDE OUT of Presentation mode and show Project***

13. Read this:

>I was trying to think of a fun application that we could use to demonstrate the concept of going from 0 to 98 as quickly as possible. ***Since this kinda sounds like speeding to me***, I thought that using a big red police siren might be appropriate.

>***So let's run this app and use it to control our red siren.***






# CodeLab

For each of the following steps (1,2,3,4,5,6), there is a branch in this repo that represents that step with all of its code changes completed.

The instructions below contain code snippets that have to be typed and 'live template' code that is inserted using IDE shortcuts.  For these to work, you MUST 'import' the [./liveTemplatesToImport.zip](./liveTemplatesToImport.zip) file.  Importing using File -> Manage IDE Settings -> Import.

To understand how Andoid Studio Live Templates work, read [this article](https://www.jetbrains.com/help/idea/using-live-templates.html)


## Step 0 - Create ‘Basic Compose Activity’

Begin with Android Studio and create a new ‘Basic Compose Activity’.  Create using package/project name: "com.ndipatri.iot.zeroto98"

## Step 1 - Use Dagger to inject ParticleAPI

### 1_1 - Update project Settings Gradle file (live template 'step1_1_gradle_project')

>For the most part, I’m not going to discuss Gradle file changes and focus on Kotlin.  So i’m just going to quickly modify >the project's gradle files to get what we need.  You can refer to this repo later to see what gradle changes were necessary for >this work.

Open project **settings.gradle** file and add an additional entry for the 'jitpack.io' repo to the dependencyResolutionManager section {using live template **step1_1**}


### 1_2 - Update app Gradle file (live template 'step1_2_gradle_app')

>We also need to modify the app gradle file

Open file **app/build.gradle** file and delete contents and {insert live template **step1_2**}


### 1_3 - Create ParticleAPI class (live template 'step1_3_api')

>We’re going to modify the MainActivity so it can talk to our siren.  We’re going to let the user see the current state of
>the siren and turn the siren on and off.

>In order to do that, it will need to create an API object that can make network calls to the Particle Cloud, which is
>how you control this siren

Create package **api** in **app/src/main/java/com/ndipatri/iot/zerioto98** directory.

Create **ParticleAPI.kt** class in that directory as shown:


```kotlin
class ParticleAPI {
     {insert live template step1_3}
}
```

In the above code just inserted, you will need to update the 'deviceId' and 'particleToken' with the real values from your own Particle Argon device and account.

>This ParticleAPI will be an external dependency to our MainActivity.

>Testing is about controlling external dependencies.  The first step in doing this is using a dependency injection
>framework such as Dagger.

>Now that we’ve defined our external dependency, the ParticleAPI, we need a way to create this dependency
>and make it available to whoever needs it in our app.

>A Dagger Component is a singleton container which will hold our ParticleAPI object. Let’s go ahead and
>create a Dagger Component for this app.”


### 1_4 - Create ApplicationComponent class and ApiModule (live template 'step1_4_dagger_module')

Create file **app/src/main/java/com.ndipatri.iot.zerioto98/ApplicationComponent.kt** as **AS INTERFACE**

(insert code below and THEN type the annotations at the top)

```kotlin
@Singleton
@Component(modules = [ApiModule::class])
interface ApplicationComponent {

      {insert live template step1_4} 

}
```

>We use Dagger modules to group our dependencies into various contexts.  In this case, we are putting our
>ParticleAPI into the ‘ApiModule’. If we had more APIs defined, they would also go in this module.


### 1_5 - Create ApplicationComponent code block (live template 'step1_5_dagger_component')

>Now we build the Dagger Component that will create and distribute this Module.

Insert Dagger Component component at the top of the class as shown:


```kotlin
class ApplicationComponent {

 {insert live template step1_5} 

   @Module
   class ApiModule() {
       @Provides
       @Singleton
       fun provideParticleAPI(): ParticleAPI {
           return ParticleAPI()
       }
       ...
   }
}
```

>The ‘component’ property is instantiated the first time we call ‘createIfNecessary()’ and returned.  Notice the ‘component’ property can be re-assigned.  This will be important later when we start writing tests.  Now our ApiModule and the ParticleAPI are available to be injected into our ‘MainActivity’”


### 1_6 - MainActivity uses injected ParticleAPI to update view and control siren (live template 'step1_6_firstFragment')

>First we need to update this app to allow for network calls.

Add the following to top of AndroidManifest.xml

```kotlin
<uses-permission android:name="android.permission.INTERNET" />
```


>Our MainActivity can now be changed to use our injected ParticleAPI to update status and control our siren.”
>
> Open file **app/src/main/java/com.ndipatri.iot.zerioto98/MainActivity.kt** file and delete contents and {insert live template **step1_6**}

>Now we display the current state of the siren at the top of the Activity and at the bottom of the Activity we have a toggle button.


Run the app to demonstrate!


### 1_7 - Use custom Espresso Test Runner (live template 'step1_7_customTestRunner')

>Before we start writing tests for this application, there's one more file we have to add.  This file configures our tests so they can use mocks, but we'll talk about that later.

Create the file **app/androidTest/java/com/ndipatri/iot/zeroTo98/EspressoTestRunner**

Add content using {insert live template **step1_7**}

### 1_8 - Rename the ExampleInstrumentedTest to MainActivityEspressoTest

>And let's rename our Espresso test file for clarity.

Rename the file **app/androidTest/java/com/ndipatri/iot/zeroTo98/ExampleInstrumentedTest** to **app/androidTest/java/com/ndipatri/iot/zeroTo98/MainActivityEspressoTest**








![Start Here](/startHere.png)

>Jump to here!
>
>If you don't want to build the app.
>Make sure you checkout
>[Step 1 Branch](https://github.com/ndipatri/ZeroTo98/tree/step1_workingNoTests)




## Step 2 - Write an unstable live end-to-end Espresso test


### 2_1 - Start with ActivityTestRule (live template 'step1_1_activityTestRule')

>Now let's assume our app has been deployed and distributed to many users. Let’s assume our app is ***way more complicated than what we just built and that it has NO unit tests***.  Let’s assume the app has all ***new developers who don't know the app***.

>***How do we proceed with feature development*** when we don’t know if we are breaking the app and its many wonderful features?

>We can ***protect the entire app with a Live end-to-end Espresso Test***. The test is 'Live' becuse we run it using real accounts and real hardware.  Settings up Mocks requires knowledge we just don't have as new developers on this team.  The test is 'End to End' because we are not mocking any of our apps components.

>This can be written without ANY knowledge of the app, but it ***requires some tricks*** as we'll soon learn.

>Let’s start by writing a very simple Espresso test.


Open **app/androidTest/java/com/ndipatri/iot/zeroTo98/MainActivityEspressoTest.kt**

Delete existing test.

Add a new **ActivityTestRule** using {insert live template **step2_1**}


```kotlin
@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    {insert live template step2_1}

}
```

>The ActivityTestRule is how Espresso starts a test.



### 2_2 - Write the test method (live template 'step2_2_testMethod')

Add new ‘showCurrentRedSirenState_off’ test

```kotlin
@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    @get:Rule
    val rule = createComposeRule()    
    
    @get:Rule
    val activityTestRule = ActivityTestRule(
        MainActivity::class.java,
        false,
        false
    )
    

    {insert live template step2_2}

}
```

>Here we have a simple test that asserts our MainActivity’s views when the siren is off.

>Remember with Espresso, our ***tests run in a separate thread*** from the app itself.  We need an explicit delay here because it takes a bit of ***time to query Particle for the current state of the siren***. If we knew more about the code itself, remember we’re new developers on this project, we could ‘synchronize’ this test with our app using ***Idling Resources***, but we’re trying to go from ***0 to 98 very quickly*** and don't know much about the code.  So we'll keep this explicit delay for our end-to-end test for now.

Make sure the siren is off and run the test.


>The big problem with our live test is that it depends on the ***current state*** of our real-world siren.  If the siren is on, this test will fail.

Turn on siren and re-run test to show it fails.


## Step 3 - Fixing Flaky End-To-End Tests

### 3_1 - Use while/try/catch block in our Espresso Test (live template 'step3_1_tryCatch')

>Now let's see how we can quickly improve this flaky End-To-End test!”

Open the file **app/src/androidTest/java/com/ndipatri/iot/zeroto98/MainActivityEspressoTest.kt**

As described below, delete part of the test and insert live code as shown:

```kotlin
@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest: TestCase() {

    @get:Rule
    val activityTestRule = ActivityTestRule(
        MainActivity::class.java,
           false,
           false
    )

    @Test  
    fun showCurrentRedSirenState_off() {

       activityTestRule.launchActivity(Intent())


       {Delete all code from test within this area and replace 
        with live template step3_1}

    } 
}
```

>Our original test lives inside of this new while/try/catch block.

>Inside of this retry block ***we’ve added something***.  We are now changing the state of the siren, by clicking the button, regardless of the current state.  So we are ***either turning the siren on or off, we don’t know***.  Then we test to see if the siren is in the desired state, which is ‘off’.

>If by chance, the siren is already off when we start this test, we will be turning it on and thus making the test fail.  Here is where the try/catch block comes into play.

>If the assertion fails, that is, if the siren isn't off, we run the block again.  The button will be pressed again, but this time the siren will be turned off and the test will succeed.  We ***keep trying until we get the desired result***.  If we can't get the test to pass after a couple tries, we assume the test is legitimately failing.

***Uninstall the app before running test.***

Run the test and demonstrate that it succeeds regardless of the state of the siren.

>In essence, we’re using ***‘short polling’*** to wait for our external system reach a known state.  This technique is ***CPU intensive and takes more time*** , but we don’t care because this is a test and ***NOT production code***.  
>The bottom line we are now testing our code and not the state of our external system.

>***Now we have live end-to-end user test coverage that is reliable. Our Android developers can now work on new features with confidence.***

>The problem with this test is ***brittle*** because it depends on ***real external dependencies*** - our siren and the Particle Cloud that controls it.  If anything breaks with these external dependencies, our test fails and we then have to investigate and waste time.

>Understand that testing is about ***controlling external dependencies***.  With this live test, we have no control over our external dependencies.

>Let’s assume that ***some time has passed*** and we now can spend a few moments converting this from a live end-to-end test to a mock end-to-end test. We’ll need to ***mock our external dependency*** which in this case is the network call to the Particle Cloud.

>I’m still calling it an ‘end-to-end’ test because we want to ***mock NOTHING internal*** to our application, just the network call itself.

>Let’s take a ***look back at the ParticleAPI*** dependency that we’re injecting into our MainActivity because this is where we make our network call.


## Step 4 - Look at ParticleAPI in DaggerComponent


Open up the file **app/src/main/java/com.ndipatri.iot.zerioto98/ApplicationComponent.kt**

>The ParticleAPI dependency is what communicates with our red siren's microcontroller.  You can see it gets its network connection from the OkHttpClient that is created here in the Dagger Component.

>Let’s talk about ***how we can mock the network calls we make using this client***.  
>What we want to do is replace the OkHttpClient dependency you see here with our own modified version.  One that can provide mock network responses.  
>The best way to ***do this is with DaggerMock*** another open source library.

>With DaggerMock, we ***don’t have to modify our production code***.  We’re going to create a special Espresso ***TestRule*** that will swap out Dagger dependencies directly as needed while running our tests.


## Step 5 - Using DaggerMock with Unmocked network calls


### 5_1 - Create DaggerMock TestRule (live template 'step5_1_createRule')

Create new file **app/src/androidTest/java/com/ndipatri/iot/zeroto98/ApplicationComponentTestRule.kt**

Type the following content:

```kotlin
class ApplicationComponentTestRule: DaggerMockRule<ApplicationComponent>(

   // The Dagger component whose dependencies we are mocking
   ApplicationComponent::class.java,

   // The modules that can have their dependencies overridden
   ApiModule()) {


}
```

>We’re **not going to be overriding everything provided by our Dagger*** component, just the OkHttpClient dependency.

Insert Dagger component's builder and OkHttpClient provider as shown:

```kotlin
class ApplicationComponentTestRule: DaggerMockRule<ApplicationComponent>(

   // The Dagger component whose dependencies we are mocking
   ApplicationComponent::class.java,

   // The modules that can have their dependencies overridden
   ApiModule()) {


   {insert live template step5_1}


}
```

Add **okHttpClient** to the constructor like this:

```kotlin
class ApplicationComponentTestRule(okHttpClient: OkHttpClient) :
```

>We’ve built and configured our DaggerMockRule, ***let’s see how we use this DaggerMockRule*** inside of our Espresso test to inject our modified OkHttpClient


### 5_2 - Use DaggerMockRule in our test (live template 'step5_2_useDaggerMockRule')

Open file **app/src/androidTest/java/com/ndipatri/iot/zeroto98/MainActivityEspressoTest.kt** and insert live template as shown:

```kotlin
class MainActivityEspressoTest() {

    {insert live template step5_2}

    @get:Rule
    val rule = createComposeRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(
       MainActivity::class.java,
       false,
       false
    )
```

>This componentRule will run for every test, ***swapping out the Dagger component with the modified one provided by DaggerMock at runtime***.  >The Component and all of its dependencies will be the same as in production with the exception of the OkHttpClient which we are passing into our DaggerMockRule.

>Notice we’ve configured our OkHttpClient with a ***MockInterceptor, yet another open source library contribution***. So we’re using a real implementation of Retrofit and OkHttpClient, but we’re intercepting the network requests just before they go out over the wire and returning mock data.  
>***We haven’t configured the MockInterceptor yet***, so let’s so how this test runs at this point.


***Uninstall the app before running test.***


Run the test and show the Logger output where MockInterceptor is complaining because we have a network call that hasn’t been configured.

>All network calls that we are making during the test have to be explicitly mocked or they will cause an ***AssertionError by the MockInterceptor***

>This is actually how I recommend you use MockInterceptor: ***only mock the network calls that are actually made during the test!*** >Remember, it’s possible we’re building this test and we still don’t fully understand our application. So we let MockInterceptor tell us what calls we haven't mocked.

>Now lets go back and properly configure our MockInterceptor so our test will pass!


## Step 6 - Configure MockInterceptor


### 6_1 - Configure MockInterceptor (live template 'step6_1_configureMockInterceptor')

Open the file **app/src/androidTest/java/com/ndipatri/iot/zeroto98/MainActivityEspressoTest.kt** and insert the live template code as shown:

```kotin
@Test
fun showCurrentRedSirenState_off() {

   {insert live template step6_1}

   activityTestRule.launchActivity(Intent())

```

>As usual, we define our mocks before we launch our Activity.

>Our test makes ***two calls to the Particle Cloud***.  One gets the current siren state and other turns the siren on.  So we have to mock both of these responses.

>Our MockInterceptor Rule matches on the path of the outbound network request.

>The ***SirenStateResponse*** is what we get back from Retrofit in response to our network call.  This is how we configure our MockInterceptor Rule’s response as well.  We do have to convert it to JSON, however, before passing to the Rule.

>The last thing we can do before running this test is ***remove the ‘button.click()’*** view action.  
>Remember, we did this as a means of getting our live system to a known state.  
>Since we using mocks, we are disconnected from the live system and ***no longer have to do this***.

>The ‘UI polling’ feature of our try/catch block is no longer necessary, but we keep using it for other features it provides.

Run the test to show that we now have an end-to-end test of our application with the external dependency mocked.









