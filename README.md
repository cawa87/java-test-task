# java-test-task

*Fork and make PR after complete*

## Create a HTTP REST API with the following functionality:

1) A simple CRUD(Create Read Update Delete) REST JSON service for accounts under the route "/accounts". Important: the accounts must have a unique integer id for getting accountDetails in point "2" of the required task.

2) A third-party service is used to get an accountDetails string. The third-party service uses socket communication with TLS1.2, you should be able to establish a connection and send/receive JSON messages. For getting the accountDetails string, you must send a JSON request in the form of `{"id":3}`, and will receive a JSON response in the form of `{"id":3, accountDetails:"jhf7yf83h92h37d287u2nc9728c9h02897gc230c308g"}`. Service URI `94.130.41.74:8000`

### Optional functionality for bonus points:

1) File upload and listing under the route "/files".

2) There should be some kind of authentication in order to make requests into the REST service. The accounts added should be able to login and also add accounts.

3) Unit tests

### Technical Requirements:
- All data must be save to a database
- All requests/responses, except for file upload requests, must be in the form of JSON
- The REST service should use HTTPS
- The system should be implemented with scalability and high-load in mind
- Code should be written in a readable and maintainable way
- The solution can use any sound technology/library in Java, bare minimum is Spring

### How to:
- Plain build with Main file located in com.example.Main
- Simple HATEOAS like `curl http://localhost:8080/accounts` and `curl -i -X POST -H "Content-Type:application/json" -d "{  \"email\" : \"email@example.com\",  \"name\" : \"Admin\", \"accountDetailsId\" : 5}" http://localhost:8080/accounts`
- `curl http://localhost:8080/accountDetails?id=3`


