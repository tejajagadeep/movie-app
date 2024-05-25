#!/bin/bash

# List of directories
directories=(
    "../../movie-backend/config-server"
    "../../movie-backend/api-gateway"
    "../../movie-backend/authentication-service"
    "../../movie-backend/eureka-server"
    "../../movie-backend/swagger-server"
    "../../movie-backend/movie-service"
    "../../movie-backend/userprofile-service"
    "../../movie-backend/wishlist-service"
)

# Loop through each directory and run mvn install
for dir in "${directories[@]}"; do
    if [ -d "$dir" ]; then
        echo "Running mvn install in $dir"
        (cd "$dir" && mvn install)
    else
        echo "Directory $dir does not exist"
    fi
done
