# spring-data-redis-lettuce-tls

Example of connecting to a Redis Enterprise database using [Spring Data Redis](https://github.com/spring-projects/spring-data-redis) and [Lettuce](https://github.com/lettuce-io/lettuce-core) with one-way TLS.

Once TLS is enabled for the database, to connect you must retrieve the cluster’s proxy certificate from the UI (Settings→General→Proxy Certificate), [REST API](https://docs.redis.com/latest/rs/references/rest-api/requests/cluster/certificates/#get-cluster-certificates), or copy it from the default path on one of the cluster nodes (`/etc/opt/redislabs/proxy_cert.pem`) and configure your connection code to perform the following:
 
1. Import the cacert to your Java truststore.
2. Configure the location and password of the truststore.
3. Build the SSL configuration for the client using `SslOptions`.
4. Build the optional settings for Redis client using `ClientOptions`.
5. Apply the SSL settings to the `LettuceClientConfiguration.builder()` using `.clientOptions()`.

Here is an example of creating a truststore file and adding the cluster's proxy certificate.

`keytool -import -trustcacerts -keystore redistruststore.jks -storepass <truststore_password> -file </path/to/proxy_cert.pem> -alias proxy_cert`

Now the truststore file and password should be referenced in `application.properties`:

`spring.redis.ssl.trust-store=<./path/to/redistruststore.jks>`\
`spring.redis.ssl.trust-store-password=<truststore_password>`

In this repo a `.env` file is used to reference environmental variables for these values. This requires that `spring.config.import=optional:file:.env[.properties]` be added to `application.properties`.