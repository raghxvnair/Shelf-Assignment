**Rate Limiter Documentation**
**1. Approach and Reasoning**
**1.1 RESTful API Design**
* We implemented a RESTful API using Spring Boot for user management operations. This approach was chosen for its simplicity, scalability, and adherence to HTTP standards.
**1.2 Rate Limiting**
* We implemented a custom rate limiter to prevent API abuse. The rate limiter is annotation-based and operates independently for each API endpoint. This approach allows for fine-grained control over rate limits for different operations.
**1.3 Response**
* Comprehensive error handling was implemented in a DTO class.
* Response structure : {
success: boolean,
message : String,
data : User (Object)
}
**1.4 Data Persistence**
* Spring Data JPA was used for data persistence, providing a clean abstraction over the database operations and allowing for easy switching between different database providers if needed in the future.
**2. Assumptions**
* **User Identification:** We assumed that each user has a unique identifier (userId) which is provided when adding a new user.
* **Concurrent Requests:** We assumed that the application might receive concurrent requests and implemented thread-safe solutions accordingly.
* **Single Instance Deployment:** The current rate limiting implementation assumes a single instance deployment. For distributed environments, further modifications would be necessary.
**3. Alternate Solutions**
**3.1 Rate Limiting**
* **Redis-based Rate Limiter:** For distributed environments, a Redis-based rate limiter could be implemented to ensure consistency across multiple application instances.
* **Bucket4j:** This is a Java rate-limiting library that could be used instead of our custom implementation, offering more advanced features out of the box.
**4. Limitations of the Current Solution**
* **Distributed Environments:** The current rate limiter implementation is not suitable for distributed environments as it uses **in-memory storage**.
* **Persistence of Rate Limit Data:** Rate limit data is not persisted, meaning that **restarting **the application will **reset **all rate limit counters.
* **User Authentication:** The current implementation doesn't include user authentication, which would be necessary for a production environment.
**5. Guide to Start The Application** :
* run git clone https://github.com/raghxvnair/Shelf-Assignment.git
* start the spring application
**6. Future Scope** :
* Dockerise the application.
* Implement rate limiting persistent throughout the application.