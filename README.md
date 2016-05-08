# Stock-Search-Engine-Android-mobile-app
Stock Search Engine Android mobile app developed using Android Studio
This project aims at developing an Android mobile application with the following functionality:

An Auto-complete edit box is provided to enter the stock name or symbol. The user can then select a stock from the suggestions. This feature would be developed using the Android's ‘AutoCompleteTextView’ component.Once the	user has provided data	and selected a	result from the autocomplete list he would	click on	‘Get Quote’, when validation must be done to check that the  entered data is valid. Once	the validation is successful, we would get the stock details using	PHP script hosted on Amazon Web Services/Google App Engine, which would return	the result in JSON format.	We would display the stock details in a ListView	component in the ‘Current’ tab. Furthermore, PHP script would	be responsible for rendering the HighCharts in the ‘Historical’ tab and also rending the news articles in the ‘News’	tab.	

Technologies used-
Android Studio for App develpment, Amazon AWS to host the PHP script, Markit on Demand API's for the stock search, Yahoo News feed API for the news feed, Google News feed API etc.
