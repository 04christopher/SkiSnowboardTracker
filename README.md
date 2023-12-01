# My Personal Project

## Ski and Snowboard Run Tracker and Organizer

This application will allow you to record, review, and plan your Ski Trips.
For each mountain, you will be able to view the trails that are available and 
information about them such as **difficulty, length**, and **features** (such as moguls, trees, etc.). 
For each run, you can also track your own information, such as the amount of times you've done a run, personal bests and average times,
total runs done,
total elevation and length travelled, and more.

This application will be useful to anyone who Skis or Snowboards in the lower mainland! Users can use this application to look at information
and track and plan  their own trips.

I'm interested in building this project because this is an application with features that I 
would actually use in my active life, and I can share it with others in Vancouver.


## User Stories

- As a user, I want to be able to track the amount of times I have done a Ski Run.
- As a user, I want to be able to track the time it took me to do a Ski run.
  - As a user, I want to be able to see information about any Ski run, such as difficulty, length, elevation, etc.
- As a user, I want to be able to record lists of trips (Ski-trail combinations) that I've done.
- As a user, I want to be able to see my personal bests and average times.
- As a user, I want to be able to add a ski trail to a Mountain.
- As a user, I want to be able to see all the trails on a Mountain.
- As a user, I want to be able to see a list of run-times.
- As a user, I want to be able to check the total distance of my runs for the day.
- As a user, I want to be able to save the state of a mountain, and all trails and trips I have added to a mountain, if I choose.
- As a user, I want to be able to load my previous trails and mountains from a closed state, if I choose.
- As a user, I want to be able to see a graphical representation of the difficulty of a trail. (Through color)
- As a user, I want to be able to see a map of a mountain and the trails on it.

## Instructions for Grader:
- To generate a trail, click See Mountains, and then click on a mountain (Grouse), then click Add Trail.
- To see the statistics and trails on a mountain, click on a mountain (Grouse), then click See Trails.
- There is a header with text over it when the program starts (it is a picture of mountains).
- To save the state of the file, click the save button on the first frame.
- To reload the state of the file, click the load button on the first frame.


## Phase 4: Task 2 (Event Log Sample)
- Thu Nov 30 17:09:44 PST 2023: Added trail The Cut to Grouse
- Thu Nov 30 17:09:44 PST 2023: Added trail Purgatory to Grouse
- Thu Nov 30 17:09:44 PST 2023: Added trail Peak to Grouse
- Thu Nov 30 17:09:44 PST 2023: Added trail Lower Peak to Grouse
- Thu Nov 30 17:09:44 PST 2023: Added trail Expo Glades to Grouse
- Thu Nov 30 17:09:44 PST 2023: Added mountain Grouse to map.
- Thu Nov 30 17:09:48 PST 2023: Added mountain Grouse to map.
- Thu Nov 30 17:09:48 PST 2023: Added trail The Cut to Grouse
- Thu Nov 30 17:09:48 PST 2023: Added trail Purgatory to Grouse
- Thu Nov 30 17:09:48 PST 2023: Added trail Jacob's Trail to Grouse
- Thu Nov 30 17:10:24 PST 2023: Recorded trip Vacation!
- Thu Nov 30 17:10:41 PST 2023: Added trail EventLoggingTrail to Grouse
- Thu Nov 30 17:10:53 PST 2023: Added mountain Seymour!!!! to map.

## Phase 4: Task 3
There are many problems I ran into that made me think about refactoring my code.
First, I would make it so that The constructor for Mountain does not require a list of Trip and Trail, and also that 
Trip does not require a list of Run. I found that these constructors were frustrating as I would almost always add the 
fields after instantiating the object, so the constructor parameters were useless anyway. Secondly, I would move
Trips from being a field of mountain to a field of Mappy, and it could perhaps have a field of mountain. This would be
less confusing and I could access trips from the main object of Mappy. Also, I would move certain search functions 
that were implemented in my UI to the actual class. For example, searchTrailByName() should have been a function in 
Mountain or Mappy that took a String and returned a Trail, that way I could use the function outside of the file it is 
implemented in.

Thanks for being such a great TA :)