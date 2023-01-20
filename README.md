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
The following 
#### Endpoints
Base url: <strong>http://localhost:8080/pesa-pal/problem3/api/v1</strong>.
The following are the resources available from the base url:
<ul>
    <li>
    <strong>Providing a client to the server</strong>
    <p>For the server to accept the client use request <a href="http://localhost:8080/pesa-pal/problem3/api/v1/solution/register">/solution/register</a> as a POST request and add json body similar to the one shown below.</p>
    <code>curl --location --request POST 'http://localhost:8080/pesa-pal/problem3/api/v1/solution/register' \
        --header 'Content-Type: application/json' \
        --data-raw '{ "client": "mandela" }'</code>
        <p>On success you will get a response with HTTP Status 201 to mean that it was a success otherwise you will get an exception with reason.</p>
        <img src="https://user-images.githubusercontent.com/54445311/213667946-e6cfcdb4-7667-48fc-90c6-fd9979bd9178.png"></img>
    </li>
    <br>
    <li>
    <strong>List clients on the server</strong>
    <p>To fetch list of active clients use <a href="http://localhost:8080/pesa-pal/problem3/api/v1/solution/clients">/solution/clients</a> as a GET request and with a json body as shown below.</p>
    <code>curl --location --request GET 'http://localhost:8080/pesa-pal/problem3/api/v1/solution/clients'</code>
    <p>On success you will get a response with HTTP Status 200 and json array body with the to mean that it was a success otherwise you will get an exception with reason.</p>
        <img src="https://user-images.githubusercontent.com/54445311/213669054-f9e855b3-1778-428d-a2e4-afe9dee3c16e.png"></img>

</ul>
    </li>
    <br>
    <li>
    <strong>List clients on the server</strong>
    <p>To fetch list of active clients use <a href="http://localhost:8080/pesa-pal/problem3/api/v1/solution/clients">/solution/clients</a> as a GET request and with a json body as shown below.</p>
    <code>curl --location --request GET 'http://localhost:8080/pesa-pal/problem3/api/v1/solution/clients'</code>
    <p>On success you will get a response with HTTP Status 200 and json array body with the to mean that it was a success otherwise you will get an exception with reason.</p>
        <img src="https://user-images.githubusercontent.com/54445311/213669054-f9e855b3-1778-428d-a2e4-afe9dee3c16e.png"></img>

</ul>
    </li>
    <br>
    


This repository is a simple Restful webservice built with Spring boot 3.0 and with Java 17.
