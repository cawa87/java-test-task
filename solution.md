Running:

    mvnw package
    java -jar target/account-api-0.0.1-SNAPSHOT.jar
    
Then navigate to [https://localhost:8443/swagger-ui.html](https://localhost:8443/swagger-ui.html).
Accept self-signed certificate.

Use `admin`/`admin` to signin (basic auth is also ok). This is the first user.

Notes on this solution:
1. Current solution uses inmemory H2 for easy testing. It's simple to replace it in favor of 
   some production-quality DB, like PostgreSQL for example.

2. Obvious scalability bottleneck are SSL connections as it's costly to create. These are 
   pooled in some ad-hock actor-type pool. For ease of implementation. For production it's better
   to pool some other way. Nevertheless current solution should be pretty fast in this area under load.

3. Scalability path:
   - SQL can be hidden under some sort of cache, ranging from in-memory to external, like Redis
   - more instances can be added as those are stateless, this might impose some issues on cache sync
   - SQL should handle a lot of requests if properly cached, at some point it can be backed by slaves
   - the next step would be something really scalable, like Cassandra, but it'll be overkill at the start

Bonus items:
- Some kind of authentication. Although it's not used to restrict access to accounts.
- Simple unit test for list/create.

File upload is just another pack of model/controller, and will require some kind of object storage to 
implement it in scalable way.