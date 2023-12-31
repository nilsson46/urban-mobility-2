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
In the command field add the steps as image showing.
<br />
Then press "save"
<br />
These steps will run everytime you making a new build.
<br />
<br />
<img width="223" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/5a1df599-c4d6-4ed1-a293-fe3f99bc0503">
<br />
Now press "build now" and jenkins will build
<br />
<br /> 

## Setup with jenkins production environment. In this pipeline you need to have docker installed. 
<br /> 
<br /> 
<img width="189" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/08179763-8e66-48ea-b55c-7d96d5783b10">
<br />
Add a new job. 
<br />
Press the "new item" button. 
<br />
<br />

<img width="446" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/ea0ca9af-a80b-49da-bc1a-677a530f1fdf">
<br />
Enter the name of the repository and the production tag.
<br />
<br />

<img width="442" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/55aa743a-9786-4477-91a8-5da087e25116">

<br />
Go to configuration. Select git and then enter the repository url. 
<br />
Change branches to build to */main.
<br />
<br />
<img width="447" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/af6dc211-6e60-48e3-8fd5-740f0065a1e1">

<img width="444" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/c913eaed-34eb-4927-ab98-a79309c6124e">

<br />
Go down to add build step and select "Execute Windows batch command".
<br />
In the command field add the steps as image showing.
<br />
Then press "save"
<br />
These steps will run everytime you making a new build.
<br />
<br />
<img width="223" alt="image" src="https://github.com/nilsson46/urban-mobility-2/assets/105226137/5a1df599-c4d6-4ed1-a293-fe3f99bc0503">
<br />
Now press "build now" and jenkins will build
<br />
<br /> 
