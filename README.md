# Weather-APP
An Android application meticulously crafted to provide users with a seamless and comprehensive weather experience. From accurate forecasts to real-time updates, it also empowers users to stay ahead of changing weather conditions with confidence and ease.

## Requirements Specifications:
➢ Main activity must show the one day forecast along with the current day’s temperature.

➢ It should be able to show the forecast of a city when searched for it.

## Main Activity:
This activity fetches weather data from an API (WeatherAPI) and displays it to the user.
### Imports: 
The code starts by importing necessary classes and libraries from Android and third-party libraries like Volley and Picasso.

### Activity Declaration: 
The MainActivity class is declared, extending AppCompatActivity.

### Variable Declarations: 
Several variables are declared to hold references to UI elements such as home, loading, cityName, temperature, etc. Also, variables like weatherModelArrayList, weatherAdapter, and locationManager are declared.

### Method description:
onCreate() Method: This method is called when the activity is created. It initializes UI elements, sets up listeners, and fetches weather data.

The setContentView() method sets the layout for the activity.
UI elements are initialized using findViewById().
Permissions for accessing device location are requested using requestPermissions().
The device's last known location is retrieved using getLastKnownLocation().
The getCityName() method is called to fetch the city name from the device's location.
The getWeatherDetails() method is called to fetch weather details using the city name.
onRequestPermissionsResult() Method: This method is called when the user responds to the permission request dialog. It handles the result of the permission request.

getCityName() Method: This method takes latitude and longitude as input parameters and returns the corresponding city name using Geocoder. It handles exceptions like IOException.

getWeatherDetails() Method: This method takes a city name as input, constructs a URL for fetching weather data from the WeatherAPI, and makes a request using Volley library.

The URL is constructed with the city name.
A JsonObjectRequest is created with the URL, and callbacks for successful and failed responses are defined.
In the successful response callback, weather data is parsed from the JSON response and displayed in the UI.
Weather data includes temperature, weather condition, weather icon, and hourly forecasts.
Icons and background images are loaded using the Picasso library.
This code demonstrates how to fetch weather data from an API, handle device permissions, and display the data in an Android application's UI. It utilizes libraries like Volley for network requests and Picasso for image loading, providing a smooth and efficient user experience.

## Weather Adapter:
It defines a RecyclerView adapter class named WeatherAdapter used to populate weather data into individual items of a RecyclerView.
This class extends RecyclerView.Adapter and is responsible for managing the data set and creating views for individual items in the RecyclerView.

Variables:

context: Holds the context of the application/activity.
weatherModels: An ArrayList containing weather data models.
Constructor:

Initializes the adapter with the context and the ArrayList of weather models.
onCreateViewHolder() Method:

Called when RecyclerView needs a new ViewHolder.
Inflates the layout for an individual weather item using the LayoutInflater.
onBindViewHolder() Method:

Called to bind data to the views of each RecyclerView item.
Gets the weather model object at the specified position.
Sets the temperature, wind speed, and time in the appropriate TextViews.
Loads the weather condition icon using Picasso into the ImageView.
getItemCount() Method:

Returns the total number of items in the data set.
ViewHolder Class:

Represents a single item view in the RecyclerView.

Holds references to the TextViews and ImageView inside the weather item layout.

Constructor:

Initializes the views by finding their respective IDs.
The ViewHolder class is static to improve performance by avoiding unnecessary instance creations.

Parsing Time:

Inside onBindViewHolder(), the time field from the WeatherModel is parsed from a specific format (yyyy-mm-dd hh:mm) to another format (hh:mm aa) using SimpleDateFormat.
This parsed time is then set to the time TextView.
This adapter class provides the necessary functionality to populate weather data into a RecyclerView efficiently. It handles the creation of individual item views and binds data to them, ensuring a smooth and optimized user experience when displaying weather information in the app's UI.

## Weather Model:
This defines a simple Java class named WeatherModel which represents a model for weather data.
This class represents a model for storing weather data, including attributes such as time, temperature, weather icon, and wind speed.

Attributes:

time: String representing the time of the weather data.
temperature: String representing the temperature.
icon: String representing the URL of the weather icon.
windSpeed: String representing the wind speed.
Constructor:

Initializes a WeatherModel object with the provided values for time, temperature, icon, and wind speed.
Getter and Setter Methods:

Getters and setters are provided for each attribute to retrieve and modify their values, respectively.

These methods enable accessing and modifying the attributes of a WeatherModel object from other parts of the application.

This class serves as a simple container for storing weather data, making it easy to manage and manipulate weather information within the application. It follows the principles of encapsulation by providing getter and setter methods to control access to its attributes.

## Output:

Day time Weather:

![Day time Weather](https://github.com/sriahri/Weather-APP/blob/main/Output/Day_image.png)

Night time Weather:

![Night time Weather](https://github.com/sriahri/Weather-APP/blob/main/Output/Night_image.png)

## References

[Volley](https://github.com/google/volley)

[Square](https://square.github.io/retrofit/)

[Picasso](https://github.com/square/picasso)

[Retrofit](https://github.com/square/retrofit/blob/master/retrofit-converters/gson/README.md)

[Android developer studio](https://developer.android.com/studio/run/device)

[Weather API](https://weatherapi.com/)