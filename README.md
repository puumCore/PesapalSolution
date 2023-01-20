# Pesapal Solution
This is a Spring boot v3.0 repository which attempts to solve Problem 3 as defined in the <a href="https://pesapal.freshteam.com/jobs/2OU7qEKgG4DR/junior-developer-23">Pesapal Careers portal</a>.

## How to Install and Run the Project
<ol>
   <li>Clone this repository</li>
   <li>Ensure you have Java 17 installed on your machine.</li>
   <li>Start your prefered Java supporting IDE e.g. Intellij.</li>
   <li>Open this project with the IDE and allow it to load and prepare the project to build with Gradle.</li>
   <li>Finally run the Gradle <strong>bootRun</strong> command.</li>
   <li>You can use <strong>Postman</strong> or <strong>cURL</strong> to test the end points.</li>
</ol>

### Problem 3: A distributed system.
Build a TCP server that can accept and hold a maximum of N clients (where N is configurable).
These clients are assigned ranks based on first-come-first-serve, i.e whoever connects first receives the next available high rank. Ranks are from 0–N, 0 being the highest rank.

Clients can send to the server commands that the server distributes among the clients. Only a client with a lower rank can execute a command of a higher rank client. Higher rank clients cannot execute commands by lower rank clients, so these commands are rejected. The command execution can be as simple as the client printing to console that command has been executed.

If a client disconnects the server should re-adjust the ranks and promote any client that needs to be promoted not to leave any gaps in the ranks.

## Solution
The following describes how i tackled the above problem. Let's start with the Endpoints then code, where i will show you how each of the endpoint functions was built.
#### Endpoints
> Base url: <strong>http://localhost:8080/pesa-pal/problem3/api/v1</strong>.

The following are the resources available from the base url:
<ul>
    <li>
    **Providing a client to the server**
    <p>For the server to accept the client, provide the client's name with the request <a href="http://localhost:8080/pesa-pal/problem3/api/v1/solution/register">/solution/register</a> as a POST request and add json body similar to the one shown below.</p>
    <code>curl --location --request POST 'http://localhost:8080/pesa-pal/problem3/api/v1/solution/register' \
        --header 'Content-Type: application/json' \
        --data-raw '{ "client": "mandela" }'</code>
    <p>On success you will get a response with CREATED http status to mean that it was a success otherwise you will get an exception with reason.</p>
    <img src="https://user-images.githubusercontent.com/54445311/213667946-e6cfcdb4-7667-48fc-90c6-fd9979bd9178.png"></img>
    <p>However when you try to add an already existing client you will receive a Bad Request error as shown below.</p>
    <img src="https://user-images.githubusercontent.com/54445311/213678087-6998fab2-6990-415b-8819-6a3620e0e9c8.png"></img>
    </li>
    
    <li>
    **List clients on the server**
    <p>To fetch list of active clients use <a href="http://localhost:8080/pesa-pal/problem3/api/v1/solution/clients">/solution/clients</a> as a GET request and with a json body as shown below.</p>
    <code>curl --location --request GET 'http://localhost:8080/pesa-pal/problem3/api/v1/solution/clients'</code>
    <p>On success you will get a response with OK http status and json array body with the to mean that it was a success otherwise you will get an exception with reason.</p>
    <img src="https://user-images.githubusercontent.com/54445311/213669054-f9e855b3-1778-428d-a2e4-afe9dee3c16e.png"></img>
    </li>
    
    <li>
    **Ping server**
    <p>The client is required to <strong>ping</strong> the server <strong>after every 60 seconds</strong> with <a href="http://localhost:8080/pesa-pal/problem3/api/v1/solution/online">/solution/online</a> as a POST request and with a json body as shown below.</p>
    <code>curl --location --request POST 'http://localhost:8080/pesa-pal/problem3/api/v1/solution/online' \
        --header 'Content-Type: application/json' \
        --data-raw '{"client": "mandela"}'</code>
    <p>On success you will get a response with HTTP Status 200 to mean that it was a success otherwise you will get an exception with reason.</p>
    <img src="https://user-images.githubusercontent.com/54445311/213679537-34adfaa1-c99b-41a5-b201-c973bc9c9d0c.png"></img>
    <p>However when the target client doesn't exist you will receive a Not Found error as shown below.</p>
    <img src="https://user-images.githubusercontent.com/54445311/213680078-7be113fb-3cfa-44e0-bd83-a72ed15b8695.png"></img>
    </li>
    
    <li>
    **Execute commands**
    <p>The client is required to provide a command using <a href="http://localhost:8080/pesa-pal/problem3/api/v1/solution/cmd">/solution/cmd</a> as a POST request and with a json body as shown below.</p>
    <code>curl --location --request POST 'http://localhost:8080/pesa-pal/problem3/api/v1/solution/cmd' \
        --header 'Content-Type: application/json' \
        --data-raw '{
                      "client": "perez",
                      "cmd": "Hello world"
                    }'</code>
    <p>On success you will get a response with HTTP Status 200 to mean that it was a success otherwise you will get an exception with reason.</p>
    <img src="https://user-images.githubusercontent.com/54445311/213682558-6fe776c9-84e2-4177-9777-002ee6f24445.png"></img>
    <p>The following are some of the exceptions that can be thrown:</p>
    <ol>
        <li>
           <br>
           <img src="https://user-images.githubusercontent.com/54445311/213682089-082f5412-628f-4f6d-8b46-633aa1f119b4.png"></img>
        </li>
        <li>
           <br>
           <img src="https://user-images.githubusercontent.com/54445311/213682247-fafdb020-2fda-48cf-84ef-5591e0ea5f32.png"></img>
        </li>
        <li>
           <br>
           <img src="https://user-images.githubusercontent.com/54445311/213682334-b363f45f-b1d1-47de-850d-7663a3675f07.png"></img>
        </li>
    </ol>
    </li>
    
</ul>

#### Code
The following are code snipnnets for important functions in the application:

<ol>
    <li>
    <strong>Service Class</strong>
    <br>
      https://github.com/puumCore/PesapalSolution/blob/457fe5ffe0d095c098142b3161dd4abd170e8be9/src/main/java/com/pesapal/problem3/PesapalSolution/Assistant.java#L18-L115
    </li>

    <li>
    <strong>Scheduler Class</strong>
    <br>
    https://github.com/puumCore/PesapalSolution/blob/457fe5ffe0d095c098142b3161dd4abd170e8be9/src/main/java/com/pesapal/problem3/PesapalSolution/Daemon.java#L18-L32
    </li>

    <li>
    <strong>Controller Class</strong>
    <br>
    https://github.com/puumCore/PesapalSolution/blob/457fe5ffe0d095c098142b3161dd4abd170e8be9/src/main/java/com/pesapal/problem3/PesapalSolution/Controller.java#L21-L80
    </li>
   
</ol>
