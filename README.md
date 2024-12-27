Simple Weather Application

User searches for a Location ex. "London", "India", "Los Angeles". Zip codes also work
Application will then search the specified location using the WeatherAPI endpoint - http://api.weatherapi.com/v1/current.json?
It appends the search query and API key to the API call and uses KtorHttpClient to get a response
Response is mapped a response model
Response model is mapped to a domain weatherInfo model.
weatherInfo model is consumed via the UI to show a searchedLocationItem
If the user clicks the searchedLocationItem then that location will be saved and the user will see a savedLocationItem with more advanced details
Also, when the user clicks the searchedLocation item, that location search query is saved via data store and restored the next time the user opens the app
