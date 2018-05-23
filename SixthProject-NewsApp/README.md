# Sixth Project - News App, Stage 1

PROJECT SPECIFICATION
1. App contains a main screen which displays multiple news stories.

2. Each list item on the main screen displays relevant text and information about the story.

3. The title of the article and the name of the section that it belongs to are required field.

4. If available, author name and date published should be included. Please note not all responses will contain these pieces of data, but it is required to include them if they are present.

5. Images are not required.

6. Stories shown on the main screen update properly whenever new news data is fetched from the API.

7. Clicking on a story uses an intent to open the story in the user’s browser.

8. App queries the content.guardianapis.com api to fetch news stories related to the topic chosen by the student, using either the ‘test’ api key or the student’s key.

9. The JSON response is parsed correctly, and relevant information is stored in the app.

10. When there is no data to display, the app shows a default TextView that informs the user how to populate the list.

11. The app checks whether the device is connected to the internet and responds appropriately. The result of the request is validated to account for a bad server response or lack of server response.

12. Networking operations are done using a Loader rather than an AsyncTask.

13. The intent of this project is to give you practice writing raw Java code using the necessary classes provided by the Android framework; therefore, the use of external libraries for the core functionality will not be permitted to complete this project.



This project is about combining various ideas and skills we’ve been practicing throughout the course. They include:
  - Connecting to an API
  - Parsing the JSON response
  - Handling error cases gracefully
  - Updating information regularly
  - Using an AsyncTask
  - Doing network operations independent of the Activity lifecycle


# Realisation:


![GitHub Logo](Screenshots/screen1.png)  ![GitHub Logo](Screenshots/screen2.png)  ![GitHub Logo](Screenshots/screen3.png)  ![GitHub Logo](Screenshots/screen4.png)  ![GitHub Logo](Screenshots/screen5.png)  ![GitHub Logo](Screenshots/screen6.png)  
  
