
echo "Starting Microservice01..."
cd microservice01
./mvnw clean compile exec:java -Dexec.mainClass=com.silmarils.microservice01.Microservice01Application &
cd ..

echo "Starting Microservice02..."
cd microservice02
./mvnw clean compile exec:java -Dexec.mainClass=com.silmarils.microservice02.Microservice02Application &
cd ..


wait
