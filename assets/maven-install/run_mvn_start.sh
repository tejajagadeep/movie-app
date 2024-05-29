#!/bin/bash

# List of directories
directories=(
    "../../movie-backend/config-server"
    "../../movie-backend/eureka-server"
    "../../movie-backend/authentication-service"
    "../../movie-backend/userprofile-service"
    "../../movie-backend/movie-service"
    "../../movie-backend/wishlist-service"
    "../../movie-backend/api-gateway"
    "../../movie-backend/swagger-server"

)

# Loop through each directory and run mvn install
# for dir in "${directories[@]}"; do
#     if [ -d "$dir" ]; then
#         echo "Starting application in $dir"
#         (cd "$dir" && mvn spring-boot:run &)
#     else
#         echo "Directory $dir does not exist"
#     fi
# done

# Function to start an application and wait for it to be ready
start_application() {
    local dir="$1"
    local log_file="${dir//\//_}.log"
    
    echo "Starting application in $dir, logging to $log_file"
    (cd "$dir" && mvn spring-boot:run > "../${log_file}" 2>&1 &)
    
    # Optionally wait for the application to be ready
    # This can be customized depending on your application's readiness criteria
    sleep 10  # Adjust sleep duration as needed for your applications to start up
}

# Loop through each directory and start the application sequentially
for dir in "${directories[@]}"; do
    if [ -d "$dir" ]; then
        start_application "$dir"
    else
        echo "Directory $dir does not exist"
    fi
done

echo "All applications started in the specified order"

echo "All applications started"
