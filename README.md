# urban-mobility-2
## Setup with jenkins starting with develop enviroment.

<img width="403" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/a859b591-61fc-4578-8a04-79b9efd45b4b">
<br />
Add a new job. 
<br />
Press the "new item" button. 
<br />
<br />
<img width="830" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/0c25a40f-d51c-4688-a81e-f969c157b69e">
<br />
Enter the name of the repository and the development tag.
<br />
<br />

<img width="680" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/1248d397-439a-45c8-befc-5adc3d858515">
<br />
Go to configuration. Select git and then enter the repository url. 
<br />
Change branches to build to origin/dev.
<br />
<br />

<img width="648" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/c66fc698-b4a6-42ce-b96c-dff93b5cd87b">
<br />
Go down to add build step and select "Execute Windows batch command".
<br />
In the command field add the steps at image showing.
<br />
Then press save
<br />
These steps will run everytime you making a new build.
<br />
<br />
<img width="223" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/5a1df599-c4d6-4ed1-a293-fe3f99bc0503">
<br />
Now press build now and jenkins will build



