Training App
====

## Technologies used
* Android Studio 1.3
* Android SDK 22
* Android Volley 1.0.18
* Gson 2.3.1

## Considerations
The web service backend ([training-service](https://github.com/aruizca/training-service)) of this app is hosted in Heroku free tier. This means the service will go to sleep after a period of inactivity of 30 minutes.

Therefore the first time this app is used, the course list will take more to load as the service has to start up before handling the request.

I have had to increase the request timeout of this particular use case to cover this scenario.
