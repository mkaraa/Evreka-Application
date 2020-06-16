# Evreka Application

An android application which consists of two main activity (MainActivity and OperatorActivity).

Firebase is used for storing data (which are containerId, sensorId, fullnessRate, temperature, latitude, longitude) and authentication with email and password. Besides, all google map markers locations fetching from firebase.

(Ex Username : evreka@test.com - Password: 123456)

## KIP Suggestions
- Short time to load data from the cloud because it is the most important less waiting time for users. Managing asynchronous methods/functions and using Thread mechanism.

- Recently, mobile phones have many screen sizes and resolutions. So, testing in all sizes phone is better for users.

- Feedback button may be good for developers because some mobile phones' operating systems slightly different from others.

- Adding an online analytic tool is good for testing in long time and take result.

- Avoiding too many screens for users because some users can be confused.

## ScreenShots
Login Screen

![Login](https://github.com/mkaraa/Evreka-Application/blob/master/screenshots/Screen%20Shot%202020-06-16%20at%2020.43.27.png?raw=true
)


Users in Firebase


![Users in firebase](https://github.com/mkaraa/Evreka-Application/blob/master/screenshots/Screen%20Shot%202020-06-16%20at%2020.51.13.png?raw=true)


Data Structure 


![Database](https://github.com/mkaraa/Evreka-Application/blob/master/screenshots/Screen%20Shot%202020-06-16%20at%2020.50.57.png?raw=true)


Operator Screen - Using cluster Manager to fast load markers


![GoogleMap with ClusterManager](https://github.com/mkaraa/Evreka-Application/blob/master/screenshots/Screen%20Shot%202020-06-16%20at%2020.42.28.png?raw=true)


## License
[MIT](https://choosealicense.com/licenses/mit/)
