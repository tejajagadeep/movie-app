#!/bin/sh

# Pull Docker images using docker-compose
docker-compose pull

# Start Docker containers using docker-compose
docker-compose up -d

# Run the container using the specified Docker image
docker run -it --rm -v /var/run/docker.sock:/var/run/docker.sock tejajagadeep/docker-compose-movie-container
